package com.yice.edu.cn.jw.controller.student;

import com.yice.edu.cn.common.pojo.jw.student.JwStudentGraduation;
import com.yice.edu.cn.jw.service.student.JwStudentGraduationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jwStudentGraduation")
@Api(value = "/jwStudentGraduation",description = "模块")
public class JwStudentGraduationController {
    @Autowired
    private JwStudentGraduationService jwStudentGraduationService;

    @GetMapping("/findJwStudentGraduationById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public JwStudentGraduation findJwStudentGraduationById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return jwStudentGraduationService.findJwStudentGraduationById(id);
    }

    @PostMapping("/saveJwStudentGraduation")
    @ApiOperation(value = "保存", notes = "返回对象")
    public JwStudentGraduation saveJwStudentGraduation(
            @ApiParam(value = "对象", required = true)
            @RequestBody JwStudentGraduation jwStudentGraduation){
        jwStudentGraduationService.saveJwStudentGraduation(jwStudentGraduation);
        return jwStudentGraduation;
    }

    @PostMapping("/findJwStudentGraduationListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<JwStudentGraduation> findJwStudentGraduationListByCondition(
            @ApiParam(value = "对象")
            @RequestBody JwStudentGraduation jwStudentGraduation){
        List<JwStudentGraduation> jwStudentGraduationList = jwStudentGraduationService.findJwStudentGraduationListByCondition(jwStudentGraduation);
        return jwStudentGraduationList;
    }
    @PostMapping("/findJwStudentGraduationCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findJwStudentGraduationCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody JwStudentGraduation jwStudentGraduation){
        return jwStudentGraduationService.findJwStudentGraduationCountByCondition(jwStudentGraduation);
    }

    @PostMapping("/updateJwStudentGraduation")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateJwStudentGraduation(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody JwStudentGraduation jwStudentGraduation){
        jwStudentGraduationService.updateJwStudentGraduation(jwStudentGraduation);
    }

    @GetMapping("/deleteJwStudentGraduation/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteJwStudentGraduation(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        jwStudentGraduationService.deleteJwStudentGraduation(id);
    }
    @PostMapping("/deleteJwStudentGraduationByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteJwStudentGraduationByCondition(
            @ApiParam(value = "对象")
            @RequestBody JwStudentGraduation jwStudentGraduation){
        jwStudentGraduationService.deleteJwStudentGraduationByCondition(jwStudentGraduation);
    }
    @PostMapping("/findOneJwStudentGraduationByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public JwStudentGraduation findOneJwStudentGraduationByCondition(
            @ApiParam(value = "对象")
            @RequestBody JwStudentGraduation jwStudentGraduation){
        return jwStudentGraduationService.findOneJwStudentGraduationByCondition(jwStudentGraduation);
    }

    @PostMapping("/batchSaveJwStudentGraduation")
    @ApiOperation(value = "批量添加数据",notes = "无返回值")
    public void batchSaveJwStudentGraduation(
            @ApiParam(value = "list对象")
            @PathVariable List<JwStudentGraduation> jwStudentGraduations
    ){
        jwStudentGraduationService.batchSaveJwStudentGraduation(jwStudentGraduations);
    }

    @PostMapping("/piLiangSaveJwStudentGraduation")
    @ApiOperation(value = "批量添加数据",notes = "无返回值")
    public void piLiangSaveJwStudentGraduation(
            @ApiParam(value = "数组对象")
            @PathVariable JwStudentGraduation[] jwStudentGraduations
    ){
        jwStudentGraduationService.piLiangSaveJwStudentGraduation(jwStudentGraduations);
    }

    @PostMapping("/findJwStudentGraduationClassesByYear")
    @ApiOperation(value = "根据年份条件查找当前学校的年级信息 如:[1,2,3]代表1班2班3班", notes = "返回列表")
    public List<Integer> findJwStudentGraduationClassesByYear(
            @ApiParam(value = "对象")
            @RequestBody JwStudentGraduation jwStudentGraduation){
        List<Integer> classList = jwStudentGraduationService.findJwStudentGraduationClassesByYear(jwStudentGraduation);
        return classList;
    }


    @PostMapping("/findStudentGraduationListForExportStudentGraduationByCondition")
    @ApiOperation(value = "导出根据条件查询所有毕业生列表,不分页", notes = "返回列表")
    public List<JwStudentGraduation> findStudentGraduationListForExportStudentGraduationByCondition(
            @ApiParam(value = "对象")
            @RequestBody JwStudentGraduation jwStudentGraduation){
        List<JwStudentGraduation> studentGraduationList = jwStudentGraduationService.findStudentGraduationListForExportStudentGraduationByCondition(jwStudentGraduation);
        return studentGraduationList;
    }


}
