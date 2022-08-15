package com.yice.edu.cn.common.pojo.general.dd;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
*
*数据字典
系统维护字段
*
*/
@Data
@Accessors(chain = true)
public class Dd{
    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("类型")
    private String typeId;
    @ApiModelProperty("类型名称")
    private String typeName;
    @ApiModelProperty("删除标志")
    private String del;
    @ApiModelProperty("createTime")
    private String createTime;
    @ApiModelProperty("updateTime")
    private String updateTime;
    @ApiModelProperty("要插入的表名")
    private String tableName;
    @ApiModelProperty("年级类型（1：小学，2：初中，3：高中 等等等）")
    private String levelType;
    @ApiModelProperty("排序")
    private Integer sort;
    @ApiModelProperty("拓展字段1")
    private String ext1;
    @ApiModelProperty("拓展字段2")
    private String ext2;
    //分页排序等
    @Transient
    private Pager pager;
    //额外
    @Transient
    private List<Dd> children;
    List<MaterialItem> item;
    private String gradeId;
    private String schoolId;

    private long  totalCount;//班级是否已有排课
}
