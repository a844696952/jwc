package com.yice.edu.cn.common.pojo.dm.msg;

import com.yice.edu.cn.common.pojo.jw.parent.Parent;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Sender{

    @NotNull(message = "发送者名称不可为空",groups = {GroupOne.class})
    private String name;
    @NotNull(message = "发送者性别不可为空",groups = {GroupOne.class})
    private String sex;
    @NotNull(message = "发送者头像不可为空",groups = {GroupOne.class})
    private String headImg;
    @NotNull(message = "发送者id不可为空",groups = {GroupOne.class})
    private String id;

    public Sender() {
    }

    public Sender(Student student){
        this.id = student.getId();
        this.headImg = student.getImgUrl();
        this.name = student.getName();
        this.sex = student.getSex();
    }

    public Sender(Teacher teacher){
        this.id = teacher.getId();
        this.headImg = teacher.getImgUrl();
        this.name = teacher.getName();
        this.sex = teacher.getSex();
    }

    public Sender(Parent parent){
        this.id = parent.getId();
        this.name = parent.getName();
        this.sex = parent.getSex();
    }

}