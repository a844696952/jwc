package com.yice.edu.cn.common.pojo.xw.dormManage.dorm;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("宿舍申请附件表")
public class HouseApplicanFiles{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("schoolId")
    private String schoolId;
    @ApiModelProperty("createTime")
    private String createTime;
    @ApiModelProperty("申请表id")
    private String houseApplicanId;
    @ApiModelProperty("附件url")
    private String fileUrl;
    @ApiModelProperty("附件名")
    private String fileName;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
