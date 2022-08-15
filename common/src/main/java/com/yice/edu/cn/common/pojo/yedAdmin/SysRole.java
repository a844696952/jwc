package com.yice.edu.cn.common.pojo.yedAdmin;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
*
*系统角色
*
*/
@Data
public class SysRole{

    private String id;
    private String title;//角色名称
    private String createTime;//创建时间
    //分页排序等
    @Transient
    @NotNull
    private Pager pager;
    @Transient
    private List<SysRole> children;
}
