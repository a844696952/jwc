package com.yice.edu.cn.bmp.controller.visitorManage;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.bmp.service.visitorManage.VisitorService;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.visitorManage.Visitor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.*;


@RestController
@RequestMapping("/visitor")
@Api(value = "/visitor", description = "模块")
public class VisitorController {
    @Autowired
    private VisitorService visitorService;

    //家长端访客申请接口
    @PostMapping("/visitorApply")
    @ApiOperation(value = "请求中移访客接口校验图片并入库)")
    public ResponseJson saveVisitorApplyRecords(
            @ApiParam(value = "访客记录", required = true)
            @RequestBody Visitor visitor) {
        visitor.setSchoolId(mySchoolId());
        ResponseJson responseJson = visitorService.saveVisitorApplyRecords(visitor);
        return responseJson;
    }


    @PostMapping("/saveVisitor")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response = Visitor.class)
    public ResponseJson saveVisitor(
            @ApiParam(value = "对象", required = true)
            @RequestBody Visitor visitor) {
        visitor.setSchoolId(mySchoolId());
        Visitor s = visitorService.saveVisitor(visitor);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findVisitorById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response = Visitor.class)
    public ResponseJson findVisitorById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        Visitor visitor = visitorService.findVisitorById(id);
        return new ResponseJson(visitor);
    }

    @PostMapping("/update/updateVisitor")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateVisitor(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody Visitor visitor) {
        visitorService.updateVisitor(visitor);
        return new ResponseJson();
    }

    @GetMapping("/look/lookVisitorById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response = Visitor.class)
    public ResponseJson lookVisitorById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        Visitor visitor = visitorService.findVisitorById(id);
        return new ResponseJson(visitor);
    }

    @PostMapping("/findVisitorsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response = Visitor.class)
    public ResponseJson findVisitorsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Visitor visitor) {
        Visitor vv=new Visitor();
        vv.setSchoolId(mySchoolId());
        vv.setParentId(myParentId());
        vv.setStudentId(myStudentId());
        vv.setVisitorType("0");
        List<Visitor> data = visitorService.findVisitorListByCondition(vv);
        if (data.size() > 0) {
            for (Visitor v : data) {
                if (DateUtil.parse(DateUtil.now()).isAfterOrEquals(DateUtil.parse(v.getEndTime())) && v.getApplyStatus().equals("2")) {
                    v.setApplyStatus("3");
                    visitorService.updateVisitor(v);
                }
            }
        }
        visitor.getPager().setSortOrder(Pager.DESC);
        visitor.getPager().setSortField("createTime");
        visitor.setSchoolId(mySchoolId());
        visitor.setParentId(myParentId());
        visitor.setStudentId(myStudentId());
        visitor.setVisitorType("0");
        List<Visitor> list = visitorService.findVisitorListByCondition(visitor);
        long count = visitorService.findVisitorCountByCondition(visitor);
        return new ResponseJson(list, count);
    }


    @PostMapping("/findOneVisitorByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response = Visitor.class)
    public ResponseJson findOneVisitorByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Visitor visitor) {
        Visitor one = visitorService.findOneVisitorByCondition(visitor);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteVisitor/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteVisitor(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        visitorService.deleteVisitor(id);
        return new ResponseJson();
    }


    @PostMapping("/findVisitorListByCondition")
    @ApiOperation(value = "根据条件查找所有记录列表", notes = "返回响应对象,不包含总条数", response = Visitor.class)
    public ResponseJson findVisitorListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Visitor visitor) {
        visitor.setSchoolId(mySchoolId());
        List<Visitor> data = visitorService.findVisitorListByCondition(visitor);
        return new ResponseJson(data);
    }

}
