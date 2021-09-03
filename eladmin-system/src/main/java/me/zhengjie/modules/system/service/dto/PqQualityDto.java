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
package me.zhengjie.modules.system.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author mashanshan
* @date 2021-08-29
**/
@Data
public class PqQualityDto implements Serializable {

    private Long id;

    /** 产品id */
    private PqProductDto pqProduct;

    /** 组织id */
//    private Long deptId;
    private DeptSmallDto dept;

    /** 单元测试运行状态：0 未知；1 成功；2 失败 */
    private Integer unitTestStatus;

    /** 有断言且运行成功的用例数 */
    private Integer successNumWithAssert;

    /** 没有断言但运行成功的用例数 */
    private Integer successNumWithoutAssert;

    /** 断言运行失败的用例数 */
    private Integer failNumWithAssert;

    /** 异常失败的用例数 */
    private Integer failNumWithException;

    /** 单元测试用例个数 */
    private Integer testNum;

    /** 单元测试用例质量（0-100分）（要求0-100之间数字格式，最多两位小数） */
    private Float unitTestQualityScore;

    /** 单元测试有效性（0-100分）（要求0-100之间数字格式，最多两位小数） */
    private Float unitTestEffectivenessScore;

    /** 被评审产品代码行数 */
    private Integer codeLineNum;

    /** 千行代码单元测试用例密度（=有断言且运行成功的用例数/被评审产品代码行数*1000）,结果保留2位小数 */
    private Float unitTestDensity;

    /** 行覆盖率，填写0-100之间的数字整数格式 */
    private Integer lineCoverageRate;

    /** 分支覆盖率，填写0-100之间的数字整数格式 */
    private Integer branchCoverageRate;

    /** 产品质量主观打分（0-100分）（要求数字格式，最多两位小数） */
    private Float productQualitySubjectiveScore;

    /** 产品质量综合得分,公式是：单元测试用例质量*20%+单元测试有效性*10%+千行代码单元测试用例密度*30%+行覆盖率*20%+分支覆盖率*10%+产品代码质量打分*10% */
    private Float productQualityComprehensiveScore;

    /** 产品质量投票得分 */
    private Float voteScore;

    /** 产品质量最终得分=产品质量综合得分+产品质量投票得分 */
    private Float productQualityFinalScore;

    /** 是否启用：0 不启用；1 启用 */
    private Integer enabled;

    /** 创建时间 */
    private Timestamp createTime;

    /** 更新时间 */
    private Timestamp updateTime;
}