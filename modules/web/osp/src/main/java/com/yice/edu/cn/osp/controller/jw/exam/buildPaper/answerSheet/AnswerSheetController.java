package com.yice.edu.cn.osp.controller.jw.exam.buildPaper.answerSheet;

import com.evopdf.PdfPage;
import com.evopdf.PdfPageOrientation;
import com.evopdf.PdfPageSize;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.answerSheet.AnswerSheet;
import com.yice.edu.cn.osp.controller.jw.exam.PdfHelper;
import com.yice.edu.cn.osp.service.jw.exam.buildPaper.answerSheet.AnswerSheetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/answerSheet")
@Api(value = "/answerSheet",description = "答题卡模块")
public class AnswerSheetController {
    @Autowired
    private AnswerSheetService answerSheetService;
    @Autowired
    private PdfHelper pdfHelper;

    @PostMapping("/saveAnswerSheet")
    @ApiOperation(value = "保存答题卡对象", notes = "返回保存好的答题卡对象", response=AnswerSheet.class)
    public ResponseJson saveAnswerSheet(
            @ApiParam(value = "答题卡对象", required = true)
            @RequestBody AnswerSheet answerSheet){
        answerSheet.setSchoolId(mySchoolId());
        answerSheet.setCreateUserId(myId());
        AnswerSheet s=answerSheetService.saveAnswerSheet(answerSheet);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findAnswerSheetById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找答题卡", notes = "返回响应对象", response=AnswerSheet.class)
    public ResponseJson findAnswerSheetById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        AnswerSheet answerSheet=answerSheetService.findAnswerSheetById(id);
        return new ResponseJson(answerSheet);
    }

    @PostMapping("/update/updateAnswerSheet")
    @ApiOperation(value = "修改答题卡对象", notes = "返回响应对象")
    public ResponseJson updateAnswerSheet(
            @ApiParam(value = "被修改的答题卡对象,对象属性不为空则修改", required = true)
            @RequestBody AnswerSheet answerSheet){
        answerSheetService.updateAnswerSheet(answerSheet);
        return new ResponseJson();
    }

    @GetMapping("/look/lookAnswerSheetById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找答题卡", notes = "返回响应对象", response=AnswerSheet.class)
    public ResponseJson lookAnswerSheetById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        AnswerSheet answerSheet=answerSheetService.findAnswerSheetById(id);
        return new ResponseJson(answerSheet);
    }

    @PostMapping("/findAnswerSheetsByCondition")
    @ApiOperation(value = "根据条件查找答题卡", notes = "返回响应对象", response=AnswerSheet.class)
    public ResponseJson findAnswerSheetsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AnswerSheet answerSheet){
        answerSheet.setCreateUserId(myId());
        //answerSheet.setType(0);//只显示自制的答题卡
        List<AnswerSheet> data=answerSheetService.findAnswerSheetListByCondition(answerSheet);
        long count=answerSheetService.findAnswerSheetCountByCondition(answerSheet);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneAnswerSheetByCondition")
    @ApiOperation(value = "根据条件查找单个答题卡,结果必须为单条数据", notes = "没有时返回空", response=AnswerSheet.class)
    public ResponseJson findOneAnswerSheetByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody AnswerSheet answerSheet){
        AnswerSheet one=answerSheetService.findOneAnswerSheetByCondition(answerSheet);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteAnswerSheet/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteAnswerSheet(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        answerSheetService.deleteAnswerSheet(id);
        return new ResponseJson();
    }



    @PostMapping("/ignore/convertHtmlsToPdf")
    @ApiOperation(value = "将html转换成pdf并下载",notes = "如果是html代码片段，需拼接成完整的html页面,同时body默认样式去掉,html代码片段的尺寸为width: 420.16mm;height: 296.99mm;")
    public void convertHtmlsToPdf(@RequestBody List<String> htmls, HttpServletResponse response) throws Exception {
        byte[] bytes = pdfHelper.multiHtmlToPdf(htmls);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();

    }

    @PostMapping("/ignore/multiHtmpToPdfA4")
    @ApiOperation(value = "将html转换成pdf并下载",notes = "如果是html代码片段，需拼接成完整的html页面,同时body默认样式去掉,html代码片段的尺寸为width: 210mm;height: 297mm;")
    public void convertHtmlsToPdfA4(@RequestBody List<String> htmls, HttpServletResponse response) throws Exception{
        byte[] bytes = pdfHelper.multiHtmpToPdfA4(htmls);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }
}
