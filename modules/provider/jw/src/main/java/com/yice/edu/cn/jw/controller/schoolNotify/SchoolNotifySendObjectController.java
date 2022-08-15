package com.yice.edu.cn.jw.controller.schoolNotify;

import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.jw.schoolNotify.SchoolNotify;
import com.yice.edu.cn.common.pojo.jw.schoolNotify.SchoolNotifySendObject;
import com.yice.edu.cn.jw.service.schoolNotify.SchoolNotifySendObjectService;
import com.yice.edu.cn.jw.service.schoolNotify.SchoolNotifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schoolNotifySendObject")
@Api(value = "/schoolNotifySendObject",description = "模块")
public class SchoolNotifySendObjectController {
    @Autowired
    private SchoolNotifySendObjectService schoolNotifySendObjectService;
    @Autowired
    private SchoolNotifyService schoolNotifyService;
    @GetMapping("/findSchoolNotifySendObjectById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public SchoolNotifySendObject findSchoolNotifySendObjectById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return schoolNotifySendObjectService.findSchoolNotifySendObjectById(id);
    }

    @PostMapping("/saveSchoolNotifySendObject")
    @ApiOperation(value = "保存", notes = "返回对象")
    public SchoolNotifySendObject saveSchoolNotifySendObject(
            @ApiParam(value = "对象", required = true)
            @RequestBody SchoolNotifySendObject schoolNotifySendObject){
        schoolNotifySendObjectService.saveSchoolNotifySendObject(schoolNotifySendObject);
        return schoolNotifySendObject;
    }

    @PostMapping("/findSchoolNotifySendObjectListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<SchoolNotifySendObject> findSchoolNotifySendObjectListByCondition(
            @ApiParam(value = "对象")
            @RequestBody SchoolNotifySendObject schoolNotifySendObject){
        return schoolNotifySendObjectService.findSchoolNotifySendObjectListByCondition(schoolNotifySendObject);
    }




    @PostMapping("/findSchoolNotifySendObjectCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findSchoolNotifySendObjectCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody SchoolNotifySendObject schoolNotifySendObject){
        return schoolNotifySendObjectService.findSchoolNotifySendObjectCountByCondition(schoolNotifySendObject);
    }

    @PostMapping("/updateSchoolNotifySendObject")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateSchoolNotifySendObject(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody SchoolNotifySendObject schoolNotifySendObject){
      SchoolNotifySendObject sendObject=  schoolNotifySendObjectService.findSchoolNotifySendObjectById(schoolNotifySendObject.getId());
      if(sendObject.getReadState().equals("1")){
          //更新通知的已读人数，更新发送对象的状态为已读
          SchoolNotify schoolNotify=new SchoolNotify();
          schoolNotify.setId(schoolNotifySendObject.getSchoolNotify().getId());
          SchoolNotify schoolNotify1= schoolNotifyService.findOneSchoolNotifyByCondition(schoolNotify);
          schoolNotify1.setAlreadyNum(schoolNotify1.getAlreadyNum()+1);
          schoolNotifyService.updateSchoolNotify(schoolNotify1);
          schoolNotifySendObject.setSchoolNotify(null);
          schoolNotifySendObject.setReadState("2");
          schoolNotifySendObjectService.updateSchoolNotifySendObject(schoolNotifySendObject);

            //更新班级的已读人数
          if(sendObject.getSchoolNotify().getSendType().equals("2")){
              SchoolNotifySendObject schoolNotifySendObject1=new SchoolNotifySendObject();
              schoolNotifySendObject1.setObjectId(sendObject.getDepartmentParentId());
              schoolNotifySendObject1.setSchoolNotify(schoolNotify);
              schoolNotifySendObject1= schoolNotifySendObjectService.findOneSchoolNotifySendObjectByCondition(schoolNotifySendObject1);
              schoolNotifySendObject1.setClassReadNum(schoolNotifySendObject1.getClassReadNum()+1);
              schoolNotifySendObjectService.updateSchoolNotifySendObject(schoolNotifySendObject1);
          }
      }

    }

    @PostMapping("/updateSchoolNotifySendObject1")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateSchoolNotifySendObject1(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody SchoolNotifySendObject schoolNotifySendObject){
        schoolNotifySendObjectService.updateSchoolNotifySendObject(schoolNotifySendObject);
    }

    @GetMapping("/deleteSchoolNotifySendObject/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteSchoolNotifySendObject(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        schoolNotifySendObjectService.deleteSchoolNotifySendObject(id);
    }
    @PostMapping("/deleteSchoolNotifySendObjectByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteSchoolNotifySendObjectByCondition(
            @ApiParam(value = "对象")
            @RequestBody SchoolNotifySendObject schoolNotifySendObject){
        schoolNotifySendObjectService.deleteSchoolNotifySendObjectByCondition(schoolNotifySendObject);
    }
    @PostMapping("/findOneSchoolNotifySendObjectByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public SchoolNotifySendObject findOneSchoolNotifySendObjectByCondition(
            @ApiParam(value = "对象")
            @RequestBody SchoolNotifySendObject schoolNotifySendObject){
        return schoolNotifySendObjectService.findOneSchoolNotifySendObjectByCondition(schoolNotifySendObject);
    }



    @PostMapping("/getSchoolNotifyReadDetail")
    @ApiOperation(value = "获取通知读取人数详情", notes = "返回单个,没有时为空")
    public  List<Department> getSchoolNotifyReadDetail(
            @ApiParam(value = "对象")
            @RequestBody SchoolNotifySendObject schoolNotifySendObject){
        return schoolNotifySendObjectService.getSchoolNotifyReadDetail(schoolNotifySendObject);
    }

    @PostMapping("/getSchoolNotifyReadDetailCount")
    @ApiOperation(value = "获取通知读取人数详情", notes = "返回单个,没有时为空")
    public long getSchoolNotifyReadDetailCount(
            @ApiParam(value = "对象")
            @RequestBody SchoolNotifySendObject schoolNotifySendObject){
        return schoolNotifySendObjectService.getSchoolNotifyReadDetailCount(schoolNotifySendObject);
    }
}
