package com.yice.edu.cn.common.pojo.wb.latticePager;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;

import java.util.List;

@Data
@ApiModel("点阵试卷背景表")
public class DmPagerBackground {

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("图片名称")
    private String name;
    @ApiModelProperty("点阵页码")
    private Integer pagerNumber;
    @ApiModelProperty("添加时间")
    private String createTime;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("点阵试卷id")
    private String latticeId;
    @ApiModelProperty("上传的图片路径")
    private String imagePath;
    @ApiModelProperty("试卷页码")
    private Integer latticeNumber;

     //分页
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    private String[] numberArrays;
}
