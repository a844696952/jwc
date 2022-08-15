package com.yice.edu.cn.common.pojo.dm.kqDevice;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

import java.util.List;


@Data
@ApiModel("考勤设备分组类型")
public class KqDeviceGroup{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("分组名称")
    private String groupName;
    @ApiModelProperty("分组类型 0 出入校门禁 1普通 2 教师考勤 ")//{ id: "0", name: "出入校" }, { id: "1", name: "普通" }, { id: "2", name: "教师考勤" }, { id: "3", name: "宿舍楼" }
    private List<String> groupType;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
