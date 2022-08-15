package com.yice.edu.cn.bmp.service.electiveCourse;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.yice.edu.cn.common.pojo.jw.electiveCourse.ElectiveCourse;
import com.yice.edu.cn.common.pojo.jw.electiveCourse.ElectiveStudent;
import com.yice.edu.cn.bmp.feignClient.electiveCourse.ElectiveStudentFeign;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



@Service
public class ElectiveStudentService {
    @Autowired
    private ElectiveStudentFeign electiveStudentFeign;

    public ElectiveStudent findElectiveStudentById(String id) {
        return electiveStudentFeign.findElectiveStudentById(id);
    }

    public Map saveElectiveStudent(ElectiveStudent electiveStudent) {
        return electiveStudentFeign.saveElectiveStudent(electiveStudent);
    }

    public List<ElectiveStudent> findElectiveStudentListByCondition(ElectiveStudent electiveStudent) {
        return electiveStudentFeign.findElectiveStudentListByCondition(electiveStudent);
    }

    public ElectiveStudent findOneElectiveStudentByCondition(ElectiveStudent electiveStudent) {
        return electiveStudentFeign.findOneElectiveStudentByCondition(electiveStudent);
    }

    public long findElectiveStudentCountByCondition(ElectiveStudent electiveStudent) {
        return electiveStudentFeign.findElectiveStudentCountByCondition(electiveStudent);
    }

    public void updateElectiveStudent(ElectiveStudent electiveStudent) {
        electiveStudentFeign.updateElectiveStudent(electiveStudent);
    }

    public void deleteElectiveStudent(String id) {
        electiveStudentFeign.deleteElectiveStudent(id);
    }

    public void deleteElectiveStudentByCondition(ElectiveStudent electiveStudent) {
        electiveStudentFeign.deleteElectiveStudentByCondition(electiveStudent);
    }

    public void batchSaveElectiveStudent(List<ElectiveStudent> electiveStudents) {
        electiveStudentFeign.batchSaveElectiveStudent( electiveStudents);
    }



    public List<ElectiveStudent> findMyElectiveStudentListByCondition(ElectiveStudent electiveStudent) {
        return electiveStudentFeign.findMyElectiveStudentListByCondition(electiveStudent);
    }

    public long findMyElectiveStudentCountByCondition(ElectiveStudent electiveStudent) {
        return electiveStudentFeign.findMyElectiveStudentCountByCondition(electiveStudent);
    }

    public List<ElectiveCourse> getCanSelectCourseList(ElectiveStudent electiveStudent) {
        return electiveStudentFeign.getCanSelectCourseList(electiveStudent);
    }

    public List<ElectiveStudent> findSchoolYearElectiveStudentsByCondition(ElectiveStudent electiveStudent) {
        return electiveStudentFeign.findSchoolYearElectiveStudentsByCondition(electiveStudent);
    }

    public long findSchoolYearElectiveStudentsCountByCondition(ElectiveStudent electiveStudent) {
        return electiveStudentFeign.findSchoolYearElectiveStudentsCountByCondition(electiveStudent);
    }

    public List<ElectiveCourse> getAlreadySelectCourseList(ElectiveStudent electiveStudent) {
        return electiveStudentFeign.getAlreadySelectCourseList(electiveStudent);
    }

    public long getAlreadySelectCourseCount(ElectiveStudent electiveStudent) {
        return electiveStudentFeign.getAlreadySelectCourseCount(electiveStudent);
    }

    public List<ElectiveStudent> findSchoolYearStudentScoreListByCondition(ElectiveStudent electiveStudent) {
        return electiveStudentFeign.findSchoolYearStudentScoreListByCondition(electiveStudent);
    }

    public long findSchoolYearStudentScoreCountByCondition(ElectiveStudent electiveStudent) {
        return electiveStudentFeign.findSchoolYearStudentScoreCountByCondition(electiveStudent);
    }

    public long checkTimeRepeatCount(ElectiveStudent es) {
        return electiveStudentFeign.checkTimeRepeatCount(es);
    }
}
