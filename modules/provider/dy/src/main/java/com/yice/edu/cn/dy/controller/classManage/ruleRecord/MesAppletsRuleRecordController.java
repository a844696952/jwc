package com.yice.edu.cn.dy.controller.classManage.ruleRecord;

import com.yice.edu.cn.common.pojo.jw.parent.Parent;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.mes.classManage.ruleRecord.MesAppletsRuleRecord;
import com.yice.edu.cn.common.pojo.mes.classManage.ruleRecord.MesStudentRecordVo;
import com.yice.edu.cn.common.pojo.mes.wxPush.WxPushDetail;
import com.yice.edu.cn.dy.service.classManage.ruleRecord.MesAppletsRuleRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mesAppletsRuleRecord")
@Api(value = "/mesAppletsRuleRecord", description = "德育小程序点评记录表模块")
public class MesAppletsRuleRecordController {
    @Autowired
    private MesAppletsRuleRecordService mesAppletsRuleRecordService;

    @GetMapping("/findMesAppletsRuleRecordById/{id}")
    @ApiOperation(value = "通过id查找德育小程序点评记录表", notes = "返回德育小程序点评记录表对象")
    public MesAppletsRuleRecord findMesAppletsRuleRecordById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        return mesAppletsRuleRecordService.findMesAppletsRuleRecordById(id);
    }

    @PostMapping("/saveMesAppletsRuleRecord")
    @ApiOperation(value = "保存德育小程序点评记录表", notes = "返回德育小程序点评记录表对象")
    public MesAppletsRuleRecord saveMesAppletsRuleRecord(
            @ApiParam(value = "德育小程序点评记录表对象", required = true)
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord) {
        mesAppletsRuleRecordService.saveMesAppletsRuleRecord(mesAppletsRuleRecord);
        return mesAppletsRuleRecord;
    }

    @PostMapping("/findMesAppletsRuleRecordListByCondition")
    @ApiOperation(value = "根据条件查找德育小程序点评记录表列表", notes = "返回德育小程序点评记录表列表")
    public List<MesAppletsRuleRecord> findMesAppletsRuleRecordListByCondition(
            @ApiParam(value = "德育小程序点评记录表对象")
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord) {
        return mesAppletsRuleRecordService.findMesAppletsRuleRecordListByCondition(mesAppletsRuleRecord);
    }

    @PostMapping("/findTodayMesAppletsRuleRecordList")
    @ApiOperation(value = "查找班干部今日记录内容", notes = "返回德育小程序点评记录表列表")
    public List<MesAppletsRuleRecord> findTodayMesAppletsRuleRecordList(
            @ApiParam(value = "德育小程序点评记录表对象")
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord
    ){
        return mesAppletsRuleRecordService.findTodayMesAppletsRuleRecordList(mesAppletsRuleRecord);
    }

    @PostMapping("/findMesAppletsRuleRecordCountByCondition")
    @ApiOperation(value = "根据条件查找德育小程序点评记录表列表个数", notes = "返回德育小程序点评记录表总个数")
    public long findMesAppletsRuleRecordCountByCondition(
            @ApiParam(value = "德育小程序点评记录表对象")
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord) {
        return mesAppletsRuleRecordService.findMesAppletsRuleRecordCountByCondition(mesAppletsRuleRecord);
    }

    @PostMapping("/updateMesAppletsRuleRecord")
    @ApiOperation(value = "修改德育小程序点评记录表", notes = "德育小程序点评记录表对象必传")
    public void updateMesAppletsRuleRecord(
            @ApiParam(value = "德育小程序点评记录表对象,对象属性不为空则修改", required = true)
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord) {
        mesAppletsRuleRecordService.updateMesAppletsRuleRecord(mesAppletsRuleRecord);
    }

    @GetMapping("/deleteMesAppletsRuleRecord/{id}")
    @ApiOperation(value = "通过id删除德育小程序点评记录表")
    public void deleteMesAppletsRuleRecord(
            @ApiParam(value = "德育小程序点评记录表对象", required = true)
            @PathVariable String id) {
        mesAppletsRuleRecordService.deleteMesAppletsRuleRecord(id);
    }

    @PostMapping("/deleteMesAppletsRuleRecordByCondition")
    @ApiOperation(value = "根据条件删除德育小程序点评记录表")
    public void deleteMesAppletsRuleRecordByCondition(
            @ApiParam(value = "德育小程序点评记录表对象")
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord) {
        mesAppletsRuleRecordService.deleteMesAppletsRuleRecordByCondition(mesAppletsRuleRecord);
    }

    @PostMapping("/findHighFrequencyMesAppletsRuleRecord")
    @ApiOperation(value = "根据学生Id查找高频点评记录")
    public List<MesAppletsRuleRecord> findHighFrequencyMesAppletsRuleRecord(
            @ApiParam(value = "根据学生Id查找高频点评记录")
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord) {
        return mesAppletsRuleRecordService.findHighFrequencyMesAppletsRuleRecord(mesAppletsRuleRecord);
    }

    @PostMapping("/findClassRuleTotalList")
    @ApiOperation(value = "德育小程序总分榜单")
    public List<MesAppletsRuleRecord> findClassRuleTotalList(
        @ApiParam(value = "德育小程序总分榜单")
        @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord){
        mesAppletsRuleRecordService.setSearchTime(mesAppletsRuleRecord);
        return mesAppletsRuleRecordService.findClassRuleRecordList(mesAppletsRuleRecord);
    }

    @PostMapping("/findClassPikChar")
    @ApiOperation(value = "德育小程序饼状图")
    public MesAppletsRuleRecord findClassPikChar(
            @ApiParam(value = "德育小程序饼状图")
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord){
          mesAppletsRuleRecordService.setSearchTime(mesAppletsRuleRecord);
         return mesAppletsRuleRecordService.findClassListByRange(mesAppletsRuleRecord);
    }

    @PostMapping("/findClassRuleAdvanceList")
    @ApiOperation(value = "德育小程序班级进步榜")
    public List<MesAppletsRuleRecord> findClassRuleAdvanceList(
            @ApiParam(value = "班级进步榜")
             @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord){
        return  mesAppletsRuleRecordService.findClassRuleAdvanceList(mesAppletsRuleRecord,1);
    }


    @PostMapping("/findClassRuleBackList")
    @ApiOperation(value = "德育小程序班级退步榜")
    public List<MesAppletsRuleRecord> findClassRuleBackList(
            @ApiParam(value = "班级退步榜")
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord){
        return  mesAppletsRuleRecordService.findClassRuleAdvanceList(mesAppletsRuleRecord,2);
    }


    @PostMapping("/findClassRankList")
    @ApiOperation(value = "德育小程序班级排名榜")
    public List<MesAppletsRuleRecord> findClassRankList(
            @ApiParam(value = "班级排名榜")
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord){
        mesAppletsRuleRecordService.setSearchTime(mesAppletsRuleRecord);
        return  mesAppletsRuleRecordService.findClassRankList(mesAppletsRuleRecord);
    }


    @PostMapping("/findScoreChangeByDay")
    @ApiOperation(value = "根据学生Id查找每日分值变化曲线")
    public List<MesAppletsRuleRecord> findScoreChangeByDay(
            @ApiParam(value = "根据学生Id查找每日分值变化曲线")
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord) {
        return mesAppletsRuleRecordService.findScoreChangeByDay(mesAppletsRuleRecord);
    }

    @PostMapping("/findStudentRankChange")
    @ApiOperation(value = "根据学生Id查找每日排名变化曲线")
    public List<MesAppletsRuleRecord> findStudentRankChange(
            @ApiParam(value = "根据学生Id查找每日排名变化曲线")
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord) {
        return mesAppletsRuleRecordService.findStudentRankChange(mesAppletsRuleRecord);
    }

    @GetMapping("/findStudentCount/{studentId}")
    @ApiOperation(value = "根据学生Id查找班级总人数")
    public long findStudentCount(
            @ApiParam(value = "根据学生Id查找班级总人数")
            @PathVariable("studentId") String studentId) {
        return mesAppletsRuleRecordService.findStudentCount(studentId);
    }

    @PostMapping("/findOneMesAppletsRuleRecordByCondition")
    @ApiOperation(value = "根据条件查找单个德育小程序点评记录表,结果必须为单条数据", notes = "返回单个德育小程序点评记录表,没有时为空")
    public MesAppletsRuleRecord findOneMesAppletsRuleRecordByCondition(
            @ApiParam(value = "德育小程序点评记录表对象")
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord) {
        return mesAppletsRuleRecordService.findOneMesAppletsRuleRecordByCondition(mesAppletsRuleRecord);
    }

    @PostMapping("/findMesAppletsRuleRecordByStudentIdAndSearchTime")
    @ApiOperation(value = "根据条件查找单个德育小程序点评记录表,结果必须为单条数据", notes = "返回单个德育小程序点评记录表,没有时为空")
    public MesAppletsRuleRecord findMesAppletsRuleRecordByStudentIdAndSearchTime(
            @ApiParam(value = "德育小程序点评记录表对象")
            @RequestBody MesAppletsRuleRecord mesAppletsRuleRecord) {
        return mesAppletsRuleRecordService.findMesAppletsRuleRecordByStudentIdAndSearchTime(mesAppletsRuleRecord);
    }

    @GetMapping("/findStudentByParentId/{parentId}")
    @ApiOperation(value = "查询当前家长绑定的学生信息", notes = "返回响应对象,不包含总条数", response= Student.class)
    public List<Student> findStudentByParentId(
            @PathVariable("parentId")String parentId){
        return mesAppletsRuleRecordService.findStudentByParentId(parentId);

    }

    @GetMapping("/findJwParentByStudentId/{studentId}")
    @ApiOperation(value = "查找学生家长", notes = "返回响应对象,不包含总条数", response= Parent.class)
    public List<Parent> findJwParentByStudentId(
            @PathVariable("studentId")String studentId){
        return mesAppletsRuleRecordService.findJwParentByStudentId(studentId);
    }

    @GetMapping("/findTeachingClassByTid")
    @ApiOperation(value = "查询当前登录老师的授课班级信息", notes = "返回响应对象,不包含总条数")
    public List<MesAppletsRuleRecord> findTeachingClassByTid(
            @ApiParam(value = "教师id",readOnly = true)
            @RequestParam("teacherId") String teacherId, @RequestParam("schoolId") String schoolId
    ){
        return mesAppletsRuleRecordService.findTeachingClassByTid(teacherId,schoolId);
    }

    @GetMapping("/findMesStudentRecordVoListByCid")
    @ApiOperation(value = "查询班级管理学生列表-班牌端", notes = "返回响应对象,不包含总条数")
    public List<MesStudentRecordVo> findMesStudentRecordVoListByCid(
            @ApiParam(value = "班级id",readOnly = true)
            @RequestParam("classId") String classId,@RequestParam("schoolId") String schoolId
    ){
        return mesAppletsRuleRecordService.findMesStudentRecordVoListByCid(classId,schoolId);
    }

    @GetMapping("/findMesStudentRecordVoListByClassId")
    @ApiOperation(value = "查询班级管理学生列表-教师端", notes = "返回响应对象,不包含总条数")
    public List<Map<String,Object>> findMesStudentRecordVoListByClassId(
            @ApiParam(value = "班级id",readOnly = true)
            @RequestParam("classId") String classId,@RequestParam("schoolId") String schoolId
    ){
        return mesAppletsRuleRecordService.findMesStudentRecordVoListByClassId(classId,schoolId);
    }


    @GetMapping("/findClassTeacherTelByClassesId/{classesId}")
    @ApiOperation(value = "查询当前班级班主任电话信息", notes = "返回响应对象,不包含总条数", response= Teacher.class)
    public List<Teacher> findClassTeacherTelByClassesId(
            @PathVariable("classesId")String classesId){
        return mesAppletsRuleRecordService.findClassTeacherTelByClassesId(classesId);

    }

    @GetMapping("/findCurrentTermTime/{studentId}")
    @ApiOperation(value = "查询当前学期起止时间")
    public Map findCurrentTermTime(
            @PathVariable("studentId")String studentId){
        return mesAppletsRuleRecordService.findCurrentTermTime(studentId);

    }

    @GetMapping("/findCurrentTermTimeBySchoolId/{schoolId}")
    @ApiOperation(value = "查询当前学期起止时间")
    public Map findCurrentTermTimeBySchoolId(
            @PathVariable("schoolId")String schoolId){
        return mesAppletsRuleRecordService.findCurrentTermTimeBySchoolId(schoolId);

    }

    @GetMapping("/findJwClaCadresBySid/{studentId}")
    @ApiOperation(value = "查询当前学生是否为班干部")
    public Boolean findJwClaCadresBySid(
            @PathVariable("studentId") String studentId
    ){
        return mesAppletsRuleRecordService.findJwClaCadresBySid(studentId);
    }

    @GetMapping("/findTodayRecordOidAndSid")
    @ApiOperation(value = "查询记录表当天学生id和所关联的家长openId")
    public List<MesStudentRecordVo> findTodayRecordOidAndSid(){
        return mesAppletsRuleRecordService.findTodayRecordOidAndSid();
    }

    @GetMapping("/findClassIdByWeeks")
    @ApiOperation(value = "查询记录表这一周班级id")
    public List<String> findClassIdByWeeks(){
        return mesAppletsRuleRecordService.findClassIdByWeeks();
    }

    @GetMapping("/findMesStudentRecordVoBySid/{studentId}")
    @ApiOperation(value = "根据学生id查询所关联的家长id以及学生姓名")
    public List<MesStudentRecordVo> findMesStudentRecordVoBySid(
            @ApiParam(value = "学生id",readOnly = true)
            @PathVariable("studentId") String studentId
    ){
        return mesAppletsRuleRecordService.findMesStudentRecordVoBySid(studentId);
    }

    @GetMapping("/findWxPushDetailListByOpenId/{openId}")
    @ApiOperation(value = "根据openId查询该家长的班管通知列表")
    public List<WxPushDetail> findWxPushDetailListByOpenId(
            @ApiParam(value = "openId",readOnly = true)
            @PathVariable("openId") String openId
    ){
        return mesAppletsRuleRecordService.findWxPushDetailListByOpenId(openId);
    }

    @GetMapping("/updateWxPushDetailRead/{id}")
    @ApiOperation(value = "根据id修改微信通知记录")
    public void updateWxPushDetailRead(
            @ApiParam(value = "id",readOnly = true)
            @PathVariable("id") String id
    ){
        mesAppletsRuleRecordService.updateWxPushDetailRead(id);
    }
}
