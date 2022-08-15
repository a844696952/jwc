package com.yice.edu.cn.osp.service.jw.student;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.yice.edu.cn.common.pojo.jw.student.JwStudentGraduation;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.osp.feignClient.jw.student.JwStudentGraduationFeign;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwStudentGraduationService {
    @Autowired
    private JwStudentGraduationFeign jwStudentGraduationFeign;

    public JwStudentGraduation findJwStudentGraduationById(String id) {
        return jwStudentGraduationFeign.findJwStudentGraduationById(id);
    }

    public JwStudentGraduation saveJwStudentGraduation(JwStudentGraduation jwStudentGraduation) {
        return jwStudentGraduationFeign.saveJwStudentGraduation(jwStudentGraduation);
    }

    public List<JwStudentGraduation> findJwStudentGraduationListByCondition(JwStudentGraduation jwStudentGraduation) {
        return jwStudentGraduationFeign.findJwStudentGraduationListByCondition(jwStudentGraduation);
    }

    public JwStudentGraduation findOneJwStudentGraduationByCondition(JwStudentGraduation jwStudentGraduation) {
        return jwStudentGraduationFeign.findOneJwStudentGraduationByCondition(jwStudentGraduation);
    }

    public long findJwStudentGraduationCountByCondition(JwStudentGraduation jwStudentGraduation) {
        return jwStudentGraduationFeign.findJwStudentGraduationCountByCondition(jwStudentGraduation);
    }

    public void updateJwStudentGraduation(JwStudentGraduation jwStudentGraduation) {
        jwStudentGraduationFeign.updateJwStudentGraduation(jwStudentGraduation);
    }

    public void deleteJwStudentGraduation(String id) {
        jwStudentGraduationFeign.deleteJwStudentGraduation(id);
    }

    public void deleteJwStudentGraduationByCondition(JwStudentGraduation jwStudentGraduation) {
        jwStudentGraduationFeign.deleteJwStudentGraduationByCondition(jwStudentGraduation);
    }

    public void batchSaveJwStudentGraduation(List<JwStudentGraduation> jwStudentGraduations){
        jwStudentGraduationFeign.batchSaveJwStudentGraduation(jwStudentGraduations);
    }

    public void piLiangSaveJwStudentGraduation(JwStudentGraduation[] students){
        jwStudentGraduationFeign.piLiangSaveJwStudentGraduation(students);
    }

    public List<Integer> findJwStudentGraduationClassesByYear(JwStudentGraduation jwStudentGraduation) {
        return jwStudentGraduationFeign.findJwStudentGraduationClassesByYear(jwStudentGraduation);
    }

    public Workbook exportStudentGraduation(JwStudentGraduation jwStudentGraduation) {
        List<JwStudentGraduation> studentGraduationList = jwStudentGraduationFeign.findStudentGraduationListForExportStudentGraduationByCondition(jwStudentGraduation);
        return ExcelExportUtil.exportExcel(new ExportParams("毕业生列表","学生"),
                JwStudentGraduation.class, studentGraduationList);
    }
}
