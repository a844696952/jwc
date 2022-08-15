package com.yice.edu.cn.common.pojo.dm.msg;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author dengfengfeng
 */
@Data
public class StudentReceiveMsg extends ReceiveMsg{

    @ApiModelProperty("班级id")
    private String classesId;

    @ApiModelProperty("学生id")
    private String studentId;


    @ApiModelProperty("学生对象")
    private Student student;

    @ApiModelProperty("班牌推送名")
    private String dmUser;

    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    protected Pager pager;

    public StudentReceiveMsg() {

    }

    public StudentReceiveMsg(DmMsg msg){
        super(msg);
        this.dmUser = msg.getDmUser();
        this.studentId =msg.getStudentId();
    }
}
