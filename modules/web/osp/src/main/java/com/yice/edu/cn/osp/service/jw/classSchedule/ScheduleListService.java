package com.yice.edu.cn.osp.service.jw.classSchedule;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.hutool.core.bean.BeanUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classSchedule.*;
import com.yice.edu.cn.common.util.map.MapUtil;
import com.yice.edu.cn.common.util.reflect.ClassScheduleUtil;
import com.yice.edu.cn.osp.feignClient.jw.classSchedule.ClassScheduleFeign;
import com.yice.edu.cn.osp.feignClient.jw.classSchedule.ClassScheduleInitFeign;
import com.yice.edu.cn.osp.feignClient.jw.classSchedule.ScheduleListFeign;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScheduleListService {
    @Autowired
    private ScheduleListFeign scheduleListFeign;
    @Autowired
    private PastInitService pastInitService;
    @Autowired
    private PastClassScheduleService pastClassScheduleService;
    @Autowired
    private ClassScheduleFeign classScheduleFeign;
    @Autowired
    private ClassScheduleInitFeign classScheduleInitFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public ScheduleList findScheduleListById(String id) {
        return scheduleListFeign.findScheduleListById(id);
    }

    public ScheduleList saveScheduleList(ScheduleList scheduleList) {
        return scheduleListFeign.saveScheduleList(scheduleList);
    }

    public void batchSaveScheduleList(List<ScheduleList> scheduleLists){
        scheduleListFeign.batchSaveScheduleList(scheduleLists);
    }

    public List<ScheduleList> findScheduleListListByCondition(ScheduleList scheduleList) {
        return scheduleListFeign.findScheduleListListByCondition(scheduleList);
    }

    public ScheduleList findOneScheduleListByCondition(ScheduleList scheduleList) {
        return scheduleListFeign.findOneScheduleListByCondition(scheduleList);
    }

    public long findScheduleListCountByCondition(ScheduleList scheduleList) {
        return scheduleListFeign.findScheduleListCountByCondition(scheduleList);
    }

    public void updateScheduleList(ScheduleList scheduleList) {
        scheduleListFeign.updateScheduleList(scheduleList);
    }

    public void updateScheduleListForAll(ScheduleList scheduleList) {
        scheduleListFeign.updateScheduleListForAll(scheduleList);
    }

    public void deleteScheduleList(String id) {
        scheduleListFeign.deleteScheduleList(id);
    }

    public void deleteScheduleListByCondition(ScheduleList scheduleList) {
        scheduleListFeign.deleteScheduleListByCondition(scheduleList);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public void updateScheduleAndSavePastSchedule(ScheduleList scheduleList){
        scheduleListFeign.updateScheduleAndSavePastSchedule(scheduleList);
    }

    //导出课表
    public Workbook exportClassSchedule(ScheduleList scheduleList){
        ScheduleList scheduleList1 = scheduleListFeign.findOneScheduleListByCondition(scheduleList);
        //已过期课表
        long[] count = new long[]{0};
        List<PastClassSchedule> pastClassSchedules = new ArrayList<>();
        List<Map<String,Object>> classScheduleList = new ArrayList<>();
        PastClassSchedule pastClassSchedule = new PastClassSchedule();
        pastClassSchedule.setScheduleId(scheduleList.getId());
        pastClassSchedule.setSchoolId(scheduleList.getSchoolId());
        if(scheduleList1.getType()==2){
            PastInit pastInit = new PastInit();
            pastInit.setScheduleId(scheduleList.getId());
            pastInit.setSchoolId(scheduleList.getSchoolId());
            ClassScheduleInit classScheduleInit = new ClassScheduleInit();
            classScheduleInit.setSchoolId(LoginInterceptor.mySchoolId());
            //查询往期节数
            count[0] = pastInitService.findOnePastInitByCondition(pastInit).getCount();
            pastClassSchedules = pastClassScheduleService.findListPastClassScheduleByCountKong(pastClassSchedule);
        }else{
            ClassScheduleInit  classScheduleInit = new ClassScheduleInit();
            classScheduleInit.setSchoolId(LoginInterceptor.mySchoolId());
            count[0] = classScheduleInitFeign.findListClassScheduleInitBySchool(classScheduleInit).size();
            ClassSchedule classSchedule = new ClassSchedule();
            BeanUtil.copyProperties(pastClassSchedule,classSchedule);
            List<ClassSchedule> classSchedules = classScheduleFeign.findClassScheduleListByConditions(classSchedule);
            for(ClassSchedule f:classSchedules){
                PastClassSchedule pastClassSchedule1= new PastClassSchedule();
                BeanUtil.copyProperties(f,pastClassSchedule1);
                pastClassSchedules.add(pastClassSchedule1);
            }
            Map<String, Map<String, List<PastClassSchedule>>> collect = pastClassSchedules.stream().collect(Collectors.groupingBy(PastClassSchedule::getGradeName, Collectors.groupingBy(PastClassSchedule::getClassName)));
            pastClassSchedules = new ArrayList<>();
            for(String s:collect.keySet()){
                for(String i:collect.get(s).keySet()){
                    PastClassSchedule pastClassSchedule1 = new PastClassSchedule();
                    pastClassSchedule1.setGradeName(s);
                    pastClassSchedule1.setClassName(i);
                    pastClassSchedule1.setClassScheduleList(collect.get(s).get(i));
                    pastClassSchedules.add(pastClassSchedule1);
                }
            }
        }

        pastClassSchedules.stream().forEach(f->{
            buildClassScheduleData(f,classScheduleList,count[0]);
        });

        List<ExcelExportEntity> excelExportEntitieList = new ArrayList<>();
        List<ExcelExportEntity> weekNumber = new ArrayList<>();
        for(int i =1;i<=count[0];i++){
            ExcelExportEntity exportEntity = new ExcelExportEntity(i+"",i+"");
            exportEntity.setNeedMerge(true);
            exportEntity.setOrderNum(i);
            weekNumber.add(exportEntity);
        }

        ExcelExportEntity excelExportEntity = new ExcelExportEntity("班级名称","num",20);
        excelExportEntity.setNeedMerge(true);
        ExcelExportEntity excelExportEntity1 = new ExcelExportEntity("周一", "1");
        excelExportEntity1.setNeedMerge(true);
        excelExportEntity1.setList(weekNumber);
        ExcelExportEntity excelExportEntity2 = new ExcelExportEntity("周二", "2");
        excelExportEntity2.setNeedMerge(true);
        excelExportEntity2.setList(weekNumber);
        ExcelExportEntity excelExportEntity3 = new ExcelExportEntity("周三", "3");
        excelExportEntity3.setNeedMerge(true);
        excelExportEntity3.setList(weekNumber);
        ExcelExportEntity excelExportEntity4 = new ExcelExportEntity("周四", "4");
        excelExportEntity4.setNeedMerge(true);
        excelExportEntity4.setList(weekNumber);
        ExcelExportEntity excelExportEntity5 = new ExcelExportEntity("周五", "5");
        excelExportEntity5.setNeedMerge(true);
        excelExportEntity5.setList(weekNumber);
        ExcelExportEntity excelExportEntity6 = new ExcelExportEntity("周六", "6");
        excelExportEntity6.setNeedMerge(true);
        excelExportEntity6.setList(weekNumber);
        ExcelExportEntity excelExportEntity7 = new ExcelExportEntity("周日", "7");
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

    /**
     * 往期课表对象
     * @param pastClassSchedule
     * 操作的List<Map<String,Object>> 对象
     * @param classScheduleList
     * 往期所用的节数
     * @param count
     */
    public void buildClassScheduleData(PastClassSchedule pastClassSchedule,List<Map<String,Object>> classScheduleList,long count){
        Map<String,Object> map = new HashMap<>();

        map.put("num",pastClassSchedule.getGradeName()+"（"+pastClassSchedule.getClassName()+"）班");
            //一周的数据
            for(int i =1;i<=7;i++){
                List<Map<String,Object>> map1 = new ArrayList<>();
                Map<String,Object> map2 = new HashMap<>();
                Integer[] integers = new Integer[1];
                integers[0] = i;
                if(pastClassSchedule.getClassScheduleList()!=null){
                    pastClassSchedule.getClassScheduleList().stream().forEach(f->{
                        if(f.getWeekId().equals(integers[0])){
                            map2.put(f.getNumberId()+"",f.getCourseName());
                        }
                    });
                }

                map1.add(map2);
                //每周对应的数据
                map.put(i+"",map1);
            }
        classScheduleList.add(map);
    }

    public ResponseJson saveScheduleListKong(ScheduleList scheduleList){
        return scheduleListFeign.saveScheduleListKong(scheduleList);
    }

    public void deleteScheduleAndClassSchedule(String id){
        scheduleListFeign.deleteScheduleAndClassSchedule(id);
    }

    public ResponseJson issueScheduleListKong(ScheduleList scheduleList){
        return scheduleListFeign.issueScheduleListKong(scheduleList);
    }

}
