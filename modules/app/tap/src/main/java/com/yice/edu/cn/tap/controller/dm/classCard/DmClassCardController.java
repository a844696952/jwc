package com.yice.edu.cn.tap.controller.dm.classCard;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.classCard.ClassCardLock;
import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import com.yice.edu.cn.tap.service.dm.classCard.DmClassCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.myId;


@RestController
@RequestMapping("/dmClassCard")
@Api(value = "/dmClassCard",description = "云班牌设备管理模块")
public class DmClassCardController {
    @Autowired
    private DmClassCardService dmClassCardService;

    @PostMapping("/batchChangeLockStatusByIds")
    @ApiOperation(value = "根据条件批量更新班牌锁屏状态", notes = "返回响应对象,不包含总条数")
    public ResponseJson batchChangeLockStatusByIds(
            @ApiParam(value = "根据条件批量更新班牌锁屏状态")
            @Validated
            @RequestBody ClassCardLock classCardLock) {
        return dmClassCardService.batchChangeLockStatusByIds(classCardLock);
    }

    @GetMapping("/lockDmScreen/{id}")
    @ApiOperation(value = "锁屏", notes = "返回响应对象")
    public ResponseJson lockDmScreen(
            @ApiParam(value = "锁屏的id", required = true)
            @PathVariable String id) {
        return dmClassCardService.lockDmScreen(id);
    }

    @GetMapping("/unLockDmScreen/{id}")
    @ApiOperation(value = "解锁", notes = "返回响应对象")
    public ResponseJson unLockDmScreen(
            @ApiParam(value = "解锁的id", required = true)
            @PathVariable String id) {
        dmClassCardService.unLockDmScreen(id);
        return new ResponseJson();
    }

    @GetMapping("/getDmClassCardByTeacherId/{lockStatus}")
    @ApiOperation(value = "通过讲师编号获取当前所管理的班牌", notes = "返回响应对象")
    public ResponseJson getDmClassCardByTeacherId(
            @ApiParam(value = "通过讲师编号获取当前所管理的班牌", required = true)
            @PathVariable(value = "lockStatus") String lockStatus) {
        List<DmClassCard> dmClassCardList = dmClassCardService.getDmClassCardByTeacherId(myId(),lockStatus);
        if(dmClassCardList.size()>0){
            return new ResponseJson(dmClassCardList);
        }else{
            return new ResponseJson(false,"无数据");
        }


    }

}
