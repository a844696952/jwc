package com.yice.edu.cn.osp.service.jw.electiveCourse;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.yice.edu.cn.common.pojo.jw.electiveCourse.ElectiveCourse;
import com.yice.edu.cn.common.pojo.jw.electiveCourse.ElectiveStudent;
import com.yice.edu.cn.osp.feignClient.jw.electiveCourse.ElectiveStudentFeign;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

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

    public Map batchSaveElectiveStudent(List<ElectiveStudent> electiveStudents) {
       return electiveStudentFeign.batchSaveElectiveStudent( electiveStudents);
    }

    public Workbook exportTemplate(String electiveId) {
        ElectiveStudent electiveStudent=new ElectiveStudent();
        electiveStudent.setElectiveId(electiveId);
        List<ElectiveStudent> electiveStudentList = findElectiveStudentListByCondition(electiveStudent);
        return ExcelExportUtil.exportExcel(new ExportParams("???????????????????????????","?????????"), ElectiveStudent.class,electiveStudentList);
    }

    public Object uploadElectiveStudent(MultipartFile file) {
        Map<String,Object> result = new HashMap<>();
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        try (InputStream is = file.getInputStream()){
            List<ElectiveStudent> list = ExcelImportUtil.importExcel(is,
                    ElectiveStudent.class, params);
            List<ElectiveStudent> list1= list.stream().filter(t->!isAllFieldNull(t)).collect(Collectors.toList());
            list1.forEach(c->{
                c.setSchoolId(mySchoolId());
            });
            result=electiveStudentFeign.uploadElectiveStudent(list1);
        }catch (Exception e){
            List<String> errors = new ArrayList<>();
            errors.add("????????????");
            result.put("code", "222");
            result.put("errors", errors);
        }
        return result;
    }


    //?????????????????????: ??????ture?????????????????????null  ??????false??????????????????????????????null
    public static boolean isAllFieldNull(Object obj){
        Class stuCla = (Class) obj.getClass();// ???????????????
        Field[] fs = stuCla.getDeclaredFields();//??????????????????
        boolean flag = true;
        try{
            for (Field f : fs) {//????????????
                f.setAccessible(true); // ??????????????????????????????(??????????????????)
                Object val = f.get(obj);// ?????????????????????
                if(val!=null&&!val.equals("null")) {//?????????1??????????????????,??????????????????????????????????????????
                    flag = false;
                    break;
                }
            }
        }catch (Exception e){
        }
        return flag;
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

    public List<ElectiveStudent> findSchoolYearStudentScoreListByCondition(ElectiveStudent electiveStudent) {
        return electiveStudentFeign.findSchoolYearStudentScoreListByCondition(electiveStudent);
    }

    public long findSchoolYearStudentScoreCountByCondition(ElectiveStudent electiveStudent) {
        return electiveStudentFeign.findSchoolYearStudentScoreCountByCondition(electiveStudent);
    }

    public List<ElectiveStudent> findElectiveStudentScoreListByCondition(ElectiveStudent electiveStudent) {
        return electiveStudentFeign.findElectiveStudentScoreListByCondition(electiveStudent);
    }

    public long findElectiveStudentScoreCountByCondition(ElectiveStudent electiveStudent) {
        return electiveStudentFeign.findElectiveStudentScoreCountByCondition(electiveStudent);
    }
}
