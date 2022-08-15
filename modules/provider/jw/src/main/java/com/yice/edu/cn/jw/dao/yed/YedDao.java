package com.yice.edu.cn.jw.dao.yed;

import com.yice.edu.cn.common.pojo.jw.yed.Enrolment;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.jw.yed.StudentCheckWork;
import com.yice.edu.cn.common.pojo.jw.yed.Yed;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.List;

@Mapper
public interface YedDao {

    //查询新生人数占比
    List<Yed> findNewbornList(Yed yed);

    //查询对应年级所占的总人数
    long findConutNewbornList(Yed yed);

    //根据教育局id查询教师总数
    long findTeacherCountByEducationBureauId(@Param("educationBureauId") String educationBureauId);

    long findStudentCountByEducationBureauId(@Param("educationBureauId") String educationBureauId);


    //根据教育局id查询学生考勤情况
    List<StudentCheckWork> findStudentCheckWork(StudentCheckWork studentCheckWork );


    //查询学校场地类型数量
    List<Yed> findSpaceByRoleList(Yed yed);
    //根据教育局id查询学校
    List<School>findSchoolByEducation(@Param("educationBureauId") String educationBureauId);

    //查询近三年升学率
    List<Enrolment> findEnrolmentList(Enrolment enrolment);

}
