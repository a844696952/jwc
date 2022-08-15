package com.yice.edu.cn.jw.controller.parent;

import com.yice.edu.cn.common.pojo.jw.parent.Parent;
import com.yice.edu.cn.common.pojo.jw.parent.ParentNameRelationship;
import com.yice.edu.cn.common.pojo.jw.parent.ParentStudent;
import com.yice.edu.cn.common.pojo.jw.parent.ParentStudentFile;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.jw.service.parent.ParentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parent")
@Api(value = "/parent",description = "模块")
public class ParentController {
    @Autowired
    private ParentService parentService;

    @PostMapping("/login")
    @ApiOperation(value = "家长账号登录")
    public Parent login(@RequestBody Parent parent){
        return parentService.login(parent);
    }

    @GetMapping("/findParentById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public Parent findParentById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return parentService.findParentById(id);
    }

    @PostMapping("/saveParent")
    @ApiOperation(value = "保存", notes = "返回对象")
    public Parent saveParent(
            @ApiParam(value = "对象", required = true)
            @RequestBody Parent parent){
        parentService.saveParent(parent);
        return parent;
    }
    @PostMapping("/saveParentStudent")
    @ApiOperation(value = "保存", notes = "返回对象")
    public ParentStudent saveParentStudent(
            @ApiParam(value = "对象", required = true)
            @RequestBody ParentStudent parentStudent){
        parentService.saveParentStudent(parentStudent);
        return parentStudent;
    }

    @PostMapping("/findParentListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<Parent> findParentListByCondition(
            @ApiParam(value = "对象")
            @RequestBody Parent parent){
        return parentService.findParentListByCondition(parent);
    }
    @PostMapping("/findParentCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findParentCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody Parent parent){
        return parentService.findParentCountByCondition(parent);
    }

    @PostMapping("/updateParent")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateParent(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody Parent parent){
        parentService.updateParent(parent);
    }

    @PostMapping("/updateParent1")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateParent1(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody Parent parent){
        parentService.updateParent1(parent);
    }

    @GetMapping("/deleteParent/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteParent(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        parentService.deleteParent(id);
    }
    @PostMapping("/deleteParentByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteParentByCondition(
            @ApiParam(value = "对象")
            @RequestBody Parent parent){
        parentService.deleteParentByCondition(parent);
    }
    @PostMapping("/findOneParentByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public Parent findOneParentByCondition(
            @ApiParam(value = "对象")
            @RequestBody Parent parent){
        return parentService.findOneParentByCondition(parent);
    }

    @PostMapping("/updatePassword")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updatePassword(@ApiParam(value = "对象,对象属性不为空则修改", required = true)
                               @RequestBody Parent parent) {
         parentService.updatePassword(parent);

    }

    @PostMapping("/findParentStudentListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<ParentStudent> findParentStudentListByCondition(
            @ApiParam(value = "对象")
            @RequestBody ParentStudent parentStudent){
        return parentService.findParentStudentListByCondition(parentStudent);
    }

    @PostMapping("/updateParentStudent")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateParentStudent(@ApiParam(value = "对象,对象属性不为空则修改", required = true)
                           @RequestBody   ParentStudent parentStudent) {
        parentService.updateParentStudent(parentStudent);

    }

    @PostMapping("/setStudentidToNull")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void setStudentidToNull(@ApiParam(value = "对象,对象属性不为空则修改", required = true)
                                    @RequestBody   Parent parent) {
        parentService.setStudentidToNull(parent);

    }

    @PostMapping("/getBoundStudentList")
    @ApiOperation(value = "查询", notes = "对象必传")
    public List<Student> getBoundStudentList(@ApiParam(value = "对象,对象parentId属性不为空", required = true)
                                    @RequestBody   ParentStudent parentStudent) {
       return parentService.getBoundStudentList(parentStudent);

    }
    @PostMapping("/getBoundStudentListInCenter")
    @ApiOperation(value = "查询", notes = "对象必传")
    public List<ParentStudentFile> getBoundStudentListInCenter(@ApiParam(value = "对象,对象parentId属性不为空", required = true)
                                                       @RequestBody   ParentStudent parentStudent) {
        return parentService.getBoundStudentListInCenter(parentStudent);

    }
    @PostMapping("/deleteParentStudentByCondition")
    @ApiOperation(value = "删除", notes = "对象必传")
    public  void deleteParentStudentByCondition(@ApiParam(value = "根据条件删除") @RequestBody   ParentStudent parentStudent){
        parentService.deleteParentStudentByCondition(parentStudent);
    }
    @PostMapping("/deleteParentStudentByParentId")
    @ApiOperation(value = "删除", notes = "对象必传")
    public  void deleteParentStudentByParentId(@ApiParam(value = "根据条件删除") @RequestBody   ParentStudent parentStudent){
        parentService.deleteParentStudentByParentId(parentStudent);
    }

    @GetMapping("/findParentToRedisById/{id}")
    @ApiOperation(value = "查询家长", notes = "参数必传")
    public Parent findParentToRedisById(@PathVariable("id")  String id){
        return  parentService.findParentToRedisById(id);
    }

    @PostMapping("/updateRelationshipAndParentName")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateRelationshipAndParentName(@ApiParam(value = "对象,对象属性不为空则修改", required = true)
                                    @RequestBody ParentNameRelationship parentNameRelationship) {
        parentService.updateRelationshipAndParentName(parentNameRelationship);

    }
    @PostMapping("/bindOpenIdParent")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void bindOpenIdParent(@ApiParam(value = "对象,对象属性不为空则修改", required = true) @RequestBody Parent parent){
        parentService.bindOpenIdParent(parent);
    }

    @GetMapping("/findParentByStudentId/{id}")
    @ApiOperation(value = "通过studentid查找", notes = "返回对象")
    public Parent findParentByStudentId(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return parentService.findParentByStudentId(id);
    }

    @GetMapping("/findParentMsgByStudentId/{id}")
    @ApiOperation(value = "通过studentid查找", notes = "返回对象")
    public ParentStudentFile findParentMsgByStudentId(
            @ApiParam(value = "需要用到的学生id", required = true)
            @PathVariable("id") String id){
        return parentService.findParentMsgByStudentId(id);
    }

    @PostMapping("/deleteParentStudentByShiftpromotion")
    @ApiOperation(value = "通过classid查找", notes = "返回对象")
    public void deleteParentStudentByShiftpromotion(@RequestBody List<String> classId){
        parentService.deleteParentStudentByShiftpromotion(classId);
    }

    @PostMapping("/findSchoolByParentId")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<ParentStudent> findSchoolByParentId(
            @ApiParam(value = "对象")
            @RequestBody ParentStudent parentStudent){
        return parentService.findSchoolByParentId(parentStudent);
    }

   /* @PostMapping("/findParentIdByStudentList")
    public void findParentIdByStudentList(@RequestBody List<String> stuList){
        parentService.findParentIdByStudentList(stuList);
    }*/


}
