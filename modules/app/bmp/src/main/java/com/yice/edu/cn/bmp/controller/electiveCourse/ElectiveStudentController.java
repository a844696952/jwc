package com.yice.edu.cn.bmp.controller.electiveCourse;

import com.yice.edu.cn.bmp.service.electiveCourse.ElectiveCourseService;
import com.yice.edu.cn.bmp.service.electiveCourse.ElectiveSetService;
import com.yice.edu.cn.bmp.service.electiveCourse.ElectiveStudentService;
import com.yice.edu.cn.bmp.service.school.SchoolYearService;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.electiveCourse.ElectiveStudent;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.mySchoolId;


@RestController
@RequestMapping("/electiveStudent")
@Api(value = "/electiveStudent",description = "选修学生关联表模块")
public class ElectiveStudentController {
    @Autowired
    private ElectiveStudentService electiveStudentService;
    @Autowired
    private ElectiveCourseService electiveCourseService;
    @Autowired
    private SchoolYearService schoolYearService;
    @Autowired
    private CurSchoolYearService curSchoolYearService;
    @Autowired
    private ElectiveSetService electiveSetService;

    /**
     * {"classesName":"2班","studentNo":"","studentId":"2424421490048721269","gradeName":"一年级","classesId":"2424382182508027904","gradeId":"11","sex":"5","studentName":"夏侯明514","electiveId":"1176062982002708480"}
     * @param electiveStudent
     * @return
     */
    @PostMapping("/saveElectiveStudent")
    @ApiOperation(value = "保存选修学生关联表对象", notes = "返回保存好的选修学生关联表对象", response=ElectiveStudent.class)
    public ResponseJson saveElectiveStudent(
            @ApiParam(value = "选修学生关联表对象", required = true)
            @RequestBody ElectiveStudent electiveStudent){

        electiveStudent.setSchoolId(mySchoolId());
        curSchoolYearService.setSchoolYearTerm(electiveStudent,mySchoolId());
        electiveStudent.setIsScore(0);
        Map result=electiveStudentService.saveElectiveStudent(electiveStudent);
        if((boolean) result.get("result")){
            return new ResponseJson();
        }else {
            return  new ResponseJson(false,Integer.parseInt(result.get("code").toString()),result.get("msg").toString());
        }
    }

    @GetMapping("/update/findElectiveStudentById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找选修学生关联表", notes = "返回响应对象", response=ElectiveStudent.class)
    public ResponseJson findElectiveStudentById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        ElectiveStudent electiveStudent=electiveStudentService.findElectiveStudentById(id);
        return new ResponseJson(electiveStudent);
    }

    @PostMapping("/update/updateElectiveStudent")
    @ApiOperation(value = "修改选修学生关联表对象", notes = "返回响应对象")
    public ResponseJson updateElectiveStudent(
            @ApiParam(value = "被修改的选修学生关联表对象,对象属性不为空则修改", required = true)
            @RequestBody ElectiveStudent electiveStudent){
        electiveStudentService.updateElectiveStudent(electiveStudent);
        return new ResponseJson();
    }

    @GetMapping("/look/lookElectiveStudentById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找选修学生关联表", notes = "返回响应对象", response=ElectiveStudent.class)
    public ResponseJson lookElectiveStudentById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        ElectiveStudent electiveStudent=electiveStudentService.findElectiveStudentById(id);
        return new ResponseJson(electiveStudent);
    }

    @PostMapping("/findElectiveStudentsByCondition")
    @ApiOperation(value = "根据条件查找选修学生关联表", notes = "返回响应对象", response=ElectiveStudent.class)
    public ResponseJson findElectiveStudentsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ElectiveStudent electiveStudent){
       electiveStudent.setSchoolId(mySchoolId());
        List<ElectiveStudent> data=electiveStudentService.findElectiveStudentListByCondition(electiveStudent);
        long count=electiveStudentService.findElectiveStudentCountByCondition(electiveStudent);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneElectiveStudentByCondition")
    @ApiOperation(value = "根据条件查找单个选修学生关联表,结果必须为单条数据", notes = "没有时返回空", response=ElectiveStudent.class)
    public ResponseJson findOneElectiveStudentByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody ElectiveStudent electiveStudent){
        ElectiveStudent one=electiveStudentService.findOneElectiveStudentByCondition(electiveStudent);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteElectiveStudent/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteElectiveStudent(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        electiveStudentService.deleteElectiveStudent(id);
        return new ResponseJson();
    }


    @PostMapping("/findElectiveStudentListByCondition")
    @ApiOperation(value = "根据条件查找选修学生关联表列表", notes = "返回响应对象,不包含总条数", response=ElectiveStudent.class)
    public ResponseJson findElectiveStudentListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ElectiveStudent electiveStudent){
       electiveStudent.setSchoolId(mySchoolId());
        List<ElectiveStudent> data=electiveStudentService.findElectiveStudentListByCondition(electiveStudent);
        return new ResponseJson(data);
    }



    @PostMapping("/batchSaveElectiveStudent")
    @ApiOperation(value = "批量保存选修学生关联表", notes = "返回选修学生关联表对象")
    public void batchSaveElectiveStudent(
            @ApiParam(value = "选修学生关联表对象", required = true)
            @RequestBody List<ElectiveStudent> electiveStudents){
        CurSchoolYear s=schoolYearService.findCurSchoolYear(mySchoolId());
        electiveStudents.forEach(e->{
            e.setSchoolId(mySchoolId());
            e.setSchoolYearId(s.getSchoolYearId());
            e.setFromTo(s.getFromTo());
            e.setTerm(s.getTerm());
            e.setIsScore(0);
        });
        electiveStudentService.batchSaveElectiveStudent(electiveStudents);
    }



}
