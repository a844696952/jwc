package com.yice.edu.cn.jy.controller.prepLessonResource;

import com.yice.edu.cn.common.pojo.jy.prepLessonResource.PrepLessonResourceFile;
import com.yice.edu.cn.jy.service.prepLessonResource.PrepLessonResourceFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prepLessonResourceFile")
@Api(value = "/prepLessonResourceFile",description = "运营平台备课资源文档图片模块")
public class PrepLessonResourceFileController {
    @Autowired
    private PrepLessonResourceFileService prepLessonResourceFileService;

    @GetMapping("/findPrepLessonResourceFileById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public PrepLessonResourceFile findPrepLessonResourceFileById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return prepLessonResourceFileService.findPrepLessonResourceFileById(id);
    }

    @PostMapping("/savePrepLessonResourceFile")
    @ApiOperation(value = "保存", notes = "返回对象")
    public PrepLessonResourceFile savePrepLessonResourceFile(
            @ApiParam(value = "对象", required = true)
            @RequestBody PrepLessonResourceFile prepLessonResourceFile){
        prepLessonResourceFileService.savePrepLessonResourceFile(prepLessonResourceFile);
        return prepLessonResourceFile;
    }

    @PostMapping("/findPrepLessonResourceFileListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<PrepLessonResourceFile> findPrepLessonResourceFileListByCondition(
            @ApiParam(value = "对象")
            @RequestBody PrepLessonResourceFile prepLessonResourceFile){
        return prepLessonResourceFileService.findPrepLessonResourceFileListByCondition(prepLessonResourceFile);
    }
    @PostMapping("/find/findPrepLessonResourceFileListByCondition2")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<PrepLessonResourceFile> findPrepLessonResourceFileListByCondition2(
            @ApiParam(value = "对象")
            @RequestBody PrepLessonResourceFile prepLessonResourceFile){
        return prepLessonResourceFileService.findPrepLessonResourceFileListByCondition2(prepLessonResourceFile);
    }

    @PostMapping("/findPrepLessonResourceFileCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findPrepLessonResourceFileCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody PrepLessonResourceFile prepLessonResourceFile){
        return prepLessonResourceFileService.findPrepLessonResourceFileCountByCondition(prepLessonResourceFile);
    }
    @PostMapping("/findPrepLessonResourceFileCountByCondition2")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findPrepLessonResourceFileCountByCondition2(
            @ApiParam(value = "对象")
            @RequestBody PrepLessonResourceFile prepLessonResourceFile){
        return prepLessonResourceFileService.findPrepLessonResourceFileCountByCondition2(prepLessonResourceFile);
    }
    @PostMapping("/updatePrepLessonResourceFile")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updatePrepLessonResourceFile(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody PrepLessonResourceFile prepLessonResourceFile){
        prepLessonResourceFileService.updatePrepLessonResourceFile(prepLessonResourceFile);
    }

    @GetMapping("/deletePrepLessonResourceFile/{id}")
    @ApiOperation(value = "通过id删除")
    public void deletePrepLessonResourceFile(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        prepLessonResourceFileService.deletePrepLessonResourceFile(id);
    }
    @PostMapping("/deletePrepLessonResourceFileByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deletePrepLessonResourceFileByCondition(
            @ApiParam(value = "对象")
            @RequestBody PrepLessonResourceFile prepLessonResourceFile){
        prepLessonResourceFileService.deletePrepLessonResourceFileByCondition(prepLessonResourceFile);
    }
    @PostMapping("/findOnePrepLessonResourceFileByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public PrepLessonResourceFile findOnePrepLessonResourceFileByCondition(
            @ApiParam(value = "对象")
            @RequestBody PrepLessonResourceFile prepLessonResourceFile){
        return prepLessonResourceFileService.findOnePrepLessonResourceFileByCondition(prepLessonResourceFile);
    }
//downloadCountChange
    @GetMapping("/update/downloadCountChange/{id}")
    @ApiOperation(value = "通过id增加文档文件下载次数")
    public void downloadCountChange(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        prepLessonResourceFileService.downloadCountChange(id);
    }
    @GetMapping("/update/numLookChange/{id}")
    @ApiOperation(value = "通过id增加文档文件下载次数")
    public void numLookChange(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        prepLessonResourceFileService.numLookChange(id);
    }

    @PostMapping("/find/findMatFilesCountByMatItemid")
    @ApiOperation(value = "通过章节id查询文件列表，不分类型")
    public long findMatFilesCountByMatItemid(
            @ApiParam(value = "对象", required = true)
            @RequestBody  PrepLessonResourceFile prepLessonResourceFile){
        return prepLessonResourceFileService.findMatFilesCountByMatItemid(prepLessonResourceFile);
    }
    @PostMapping("/find/findMatFileListByMatItemid")
    @ApiOperation(value = "通过章节id查询文件数量，不分类型")
    public  List<PrepLessonResourceFile> findMatFileListByMatItemid(
            @ApiParam(value = "对象", required = true)
            @RequestBody  PrepLessonResourceFile prepLessonResourceFile){
        return prepLessonResourceFileService.findMatFileListByMatItemid(prepLessonResourceFile);
    }

    //通过文件名或文件类型模糊查
    @PostMapping("/find/findMatFilesCountByCondition3")
    @ApiOperation(value = "通过条件查询文件列表，不分类型")
    public long findMatFilesCountByCondition3(
            @ApiParam(value = "对象", required = true)
            @RequestBody  PrepLessonResourceFile prepLessonResourceFile){
        return prepLessonResourceFileService.findMatFilesCountByCondition3(prepLessonResourceFile);
    }
    @PostMapping("/find/findMatFileListByCondition3")
    @ApiOperation(value = "通过条件查询文件数量，不分类型")
    public  List<PrepLessonResourceFile> findMatFileListByCondition3(
            @ApiParam(value = "对象", required = true)
            @RequestBody  PrepLessonResourceFile prepLessonResourceFile){
        return prepLessonResourceFileService.findMatFileListByCondition3(prepLessonResourceFile);
    }

}
