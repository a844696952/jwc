package com.yice.edu.cn.osp.service.xw.stuInAndOut;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.hutool.core.bean.BeanUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.xw.stuInAndOut.StuNotComeToSchool;
import com.yice.edu.cn.osp.feignClient.xw.stuInAndOut.StuNotComeToSchoolFeign;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.*;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class StuNotComeToSchoolService {
    @Autowired
    private StuNotComeToSchoolFeign stuNotComeToSchoolFeign;

    public StuNotComeToSchool findStuNotComeToSchoolById(String id) {
        return stuNotComeToSchoolFeign.findStuNotComeToSchoolById(id);
    }

    public StuNotComeToSchool saveStuNotComeToSchool(StuNotComeToSchool stuNotComeToSchool) {
        return stuNotComeToSchoolFeign.saveStuNotComeToSchool(stuNotComeToSchool);
    }

    public List<StuNotComeToSchool> findStuNotComeToSchoolListByCondition(StuNotComeToSchool stuNotComeToSchool) {
        return stuNotComeToSchoolFeign.findStuNotComeToSchoolListByCondition(stuNotComeToSchool);
    }

    public StuNotComeToSchool findOneStuNotComeToSchoolByCondition(StuNotComeToSchool stuNotComeToSchool) {
        return stuNotComeToSchoolFeign.findOneStuNotComeToSchoolByCondition(stuNotComeToSchool);
    }

    public long findStuNotComeToSchoolCountByCondition(StuNotComeToSchool stuNotComeToSchool) {
        return stuNotComeToSchoolFeign.findStuNotComeToSchoolCountByCondition(stuNotComeToSchool);
    }

    public void updateStuNotComeToSchool(StuNotComeToSchool stuNotComeToSchool) {
        stuNotComeToSchoolFeign.updateStuNotComeToSchool(stuNotComeToSchool);
    }

    public void deleteStuNotComeToSchool(String id) {
        stuNotComeToSchoolFeign.deleteStuNotComeToSchool(id);
    }

    public void deleteStuNotComeToSchoolByCondition(StuNotComeToSchool stuNotComeToSchool) {
        stuNotComeToSchoolFeign.deleteStuNotComeToSchoolByCondition(stuNotComeToSchool);
    }

    public Workbook exportStuNotComeToSchool(StuNotComeToSchool stuNotComeToSchool) {
        List<ExcelExportEntity> exportEntityList = new ArrayList<>();
        exportEntityList.add(new ExcelExportEntity("????????????", "studentName", 20));
        exportEntityList.add(new ExcelExportEntity("??????", "sex"));
        exportEntityList.add(new ExcelExportEntity("????????????", "gradeName"));
        exportEntityList.add(new ExcelExportEntity("??????", "classesNumber"));
        exportEntityList.add(new ExcelExportEntity("????????????", "classStartTime"));
        exportEntityList.add(new ExcelExportEntity("????????????", "classStartTime"));
        exportEntityList.add(new ExcelExportEntity("????????????", "createTime"));
        List<StuNotComeToSchool> tuNotComeToSchoolsList = stuNotComeToSchoolFeign.findStuNotComeToSchoolListByCondition (stuNotComeToSchool);
        List<Map<String, Object>> mapList = new ArrayList<>();
        //?????????map
        for (StuNotComeToSchool k : tuNotComeToSchoolsList) {
            StuNotComeToSchool kmap = new StuNotComeToSchool();
            kmap.setStudentName(k.getStudentName());
            kmap.setSex(k.getSex().equals(Constant.SEX_TYPE.MAN) ?"???":"???");
            kmap.setGradeName(k.getGradeName());
            kmap.setClassesNumber(k.getClassesNumber());
            kmap.setClassStartTime(k.getClassStartTime());
            kmap.setClassEndTime(k.getClassEndTime());
            kmap.setCreateTime(k.getCreateTime());
            Map<String, Object> stringObjectMap = BeanUtil.beanToMap(kmap);
            mapList.add(stringObjectMap);
        }
        return ExcelExportUtil.exportExcel(new ExportParams("?????????????????????", "???????????????"), exportEntityList, mapList);
    }

    public void makeNotComeData() {
        stuNotComeToSchoolFeign.makeNotComeData(mySchoolId());
    }
}
