package com.yice.edu.cn.osp.controller.dm.dmMobileRedBanner;

import com.yice.edu.cn.common.annotation.EccJpush;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import com.yice.edu.cn.common.pojo.dm.dmMobileRedBanner.DmCountDownManage;
import com.yice.edu.cn.common.pojo.dm.dmMobileRedBanner.DmCountDownManageClass;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.osp.service.dm.classCard.DmClassCardService;
import com.yice.edu.cn.osp.service.dm.dmMobileRedBanner.DmCountDownManageService;
import com.yice.edu.cn.osp.service.jw.classes.JwClassesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dmCountDownManage")
@Api(value = "/dmCountDownManage", description = "倒计时管理模块")
public class DmCountDownManageController {
    @Autowired
    private DmCountDownManageService dmCountDownManageService;
    @Autowired
    private DmClassCardService dmClassCardService;


    @PostMapping("/saveDmCountDownManage")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response = DmCountDownManage.class)
    public ResponseJson saveDmCountDownManage(
            @ApiParam(value = "对象", required = true)
            @RequestBody DmCountDownManage dmCountDownManage) {
        dmCountDownManage.setSchoolId(mySchoolId());
        DmCountDownManage s = dmCountDownManageService.saveDmCountDownManage(dmCountDownManage);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findDmCountDownManageById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response = DmCountDownManage.class)
    public ResponseJson findDmCountDownManageById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        DmCountDownManage dmCountDownManage = dmCountDownManageService.findDmCountDownManageById(id);
        return new ResponseJson(dmCountDownManage);
    }

    @PostMapping("/update/updateDmCountDownManage")
    @ApiOperation(value = "修改对象非空字段", notes = "返回响应对象")
    public ResponseJson updateDmCountDownManage(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody DmCountDownManage dmCountDownManage) {
        dmCountDownManageService.updateDmCountDownManage(dmCountDownManage);
        return new ResponseJson();
    }

    @PostMapping("/update/updateDmCountDownManageStatus")
    @ApiOperation(value = "修改状态", notes = "返回响应对象")
    @EccJpush(type = Extras.DM_COUNTDOWN_MSG, content = "实时更新倒计时数据")
    public ResponseJson updateDmCountDownManageStatus(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody DmCountDownManage dmCountDownManage) {
        dmCountDownManage.setSchoolId(mySchoolId());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long currentTime = System.currentTimeMillis();
        String breakTime = dmCountDownManage.getBreakTime();
        Date dateTime = null;
        try {
            dateTime = df.parse(breakTime);
            if (dateTime.getTime() < currentTime && dmCountDownManage.getStatus() == Constant.DM_COUNT_DOWN_MANAGE.MANAGE_VALID) {
                return new ResponseJson(false);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dmCountDownManageService.updateDmCountDownManageStatus(dmCountDownManage);
        return new ResponseJson(true);
    }

    @PostMapping("/update/updateDmCountDownManageForAll")
    @ApiOperation(value = "修改对象所有字段", notes = "返回响应对象")
    public ResponseJson updateDmCountDownManageForAll(
            @ApiParam(value = "被修改的对象", required = true)
            @RequestBody DmCountDownManage dmCountDownManage) {
        dmCountDownManageService.updateDmCountDownManageForAll(dmCountDownManage);
        return new ResponseJson();
    }

    @GetMapping("/look/lookDmCountDownManageById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response = DmCountDownManage.class)
    public ResponseJson lookDmCountDownManageById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        DmCountDownManage dmCountDownManage = dmCountDownManageService.findDmCountDownManageById(id);
        return new ResponseJson(dmCountDownManage);
    }

    @PostMapping("/findDmCountDownManagesByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response = DmCountDownManage.class)
    public ResponseJson findDmCountDownManagesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmCountDownManage dmCountDownManage) {
        dmCountDownManage.setSchoolId(mySchoolId());
        List<DmCountDownManage> data = dmCountDownManageService.findDmCountDownManageListByCondition(dmCountDownManage);
        long count = dmCountDownManageService.findDmCountDownManageCountByCondition(dmCountDownManage);
        //配合前端做状态切换
        data.stream().forEach(skt -> {
            Integer status = skt.getStatus();
            if (status == Constant.DM_COUNT_DOWN_MANAGE.MANAGE_DISABLE) {
                skt.setResult(false);
            } else if (status == Constant.DM_COUNT_DOWN_MANAGE.MANAGE_VALID) {
                skt.setResult(true);
            }
        });
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneDmCountDownManageByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response = DmCountDownManage.class)
    public ResponseJson findOneDmCountDownManageByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmCountDownManage dmCountDownManage) {
        DmCountDownManage one = dmCountDownManageService.findOneDmCountDownManageByCondition(dmCountDownManage);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteDmCountDownManage/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDmCountDownManage(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        dmCountDownManageService.deleteDmCountDownManage(id);
        return new ResponseJson();
    }


    @PostMapping("/findDmCountDownManageListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response = DmCountDownManage.class)
    public ResponseJson findDmCountDownManageListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmCountDownManage dmCountDownManage) {
        dmCountDownManage.setSchoolId(mySchoolId());
        List<DmCountDownManage> data = dmCountDownManageService.findDmCountDownManageListByCondition(dmCountDownManage);
        return new ResponseJson(data);
    }


    @GetMapping("/findDmCardTree")
    @ApiOperation(value = "去更新页面，查当前学校云班牌树", notes = "返回云班牌树", response = DmClassCard.class)
    public ResponseJson findDmCardTree() {
        DmClassCard dmClassCard = new DmClassCard();
        dmClassCard.setSchoolId(mySchoolId());
        List<DmClassCard> dmClassCardList = dmClassCardService.getDmClassCardTree(dmClassCard);
        return new ResponseJson(dmClassCardList);
    }

}
