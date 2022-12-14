package com.yice.edu.cn.common.pojo.yedAdmin;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
/**
*
*
*
*/
@Data
public class AdminSysRole{

    private String id;
    private String adminId;//关联admin id
    private String roleId;//关联role id
    //分页排序等
    @Transient
    @NotNull
    private Pager pager;
}
