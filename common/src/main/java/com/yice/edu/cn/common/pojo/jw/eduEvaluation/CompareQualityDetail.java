package com.yice.edu.cn.common.pojo.jw.eduEvaluation;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.schoolYear.SchoolYear;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;


@Data
@ApiModel("综合素质表详情")
public class CompareQualityDetail implements Serializable {
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("综合素质列表id")
    private String compareQualityId;
    @ApiModelProperty("学生姓名")
    @Excel(name = "姓名")
    private String stuName;

    @ApiModelProperty("年级名称")
    @Excel(name = "年级")
    private String gradeName;

    @ApiModelProperty("班号")
    @Excel(name = "班级")
    private String classNum;

    @ApiModelProperty("班级id")
    private String classId;

    @ApiModelProperty("学号")
    @Excel(name = "学号")
    private String stuNum;

    @ApiModelProperty("思想品德分数")
    @Excel(name = "思想品德")
    private String moralEducationScore;

    @ApiModelProperty("学业水平分数")
    @Excel(name = "学业水平")
    private String academicLevelScore;

    @ApiModelProperty("身心健康分数")
    @Excel(name = "身心健康")
    private String physicalHealthScore;

    @ApiModelProperty("艺术素养分数")
    @Excel(name = "艺术素养")
    private String artisticAccomplishmentScore;

    @ApiModelProperty("社会实践分数")
    @Excel(name = "社会实践")
    private String socialPracticeScore;

    @ApiModelProperty("总分")
    @Excel(name = "总分")
    private String totalScore;


    //分页排序等
    @Transient
    @Valid
    private Pager pager;

    @Transient
    @ApiModelProperty("年级名称集合")
    private List<String> gradeNameArray;
    @Transient
    private String uploadGradeName;
    @ApiModelProperty("档案名称")
    private String compareQualityName;
    @ApiModelProperty("学年")
    private SchoolYear schoolYear;
    @ApiModelProperty("学期")
    private String term;
    private CompareQuality compareQuality;


    @ApiModelProperty("schoolId")
    private String schoolId;
    @ApiModelProperty("发布时间")
    private String createTime;
    @Transient
    private List<CompareQuality>checkIdList;
}
