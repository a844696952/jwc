package com.yice.edu.cn.common.pojo.loc;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;

@Data
@ApiModel("学校")
public class LocSchool {

    @ApiModelProperty("id")
    @NotNull(message = "学校id不能为空",groups = {GroupOne.class})
    private String id;
    @ApiModelProperty("学校名称")
    private String name;
    @ApiModelProperty("校徽")
    private String schoolBadge;
    @ApiModelProperty("办学性质,0公立,1私立,2中外合资")
    private String outing;
    @ApiModelProperty("学校属性,引用dd的id")
    private String propertyId;
    @ApiModelProperty("属性名称,冗余dd的name")
    private String propertyName;
    @ApiModelProperty("学校等级,引用dd的id")
    private String levelId;
    @ApiModelProperty("学校等级,冗余dd的name")
    private String levelName;
    @ApiModelProperty("省份id,引用ad_region的id")
    private String provinceId;
    @ApiModelProperty("省份名称,引用ad_region的name")
    private String provinceName;
    @ApiModelProperty("城市id,引用ad_region的id")
    private String cityId;
    @ApiModelProperty("城市名称,引用ad_region的name")
    private String cityName;
    @ApiModelProperty("区id,引用ad_region的id")
    private String districtId;
    @ApiModelProperty("区名称,引用ad_region的name")
    private String districtName;
    @ApiModelProperty("详细地址")
    private String address;
    @ApiModelProperty("创办时间")
    private String buildTime;
    @ApiModelProperty("校长名字")
    private String principal;
    @ApiModelProperty("校训")
    private String motto;
    @ApiModelProperty("学校简介")
    private String descr;
    @ApiModelProperty("学校文化")
    private String culture;
    @ApiModelProperty("学校类型,小学,初中高中,引用dd")
    private String typeId;
    @ApiModelProperty("学校类型名称,引用dd")
    private String typeName;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("修改时间")
    private String updateTime;
    @ApiModelProperty("学校账号到期时间")
    private String outTime;
    @ApiModelProperty("百度地图经度")
    private String lon;
    @ApiModelProperty("百度地图纬度")
    private String lat;
    @ApiModelProperty("学校账号状态【启用、禁用】")
    private String status;
    @ApiModelProperty("默认亿策,为空时显示亿策logo，0为平台logo,1不显示logo，2显示学校logo")
    private Integer showLogo;
    @ApiModelProperty("升班开始时间")
    private String riseBeginTime;
    @ApiModelProperty("学校服务器ip")
    private String ip;
    @ApiModelProperty("版本号")
    @NotNull(message = "版本号不能为空",groups = {GroupOne.class})
    private String version;
     //分页
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
