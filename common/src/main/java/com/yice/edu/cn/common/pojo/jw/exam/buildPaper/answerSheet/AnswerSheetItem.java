package com.yice.edu.cn.common.pojo.jw.exam.buildPaper.answerSheet;

import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.validateClass.GroupSix;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "答题卡数据项,等同于小题",description = "客户端上传数据时,需要传typeName,typeId,num,score字段,客观题还需要optionNum,answer,yourAnswer,right,yourScore,当考试含有试卷id时,还需要topicId,topicSource,")
public class AnswerSheetItem implements Serializable {
    private static final long serialVersionUID = 5198151952343839922L;

    @ApiModelProperty(value = "题目的id",dataType = "String")
    private String topicId;
    @ApiModelProperty(value = "题目的来源，0运营平台题库,1校本题库,2个人题库",dataType = "Integer")
    private Integer topicSource;
    @ApiModelProperty(value = "题型名称",dataType = "String")
    @NotBlank(message = "题型名称不能为空",groups = GroupSix.class)
    private String typeName;
    @ApiModelProperty(value = "题型id",dataType = "String")
    @NotNull(message = "题型id不能为空",groups = GroupSix.class)
    private Integer typeId;
    @ApiModelProperty(value = "题目序号",dataType = "String")
    @NotNull(message = "题型id不能为空",groups = GroupSix.class)
    private Integer num;
    @ApiModelProperty(value = "选项个数",dataType = "String")
    private Integer optionNum;
    @ApiModelProperty(value = "题目答案,数组类型,只有完形填空和填空题时有多个选项,多选答案如:'ABC'",dataType = "Array")
    private String[] answer;
    @ApiModelProperty(value = "题目分数",dataType = "Double")
    @NotNull(message = "题型id不能为空",groups = GroupSix.class)
    private Double score;
    @ApiModelProperty("小题坐标集合")
    private TopicPosition[] topicPositions;
    @ApiModelProperty("学生的答案,该findMyAnswerSheetByName字段阅卷时用到")
    private String yourAnswer;
    @ApiModelProperty("学生的答案是否正确")
    private Boolean right;
    @ApiModelProperty("学生的得分,该字段阅卷时用到")
    private Double yourScore;
    @ApiModelProperty("知识点列表")
    private List<KnowledgePoint> knowledges;

    @ApiModelProperty("小题批注")
    private String postil;

    @ApiModelProperty("学生id->app端使用")
    private String stuScoreId;
    @ApiModelProperty("app端图片")
    private String img;
}
