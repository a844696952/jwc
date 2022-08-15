package com.yice.edu.cn.common.pojo.jw.qusSurvey;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
*
*问卷题目表
*
*/
@Data
public class QusSurveyQuestion{

    @ApiModelProperty(value = "问题id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "问卷id",dataType = "String")
    private String surveyId;
    @ApiModelProperty(value = "题型id：1、单选 2、多选  3、问答题",dataType = "String")
    private String qusTypeId;
    @ApiModelProperty(value = "题目内容",dataType = "String")
    private String qusContent;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "提交人数",dataType = "int")
    private Integer submitPeoNum;
    @ApiModelProperty(value = "选项集")
    private ArrayList<Option> options;
    @ApiModelProperty(value = "已选选项集")
    private List<String> alreadyOptions;
    //分页排序等
    @Transient
    @Valid
    private Pager pager;
}
