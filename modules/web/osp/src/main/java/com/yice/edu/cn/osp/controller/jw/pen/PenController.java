package com.yice.edu.cn.osp.controller.jw.pen;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.pen.Pen;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.common.pojo.validateClass.GroupTwo;
import com.yice.edu.cn.osp.service.jw.pen.PenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.yice.edu.cn.common.service.CurSchoolYearService;

import javax.validation.Valid;
import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/pen")
@Api(value = "/pen",description = "钢笔模块")
public class PenController {
    @Autowired
    private PenService penService;
    @Autowired
    private CurSchoolYearService curSchoolService;
    @PostMapping("/savePen")
    @ApiOperation(value = "保存钢笔对象", notes = "返回保存好的钢笔对象", response= Pen.class)
    public ResponseJson savePen(
            @ApiParam(value = "钢笔对象", required = true)
            @RequestBody Pen pen){
       pen.setSchoolId(mySchoolId());
      curSchoolService.setSchoolYearTerm(pen,mySchoolId());

        Pen s=penService.savePen(pen);
        return new ResponseJson(s);
    }


    @PostMapping("/updatePen")
    @ApiOperation(value = "修改钢笔对象", notes = "返回响应对象")
    public ResponseJson updatePen(
            @ApiParam(value = "被修改的钢笔对象,对象属性不为空则修改", required = true)
            @RequestBody Pen pen){
        penService.updatePen(pen);
        return new ResponseJson();
    }

    @GetMapping("/findPenById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找钢笔", notes = "返回响应对象", response=Pen.class)
    public ResponseJson findPenById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        Pen pen=penService.findPenById(id);
        return new ResponseJson(pen);
    }

    @GetMapping("/deletePen/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deletePen(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        penService.deletePen(id);
        return new ResponseJson();
    }


    @PostMapping("/findPenListByCondition")
    @ApiOperation(value = "根据条件查找钢笔列表", notes = "返回响应对象,不包含总条数", response=Pen.class)
    public ResponseJson findPenListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Pen pen){
       pen.setSchoolId(mySchoolId());
      curSchoolService.setSchoolYearId(pen,mySchoolId());
        List<Pen> data=penService.findPenListByCondition(pen);
        return new ResponseJson(data);
    }



}
