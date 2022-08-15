package com.yice.edu.cn.common.pojo.xw.pcdData;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@ApiModel("教师数据")
public class PcdTeacherData implements Serializable {

    private static final long serialVersionUID = -9039172250493382307L;
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
     //分页
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    @ApiModelProperty("json数据")
    private Map<String,Object> data;
    @ApiModelProperty("小初高教师总数量")
    private Map<String,Long> count;
}
