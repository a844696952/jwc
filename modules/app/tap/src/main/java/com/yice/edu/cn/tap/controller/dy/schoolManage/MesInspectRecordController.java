package com.yice.edu.cn.tap.controller.dy.schoolManage;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.mes.schoolManage.inspectRecord.MesInspectRecord;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesInstitution;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesUserAuthInstitution;
import com.yice.edu.cn.common.pojo.mes.schoolManage.query.MirQuery;
import com.yice.edu.cn.tap.service.dy.schoolManage.MesInspectRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.*;

@RestController
@RequestMapping("/mesInspectRecord")
@Api(value = "/mesInspectRecord",description = "检查记录表模块")
public class MesInspectRecordController {
    @Autowired
    private MesInspectRecordService mesInspectRecordService;

    @PostMapping("/saveMesInspectRecord")
    @ApiOperation(value = "保存检查记录表对象", notes = "返回保存好的检查记录表对象", response= MesInspectRecord.class)
    public ResponseJson saveMesInspectRecord(
            @ApiParam(value = "检查记录表对象", required = true)
            @RequestBody MesInspectRecord mesInspectRecord){
        mesInspectRecord.setSchoolId(mySchoolId());
        mesInspectRecord.setRecordUserId(myId());
        mesInspectRecord.setRecordUserName(currentTeacher().getName());
        mesInspectRecord.setRecordUserType(1);
        try {
            if(mesInspectRecordService.saveMesInspectRecord(mesInspectRecord)){
                return new ResponseJson(true,"提交成功");
            }else {
                return new ResponseJson(false,"提交失败,15分钟之内有人登记过");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseJson(false,"提交失败，服务器出错");
        }

    }


    @GetMapping("/update/findMesInspectRecordById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找检查记录表", notes = "返回响应对象", response=MesInspectRecord.class)
    public ResponseJson findMesInspectRecordById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        MesInspectRecord mesInspectRecord=mesInspectRecordService.findMesInspectRecordById(id);
        return new ResponseJson(mesInspectRecord);
    }

    @PostMapping("/update/updateMesInspectRecord")
    @ApiOperation(value = "修改检查记录表对象", notes = "返回响应对象")
    public ResponseJson updateMesInspectRecord(
            @ApiParam(value = "被修改的检查记录表对象,对象属性不为空则修改", required = true)
            @RequestBody MesInspectRecord mesInspectRecord){
        mesInspectRecordService.updateMesInspectRecord(mesInspectRecord);
        return new ResponseJson();
    }

    @GetMapping("/look/lookMesInspectRecordById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找检查记录表", notes = "返回响应对象", response=MesInspectRecord.class)
    public ResponseJson lookMesInspectRecordById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        MesInspectRecord mesInspectRecord=mesInspectRecordService.findMesInspectRecordById(id);
        return new ResponseJson(mesInspectRecord);
    }

    @PostMapping("/findMesInspectRecordsByCondition")
    @ApiOperation(value = "根据条件查找检查记录表", notes = "返回响应对象", response=MesInspectRecord.class)
    public ResponseJson findMesInspectRecordsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MesInspectRecord mesInspectRecord){
        mesInspectRecord.setSchoolId(mySchoolId());
        mesInspectRecord.setRecordUserId(myId());
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
        mesInspectRecord.setRecordUserId(myId());
        Pager pager = new Pager();
        pager.setPaging(false);
        pager.setSortField("createTime");
        pager.setSortOrder("desc");
        mesInspectRecord.setPager(pager);
        List<MesInspectRecord> data=mesInspectRecordService.findMesInspectRecordListByCondition(mesInspectRecord);
        return new ResponseJson(data);
    }

    @GetMapping("/findMesInstitutionOlList")
    @ApiOperation(value = "通过用户id查询一级检查类型")
    public ResponseJson findMesInstitutionOlList(){
        List<MesInstitution> data = mesInspectRecordService.findMesInstitutionOlList(myId());
        return new ResponseJson(data);
    }

    @PostMapping("/findMesInstitutionTlListByParentId")
    @ApiOperation(value = "通过条件查询该级下面的二级检查类型")
    public ResponseJson findMesInstitutionTlListByParentId(
            @ApiParam(value = "用户制度权限对象")
            @RequestBody MesUserAuthInstitution mesUserAuthInstitution
    ){
        mesUserAuthInstitution.setUserId(myId());
        List<MesInstitution> data = mesInspectRecordService.findMesInstitutionTlListByParentId(mesUserAuthInstitution);
        return new ResponseJson(data);
    }


    @PostMapping("/findMirAndClassId")
    @ApiOperation(value = "根据条件查找检查记录表列表", notes = "返回响应对象,不包含总条数", response=ResponseJson.class)
    public ResponseJson findMirAndClassId(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MirQuery mirQuery){
        if(Objects.isNull(currentTeacher())){
            return new ResponseJson(false,"当前登录用户信息错误");
        }
        mirQuery.setSchoolId(mySchoolId());
        return mesInspectRecordService.findMirAndClassId(mirQuery,currentTeacher());
    }



    @GetMapping("/findReference/{id}")
    @ApiOperation(value = "去查看页面,通过id查找检查记录表", notes = "返回响应对象", response=MesInspectRecord.class)
    public ResponseJson findReference(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        MesInspectRecord mesInspectRecord=mesInspectRecordService.findReference(id);
        return new ResponseJson(mesInspectRecord);
    }


}
