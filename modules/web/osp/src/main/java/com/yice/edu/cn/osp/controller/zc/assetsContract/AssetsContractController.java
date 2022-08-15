package com.yice.edu.cn.osp.controller.zc.assetsContract;

import com.qiniu.http.Response;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.zc.assetsContract.AssetsContract;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.osp.service.zc.assetsContract.AssetsContractService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/assetsContract")
@Api(value = "/assetsContract", description = "资产合同模块")
public class AssetsContractController {
    @Autowired
    private AssetsContractService assetsContractService;

    @PostMapping("/saveAssetsContract")
    @ApiOperation(value = "保存资产合同对象", notes = "返回保存好的资产合同对象", response = AssetsContract.class)
    public ResponseJson saveAssetsContract(
            @ApiParam(value = "资产合同对象", required = true)
            @RequestBody AssetsContract assetsContract) {
        if(StringUtils.isEmpty(assetsContract.getUrl())){
            return new ResponseJson(false,"请上传文件");
        }
        assetsContract.setSchoolId(mySchoolId());
        AssetsContract s = assetsContractService.saveAssetsContract(assetsContract);
        return new ResponseJson(s);
    }

    @GetMapping("/look/lookAssetsContractById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找资产合同", notes = "返回响应对象", response = AssetsContract.class)
    public ResponseJson lookAssetsContractById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        AssetsContract assetsContract = assetsContractService.findAssetsContractById(id);
        return new ResponseJson(assetsContract);
    }

    @PostMapping("/findAssetsContractsByCondition")
    @ApiOperation(value = "根据条件查找资产合同", notes = "返回响应对象", response = AssetsContract.class)
    public ResponseJson findAssetsContractsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AssetsContract assetsContract) {
        if (assetsContract.getPurchaseName()!=null){
            Pager pager = assetsContract.getPager().setLike("purchaseName");
            assetsContract.setPager(pager);
        }
        assetsContract.setSchoolId(mySchoolId());
        List<AssetsContract> data = assetsContractService.findAssetsContractListByCondition(assetsContract);
        long count = assetsContractService.findAssetsContractCountByCondition(assetsContract);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneAssetsContractByCondition")
    @ApiOperation(value = "根据条件查找单个资产合同,结果必须为单条数据", notes = "没有时返回空", response = AssetsContract.class)
    public ResponseJson findOneAssetsContractByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody AssetsContract assetsContract) {
        AssetsContract one = assetsContractService.findOneAssetsContractByCondition(assetsContract);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteAssetsContract/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteAssetsContract(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        assetsContractService.deleteAssetsContract(id);
        return new ResponseJson();
    }

    @PostMapping("/deleteAssetsContractByIds")
    @ApiOperation(value = "根据多个id删除,id数组放在rowDatas字段中", notes = "返回响应对象")
    public ResponseJson deleteAssetsContractByIds(
            @ApiParam(value = "被删除记录的ids")
            @Validated
            @RequestBody AssetsContract assetsContract) {
        assetsContractService.deleteAssetsContractByIds(assetsContract);
        return new ResponseJson();
    }

    @PostMapping("/findAssetsContractListByCondition")
    @ApiOperation(value = "根据条件查找资产合同列表", notes = "返回响应对象,不包含总条数", response = AssetsContract.class)
    public ResponseJson findAssetsContractListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AssetsContract assetsContract) {
        assetsContract.setSchoolId(mySchoolId());
        List<AssetsContract> data = assetsContractService.findAssetsContractListByCondition(assetsContract);
        return new ResponseJson(data);
    }

    @PostMapping("deleteQiniuFile")
    @ApiOperation(value = "说明：用于删除七牛的文件", notes = "返回响应对象")
    public Response deleteQiniuFile(@ApiParam(value = "去删除七牛的文件,需要用到url", required = true) String url) {
        return QiniuUtil.deleteFile(url);
    }

    @PostMapping("/uploadQiniuFile")
    @ApiOperation(value = "说明：上传文件到七牛", notes = "返回资源名称和资源的url", response = AssetsContract.class)
    public ResponseJson uploadQiniuFile(@ApiParam(value = "上传到七牛的文件", required = true) MultipartFile file) {
        //文件名后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        String[] docArray = {"doc", "docx", "pdf"};
        if (!ArrayUtils.contains(docArray, suffix)) {
            return new ResponseJson(false, "不支持的文件格式");
        }
        Map<String, String> map = new HashMap<>();
        //获取保存文件路径
        String url = QiniuUtil.commonUploadFile(file, Constant.Upload.ZC_Contract + suffix);
        map.put("url", url);
        map.put("contractName", file.getOriginalFilename());
        return new ResponseJson(map);
    }
}
