package com.yice.edu.cn.common.pojo.oa.process;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@ApiModel("给oa用的用户")
public class OaPeople {
    @ApiModelProperty("教师id")
    private String id;
    @ApiModelProperty("教师名称")
    private String name;
    @ApiModelProperty("教师头像路径")
    private String imgUrl;
    @Indexed
    @ApiModelProperty("教师审批状态,0待审批,1审批通过,2审批不同意,3流程已撤销")
    private Integer status;//节点状态，Constant.OA中
    @ApiModelProperty("教师审批不同意时的原因")
    private String reason;//不同意时填写的原因
    @ApiModelProperty("审批时间,精确到秒")
    private String approveTime;
}
