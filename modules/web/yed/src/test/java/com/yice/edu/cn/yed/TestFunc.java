package com.yice.edu.cn.yed;

import org.junit.Test;

public class TestFunc {
    private String capitalize(String str){
        return str.substring(0,1).toUpperCase()+str.substring(1);
    }
    private String toCamel(String str){
        String[] arr = str.split("_");
        String r=arr[0];
        if(arr.length>1){
            for (int i = 1; i < arr.length; i++) {
                r+=capitalize(arr[i]);
            }
        }
        return r;

    }

    @Test
    public void test(){
        System.out.println(toCamel("c_id"));
    }
}
