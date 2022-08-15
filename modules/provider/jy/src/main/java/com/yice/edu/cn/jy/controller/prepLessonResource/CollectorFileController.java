package com.yice.edu.cn.jy.controller.prepLessonResource;

import com.yice.edu.cn.common.pojo.jy.prepLessonResource.CollectorFile;
import com.yice.edu.cn.common.pojo.jy.prepLessonResource.PrepLessonResourceFile;
import com.yice.edu.cn.jy.service.prepLessonResource.CollectorFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collectorFile")
@Api(value = "/collectorFile",description = "收藏者（教师或家长）与运营平台资源文件关联表模块")
public class CollectorFileController {
    @Autowired
    private CollectorFileService collectorFileService;

    @GetMapping("/findCollectorFileById/{id}")
    @ApiOperation(value = "通过id查找收藏者（教师或家长）与运营平台资源文件关联表", notes = "返回收藏者（教师或家长）与运营平台资源文件关联表对象")
    public CollectorFile findCollectorFileById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return collectorFileService.findCollectorFileById(id);
    }

    @PostMapping("/saveCollectorFile")
    @ApiOperation(value = "保存收藏者（教师或家长）与运营平台资源文件关联表", notes = "返回收藏者（教师或家长）与运营平台资源文件关联表对象")
    public CollectorFile saveCollectorFile(
            @ApiParam(value = "收藏者（教师或家长）与运营平台资源文件关联表对象", required = true)
            @RequestBody CollectorFile collectorFile){
        collectorFileService.saveCollectorFile(collectorFile);
        return collectorFile;
    }

    @PostMapping("/findCollectorFileListByCondition")
    @ApiOperation(value = "根据条件查找收藏者（教师或家长）与运营平台资源文件关联表列表", notes = "返回收藏者（教师或家长）与运营平台资源文件关联表列表")
    public List<CollectorFile> findCollectorFileListByCondition(
            @ApiParam(value = "收藏者（教师或家长）与运营平台资源文件关联表对象")
            @RequestBody CollectorFile collectorFile){
        return collectorFileService.findCollectorFileListByCondition(collectorFile);
    }
    @PostMapping("/findCollectorFileCountByCondition")
    @ApiOperation(value = "根据条件查找收藏者（教师或家长）与运营平台资源文件关联表列表个数", notes = "返回收藏者（教师或家长）与运营平台资源文件关联表总个数")
    public long findCollectorFileCountByCondition(
            @ApiParam(value = "收藏者（教师或家长）与运营平台资源文件关联表对象")
            @RequestBody CollectorFile collectorFile){
        return collectorFileService.findCollectorFileCountByCondition(collectorFile);
    }

    @PostMapping("/updateCollectorFile")
    @ApiOperation(value = "修改收藏者（教师或家长）与运营平台资源文件关联表", notes = "收藏者（教师或家长）与运营平台资源文件关联表对象必传")
    public void updateCollectorFile(
            @ApiParam(value = "收藏者（教师或家长）与运营平台资源文件关联表对象,对象属性不为空则修改", required = true)
            @RequestBody CollectorFile collectorFile){
        collectorFileService.updateCollectorFile(collectorFile);
    }

    @GetMapping("/deleteCollectorFile/{id}")
    @ApiOperation(value = "通过id删除收藏者（教师或家长）与运营平台资源文件关联表")
    public void deleteCollectorFile(
            @ApiParam(value = "收藏者（教师或家长）与运营平台资源文件关联表对象", required = true)
            @PathVariable String id){
        collectorFileService.deleteCollectorFile(id);
    }
    @PostMapping("/deleteCollectorFileByCondition")
    @ApiOperation(value = "根据条件删除收藏者（教师或家长）与运营平台资源文件关联表")
    public void deleteCollectorFileByCondition(
            @ApiParam(value = "收藏者（教师或家长）与运营平台资源文件关联表对象")
            @RequestBody CollectorFile collectorFile){
        collectorFileService.deleteCollectorFileByCondition(collectorFile);
    }
    @PostMapping("/findOneCollectorFileByCondition")
    @ApiOperation(value = "根据条件查找单个收藏者（教师或家长）与运营平台资源文件关联表,结果必须为单条数据", notes = "返回单个收藏者（教师或家长）与运营平台资源文件关联表,没有时为空")
    public CollectorFile findOneCollectorFileByCondition(
            @ApiParam(value = "收藏者（教师或家长）与运营平台资源文件关联表对象")
            @RequestBody CollectorFile collectorFile){
        return collectorFileService.findOneCollectorFileByCondition(collectorFile);
    }


    @PostMapping("/findCollectorFilesByConditionToApp")
    @ApiOperation(value = "根据条件查找收藏者（教师或家长）与运营平台资源文件关联表列表", notes = "返回收藏者（教师或家长）与运营平台资源文件关联表列表")
    public List<PrepLessonResourceFile> findCollectorFilesByConditionToApp(
            @ApiParam(value = "收藏者（教师或家长）与运营平台资源文件关联表对象，要传分页Pager")
            @RequestBody CollectorFile collectorFile){
        return collectorFileService.findCollectorFilesByConditionToApp(collectorFile);
    }

    @PostMapping("/findCollectorFileCountByConditionToApp")
    @ApiOperation(value = "根据条件查找收藏者（教师或家长）与运营平台资源文件关联表列表个数", notes = "返回收藏者（教师或家长）与运营平台资源文件关联表总个数")
    public long findCollectorFileCountByConditionToApp(
            @ApiParam(value = "收藏者（教师或家长）与运营平台资源文件关联表对象")
            @RequestBody CollectorFile collectorFile){
        return collectorFileService.findCollectorFileCountByConditionToApp(collectorFile);
    }
}
