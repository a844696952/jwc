package com.yice.edu.cn.common.pojo.dm.schoolActive;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@ApiModel("活动信息表")
public class DmActivityInfo{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("活动ID")
    private String activityId;
    @ApiModelProperty("活动名称")
    private String activityName;
    @ApiModelProperty("活动内容")
    private String activityContent;
    @ApiModelProperty("通知对象 1--教师 2--家长 3--教师和家长")
    private Integer notifyObj;
    @ApiModelProperty("年级ID")
    private String gradeId;
    @ApiModelProperty("1报名 0--未报名")
    private Integer isSignUp;
    @ApiModelProperty("是否自定义0--不是定义 1-自定义")
    private Integer isCustomize;
    @ApiModelProperty("截止时间")
    private String endDate;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("学校ID")
    private String schoolId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    private List<String> gradeIds;
    private String gradeName;
    private String gradeNames;
    private String projectName;
    private String studentName;
    private String classesId;
    private String className;
    private String itemId;
    private String teacherId;
    private String number;
    private String realGradeId;

    @ApiModelProperty(value = "活动项目集合",hidden = true)
    private List<DmActivityItem> dmActivityItems;
    @ApiModelProperty(value = "字典",hidden = true)
    private List<Dd> dds;
    @ApiModelProperty(value = "附件集合",hidden = true)
    private List<DmAttachmentFile> files;

    private Integer status;   //0-报名中，1-报名结束，3-不可报名

}
