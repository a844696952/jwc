package com.yice.edu.cn.yed.service.jw.student;

import com.yice.edu.cn.common.pojo.jw.student.StudentFamily;
import com.yice.edu.cn.yed.feignClient.jw.student.StudentFamilyFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentFamilyService {
    @Autowired
    private StudentFamilyFeign studentFamilyFeign;

    public List<StudentFamily> findStudentFamilyById(String id) {
        return studentFamilyFeign.findStudentFamilyById(id);
    }

    public StudentFamily saveStudentFamily(StudentFamily studentFamily) {
        return studentFamilyFeign.saveStudentFamily(studentFamily);
    }

    public List<StudentFamily> findStudentFamilyListByCondition(StudentFamily studentFamily) {
        return studentFamilyFeign.findStudentFamilyListByCondition(studentFamily);
    }

    public StudentFamily findOneStudentFamilyByCondition(StudentFamily studentFamily) {
        return studentFamilyFeign.findOneStudentFamilyByCondition(studentFamily);
    }

    public long findStudentFamilyCountByCondition(StudentFamily studentFamily) {
        return studentFamilyFeign.findStudentFamilyCountByCondition(studentFamily);
    }

    public void updateStudentFamily(StudentFamily studentFamily) {
        studentFamilyFeign.updateStudentFamily(studentFamily);
    }

    public void deleteStudentFamily(String id) {
        studentFamilyFeign.deleteStudentFamily(id);
    }

    public void deleteStudentFamilyByCondition(StudentFamily studentFamily) {
        studentFamilyFeign.deleteStudentFamilyByCondition(studentFamily);
    }
}
