package com.yice.edu.cn.osp.feignClient.jw.student;

import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDeviceGroupPerson;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.ExamStudentInfo;
import com.yice.edu.cn.common.pojo.jw.student.ClassesStudentScoreNum;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(value="jw",contextId = "studentFeign",path = "/student")
public interface StudentFeign {
    @GetMapping("/findStudentById/{id}")
    Student findStudentById(@PathVariable("id") String id);
    @PostMapping("/findOneStudentByCondition")
    Student findOneStudentByCondition(Student student);
    @PostMapping("/saveStudent")
    Student saveStudent(Student student);
    @PostMapping("/findStudentListByCondition")
    List<Student> findStudentListByCondition(Student student);
    @PostMapping("/findStudentCodeCountByCondition")
    long findStudentCountByCondition(Student student);
    @PostMapping("/findStudentCodeCountByCondition")
    long findStudentCodeCountByCondition(Student student);
    @PostMapping("/updateStudent")
    void updateStudent(Student student);
    @PostMapping("/updateStudentNew")
    void updateStudentNew(Student student);
    @GetMapping("/deleteStudent/{id}")
    void deleteStudent(@PathVariable("id") String id);
    @PostMapping("/deleteStudentByCondition")
    void deleteStudentByCondition(Student student);
    @PostMapping("/findStudentListByConditionWithFamily")
    List<Student> findStudentListByConditionWithFamily(Student student);
    @PostMapping("/findStudentCountByConditionWithFamily")
    long findStudentCountByConditionWithFamily(Student student);

    @PostMapping("/batchSaveStudent")
    void batchSaveStudent(List<Student> studentList);

    @PostMapping("/findStudentListForClassesByCondition") //按条件查询学生列表，班级管理调用 名字含模糊匹配 并且要自己拼接 %name%
    List<Student> findStudentListForClassesByCondition(Student student);
    @PostMapping("/findStudentCountForClassesByCondition")
    long findStudentCountForClassesByCondition(Student student);

    @GetMapping("/findStudentCodeForUpdateByStudentCode/{studentCode}")
    long findStudentCodeForUpdateByStudentCode(@PathVariable("studentCode") String studentCode);

    @PostMapping("/findStudentCodeAllListByCondition")
    List<String> findStudentCodeAllListByCondition(Student student);

    @PostMapping("/findStudentCountByConditionForUpdate")
    long findStudentCountByConditionForUpdate(Student student);

    @PostMapping("/findCorrespondencesByStudent")
    List<Student> findCorrespondencesByStudent(Student student);

    @PostMapping("/findTeacherClassList")
    List<Student> findTeacherClassList(Student student);

    @PostMapping("/findClassStudentByclassTitle")
    List<Student> findClassStudentByclassTitle(Student student);

    @PostMapping("/findOneStudentMaxSeatNumberByCondition")
    Student findOneStudentMaxSeatNumberByCondition(Student student);

    @PostMapping("/findStudentRowNumber")
    void findStudentRowNumber(Student student);

    @PostMapping("/batchUpdateStudentRegisterStatus")
    long batchUpdateStudentRegisterStatus(Student student);

    @PostMapping("/findStudentCountClassesByCondition")
    List<ClassesStudentScoreNum> findStudentCountClassesByCondition(Map map);
    @PostMapping("/findStudentListScoreByCondition")
    List<Student> findStudentListScoreByCondition(Student student);

    @PostMapping("/findStudentListByConditionWithBmp")  //按条件查询学生列表，不含模糊匹配
    List<Student> findStudentListByConditionWithBmp(Student student);

    @PostMapping("/findStudentListClassesByCondition") //导入
    List<Map<String,Object>> findStudentListClassesByCondition(List<JwClasses> jwClassesList);
    @PostMapping("/findStudentListByClazzIds")
    List<ExamStudentInfo> findStudentListByClazzIds(List<String> clazzIds);

    @PostMapping("/findStudentListForExamByCondition")//查询考试学生
    List<Student> findStudentListForExamByCondition(Student student);

    @PostMapping("/findStudentListForExportStudentByCondition")
    List<Student> findStudentListForExportStudentByCondition(Student student);
    //批量更新
    @PostMapping("/batchUpdateStudent")
    void batchUpdateStudent(List<Student> studentList);

    @PostMapping("/findStudentListByStudentIdsAndSchoolId")
    List<Student> findStudentListByStudentIdsAndSchoolId(KqDeviceGroupPerson kqDeviceGroupPerson);
    @PostMapping("/findStudentListByExcludeStudentIdsAndSchoolId")
    List<Student> findStudentListByExcludeStudentIdsAndSchoolId(KqDeviceGroupPerson kqDeviceGroupPerson);

    @PostMapping("/findStudentListByClassIdAndName")
    List<Student> findStudentListByClassIdAndName(Student student);

    @GetMapping("/findStudentSummaryBySchool4Index/{schoolId}")
    Map<String,Long> findStudentSummaryBySchool4Index(@PathVariable("schoolId")String mySchoolId);

    @GetMapping("/findGradeStudentSummaryBySchool4Index/{schoolId}")
    List<Map<String,Object>> findGradeStudentSummaryBySchool4Index(@PathVariable("schoolId")String mySchoolId);

    @GetMapping("/findSchoolStudentNowStatusNumGroupByClassesId/{schoolId}")
    List<Map<String,Object>> findSchoolStudentNowStatusNumGroupByClassesId(@PathVariable("schoolId")String mySchoolId);

    @GetMapping("/findSchoolStudentNowStatusNum/{schoolId}")
    List<Map<String,Object>> findSchoolStudentNowStatusNum(@PathVariable("schoolId")String mySchoolId);

    @PostMapping("/updateStudentById")
    void updateStudentById(Student student) ;

    @PostMapping("findStudentNoCountByStudentNo")
    long findStudentNoCountByStudentNo(Student student);

    @PostMapping("resetPassword")
    void resetPassword(List<String> ids);

    @PostMapping("findStudentNoCountByStudentNoExceptSelf")
    long findStudentNoCountByStudentNoExceptSelf(Student student);
}
