package com.yice.edu.cn.yed.controller.jw.apk;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.classCard.Apk;
import com.yice.edu.cn.common.util.FileTypeUtil;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.yed.service.jw.apk.ApkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


@RestController
@RequestMapping("/apk")
@Api(value = "/apk",description = "apk管理模块")
public class ApkController {
    @Autowired
    private ApkService apkService;
    @Autowired
    private FileTypeUtil fileTypeUtil;

    final Pattern pattern=Pattern.compile("^-?\\d+(\\.\\d+)?$");

    @PostMapping("/saveApk")
    @ApiOperation(value = "保存apk管理对象", notes = "返回保存好的apk管理对象", response=Apk.class)
    public ResponseJson saveApk(
            @ApiParam(value = "apk管理对象", required = true)
            @RequestBody Apk apk){
        if(StringUtils.isNotEmpty(apk.getVersion())){
           if(pattern.matcher(apk.getVersion()).matches()){
               Apk s=apkService.saveApk(apk);
               return new ResponseJson(s);
           }
        }
        return new ResponseJson(false,"version版本号错误");
    }

    @GetMapping("/update/findApkById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找apk管理", notes = "返回响应对象", response=Apk.class)
    public ResponseJson findApkById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        Apk apk=apkService.findApkById(id);
        return new ResponseJson(apk);
    }

    @PostMapping("/update/updateApk")
    @ApiOperation(value = "修改apk管理对象", notes = "返回响应对象")
    public ResponseJson updateApk(
            @ApiParam(value = "被修改的apk管理对象,对象属性不为空则修改", required = true)
            @RequestBody Apk apk){
        apkService.updateApk(apk);
        return new ResponseJson();
    }

    @GetMapping("/look/lookApkById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找apk管理", notes = "返回响应对象", response=Apk.class)
    public ResponseJson lookApkById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        Apk apk=apkService.findApkById(id);
        return new ResponseJson(apk);
    }

    @PostMapping("/findApksByCondition")
    @ApiOperation(value = "根据条件查找apk管理", notes = "返回响应对象", response=Apk.class)
    public ResponseJson findApksByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Apk apk){
        //apk.setSchoolId(mySchoolId());
        List<Apk> data=apkService.findApkListByCondition(apk);
        long count=apkService.findApkCountByCondition(apk);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneApkByCondition")
    @ApiOperation(value = "根据条件查找单个apk管理,结果必须为单条数据", notes = "没有时返回空", response=Apk.class)
    public ResponseJson findOneApkByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Apk apk){
        Apk one=apkService.findOneApkByCondition(apk);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteApk/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteApk(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        apkService.deleteApk(id);
        return new ResponseJson();
    }


    @PostMapping("/findApkListByCondition")
    @ApiOperation(value = "根据条件查找apk管理列表", notes = "返回响应对象,不包含总条数", response=Apk.class)
    public ResponseJson findApkListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Apk apk){
       // apk.setSchoolId(mySchoolId());
        List<Apk> data=apkService.findApkListByCondition(apk);
        return new ResponseJson(data);
    }

    @PostMapping("/uploadQiniuFile")
    @ApiOperation(value = "创建时间：2018-10-29。说明：上传文件到七牛", notes = "返回资源名称和资源的url")
    public ResponseJson uploadQiniuFile(@ApiParam(value = "上传到七牛的文件file", required = true) MultipartFile file) {
        //文件名后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        int suffixInt = fileTypeUtil.setResouceType(suffix);
        if (suffixInt != 5) {
            return new ResponseJson(false, "不支持的文件格式,请上传apk格式");
        }
        //不包含文件后缀名
        ///String fileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."));
        Map<String, String> map = new HashMap<>();
        map.put("name", file.getOriginalFilename());
        //获取保存文件路径
        String url = QiniuUtil.commonUploadFile(file, Constant.Upload.UPLOAD_APK + suffix);
        map.put("url", url);
        return new ResponseJson(map);
    }

    @PostMapping("/selectApksList")
    @ApiOperation(value = "根据条件查找apk管理", notes = "返回响应对象", response=Apk.class)
    public ResponseJson selectApksList(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Apk apk){
       // apk.setSchoolId(mySchoolId());
        apk.setPager(null);
        List<Apk> data=apkService.findApkListByCondition(apk);
        return new ResponseJson(data);
    }


}
