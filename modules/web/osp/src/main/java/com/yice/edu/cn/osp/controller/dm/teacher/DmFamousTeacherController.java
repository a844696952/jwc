package com.yice.edu.cn.osp.controller.dm.teacher;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.yice.edu.cn.common.annotation.EccJpush;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.teacher.DmFamousTeacher;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.osp.service.dm.teacher.DmFamousTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dmFamousTeacher")
@Api(value = "/dmFamousTeacher",description = "名师风采表模块")
public class DmFamousTeacherController {
    @Autowired
    private DmFamousTeacherService dmFamousTeacherService;


    @PostMapping("/saveDmFamousTeacher")
    @EccJpush(type = Extras.DM_ECC_FAMOUS_TEACHER,content = "新增名师风采")
    @ApiOperation(value = "保存名师风采表对象", notes = "返回保存好的名师风采表对象", response=DmFamousTeacher.class)
    public ResponseJson saveDmFamousTeacher(
            @ApiParam(value = "名师风采表对象", required = true)
            @RequestBody DmFamousTeacher dmFamousTeacher){
        DmFamousTeacher find = new DmFamousTeacher();
        find.setTeacherId(dmFamousTeacher.getTeacherId());
        find.setSchoolId((mySchoolId()));
        DmFamousTeacher one=dmFamousTeacherService.findOneDmFamousTeacherByCondition(find);
        if(ObjectUtil.isNotNull(one)){
            return new ResponseJson(false,"该教师已经是名师,无法添加");
        }
        dmFamousTeacher.setSchoolId(mySchoolId());
        dmFamousTeacher.setCreateTime(DateUtil.formatDate(new Date()));
        dmFamousTeacherService.saveDmFamousTeacher(dmFamousTeacher);
        return new ResponseJson();
    }

    @GetMapping("/findDmFamousTeacherById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找名师风采表", notes = "返回响应对象", response=DmFamousTeacher.class)
    public ResponseJson findDmFamousTeacherById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DmFamousTeacher dmFamousTeacher=dmFamousTeacherService.findDmFamousTeacherById(id);
        return new ResponseJson(dmFamousTeacher);
    }

    @PostMapping("/updateDmFamousTeacher")
    @EccJpush(type = Extras.DM_ECC_FAMOUS_TEACHER,content = "修改名师风采")
    @ApiOperation(value = "修改名师风采表对象非空字段", notes = "返回响应对象")
    public ResponseJson updateDmFamousTeacher(
            @ApiParam(value = "被修改的名师风采表对象,对象属性不为空则修改", required = true)
            @RequestBody DmFamousTeacher dmFamousTeacher){
        /*
          由于前端已经做了处理
        DmFamousTeacher find = new DmFamousTeacher();
        find.setTeacherId(dmFamousTeacher.getTeacherId());
        find.setSchoolId(dmFamousTeacher.getSchoolId());
        DmFamousTeacher one=dmFamousTeacherService.findOneDmFamousTeacherByCondition(find);
        if(ObjectUtil.isNotNull(one)){
            return new ResponseJson(false,"该教师已经是名师，无法修改");
        }*/
        dmFamousTeacherService.updateDmFamousTeacher(dmFamousTeacher);
        return new ResponseJson();
    }

    @PostMapping("/updateDmFamousTeacherForAll")
    @ApiOperation(value = "修改名师风采表对象所有字段", notes = "返回响应对象")
    public ResponseJson updateDmFamousTeacherForAll(
            @ApiParam(value = "被修改的名师风采表对象", required = true)
            @RequestBody DmFamousTeacher dmFamousTeacher){
        dmFamousTeacherService.updateDmFamousTeacherForAll(dmFamousTeacher);
        return new ResponseJson();
    }


    @PostMapping("/findDmFamousTeachersByCondition")
    @ApiOperation(value = "根据条件查找名师风采表", notes = "返回响应对象", response=DmFamousTeacher.class)
    public ResponseJson findDmFamousTeachersByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmFamousTeacher dmFamousTeacher){
       dmFamousTeacher.setSchoolId(mySchoolId());
        List<DmFamousTeacher> data=dmFamousTeacherService.findDmFamousTeacherListByCondition(dmFamousTeacher);
        long count=dmFamousTeacherService.findDmFamousTeacherCountByCondition(dmFamousTeacher);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDmFamousTeacherByCondition")
    @ApiOperation(value = "根据条件查找单个名师风采表,结果必须为单条数据", notes = "没有时返回空", response=DmFamousTeacher.class)
    public ResponseJson findOneDmFamousTeacherByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmFamousTeacher dmFamousTeacher){
        DmFamousTeacher one=dmFamousTeacherService.findOneDmFamousTeacherByCondition(dmFamousTeacher);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteDmFamousTeacher/{id}")
    @EccJpush(type = Extras.DM_ECC_FAMOUS_TEACHER,content = "删除名师风采根据id")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDmFamousTeacher(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        dmFamousTeacherService.deleteDmFamousTeacher(id);
        return new ResponseJson();
    }


    @PostMapping("/findAll")
    @ApiOperation(value = "查找全部名师风采表列表", notes = "返回响应对象", response=DmFamousTeacher.class)
    public ResponseJson findAll(){
        DmFamousTeacher find  = new DmFamousTeacher();
        find.setSchoolId(mySchoolId());
        find.setPager(new Pager().setPaging(false).setSortField("create_time").setSortOrder(Pager.DESC));
        List<DmFamousTeacher> data=dmFamousTeacherService.findDmFamousTeacherListByCondition(find);
        return new ResponseJson(data);
    }



}
