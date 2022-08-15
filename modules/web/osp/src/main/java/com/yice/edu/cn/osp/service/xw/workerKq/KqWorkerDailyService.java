package com.yice.edu.cn.osp.service.xw.workerKq;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.hutool.core.bean.BeanUtil;
import com.yice.edu.cn.common.pojo.xw.workerKq.*;
import com.yice.edu.cn.osp.feignClient.xw.workerKq.KqWorkerDailyFeign;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class KqWorkerDailyService {
    @Autowired
    private KqWorkerDailyFeign kqWorkerDailyFeign;

    public KqWorkerDaily findKqWorkerDailyById(String id) {
        return kqWorkerDailyFeign.findKqWorkerDailyById(id);
    }

    public KqWorkerDaily saveKqWorkerDaily(KqWorkerDaily kqWorkerDaily) {
        return kqWorkerDailyFeign.saveKqWorkerDaily(kqWorkerDaily);
    }

    public List<KqWorkerDaily> findKqWorkerDailyListByCondition(KqWorkerDaily kqWorkerDaily) {
        return kqWorkerDailyFeign.findKqWorkerDailyListByCondition(kqWorkerDaily);
    }

    public KqWorkerDaily findOneKqWorkerDailyByCondition(KqWorkerDaily kqWorkerDaily) {
        return kqWorkerDailyFeign.findOneKqWorkerDailyByCondition(kqWorkerDaily);
    }

    public long findKqWorkerDailyCountByCondition(KqWorkerDaily kqWorkerDaily) {
        return kqWorkerDailyFeign.findKqWorkerDailyCountByCondition(kqWorkerDaily);
    }

    public void updateKqWorkerDaily(KqWorkerDaily kqWorkerDaily) {
        kqWorkerDailyFeign.updateKqWorkerDaily(kqWorkerDaily);
    }

    public void deleteKqWorkerDaily(String id) {
        kqWorkerDailyFeign.deleteKqWorkerDaily(id);
    }

    public void deleteKqWorkerDailyByCondition(KqWorkerDaily kqWorkerDaily) {
        kqWorkerDailyFeign.deleteKqWorkerDailyByCondition(kqWorkerDaily);
    }

    public KqWorkerDaily dailyRecords(KqWorkerDaily kqWorkerDaily) {
        return kqWorkerDailyFeign.dailyRecords(kqWorkerDaily);
    }


    public void createWorkerKqDailyRecord() {
        kqWorkerDailyFeign.createWorkerKqDailyRecord();
    }

    public Workbook exportWorkerKq(KqWorkerDaily kqWorkerDaily) {
        List<ExcelExportEntity> exportEntityList = new ArrayList<>();
        exportEntityList.add(new ExcelExportEntity("姓名", "userName", 20));
        exportEntityList.add(new ExcelExportEntity("部门", "departmentName"));
        exportEntityList.add(new ExcelExportEntity("工号", "worknumber"));
        exportEntityList.add(new ExcelExportEntity("日期", "kqDate"));
        exportEntityList.add(new ExcelExportEntity("班组", "groupName"));
        exportEntityList.add(new ExcelExportEntity("旷工", "isAbsence"));
        kqWorkerDaily.setSchoolId(mySchoolId());
        List<KqWorkerDaily> kqDataList = kqWorkerDailyFeign.findKqWorkerDailyListByCondition(kqWorkerDaily);
        //是否只有一组考勤
        List<KqWorkerDaily> hasPunchuNumTwo = kqDataList.stream().filter(kqWorkerDaily1 -> kqWorkerDaily1.getPunchNumber().equals("2")).collect(Collectors.toList());
        if (hasPunchuNumTwo != null && hasPunchuNumTwo.size() > 0) {
            exportEntityList.add(new ExcelExportEntity("上班打卡1", "In1"));
            exportEntityList.add(new ExcelExportEntity("下班打卡1", "Out1"));
            exportEntityList.add(new ExcelExportEntity("上班打卡2", "In2"));
            exportEntityList.add(new ExcelExportEntity("下班打卡2", "Out2"));
        } else {
            exportEntityList.add(new ExcelExportEntity("上班打卡1", "In1"));
            exportEntityList.add(new ExcelExportEntity("下班打卡1", "Out1"));
        }
        List<Map<String, Object>> mapList = new ArrayList<>();
        //对象转map
        for (KqWorkerDaily k : kqDataList) {
            KqWorkerDaily kmap = new KqWorkerDaily();
            kmap.setUserName(k.getUserName());
            kmap.setDepartmentName(k.getDepartmentName());
            kmap.setWorknumber(k.getWorknumber());
            kmap.setKqDate(k.getKqDate());
            kmap.setGroupName(k.getGroupName());
            kmap.setIsAbsence(k.getIsAbsence().equals("1") ? "是" : "否");
            Map<String, Object> stringObjectMap = BeanUtil.beanToMap(kmap);
            PunchRules punchRules = k.getPunchRules();
            String punchNumber = k.getPunchNumber();
            //获得该点考勤描述

            if (punchNumber.equals("2")) {
                String morningInKqMsg = getKqMsg(1, k);
                stringObjectMap.put("In1", morningInKqMsg);
                String morningOutKqMsg = getKqMsg(2, k);
                stringObjectMap.put("Out1", morningOutKqMsg);
                String noonInKqMsg = getKqMsg(3, k);
                stringObjectMap.put("In2", noonInKqMsg);
                String duskOutKqMsg = getKqMsg(4, k);
                stringObjectMap.put("Out2", duskOutKqMsg);
            } else if (punchNumber.equals("1")) {
                String morningInKqMsg = getKqMsg(1, k);
                stringObjectMap.put("In1", morningInKqMsg);
                String duskOutKqMsg = getKqMsg(4, k);
                stringObjectMap.put("Out1", duskOutKqMsg);
            }

            mapList.add(stringObjectMap);
        }

        return ExcelExportUtil.exportExcel(new ExportParams("职工日考勤列表", "日考勤"), exportEntityList, mapList);
    }

    private String getKqMsg(int i, KqWorkerDaily k) {
        //打卡记录 0迟到 1早退 2正常 3缺卡 4 请假 5补卡 6出差 7公出
        //考勤日统计打卡时间点状态详情
        // 4 请假(0 事假 2病假 3 婚假 4 生育假 5 陪产假 6 丧假 7 会议请假 8 其他)
        // 5 补卡(1 上班补卡1 2 上班补卡2 3 下班补卡1  4 下班补卡2) 6 出差  7 公出
        PunchRules punchRules = k.getPunchRules();
        StringBuilder kqDetails = new StringBuilder();
        switch (i) {
            case 1:
                String morningInStatus = punchRules.getMorningInStatus();
                kqDetails = getKqDetails(morningInStatus, k, i);
                ;
                break;
            case 2:
                String morningOutStatus = punchRules.getMorningOutStatus();
                kqDetails = getKqDetails(morningOutStatus, k, i);
                ;
                break;
            case 3:
                String noonInStatus = punchRules.getNoonInStatus();
                kqDetails = getKqDetails(noonInStatus, k, i);
                ;
                break;
            case 4:
                String duskOutStatus = punchRules.getDuskOutStatus();
                kqDetails = getKqDetails(duskOutStatus, k, i);
                ;
                break;
        }


        return kqDetails.toString();
    }

    private StringBuilder getKqDetails(String morningInStatus, KqWorkerDaily k, int timePoint) {
        //打卡记录 0迟到 1早退 2正常 3缺卡 4 请假 5补卡 6出差 7公出
        //考勤日统计打卡时间点状态详情
        // 4 请假(0 事假 2病假 3 婚假 4 生育假 5 陪产假 6 丧假 7 会议请假 8 其他)
        // 5 补卡(1 上班补卡1 2 上班补卡2 3 下班补卡1  4 下班补卡2) 6 出差  7 公出
        PunchRules punchRules = k.getPunchRules();
        StringBuilder detail = new StringBuilder();
        switch (morningInStatus) {
            case "0":
                if (timePoint == 1) {
                    detail.append(punchRules.getMorningIn());
                    detail.append("(");
                    detail.append("迟到");
                    detail.append(punchRules.getMorningInTimeLag());
                    detail.append("分钟");
                    detail.append(")");
                } else if (timePoint == 3) {
                    detail.append(punchRules.getNoonIn());
                    detail.append("(");
                    detail.append("迟到");
                    detail.append(punchRules.getNoonInTimeLag());
                    detail.append("分钟");
                    detail.append(")");
                }
                ;
                break;
            case "1":
                if (timePoint == 2) {
                    detail.append(punchRules.getMorningOut());
                    detail.append("(");
                    detail.append("早退");
                    detail.append(punchRules.getMorningOutTimeLag());
                    detail.append("分钟");
                    detail.append(")");
                } else if (timePoint == 4) {
                    detail.append(punchRules.getDuskOut());
                    detail.append("(");
                    detail.append("早退");
                    detail.append(punchRules.getDuskOutTimeLag());
                    detail.append("分钟");
                    detail.append(")");
                }
                ;
                break;
            case "2":
                if (timePoint == 1) {
                    detail.append(punchRules.getMorningIn());
                } else if (timePoint == 2) {
                    detail.append(punchRules.getMorningOut());
                } else if (timePoint == 3) {
                    detail.append(punchRules.getNoonIn());
                } else if (timePoint == 4) {
                    detail.append(punchRules.getDuskOut());
                }
                ;
                break;
            case "3":
                if (k.getTodayIsHoliday() != null && k.getTodayIsHoliday().equals("1")) {
                    detail.append("无需打卡");
                } else if (timePoint==2||timePoint==4) {
                    WorkerKqRules todayStandardRules = k.getTodayStandardRules();
                    String outNoNeedCard = "0";
                    if (todayStandardRules.getNoNeedCard()!=null){
                        outNoNeedCard = todayStandardRules.getNoNeedCard();
                    }
                    if (outNoNeedCard.equals("1")){
                        detail.append("无需打卡");
                    }else {
                        detail.append("缺卡");
                    }
                } else {
                    detail.append("缺卡");
                }
                ;
                break;
            case "4":
                // 4 请假(0 事假 2病假 3 婚假 4 生育假 5 陪产假 6 丧假 7 会议请假 8 其他)
                detail.append("请假");
                String typeDetail = "其他";
                try {
                    if (timePoint == 1) {
                        String type = punchRules.getMorningInStatusType();
                        typeDetail = getStatusType(type);
                    } else if (timePoint == 2) {
                        String type = punchRules.getMorningOutStatusType();
                        typeDetail = getStatusType(type);
                    } else if (timePoint == 3) {
                        String type = punchRules.getNoonInStatusType();
                        typeDetail = getStatusType(type);
                    } else if (timePoint == 4) {
                        String type = punchRules.getDuskOutStatusType();
                        typeDetail = getStatusType(type);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                detail.append("(");
                detail.append(typeDetail);
                detail.append(")");
                ;
                break;
            case "5":
                detail.append("补卡");
                ;
                break;
            case "6":
                detail.append("出差");
                ;
                break;
            case "7":
                detail.append("公出");
                ;
                break;
        }
        return detail;
    }

    private String getStatusType(String statusType) {
        //4 请假(0 事假 2 病假 3 婚假 4 生育假 5 陪产假 6 丧假 7 会议请假 8 其他
        switch (statusType) {
            case "0":
                statusType = "事假";
                break;
            case "1":
                statusType = "事假";
                break;
            case "2":
                statusType = "病假";
                break;
            case "3":
                statusType = "婚假";
                break;
            case "4":
                statusType = "生育假";
                break;
            case "5":
                statusType = "陪产假";
                break;
            case "6":
                statusType = "丧假";
                break;
            case "7":
                statusType = "会议请假";
                break;
            case "8":
                statusType = "其他";
                break;
            case "9":
                statusType = "调休";
                break;
        }
        return statusType;
    }

    public KqWorkerManageMonth findSchoolLeaderMonthStatistic(KqWorkerMonth kqWorkerMonth){
        return  kqWorkerDailyFeign.findSchoolLeaderMonthStatistic(kqWorkerMonth);
    }
    public  List<String> isKqGroupManage() {
        KqWorkerDaily kqWorkerDaily = new KqWorkerDaily();
        kqWorkerDaily.setUserId(myId());
        kqWorkerDaily.setSchoolId(mySchoolId());
        List<String> kqGroupManage = kqWorkerDailyFeign.isKqGroupManage(kqWorkerDaily);
        return kqGroupManage;
    }
}
