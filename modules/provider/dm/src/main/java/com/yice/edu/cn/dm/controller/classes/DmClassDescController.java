package com.yice.edu.cn.dm.controller.classes;

import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import com.yice.edu.cn.common.pojo.dm.classes.DmClassDesc;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.dm.service.classes.DmClassDescService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dmClassDesc")
@Api(value = "/dmClassDesc",description = "班级简介模块")
public class DmClassDescController {
    @Autowired
    private DmClassDescService dmClassDescService;

    @GetMapping("/findDmClassDescById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public DmClassDesc findDmClassDescById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        DmClassDesc dc =  dmClassDescService.findDmClassDescById(id);
        if(dc!=null && dc.getClassAlias() == null){
            dc.setClassAlias("");
        }
        return dc;
    }

    @PostMapping("/saveDmClassDesc")
    @ApiOperation(value = "保存", notes = "返回对象")
    public DmClassDesc saveDmClassDesc(
            @ApiParam(value = "对象", required = true)
            @RequestBody DmClassDesc dmClassDesc){

        dmClassDescService.saveDmClassDesc(dmClassDesc);
        return dmClassDesc;
    }

    @PostMapping("/findDmClassDescListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<DmClassDesc> findDmClassDescListByCondition(
            @ApiParam(value = "对象")
            @RequestBody DmClassDesc dmClassDesc){
        return dmClassDescService.findDmClassDescListByCondition(dmClassDesc);
    }
    @PostMapping("/findDmClassDescCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findDmClassDescCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody DmClassDesc dmClassDesc){
        return dmClassDescService.findDmClassDescCountByCondition(dmClassDesc);
    }

    @PostMapping("/updateDmClassDesc")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateDmClassDesc(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody DmClassDesc dmClassDesc){
        dmClassDescService.updateDmClassDesc(dmClassDesc);
    }

    @GetMapping("/deleteDmClassDesc/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteDmClassDesc(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        dmClassDescService.deleteDmClassDesc(id);
    }
    @PostMapping("/deleteDmClassDescByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteDmClassDescByCondition(
            @ApiParam(value = "对象")
            @RequestBody DmClassDesc dmClassDesc){
        dmClassDescService.deleteDmClassDescByCondition(dmClassDesc);
    }
    @PostMapping("/findOneDmClassDescByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public DmClassDesc findOneDmClassDescByCondition(
            @ApiParam(value = "对象")
            @RequestBody DmClassDesc dmClassDesc){
        DmClassDesc dd = dmClassDescService.findOneDmClassDescByCondition(dmClassDesc);
        if(dd != null){
            if(dd.getClassAlias() == null){
                dd.setClassAlias("");
            }
        }
        return dd;
    }

    @GetMapping("/clearClassDescAndPhoto/{classId}")
    @ApiOperation(value = "清除班级简介以及班级相册", notes = "更新班级简介为空并清除班级下面的相册")
    public void clearClassDescAndPhoto(
            @ApiParam(value = "班级id")
            @PathVariable String classId){
        dmClassDescService.clearClassDescAndPhoto(classId);
    }

    @PostMapping("/batchClearClassDescAndPhoto")
    @ApiOperation(value = "批量清除班级简介以及班级相册", notes = "更新班级简介为空并清除班级下面的相册")
    public void batchClearClassDescAndPhoto(
            @ApiParam(value = "班级id字符串集合")
            @RequestBody String classIds){
        dmClassDescService.batchClearClassDescAndPhoto(classIds);
    }

    @PostMapping("/findJwClassesListByCardCondition")
    public List<JwClasses> findJwClassesListByCardCondition(@ApiParam(value = "班级信息对象") @RequestBody JwClasses jwClasses){
        return dmClassDescService.findJwClassesListByCardCondition(jwClasses);
    }

    @PostMapping("/findJwClassesCountByCardCondition")
    public long findJwClassesCountByCardCondition(@ApiParam(value = "班级信息对象") @RequestBody JwClasses jwClasses){
        return dmClassDescService.findJwClassesCountByCardCondition(jwClasses);
    }

    @PostMapping("/findDmClassesCountByCardCondition")
    public long findDmClassesCountByCardCondition(@ApiParam(value = "班级信息对象") @RequestBody JwClasses jwClasses){
        return dmClassDescService.findDmClassesCountByCardCondition(jwClasses);
    }

    /**
     *  查询班级信息（从云班牌获取）
     * @param jwClasses
     * @return
     */
    @PostMapping("/findDmClassesListByCardCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<JwClasses> findDmClassesListByCardCondition(
            @ApiParam(value = "对象")
            @RequestBody JwClasses jwClasses){
        return dmClassDescService.findDmClassesListByCardCondition(jwClasses);
    }
}
