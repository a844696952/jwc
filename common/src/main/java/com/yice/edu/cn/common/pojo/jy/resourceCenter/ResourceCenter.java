package com.yice.edu.cn.common.pojo.jy.resourceCenter;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

import java.util.ArrayList;


@Data
@ApiModel("资源中心信息表")
public class ResourceCenter{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("文件名")
    private String fileName;
    @ApiModelProperty("上传时间")
    private String uploadTime;
    @ApiModelProperty("资源路径")
    private String url;
    @ApiModelProperty("文件集合")
    private ArrayList<ResourceCenter> fileList;
    @ApiModelProperty("来源")
    private String source;
    @ApiModelProperty("发布状态：1、未发布  2、已发布")
    private String releaseStatus;
    @ApiModelProperty("资源名称")
    private String name;
    @ApiModelProperty("主讲人id")
    private String teacherId;
    @ApiModelProperty("上课时间")
    private String classTime;
    @ApiModelProperty("课程简介")
    private String courseIntroduction;
    @ApiModelProperty("课程详细介绍")
    private String courseDetail;
    @ApiModelProperty("课件简介")
    private String coursewareDetail;
    @ApiModelProperty("课件路径")
    private String coursewareUrl;
    @ApiModelProperty("课件名称")
    private String coursewareName;
    @ApiModelProperty("封面路径")
    private String coverUrl;
    @ApiModelProperty("主讲人姓名")
    private String teacherName;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("教师头像")
    private String teacherImgUrl;
    @ApiModelProperty("教师课程")
    private String teacherCourse;
    @ApiModelProperty("学校id")
    private String schoolId;

    @ApiModelProperty("选项集合")
    private ArrayList<ResourceCenterTypeCondition> typeConditionList;


    @ApiModelProperty("条件id集合")
    private ArrayList<String> conditionIdList;

    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
