package com.yice.edu.cn.ecc.controller.xwSearch;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.searchGoods.XwSearchGoods;
import com.yice.edu.cn.common.pojo.xw.searchOwner.XwSearchOwner;
import com.yice.edu.cn.ecc.service.xwSearch.XwSearchGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.ecc.interceptor.LoginInterceptor.mySchoolId;

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




    @GetMapping("/findXwSearchGoodsListByCondition")
    @ApiOperation(value = "根据条件查找寻找物品表列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findXwSearchGoodsListByCondition(){
        XwSearchGoods xwSearchGoods = new XwSearchGoods();
        xwSearchGoods.setSchoolId(mySchoolId());
        Pager pager = new Pager();
        pager.setSortField("create_time");
        pager.setSortOrder(Pager.DESC);
        xwSearchGoods.setPager(pager);
        xwSearchGoods.setSchoolId(mySchoolId());
        //已经归还的不展示
        xwSearchGoods.setStatus2("3");
        List<XwSearchGoods> data=xwSearchGoodsService.findXwSearchGoodsListByCondition(xwSearchGoods);
        return new ResponseJson(data,"寻物");
    }

    @PostMapping("/saveXwSearchGoods")
    @ApiOperation(value = "保存寻找物品表对象", notes = "返回响应对象")
    public ResponseJson saveXwSearchGoods(
            @ApiParam(value = "寻找物品表对象", required = true)
            @RequestBody XwSearchGoods xwSearchGoods){
        xwSearchGoods.setSchoolId(mySchoolId());
        //xwSearchGoods.setStudentId(myStudentId());
        XwSearchGoods s=xwSearchGoodsService.saveXwSearchGoods(xwSearchGoods);
        return new ResponseJson(s);
    }

    }



