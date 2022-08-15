package com.yice.edu.cn.tap.controller.stuEvaluate;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.stuEvaluate.CardDetail;
import com.yice.edu.cn.common.pojo.jw.stuEvaluate.StuEvaluateContent;
import com.yice.edu.cn.common.pojo.jw.stuEvaluate.StuEvaluateSendObject;
import com.yice.edu.cn.tap.service.stuEvaluate.StuEvaluateContentService;
import com.yice.edu.cn.tap.service.stuEvaluate.StuEvaluateSendObjectService;
import com.yice.edu.cn.tap.service.stuEvaluate.StuEvaluateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/stuEvaluate")
@Api(value = "/stuEvaluate",description = "学生评价信息模块")
public class StuEvaluateController {
    @Autowired
    private StuEvaluateService stuEvaluateService;
    @Autowired
    private StuEvaluateSendObjectService stuEvaluateSendObjectService;
    @Autowired
    private StuEvaluateContentService  stuEvaluateContentService;


    @PostMapping("findStuEvaluateSendObjectListByEvaluateId")
    @ApiOperation(value = "根据教师id,查询发送对象集合", notes = "没有时返回空")
    public ResponseJson findStuEvaluateSendObjectListByEvaluateId(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody StuEvaluateSendObject stuEvaluateSendObject){
        stuEvaluateSendObject.setTeacherId(myId());
        List<StuEvaluateSendObject> data=stuEvaluateSendObjectService.findStuEvaluateSendObjectListByCondition(stuEvaluateSendObject);
        long count=stuEvaluateSendObjectService.findStuEvaluateSendObjectCountByCondition(stuEvaluateSendObject);
        return new ResponseJson(data,count);
    }


    @PostMapping("/findStuEvaluateContentListByCondition")
    @ApiOperation(value = "卡片详情获取学生列表", notes = "返回学生评价对象,不包含总条数")
    public ResponseJson findStuEvaluateContentListByCondition(
            @ApiParam(value = "评价id：evaluateId，班级id：classId，评价标题：evaluateTitle，评价截止时间：endTime")
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

    @PostMapping("/updateStuEvaluateContent")
    @ApiOperation(value = "进行评价", notes = "返回响应对象")
    public ResponseJson updateStuEvaluateContent(
            @ApiParam(value = "content（评价内容)、id(评价id)、label(标签)、sendObjectId(发送对象id)、starNum(星星数量)", required = true)
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

}
