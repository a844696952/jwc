package com.yice.edu.cn.jw.controller.pen;

import com.yice.edu.cn.common.pojo.jw.pen.Pen;
import com.yice.edu.cn.jw.service.pen.PenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pen")
@Api(value = "/pen",description = "钢笔模块")
public class PenController {
    @Autowired
    private PenService penService;

    @GetMapping("/findPenById/{id}")
    @ApiOperation(value = "通过id查找钢笔", notes = "返回钢笔对象")
    public Pen findPenById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return penService.findPenById(id);
    }

    @PostMapping("/savePen")
    @ApiOperation(value = "保存钢笔", notes = "返回钢笔对象")
    public Pen savePen(
            @ApiParam(value = "钢笔对象", required = true)
            @RequestBody Pen pen){
        penService.savePen(pen);
        return pen;
    }

    @PostMapping("/findPenListByCondition")
    @ApiOperation(value = "根据条件查找钢笔列表", notes = "返回钢笔列表")
    public List<Pen> findPenListByCondition(
            @ApiParam(value = "钢笔对象")
            @RequestBody Pen pen){
        return penService.findPenListByCondition(pen);
    }
    @PostMapping("/findPenCountByCondition")
    @ApiOperation(value = "根据条件查找钢笔列表个数", notes = "返回钢笔总个数")
    public long findPenCountByCondition(
            @ApiParam(value = "钢笔对象")
            @RequestBody Pen pen){
        return penService.findPenCountByCondition(pen);
    }

    @PostMapping("/updatePen")
    @ApiOperation(value = "修改钢笔", notes = "钢笔对象必传")
    public void updatePen(
            @ApiParam(value = "钢笔对象,对象属性不为空则修改", required = true)
            @RequestBody Pen pen){
        penService.updatePen(pen);
    }

    @GetMapping("/deletePen/{id}")
    @ApiOperation(value = "通过id删除钢笔")
    public void deletePen(
            @ApiParam(value = "钢笔对象", required = true)
            @PathVariable String id){
        penService.deletePen(id);
    }
    @PostMapping("/deletePenByCondition")
    @ApiOperation(value = "根据条件删除钢笔")
    public void deletePenByCondition(
            @ApiParam(value = "钢笔对象")
            @RequestBody Pen pen){
        penService.deletePenByCondition(pen);
    }
    @PostMapping("/findOnePenByCondition")
    @ApiOperation(value = "根据条件查找单个钢笔,结果必须为单条数据", notes = "返回单个钢笔,没有时为空")
    public Pen findOnePenByCondition(
            @ApiParam(value = "钢笔对象")
            @RequestBody Pen pen){
        return penService.findOnePenByCondition(pen);
    }
}
