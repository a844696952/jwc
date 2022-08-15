package com.yice.edu.cn.osp.controller.zc.assetsOutNew;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.zc.assetsOutNew.AssetsOutNew;
import com.yice.edu.cn.osp.service.zc.assetsOutNew.AssetsOutNewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.*;

@RestController
@RequestMapping("/assetsOutNew")
@Api(value = "/assetsOutNew", description = "新资产出库模块")
public class AssetsOutNewController {
    @Autowired
    private AssetsOutNewService assetsOutNewService;

    @PostMapping("/saveAssetsOutNew")
    @ApiOperation(value = "保存新资产出库对象", notes = "返回保存好的新资产出库对象", response = AssetsOutNew.class)
    public ResponseJson saveAssetsOutNew(
            @ApiParam(value = "新资产出库对象", required = true)
            @RequestBody AssetsOutNew assetsOutNew) {
        assetsOutNew.setSchoolId(mySchoolId());
        AssetsOutNew s = assetsOutNewService.saveAssetsOutNew(assetsOutNew);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findAssetsOutNewById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找新资产出库", notes = "返回响应对象", response = AssetsOutNew.class)
    public ResponseJson findAssetsOutNewById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        AssetsOutNew assetsOutNew = assetsOutNewService.findAssetsOutNewById(id);
        return new ResponseJson(assetsOutNew);
    }

    @PostMapping("/update/updateAssetsOutNew")
    @ApiOperation(value = "修改新资产出库对象", notes = "返回响应对象")
    public ResponseJson updateAssetsOutNew(
            @ApiParam(value = "被修改的新资产出库对象,对象属性不为空则修改", required = true)
            @RequestBody AssetsOutNew assetsOutNew) {
        assetsOutNewService.updateAssetsOutNew(assetsOutNew);
        return new ResponseJson();
    }

    @GetMapping("/look/lookAssetsOutNewById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找新资产出库", notes = "返回响应对象", response = AssetsOutNew.class)
    public ResponseJson lookAssetsOutNewById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        AssetsOutNew assetsOutNew = assetsOutNewService.findAssetsOutNewById(id);
        return new ResponseJson(assetsOutNew);
    }

    @PostMapping("/findAssetsOutNewsByCondition")
    @ApiOperation(value = "根据条件查找新资产出库", notes = "返回响应对象", response = AssetsOutNew.class, responseContainer = "List")
    public ResponseJson findAssetsOutNewsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AssetsOutNew assetsOutNew) {
        assetsOutNew.setSchoolId(mySchoolId());
        List<AssetsOutNew> data = assetsOutNewService.findAssetsOutNewListByCondition(assetsOutNew);
        long count = assetsOutNewService.findAssetsOutNewCountByCondition(assetsOutNew);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneAssetsOutNewByCondition")
    @ApiOperation(value = "根据条件查找单个新资产出库,结果必须为单条数据", notes = "没有时返回空", response = AssetsOutNew.class)
    public ResponseJson findOneAssetsOutNewByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody AssetsOutNew assetsOutNew) {
        AssetsOutNew one = assetsOutNewService.findOneAssetsOutNewByCondition(assetsOutNew);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteAssetsOutNew/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteAssetsOutNew(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        assetsOutNewService.deleteAssetsOutNew(id);
        return new ResponseJson();
    }


    @PostMapping("/findAssetsOutNewListByCondition")
    @ApiOperation(value = "根据条件查找新资产出库列表", notes = "返回响应对象,不包含总条数", response = AssetsOutNew.class, responseContainer = "List")
    public ResponseJson findAssetsOutNewListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AssetsOutNew assetsOutNew) {
        assetsOutNew.setSchoolId(mySchoolId());
        List<AssetsOutNew> data = assetsOutNewService.findAssetsOutNewListByCondition(assetsOutNew);
        return new ResponseJson(data);
    }

    @PostMapping("/batchSaveAssetsOutNew")
    @ApiOperation(value = "批量保存", notes = "返回响应对象")
    public ResponseJson batchSaveAssetsOutNew(
            @ApiParam(value = "新资产出库对象")
            @RequestBody List<AssetsOutNew> assetsOutNews) {
        List<AssetsOutNew> newAssetsOutNews = assetsOutNews.stream().map(ss -> {
            ss.setSchoolId(mySchoolId());
            ss.setCreateUserId(myId());
            ss.setCreateUsername(currentTeacher().getName());
            return ss;
        }).collect(Collectors.toList());
        assetsOutNewService.batchSaveAssetsOutNew(newAssetsOutNews);
        return new ResponseJson();
    }

    @PostMapping("/findAssetsOutNewListByCondition4Gather")
    @ApiOperation(value = "出库单列表界面根据条件查找资产出库列表")
    public ResponseJson findAssetsOutNewListByCondition4Gather(
            @ApiParam(value = "新资产出库对象")
            @Validated
            @RequestBody AssetsOutNew assetsOutNew){
        assetsOutNew.setSchoolId(mySchoolId());
        List<AssetsOutNew> data = assetsOutNewService.findAssetsOutNewListByCondition4Gather(assetsOutNew);
        long count = assetsOutNewService.findAssetsOutNewCountByCondition4Gather(assetsOutNew);
        return new ResponseJson(data,count);
    }

}
