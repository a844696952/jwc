package com.yice.edu.cn.osp.service.xw.workerKq;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.pojo.xw.workerKq.KqWorkerMonth;
import com.yice.edu.cn.common.util.WeekUtils.DateZoneUtil;
import com.yice.edu.cn.osp.feignClient.xw.workerKq.KqWorkerMonthFeign;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.afterturn.easypoi.excel.ExcelExportUtil;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class KqWorkerMonthService {
    @Autowired
    private KqWorkerMonthFeign kqWorkerMonthFeign;

   /* public KqWorkerMonth findKqWorkerMonthById(String id) {
        return kqWorkerMonthFeign.findKqWorkerMonthById(id);
    }

    public KqWorkerMonth saveKqWorkerMonth(KqWorkerMonth kqWorkerMonth) {
        return kqWorkerMonthFeign.saveKqWorkerMonth(kqWorkerMonth);
    }

    public List<KqWorkerMonth> findKqWorkerMonthListByCondition(KqWorkerMonth kqWorkerMonth) {
        return kqWorkerMonthFeign.findKqWorkerMonthListByCondition(kqWorkerMonth);
    }

    public KqWorkerMonth findOneKqWorkerMonthByCondition(KqWorkerMonth kqWorkerMonth) {
        return kqWorkerMonthFeign.findOneKqWorkerMonthByCondition(kqWorkerMonth);
    }

    public long findKqWorkerMonthCountByCondition(KqWorkerMonth kqWorkerMonth) {
        return kqWorkerMonthFeign.findKqWorkerMonthCountByCondition(kqWorkerMonth);
    }

    public void updateKqWorkerMonth(KqWorkerMonth kqWorkerMonth) {
        kqWorkerMonthFeign.updateKqWorkerMonth(kqWorkerMonth);
    }

    public void deleteKqWorkerMonth(String id) {
        kqWorkerMonthFeign.deleteKqWorkerMonth(id);
    }

    public void deleteKqWorkerMonthByCondition(KqWorkerMonth kqWorkerMonth) {
        kqWorkerMonthFeign.deleteKqWorkerMonthByCondition(kqWorkerMonth);
    }*/

    public List<KqWorkerMonth> findWorkerMonthRecordList(KqWorkerMonth kqWorkerMonth) {
        return kqWorkerMonthFeign.findWorkerMonthRecordList(kqWorkerMonth);
    }


    public Workbook exportWorkerKq(KqWorkerMonth kqWorkerMonth) {
        Object[] rangeArray = kqWorkerMonth.getPager().getRangeArray();
        String s = (String)rangeArray[0];
        String e = (String)rangeArray[1];
        DateTime parse = DateUtil.parse(s);
        Date startDate = parse.toJdkDate();
        DateTime parse1 = DateUtil.parse(e);
        Date endDate = parse1.toJdkDate();
        DateZoneUtil dateCal = new DateZoneUtil();
        dateCal.countDays(startDate,  endDate);
        List<Date> list = dateCal.getDayList();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd EEE");
        List<String> dateList = new ArrayList<>();
        for (Date date : list) {
            dateList.add(dateFormat.format(date));
        }
        List<ExcelExportEntity> exportEntityList = new ArrayList<>();
        exportEntityList.add(new ExcelExportEntity("姓名", "userName", 20));
        exportEntityList.add(new ExcelExportEntity("部门", "departmentName"));
        exportEntityList.add(new ExcelExportEntity("工号", "worknumber"));
        exportEntityList.add(new ExcelExportEntity("缺卡次数", "missCardNum"));
        exportEntityList.add(new ExcelExportEntity("旷工天数", "absenceNum"));
        exportEntityList.add(new ExcelExportEntity("补卡", "fillMissNum"));
        exportEntityList.add(new ExcelExportEntity("请假", "leaveMsg"));
        kqWorkerMonth.setSchoolId(mySchoolId());
        List<KqWorkerMonth> workerMonthRecordList = kqWorkerMonthFeign.findWorkerMonthRecordList(kqWorkerMonth);
        if (workerMonthRecordList != null && workerMonthRecordList.size() > 0) {
            KqWorkerMonth km = workerMonthRecordList.get(0);
            HashMap<String, Integer> lateMap = km.getLateMap();
            if (lateMap != null && lateMap.size() > 0) {
                //设置动态迟到表头
                Set<String> lateMapKeys = lateMap.keySet();
                for (String key : lateMapKeys) {
                    StringBuilder tableHeadName = new StringBuilder();
                    String[] split = key.split("-");
                    key= "late-"+key;
                    String begin = split[0];
                    String end = split[1];
                    if (begin.equals("0")) {
                        tableHeadName.append("迟到");
                        tableHeadName.append(end);
                        tableHeadName.append("分钟以内");
                    } else if (end.equals("10000000")) {
                        tableHeadName.append("迟到");
                        tableHeadName.append(begin);
                        tableHeadName.append("分钟以上");
                    } else {
                        tableHeadName.append("迟到");
                        tableHeadName.append(begin);
                        tableHeadName.append("-");
                        tableHeadName.append(end);
                        tableHeadName.append("分钟");
                    }
                    exportEntityList.add(new ExcelExportEntity(tableHeadName.toString(), key));
                }

            }
            HashMap<String, Integer> earlyMap = km.getEarlyMap();
            if (lateMap != null && lateMap.size() > 0) {
                //设置动态迟到表头
                Set<String> earlyMapKeys = earlyMap.keySet();
                for (String key : earlyMapKeys) {
                    StringBuilder tableHeadName = new StringBuilder();
                    String[] split = key.split("-");
                    key= "early-"+key;
                    String begin = split[0];
                    String end = split[1];
                    if (begin.equals("0")) {
                        tableHeadName.append("早退");
                        tableHeadName.append(end);
                        tableHeadName.append("分钟以内");
                    } else if (end.equals("10000000")) {
                        tableHeadName.append("早退");
                        tableHeadName.append(begin);
                        tableHeadName.append("分钟以上");
                    } else {
                        tableHeadName.append("早退");
                        tableHeadName.append(begin);
                        tableHeadName.append("-");
                        tableHeadName.append(end);
                        tableHeadName.append("分钟");
                    }
                    exportEntityList.add(new ExcelExportEntity(tableHeadName.toString(), key));
                }

            }
        }
        if (dateList.size()>0){
            for (String day:dateList){
                String dayFild = day.substring(0,10);
                exportEntityList.add(new ExcelExportEntity(day, dayFild));
            }
        }
        List<Map<String, Object>> mapList = new ArrayList<>();
        if (workerMonthRecordList!=null){
            for (KqWorkerMonth k : workerMonthRecordList) {
                KqWorkerMonth kmp = new KqWorkerMonth();
                kmp.setUserName(k.getUserName());
                kmp.setDepartmentName(k.getDepartmentName());
                kmp.setWorknumber(k.getWorknumber());
                kmp.setMissCardNum(k.getMissCardNum());
                kmp.setAbsenceNum(k.getAbsenceNum());
                kmp.setFillMissNum(k.getFillMissNum());
                kmp.setLeaveMsg(k.getLeaveMsg());
                Map<String, Object> stringObjectMap = BeanUtil.beanToMap(kmp);
                HashMap<String, Integer> lateMap = k.getLateMap();
                Set<String> latekeys = lateMap.keySet();
                for (String key : latekeys) {
                    String lateNum = lateMap.get(key)+"次";
                    key= "late-"+key;
                    stringObjectMap.put(key,lateNum);
                }
                HashMap<String, Integer> earlyMap = k.getEarlyMap();
                Set<String> earlykeys = earlyMap.keySet();
                for (String key : earlykeys) {
                    String earlyNum = earlyMap.get(key)+"次";
                    key= "early-"+key;
                    stringObjectMap.put(key,earlyNum);
                }
                HashMap<String, String> dateAndMsgMap = k.getDateAndMsgMap();
                for (String day:dateList){
                    String dayFild = day.substring(0,10);
                    String dateMsg = dateAndMsgMap.get(dayFild);
                    if (StrUtil.isNotEmpty(dateMsg)){
                        stringObjectMap.put(dayFild,dateMsg);
                    }else {
                        stringObjectMap.put(dayFild,"无需打卡");
                    }
                }
                mapList.add(stringObjectMap);
            }
        }
        return ExcelExportUtil.exportExcel(new ExportParams("职工月考勤列表", "月考勤"), exportEntityList, mapList);
    }
}
