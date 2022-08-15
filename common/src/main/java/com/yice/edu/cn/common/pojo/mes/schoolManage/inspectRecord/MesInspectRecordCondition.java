package com.yice.edu.cn.common.pojo.mes.schoolManage.inspectRecord;

import lombok.Data;

@Data
public class MesInspectRecordCondition {
    private String institutionId;//二级制度id
    private String institutionName;//二级制度名称
    private Integer institutionScore;//事件分值
    private String institutionDesc;//事件描述
    private String happenDate;//发生日期

}
