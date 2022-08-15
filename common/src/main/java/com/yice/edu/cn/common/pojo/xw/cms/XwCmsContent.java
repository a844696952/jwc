package com.yice.edu.cn.common.pojo.xw.cms;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@ApiModel("内容信息表")
public class XwCmsContent{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("外键")
    private String columnId;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("链接")
    private String url;
    @ApiModelProperty("内容")
    private String content;
    @ApiModelProperty("封面")
    private String fontCoverUrl;
    @ApiModelProperty("权重")
    private Integer weights;
    @ApiModelProperty("修改时间")
    private String updateDate;
    @ApiModelProperty("排序编号")
    private Integer sortNumber;
    @ApiModelProperty("类型 1--通栏")
    private Integer type;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("文件名称")
    private String fileName;
    @ApiModelProperty("通栏是否显示 0 -> 不显示 1 -> 显示")
    private Integer isShow;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;


    private List<XwCmsContentFile> files;
}
