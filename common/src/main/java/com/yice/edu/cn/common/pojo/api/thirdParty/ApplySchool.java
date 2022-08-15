package com.yice.edu.cn.common.pojo.api.thirdParty;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;

@Data
@ApiModel("绑定到学校的第三方应用")
public class ApplySchool {

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("应用名称")
    private String applyName;
    @ApiModelProperty("应用链接")
    private String applyUrl;
    @ApiModelProperty("应用图标")
    private String iconUrl;
    @ApiModelProperty("应用生成ID")
    private String applyId;
    @ApiModelProperty("有效期结束时间")
    private String expireTime;
    @ApiModelProperty("学校Id")
    private String schoolId;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("排序")
    private Integer sort;
     //分页
    @Transient
    @Valid
    private Pager pager;
}
