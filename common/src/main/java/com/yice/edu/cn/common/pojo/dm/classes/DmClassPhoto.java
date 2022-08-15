package com.yice.edu.cn.common.pojo.dm.classes;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
/**
*
*班级相册表
*
*/
@Data
@ApiModel(description="班级相册")
public class DmClassPhoto{

    @ApiModelProperty(value = "编号",dataType = "String")
    private String id;//主键id
    @ApiModelProperty(value = "学校Id",dataType = "String")
    private String schoolId;//学校id
    @ApiModelProperty(value = "班级Id",dataType = "String")
    private String classId;//班级id
    @ApiModelProperty(value = "相册名称",dataType = "String")
    private String imgName;//相册名称
    private String imgDisplayName;//相册展示名称
    public String getImgDisplayName(){
        if(this.imgName != null || "".equals(this.imgName)){
            int idx = this.imgName.lastIndexOf(".");
            if(idx > -1){
                this.imgDisplayName = this.imgName.substring(0,idx);
            }else{
                this.imgDisplayName = this.imgName;
            }
        }
        return this.imgDisplayName;
    }
    @ApiModelProperty(value = "相册类型，1：荣誉照片,2:普通照片",dataType = "String")
    private Integer imgType;//相册类型。1：荣誉照片，2：普通照片，3：两者
    @ApiModelProperty(value = "图片地址",dataType = "String")
    private String imgUrl;//图片地址
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;//创建时间
    @ApiModelProperty(value = "排序Id",dataType = "String")
    private Integer imgSort;//排序id
    @ApiModelProperty(value = "相册名称模糊查询时用到的字段",dataType = "String")
    private String imgNameLike;//相册名称模糊查询时用到的字段
    //业务字段
    private Integer type;//0 --> 班级相册 1 --> 班会相册
    //分页排序等
    @Transient
    @NotNull
    @Valid
    private Pager pager;
}
