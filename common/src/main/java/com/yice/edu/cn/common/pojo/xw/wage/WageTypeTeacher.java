package com.yice.edu.cn.common.pojo.xw.wage;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

/**
*
*工资教师记录表
*
*/
@Data
public class WageTypeTeacher{

    @ApiModelProperty(value = "主键id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "职工名称",dataType = "String")
    private String name;
    @ApiModelProperty(value = "工号",dataType = "String")
    private String workNumber;
    @ApiModelProperty(value = "工资类型id",dataType = "String")
    private String wageTypeId;
    @ApiModelProperty(value = "发放时间",dataType = "String")
    private String releaseTime;
    @ApiModelProperty(value = "0代表未发放1代表已发放",dataType = "String")
    private String releaseState;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "教师id",dataType = "String")
    private String teacherId;
    @ApiModelProperty(value = "工资时间",dataType = "String")
    private String salaryTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    //额外字段
    @ApiModelProperty(value = "属性id列表",dataType = "String")
    private String attributeIdList[];
    @ApiModelProperty(value = "属性列表",dataType = "String")
    private String attributeNameList[];
    @ApiModelProperty(value = "值列表",dataType = "String")
    private String valueList[];
    @ApiModelProperty(value = "权重列表",dataType = "String")
    private String sortList[];
    @ApiModelProperty(value = "值",dataType = "String")
    private Map<String, String> valueSize;
    @ApiModelProperty(value = "属性名",dataType = "String")
    private String attributeName;
    @ApiModelProperty(value = "属性ID",dataType = "String")
    private String wageAttributeId;
    @ApiModelProperty(value = "属性IDmap",dataType = "String")
    private Map<String,Object> propmap;
    @ApiModelProperty(value = "属性namemap",dataType = "String")
    private Map<String,Object>propNameMap;
    @ApiModelProperty(value = "记录Id集合",dataType = "String")
    private String selAllWorkId[];
    @ApiModelProperty(value = "时间范围",dataType = "String")
    private String rangeTime[];
    @ApiModelProperty(value = "开始时间",dataType = "String")
    private String startTime;
    @ApiModelProperty(value = "结束时间",dataType = "String")
    private String endTime;
}
