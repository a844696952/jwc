package com.yice.edu.cn.common.pojo.dm.dmMobileRedBanner;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

import java.util.List;


@Data
@ApiModel("流动红旗表")
public class DmMobileRedBanner{

    @ApiModelProperty("流动红旗编号")
    private String id;
    @ApiModelProperty("学校编号")
    private String schoolId;
    @ApiModelProperty("班级编号(多个)")
    private String classIds;
    @ApiModelProperty("班级名称(多个)")
    private String classNames;
    @ApiModelProperty("荣誉名称")
    private String honourName;
    @ApiModelProperty("获奖时间")
    private String honourTime;
    @ApiModelProperty("颁奖时间")
    private String awardTime;
    @ApiModelProperty("状态，是否展示。1：展示中，2：已结束")
    private Integer status;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("修改时间")
    private String updateTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    @Transient
    private String[] classList;

    /**
     * 查询回显时需要用到年级id
     */
    private List<GradeAndClazz> gradeAndClazzList;
    @Data
    public static class GradeAndClazz{
        private String gradeId;
        private String classId;
    }
}
