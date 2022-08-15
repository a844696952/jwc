package com.yice.edu.cn.osp.controller.zc.assetsFile;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.zc.assetsFile.AssetsFile;
import com.yice.edu.cn.common.pojo.xw.zc.assetsStockDetail.AssetsStockDetail;
import com.yice.edu.cn.common.pojo.xw.zc.assetsUnit.AssetsUnit;
import com.yice.edu.cn.common.pojo.xw.zc.assetsWarehouse.AssetsWarehouse;
import com.yice.edu.cn.osp.service.zc.assetsFile.AssetsFileService;
import com.yice.edu.cn.osp.service.zc.assetsStockDetail.AssetsStockDetailService;
import com.yice.edu.cn.osp.service.zc.assetsUnit.AssetsUnitService;
import com.yice.edu.cn.osp.service.zc.assetsWarehouse.AssetsWarehouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.*;

@RestController
@RequestMapping("/assetsFile")
@Api(value = "/assetsFile",description = "资产档案模块")
public class AssetsFileController {
    @Autowired
    private AssetsFileService assetsFileService;
    @Autowired
    private AssetsUnitService assetsUnitService;
    @Autowired
    private AssetsWarehouseService assetsWarehouseService;
    @Autowired
    private AssetsStockDetailService assetsStockDetailService;

    @PostMapping("/save/assetsFile")
    @ApiOperation(value = "保存资产档案对象", notes = "返回保存好的资产档案对象", response=AssetsFile.class)
    public ResponseJson saveAssetsFile(
            @ApiParam(value = "资产档案对象", required = true)
            @RequestBody AssetsFile assetsFile){

//        保存之前先判断存货编码是否已经存在,存在返回消息。
        AssetsFile assetsFileParam = new AssetsFile();
        assetsFileParam.setSchoolId(mySchoolId());//当前学校是否有 这个存货编码
        assetsFileParam.setInventoryCode(assetsFile.getInventoryCode());
        long count = assetsFileService.findAssetsFileCountByCondition(assetsFileParam);

        if(count == 0){
            assetsFile.setSchoolId(mySchoolId());
            AssetsFile s=assetsFileService.saveAssetsFile(assetsFile);
            return new ResponseJson(s);
        }
        return new ResponseJson(false,"该存货编码已存在！");

    }

    @GetMapping("/update/findAssetsFileById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找资产档案", notes = "返回响应对象", response=AssetsFile.class)
    public ResponseJson findAssetsFileById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        AssetsFile assetsFile=assetsFileService.findAssetsFileById(id);
        return new ResponseJson(assetsFile);
    }

    @PostMapping("/update/updateAssetsFile")
    @ApiOperation(value = "修改资产档案对象", notes = "返回响应对象")
    public ResponseJson updateAssetsFile(
            @ApiParam(value = "被修改的资产档案对象,对象属性不为空则修改", required = true)
            @RequestBody AssetsFile assetsFile){
        assetsFileService.updateAssetsFile(assetsFile);
        return new ResponseJson();
    }

    @GetMapping("/look/lookAssetsFileById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找资产档案", notes = "返回响应对象", response=AssetsFile.class)
    public ResponseJson lookAssetsFileById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        AssetsFile assetsFile=assetsFileService.findAssetsFileById(id);
        return new ResponseJson(assetsFile);
    }

    @PostMapping("/find/findAssetsFilesByCondition")
    @ApiOperation(value = "根据条件查找资产档案", notes = "返回响应对象", response=AssetsFile.class)
    public ResponseJson findAssetsFilesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AssetsFile assetsFile){
       assetsFile.setSchoolId(mySchoolId());
        assetsFile.getPager().setLike("inventoryCode,assetsName");
        assetsFile.getPager().setSortField("createTime");
        assetsFile.getPager().setSortOrder(Pager.DESC);
        List<AssetsFile> data=assetsFileService.findAssetsFileListByCondition(assetsFile);
        long count=assetsFileService.findAssetsFileCountByCondition(assetsFile);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneAssetsFileByCondition")
    @ApiOperation(value = "根据条件查找单个资产档案,结果必须为单条数据", notes = "没有时返回空", response=AssetsFile.class)
    public ResponseJson findOneAssetsFileByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody AssetsFile assetsFile){
        AssetsFile one=assetsFileService.findOneAssetsFileByCondition(assetsFile);
        return new ResponseJson(one);
    }
    @GetMapping("/delete/assetsFile/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteAssetsFile(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        AssetsStockDetail assetsStockDetail = new AssetsStockDetail();
        assetsStockDetail.setAssetsFileId(id);
        assetsStockDetail.setStatus(1);
        assetsStockDetail.setFreeFile("1");

        long count = assetsStockDetailService.findAssetsStockDetailCountByFuzzyCondition(assetsStockDetail);
        if(count == 0){
            assetsFileService.deleteAssetsFile(id);
            assetsStockDetail.setDel(2);
            assetsStockDetail.setDelStaffId(myId());
            assetsStockDetail.setDelStaffName(currentTeacher().getName());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            assetsStockDetail.setDelTime(sdf.format(new Date()));// 设置时间

            assetsStockDetailService.updateAssetsStockDetailForNotNullByCondition(assetsStockDetail);
            return new ResponseJson();
        }
        return new ResponseJson(false,"该档案存在占用资产，无法删除！");
    }


    @PostMapping("/find/assetsFileListByCondition")
    @ApiOperation(value = "根据条件查找资产档案列表", notes = "返回响应对象,不包含总条数", response=AssetsFile.class)
    public ResponseJson findAssetsFileListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AssetsFile assetsFile){
        assetsFile.setSchoolId(mySchoolId());
        assetsFile.getPager().setLike("inventoryCode");
        assetsFile.getPager().setLike("assetsName");
        assetsFile.getPager().setSortField("createTime");
        assetsFile.getPager().setSortOrder(Pager.DESC);
//        assetsFile.getPager().setLike("name");
        List<AssetsFile> data=assetsFileService.findAssetsFileListByCondition(assetsFile);
        return new ResponseJson(data);
    }


    @GetMapping("/find/assetsUnitListByCondition")
    @ApiOperation(value = "根据条件查找资产档案列表", notes = "返回响应对象,不包含总条数", response=AssetsFile.class)
    public ResponseJson findAssetsUnitListByCondition(){
        AssetsUnit assetsUnit = new AssetsUnit();
        assetsUnit.setSchoolId(mySchoolId());
        assetsUnit.setDel(1);
//        assetsUnit.getPager().setSortField("createTime");
//        assetsUnit.getPager().setSortOrder(Pager.DESC);
        List<AssetsUnit> data=assetsUnitService.findAssetsUnitListByCondition(assetsUnit);
        return new ResponseJson(data);
    }

    @GetMapping("/find/findAssetsWarehouseListByCondition")
    @ApiOperation(value = "根据条件查找资产档案列表", notes = "返回响应对象,不包含总条数", response=AssetsFile.class)
    public ResponseJson findAssetsWarehouseListByCondition(){
        AssetsWarehouse assetsWarehouse = new AssetsWarehouse();
        assetsWarehouse.setSchoolId(mySchoolId());
//        assetsWarehouse.setDel(1);
        List<AssetsWarehouse> data=assetsWarehouseService.findAssetsWarehouseListByCondition(assetsWarehouse);
        return new ResponseJson(data);
    }


}
