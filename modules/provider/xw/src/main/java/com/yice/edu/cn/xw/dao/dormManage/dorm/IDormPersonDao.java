package com.yice.edu.cn.xw.dao.dormManage.dorm;

import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildVo;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildingPersonInfo;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormPerson;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface IDormPersonDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<DormPerson> findDormPersonListByCondition(DormPerson dormPerson);

    long findDormPersonCountByCondition(DormPerson dormPerson);

    DormPerson findOneDormPersonByCondition(DormPerson dormPerson);

    DormPerson findDormPersonById(@Param("id") String id);

    void saveDormPerson(DormPerson dormPerson);

    long updateDormPerson(DormPerson dormPerson);

    void deleteDormPerson(@Param("id") String id);

    void deleteDormPersonByCondition(DormPerson dormPerson);

    void batchSaveDormPerson(List<DormPerson> dormPersons);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    List<Student> findStudentListByConditionOnDorm(Student student);

    Long findStudentListCountByConditionOnDorm(Student student);

    List<Teacher> findTeacherListByConditionOnDorm(Teacher teacher);

    Long findTeacherListCountByConditionOnDorm(Teacher teacher);

    List<Teacher> findNoTeacherListByConditionOnDorm(Teacher teacher);

    Long findNoTeacherCountListByConditionOnDorm(Teacher teacher);

    List<DormPerson> findDormPersonListConnectTeacher(DormBuildVo dormBuildVo);

    List<DormPerson> findDormPersonListConnectStudent(DormBuildVo dormBuildVo);

    DormBuildingPersonInfo getDormBuildingById(@Param("id") String id);

    DormPerson findDormPersonOneConnectTeacher(DormBuildVo dormBuildVo);

    DormPerson findDormPersonOneConnectStudent(DormBuildVo dormBuildVo);

    long leaveDorm(DormPerson dormPerson);

    List<DormBuildingPersonInfo> findDormPersonInfoWithStudent(DormBuildingPersonInfo dormBuildingPersonInfo);

    long findDormPersonInfoCountWithStudent(DormBuildingPersonInfo dormBuildingPersonInfo);

    List<DormBuildingPersonInfo> findDormPersonInfoWithTeacher(DormBuildingPersonInfo dormBuildingPersonInfo);

    long findDormPersonInfoCountWithTeacher(DormBuildingPersonInfo dormBuildingPersonInfo);

    //导出空宿舍
    List<Map<String, String>> findEmptyDormByDormCategory(DormBuildVo dormBuildVo);

    //修改保存安排宿舍
    long updateSaveDormPerson(DormPerson dormPerson);

    //毕业学生退宿
    void leaveDormByClassesId(@Param("classesId")String classesId);

    //根据条件查询楼栋,楼层,宿舍的学生入住人数
    long findDormMoveIntoPersonNumByCondition(DormBuildVo dormBuildVo);

    void batchDeleteDormPersonByDormIdList(@Param("dormIdList") List<String> dormIdList);
}
