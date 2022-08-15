package com.yice.edu.cn.common.pojo.wb.pentrace;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("智能笔轨迹表")
public class DmPentrace{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("章节id")
    private String sectionId;
    @ApiModelProperty("页编号")
    private String pageId;
    @ApiModelProperty("序列号")
    private String seq;
    @ApiModelProperty("教材id")
    private String bookId;
    @ApiModelProperty("轨迹坐标内容")
    private String list;
    @ApiModelProperty("学生id")
    private String studentid;
    @ApiModelProperty("ownerId")
    private String ownerId;
    @ApiModelProperty("createTime")
    private String createTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
