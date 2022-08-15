package com.yice.edu.cn.yed.service.baseData.school;

import cn.hutool.crypto.digest.DigestUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.auth.SchoolPerm;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.jw.school.SchoolExt;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.yed.feignClient.baseData.school.SchoolFeign;
import com.yice.edu.cn.yed.feignClient.jw.teacher.TeacherFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
public class SchoolService {
    @Autowired
    private SchoolFeign schoolFeign;
    @Autowired
    private TeacherFeign teacherFeign;

    public School findSchoolById(String id) {
        return schoolFeign.findSchoolById(id);
    }

    public School saveSchool(School school) {
        return schoolFeign.saveSchool(school);
    }

    public List<School> findSchoolListByCondition(School school) {
        return schoolFeign.findSchoolListByCondition(school);
    }

    public long findSchoolCountByCondition(School school) {
        return schoolFeign.findSchoolCountByCondition(school);
    }

    public void updateSchool(School school) {
        schoolFeign.updateSchool(school);
    }

    public void deleteSchool(String id) {
        schoolFeign.deleteSchool(id);
    }

    public void deleteSchoolByCondition(School school) {
        schoolFeign.deleteSchoolByCondition(school);
    }


    public Teacher findAdminBySchool(String id){
        Teacher teacher = new Teacher();
        teacher.setSchoolId(id);
        teacher.setStatus(Constant.STATUS.TEACHER_ADMIN);
        return teacherFeign.findAdminBySchool(teacher);
    }
    public void resetAdminPassword(String teacherId){
        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        teacher.setPassword(DigestUtil.sha1Hex(DigestUtil.md5Hex(Constant.DEFAULT_PWD)));
        teacherFeign.updateTeacher(teacher);
    }

    public void saveSchoolAndSchoolYear(SchoolExt schoolExt) {
        schoolFeign.saveSchoolAndSchoolYear(schoolExt);
    }

    public long findSchoolNoRepetitionSchoolName(School school){
        return schoolFeign.findSchoolNoRepetitionSchoolName(school);
    }
    public List<Integer> queryClockInRange(){
    	 return schoolFeign.queryClockInRange();
    }
}
