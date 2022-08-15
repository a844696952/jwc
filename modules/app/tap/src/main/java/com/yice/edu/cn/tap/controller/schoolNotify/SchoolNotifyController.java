package com.yice.edu.cn.tap.controller.schoolNotify;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.schoolNotify.SchoolNotify;
import com.yice.edu.cn.tap.service.schoolNotify.SchoolNotifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/schoolNotify")
@Api(value = "/schoolNotify",description = "模块")
public class SchoolNotifyController {
    @Autowired
    private SchoolNotifyService schoolNotifyService;


    /**
     * {
     *   "content": "测试内容1111",
     *   "schoolNotifySendObjectList": [
     *     {
     *             "objectId": "1753688335901876224",
     *             "departmentName": "小王哥",
     *             "departmentParentId": "1978372609324965888",
     *             "type": "1",
     *             "imgUrl": "/upload/avatar/2019/0301/8538e006dae245e6.jpg"
     *         }
     *   ],
     *   "sendType": "2",
     *   "title": "测试标题1111",
     *   "urgent": "0"
     * }
     * @param schoolNotify
     * @return
     */
    @PostMapping("/saveSchoolNotify")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response=SchoolNotify.class)
    public ResponseJson saveSchoolNotify(
            @ApiParam(value = "对象", required = true)
            @RequestBody SchoolNotify schoolNotify){
       schoolNotify.setSchoolId(mySchoolId());
        schoolNotify.setSenderId(myId());
        schoolNotify.setSenderName(currentTeacher().getName());
        SchoolNotify s=schoolNotifyService.saveSchoolNotify(schoolNotify);
        return new ResponseJson(s);
    }


    @PostMapping("/updateSchoolNotify")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateSchoolNotify(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody SchoolNotify schoolNotify){
        schoolNotifyService.updateSchoolNotify(schoolNotify);
        return new ResponseJson();
    }

    @GetMapping("/findSchoolNotifyById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=SchoolNotify.class)
    public ResponseJson findSchoolNotifyById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        SchoolNotify schoolNotify=schoolNotifyService.findSchoolNotifyById(id);
        return new ResponseJson(schoolNotify);
    }

    @GetMapping("/deleteSchoolNotify/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteSchoolNotify(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        schoolNotifyService.deleteSchoolNotify(id);
        return new ResponseJson();
    }


    @PostMapping("/findSchoolNotifyListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=SchoolNotify.class)
    public ResponseJson findSchoolNotifyListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody SchoolNotify schoolNotify){
       schoolNotify.setSchoolId(mySchoolId());
        List<SchoolNotify> data=schoolNotifyService.findSchoolNotifyListByCondition(schoolNotify);
        return new ResponseJson(data);
    }



}
