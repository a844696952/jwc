package com.yice.edu.cn.dm.controller.sturespMsg;

import com.yice.edu.cn.common.pojo.dm.sturespMsg.Sturespmsg;
import com.yice.edu.cn.dm.service.sturespMsg.SturespmsgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sturespmsg")
@Api(value = "/sturespmsg",description = "模块")
public class SturespmsgController {
    @Autowired
    private SturespmsgService sturespmsgService;

    @GetMapping("/findSturespmsgById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public Sturespmsg findSturespmsgById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return sturespmsgService.findSturespmsgById(id);
    }

    @PostMapping("/saveSturespmsg")
    @ApiOperation(value = "保存", notes = "返回对象")
    public Sturespmsg saveSturespmsg(
            @ApiParam(value = "对象", required = true)
            @RequestBody Sturespmsg sturespmsg){
        sturespmsgService.saveSturespmsg(sturespmsg);
        return sturespmsg;
    }

    @PostMapping("/findSturespmsgListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<Sturespmsg> findSturespmsgListByCondition(
            @ApiParam(value = "对象")
            @RequestBody Sturespmsg sturespmsg){
        return sturespmsgService.findSturespmsgListByCondition(sturespmsg);
    }
    @PostMapping("/findSturespmsgCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findSturespmsgCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody Sturespmsg sturespmsg){
        return sturespmsgService.findSturespmsgCountByCondition(sturespmsg);
    }

    @PostMapping("/updateSturespmsg")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateSturespmsg(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody Sturespmsg sturespmsg){
        sturespmsgService.updateSturespmsg(sturespmsg);
    }

    @GetMapping("/deleteSturespmsg/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteSturespmsg(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        sturespmsgService.deleteSturespmsg(id);
    }
    @PostMapping("/deleteSturespmsgByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteSturespmsgByCondition(
            @ApiParam(value = "对象")
            @RequestBody Sturespmsg sturespmsg){
        sturespmsgService.deleteSturespmsgByCondition(sturespmsg);
    }
    @PostMapping("/findOneSturespmsgByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public Sturespmsg findOneSturespmsgByCondition(
            @ApiParam(value = "对象")
            @RequestBody Sturespmsg sturespmsg){
        return sturespmsgService.findOneSturespmsgByCondition(sturespmsg);
    }

    @PostMapping("/deleteSturespmsgBeforeTwoDay")
    @ApiOperation(value = "删除两天前的学生消息", notes = "删除两天前的学生消息")
    public void deleteSturespmsgBeforeTwoDay(){
        sturespmsgService.deleteSturespmsgBeforeTwoDay();
    }
}
