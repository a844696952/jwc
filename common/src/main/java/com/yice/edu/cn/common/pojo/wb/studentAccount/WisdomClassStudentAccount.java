package com.yice.edu.cn.common.pojo.wb.studentAccount;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel("智慧课堂笔盒绑定学生账号")
public class WisdomClassStudentAccount {
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("学校id")
    private String schoolId;

    @Excel(name = "学生姓名")
    @ApiModelProperty("学生姓名")
    private String name;

    @ApiModelProperty("年级id")
    private String gradeId;

    @ApiModelProperty("年级")
    private String gradeName;

    @ApiModelProperty("班级id")
    private String classId;

    @ApiModelProperty("班级")
    private String className;

    @Excel(name = "班级")
    @ApiModelProperty("班级名称")
    private String classesName;

    @ApiModelProperty("学生id")
    private String studentId;

    @ApiModelProperty("学生账号")
    private String padAccount;

    @ApiModelProperty("学生密码")
    private String padPassword;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("修改时间")
    private String updateTime;

    @ApiModelProperty("修改时间1")
    private String updateTime1;

    @ApiModelProperty("修改时间2")
    private String updateTime2;

    @Excel(name = "imei")
    @ApiModelProperty("imei")
    private String imei;

    @Excel(name = "标签",replace = {"_null","一般_1","中等_2","良好_3","优秀_4"})
    @ApiModelProperty("标签，1-一般，2-中等，3-良好，4-优秀")
    private Integer studentType;

    @Excel(name = "厂家",replace = {"腾千里_1","拓思德_2"})
    @ApiModelProperty("厂家 1 -> 腾千里; 2 -> 拓思德")
    private Integer penFactory;


    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    @Transient
    private List<String> ids;
}
