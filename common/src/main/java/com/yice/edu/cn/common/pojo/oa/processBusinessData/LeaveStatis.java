package com.yice.edu.cn.common.pojo.oa.processBusinessData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveStatis {
    private Integer count;
    private Double duration;
    private String initiator;
    private String departmentName;
    private String leaveTypeName;
}
