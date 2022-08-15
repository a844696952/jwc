package com.yice.edu.cn.common.pojo.xw.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("CMS首页布局表")
public class XwCmsHomeLayout {
    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("栏目id")
    private String columnId;
    @ApiModelProperty("学校id")
    private String schoolId;
    //业务字段
    @ApiModelProperty("栏目名称")
    private String columnName;
    @ApiModelProperty("栏目类型")
    private String columnType;
    @ApiModelProperty("栏目位置 位置1 位置2 位置3")
    private Integer position;
    @ApiModelProperty("文件路径")
    private String filePath;
    @ApiModelProperty("CMS内容链接")
    private String url;
    @ApiModelProperty("CMS内容id")
    private String contentId;
    @ApiModelProperty("排序字段")
    private Integer sortNumber;
    @ApiModelProperty("通栏是否显示 0 -> 不显示 1 -> 显示")
    private Integer isShow;
    @ApiModelProperty("创建日期")
    private String createTime;
}
