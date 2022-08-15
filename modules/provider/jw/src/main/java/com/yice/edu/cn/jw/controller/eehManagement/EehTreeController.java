package com.yice.edu.cn.jw.controller.eehManagement;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.eehManagement.EehTree;
import com.yice.edu.cn.jw.service.eehManagement.EehTreeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eehTree")
@Api(value = "/eehTree",description = "模块")
public class EehTreeController {
    @Autowired
    private EehTreeService eehTreeService;

    @GetMapping("/findEehTreeById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public EehTree findEehTreeById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return eehTreeService.findEehTreeById(id);
    }

    @PostMapping("/saveEehTree")
    @ApiOperation(value = "保存", notes = "返回对象")
    public EehTree saveEehTree(
            @ApiParam(value = "对象", required = true)
            @RequestBody EehTree eehTree){
        eehTreeService.saveEehTree(eehTree);
        return eehTree;
    }

    @PostMapping("/findEehTreeListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<EehTree> findEehTreeListByCondition(
            @ApiParam(value = "对象")
            @RequestBody EehTree eehTree){
        return eehTreeService.findEehTreeListByCondition(eehTree);
    }
    @PostMapping("/findEehTreeCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findEehTreeCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody EehTree eehTree){
        return eehTreeService.findEehTreeCountByCondition(eehTree);
    }

    @PostMapping("/updateEehTree")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateEehTree(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody EehTree eehTree){
        eehTreeService.updateEehTree(eehTree);
    }

    @GetMapping("/deleteEehTree/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteEehTree(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        eehTreeService.deleteEehTree(id);
    }
    @PostMapping("/deleteEehTreeByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteEehTreeByCondition(
            @ApiParam(value = "对象")
            @RequestBody EehTree eehTree){
        eehTreeService.deleteEehTreeByCondition(eehTree);
    }
    @PostMapping("/findOneEehTreeByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public EehTree findOneEehTreeByCondition(
            @ApiParam(value = "对象")
            @RequestBody EehTree eehTree){
        return eehTreeService.findOneEehTreeByCondition(eehTree);
    }

    @GetMapping("/findAllTreeMenu/{type}")
    public List<EehTree> findAllTreeMenu(@PathVariable String type){
        List<EehTree> perms=eehTreeService.findAllTreeMenu(type);
        return perms;
    }

    @GetMapping("/lookEehTreeNewById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public EehTree lookEehTreeNewById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return eehTreeService.lookEehTreeNewById(id);
    }
    @GetMapping("/findChildEehId/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=EehTree.class)
    public List<String> findChildEehId( @ApiParam(value = "需要用到的id", required = true) @PathVariable String id){
        return eehTreeService.findChildEehId(id);
    }


    @PostMapping("/findEehSchoolListNoCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<EehTree> findEehSchoolListNoCondition(
            @ApiParam(value = "对象")
            @RequestBody EehTree eehTree){
        return eehTreeService.findEehSchoolListNoCondition(eehTree);
    }
}
