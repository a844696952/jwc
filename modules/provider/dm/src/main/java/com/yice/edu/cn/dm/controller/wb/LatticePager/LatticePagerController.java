package com.yice.edu.cn.dm.controller.wb.LatticePager;



import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.wb.latticePager.DmPagerBackground;
import com.yice.edu.cn.common.pojo.wb.latticePager.LatticePager;
import com.yice.edu.cn.dm.service.wb.LatticePager.LatticePagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/latticePager")
@Api(value = "/latticePager",description = "点阵试卷表模块")
public class LatticePagerController {

    @Autowired
    private LatticePagerService latticePagerService;

    @PostMapping("/findLatticePagerListByCondition")
    @ApiOperation(value = "根据条件查找点阵试卷表列表", notes = "返回点阵试卷表列表")
    public List<LatticePager> findLatticePagerListByCondition(
            @ApiParam(value = "点阵试卷表对象")
            @RequestBody LatticePager latticePager){
        return latticePagerService.findLatticePagerListByCondition(latticePager);
    }



    @PostMapping("/findLatticePagerCountByCondition")
    @ApiOperation(value = "根据条件查找点阵试卷表个数", notes = "返回点阵试卷表总个数")
    public long findLatticePagerCountByCondition(
            @ApiParam(value = "点阵试卷表对象")
            @RequestBody LatticePager latticePager){
        return latticePagerService.findLatticePagerCountByCondition(latticePager);
    }

    @PostMapping("/saveOrUpdateLatticePager")
    @ApiOperation(value = "保存点阵试卷表表", notes = "返回点阵试卷表对象")
    public LatticePager saveOrUpdateLatticePager(
            @ApiParam(value = "点阵试卷表对象", required = true)
            @RequestBody LatticePager latticePager){
        latticePagerService.saveOrUpdateLatticePager(latticePager);
        return latticePager;
    }


    @GetMapping("/deleteLatticePager/{id}")
    @ApiOperation(value = "通过id删除点阵试卷表")
    public void deleteLatticePager(
            @ApiParam(value = "点阵试卷表对象", required = true)
            @PathVariable String id){
        latticePagerService.deleteLatticePager(id);
    }


    @GetMapping("/findLatticePagerById/{id}")
    @ApiOperation(value = "通过id查找点阵试卷表", notes = "返回点阵试卷表对象")
    public LatticePager findLatticePagerById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
       return latticePagerService.findLatticePagerById(id);
    }


    @PostMapping("/findLatticePagerReference")
    @ApiOperation(value = "通过id查找点阵试卷表所有东西", notes = "返回点阵试卷表对象")
    public ResponseJson findLatticePagerReference(
            @ApiParam(value = "需要用到的id", required = true)
            @RequestBody DmPagerBackground pagerBackground){
        return latticePagerService.findLatticePagerReference(pagerBackground);
    }
}
