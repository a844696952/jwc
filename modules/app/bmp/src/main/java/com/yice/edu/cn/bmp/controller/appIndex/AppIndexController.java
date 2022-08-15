package com.yice.edu.cn.bmp.controller.appIndex;

import com.yice.edu.cn.bmp.service.appIndex.AppIndexService;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.appIndex.AppIndex;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.mySchoolId;
import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.myStudentId;


@RestController
@RequestMapping("/appIndex")
@Api(value = "/appIndex",description = "家长端新版首页")
public class AppIndexController {
    @Autowired
    private AppIndexService appIndexService;
    @GetMapping("/getAppIndexes")
    public ResponseJson getAppIndexes(){

        //判断 当前学校账号是否期或者处于升学状态
        ResponseJson responseJson = appIndexService.findSchoolExpireOrSchoolYear(mySchoolId());
        if (!responseJson.getResult().isSuccess()){
            return responseJson;
        }

        List<AppIndex> appIndexList= appIndexService.getAppIndexesForParents(mySchoolId(),myStudentId());
        return new ResponseJson(appIndexList);
    }


}
