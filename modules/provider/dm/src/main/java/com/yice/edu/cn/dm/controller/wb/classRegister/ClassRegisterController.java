package com.yice.edu.cn.dm.controller.wb.classRegister;

import com.yice.edu.cn.common.pojo.wb.classRegister.ClassRegister;
import com.yice.edu.cn.dm.service.wb.classRegister.ClassRegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classRegister")
@Api(value = "/classRegister",description = "模块")
public class ClassRegisterController {
    @Autowired
    private ClassRegisterService classRegisterService;

    @GetMapping("/findClassRegisterById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public ClassRegister findClassRegisterById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return classRegisterService.findClassRegisterById(id);
    }

    @PostMapping("/saveClassRegister")
    @ApiOperation(value = "保存", notes = "返回对象")
    public ClassRegister saveClassRegister(
            @ApiParam(value = "对象", required = true)
            @RequestBody ClassRegister classRegister){
        classRegisterService.saveClassRegister(classRegister);
        return classRegister;
    }

    @PostMapping("/findClassRegisterListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<ClassRegister> findClassRegisterListByCondition(
            @ApiParam(value = "对象")
            @RequestBody ClassRegister classRegister){
        return classRegisterService.findClassRegisterListByCondition(classRegister);
    }
    @PostMapping("/findClassRegisterCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findClassRegisterCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody ClassRegister classRegister){
        return classRegisterService.findClassRegisterCountByCondition(classRegister);
    }

    @PostMapping("/updateClassRegister")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateClassRegister(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody ClassRegister classRegister){
        classRegisterService.updateClassRegister(classRegister);
    }

    @PostMapping("/updateClassRegisterStatus")
    @ApiOperation(value = "修改上课状态", notes = "对象必传")
    public void updateClassRegisterStatus(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody ClassRegister classRegister){
        classRegisterService.updateClassRegisterStatus(classRegister);
    }

    @GetMapping("/deleteClassRegister/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteClassRegister(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        classRegisterService.deleteClassRegister(id);
    }
    @PostMapping("/deleteClassRegisterByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteClassRegisterByCondition(
            @ApiParam(value = "对象")
            @RequestBody ClassRegister classRegister){
        classRegisterService.deleteClassRegisterByCondition(classRegister);
    }
    @PostMapping("/findOneClassRegisterByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public ClassRegister findOneClassRegisterByCondition(
            @ApiParam(value = "对象")
            @RequestBody ClassRegister classRegister){
        return classRegisterService.findOneClassRegisterByCondition(classRegister);
    }

    @PostMapping("/findClassRegisterListByCondition2")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<ClassRegister> findClassRegisterListByCondition2(
            @ApiParam(value = "对象")
            @RequestBody ClassRegister classRegister){
        return classRegisterService.findClassRegisterListByCondition2(classRegister);
    }
    @PostMapping("/findClassRegisterCountByCondition2")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findClassRegisterCountByCondition2(
            @ApiParam(value = "对象")
            @RequestBody ClassRegister classRegister){
        return classRegisterService.findClassRegisterCountByCondition2(classRegister);
    }
}
