package com.yice.edu.cn.yed.controller.jy.knowledgePoint;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.jy.topics.Topics;
import com.yice.edu.cn.yed.service.jy.knowledgePoint.KnowledgePointService;
import com.yice.edu.cn.yed.service.jy.topics.TopicsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/knowledgePoint")
@Api(value = "/knowledgePoint",description = "知识点表模块")
public class KnowledgePointController {
    @Autowired
    private KnowledgePointService knowledgePointService;
    @Autowired
    private TopicsService topicsService;

    @PostMapping("/saveKnowledgePoint")
    @ApiOperation(value = "保存知识点表对象", notes = "返回响应对象")
    public ResponseJson saveKnowledgePoint(
            @ApiParam(value = "知识点表对象", required = true)
            @RequestBody KnowledgePoint knowledgePoint){
        KnowledgePoint s=knowledgePointService.saveKnowledgePoint(knowledgePoint);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findKnowledgePointById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找知识点表", notes = "返回响应对象")
    public ResponseJson findKnowledgePointById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        KnowledgePoint knowledgePoint=knowledgePointService.findKnowledgePointById(id);
        return new ResponseJson(knowledgePoint);
    }

    @PostMapping("/update/updateKnowledgePoint")
    @ApiOperation(value = "修改知识点表对象非空字段", notes = "返回响应对象")
    public ResponseJson updateKnowledgePoint(
            @ApiParam(value = "被修改的知识点表对象,对象属性不为空则修改", required = true)
            @RequestBody KnowledgePoint knowledgePoint){
        knowledgePointService.updateKnowledgePoint(knowledgePoint);
        return new ResponseJson();
    }

    @GetMapping("/look/lookKnowledgePointById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找知识点表", notes = "返回响应对象")
    public ResponseJson lookKnowledgePointById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        KnowledgePoint knowledgePoint=knowledgePointService.findKnowledgePointById(id);
        return new ResponseJson(knowledgePoint);
    }

    @PostMapping("/findKnowledgePointsByCondition")
    @ApiOperation(value = "根据条件查找知识点表模糊查询", notes = "返回响应对象")
    public ResponseJson findKnowledgePointsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KnowledgePoint knowledgePoint){
        //knowledgePoint.setPager(Optional.ofNullable(knowledgePoint.getPager()).orElse(new Pager().setPaging(false)).setLike("name"));
        knowledgePoint.setPager(Optional.ofNullable(knowledgePoint.getPager()).orElse(new Pager()).setPaging(false));
        List<KnowledgePoint> data=knowledgePointService.findKnowledgePointTreeByCondition(knowledgePoint);
        //long count=knowledgePointService.findKnowledgePointCountByCondition(knowledgePoint);
        return new ResponseJson(data);
    }
    @PostMapping("/findOneKnowledgePointByCondition")
    @ApiOperation(value = "根据条件查找单个知识点表,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneKnowledgePointByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody KnowledgePoint knowledgePoint){
        KnowledgePoint one=knowledgePointService.findOneKnowledgePointByCondition(knowledgePoint);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteKnowledgePoint/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteKnowledgePoint(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        knowledgePointService.deleteKnowledgePoint(id);
        return new ResponseJson();
    }


    @PostMapping("/findKnowledgePointListByCondition")
    @ApiOperation(value = "根据条件查找知识点表列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findKnowledgePointListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KnowledgePoint knowledgePoint){
        List<KnowledgePoint> data=knowledgePointService.findKnowledgePointListByCondition(knowledgePoint);
        return new ResponseJson(data);
    }

    @PostMapping("/topic/findTopicsListByCondition4Muti")
    @ApiOperation(value = "根据知识点查询题目列表", notes = "返回响应对象")
    public ResponseJson findTopicsListByCondition4Muti(@RequestBody Topics topics){
        topics.setPager(Optional.ofNullable(topics.getPager()).orElse(new Pager().setPaging(false)).setLike("content"));
        List<Topics> data=topicsService.findTopicsListByCondition4Muti(topics);
        Long count = topicsService.findTopicsCountByCondition4Muti(topics);
        return new ResponseJson(data,count);
    }

    @PostMapping("/ignore/findKnowledgePointListByCondition4Like")
    @ApiOperation(value = "根据条件查找知识点表模糊查询", notes = "返回响应对象")
    public ResponseJson findKnowledgePointListByCondition4Like(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KnowledgePoint knowledgePoint){
        Pager pager = knowledgePoint.getPager()==null?new Pager().setPaging(false):knowledgePoint.getPager();
        knowledgePoint.setPager(pager.setLike("name"));
        List<KnowledgePoint> data=knowledgePointService.findKnowledgePointListByCondition(knowledgePoint);
        return new ResponseJson(data);
    }
    @GetMapping("/ignore/exportTemplate")
    public void exportTemplate(HttpServletResponse response) {
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=teacher.xls");
        try (ServletOutputStream s = response.getOutputStream()) {
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(),
                    KnowledgePoint.class,new ArrayList<>());
            workbook.write(s);
        } catch (Exception e) {

        }
    }
    @PostMapping("/ignore/uploadKnowledgePoint")
    public ResponseJson uploadKnowledgePoint(MultipartFile file) {
        return new ResponseJson(knowledgePointService.uploadKnowledgePoint(file));
    }
    @GetMapping("/ignore/downKnowledge421/{stage}")
    public void downKnowledge421(@PathVariable("stage") String stage){
        knowledgePointService.downKnowledge421(stage);
    }
}
