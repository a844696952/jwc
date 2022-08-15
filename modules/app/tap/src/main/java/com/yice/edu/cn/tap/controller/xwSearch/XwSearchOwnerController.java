package com.yice.edu.cn.tap.controller.xwSearch;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.searchOwner.XwSearchOwner;
import com.yice.edu.cn.tap.service.xwSearch.XwSearchOwnerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

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




    @PostMapping("/findXwSearchOwnerListByCondition")
    @ApiOperation(value = "根据条件查找寻找失主表列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findXwSearchOwnerListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwSearchOwner xwSearchOwner){
       xwSearchOwner.setSchoolId(mySchoolId());
        List<XwSearchOwner> data=xwSearchOwnerService.findXwSearchOwnerListByCondition(xwSearchOwner);
        return new ResponseJson(data);
    }



}
