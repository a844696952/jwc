package com.yice.edu.cn.tap.controller.xwDormManage.houseApplican;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.HouseApplicanFiles;
import com.yice.edu.cn.tap.service.xwDormManage.houseApplican.HouseApplicanFilesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/houseApplicanFiles")
@Api(value = "/houseApplicanFiles",description = "模块")
public class HouseApplicanFilesController {
    @Autowired
    private HouseApplicanFilesService houseApplicanFilesService;

    @PostMapping("/saveHouseApplicanFiles")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response= HouseApplicanFiles.class)
    public ResponseJson saveHouseApplicanFiles(
            @ApiParam(value = "对象", required = true)
            @RequestBody HouseApplicanFiles houseApplicanFiles){
       houseApplicanFiles.setSchoolId(mySchoolId());
        HouseApplicanFiles s=houseApplicanFilesService.saveHouseApplicanFiles(houseApplicanFiles);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findHouseApplicanFilesById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=HouseApplicanFiles.class)
    public ResponseJson findHouseApplicanFilesById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        HouseApplicanFiles houseApplicanFiles=houseApplicanFilesService.findHouseApplicanFilesById(id);
        return new ResponseJson(houseApplicanFiles);
    }

    @PostMapping("/update/updateHouseApplicanFiles")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateHouseApplicanFiles(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody HouseApplicanFiles houseApplicanFiles){
        houseApplicanFilesService.updateHouseApplicanFiles(houseApplicanFiles);
        return new ResponseJson();
    }

    @GetMapping("/look/lookHouseApplicanFilesById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=HouseApplicanFiles.class)
    public ResponseJson lookHouseApplicanFilesById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        HouseApplicanFiles houseApplicanFiles=houseApplicanFilesService.findHouseApplicanFilesById(id);
        return new ResponseJson(houseApplicanFiles);
    }

    @PostMapping("/findHouseApplicanFilessByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=HouseApplicanFiles.class)
    public ResponseJson findHouseApplicanFilessByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody HouseApplicanFiles houseApplicanFiles){
       houseApplicanFiles.setSchoolId(mySchoolId());
        List<HouseApplicanFiles> data=houseApplicanFilesService.findHouseApplicanFilesListByCondition(houseApplicanFiles);
        long count=houseApplicanFilesService.findHouseApplicanFilesCountByCondition(houseApplicanFiles);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneHouseApplicanFilesByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=HouseApplicanFiles.class)
    public ResponseJson findOneHouseApplicanFilesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody HouseApplicanFiles houseApplicanFiles){
        HouseApplicanFiles one=houseApplicanFilesService.findOneHouseApplicanFilesByCondition(houseApplicanFiles);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteHouseApplicanFiles/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteHouseApplicanFiles(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        houseApplicanFilesService.deleteHouseApplicanFiles(id);
        return new ResponseJson();
    }


    @PostMapping("/findHouseApplicanFilesListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=HouseApplicanFiles.class)
    public ResponseJson findHouseApplicanFilesListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody HouseApplicanFiles houseApplicanFiles){
       houseApplicanFiles.setSchoolId(mySchoolId());
        List<HouseApplicanFiles> data=houseApplicanFilesService.findHouseApplicanFilesListByCondition(houseApplicanFiles);
        return new ResponseJson(data);
    }



}
