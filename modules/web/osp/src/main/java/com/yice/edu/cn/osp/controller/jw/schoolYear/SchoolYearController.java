package com.yice.edu.cn.osp.controller.jw.schoolYear;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.jw.schoolYear.SchoolYear;
import com.yice.edu.cn.osp.service.jw.schoolYear.SchoolYearService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/schoolYear")
@Api(value = "/schoolYear",description = "学年模块")
public class SchoolYearController {
    @Autowired
    private SchoolYearService schoolYearService;

    @GetMapping("/findCurSchoolYear")
    @ApiOperation(value = "根据学校id获取当前的学年学期", notes = "返回一个对象，两个字段，schoolYearId和term")
    public CurSchoolYear findCurSchoolYear(){
        return schoolYearService.findCurSchoolYear(mySchoolId());
    }
    @GetMapping("/findSchoolYears")
    @ApiOperation(value = "根据学校id获取学年列表,给前端页面显示学年下拉框用的额", notes = "返回一个对象，两个字段，id和fromTo")
    public List<SchoolYear> findSchoolYears(){
        return schoolYearService.findSchoolYears(mySchoolId());
    }


    @GetMapping("/findSchoolYearById/{id}")
    @ApiModelProperty(value = "根据学年Id获取单条记录",notes = "返回学年对象")
    public ResponseJson findSchoolYearById(@PathVariable("id")String id){
        SchoolYear schoolYear = schoolYearService.findSchoolYearById(id);
        return new ResponseJson(schoolYear);
    }


}
