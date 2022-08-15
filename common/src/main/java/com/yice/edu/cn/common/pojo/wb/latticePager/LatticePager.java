package com.yice.edu.cn.common.pojo.wb.latticePager;


import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
@ApiModel("点阵试卷表")
public class LatticePager extends CurSchoolYear {

    @Id
    @Indexed
    @ApiModelProperty(value = "主键",dataType = "String")
    private String id;


    @ApiModelProperty(value = "试卷名称",dataType = "String")
    private String name;

    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;

    @ApiModelProperty(value = "修改时间",dataType = "String")
    private String modifyTime;

    @ApiModelProperty(value = "再次启用试卷的地址",dataType = "String")
    private String pagerPath;

    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;

    @ApiModelProperty(value = "教师id",dataType = "String")
    private String teacherId;

    @ApiModelProperty(value = "试卷每页的地址",dataType = "Object")
    private List<String> pagers;



    //额外字段
    @Transient
    @ApiModelProperty(value = "时间段数组，Pc端用来查询时用",dataType = "String[]")
    //时间段数组
    private String[] searchTimeZone;


    private List<LatticePagerInfo> latticePagerInfos;


    private List<DmPagerBackground> dmPagerBackgrounds;

}
