package com.yice.edu.cn.osp.service.dm.modeManage;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.core.collection.CollectionUtil;
import com.qiniu.util.StringUtils;
import com.yice.edu.cn.common.pojo.dm.modeManage.ExamInfo;
import com.yice.edu.cn.common.pojo.dm.modeManage.ExamMode;
import com.yice.edu.cn.common.pojo.dm.modeManage.ExamSubject;
import com.yice.edu.cn.osp.feignClient.dm.modeManage.ExamModeFeign;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

import static org.apache.poi.ss.usermodel.Font.COLOR_RED;

@Service
public class ExamModeService {

    @Autowired
    private ExamModeFeign examModeFeign;

    public ExamMode saveExamMode(ExamMode examMode) {
        return examModeFeign.saveExamMode(examMode);
    }

    public ExamMode findExamModeById(String id) {
        return examModeFeign.findExamModeById(id);
    }

    public Workbook exportExamModeById(String id) {
        ExamMode examModeById = examModeFeign.findExamModeById(id);
        for (ExamInfo examInfo : examModeById.getExamInfo()) {
            examInfo.setExamSiteNumber(null);
        }
        ExportParams exportParams = new ExportParams("导入考试人员", "考试信息表（按科目分配人员）");
        HSSFWorkbook workbook = (HSSFWorkbook) ExcelExportUtil.exportExcel(exportParams, ExamInfo.class,examModeById.getExamInfo());
        return setUpPrompt(workbook);
    }

    public Workbook setUpPrompt(HSSFWorkbook workbook) {
        HSSFSheet sheet = workbook.getSheet("考试信息表（按科目分配人员）");
        HSSFRow row = sheet.getRow(3);
        row.createCell(7).setCellValue("说明：\n" +
                "1.班牌对应的班级，就是所在的考场\n" +
                "2.监考老师及考试学生为选填，不填写，班牌无人员显示；填写，显示人员。\n" +
                "3.所有班牌对应的人员必须都要填写完整，否则无法导入此表格\n" +
                "4.座位号-人员之间间隔用中文分号：“；”，如1-小明；2-校长；3-小李，填\n" +
                "写其他形式符号，无法导入表格");
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.RED.index);
        cellStyle.setFont(font);
        row.getCell(7).setCellStyle(cellStyle);
        //合并單元格
        CellRangeAddress region = new CellRangeAddress(3, 7, (short) 7, (short) 13);
        sheet.addMergedRegion(region);
        cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
        cellStyle.setWrapText(true);//设置自动换行
        HSSFRow row1 = sheet.getRow(1);
        HSSFRichTextString richStringCellValue = row1.getCell(1).getRichStringCellValue();
        HSSFFont smallFont = workbook.createFont();
        smallFont.setColor(COLOR_RED);
        HSSFCell cell = row1.getCell(1);
        richStringCellValue.applyFont(3, 12, smallFont);
        cell.setCellValue(richStringCellValue);
        return workbook;
    }

    public Workbook exportExamMode(String id) {
        ExamMode examModeById = examModeFeign.findExamModeById(id);
        ExportParams exportParams = new ExportParams("导入考试人员", "考试信息表（按科目分配人员）");
        List<ExamInfo> examInfo = examModeById.getExamInfo();
        for (ExamInfo info : examInfo) {
            List<String> examStudentList = info.getExamStudentList();
            String examStudentStr = StringUtils.join(examStudentList, "；");
            info.setExamStudentStr(examStudentStr);
            List<ExamSubject> subjectList = info.getSubjectList();
            for (ExamSubject examSubject : subjectList) {
                String invigilatorStr = StringUtils.join(examSubject.getInvigilatorList(), "；");
                examSubject.setInvigilatorStr(invigilatorStr);
            }
        }
        HSSFWorkbook workbook = (HSSFWorkbook) ExcelExportUtil.exportExcel(exportParams, ExamInfo.class,examModeById.getExamInfo());
        return setUpPrompt(workbook);
    }

    public ExamMode importExamModeById(MultipartFile file, String id) {
        //监考老师集合
        Set<String> invigilators = new HashSet<>();
        //学生集合
        List<String> students = new ArrayList<>();
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(2);
        ExamMode examModeById = examModeFeign.findExamModeById(id);
        examModeById.setFileName(file.getOriginalFilename());
        List<ExamInfo> list1 = examModeById.getExamInfo();
        boolean flag1=false;
        boolean flag2=false;
        try (InputStream is = file.getInputStream()) {
            List<ExamInfo> list = ExcelImportUtil.importExcel(is, ExamInfo.class, params);
            for (int i = 0; i < list.size(); i++) {
                //错误模板
                if (Objects.isNull(list.get(i).getClassName())){
                    return null;
                }
                list1.get(i).setExamSiteNumber(list.get(i).getExamSiteNumber());
                List<ExamSubject> subjectList =list.get(i).getSubjectList();
                List<ExamSubject> subjectList1 =list1.get(i).getSubjectList();
                //examStudentStr转化为examStudentList
                String examStudentStr = list.get(i).getExamStudentStr();
                //没有科目，模板错误，返回null
                if (CollectionUtil.isEmpty(subjectList)){
                    return null;
                }
                //判断Excel中考生栏有没有填
                if (!StringUtils.isNullOrEmpty(examStudentStr)){
                    flag1=true;
                    List<String> examStudentList = Arrays.asList(examStudentStr.split("；"));
                    List<String> studentList = new ArrayList<>();
                    List<String> seatList = new ArrayList<>();
                    for (String s : examStudentList) {
                        String[] split = s.split("-");
                        if (split.length==2){
                            seatList.add(split[0]);
                            studentList.add(split[1]);
                        }else {
                            return null;
                        }
                    }
                    list1.get(i).setExamStudentList(studentList);
                    list1.get(i).setExamStudentSeatNumberList(seatList);
                    students.addAll(examStudentList);
                }else {
                    flag2=true;
                }
                for (int i1 = 0; i1 < subjectList.size(); i1++) {
                    String invigilatorStr = subjectList.get(i1).getInvigilatorStr();
                    //判断Excel中监考老师栏有没有填
                    if (!StringUtils.isNullOrEmpty(invigilatorStr)){
                        flag1=true;
                        //invigilatorStr转化为invigilatorList
                        List<String> invigilatorList = Arrays.asList(invigilatorStr.split("；"));
                        subjectList1.get(i1).setInvigilatorList(invigilatorList);
                        invigilators.addAll(invigilatorList);
                    }else {
                        flag2=true;
                    }
                }
            }
            if (!flag1){
                //考生和监考老师全都没填
                examModeFeign.updateExamInfo(examModeById);
                deleteExamInfo(id);
                return examModeById;
            }else if (flag2&&flag1){
                //有一部分填了，有一部分没填
                return null;
            }
            //考生和监考老师都填了
            examModeById.setStudentCount(students.size());
            examModeById.setInvigilatorCount(invigilators.size());
            examModeFeign.updateExamInfo(examModeById);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return examModeById;
    }
    public void deleteExamInfo(String id) {
        ExamMode examModeById = examModeFeign.findExamModeById(id);
        examModeById.setFileName(null);
        examModeById.setInvigilatorCount(null);
        examModeById.setStudentCount(null);
        List<ExamInfo> examInfo = examModeById.getExamInfo();
        List<ExamSubject> subjectList =null;
        for (ExamInfo info : examInfo) {
            info.setExamStudentList(null);
            subjectList = info.getSubjectList();
            for (ExamSubject examSubject : subjectList) {
                examSubject.setInvigilatorList(null);
            }
        }
        examModeFeign.deleteExamInfo(examModeById);
    }

    public List<ExamMode> findExamModeListByCondition(ExamMode examMode) {
        return examModeFeign.findExamModeListByCondition(examMode);
    }

    public long findExamModeCountByCondition(ExamMode examMode) {
        return examModeFeign.findExamModeCountByCondition(examMode);
    }

    public void deleteExamMode(String id) {
         examModeFeign.deleteExamMode(id);
    }


    public void close(String id) {
        examModeFeign.close(id);
    }



    public ExamMode updateExamMode(ExamMode examMode) {
        return examModeFeign.updateExamMode(examMode);
    }
}
