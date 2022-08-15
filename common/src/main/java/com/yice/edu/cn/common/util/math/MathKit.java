package com.yice.edu.cn.common.util.math;

import java.text.DecimalFormat;

public class MathKit {
    /**
     * 获取百分比
     * @param x
     * @param total
     * @return
     */
    public static  String getPercent(Long x, Long total) {
        if(x==null||total==null){
            return "0.00%";
        }
        double tempresult = x*1.0 / total;
        DecimalFormat df1 = new DecimalFormat("0.00%");//##.00%   百分比格式，后面不足2位的用0补齐  
        return df1.format(tempresult);
    }


}
