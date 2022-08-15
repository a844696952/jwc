package com.yice.edu.cn.common.pojo.dm.ycVeriface.personEnter;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

import java.util.List;


@Data
@ApiModel("人员图片校验结果")
public class YcVerifacePersonEnter{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("人员id")
    private String personId;
    @ApiModelProperty("人员类型")
    private String personType;
    @ApiModelProperty("校验状态")
    private String checkStatus;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("更新时间")
    private String updateTime;
    @ApiModelProperty("创建时间")
    private String createTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    /*-------------------------------*/
    @ApiModelProperty("人员id列表")
    private List<String> personIdList;
    @Transient
    private String state;//0成功1失败
}
