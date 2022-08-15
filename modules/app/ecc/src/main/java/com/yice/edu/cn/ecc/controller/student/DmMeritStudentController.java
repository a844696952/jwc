package com.yice.edu.cn.ecc.controller.student;


import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.student.DmMeritStudent;
import com.yice.edu.cn.ecc.service.student.DmMeritStudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static com.yice.edu.cn.ecc.interceptor.LoginInterceptor.mySchoolId;


@RestController
@RequestMapping("/dmMeritStudent")
@Api(value = "/dmMeritStudent",description = "三好学生表模块")
public class DmMeritStudentController {
    @Autowired
    private DmMeritStudentService dmMeritStudentService;



    @GetMapping("/findDmMeritStudentById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找三好学生表", notes = "返回响应对象", response=DmMeritStudent.class)
    public ResponseJson findDmMeritStudentById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DmMeritStudent dmMeritStudent=dmMeritStudentService.findDmMeritStudentById(id);
        return new ResponseJson(dmMeritStudent);
    }




    @PostMapping("/findDmMeritStudentsByCondition")
    @ApiOperation(value = "根据条件查找三好学生表", notes = "返回响应对象", response=DmMeritStudent.class)
    public ResponseJson findDmMeritStudentsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmMeritStudent dmMeritStudent){
        dmMeritStudent.setSchoolId(mySchoolId());
        List<DmMeritStudent> data=dmMeritStudentService.findDmMeritStudentListByCondition(dmMeritStudent);
        long count=dmMeritStudentService.findDmMeritStudentCountByCondition(dmMeritStudent);
        return new ResponseJson(data,count);
    }




}
