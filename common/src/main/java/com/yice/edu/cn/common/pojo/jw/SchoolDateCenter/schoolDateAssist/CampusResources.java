package com.yice.edu.cn.common.pojo.jw.SchoolDateCenter.schoolDateAssist;

import com.yice.edu.cn.common.pojo.jy.resources.JyResouces;
import com.yice.edu.cn.common.pojo.jy.resources.JySchoolResourceCensus;
import com.yice.edu.cn.common.pojo.jy.resources.JySchoolResourcesByDay;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Api("校内资源")
@Data
public class CampusResources {
    @ApiModelProperty("校内资源总数")
    private Long schoolResourcesNumber;
    @ApiModelProperty("本周上传数量")
    private Long schoolWeekResourcesNumber;
    @ApiModelProperty("资源分布图")
    private List<JySchoolResourcesByDay> jyResouces;
    @ApiModelProperty("纵坐标初始值")
    private Long initNumber;
    @ApiModelProperty("校本资源")
    private List<JySchoolResourceCensus> scholasticResources;
    @ApiModelProperty("生成的资源分布图数据")
    private List<Long> hlafMonth;
    @ApiModelProperty
    private String schoolId;

}
