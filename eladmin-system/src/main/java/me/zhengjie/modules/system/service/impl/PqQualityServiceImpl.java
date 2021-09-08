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

import me.zhengjie.modules.system.domain.PqQuality;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.PqQualityRepository;
import me.zhengjie.modules.system.service.PqQualityService;
import me.zhengjie.modules.system.service.dto.PqQualityDto;
import me.zhengjie.modules.system.service.dto.PqQualityQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.PqQualityMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author mashanshan
* @date 2021-08-29
**/
@Service
@RequiredArgsConstructor
public class PqQualityServiceImpl implements PqQualityService {

    private final PqQualityRepository pqQualityRepository;
    private final PqQualityMapper pqQualityMapper;

    @Override
    public Map<String,Object> queryAll(PqQualityQueryCriteria criteria, Pageable pageable){
        Page<PqQuality> page = pqQualityRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(pqQualityMapper::toDto));
    }

    @Override
    public List<PqQualityDto> queryAll(PqQualityQueryCriteria criteria){
        return pqQualityMapper.toDto(pqQualityRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public PqQualityDto findById(Long id) {
        PqQuality pqQuality = pqQualityRepository.findById(id).orElseGet(PqQuality::new);
        ValidationUtil.isNull(pqQuality.getId(),"PqQuality","id",id);
        return pqQualityMapper.toDto(pqQuality);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PqQualityDto create(PqQuality resources) {
        return pqQualityMapper.toDto(pqQualityRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PqQuality resources) {
        PqQuality pqQuality = pqQualityRepository.findById(resources.getId()).orElseGet(PqQuality::new);
        ValidationUtil.isNull( pqQuality.getId(),"PqQuality","id",resources.getId());
        pqQuality.copy(resources);
        pqQualityRepository.save(pqQuality);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            pqQualityRepository.deleteById(id);
        }
    }

    @Override
    public void deleteByProductId(Long productId) {
        if (pqQualityRepository.queryByProductId(productId).isPresent()) {
            pqQualityRepository.deleteByProductId(productId);
        }
    }

    @Override
    public void download(List<PqQualityDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (PqQualityDto pqQuality : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("产品", pqQuality.getPqProduct() == null ? "" : pqQuality.getPqProduct().getProductName());
            map.put("组织名称", pqQuality.getDept() == null ? "" : pqQuality.getDept().getName());
            map.put("单元测试运行状态：0 未知；1 成功；2 失败", pqQuality.getUnitTestStatus());
            map.put("有断言且运行成功的用例数", pqQuality.getSuccessNumWithAssert());
            map.put("没有断言但运行成功的用例数", pqQuality.getSuccessNumWithoutAssert());
            map.put("断言运行失败的用例数", pqQuality.getFailNumWithAssert());
            map.put("异常失败的用例数", pqQuality.getFailNumWithException());
            map.put("单元测试用例个数", pqQuality.getTestNum());
            map.put("单元测试用例质量（0-100分）（要求0-100之间数字格式，最多两位小数）", pqQuality.getUnitTestQualityScore());
            map.put("被评审产品代码行数", pqQuality.getCodeLineNum());
            map.put("千行代码单元测试用例密度（=有断言且运行成功的用例数/被评审产品代码行数*1000）,结果保留2位小数", pqQuality.getUnitTestDensity());
            map.put("行覆盖率，填写0-100之间的数字整数格式", pqQuality.getLineCoverageRate());
            map.put("分支覆盖率，填写0-100之间的数字整数格式", pqQuality.getBranchCoverageRate());
            map.put("产品质量主观打分（0-100分）（要求数字格式，最多两位小数）", pqQuality.getProductQualitySubjectiveScore());
            map.put("产品质量综合得分,公式是：单元测试用例质量*20%+单元测试有效性*10%+千行代码单元测试用例密度*30%+行覆盖率*20%+分支覆盖率*10%+产品代码质量打分*10%", pqQuality.getProductQualityComprehensiveScore());
            map.put("产品质量投票得分", pqQuality.getVoteScore());
            map.put("产品质量最终得分=产品质量综合得分+产品质量投票得分", pqQuality.getProductQualityFinalScore());
            map.put("是否启用：0 不启用；1 启用", pqQuality.getEnabled());
            map.put("创建时间", pqQuality.getCreateTime());
            map.put("更新时间", pqQuality.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    /**
     * 批量新增
     * @param pqQualities
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<PqQualityDto> createAll(List<PqQuality> pqQualities) {
        return pqQualityMapper.toDto(pqQualityRepository.saveAll(pqQualities));
    }
}
