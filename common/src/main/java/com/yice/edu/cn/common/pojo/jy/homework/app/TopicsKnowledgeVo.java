package com.yice.edu.cn.common.pojo.jy.homework.app;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TopicsKnowledgeVo {
    @ApiModelProperty(value = "题型id [1:判断，2:单选，3:多选]",dataType = "Integer")
    private Integer typeId;
    @ApiModelProperty(value = "知识点（传递id，name就好）",dataType = "JyKnowledge")
    @NotNull
    private KnowledgePoint knowledge;
    @ApiModelProperty(value = "分页参数",dataType = "Pager")
    @NotNull
    private Pager pager;
}
