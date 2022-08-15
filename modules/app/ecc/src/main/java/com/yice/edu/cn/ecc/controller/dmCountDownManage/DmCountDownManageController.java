package com.yice.edu.cn.ecc.controller.dmCountDownManage;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.dmMobileRedBanner.DmCountDownManage;
import com.yice.edu.cn.ecc.service.dmCountDownManage.DmCountDownManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.yice.edu.cn.ecc.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dmCountDownManage")
@Api(value = "/dmCountDownManage", description = "倒计时管理模块")
public class DmCountDownManageController {
    @Autowired
    private DmCountDownManageService dmCountDownManageService;

    @PostMapping("/saveDmCountDownManage")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response = DmCountDownManage.class)
    public ResponseJson saveDmCountDownManage(
            @ApiParam(value = "对象", required = true)
            @RequestBody DmCountDownManage dmCountDownManage) {
        dmCountDownManage.setSchoolId(mySchoolId());
        DmCountDownManage s = dmCountDownManageService.saveDmCountDownManage(dmCountDownManage);
        return new ResponseJson(s);
    }


    @PostMapping("/updateDmCountDownManage")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateDmCountDownManage(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody DmCountDownManage dmCountDownManage) {
        dmCountDownManageService.updateDmCountDownManage(dmCountDownManage);
        return new ResponseJson();
    }

    @GetMapping("/findDmCountDownManageById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response = DmCountDownManage.class)
    public ResponseJson findDmCountDownManageById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        DmCountDownManage dmCountDownManage = dmCountDownManageService.findDmCountDownManageById(id);
        return new ResponseJson(dmCountDownManage);
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
        dmCountDownManage.setStatus(Constant.DM_COUNT_DOWN_MANAGE.MANAGE_VALID);
        List<DmCountDownManage> data = dmCountDownManageService.findDmCountDownManageListByCondition(dmCountDownManage);

        if (data != null && !data.isEmpty()) {
            List<DmCountDownManage> newDmList = dateFormat(data);
            return new ResponseJson(newDmList);
        } else {
            return new ResponseJson();
        }
    }

    /**
     * 校级班牌，无需过滤，直接做时间处理
     *
     * @param list
     * @return
     */
    private static List<DmCountDownManage> dateFormat(List<DmCountDownManage> list) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long currentTime = System.currentTimeMillis();

        List<DmCountDownManage> data = list.stream().map(skt -> {
            Date breakTime = null;
            try {
                breakTime = df.parse(skt.getBreakTime());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                skt.setBreakTime(sdf.format(breakTime));

                double time = 1000 * 60 * 60 * 24;
                double dayNumber = ((double) breakTime.getTime() - (double) currentTime) / time;
                double ceil = Math.ceil(dayNumber);
                skt.setNumber((int) ceil);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return skt;
        }).collect(Collectors.toList());

        return data;
    }

}
