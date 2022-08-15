package com.yice.edu.cn.tap.service.xwDormManage.dorm;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.department.DepartmentTeacher;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildingPersonInfo;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormPerson;
import com.yice.edu.cn.tap.feignClient.department.DepartmentTeacherFeign;
import com.yice.edu.cn.tap.feignClient.student.StudentFeign;
import com.yice.edu.cn.tap.feignClient.teacher.TeacherClassesFeign;
import com.yice.edu.cn.tap.feignClient.xwDormManage.dorm.DormPersonFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DormPersonService {
    @Autowired
    private DormPersonFeign dormPersonFeign;
    @Autowired
    private TeacherClassesFeign teacherClassesFeign;
    @Autowired
    private DepartmentTeacherFeign departmentTeacherFeign;
    @Autowired
    private StudentFeign studentFeign;





    public List<DormBuildingPersonInfo> findDormPersonListByConditionConnect(DormBuildingPersonInfo dormBuildingPersonInfo) {
        if (dormBuildingPersonInfo.getDormCategory().equals(Constant.DORM_CATEGORY.DORM_STUDENT)) {
            List<DormBuildingPersonInfo> list = dormPersonFeign.findDormPersonInfoWithStudent(dormBuildingPersonInfo);
            list.forEach(s->{
                //当前孩子班主任
                TeacherClasses teacherClasses = new TeacherClasses();
                teacherClasses.setClassesId(s.getClassesId());
                Teacher teacher = teacherClassesFeign.findHeadmasterByClasses(teacherClasses);
                if (teacher != null) {
                    s.setTeacherName(teacher.getName());
                    s.setTeacherTel(teacher.getTel());
                }
            });
            return list;
        } else {
            List<DormBuildingPersonInfo> dormPersonInfoWithTeacher = dormPersonFeign.findDormPersonInfoWithTeacher(dormBuildingPersonInfo);
            return dormPersonInfoWithTeacher;
        }

    }



    public DormBuildingPersonInfo getDormBuildingById(String id){
        return dormPersonFeign.getDormBuildingById(id);
    }



    public List<DormBuildingPersonInfo> findDormPersonInfoWithStudent(DormBuildingPersonInfo dormBuildingPersonInfo) {
        List<DormBuildingPersonInfo> list = dormPersonFeign.findDormPersonInfoWithStudent(dormBuildingPersonInfo);
        list.forEach(s->{
            //当前孩子班主任
            Student student = studentFeign.findStudentById(s.getPersonId());
            TeacherClasses teacherClasses = new TeacherClasses();
            teacherClasses.setClassesId(student.getClassesId());
            Teacher teacher = teacherClassesFeign.findHeadmasterByClasses(teacherClasses);
            if (teacher != null) {
                s.setTeacherName(teacher.getName());
                s.setTeacherTel(teacher.getTel());
            }
        });
       return list;
    }

    public Long findDormPersonInfoCountWithStudent(DormBuildingPersonInfo dormBuildingPersonInfo) {
        return dormPersonFeign.findDormPersonInfoCountWithStudent(dormBuildingPersonInfo);
    }

    public List<DormBuildingPersonInfo> findDormPersonInfoWithTeacher(DormBuildingPersonInfo dormBuildingPersonInfo) {
        List<DormBuildingPersonInfo> list = dormPersonFeign.findDormPersonInfoWithTeacher(dormBuildingPersonInfo);
        if (list!=null&&list.size()>0){
            list.forEach(info->{
                List<DepartmentTeacher> departmentTeacherList = departmentTeacherFeign.findDepartmentByTeacherId(info.getPersonId());
                info.setDepartmentTeacher(departmentTeacherList);
            });
        }
        return list;
    }

    public Long findDormPersonInfoCountWithTeacher(DormBuildingPersonInfo dormBuildingPersonInfo) {
        return dormPersonFeign.findDormPersonInfoCountWithTeacher(dormBuildingPersonInfo);
    }

    public  Teacher findHeadmasterByClasses(TeacherClasses teacherClasses ){
        return teacherClassesFeign.findHeadmasterByClasses(teacherClasses);
    }


    public long findDormPersonCountByCondition(DormPerson dormPerson) {

        return dormPersonFeign.findDormPersonCountByCondition(dormPerson);
    }

    public List<Student> findStudentListByConditionOnDorm(Student student) {
        return dormPersonFeign.findStudentListByConditionOnDorm(student);
    }

    public Long findStudentListCountByConditionOnDorm(Student student) {
        return dormPersonFeign.findStudentListCountByConditionOnDorm(student);
    }
}
