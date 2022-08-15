package com.yice.edu.cn.bmp.controller.schoolNotify;

import com.yice.edu.cn.bmp.service.schoolNotify.SchoolNotifySendObjectService;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.schoolNotify.SchoolNotifySendObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.mySchoolId;
import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.myStudentId;

@RestController
@RequestMapping("/schoolNotifySendObject")
@Api(value = "/schoolNotifySendObject", description = "模块")
public class SchoolNotifySendObjectController {
    @Autowired
    private SchoolNotifySendObjectService schoolNotifySendObjectService;


    @PostMapping("/getMySchoolNotifySendObjectList")
    @ApiOperation(value = "获取当前用户的通知列表(传入readState:0、全部 1、未读  2、已读)", notes = "返回响应对象,不包含总条数", response = SchoolNotifySendObject.class)
    public ResponseJson getMySchoolNotifySendObjectList(@RequestBody SchoolNotifySendObject schoolNotifySendObject) {
        //查询全部要置空读取状态
        if(schoolNotifySendObject.getReadState().equals("0"))
            schoolNotifySendObject.setReadState(null);
            schoolNotifySendObject.setSchoolId(mySchoolId());
            schoolNotifySendObject.setObjectId(myStudentId());
            schoolNotifySendObject.setDel("1");
            List<SchoolNotifySendObject> data = schoolNotifySendObjectService.findSchoolNotifySendObjectListByCondition(schoolNotifySendObject);
            long count = schoolNotifySendObjectService.findSchoolNotifySendObjectCountByCondition(schoolNotifySendObject);
            return new ResponseJson(data, count);
    }


    /**
     *
     * @param
     *{
     *"id":"",
     * "schoolNotify":{
     *             "id":""
     *              },
     * "readState":"2"}
     * @return
     */
    @PostMapping("/updateSchoolNotifySendObject")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateSchoolNotifySendObject(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody SchoolNotifySendObject schoolNotifySendObject) {
        schoolNotifySendObjectService.updateSchoolNotifySendObject(schoolNotifySendObject);
        return new ResponseJson();
    }

    @GetMapping("/getNotReadNum")
    @ApiOperation(value = "获取当前用户未读数量，用于判断是否显示红点", notes = "返回响应对象,不包含总条数", response = SchoolNotifySendObject.class)
    public ResponseJson findSchoolNotifySendObjectListForSchoolNotifyByCondition() {
        boolean isRead=false;
        SchoolNotifySendObject schoolNotifySendObject=new SchoolNotifySendObject();
        schoolNotifySendObject.setSchoolId(mySchoolId());
        schoolNotifySendObject.setObjectId(myStudentId());
        schoolNotifySendObject.setReadState("1");
        long count = schoolNotifySendObjectService.findSchoolNotifySendObjectCountByCondition(schoolNotifySendObject);
        if(count>0){
            isRead=true;
        }
        return new ResponseJson(isRead);
    }


}
