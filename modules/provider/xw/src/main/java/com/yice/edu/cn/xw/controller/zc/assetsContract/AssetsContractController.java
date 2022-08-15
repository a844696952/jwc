package com.yice.edu.cn.xw.controller.zc.assetsContract;

import com.yice.edu.cn.common.pojo.xw.zc.assetsContract.AssetsContract;
import com.yice.edu.cn.xw.service.zc.assetsContract.AssetsContractService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assetsContract")
@Api(value = "/assetsContract",description = "资产合同模块")
public class AssetsContractController {
    @Autowired
    private AssetsContractService assetsContractService;

    @GetMapping("/findAssetsContractById/{id}")
    @ApiOperation(value = "通过id查找资产合同", notes = "返回资产合同对象")
    public AssetsContract findAssetsContractById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return assetsContractService.findAssetsContractById(id);
    }

    @PostMapping("/saveAssetsContract")
    @ApiOperation(value = "保存资产合同", notes = "返回资产合同对象")
    public AssetsContract saveAssetsContract(
            @ApiParam(value = "资产合同对象", required = true)
            @RequestBody AssetsContract assetsContract){
        assetsContractService.saveAssetsContract(assetsContract);
        return assetsContract;
    }

    @PostMapping("/findAssetsContractListByCondition")
    @ApiOperation(value = "根据条件查找资产合同列表", notes = "返回资产合同列表")
    public List<AssetsContract> findAssetsContractListByCondition(
            @ApiParam(value = "资产合同对象")
            @RequestBody AssetsContract assetsContract){
        return assetsContractService.findAssetsContractListByCondition(assetsContract);
    }
    @PostMapping("/findAssetsContractCountByCondition")
    @ApiOperation(value = "根据条件查找资产合同列表个数", notes = "返回资产合同总个数")
    public long findAssetsContractCountByCondition(
            @ApiParam(value = "资产合同对象")
            @RequestBody AssetsContract assetsContract){
        return assetsContractService.findAssetsContractCountByCondition(assetsContract);
    }

    @PostMapping("/updateAssetsContract")
    @ApiOperation(value = "修改资产合同", notes = "资产合同对象必传")
    public void updateAssetsContract(
            @ApiParam(value = "资产合同对象,对象属性不为空则修改", required = true)
            @RequestBody AssetsContract assetsContract){
        assetsContractService.updateAssetsContract(assetsContract);
    }

    @GetMapping("/deleteAssetsContract/{id}")
    @ApiOperation(value = "通过id删除资产合同")
    public void deleteAssetsContract(
            @ApiParam(value = "资产合同对象", required = true)
            @PathVariable String id){
        assetsContractService.deleteAssetsContract(id);
    }
    @PostMapping("/deleteAssetsContractByCondition")
    @ApiOperation(value = "根据条件删除资产合同")
    public void deleteAssetsContractByCondition(
            @ApiParam(value = "资产合同对象")
            @RequestBody AssetsContract assetsContract){
        assetsContractService.deleteAssetsContractByCondition(assetsContract);
    }
    @PostMapping("/findOneAssetsContractByCondition")
    @ApiOperation(value = "根据条件查找单个资产合同,结果必须为单条数据", notes = "返回单个资产合同,没有时为空")
    public AssetsContract findOneAssetsContractByCondition(
            @ApiParam(value = "资产合同对象")
            @RequestBody AssetsContract assetsContract){
        return assetsContractService.findOneAssetsContractByCondition(assetsContract);
    }

    @PostMapping("/getFileList")
    @ApiOperation(value = "获取文件列表")
    public List<AssetsContract> getFileList(
            @ApiParam(value = "资产管理合同对象")
            @RequestBody AssetsContract assetsContract) {
        return assetsContractService.getFileList(assetsContract);
    }

    @PostMapping("/deleteAssetsContractByIds")
    @ApiOperation(value = "根据多个id删除,id数组放在rowDatas字段中")
    public void deleteAssetsContractByIds(
            @ApiParam(value = "资产管理合同对象")
            @RequestBody AssetsContract assetsContract){
        assetsContractService.deleteAssetsContractByIds(assetsContract);
    }
}
