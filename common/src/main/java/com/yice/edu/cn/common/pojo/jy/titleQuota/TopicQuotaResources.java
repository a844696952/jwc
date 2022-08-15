package com.yice.edu.cn.common.pojo.jy.titleQuota;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Data
@ApiModel("题目额度资源表")
public class TopicQuotaResources{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("已使用额度(单位为：次)")
    private Integer remainingMargin;
    @ApiModelProperty("总配置额度(单位为：次)")
    private Integer totalMargin;
    @ApiModelProperty("备注")
    private String remarks;
    @ApiModelProperty("截止时间")
    private String closingDate;
    @ApiModelProperty("上传凭证")
    private String uploadVouchers;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    @ApiModelProperty("学校id")
    private String schoolId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    private Integer increaseQuantity;//增加资源数量
    //当前操作者名字
    private  String opertor;
    private  String code1;
    private  String code2;
    private  String address;
    private  String name;
    private  String pritivetel;//原手机号码
    private  String tel;//新手机号码
    private  String surepassword;//确认密码
    private  String password;//用户输入密码
    private  String ids;//平台id字符串

    //给理平接口添加字段
    private long schoolRemain;//学校剩余额度
    private long personRemain;//个人剩余
    private String teacherId;//教师id
}
