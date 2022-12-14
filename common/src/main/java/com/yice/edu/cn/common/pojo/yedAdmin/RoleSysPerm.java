package com.yice.edu.cn.common.pojo.yedAdmin;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
/**
*
*角色权限
*
*/
@Data
public class RoleSysPerm{

    private String id;
    private String roleId;//role表id
    private String permId;//perm表id
    //分页排序等
    @Transient
    @NotNull
    private Pager pager;
}
