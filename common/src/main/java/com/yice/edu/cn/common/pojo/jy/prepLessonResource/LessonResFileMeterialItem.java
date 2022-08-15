package com.yice.edu.cn.common.pojo.jy.prepLessonResource;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
/**
*
*
*
*/
@Data
public class LessonResFileMeterialItem{

    @ApiModelProperty(value = "主键id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "资源文件id",dataType = "String")
    private String resourceFileId;
    @ApiModelProperty(value = "所属科目年级表id",dataType = "String")
    private String subjectMaterialId;
    @ApiModelProperty(value = "树路径",dataType = "String")
    private String path;
    @ApiModelProperty(value = "年段id",dataType = "String")
    private String annualPeriodId;
    @ApiModelProperty(value = "科目id",dataType = "String")
    private String courseId;
    @ApiModelProperty(value = "年级id",dataType = "String")
    private String gradeId;
    @ApiModelProperty(value = "教材id",dataType = "String")
    private String meterialId;
    @ApiModelProperty(value = "章节id",dataType = "String")
    private String meterialItemId;
    @ApiModelProperty(value = "角色，0教师(所有)，1家长",dataType = "String")
    private String role;

    @ApiModelProperty("年段名称")
    private String annualPeriodName;
    @ApiModelProperty("科目名称")
    private String courseName;
    @ApiModelProperty("年级名称")
    private String gradeName;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
