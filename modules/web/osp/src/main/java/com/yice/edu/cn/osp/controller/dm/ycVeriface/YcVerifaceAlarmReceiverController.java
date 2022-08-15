package com.yice.edu.cn.osp.controller.dm.ycVeriface;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.alarmReceiver.YcVerifaceAlarmReceiver;
import com.yice.edu.cn.common.pojo.jw.department.DepartmentTeacher;
import com.yice.edu.cn.common.pojo.xw.stuLeaveManage.NotifyPerson;
import com.yice.edu.cn.osp.feignClient.jw.department.DepartmentFeign;
import com.yice.edu.cn.osp.feignClient.jw.department.DepartmentTeacherFeign;
import com.yice.edu.cn.osp.service.dm.ycVeriface.YcVerifaceAlarmReceiverService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/ycVerifaceAlarmReceiver")
@Api(value = "/ycVerifaceAlarmReceiver", description = "人脸识别预警接收模块")
public class YcVerifaceAlarmReceiverController {
    @Autowired
    private YcVerifaceAlarmReceiverService ycVerifaceAlarmReceiverService;
    @Autowired
    private DepartmentTeacherFeign departmentTeacherFeign;

    @PostMapping("/saveYcVerifaceAlarmReceiver")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response = YcVerifaceAlarmReceiver.class)
    public ResponseJson saveYcVerifaceAlarmReceiver(
            @ApiParam(value = "对象", required = true)
            @RequestBody YcVerifaceAlarmReceiver ycVerifaceAlarmReceiver) {
        ycVerifaceAlarmReceiver.setSchoolId(mySchoolId());
        YcVerifaceAlarmReceiver s = ycVerifaceAlarmReceiverService.saveYcVerifaceAlarmReceiver(ycVerifaceAlarmReceiver);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findYcVerifaceAlarmReceiverById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response = YcVerifaceAlarmReceiver.class)
    public ResponseJson findYcVerifaceAlarmReceiverById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        YcVerifaceAlarmReceiver ycVerifaceAlarmReceiver = ycVerifaceAlarmReceiverService.findYcVerifaceAlarmReceiverById(id);
        return new ResponseJson(ycVerifaceAlarmReceiver);
    }

    @PostMapping("/update/updateYcVerifaceAlarmReceiver")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateYcVerifaceAlarmReceiver(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody YcVerifaceAlarmReceiver ycVerifaceAlarmReceiver) {
        ycVerifaceAlarmReceiverService.updateYcVerifaceAlarmReceiver(ycVerifaceAlarmReceiver);
        return new ResponseJson();
    }

    @GetMapping("/look/lookYcVerifaceAlarmReceiverById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response = YcVerifaceAlarmReceiver.class)
    public ResponseJson lookYcVerifaceAlarmReceiverById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        YcVerifaceAlarmReceiver ycVerifaceAlarmReceiver = ycVerifaceAlarmReceiverService.findYcVerifaceAlarmReceiverById(id);
        return new ResponseJson(ycVerifaceAlarmReceiver);
    }

    @PostMapping("/findYcVerifaceAlarmReceiversByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response = YcVerifaceAlarmReceiver.class)
    public ResponseJson findYcVerifaceAlarmReceiversByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody YcVerifaceAlarmReceiver ycVerifaceAlarmReceiver) {
        ycVerifaceAlarmReceiver.setSchoolId(mySchoolId());
        List<YcVerifaceAlarmReceiver> data = ycVerifaceAlarmReceiverService.findYcVerifaceAlarmReceiverListByCondition(ycVerifaceAlarmReceiver);
        List<YcVerifaceAlarmReceiver> list = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(data)) {
            for (YcVerifaceAlarmReceiver yc : data) {
                StringBuffer s = new StringBuffer();
                List<DepartmentTeacher> li = departmentTeacherFeign.findDepartmentByTeacherId(yc.getPersonId());
                if (CollectionUtil.isNotEmpty(li)) {
                    for (DepartmentTeacher d : li) {
                        s.append(d.getDepartmentName() + "、");
                    }
                }
                if (s.length() > 1) {
                    yc.setDepartmentName(Arrays.asList(s.toString().substring(0, s.length() - 1)));
                } else {
                    yc.setDepartmentName(Arrays.asList(s.toString()));
                }
                list.add(yc);
            }
        }
        long count = ycVerifaceAlarmReceiverService.findYcVerifaceAlarmReceiverCountByCondition(ycVerifaceAlarmReceiver);
        return new ResponseJson(list, count);
    }

    @PostMapping("/findOneYcVerifaceAlarmReceiverByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response = YcVerifaceAlarmReceiver.class)
    public ResponseJson findOneYcVerifaceAlarmReceiverByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody YcVerifaceAlarmReceiver ycVerifaceAlarmReceiver) {
        YcVerifaceAlarmReceiver one = ycVerifaceAlarmReceiverService.findOneYcVerifaceAlarmReceiverByCondition(ycVerifaceAlarmReceiver);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteYcVerifaceAlarmReceiver/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteYcVerifaceAlarmReceiver(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        ycVerifaceAlarmReceiverService.deleteYcVerifaceAlarmReceiver(id);
        return new ResponseJson();
    }


    @PostMapping("/findYcVerifaceAlarmReceiverListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response = YcVerifaceAlarmReceiver.class)
    public ResponseJson findYcVerifaceAlarmReceiverListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody YcVerifaceAlarmReceiver ycVerifaceAlarmReceiver) {
        ycVerifaceAlarmReceiver.setSchoolId(mySchoolId());
        List<YcVerifaceAlarmReceiver> data = ycVerifaceAlarmReceiverService.findYcVerifaceAlarmReceiverListByCondition(ycVerifaceAlarmReceiver);
        return new ResponseJson(data);
    }

    @PostMapping("/batchSaveYcVerifaceAlarmReceiver")
    @ApiOperation(value = "批量保存", notes = "返回响应对象,不包含总条数", response = YcVerifaceAlarmReceiver.class)
    public ResponseJson batchSaveYcVerifaceAlarmReceiver(
            @ApiParam(value = "对象集合")
            @RequestBody YcVerifaceAlarmReceiver ycVerifaceAlarmReceivers) {
        for (YcVerifaceAlarmReceiver yc : ycVerifaceAlarmReceivers.getList()) {
            yc.setSchoolId(mySchoolId());
            yc.setCreateTime(DateUtil.now());
        }
        ycVerifaceAlarmReceiverService.batchSaveYcVerifaceAlarmReceiver(ycVerifaceAlarmReceivers.getList());
        return new ResponseJson();
    }

    @GetMapping("/findDepartmentByTeacherId/{id}")
    @ApiOperation(value = "根据id查找部门", notes = "返回响应对象")
    public ResponseJson findDepartmentByTeacherId(
            @ApiParam(value = "id", required = true)
            @PathVariable String id) {
        List<DepartmentTeacher> list = departmentTeacherFeign.findDepartmentByTeacherId(id);
        return new ResponseJson(list);
    }

}
