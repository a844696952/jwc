package com.yice.edu.cn.osp.controller.dm.schoolActive;

import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.annotation.EccJpush;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.schoolActive.DmActivityInfo;
import com.yice.edu.cn.common.pojo.dm.schoolActive.ExportActiveByClass;
import com.yice.edu.cn.common.pojo.dm.schoolActive.ExportActiveByItem;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.util.FileTypeUtil;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.osp.service.dm.schoolActive.DmActivityInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dmActivityInfo")
@Api(value = "/dmActivityInfo",description = "活动信息表模块")
@Slf4j
public class DmActivityInfoController {
    @Autowired
    private DmActivityInfoService dmActivityInfoService;

    @Autowired
    private FileTypeUtil fileTypeUtil;

    @PostMapping("/saveDmActivityInfo")
    @ApiOperation(value = "保存活动信息表对象", notes = "返回保存好的活动信息表对象", response= DmActivityInfo.class)
    @EccJpush(type = Extras.DM_ECC_ACTIVITY,content = "新增校园活动")
    public ResponseJson saveDmActivityInfo(
            @ApiParam(value = "活动信息表对象", required = true)
            @RequestBody DmActivityInfo dmActivityInfo){
        dmActivityInfo.setSchoolId(mySchoolId());
        dmActivityInfoService.saveDmActivityInfo(dmActivityInfo);
        return new ResponseJson();
    }

    @GetMapping("/update/findDmActivityInfoByActivityId/{activityId}")
    @ApiOperation(value = "去更新页面,通过activityId查找活动信息表", notes = "返回响应对象", response=DmActivityInfo.class)
    public ResponseJson findDmActivityInfoById(
            @ApiParam(value = "去更新页面,需要用到的activityId", required = true)
            @PathVariable String activityId){
        DmActivityInfo dmActivityInfo=dmActivityInfoService.findDmActivityInfoByActivityId(activityId);
        return new ResponseJson(dmActivityInfo);
    }

    @PostMapping("/update/updateDmActivityInfo")
    @ApiOperation(value = "修改活动信息表对象", notes = "返回响应对象")
    @EccJpush(type = Extras.DM_ECC_ACTIVITY,content = "修改校园活动")
    public ResponseJson updateDmActivityInfo(
            @ApiParam(value = "被修改的活动信息表对象,对象属性不为空则修改", required = true)
            @RequestBody DmActivityInfo dmActivityInfo){
        dmActivityInfoService.updateDmActivityInfo(dmActivityInfo);
        return new ResponseJson();
    }

    @GetMapping("/look/lookDmActivityInfoById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找活动信息表", notes = "返回响应对象", response=DmActivityInfo.class)
    public ResponseJson lookDmActivityInfoById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        DmActivityInfo dmActivityInfo=dmActivityInfoService.findDmActivityInfoById(id);
        return new ResponseJson(dmActivityInfo);
    }

    @PostMapping("/findDmActivityInfosByCondition")
    @ApiOperation(value = "根据条件查找活动信息表", notes = "返回响应对象", response=DmActivityInfo.class)
    public ResponseJson findDmActivityInfosByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmActivityInfo dmActivityInfo){
        dmActivityInfo.setSchoolId(mySchoolId());
        List<DmActivityInfo> data=dmActivityInfoService.findDmActivityInfoListByCondition(dmActivityInfo);
        long count=dmActivityInfoService.findDmActivityInfoCountByCondition(dmActivityInfo);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDmActivityInfoByCondition")
    @ApiOperation(value = "根据条件查找单个活动信息表,结果必须为单条数据", notes = "没有时返回空", response=DmActivityInfo.class)
    public ResponseJson findOneDmActivityInfoByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmActivityInfo dmActivityInfo){
        DmActivityInfo one=dmActivityInfoService.findOneDmActivityInfoByCondition(dmActivityInfo);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteDmActivityInfo/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDmActivityInfo(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        dmActivityInfoService.deleteDmActivityInfo(id);
        return new ResponseJson();
    }


    @PostMapping("/findDmActivityInfoListByCondition")
    @ApiOperation(value = "根据条件查找活动信息表列表", notes = "返回响应对象,不包含总条数", response=DmActivityInfo.class)
    public ResponseJson findDmActivityInfoListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmActivityInfo dmActivityInfo){
        dmActivityInfo.setSchoolId(mySchoolId());
        List<DmActivityInfo> data=dmActivityInfoService.findDmActivityInfoListByCondition(dmActivityInfo);
        return new ResponseJson(data);
    }

    @GetMapping("/findGradesCurrentSchool")
    @ApiOperation(value = "查找当前学校包含的年级", notes = "返回响应对象")
    public ResponseJson findGradesCurrentSchool(){
        List<Dd> data=dmActivityInfoService.findDdListBySchoolId(mySchoolId());
        return new ResponseJson(data);
    }

    @PostMapping("/checkGradeSingUp")
    @ApiOperation(value = "检查该活动当前年级是否有人报名", notes = "返回响应对象")
    public ResponseJson checkGradeSingUp(
            @RequestBody DmActivityInfo dmActivityInfo
    ){
        boolean flag = dmActivityInfoService.checkGradeSingUp(dmActivityInfo);
        return new ResponseJson(flag);
    }

    @GetMapping("/checkItemSingUp/{itemId}")
    @ApiOperation(value = "检查该活动当前项目是否有人报名", notes = "返回响应对象")
    public ResponseJson checkItemSingUp(
            @PathVariable String itemId
    ){
        Map map = dmActivityInfoService.checkItemSingUp(itemId);
        return new ResponseJson(map);
    }

    @PostMapping("/ExportActivityByClassId")
    @ApiOperation(value = "导出活动模板根据班级分类", notes = "返回响应对象")
    public void ExportActivityByClassId(@RequestBody DmActivityInfo dmActivityInfo, HttpServletResponse response){
        dmActivityInfo.setSchoolId(mySchoolId());
        Workbook workbook = dmActivityInfoService.findClassItemByExportActiveId(dmActivityInfo);
        response.setHeader("Content-Disposition", "attachment;filename=Excel.xls");
        response.setCharacterEncoding("utf-8");
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
        } catch (IOException e) {
            log.error(""+e);
        }
    }

    @PostMapping("/ExportActivityByItem")
    @ApiOperation(value = "导出活动模板根据项目分类", notes = "返回响应对象")
    public void ExportActivityByItem(@RequestBody DmActivityInfo dmActivityInfo,HttpServletResponse response){
        dmActivityInfo.setSchoolId(mySchoolId());
        Workbook workbook = dmActivityInfoService.findItemByExportActiveId(dmActivityInfo);
        response.setHeader("Content-Disposition", "attachment;filename=Excel.xls");
        response.setCharacterEncoding("utf-8");
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
        } catch (IOException e) {
            log.error(""+e);
        }
    }

    @PostMapping("/getDetailByClass")
    @ApiOperation(value = "根据班级分类查询详情", notes = "返回响应对象")
    public ResponseJson getDetailByClass(@RequestBody DmActivityInfo dmActivityInfo){
        dmActivityInfo.setSchoolId(mySchoolId());
        ExportActiveByClass classItemByActiveId = dmActivityInfoService.findClassItemByActiveId(dmActivityInfo);
        if(Objects.nonNull(classItemByActiveId)){
            return new ResponseJson(true,classItemByActiveId);
        }
        return new ResponseJson(false);

    }


    @PostMapping("/getDetailByItem")
    @ApiOperation(value = "根据项目分类查询详情", notes = "返回响应对象")
    public ResponseJson getDetailByItem(@RequestBody DmActivityInfo dmActivityInfo){
        dmActivityInfo.setSchoolId(mySchoolId());
        ExportActiveByItem itemByActiveId = dmActivityInfoService.findItemByActiveId(dmActivityInfo);
        if(Objects.nonNull(itemByActiveId)){
            return new ResponseJson(true,itemByActiveId);
        }
        return new ResponseJson(false);

    }



    @PostMapping("/upload")
    @ApiOperation(value = "上传文件", notes = "返回状态和资源的url")
    public ResponseJson upload(@ApiParam(value = "上传的文件", required = true) MultipartFile file) {
        Map<String, Object> map = new HashMap<>(3);
        //文件名后缀
        String originalFilename = file.getOriginalFilename();
        String suffix = StrUtil.subAfter(originalFilename, ".", true);
        int suffixInt = fileTypeUtil.setResouceType(suffix);
        if(suffixInt!=2) {
            return new ResponseJson(false,"只能上传图片");
        }
        map.put("fileName", StrUtil.subBefore(originalFilename, ".", true));
        map.put("fileType", suffixInt);
        map.put("filePath", QiniuUtil.commonUploadFile(file, Constant.Upload.UPLOAD_SCHOOL_ACTIVE));
        return new ResponseJson(map);
    }





}
