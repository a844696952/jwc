package com.yice.edu.cn.common.easypoiplus;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author dengfengfeng
 * easypoi excel加强注解版，目前没用到，因为只加强了一个锁列功能，用LockExcelColumn注解可完成，后期如果还有功能可以往这个里面写
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ExcelPlusA {

    /**
     * 是否锁列
     * @return
     */
    boolean lockColumn() default false;

}
