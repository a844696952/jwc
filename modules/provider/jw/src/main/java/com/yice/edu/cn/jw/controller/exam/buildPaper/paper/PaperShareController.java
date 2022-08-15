package com.yice.edu.cn.jw.controller.exam.buildPaper.paper;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.Paper;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperShare;
import com.yice.edu.cn.jw.service.exam.buildPaper.paper.PaperShareService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paperShare")
@Api(value = "/paperShare",description = "分享试卷模块")
public class PaperShareController {
    @Autowired
    private PaperShareService paperShareService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findPaperShareById/{id}")
    @ApiOperation(value = "通过id查找分享试卷", notes = "返回分享试卷对象")
    public PaperShare findPaperShareById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return paperShareService.findPaperShareById(id);
    }

    @PostMapping("/savePaperShare")
    @ApiOperation(value = "保存分享试卷", notes = "返回分享试卷对象")
    public PaperShare savePaperShare(
            @ApiParam(value = "分享试卷对象", required = true)
            @RequestBody PaperShare paperShare){
        paperShareService.savePaperShare(paperShare);
        return paperShare;
    }

    @PostMapping("/batchSavePaperShare")
    @ApiOperation(value = "批量保存分享试卷")
    public void batchSavePaperShare(
            @ApiParam(value = "分享试卷对象集合", required = true)
            @RequestBody List<PaperShare> paperShares){
        paperShareService.batchSavePaperShare(paperShares);
    }

    @PostMapping("/findPaperShareListByCondition")
    @ApiOperation(value = "根据条件查找分享试卷列表", notes = "返回分享试卷列表")
    public List<PaperShare> findPaperShareListByCondition(
            @ApiParam(value = "分享试卷对象")
            @RequestBody PaperShare paperShare){
        return paperShareService.findPaperShareListByCondition(paperShare);
    }
    @PostMapping("/findPaperShareCountByCondition")
    @ApiOperation(value = "根据条件查找分享试卷列表个数", notes = "返回分享试卷总个数")
    public long findPaperShareCountByCondition(
            @ApiParam(value = "分享试卷对象")
            @RequestBody PaperShare paperShare){
        return paperShareService.findPaperShareCountByCondition(paperShare);
    }

    @PostMapping("/updatePaperShare")
    @ApiOperation(value = "修改分享试卷有值的字段", notes = "分享试卷对象必传")
    public void updatePaperShare(
            @ApiParam(value = "分享试卷对象,对象属性不为空则修改", required = true)
            @RequestBody PaperShare paperShare){
        paperShareService.updatePaperShare(paperShare);
    }
    @PostMapping("/updatePaperShareForAll")
    @ApiOperation(value = "修改分享试卷所有字段", notes = "分享试卷对象必传")
    public void updatePaperShareForAll(
            @ApiParam(value = "分享试卷对象", required = true)
            @RequestBody PaperShare paperShare){
        paperShareService.updatePaperShareForAll(paperShare);
    }

    @GetMapping("/deletePaperShare/{id}")
    @ApiOperation(value = "通过id删除分享试卷")
    public void deletePaperShare(
            @ApiParam(value = "分享试卷对象", required = true)
            @PathVariable String id){
        paperShareService.deletePaperShare(id);
    }
    @PostMapping("/deletePaperShareByCondition")
    @ApiOperation(value = "根据条件删除分享试卷")
    public void deletePaperShareByCondition(
            @ApiParam(value = "分享试卷对象")
            @RequestBody PaperShare paperShare){
        paperShareService.deletePaperShareByCondition(paperShare);
    }
    @PostMapping("/findOnePaperShareByCondition")
    @ApiOperation(value = "根据条件查找单个分享试卷,结果必须为单条数据", notes = "返回单个分享试卷,没有时为空")
    public PaperShare findOnePaperShareByCondition(
            @ApiParam(value = "分享试卷对象")
            @RequestBody PaperShare paperShare){
        return paperShareService.findOnePaperShareByCondition(paperShare);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @PostMapping("/savePaperShareListKong")
    public void savePaperShareListKong(@RequestBody PaperShare paperShare){
        paperShareService.savePaperShareListKong(paperShare);
    }

    @PostMapping("/updatePaperShareAddPaper")
    public ResponseJson updatePaperShareAddPaper(@RequestBody PaperShare paperShare){
        return paperShareService.updatePaperShareAddPaper(paperShare);
    }

    @GetMapping("/uploadSharePaper/{paperId}")
    public Paper uploadSharePaper(@PathVariable("paperId")String paperId){
        return paperShareService.uploadSharePaper(paperId);
    }

    @PostMapping("/savePaperShareKong")
    public void savePaperShareKong(@RequestBody PaperShare paperShare){
        paperShareService.savePaperShareKong(paperShare);
    }
}
