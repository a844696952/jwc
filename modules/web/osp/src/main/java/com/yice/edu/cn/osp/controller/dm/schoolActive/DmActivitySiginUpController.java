package com.yice.edu.cn.osp.controller.dm.schoolActive;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.schoolActive.DmActivitySiginUp;
import com.yice.edu.cn.osp.service.dm.schoolActive.DmActivitySiginUpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dmActivitySiginUp")
@Api(value = "/dmActivitySiginUp",description = "活动报名表模块")
public class DmActivitySiginUpController {
    @Autowired
    private DmActivitySiginUpService dmActivitySiginUpService;

    @PostMapping("/saveDmActivitySiginUp")
    @ApiOperation(value = "保存活动报名表对象", notes = "返回保存好的活动报名表对象", response= DmActivitySiginUp.class)
    public ResponseJson saveDmActivitySiginUp(
            @ApiParam(value = "活动报名表对象", required = true)
            @RequestBody DmActivitySiginUp dmActivitySiginUp){
       dmActivitySiginUp.setSchoolId(mySchoolId());
        DmActivitySiginUp s=dmActivitySiginUpService.saveDmActivitySiginUp(dmActivitySiginUp);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findDmActivitySiginUpById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找活动报名表", notes = "返回响应对象", response=DmActivitySiginUp.class)
    public ResponseJson findDmActivitySiginUpById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DmActivitySiginUp dmActivitySiginUp=dmActivitySiginUpService.findDmActivitySiginUpById(id);
        return new ResponseJson(dmActivitySiginUp);
    }

    @PostMapping("/update/updateDmActivitySiginUp")
    @ApiOperation(value = "修改活动报名表对象", notes = "返回响应对象")
    public ResponseJson updateDmActivitySiginUp(
            @ApiParam(value = "被修改的活动报名表对象,对象属性不为空则修改", required = true)
            @RequestBody DmActivitySiginUp dmActivitySiginUp){
        dmActivitySiginUpService.updateDmActivitySiginUp(dmActivitySiginUp);
        return new ResponseJson();
    }

    @GetMapping("/look/lookDmActivitySiginUpById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找活动报名表", notes = "返回响应对象", response=DmActivitySiginUp.class)
    public ResponseJson lookDmActivitySiginUpById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        DmActivitySiginUp dmActivitySiginUp=dmActivitySiginUpService.findDmActivitySiginUpById(id);
        return new ResponseJson(dmActivitySiginUp);
    }

    @PostMapping("/findDmActivitySiginUpsByCondition")
    @ApiOperation(value = "根据条件查找活动报名表", notes = "返回响应对象", response=DmActivitySiginUp.class)
    public ResponseJson findDmActivitySiginUpsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmActivitySiginUp dmActivitySiginUp){
       dmActivitySiginUp.setSchoolId(mySchoolId());
        List<DmActivitySiginUp> data=dmActivitySiginUpService.findDmActivitySiginUpListByCondition(dmActivitySiginUp);
        long count=dmActivitySiginUpService.findDmActivitySiginUpCountByCondition(dmActivitySiginUp);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDmActivitySiginUpByCondition")
    @ApiOperation(value = "根据条件查找单个活动报名表,结果必须为单条数据", notes = "没有时返回空", response=DmActivitySiginUp.class)
    public ResponseJson findOneDmActivitySiginUpByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmActivitySiginUp dmActivitySiginUp){
        DmActivitySiginUp one=dmActivitySiginUpService.findOneDmActivitySiginUpByCondition(dmActivitySiginUp);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteDmActivitySiginUp/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDmActivitySiginUp(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        dmActivitySiginUpService.deleteDmActivitySiginUp(id);
        return new ResponseJson();
    }


    @PostMapping("/findDmActivitySiginUpListByCondition")
    @ApiOperation(value = "根据条件查找活动报名表列表", notes = "返回响应对象,不包含总条数", response=DmActivitySiginUp.class)
    public ResponseJson findDmActivitySiginUpListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmActivitySiginUp dmActivitySiginUp){
       dmActivitySiginUp.setSchoolId(mySchoolId());
        List<DmActivitySiginUp> data=dmActivitySiginUpService.findDmActivitySiginUpListByCondition(dmActivitySiginUp);
        return new ResponseJson(data);
    }



}
