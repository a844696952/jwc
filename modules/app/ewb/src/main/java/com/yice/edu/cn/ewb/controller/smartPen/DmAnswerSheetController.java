package com.yice.edu.cn.ewb.controller.smartPen;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.smartPen.DmAnswerSheet;
import com.yice.edu.cn.ewb.service.smartPen.DmAnswerSheetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.ewb.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.ewb.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dmAnswerSheet")
@Api(value = "/dmAnswerSheet",description = "答题卡表模块")
public class DmAnswerSheetController {
    @Autowired
    private DmAnswerSheetService dmAnswerSheetService;

    @PostMapping("/saveDmAnswerSheet")
    @ApiOperation(value = "保存答题卡表对象", notes = "返回保存好的答题卡表对象", response= DmAnswerSheet.class)
    public ResponseJson saveDmAnswerSheet(
            @ApiParam(value = "答题卡表对象", required = true)
            @RequestBody DmAnswerSheet dmAnswerSheet){
        dmAnswerSheet.setSchoolId(mySchoolId());
        dmAnswerSheet.setTeacherId(myId());
        dmAnswerSheet.setCreateDate(DateUtil.format(DateTime.now(),"yyyy-MM-dd HH:mm:ss"));
        DmAnswerSheet s=dmAnswerSheetService.saveDmAnswerSheet(dmAnswerSheet);
        return new ResponseJson(s);
    }


    @PostMapping("/updateDmAnswerSheet")
    @ApiOperation(value = "修改答题卡表对象", notes = "返回响应对象")
    public ResponseJson updateDmAnswerSheet(
            @ApiParam(value = "被修改的答题卡表对象,对象属性不为空则修改", required = true)
            @RequestBody DmAnswerSheet dmAnswerSheet){
        dmAnswerSheetService.updateDmAnswerSheet(dmAnswerSheet);
        return new ResponseJson();
    }

    @GetMapping("/findDmAnswerSheetById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找答题卡表", notes = "返回响应对象", response=DmAnswerSheet.class)
    public ResponseJson findDmAnswerSheetById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        DmAnswerSheet dmAnswerSheet=dmAnswerSheetService.findDmAnswerSheetById(id);
        return new ResponseJson(dmAnswerSheet);
    }

    @GetMapping("/deleteDmAnswerSheet/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDmAnswerSheet(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        dmAnswerSheetService.deleteDmAnswerSheet(id);
        return new ResponseJson();
    }


    @PostMapping("/findDmAnswerSheetListByCondition")
    @ApiOperation(value = "根据条件查找答题卡表列表", notes = "返回响应对象,不包含总条数", response=DmAnswerSheet.class)
    public ResponseJson findDmAnswerSheetListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmAnswerSheet dmAnswerSheet){
        List<DmAnswerSheet> data=dmAnswerSheetService.findDmAnswerSheetListByCondition(dmAnswerSheet);
        return new ResponseJson(data);
    }



}
