package com.yice.edu.cn.osp.controller.dy.schoolManage;

import com.yice.edu.cn.common.annotation.EccJpush;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.mes.schoolManage.inspectRecord.MesInspectRecord;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.osp.service.dy.schoolManage.MesInspectRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/mesInspectRecord")
@Api(value = "/mesInspectRecord",description = "检查记录表模块")
public class MesInspectRecordController {
    @Autowired
    private MesInspectRecordService mesInspectRecordService;

    @PostMapping("/saveMesInspectRecord")
    @ApiOperation(value = "保存检查记录表对象", notes = "返回保存好的检查记录表对象", response= MesInspectRecord.class)
    @EccJpush(type = Extras.DY_ECC_InspectRecord,content = "新增扣分记录")
    public ResponseJson saveMesInspectRecord(
            @ApiParam(value = "检查记录表对象", required = true)
            @RequestBody MesInspectRecord mesInspectRecord){
       mesInspectRecord.setSchoolId(mySchoolId());
        MesInspectRecord s=mesInspectRecordService.saveMesInspectRecord(mesInspectRecord);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findMesInspectRecordById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找检查记录表", notes = "返回响应对象", response=MesInspectRecord.class)
    public ResponseJson findMesInspectRecordById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        MesInspectRecord mesInspectRecord=mesInspectRecordService.findMesInspectRecordById(id);
        return new ResponseJson(mesInspectRecord);
    }

    @GetMapping("/findMesInspectRecordListByClassId/{classId}")
    @ApiOperation(value = "根据班级ID查询最新六条检查记录", notes = "返回检查记录表对象", response=MesInspectRecord.class)
    public ResponseJson findMesInspectRecordListByClassId(
            @ApiParam(value = "检查记录表对象", required = true)
            @PathVariable("classId") String classId){
        Map<String, List<MesInspectRecord>> data=mesInspectRecordService.findMesInspectRecordListByClassId(classId);
        return new ResponseJson(data);
    }

    @PostMapping("/update/updateMesInspectRecord")
    @ApiOperation(value = "修改检查记录表对象", notes = "返回响应对象")
    public ResponseJson updateMesInspectRecord(
            @ApiParam(value = "被修改的检查记录表对象,对象属性不为空则修改", required = true)
            @RequestBody MesInspectRecord mesInspectRecord){
        mesInspectRecordService.updateMesInspectRecord(mesInspectRecord);
        return new ResponseJson();
    }

    @PostMapping("/findMesInspectRecordByClassId")
    @ApiOperation(value = "根据班级ID查询检查记录表", notes = "返回检查记录表对象")
    public ResponseJson findMesInspectRecordByClassId(
            @ApiParam(value = "检查记录表对象", required = true)
            @RequestBody MesInspectRecord mesInspectRecord){
        mesInspectRecord.setSchoolId(mySchoolId());
        Map<String,Object> data= mesInspectRecordService.findMesInspectRecordByClassId(mesInspectRecord);
        long count= mesInspectRecordService.findMesInspectRecordCountByClassId(mesInspectRecord);
        return new ResponseJson(data,count);
    }

    @GetMapping("/look/lookMesInspectRecordById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找检查记录表", notes = "返回响应对象", response=MesInspectRecord.class)
    public ResponseJson lookMesInspectRecordById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        MesInspectRecord mesInspectRecord=mesInspectRecordService.findMesInspectRecordById(id);
        return new ResponseJson(mesInspectRecord);
    }

    @GetMapping("/findMesInspectRecordByRecordId/{id}")
    @ApiOperation(value = "通过id查找检查记录表", notes = "返回检查记录表对象")
    public ResponseJson findMesInspectRecordByRecordId(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        Map data = mesInspectRecordService.findMesInspectRecordByRecordId(id);
        return new ResponseJson(data);
    }

    @PostMapping("/findMesInspectRecordsByCondition")
    @ApiOperation(value = "根据条件查找检查记录表", notes = "返回响应对象", response=MesInspectRecord.class)
    public ResponseJson findMesInspectRecordsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MesInspectRecord mesInspectRecord){
       mesInspectRecord.setSchoolId(mySchoolId());
        List<MesInspectRecord> data=mesInspectRecordService.findMesInspectRecordListByCondition(mesInspectRecord);
        long count=mesInspectRecordService.findMesInspectRecordCountByCondition(mesInspectRecord);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneMesInspectRecordByCondition")
    @ApiOperation(value = "根据条件查找单个检查记录表,结果必须为单条数据", notes = "没有时返回空", response=MesInspectRecord.class)
    public ResponseJson findOneMesInspectRecordByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody MesInspectRecord mesInspectRecord){
        MesInspectRecord one=mesInspectRecordService.findOneMesInspectRecordByCondition(mesInspectRecord);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteMesInspectRecord/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteMesInspectRecord(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        mesInspectRecordService.deleteMesInspectRecord(id);
        return new ResponseJson();
    }


    @PostMapping("/findMesInspectRecordListByCondition")
    @ApiOperation(value = "根据条件查找检查记录表列表", notes = "返回响应对象,不包含总条数", response=MesInspectRecord.class)
    public ResponseJson findMesInspectRecordListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MesInspectRecord mesInspectRecord){
       mesInspectRecord.setSchoolId(mySchoolId());
        List<MesInspectRecord> data=mesInspectRecordService.findMesInspectRecordListByCondition(mesInspectRecord);
        return new ResponseJson(data);
    }

    @GetMapping("/findTlInstitutionStatistics/{type}")
    @ApiOperation("根据条件查找对应范围的德育高频事件统计")
    public ResponseJson findTlInstitutionStatistics(
            @ApiParam("统计查询范围条件 1->本周，2->本月，3->本学期")
            @PathVariable("type") Integer type
    ){
        List<MesInspectRecord> data = mesInspectRecordService.findTlInstitutionStatistics(type,mySchoolId());
        return new ResponseJson(data);
    }

    @GetMapping("/findRadarStatistics/{type}")
    @ApiOperation("根据条件查找对应范围的德育雷达统计数据")
    public ResponseJson findRadarStatistics(
            @ApiParam("统计查询范围条件 1->本周，2->本月，3->本学期")
            @PathVariable("type") Integer type
    ){
        List<MesInspectRecord> data = mesInspectRecordService.findRadarStatistics(type,mySchoolId());
        return new ResponseJson(data);
    }

}
