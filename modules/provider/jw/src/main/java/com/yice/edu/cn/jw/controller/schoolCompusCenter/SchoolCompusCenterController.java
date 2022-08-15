package com.yice.edu.cn.jw.controller.schoolCompusCenter;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.schoolCompusCenter.SchoolCompusCenter;
import com.yice.edu.cn.jw.service.schoolCompusCenter.SchoolCompusCenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schoolCompusCenter")
@Api(value = "/schoolCompusCenter",description = "模块")
public class SchoolCompusCenterController {
    @Autowired
    private SchoolCompusCenterService schoolCompusCenterService;

    @GetMapping("/findSchoolCompusCenterById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public SchoolCompusCenter findSchoolCompusCenterById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return schoolCompusCenterService.findSchoolCompusCenterById(id);
    }

    @PostMapping("/saveSchoolCompusCenter")
    @ApiOperation(value = "保存", notes = "返回对象")
    public SchoolCompusCenter saveSchoolCompusCenter(
            @ApiParam(value = "对象", required = true)
            @RequestBody SchoolCompusCenter schoolCompusCenter){
        schoolCompusCenterService.saveSchoolCompusCenter(schoolCompusCenter);
        return schoolCompusCenter;
    }

    @PostMapping("/findSchoolCompusCenterListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<SchoolCompusCenter> findSchoolCompusCenterListByCondition(
            @ApiParam(value = "对象")
            @RequestBody SchoolCompusCenter schoolCompusCenter){
        return schoolCompusCenterService.findSchoolCompusCenterListByCondition(schoolCompusCenter);
    }
    @PostMapping("/findSchoolCompusCenterCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findSchoolCompusCenterCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody SchoolCompusCenter schoolCompusCenter){
        return schoolCompusCenterService.findSchoolCompusCenterCountByCondition(schoolCompusCenter);
    }

    @PostMapping("/updateSchoolCompusCenter")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateSchoolCompusCenter(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody SchoolCompusCenter schoolCompusCenter){
        schoolCompusCenterService.updateSchoolCompusCenter(schoolCompusCenter);
    }

    @GetMapping("/deleteSchoolCompusCenter/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteSchoolCompusCenter(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        schoolCompusCenterService.deleteSchoolCompusCenter(id);
    }
    @PostMapping("/deleteSchoolCompusCenterByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteSchoolCompusCenterByCondition(
            @ApiParam(value = "对象")
            @RequestBody SchoolCompusCenter schoolCompusCenter){
        schoolCompusCenterService.deleteSchoolCompusCenterByCondition(schoolCompusCenter);
    }
    @PostMapping("/findOneSchoolCompusCenterByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public SchoolCompusCenter findOneSchoolCompusCenterByCondition(
            @ApiParam(value = "对象")
            @RequestBody SchoolCompusCenter schoolCompusCenter){
        return schoolCompusCenterService.findOneSchoolCompusCenterByCondition(schoolCompusCenter);
    }

    @GetMapping("/findSchoolCompusCenter/{schoolId}")
    public ResponseJson findSchoolCompusCenter(
            @PathVariable("schoolId") String schoolId){
        return schoolCompusCenterService.findSchoolCompusCenter(schoolId);
    }

    @PostMapping("/saveSchoolCompusCenterData")
    public void saveSchoolCompusCenterData(
            @RequestBody
            SchoolCompusCenter schoolCompusCenter
    ){
        schoolCompusCenterService.saveSchoolCompusCenterData(schoolCompusCenter);
    }
}
