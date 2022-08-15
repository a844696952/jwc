package com.yice.edu.cn.osp.controller.zc.assetsStockDetail;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.zc.AssetsQrcodeMsg;
import com.yice.edu.cn.common.pojo.xw.zc.AssetsResQrcode;
import com.yice.edu.cn.common.pojo.xw.zc.assetsContract.AssetsContract;
import com.yice.edu.cn.common.pojo.xw.zc.assetsFile.AssetsFile;
import com.yice.edu.cn.common.pojo.xw.zc.assetsStockDetail.AssetsStockDetail;
import com.yice.edu.cn.common.pojo.xw.zc.repairNew.RepairFile;
import com.yice.edu.cn.common.pojo.xw.zc.repairNew.RepairNew;
import com.yice.edu.cn.common.util.crypto.SimpleCryptoKit;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.common.util.zip.ZipUtils;
import com.yice.edu.cn.osp.service.jw.department.DepartmentTeacherService;
import com.yice.edu.cn.osp.service.zc.assetsStockDetail.AssetsStockDetailService;
import com.yice.edu.cn.osp.service.zc.repairNew.RepairFileService;
import com.yice.edu.cn.osp.service.zc.repairNew.RepairNewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/assetsStockDetail")
@Api(value = "/assetsStockDetail",description = "资产库存明细模块")
public class AssetsStockDetailController {
    @Autowired
    private AssetsStockDetailService assetsStockDetailService;
    @Autowired
    private RepairNewService repairNewService;
    @Autowired
    private DepartmentTeacherService departmentTeacherService;
    @Autowired
    private RepairFileService repairFileService;


    @PostMapping("/saveAssetsStockDetail")
    @ApiOperation(value = "保存资产库存明细对象", notes = "返回保存好的资产库存明细对象", response=AssetsStockDetail.class)
    public ResponseJson saveAssetsStockDetail(
            @ApiParam(value = "资产库存明细对象", required = true)
            @RequestBody AssetsStockDetail assetsStockDetail){
       assetsStockDetail.setSchoolId(mySchoolId());
        AssetsStockDetail s=assetsStockDetailService.saveAssetsStockDetail(assetsStockDetail);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findAssetsStockDetailById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找资产库存明细", notes = "返回响应对象", response=AssetsStockDetail.class)
    public ResponseJson findAssetsStockDetailById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        AssetsStockDetail assetsStockDetail=assetsStockDetailService.findAssetsStockDetailById(id);
        return new ResponseJson(assetsStockDetail);
    }

    @PostMapping("/update/updateAssetsStockDetail")
    @ApiOperation(value = "修改资产库存明细对象，【修改、删除、报废、归还、修改责任人】", notes = "返回响应对象")
    public ResponseJson updateAssetsStockDetail(
            @ApiParam(value = "被修改的资产库存明细对象,对象属性不为空则修改", required = true)
            @RequestBody AssetsStockDetail assetsStockDetail){
        assetsStockDetail.setSchoolId(mySchoolId());
        if( assetsStockDetail.getStatus() != null && assetsStockDetail.getStatus() == 5) {
            assetsStockDetail.setOperatorId(currentTeacher().getId());
            assetsStockDetail.setOperatorName(currentTeacher().getName());
        }else if(assetsStockDetail.getDel() != null && assetsStockDetail.getDel() == 2) {
            assetsStockDetail.setDelStaffId(currentTeacher().getId());
            assetsStockDetail.setDelStaffName(currentTeacher().getName());
        }
        assetsStockDetailService.updateAssetsStockDetailForNotNull(assetsStockDetail);

        return new ResponseJson();
    }

    @GetMapping("/look/lookAssetsStockDetailById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找资产库存明细", notes = "返回响应对象", response=AssetsStockDetail.class)
    public ResponseJson lookAssetsStockDetailById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        AssetsStockDetail one = assetsStockDetailService.findAssetsStockDetailJoinTableById(id);
        RepairFile repairFile = new RepairFile();
        repairFile.setAssetsStockDetailId(one.getId());
        List<RepairFile> repairFileListByCondition = repairFileService.findRepairFileListByCondition(repairFile);
        one.setRepairFiles(repairFileListByCondition);

        return new ResponseJson(one);
    }

    @GetMapping("/look/lookAssetsStockDetailByNo/{assetsNo}")
    @ApiOperation(value = "去查看页面,通过AssetsNo查找资产库存明细", notes = "返回响应对象", response=AssetsStockDetail.class)
    public ResponseJson lookAssetsStockDetailByAssetsNo(
            @ApiParam(value = "去查看页面,需要用到的AssetsNo", required = true)
            @PathVariable String assetsNo){
        AssetsStockDetail assetsStockDetail = new AssetsStockDetail();
        assetsStockDetail.setAssetsNo(assetsNo);
        AssetsStockDetail one=assetsStockDetailService.findAssetsStockDetailListByCondition(assetsStockDetail).get(0);
        return new ResponseJson(one);
    }

    @PostMapping("/find/findAssetsStockDetailsByCondition")
    @ApiOperation(value = "[首页]资产库存明细查询", notes = "返回响应对象", response=AssetsStockDetail.class)
    public ResponseJson findAssetsStockDetailsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AssetsStockDetail assetsStockDetail){
        assetsStockDetail.setSchoolId(mySchoolId());
        Pager pager = assetsStockDetail.getPager();
        pager.setLike("assetsNo");
        pager.setSortField("id");
        pager.setSortOrder("DESC");//
        assetsStockDetail.setPager(pager);
        //首页模糊查询
        List<AssetsStockDetail> data=assetsStockDetailService.findAssetsStockDetailListByFuzzyCondition(assetsStockDetail);
        long count=assetsStockDetailService.findAssetsStockDetailCountByFuzzyCondition(assetsStockDetail);
        return new ResponseJson(data,count);
    }
    @PostMapping("/find/findOneAssetsStockDetailByCondition")
    @ApiOperation(value = "根据条件查找单个资产库存明细,结果必须为单条数据", notes = "没有时返回空", response=AssetsStockDetail.class)
    public ResponseJson findOneAssetsStockDetailByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody AssetsStockDetail assetsStockDetail){

        //解密 资产编号
        assetsStockDetail.setAssetsNo(SimpleCryptoKit.decrypt(assetsStockDetail.getAssetsNo()));
        AssetsStockDetail one=assetsStockDetailService.findOneAssetsStockDetailByCondition(assetsStockDetail);
        return new ResponseJson(one);
    }
    @GetMapping("/delete/deleteAssetsStockDetail/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteAssetsStockDetail(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        assetsStockDetailService.deleteAssetsStockDetail(id);
        return new ResponseJson();
    }


    @PostMapping("/find/findAssetsStockDetailListByCondition")
    @ApiOperation(value = "根据条件查找资产明细列表", notes = "返回响应对象,不包含总条数", response=AssetsStockDetail.class)
    public ResponseJson findAssetsStockDetailListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AssetsStockDetail assetsStockDetail){
        assetsStockDetail.setSchoolId(mySchoolId());
        List<AssetsStockDetail> data=assetsStockDetailService.findAssetsStockDetailListByCondition(assetsStockDetail);
        return new ResponseJson(data);
    }



    @PostMapping("/look/findRepairListByAssetsById")
    @ApiOperation(value = "根据资产编号查询，资产所有维修记录，调用 兵彬报修接口", notes = "返回响应对象", response=AssetsStockDetail.class)
    public ResponseJson findRepairListByAssetsNo(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody RepairNew repairNew){

        //RepairNew repairNew = new RepairNew();

        repairNew.setSchoolId(mySchoolId());
        //repairNew.setPager(new Pager());
        repairNew.getPager().setSortField("repairPriority");
        repairNew.getPager().setSortOrder(Pager.DESC);
        List<RepairNew> data=repairNewService.findRepairNewListByCondition(repairNew);
        long count=repairNewService.findRepairNewCountByCondition(repairNew);
        return new ResponseJson(data,count);
    }

    @GetMapping("/find/findAssetsUseRecordById/{id}")
    @ApiOperation(value = "根据资产编号查询，资产所有使用记录", notes = "返回响应对象", response=AssetsStockDetail.class)
    public ResponseJson findAssetsUseRecordById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){

        AssetsStockDetail assetsStockDetail = new AssetsStockDetail();
        assetsStockDetail.setId(id);
        assetsStockDetail.setSchoolId(mySchoolId());
        assetsStockDetail.setPager(new Pager());
        assetsStockDetail.getPager().setSortField("putInTime");
        assetsStockDetail.getPager().setSortOrder(Pager.DESC);

        List<AssetsStockDetail> assetsUseRecordByNo = assetsStockDetailService.findAssetsUseRecordById(assetsStockDetail);
        long count = assetsStockDetailService.findAssetsUseRecordCountById(assetsStockDetail);

        return new ResponseJson(assetsUseRecordByNo,count);
    }


    @PostMapping("/update/uploadImg")
    @ApiOperation(value = "保修图片上传", notes = "返回图片地址")
    public String uploadImg(MultipartFile file){
        return QiniuUtil.uploadImage(file, Constant.Upload.ZC_ASSETS_STOCK_DETAIL);
    }


    @ApiOperation(value = "导出excel资产明细信息", notes = "返回一个excel")
    @PostMapping("/find/exportExcel")
//    public ResponseEntity<byte[]> exportExcel(
    public void exportExcel(
            @Validated
            @RequestBody List<AssetsStockDetail> assetsStockDetailList, HttpServletResponse response){
        //告诉浏览器用什么软件可以打开此文件
       response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=schedule.xls");

        try (ServletOutputStream s = response.getOutputStream()) {
            Workbook workbook = assetsStockDetailService.exportAssetsStockDetail(assetsStockDetailList);
            workbook.write(s);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private ResponseEntity<byte[]> getResponseEntity(String attachment, Workbook workbook) throws IOException {
        //创建一个HttpHeader
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", attachment);
        try (ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();) {
            workbook.write(outByteStream);
            headers.setContentLength(outByteStream.size());
            return new ResponseEntity<>(outByteStream.toByteArray(), headers, HttpStatus.OK);
        } catch (IOException e) {
            throw e;
        }
    }

    @PostMapping("/ignore/uploadFile")
    @ApiOperation(value = "说明：上传文件到七牛", notes = "返回资源名称和资源的url", response = AssetsContract.class)
    public ResponseJson uploadQiniuFile(@ApiParam(value = "上传到七牛的文件", required = true) MultipartFile file) {
        //文件名后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        Map<String, String> map = new HashMap<>();
        //获取保存文件路径
        String url = QiniuUtil.commonUploadFile(file, Constant.Upload.ZC_REPAIRNEW + suffix);
        map.put("url", url);
        map.put("contractName", file.getOriginalFilename());
        return new ResponseJson(map);
    }


    @PostMapping("/find/findDownloadStatus")
    @ApiOperation(value = "查询能否马上下载", notes = "返回一个二维码信息提示")
    public ResponseJson findDownloadStatus(
            @ApiParam(value = "生成多个二维码")
            @RequestBody List<String> ids) {

        AssetsQrcodeMsg data = assetsStockDetailService.createQrCodes(ids,mySchoolId());


        return new ResponseJson(data);
    }

    @PostMapping("/find/createQrCodes")
    @ApiOperation(value = "生成多个二维码", notes = "返回一个压缩包")
    public void createQrCodes(
            @ApiParam(value = "生成多个二维码")
            @RequestBody List<String> ids,HttpServletResponse response) throws IOException{

        List<AssetsResQrcode> assetsResListData = assetsStockDetailService.findAssetsResQrcodes(ids);
        ZipUtils zipUtils = new ZipUtils();
        zipUtils.downQrCode(assetsResListData, response);

    }

    @GetMapping("/update/selectRepairFilesById/{id}")
    @ApiOperation(value = "根据条件查找新报修表列表", notes = "返回响应对象,不包含总条数", response=RepairNew.class)
    public ResponseJson selectRepairFilesById(
            @PathVariable String id){
        RepairFile repairFile = new RepairFile();
        repairFile.setSchoolId(mySchoolId());
        repairFile.setRepairId(id);
        List<RepairFile> data=repairFileService.findRepairFileListByCondition(repairFile);
        return new ResponseJson(data);
    }


    @GetMapping("/find/findAssetsUsePercentage")
    @ApiOperation(value = "查询学校资产使用百分比", notes = "返回响应对象,不包含总条数", response=RepairNew.class)
    public ResponseJson findAssetsUsePercentage(){
        AssetsStockDetail assetsStockDetail = new AssetsStockDetail();
        assetsStockDetail.setSchoolId(mySchoolId());
        String data= assetsStockDetailService.findAssetsUsePercentage(assetsStockDetail);
        return new ResponseJson(data);
    }

    @GetMapping("/find/findRecentOneYearAssertsCount")
    @ApiOperation(value = "查询一年的资产信息", notes = "返回响应对象,不包含总条数", response=RepairNew.class)
    public ResponseJson findRecentOneYearAssertsCount(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AssetsStockDetail assetsStockDetai){
        AssetsStockDetail assetsStockDetail = new AssetsStockDetail();
        assetsStockDetail.setSchoolId(mySchoolId());
        List<AssetsStockDetail> data= assetsStockDetailService.findRecentOneYearAssertsCount(assetsStockDetail);
        return new ResponseJson(data);
    }


    @GetMapping("/find/findRecentOneMonthAssertsCount")
    @ApiOperation(value = "查近一个月的资产信息", notes = "返回响应对象,不包含总条数", response=RepairNew.class)
    public ResponseJson findRecentOneMonthAssertsCount(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AssetsStockDetail assetsStockDetai){
        AssetsStockDetail assetsStockDetail = new AssetsStockDetail();
        assetsStockDetail.setSchoolId(mySchoolId());
        List<AssetsStockDetail> data= assetsStockDetailService.findRecentOneMonthAssertsCount(assetsStockDetail);
        return new ResponseJson(data);
    }

    @GetMapping("/find/findFileTotalPrice")
    @ApiOperation(value = "查询学校的档案总价", notes = "返回响应对象,不包含总条数", response=RepairNew.class)
    public ResponseJson findFileTotalPrice(){
        AssetsStockDetail assetsStockDetail = new AssetsStockDetail();
        assetsStockDetail.setSchoolId(mySchoolId());
        List<AssetsFile> data= assetsStockDetailService.findFileTotalPrice(assetsStockDetail);
        return new ResponseJson(data);
    }

}
