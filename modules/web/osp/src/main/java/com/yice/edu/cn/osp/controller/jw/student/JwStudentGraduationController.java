package com.yice.edu.cn.osp.controller.jw.student;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.student.JwStudentGraduation;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.student.StudentFamily;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import com.yice.edu.cn.osp.service.jw.student.JwStudentGraduationService;
import com.yice.edu.cn.osp.service.jw.student.StudentFamilyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/jwStudentGraduation")
@Api(value = "/jwStudentGraduation",description = "模块")
public class JwStudentGraduationController {
    @Autowired
    private JwStudentGraduationService jwStudentGraduationService;
    @Autowired
    private StudentFamilyService studentFamilyService;

    @PostMapping("/saveJwStudentGraduation")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveJwStudentGraduation(
            @ApiParam(value = "对象", required = true)
            @RequestBody JwStudentGraduation jwStudentGraduation){
        JwStudentGraduation s=jwStudentGraduationService.saveJwStudentGraduation(jwStudentGraduation);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findJwStudentGraduationById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findJwStudentGraduationById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        JwStudentGraduation jwStudentGraduation=jwStudentGraduationService.findJwStudentGraduationById(id);
        return new ResponseJson(jwStudentGraduation);
    }

    @PostMapping("/update/updateJwStudentGraduation")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateJwStudentGraduation(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody JwStudentGraduation jwStudentGraduation){
        jwStudentGraduationService.updateJwStudentGraduation(jwStudentGraduation);
        return new ResponseJson();
    }

    @GetMapping("/look/lookJwStudentGraduationById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookJwStudentGraduationById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        JwStudentGraduation jwStudentGraduation=jwStudentGraduationService.findJwStudentGraduationById(id);
        return new ResponseJson(jwStudentGraduation);
    }

    @PostMapping("/findJwStudentGraduationClassesByYear")
    @ApiOperation(value = "根据年份条件查找当前学校的年级信息 如:[1,2,3]代表1班2班3班", notes = "返回响应对象")
    public ResponseJson findJwStudentGraduationClassesByYear(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody JwStudentGraduation jwStudentGraduation){
        jwStudentGraduation.setSchoolId(LoginInterceptor.mySchoolId());
        List<Integer>data = jwStudentGraduationService.findJwStudentGraduationClassesByYear(jwStudentGraduation);
        List<Map<String,Object>>result = new ArrayList<>();
        for (Integer i : data) {
            Map<String,Object> map = new HashMap<>();
            map.put("label","("+i+")班");
            map.put("value",i);
            result.add(map);
        }
        return new ResponseJson(result);
    }

    @PostMapping("/findJwStudentGraduationsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findJwStudentGraduationsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody JwStudentGraduation jwStudentGraduation){
        jwStudentGraduation.setSchoolId(LoginInterceptor.mySchoolId());
        List<JwStudentGraduation> data=jwStudentGraduationService.findJwStudentGraduationListByCondition(jwStudentGraduation);
        long count=jwStudentGraduationService.findJwStudentGraduationCountByCondition(jwStudentGraduation);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneJwStudentGraduationByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneJwStudentGraduationByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody JwStudentGraduation jwStudentGraduation){
        JwStudentGraduation jwStudentGraduations=jwStudentGraduationService.findOneJwStudentGraduationByCondition(jwStudentGraduation);
        return new ResponseJson(jwStudentGraduations);
    }
    @GetMapping("/deleteJwStudentGraduation/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteJwStudentGraduation(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        jwStudentGraduationService.deleteJwStudentGraduation(id);
        return new ResponseJson();
    }

    @GetMapping("/deleteJwStudentGraduationByCondition")
    @ApiOperation(value = "根据条件删除", notes = "返回响应对象")
    public ResponseJson deleteJwStudentGraduationByCondition(
            @ApiParam(value = "被删除的对象,对象属性不为空则作为删除条件", required = true)
            @RequestBody JwStudentGraduation jwStudentGraduation){
        jwStudentGraduationService.deleteJwStudentGraduationByCondition(jwStudentGraduation);
        return new ResponseJson();
    }

    @PostMapping("/look/batchSaveJwStudentGraduation")
    @ApiOperation(value = "批量添加",notes = "无返回值")
    public ResponseJson batchSaveJwStudentGraduation(
            @ApiParam(value = "批量添加",required = true)
            @RequestBody JwStudentGraduation[] jwStudentGraduations
    ){

        jwStudentGraduationService.piLiangSaveJwStudentGraduation(jwStudentGraduations);
        return new ResponseJson();
    }

    @GetMapping("/look/lookStudentFamilyById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找学生家庭信息", notes = "返回响应对象")
    public ResponseJson lookStudentFamilyById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        List<StudentFamily> studentFamily=studentFamilyService.findStudentFamilyById(id);
        return new ResponseJson(studentFamily);
    }

    @PostMapping("/download")
    public void exportStudentGraduation(@ApiParam(value = "毕业生列表")
                              @RequestBody JwStudentGraduation jwStudentGraduation, HttpServletResponse response) {

        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=studentGraduation.xls");
        try (ServletOutputStream s = response.getOutputStream()) {

            jwStudentGraduation.setSchoolId(mySchoolId());
            Workbook workbook = jwStudentGraduationService.exportStudentGraduation(jwStudentGraduation);
            workbook.write(s);
        } catch (Exception e) {

        }
    }
}
