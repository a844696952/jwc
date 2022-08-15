package com.yice.edu.cn.common.pojo.jw.exam.buildPaper.answerSheet;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@ApiModel("答题卡")
@Document
public class AnswerSheet{

    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "答题卡名称",dataType = "String")
    @NotBlank(message = "答题卡名称不能为空",groups = GroupOne.class)
    @Length(message = "名字最长255字符",max = 255,groups = GroupOne.class)
    private String name;
    @ApiModelProperty(value = "答题卡类型,0自制,1试卷生成",dataType = "Integer")
    @NotBlank(message = "答题卡名称不能为空",groups = GroupOne.class)
    @Range(message = "类型只能0或者1",max = 1,min =0,groups = GroupOne.class)
    private Integer type;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "考号版本,0表示8位考号填涂",dataType = "Integer")
    @NotBlank(message = "考号版本不能为空",groups = GroupOne.class)
    @Range(message = "考号版本只能0或者1",max = 1,min =0,groups = GroupOne.class)
    private Integer version;
    @ApiModelProperty(value = "纸张样式,0:A3",dataType = "Integer")
    @NotBlank(message = "纸张样式不能为空",groups = GroupOne.class)
    @Range(message = "纸张样式只能0或者1",max = 1,min =0,groups = GroupOne.class)
    private Integer paperType;
    @ApiModelProperty(value = "试卷id",dataType = "String")
//    @Indexed(name="answerSheet.paperId",unique = true)
    private String paperId;
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
    @ApiModelProperty("创建者id")
    private String createUserId;
    //开始和构建答题卡相关的字段
    @ApiModelProperty("前端构建答题卡是用到的存于store的数据")
    private String answerSheetStore;
    @ApiModelProperty(value = "客观题数量",dataType = "Integer")
    @NotNull(message = "客观题数量必传",groups = GroupOne.class)
    private Integer objectiveNum;
    @ApiModelProperty(value = "主观题数量",dataType = "Integer")
    @NotNull(message = "主观题数量必传",groups = GroupOne.class)
    private Integer subjectiveNum;
    @ApiModelProperty(value = "答题卡总分",dataType = "Integer")
    @NotNull(message = "答题卡总分必传",groups = GroupOne.class)
    private Integer totalScore;
    @ApiModelProperty("答题卡数据列表")
    @NotEmpty(message = "答题卡数据列表必传",groups = GroupOne.class)
    private List<AnswerSheetData> answerSheetDatas;
    @ApiModelProperty("答题卡共多少页")
    @NotNull(message = "答题卡共多少页必传",groups = GroupOne.class)
    private Integer totalPage;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;



}
