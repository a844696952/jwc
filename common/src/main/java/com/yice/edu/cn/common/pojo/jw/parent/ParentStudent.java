package com.yice.edu.cn.common.pojo.jw.parent;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.common.pojo.validateClass.GroupTwo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @BelongsProject: yep
 * @BelongsPackage: com.yice.edu.cn.common.pojo.jw.parent
 * @Author: Administrator
 * @CreateTime: 2018-09-05 11:56
 * @Description: ${Description}
 */
@Data
public class ParentStudent {
    private String id;//主键id
    @ApiModelProperty(value = "家长id" ,dataType = "String")
    private String parentId;//家长id
    @NotBlank(message = "学生id不能为空",groups = GroupTwo.class)
    @ApiModelProperty(value = "学生id" ,dataType = "String")
    private String studentId;//孩子id
    @NotBlank(message = "亲属关系不能为空" ,groups = GroupTwo.class)
    @ApiModelProperty(value = "亲属关系" ,dataType = "String")
    private String relationship;//亲属关系
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("学校id")
    private String schoolId;
    //分页排序等
    @Transient
    @NotNull
    @Valid
    private Pager pager;
}
