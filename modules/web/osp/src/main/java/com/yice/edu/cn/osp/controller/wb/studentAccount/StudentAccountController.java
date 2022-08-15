package com.yice.edu.cn.osp.controller.wb.studentAccount;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.wb.studentAccount.StudentAccount;
import com.yice.edu.cn.common.pojo.wb.studentAccount.WisdomClassStudentAccount;
import com.yice.edu.cn.osp.service.wb.studentAccount.StudentAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/studentAccount")
@Api(value = "/studentAccount",description = "模块")
public class StudentAccountController {
    @Autowired
    private StudentAccountService studentAccountService;

    @PostMapping("/saveStudentAccount")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response=StudentAccount.class)
    public ResponseJson saveStudentAccount(
            @ApiParam(value = "对象", required = true)
            @RequestBody StudentAccount studentAccount){
        studentAccount.setSchoolId(mySchoolId());
        StudentAccount s=studentAccountService.saveStudentAccount(studentAccount);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findStudentAccountById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=StudentAccount.class)
    public ResponseJson findStudentAccountById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        StudentAccount studentAccount=studentAccountService.findStudentAccountById(id);
        return new ResponseJson(studentAccount);
    }

    @PostMapping("/update/saveOrUpdateStudentAccount")
    @ApiOperation(value = "保存或修改对象", notes = "返回响应对象")
    public ResponseJson updateStudentAccount(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody StudentAccount studentAccount){
        studentAccount.setSchoolId(mySchoolId());
        return studentAccountService.saveOrUpdateStudentAccount(studentAccount);
    }

    @GetMapping("/look/lookStudentAccountById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=StudentAccount.class)
    public ResponseJson lookStudentAccountById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        StudentAccount studentAccount=studentAccountService.findStudentAccountById(id);
        return new ResponseJson(studentAccount);
    }

    @PostMapping("/findStudentAccountsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=StudentAccount.class)
    public ResponseJson findStudentAccountsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody StudentAccount studentAccount){
        studentAccount.setSchoolId(mySchoolId());
        List<StudentAccount> data=studentAccountService.findStudentAccountListByCondition(studentAccount);
        long count=studentAccountService.findStudentAccountCountByCondition(studentAccount);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneStudentAccountByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=StudentAccount.class)
    public ResponseJson findOneStudentAccountByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody StudentAccount studentAccount){
        studentAccount.setSchoolId(mySchoolId());
        StudentAccount one=studentAccountService.findOneStudentAccountByCondition(studentAccount);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteStudentAccount/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteStudentAccount(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        studentAccountService.deleteStudentAccount(id);
        return new ResponseJson();
    }


    @PostMapping("/findStudentAccountListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=StudentAccount.class)
    public ResponseJson findStudentAccountListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody StudentAccount studentAccount){
        studentAccount.setSchoolId(mySchoolId());
        List<StudentAccount> data=studentAccountService.findStudentAccountListByCondition(studentAccount);
        return new ResponseJson(data);
    }

    // 导出
    @PostMapping("/exportStudentAccount")
    public void exportAssetsSort(HttpServletResponse response, @RequestBody StudentAccount studentAccount) {
        //Workbook w = teacherService.exportTeacher();
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=assetsSort.xls");
        studentAccount.setSchoolId(mySchoolId());
        try (ServletOutputStream s = response.getOutputStream()) {
            Workbook workbook = studentAccountService.exportStudentAccount(studentAccount);
            workbook.write(s);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }



    @GetMapping("/resetStudentPwd/{studentId}")
    @ApiOperation(value = "根据studentId重置密码", notes = "返回响应对象")
    public ResponseJson resetStudentPwd(
            @ApiParam(value = "studentId", required = true)
            @PathVariable String studentId){
        studentAccountService.resetStudentPwd(studentId);
        return new ResponseJson();
    }

    @PostMapping("/batchSaveStudentAccount")
    @ApiOperation(value = "批量保存对象", notes = "返回响应对象")
    public ResponseJson batchSaveStudentAccount(
            @ApiParam(value = "对象", required = true)
            @RequestBody StudentAccount studentAccount){
        studentAccount.setSchoolId(mySchoolId());
        ResponseJson responseJson = studentAccountService.batchSaveStudentAccount(studentAccount);
        return responseJson;
    }

    @PostMapping("/batchResetStudentPwd")
    @ApiOperation(value = "批量重置学生密码", notes = "返回响应对象")
    public ResponseJson batchResetStudentPwd(
            @ApiParam(value = "对象", required = true)
            @RequestBody StudentAccount studentAccount){
        ResponseJson responseJson = studentAccountService.batchResetStudentPwd(studentAccount);
        return responseJson;
    }


    @PostMapping("/batchDeleteByIds")
    @ApiOperation(value = "批量删除对象", notes = "返回响应对象")
    public ResponseJson batchDeleteByIds(
            @ApiParam(value = "对象", required = true)
            @RequestBody StudentAccount studentAccount){
        studentAccountService.batchDeleteByIds(studentAccount.getIds());
        return new ResponseJson();
    }

    @PostMapping("/update/batchUpdateStudentType")
    @ApiOperation(value = "批量修改学生对象类型", notes = "返回响应对象")
    public ResponseJson batchUpdateStudentType(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody StudentAccount studentAccount){
        studentAccountService.batchUpdateStudentType(studentAccount);
        return new ResponseJson();
    }

    @PostMapping("/batchSaveWisdomClassStudentAccount")
    @ApiOperation(value = "批量保存智慧课堂笔盒绑定学生账号", notes = "返回响应对象")
    public ResponseJson batchSaveWisdomClassStudentAccount(
            @ApiParam(value = "学生账号对象", required = true)
            @RequestBody StudentAccount studentAccount
    ){
        studentAccount.setSchoolId(mySchoolId());
        ResponseJson responseJson = studentAccountService.batchSaveWisdomClassStudentAccount(studentAccount);
        return responseJson;
    }

    @PostMapping("/findWisdomClassStudentAccountListByCondition")
    @ApiOperation(value = "根据条件查找笔盒绑定学生列表",notes = "返回笔盒绑定学生列表")
    public ResponseJson findWisdomClassStudentAccountListByCondition(
            @ApiParam("学生账号对象")
            @RequestBody StudentAccount studentAccount
    ){
        studentAccount.setSchoolId(mySchoolId());
        List<WisdomClassStudentAccount> data = studentAccountService.findWisdomClassStudentAccountListByCondition(studentAccount);
        Long count = studentAccountService.findWisdomClassStudentAccountCountByCondition(studentAccount);
        return new ResponseJson(data,count);
    }

    @PostMapping("/exportWisdomClassStudentAccount")
    public void exportWisdomClassStudentAccount(HttpServletResponse response, @RequestBody StudentAccount studentAccount){
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=笔盒绑定学生列表.xls");
        studentAccount.setSchoolId(mySchoolId());
        try (ServletOutputStream s = response.getOutputStream()) {
            Workbook workbook = studentAccountService.exportWisdomClassStudentAccount(studentAccount);
            workbook.write(s);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
