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
package me.zhengjie.modules.system.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author mashanshan
* @date 2021-08-29
**/
@Entity
@Data
@Table(name="pq_quality")
public class PqQuality implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "id")
    private Long id;

    @Column(name = "product_id",nullable = false)
    @NotNull
    @ApiModelProperty(value = "产品id")
    private Long productId;

    @Column(name = "dept_id",nullable = false)
    @NotNull
    @ApiModelProperty(value = "组织id")
    private Long deptId;

    @Column(name = "unit_test_status",nullable = false)
    @NotNull
    @ApiModelProperty(value = "单元测试运行状态：0 未知；1 成功；2 失败")
    private Integer unitTestStatus;

    @Column(name = "success_num_with_assert",nullable = false)
    @NotNull
    @ApiModelProperty(value = "有断言且运行成功的用例数")
    private Integer successNumWithAssert;

    @Column(name = "success_num_without_assert",nullable = false)
    @NotNull
    @ApiModelProperty(value = "没有断言但运行成功的用例数")
    private Integer successNumWithoutAssert;

    @Column(name = "fail_num_with_assert",nullable = false)
    @NotNull
    @ApiModelProperty(value = "断言运行失败的用例数")
    private Integer failNumWithAssert;

    @Column(name = "fail_num_with_exception",nullable = false)
    @NotNull
    @ApiModelProperty(value = "异常失败的用例数")
    private Integer failNumWithException;

    @Column(name = "test_num",nullable = false)
    @NotNull
    @ApiModelProperty(value = "单元测试用例个数")
    private Integer testNum;

    @Column(name = "unit_test_quality_score",nullable = false)
    @NotNull
    @ApiModelProperty(value = "单元测试用例质量（0-100分）（要求0-100之间数字格式，最多两位小数）")
    private Float unitTestQualityScore;

    @Column(name = "code_line_num",nullable = false)
    @NotNull
    @ApiModelProperty(value = "被评审产品代码行数")
    private Integer codeLineNum;

    @Column(name = "unit_test_density",nullable = false)
    @NotNull
    @ApiModelProperty(value = "千行代码单元测试用例密度（=有断言且运行成功的用例数/被评审产品代码行数*1000）,结果保留2位小数")
    private Float unitTestDensity;

    @Column(name = "line_coverage_rate",nullable = false)
    @NotNull
    @ApiModelProperty(value = "行覆盖率，填写0-100之间的数字整数格式")
    private Integer lineCoverageRate;

    @Column(name = "branch_coverage_rate",nullable = false)
    @NotNull
    @ApiModelProperty(value = "分支覆盖率，填写0-100之间的数字整数格式")
    private Integer branchCoverageRate;

    @Column(name = "product_quality_subjective_score",nullable = false)
    @NotNull
    @ApiModelProperty(value = "产品质量主观打分（0-100分）（要求数字格式，最多两位小数）")
    private Float productQualitySubjectiveScore;

    @Column(name = "product_quality_comprehensive_score",nullable = false)
    @NotNull
    @ApiModelProperty(value = "产品质量综合得分,公式是：单元测试用例质量*20%+单元测试有效性*10%+千行代码单元测试用例密度*30%+行覆盖率*20%+分支覆盖率*10%+产品代码质量打分*10%")
    private Float productQualityComprehensiveScore;

    @Column(name = "vote_score",nullable = false)
    @NotNull
    @ApiModelProperty(value = "产品质量投票得分")
    private Float voteScore;

    @Column(name = "product_quality_final_score",nullable = false)
    @NotNull
    @ApiModelProperty(value = "产品质量最终得分=产品质量综合得分+产品质量投票得分")
    private Float productQualityFinalScore;

    @Column(name = "enabled",nullable = false)
    @ApiModelProperty(value = "是否启用：0 不启用；1 启用")
    private Integer enabled;

    @Column(name = "create_time",nullable = false)
    @CreationTimestamp
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @Column(name = "update_time",nullable = false)
    @UpdateTimestamp
    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;

    public void copy(PqQuality source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}