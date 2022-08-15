package com.yice.edu.cn.osp.controller.zc.AssetsInNew;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.zc.assetsFile.AssetsFile;
import com.yice.edu.cn.common.pojo.xw.zc.assetsInNew.AssetsInNew;
import com.yice.edu.cn.osp.service.zc.AssetsInNew.AssetsInNewService;
import com.yice.edu.cn.osp.service.zc.assetsFile.AssetsFileService;
import com.yice.edu.cn.osp.service.zc.assetsStockDetail.AssetsStockDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/assetsInNew")
@Api(value = "/assetsInNew",description = "资产入库模块")
public class AssetsInNewController {
    @Autowired
    private AssetsInNewService assetsInNewService;
    @Autowired
    private AssetsStockDetailService assetsStockDetailService;
    @Autowired
    private AssetsFileService assetsFileService;


    @PostMapping("/save/assetsInNew")
    @ApiOperation(value = "保存资产入库对象", notes = "返回保存好的资产入库对象", response = AssetsInNew.class)
    public ResponseJson saveAssetsInNew(
            @ApiParam(value = "资产入库对象", required = true)
            @RequestBody AssetsInNew assetsInNew) {
        assetsInNew.setSchoolId(mySchoolId());
        assetsInNew.setCreateId(currentTeacher().getId());
        assetsInNew.setCreateUsername(currentTeacher().getName());
        assetsInNew.setAssetsFileId(assetsInNew.getId());
        AssetsInNew s = assetsInNewService.saveAssetsInNew(assetsInNew);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findAssetsInNewById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找资产入库", notes = "返回响应对象", response = AssetsInNew.class)
    public ResponseJson findAssetsInNewById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        AssetsInNew assetsInNew = assetsInNewService.findAssetsInNewById(id);
        return new ResponseJson(assetsInNew);
    }

    @PostMapping("/update/updateAssetsInNew")
    @ApiOperation(value = "修改资产入库对象", notes = "返回响应对象")
    public ResponseJson updateAssetsInNew(
            @ApiParam(value = "被修改的资产入库对象,对象属性不为空则修改", required = true)
            @RequestBody AssetsInNew assetsInNew) {
        assetsInNewService.updateAssetsInNew(assetsInNew);
        return new ResponseJson();
    }

    @GetMapping("/look/lookAssetsInNewById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找资产入库", notes = "返回响应对象", response = AssetsInNew.class)
    public ResponseJson lookAssetsInNewById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        AssetsInNew assetsInNew = assetsInNewService.findAssetsInNewById(id);
        return new ResponseJson(assetsInNew);
    }

    //    @PostMapping("/find/assetsInNewsByCondition")
//    @ApiOperation(value = "根据条件查找资产入库", notes = "返回响应对象", response=AssetsInNew.class)
//    public ResponseJson findAssetsInNewsByCondition(
//            @ApiParam(value = "属性不为空则作为条件查询")
//            @Validated
//            @RequestBody AssetsInNew assetsInNew){
//       assetsInNew.setSchoolId(mySchoolId());
//        List<AssetsInNew> data=assetsInNewService.findAssetsInNewListByCondition(assetsInNew);
//        long count=assetsInNewService.findAssetsInNewCountByCondition(assetsInNew);
//        return new ResponseJson(data,count);
//    }
    @PostMapping("/findOneAssetsInNewByCondition")
    @ApiOperation(value = "根据条件查找单个资产入库,结果必须为单条数据", notes = "没有时返回空", response = AssetsInNew.class)
    public ResponseJson findOneAssetsInNewByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody AssetsInNew assetsInNew) {
        AssetsInNew one = assetsInNewService.findOneAssetsInNewByCondition(assetsInNew);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteAssetsInNew/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteAssetsInNew(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        assetsInNewService.deleteAssetsInNew(id);
        return new ResponseJson();
    }


//    @PostMapping("/find/assetsInNewListByCondition")
//    @ApiOperation(value = "根据条件查找资产入库列表", notes = "返回响应对象,不包含总条数", response=AssetsInNew.class)
//    public ResponseJson findAssetsInNewListByCondition(
//            @ApiParam(value = "属性不为空则作为条件查询")
//            @Validated
//            @RequestBody AssetsInNew assetsInNew){
//       assetsInNew.setSchoolId(mySchoolId());
//        List<AssetsInNew> data=assetsInNewService.findAssetsInNewListByCondition(assetsInNew);
//        return new ResponseJson(data);
//    }


    @PostMapping("/find/findAssetsNoListByCondition")
    @ApiOperation(value = "首页 group by putInNo", notes = "返回响应对象,不包含总条数", response = AssetsInNew.class)
    public ResponseJson findAssetsNoListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AssetsInNew assetsInNew) {
        assetsInNew.setSchoolId(mySchoolId());
        List<AssetsInNew> data = assetsInNewService.findAssetsNoListByCondition(assetsInNew);
        long count = assetsInNewService.findAssetsNoCountByCondition(assetsInNew);
        return new ResponseJson(data, count);
    }


    @PostMapping("/find/findAssetsInNewDetailByNo")
    @ApiOperation(value = "根据 资产编号 查询，该资产编号的信息，包括该入库单有几个 资产", notes = "返回响应对象,不包含总条数", response = AssetsInNew.class)
    public ResponseJson findAssetsInNewDetailByNo(//findAssetsPutInBasicByNo
                                                  @ApiParam(value = "属性不为空则作为条件查询")
                                                  @Validated
                                                  @RequestBody AssetsInNew assetsInNew) {
        assetsInNew.setSchoolId(mySchoolId());
        List<AssetsInNew> assetsInNewList = assetsInNewService.findAssetsInNewDetailByNo(assetsInNew);
        long count = assetsInNewService.findAssetsInNewDetailCountByNo(assetsInNew);

        return new ResponseJson(assetsInNewList, count);
    }


    @GetMapping("/find/lookAssetsFileById/{assetsBarcode}")
    @ApiOperation(value = "去查看页面,通过id查找资产档案", notes = "返回响应对象", response = AssetsFile.class)
    public ResponseJson lookAssetsFileById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String assetsBarcode) {
        AssetsFile assetsFile = new AssetsFile();
        assetsFile.setAssetsBarcode(assetsBarcode);
        assetsFile.setSchoolId(mySchoolId());
        List<AssetsFile> assetsFileList = assetsFileService.findAssetsFileListByCondition(assetsFile);
        if (assetsFileList != null && assetsFileList.size() > 0) {
            return new ResponseJson(assetsFileList);
        }
        return new ResponseJson(false,"该条码未查询到档案信息！");
    }

}
