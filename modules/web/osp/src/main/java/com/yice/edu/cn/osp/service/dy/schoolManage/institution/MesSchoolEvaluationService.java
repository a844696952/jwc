package com.yice.edu.cn.osp.service.dy.schoolManage.institution;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qiniu.util.StringUtils;
import com.yice.edu.cn.common.pojo.mes.institution.MesClassHonor;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesInstitution;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesSchoolEvaluation;
import com.yice.edu.cn.common.pojo.mes.schoolManage.schoolEvaluation.MesSchoolEvaluationVo;
import com.yice.edu.cn.osp.feignClient.dy.schoolManage.institution.MesSchoolEvaluationFeign;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MesSchoolEvaluationService {
    @Autowired
    private MesSchoolEvaluationFeign mesSchoolEvaluationFeign;

    public Map findMesSchoolEvaluationListByCondition(MesSchoolEvaluationVo mesSchoolEvaluationVo) {
        return mesSchoolEvaluationFeign.findMesSchoolEvaluationListByCondition(mesSchoolEvaluationVo);
    }

    public Map findSchoolWeekOrMonthByCondition(MesSchoolEvaluationVo mesSchoolEvaluationVo) {
        return mesSchoolEvaluationFeign.findSchoolWeekOrMonthByCondition(mesSchoolEvaluationVo);
    }

    public List<MesInstitution> selectTableWhetherToShow(String schoolId) {
        return mesSchoolEvaluationFeign.selectTableWhetherToShow(schoolId);
    }

    public Map<String, Object> findEvaluationListByDay(MesSchoolEvaluationVo mesSchoolEvaluationVo) {
        return mesSchoolEvaluationFeign.findEvaluationListByDay(mesSchoolEvaluationVo);
    }

    public void saveMesSchoolEvaluationByAdmin(String time) {
        mesSchoolEvaluationFeign.saveMesSchoolEvaluationByAdmin(time);
    }

    public Workbook exportMesSchoolEvaluation(MesSchoolEvaluationVo mesSchoolEvaluationVo) {
        Map<String, Object> map;
        if (mesSchoolEvaluationVo.getExportType() == 0) {
            map = mesSchoolEvaluationFeign.findEvaluationListByDay(mesSchoolEvaluationVo);
        } else {
            map = mesSchoolEvaluationFeign.findMesSchoolEvaluationListByCondition(mesSchoolEvaluationVo);
        }
        List<MesSchoolEvaluation> list = JSONObject.parseArray(JSON.toJSONString(map.get("list")), MesSchoolEvaluation.class);
        List<MesInstitution> tableHead = JSONObject.parseArray(JSON.toJSONString(map.get("tableHead")), MesInstitution.class);
        HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("评比表");
        Row row0 = sheet.createRow(0);
        row0.setHeightInPoints(32);
        CellRangeAddress region;
        int l = 0;
        if (CollUtil.isNotEmpty(list) && CollUtil.isNotEmpty(list.get(0).getFormData())) {
            if (mesSchoolEvaluationVo.getExportType() == 0) {
                region = new CellRangeAddress(0, 0, (short) 0, (short) list.get(0).getFormData().size());
                l = list.get(0).getFormData().size();
            } else {
                region = new CellRangeAddress(0, 0, (short) 0, (short) list.get(0).getFormData().size() + 2);
                l = list.get(0).getFormData().size() + 1;
            }
            for (int i = 0; i <= l; i++) {
                sheet.setColumnWidth(i, 20 * 256);
            }
        } else {
            region = new CellRangeAddress(0, 0, (short) 0, (short) 5);
        }
        sheet.addMergedRegion(region);
        HSSFFont bigFont = workbook.createFont();
        bigFont.setBold(true);//粗体显示
        bigFont.setFontHeightInPoints((short) 13);
        HSSFFont smallFont = workbook.createFont();
        smallFont.setFontHeightInPoints((short) 10);
        Cell cell1 = row0.createCell(0);
        String str;
        if (mesSchoolEvaluationVo.getExportType() == 0) {
            str = map.get("schoolName") + "一日常规日评比" + "      " + map.get("beginTime") + "-" + map.get("endTime");
        } else if (mesSchoolEvaluationVo.getEvaluationType() == 2) {
            str = map.get("schoolName") + "一日常规周评比" + "      " + map.get("beginTime") + "-" + map.get("endTime");
        } else if (mesSchoolEvaluationVo.getEvaluationType() == 3) {
            str = map.get("schoolName") + "一日常规月评比" + "      " + map.get("beginTime") + "-" + map.get("endTime");
        } else {
            str = map.get("schoolName") + "一日常规学期评比" + "      " + map.get("beginTime") + "-" + map.get("endTime");
        }
        int i1 = str.indexOf("  20");
        HSSFRichTextString richString = new HSSFRichTextString(str);
        richString.applyFont(0, i1 - 1 > 0 ? i1 - 1 : 0, bigFont);
        richString.applyFont(i1 - 1 > 0 ? i1 : 0, str.length() - 1, smallFont);
        cell1.setCellValue(richString);

        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        cell1.setCellStyle(style);
        int i = 1;
        Row row1 = sheet.createRow(i);
        row1.setHeightInPoints(25);
        if (tableHead != null && !tableHead.isEmpty()) {
            int j = 0;
            Cell cellClass = row1.createCell(j++);
            cellClass.setCellValue("班级");
            for (MesInstitution cellValue : tableHead) {
                Cell cell = row1.createCell(j++);
                cell.setCellValue(cellValue.getName());
                if (mesSchoolEvaluationVo.getExportType() != 0) {
                    if (j == tableHead.size() + 1) {
                        Cell titleCell = row1.createCell(j++);
                        titleCell.setCellValue("称号名");
                        sheet.setColumnWidth(l + 1, 30 * 256);
                    }
                }
            }
        }
        if (CollUtil.isNotEmpty(list)) {
            for (MesSchoolEvaluation mesSchoolEvaluation : list) {
                List<Integer> formData = mesSchoolEvaluation.getFormData();
                List<String> strings = mesSchoolEvaluation.getTitles();
                String title = "";
                if (CollUtil.isNotEmpty(strings)) {
                    title = StringUtils.join(strings, "、");
                }
                List<String> list1 = new ArrayList<>();
                list1.add(mesSchoolEvaluation.getClassName());
                for (Integer formDatum : formData) {
                    list1.add("" + formDatum);
                }
                Row row = sheet.createRow(++i);
                row.setHeightInPoints(20);
                if (list1 != null && !list1.isEmpty()) {
                    int j = 0;
                    for (String cellValue : list1) {
                        Cell cell = row.createCell(j++);
                        cell.setCellValue(cellValue);
                        if (mesSchoolEvaluationVo.getExportType() != 0) {
                            if (j == list1.size()) {
                                Cell totalCell = row.createCell(j++);
                                totalCell.setCellValue(mesSchoolEvaluation.getTotalScore());
                                Cell titleCell = row.createCell(j++);
                                HSSFCellStyle cellStyle = workbook.createCellStyle();
                                cellStyle.setAlignment(HorizontalAlignment.LEFT);
                                cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
                                cellStyle.setWrapText(true);//设置自动换行
                                titleCell.setCellStyle(cellStyle);
                                titleCell.setCellValue(title);
                            }
                        }
                    }
                }
            }
        }
        return workbook;
    }

    public void saveMesSchoolEvaluationHonor() {
        mesSchoolEvaluationFeign.saveMesSchoolEvaluationHonor();
    }

    public List<List<MesClassHonor>> findMesSchoolEvaluationHonor(MesClassHonor mesClassHonor) {
        return mesSchoolEvaluationFeign.findMesSchoolEvaluationHonor(mesClassHonor);
    }

    public void saveMesSchoolEvaluationHonorByAdmin(String time) {
        mesSchoolEvaluationFeign.saveMesSchoolEvaluationHonorByAdmin(time);
    }

    public List<MesClassHonor> findMesSchoolEvaluationHonorNewest(MesClassHonor mesClassHonor) {
        return mesSchoolEvaluationFeign.findMesSchoolEvaluationHonorNewest(mesClassHonor);
    }
}
