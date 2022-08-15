package com.yice.edu.cn.common.pojo.jy.sensitiveWord;

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
public class SensitiveWord{

    private String id;
    private String name;//词汇
    private String type;//分类
    private String createTime;//创建时间
    private String updateTime;//修改时间
    //分页排序等
    @Transient
    @NotNull
    private Pager pager;
}
