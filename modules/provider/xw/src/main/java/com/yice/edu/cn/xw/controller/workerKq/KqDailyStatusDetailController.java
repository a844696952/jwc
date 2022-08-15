package com.yice.edu.cn.xw.controller.workerKq;

import com.yice.edu.cn.common.pojo.xw.workerKq.KqDailyStatusDetail;
import com.yice.edu.cn.xw.service.workerKq.KqDailyStatusDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kqDailyStatusDetail")
@Api(value = "/kqDailyStatusDetail", description = "职工考勤请假条模块")
public class KqDailyStatusDetailController {
    @Autowired
    private KqDailyStatusDetailService kqDailyStatusDetailService;

    @GetMapping("/findKqDailyStatusDetailById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public KqDailyStatusDetail findKqDailyStatusDetailById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        return kqDailyStatusDetailService.findKqDailyStatusDetailById(id);
    }

    @PostMapping("/saveKqDailyStatusDetail")
    @ApiOperation(value = "保存", notes = "返回对象")
    public KqDailyStatusDetail saveKqDailyStatusDetail(
            @ApiParam(value = "对象", required = true)
            @RequestBody KqDailyStatusDetail kqDailyStatusDetail) {
        kqDailyStatusDetailService.saveKqDailyStatusDetail(kqDailyStatusDetail);
        kqDailyStatusDetailService.updatKqWorkerDailyRecorrds(kqDailyStatusDetail);
        return kqDailyStatusDetail;
    }

    @PostMapping("/findKqDailyStatusDetailListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<KqDailyStatusDetail> findKqDailyStatusDetailListByCondition(
            @ApiParam(value = "对象")
            @RequestBody KqDailyStatusDetail kqDailyStatusDetail) {
        return kqDailyStatusDetailService.findKqDailyStatusDetailListByCondition(kqDailyStatusDetail);
    }

    @PostMapping("/findKqDailyStatusDetailCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findKqDailyStatusDetailCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody KqDailyStatusDetail kqDailyStatusDetail) {
        return kqDailyStatusDetailService.findKqDailyStatusDetailCountByCondition(kqDailyStatusDetail);
    }

    @PostMapping("/updateKqDailyStatusDetail")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateKqDailyStatusDetail(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody KqDailyStatusDetail kqDailyStatusDetail) {
        kqDailyStatusDetailService.updateKqDailyStatusDetail(kqDailyStatusDetail);
        kqDailyStatusDetailService.updatKqWorkerDailyRecorrds(kqDailyStatusDetail);
    }

    @GetMapping("/deleteKqDailyStatusDetail/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteKqDailyStatusDetail(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id) {
        kqDailyStatusDetailService.deleteKqDailyStatusDetail(id);
    }

    @PostMapping("/deleteKqDailyStatusDetailByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteKqDailyStatusDetailByCondition(
            @ApiParam(value = "对象")
            @RequestBody KqDailyStatusDetail kqDailyStatusDetail) {
        kqDailyStatusDetailService.deleteKqDailyStatusDetailByCondition(kqDailyStatusDetail);
    }

    @PostMapping("/findOneKqDailyStatusDetailByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public KqDailyStatusDetail findOneKqDailyStatusDetailByCondition(
            @ApiParam(value = "对象")
            @RequestBody KqDailyStatusDetail kqDailyStatusDetail) {
        return kqDailyStatusDetailService.findOneKqDailyStatusDetailByCondition(kqDailyStatusDetail);
    }

    @GetMapping("/saveOaKqDailyStatusDetail")
    @ApiOperation(value = "保存OA")
    public void saveOaKqDailyStatusDetail() {
        kqDailyStatusDetailService.saveOaKqDailyStatusDetail();
    }

}
