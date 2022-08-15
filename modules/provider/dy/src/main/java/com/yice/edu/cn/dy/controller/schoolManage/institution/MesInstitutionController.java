package com.yice.edu.cn.dy.controller.schoolManage.institution;

import com.yice.edu.cn.common.pojo.jw.schoolWeek.SchoolWeek;
import com.yice.edu.cn.common.pojo.mes.schoolManage.schoolEvaluation.MesInstitutionStudent;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesCommonConfig;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesCustomTitle;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesInstitution;
import com.yice.edu.cn.dy.service.schoolManage.institution.MesInstitutionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mesInstitution")
@Api(value = "/mesInstitution", description = "制度表模块")
public class MesInstitutionController {
    @Autowired
    private MesInstitutionService mesInstitutionService;

    @GetMapping("/findMesInstitutionById/{id}")
    @ApiOperation(value = "通过id查找制度表", notes = "返回制度表对象")
    public MesInstitution findMesInstitutionById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        return mesInstitutionService.findMesInstitutionById(id);
    }

    @PostMapping("/saveMesInstitution")
    @ApiOperation(value = "保存制度表", notes = "返回制度表对象")
    public MesInstitution saveMesInstitution(
            @ApiParam(value = "制度表对象", required = true)
            @RequestBody MesInstitution mesInstitution) {
        return mesInstitutionService.saveMesInstitution(mesInstitution);
    }

    @PostMapping("/findMesInstitutionListByCondition")
    @ApiOperation(value = "根据条件查找编辑中的制度表列表", notes = "返回制度表列表")
    public List<MesInstitution> findMesInstitutionListByCondition(
            @ApiParam(value = "制度表对象")
            @RequestBody MesInstitution mesInstitution) {
        return mesInstitutionService.findMesInstitutionListByCondition(mesInstitution);
    }

    @PostMapping("/findMesInstitutionEditing")
    @ApiOperation(value = "根据条件查找编辑中的制度表列表", notes = "返回制度表列表")
    public List<MesInstitution> findMesInstitutionEditing(
            @ApiParam(value = "制度表对象")
            @RequestBody MesInstitution mesInstitution) {
        return mesInstitutionService.findMesInstitutionEditing(mesInstitution);
    }

    @PostMapping("/findMesInstitutionsByCondition")
    @ApiOperation(value = "根据条件查找当前生效制度表", notes = "返回制度表列表")
    public List<MesInstitution> findMesInstitutionsByCondition(
            @ApiParam(value = "制度表对象")
            @RequestBody MesInstitution mesInstitution) {
        return mesInstitutionService.findMesInstitutionsByCondition(mesInstitution);
    }

    @PostMapping("/findMesInstitutionCountByCondition")
    @ApiOperation(value = "根据条件查找制度表列表个数", notes = "返回制度表总个数")
    public long findMesInstitutionCountByCondition(
            @ApiParam(value = "制度表对象")
            @RequestBody MesInstitution mesInstitution) {
        return mesInstitutionService.findMesInstitutionCountByCondition(mesInstitution);
    }

    @PostMapping("/updateMesInstitution")
    @ApiOperation(value = "修改制度表", notes = "制度表对象必传")
    public void updateMesInstitution(
            @ApiParam(value = "制度表对象,对象属性不为空则修改", required = true)
            @RequestBody MesInstitution mesInstitution) {
        mesInstitutionService.updateMesInstitution(mesInstitution);
    }

    @PostMapping("/batchSaveMesInstitution")
    @ApiOperation(value = "批量新增制度表", notes = "制度表集合必传")
    public Map<String, List<MesCustomTitle>> batchSaveMesInstitution(
            @ApiParam(value = "制度表对象集合", required = true)
            @RequestBody List<MesInstitution> mesInstitutions) {
        return mesInstitutionService.batchSaveMesInstitution(mesInstitutions);
    }

    @GetMapping("/deleteMesInstitution/{id}")
    @ApiOperation(value = "通过id删除制度表")
    public void deleteMesInstitution(
            @ApiParam(value = "制度表对象", required = true)
            @PathVariable String id) {
        mesInstitutionService.deleteMesInstitution(id);
    }

    @GetMapping("/deleteMesInstitutionWhereTimeStatusIdIsNull/{id}")
    @ApiOperation(value = "通过id删除制度表")
    public void deleteMesInstitutionWhereTimeStatusIdIsNull(
            @ApiParam(value = "制度表对象", required = true)
            @PathVariable String id) {
        mesInstitutionService.deleteMesInstitutionWhereTimeStatusIdIsNull(id);
    }

    @PostMapping("/deleteMesInstitutionByCondition")
    @ApiOperation(value = "根据条件删除制度表")
    public void deleteMesInstitutionByCondition(
            @ApiParam(value = "制度表对象")
            @RequestBody MesInstitution mesInstitution) {
        mesInstitutionService.deleteMesInstitutionByCondition(mesInstitution);
    }

    @PostMapping("/findOneMesInstitutionByCondition")
    @ApiOperation(value = "根据条件查找单个制度表,结果必须为单条数据", notes = "返回单个制度表,没有时为空")
    public MesInstitution findOneMesInstitutionByCondition(
            @ApiParam(value = "制度表对象")
            @RequestBody MesInstitution mesInstitution) {
        return mesInstitutionService.findOneMesInstitutionByCondition(mesInstitution);
    }

    @GetMapping("/findAllJwClassesAndStudents/{schoolId}")
    @ApiOperation(value = "德育制度设置管理人员学生树")
    public List<MesInstitutionStudent> findAllJwClassesAndStudents(
            @ApiParam(value = "学校id")
            @PathVariable("schoolId") String schoolId) {
        return mesInstitutionService.findAllJwClassesAndStudents(schoolId);
    }

    @PostMapping("/findInstitutionScore")
    @ApiOperation(value = "柱形图")
    public Map<String, Object> findsdfjgdkhljgk(
            @ApiParam(value = "学校id")
            @RequestBody MesCommonConfig mesCommonConfig) {
        return mesInstitutionService.findInstitutionScore(mesCommonConfig);
    }

    @GetMapping("/findSchoolYearsBySchoolId/{schoolId}")
    @ApiOperation(value = "根据学校id查学年")
    public List<SchoolWeek> findSchoolYearsBySchoolId(
            @ApiParam(value = "学校id")
            @PathVariable("schoolId") String schoolId) {
        return mesInstitutionService.findSchoolYearsBySchoolId(schoolId);
    }

    @GetMapping("/findSchoolYearListBySchoolId/{schoolId}")
    @ApiOperation(value = "根据学校id查学年")
    public List<SchoolWeek> findSchoolYearListBySchoolId(
            @ApiParam(value = "学校id")
            @PathVariable("schoolId") String schoolId) {
        return mesInstitutionService.findSchoolYearListBySchoolId(schoolId);
    }

    @PostMapping("/saveSchoolWeek")
    @ApiOperation(value = "保存学周")
    public void saveSchoolWeek(
            @ApiParam(value = "学校id")
            @RequestBody List<SchoolWeek> schoolWeeks) {
        mesInstitutionService.saveSchoolWeek(schoolWeeks);
    }

    @GetMapping("/findAllJwClassesBySchoolId/{schoolId}")
    @ApiOperation(value = "根据学校id查年级")
    public List<MesInstitutionStudent> findAllJwClassesBySchoolId(
            @ApiParam(value = "学校id")
            @PathVariable("schoolId") String schoolId) {
        return mesInstitutionService.findAllJwClassesBySchoolId(schoolId);
    }

}
