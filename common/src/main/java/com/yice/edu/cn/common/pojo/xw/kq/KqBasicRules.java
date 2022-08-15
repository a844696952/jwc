package com.yice.edu.cn.common.pojo.xw.kq;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author:xushu
 * @date:2019/3/1
 */
@Data
@Document
@ApiModel("考勤管理基础规则表")
public class KqBasicRules {
    @ApiModelProperty(value = "主键", dataType = "String")
    private String id;
    @ApiModelProperty(value = "打卡次数 1 一天一组 2 一天两组", dataType = "String")
    private String punchNumber;
    @ApiModelProperty(value = "打卡的规范", dataType = "String")
    private KqPunchRules punchTime;
    @ApiModelProperty("打卡时间 1:星期日,2:星期一,3:星期二,4:星期三,5:星期四,6:星期五,7:星期六")
    private String[] punchDay;
    @ApiModelProperty(value = "0 旧规则 1 新规则 ", dataType = "String")
    private String rule;
    @ApiModelProperty(value = "创建时间", dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "更新时间", dataType = "String")
    private String updateTime;
    @Indexed
    @ApiModelProperty(value = "学校Id", dataType = "String")
    private String schoolId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

}




























































