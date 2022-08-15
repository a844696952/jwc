package com.yice.edu.cn.common.pojo.dm.msg;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author dengfengfeng
 */
@Data
public class TeacherReceiveMsg extends ReceiveMsg {

    @ApiModelProperty("教师id")
    private String teacherId;

    public TeacherReceiveMsg(){

    }

    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    protected Pager pager;

    public TeacherReceiveMsg(DmMsg msg){
        super(msg);
        this.teacherId = msg.getTeacherId();
    }
}
