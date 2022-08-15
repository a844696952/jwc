package com.yice.edu.cn.common.pojo.xw.pcdData;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.xw.pcdData.son.StudentWorkAverAge;
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
@ApiModel("教学数据")
public class PcdTeachingData implements Serializable {

    private static final long serialVersionUID = -3460966046344385757L;
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
    @ApiModelProperty("小初高全区教学资源情况")
    private Map<String,Long> count;
    @ApiModelProperty("小初高全区教学资源比例")
    private Map<String,List<String>> ratioMap;
    @ApiModelProperty("小初高教学资源学校总数量")
    private Map<String,Long> schoolCountMap;
    @ApiModelProperty("小学平均做题情况")
    private Map<String,Map<String,StudentWorkAverAge>> studentWorkMap;

}
