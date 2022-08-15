package com.yice.edu.cn.ecc.controller.active;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.active.DmActive;
import com.yice.edu.cn.ecc.service.active.DmActiveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dmActive")
@Api(value = "/dmActive",description = "模块")
public class DmActiveController {
    @Autowired
    private DmActiveService dmActiveService;

    @GetMapping("/look/lookDmActiveById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookDmActiveById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        if(StringUtils.isBlank(id)){
            return new ResponseJson(false,"活动编号不能为空");
        }else{
            DmActive dmActive=dmActiveService.findDmActiveById(id);
            return new ResponseJson(dmActive);
        }
    }

    @PostMapping("/findDmActivesByConditionVue")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findDmActivesByConditionVue(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmActive dmActive){
        if(StringUtils.isBlank(dmActive.getSchoolId())){
            return new ResponseJson(false,"活动编号不能为空");
        }else{
            try{
                List<DmActive> data=dmActiveService.findDmActiveListByConditionVue(dmActive);
                return new ResponseJson(data);
            }catch (Exception e){
                return new ResponseJson(false,"数据异常");
            }

        }
    }
}
