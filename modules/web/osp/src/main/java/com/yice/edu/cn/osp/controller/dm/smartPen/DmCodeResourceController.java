package com.yice.edu.cn.osp.controller.dm.smartPen;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.smartPen.DmCodeResource;
import com.yice.edu.cn.osp.service.dm.smartPen.DmCodeResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dmCodeResource")
@Api(value = "/dmCodeResource", description = "设备管理-智能笔-铺码资源表模块")
public class DmCodeResourceController {
    @Autowired
    private DmCodeResourceService dmCodeResourceService;

    @PostMapping("/saveDmCodeResource")
    @ApiOperation(value = "保存设备管理-智能笔-铺码资源表对象", notes = "返回保存好的设备管理-智能笔-铺码资源表对象", response = DmCodeResource.class)
    public ResponseJson saveDmCodeResource(
            @ApiParam(value = "设备管理-智能笔-铺码资源表对象", required = true)
            @RequestBody DmCodeResource dmCodeResource) {
        dmCodeResource.setTeacherId(myId());
        dmCodeResource.setSchoolId(mySchoolId());
        DmCodeResource s = dmCodeResourceService.saveDmCodeResource(dmCodeResource);
        return new ResponseJson(s);
    }

    @PostMapping("/batchSaveDmCodeResource")
    @ApiOperation(value = "批量保存设备管理-智能笔-铺码资源表对象", notes = "返回保存好的设备管理-智能笔-铺码资源表对象", response = DmCodeResource.class)
    public ResponseJson batchSaveDmCodeResource(
            @ApiParam(value = "多个设备管理-智能笔-铺码资源表对象", required = true)
            @RequestBody List<DmCodeResource> dmCodeResources) {
        List<Integer> pages = dmCodeResources.stream().filter(item -> item.getRecordPage() != null)
                .map(DmCodeResource::getRecordPage).distinct().collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(pages) && pages.size() < dmCodeResources.size()) {
            return new ResponseJson(false, "当前输入的页号已存在请重新输入");
        }
        DmCodeResource model = new DmCodeResource();
        model.setRecordPages(StringUtils.join(pages.toArray(), ","));
        List<DmCodeResource> data = judgeRepeat(model);
        if (CollectionUtil.isNotEmpty(data)) {
            return new ResponseJson(false, "当前输入的页号已存在请重新输入");
        }
        if (CollectionUtil.isNotEmpty(dmCodeResources)) {
            String schoolId = mySchoolId();
            String myId = myId();
            dmCodeResources.forEach(dmCodeResource -> {
                dmCodeResource.setTeacherId(myId);
                dmCodeResource.setSchoolId(schoolId);
            });
        }
        List<DmCodeResource> s = dmCodeResourceService.batchSaveDmCodeResource(dmCodeResources);
        return new ResponseJson(s);
    }

    /**
     * 判断页码是否重复
     *
     * @param dmCodeResource 查询条件
     * @return 数据
     */
    private List<DmCodeResource> judgeRepeat(DmCodeResource dmCodeResource) {
        DmCodeResource model = new DmCodeResource();
        model.setTeacherId(myId());
        model.setSchoolId(mySchoolId());
        model.setRecordPages(dmCodeResource.getRecordPages());
        return dmCodeResourceService.findDmCodeResourceListByCondition(model);
    }

    @GetMapping("/update/findDmCodeResourceById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找设备管理-智能笔-铺码资源表", notes = "返回响应对象", response = DmCodeResource.class)
    public ResponseJson findDmCodeResourceById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        DmCodeResource dmCodeResource = dmCodeResourceService.findDmCodeResourceById(id);
        return new ResponseJson(dmCodeResource);
    }

    @PostMapping("/update/updateDmCodeResource")
    @ApiOperation(value = "修改设备管理-智能笔-铺码资源表对象", notes = "返回响应对象")
    public ResponseJson updateDmCodeResource(
            @ApiParam(value = "被修改的设备管理-智能笔-铺码资源表对象,对象属性不为空则修改", required = true)
            @RequestBody DmCodeResource dmCodeResource) {
        dmCodeResource.setRecordPages(dmCodeResource.getRecordPage().toString());
        List<DmCodeResource> data = judgeRepeat(dmCodeResource);
        if (CollectionUtil.isNotEmpty(data) && !dmCodeResource.getId().equals(data.get(0).getId())) {
            return new ResponseJson(false, "当前输入的页号已存在请重新输入");
        }
        dmCodeResourceService.updateDmCodeResource(dmCodeResource);
        return new ResponseJson();
    }

    @GetMapping("/look/lookDmCodeResourceById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找设备管理-智能笔-铺码资源表", notes = "返回响应对象", response = DmCodeResource.class)
    public ResponseJson lookDmCodeResourceById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        DmCodeResource dmCodeResource = dmCodeResourceService.findDmCodeResourceById(id);
        return new ResponseJson(dmCodeResource);
    }

    @PostMapping("/findDmCodeResourcesByCondition")
    @ApiOperation(value = "根据条件查找设备管理-智能笔-铺码资源表", notes = "返回响应对象", response = DmCodeResource.class)
    public ResponseJson findDmCodeResourcesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmCodeResource dmCodeResource) {
        dmCodeResource.setSchoolId(mySchoolId());
        dmCodeResource.setTeacherId(myId());
        dmCodeResource.setPictureName(StrUtil.isNotBlank(dmCodeResource.getPictureName()) ? dmCodeResource.getPictureName().trim() : null);
        if (StrUtil.isNotBlank(dmCodeResource.getCreateDateEnd())) {
            Date ht = DateUtil.parse(dmCodeResource.getCreateDateEnd(), "yyyy-MM-dd");
            DateTime end = DateUtil.endOfDay(ht);
            dmCodeResource.setCreateDateEnd(end.toString());
        }
        List<DmCodeResource> data = dmCodeResourceService.findDmCodeResourceListByCondition(dmCodeResource);
        long count = dmCodeResourceService.findDmCodeResourceCountByCondition(dmCodeResource);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneDmCodeResourceByCondition")
    @ApiOperation(value = "根据条件查找单个设备管理-智能笔-铺码资源表,结果必须为单条数据", notes = "没有时返回空", response = DmCodeResource.class)
    public ResponseJson findOneDmCodeResourceByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmCodeResource dmCodeResource) {
        DmCodeResource one = dmCodeResourceService.findOneDmCodeResourceByCondition(dmCodeResource);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteDmCodeResource/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDmCodeResource(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        dmCodeResourceService.deleteDmCodeResource(id);
        return new ResponseJson();
    }

}
