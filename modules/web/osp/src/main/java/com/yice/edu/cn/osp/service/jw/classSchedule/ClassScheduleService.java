package com.yice.edu.cn.osp.service.jw.classSchedule;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.hutool.core.bean.BeanUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.classSchedule.*;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.osp.feignClient.dd.DdFeign;
import com.yice.edu.cn.osp.feignClient.jw.classSchedule.ClassScheduleFeign;
import com.yice.edu.cn.osp.feignClient.jw.classes.JwClassesFeign;
import com.yice.edu.cn.osp.feignClient.jw.jwCourse.JwCourseFeign;
import com.yice.edu.cn.osp.feignClient.jw.teacher.TeacherClassesFeign;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class ClassScheduleService {
    @Autowired
    private ClassScheduleFeign classScheduleFeign;

    @Autowired
    private JwClassesFeign jwClassesFeign;

    @Autowired
    private JwCourseFeign jwCourseFeign;

    @Autowired
    private TeacherClassesFeign teacherClassesFeign;

    @Autowired
    private DdFeign ddFeign;

    @Autowired
    private ClassScheduleInitService classScheduleInitService;

    public ClassSchedule findClassScheduleById(@PathVariable String id) {
        return classScheduleFeign.findClassScheduleById(id);
    }

    public ClassSchedule saveClassSchedule(ClassSchedule classSchedule) {
        return classScheduleFeign.saveClassSchedule(classSchedule);
    }

    public List<ClassSchedule> findClassScheduleListByCondition(ClassSchedule classSchedule) {
        return classScheduleFeign.findClassScheduleListByCondition(classSchedule);
    }

    public ClassSchedule findOneClassScheduleByCondition(ClassSchedule classSchedule) {
        return classScheduleFeign.findOneClassScheduleByCondition(classSchedule);
    }

    public long findClassScheduleCountByCondition(ClassSchedule classSchedule) {
        return classScheduleFeign.findClassScheduleCountByCondition(classSchedule);
    }

    public void updateClassSchedule(ClassSchedule classSchedule) {
        classScheduleFeign.updateClassSchedule(classSchedule);
    }

    public void deleteClassSchedule(String id) {
        classScheduleFeign.deleteClassSchedule(id);
    }

    public void deleteClassScheduleByCondition(ClassSchedule classSchedule) {
        classScheduleFeign.deleteClassScheduleByCondition(classSchedule);
    }

    public List<Dd> findGradesBySchoolId(String id,String scheduleId) {
        return classScheduleFeign.findGradesBySchoolId(id,scheduleId);
    }


    //????????????id???????????????????????????????????????
    public List<Map<String, String>> findCourseTeacherByClass(@RequestBody TeacherClasses teacherClasses) {
        return classScheduleFeign.findCourseTeacherByClass(teacherClasses);
    }

    //????????????id??????????????????
    public JwClasses findJwClassesById(@PathVariable String id) {
        return jwClassesFeign.findJwClassesById(id);
    }


    //????????????id???????????????????????????
    public JwCourse getJwCourseOne(@PathVariable("id") String id) {
        JwCourse jwCourse = jwCourseFeign.findJwCourseById(id);
        return jwCourse;
    }

    //???????????????????????????????????????
   /* public List<JwSpace> getJwSpaceAll(String typeId) {
        JwSpace jwSpace = new JwSpace();
        jwSpace.setSchoolId(LoginInterceptor.mySchoolId());
        jwSpace.setTypeId(typeId);
        List<JwSpace> jwSpaceList = jwSpaceFeign.findJwSpaceListByCondition(jwSpace);
        return jwSpaceList;
    }*/



    //?????????????????????
    public Workbook exportClassSchedule(ClassSchedule classSchedule) {
        //???????????????????????????
        List<ClassSchedule> data = classScheduleFeign.findClassScheduleListByConditions(classSchedule);
        ClassScheduleInit classScheduleInit = new ClassScheduleInit();
        classScheduleInit.setSchoolId(LoginInterceptor.mySchoolId());
        long count = classScheduleInitService.findClassScheduleInitCountByCondition(classScheduleInit);
        if(count==0){
            count=Constant.CLASS_SCHEDULE.CLASS_SCHEDULE_NUMBER;
        }
        List<List<ClassSchedule>> list = new ArrayList();
        boolean flag = false;
        for (int i = 1; i <= count; i++) {
            List<ClassSchedule> clist = new ArrayList<>();
            for (int j = 1; j <= 7; j++) {
                ClassSchedule cs = null;
                for (ClassSchedule schedule : data) {
                    if (schedule.getWeekId() == j && schedule.getNumberId() == i) {
                        cs = schedule;
                    }else if(schedule.getWeekId()>5){
                        flag = true;
                    }
                }
                clist.add(cs);
            }
            list.add(clist);
        }


        /*if(flag){*/
            //????????????????????????????????????Schedule???????????????
            List<Map<String,Object>> classScheduleList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                Schedule schedule = new Schedule();
                schedule.setNum("???" + (i + 1) + "???");
                for (int j = 0; j < list.get(0).size(); j++) {
                    ClassSchedule schedule1 = list.get(i).get(j);
                    if (j == 0) {
                        if (schedule1 != null) {
                            /*
                             * if(schedule1.getCourseName()!=null&&schedule1.getAcademicBuildingName()!=null&&schedule1!=null&&schedule1.getSpaceName()!=null&&schedule1.getTeacherName()!=null){
                                schedule.setOne(schedule1.getCourseName() + "\r\n" + schedule1.getTeacherName() + "\r\n" + schedule1.getAcademicBuildingName()+" "+schedule1.getSpaceName());
                            }else if(schedule1.getAcademicBuildingName()!=null&&schedule1.getSpaceName()!=null&&schedule1.getTeacherName()!=null){
                                schedule.setOne( schedule1.getTeacherName() + "\r\n" + schedule1.getAcademicBuildingName()+" "+schedule1.getSpaceName());
                            }else if(schedule1.getCourseName()!=null&&schedule1.getTeacherName()!=null&&schedule1.getSpaceName()!=null){
                                schedule.setOne( schedule1.getCourseName() + "\r\n" + schedule1.getTeacherName()+"\r\n"+schedule1.getSpaceName());
                            }else
                            * */
                            if(schedule1.getCourseName()!=null&&schedule1.getTeacherName()!=null){
                                schedule.setOne(schedule1.getCourseName() + "\r\n" + schedule1.getTeacherName());
                            }else{
                                schedule.setOne(null);
                            }
                        } else {
                            schedule.setOne(null);
                        }
                    } else if (j == 1) {
                        if (schedule1 != null) {
                            /*
                              if(schedule1.getCourseName()!=null&&schedule1.getAcademicBuildingName()!=null&&schedule1!=null&&schedule1.getSpaceName()!=null&&schedule1.getTeacherName()!=null){
                                                              schedule.setTwo(schedule1.getCourseName() + "\r\n" + schedule1.getTeacherName() + "\r\n" + schedule1.getAcademicBuildingName()+" "+schedule1.getSpaceName());
                                                          }else if(schedule1.getAcademicBuildingName()!=null&&schedule1.getSpaceName()!=null&&schedule1.getTeacherName()!=null){
                                                              schedule.setTwo( schedule1.getTeacherName() + "\r\n" + schedule1.getAcademicBuildingName()+" "+schedule1.getSpaceName());
                                                          }else if(schedule1.getCourseName()!=null&&schedule1.getTeacherName()!=null&&schedule1.getSpaceName()!=null){
                                                              schedule.setTwo( schedule1.getCourseName() + "\r\n" + schedule1.getTeacherName()+"\r\n"+schedule1.getSpaceName());
                                                          }else
                             */
                            if(schedule1.getCourseName()!=null&&schedule1.getTeacherName()!=null){
                                schedule.setTwo(schedule1.getCourseName() + "\r\n" +schedule1.getTeacherName());
                            }else{
                                schedule.setTwo(null);
                            }
                        } else {
                            schedule.setTwo(null);
                        }
                    } else if (j == 2) {
                        if (schedule1 != null) {
                            /*
                            if(schedule1.getCourseName()!=null&&schedule1.getAcademicBuildingName()!=null&&schedule1!=null&&schedule1.getSpaceName()!=null&&schedule1.getTeacherName()!=null){
                                schedule.setThree(schedule1.getCourseName() + "\r\n" + schedule1.getTeacherName() + "\r\n" + schedule1.getAcademicBuildingName()+" "+schedule1.getSpaceName());
                            }else if(schedule1.getAcademicBuildingName()!=null&&schedule1.getSpaceName()!=null&&schedule1.getTeacherName()!=null){
                                schedule.setThree( schedule1.getTeacherName() + "\r\n" + schedule1.getAcademicBuildingName()+" "+schedule1.getSpaceName());
                            }else if(schedule1.getCourseName()!=null&&schedule1.getTeacherName()!=null&&schedule1.getSpaceName()!=null){
                                schedule.setThree( schedule1.getCourseName() + "\r\n" + schedule1.getTeacherName()+"\r\n"+schedule1.getSpaceName());
                            }else
                             */
                            if(schedule1.getCourseName()!=null&&schedule1.getTeacherName()!=null){
                                schedule.setThree(schedule1.getCourseName() + "\r\n" + schedule1.getTeacherName());
                            }else{
                                schedule.setThree(null);
                            }
                        } else {
                            schedule.setThree(null);
                        }
                    } else if (j == 3) {
                        if (schedule1 != null) {
                            /*
                            if(schedule1.getCourseName()!=null&&schedule1.getAcademicBuildingName()!=null&&schedule1!=null&&schedule1.getSpaceName()!=null&&schedule1.getTeacherName()!=null){
                                schedule.setFour(schedule1.getCourseName() + "\r\n" + schedule1.getTeacherName() + "\r\n" + schedule1.getAcademicBuildingName()+" "+schedule1.getSpaceName());
                            }else if(schedule1.getAcademicBuildingName()!=null&&schedule1.getSpaceName()!=null&&schedule1.getTeacherName()!=null){
                                schedule.setFour( schedule1.getTeacherName() + "\r\n" + schedule1.getAcademicBuildingName()+" "+schedule1.getSpaceName());
                            }else if(schedule1.getCourseName()!=null&&schedule1.getTeacherName()!=null&&schedule1.getSpaceName()!=null){
                                schedule.setFour( schedule1.getCourseName() + "\r\n" + schedule1.getTeacherName()+"\r\n"+schedule1.getSpaceName());
                            }else
                             */
                            if(schedule1.getCourseName()!=null&&schedule1.getTeacherName()!=null){
                                schedule.setFour(schedule1.getCourseName() + "\r\n" + schedule1.getTeacherName());
                            }else{
                                schedule.setFour(null);
                            }
                        } else {
                            schedule.setFour(null);
                        }
                    } else if (j == 4) {
                        if (schedule1!= null) {
                            /*
                            if(schedule1.getCourseName()!=null&&schedule1.getAcademicBuildingName()!=null&&schedule1!=null&&schedule1.getSpaceName()!=null&&schedule1.getTeacherName()!=null){
                                schedule.setFive(schedule1.getCourseName() + "\r\n" + schedule1.getTeacherName() + "\r\n" + schedule1.getAcademicBuildingName()+" "+schedule1.getSpaceName());
                            }else if(schedule1.getAcademicBuildingName()!=null&&schedule1.getSpaceName()!=null&&schedule1.getTeacherName()!=null){
                                schedule.setFive( schedule1.getTeacherName() + "\r\n" + schedule1.getAcademicBuildingName()+" "+schedule1.getSpaceName());
                            }else if(schedule1.getCourseName()!=null&&schedule1.getTeacherName()!=null&&schedule1.getSpaceName()!=null){
                                schedule.setFive( schedule1.getCourseName() + "\r\n" + schedule1.getTeacherName()+"\r\n"+schedule1.getSpaceName());
                            }else
                             */
                            if(schedule1.getCourseName()!=null&&schedule1.getTeacherName()!=null){
                                schedule.setFive(schedule1.getCourseName() + "\r\n" + schedule1.getTeacherName());
                            }else {
                                schedule.setFive(null);
                            }
                        } else {
                            schedule.setFive(null);
                        }
                    } else if (j == 5) {
                        if (schedule1!= null) {
                            /*
                            if(schedule1.getCourseName()!=null&&schedule1.getAcademicBuildingName()!=null&&schedule1!=null&&schedule1.getSpaceName()!=null&&schedule1.getTeacherName()!=null){
                                schedule.setSix(schedule1.getCourseName() + "\r\n" + schedule1.getTeacherName() + "\r\n" + schedule1.getAcademicBuildingName()+" "+schedule1.getSpaceName());
                            }else if(schedule1.getAcademicBuildingName()!=null&&schedule1.getSpaceName()!=null&&schedule1.getTeacherName()!=null){
                                schedule.setSix( schedule1.getTeacherName() + "\r\n" + schedule1.getAcademicBuildingName()+" "+schedule1.getSpaceName());
                            }else if(schedule1.getCourseName()!=null&&schedule1.getTeacherName()!=null&&schedule1.getSpaceName()!=null){
                                schedule.setSix( schedule1.getCourseName() + "\r\n" + schedule1.getTeacherName()+"\r\n"+schedule1.getSpaceName());
                            }else
                             */
                            if(schedule1.getCourseName()!=null&&schedule1.getTeacherName()!=null){
                                schedule.setSix(schedule1.getCourseName() + "\r\n" + schedule1.getTeacherName());
                            }else{
                                schedule.setSix(null);
                            }
                        } else {
                            schedule.setSix(null);
                        }
                    } else if (j == 6) {
                        if (schedule1 != null) {
                            /*
                            if(schedule1.getCourseName()!=null&&schedule1.getAcademicBuildingName()!=null&&schedule1!=null&&schedule1.getSpaceName()!=null&&schedule1.getTeacherName()!=null){
                                schedule.setSeven(schedule1.getCourseName() + "\r\n" + schedule1.getTeacherName() + "\r\n" + schedule1.getAcademicBuildingName()+" "+schedule1.getSpaceName());
                            }else if(schedule1.getAcademicBuildingName()!=null&&schedule1.getSpaceName()!=null&&schedule1.getTeacherName()!=null){
                                schedule.setSeven( schedule1.getTeacherName() + "\r\n" + schedule1.getAcademicBuildingName()+" "+schedule1.getSpaceName());
                            }else if(schedule1.getCourseName()!=null&&schedule1.getTeacherName()!=null&&schedule1.getSpaceName()!=null){
                                schedule.setSeven( schedule1.getCourseName() + "\r\n" + schedule1.getTeacherName()+"\r\n"+schedule1.getSpaceName());
                            }else
                             */
                             if(schedule1.getCourseName()!=null&&schedule1.getTeacherName()!=null){
                                schedule.setSeven(schedule1.getCourseName() + "\r\n" + schedule1.getTeacherName());
                            }else {
                                schedule.setSeven(null);
                            }
                        } else {
                            schedule.setSeven(null);
                        }
                    }
                }
                Map<String, Object> stringObjectMap = BeanUtil.beanToMap(schedule);
                classScheduleList.add(stringObjectMap);
            }

            List<ExcelExportEntity> excelExportEntitieList = new ArrayList<>();
            ExcelExportEntity excelExportEntity = new ExcelExportEntity("??????","num",20);
            excelExportEntitieList.add(excelExportEntity);
            excelExportEntitieList.add(new ExcelExportEntity("??????","one",20));
            excelExportEntitieList.add(new ExcelExportEntity("??????","two",20));
            excelExportEntitieList.add(new ExcelExportEntity("??????","three",20));
            excelExportEntitieList.add(new ExcelExportEntity("??????","four",20));
            excelExportEntitieList.add(new ExcelExportEntity("??????","five",20));
            if(flag){
                excelExportEntitieList.add(new ExcelExportEntity("??????","six",20));
                excelExportEntitieList.add(new ExcelExportEntity("??????","seven",20));
            }

            excelExportEntitieList.forEach(f->{
                f.setHeight(20);
                f.setWrap(true);
            });


        return ExcelExportUtil.exportExcel(new ExportParams("?????????", data.get(0).getGradeName() + "(" + data.get(0).getClassName() + ")???"), excelExportEntitieList, classScheduleList);
    }

    public List<List<ClassSchedule>> findList(ClassSchedule classSchedule) {
        return classScheduleFeign.findList(classSchedule);
    }

    public List<List<ClassSchedule>> getTeacherThisWeekClassSchedule(ClassSchedule classSchedule){
        return classScheduleFeign.getTeacherThisWeekClassSchedule(classSchedule);
    }



    //????????????id????????????????????????????????????
    public Dd findDdById(String id) {
        return ddFeign.findDdById(id);
    }

    public void updateorfind(ClassSchedule classSchedules) {
        classScheduleFeign.updateorfind(classSchedules);
    }

    public void deletebatchdelete(ClassSchedule classSchedule) {
        classScheduleFeign.deletebatchdelete(classSchedule);
    }


    public List<ClassSchedule> findClassScheduleListByConditions(ClassSchedule classSchedule) {
        return classScheduleFeign.findClassScheduleListByConditions(classSchedule);
    }

    public  List<ClassSchedule> todayClassScheduleList(ClassSchedule classSchedule){
        return classScheduleFeign.todayClassScheduleList(classSchedule);
    }

    //????????????-????????????
    public Workbook exportTemplate(){
        List<Map<String,Object>> classScheduleList = new ArrayList<>();
        ClassScheduleInit classScheduleInit = new ClassScheduleInit();
        classScheduleInit.setSchoolId(LoginInterceptor.mySchoolId());
        long count = classScheduleInitService.findClassScheduleInitCountByCondition(classScheduleInit);
        if(count==0){
            count = Constant.CLASS_SCHEDULE.CLASS_SCHEDULE_NUMBER;
        }
        for(int i=0;i<count;i++){
            Schedule schedule = new Schedule();
            schedule.setNum("???"+(i+1)+"???");
            if(i==0){
                schedule.setOne("????????????\r\n?????????");
                schedule.setTwo("????????????,?????????");
                schedule.setSeven("????????????????????????XXX(xx)???\r\n???:?????????6??????,?????????(12)???\r\n????????????????????????????????????");
            }
            Map<String,Object> map = BeanUtil.beanToMap(schedule);
            classScheduleList.add(map);
        }
        List<ExcelExportEntity> excelExportEntitieList = new ArrayList<>();
        ExcelExportEntity excelExportEntity = new ExcelExportEntity("??????","num",20);
        excelExportEntitieList.add(excelExportEntity);
        excelExportEntitieList.add(new ExcelExportEntity("??????","one",20));
        excelExportEntitieList.add(new ExcelExportEntity("??????","two",20));
        excelExportEntitieList.add(new ExcelExportEntity("??????","three",20));
        excelExportEntitieList.add(new ExcelExportEntity("??????","four",20));
        excelExportEntitieList.add(new ExcelExportEntity("??????","five",20));
        excelExportEntitieList.add(new ExcelExportEntity("??????","six",20));
        excelExportEntitieList.add(new ExcelExportEntity("??????","seven",30));

        excelExportEntitieList.forEach(f->{
            f.setHeight(20);
            f.setWrap(true);
        });

        return ExcelExportUtil.exportExcel(new ExportParams("?????????????????????","?????????"),
                excelExportEntitieList,classScheduleList);
    }

    //????????????-?????????????????????????????????????????????-??????
    public Workbook exportTemplateNew(JwClasses jwClasses){
        jwClasses.setSchoolId(mySchoolId());
        Pager pager = new Pager();
        pager.setSortField("gradeId,number");
        pager.setSortOrder("asc,asc");
        pager.setPaging(false);
        jwClasses.setPager(pager);
        List<JwClasses> list = jwClassesFeign.findClassListByJwClassesKong(jwClasses);
        List<Map<String,Object>> classScheduleList = new ArrayList<>();
        ClassScheduleInit classScheduleInit = new ClassScheduleInit();
        classScheduleInit.setSchoolId(LoginInterceptor.mySchoolId());
        List<ClassScheduleInit> listClassScheduleInitBySchool = classScheduleInitService.findListClassScheduleInitBySchool(classScheduleInit);
        long count = listClassScheduleInitBySchool.size();
        List<Map<String,Object>> map1 = new ArrayList<>();
        Map<String,Object> map2 = new HashMap<>();
        for(int i =1;i<=count;i++){
            map2.put(i+"","");
        }
        map1.add(map2);
        list.stream().forEach(f->{
            Map<String,Object> map = new HashMap<>();
            map.put("num",f.getGradeName()+"???"+f.getNumber()+"??????");
            map.put("one",map1);
            map.put("two",map1);
            map.put("three",map1);
            map.put("four",map1);
            map.put("five",map1);
            map.put("six",map1);
            classScheduleList.add(map);
        });

        List<ExcelExportEntity> excelExportEntitieList = new ArrayList<>();
        List<ExcelExportEntity> weekNumber = new ArrayList<>();
        for(int i =1;i<=count;i++){
            ExcelExportEntity exportEntity = new ExcelExportEntity(i+"",i+"");
            exportEntity.setNeedMerge(true);
            exportEntity.setOrderNum(i);
            weekNumber.add(exportEntity);
        }

        ExcelExportEntity excelExportEntity = new ExcelExportEntity("????????????","num",20);
        excelExportEntity.setNeedMerge(true);
        ExcelExportEntity excelExportEntity1 = new ExcelExportEntity("??????", "one");
        excelExportEntity1.setNeedMerge(true);
        excelExportEntity1.setList(weekNumber);
        ExcelExportEntity excelExportEntity2 = new ExcelExportEntity("??????", "two");
        excelExportEntity2.setNeedMerge(true);
        excelExportEntity2.setList(weekNumber);
        ExcelExportEntity excelExportEntity3 = new ExcelExportEntity("??????", "three");
        excelExportEntity3.setNeedMerge(true);
        excelExportEntity3.setList(weekNumber);
        ExcelExportEntity excelExportEntity4 = new ExcelExportEntity("??????", "four");
        excelExportEntity4.setNeedMerge(true);
        excelExportEntity4.setList(weekNumber);
        ExcelExportEntity excelExportEntity5 = new ExcelExportEntity("??????", "five");
        excelExportEntity5.setNeedMerge(true);
        excelExportEntity5.setList(weekNumber);
        ExcelExportEntity excelExportEntity6 = new ExcelExportEntity("??????", "six");
        excelExportEntity6.setNeedMerge(true);
        excelExportEntity6.setList(weekNumber);
        ExcelExportEntity excelExportEntity7 = new ExcelExportEntity("??????", "seven");
        excelExportEntity7.setNeedMerge(true);
        excelExportEntity7.setList(weekNumber);

        excelExportEntitieList.add(excelExportEntity);
        excelExportEntitieList.add(excelExportEntity1);
        excelExportEntitieList.add(excelExportEntity2);
        excelExportEntitieList.add(excelExportEntity3);
        excelExportEntitieList.add(excelExportEntity4);
        excelExportEntitieList.add(excelExportEntity5);
        excelExportEntitieList.add(excelExportEntity6);
        excelExportEntitieList.add(excelExportEntity7);


        excelExportEntitieList.forEach(f->{
            f.setWrap(true);
        });

        return ExcelExportUtil.exportExcel(new ExportParams(),
                excelExportEntitieList,classScheduleList);
    }

    //????????????????????????
    public Map<String,Object> uploadClassSchedule(MultipartFile file){
        String fileType = QiniuUtil.getExt(file);
        if(".xls".equals(fileType)
                ||".xlsx".equals(fileType)){
        }else{
            Map<String,Object> map = new HashMap<>();
            map.put("code","222");
            map.put("error","??????????????????");
            return map;
        }
        List<String> error = new ArrayList<>();
        //????????????????????????
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.GRADE);
        dd.setLevelType(currentTeacher().getSchool().getTypeId());
        List<Dd> dds = ddFeign.findDdListByCondition(dd);

        Map<String,Object> result = new HashMap<>();
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        String[] titleArray = new String[]{"??????","??????","??????","??????","??????","??????","??????","??????"};
        params.setImportFields(titleArray);
        String fileName = file.getOriginalFilename();
        String gradeName = new String();
        String number  = new String();
        Pattern pattern = Pattern.compile("[0-9]*");
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println(LoginInterceptor.currentTeacher().getSchool().getTypeId());
        if(LoginInterceptor.currentTeacher().getSchool().getTypeId().equals("120")){
            gradeName = fileName.substring(0,3);
        } else{
            gradeName = fileName.substring(0,2);
        }
        String[] numbers = fileName.split("");
        for(String numbe:numbers){
            if(pattern.matcher(numbe).matches()){
                stringBuilder.append(numbe);
            }
        }
        number = stringBuilder.toString();
        String gradeId = new String();
        //?????????????????????id
        for(Dd f:dds){
            if(f.getName().equals(gradeName)){
                gradeId =f.getId();
                break;
            }
        }
        if(gradeId.isEmpty()&&gradeId.length()==0){
            error.add("excel????????????????????????");
            result.put("code","222");
            result.put("error",error);
            return result;
        }

        if(number.isEmpty()&&number.length()==0){
            error.add("excel????????????????????????");
            result.put("code","222");
            result.put("error",error);
            return result;
        }
        JwClasses jwClasses = new JwClasses();
        jwClasses.setSchoolId(mySchoolId());
        jwClasses.setGradeId(gradeId);

        jwClasses.setNumber(number);
        JwClasses jwClassesList = jwClassesFeign.findOneJwClassesByCondition(jwClasses);
        String classesId = new String();
        if(jwClassesList!=null){
            classesId = jwClassesList.getId();
        }else{
            error.add("excel????????????????????????");
        }
        if(error!=null&&error.size()>0){
            result.put("code","222");
            result.put("error",error);
            return result;
        }

        try (InputStream is = file.getInputStream()){
            List<Schedule> list = ExcelImportUtil.importExcel(is,
                    Schedule.class, params);
            ImportClassSchedule importClassSchedule = new ImportClassSchedule();
            importClassSchedule.setClassesId(classesId);
            importClassSchedule.setGradeId(gradeId);
            importClassSchedule.setSchedules(list);
            importClassSchedule.setError(error);
            importClassSchedule.setSchoolId(mySchoolId());
            result =  classScheduleFeign.conversionSchedule(importClassSchedule);

        }catch (Exception e){
            e.printStackTrace();
            Map<String,Object> map = new HashMap<>();
            map.put("code","222");
            map.put("error",new String[]{e.getMessage()+",???????????????????????????"});
            return map;
        }
        return result;
    }


    public ResponseJson  getTeacherList(ClassSchedule classSchedule){
        return classScheduleFeign.getTeacherList(classSchedule);
    }

    public List<ClassSchedule> findClassScheduleGroupClassId(ClassSchedule classSchedule){
        return classScheduleFeign.findClassScheduleGroupClassId(classSchedule);
    }

    //??????????????????
    public Map<String,Object> uploadClassScheduleNew(String scheduleId,MultipartFile file){
        String fileType = QiniuUtil.getExt(file);
        if(".xls".equals(fileType)
                ||".xlsx".equals(fileType)){
        }else{
            Map<String,Object> map = new HashMap<>();
            map.put("code","222");
            map.put("error","??????????????????");
            return map;
        }
        Map<String,Object> result = new HashMap<>();
        ImportParams params = new ImportParams();
        try (InputStream is = file.getInputStream()){
            ExcelImportResult<Object> objectExcelImportResult = ExcelImportUtil.importExcelMore(is, Object.class, params);
            Sheet sheet = objectExcelImportResult.getWorkbook().getSheetAt(0);
            Row row1 = sheet.getRow(0);
            //??????
            List<String> title = new ArrayList<>();
            for(int i = 1;i<row1.getLastCellNum();i++){
                Cell cell = row1.getCell(i);
                if(cell!=null&&!cell.getStringCellValue().equals("")){
                    title.add(cell.getStringCellValue());
                }
            }
            List<String> titleString = new ArrayList<>();
            titleString.addAll(Arrays.asList("??????","??????","??????","??????","??????","??????","??????"));
            boolean titleFlag = false;
            next:for(int i = 0;i<titleString.size();i++){
                for(int j = 0;j<title.size();j++){
                    if(titleString.get(i).equals(title.get(j))){
                        continue next;
                    }
                }
                titleFlag = true;
                break next;
            }
            if(titleFlag){
                result.put("code","222");
                result.put("error",new String[]{"???????????????????????????"});
                return result;
            }
            ClassScheduleInit classScheduleInit = new ClassScheduleInit();
            classScheduleInit.setSchoolId(mySchoolId());
            List<ClassScheduleInit> init = classScheduleInitService.findListClassScheduleInitBySchool(classScheduleInit);
            List<Integer> z = everyWeekTitleCount(row1,init.size()-1);

            if(z.size()>0){
                result.put("code","222");
                List<String> error = new ArrayList<>();
                z.stream().forEach(f->{
                    error.add(returnWeek(f)+"?????????????????????????????????!");
                });
                result.put("error",error);
                return result;
            }
            List<List<String>> listString = new ArrayList<>();
            for(int i=2;i<=sheet.getLastRowNum();i++){
                Row row = sheet.getRow(i);
                if(row!=null){
                    List<String> list = new ArrayList<>();
                    for(int j=0;j<row.getLastCellNum();j++){
                        Cell cell = row.getCell(j);
                        list.add(cell==null||cell.getStringCellValue().equals("")?"0":cell.getStringCellValue());
                    }
                    listString.add(list);
                }

            }
            ImportClassSchedule importClassSchedule = new ImportClassSchedule();
            importClassSchedule.setLists(listString);
            importClassSchedule.setCount(init.size());
            importClassSchedule.setSchoolId(LoginInterceptor.mySchoolId());
            importClassSchedule.setScheduleId(scheduleId);
            result = classScheduleFeign.conversionScheduleNew(importClassSchedule);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,Object> map = new HashMap<>();
            map.put("code","222");
            map.put("error",new String[]{e.getMessage()+",???????????????????????????"});
            return map;
        }
        return result;
    }

    /**
     * row ???????????????
     * sum ?????????????????????
     * @param row
     * @param sum
     * @return
     */
    public List<Integer> everyWeekTitleCount(Row row,int sum){
        List<Integer> integerList = new ArrayList<>();
        int count = 0;
        int j = 0;
        for(int i =1;i<row.getLastCellNum();i++){
            Cell cell = row.getCell(i);
            if((cell==null||cell.getStringCellValue().equals(""))){
                count++;
                if(i==row.getLastCellNum()-1&&count!=sum){
                    integerList.add(j);
                    count = 0;
                    j++;
                }
            }else if(count!=sum&&!cell.getStringCellValue().equals("??????")){
                integerList.add(j);
                count = 0;
                j++;
            }else{
                count = 0;
                j++;
            }
        }
        return integerList;
    }

    public String returnWeek(Integer i){
        Map<Integer,String> map = new HashMap();
        map.put(0,"??????");
        map.put(1,"??????");
        map.put(2,"??????");
        map.put(3,"??????");
        map.put(4,"??????");
        map.put(5,"??????");
        map.put(6,"??????");
        map.put(7,"??????");
        return map.get(i);
    }

    public void checkoutErrorClassSchedule(List<List<ClassSchedule>> classScheduleList){
        ClassSchedule classSchedule = new ClassSchedule();
        classSchedule.setSchoolId(mySchoolId());
        List<ClassSchedule> c = classScheduleFeign.findClassScheduleListByConditions(classSchedule);
        List<ClassSchedule> collect = c.stream().filter(f -> f.getTeacherId() != null).collect(Collectors.toList());
        classScheduleList.stream().forEach(f->{
            f.stream().forEach(fe->{
                if(fe!=null){
                    if(fe.getCourseId()==null||fe.getCourseName()==null){
                        fe.setCourseName("?????????????????????????????????");
                        fe.setError(1);
                    }else if(fe.getTeacherId()==null|| fe.getTeacherName()==null){
                        fe.setTeacherName("???????????????????????????");
                        fe.setError(1);
                    }else{
                        Integer[] i = new Integer[]{0};
                        collect.stream().forEach(fo->{
                            if(fo.getWeekId().equals(fe.getWeekId())
                                    &&fo.getNumberId().equals(fe.getNumberId())
                                    &&fo.getTeacherId().equals(fe.getTeacherId())
                                    &&!fo.getClassId().equals(fe.getClassId())){
                                fe.setTeacherName(fe.getTeacherName()+"???"+fo.getGradeName()+"("+fo.getClassName()+")????????????");
                                i[0] = i[0]+1;
                            }
                            if(i[0]>0){
                                fe.setError(1);
                            }else{
                                fe.setError(0);
                            }
                        });
                    }
                }
            });
        });
    }
}
