package com.yice.edu.cn.tap.service.teacher;

import com.yice.edu.cn.common.pojo.xw.teacherattendance.XwTeacherImageInput;
import com.yice.edu.cn.tap.feignClient.teacher.XwTeacherImageInputFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XwTeacherImageInputService {
    @Autowired
    private XwTeacherImageInputFeign xwTeacherImageInputFeign;
    @Autowired
    private TeacherService teacherService;

    public XwTeacherImageInput findXwTeacherImageInputById(String id) {
        return xwTeacherImageInputFeign.findXwTeacherImageInputById(id);
    }

    public XwTeacherImageInput saveXwTeacherImageInput(XwTeacherImageInput xwTeacherImageInput) {
        return xwTeacherImageInputFeign.saveXwTeacherImageInput(xwTeacherImageInput);
    }

    public List<XwTeacherImageInput> findXwTeacherImageInputListByCondition(XwTeacherImageInput xwTeacherImageInput) {
        return xwTeacherImageInputFeign.findXwTeacherImageInputListByCondition(xwTeacherImageInput);
    }

    public XwTeacherImageInput findOneXwTeacherImageInputByCondition(XwTeacherImageInput xwTeacherImageInput) {
        return xwTeacherImageInputFeign.findOneXwTeacherImageInputByCondition(xwTeacherImageInput);
    }

    public long findXwTeacherImageInputCountByCondition(XwTeacherImageInput xwTeacherImageInput) {
        return xwTeacherImageInputFeign.findXwTeacherImageInputCountByCondition(xwTeacherImageInput);
    }

    public void updateXwTeacherImageInput(XwTeacherImageInput xwTeacherImageInput) {
        xwTeacherImageInputFeign.updateXwTeacherImageInput(xwTeacherImageInput);
    }

    public void deleteXwTeacherImageInput(String id) {
        xwTeacherImageInputFeign.deleteXwTeacherImageInput(id);
    }

    public void deleteXwTeacherImageInputByCondition(XwTeacherImageInput xwTeacherImageInput) {
        xwTeacherImageInputFeign.deleteXwTeacherImageInputByCondition(xwTeacherImageInput);
    }

    public List<XwTeacherImageInput> findXwTeacherImageInputListAll(String schoolId) {
        return xwTeacherImageInputFeign.findXwTeacherImageInputListAll(schoolId);
    }

    public long findXwTeacherImageInputListAllCount(String schoolId) {
        return xwTeacherImageInputFeign.findXwTeacherImageInputListAllCount(schoolId);
    }

    public List<XwTeacherImageInput> findXwTeacherImageInputByTeacherName(String teacherName, String schoolId) {
        return xwTeacherImageInputFeign.findXwTeacherImageInputByTeacherName(teacherName, schoolId);
    }

    public long findXwTeacherImageInputByTeacherNameCount(String teacherName, String schoolId) {
        return xwTeacherImageInputFeign.findXwTeacherImageInputByTeacherNameCount(teacherName, schoolId);
    }

    public List<XwTeacherImageInput> findXwTeacherImageInputByTeacherId(String teacherId, String schoolId) {
        return xwTeacherImageInputFeign.findXwTeacherImageInputByTeacherId(teacherId, schoolId);
    }
}
