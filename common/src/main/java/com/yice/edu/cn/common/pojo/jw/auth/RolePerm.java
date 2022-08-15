package com.yice.edu.cn.common.pojo.jw.auth;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
/**
*
*教师角色权限
*
*/
@Data
public class RolePerm{

    private String id;
    private String roleId;//role表id
    private String permId;//perm表id
    private String schoolId;//perm表中的school_id
    @ApiModelProperty("创建时间")
    private String createTime;
    //分页排序等
    @Transient
    @NotNull
    private Pager pager;
    private String permIds;
}
