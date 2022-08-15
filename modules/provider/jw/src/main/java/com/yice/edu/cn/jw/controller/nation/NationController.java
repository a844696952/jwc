package com.yice.edu.cn.jw.controller.nation;

import com.yice.edu.cn.common.pojo.general.nation.Nation;
import com.yice.edu.cn.jw.service.nation.NationService;
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
    @ApiOperation(value = "通过id查找中国 民族信息", notes = "返回中国 民族信息对象")
    public Nation findNationById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return nationService.findNationById(id);
    }

    @PostMapping("/saveNation")
    @ApiOperation(value = "保存中国 民族信息", notes = "返回中国 民族信息对象")
    public Nation saveNation(
            @ApiParam(value = "中国 民族信息对象", required = true)
            @RequestBody Nation nation){
        nationService.saveNation(nation);
        return nation;
    }

    @PostMapping("/findNationListByCondition")
    @ApiOperation(value = "根据条件查找中国 民族信息列表", notes = "返回中国 民族信息列表")
    public List<Nation> findNationListByCondition(
            @ApiParam(value = "中国 民族信息对象")
            @RequestBody Nation nation){
        return nationService.findNationListByCondition(nation);
    }
    @PostMapping("/findNationCountByCondition")
    @ApiOperation(value = "根据条件查找中国 民族信息列表个数", notes = "返回中国 民族信息总个数")
    public long findNationCountByCondition(
            @ApiParam(value = "中国 民族信息对象")
            @RequestBody Nation nation){
        return nationService.findNationCountByCondition(nation);
    }

    @PostMapping("/updateNation")
    @ApiOperation(value = "修改中国 民族信息", notes = "中国 民族信息对象必传")
    public void updateNation(
            @ApiParam(value = "中国 民族信息对象,对象属性不为空则修改", required = true)
            @RequestBody Nation nation){
        nationService.updateNation(nation);
    }

    @GetMapping("/deleteNation/{id}")
    @ApiOperation(value = "通过id删除中国 民族信息")
    public void deleteNation(
            @ApiParam(value = "中国 民族信息对象", required = true)
            @PathVariable String id){
        nationService.deleteNation(id);
    }
}
