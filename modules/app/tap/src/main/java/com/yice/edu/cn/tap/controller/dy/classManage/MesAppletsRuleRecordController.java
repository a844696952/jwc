package com.yice.edu.cn.tap.controller.dy.classManage;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.parent.Parent;
import com.yice.edu.cn.common.pojo.mes.classManage.ruleRecord.MesAppletsRuleRecord;
import com.yice.edu.cn.common.pojo.mes.classManage.ruleRecord.MesStudentRecordVo;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.tap.service.dy.classManage.MesAppletsRuleRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

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
            @RequestBody @Validated(value = GroupOne.class) MesAppletsRuleRecord mesAppletsRuleRecord){
        mesAppletsRuleRecord.setSchoolId(mySchoolId());
        mesAppletsRuleRecord.setCommentatorId(myId());
        mesAppletsRuleRecord.setCommentator(currentTeacher().getName());
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

    @GetMapping("/look/lookMesAppletsRuleRecordById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找德育小程序点评记录表", notes = "返回响应对象", response=MesAppletsRuleRecord.class)
    public ResponseJson lookMesAppletsRuleRecordById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        MesAppletsRuleRecord mesAppletsRuleRecord=mesAppletsRuleRecordService.findMesAppletsRuleRecordById(id);
        return new ResponseJson(mesAppletsRuleRecord);
    }

    @PostMapping("/findMesAppletsRuleRecordsByCondition")
    @ApiOperation(value = "根据条件查找德育小程序点评记录表", notes = "返回响应对象", response=MesAppletsRuleRecord.class)
    public ResponseJson findMesAppletsRuleRecordsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord){
        mesAppletsRuleRecord.getPager().setPage(mesAppletsRuleRecord.getQueryPage().getPage());
        mesAppletsRuleRecord.getPager().setPageSize(mesAppletsRuleRecord.getQueryPage().getPageSize());
        mesAppletsRuleRecord.setSchoolId(mySchoolId());
        mesAppletsRuleRecord.setCurrentTeacherId(myId());
        List<MesAppletsRuleRecord> data=mesAppletsRuleRecordService.findMesAppletsRuleRecordListByCondition(mesAppletsRuleRecord);
        long count=mesAppletsRuleRecordService.findMesAppletsRuleRecordCountByCondition(mesAppletsRuleRecord);
        return new ResponseJson(data,count);
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

    @PostMapping("/findHighFrequencyMesAppletsRuleRecord")
    @ApiOperation(value = "根据学生Id查找高频点评记录")
    public ResponseJson findHighFrequencyMesAppletsRuleRecord(
            @ApiParam(value = "根据学生Id查找高频点评记录")
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord) {
        List<MesAppletsRuleRecord> data=mesAppletsRuleRecordService.findHighFrequencyMesAppletsRuleRecord(mesAppletsRuleRecord);
        return new ResponseJson(data);
    }

    @GetMapping("/findCurrentTermTime")
    @ApiOperation(value = "查询当前学期起止时间")
    public ResponseJson findCurrentTermTime(){
        Map map = mesAppletsRuleRecordService.findCurrentTermTimeBySchoolId(mySchoolId());
        return new ResponseJson(map);
    }

    @PostMapping("/findMesAppletsRuleRecordByStudentIdAndSearchTime")
    @ApiOperation(value = "根据条件查找单个德育小程序点评记录表,结果必须为单条数据", notes = "返回单个德育小程序点评记录表,没有时为空")
    public ResponseJson findMesAppletsRuleRecordByStudentIdAndSearchTime(
            @ApiParam(value = "德育小程序点评记录表对象")
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord) {
        MesAppletsRuleRecord data = mesAppletsRuleRecordService.findMesAppletsRuleRecordByStudentIdAndSearchTime(mesAppletsRuleRecord);
        return new ResponseJson(data);
    }

    @PostMapping("/findScoreChangeByDay")
    @ApiOperation(value = "根据学生Id查找每日分值变化曲线")
    public ResponseJson findScoreChangeByDay(
            @ApiParam(value = "根据学生Id查找每日分值变化曲线")
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord) {
        List<MesAppletsRuleRecord> data = mesAppletsRuleRecordService.findScoreChangeByDay(mesAppletsRuleRecord);
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

    @GetMapping("/findJwParentByStudentId/{studentId}")
    @ApiOperation(value = "查找学生家长", notes = "返回响应对象,不包含总条数", response= Parent.class)
    public ResponseJson findJwParentByStudentId(
            @PathVariable("studentId")String studentId){
        List<Parent> data = mesAppletsRuleRecordService.findJwParentByStudentId(studentId);
        return new ResponseJson(data);
    }

    @PostMapping("/findClassRuleTotalList")
    @ApiOperation(value = "德育小程序总分榜单")
    public ResponseJson findClassRuleTotalList(
            @ApiParam(value = "德育小程序总分榜单")
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord){
        mesAppletsRuleRecord.setSchoolId(mySchoolId());
        List<MesAppletsRuleRecord> classRuleTotalList = mesAppletsRuleRecordService.findClassRuleTotalList(mesAppletsRuleRecord);
        return new ResponseJson(classRuleTotalList);
    }

    @PostMapping("/findClassPikChar")
    @ApiOperation(value = "德育小程序饼状图")
    public ResponseJson findClassPikChar(
            @ApiParam(value = "德育小程序饼状图")
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord){
        mesAppletsRuleRecord.setSchoolId(mySchoolId());
        MesAppletsRuleRecord classPikChar = mesAppletsRuleRecordService.findClassPikChar(mesAppletsRuleRecord);
        return new ResponseJson(classPikChar);
    }

    @PostMapping("/findClassRuleAdvanceList")
    @ApiOperation(value = "德育小程序班级进步榜")
    public ResponseJson findClassRuleAdvanceList(
            @ApiParam(value = "班级进步榜")
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord){
        mesAppletsRuleRecord.setSchoolId(mySchoolId());
        List<MesAppletsRuleRecord> classRuleAdvanceList = mesAppletsRuleRecordService.findClassRuleAdvanceList(mesAppletsRuleRecord);
        return new ResponseJson(classRuleAdvanceList);
    }

    @PostMapping("/findClassRuleBackList")
    @ApiOperation(value = "德育小程序班级退步榜")
    public ResponseJson findClassRuleBackList(
            @ApiParam(value = "班级退步榜")
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord){
        mesAppletsRuleRecord.setSchoolId(mySchoolId());
        List<MesAppletsRuleRecord> classRuleBackList = mesAppletsRuleRecordService.findClassRuleBackList(mesAppletsRuleRecord);
        return new ResponseJson(classRuleBackList);
    }

    @PostMapping("/findClassRankList")
    @ApiOperation(value = "德育小程序班级排名榜单")
    public ResponseJson findClassRankList(
            @ApiParam(value = "德育小程序班级排名榜单")
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord){
        mesAppletsRuleRecord.setSchoolId(mySchoolId());
        List<MesAppletsRuleRecord> classRankList = mesAppletsRuleRecordService.findClassRankList(mesAppletsRuleRecord);
        return new ResponseJson(classRankList);
    }

    @GetMapping("/findTeachingClassByTid")
    @ApiOperation(value = "查询当前登录老师的授课班级信息", notes = "返回响应对象,不包含总条数")
    public ResponseJson findTeachingClassByTid(){
        List<MesAppletsRuleRecord> list =  mesAppletsRuleRecordService.findTeachingClassByTid(myId(),mySchoolId());
        return new ResponseJson(list);
    }

    @GetMapping("/findMesStudentRecordVoListByCid/{classId}")
    @ApiOperation(value = "查询班级管理学生列表-班牌端", notes = "返回响应对象,不包含总条数")
    public ResponseJson findMesStudentRecordVoListByCid(
            @ApiParam(value = "班级id",readOnly = true)
            @PathVariable("classId") String classId
    ){
        List<MesStudentRecordVo> list =  mesAppletsRuleRecordService.findMesStudentRecordVoListByCid(classId,mySchoolId());
        return new ResponseJson(list);
    }

    @GetMapping("/findMesStudentRecordVoListByClassId/{classId}")
    @ApiOperation(value = "查询班级管理学生列表-教师端", notes = "返回响应对象,不包含总条数")
    public ResponseJson findMesStudentRecordVoListByClassId(
            @ApiParam(value = "班级id",readOnly = true)
            @PathVariable("classId") String classId
    ){
        List<Map<String,Object>> list =  mesAppletsRuleRecordService.findMesStudentRecordVoListByClassId(classId,mySchoolId());
        return new ResponseJson(list);
    }

}
