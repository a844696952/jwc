package com.yice.edu.cn.osp.controller.jw.stuEvaluate;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.stuEvaluate.*;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClassesForStuEvaluate;
import com.yice.edu.cn.osp.service.jw.stuEvaluate.StuEvaluateContentService;
import com.yice.edu.cn.osp.service.jw.stuEvaluate.StuEvaluateSendObjectService;
import com.yice.edu.cn.osp.service.jw.stuEvaluate.StuEvaluateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import java.util.ArrayList;
import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/stuEvaluate")
@Api(value = "/stuEvaluate",description = "学生评价信息模块")
public class StuEvaluateController {
    @Autowired
    private StuEvaluateService stuEvaluateService;
    @Autowired
    private StuEvaluateSendObjectService stuEvaluateSendObjectService;
    @Autowired
    private StuEvaluateContentService stuEvaluateContentService;

    @PostMapping("save/saveStuEvaluate")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveStuEvaluate(
            @ApiParam(value = "对象", required = true)
            @RequestBody StuEvaluate stuEvaluate){
        stuEvaluate.setSchoolId(mySchoolId());
        StuEvaluate s=stuEvaluateService.saveStuEvaluate(stuEvaluate);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findStuEvaluateById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findStuEvaluateById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        StuEvaluate stuEvaluate=stuEvaluateService.findStuEvaluateById(id);
        return new ResponseJson(stuEvaluate);
    }

    @PostMapping("/update/updateStuEvaluate")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateStuEvaluate(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody StuEvaluate stuEvaluate){
        stuEvaluateService.updateStuEvaluate(stuEvaluate);
        return new ResponseJson();
    }

    @GetMapping("/look/lookStuEvaluateById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookStuEvaluateById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        StuEvaluate stuEvaluate=stuEvaluateService.findStuEvaluateById(id);
        return new ResponseJson(stuEvaluate);
    }

    @PostMapping("/findStuEvaluatesByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findStuEvaluatesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody StuEvaluate stuEvaluate){
        stuEvaluate.setSchoolId(mySchoolId());
        Pager pager=stuEvaluate.getPager();
        if(pager!=null){
            pager.setLike("title");
        }
        List<StuEvaluate> data=stuEvaluateService.findStuEvaluateListByCondition(stuEvaluate);
        long count=stuEvaluateService.findStuEvaluateCountByCondition(stuEvaluate);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneStuEvaluateByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneStuEvaluateByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody StuEvaluate stuEvaluate){
        stuEvaluate.setSchoolId(mySchoolId());
        StuEvaluate one=stuEvaluateService.findOneStuEvaluateByCondition(stuEvaluate);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteStuEvaluate/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteStuEvaluate(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        stuEvaluateService.deleteStuEvaluate(id);
        return new ResponseJson();
    }


    @PostMapping("/findStuEvaluateListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findStuEvaluateListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody StuEvaluate stuEvaluate){
        stuEvaluate.setSchoolId(mySchoolId());
        List<StuEvaluate> data=stuEvaluateService.findStuEvaluateListByCondition(stuEvaluate);
        return new ResponseJson(data);
    }


    @GetMapping("ignore/findClassesHeadTeacherBySchoolId")
    @ApiOperation(value = "学生评价获取学校班级及班主任", notes = "班级教师对象")
    public ResponseJson findClassesHeadTeacherBySchoolId(){
        List<TeacherClassesForStuEvaluate> data= stuEvaluateService.findClassesHeadTeacherBySchoolId(mySchoolId());
        return new ResponseJson(data);
    }


    @PostMapping("save/saveStuEvaluateSendObject")
    @ApiOperation(value = "保存发送对象", notes = "返回对象")
    public ResponseJson  saveStuEvaluateSendObject(
            @ApiParam(value = "对象", required = true)
            @RequestBody ArrayList<StuEvaluateSendObject> sendObjectList){
        sendObjectList.get(0).getStuEvaluate().setSchoolId(mySchoolId());
        stuEvaluateService.saveStuEvaluateSendObject(sendObjectList);
        return new ResponseJson();
    }



    @GetMapping("look/findStuEvaluateSendObjectListByEvaluateId/{evaluateId}")
    @ApiOperation(value = "学生评价查看", notes = "没有时返回空")
    public ResponseJson findStuEvaluateSendObjectListByEvaluateId(
            @ApiParam(value = "传入evaluateId")
            @PathVariable String evaluateId){
        StuEvaluateSendObject stuEvaluateSendObject=new StuEvaluateSendObject();
        StuEvaluate stuEvaluate=new StuEvaluate();
        stuEvaluate.setId(evaluateId);
        stuEvaluateSendObject.setStuEvaluate(stuEvaluate);
        List<StuEvaluateSendObject> data=stuEvaluateSendObjectService.findStuEvaluateSendObjectListByCondition(stuEvaluateSendObject);
        return new ResponseJson(data);
    }


    @PostMapping("look/findStuEvaluateContentsByCondition")
    @ApiOperation(value = "根据评价evaluateId,和班级id：classId，返回学生评价列表", notes = "返回响应对象")
    public ResponseJson findStuEvaluateContentsByCondition(
            @ApiParam(value = "传入评价evaluateId,和班级id：classId，评价标题：evaluateTitle，评价截止时间：endTime")
            @Validated
            @RequestBody StuEvaluateContent stuEvaluateContent){
        stuEvaluateContent.setEvaluateState("2");
        List<StuEvaluateContent> data=stuEvaluateContentService.findStuEvaluateContentListByCondition(stuEvaluateContent);
        long count=stuEvaluateContentService.findStuEvaluateContentCountByCondition(stuEvaluateContent);
        return new ResponseJson(data,count);
    }


    /**
     * 以下方法为评价任务中使用的方法
     */

    @PostMapping("/find/findStuEvaluateSendObjectList")
    @ApiOperation(value = "根据教师id,查询发送对象集合", notes = "没有时返回空")
    public ResponseJson findStuEvaluateSendObjectList(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody StuEvaluateSendObject stuEvaluateSendObject){
        stuEvaluateSendObject.setTeacherId(myId());
        List<StuEvaluateSendObject> data=stuEvaluateSendObjectService.findStuEvaluateSendObjectListByCondition(stuEvaluateSendObject);
        long count=stuEvaluateSendObjectService.findStuEvaluateSendObjectCountByCondition(stuEvaluateSendObject);
        return new ResponseJson(data,count);
    }


    @PostMapping("look/findStuEvaluateContentListByCondition")
    @ApiOperation(value = "卡片详情获取学生列表", notes = "返回学生评价对象,不包含总条数")
    public ResponseJson findStuEvaluateContentListByCondition(
            @ApiParam(value = "评价id：evaluateId，班级id：classId")
            @Validated
            @RequestBody StuEvaluateContent stuEvaluateContent){
        stuEvaluateContent.setSchoolId(mySchoolId());
        stuEvaluateContent.setEvaluateState("1");
        List<StuEvaluateContent> data=stuEvaluateContentService.findStuEvaluateContentListByCondition(stuEvaluateContent);//获取为评价
        stuEvaluateContent.setEvaluateState("2");
        List<StuEvaluateContent> data1=stuEvaluateContentService.findStuEvaluateContentListByCondition(stuEvaluateContent);//获取已评价
        CardDetail cardDetail=new CardDetail();
        cardDetail.setNotEvaluatedList(data);
        cardDetail.setNotSubmitNum(data.size());
        cardDetail.setAlreadyEvaluatedList(data1);
        cardDetail.setSubmitNum(data1.size());
        return new ResponseJson(cardDetail);
    }

    @PostMapping("/update/updateStuEvaluateContent")
    @ApiOperation(value = "进行评价", notes = "返回响应对象")
    public ResponseJson updateStuEvaluateContent(
            @ApiParam(value = "content（评价内容)、id(评价内容id)、label(标签)、sendObjectId(发送对象id)、starNum(星星数量)", required = true)
            @RequestBody StuEvaluateContent stuEvaluateContent){
        int num= stuEvaluateContent.getStarNum();
        switch (num){
            case 1:
                stuEvaluateContent.setScore("E");
                break;
            case 2:
                stuEvaluateContent.setScore("D");
                break;
            case 3:
                stuEvaluateContent.setScore("C");
                break;
            case 4:
                stuEvaluateContent.setScore("B");
                break;
            default:
                stuEvaluateContent.setScore("A");
                break;
        }
        stuEvaluateContent.setEvaluateState("2");
        stuEvaluateContent.setCreateTime(DateUtil.now());
        stuEvaluateContentService.updateStuEvaluateContent(stuEvaluateContent);
        //更新提交人数
        StuEvaluateSendObject stuEvaluateSendObject=stuEvaluateSendObjectService.findStuEvaluateSendObjectById(stuEvaluateContent.getSendObjectId());
        stuEvaluateSendObject.setSubmitNum(stuEvaluateSendObject.getSubmitNum()+1);
        stuEvaluateSendObjectService.updateStuEvaluateSendObject(stuEvaluateSendObject);
        return new ResponseJson();
    }



    @PostMapping("/moveStuEvaluateToHistory")
    @ApiOperation(value = "将毕业生学生评价数据移动到历史数据库", notes = "返回对象")
    public void moveStuEvaluateToHistory(
            @ApiParam(value = "对象", required = true)
            @RequestBody HistoryPojo historyPojo){
        stuEvaluateService.moveStuEvaluateToHistory(historyPojo);
    }




}
