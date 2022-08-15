package com.yice.edu.cn.common.pojo.dm.student;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;

@Data
@ApiModel("三好学生表")
public class DmMeritStudent {

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("学生ID")
    private String studentId;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("学校ID")
    private String schoolId;
     //分页
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    @ApiModelProperty("学生名称，不在数据库供VO使用")
    private String name;


    @ApiModelProperty("学生名称，不在数据库供VO使用")
    private String imgUrl;

    @ApiModelProperty("班级号码，不在数据库供VO使用")
    private Integer number;

    @ApiModelProperty("年级，不在数据库供VO使用")
    private String gradeName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DmMeritStudent that = (DmMeritStudent) o;

        return studentId.equals(that.studentId);
    }

    @Override
    public int hashCode() {
        return studentId.hashCode();
    }
}
