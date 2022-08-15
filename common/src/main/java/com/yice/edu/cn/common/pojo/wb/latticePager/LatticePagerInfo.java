package com.yice.edu.cn.common.pojo.wb.latticePager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
@ApiModel("点阵试卷详情")
public class LatticePagerInfo {

    @Id
    @Indexed
    @ApiModelProperty(value = "主键",dataType = "String")
    private String id;

    @ApiModelProperty(value = "试卷id",dataType = "String")
    private String pagerId;

    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;

    @ApiModelProperty(value = "修改时间",dataType = "String")
    private String modifyTime;

    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;

    @ApiModelProperty(value = "题号",dataType = "int")
    private Integer questionNumber;

    @ApiModelProperty(value = "题型id [1:判断，2:单选，3:多选，4:填空题，5:问答题, 6:作文题]",dataType = "int")
    private Integer type;

    @ApiModelProperty(value = "小题字段小题号",dataType = "int")
    private Integer subclassNumber;

    @ApiModelProperty(value = "答案",dataType = "String")
    private String answer;

    @ApiModelProperty(value = "矩形的坐标x轴左上顶点",dataType = "String")
    private String x1;

    @ApiModelProperty(value = "矩形的坐标y轴左上顶点",dataType = "String")
    private String y1;

    @ApiModelProperty(value = "矩形的坐标x轴右下底点",dataType = "String")
    private String x2;

    @ApiModelProperty(value = "矩形的坐标y轴右下底点",dataType = "String")
    private String y2;

    @ApiModelProperty(value = "pdf解析出来的试卷页码",dataType = "int")
    private Integer pageNumber;

    /**
     *
     * 非数据库字段，接收多选题参数
     */
    private List<String> answers;

}
