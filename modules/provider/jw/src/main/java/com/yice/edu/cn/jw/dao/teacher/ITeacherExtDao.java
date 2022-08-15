package com.yice.edu.cn.jw.dao.teacher;

import com.yice.edu.cn.common.pojo.general.node.Node;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ITeacherExtDao {
    List<Node> searchTeachersByName(Teacher teacher);
}
