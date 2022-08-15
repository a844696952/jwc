package com.yice.edu.cn.common.pojo.jw.school;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
*
*学校
*
*/
@Data
public class School implements Serializable {

    private static final long serialVersionUID = 5168851949324965380L;
    @ApiModelProperty(value = "编号",dataType = "String")
    private String id;
    @ApiModelProperty(value = "学校名称",dataType = "String")
    private String name;//学校名称
    @ApiModelProperty(value = "校徽",dataType = "String")
    private String schoolBadge;//校徽
    @ApiModelProperty(value = "办学性质,0公立,1私立",dataType = "String")
    private String outing;//办学性质,0公立,1私立
    @ApiModelProperty(value = "学校属性id",dataType = "String")
    private String propertyId;//学校属性,引用dd的id
    @ApiModelProperty(value = "学校属性名称",dataType = "String")
    private String propertyName;//属性名称,冗余dd的name
    @ApiModelProperty(value = "学校等级id",dataType = "String")
    private String levelId;//学校等级,引用dd的id
    @ApiModelProperty(value = "学校等级名称",dataType = "String")
    private String levelName;//学校等级,冗余dd的name
    @ApiModelProperty(value = "省份id",dataType = "String")
    private String provinceId;//省份id,引用ad_region的id
    @ApiModelProperty(value = "省份名称",dataType = "String")
    private String provinceName;//省份名称,引用ad_region的name
    @ApiModelProperty(value = "城市id",dataType = "String")
    private String cityId;//城市id,引用ad_region的id
    @ApiModelProperty(value = "城市名称",dataType = "String")
    private String cityName;//城市名称,引用ad_region的name
    @ApiModelProperty(value = "区id",dataType = "String")
    private String districtId;//区id,引用ad_region的id
    @ApiModelProperty(value = "区名称",dataType = "String")
    private String districtName;//区名称,引用ad_region的name
    @ApiModelProperty(value = "详细地址",dataType = "String")
    private String address;//详细地址
    @ApiModelProperty(value = "创办时间",dataType = "String")
    private String buildTime;//创办时间
    @ApiModelProperty(value = "校长名字",dataType = "String")
    private String principal;//校长名字
    @ApiModelProperty(value = "校训",dataType = "String")
    private String motto;//校训
    @ApiModelProperty(value = "学校简介",dataType = "String")
    private String descr;//学校简介
    @ApiModelProperty(value = "学校文化",dataType = "String")
    private String culture;//学校文化
    @ApiModelProperty(value = "学校类型id",dataType = "String")
    private String typeId;//学校类型,小学,初中高中,引用dd
    @ApiModelProperty(value = "学校类型名称",dataType = "String")
    private String typeName;//学校类型名称,引用dd
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;//创建时间
    @ApiModelProperty(value = "修改时间",dataType = "String")
    private String updateTime;//修改时间
    @ApiModelProperty(value = "学校账号到期时间",dataType = "String")
    private String outTime;
    @ApiModelProperty(value = "百度地图经度",dataType = "String")
    private String lon;
    @ApiModelProperty(value = "百度地图纬度",dataType = "String")
    private String lat;
    @ApiModelProperty(value = "学校账号状态【启用、禁用】",dataType = "String")
    private String status;
    @ApiModelProperty("默认亿策,为空时显示亿策logo，1不显示logo，2显示学校logo")
    private Integer showLogo;
    @ApiModelProperty(value = "升班开始时间",dataType = "String")
    private String riseBeginTime;
    @ApiModelProperty("平台名称")
    private String terraceName;
    @ApiModelProperty("学校字体大小")
    private String schoolFontSize;
    @ApiModelProperty("考勤范围")
    private Integer clockInRange;
    //分页排序等
    @Transient
    @NotNull
    private Pager pager;

    private List<String> descrImgs;
}
