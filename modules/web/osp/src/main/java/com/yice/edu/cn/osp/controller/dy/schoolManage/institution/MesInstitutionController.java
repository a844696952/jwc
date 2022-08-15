package com.yice.edu.cn.osp.controller.dy.schoolManage.institution;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.schoolWeek.SchoolWeek;
import com.yice.edu.cn.common.pojo.mes.schoolManage.schoolEvaluation.MesInstitutionStudent;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesCommonConfig;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesCustomTitle;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesInstitution;
import com.yice.edu.cn.osp.service.dy.schoolManage.institution.MesInstitutionService;
import com.yice.edu.cn.osp.service.login.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/mesInstitution")
@Api(value = "/mesInstitution", description = "制度表模块")
public class MesInstitutionController {
    @Autowired
    private MesInstitutionService mesInstitutionService;
    @Autowired
    private LoginService loginService;

    @PostMapping("/saveMesInstitution")
    @ApiOperation(value = "保存制度表对象", notes = "返回保存好的制度表对象", response = MesInstitution.class)
    public ResponseJson saveMesInstitution(
            @ApiParam(value = "制度表对象", required = true)
            @RequestBody MesInstitution mesInstitution) {
        mesInstitution.setSchoolId(mySchoolId());
        MesInstitution s = mesInstitutionService.saveMesInstitution(mesInstitution);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findMesInstitutionById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找制度表", notes = "返回响应对象", response = MesInstitution.class)
    public ResponseJson findMesInstitutionById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        MesInstitution mesInstitution = mesInstitutionService.findMesInstitutionById(id);
        return new ResponseJson(mesInstitution);
    }

    @PostMapping("/update/updateMesInstitution")
    @ApiOperation(value = "修改制度表对象", notes = "返回响应对象")
    public ResponseJson updateMesInstitution(
            @ApiParam(value = "被修改的制度表对象,对象属性不为空则修改", required = true)
            @RequestBody MesInstitution mesInstitution) {
        mesInstitution.setSchoolId(mySchoolId());
        mesInstitutionService.updateMesInstitution(mesInstitution);
        return new ResponseJson();
    }

    @PostMapping("/batchSaveMesInstitution")
    @ApiOperation(value = "批量新增制度表", notes = "返回响应对象")
    public ResponseJson batchSaveMesInstitution(
            @ApiParam(value = "制度表对象,对象属性不为空则修改", required = true)
            @RequestBody List<MesInstitution> mesInstitutions) {
        Map<String, List<MesCustomTitle>> data = mesInstitutionService.batchSaveMesInstitution(mesInstitutions);
        if (data==null){
            return new ResponseJson(false,"保存失败,同级制度名不能重复");
        }
        return new ResponseJson(data);
    }

    @GetMapping("/look/lookMesInstitutionById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找制度表", notes = "返回响应对象", response = MesInstitution.class)
    public ResponseJson lookMesInstitutionById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        MesInstitution mesInstitution = mesInstitutionService.findMesInstitutionById(id);
        return new ResponseJson(mesInstitution);
    }

    @PostMapping("/findMesInstitutionsByCondition")
    @ApiOperation(value = "根据条件查找当前生效制度表", notes = "返回响应对象", response = MesInstitution.class)
    public ResponseJson findMesInstitutionsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MesInstitution mesInstitution) {
        mesInstitution.setSchoolId(mySchoolId());
        List<MesInstitution> data = mesInstitutionService.findMesInstitutionsByCondition(mesInstitution);
        long count = mesInstitutionService.findMesInstitutionCountByCondition(mesInstitution);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findMesInstitutionEditing")
    @ApiOperation(value = "根据条件查找当前生效制度表", notes = "返回响应对象", response = MesInstitution.class)
    public ResponseJson findMesInstitutionEditing(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MesInstitution mesInstitution) {
        mesInstitution.setSchoolId(mySchoolId());
        List<MesInstitution> data = mesInstitutionService.findMesInstitutionEditing(mesInstitution);
        return new ResponseJson(data);
    }

    @PostMapping("/findOneMesInstitutionByCondition")
    @ApiOperation(value = "根据条件查找单个制度表,结果必须为单条数据", notes = "没有时返回空", response = MesInstitution.class)
    public ResponseJson findOneMesInstitutionByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody MesInstitution mesInstitution) {
        mesInstitution.setSchoolId(mySchoolId());
        MesInstitution one = mesInstitutionService.findOneMesInstitutionByCondition(mesInstitution);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteMesInstitution/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteMesInstitution(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        mesInstitutionService.deleteMesInstitution(id);
        return new ResponseJson();
    }

    @GetMapping("/deleteMesInstitutionWhereTimeStatusIdIsNull")
    @ApiOperation(value = "编辑前，先删除上次草稿", notes = "返回响应对象")
    public ResponseJson deleteMesInstitutionWhereTimeStatusIdIsNull() {
        mesInstitutionService.deleteMesInstitutionWhereTimeStatusIdIsNull(mySchoolId());
        return new ResponseJson();
    }

    @PostMapping("/findMesInstitutionListByCondition")
    @ApiOperation(value = "根据条件查找编辑中的制度表列表", notes = "返回响应对象,不包含总条数", response = MesInstitution.class)
    public ResponseJson findMesInstitutionListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MesInstitution mesInstitution) {
        mesInstitution.setSchoolId(mySchoolId());
        List<MesInstitution> data = mesInstitutionService.findMesInstitutionListByCondition(mesInstitution);
        return new ResponseJson(data);
    }

    @GetMapping("/findAllJwClassesAndStudents")
    @ApiOperation(value = "德育制度设置管理人员学生树")
    public ResponseJson findAllJwClassesAndStudents() {
        List<MesInstitutionStudent> jwClassesList = mesInstitutionService.findAllJwClassesAndStudents(mySchoolId());
        return new ResponseJson(jwClassesList);
    }

    @GetMapping("/findAllJwClassesBySchoolId")
    @ApiOperation(value = "德育制度设置管理人员学生树")
    public ResponseJson findAllJwClassesBySchoolId() {
        List<MesInstitutionStudent> jwClassesList = mesInstitutionService.findAllJwClassesBySchoolId(mySchoolId());
        return new ResponseJson(jwClassesList);
    }

    @PostMapping("/findInstitutionScore")
    @ApiOperation(value = "德育制度设置管理人员学生树")
    public ResponseJson findInstitutionScore(
            @ApiParam(value = "学校id")
            @RequestBody MesCommonConfig mesCommonConfig) {
        mesCommonConfig.setSchoolId(mySchoolId());
        Map<String, Object> data = mesInstitutionService.findInstitutionScore(mesCommonConfig);
        return new ResponseJson(data);
    }

    @GetMapping("/findSchoolYearsBySchoolId")
    @ApiOperation(value = "根据学校id查学年")
    public ResponseJson findSchoolYearsBySchoolId() {
        List<SchoolWeek> data = mesInstitutionService.findSchoolYearsBySchoolId(mySchoolId());
        return new ResponseJson(data);
    }

    @GetMapping("/findSchoolYearListBySchoolId")
    @ApiOperation(value = "根据学校id查学年")
    public ResponseJson findSchoolYearListBySchoolId() {
        List<SchoolWeek> data = mesInstitutionService.findSchoolYearListBySchoolId(mySchoolId());
        return new ResponseJson(data);
    }

    @GetMapping("/findSchoolYearIsExist")
    @ApiOperation(value = "根据学校id查学年是否创建")
    public ResponseJson findSchoolYearIsExist() {
        HashMap<String, Boolean> map = new HashMap<>();
        Integer riseGradeStatus = loginService.riseGradeStatus(mySchoolId());
        if(riseGradeStatus==null || riseGradeStatus.intValue()== Constant.SCHOOL_RISE_RECORD.NO_SET_TIME_RISE || riseGradeStatus.intValue()==Constant.SCHOOL_RISE_RECORD.ERROR_RISE) {
            map.put("isExist",false);
            return new ResponseJson(map);
        }
        if(riseGradeStatus.intValue()!=Constant.SCHOOL_RISE_RECORD.NOT_BEGIN_RISE && riseGradeStatus.intValue()!=Constant.SCHOOL_RISE_RECORD.HAS_RISE) {
            map.put("isExist",false);
            return new ResponseJson(map);
        }
        map.put("isExist",true);
        return new ResponseJson(map);
    }

    @PostMapping("/saveSchoolWeek")
    @ApiOperation(value = "保存学周")
    public ResponseJson saveSchoolWeek(
            @ApiParam(value = "学校id")
            @RequestBody List<SchoolWeek> schoolWeeks) {
        mesInstitutionService.saveSchoolWeek(schoolWeeks);
        return new ResponseJson();
    }

}
