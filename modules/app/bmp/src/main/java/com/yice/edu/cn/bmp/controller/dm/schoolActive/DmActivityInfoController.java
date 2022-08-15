package com.yice.edu.cn.bmp.controller.dm.schoolActive;

import com.yice.edu.cn.bmp.service.dm.schoolActive.DmActivityInfoService;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.schoolActive.DmActivityInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dmActivityInfo")
@Api(value = "/dmActivityInfo",description = "活动信息表模块")
public class DmActivityInfoController {

    @Autowired
    private DmActivityInfoService dmActivityInfoService;




    @GetMapping("/findDmActivityInfoByActivityId/{activityId}")
    @ApiOperation(value = "通过activityId查找活动信息表", notes = "返回响应对象", response= DmActivityInfo.class)
    public ResponseJson findDmActivityInfoByActivityId(
            @ApiParam(value = "需要用到的activityId", required = true)
            @PathVariable String activityId){
        DmActivityInfo dmActivityInfo=dmActivityInfoService.findDmActivityInfoByActivityId(activityId);
        return new ResponseJson(dmActivityInfo);
    }

}
