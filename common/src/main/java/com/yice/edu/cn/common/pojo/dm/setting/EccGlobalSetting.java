package com.yice.edu.cn.common.pojo.dm.setting;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.validateClass.GroupFour;
import com.yice.edu.cn.common.pojo.validateClass.GroupThree;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("云班牌学生全局设置")
public class EccGlobalSetting<T> {
    private String id;
    private String schoolId;
    private String key;
    private T value;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空",groups = {GroupThree.class, GroupFour.class})
    @Valid
    private Pager pager;
}
