package com.yice.edu.cn.common.pojo.jy.prepLessonResource;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
*
*
*
*/
@Data
public class PrepLessonResourceFile{

    @ApiModelProperty(value = "运营平台文件id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "文件名",dataType = "String")
    private String filename;
    @ApiModelProperty(value = "文件地址",dataType = "String")
    private String fileUrl;
    @ApiModelProperty(value = "文件类型：1文档，2图片，3视频，4音频",dataType = "String")
    private String fileType;
    @ApiModelProperty(value = "文件下载次数",dataType = "Integer")
    private Integer downloadCount;
    @ApiModelProperty(value = "更新时间",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "上传人id",dataType = "String")
    private String adminId;
    @ApiModelProperty(value = "上传人姓名",dataType = "String")
    private String adminName;
    @ApiModelProperty(value = "文件大小",dataType = "String")
    private String size;
    @ApiModelProperty(value = "文件封面",dataType = "String")
    private String cover;
    @ApiModelProperty(value = "主讲人/来源",dataType = "String")
    private String speaker;
    @ApiModelProperty("查看次数")
    private Integer numLook;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    //额外字段
    private List<PrepLessonResourceFile> files;
    //设置文件与章节关联
    private LessonResFileMeterialItem LessonResFileMeterialItem;
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
    @ApiModelProperty(value = "角色",dataType = "String")
    private String role;
    @ApiModelProperty("年段名称")
    private String annualPeriodName;
    @ApiModelProperty("科目名称")
    private String courseName;
    @ApiModelProperty("年级名称")
    private String gradeName;
    @ApiModelProperty(value = "关联表id",dataType = "String")
    private String lessonResFileMeterialItemId;
    @ApiModelProperty("设置展示数量map")
    Map<String,String> fileShowNumByType;

    @ApiModelProperty("章节绑定id")
    private String boundId;
}
