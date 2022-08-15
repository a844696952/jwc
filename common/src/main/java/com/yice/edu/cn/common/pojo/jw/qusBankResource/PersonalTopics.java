package com.yice.edu.cn.common.pojo.jw.qusBankResource;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.answerSheet.TopicPosition;
import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.jy.topics.Topics;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.List;


@Data
@ApiModel("校园个人题库")
public class PersonalTopics extends Topics {

    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "教师id",dataType = "String")
    private String teacherId;
    @ApiModelProperty(value = "创建来源",dataType = "String")
    private String createBy;
    @ApiModelProperty(value = "平台题目原有的id",dataType = "String")
    private String originalId;
}
