package com.yice.edu.cn.osp.controller.dm.ycVeriface;

import cn.hutool.core.collection.CollectionUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.blacklist.YcVerifaceBlacklist;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.personEnter.YcVerifacePersonEnter;
import com.yice.edu.cn.osp.service.dm.ycVeriface.YcVerifaceBlacklistService;
import com.yice.edu.cn.osp.service.dm.ycVeriface.YcVerifacePersonEnterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/ycVerifaceBlacklist")
@Api(value = "/ycVerifaceBlacklist", description = "人脸识别黑名单")
public class YcVerifaceBlacklistController {
    @Autowired
    private YcVerifaceBlacklistService ycVerifaceBlacklistService;
    @Autowired
    private YcVerifacePersonEnterService ycVerifacePersonEnterService;
    @Value("#{'${spring.profiles.active}'=='prod'}")
    private Boolean isProd;

    @PostMapping("/saveYcVerifaceBlacklist")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response = YcVerifaceBlacklist.class)
    public ResponseJson saveYcVerifaceBlacklist(
            @ApiParam(value = "对象", required = true)
            @RequestBody YcVerifaceBlacklist ycVerifaceBlacklist) {
        ycVerifaceBlacklist.setSchoolId(mySchoolId());
        YcVerifaceBlacklist s = ycVerifaceBlacklistService.saveYcVerifaceBlacklist(ycVerifaceBlacklist);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findYcVerifaceBlacklistById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response = YcVerifaceBlacklist.class)
    public ResponseJson findYcVerifaceBlacklistById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        YcVerifaceBlacklist ycVerifaceBlacklist = ycVerifaceBlacklistService.findYcVerifaceBlacklistById(id);
        return new ResponseJson(ycVerifaceBlacklist);
    }

    @PostMapping("/update/updateYcVerifaceBlacklist")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateYcVerifaceBlacklist(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody YcVerifaceBlacklist ycVerifaceBlacklist) {
        ycVerifaceBlacklistService.updateYcVerifaceBlacklist(ycVerifaceBlacklist);
        return new ResponseJson();
    }

    @GetMapping("/look/lookYcVerifaceBlacklistById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response = YcVerifaceBlacklist.class)
    public ResponseJson lookYcVerifaceBlacklistById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        YcVerifaceBlacklist ycVerifaceBlacklist = ycVerifaceBlacklistService.findYcVerifaceBlacklistById(id);
        return new ResponseJson(ycVerifaceBlacklist);
    }

    @PostMapping("/findYcVerifaceBlacklistsByCondition")
    @ApiOperation(value = "根据条件查找陌生人列表", notes = "返回响应对象", response = YcVerifaceBlacklist.class)
    public ResponseJson findYcVerifaceBlacklistsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody YcVerifaceBlacklist ycVerifaceBlacklist) {
        Pager pager = ycVerifaceBlacklist.getPager();
        if (ycVerifaceBlacklist.getRangeTime() != null && ycVerifaceBlacklist.getRangeTime().length == 2) {
            pager.setRangeField("createTime");
            pager.setRangeArray(ycVerifaceBlacklist.getRangeTime());
        }
        ycVerifaceBlacklist.setRangeTime(null);
        ycVerifaceBlacklist.setSchoolId(mySchoolId());
        List<YcVerifaceBlacklist> data = ycVerifaceBlacklistService.findYcVerifaceBlacklistListByCondition(ycVerifaceBlacklist);
        long count = ycVerifaceBlacklistService.findYcVerifaceBlacklistCountByCondition(ycVerifaceBlacklist);
        data = ycVerifacePersonEnterService.addBlackListCheckStatus(data, Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_BLACKPERSON);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneYcVerifaceBlacklistByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response = YcVerifaceBlacklist.class)
    public ResponseJson findOneYcVerifaceBlacklistByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody YcVerifaceBlacklist ycVerifaceBlacklist) {
        YcVerifaceBlacklist one = ycVerifaceBlacklistService.findOneYcVerifaceBlacklistByCondition(ycVerifaceBlacklist);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteYcVerifaceBlacklist/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteYcVerifaceBlacklist(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        ycVerifaceBlacklistService.deleteYcVerifaceBlacklist(id);
        return new ResponseJson();
    }


    @PostMapping("/findYcVerifaceBlacklistListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response = YcVerifaceBlacklist.class)
    public ResponseJson findYcVerifaceBlacklistListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody YcVerifaceBlacklist ycVerifaceBlacklist) {
        ycVerifaceBlacklist.setSchoolId(mySchoolId());
        List<YcVerifaceBlacklist> data = ycVerifaceBlacklistService.findYcVerifaceBlacklistListByCondition(ycVerifaceBlacklist);
        return new ResponseJson(data);
    }


    //校验教师头像
    @PostMapping("/checkBlacklistPic")
    @ApiOperation(value = "中移批量校验考勤人员图像信息,更新教师数据")
    public ResponseJson checkBlacklistPic(
            @ApiParam(value = "教师", required = true)
            @RequestBody YcVerifaceBlacklist blacklist) {
        if (blacklist.getPager()!=null){
            blacklist.getPager().setPaging(false);
        }
        blacklist.setSchoolId(mySchoolId());
        List<YcVerifaceBlacklist> blacklists = ycVerifacePersonEnterService.getAllCheckBlacklist(blacklist);
        List<List<YcVerifaceBlacklist>> batchList = ycVerifacePersonEnterService.getBlacklistBatchList(blacklists, 50);
        //请求人脸服务器校验并获得校验结果数据
        Integer personType = null;
        if (CollectionUtil.isEmpty(blacklists)){
            return new ResponseJson(false, "请确认录入人员和头像信息");
        }
        List<YcVerifacePersonEnter> reslist = ycVerifacePersonEnterService.requestYcBlackListEnter(isProd, batchList,personType);
        if (CollectionUtil.isEmpty(reslist)) {
            return new ResponseJson(reslist);
        }
        //获得检验成功数据,剔除失败数据
        List<YcVerifaceBlacklist> enters = ycVerifacePersonEnterService.getEnterBlacklistData(reslist, blacklists);
        if (CollectionUtil.isEmpty(enters)) {
            return new ResponseJson(reslist);
        }
        //更新校验成功结果集
        List<YcVerifacePersonEnter> res = ycVerifacePersonEnterService.updateSuccessBlacklist(reslist, enters);
        //返回校验结果数据
        return new ResponseJson(res);
    }
}
