package com.yice.edu.cn.xw.controller.stuLeaveManage;

import com.yice.edu.cn.common.pojo.xw.stuLeaveManage.NotifyPerson;
import com.yice.edu.cn.xw.service.stuLeaveManage.NotifyPersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifyPerson")
@Api(value = "/notifyPerson",description = "模块")
public class NotifyPersonController {
    @Autowired
    private NotifyPersonService notifyPersonService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findNotifyPersonById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public NotifyPerson findNotifyPersonById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return notifyPersonService.findNotifyPersonById(id);
    }

    @PostMapping("/saveNotifyPerson")
    @ApiOperation(value = "保存", notes = "返回对象")
    public NotifyPerson saveNotifyPerson(
            @ApiParam(value = "对象", required = true)
            @RequestBody NotifyPerson notifyPerson){
        notifyPersonService.saveNotifyPerson(notifyPerson);
        return notifyPerson;
    }

    @PostMapping("/batchSaveNotifyPerson")
    @ApiOperation(value = "批量保存")
    public void batchSaveNotifyPerson(
            @ApiParam(value = "对象集合", required = true)
            @RequestBody List<NotifyPerson> notifyPersons){
        notifyPersonService.batchSaveNotifyPerson(notifyPersons);
    }

    @PostMapping("/findNotifyPersonListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<NotifyPerson> findNotifyPersonListByCondition(
            @ApiParam(value = "对象")
            @RequestBody NotifyPerson notifyPerson){
        return notifyPersonService.findNotifyPersonListByCondition(notifyPerson);
    }
    @PostMapping("/findNotifyPersonCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findNotifyPersonCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody NotifyPerson notifyPerson){
        return notifyPersonService.findNotifyPersonCountByCondition(notifyPerson);
    }

    @PostMapping("/updateNotifyPerson")
    @ApiOperation(value = "修改有值的字段", notes = "对象必传")
    public void updateNotifyPerson(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody NotifyPerson notifyPerson){
        notifyPersonService.updateNotifyPerson(notifyPerson);
    }
    @PostMapping("/updateNotifyPersonForAll")
    @ApiOperation(value = "修改所有字段", notes = "对象必传")
    public void updateNotifyPersonForAll(
            @ApiParam(value = "对象", required = true)
            @RequestBody NotifyPerson notifyPerson){
        notifyPersonService.updateNotifyPersonForAll(notifyPerson);
    }

    @GetMapping("/deleteNotifyPerson/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteNotifyPerson(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        notifyPersonService.deleteNotifyPerson(id);
    }
    @PostMapping("/deleteNotifyPersonByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteNotifyPersonByCondition(
            @ApiParam(value = "对象")
            @RequestBody NotifyPerson notifyPerson){
        notifyPersonService.deleteNotifyPersonByCondition(notifyPerson);
    }
    @PostMapping("/findOneNotifyPersonByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public NotifyPerson findOneNotifyPersonByCondition(
            @ApiParam(value = "对象")
            @RequestBody NotifyPerson notifyPerson){
        return notifyPersonService.findOneNotifyPersonByCondition(notifyPerson);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
