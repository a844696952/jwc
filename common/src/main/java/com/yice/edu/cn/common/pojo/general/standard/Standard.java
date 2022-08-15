package com.yice.edu.cn.common.pojo.general.standard;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class Standard {
    private String id;
    private String name;
    private Integer age;
    private Double area;
    private String descr;
    private Boolean top;
    private String createTime;
    //分页排序等
    @Transient
    @NotNull
    @Valid
    private Pager pager;

}
