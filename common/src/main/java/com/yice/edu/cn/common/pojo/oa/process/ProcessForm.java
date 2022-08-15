package com.yice.edu.cn.common.pojo.oa.process;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.general.node.Node;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
*
*流程表单
*
*/
@Data
public class ProcessForm{

    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "流程库id,前端可以忽略",dataType = "String")
    private String processLibId;
    @ApiModelProperty(value = "名称,提交表单时用到的名称",dataType = "String")
    private String name;
    @ApiModelProperty(value = "中文名,显示给用户看",dataType = "String")
    private String label;
    @ApiModelProperty(value = "默认值,控件默认值",dataType = "String")
    private Object defaultValue;
    @ApiModelProperty(value = "值长度,用于校验控件的值长度",dataType = "Integer")
    private Integer len;
    @ApiModelProperty(value = "正则表达式验证,用于校验控件",dataType = "String")
    private String regexp;
    @ApiModelProperty(value = "正则表达式验证没通过的提示",dataType = "String")
    private String regexpTip;
    @ApiModelProperty(value = "是否必填",dataType = "Boolean")
    private Boolean required;
    @ApiModelProperty(value = "控件类型,目前有text,textarea,select,datetime,upload",dataType = "String")
    private String formType;
    @ApiModelProperty(value = "上传地址",dataType = "String")
    private String uploadUrl;
    @ApiModelProperty(value = "上传文件大小,多少个字节",dataType = "Integer")
    private Integer uploadSize;
    @ApiModelProperty(value = "上传文件格式",dataType = "String[]")
    private String[] uploadExt;
    @ApiModelProperty(value = "提示",dataType = "String")
    private String tip;
    @ApiModelProperty(value = "时间格式,形如:yyyy-MM-dd HH:mm:ss",dataType = "String")
    private String timeFormat;
    @ApiModelProperty(value = "是否只读",dataType = "Boolean")
    private Boolean readonly;
    @ApiModelProperty(value = "远程数据源,是个url,返回格式同localDatasource",dataType = "String")
    private String remoteDatasource;
    @ApiModelProperty(value = "本地数据源",dataType = "String")
    private List<Node> localDatasource;
    @ApiModelProperty(value = "占位提示",dataType = "String")
    private String placeholder;
    @ApiModelProperty(value = "开始占位提示",dataType = "String")
    private String startPlaceholder;
    @ApiModelProperty(value = "结束占位提示",dataType = "String")
    private String endPlaceholder;

    @ApiModelProperty(value = "数据类型,目前有String,Integer,Double,Array",dataType = "String")
    private String dataType;
    @ApiModelProperty("文件上传时限制的个数")
    private Integer limit;
    @ApiModelProperty("图片上传款的宽度")
    private Integer boxWidth;
    @ApiModelProperty("分割线的线样式,solid或者dashed")
    private String style;
    @ApiModelProperty("数字的最小值")
    private Double min;
    @ApiModelProperty("数字的最大值")
    private Double max;
    @ApiModelProperty("文本框会用到的单位")
    private String unit;


}
