package com.yice.edu.cn.osp.controller.wb.classRegister;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.wb.classRegister.ClassRegister;
import com.yice.edu.cn.osp.service.wb.classRegister.ClassRegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/classRegister")
@Api(value = "/classRegister",description = "模块")
public class ClassRegisterController {
    @Autowired
    private ClassRegisterService classRegisterService;

    @PostMapping("/saveClassRegister")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response=ClassRegister.class)
    public ResponseJson saveClassRegister(
            @ApiParam(value = "对象", required = true)
            @RequestBody ClassRegister classRegister){
       classRegister.setSchoolId(mySchoolId());
        ClassRegister s=classRegisterService.saveClassRegister(classRegister);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findClassRegisterById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=ClassRegister.class)
    public ResponseJson findClassRegisterById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        ClassRegister classRegister=classRegisterService.findClassRegisterById(id);
        return new ResponseJson(classRegister);
    }

    @PostMapping("/update/updateClassRegister")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateClassRegister(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody ClassRegister classRegister){
        classRegisterService.updateClassRegister(classRegister);
        return new ResponseJson();
    }

    @GetMapping("/look/lookClassRegisterById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=ClassRegister.class)
    public ResponseJson lookClassRegisterById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        ClassRegister classRegister=classRegisterService.findClassRegisterById(id);
        return new ResponseJson(classRegister);
    }

    @PostMapping("/findClassRegistersByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=ClassRegister.class)
    public ResponseJson findClassRegistersByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ClassRegister classRegister){
       classRegister.setSchoolId(mySchoolId());
        List<ClassRegister> data=classRegisterService.findClassRegisterListByCondition(classRegister);
        long count=classRegisterService.findClassRegisterCountByCondition(classRegister);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneClassRegisterByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=ClassRegister.class)
    public ResponseJson findOneClassRegisterByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody ClassRegister classRegister){
        ClassRegister one=classRegisterService.findOneClassRegisterByCondition(classRegister);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteClassRegister/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteClassRegister(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        classRegisterService.deleteClassRegister(id);
        return new ResponseJson();
    }


    @PostMapping("/findClassRegisterListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=ClassRegister.class)
    public ResponseJson findClassRegisterListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ClassRegister classRegister){
       classRegister.setSchoolId(mySchoolId());
        List<ClassRegister> data=classRegisterService.findClassRegisterListByCondition(classRegister);
        return new ResponseJson(data);
    }

    @PostMapping("/findClassRegistersByCondition2")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=ClassRegister.class)
    public ResponseJson findClassRegistersByCondition2(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ClassRegister classRegister){
        classRegister.setSchoolId(mySchoolId());
        classRegister.setTeacherId(myId());
        List<ClassRegister> data=classRegisterService.findClassRegisterListByCondition2(classRegister);
        long count=classRegisterService.findClassRegisterCountByCondition2(classRegister);
        return new ResponseJson(data,count);
    }



}
