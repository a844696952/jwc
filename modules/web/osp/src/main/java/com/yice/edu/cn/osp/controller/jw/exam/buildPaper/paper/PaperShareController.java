package com.yice.edu.cn.osp.controller.jw.exam.buildPaper.paper;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.Paper;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperShare;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import com.yice.edu.cn.osp.service.jw.exam.buildPaper.paper.PaperShareService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/paperShare")
@Api(value = "/paperShare",description = "分享试卷模块")
public class PaperShareController {
    @Autowired
    private PaperShareService paperShareService;

    @PostMapping("/savePaperShare")
    @ApiOperation(value = "保存分享试卷对象", notes = "返回保存好的分享试卷对象", response=PaperShare.class)
    public ResponseJson savePaperShare(
            @ApiParam(value = "分享试卷对象", required = true)
            @RequestBody PaperShare paperShare){
        paperShare.setSchoolId(LoginInterceptor.mySchoolId());
        paperShare.setCreateUserId(LoginInterceptor.currentTeacher().getId());
        paperShare.setCreateUserName(LoginInterceptor.currentTeacher().getName());
        paperShareService.savePaperShareListKong(paperShare);
        return new ResponseJson(paperShare);
    }

    @GetMapping("/update/findPaperShareById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找分享试卷", notes = "返回响应对象", response=PaperShare.class)
    public ResponseJson findPaperShareById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        PaperShare paperShare=paperShareService.findPaperShareById(id);
        return new ResponseJson(paperShare);
    }

    @PostMapping("/update/updatePaperShare")
    @ApiOperation(value = "修改分享试卷对象非空字段", notes = "返回响应对象")
    public ResponseJson updatePaperShare(
            @ApiParam(value = "被修改的分享试卷对象,对象属性不为空则修改", required = true)
            @RequestBody PaperShare paperShare){
        paperShareService.updatePaperShare(paperShare);
        return new ResponseJson();
    }

    @PostMapping("/update/updatePaperShareForAll")
    @ApiOperation(value = "修改分享试卷对象所有字段", notes = "返回响应对象")
    public ResponseJson updatePaperShareForAll(
            @ApiParam(value = "被修改的分享试卷对象", required = true)
            @RequestBody PaperShare paperShare){

        paperShareService.savePaperShareKong(paperShare);
        return new ResponseJson();
    }

    @GetMapping("/look/lookPaperShareById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找分享试卷", notes = "返回响应对象", response=PaperShare.class)
    public ResponseJson lookPaperShareById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        PaperShare paperShare=paperShareService.findPaperShareById(id);
        return new ResponseJson(paperShare);
    }

    @PostMapping("/findPaperSharesByCondition")
    @ApiOperation(value = "根据条件查找分享试卷", notes = "返回响应对象", response=PaperShare.class)
    public ResponseJson findPaperSharesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody PaperShare paperShare){
        paperShare.setSchoolId(LoginInterceptor.mySchoolId());
        paperShare.setReceiveId(LoginInterceptor.myId());
        Pager pager = paperShare.getPager();
        pager.setLike("paperName");
        if(paperShare.getSearchTimeZone()!=null&&paperShare.getSearchTimeZone().length>0){
            pager.setRangeField("createTime");
            pager.setRangeArray(paperShare.getSearchTimeZone());
        }
        pager.setSortOrder("asc,desc");
        pager.setSortField("type,createTime");
        paperShare.setSearchTimeZone(null);
        List<PaperShare> data=paperShareService.findPaperShareListByCondition(paperShare);
        long count=paperShareService.findPaperShareCountByCondition(paperShare);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOnePaperShareByCondition")
    @ApiOperation(value = "根据条件查找单个分享试卷,结果必须为单条数据", notes = "没有时返回空", response=PaperShare.class)
    public ResponseJson findOnePaperShareByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody PaperShare paperShare){
        PaperShare one=paperShareService.findOnePaperShareByCondition(paperShare);
        return new ResponseJson(one);
    }
    @GetMapping("/deletePaperShare/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deletePaperShare(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        paperShareService.deletePaperShare(id);
        return new ResponseJson();
    }
    @PostMapping("/deletePaperShareByCondition")
    public ResponseJson deletePaperShareByCondition(@RequestBody PaperShare paperShare){
        paperShareService.deletePaperShareByCondition(paperShare);
        return new ResponseJson();
    }


    @PostMapping("/findPaperShareListByCondition")
    @ApiOperation(value = "根据条件查找分享试卷列表", notes = "返回响应对象,不包含总条数", response=PaperShare.class)
    public ResponseJson findPaperShareListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody PaperShare paperShare){
        paperShare.setSchoolId(LoginInterceptor.mySchoolId());
        List<PaperShare> data=paperShareService.findPaperShareListByCondition(paperShare);
        return new ResponseJson(data);
    }

    @PostMapping("/updatePaperShareAddPaper")
    @ApiOperation(value = "将试卷加入我的题库",notes = "返回响应对象")
    public ResponseJson updatePaperShareAddPaper(@RequestBody PaperShare paperShare){
        return paperShareService.updatePaperShareAddPaper(paperShare);
    }

    @GetMapping("/uploadSharePaper/{paperId}")
    public ResponseJson uploadSharePaper(@PathVariable("paperId")String paperId){
        Paper paper = paperShareService.uploadSharePaper(paperId);
        if(paper==null){
            return new ResponseJson(false,"分享人已删除原卷");
        }
        return new ResponseJson(paper);
    }

}
