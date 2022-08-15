package com.yice.edu.cn.common.pojo.dm.kq;

import lombok.Data;

import java.util.List;


public class CalendayEntity {
    /**
     *节日
     */
    public String festival;

    /**
     * 节日名称
     */
    public String name;
    /**
     * 节假日列表，状态为1表示节假日，状态为2表示节假日前后调整工作日
     */
    public List<Festival> list;

    /**
     * 备注
     */
    public String desc;

    public class Festival {
        /**
         * 日期字符串
         */
        public String date;
        /**
         * 节假日状态
         */
        public String status;
    }
}
