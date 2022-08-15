package com.yice.edu.cn.xw.controller.kq;

import com.yice.edu.cn.common.pojo.xw.kq.KqSchoolInit;
import com.yice.edu.cn.xw.service.kq.KqSchoolInitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kqSchoolInit")
@Api(value = "/kqSchoolInit",description = "学校初始化中移账号表模块")
public class KqSchoolInitController {
    @Autowired
    private KqSchoolInitService kqSchoolInitService;

    @GetMapping("/findKqSchoolInitById/{id}")
    @ApiOperation(value = "通过id查找学校初始化中移账号表", notes = "返回学校初始化中移账号表对象")
    public KqSchoolInit findKqSchoolInitById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return kqSchoolInitService.findKqSchoolInitById(id);
    }

    @PostMapping("/saveKqSchoolInit")
    @ApiOperation(value = "保存学校初始化中移账号表", notes = "返回学校初始化中移账号表对象")
    public KqSchoolInit saveKqSchoolInit(
            @ApiParam(value = "学校初始化中移账号表对象", required = true)
            @RequestBody KqSchoolInit kqSchoolInit){
        kqSchoolInitService.saveKqSchoolInit(kqSchoolInit);
        return kqSchoolInit;
    }

    @PostMapping("/findKqSchoolInitListByCondition")
    @ApiOperation(value = "根据条件查找学校初始化中移账号表列表", notes = "返回学校初始化中移账号表列表")
    public List<KqSchoolInit> findKqSchoolInitListByCondition(
            @ApiParam(value = "学校初始化中移账号表对象")
            @RequestBody KqSchoolInit kqSchoolInit){
        return kqSchoolInitService.findKqSchoolInitListByCondition(kqSchoolInit);
    }
    @PostMapping("/findKqSchoolInitCountByCondition")
    @ApiOperation(value = "根据条件查找学校初始化中移账号表列表个数", notes = "返回学校初始化中移账号表总个数")
    public long findKqSchoolInitCountByCondition(
            @ApiParam(value = "学校初始化中移账号表对象")
            @RequestBody KqSchoolInit kqSchoolInit){
        return kqSchoolInitService.findKqSchoolInitCountByCondition(kqSchoolInit);
    }

    @PostMapping("/updateKqSchoolInit")
    @ApiOperation(value = "修改学校初始化中移账号表", notes = "学校初始化中移账号表对象必传")
    public void updateKqSchoolInit(
            @ApiParam(value = "学校初始化中移账号表对象,对象属性不为空则修改", required = true)
            @RequestBody KqSchoolInit kqSchoolInit){
        kqSchoolInitService.updateKqSchoolInit(kqSchoolInit);
    }

    @GetMapping("/deleteKqSchoolInit/{id}")
    @ApiOperation(value = "通过id删除学校初始化中移账号表")
    public void deleteKqSchoolInit(
            @ApiParam(value = "学校初始化中移账号表对象", required = true)
            @PathVariable String id){
        kqSchoolInitService.deleteKqSchoolInit(id);
    }
    @PostMapping("/deleteKqSchoolInitByCondition")
    @ApiOperation(value = "根据条件删除学校初始化中移账号表")
    public void deleteKqSchoolInitByCondition(
            @ApiParam(value = "学校初始化中移账号表对象")
            @RequestBody KqSchoolInit kqSchoolInit){
        kqSchoolInitService.deleteKqSchoolInitByCondition(kqSchoolInit);
    }
    @PostMapping("/findOneKqSchoolInitByCondition")
    @ApiOperation(value = "根据条件查找单个学校初始化中移账号表,结果必须为单条数据", notes = "返回单个学校初始化中移账号表,没有时为空")
    public KqSchoolInit findOneKqSchoolInitByCondition(
            @ApiParam(value = "学校初始化中移账号表对象")
            @RequestBody KqSchoolInit kqSchoolInit){
        return kqSchoolInitService.findOneKqSchoolInitByCondition(kqSchoolInit);
    }
}
