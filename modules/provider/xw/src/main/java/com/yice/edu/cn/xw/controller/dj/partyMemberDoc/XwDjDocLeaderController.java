package com.yice.edu.cn.xw.controller.dj.partyMemberDoc;

import com.yice.edu.cn.common.pojo.xw.document.Doc;
import com.yice.edu.cn.common.pojo.xw.document.DocDepartment;
import com.yice.edu.cn.xw.service.dj.partyMemberDoc.XwDjDocLeaderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/xwDjDocLeader")
@Api(value = "/xwDjDocLeader", description = "（收文管理）我批阅的")
public class XwDjDocLeaderController {
    @Autowired
    private XwDjDocLeaderService docLeaderService;

    @PostMapping("/findDocAndDocLeaderList")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<Doc> findDocAndDocLeaderList(
            @ApiParam(value = "对象")
            @RequestBody Doc doc
    ) {
        return docLeaderService.findDocAndDocLeaderList(doc);
    }

    @PostMapping("/findDocCountByCondition")
    @ApiOperation(value = "根据条件返回数量", notes = "返回数量")
    public long findDocCountByCondition(
            @ApiParam(value = "传递的doc对象")
            @RequestBody Doc doc
    ) {
        return docLeaderService.findDocCountByCondition(doc);
    }

    @PostMapping("/saveUpdateDocCompletion")
    @ApiOperation(value = "根据条件修改公文", notes = "无返回")
    public void saveUpdateDocCompletion(
            @ApiParam(value = "对象")
            @RequestBody Doc doc
    ) {
        docLeaderService.saveUpdateDocCompletion(doc);
    }

    @PostMapping("/saveDocManagement/{docId}")
    @ApiOperation(value = "添加收文管理发送对象", notes = "无返回")
    public Doc saveDocManagement(
            @ApiParam(value = "传递发送对象数组与公文Id")
            @RequestBody DocDepartment docDepartment,
            @PathVariable String docId
    ) {
        return docLeaderService.saveDocManagement(docDepartment, docId);
    }

    @PostMapping("/saveUpdateDocLeaterCompletion")
    @ApiOperation(value = "根据条件转他人审批", notes = "无返回")
    public void saveUpdateDocLeaterCompletion(
            @ApiParam(value = "对象")
            @RequestBody Doc doc
    ) {
        docLeaderService.saveUpdateDocLeaterCompletion(doc);
    }


}
