package com.yice.edu.cn.common.util.vaptch;

import lombok.Data;

@Data
public class VaptchaResponse {
    private Integer success;
    private Integer score;
    private String msg;
}
