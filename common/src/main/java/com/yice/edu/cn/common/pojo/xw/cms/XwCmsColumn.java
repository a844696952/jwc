package com.yice.edu.cn.common.pojo.xw.cms;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.common.pojo.validateClass.GroupTwo;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

import java.util.List;


@Data
@ApiModel("校务CMS栏目表")
public class XwCmsColumn{

    @ApiModelProperty("主键")
    @NotNull(groups= {GroupTwo.class},message="栏目Id不能为空")
    private String id;

    @ApiModelProperty("栏目名称")
    @NotNull(groups= {GroupOne.class,GroupTwo.class},message="栏目名称不能为空")
    private String columnName;

    @ApiModelProperty("排序编号")
    @NotNull(groups= {GroupTwo.class},message="栏目排序编号不能为空")
    private Integer columnNumber;

    @ApiModelProperty("栏目父Id")
    @NotNull(groups= {GroupOne.class,GroupTwo.class},message="父栏目不能为空")
    private String parentId;

    @ApiModelProperty("1---文章列表 2-图片列表 3-视频列表 4-文章详情 5-大数据 6-应用列表  7-图文列表页 8---其它")
    @NotNull(groups= {GroupOne.class,GroupTwo.class},message="栏目类型不能为空")
    private Integer columnType;


    @ApiModelProperty("是否显示 1展示 0不展示 默认为1")
    @NotNull(groups= {GroupTwo.class},message="栏目是否展示不能为空")
    private Integer isShow;

    @ApiModelProperty("栏目级别（1级栏目 2级栏目）")
    @NotNull(groups= {GroupTwo.class},message="栏目级别不能为空")
    private Integer columnLevel;

    @ApiModelProperty("栏目图标")
    private String columnIcon;

    @ApiModelProperty("学校Id")
    @NotNull(groups= {GroupOne.class,GroupTwo.class},message="学校Id不能为空")
    private String schoolId;


    @ApiModelProperty("位置1  位置2")
    private Integer position;


    @ApiModelProperty("创建日期")
    private String createTime;

    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;


    private List<XwCmsColumn> children;

    /**
     * banner的标识符 //false 不包含banner true 包含banner
     * 上下移动标志    //false 上移动  true 下移动
     * */
    private Boolean banner;
}
