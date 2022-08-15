package com.yice.edu.cn.common.pojo.jw.pen;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
/**
*
*钢笔
*
*/
@Data
public class Pen extends CurSchoolYear {

    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "长度",dataType = "Integer")
    private Integer len;
    @ApiModelProperty(value = "品牌",dataType = "String")
    private String brand;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "修改时间",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "教师id",dataType = "String")
    private String teacherId;
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "钢笔名称",dataType = "String")
    private String name;
}
