package com.yice.edu.cn.common.pojo.xw.dj.partyMerberPhoto;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.common.pojo.validateClass.GroupTwo;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberFile.XwDjAttachmentFile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@ApiModel("党建党员相册表")
public class XwDjPhoto{

    @ApiModelProperty("主键")
    @NotNull(groups= {GroupTwo.class},message="相册Id不能为空")
    private String id;

    @ApiModelProperty("相册名称")
    @NotNull(groups= {GroupOne.class},message="相册标题不能为空")
    private String photoTitle;

    @ApiModelProperty("创建日期")
    private String createDate;

    @ApiModelProperty("创建日期")
    private String createTime;

    @ApiModelProperty("状态(0未发布/1已发布/2已关闭)")
    @NotNull(groups= {GroupOne.class},message="相册状态不能为空")
    private Integer photoState;

    @ApiModelProperty("创建人ID")
    @NotNull(groups= {GroupOne.class},message="创建人Id不能为空")
    private String creatorId;

    @ApiModelProperty("操作人ID")
    private String operatorId;

    @ApiModelProperty("操作日期")
    private String operateDate;

    @ApiModelProperty("学校ID")
    @NotNull(groups= {GroupOne.class},message="学校Id不能为空")
    private String schoolId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    @ApiModelProperty("操作人名称")
    private String teacherName;

    @ApiModelProperty("附件集合")
    private List<XwDjAttachmentFile> files;


}
