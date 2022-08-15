package com.yice.edu.cn.dm.controller.kq;


import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.dm.kq.EccStudentKqRecord;
import com.yice.edu.cn.common.pojo.dm.kq.ExportKqRecord;
import com.yice.edu.cn.common.pojo.dm.kq.ProtectedStudent;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.util.date.DateUtils;
import com.yice.edu.cn.common.util.jmessage.utils.StringUtils;
import com.yice.edu.cn.dm.service.kq.EccStudentKqRecordService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dengfengfeng
 */
@RequestMapping("/eccStudentKqRecord")
@RestController
public class EccStudentKqRecordController {

    @Autowired
    private EccStudentKqRecordService eccStudentKqRecordService;

    @PostMapping("/dk")
    public EccStudentKqRecord dk(@RequestBody EccStudentKqRecord record){
        return eccStudentKqRecordService.dk(record);
    }

    @PostMapping("/findOneEccStudentKqRecordByCondition")
    public EccStudentKqRecord findOneEccStudentKqRecordByCondition(@RequestBody EccStudentKqRecord record){
        return eccStudentKqRecordService.findOneEccStudentKqRecordByCondition(record);
    }

    /**
     * 查询打卡记录
     * @param record
     * @return
     */
    @PostMapping("/findEccStudentKqRecordListByCondition")
    public List<EccStudentKqRecord> findEccStudentKqRecordListByCondition(@RequestBody EccStudentKqRecord record){

        return eccStudentKqRecordService.findDmStudentKqRecordListByCondition(record);
    }


    /**
     * 查询当前实时打卡记录
     * @param protectedStudent
     * @return
     */
    @PostMapping("/findCurrentKqListByCondition")
    public List<ProtectedStudent> findCurrentKqList(@RequestBody ProtectedStudent protectedStudent){
        return eccStudentKqRecordService.getCurrentKqListByCondition(protectedStudent);
    }

    /**
     * 查询当前学生在某一个时间段内的考勤数据
     * @param protectedStudent
     * @return
     */
    @PostMapping("/findKqListByStudentId")
    public List<ProtectedStudent> findKqListByStudentId(@RequestBody ProtectedStudent protectedStudent){
       // setTimeRange(protectedStudent);
        if(StringUtils.isNotEmpty(protectedStudent.getKqBeginDate())){
            return eccStudentKqRecordService.findKqListByStudentId(protectedStudent);
        }
       return new ArrayList<>();
    }

    private void setTimeRange(@RequestBody ProtectedStudent protectedStudent) {
        if (StringUtils.isNotEmpty(protectedStudent.getKqBeginDate()) && StringUtils.isNotEmpty(protectedStudent.getKqEndDate())) {
            if (DateUtil.parse(protectedStudent.getKqEndDate(), Constant.DATE_FORMATTER_DAY).isAfterOrEquals(DateUtil.parse(DateUtil.now(), Constant.DATE_FORMATTER_DAY))) {
                protectedStudent.setKqEndDate(DateUtil.now());
            } else {
                protectedStudent.setKqEndDate(DateUtils.getOriginalDateTime(protectedStudent.getKqEndDate(), 2));
            }
            protectedStudent.setKqBeginDate(DateUtils.getOriginalDateTime(protectedStudent.getKqBeginDate(), 1));
        }
    }

    /**
     * 导出学生考勤数据
     *
     * @param protectedStudent 查询条件
     * @return 学生考勤数据
     */
    @PostMapping("/getExportKqData")
    public List<ExportKqRecord> getExportKqData(@RequestBody ProtectedStudent protectedStudent) {
        setTimeRange(protectedStudent);
        return eccStudentKqRecordService.getExportKqData(protectedStudent);
    }

    /**
     * 学生打卡
     *
     * @param student 学生信息
     * @return 错误信息
     */
    @PostMapping("/dkRecord")
    public String dkRecord(@RequestBody Student student) {
        return eccStudentKqRecordService.dkRecord(student);
    }

    @PostMapping("/findStudentKqByCondition")
    @ApiOperation(value = "根据条件查询学生考勤信息", notes = "返回考勤集合")
    public List<ProtectedStudent> findStudentKqByCondition(@RequestBody ProtectedStudent protectedStudent){
        setTimeRange(protectedStudent);
        if(StringUtils.isNotEmpty(protectedStudent.getKqBeginDate()) && StringUtils.isNotEmpty(protectedStudent.getKqEndDate())){
            return eccStudentKqRecordService.findStudentKqByCondition(protectedStudent);
        }
        return new ArrayList<>();
    }

    @PostMapping("/getKqBeginTime")
    @ApiOperation(value = "获取考勤开始时间", notes = "考勤开始时间")
    public String getKqBeginTime(@RequestBody ProtectedStudent protectedStudent){
        return eccStudentKqRecordService.getKqBeginTime(protectedStudent);
    }

}
