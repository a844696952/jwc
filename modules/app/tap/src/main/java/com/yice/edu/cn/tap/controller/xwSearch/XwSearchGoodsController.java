package com.yice.edu.cn.tap.controller.xwSearch;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.searchGoods.XwSearchGoods;
import com.yice.edu.cn.tap.service.xwSearch.XwSearchGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/xwSearchGoods")
@Api(value = "/xwSearchGoods",description = "寻找物品表模块")
public class XwSearchGoodsController {
    @Autowired
    private XwSearchGoodsService xwSearchGoodsService;




    @GetMapping("/findXwSearchGoodsById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找寻找物品表", notes = "返回响应对象")
    public ResponseJson findXwSearchGoodsById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        XwSearchGoods xwSearchGoods=xwSearchGoodsService.findXwSearchGoodsById(id);
        return new ResponseJson(xwSearchGoods);
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



}
