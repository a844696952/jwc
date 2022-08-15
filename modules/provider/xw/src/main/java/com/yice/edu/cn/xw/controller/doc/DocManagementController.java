package com.yice.edu.cn.xw.controller.doc;

import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.xw.document.Doc;
import com.yice.edu.cn.common.pojo.xw.document.DocManagement;
import com.yice.edu.cn.xw.service.doc.DocManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/docManagement")
@Api(value = "/docManagement",description = "我接收的模块")
public class DocManagementController {

    @Autowired
    private DocManagementService docManagementService;

    @PostMapping("/findDocListByCondition")
    @ApiOperation(value = "通过条件查询列表",notes = "返回列表")
    public List<Doc> findDocListByCondition(
            @ApiParam(value = "条件为空则返回全部")
            @RequestBody Doc doc
    ){
        return docManagementService.findDocListByCondition(doc);
    }

    @PostMapping("/findDocManagementCountByCondition")
    @ApiOperation(value = "通过条件查询总数量",notes = "返回总数量")
    public long findDocManagementCountByCondition(
            @ApiParam(value = "条件为空则返回全部")
            @RequestBody Doc doc
    ){
        return docManagementService.findDocManagementCountByCondition(doc);
    }


    @GetMapping("/fingOneDocUpdateManagement/{docId}/{docObjectId}")
    @ApiOperation(value = "查看收文详细信息，并修改type值(未读改成已读)",notes = "返回对象")
    public Doc fingOneDocUpdateManagement(
            @ApiParam(value = "查看收文详细信息")
            @PathVariable String docId,
            @PathVariable String docObjectId
    ){
        return docManagementService.fingOneDocUpdateManagement(docId,docObjectId);
    }


    @PostMapping("/findDocManagementReadList")
    @ApiOperation(value = "通过条件查询",notes = "返回对应列表")
    public List<DocManagement> findDocManagementReadList(
            @ApiParam(value = "查看已阅或未阅名单")
            @RequestBody DocManagement docManagement
    ){
        return docManagementService.findDocManagementReadList(docManagement);
    }

    @PostMapping("/getDocManagementReadOrUnRead")
    public List<Department> getDocManagementReadOrUnRead(
            @RequestBody DocManagement docManagement){
        return docManagementService.getDocManagementReadOrUnRead(docManagement);
    }

}
