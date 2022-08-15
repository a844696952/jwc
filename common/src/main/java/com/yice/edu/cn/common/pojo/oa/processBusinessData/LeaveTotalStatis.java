package com.yice.edu.cn.common.pojo.oa.processBusinessData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveTotalStatis {
    private long totalCount;//请假总次数
    private long totalPeople;//请假总人数
    private String maxType;//请假最多类别
    private String maxDepartmentName;//请假最多部门
}
