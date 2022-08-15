package com.yice.edu.cn.ewb.controller.jyResources;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.resources.JyResouces;
import com.yice.edu.cn.ewb.service.jyResources.JyResoucesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.yice.edu.cn.ewb.interceptor.LoginInterceptor.myId;

@RestController
@RequestMapping("/jyResouces")
@Api(value = "/jyResouces", description = "创建时间：2018-10-29。说明：用于存放我的资源模块")
public class JyResoucesController {
    @Autowired
    private JyResoucesService jyResoucesService;



    @PostMapping("/findJyResoucessByCondition")
    @ApiOperation(value = "根据条件查找课堂检测我的资源", notes = "返回响应对象")
    public ResponseJson findJyResoucessByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody JyResouces jyResouces) {
        //不分页
        jyResouces.getPager().setPaging(false);
        jyResouces.setTeacherId(myId());
        List<JyResouces> data = jyResoucesService.findJyResoucesListByCondition(jyResouces);
        long count = jyResoucesService.findJyResoucesCountByCondition(jyResouces);
        return new ResponseJson(data, count);
    }



}
