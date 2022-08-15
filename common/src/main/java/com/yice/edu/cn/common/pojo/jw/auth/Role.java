package com.yice.edu.cn.common.pojo.jw.auth;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
/**
*
*学校角色
*
*/
@Data
public class Role{

    private String id;
    private String title;//角色名称
    private String createTime;//创建时间
    private String schoolId;//学校id
    //分页排序等
    @Transient
    @NotNull
    private Pager pager;
}
