package com.yice.edu.cn.osp.controller.dm.ycVeriface;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherPost;
import com.yice.edu.cn.common.pojo.xw.kq.KqDailyStatistical;
import com.yice.edu.cn.common.pojo.xw.kq.KqOriginalData;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherService;
import com.yice.edu.cn.osp.service.xw.kq.KqOriginalDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/YcVerifaceOriginalData")
@Api(value = "/YcVerifaceOriginalData", description = "亿策人脸识别原始记录")
public class YcVerifaceOriginalDataController {
    @Autowired
    private KqOriginalDataService kqOriginalDataService;
    @Autowired
    private TeacherService teacherService;

    @PostMapping("/saveKqOriginalData")
    @ApiOperation(value = "保存考勤打卡原始记录表对象", notes = "返回保存好的考勤打卡原始记录表对象", response = KqOriginalData.class)
    public ResponseJson saveKqOriginalData(
            @ApiParam(value = "考勤打卡原始记录表对象", required = true)
            @RequestBody KqOriginalData kqOriginalData) {
        kqOriginalData.setSchoolId(mySchoolId());
        KqOriginalData s = kqOriginalDataService.saveKqOriginalData(kqOriginalData);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findKqOriginalDataById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找考勤打卡原始记录表", notes = "返回响应对象", response = KqOriginalData.class)
    public ResponseJson findKqOriginalDataById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        KqOriginalData kqOriginalData = kqOriginalDataService.findKqOriginalDataById(id);
        return new ResponseJson(kqOriginalData);
    }

    @PostMapping("/update/updateKqOriginalData")
    @ApiOperation(value = "修改考勤打卡原始记录表对象", notes = "返回响应对象")
    public ResponseJson updateKqOriginalData(
            @ApiParam(value = "被修改的考勤打卡原始记录表对象,对象属性不为空则修改", required = true)
            @RequestBody KqOriginalData kqOriginalData) {
        kqOriginalDataService.updateKqOriginalData(kqOriginalData);
        return new ResponseJson();
    }

    @GetMapping("/look/lookKqOriginalDataById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找考勤打卡原始记录表", notes = "返回响应对象", response = KqOriginalData.class)
    public ResponseJson lookKqOriginalDataById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        KqOriginalData kqOriginalData = kqOriginalDataService.findKqOriginalDataById(id);
        return new ResponseJson(kqOriginalData);
    }

    @PostMapping("/find/findKqOriginalDatasByCondition")
    @ApiOperation(value = "根据条件查找考勤打卡原始记录表", notes = "返回响应对象", response = KqOriginalData.class)
    public ResponseJson findKqOriginalDatasByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqOriginalData kqOriginalData) {
       if (kqOriginalData.getTimeZone()!=null&&kqOriginalData.getTimeZone().length==2){
            kqOriginalData.getPager().setRangeField("capturedTime");
            kqOriginalData.getTimeZone()[0] = kqOriginalData.getTimeZone()[0]+" 00:00:00";
            kqOriginalData.getTimeZone()[1] = kqOriginalData.getTimeZone()[1]+" 23:59:59";
            kqOriginalData.getPager().setRangeArray(kqOriginalData.getTimeZone());
            kqOriginalData.setTimeZone(null);
        }else {
           kqOriginalData.setTimeZone(null);
       }

        kqOriginalData.setSchoolId(mySchoolId());
       kqOriginalData.setDeviceFactory(Constant.KQ_ORIGINAL_DATA.DEVICE_FACTORY_YC);
        List<KqOriginalData> data = kqOriginalDataService.findKqOriginalDataListByCondition(kqOriginalData);
        long count = kqOriginalDataService.findKqOriginalDataCountByCondition(kqOriginalData);
        return new ResponseJson(data, count);
    }

    @PostMapping("/find/findOneKqOriginalDataByCondition")
    @ApiOperation(value = "根据条件查找单个考勤打卡原始记录表,结果必须为单条数据", notes = "没有时返回空", response = KqOriginalData.class)
    public ResponseJson findOneKqOriginalDataByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody KqOriginalData kqOriginalData) {
        KqOriginalData one = kqOriginalDataService.findOneKqOriginalDataByCondition(kqOriginalData);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteKqOriginalData/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteKqOriginalData(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        kqOriginalDataService.deleteKqOriginalData(id);
        return new ResponseJson();
    }


    @PostMapping("/find/findKqOriginalDataListByCondition")
    @ApiOperation(value = "根据条件查找考勤打卡原始记录表列表", notes = "返回响应对象,不包含总条数", response = KqOriginalData.class)
    public ResponseJson findKqOriginalDataListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqOriginalData kqOriginalData) {
        kqOriginalData.setSchoolId(mySchoolId());
        List<KqOriginalData> data = kqOriginalDataService.findKqOriginalDataListByCondition(kqOriginalData);
        return new ResponseJson(data);
    }

    @PostMapping("/judgePunchStatusByrules")
    @ApiOperation(value = "根据特殊日期考勤规则判断学生打卡状态", notes = "返回响应对象,不包含总条数", response = KqDailyStatistical.class)
    public ResponseJson judgePunchStatusByrules(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqOriginalData kqOriginalData) {
        kqOriginalData.setSchoolId(mySchoolId());
        KqDailyStatistical data = kqOriginalDataService.judgePunchStatusByrules(kqOriginalData);
        return new ResponseJson(data);
    }

    @PostMapping("/dayBasicRecords")
    @ApiOperation(value = "根据普通考勤规则判断学生打卡状态", notes = "返回响应对象,不包含总条数", response = KqDailyStatistical.class)
    public ResponseJson dayBasicRecords(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqOriginalData kqOriginalData) {
        kqOriginalData.setSchoolId(mySchoolId());
        KqDailyStatistical data = kqOriginalDataService.dayBasicRecords(kqOriginalData);
        return new ResponseJson(data);
    }

    @PostMapping("/find/inTimeCountByRules")
    @ApiOperation(value = "即时统计", notes = "返回响应对象,不包含总条数", response = KqOriginalData.class)
    public ResponseJson inTimeCountByRules(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqOriginalData kqOriginalData) {
        kqOriginalData.setSchoolId(mySchoolId());
        KqDailyStatistical data = kqOriginalDataService.inTimeCountByRules(kqOriginalData);
        return new ResponseJson(data);
    }

    @PostMapping("/find/findKqStudentOriginalDatasByConditionAndPower")
    @ApiOperation(value = "根据权限和条件查找学生考勤打卡原始记录表", notes = "返回响应对象", response = KqOriginalData.class)
    public ResponseJson findKqStudentOriginalDatasByConditionAndPower(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqOriginalData kqOriginalData) {
        if (kqOriginalData.getUserType()==null||kqOriginalData.getUserType().equals("")){
            kqOriginalData.setUserType("2,4");
        }
        if (kqOriginalData.getTimeZone()!=null&&kqOriginalData.getTimeZone().length==2){
            kqOriginalData.getPager().setRangeField("capturedTime");
            kqOriginalData.getTimeZone()[0] = kqOriginalData.getTimeZone()[0]+" 00:00:00";
            kqOriginalData.getTimeZone()[1] = kqOriginalData.getTimeZone()[1]+" 23:59:59";
            kqOriginalData.getPager().setRangeArray(kqOriginalData.getTimeZone());
            kqOriginalData.setTimeZone(null);
        }else {
            kqOriginalData.setTimeZone(null);
        }
        if (kqOriginalData.getPassStatus()==null) {
            kqOriginalData.setPassStatus("-1");
        }
        kqOriginalData.setSchoolId(mySchoolId());
        kqOriginalData.setDeviceFactory(Constant.KQ_ORIGINAL_DATA.DEVICE_FACTORY_ZY);
        List<TeacherPost> teacherPost = teacherService.findTeacherPost(myId());
        teacherPost = teacherPost.stream().filter(teacherPost1 -> teacherPost1.getSort()!=null).collect(Collectors.toList());
        teacherPost = teacherPost.stream().sorted(Comparator.comparing(TeacherPost::getSort)).collect(Collectors.toList());
        if (teacherPost.size()==0){
            return new ResponseJson( );
        }
        String ddId = teacherPost.get(0).getDdId();
        final TeacherPost teacherPost1 = teacherPost.get(0);
        if (ddId.equals(Constant.TEACHER_POST.GRADE_LEADER)) {//段长
            if (kqOriginalData.getGradeId() != null && kqOriginalData.getGradeId().equals(teacherPost1.getGradeId())) {
                //返回本年段
                kqOriginalData.setGradeId(teacherPost1.getGradeId());
            } else if (kqOriginalData.getGradeId() == null) {
                //返回本年段
                kqOriginalData.setGradeId(teacherPost1.getGradeId());
            } else if (kqOriginalData.getGradeId() != null && !kqOriginalData.getGradeId().equals(teacherPost1.getGradeId())) {
                //是否是任班主任年段
                List<TeacherPost> classHeader = teacherPost.stream().filter(t -> t.getDdId().equals(Constant.TEACHER_POST.CLASS_TEACHER)).collect(Collectors.toList());
                if (classHeader.size() > 0) {
                    //是班主任，判断指定的年段是否是班主任所在年段
                    TeacherPost teacherPost2 = classHeader.get(0);
                    if (kqOriginalData.getGradeId().equals(teacherPost2.getGradeId())) {
                        kqOriginalData.setGradeId(teacherPost2.getGradeId());
                        kqOriginalData.setClassId(teacherPost2.getClassId());
                    } else {
                        return new ResponseJson(false, "您在该年段没有查看的权限");
                    }
                }
            }
        } else if (ddId.equals(Constant.TEACHER_POST.CLASS_TEACHER)) {//班主任
            //返回本年段
            kqOriginalData.setClassId(teacherPost1.getClassId());
        } else if (ddId.equals(Constant.TEACHER_POST.PRINCIPAL)) {//校长
            //返回所有年段
        }else {
            return new ResponseJson(false, "您没有查看的权限");
        }
        List<KqOriginalData> data = kqOriginalDataService.findKqOriginalDataListByCondition(kqOriginalData);
        long count = kqOriginalDataService.findKqOriginalDataCountByCondition(kqOriginalData);
        return new ResponseJson(data, count);
    }

    @PostMapping("/find/findKqStudentOriginalDatasByCondition")
    @ApiOperation(value = "根据条件查找学生考勤打卡原始记录表", notes = "返回响应对象", response = KqOriginalData.class)
    public ResponseJson findKqStudentOriginalDatasByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqOriginalData kqOriginalData) {
        if (kqOriginalData.getUserType()==null||kqOriginalData.getUserType().equals("")){
            kqOriginalData.setUserType("2,4");
        }
        if (kqOriginalData.getTimeZone()!=null&&kqOriginalData.getTimeZone().length==2){
            kqOriginalData.getPager().setRangeField("capturedTime");
            kqOriginalData.getTimeZone()[0] = kqOriginalData.getTimeZone()[0]+" 00:00:00";
            kqOriginalData.getTimeZone()[1] = kqOriginalData.getTimeZone()[1]+" 23:59:59";
            kqOriginalData.getPager().setRangeArray(kqOriginalData.getTimeZone());
            kqOriginalData.setTimeZone(null);
        }else {
            kqOriginalData.setTimeZone(null);
        }
        kqOriginalData.setSchoolId(mySchoolId());
        kqOriginalData.setDeviceFactory(Constant.KQ_ORIGINAL_DATA.DEVICE_FACTORY_ZY);
        List<KqOriginalData> data = kqOriginalDataService.findKqOriginalDataListByCondition(kqOriginalData);
        long count = kqOriginalDataService.findKqOriginalDataCountByCondition(kqOriginalData);
        return new ResponseJson(data, count);
    }
    @PostMapping("/find/findTeacherKqOriginalDatasByCondition")
    @ApiOperation(value = "根据条件查找教师考勤打卡原始记录表", notes = "返回响应对象", response = KqOriginalData.class)
    public ResponseJson findTeacherKqOriginalDatasByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqOriginalData kqOriginalData) {
        if (kqOriginalData.getUserType()==null||kqOriginalData.getUserType().equals("")){
            kqOriginalData.setUserType("1,3");
        }
        if (kqOriginalData.getTimeZone()!=null&&kqOriginalData.getTimeZone().length==2){
            kqOriginalData.getPager().setRangeField("capturedTime");
            kqOriginalData.getTimeZone()[0] = kqOriginalData.getTimeZone()[0]+" 00:00:00";
            kqOriginalData.getTimeZone()[1] = kqOriginalData.getTimeZone()[1]+" 23:59:59";
            kqOriginalData.getPager().setRangeArray(kqOriginalData.getTimeZone());
            kqOriginalData.setTimeZone(null);
        }else {
            kqOriginalData.setTimeZone(null);
        }
        kqOriginalData.setSchoolId(mySchoolId());
        List<KqOriginalData> data = kqOriginalDataService.findKqOriginalDataListByCondition(kqOriginalData);
        long count = kqOriginalDataService.findKqOriginalDataCountByCondition(kqOriginalData);
        return new ResponseJson(data, count);
    }
    @PostMapping("/find/findTeacherSelfKqOriginalDatasByCondition")
    @ApiOperation(value = "根据条件查找教师考勤打卡原始记录表", notes = "返回响应对象", response = KqOriginalData.class)
    public ResponseJson findTeacherSelfKqOriginalDatasByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqOriginalData kqOriginalData) {
        if (kqOriginalData.getUserType()==null||kqOriginalData.getUserType().equals("")){
            kqOriginalData.setUserType("1,3");
        }
        if (kqOriginalData.getTimeZone()!=null&&kqOriginalData.getTimeZone().length==2){
            kqOriginalData.getPager().setRangeField("capturedTime");
            kqOriginalData.getTimeZone()[0] = kqOriginalData.getTimeZone()[0]+" 00:00:00";
            kqOriginalData.getTimeZone()[1] = kqOriginalData.getTimeZone()[1]+" 23:59:59";
            kqOriginalData.getPager().setRangeArray(kqOriginalData.getTimeZone());
            kqOriginalData.setTimeZone(null);
        }else {
            kqOriginalData.setTimeZone(null);
        }
        kqOriginalData.setSchoolId(mySchoolId());
        kqOriginalData.setUserId(myId());
        List<KqOriginalData> data = kqOriginalDataService.findKqOriginalDataListByCondition(kqOriginalData);
        long count = kqOriginalDataService.findKqOriginalDataCountByCondition(kqOriginalData);
        return new ResponseJson(data, count);
    }
}
