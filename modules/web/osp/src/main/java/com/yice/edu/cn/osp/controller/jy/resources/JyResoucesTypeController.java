package com.yice.edu.cn.osp.controller.jy.resources;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.resources.JyResouces;
import com.yice.edu.cn.common.pojo.jy.resources.JyResoucesType;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.osp.service.jy.resources.JyResoucesTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/jyResoucesType")
@Api(value = "/jyResoucesType",description = "创建时间：2018-10-30。说明：用于存放我的资源的所有类型，相当于文件夹模块")
public class JyResoucesTypeController {
    @Autowired
    private JyResoucesTypeService jyResoucesTypeService;

    @PostMapping("/saveJyResoucesType")
    @ApiOperation(value = "保存创建时间：2018-10-30。说明：用于存放我的资源的所有类型，相当于文件夹对象", notes = "返回响应对象")
    public ResponseJson saveJyResoucesType(
            @ApiParam(value = "创建时间：2018-10-30。说明：用于存放我的资源的所有类型，相当于文件夹对象", required = true)
            @RequestBody JyResoucesType jyResoucesType){
        jyResoucesType.setTeacherId(myId());
        jyResoucesType.setSchoolId(mySchoolId());
        JyResoucesType s=jyResoucesTypeService.saveJyResoucesType(jyResoucesType);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findJyResoucesTypeById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找创建时间：2018-10-30。说明：用于存放我的资源的所有类型，相当于文件夹", notes = "返回响应对象")
    public ResponseJson findJyResoucesTypeById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        JyResoucesType jyResoucesType=jyResoucesTypeService.findJyResoucesTypeById(id);
        return new ResponseJson(jyResoucesType);
    }

    @PostMapping("/update/updateJyResoucesType")
    @ApiOperation(value = "修改创建时间：2018-10-30。说明：用于存放我的资源的所有类型，相当于文件夹对象", notes = "返回响应对象")
    public ResponseJson updateJyResoucesType(
            @ApiParam(value = "被修改的创建时间：2018-10-30。说明：用于存放我的资源的所有类型，相当于文件夹对象,对象属性不为空则修改", required = true)
            @RequestBody JyResoucesType jyResoucesType){
        jyResoucesTypeService.updateJyResoucesType(jyResoucesType);
        return new ResponseJson();
    }

    @GetMapping("/look/lookJyResoucesTypeById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找创建时间：2018-10-30。说明：用于存放我的资源的所有类型，相当于文件夹", notes = "返回响应对象")
    public ResponseJson lookJyResoucesTypeById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        JyResoucesType jyResoucesType=jyResoucesTypeService.findJyResoucesTypeById(id);
        return new ResponseJson(jyResoucesType);
    }

    @PostMapping("/findJyResoucesTypesByCondition")
    @ApiOperation(value = "根据条件查找创建时间：2018-10-30。说明：用于存放我的资源的所有类型，相当于文件夹", notes = "返回响应对象")
    public ResponseJson findJyResoucesTypesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody JyResoucesType jyResoucesType){
        jyResoucesType.setTeacherId(myId());
        List<JyResoucesType> data=jyResoucesTypeService.findJyResoucesTypeListByCondition(jyResoucesType);
        long count=jyResoucesTypeService.findJyResoucesTypeCountByCondition(jyResoucesType);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneJyResoucesTypeByCondition")
    @ApiOperation(value = "根据条件查找单个创建时间：2018-10-30。说明：用于存放我的资源的所有类型，相当于文件夹,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneJyResoucesTypeByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody JyResoucesType jyResoucesType){
        JyResoucesType one=jyResoucesTypeService.findOneJyResoucesTypeByCondition(jyResoucesType);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteJyResoucesType/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteJyResoucesType(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        jyResoucesTypeService.deleteJyResoucesType(id);
        return new ResponseJson();
    }


    @PostMapping("/findJyResoucesTypeListByCondition")
    @ApiOperation(value = "根据条件查找创建时间：2018-10-30。说明：用于存放我的资源的所有类型，相当于文件夹列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findJyResoucesTypeListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody JyResoucesType jyResoucesType){
        jyResoucesType.setTeacherId(myId());
        List<JyResoucesType> data=jyResoucesTypeService.findJyResoucesTypeListByCondition(jyResoucesType);
        return new ResponseJson(data);
    }
    /**
     * 批量移动文件到相应的文件夹
     *
     * @param jyResouces
     */
    @PostMapping("/updateManyResouces")
    @ApiOperation(value = "返回文件夹和资源个数")
    public ResponseJson updateManyResoucesType(
            @ApiParam(value = "参数为整个JyResouces对象")
            @Validated
            @RequestBody JyResouces jyResouces) {

        jyResoucesTypeService.updateManyResoucesType(jyResouces);
        return new ResponseJson();
    }

    /**
     * 批量删除文件
     *
     * @param jyResouces
     */
    @PostMapping("/deleteManyResoucesType")
    @ApiOperation(value = "返回文件夹和资源个数")
    public ResponseJson deleteManyResoucesType(
            @ApiParam(value = "参数为整个JyResouces对象")
            @Validated
            @RequestBody JyResouces jyResouces) {
        jyResoucesTypeService.deleteManyResoucesType(jyResouces);
        return new ResponseJson();
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
     * 获取文件夹的属性结构
     * @param jyResoucesType
     * @return
     */
    @PostMapping("/getResoucesTree")
    @ApiOperation(value = "获取树形结构的文件夹")
    public ResponseJson getResoucesTree(
            @ApiParam(value = "参数为整个jyResouces对象")
            @RequestBody JyResoucesType jyResoucesType){
        jyResoucesType.setTeacherId(myId());
        jyResoucesType.setSchoolId(mySchoolId());
        List<JyResoucesType> data=jyResoucesTypeService.getTreeList(jyResoucesType);
        List<JyResoucesType> treeData = ObjectKit.buildTree(data,"0");
        return new ResponseJson(treeData);
    }
}
