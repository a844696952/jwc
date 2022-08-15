package com.yice.edu.cn.common.pojo.jw.SchoolDateCenter.schoolDateAssist;

import com.yice.edu.cn.common.pojo.xw.zc.assetsFile.AssetsFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Api("固定资产、易耗品类")
@Data
public class Fixation {
    @ApiModelProperty("月纵坐标")
    private Long monthOrdinate;
    @ApiModelProperty("年纵坐标")
    private Long yearOrdinate;
    @ApiModelProperty("资产使用率")
    private String zcsyl;
    @ApiModelProperty("本周上传数量")
    private Long thisWeekUpTotal;
    @ApiModelProperty("上周上传数量")
    private Long upWeekUpTotal;
    @ApiModelProperty("周同比率")
    private String weekComparedToTheSame;
    @ApiModelProperty("告诉前端是上升还是下降,true为上升，false为下降")
    private Boolean flag;
    @ApiModelProperty("占用资产数量")
    private Long occupyTotal;
    @ApiModelProperty("总的资产数量")
    private Long assetsTotal;

    @ApiModelProperty("年的数据")
    private List<Long> yearTotal;
    @ApiModelProperty("月的数据")
    private List<Long> monthTotal;
    @ApiModelProperty("库存价格")
    private List<AssetsFile> assetsFiles;
}
