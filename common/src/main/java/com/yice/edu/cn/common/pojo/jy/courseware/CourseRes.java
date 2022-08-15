package com.yice.edu.cn.common.pojo.jy.courseware;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.validateClass.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.util.List;

/**
 * <p>GroupOne.class 移动需要用到</p>
 * <p>GroupTwo.class 重命名</p>
 * <p>GroupThreee.class 新增验证</p>
 * <p>GroupFour.class 修改验证</p>
 */

@Data
@ApiModel("课件资源表")
public class CourseRes{

    @ApiModelProperty("主键")
    @NotBlank(message = "主键id不可为空",groups = {GroupOne.class, GroupTwo.class, GroupFour.class})
    private String id;
    @ApiModelProperty("资源名称")
    @NotBlank(message = "资源名称不可为空",groups = {GroupTwo.class, GroupThree.class,GroupFive.class})
    private String name;
    @NotBlank(message = "1级目录不可为空",groups = {GroupOne.class,GroupThree.class,GroupFive.class,GroupSix.class})
    @ApiModelProperty("1级目录")
    private String lv1;
    @ApiModelProperty("2级目录")
    private String lv2;
    @ApiModelProperty("3级目录")
    private String lv3;
    @ApiModelProperty("4级目录")
    private String lv4;
    @ApiModelProperty("资源类型")
    @NotNull(message = "资源类型不可为空",groups = {GroupThree.class,GroupFive.class})
    private ResType resType;
    @ApiModelProperty("文件类型")
    private FileType fileType;
    @ApiModelProperty("资源地址")
    @NotBlank(message = "资源地址不可为空",groups = {GroupThree.class,GroupFive.class})
    private String resUrl;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("教师id")
    private String teacherId;
    @ApiModelProperty("资源大小")
    @NotNull(message = "资源大小不可为空",groups = {GroupThree.class,GroupFive.class})
    private Integer resSize;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("教材名称")
    @NotBlank(message = "教材名称不可为空",groups = {GroupThree.class,GroupFive.class})
    private String textbook;
    @ApiModelProperty("目录标题")
    @NotBlank(message = "目录标题不可为空",groups = {GroupThree.class,GroupFive.class})
    private String title;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空",groups = {GroupSeven.class})
    @ConvertGroup(from = GroupSeven.class,to = Default.class)
    @Valid
    private Pager pager;

    @ApiModelProperty("教材id")
    @NotBlank(message = "教材id不可为空",groups = {GroupOne.class,GroupThree.class,GroupFive.class,GroupSix.class,GroupSeven.class})
    private String subjectMateriaId;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("音视频时长")
    private Integer duration;
    @ApiModelProperty("是否未分类")
    private Boolean unCategorized;


    @Transient
    @NotEmpty(message = "要移动的资源不可为空",groups = {GroupSix.class})
    private List<String> ids;


    @Transient
    @NotEmpty(message = "课堂检测答案不可为空",groups = {GroupFive.class})
    @Valid
    private List<CourseTestAnswer> courseTestAnswers;

    public boolean notMatchType(){
        return !resType.matches(resUrl.substring(resUrl.lastIndexOf(".") + 1));
    }


}
