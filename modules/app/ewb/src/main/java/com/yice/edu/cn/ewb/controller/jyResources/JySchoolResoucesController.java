package com.yice.edu.cn.ewb.controller.jyResources;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.resources.JySchoolResouces;
import com.yice.edu.cn.ewb.service.jyResources.JySchoolResoucesService;
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

import static com.yice.edu.cn.ewb.interceptor.LoginInterceptor.mySchoolId;
import static com.yice.edu.cn.ewb.interceptor.LoginInterceptor.myId;

@RestController
@RequestMapping("/jySchoolResouces")
@Api(value = "/jySchoolResouces", description = "校本教案模块接口")
public class JySchoolResoucesController {
    @Autowired
    private JySchoolResoucesService jySchoolResoucesService;




    @PostMapping("/findJySchoolResoucessByCondition")
    @ApiOperation(value = "分页显示课堂检测校本资源", notes = "返回响应对象")
    public ResponseJson findJySchoolResoucessByCondition(
            @ApiParam(value = "参数是整个对象")
            @Validated
            @RequestBody JySchoolResouces jySchoolResouces) {
        //不分页
        jySchoolResouces.getPager().setPaging(false);
        //校本教案，只需要进行学校的限定
        jySchoolResouces.setSchoolId(mySchoolId());
        List<JySchoolResouces> data = jySchoolResoucesService.findJySchoolResoucesListByCondition(jySchoolResouces);
        long count = jySchoolResoucesService.findJySchoolResoucesCountByCondition(jySchoolResouces);
        return new ResponseJson(data, count);
    }





    @PostMapping("/findJySchoolResoucess")
    @ApiOperation(value = "返回当前讲师的资源列表和文件夹")
    public ResponseJson findJySchoolResoucess(
            @ApiParam(value = "参数为整个JySchoolResouces对象")
            @Validated
            @RequestBody JySchoolResouces jySchoolResouces) {
        //不分页
        jySchoolResouces.getPager().setPaging(false);
        jySchoolResouces.setTeacherId(myId());
        jySchoolResouces.setSchoolId(mySchoolId());
        List<JySchoolResouces> data = jySchoolResoucesService.findJySchoolResoucesList(jySchoolResouces);
        long count = jySchoolResoucesService.findJySchoolResoucesCount(jySchoolResouces);
        return new ResponseJson(data, count);
    }


}
