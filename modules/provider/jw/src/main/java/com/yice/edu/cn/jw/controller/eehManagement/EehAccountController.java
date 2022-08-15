package com.yice.edu.cn.jw.controller.eehManagement;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.eehManagement.EehAccount;
import com.yice.edu.cn.jw.service.eehManagement.EehAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eehAccount")
@Api(value = "/eehAccount",description = "模块")
public class EehAccountController {
    @Autowired
    private EehAccountService eehAccountService;

    @GetMapping("/findEehAccountById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public EehAccount findEehAccountById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return eehAccountService.findEehAccountById(id);
    }

    @PostMapping("/saveEehAccount")
    @ApiOperation(value = "保存", notes = "返回对象")
    public EehAccount saveEehAccount(
            @ApiParam(value = "对象", required = true)
            @RequestBody EehAccount eehAccount){
        eehAccountService.saveEehAccount(eehAccount);
        return eehAccount;
    }

    @PostMapping("/findEehAccountListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<EehAccount> findEehAccountListByCondition(
            @ApiParam(value = "对象")
            @RequestBody EehAccount eehAccount){
        return eehAccountService.findEehAccountListByCondition(eehAccount);
    }
    @PostMapping("/findEehAccountCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findEehAccountCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody EehAccount eehAccount){
        return eehAccountService.findEehAccountCountByCondition(eehAccount);
    }

    @PostMapping("/updateEehAccount")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateEehAccount(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody EehAccount eehAccount){
        eehAccountService.updateEehAccount(eehAccount);
    }

    @GetMapping("/deleteEehAccount/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteEehAccount(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        eehAccountService.deleteEehAccount(id);
    }
    @PostMapping("/deleteEehAccountByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteEehAccountByCondition(
            @ApiParam(value = "对象")
            @RequestBody EehAccount eehAccount){
        eehAccountService.deleteEehAccountByCondition(eehAccount);
    }
    @PostMapping("/findOneEehAccountByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public EehAccount findOneEehAccountByCondition(
            @ApiParam(value = "对象")
            @RequestBody EehAccount eehAccount){
        return eehAccountService.findOneEehAccountByCondition(eehAccount);
    }

    @GetMapping("/updatePassword/{id}")
    public void updatePassword(@PathVariable String id){
        eehAccountService.updatePassword(id);
    }

    @PostMapping("/EehAccountLogin")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public EehAccount EehAccountLogin(
            @ApiParam(value = "对象")
            @RequestBody EehAccount eehAccount){
        return eehAccountService.EehAccountLogin(eehAccount);
    }

    @PostMapping("/saveEehAccountNew")
    @ApiOperation(value = "保存", notes = "返回对象")
    public EehAccount saveEehAccountNew(
            @ApiParam(value = "对象", required = true)
            @RequestBody EehAccount eehAccount){
        eehAccountService.saveEehAccountNew(eehAccount);
        return eehAccount;
    }

    @PostMapping("/findEehAccountByOldPs")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public EehAccount findEehAccountByOldPs(
            @ApiParam(value = "对象")
            @RequestBody EehAccount eehAccount){
        return eehAccountService.findEehAccountByOldPs(eehAccount);
    }

    @PostMapping("/updateEehAccountStatus")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateEehAccountStatus(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody EehAccount eehAccount){
        eehAccountService.updateEehAccountStatus(eehAccount);
    }

    @PostMapping("/findOneEehAccountByAccountType")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public EehAccount findOneEehAccountByAccountType(
            @ApiParam(value = "对象")
            @RequestBody EehAccount eehAccount){
        return eehAccountService.findOneEehAccountByAccountType(eehAccount);
    }
}
