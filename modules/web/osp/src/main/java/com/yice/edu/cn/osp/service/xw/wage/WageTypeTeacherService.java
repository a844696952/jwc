package com.yice.edu.cn.osp.service.xw.wage;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.xw.wage.WageType;
import com.yice.edu.cn.common.pojo.xw.wage.WageTypeTeacher;
import com.yice.edu.cn.common.pojo.xw.wage.WageValue;
import com.yice.edu.cn.osp.feignClient.jw.teacher.TeacherFeign;
import com.yice.edu.cn.osp.feignClient.xw.wage.WageTypeFeign;
import com.yice.edu.cn.osp.feignClient.xw.wage.WageTypeTeacherFeign;
import com.yice.edu.cn.osp.feignClient.xw.wage.WageValueFeign;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.InputStream;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class WageTypeTeacherService {
    @Autowired
    private WageTypeTeacherFeign wageTypeTeacherFeign;
    @Autowired
    private WageTypeFeign wageTypeFeign;
    @Autowired
    private TeacherFeign teacherFeign;
    @Autowired
    private WageValueFeign wageValueFeign;
    public WageTypeTeacher findWageTypeTeacherById(String id) {
        return wageTypeTeacherFeign.findWageTypeTeacherById(id);
    }

    public WageTypeTeacher saveWageTypeTeacher(WageTypeTeacher wageTypeTeacher) {
        return wageTypeTeacherFeign.saveWageTypeTeacher(wageTypeTeacher);
    }

    public List<WageTypeTeacher> findWageTypeTeacherListByCondition(WageTypeTeacher wageTypeTeacher) {
        return wageTypeTeacherFeign.findWageTypeTeacherListByCondition(wageTypeTeacher);
    }
    public List<WageTypeTeacher> findWageTypeTeacherListByWorkNum(WageTypeTeacher wageTypeTeacher) {
        return wageTypeTeacherFeign.findWageTypeTeacherListByWorkNum(wageTypeTeacher);
    }

    public WageTypeTeacher findOneWageTypeTeacherByCondition(WageTypeTeacher wageTypeTeacher) {
        return wageTypeTeacherFeign.findOneWageTypeTeacherByCondition(wageTypeTeacher);
    }

    public long findWageTypeTeacherCountByCondition(WageTypeTeacher wageTypeTeacher) {
        return wageTypeTeacherFeign.findWageTypeTeacherCountByCondition(wageTypeTeacher);
    }

    public void updateWageTypeTeacher(WageTypeTeacher wageTypeTeacher) {
        wageTypeTeacherFeign.updateWageTypeTeacher(wageTypeTeacher);
    }

    public void deleteWageTypeTeacher(String id) {
        wageTypeTeacherFeign.deleteWageTypeTeacher(id);
    }

    public void deleteWageTypeTeacherByCondition(WageTypeTeacher wageTypeTeacher) {
        wageTypeTeacherFeign.deleteWageTypeTeacherByCondition(wageTypeTeacher);
    }

    public void saveWageTypeValue(WageTypeTeacher wageTypeTeacher) {
        wageTypeTeacherFeign.saveWageTypeValue(wageTypeTeacher);
    }

    public List<Map<String, Object>> findWageValueByTypeId(Map<String,Object>wageTypeTeacherMap) {
        return wageTypeTeacherFeign.findWageValueByTypeId(wageTypeTeacherMap);
    }

    public List<Map<String, String>> findWageValueByRecordId(Map<String,Object>wageTypeRecordMap) {
        return wageTypeTeacherFeign.findWageValueByRecordId(wageTypeRecordMap);
    }

    public List<Map<String, String>> findWageValueByTeacherId(Map<String,Object>wageTypeTeacherMap) {
        return wageTypeTeacherFeign.findWageValueByTeacherId(wageTypeTeacherMap);
    }


    public List<WageTypeTeacher> findWageAttributeListByRecordId(WageTypeTeacher wageTypeTeacher) {
        return wageTypeTeacherFeign.findWageAttributeListByRecordId(wageTypeTeacher);
    }

    public List<WageTypeTeacher> findWageAttributeListByTeacherId(WageTypeTeacher wageTypeTeacher) {
        return wageTypeTeacherFeign.findWageAttributeListByTeacherId(wageTypeTeacher);
    }
    public List<WageTypeTeacher> findWageAttributeNameByTeacherId(WageTypeTeacher wageTypeTeacher) {
        return wageTypeTeacherFeign.findWageAttributeNameByTeacherId(wageTypeTeacher);
    }

    public void updateWageTypeValue(WageTypeTeacher wageTypeTeacher) {
        wageTypeTeacherFeign.updateWageTypeValue(wageTypeTeacher);
    }

    public Workbook exportWage(WageTypeTeacher wageTypeTeacher){
        WageType wageType=new WageType();
        wageType.setId(wageTypeTeacher.getWageTypeId());
        wageType.setSchoolId(mySchoolId());
        List<WageType> wageAttributeList = wageTypeFeign.findWageAttributeListByTypeId(wageType);
        List<ExcelExportEntity> exportEntityList=new ArrayList<>();
        exportEntityList.add(new ExcelExportEntity("工号","work_number",20));
        exportEntityList.add(new ExcelExportEntity("职工姓名","name"));
        exportEntityList.add(new ExcelExportEntity("工资月份","salary_time"));
        for (int i = 0; i <wageAttributeList.size() ; i++) {
            exportEntityList.add(new ExcelExportEntity(wageAttributeList.get(i).getAttributeName(),wageAttributeList.get(i).getWageAttributeId()));
        }
        exportEntityList.add(new ExcelExportEntity("发放时间","release_time",30));
        exportEntityList.add(new ExcelExportEntity("发放状态","release_state"));
        Map<String,Object>wageTypeMap=new HashMap<>();
        wageTypeMap.put("id",wageTypeTeacher.getWageTypeId());
        wageTypeMap.put("wageTypeList",wageAttributeList);
        wageTypeMap.put("pager",wageTypeTeacher.getPager());
        List<Map<String, Object>> wageValueList = wageTypeTeacherFeign.findWageValueByTypeId(wageTypeMap);
        for (int i = 0; i <wageValueList.size() ; i++) {
            if( wageValueList.get(i).get("release_state").equals("0")){
                wageValueList.get(i).put("release_state","未发放");
            }else{
                wageValueList.get(i).put("release_state","已发放");
            }
        }
        return ExcelExportUtil.exportExcel(new ExportParams("工资列表","工资"),exportEntityList,wageValueList);
    }
    public Workbook exportWageTemplate(WageTypeTeacher wageTypeTeacher){
        WageType wageType=new WageType();
        wageType.setId(wageTypeTeacher.getWageTypeId());
        wageType.setSchoolId(mySchoolId());
        List<WageType> wageAttributeList = wageTypeFeign.findWageAttributeListByTypeId(wageType);
        List<ExcelExportEntity> exportEntityList=new ArrayList<>();
        exportEntityList.add(new ExcelExportEntity("工号","work_number"));
        exportEntityList.add(new ExcelExportEntity("职工姓名","name"));
        exportEntityList.add(new ExcelExportEntity("工资月份","salary_time"));
        for (int i = 0; i <wageAttributeList.size() ; i++) {
            exportEntityList.add(new ExcelExportEntity(wageAttributeList.get(i).getAttributeName(),wageAttributeList.get(i).getWageAttributeId()));
        }
       /* exportEntityList.add(new ExcelExportEntity("发放时间","release_time"));*/
      /*  exportEntityList.add(new ExcelExportEntity("发放状态","release_state"));*/
        List<Map<String,Object>>wageValueList=new ArrayList<>();
        return ExcelExportUtil.exportExcel(new ExportParams("工资列表","工资"),exportEntityList,wageValueList);
    }

   public Map<String,Object> uploadWage(MultipartFile file,String id){
       Map<String,Object> result = new HashMap<>();
       ImportParams params = new ImportParams();
       params.setTitleRows(1);
       params.setHeadRows(1);
       try (InputStream is = file.getInputStream()){
           List<Map<String,Object>> list = ExcelImportUtil.importExcel(is, Map.class, params);
           list = list.stream().filter(stringObjectMap -> !isAllFieldNull(stringObjectMap)).collect(Collectors.toList());
           if(list == null||list.size()<=0){
               result.put("size",0);
               result.put("code","201");
               result.put("error","请勿上传空文件");
               return result;
           }
           if(list.size()>10000){
               result.put("code","202");
               result.put("error","超出导入上限：10000条");
               return result;
           }
           List<String> errors = new ArrayList<>();
           String teaNumber="";
           String name="";
           WageTypeTeacher wageTypeTeacher=null;
           WageType wageType=null;
           Teacher teacher=null;
           WageValue wageValue=null;

           wageType=new WageType();
           wageType.setId(id);
           wageType.setSchoolId(mySchoolId());
           List<WageType>wageTypeList=wageTypeFeign.findWageAttributeListByTypeId(wageType);

           wageTypeTeacher=new WageTypeTeacher();
           wageTypeTeacher.setSchoolId(mySchoolId());
           List<WageTypeTeacher> wageTypeTeacherList = wageTypeTeacherFeign.findWageTypeTeacherListByCondition(wageTypeTeacher);

           teacher=new Teacher();
           teacher.setSchoolId(mySchoolId());
           List<Teacher> teacherList = teacherFeign.findTeacherListByCondition(teacher);

           List<WageTypeTeacher>wageList=new ArrayList<>();
           for (int i = 0; i < list.size(); i++) {
               Pattern pattern;
               int a = i + 1;//获取当前所在条数
               StringBuffer error = new StringBuffer();//异常提示
               if (list.get(0).keySet().size() - 3 < wageTypeList.size()) {
                   error.append("导入模板有误  ");
               }

               wageTypeTeacher=new WageTypeTeacher();
               Map<String,Object>valueMap=new HashMap<>();
               for (Map.Entry<String, Object> entry : list.get(i).entrySet()) {
                   boolean flag = false;
                   boolean flag1 = false;
                   boolean flag2 = false;
                   if(entry.getKey().equals("工号")){
                       if(StringUtils.isNotEmpty(entry.getValue() == null ? null : entry.getValue().toString())) {
                          teaNumber=entry.getValue().toString();
                          wageTypeTeacher.setWorkNumber(teaNumber);
                       }else {
                           error.append("工号不能为空；");
                       }
                       continue;
                   }
                   if(entry.getKey().equals("职工姓名")){
                       if(StringUtils.isNotEmpty(entry.getValue() == null ? null : entry.getValue().toString())) {
                       name=entry.getValue().toString();
                       wageTypeTeacher.setName(name);
                       }else{
                           error.append("职工姓名不能为空；");
                       }
                       for (Teacher teachers:teacherList) {
                           if(teaNumber.equals(teachers.getWorkNumber())&&name.equals(teachers.getName())){
                               flag2=true;
                           }
                       }
                       if(!flag2){
                           error.append("该职工不存在；");
                       }
                       continue;
                   }


                   if(entry.getKey().equals("工资月份")){
                       if(StringUtils.isNotEmpty(entry.getValue() == null ? null : entry.getValue().toString())) {
                           pattern = Pattern.compile("\\d{4}-(0[1-9]|1[0-2])");
                           if (!pattern.matcher(entry.getValue().toString()).matches()) {
                               error.append("工资月份格式：2018-01（文本类型）；");
                           }else {
                               wageTypeTeacher.setSalaryTime(entry.getValue().toString());
                           }
                       }else {
                           error.append("工资月份格式：2018-01（文本类型）；");
                       }
                       for (WageTypeTeacher teacherWage:wageTypeTeacherList) {
                           if(teaNumber.equals(teacherWage.getWorkNumber())&&name.equals(teacherWage.getName())&&entry.getValue().equals(teacherWage.getSalaryTime())){
                               flag1=true;
                           }
                       }
                       if(flag1){
                           error.append("工号已存在；");
                       }
                       continue;
                   }


                   for (int j = 0; j < wageTypeList.size(); j++) {
                       if(wageTypeList.get(j).getAttributeName().equals(entry.getKey())){
                           flag=true;
                           if(StringUtils.isNotEmpty(entry.getValue() == null ? null : entry.getValue().toString())) {
                               pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");
                               if(!pattern.matcher(entry.getValue().toString()).matches()){
                                   error.append(entry.getKey()+"格式错误(最多保留小数点两位)");
                               }else{
                                   valueMap.put(wageTypeList.get(j).getWageAttributeId(), entry.getValue().toString());
                                   wageTypeTeacher.setPropmap(valueMap);
                               }
                           }else{
                               error.append(entry.getKey()+"不能为空；");
                           /*    valueMap.put(wageTypeList.get(j).getWageAttributeId(), "");
                               wageTypeTeacher.setPropmap(valueMap);*/
                           }
                       }
                   }
                   if(!flag){
                       error.append(entry.getKey()+"属性不存在;  ");
                   }
                   wageTypeTeacher.setWageTypeId(id);
               }
               wageList.add(wageTypeTeacher);
               //异常添加list
               if (error.length() > 0) {
                   error.insert(0, "第" + a + "条:");
                   errors.add(error.toString());
               }
           }
           if (errors.size() <= 0) {
               wageTypeTeacherFeign.batchSaveWageTypeTeacher(wageList.stream().map(s->{
                   s.setSchoolId(mySchoolId());
                   return s;
               }).collect(Collectors.toList()));
               result.put("code", "200");
           } else {
               result.put("code", "222");
               result.put("errors", errors);
           }
       }catch (Exception e){
           System.out.println(e);
       }

       return result;
   }


    public void deleteWageValueByRecordId(String id) {
        wageTypeTeacherFeign.deleteWageValueByRecordId(id);
    }

    public void updateWageTypeTeacherReleaseState(WageTypeTeacher wageTypeTeacher) {
        wageTypeTeacherFeign.updateWageTypeTeacherReleaseState(wageTypeTeacher);
    }

    public long findWageValueByTypeIdCount(WageTypeTeacher wageTypeTeacher) {
        return wageTypeTeacherFeign.findWageValueByTypeIdCount(wageTypeTeacher);
    }

    private boolean isAllFieldNull(Map<String,Object> map){
        boolean allFiedNull = true;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if(entry.getValue() != null && !StringUtils.isBlank(entry.getValue() + "")){
                allFiedNull=false;
                break;
            }
        }
        return  allFiedNull;
    }
}
