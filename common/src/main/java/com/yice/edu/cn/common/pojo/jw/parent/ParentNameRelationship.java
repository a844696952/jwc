package com.yice.edu.cn.common.pojo.jw.parent;

import com.yice.edu.cn.common.pojo.validateClass.GroupTwo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @BelongsProject: yep
 * @BelongsPackage: com.yice.edu.cn.common.pojo.jw.parent
 * @Author: Administrator
 * @CreateTime: 2018-09-20 20:02
 * @Description: ${Description}
 */
@Data
public class ParentNameRelationship implements Serializable {
    private static final long serialVersionUID = 6260618682507344724L;
    @ApiModelProperty(value = "家长姓名",dataType = "String")
    @NotBlank(message = "家长姓名不能为空" )
    private String parentName;
    @NotBlank(message = "亲属关系不能为空")
    @ApiModelProperty(value = "亲属关系" ,dataType = "String")
    private String relationship;
    @NotBlank(message = "学生id")
    @ApiModelProperty(value = "学生id" ,dataType = "String")
    private  String studentId;

    private  Parent parent;
    private  ParentStudent parentStudent;
  }
