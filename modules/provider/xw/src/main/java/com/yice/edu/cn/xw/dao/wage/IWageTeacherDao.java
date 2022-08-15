package com.yice.edu.cn.xw.dao.wage;

import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IWageTeacherDao {
    List<Teacher> findTeacherListByCondition(Teacher teacher);
}
