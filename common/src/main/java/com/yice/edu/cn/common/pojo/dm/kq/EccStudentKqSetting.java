package com.yice.edu.cn.common.pojo.dm.kq;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.validateClass.GroupFour;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.common.pojo.validateClass.GroupThree;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@ApiModel("云班牌学生考勤设置")
public class EccStudentKqSetting {

    private String id;

    private String schoolId;

    private String createTime;

    private String createUser;

    @NotNull(message = "考勤时间不可为空",groups = {GroupOne.class})
    @Pattern(message = "时间格式须为HH:mm",regexp = "^([0-1]?[0-9]|2[0-3]):([0-5][0-9])$" ,groups = {GroupOne.class})
    private String kqTime;




    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空",groups = {GroupThree.class, GroupFour.class})
    @Valid
    private Pager pager;

}
