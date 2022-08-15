package com.yice.edu.cn.osp.service.jw.teacher;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherQuit;
import com.yice.edu.cn.osp.feignClient.jw.teacher.TeacherQuitFeign;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherQuitService {
    @Autowired
    private TeacherQuitFeign teacherQuitFeign;

    public TeacherQuit findTeacherQuitById(String id) {
        return teacherQuitFeign.findTeacherQuitById(id);
    }

    public TeacherQuit saveTeacherQuit(TeacherQuit teacherQuit) {
        return teacherQuitFeign.saveTeacherQuit(teacherQuit);
    }

    public List<TeacherQuit> findTeacherQuitListByCondition(TeacherQuit teacherQuit) {
        return teacherQuitFeign.findTeacherQuitListByCondition(teacherQuit);
    }

    public long findTeacherQuitCountByCondition(TeacherQuit teacherQuit) {
        return teacherQuitFeign.findTeacherQuitCountByCondition(teacherQuit);
    }

    public void updateTeacherQuit(TeacherQuit teacherQuit) {
        teacherQuitFeign.updateTeacherQuit(teacherQuit);
    }

    public void deleteTeacherQuit(String id) {
        teacherQuitFeign.deleteTeacherQuit(id);
    }

    public void deleteTeacherQuitByCondition(TeacherQuit teacherQuit) {
        teacherQuitFeign.deleteTeacherQuitByCondition(teacherQuit);
    }
    public Workbook exportTeacher(TeacherQuit teacherQuit){
        List<TeacherQuit> teacherQuitList = teacherQuitFeign.findTeacherQuitListByCondition(teacherQuit);
        return ExcelExportUtil.exportExcel(new ExportParams("离职教师列表","离职教师"),
                TeacherQuit.class, teacherQuitList);
    }
}
