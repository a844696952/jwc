package com.yice.edu.cn.osp.controller.nation;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.general.nation.Nation;
import com.yice.edu.cn.osp.service.nation.NationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nation")
@Api(value = "/nation",description = "中国 民族信息模块")
public class NationController {
    @Autowired
    private NationService nationService;
    @GetMapping("/findNationById/{id}")
    @ApiOperation(value = "通过id查找中国 民族信息", notes = "返回响应对象")
    public ResponseJson findNationById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        Nation nation=nationService.findNationById(id);
        return new ResponseJson(nation);
    }

    @PostMapping("/saveNation")
    @ApiOperation(value = "保存中国 民族信息对象", notes = "返回响应对象")
    public ResponseJson saveNation(
            @ApiParam(value = "中国 民族信息对象", required = true)
            @RequestBody Nation nation){
        Nation s=nationService.saveNation(nation);
        return new ResponseJson(s);
    }
    @PostMapping("/updateNation")
    @ApiOperation(value = "修改中国 民族信息对象", notes = "返回响应对象")
    public ResponseJson updateNation(
            @ApiParam(value = "被修改的中国 民族信息对象,对象属性不为空则修改", required = true)
            @RequestBody Nation nation){
        nationService.updateNation(nation);
        return new ResponseJson();
    }

    @PostMapping("/findNationsByCondition")
    @ApiOperation(value = "根据条件查找中国 民族信息", notes = "返回响应对象")
    public ResponseJson findNationsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Nation nation){
        List<Nation> data=nationService.findNationListByCondition(nation);
        long count=nationService.findNationCountByCondition(nation);
        return new ResponseJson(data,count);
    }
    @GetMapping("/findNations")
    @ApiOperation(value = "根据条件查找中国 民族信息", notes = "返回响应对象")
    public ResponseJson findNations(){
        List<Nation> data=nationService.findNationListByCondition(new Nation());
        return new ResponseJson(data);
    }
    @GetMapping("/deleteNation/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteNation(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        nationService.deleteNation(id);
        return new ResponseJson();
    }

}
