package com.yice.edu.cn.common.pojo.ts.aliMsn;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * 短信发布对象
 */
@Data
@AllArgsConstructor
public class Sms {
    /**
     * 短信发送 接收方手机号
     * 必填
     */
    private String tel;
    /**
     * 短信签名-可在短信控制台中找到
     * 必填 从 Constant.MCS_SIGN_NAME 获取
     */
    private String signName;
    /**
     * 短信模板-可在短信控制台中找到
     * 必填 从 Constant.MCS_TEMPLATE 获取
     */
    private String tplCode;
    /**
     * 选填:模板中的变量,如验证码${code},
     * 此处的值为 code:112311
     */
    private Map<String,String> msg;
}
