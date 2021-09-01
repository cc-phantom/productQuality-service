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
import me.zhengjie.modules.system.domain.PqQuality;
import me.zhengjie.modules.system.service.PqQualityService;
import me.zhengjie.modules.system.service.dto.PqQualityQueryCriteria;
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
* @date 2021-08-29
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "产品质量管理")
@RequestMapping("/api/pqQuality")
public class PqQualityController {

    private final PqQualityService pqQualityService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('pqQuality:list')")
    public void download(HttpServletResponse response, PqQualityQueryCriteria criteria) throws IOException {
        pqQualityService.download(pqQualityService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询产品质量")
    @ApiOperation("查询产品质量")
    @PreAuthorize("@el.check('pqQuality:list')")
    public ResponseEntity<Object> query(PqQualityQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(pqQualityService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增产品质量")
    @ApiOperation("新增产品质量")
    @PreAuthorize("@el.check('pqQuality:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody PqQuality resources){
        return new ResponseEntity<>(pqQualityService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改产品质量")
    @ApiOperation("修改产品质量")
    @PreAuthorize("@el.check('pqQuality:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody PqQuality resources){
        pqQualityService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除产品质量")
    @ApiOperation("删除产品质量")
    @PreAuthorize("@el.check('pqQuality:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        pqQualityService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}