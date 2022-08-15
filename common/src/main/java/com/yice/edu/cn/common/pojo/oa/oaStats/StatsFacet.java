package com.yice.edu.cn.common.pojo.oa.oaStats;

import lombok.Data;

import java.util.List;

@Data
public class StatsFacet {
    private List<CountObj> total;
    private List<CountObj> wait;
    private List<CountObj> success;
    private List<CountObj> fail;


    @Data
    public static class  CountObj{
        private String id;
        private String schoolProcessId;
        private String type;
        private Integer status;
        private int count;
    }
}
