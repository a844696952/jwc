package com.yice.edu.cn.osp.service.jw.eduEvaluation;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.yice.edu.cn.common.pojo.jw.eduEvaluation.CompareQuality;
import com.yice.edu.cn.common.pojo.jw.eduEvaluation.CompareQualityDetail;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.osp.feignClient.jw.eduEvaluation.CompareQualityFeign;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class CompareQualityService {
    @Autowired
    private CompareQualityFeign compareQualityFeign;

    public CompareQuality findCompareQualityById(String id) {
        return compareQualityFeign.findCompareQualityById(id);
    }

    public CompareQuality saveCompareQuality(CompareQuality compareQuality) {
        return compareQualityFeign.saveCompareQuality(compareQuality);
    }

    public List<CompareQuality> findCompareQualityListByCondition(CompareQuality compareQuality) {
        return compareQualityFeign.findCompareQualityListByCondition(compareQuality);
    }

    public CompareQuality findOneCompareQualityByCondition(CompareQuality compareQuality) {
        return compareQualityFeign.findOneCompareQualityByCondition(compareQuality);
    }

    public long findCompareQualityCountByCondition(CompareQuality compareQuality) {
        return compareQualityFeign.findCompareQualityCountByCondition(compareQuality);
    }

    public void updateCompareQuality(CompareQuality compareQuality) {
        compareQualityFeign.updateCompareQuality(compareQuality);
    }

    public void deleteCompareQuality(String id) {
        compareQualityFeign.deleteCompareQuality(id);
    }

    public void deleteCompareQualityByCondition(CompareQuality compareQuality) {
        compareQualityFeign.deleteCompareQualityByCondition(compareQuality);
    }

    public Workbook exportTemplate() {
        List<CompareQualityDetail> CompareQualityList = new ArrayList<>();
        return ExcelExportUtil.exportExcel(new ExportParams("综合素质成绩导入模板","综合素质"), CompareQualityDetail.class,CompareQualityList);
    }

    public Object uploadCompareQuality(MultipartFile file, String gradeName, String id) {
        Map<String,Object> result = new HashMap<>();
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        try (InputStream is = file.getInputStream()){
            List<CompareQualityDetail> list = ExcelImportUtil.importExcel(is,
                    CompareQualityDetail.class, params);
           List<CompareQualityDetail> list1= list.stream().filter(t->!isAllFieldNull(t)).collect(Collectors.toList());
            list1.forEach(c->{
                c.setCompareQualityId(id);
                c.setUploadGradeName(gradeName);
                c.setSchoolId(mySchoolId());
            });
            result=compareQualityFeign.batchSaveCompareQuality(list1);
        }catch (Exception e){

        }
        return result;

    }


    //判断该对象是否: 返回ture表示所有属性为null  返回false表示不是所有属性都是null
    public static boolean isAllFieldNull(Object obj){
        Class stuCla = (Class) obj.getClass();// 得到类对象
        Field[] fs = stuCla.getDeclaredFields();//得到属性集合
        boolean flag = true;
        try{
            for (Field f : fs) {//遍历属性
                f.setAccessible(true); // 设置属性是可以访问的(私有的也可以)
                Object val = f.get(obj);// 得到此属性的值
                if(val!=null&&!val.equals("null")) {//只要有1个属性不为空,那么就不是所有的属性值都为空
                    flag = false;
                    break;
                }
            }
        }catch (Exception e){
        }
        return flag;
    }

    public void deleteCompareQualityByIdList(List<String> idList) {
        compareQualityFeign.deleteCompareQualityByIdList(idList);
    }

    public void moveCompareQualityDataToHistory(List<String> classIdList) {
        compareQualityFeign.moveCompareQualityDataToHistory(classIdList);
    }
}
