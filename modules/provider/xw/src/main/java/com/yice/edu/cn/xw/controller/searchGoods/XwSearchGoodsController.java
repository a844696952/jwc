package com.yice.edu.cn.xw.controller.searchGoods;

import com.yice.edu.cn.common.pojo.xw.searchGoods.XwSearchGoods;
import com.yice.edu.cn.xw.service.searchGoods.XwSearchGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/xwSearchGoods")
@Api(value = "/xwSearchGoods",description = "寻找物品表模块")
public class XwSearchGoodsController {
    @Autowired
    private XwSearchGoodsService xwSearchGoodsService;

    @GetMapping("/findXwSearchGoodsById/{id}")
    @ApiOperation(value = "通过id查找寻找物品表", notes = "返回寻找物品表对象")
    public XwSearchGoods findXwSearchGoodsById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return xwSearchGoodsService.findXwSearchGoodsById(id);
    }

    @PostMapping("/saveXwSearchGoods")
    @ApiOperation(value = "保存寻找物品表", notes = "返回寻找物品表对象")
    public XwSearchGoods saveXwSearchGoods(
            @ApiParam(value = "寻找物品表对象", required = true)
            @RequestBody XwSearchGoods xwSearchGoods){
        xwSearchGoodsService.saveXwSearchGoods(xwSearchGoods);
        return xwSearchGoods;
    }

    @PostMapping("/findXwSearchGoodsListByCondition")
    @ApiOperation(value = "根据条件查找寻找物品表列表", notes = "返回寻找物品表列表")
    public List<XwSearchGoods> findXwSearchGoodsListByCondition(
            @ApiParam(value = "寻找物品表对象")
            @RequestBody XwSearchGoods xwSearchGoods){
        return xwSearchGoodsService.findXwSearchGoodsListByCondition(xwSearchGoods);
    }
    @PostMapping("/findXwSearchGoodsCountByCondition")
    @ApiOperation(value = "根据条件查找寻找物品表列表个数", notes = "返回寻找物品表总个数")
    public long findXwSearchGoodsCountByCondition(
            @ApiParam(value = "寻找物品表对象")
            @RequestBody XwSearchGoods xwSearchGoods){
        return xwSearchGoodsService.findXwSearchGoodsCountByCondition(xwSearchGoods);
    }

    @PostMapping("/updateXwSearchGoods")
    @ApiOperation(value = "修改寻找物品表", notes = "寻找物品表对象必传")
    public void updateXwSearchGoods(
            @ApiParam(value = "寻找物品表对象,对象属性不为空则修改", required = true)
            @RequestBody XwSearchGoods xwSearchGoods){
        xwSearchGoodsService.updateXwSearchGoods(xwSearchGoods);
    }

    @GetMapping("/deleteXwSearchGoods/{id}")
    @ApiOperation(value = "通过id删除寻找物品表")
    public void deleteXwSearchGoods(
            @ApiParam(value = "寻找物品表对象", required = true)
            @PathVariable String id){
        xwSearchGoodsService.deleteXwSearchGoods(id);
    }
    @PostMapping("/deleteXwSearchGoodsByCondition")
    @ApiOperation(value = "根据条件删除寻找物品表")
    public void deleteXwSearchGoodsByCondition(
            @ApiParam(value = "寻找物品表对象")
            @RequestBody XwSearchGoods xwSearchGoods){
        xwSearchGoodsService.deleteXwSearchGoodsByCondition(xwSearchGoods);
    }
    @PostMapping("/findOneXwSearchGoodsByCondition")
    @ApiOperation(value = "根据条件查找单个寻找物品表,结果必须为单条数据", notes = "返回单个寻找物品表,没有时为空")
    public XwSearchGoods findOneXwSearchGoodsByCondition(
            @ApiParam(value = "寻找物品表对象")
            @RequestBody XwSearchGoods xwSearchGoods){
        return xwSearchGoodsService.findOneXwSearchGoodsByCondition(xwSearchGoods);
    }

    @PostMapping("/updateXwSearchGoodsReturn")
    @ApiOperation(value = "修改寻找物品表(归还)", notes = "寻找物品表对象必传")
    public void updateXwSearchGoodsReturn(
            @ApiParam(value = "寻找物品表对象,对象属性不为空则修改", required = true)
            @RequestBody XwSearchGoods xwSearchGoods){
        xwSearchGoodsService.updateXwSearchGoodsReturn(xwSearchGoods);
    }
}
