package com.yice.edu.cn.ecc.controller.xwSearch;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.searchOwner.XwSearchOwner;
import com.yice.edu.cn.ecc.service.xwSearch.XwSearchOwnerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.ecc.interceptor.LoginInterceptor.mySchoolId;


@RestController
@RequestMapping("/xwSearchOwner")
@Api(value = "/xwSearchOwner",description = "寻找失主表模块")
public class XwSearchOwnerController {
    @Autowired
    private XwSearchOwnerService xwSearchOwnerService;

    @GetMapping("/findXwSearchOwnerById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找寻找失主表", notes = "返回响应对象")
    public ResponseJson findXwSearchOwnerById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        XwSearchOwner xwSearchOwner=xwSearchOwnerService.findXwSearchOwnerById(id);
        return new ResponseJson(xwSearchOwner);
    }




    @GetMapping("/findXwSearchOwnerListByCondition")
    @ApiOperation(value = "根据条件查找寻找失主表列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findXwSearchOwnerListByCondition(){
        XwSearchOwner xwSearchOwner = new XwSearchOwner();
        xwSearchOwner.setSchoolId(mySchoolId());
        Pager pager = new Pager();
        pager.setSortField("create_time");
        pager.setSortOrder(Pager.DESC);
        xwSearchOwner.setPager(pager);
        //班牌只需要展示未认领的物品
        xwSearchOwner.setStatus("1");
        List<XwSearchOwner> data=xwSearchOwnerService.findXwSearchOwnerListByCondition(xwSearchOwner);
        return new ResponseJson(data,"寻人");
    }

    @PostMapping("/saveXwSearchOwner")
    @ApiOperation(value = "保存寻找失主表对象", notes = "返回响应对象")
    public ResponseJson saveXwSearchOwner(
            @ApiParam(value = "寻找失主表对象", required = true)
            @RequestBody XwSearchOwner xwSearchOwner){
        xwSearchOwner.setSchoolId(mySchoolId());
        //xwSearchOwner.setStudentId(myStudentId());
        XwSearchOwner s=xwSearchOwnerService.saveXwSearchOwner(xwSearchOwner);
        return new ResponseJson(s);
    }



}
