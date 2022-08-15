package com.yice.edu.cn.common.pojo.mes.schoolManage.mesAttachFile;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Data
@ApiModel("附件表")
public class MesAttachFile{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("关联id")
    private String referenceId;
    @ApiModelProperty("文件名称")
    private String fileName;
    @ApiModelProperty("文件路径")
    private String filePath;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("文件类型 1—图片2 --视频")
    private Integer fileType;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
