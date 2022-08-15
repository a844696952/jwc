package com.yice.edu.cn.bmp.controller.dy;

import com.yice.edu.cn.bmp.service.dy.MesAppletsRuleRecordService;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.mes.classManage.ruleRecord.MesAppletsRuleRecord;
import com.yice.edu.cn.common.pojo.mes.wxPush.WxPushDetail;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.currentParent;
import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/mesAppletsRuleRecord")
@Api(value = "/mesAppletsRuleRecord",description = "德育小程序点评记录表模块")
public class MesAppletsRuleRecordController {
    @Autowired
    private MesAppletsRuleRecordService mesAppletsRuleRecordService;

    @PostMapping("/saveMesAppletsRuleRecord")
    @ApiOperation(value = "保存德育小程序点评记录表对象", notes = "返回保存好的德育小程序点评记录表对象", response= MesAppletsRuleRecord.class)
    public ResponseJson saveMesAppletsRuleRecord(
            @ApiParam(value = "德育小程序点评记录表对象", required = true)
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord){
        mesAppletsRuleRecord.setSchoolId(mySchoolId());
        MesAppletsRuleRecord s=mesAppletsRuleRecordService.saveMesAppletsRuleRecord(mesAppletsRuleRecord);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findMesAppletsRuleRecordById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找德育小程序点评记录表", notes = "返回响应对象", response=MesAppletsRuleRecord.class)
    public ResponseJson findMesAppletsRuleRecordById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        MesAppletsRuleRecord mesAppletsRuleRecord=mesAppletsRuleRecordService.findMesAppletsRuleRecordById(id);
        return new ResponseJson(mesAppletsRuleRecord);
    }

    @PostMapping("/update/updateMesAppletsRuleRecord")
    @ApiOperation(value = "修改德育小程序点评记录表对象", notes = "返回响应对象")
    public ResponseJson updateMesAppletsRuleRecord(
            @ApiParam(value = "被修改的德育小程序点评记录表对象,对象属性不为空则修改", required = true)
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord){
        mesAppletsRuleRecordService.updateMesAppletsRuleRecord(mesAppletsRuleRecord);
        return new ResponseJson();
    }

    @PostMapping("/findMesAppletsRuleRecordsByCondition")
    @ApiOperation(value = "根据条件查找德育小程序点评记录表", notes = "返回响应对象", response=MesAppletsRuleRecord.class)
    public ResponseJson findMesAppletsRuleRecordsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord){
        mesAppletsRuleRecord.getPager().setPage(mesAppletsRuleRecord.getQueryPage().getPage());
        mesAppletsRuleRecord.getPager().setPageSize(mesAppletsRuleRecord.getQueryPage().getPageSize());
        List<MesAppletsRuleRecord> data=mesAppletsRuleRecordService.findMesAppletsRuleRecordListByCondition(mesAppletsRuleRecord);
        long count=mesAppletsRuleRecordService.findMesAppletsRuleRecordCountByCondition(mesAppletsRuleRecord);
        return new ResponseJson(data,count);
    }

    @GetMapping("/look/lookMesAppletsRuleRecordById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找德育小程序点评记录表", notes = "返回响应对象", response=MesAppletsRuleRecord.class)
    public ResponseJson lookMesAppletsRuleRecordById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        MesAppletsRuleRecord mesAppletsRuleRecord=mesAppletsRuleRecordService.findMesAppletsRuleRecordById(id);
        return new ResponseJson(mesAppletsRuleRecord);
    }

    @PostMapping("/findOneMesAppletsRuleRecordByCondition")
    @ApiOperation(value = "根据条件查找单个德育小程序点评记录表,结果必须为单条数据", notes = "没有时返回空", response=MesAppletsRuleRecord.class)
    public ResponseJson findOneMesAppletsRuleRecordByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord){
        MesAppletsRuleRecord one=mesAppletsRuleRecordService.findOneMesAppletsRuleRecordByCondition(mesAppletsRuleRecord);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteMesAppletsRuleRecord/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteMesAppletsRuleRecord(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        mesAppletsRuleRecordService.deleteMesAppletsRuleRecord(id);
        return new ResponseJson();
    }

    @GetMapping("/findStudentByParentId")
    @ApiOperation(value = "查询当前家长绑定的学生信息", notes = "返回响应对象,不包含总条数", response= Student.class)
    public ResponseJson findStudentByParentId(){
        List<Student> data=mesAppletsRuleRecordService.findStudentByParentId(currentParent().getId());
        return new ResponseJson(data);
    }

    @GetMapping("/findClassTeacherTelByClassesId/{classesId}")
    @ApiOperation(value = "查询当前班级班主任电话信息", notes = "返回响应对象,不包含总条数", response= Teacher.class)
    public ResponseJson findClassTeacherTel(
            @ApiParam(value = "班级id", required = true)
            @PathVariable String classesId){
        List<Teacher> data=mesAppletsRuleRecordService.findClassTeacherTelByClassesId(classesId);
        return new ResponseJson(data);
    }

    @PostMapping("/findStudentRankChange")
    @ApiOperation(value = "根据学生Id查找每日排名变化曲线")
    public ResponseJson findStudentRankChange(
            @ApiParam(value = "根据学生Id查找每日排名变化曲线")
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord) {
        List<MesAppletsRuleRecord> data = mesAppletsRuleRecordService.findStudentRankChange(mesAppletsRuleRecord);
        long data2 = mesAppletsRuleRecordService.findStudentCount(mesAppletsRuleRecord.getStudentId());
        return new ResponseJson(data,data2);
    }

    @PostMapping("/findScoreChangeByDay")
    @ApiOperation(value = "根据学生Id查找每日分值变化曲线")
    public ResponseJson findScoreChangeByDay(
            @ApiParam(value = "根据学生Id查找每日分值变化曲线")
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord) {
        List<MesAppletsRuleRecord> data = mesAppletsRuleRecordService.findScoreChangeByDay(mesAppletsRuleRecord);
        return new ResponseJson(data);
    }

    @PostMapping("/findHighFrequencyMesAppletsRuleRecord")
    @ApiOperation(value = "根据学生Id查找高频点评记录")
    public ResponseJson findHighFrequencyMesAppletsRuleRecord(
            @ApiParam(value = "根据学生Id查找高频点评记录")
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord) {
        List<MesAppletsRuleRecord> data=mesAppletsRuleRecordService.findHighFrequencyMesAppletsRuleRecord(mesAppletsRuleRecord);
        return new ResponseJson(data);
    }

    @PostMapping("/findMesAppletsRuleRecordByStudentIdAndSearchTime")
    @ApiOperation(value = "根据条件查找单个德育小程序点评记录表,结果必须为单条数据饼状图", notes = "返回单个德育小程序点评记录表,没有时为空")
    public ResponseJson findMesAppletsRuleRecordByStudentIdAndSearchTime(
            @ApiParam(value = "德育小程序点评记录表对象")
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord) {
        MesAppletsRuleRecord data = mesAppletsRuleRecordService.findMesAppletsRuleRecordByStudentIdAndSearchTime(mesAppletsRuleRecord);
        return new ResponseJson(data);
    }

    @GetMapping("/findCurrentTermTime/{studentId}")
    @ApiOperation(value = "查询当前学期起止时间")
    public ResponseJson findCurrentTermTime(@PathVariable String studentId){
        Map map = mesAppletsRuleRecordService.findCurrentTermTime(studentId);
        return new ResponseJson(map);
    }

    @PostMapping("/findMesAppletsRuleRecordListByCondition")
    @ApiOperation(value = "根据条件查找德育小程序点评记录表列表", notes = "返回响应对象,不包含总条数", response=MesAppletsRuleRecord.class)
    public ResponseJson findMesAppletsRuleRecordListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord){
        mesAppletsRuleRecord.setSchoolId(mySchoolId());
        Pager pager = new Pager();
        pager.setPaging(false);
        pager.setSortField("createTime");
        pager.setSortOrder("desc");
        mesAppletsRuleRecord.setPager(pager);
        List<MesAppletsRuleRecord> data=mesAppletsRuleRecordService.findMesAppletsRuleRecordListByCondition(mesAppletsRuleRecord);
        return new ResponseJson(data);
    }

    @GetMapping("/findWxPushDetailListByOpenId/{openId}")
    @ApiOperation(value = "根据openId查询该家长的班管通知列表")
    public ResponseJson findWxPushDetailListByOpenId(
            @ApiParam(value = "openId",readOnly = true)
            @PathVariable("openId") String openId
    ){
        List<WxPushDetail> list =  mesAppletsRuleRecordService.findWxPushDetailListByOpenId(openId);
        return new ResponseJson(list);
    }

    @GetMapping("/updateWxPushDetailRead/{id}")
    @ApiOperation(value = "根据id修改微信通知记录")
    public ResponseJson updateWxPushDetailRead(
            @ApiParam(value = "id",readOnly = true)
            @PathVariable("id") String id
    ){
        mesAppletsRuleRecordService.updateWxPushDetailRead(id);
        return new ResponseJson();
    }
}
