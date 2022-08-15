package com.yice.edu.cn.osp.controller.jw.exam.buildPaper.myPaper;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.answerSheet.AnswerSheet;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.Paper;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import com.yice.edu.cn.osp.service.jw.exam.buildPaper.answerSheet.AnswerSheetService;
import com.yice.edu.cn.osp.service.jw.exam.buildPaper.myPaper.MyPaperService;
import com.yice.edu.cn.osp.service.jw.exam.buildPaper.paper.PaperService;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;

@RestController
@RequestMapping("/myPaper")
public class MyPaperCotroller {
    @Autowired
    private PaperService paperService;
    @Autowired
    private MyPaperService myPaperService;
    @Autowired
    private AnswerSheetService answerSheetService;

    @PostMapping("/findMyPapersByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findMyPapersByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Paper paper){//pc端调用的接口
        paper.setCreateUserId(myId());
        paper.setCurrentType(2);
        if(paper.getSearchTimeZone()==null){
            paper.setSearchTimeZone(new String[0]);
        }
        List<Paper> data= paperService.findTestPaperListByConditionKong(paper);
        long count = paperService.findTestPaperCountByConditionKong(paper);
        return new ResponseJson(data,count);
    }

    @GetMapping("/look/lookTestPaperById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookTestPaperById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        Paper paper = paperService.findTestPaperById(id);
        return new ResponseJson(paper);
    }


    @PostMapping("/look/findOneTestPaperByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneTestPaperByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Paper paper){
        if(paper.getSearchTimeZone()==null){
            paper.setSearchTimeZone(new String[0]);
        }
        paper.setCreateUserId(myId());
        Paper one= myPaperService.findOneTestPaperByCondition(paper);
        return new ResponseJson(one);
    }


    @GetMapping("/update/updatePaperQuestion/{id}")
    @ApiOperation(value = "根据用户Id修改当前试题篮状态,并返回当前点击的试卷对象",notes = "返回试卷对象")
    public  ResponseJson updatePaperQuestion(
            @ApiParam(value = "试卷id",required = true)
            @PathVariable String id
    ){
        Paper paper = new Paper();
        paper.setId(id);
        paper.setCreateUserId(myId());
        Paper paper1 = myPaperService.updatePaperQuestion(paper);
        return new ResponseJson(paper1);
    }


    @GetMapping("/deleteMyPaper/{id}")
    @ApiOperation(value = "判断是否正在被考试使用，如果没有直接删除试卷",notes = "无返回")
    public ResponseJson deleteMyPaper(
            @ApiParam(value = "试卷Id",required = true)
            @PathVariable String id
    ){

        long count = myPaperService.deletePaper(id);
        return new ResponseJson(count);
    }

    //直接删除，不做判断
    @GetMapping("/update/deletePaper/{id}")
    @ApiOperation(value = "通过试卷Id删除试卷",notes = "无返回")
    public ResponseJson deletePaper(
            @ApiParam(value = "试卷Id",required = true)
            @PathVariable String id
    ){
        paperService.deleteTestPaper(id);
        return new ResponseJson();
    }

    //直接通过试卷Id查询试卷
    @PostMapping("/ignore/findOnePaper")
    @ApiOperation(value = "通过试卷Id查询试卷,",notes = "返回试卷对象")
    public ResponseJson findOnePaper(
            @ApiParam(value = "试卷Id",required = true)
            @RequestBody Paper paper
    ){
        Paper paper1 = myPaperService.findOnePaper(paper);
        return new ResponseJson(paper1);
    }


    @GetMapping("/look/AnswerSheet/{id}")
    @ApiOperation(value = "传递试卷id",notes = "返回答题卡对象")
    public ResponseJson findAnswerSheetByPaperId(
            @ApiParam(value = "试卷Id",required = true)
            @PathVariable String id
    ){
        AnswerSheet answerSheet = new AnswerSheet();
        answerSheet.setPaperId(id);
        answerSheet = answerSheetService.findOneAnswerSheetByCondition(answerSheet);
        return new ResponseJson(answerSheet);
    }

    @PostMapping("/update/paperClone")
    @ApiModelProperty(value = "克隆一份试卷",notes = "返回克隆的数据")
    public ResponseJson paperClone(
            @ApiParam(value = "传递需要克隆的试卷对象")
            @RequestBody Paper paper
    ){
        Paper paper1 = myPaperService.findOnePaper(paper);
        Paper paper2 = myPaperService.paperClone(paper1);
        return new ResponseJson(paper2);
    }

    @GetMapping("/update/coveringPaper/{id}")
    @ApiModelProperty(value = "传递克隆试卷的Id",notes = "返回新的试卷记录")
    public ResponseJson coveringPaper(
            @ApiParam(value = "传递克隆试卷的Id")
            @PathVariable String id
    ){
        Paper paper = new Paper();
        paper.setId(id);
        paper.setCreateUserId(LoginInterceptor.myId());
        Paper paper1 = myPaperService.coveringPaper(paper);
        return  new ResponseJson(paper1);
    }
}
