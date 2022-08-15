package com.yice.edu.cn.xw.dao.student;


import com.yice.edu.cn.common.pojo.jw.student.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IStudentDao {
    void updateStudentById(Student student);

    Student findStudentById(String id);
}
