package com.yice.edu.cn.osp.controller.wb.latticePager;


import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.wb.latticePager.LatticePager;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.osp.service.wb.latticePager.LatticePagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/latticePager")
@Api(value = "/latticePager",description = "点阵试卷模块")
public class LatticePagerController {


    @Autowired
    private LatticePagerService latticePagerService;
    @Autowired
    private CurSchoolYearService curSchoolYearService;



    @PostMapping("/findLatticePagersByCondition")
    @ApiOperation(value = "根据条件查找点阵试卷表", notes = "返回响应对象", response= LatticePager.class)
    public ResponseJson findLatticePagersByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody LatticePager latticePager){
        latticePager.setSchoolId(mySchoolId());
        latticePager.setTeacherId(myId());
        curSchoolYearService.setSchoolYearId(latticePager,mySchoolId());
        List<LatticePager> data=latticePagerService.findLatticePagerListByCondition(latticePager);
        long count=latticePagerService.findLatticePagerCountByCondition(latticePager);
        return new ResponseJson(data,count);
    }

    @PostMapping("/analysisPdf")
    @ApiOperation(value = "附件上传文件", notes = "返回状态和资源的url")
    public ResponseJson analysisPdf(@ApiParam(value = "上传的附件", required = true) MultipartFile file){
        try {
            if(!Optional.ofNullable(file).isPresent()){
                return new ResponseJson(false,"文件必传");
            }
            return latticePagerService.analysisPdf(file);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseJson(false,"系统繁忙，请稍后再试");
        }
    }

    /**
     * 后来上传不使用图片，使用pdf
     * */
    @PostMapping("/upload")
    @ApiOperation(value = "附件上传文件", notes = "返回状态和资源的url")
    public ResponseJson upload(@ApiParam(value = "上传的附件", required = true) MultipartFile file){
        Map<String, String> map = new HashMap<>(16);
        //文件名后缀
        String suffix = file.getOriginalFilename().substring(Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".") + 1);
        //图片后缀数组
        String[] imgeArray = {"jpg", "png", "gif", "bmp","jpeg"};
        if(!ArrayUtils.contains(imgeArray,suffix)){
            return new ResponseJson(false,"不支持的文件格式");
        }
        map.put("fileName", file.getOriginalFilename());
        map.put("filePath", QiniuUtil.commonUploadFile(file, Constant.Upload.WB_LATTICE_PAGER_PAGES + suffix));
        return new ResponseJson(map);
    }





    @PostMapping("/saveOrUpdateLatticePager")
    @ApiOperation(value = "保存点阵试卷对象", notes = "返回点阵试卷表对象", response= LatticePager.class)
    public ResponseJson saveOrUpdateLatticePager(
            @ApiParam(value = "点阵试卷表对象", required = true)
            @RequestBody LatticePager latticePager){
        latticePager.setSchoolId(mySchoolId());
        latticePager.setTeacherId(myId());
        curSchoolYearService.setSchoolYearTerm(latticePager,mySchoolId());
        LatticePager s = latticePagerService.saveOrUpdateLatticePager(latticePager);
        return new ResponseJson(s);
    }

    @GetMapping("/deleteLatticePager/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteLatticePager(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        latticePagerService.deleteLatticePager(id);
        return new ResponseJson();
    }



    @GetMapping("/look/lookLatticePagerById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找点阵试卷管理", notes = "返回响应对象", response=LatticePager.class)
    public ResponseJson lookLatticePagerById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        LatticePager latticePager=latticePagerService.lookLatticePagerById(id);
        return new ResponseJson(latticePager);
    }


}
