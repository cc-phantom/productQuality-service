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
package me.zhengjie.modules.system.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.system.domain.PqProduct;
import me.zhengjie.modules.system.service.PqProductService;
import me.zhengjie.modules.system.service.dto.PqProductQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
 * @website https://el-admin.vip
 * @author mashanshan
 * @date 2021-08-30
 **/
@RestController
@RequiredArgsConstructor
@Api(tags = "产品管理")
@RequestMapping("/api/pqProduct")
public class PqProductController {

    private final PqProductService pqProductService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('pqProduct:list')")
    public void download(HttpServletResponse response, PqProductQueryCriteria criteria) throws IOException {
        pqProductService.download(pqProductService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询产品")
    @ApiOperation("查询产品")
    @PreAuthorize("@el.check('pqProduct:list')")
    public ResponseEntity<Object> query(PqProductQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(pqProductService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增产品")
    @ApiOperation("新增产品")
    @PreAuthorize("@el.check('pqProduct:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody PqProduct resources){
        return new ResponseEntity<>(pqProductService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改产品")
    @ApiOperation("修改产品")
    @PreAuthorize("@el.check('pqProduct:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody PqProduct resources){
        pqProductService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除产品")
    @ApiOperation("删除产品")
    @PreAuthorize("@el.check('pqProduct:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        pqProductService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}