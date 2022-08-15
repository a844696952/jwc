package com.yice.edu.cn.osp.controller.xw.dj.information;


import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.dj.information.XwDjInformation;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import com.yice.edu.cn.osp.service.xw.dj.information.XwDjInformationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/xwDjInformation")
@Api(value = "/xwDjInformation",description = "党建资讯模块")
public class XwDjInformationController {
    @Autowired
    private XwDjInformationService xwDjInformationService;

    @PostMapping("/saveXwDjInformation")
    @ApiOperation(value = "保存党建资讯对象", notes = "返回保存好的党建资讯对象", response= XwDjInformation.class)
    public ResponseJson saveXwDjInformation(
            @ApiParam(value = "党建资讯对象", required = true)
            @RequestBody XwDjInformation xwDjInformation){
        xwDjInformation.setCreatorId(LoginInterceptor.myId());
        xwDjInformation.setSchoolId(mySchoolId());
        XwDjInformation s=xwDjInformationService.saveXwDjInformation(xwDjInformation);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findXwDjInformationById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找党建资讯", notes = "返回响应对象", response=XwDjInformation.class)
    public ResponseJson findXwDjInformationById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        XwDjInformation xwDjInformation=xwDjInformationService.findXwDjInformationById(id);
        return new ResponseJson(xwDjInformation);
    }

    @PostMapping("/update/updateXwDjInformation")
    @ApiOperation(value = "修改党建资讯对象", notes = "返回响应对象")
    public ResponseJson updateXwDjInformation(
            @ApiParam(value = "被修改的党建资讯对象,对象属性不为空则修改", required = true)
            @RequestBody XwDjInformation xwDjInformation){
        xwDjInformation.setOperatorId(LoginInterceptor.myId());
        xwDjInformation.setSchoolId(mySchoolId());
        xwDjInformationService.updateXwDjInformation(xwDjInformation);
        return new ResponseJson();
    }

    @GetMapping("/look/lookXwDjInformationById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找党建资讯", notes = "返回响应对象", response=XwDjInformation.class)
    public ResponseJson lookXwDjInformationById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        XwDjInformation xwDjInformation=xwDjInformationService.findXwDjInformationById(id);
        return new ResponseJson(xwDjInformation);
    }

    @PostMapping("/findXwDjInformationsByCondition")
    @ApiOperation(value = "根据条件查找党建资讯", notes = "返回响应对象", response=XwDjInformation.class)
    public ResponseJson findXwDjInformationsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwDjInformation xwDjInformation){
        xwDjInformation.setCreatorId(Constant.IS_ADMIN.IS_ADMIN.equals(xwDjInformation.getIsAdmin()) ? null : myId());
        List<XwDjInformation> data=xwDjInformationService.findXwDjInformationListByCondition(xwDjInformation);
        long count=xwDjInformationService.findXwDjInformationCountByCondition(xwDjInformation);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneXwDjInformationByCondition")
    @ApiOperation(value = "根据条件查找单个党建资讯,结果必须为单条数据", notes = "没有时返回空", response=XwDjInformation.class)
    public ResponseJson findOneXwDjInformationByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody XwDjInformation xwDjInformation){
        XwDjInformation one=xwDjInformationService.findOneXwDjInformationByCondition(xwDjInformation);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteXwDjInformation/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteXwDjInformation(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        xwDjInformationService.deleteXwDjInformation(id);
        return new ResponseJson();
    }


    @PostMapping("/findXwDjInformationListByCondition")
    @ApiOperation(value = "根据条件查找党建资讯列表", notes = "返回响应对象,不包含总条数", response=XwDjInformation.class)
    public ResponseJson findXwDjInformationListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwDjInformation xwDjInformation){
        List<XwDjInformation> data=xwDjInformationService.findXwDjInformationListByCondition(xwDjInformation);
        return new ResponseJson(data);
    }

    @GetMapping("/selectXwDjInformationById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找党建资讯(包括附件)", notes = "返回响应对象", response=XwDjInformation.class)
    public ResponseJson selectXwDjInformationById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        XwDjInformation xwDjInformation=xwDjInformationService.selectXwDjInformationById(id);
        return new ResponseJson(xwDjInformation);
    }

    @PostMapping("/update/editXwDjInformation")
    @ApiOperation(value = "编辑党建资讯对象", notes = "返回响应对象")
    public ResponseJson editXwDjInformation(
            @ApiParam(value = "被修改的党建资讯对象,对象属性不为空则修改", required = true)
            @RequestBody XwDjInformation xwDjInformation) {
        xwDjInformation.setOperatorId(LoginInterceptor.myId());
        xwDjInformation.setSchoolId(mySchoolId());
        xwDjInformationService.editXwDjInformation(xwDjInformation);
        return new ResponseJson();
    }

}
