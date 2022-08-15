package com.yice.edu.cn.mes.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.mes.schoolManage.inspectRecord.MesInspectRecord;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesInstitution;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesUserAuthInstitution;
import com.yice.edu.cn.common.util.FileTypeUtil;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.mes.service.MesInspectRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yice.edu.cn.mes.LoginInterceptor.LoginInterceptor.*;

@RestController
@RequestMapping("/mesInspectRecord")
@Api(value = "/mesInspectRecord", description = "检查记录表模块")
public class MesInspectRecordController {

    @Autowired
    private MesInspectRecordService mesInspectRecordService;
    @Autowired
    private FileTypeUtil fileTypeUtil;

    @PostMapping("/saveMesInspectRecord")
    @ApiOperation(value = "保存检查记录表对象", notes = "返回保存好的检查记录表对象", response = MesInspectRecord.class)
    public ResponseJson saveMesInspectRecord(
            @ApiParam(value = "检查记录表对象", required = true)
            @RequestBody MesInspectRecord mesInspectRecord) {
        mesInspectRecord.setRecordUserId(myStudentId());
        mesInspectRecord.setSchoolId(mySchoolId());
        mesInspectRecord.setRecordUserName(currentStudent().getName());
        mesInspectRecord.setRecordUserType(0);
        if (mesInspectRecordService.saveMesInspectRecord(mesInspectRecord)) {
            return new ResponseJson(true, "操作成功");
        } else {
            return new ResponseJson(false, "操作失败,15分钟之内有人登记过");
        }
    }

    @GetMapping("/update/findMesInspectRecordById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找检查记录表", notes = "返回响应对象", response = MesInspectRecord.class)
    public ResponseJson findMesInspectRecordById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        MesInspectRecord mesInspectRecord = mesInspectRecordService.findMesInspectRecordById(id);
        return new ResponseJson(mesInspectRecord);
    }

    @PostMapping("/update/updateMesInspectRecord")
    @ApiOperation(value = "修改检查记录表对象", notes = "返回响应对象")
    public ResponseJson updateMesInspectRecord(
            @ApiParam(value = "被修改的检查记录表对象,对象属性不为空则修改", required = true)
            @RequestBody MesInspectRecord mesInspectRecord) {
        mesInspectRecordService.updateMesInspectRecord(mesInspectRecord);
        return new ResponseJson();
    }

    @GetMapping("/look/lookMesInspectRecordById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找检查记录表", notes = "返回响应对象", response = MesInspectRecord.class)
    public ResponseJson lookMesInspectRecordById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        MesInspectRecord mesInspectRecord = mesInspectRecordService.findMesInspectRecordById(id);
        return new ResponseJson(mesInspectRecord);
    }

    @PostMapping("/findMesInspectRecordsByCondition")
    @ApiOperation(value = "根据条件查找检查记录表", notes = "返回响应对象", response = MesInspectRecord.class)
    public ResponseJson findMesInspectRecordsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MesInspectRecord mesInspectRecord) {
        mesInspectRecord.setSchoolId(mySchoolId());
        mesInspectRecord.setRecordUserId(myStudentId());
        List<MesInspectRecord> data = mesInspectRecordService.findMesInspectRecordListByCondition(mesInspectRecord);
        long count = mesInspectRecordService.findMesInspectRecordCountByCondition(mesInspectRecord);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneMesInspectRecordByCondition")
    @ApiOperation(value = "根据条件查找单个检查记录表,结果必须为单条数据", notes = "没有时返回空", response = MesInspectRecord.class)
    public ResponseJson findOneMesInspectRecordByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody MesInspectRecord mesInspectRecord) {
        MesInspectRecord one = mesInspectRecordService.findOneMesInspectRecordByCondition(mesInspectRecord);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteMesInspectRecord/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteMesInspectRecord(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        mesInspectRecordService.deleteMesInspectRecord(id);
        return new ResponseJson();
    }

    @PostMapping("/findMesInspectRecordListByCondition")
    @ApiOperation(value = "根据条件查找检查记录表列表", notes = "返回响应对象,不包含总条数", response = MesInspectRecord.class)
    public ResponseJson findMesInspectRecordListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MesInspectRecord mesInspectRecord) {
        mesInspectRecord.setSchoolId(mySchoolId());
        List<MesInspectRecord> data = mesInspectRecordService.findMesInspectRecordListByCondition(mesInspectRecord);
        return new ResponseJson(data);
    }

    @GetMapping("/findMesInstitutionOlList")
    @ApiOperation(value = "通过用户id查询一级检查类型")
    public ResponseJson findMesInstitutionOlList() {
        List<MesInstitution> data = mesInspectRecordService.findMesInstitutionOlList(myStudentId());
        return new ResponseJson(data);
    }

    @PostMapping("/findMesInstitutionTlListByParentId")
    @ApiOperation(value = "通过条件查询该级下面的二级检查类型")
    public ResponseJson findMesInstitutionTlListByParentId(
            @ApiParam(value = "用户制度权限对象")
            @RequestBody MesUserAuthInstitution mesUserAuthInstitution
    ){
        mesUserAuthInstitution.setUserId(myStudentId());
        List<MesInstitution> data = mesInspectRecordService.findMesInstitutionTlListByParentId(mesUserAuthInstitution);
        return new ResponseJson(data);
    }

    @GetMapping("/findReference/{id}")
    @ApiOperation(value = "去查看页面,通过id查找检查记录表", notes = "返回响应对象", response = MesInspectRecord.class)
    public ResponseJson findReference(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        MesInspectRecord mesInspectRecord = mesInspectRecordService.findReference(id);
        return new ResponseJson(mesInspectRecord);
    }

    @PostMapping("/findHistoryMesInspectRecordListByCondition")
    @ApiOperation(value = "根据条件查找历史检查记录表列表", notes = "返回响应对象,不包含总条数", response = MesInspectRecord.class)
    public ResponseJson findHistoryMesInspectRecordListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody MesInspectRecord mesInspectRecord) {
        mesInspectRecord.setSchoolId(mySchoolId());
        mesInspectRecord.setRecordUserId(myStudentId());
        List<MesInspectRecord> data = mesInspectRecordService.findHistoryMesInspectRecordListByCondition(mesInspectRecord);
        return new ResponseJson(data);
    }

    @GetMapping("/findClassesByGradeId/{id}")
    @ApiOperation(value = "根据年级id查找班级", notes = "返回班级信息")
    public ResponseJson findClassesByGradeId(
            @ApiParam(value = "年级id", required = true) @PathVariable String id
    ){
        JwClasses jwClasses = new JwClasses();
        jwClasses.setGradeId(id);
        jwClasses.setSchoolId(mySchoolId());
        List<JwClasses> jwClassesList = mesInspectRecordService.findClassesByGradeId(jwClasses);;

        if(CollUtil.isNotEmpty(jwClassesList)){
            Collections.sort(jwClassesList,(o1, o2) -> {
                return Integer.parseInt(o1.getNumber()) - Integer.parseInt(o2.getNumber());
            } );
        }
        return new ResponseJson(jwClassesList);
    }

    @GetMapping("/findGradesCurrentSchool")
    @ApiOperation(value = "查找当前学校包含的年级", notes = "返回响应对象")
    public ResponseJson findGradesCurrentSchool(){
        List<Dd> data=mesInspectRecordService.findDdListBySchoolId(mySchoolId());
        return new ResponseJson(data);
    }

    @GetMapping("/findMesInspectRecordOneById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找检查记录表", notes = "返回响应对象", response = MesInspectRecord.class)
    public ResponseJson findMesInspectRecordOneById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        MesInspectRecord mesInspectRecord = mesInspectRecordService.findMesInspectRecordOneById(id);
        return new ResponseJson(mesInspectRecord);
    }

    @PostMapping("/upload")
    @ApiOperation(value = "上传文件", notes = "返回状态和资源的url")
    public ResponseJson upload(@ApiParam(value = "上传的文件", required = true) MultipartFile file) {
        Map<String, Object> map = new HashMap<>(16);
        //文件名后缀
        String originalFilename = file.getOriginalFilename();
        String suffix = StrUtil.subAfter(originalFilename, ".", true);
        int suffixInt = fileTypeUtil.setResouceType(suffix);
        if (suffixInt == 0) {
            return new ResponseJson(false, "不支持的文件格式");
        }
        map.put("fileName", StrUtil.subBefore(originalFilename, ".", true));
        map.put("fileType", suffixInt == 2 ? 1 : 2);
        map.put("filePath", QiniuUtil.commonUploadFile(file, Constant.Upload.UPLOAD_DY_FILE + suffix));
        return new ResponseJson(map);
    }

    @PostMapping("/ifHaveRecord")
    @ApiOperation(value = "先判断一小时之内是否有人登记该学生", notes = "返回Boolean")
    public ResponseJson ifHaveRecord(
            @ApiParam(value = "先判断一小时之内是否有人登记该学生", required = true)
            @RequestBody MesInspectRecord mesInspectRecord) {
        if (mesInspectRecordService.ifHaveRecord(mesInspectRecord)) {
            return new ResponseJson(true, "操作成功");
        } else {
            return new ResponseJson(false, "1h之内已有人登记");
        }
    }
}
