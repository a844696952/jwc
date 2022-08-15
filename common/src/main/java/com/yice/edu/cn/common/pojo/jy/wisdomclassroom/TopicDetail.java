package com.yice.edu.cn.common.pojo.jy.wisdomclassroom;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 课堂检测详情
 */
@Document
@Data
public class TopicDetail implements Serializable {

    @ApiModelProperty(value = "主键",dataType = "String")
    @Indexed
    private String id;
    @ApiModelProperty(value = "习题编号",dataType = "Integer")
    private Integer Index;
    @ApiModelProperty(value = "课堂检测id")
    private String classTestId;
    @ApiModelProperty(value = "学生答案列表")
    private List<OptionStatistics> optionStatistics;
    @ApiModelProperty(value = "答对人数",dataType = "Integer")
    private Integer rightCount;
    @ApiModelProperty(value = "答错人数",dataType = "Integer")
    private Integer errorCount;
    @ApiModelProperty(value = "未答题人数",dataType = "Integer")
    private Integer unAnswered;
    @ApiModelProperty(value = "正确答案",dataType = "String")
    private String rightAnswer;
    @ApiModelProperty(value = "提交时间",dataType = "String")
    private String commitTime;

    @ApiModelProperty(value = "易错题标记,0->不是，1->是",dataType = "String")
    private String fallibility;

    @ApiModelProperty(value = "题目内容带标签",dataType = "String")
    private String topicContent;

    @ApiModelProperty(value = "题目内容文本",dataType = "String")
    private String topicContentText;

    @ApiModelProperty(value = "习题解析",dataType = "String")
    private String analysis;

    @ApiModelProperty(value = "习题类型,1 -> 个人题库，2 -> 校本题库",dataType = "Integer")
    private Integer topicType;

    @ApiModelProperty(value = "习题ID",dataType = "String")
    private String topicId;

    @ApiModelProperty(value = "题型id [1:判断，2:单选，3:多选，4:填空题，5:问答题, 6:作文题]",dataType = "int")
    private Integer typeId;

    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;

    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;

    //分页排序
    @Transient
    @NotNull
    private Pager pager;


    //业务字段
    private String beginTime;
    private String endTime;




}
