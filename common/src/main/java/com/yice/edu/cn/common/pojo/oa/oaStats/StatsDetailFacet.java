package com.yice.edu.cn.common.pojo.oa.oaStats;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class StatsDetailFacet {
    @ApiModelProperty("总的次数")
    private List<Pipe> total;
    @ApiModelProperty("次数最多的教师")
    private List<Pipe> maxPerson;
    @ApiModelProperty("次数最多的部门")
    private List<Pipe> maxDepartment;
    @ApiModelProperty("次数最多的请假类别")
    private List<Pipe> maxLeaveType;
    @ApiModelProperty("申请总节数")
    private int totalSections;

    @Data
    public static class Pipe{
        private String id;
        private int count;
        private String initiator;
        private String departmentName;
        private String leaveTypeName;
    }
}
