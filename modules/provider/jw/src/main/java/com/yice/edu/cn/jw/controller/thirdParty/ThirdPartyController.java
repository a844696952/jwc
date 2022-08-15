package com.yice.edu.cn.jw.controller.thirdParty;

import com.yice.edu.cn.common.pojo.api.thirdParty.ThirdParty;
import com.yice.edu.cn.jw.service.thirdParty.ThirdPartyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/thirdParty")
@Api(value = "/thirdParty",description = "第三方账号模块")
public class ThirdPartyController {
    @Autowired
    private ThirdPartyService thirdPartyService;

    @GetMapping("/findThirdPartyById/{id}")
    @ApiOperation(value = "通过id查找第三方账号", notes = "返回第三方账号对象")
    public ThirdParty findThirdPartyById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return thirdPartyService.findThirdPartyById(id);
    }

    @PostMapping("/saveThirdParty")
    @ApiOperation(value = "保存第三方账号", notes = "返回第三方账号对象")
    public ThirdParty saveThirdParty(
            @ApiParam(value = "第三方账号对象", required = true)
            @RequestBody ThirdParty thirdParty){
        thirdPartyService.saveThirdParty(thirdParty);
        return thirdParty;
    }

    @PostMapping("/findThirdPartyListByCondition")
    @ApiOperation(value = "根据条件查找第三方账号列表", notes = "返回第三方账号列表")
    public List<ThirdParty> findThirdPartyListByCondition(
            @ApiParam(value = "第三方账号对象")
            @RequestBody ThirdParty thirdParty){
        return thirdPartyService.findThirdPartyListByCondition(thirdParty);
    }
    @PostMapping("/findThirdPartyCountByCondition")
    @ApiOperation(value = "根据条件查找第三方账号列表个数", notes = "返回第三方账号总个数")
    public long findThirdPartyCountByCondition(
            @ApiParam(value = "第三方账号对象")
            @RequestBody ThirdParty thirdParty){
        return thirdPartyService.findThirdPartyCountByCondition(thirdParty);
    }

    @PostMapping("/updateThirdParty")
    @ApiOperation(value = "修改第三方账号所有非@Transient注释的字段", notes = "第三方账号对象必传")
    public void updateThirdParty(
            @ApiParam(value = "第三方账号对象,对象属性不为空则修改", required = true)
            @RequestBody ThirdParty thirdParty){
        thirdPartyService.updateThirdParty(thirdParty);
    }
    @PostMapping("/updateThirdPartyForNotNull")
    @ApiOperation(value = "修改第三方账号有值的字段", notes = "第三方账号对象必传")
    public void updateThirdPartyForNotNull(
            @ApiParam(value = "第三方账号对象,对象属性不为空则修改", required = true)
            @RequestBody ThirdParty thirdParty){
        thirdPartyService.updateThirdPartyForNotNull(thirdParty);
    }

    @GetMapping("/deleteThirdParty/{id}")
    @ApiOperation(value = "通过id删除第三方账号")
    public void deleteThirdParty(
            @ApiParam(value = "第三方账号对象", required = true)
            @PathVariable String id){
        thirdPartyService.deleteThirdParty(id);
    }
    @PostMapping("/deleteThirdPartyByCondition")
    @ApiOperation(value = "根据条件删除第三方账号")
    public void deleteThirdPartyByCondition(
            @ApiParam(value = "第三方账号对象")
            @RequestBody ThirdParty thirdParty){
        thirdPartyService.deleteThirdPartyByCondition(thirdParty);
    }
    @PostMapping("/findOneThirdPartyByCondition")
    @ApiOperation(value = "根据条件查找单个第三方账号,结果必须为单条数据", notes = "返回单个第三方账号,没有时为空")
    public ThirdParty findOneThirdPartyByCondition(
            @ApiParam(value = "第三方账号对象")
            @RequestBody ThirdParty thirdParty){
        return thirdPartyService.findOneThirdPartyByCondition(thirdParty);
    }
}
