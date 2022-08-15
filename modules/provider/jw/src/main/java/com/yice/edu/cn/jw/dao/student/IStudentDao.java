package com.yice.edu.cn.jw.dao.student;

import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDeviceGroupPerson;
import com.yice.edu.cn.common.pojo.jw.electiveCourse.StudentPojo;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.ExamStudentInfo;
import com.yice.edu.cn.common.pojo.jw.student.*;
import com.yice.edu.cn.common.pojo.wb.studentAccount.StudentAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface IStudentDao {
    List<Student> findStudentListByCondition(Student student);

    List<Student> findStudentListByConditionWithBmp(Student student);
    List<StudentForSchoolNotify> findStudentListForSchoolNotify(StudentForSchoolNotify student);
    long findStudentCountByCondition(Student student);

    long findStudentCodeCountByCondition(Student student);

    Student findStudentById(String id);

    Student findOneStudentByCondition(Student student);

    void saveStudent(Student student);

    void updateStudent(Student student);

    void updateStudentForAll(Student student);

    void deleteStudent(String id);

    void deleteStudentByCondition(Student student);

    void batchSaveStudent(List<Student> students);

    List<Student> findStudentListByConditionWithFamily(Student student);
    long findStudentCountByConditionWithFamily(Student student);

    List<Student> findStudentListForYiJianPaiHaoByCondition(Student student);

    List<Student> findStudentListForClassesByCondition(Student student);//名字字段模糊匹配
    long findStudentCountForClassesByCondition(Student student);

    //导出查询
    List<Student> findStudentListForExportStudentByCondition(Student student);
    //倒入查询学籍号
    long findStudentCodeForUpdateByStudentCode(String studentCode);
    //查询全部学籍号
    List<String> findStudentCodeAllListByCondition(Student student);

    //修改查询学籍号
    long findStudentCountByConditionForUpdate(Student student);

    List<ClassesStudentNum> findClassStudentNum(String schoolId,String gradeId);

    // 未升学的学生 {stauts,studentIdsArray}
    void updateNoRiseStudent(Map<String,Object> map);
    // 正常升学的学生{newClassesId,newGradeId,newGradeName,oldClassesId,status}
    void updateRiseStudent(Map<String,Object> map);
    //模糊查询学生通讯录
    List<Student> findStudentListByConditions(Student sd);
    //查询老师所教的班级以及模糊查询（通过学生姓名和学生学籍号）
    List<Student> findTeacherClassList(Student student);
    //用关联查询（查询学生通讯录信息）
    List<Student> findStudentClassStudentInfo(Student student);
    //(一次性查出学生和家庭)
    List<Student> findStudentAndFamily(Student student);
    //(一个班级的学生)
    Long findStudentCount1ByCondition(Student student);
    /**
     * 删除班级后解除非在读学生的挂靠
     * @param map status=51, classesId
     */
    void removeStudentClasses(Map<String,Object> map);

    //查询最大座位号学生
    Student findOneStudentMaxSeatNumberByCondition(Student student);

    //根据条件查询所有学校的学生-运营平台
    List<Student> findAllSchoolStudentListByCondition(Student student);

    long findAllSchoolStudentCountByCondition(Student student);

    List<Student> findStudentByTitleim(Student student);

    //一键注册IM
    void batchUpdateStudentRegisterStatus (Map<String,Object> map);
    //im端只显示已注册的
    List<Student> findTeacherClassListim(Student student);
    //(一次性查出学生和家庭)学生
    List<Student> findStudentAndFamilyim(Student student);
    //学生模糊查询
    List<Student> findStudentListByCondition4Like(Student student);

    List<Student> findStudentListByConditionim(Student student);

    //查询出班级数组总人数
    List<ClassesStudentScoreNum> findStudentCountClassesByCondition(Map<String,Object> map);

    //导入
    List<Map<String,Object>> findStudentListClassesByCondition(Map<String,Object> map);

    List<ExamStudentInfo> findStudentListByClazzIds(@Param("clazzIds") List<String> clazzIds);
    //查询考试学生
    List<Student> findStudentListForExamByCondition(Student student);

    //批量更新学生信息
    void batchUpdateStudent(List<Student> students);

    List<Student> findStudentWithParent(StuParentVo stuParentVo);

    List<Student> findStudentLogin(StudentAccount studentAccount);

    Student getStudentLoginInfo(String id);

    List<Student> findStudentListByStudentIdsAndSchoolId(KqDeviceGroupPerson kqDeviceGroupPerson);
    List<Student> findStudentListByExcludeStudentIdsAndSchoolId(KqDeviceGroupPerson kqDeviceGroupPerson);

    //根据条件修改学生信息(用于修改部分状态信息，具体个人信息 例如[姓名、头像、性别]不应用此接口修改)
    void updateStudentByCondition(@Param("student") Student studentData, @Param("condition") Student studentCondition);

    List<Student> findStudentsByIds(@Param("groupId") String groupId);

    List<Student> findStudentListByClassIdAndName(Student student);

    Map<String, Long> findStudentSummaryBySchool4Index(String schoolId);

    List<Map<String, Object>> findGradeStudentSummaryBySchool4Index(String schoolId);

    List<Student> findStudentByIds(@Param("studentIds") List<String> studentIds);

    List<Map<String, Object>> findSchoolStudentNowStatusNumGroupByClassesId(String schoolId);

    List<Map<String, Object>> findSchoolStudentNowStatusNum(String schoolId);

    List<Student> findSchoolStudentByClazzIdList(Student student);

    List<StudentHistory> findStudentHistoryListBySchoold(String schoolId);

    List<StudentPojo> findStudentListByClassIdList(Student student);

    Long findStudentNoCountByStudentNo(@Param("studentNo") String studentNo,@Param("schoolId")String schoolId);

    void resetPassword(@Param("list") List<String> studentIds,@Param("defaultPassword") String defaultPassword);

    void deleteStudentByClazzIdList(@Param("clazzIdList") List<String> clazzIdList);

    long findStudentNoCountByStudentNoExceptSelf(@Param("studentNo")String studentNo, @Param("schoolId")String schoolId, @Param("id")String id);

    List<StudentPojo> findStudentListAndClassByClazzIdList(@Param("clazzIdList") List<String> clazzIdList);
    List<Student> findClassStudentByClassesId(@Param("classesId") String classesId);

    List<String> selectSignUpStudentsByItemId(@Param("itemId") String itemId,@Param("classesId") String classesId);
}
