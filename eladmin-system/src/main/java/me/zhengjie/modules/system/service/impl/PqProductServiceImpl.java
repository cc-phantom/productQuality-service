/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.modules.system.service.impl;

import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.domain.PqProduct;
import me.zhengjie.modules.system.domain.PqQuality;
import me.zhengjie.modules.system.repository.PqProductRepository;
import me.zhengjie.modules.system.service.PqProductService;
import me.zhengjie.modules.system.service.PqQualityService;
import me.zhengjie.modules.system.service.dto.PqProductDto;
import me.zhengjie.modules.system.service.dto.PqProductQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.PqProductMapper;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import me.zhengjie.utils.ValidationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author mashanshan
* @date 2021-08-29
**/
@Service
@RequiredArgsConstructor
public class PqProductServiceImpl implements PqProductService {

    private final PqQualityService pqQualityService;
    private final PqProductRepository pqProductRepository;
    private final PqProductMapper pqProductMapper;

    @Override
    public Map<String, Object> queryAll(PqProductQueryCriteria criteria, Pageable pageable) {
        Page<PqProduct> page = pqProductRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(pqProductMapper::toDto));
    }

    @Override
    public List<PqProductDto> queryAll(PqProductQueryCriteria criteria) {
        return pqProductMapper.toDto(pqProductRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    @Transactional
    public PqProductDto findById(Long id) {
        PqProduct pqProduct = pqProductRepository.findById(id).orElseGet(PqProduct::new);
        ValidationUtil.isNull(pqProduct.getId(), "PqProduct", "id", id);
        return pqProductMapper.toDto(pqProduct);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PqProductDto create(PqProduct resources) {
        PqProduct pqProduct = pqProductRepository.save(resources);
        PqProductDto productDto = pqProductMapper.toDto(pqProduct);
        PqQuality quality = new PqQuality();
        quality.setPqProduct(pqProduct);
        quality.setDept(pqProduct.getDept());
        pqQualityService.create(quality);
        return productDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PqProduct resources) {
        PqProduct pqProduct = pqProductRepository.findById(resources.getId()).orElseGet(PqProduct::new);
        ValidationUtil.isNull(pqProduct.getId(), "PqProduct", "id", resources.getId());
        pqProduct.copy(resources);
        pqProductRepository.save(pqProduct);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            if (pqProductRepository.findById(id).isPresent()) {
                pqProductRepository.deleteById(id);
            }
            pqQualityService.deleteByProductId(id);
        }
    }

    @Override
    public void download(List<PqProductDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (PqProductDto pqProduct : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("组织id", pqProduct.getDept() == null ? "" : pqProduct.getDept().getName());
            map.put("产品名称", pqProduct.getProductName());
            map.put("是否启用：0 不启用；1 启用", pqProduct.getEnabled());
            map.put("创建时间", pqProduct.getCreateTime());
            map.put("更新时间", pqProduct.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}