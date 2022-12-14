package com.yice.edu.cn.common.util.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ClassScheduleUtil {
    /**
     * 获取属性名数组
     * */
    public static String[] getFiledName(Object o){
        Field[] fields=o.getClass().getDeclaredFields();
        String[] fieldNames=new String[fields.length];
        for(int i=0;i<fields.length;i++){
            fieldNames[i]=fields[i].getName();
        }
        return fieldNames;
    }

    /**
     * 根据属性名获取属性值
     */
    public static Object getFieldValueByName(String fieldName,Object o ){
        try {
            String firstLetter = fieldName.substring(0,1).toUpperCase();
            String getter = "get"+firstLetter+fieldName.substring(1);
            Method method = o.getClass().getMethod(getter,new Class[]{});
            Object value = method.invoke(o,new Object[]{});
            return value;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
