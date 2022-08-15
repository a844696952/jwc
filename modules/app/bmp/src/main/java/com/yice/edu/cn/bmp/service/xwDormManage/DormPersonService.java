package com.yice.edu.cn.bmp.service.xwDormManage;

import com.yice.edu.cn.bmp.feignClient.student.StudentFeign;
import com.yice.edu.cn.bmp.feignClient.teacher.TeacherClassesFeign;
import com.yice.edu.cn.bmp.feignClient.xwDormManage.DormPersonFeign;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildingPersonInfo;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DormPersonService {
    @Autowired
    private DormPersonFeign dormPersonFeign;
    @Autowired
    private StudentFeign studentFeign;
    @Autowired
    private TeacherClassesFeign teacherClassesFeign;

    public List<DormBuildingPersonInfo> findDormPersonInfoWithStudent(DormBuildingPersonInfo dormBuildingPersonInfo) {

        List<DormBuildingPersonInfo> list = dormPersonFeign.findDormPersonInfoWithStudent(dormBuildingPersonInfo);
        if (list!=null&&list.size()>0){
            DormBuildingPersonInfo personInfo = list.get(0);
            //当前孩子班主任
            Student student = studentFeign.findStudentById(personInfo.getPersonId());
            TeacherClasses teacherClasses = new TeacherClasses();
            teacherClasses.setClassesId(student.getClassesId());
            Teacher teacher = teacherClassesFeign.findHeadmasterByClasses(teacherClasses);
            if (teacher != null) {
                personInfo.setTeacherName(teacher.getName());
                personInfo.setTeacherTel(teacher.getTel());
            }

        }
        return list;
    }

    public long findDormPersonCountByCondition(DormPerson dormPerson) {
        return dormPersonFeign.findDormPersonCountByCondition(dormPerson);
    }

}
