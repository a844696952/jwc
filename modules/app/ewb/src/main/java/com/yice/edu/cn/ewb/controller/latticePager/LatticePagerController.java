package com.yice.edu.cn.ewb.controller.latticePager;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.wb.latticePager.DmPagerBackground;
import com.yice.edu.cn.common.pojo.wb.latticePager.LatticePager;
import com.yice.edu.cn.ewb.interceptor.LoginInterceptor;
import com.yice.edu.cn.ewb.service.latticePager.LatticePagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/latticePager")
@Api(value = "/latticePager",description = "点阵试卷模块")
public class LatticePagerController {



    @Autowired
    private LatticePagerService latticePagerService;

    @PostMapping("/findLatticePagerReference")
    @ApiOperation(value = "去查看页面,通过页码去转查找点阵试卷管理", notes = "返回响应对象", response= ResponseJson.class)
    public ResponseJson findLatticePagerReference(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @RequestBody DmPagerBackground dmPagerBackground){
        dmPagerBackground.setSchoolId(LoginInterceptor.mySchoolId());
        return latticePagerService.findLatticePagerReference(dmPagerBackground);
    }








}
