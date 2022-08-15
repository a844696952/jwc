package com.yice.edu.cn.osp.controller.xw.workerKq;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.workerKq.KqDailyStatusDetail;
import com.yice.edu.cn.osp.service.xw.workerKq.KqDailyStatusDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;


@RestController
@RequestMapping("/kqDailyStatusDetail")
@Api(value = "/kqDailyStatusDetail", description = "模块")
public class KqDailyStatusDetailController {
    @Autowired
    private KqDailyStatusDetailService kqDailyStatusDetailService;

    @PostMapping("/saveKqDailyStatusDetail")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response = KqDailyStatusDetail.class)
    public ResponseJson saveKqDailyStatusDetail(
            @ApiParam(value = "对象", required = true)
            @RequestBody KqDailyStatusDetail kqDailyStatusDetail) {
        KqDailyStatusDetail s = kqDailyStatusDetailService.saveKqDailyStatusDetail(kqDailyStatusDetail);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findKqDailyStatusDetailById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response = KqDailyStatusDetail.class)
    public ResponseJson findKqDailyStatusDetailById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        KqDailyStatusDetail kqDailyStatusDetail = kqDailyStatusDetailService.findKqDailyStatusDetailById(id);
        return new ResponseJson(kqDailyStatusDetail);
    }

    @PostMapping("/update/updateKqDailyStatusDetail")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateKqDailyStatusDetail(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody KqDailyStatusDetail kqDailyStatusDetail) {
        kqDailyStatusDetailService.updateKqDailyStatusDetail(kqDailyStatusDetail);
        return new ResponseJson();
    }

    @GetMapping("/look/lookKqDailyStatusDetailById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response = KqDailyStatusDetail.class)
    public ResponseJson lookKqDailyStatusDetailById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        KqDailyStatusDetail kqDailyStatusDetail = kqDailyStatusDetailService.findKqDailyStatusDetailById(id);
        return new ResponseJson(kqDailyStatusDetail);
    }

    @PostMapping("/findKqDailyStatusDetailsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response = KqDailyStatusDetail.class)
    public ResponseJson findKqDailyStatusDetailsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqDailyStatusDetail kqDailyStatusDetail) {
        List<KqDailyStatusDetail> data = kqDailyStatusDetailService.findKqDailyStatusDetailListByCondition(kqDailyStatusDetail);
        long count = kqDailyStatusDetailService.findKqDailyStatusDetailCountByCondition(kqDailyStatusDetail);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneKqDailyStatusDetailByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response = KqDailyStatusDetail.class)
    public ResponseJson findOneKqDailyStatusDetailByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody KqDailyStatusDetail kqDailyStatusDetail) {
        KqDailyStatusDetail one = kqDailyStatusDetailService.findOneKqDailyStatusDetailByCondition(kqDailyStatusDetail);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteKqDailyStatusDetail/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteKqDailyStatusDetail(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        kqDailyStatusDetailService.deleteKqDailyStatusDetail(id);
        return new ResponseJson();
    }


    @PostMapping("/findKqDailyStatusDetailListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response = KqDailyStatusDetail.class)
    public ResponseJson findKqDailyStatusDetailListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqDailyStatusDetail kqDailyStatusDetail) {
        List<KqDailyStatusDetail> data = kqDailyStatusDetailService.findKqDailyStatusDetailListByCondition(kqDailyStatusDetail);
        return new ResponseJson(data);
    }

}
