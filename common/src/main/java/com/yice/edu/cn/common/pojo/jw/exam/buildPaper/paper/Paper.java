package com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.structure.*;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
*
*组卷
*
*/
@Document
@Data
public class Paper implements Serializable {

    private static final long serialVersionUID = -207745919958496623L;
    @ApiModelProperty(value = "主键Id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "修改时间",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "年段id",dataType = "String")
    private String annualPeriodId;
    @ApiModelProperty(value = "年段名称",dataType = "String")
    private String annualPeriodName;
    @ApiModelProperty(value = "科目id",dataType = "String")
    private String subjectId;
    @ApiModelProperty(value = "科目名称",dataType = "String")
    private String subjectName;
    @ApiModelProperty(value = "创建id",dataType = "String")
    private String createUserId;
    @ApiModelProperty(value = "创建人名称",dataType = "String")
    private String createUserName;
  /*  @ApiModelProperty(value = "分数值",dataType = "Integer")
    private Integer scoreNumber;*/
    @ApiModelProperty(value = "考生输入栏",dataType = "Object")
    private ExamineeInfo examineeInfo;//考生输入栏
    @ApiModelProperty(value = "装订线",dataType = "Object")
    private Gutter gutter;//装订线
    @ApiModelProperty(value = "主标题",dataType = "Object")
    private MainTitle mainTitle;//主标题
    @ApiModelProperty(value = "试卷信息栏",dataType = "Object")
    private PaperInfo paperInfo;//试卷信息栏
    @ApiModelProperty(value = "注意事项栏",dataType = "Object")
    private Precaution precaution;//注意事项栏
    @ApiModelProperty(value = "誉分栏",dataType = "Object")
    private ScoreBox scoreBox;//誉分栏
    @ApiModelProperty(value = "保密协议",dataType = "Object")
    private SecretMark secretMark;//保密协议
    @ApiModelProperty(value = "副标题",dataType = "Object")
    private SubTitle subTitle;//副标题
    @ApiModelProperty(value = "卷表卷注",dataType = "Object")
    private List<VolumeOne> volumes;//卷表卷注
    @ApiModelProperty(value = "学校Id",dataType = "String")
    private String schoolId;

    @ApiModelProperty(value = "多道大题",dataType = "Object")
    private List<PaperQuestion> subject;//多道大题
    @ApiModelProperty(value = "记录是否是当前试题篮的题目，1为当前，2为已保存的试卷",dataType = "Integer")
    private Integer currentType;  //当前记录  1为当前  2为不是当前
    @ApiModelProperty(value = "学生版试卷的坐标",dataType = "String")
    private String paperFormatData;
    @ApiModelProperty(value = "老师版试卷坐标",dataType = "String")
    private String paperFormatDataTe;
    @ApiModelProperty(value = "被X场考试所引用的数量")
    private Integer numbers;


    /*@ApiModelProperty(value = "用来匹配模糊查询的字段",dataType = "String")
    private String mainTitleLike;*/
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;


    @ApiModelProperty(value = "用来做时间段筛选的字段",dataType = "String[]")
    private String[] searchTimeZone;

    @ApiModelProperty(value = "用来区分当前用户在试题篮是否存在试卷,1为存在，2为不存在",dataType = "Integer")
    private Integer paperTypeId;

    @ApiModelProperty(value = "克隆试卷Id",dataType = "String")
    private String clonePaperId;
    @ApiModelProperty(value = "答题卡名称")
    private String answerSheetName;
    @ApiModelProperty(value = "0表示手动填涂，1表示条形码填涂")
    private Integer version;
    @ApiModelProperty(value = "答题卡id")
    private String answerSheetId;

}
