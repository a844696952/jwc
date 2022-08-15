package com.yice.edu.cn.common.pojo.jw.SchoolDateCenter;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.SchoolDateCenter.schoolDateAssist.*;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.building.SpaceOfType;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.xw.zc.assetsFile.AssetsFile;
import com.yice.edu.cn.common.pojo.xw.zc.assetsStockDetail.AssetsStockDetail;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


@Api("Osp学校数据中心")
@Data
@Document
public class SchoolDateCenter {
    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("学校基础数据")
    private School school;
    @ApiModelProperty("学生总数")
    private StudentSum studentSum;
    @ApiModelProperty("教师总数")
    private TeacherSum teacherSum;
    @ApiModelProperty("校内资源")
    private CampusResources campusResources;
    @ApiModelProperty("教师考勤")
    private TeacherAttendance teacherAttendance;
    @ApiModelProperty("资产使用率")
    private Fixation assetsStockDetail;
    @ApiModelProperty("班级")
    private List<JwClasses> classesList;
    @ApiModelProperty("教师考勤今日打卡率")
    private Double teacherTheClockRate;
    @ApiModelProperty("基础建设")
    private List<SpaceOfType> jwSpaceList;
    @ApiModelProperty("返回前端的固定资产、易耗品")
    private List<List<AssetsStockDetail>> assetsStockDetailList;
    @ApiModelProperty("库存价格")
    private List<List<AssetsFile>> assetsFiles;
    @ApiModelProperty("学生考勤")
    private StudentThisKq studentThisKq;
    @ApiModelProperty("假数据的固定资产对象")
    private Fixation fixation;
    @ApiModelProperty("假数据的易耗品资产对象")
    private Fixation consumable;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
