package com.yice.edu.cn.yed.controller.general.standard;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.general.standard.Standard;
import com.yice.edu.cn.yed.service.general.standard.StandardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/standard")
@Api(value = "/standard",description = "增删改查基本操作")
public class StandardController {
    @Autowired
    private StandardService standardService;
    @GetMapping("/findStandardById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回ResponseJon对象")
    public ResponseJson findStandardById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        Standard standard=standardService.findStandardById(id);
        return new ResponseJson(standard);
    }

    @PostMapping("/saveStandard")
    @ApiOperation(value = "保存对象", notes = "返回ResponseJon对象")
    public ResponseJson saveStandard(
            @ApiParam(value = "对象参数", required = true)
            @RequestBody Standard standard){
        Standard s=standardService.saveStandard(standard);
        return new ResponseJson(s);
    }
    @PostMapping("/updateStandard")
    @ApiOperation(value = "修改对象", notes = "返回ResponseJon对象")
    public ResponseJson updateStandard(
            @ApiParam(value = "被修改的对象,不为空则修改", required = true)
            @RequestBody Standard standard){
        standardService.updateStandard(standard);
        return new ResponseJson();
    }

    @PostMapping("/findStandardsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回ResponseJon对象")
    public ResponseJson findStandardsByCondition(
            @ApiParam(value = "不为空则作为条件查询", required = true)
            @Validated
            @RequestBody Standard standard){
        List<Standard> data=standardService.findStandardListByCondition(standard);
        long count=standardService.findStandardCountByCondition(standard);
        return new ResponseJson(data,count);
    }
    @GetMapping("/deleteStandard/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回ResponseJon对象")
    public ResponseJson deleteStandard(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        standardService.deleteStandard(id);
        return new ResponseJson();
    }

    @PostMapping("/testNonEmpty")
    public ResponseJson testNonEmpty(@RequestBody String[] arr){
        System.out.println(arr.length);
        return new ResponseJson(arr);

    }

}
