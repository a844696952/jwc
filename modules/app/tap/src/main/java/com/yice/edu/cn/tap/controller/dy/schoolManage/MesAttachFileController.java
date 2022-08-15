package com.yice.edu.cn.tap.controller.dy.schoolManage;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.mes.schoolManage.mesAttachFile.MesAttachFile;
import com.yice.edu.cn.common.util.FileTypeUtil;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.tap.service.dy.schoolManage.MesAttachFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/mesAttachFile")
@Api(value = "/mesAttachFile",description = "附件表模块")
public class MesAttachFileController {


    private final FileTypeUtil fileTypeUtil;
    @Autowired
    private MesAttachFileService mesAttachFileService;

    @Autowired
    public MesAttachFileController(FileTypeUtil fileTypeUtil) {
        this.fileTypeUtil = fileTypeUtil;
    }

    @PostMapping("/upload")
    @ApiOperation(value = "附件上传文件", notes = "返回状态和资源的url")
    public ResponseJson upload(@ApiParam(value = "上传的附件", required = true) MultipartFile file){
        Map<String, String> map = new HashMap<>(16);
        //文件名后缀
        String suffix = file.getOriginalFilename().substring(Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".") + 1);
        int suffixInt = fileTypeUtil.setResouceType(suffix);
        if(suffixInt==0){
            return new ResponseJson(false,"不支持的文件格式");
        }
        map.put("fileName", file.getOriginalFilename());
        if(suffixInt == 2){
            map.put("fileType","1");
        }else{
            map.put("fileType","2");
        }
        map.put("filePath", QiniuUtil.commonUploadFile(file, Constant.Upload.UPLOAD_DY_FILE + suffix));
        return new ResponseJson(map);
    }

    @PostMapping("/findOneMesAttachFileByCondition")
    @ApiOperation(value = "根据条件查找单个附件表,结果必须为单条数据", notes = "返回单个附件表,没有时为空")
    public ResponseJson findOneMesAttachFileByCondition(
            @ApiParam(value = "附件表对象")
            @RequestBody MesAttachFile mesAttachFile){
        MesAttachFile data = mesAttachFileService.findOneMesAttachFileByCondition(mesAttachFile);
        return new ResponseJson(data);
    }

    @PostMapping("/findMesAttachFileListByCondition")
    @ApiOperation(value = "根据条件查找附件表列表", notes = "返回附件表列表")
    public ResponseJson findMesAttachFileListByCondition(
            @ApiParam(value = "附件表对象")
            @RequestBody MesAttachFile mesAttachFile){
        List<MesAttachFile> data = mesAttachFileService.findMesAttachFileListByCondition(mesAttachFile);
        return new ResponseJson(data);
    }

    @PostMapping("/batchUpload")
    @ApiOperation(value = "批量附件上传文件", notes = "返回状态和资源的url")
    public ResponseJson batchUpload(
            @ApiParam(value = "上传的附件集合", required = true)
            HttpServletRequest request
    ){
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        List<Map<String,String>> list = new ArrayList<>();
        Map<String,String> map;
        for (MultipartFile file : files) {
            //文件名后缀
            String suffix = file.getOriginalFilename().substring(Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".") + 1);
            int suffixInt = fileTypeUtil.setResouceType(suffix);
            if(suffixInt==0){
                return new ResponseJson(false,"其中有不支持的文件格式");
            }
            map = new HashMap<>();
            map.put("fileName", file.getOriginalFilename());
            if(suffixInt == 2){
                map.put("fileType","1");
            }else{
                map.put("fileType","2");
            }
            map.put("filePath", QiniuUtil.commonUploadFile(file, Constant.Upload.UPLOAD_DY_FILE + suffix));
            list.add(map);
        }
        return new ResponseJson(list);
    }
}
