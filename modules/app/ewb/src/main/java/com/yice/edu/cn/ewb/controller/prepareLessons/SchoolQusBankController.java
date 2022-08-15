package com.yice.edu.cn.ewb.controller.prepareLessons;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.PersonalTopics;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.ResourceVo;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.SchoolQusBank;
import com.yice.edu.cn.common.util.StringUtils;
import com.yice.edu.cn.ewb.interceptor.LoginInterceptor;
import com.yice.edu.cn.ewb.service.prepareLessons.SchoolQusBankService;
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

@RestController
@RequestMapping("/schoolQusBank")
@Api(value = "/schoolQusBank", description = "模块")
public class SchoolQusBankController {

    @Autowired
    private SchoolQusBankService schoolQusBankService;


    @PostMapping("/findSchoolQusBanksByCondition")
    @ApiOperation(value = "根据条件查找校本题库", notes = "返回响应对象")
    public ResponseJson findSchoolQusBanksByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody SchoolQusBank schoolQusBank) {
        schoolQusBank.setContentText(StringUtils.specialCharacterConvert(schoolQusBank.getContentText()));
        schoolQusBank.getPager().setLike("contentText");
        schoolQusBank.getPager().setSortField("createTime");
        schoolQusBank.getPager().setSortOrder(Pager.DESC);
        schoolQusBank.setSchoolId(LoginInterceptor.mySchoolId());
        List<SchoolQusBank> data = schoolQusBankService.findSchoolQusBankListByCondition(schoolQusBank);
        long count = schoolQusBankService.findSchoolQusBankCountByCondition(schoolQusBank);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findTopicDetail")
    @ApiOperation(value = "多平台查询题目详情", notes = "返回响应对象,含总条数",response = PersonalTopics.class)
    public ResponseJson findTopicDetail(@RequestBody ResourceVo resourceVo){
        return new ResponseJson(schoolQusBankService.findTopicDetail(resourceVo));
    }
}
