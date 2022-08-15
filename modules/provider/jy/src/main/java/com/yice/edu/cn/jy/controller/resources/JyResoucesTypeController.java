package com.yice.edu.cn.jy.controller.resources;

import com.yice.edu.cn.common.pojo.jy.resources.JyResouces;
import com.yice.edu.cn.common.pojo.jy.resources.JyResoucesType;
import com.yice.edu.cn.jy.service.resources.JyResoucesTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jyResoucesType")
@Api(value = "/jyResoucesType",description = "创建时间：2018-10-30。说明：用于存放我的资源的所有类型，相当于文件夹模块")
public class JyResoucesTypeController {
    @Autowired
    private JyResoucesTypeService jyResoucesTypeService;

    @GetMapping("/findJyResoucesTypeById/{id}")
    @ApiOperation(value = "通过id查找创建时间：2018-10-30。说明：用于存放我的资源的所有类型，相当于文件夹", notes = "返回创建时间：2018-10-30。说明：用于存放我的资源的所有类型，相当于文件夹对象")
    public JyResoucesType findJyResoucesTypeById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return jyResoucesTypeService.findJyResoucesTypeById(id);
    }

    @PostMapping("/saveJyResoucesType")
    @ApiOperation(value = "保存创建时间：2018-10-30。说明：用于存放我的资源的所有类型，相当于文件夹", notes = "返回创建时间：2018-10-30。说明：用于存放我的资源的所有类型，相当于文件夹对象")
    public JyResoucesType saveJyResoucesType(
            @ApiParam(value = "创建时间：2018-10-30。说明：用于存放我的资源的所有类型，相当于文件夹对象", required = true)
            @RequestBody JyResoucesType jyResoucesType){
        jyResoucesTypeService.saveJyResoucesType(jyResoucesType);
        return jyResoucesType;
    }

    @PostMapping("/findJyResoucesTypeListByCondition")
    @ApiOperation(value = "根据条件查找创建时间：2018-10-30。说明：用于存放我的资源的所有类型，相当于文件夹列表", notes = "返回创建时间：2018-10-30。说明：用于存放我的资源的所有类型，相当于文件夹列表")
    public List<JyResoucesType> findJyResoucesTypeListByCondition(
            @ApiParam(value = "创建时间：2018-10-30。说明：用于存放我的资源的所有类型，相当于文件夹对象")
            @RequestBody JyResoucesType jyResoucesType){
        return jyResoucesTypeService.findJyResoucesTypeListByCondition(jyResoucesType);
    }
    @PostMapping("/findJyResoucesTypeCountByCondition")
    @ApiOperation(value = "根据条件查找创建时间：2018-10-30。说明：用于存放我的资源的所有类型，相当于文件夹列表个数", notes = "返回创建时间：2018-10-30。说明：用于存放我的资源的所有类型，相当于文件夹总个数")
    public long findJyResoucesTypeCountByCondition(
            @ApiParam(value = "创建时间：2018-10-30。说明：用于存放我的资源的所有类型，相当于文件夹对象")
            @RequestBody JyResoucesType jyResoucesType){
        return jyResoucesTypeService.findJyResoucesTypeCountByCondition(jyResoucesType);
    }

    @PostMapping("/updateJyResoucesType")
    @ApiOperation(value = "修改创建时间：2018-10-30。说明：用于存放我的资源的所有类型，相当于文件夹", notes = "创建时间：2018-10-30。说明：用于存放我的资源的所有类型，相当于文件夹对象必传")
    public void updateJyResoucesType(
            @ApiParam(value = "创建时间：2018-10-30。说明：用于存放我的资源的所有类型，相当于文件夹对象,对象属性不为空则修改", required = true)
            @RequestBody JyResoucesType jyResoucesType){
        jyResoucesTypeService.updateJyResoucesType(jyResoucesType);
    }

    @GetMapping("/deleteJyResoucesType/{id}")
    @ApiOperation(value = "通过id删除创建时间：2018-10-30。说明：用于存放我的资源的所有类型，相当于文件夹")
    public void deleteJyResoucesType(
            @ApiParam(value = "创建时间：2018-10-30。说明：用于存放我的资源的所有类型，相当于文件夹对象", required = true)
            @PathVariable String id){
        jyResoucesTypeService.deleteJyResoucesType(id);
    }
    @PostMapping("/deleteJyResoucesTypeByCondition")
    @ApiOperation(value = "根据条件删除创建时间：2018-10-30。说明：用于存放我的资源的所有类型，相当于文件夹")
    public void deleteJyResoucesTypeByCondition(
            @ApiParam(value = "创建时间：2018-10-30。说明：用于存放我的资源的所有类型，相当于文件夹对象")
            @RequestBody JyResoucesType jyResoucesType){
        jyResoucesTypeService.deleteJyResoucesTypeByCondition(jyResoucesType);
    }
    @PostMapping("/findOneJyResoucesTypeByCondition")
    @ApiOperation(value = "根据条件查找单个创建时间：2018-10-30。说明：用于存放我的资源的所有类型，相当于文件夹,结果必须为单条数据", notes = "返回单个创建时间：2018-10-30。说明：用于存放我的资源的所有类型，相当于文件夹,没有时为空")
    public JyResoucesType findOneJyResoucesTypeByCondition(
            @ApiParam(value = "创建时间：2018-10-30。说明：用于存放我的资源的所有类型，相当于文件夹对象")
            @RequestBody JyResoucesType jyResoucesType){
        return jyResoucesTypeService.findOneJyResoucesTypeByCondition(jyResoucesType);
    }

    /**
     * 批量移动文件到相应的文件夹
     * @param jyResouces
     */
    @PostMapping("/updateManyResoucesType")
    @ApiOperation(value = "返回文件夹和资源个数")
    public void updateManyResoucesType(
            @ApiParam(value = "参数为整个JyResouces对象")
            @RequestBody JyResouces jyResouces){
        jyResoucesTypeService.updateManyResoucesType(jyResouces);
    }
    /**
     * 批量删除文件
     * @param jyResouces
     */
    @PostMapping("/deleteManyResoucesType")
    @ApiOperation(value = "返回文件夹和资源个数")
    public void deleteManyResoucesType(
            @ApiParam(value = "参数为整个JyResouces对象")
            @RequestBody JyResouces jyResouces){
        jyResoucesTypeService.deleteManyResoucesType(jyResouces);
    }

    /**
     * 去重复
     * @param jyResoucesType
     * @return
     */
    @PostMapping("/repeatType")
    @ApiOperation(value = "去重复")
    public int repeatType(
            @ApiParam(value = "参数为整个jyResouces对象")
            @RequestBody JyResoucesType jyResoucesType){
        return jyResoucesTypeService.repeatType(jyResoucesType);
    }

    /**
     * 获取属树形结构
     * @param jyResoucesType
     * @return
     */
    @PostMapping("/getTreeList")
    @ApiOperation(value = "去重复")
    public List<JyResoucesType> getTreeList(
            @ApiParam(value = "参数为整个jyResouces对象")
            @RequestBody JyResoucesType jyResoucesType){
        return jyResoucesTypeService.getTreeList(jyResoucesType);
    }

    /**
     * 移动去重处理
     * @param jyResouces
     * @return
     */
    @PostMapping("/removeRepartResoucesType")
    @ApiOperation(value = "移动去重处理")
    public List<JyResoucesType> removeRepartResoucesType(
            @ApiParam(value = "参数为整个jyResouces对象")
            @RequestBody JyResouces jyResouces){
        return jyResoucesTypeService.removeRepartResoucesType(jyResouces);
    }
}
