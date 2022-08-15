package com.yice.edu.cn.osp.controller.jw.shortcut;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.api.thirdParty.ApplySchool;
import com.yice.edu.cn.osp.service.jw.shortcut.ApplySchoolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;


@RestController
@RequestMapping("/applySchool")
@Api(value = "/applySchool", description = "绑定到学校的第三方应用")
public class ApplySchoolController {
    @Autowired
    private ApplySchoolService applySchoolService;


    @PostMapping("/findApplySchoolsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response = ApplySchool.class)
    public ResponseJson findApplySchoolsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ApplySchool applySchool) {
        applySchool.setSchoolId(mySchoolId());
        applySchool.setPager(null);
        List<ApplySchool> data = applySchoolService.findApplySchoolListByCondition(applySchool);
        long count = applySchoolService.findApplySchoolCountByCondition(applySchool);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneApplySchoolByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response = ApplySchool.class)
    public ResponseJson findOneApplySchoolByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody ApplySchool applySchool) {
        applySchool.setSchoolId(mySchoolId());
        ApplySchool one = applySchoolService.findOneApplySchoolByCondition(applySchool);
        return new ResponseJson(one);
    }

    @PostMapping("/findApplySchoolListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response = ApplySchool.class)
    public ResponseJson findApplySchoolListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ApplySchool applySchool) {
        applySchool.setSchoolId(mySchoolId());
        List<ApplySchool> data = applySchoolService.findApplySchoolListByCondition(applySchool);
        return new ResponseJson(data);
    }


}
