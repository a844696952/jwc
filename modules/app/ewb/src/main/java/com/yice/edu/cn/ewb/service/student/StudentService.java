package com.yice.edu.cn.ewb.service.student;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.student.StuParentVo;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.student.StudentForSchoolNotify;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.wb.studentAccount.StudentAccount;
import com.yice.edu.cn.ewb.feignClient.student.StudentFeign;
import com.yice.edu.cn.ewb.feignClient.studentAccount.StudentAccountFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class StudentService {
    @Autowired
    private StudentFeign studentFeign;

    @Autowired
    private StudentAccountFeign studentAccountFeign;



    public Student findStudentById(String id) {
        return studentFeign.findStudentById(id);
    }
    public List<Student> findStudentListByCondition(Student student) {
        return studentFeign.findStudentListByCondition(student);
    }

    public long findStudentCountByCondition(Student student) {
        return studentFeign.findStudentCountByCondition(student);
    }

    public List<Student> findStudentWithParent(StuParentVo stuParentVo){
        return studentFeign.findStudentWithParent(stuParentVo);
    }



    public List<Student> findCorrespondencesByStudent(Student student) {
        return studentFeign.findCorrespondencesByStudent(student);
    }


    public List<Student> findTeacherClassList(Student student) {
        return studentFeign.findTeacherClassList(student);
    }

    public List<Student> findClassStudentByclassTitle(Student student) {
        return studentFeign.findClassStudentByclassTitle(student);
    }

    public List<Student> findStudentByTitleim(Student student) {
        return studentFeign.findStudentByTitleim(student);
    }

    public List<Student> findTeacherClassListim(Student student) {
        return studentFeign.findTeacherClassListim(student);
    }

    public List<Student> findCorrespondencesByStudentim(Student student) {
        return studentFeign.findCorrespondencesByStudentim(student);
    }

    public List<Student> findStudentListByCondition4Like(Student s) {
        return studentFeign.findStudentListByCondition4Like(s);
    }

    public List<Student> findStudentListByConditionim(Student s) {
        return studentFeign.findStudentListByConditionim(s);
    }


    public List<Student> findStudentInfoAndFamily(Student student) {
        return studentFeign.findStudentInfoAndFamily(student);
    }

    public List<StudentForSchoolNotify> findStudentListForSchoolNotify(Student student) {
        return studentFeign.findStudentListForSchoolNotify(student);
    }

    @Cached(name= Constant.Redis.EWB_STUDENT_LOGIN_ERROR, key="#key",expire = 1,timeUnit= TimeUnit.DAYS)
    public String saveLoginErroInfoToRedis(String ErrorInfo,String key){
        return  ErrorInfo;
    }

    @CacheUpdate(name=Constant.Redis.EWB_STUDENT_LOGIN_ERROR, key="#key",value = "#ErrorInfo")
    public void updateLoginErroInfoToRedis(String ErrorInfo,String key){

    }
    @CacheInvalidate(name=Constant.Redis.EWB_STUDENT_LOGIN_ERROR,key = "#key")
    public void clearLoginErroInfoCache(String key){

    }
    @Cached(name = Constant.Redis.EWB_STUDENT_CACHE,key ="#student.id",expire =Constant.Redis.EWB_STUDENT__TIMEOUT, timeUnit = TimeUnit.DAYS)
    public Student saveStudentToRedis(Student student){
        return student;
    }


    public List<StudentAccount> findStudentAccountImei(StudentAccount studentAccount){
        return studentAccountFeign.findStudentAccountImei(studentAccount);
    }


    public List<Student> findStudentLogin(StudentAccount studentAccount){
        return studentFeign.findStudentLogin(studentAccount);
    }

    public Student getStudentLoginInfo(String id){
        return studentFeign.getStudentLoginInfo(id);
    }

}
