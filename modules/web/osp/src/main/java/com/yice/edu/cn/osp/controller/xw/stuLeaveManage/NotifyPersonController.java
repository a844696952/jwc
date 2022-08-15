package com.yice.edu.cn.osp.controller.xw.stuLeaveManage;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.department.DepartmentTeacher;
import com.yice.edu.cn.common.pojo.xw.stuLeaveManage.NotifyPerson;
import com.yice.edu.cn.osp.feignClient.jw.department.DepartmentTeacherFeign;
import com.yice.edu.cn.osp.feignClient.xw.doc.DepartmentFeign;
import com.yice.edu.cn.osp.service.xw.stuLeaveManage.NotifyPersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/notifyPerson")
@Api(value = "/notifyPerson", description = "学生请假通知人模块")
public class NotifyPersonController {
    @Autowired
    private NotifyPersonService notifyPersonService;
    @Autowired
    private DepartmentTeacherFeign departmentTeacherFeign;

    @PostMapping("/saveNotifyPerson")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response = NotifyPerson.class)
    public ResponseJson saveNotifyPerson(
            @ApiParam(value = "对象", required = true)
            @RequestBody NotifyPerson notifyPerson) {
        notifyPerson.setSchoolId(mySchoolId());
        NotifyPerson s = notifyPersonService.saveNotifyPerson(notifyPerson);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findNotifyPersonById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response = NotifyPerson.class)
    public ResponseJson findNotifyPersonById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        NotifyPerson notifyPerson = notifyPersonService.findNotifyPersonById(id);
        return new ResponseJson(notifyPerson);
    }

    @PostMapping("/update/updateNotifyPerson")
    @ApiOperation(value = "修改对象非空字段", notes = "返回响应对象")
    public ResponseJson updateNotifyPerson(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody NotifyPerson notifyPerson) {
        notifyPersonService.updateNotifyPerson(notifyPerson);
        return new ResponseJson();
    }

    @PostMapping("/update/updateNotifyPersonForAll")
    @ApiOperation(value = "修改对象所有字段", notes = "返回响应对象")
    public ResponseJson updateNotifyPersonForAll(
            @ApiParam(value = "被修改的对象", required = true)
            @RequestBody NotifyPerson notifyPerson) {
        notifyPersonService.updateNotifyPersonForAll(notifyPerson);
        return new ResponseJson();
    }

    @GetMapping("/look/lookNotifyPersonById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response = NotifyPerson.class)
    public ResponseJson lookNotifyPersonById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        NotifyPerson notifyPerson = notifyPersonService.findNotifyPersonById(id);
        return new ResponseJson(notifyPerson);
    }

    @PostMapping("/findNotifyPersonsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response = NotifyPerson.class)
    public ResponseJson findNotifyPersonsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody NotifyPerson notifyPerson) {
        notifyPerson.setSchoolId(mySchoolId());
        List<NotifyPerson> data = notifyPersonService.findNotifyPersonListByCondition(notifyPerson);
        long count = notifyPersonService.findNotifyPersonCountByCondition(notifyPerson);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneNotifyPersonByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response = NotifyPerson.class)
    public ResponseJson findOneNotifyPersonByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody NotifyPerson notifyPerson) {
        NotifyPerson one = notifyPersonService.findOneNotifyPersonByCondition(notifyPerson);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteNotifyPerson/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteNotifyPerson(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        notifyPersonService.deleteNotifyPerson(id);
        return new ResponseJson();
    }


    @PostMapping("/findNotifyPersonListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response = NotifyPerson.class)
    public ResponseJson findNotifyPersonListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody NotifyPerson notifyPerson) {
        notifyPerson.setSchoolId(mySchoolId());
        notifyPerson.getPager().setSortOrder(Pager.DESC).setSortField("createTime");
        List<NotifyPerson> data = notifyPersonService.findNotifyPersonListByCondition(notifyPerson);
        return new ResponseJson(data);
    }

    @GetMapping("/findDepartmentByTeacherId/{id}")
    @ApiOperation(value = "根据id查找部门", notes = "返回响应对象")
    public ResponseJson findDepartmentByTeacherId(
            @ApiParam(value = "id", required = true)
            @PathVariable String id) {
        List<DepartmentTeacher> list = departmentTeacherFeign.findDepartmentByTeacherId(id);
        return new ResponseJson(list);
    }

    @PostMapping("/batchSaveNotifyPerson")
    @ApiOperation(value = "批量保存")
    public ResponseJson batchSaveNotifyPerson(
            @ApiParam(value = "对象集合", required = true)
            @RequestBody List<NotifyPerson> notifyPersons) {
        for(NotifyPerson nn:notifyPersons){
            nn.setSchoolId(mySchoolId());
            nn.setCreateTime(DateUtil.now());
        }
        notifyPersonService.batchSaveNotifyPerson(notifyPersons);
        return new ResponseJson();
    }


}
