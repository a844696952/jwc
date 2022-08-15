package com.yice.edu.cn.osp.controller.dm.kq;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.kq.ExportKqRecord;
import com.yice.edu.cn.common.pojo.dm.kq.ExportStatisticsRecord;
import com.yice.edu.cn.common.pojo.dm.kq.ProtectedStudent;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.util.ExcelStyleUtil;
import com.yice.edu.cn.osp.service.dd.DdService;
import com.yice.edu.cn.osp.service.dm.kq.EccStudentKqRecordService;
import com.yice.edu.cn.osp.service.jw.schoolYear.SchoolYearService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RequestMapping("/eccStudentKqRecord")
@RestController
@Api(value = "/eccStudentKqRecord", description = "云班牌考勤记录")
public class EccStudentKqRecordController {
    @Autowired
    private EccStudentKqRecordService eccStudentKqRecordService;
    @Autowired
    private DdService ddService;

    @Autowired
    private SchoolYearService schoolYearService;

    @ApiOperation(value = "导出异常考勤信息", notes = "返回一个excel")
    @PostMapping("/exportExcel")
    public void exportExcel(@RequestBody ProtectedStudent protectedStudent, HttpServletResponse response) throws IOException {
        protectedStudent.setSchoolId(mySchoolId());
        List<ExportKqRecord> list = eccStudentKqRecordService.getExportKqData(protectedStudent);
        ExportParams exportParams = new ExportParams("异常考勤详情", "sheet1");
        exportParams.setStyle(ExcelStyleUtil.class);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, ExportKqRecord.class, list);
        //设置单元格宽度自适应  数据总共4列
        Sheet sheet = workbook.getSheetAt(0);
        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 17 / 10);
        }

        export(response, workbook, "异常考勤详情");
    }

    /**
     * export导出请求头设置
     *
     * @param response
     * @param workbook
     * @param fileName
     * @throws Exception
     */
    private void export(HttpServletResponse response, Workbook workbook, String fileName) throws IOException {
        response.reset();
        response.setContentType("application/vnd.ms-excel");
        fileName = fileName + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1) + ".xls");
        try (ServletOutputStream outStream = response.getOutputStream()) {
            workbook.write(outStream);
        }
    }

    @ApiOperation(value = "导出全部考勤统计信息", notes = "返回一个excel")
    @PostMapping("/exportStatisticsExcel")
    public void exportStatisticsExcel(@RequestBody ProtectedStudent protectedStudent, HttpServletResponse response) throws IOException {
        protectedStudent.setSchoolId(mySchoolId());
        List<ExportStatisticsRecord> list = eccStudentKqRecordService.getKqListByCondition(protectedStudent);
        ExportParams exportParams = new ExportParams("考勤统计", "sheet1");
        exportParams.setStyle(ExcelStyleUtil.class);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, ExportStatisticsRecord.class, list);
        export(response, workbook, "考勤统计");
    }

    @ApiOperation(value = "考勤统计列表", notes = "list数据")
    @PostMapping("/findStudentKqByCondition")
    public ResponseJson findStudentKqByCondition(@RequestBody ProtectedStudent protectedStudent) {
        protectedStudent.setSchoolId(mySchoolId());
        List studentKqByCondition = eccStudentKqRecordService.findStudentKqByCondition(protectedStudent);
        return new ResponseJson(studentKqByCondition);
    }


    @ApiOperation(value = "考勤统计列表(页面显示用)", notes = "list数据")
    @PostMapping("/getKqListByCondition")
    public ResponseJson getKqListByCondition(@RequestBody ProtectedStudent protectedStudent) {
        protectedStudent.setSchoolId(mySchoolId());
        return new ResponseJson(eccStudentKqRecordService.getKqListByCondition(protectedStudent));
    }

    @GetMapping("/findGradesCurrentSchool")
    @ApiOperation(value = "查找当前学校包含的年级", notes = "返回响应对象")
    public ResponseJson findGradesCurrentSchool() {
        Dd dd = new Dd();
        dd.setLevelType(currentTeacher().getSchool().getTypeId());
        dd.setTypeId(Constant.DD_TYPE.GRADE);
        return new ResponseJson(ddService.findDdListByCondition(dd));
    }

    @PostMapping("/findClassesListByGradeId")
    @ApiOperation(value = "查找当前年级包含的班级", notes = "返回响应对象")
    public ResponseJson findClassesListByGradeId(
            @ApiParam(value = "年级id") @RequestParam(value = "gradeId") String gradeId
    ) {
        Dd d = new Dd();
        d.setGradeId(gradeId);
        d.setSchoolId(mySchoolId());
        return new ResponseJson(ddService.findJwClassesList(d));
    }


    @GetMapping("/findCurrentSchoolRange")
    @ApiOperation(value = "查询当前学年的时间段")
    public String [] findCurrentSchoolRange(){
        return schoolYearService.findCurrentSchoolRange(mySchoolId());
    }

}
