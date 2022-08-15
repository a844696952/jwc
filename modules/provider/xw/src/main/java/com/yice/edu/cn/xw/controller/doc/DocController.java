package com.yice.edu.cn.xw.controller.doc;

import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.xw.document.Doc;
import com.yice.edu.cn.xw.service.doc.DocService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doc")
@Api(value = "/doc",description = "模块")
public class DocController {
    @Autowired
    private DocService docService;

    @GetMapping("/findDocById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public Doc findDocById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return docService.findDocById(id);
    }

    @PostMapping("/saveDoc")
    @ApiOperation(value = "保存", notes = "返回对象")
    public Doc saveDoc(
            @ApiParam(value = "对象", required = true)
            @RequestBody Doc doc){
        docService.saveDoc(doc);
        return doc;
    }

    @PostMapping("/findDocListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<Doc> findDocListByCondition(
            @ApiParam(value = "对象")
            @RequestBody Doc doc){
        return docService.findDocListByCondition(doc);
    }
    @PostMapping("/findDocCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findDocCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody Doc doc){
        return docService.findDocCountByCondition(doc);
    }

    @PostMapping("/updateDoc")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateDoc(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody Doc doc){
        docService.updateDoc(doc);
    }

    @GetMapping("/deleteDoc/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteDoc(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        docService.deleteDoc(id);
    }
    @PostMapping("/deleteDocByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteDocByCondition(
            @ApiParam(value = "对象")
            @RequestBody Doc doc){
        docService.deleteDocByCondition(doc);
    }
    @PostMapping("/findOneDocByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public Doc findOneDocByCondition(
            @ApiParam(value = "对象")
            @RequestBody Doc doc){
        return docService.findOneDocByCondition(doc);
    }
}
