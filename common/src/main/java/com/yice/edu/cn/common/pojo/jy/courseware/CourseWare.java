package com.yice.edu.cn.common.pojo.jy.courseware;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
@ApiModel("课件表")
public class CourseWare{

    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty(value = "学校id",hidden = true)
    private String schoolId;
    @ApiModelProperty(value = "教师id",hidden = true)
    private String teacherId;
    @ApiModelProperty("资源Id")
    private String resoucesId;
    @ApiModelProperty("课件名称")
    private String coursewareName;
    @ApiModelProperty(value = "创建时间",hidden = true)
    private String createTime;
    @ApiModelProperty(value="更新时间",hidden = true)
    private String updateTime;
    @ApiModelProperty("课件ppt地址")
    private String coursewareUrl;
    @ApiModelProperty("课件大小")
    private String coursewareSize;
    @ApiModelProperty("节点1")
    private String lv1;
    @ApiModelProperty("节点2")
    private String lv2;
    @ApiModelProperty("节点3")
    private String lv3;
    @ApiModelProperty("节点4")
    private String lv4;
    @ApiModelProperty("教材名称")
    private String textbook;
    @ApiModelProperty("目录标题")
    private String title;
    @ApiModelProperty("教材id")
    private String subjectMateriaId;
    @ApiModelProperty("remark")
    private String remark;
    @ApiModelProperty("是否未分类")
    private Boolean unCategorized;

    //分页排序等
    @ApiModelProperty(value="分页对象")
    private Pager pager;
    @ApiModelProperty(value="课件id集合" ,hidden = true)
    private List<String> ids;
}
