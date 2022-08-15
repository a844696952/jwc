package com.yice.edu.cn.common.pojo.dm.ycVeriface.alarmReceiver;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

import java.util.List;


@Data
@ApiModel("人脸识别接收人员")
public class YcVerifaceAlarmReceiver{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("人员id")
    private String personId;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("部门id")
    private String[] departmentId;
    @ApiModelProperty("部门名称")
    private List<String> departmentName;
    @ApiModelProperty("学校id")
    private String schoolId;
    @Transient
    private List<YcVerifaceAlarmReceiver> list;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    private Pager pager;
}
