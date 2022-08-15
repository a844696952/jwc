package com.yice.edu.cn.common.pojo.xw.pcdData;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.xw.pcdData.son.EduInputSchool;
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
@ApiModel("教育建设数据")
public class PcdEducationData implements Serializable {

    private static final long serialVersionUID = -5842392629916493506L;
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
    @ApiModelProperty("投入总金额")
    private Double count;
    @ApiModelProperty("小学、初中、高中、投入比列")
    private Map<String,List<String>> porp;
    @ApiModelProperty("学校涨幅")
    private Map<String,List<EduInputSchool>> schoolMap;

}
