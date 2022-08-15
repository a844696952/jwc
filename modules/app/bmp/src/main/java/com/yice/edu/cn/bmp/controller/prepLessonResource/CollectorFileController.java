package com.yice.edu.cn.bmp.controller.prepLessonResource;

import com.yice.edu.cn.bmp.service.prepLessonResource.CollectorFileService;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.prepLessonResource.CollectorFile;
import com.yice.edu.cn.common.pojo.jy.prepLessonResource.PrepLessonResourceFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.myParentId;

@RestController
@RequestMapping("/collectorFile")
@Api(value = "/collectorFile",description = "收藏者（教师或家长）与运营平台资源文件关联表模块")
public class CollectorFileController {
    @Autowired
    private CollectorFileService collectorFileService;

    @PostMapping("/saveCollectorFile")
    @ApiOperation(value = "收藏文件，需要传boundId章节绑定id和fileId收藏文件id", notes = "返回保存好的收藏者（教师或家长）与运营平台资源文件关联表对象", response=CollectorFile.class)
    public ResponseJson saveCollectorFile(
            @ApiParam(value = "收藏者对象，需要传boundId章节绑定id和fileId收藏文件id", required = true)
            @RequestBody CollectorFile collectorFile){
        collectorFile.setCollectorId(myParentId());
        CollectorFile s=collectorFileService.saveCollectorFile(collectorFile);
        return new ResponseJson(s);
    }

  /*  @GetMapping("/update/findCollectorFileById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找收藏者（教师或家长）与运营平台资源文件关联表", notes = "返回响应对象", response=CollectorFile.class)
    public ResponseJson findCollectorFileById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        CollectorFile collectorFile=collectorFileService.findCollectorFileById(id);
        return new ResponseJson(collectorFile);
    }

    @PostMapping("/update/updateCollectorFile")
    @ApiOperation(value = "修改收藏者（教师或家长）与运营平台资源文件关联表对象", notes = "返回响应对象")
    public ResponseJson updateCollectorFile(
            @ApiParam(value = "被修改的收藏者（教师或家长）与运营平台资源文件关联表对象,对象属性不为空则修改", required = true)
            @RequestBody CollectorFile collectorFile){
        collectorFileService.updateCollectorFile(collectorFile);
        return new ResponseJson();
    }

    @GetMapping("/look/lookCollectorFileById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找收藏者（教师或家长）与运营平台资源文件关联表", notes = "返回响应对象", response=CollectorFile.class)
    public ResponseJson lookCollectorFileById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        CollectorFile collectorFile=collectorFileService.findCollectorFileById(id);
        return new ResponseJson(collectorFile);
    }*/

    /*@PostMapping("/findCollectorFilesByCondition")
    @ApiOperation(value = "根据条件查找收藏者（教师或家长）与运营平台资源文件关联表", notes = "返回响应对象", response=CollectorFile.class)
    public ResponseJson findCollectorFilesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CollectorFile collectorFile){
        List<CollectorFile> data=collectorFileService.findCollectorFileListByCondition(collectorFile);
        long count=collectorFileService.findCollectorFileCountByCondition(collectorFile);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneCollectorFileByCondition")
    @ApiOperation(value = "根据条件查找单个收藏者（教师或家长）与运营平台资源文件关联表,结果必须为单条数据", notes = "没有时返回空", response=CollectorFile.class)
    public ResponseJson findOneCollectorFileByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody CollectorFile collectorFile){
        CollectorFile one=collectorFileService.findOneCollectorFileByCondition(collectorFile);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteCollectorFile/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteCollectorFile(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        collectorFileService.deleteCollectorFile(id);
        return new ResponseJson();
    }


    @PostMapping("/findCollectorFileListByCondition")
    @ApiOperation(value = "根据条件查找收藏者（教师或家长）与运营平台资源文件关联表列表", notes = "返回响应对象,不包含总条数", response=CollectorFile.class)
    public ResponseJson findCollectorFileListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CollectorFile collectorFile){
        List<CollectorFile> data=collectorFileService.findCollectorFileListByCondition(collectorFile);
        return new ResponseJson(data);
    }*/

   @PostMapping("/findCollectorFilesByConditionToApp")
    @ApiOperation(value = "根据条件查找收藏者（教师或家长）与运营平台资源文件关联表", notes = "返回响应对象", response=CollectorFile.class)
    public ResponseJson findCollectorFilesByConditionToApp(
            @ApiParam(value = "可以用filename模糊查询，分页需要传pager")
            @RequestBody CollectorFile collectorFile){
        collectorFile.setCollectorId(myParentId());
        List<PrepLessonResourceFile> data=collectorFileService.findCollectorFilesByConditionToApp(collectorFile);
        long count=collectorFileService.findCollectorFileCountByConditionToApp(collectorFile);
        return new ResponseJson(data,count);
    }
    @PostMapping("/deleteCollectorFileByCondition")
    @ApiOperation(value = "取消文件收藏，需要传boundId章节绑定id", notes = "返回响应对象", response=CollectorFile.class)
    public ResponseJson deleteCollectorFileByCondition(
            @ApiParam(value = "收藏者对象，需要传boundId章节绑定id")
            @RequestBody CollectorFile collectorFile){
        collectorFile.setCollectorId(myParentId());
        collectorFileService.deleteCollectorFileByCondition(collectorFile);
        return new ResponseJson();
   }
    @PostMapping("/findCollectorFileExistByCondition")
    @ApiOperation(value = "查看文件是否已收藏", notes = "返回响应对象", response=CollectorFile.class)
    public ResponseJson findCollectorFileExistByCondition(
            @ApiParam(value = "传boundId章节绑定id")
            @RequestBody CollectorFile collectorFile){
       collectorFile.setCollectorId(myParentId());
        long count=collectorFileService.findCollectorFileCountByCondition(collectorFile);
        if (count>0){
            return new ResponseJson(true);
        }else {
            return new ResponseJson(false);
        }

    }
}
