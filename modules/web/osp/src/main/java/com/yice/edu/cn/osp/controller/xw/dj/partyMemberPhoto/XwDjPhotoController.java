package com.yice.edu.cn.osp.controller.xw.dj.partyMemberPhoto;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.dj.partyMerberPhoto.XwDjPhoto;
import com.yice.edu.cn.common.util.FileTypeUtil;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.osp.service.xw.dj.partyMemberPhoto.XwDjPhotoService;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/xwDjPhoto")
@Api(value = "/xwDjPhoto",description = "党建相册表模块")
public class XwDjPhotoController {
      @Autowired
    private XwDjPhotoService xwDjPhotoService;
    @Autowired
    private FileTypeUtil fileTypeUtil;

    @PostMapping("/saveXwDjPhoto")
    @ApiOperation(value = "保存党建相册表对象", notes = "返回保存好的党建相册表对象", response=XwDjPhoto.class)
    public ResponseJson saveXwDjPhoto(
            @ApiParam(value = "党建相册表对象", required = true)
            @RequestBody XwDjPhoto xwDjPhoto){
        xwDjPhoto.setSchoolId(mySchoolId());
        xwDjPhoto.setCreatorId(myId());
        xwDjPhoto.setOperatorId(myId());
        XwDjPhoto s=xwDjPhotoService.saveXwDjPhoto(xwDjPhoto);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findXwDjPhotoById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找党建相册表", notes = "返回响应对象", response=XwDjPhoto.class)
    public ResponseJson findXwDjPhotoById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        XwDjPhoto xwDjPhoto=xwDjPhotoService.findXwDjPhotoById(id);
        return new ResponseJson(xwDjPhoto);
    }

    @PostMapping("/update/updateXwDjPhoto")
    @ApiOperation(value = "修改党建相册表对象", notes = "返回响应对象")
    public ResponseJson updateXwDjPhoto(
            @ApiParam(value = "被修改的党建相册表对象,对象属性不为空则修改", required = true)
            @RequestBody XwDjPhoto xwDjPhoto){
        xwDjPhoto.setOperatorId(myId());
        xwDjPhotoService.updateXwDjPhoto(xwDjPhoto);
        return new ResponseJson();
    }

    @GetMapping("/look/lookXwDjPhotoById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找党建相册表", notes = "返回响应对象", response=XwDjPhoto.class)
    public ResponseJson lookXwDjPhotoById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        XwDjPhoto xwDjPhoto=xwDjPhotoService.findXwDjPhotoById(id);
        return new ResponseJson(xwDjPhoto);
    }

    @PostMapping("/findXwDjPhotosByConditionCommon")
    @ApiOperation(value = "普通用户根据条件查找党建相册列表", notes = "返回响应对象", response=XwDjPhoto.class)
    public ResponseJson findXwDjPhotosByConditionCommon(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwDjPhoto xwDjPhoto){
        xwDjPhoto.setSchoolId(mySchoolId());
        xwDjPhoto.setCreatorId(myId());
        //搜索字段去空格
        if(!StringUtil.isNullOrEmpty(xwDjPhoto.getPhotoTitle())){
            xwDjPhoto.setPhotoTitle(xwDjPhoto.getPhotoTitle().replace(" ",""));
        }
        List<XwDjPhoto> data=xwDjPhotoService.findXwDjPhotoListByCondition(xwDjPhoto);
        long count=xwDjPhotoService.findXwDjPhotoCountByCondition(xwDjPhoto);
        return new ResponseJson(data,count);
    }


    @PostMapping("/findXwDjPhotosByConditionAdmin")
    @ApiOperation(value = "管理员根据条件查找党建相册列表", notes = "返回响应对象", response=XwDjPhoto.class)
    public ResponseJson findXwDjPhotosByConditionAdmin(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwDjPhoto xwDjPhoto){
        xwDjPhoto.setSchoolId(mySchoolId());
        //搜索字段去空格
        if(!StringUtil.isNullOrEmpty(xwDjPhoto.getPhotoTitle())){
            xwDjPhoto.setPhotoTitle(xwDjPhoto.getPhotoTitle().replace(" ",""));
        }
        List<XwDjPhoto> data=xwDjPhotoService.findXwDjPhotoListByCondition(xwDjPhoto);
        long count=xwDjPhotoService.findXwDjPhotoCountByCondition(xwDjPhoto);
        return new ResponseJson(data,count);
    }


    @PostMapping("/findOneXwDjPhotoByCondition")
    @ApiOperation(value = "根据条件查找单个党建相册表,结果必须为单条数据", notes = "没有时返回空", response=XwDjPhoto.class)
    public ResponseJson findOneXwDjPhotoByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody XwDjPhoto xwDjPhoto){
        xwDjPhoto.setSchoolId(mySchoolId());
        XwDjPhoto one=xwDjPhotoService.findOneXwDjPhotoByCondition(xwDjPhoto);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteXwDjPhoto/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteXwDjPhoto(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        xwDjPhotoService.deleteXwDjPhoto(id);
        return new ResponseJson();
    }


    @PostMapping("/findXwDjPhotoListByCondition")
    @ApiOperation(value = "根据条件查找党建相册表列表", notes = "返回响应对象,不包含总条数", response=XwDjPhoto.class)
    public ResponseJson findXwDjPhotoListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwDjPhoto xwDjPhoto){
        xwDjPhoto.setSchoolId(mySchoolId());
        List<XwDjPhoto> data=xwDjPhotoService.findXwDjPhotoListByCondition(xwDjPhoto);
        return new ResponseJson(data);
    }

    @PostMapping("/upload")
    @ApiOperation(value = "党员相册上传文件", notes = "返回状态和资源的url")
    public ResponseJson upload(@ApiParam(value = "上传的文件", required = true) MultipartFile file){
        Map<String, String> map = new HashMap<>(16);
        //文件名后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        int suffixInt = fileTypeUtil.setResouceType(suffix);
        if(suffixInt==0){
            return new ResponseJson(false,"不支持的文件格式");
        }
        map.put("fileName", file.getOriginalFilename());
        map.put("fileType",String.valueOf(suffixInt));
        map.put("filePath",QiniuUtil.commonUploadFile(file, Constant.Upload.UPLOAD_DJ_PARTYMEMBERPHOTO + suffix));
        return new ResponseJson(map);
    }


}
