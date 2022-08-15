package com.yice.edu.cn.osp.controller.xw.dj;

import com.alibaba.fastjson.JSONObject;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.dj.DjClassify;
import com.yice.edu.cn.common.pojo.xw.dj.XwDjStudyResource;
import com.yice.edu.cn.osp.service.xw.dj.DjClassifyService;
import com.yice.edu.cn.osp.service.xw.dj.XwDjStudyResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/djClassify")
@Api(value = "/djClassify",description = "党建公用类型表模块")
public class DjClassifyController {
    @Autowired
    private DjClassifyService djClassifyService;

    @Autowired
    private XwDjStudyResourceService xwDjStudyResourceService;

    @PostMapping("/saveDjClassify")
    @ApiOperation(value = "保存党建公用类型表对象", notes = "返回保存好的党建公用类型表对象", response=DjClassify.class)
    public ResponseJson saveDjClassify(
            @ApiParam(value = "党建公用类型表对象", required = true)
            @RequestBody DjClassify djClassify){
       djClassify.setSchoolId(mySchoolId());
       //判断是否重复
        if(djClassifyService.findClassifyListByType(djClassify).size()>0){
            return  new ResponseJson(false,"该类型已存在");
        }
        DjClassify s=djClassifyService.saveDjClassify(djClassify);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findDjClassifyById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找党建公用类型表", notes = "返回响应对象", response=DjClassify.class)
    public ResponseJson findDjClassifyById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DjClassify djClassify=djClassifyService.findDjClassifyById(id);
        return new ResponseJson(djClassify);
    }

    @PostMapping("/update/updateDjClassify")
    @ApiOperation(value = "修改党建公用类型表对象", notes = "返回响应对象")
    public ResponseJson updateDjClassify(
            @ApiParam(value = "被修改的党建公用类型表对象,对象属性不为空则修改", required = true)
            @RequestBody DjClassify djClassify){
        //判断是否重复
        if(djClassifyService.findClassifyListByType(djClassify).size()>0){
            return  new ResponseJson(false,"该类型已存在");
        }
        djClassifyService.updateDjClassify(djClassify);
        return new ResponseJson();
    }

    @GetMapping("/look/lookDjClassifyById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找党建公用类型表", notes = "返回响应对象", response=DjClassify.class)
    public ResponseJson lookDjClassifyById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        DjClassify djClassify=djClassifyService.findDjClassifyById(id);
        return new ResponseJson(djClassify);
    }

    @PostMapping("/findDjClassifysByCondition")
    @ApiOperation(value = "根据条件查找党建公用类型表", notes = "返回响应对象", response=DjClassify.class)
    public ResponseJson findDjClassifysByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DjClassify djClassify){
        djClassify.setSchoolId(mySchoolId());
        List<DjClassify> data=djClassifyService.findDjClassifyListByCondition(djClassify);
        long count=djClassifyService.findDjClassifyCountByCondition(djClassify);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDjClassifyByCondition")
    @ApiOperation(value = "根据条件查找单个党建公用类型表,结果必须为单条数据", notes = "没有时返回空", response=DjClassify.class)
    public ResponseJson findOneDjClassifyByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DjClassify djClassify){
        DjClassify one=djClassifyService.findOneDjClassifyByCondition(djClassify);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteDjClassify/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDjClassify(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        //如果分类下面有学习资源则不能进行删除
        XwDjStudyResource xw = new XwDjStudyResource();
        xw.setType(id);
       List<XwDjStudyResource>  li = xwDjStudyResourceService.findXwDjStudyResourceListByCondition(xw);
       if(li.size()>0){
           return  new ResponseJson(false,"该分类下有资源信息，无法删除");
       }
        djClassifyService.deleteDjClassify(id);
        return new ResponseJson();
    }
    @GetMapping("/deleteDjClassifyNotEnpty/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDjClassifyNotEnpty(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        JSONObject data = djClassifyService.deleteDjClassifyNotEnpty(id);
        return new ResponseJson(data.get("success"),data.get("msg"));
    }

    @GetMapping("/findActivityDjClassify")
    @ApiOperation(value = "查找所有党建活动类型", notes = "返回所有党建活动类型", response=DjClassify.class)
    public ResponseJson findActivityDjClassify(){
        List<DjClassify> data= djClassifyService.findActivityDjClassify();
        return new ResponseJson(data);
    }

    @PostMapping("/findDjClassifyListByCondition")
    @ApiOperation(value = "根据条件查找党建公用类型表列表", notes = "返回响应对象,不包含总条数", response=DjClassify.class)
    public ResponseJson findDjClassifyListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DjClassify djClassify){
       djClassify.setSchoolId(mySchoolId());
        List<DjClassify> data=djClassifyService.findDjClassifyListByCondition(djClassify);
        return new ResponseJson(data);
    }

    @PostMapping("/selectClassifyListByType")
    @ApiOperation(value = "根据条件查找党建公用类型表", notes = "返回响应对象", response=DjClassify.class)
    public ResponseJson selectClassifyListByType(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DjClassify djClassify){
        djClassify.setSchoolId(mySchoolId());
        if(StringUtils.isBlank(djClassify.getClassifyType())){
            return  new ResponseJson(false,"下拉框类型不能为空");
        }
        List<DjClassify> data=djClassifyService.selectClassifyListByType(djClassify);
        return new ResponseJson(data);
    }

}
