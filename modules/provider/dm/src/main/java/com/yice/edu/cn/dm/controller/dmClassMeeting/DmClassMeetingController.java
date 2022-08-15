package com.yice.edu.cn.dm.controller.dmClassMeeting;

import com.yice.edu.cn.common.pojo.dm.dmClassMeeting.DmClassMeeting;
import com.yice.edu.cn.common.pojo.dm.schoolActive.DmAttachmentFile;
import com.yice.edu.cn.dm.service.dmClassMeeting.DmClassMeetingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dmClassMeeting")
@Api(value = "/dmClassMeeting",description = "电子班牌班会表模块")
public class DmClassMeetingController {
    @Autowired
    private DmClassMeetingService dmClassMeetingService;

    @GetMapping("/findDmClassMeetingById/{id}")
    @ApiOperation(value = "通过id查找电子班牌班会表", notes = "返回电子班牌班会表对象")
    public DmClassMeeting findDmClassMeetingById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dmClassMeetingService.findDmClassMeetingById(id);
    }

    @PostMapping("/saveDmClassMeeting")
    @ApiOperation(value = "保存电子班牌班会表", notes = "返回电子班牌班会表对象")
    public Boolean saveDmClassMeeting(
            @ApiParam(value = "电子班牌班会表对象", required = true)
            @RequestBody DmClassMeeting dmClassMeeting){
        return dmClassMeetingService.saveDmClassMeeting(dmClassMeeting);
    }

    @PostMapping("/findDmClassMeetingListByCondition")
    @ApiOperation(value = "根据条件查找电子班牌班会表列表", notes = "返回电子班牌班会表列表")
    public List<DmClassMeeting> findDmClassMeetingListByCondition(
            @ApiParam(value = "电子班牌班会表对象")
            @RequestBody DmClassMeeting dmClassMeeting){
        return dmClassMeetingService.findDmClassMeetingListByCondition(dmClassMeeting);
    }
    @PostMapping("/findDmClassMeetingCountByCondition")
    @ApiOperation(value = "根据条件查找电子班牌班会表列表个数", notes = "返回电子班牌班会表总个数")
    public long findDmClassMeetingCountByCondition(
            @ApiParam(value = "电子班牌班会表对象")
            @RequestBody DmClassMeeting dmClassMeeting){
        return dmClassMeetingService.findDmClassMeetingCountByCondition(dmClassMeeting);
    }

    @PostMapping("/updateDmClassMeeting")
    @ApiOperation(value = "修改电子班牌班会表", notes = "电子班牌班会表对象必传")
    public void updateDmClassMeeting(
            @ApiParam(value = "电子班牌班会表对象,对象属性不为空则修改", required = true)
            @RequestBody DmClassMeeting dmClassMeeting){
        dmClassMeetingService.updateDmClassMeeting(dmClassMeeting);
    }

    @GetMapping("/deleteDmClassMeeting/{id}")
    @ApiOperation(value = "通过id删除电子班牌班会表")
    public void deleteDmClassMeeting(
            @ApiParam(value = "电子班牌班会表对象", required = true)
            @PathVariable String id){
        dmClassMeetingService.deleteDmClassMeeting(id);
    }
    @PostMapping("/deleteDmClassMeetingByCondition")
    @ApiOperation(value = "根据条件删除电子班牌班会表")
    public void deleteDmClassMeetingByCondition(
            @ApiParam(value = "电子班牌班会表对象")
            @RequestBody DmClassMeeting dmClassMeeting){
        dmClassMeetingService.deleteDmClassMeetingByCondition(dmClassMeeting);
    }
    @PostMapping("/findOneDmClassMeetingByCondition")
    @ApiOperation(value = "根据条件查找单个电子班牌班会表,结果必须为单条数据", notes = "返回单个电子班牌班会表,没有时为空")
    public DmClassMeeting findOneDmClassMeetingByCondition(
            @ApiParam(value = "电子班牌班会表对象")
            @RequestBody DmClassMeeting dmClassMeeting){
        return dmClassMeetingService.findOneDmClassMeetingByCondition(dmClassMeeting);
    }
    @GetMapping("/findDmClassMeetingImgsByclassId/{classId}")
    @ApiOperation(value = "通过classId查找电子班牌班会照片地址(只取时间倒序头两张)", notes = "返回照片地址字符串集合")
    public List<DmAttachmentFile> findDmClassMeetingImgsByclassId(
            @ApiParam(value = "需要用到的classId", required = true)
            @PathVariable String classId){
        return dmClassMeetingService.findDmClassMeetingImgsByclassId(classId);
    }
}
