package com.yice.edu.cn.common.pojo.loc;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;

@Data
@ApiModel("部署版本")
public class LocVersion {

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("版本名称")
    private String versionName;
    @ApiModelProperty("说明")
    private String state;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
     //分页
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    private String searchTimeZone[];
}
