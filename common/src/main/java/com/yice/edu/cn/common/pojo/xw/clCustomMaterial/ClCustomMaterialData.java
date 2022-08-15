package com.yice.edu.cn.common.pojo.xw.clCustomMaterial;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

import java.util.List;


@Data
@ApiModel("自定义材料数据表")
public class ClCustomMaterialData {

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("修改时间")
    private String updateTime;
    @ApiModelProperty("文件上传路径")
    private String url;
    @ApiModelProperty("文件名称")
    private String name;
    @ApiModelProperty("类别id")
    private String parentId;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("学年id")
    private String schoolYearId;
    /*@ApiModelProperty("学年名称")
    private String fromTo;*/
    @ApiModelProperty("0表示上学期,1下学期")
    private Integer term;
    @ApiModelProperty("上传用户")
    private String userId;
    @ApiModelProperty("上传用户名称")
    private String userName;
    @ApiModelProperty("权重，上限十万，下限0")
    private Long weight;
    @ApiModelProperty("文件内学籍号")
    private List<String> stuCode;
    @ApiModelProperty("文件内学生姓名")
    private List<String> stuName;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("时间段查询")
    private String[] searchTimeZone;
    @ApiModelProperty("1-学生类别,2-教师类别")
    private Integer stuOrTea;
    @ApiModelProperty("1-校务自定义材料，2-学情自定义材料，3-教育评价自定义材料")
    private Integer type;
    @ApiModelProperty("材料上传时间")
    private String materialTime;
    @ApiModelProperty("材料查询数组")
    private String[] materialTimeList;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
