package com.yice.edu.cn.xw.controller.clCustomMaterial;

import com.yice.edu.cn.common.pojo.xw.clCustomMaterial.ClCustomMaterial;
import com.yice.edu.cn.xw.service.clCustomMaterial.ClCustomMaterialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clCustomMaterial")
@Api(value = "/clCustomMaterial",description = "自定义材料表模块")
public class ClCustomMaterialController {
    @Autowired
    private ClCustomMaterialService clCustomMaterialService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findClCustomMaterialById/{id}")
    @ApiOperation(value = "通过id查找自定义材料表", notes = "返回自定义材料表对象")
    public ClCustomMaterial findClCustomMaterialById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return clCustomMaterialService.findClCustomMaterialById(id);
    }

    @PostMapping("/saveClCustomMaterial")
    @ApiOperation(value = "保存自定义材料表", notes = "返回自定义材料表对象")
    public ClCustomMaterial saveClCustomMaterial(
            @ApiParam(value = "自定义材料表对象", required = true)
            @RequestBody ClCustomMaterial clCustomMaterial){
        clCustomMaterialService.saveClCustomMaterial(clCustomMaterial);
        return clCustomMaterial;
    }

    @PostMapping("/batchSaveClCustomMaterial")
    @ApiOperation(value = "批量保存自定义材料表")
    public void batchSaveClCustomMaterial(
            @ApiParam(value = "自定义材料表对象集合", required = true)
            @RequestBody List<ClCustomMaterial> clCustomMaterials){
        clCustomMaterialService.batchSaveClCustomMaterial(clCustomMaterials);
    }

    @PostMapping("/findClCustomMaterialListByCondition")
    @ApiOperation(value = "根据条件查找自定义材料表列表", notes = "返回自定义材料表列表")
    public List<ClCustomMaterial> findClCustomMaterialListByCondition(
            @ApiParam(value = "自定义材料表对象")
            @RequestBody ClCustomMaterial clCustomMaterial){
        return clCustomMaterialService.findClCustomMaterialListByCondition(clCustomMaterial);
    }
    @PostMapping("/findClCustomMaterialCountByCondition")
    @ApiOperation(value = "根据条件查找自定义材料表列表个数", notes = "返回自定义材料表总个数")
    public long findClCustomMaterialCountByCondition(
            @ApiParam(value = "自定义材料表对象")
            @RequestBody ClCustomMaterial clCustomMaterial){
        return clCustomMaterialService.findClCustomMaterialCountByCondition(clCustomMaterial);
    }

    @PostMapping("/updateClCustomMaterial")
    @ApiOperation(value = "修改自定义材料表有值的字段", notes = "自定义材料表对象必传")
    public void updateClCustomMaterial(
            @ApiParam(value = "自定义材料表对象,对象属性不为空则修改", required = true)
            @RequestBody ClCustomMaterial clCustomMaterial){
        clCustomMaterialService.updateClCustomMaterial(clCustomMaterial);
    }
    @PostMapping("/updateClCustomMaterialForAll")
    @ApiOperation(value = "修改自定义材料表所有字段", notes = "自定义材料表对象必传")
    public void updateClCustomMaterialForAll(
            @ApiParam(value = "自定义材料表对象", required = true)
            @RequestBody ClCustomMaterial clCustomMaterial){
        clCustomMaterialService.updateClCustomMaterialForAll(clCustomMaterial);
    }

    @GetMapping("/deleteClCustomMaterial/{id}")
    @ApiOperation(value = "通过id删除自定义材料表")
    public void deleteClCustomMaterial(
            @ApiParam(value = "自定义材料表对象", required = true)
            @PathVariable String id){
        clCustomMaterialService.deleteClCustomMaterial(id);
    }
    @PostMapping("/deleteClCustomMaterialByCondition")
    @ApiOperation(value = "根据条件删除自定义材料表")
    public void deleteClCustomMaterialByCondition(
            @ApiParam(value = "自定义材料表对象")
            @RequestBody ClCustomMaterial clCustomMaterial){
        clCustomMaterialService.deleteClCustomMaterialByCondition(clCustomMaterial);
    }
    @PostMapping("/findOneClCustomMaterialByCondition")
    @ApiOperation(value = "根据条件查找单个自定义材料表,结果必须为单条数据", notes = "返回单个自定义材料表,没有时为空")
    public ClCustomMaterial findOneClCustomMaterialByCondition(
            @ApiParam(value = "自定义材料表对象")
            @RequestBody ClCustomMaterial clCustomMaterial){
        return clCustomMaterialService.findOneClCustomMaterialByCondition(clCustomMaterial);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @PostMapping("/findClCustomMaterialMaxWeight")
    public Long findClCustomMaterialMaxWeight(@RequestBody ClCustomMaterial clCustomMaterial){
        return clCustomMaterialService.findClCustomMaterialMaxWeight(clCustomMaterial);
    }
    @PostMapping("/findClCustomMaterialListByConditionKong")
    public List<ClCustomMaterial> findClCustomMaterialListByConditionKong(@RequestBody ClCustomMaterial clCustomMaterial){
        return  clCustomMaterialService.findClCustomMaterialListByConditionKong(clCustomMaterial);
    }

    @PostMapping("/saveClCustomMaterialDataAndClWeight")
    public void saveClCustomMaterialDataAndClWeight(@RequestBody ClCustomMaterial clCustomMaterial){
        clCustomMaterialService.saveClCustomMaterialDataAndClWeight(clCustomMaterial);
    }

}
