package com.yice.edu.cn.common.pojo.jw.schoolCompusCenter;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

import java.util.Map;


@Data
@ApiModel("yed学校数据中心")
public class SchoolCompusCenter{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("学校Id")
    private String schoolId;

    private String data;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
