package com.yice.edu.cn.osp.controller.xw.searchGoods;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.annotation.EccJpush;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.xw.searchGoods.XwSearchGoods;
import com.yice.edu.cn.osp.service.xw.searchGoods.XwSearchGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/xwSearchGoods")
@Api(value = "/xwSearchGoods",description = "寻找物品表模块")
public class XwSearchGoodsController {
    @Autowired
    private XwSearchGoodsService xwSearchGoodsService;

    @PostMapping("/saveXwSearchGoods")
    @ApiOperation(value = "保存寻找物品表对象", notes = "返回响应对象")
    @EccJpush(type = Extras.DM_LOSTANDFOUND_MSG,content = "寻找物品表列表")
    public ResponseJson saveXwSearchGoods(
            @ApiParam(value = "寻找物品表对象", required = true)
            @RequestBody XwSearchGoods xwSearchGoods){
       xwSearchGoods.setSchoolId(mySchoolId());
        XwSearchGoods s=xwSearchGoodsService.saveXwSearchGoods(xwSearchGoods);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findXwSearchGoodsById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找寻找物品表", notes = "返回响应对象")
    public ResponseJson findXwSearchGoodsById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        XwSearchGoods xwSearchGoods=xwSearchGoodsService.findXwSearchGoodsById(id);
        return new ResponseJson(xwSearchGoods);
    }

    @PostMapping("/update/updateXwSearchGoods")
    @ApiOperation(value = "修改寻找物品表对象", notes = "返回响应对象")
    @EccJpush(type = Extras.DM_LOSTANDFOUND_MSG,content = "寻找物品表列表")
    public ResponseJson updateXwSearchGoods(
            @ApiParam(value = "被修改的寻找物品表对象,对象属性不为空则修改", required = true)
            @RequestBody XwSearchGoods xwSearchGoods){
        xwSearchGoodsService.updateXwSearchGoods(xwSearchGoods);
        return new ResponseJson();
    }

    @GetMapping("/look/lookXwSearchGoodsById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找寻找物品表", notes = "返回响应对象")
    public ResponseJson lookXwSearchGoodsById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        XwSearchGoods xwSearchGoods=xwSearchGoodsService.findXwSearchGoodsById(id);
        return new ResponseJson(xwSearchGoods);
    }

    @PostMapping("/findXwSearchGoodssByCondition")
    @ApiOperation(value = "根据条件查找寻找物品表", notes = "返回响应对象")
    public ResponseJson findXwSearchGoodssByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwSearchGoods xwSearchGoods){
       xwSearchGoods.setSchoolId(mySchoolId());
       if(xwSearchGoods.getStartTime() != null  && xwSearchGoods.getEndTime() !=null ){
           String startTime = DateUtil.format( DateUtil.beginOfDay(DateUtil.parse(xwSearchGoods.getStartTime())), "yyyy-MM-dd HH:mm:ss");
           String endTime = DateUtil.format( DateUtil.endOfDay(DateUtil.parse(xwSearchGoods.getEndTime())), "yyyy-MM-dd HH:mm:ss");
           xwSearchGoods.setStartTime(startTime);
           xwSearchGoods.setEndTime(endTime);
       }


        List<XwSearchGoods> data=xwSearchGoodsService.findXwSearchGoodsListByCondition(xwSearchGoods);
        long count=xwSearchGoodsService.findXwSearchGoodsCountByCondition(xwSearchGoods);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneXwSearchGoodsByCondition")
    @ApiOperation(value = "根据条件查找单个寻找物品表,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneXwSearchGoodsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody XwSearchGoods xwSearchGoods){
        XwSearchGoods one=xwSearchGoodsService.findOneXwSearchGoodsByCondition(xwSearchGoods);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteXwSearchGoods/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    @EccJpush(type = Extras.DM_LOSTANDFOUND_MSG,content = "寻找物品表列表")
    public ResponseJson deleteXwSearchGoods(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        xwSearchGoodsService.deleteXwSearchGoods(id);
        return new ResponseJson();
    }


    @PostMapping("/findXwSearchGoodsListByCondition")
    @ApiOperation(value = "根据条件查找寻找物品表列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findXwSearchGoodsListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwSearchGoods xwSearchGoods){
       xwSearchGoods.setSchoolId(mySchoolId());
        List<XwSearchGoods> data=xwSearchGoodsService.findXwSearchGoodsListByCondition(xwSearchGoods);
        return new ResponseJson(data);
    }

    @PostMapping("/update/updateXwSearchGoodsReturn")
    @EccJpush(type = Extras.DM_LOSTANDFOUND_MSG,content = "寻找物品表列表")
    @ApiOperation(value = "修改寻找物品表对象(归还)", notes = "返回响应对象")
    public ResponseJson updateXwSearchGoodsReturn(
            @ApiParam(value = "被修改的寻找物品表对象,对象属性不为空则修改", required = true)
            @RequestBody XwSearchGoods xwSearchGoods){
        xwSearchGoodsService.updateXwSearchGoodsReturn(xwSearchGoods);
        return new ResponseJson();
    }



}
