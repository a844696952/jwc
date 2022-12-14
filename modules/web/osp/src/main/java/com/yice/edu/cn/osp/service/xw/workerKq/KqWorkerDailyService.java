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
        exportEntityList.add(new ExcelExportEntity("??????", "userName", 20));
        exportEntityList.add(new ExcelExportEntity("??????", "departmentName"));
        exportEntityList.add(new ExcelExportEntity("??????", "worknumber"));
        exportEntityList.add(new ExcelExportEntity("??????", "kqDate"));
        exportEntityList.add(new ExcelExportEntity("??????", "groupName"));
        exportEntityList.add(new ExcelExportEntity("??????", "isAbsence"));
        kqWorkerDaily.setSchoolId(mySchoolId());
        List<KqWorkerDaily> kqDataList = kqWorkerDailyFeign.findKqWorkerDailyListByCondition(kqWorkerDaily);
        //????????????????????????
        List<KqWorkerDaily> hasPunchuNumTwo = kqDataList.stream().filter(kqWorkerDaily1 -> kqWorkerDaily1.getPunchNumber().equals("2")).collect(Collectors.toList());
        if (hasPunchuNumTwo != null && hasPunchuNumTwo.size() > 0) {
            exportEntityList.add(new ExcelExportEntity("????????????1", "In1"));
            exportEntityList.add(new ExcelExportEntity("????????????1", "Out1"));
            exportEntityList.add(new ExcelExportEntity("????????????2", "In2"));
            exportEntityList.add(new ExcelExportEntity("????????????2", "Out2"));
        } else {
            exportEntityList.add(new ExcelExportEntity("????????????1", "In1"));
            exportEntityList.add(new ExcelExportEntity("????????????1", "Out1"));
        }
        List<Map<String, Object>> mapList = new ArrayList<>();
        //?????????map
        for (KqWorkerDaily k : kqDataList) {
            KqWorkerDaily kmap = new KqWorkerDaily();
            kmap.setUserName(k.getUserName());
            kmap.setDepartmentName(k.getDepartmentName());
            kmap.setWorknumber(k.getWorknumber());
            kmap.setKqDate(k.getKqDate());
            kmap.setGroupName(k.getGroupName());
            kmap.setIsAbsence(k.getIsAbsence().equals("1") ? "???" : "???");
            Map<String, Object> stringObjectMap = BeanUtil.beanToMap(kmap);
            PunchRules punchRules = k.getPunchRules();
            String punchNumber = k.getPunchNumber();
            //????????????????????????

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

        return ExcelExportUtil.exportExcel(new ExportParams("?????????????????????", "?????????"), exportEntityList, mapList);
    }

    private String getKqMsg(int i, KqWorkerDaily k) {
        //???????????? 0?????? 1?????? 2?????? 3?????? 4 ?????? 5?????? 6?????? 7??????
        //??????????????????????????????????????????
        // 4 ??????(0 ?????? 2?????? 3 ?????? 4 ????????? 5 ????????? 6 ?????? 7 ???????????? 8 ??????)
        // 5 ??????(1 ????????????1 2 ????????????2 3 ????????????1  4 ????????????2) 6 ??????  7 ??????
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
        //???????????? 0?????? 1?????? 2?????? 3?????? 4 ?????? 5?????? 6?????? 7??????
        //??????????????????????????????????????????
        // 4 ??????(0 ?????? 2?????? 3 ?????? 4 ????????? 5 ????????? 6 ?????? 7 ???????????? 8 ??????)
        // 5 ??????(1 ????????????1 2 ????????????2 3 ????????????1  4 ????????????2) 6 ??????  7 ??????
        PunchRules punchRules = k.getPunchRules();
        StringBuilder detail = new StringBuilder();
        switch (morningInStatus) {
            case "0":
                if (timePoint == 1) {
                    detail.append(punchRules.getMorningIn());
                    detail.append("(");
                    detail.append("??????");
                    detail.append(punchRules.getMorningInTimeLag());
                    detail.append("??????");
                    detail.append(")");
                } else if (timePoint == 3) {
                    detail.append(punchRules.getNoonIn());
                    detail.append("(");
                    detail.append("??????");
                    detail.append(punchRules.getNoonInTimeLag());
                    detail.append("??????");
                    detail.append(")");
                }
                ;
                break;
            case "1":
                if (timePoint == 2) {
                    detail.append(punchRules.getMorningOut());
                    detail.append("(");
                    detail.append("??????");
                    detail.append(punchRules.getMorningOutTimeLag());
                    detail.append("??????");
                    detail.append(")");
                } else if (timePoint == 4) {
                    detail.append(punchRules.getDuskOut());
                    detail.append("(");
                    detail.append("??????");
                    detail.append(punchRules.getDuskOutTimeLag());
                    detail.append("??????");
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
                    detail.append("????????????");
                } else if (timePoint==2||timePoint==4) {
                    WorkerKqRules todayStandardRules = k.getTodayStandardRules();
                    String outNoNeedCard = "0";
                    if (todayStandardRules.getNoNeedCard()!=null){
                        outNoNeedCard = todayStandardRules.getNoNeedCard();
                    }
                    if (outNoNeedCard.equals("1")){
                        detail.append("????????????");
                    }else {
                        detail.append("??????");
                    }
                } else {
                    detail.append("??????");
                }
                ;
                break;
            case "4":
                // 4 ??????(0 ?????? 2?????? 3 ?????? 4 ????????? 5 ????????? 6 ?????? 7 ???????????? 8 ??????)
                detail.append("??????");
                String typeDetail = "??????";
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
                detail.append("??????");
                ;
                break;
            case "6":
                detail.append("??????");
                ;
                break;
            case "7":
                detail.append("??????");
                ;
                break;
        }
        return detail;
    }

    private String getStatusType(String statusType) {
        //4 ??????(0 ?????? 2 ?????? 3 ?????? 4 ????????? 5 ????????? 6 ?????? 7 ???????????? 8 ??????
        switch (statusType) {
            case "0":
                statusType = "??????";
                break;
            case "1":
                statusType = "??????";
                break;
            case "2":
                statusType = "??????";
                break;
            case "3":
                statusType = "??????";
                break;
            case "4":
                statusType = "?????????";
                break;
            case "5":
                statusType = "?????????";
                break;
            case "6":
                statusType = "??????";
                break;
            case "7":
                statusType = "????????????";
                break;
            case "8":
                statusType = "??????";
                break;
            case "9":
                statusType = "??????";
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
