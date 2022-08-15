package com.yice.edu.cn.common.pojo.jy.questionItem;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

/**
 * 题目类型-复合组件 比如单选题这个类型的id只有一个，但是他可以给多个科目公用，因为抓取21世纪的，又没法抓取他总的题目类型建立公用字典数据，目前就只能这样复合组件了
 */

@Data
@ApiModel("题目类型")
public class QuestionItem{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("学段id")
    private String schoolSectionId;
    @ApiModelProperty("学段")
    private String schoolSection;
    @ApiModelProperty("科目id")
    private String subjectId;
    @ApiModelProperty("科目")
    private String subjectName;
    @ApiModelProperty("创建时间")
    private String createTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
