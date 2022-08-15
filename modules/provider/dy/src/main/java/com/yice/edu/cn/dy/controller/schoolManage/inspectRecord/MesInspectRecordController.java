package com.yice.edu.cn.dy.controller.schoolManage.inspectRecord;

import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.mes.schoolManage.inspectRecord.MesInspectRecord;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesInstitution;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesUserAuthInstitution;
import com.yice.edu.cn.common.pojo.mes.schoolManage.query.MirQuery;
import com.yice.edu.cn.dy.service.schoolManage.inspectRecord.MesInspectRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mesInspectRecord")
@Api(value = "/mesInspectRecord",description = "检查记录表模块")
public class MesInspectRecordController {
    @Autowired
    private MesInspectRecordService mesInspectRecordService;

    @GetMapping("/findMesInspectRecordById/{id}")
    @ApiOperation(value = "通过id查找检查记录表", notes = "返回检查记录表对象")
    public MesInspectRecord findMesInspectRecordById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return mesInspectRecordService.findMesInspectRecordById(id);
    }

    @GetMapping("/findMesInspectRecordByRecordId/{id}")
    @ApiOperation(value = "通过id查找检查记录表", notes = "返回检查记录表对象")
    public Map findMesInspectRecordByRecordId(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return mesInspectRecordService.findMesInspectRecordByRecordId(id);
    }

    @PostMapping("/saveMesInspectRecord")
    @ApiOperation(value = "保存检查记录表", notes = "返回检查记录表对象")
    public Boolean saveMesInspectRecord(
            @ApiParam(value = "检查记录表对象", required = true)
            @RequestBody MesInspectRecord mesInspectRecord){
        return mesInspectRecordService.saveMesInspectRecord(mesInspectRecord);
    }

    @PostMapping("/ifHaveRecord")
    @ApiOperation(value = "先判断一小时之内是否有人登记该学生", notes = "返回Boolean")
    public Boolean ifHaveRecord(
            @ApiParam(value = "先判断一小时之内是否有人登记该学生", required = true)
            @RequestBody MesInspectRecord mesInspectRecord) {
        return mesInspectRecordService.ifHaveRecord(mesInspectRecord);
    }

    @PostMapping("/findMesInspectRecordByClassId")
    @ApiOperation(value = "根据班级ID查询检查记录表", notes = "返回检查记录表对象")
    public Map<String,Object> findMesInspectRecordByClassId(
            @ApiParam(value = "检查记录表对象", required = true)
            @RequestBody MesInspectRecord mesInspectRecord){
        return mesInspectRecordService.findMesInspectRecordByClassId(mesInspectRecord);
    }


    @PostMapping("/findMesInspectRecordCountByClassId")
    @ApiOperation(value = "根据班级ID查询检查记录表", notes = "返回检查记录表对象")
    public long findMesInspectRecordCountByClassId(
            @ApiParam(value = "检查记录表对象", required = true)
            @RequestBody MesInspectRecord mesInspectRecord){
        return mesInspectRecordService.findMesInspectRecordCountByClassId(mesInspectRecord);
    }

    @PostMapping("/findMesInspectRecordListByCondition")
    @ApiOperation(value = "根据条件查找检查记录表列表", notes = "返回检查记录表列表")
    public List<MesInspectRecord> findMesInspectRecordListByCondition(
            @ApiParam(value = "检查记录表对象")
            @RequestBody MesInspectRecord mesInspectRecord){
        return mesInspectRecordService.findMesInspectRecordListByCondition(mesInspectRecord);
    }
    @PostMapping("/findMesInspectRecordCountByCondition")
    @ApiOperation(value = "根据条件查找检查记录表列表个数", notes = "返回检查记录表总个数")
    public long findMesInspectRecordCountByCondition(
            @ApiParam(value = "检查记录表对象")
            @RequestBody MesInspectRecord mesInspectRecord){
        return mesInspectRecordService.findMesInspectRecordCountByCondition(mesInspectRecord);
    }

    @GetMapping("/findMesInspectRecordListByClassId/{classId}")
    @ApiOperation(value = "根据条件查找检查记录表列表个数", notes = "返回检查记录表总个数")
    public Map<String, List<MesInspectRecord>> findMesInspectRecordListBysdg(
            @ApiParam(value = "检查记录表对象")
            @PathVariable("classId") String classId){
        return mesInspectRecordService.findMesInspectRecordListByClassId(classId);
    }

    @PostMapping("/updateMesInspectRecord")
    @ApiOperation(value = "修改检查记录表", notes = "检查记录表对象必传")
    public void updateMesInspectRecord(
            @ApiParam(value = "检查记录表对象,对象属性不为空则修改", required = true)
            @RequestBody MesInspectRecord mesInspectRecord){
        mesInspectRecordService.updateMesInspectRecord(mesInspectRecord);
    }

    @GetMapping("/deleteMesInspectRecord/{id}")
    @ApiOperation(value = "通过id删除检查记录表")
    public void deleteMesInspectRecord(
            @ApiParam(value = "检查记录表对象", required = true)
            @PathVariable String id){
        mesInspectRecordService.deleteMesInspectRecord(id);
    }
    @PostMapping("/deleteMesInspectRecordByCondition")
    @ApiOperation(value = "根据条件删除检查记录表")
    public void deleteMesInspectRecordByCondition(
            @ApiParam(value = "检查记录表对象")
            @RequestBody MesInspectRecord mesInspectRecord){
        mesInspectRecordService.deleteMesInspectRecordByCondition(mesInspectRecord);
    }
    @PostMapping("/findOneMesInspectRecordByCondition")
    @ApiOperation(value = "根据条件查找单个检查记录表,结果必须为单条数据", notes = "返回单个检查记录表,没有时为空")
    public MesInspectRecord findOneMesInspectRecordByCondition(
            @ApiParam(value = "检查记录表对象")
            @RequestBody MesInspectRecord mesInspectRecord){
        return mesInspectRecordService.findOneMesInspectRecordByCondition(mesInspectRecord);
    }

    @GetMapping("/findMesInstitutionOlList/{userId}")
    @ApiOperation(value = "通过用户id查询一级检查类型")
    public List<MesInstitution> findMesInstitutionOlList(
            @ApiParam(value = "用户id")
            @PathVariable("userId") String userId
    ){
        return mesInspectRecordService.findMesInstitutionOlList(userId);
    }

    @PostMapping("/findMesInstitutionTlListByParentId")
    @ApiOperation(value = "通过条件查询该级下面的二级检查类型")
    public List<MesInstitution> findMesInstitutionTlListByParentId(
            @ApiParam(value = "用户制度权限对象")
            @RequestBody MesUserAuthInstitution mesUserAuthInstitution
    ){
        return mesInspectRecordService.findMesInstitutionTlListByParentId(mesUserAuthInstitution);
    }




    @PostMapping("/findMirAndClassId")
    @ApiOperation(value = " 根据老师的带班（班主任） 班级Id去查相关的检查记录", notes = "返回检查记录表列表")
    public List<MesInspectRecord> findMirAndClassId(
            @ApiParam(value = "老师职务班级表和记录表相关参数的封装VO")
            @RequestBody MirQuery query){
        return mesInspectRecordService.findMirAndClassId(query);
    }


    @PostMapping("/findMirAndClassIdCount")
    @ApiOperation(value = "根据老师的带班（班主任） 班级Id去查相关的检查记录列表个数", notes = "返回检查记录表总个数")
    public long findMirAndClassIdCount(
            @ApiParam(value = "老师职务班级表和记录表相关参数的封装VO")
            @RequestBody MirQuery query){
        return mesInspectRecordService.findMirAndClassIdCount(query);
    }


    @GetMapping("/findReference/{id}")
    @ApiOperation(value = "通过id查找检查记录表", notes = "返回检查记录表对象")
    public MesInspectRecord findreference(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return mesInspectRecordService.findReference(id);
    }

    @PostMapping("/findHistoryMesInspectRecordListByCondition")
    @ApiOperation(value = "根据条件查找历史检查记录表列表", notes = "返回检查记录表")
    public List<MesInspectRecord> findHistoryMesInspectRecordListByCondition(
            @ApiParam(value = "检查记录表对象")
            @RequestBody MesInspectRecord mesInspectRecord){
        return mesInspectRecordService.findHistoryMesInspectRecordListByCondition(mesInspectRecord);
    }

    @GetMapping("/findTlInstitutionStatistics")
    @ApiOperation("根据条件查找对应范围的德育高频事件统计")
    public List<MesInspectRecord> findTlInstitutionStatistics(
            @ApiParam("统计查询范围条件和学校id")
            @RequestParam("type") Integer type, @RequestParam("schoolId") String schoolId
    ){
        return mesInspectRecordService.findTlInstitutionStatistics(type,schoolId);
    }

    @GetMapping("/findRadarStatistics")
    @ApiOperation("根据条件查找对应范围的德育雷达统计数据")
    public List<MesInspectRecord> findRadarStatistics(
            @ApiParam("统计查询范围条件和学校id")
            @RequestParam("type") Integer type, @RequestParam("schoolId") String schoolId
    ){
        return mesInspectRecordService.findRadarStatistics(type,schoolId);
    }

    @GetMapping("/findMesInspectRecordOneById/{id}")
    @ApiOperation(value = "通过id查找检查记录表", notes = "返回检查记录表对象")
    public MesInspectRecord findMesInspectRecordOneById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return mesInspectRecordService.findMesInspectRecordOneById(id);
    }


    @PostMapping("/judgeTeacher")
    @ApiOperation(value = "查询当前teacher是否用值日教师", notes = "long不为0标识有值日教师")
    public long judgeTeacher(
            @ApiParam(value = "教师对象")
            @RequestBody Teacher teacher){
        return mesInspectRecordService.judgeTeacher(teacher);
    }

}
