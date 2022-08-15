package com.yice.edu.cn.ewb.controller.classRegister;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.common.pojo.validateClass.GroupTwo;
import com.yice.edu.cn.common.pojo.wb.classRegister.ClassRegister;
import com.yice.edu.cn.ewb.service.classRegister.ClassRegisterService;
import com.yice.edu.cn.ewb.service.classRegister.JwClassesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

import static com.yice.edu.cn.ewb.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.ewb.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/classRegister")
@Api(value = "/classRegister",description = "上课记录模块")
public class ClassRegisterController {
    @Autowired
    private ClassRegisterService classRegisterService;
    @Autowired
    private JwClassesService jwClassesService;

    @PostMapping("/saveClassRegister")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response=ClassRegister.class)
    public ResponseJson saveClassRegister(
            @ApiParam(value = "对象", required = true)
            @Validated({GroupOne.class})
            @RequestBody ClassRegister classRegister){
        classRegister.setSchoolId(mySchoolId());
        classRegister.setTeacherId(myId());
/*        if(StringUtils.isBlank(classRegister.getChapterId())){
            return  new ResponseJson(false,"章节id为空");
        }
        if(StringUtils.isBlank(classRegister.getClassroomName())){
            return  new ResponseJson(false,"课堂名称为空");
        }
        if(StringUtils.isBlank(classRegister.getGradeId())){
            return  new ResponseJson(false,"年级为空");
        }
        if(StringUtils.isBlank(classRegister.getClassId())){
            return  new ResponseJson(false,"班级为空");
        }*/
        ClassRegister s=classRegisterService.saveClassRegister(classRegister);
        return new ResponseJson(s);
    }


    @PostMapping("/updateClassRegister")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateClassRegister(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody ClassRegister classRegister){
        classRegisterService.updateClassRegister(classRegister);
        return new ResponseJson();
    }

    @GetMapping("/findClassRegisterById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=ClassRegister.class)
    public ResponseJson findClassRegisterById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        ClassRegister classRegister=classRegisterService.findClassRegisterById(id);
        return new ResponseJson(classRegister);
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
            @RequestBody ClassRegister classRegister){
       classRegister.setSchoolId(mySchoolId());
        classRegister.setTeacherId(myId());
        Pager pager = new Pager();
        pager.setPaging(false);
        pager.setSortField("create_time");
        pager.setSortOrder(Pager.DESC);
        classRegister.setPager(pager);
        List<ClassRegister> data=classRegisterService.findClassRegisterListByCondition(classRegister);
        return new ResponseJson(data);
    }

    @GetMapping("/findClassesByGradeId/{gradeId}")
    public ResponseJson findClassesByGradeId(@PathVariable String gradeId) {
        JwClasses c  = new JwClasses();
        c.setGradeId(gradeId);
        c.setSchoolId(mySchoolId());
        return new ResponseJson(jwClassesService.findJwClassesListByCondition(c));
    }


}
