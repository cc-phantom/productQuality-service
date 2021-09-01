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
import java.util.List;
import me.zhengjie.annotation.Query;

/**
* @website https://el-admin.vip
* @author mashanshan
* @date 2021-08-29
**/
@Data
public class PqQualityQueryCriteria{

    /** 精确 */
    @Query
    private Long productId;

    /** 精确 */
    @Query
    private Long deptId;

    /** 精确 */
    @Query
    private Integer unitTestStatus;

    /** 大于等于 */
    @Query(type = Query.Type.GREATER_THAN)
    private Integer branchCoverageRate;

    /** 大于等于 */
    @Query(type = Query.Type.GREATER_THAN)
    private Float productQualitySubjectiveScore;

    /** 大于等于 */
    @Query(type = Query.Type.GREATER_THAN)
    private Float productQualityComprehensiveScore;

    /** 大于等于 */
    @Query(type = Query.Type.GREATER_THAN)
    private Float voteScore;

    /** 大于等于 */
    @Query(type = Query.Type.GREATER_THAN)
    private Float productQualityFinalScore;

    /** 精确 */
    @Query
    private Integer enabled;
}