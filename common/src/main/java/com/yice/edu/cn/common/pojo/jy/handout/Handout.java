package com.yice.edu.cn.common.pojo.jy.handout;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

/**
*
*
*
*/
@Data
public class Handout implements Serializable {

    private static final long serialVersionUID = 4111754449853865797L;
    @ApiModelProperty(value = "讲义id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "教师id",dataType = "String")
    private String teacherId;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "讲义名称",dataType = "String")
    private String handoutName;
    @ApiModelProperty(value = "科目",dataType = "String")
    private String course;
    @ApiModelProperty(value = "附件个数",dataType = "Integer")
    private Integer fileNumber;
    @ApiModelProperty(value = "修改时间 ",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    //额外字段
    private List<HandoutFile> handoutFiles;
    private String[] searchTimeZone;
    private String searchStearTime;
    private String searchEndTime;
}
