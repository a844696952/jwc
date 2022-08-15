package com.yice.edu.cn.ecc.controller.dmClassMeeting;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.dmClassMeeting.DmClassMeeting;
import com.yice.edu.cn.ecc.service.dmClassMeeting.DmClassMeetingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.ecc.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dmClassMeeting")
@Api(value = "/dmClassMeeting",description = "电子班牌班会表模块")
public class DmClassMeetingController {
    @Autowired
    private DmClassMeetingService dmClassMeetingService;


    @GetMapping("/update/findDmClassMeetingById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找电子班牌班会表", notes = "返回响应对象", response=DmClassMeeting.class)
    public ResponseJson findDmClassMeetingById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DmClassMeeting dmClassMeeting=dmClassMeetingService.findDmClassMeetingById(id);
        return new ResponseJson(dmClassMeeting);
    }


    @PostMapping("/findDmClassMeetingsByCondition")
    @ApiOperation(value = "根据条件查找电子班牌班会表", notes = "返回响应对象", response=DmClassMeeting.class)
    public ResponseJson findDmClassMeetingsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmClassMeeting dmClassMeeting){
       dmClassMeeting.setSchoolId(mySchoolId());
        List<DmClassMeeting> data=dmClassMeetingService.findDmClassMeetingListByCondition(dmClassMeeting);
        long count=dmClassMeetingService.findDmClassMeetingCountByCondition(dmClassMeeting);
        return new ResponseJson(data,count);
    }



}
