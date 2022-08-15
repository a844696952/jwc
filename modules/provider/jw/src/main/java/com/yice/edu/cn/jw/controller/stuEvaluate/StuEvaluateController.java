package com.yice.edu.cn.jw.controller.stuEvaluate;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.stuEvaluate.HistoryPojo;
import com.yice.edu.cn.common.pojo.jw.stuEvaluate.StuEvaluate;
import com.yice.edu.cn.common.pojo.jw.stuEvaluate.StuEvaluateContent;
import com.yice.edu.cn.common.pojo.jw.stuEvaluate.StuEvaluateSendObject;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClassesForStuEvaluate;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.jw.service.stuEvaluate.StuEvaluateContentService;
import com.yice.edu.cn.jw.service.stuEvaluate.StuEvaluateSendObjectService;
import com.yice.edu.cn.jw.service.stuEvaluate.StuEvaluateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/stuEvaluate")
@Api(value = "/stuEvaluate",description = "模块")
public class StuEvaluateController {
    @Autowired
    private StuEvaluateService stuEvaluateService;
    @Autowired
    private StuEvaluateSendObjectService stuEvaluateSendObjectService;
    @Autowired
    private StuEvaluateContentService stuEvaluateContentService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @GetMapping("/findStuEvaluateById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public StuEvaluate findStuEvaluateById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return stuEvaluateService.findStuEvaluateById(id);
    }

    @PostMapping("/saveStuEvaluate")
    @ApiOperation(value = "保存", notes = "返回对象")
    public StuEvaluate saveStuEvaluate(
            @ApiParam(value = "对象", required = true)
            @RequestBody StuEvaluate stuEvaluate){
        stuEvaluateService.saveStuEvaluate(stuEvaluate);
        return stuEvaluate;
    }

    @PostMapping("/findStuEvaluateListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<StuEvaluate> findStuEvaluateListByCondition(
            @ApiParam(value = "对象")
            @RequestBody StuEvaluate stuEvaluate){
        List<StuEvaluate> stuEvaluateList= stuEvaluateService.findStuEvaluateListByCondition(stuEvaluate);
        stuEvaluateList.forEach(stuEvaluate1 -> {
            Date endTime1= DateUtil.parse(stuEvaluate1.getEndTime());
            Date nowDate = DateUtil.date();
           if(endTime1.getTime()> nowDate.getTime()){
               stuEvaluate1.setState("1");
           }else {
               stuEvaluate1.setState("2");
           }


        });

    return  stuEvaluateList;
    }
    @PostMapping("/findStuEvaluateCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findStuEvaluateCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody StuEvaluate stuEvaluate){
        return stuEvaluateService.findStuEvaluateCountByCondition(stuEvaluate);
    }

    @PostMapping("/updateStuEvaluate")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateStuEvaluate(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody StuEvaluate stuEvaluate){
        stuEvaluateService.updateStuEvaluate(stuEvaluate);
    }

    @GetMapping("/deleteStuEvaluate/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteStuEvaluate(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        stuEvaluateService.deleteStuEvaluate(id);
    }
    @PostMapping("/deleteStuEvaluateByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteStuEvaluateByCondition(
            @ApiParam(value = "对象")
            @RequestBody StuEvaluate stuEvaluate){
        stuEvaluateService.deleteStuEvaluateByCondition(stuEvaluate);
    }
    @PostMapping("/findOneStuEvaluateByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public StuEvaluate findOneStuEvaluateByCondition(
            @ApiParam(value = "对象")
            @RequestBody StuEvaluate stuEvaluate){
        return stuEvaluateService.findOneStuEvaluateByCondition(stuEvaluate);
    }

    @PostMapping("/findClassesHeadTeacherBySchoolId")
    @ApiOperation(value = "学生评价获取学校班级及班主任", notes = "班级教师对象")
    public  List<TeacherClassesForStuEvaluate> findClassesHeadTeacherBySchoolId(
            @ApiParam(value = "对象")
            @RequestBody String schoolId){
        return stuEvaluateService.findClassesHeadTeacherBySchoolId(schoolId);
    }

    @PostMapping("/saveStuEvaluateSendObject")
    @ApiOperation(value = "保存发送对象", notes = "返回对象")
    public void saveStuEvaluateSendObject(
            @ApiParam(value = "对象", required = true)
            @RequestBody ArrayList<StuEvaluateSendObject> sendObjectList){
        List<String> teacherIdList=new ArrayList<>();//推送缓存集合
        StuEvaluate stuEvaluate=sendObjectList.get(0).getStuEvaluate();
        String title=sendObjectList.get(0).getStuEvaluate().getTitle();
        StuEvaluate stuEvaluate1=saveStuEvaluate(stuEvaluate);
        sendObjectList.forEach(stuEvaluateSendObject -> {
            teacherIdList.add(stuEvaluateSendObject.getTeacherId());
            stuEvaluateSendObject.setStuEvaluate(stuEvaluate1);
            stuEvaluateSendObject.setSubmitNum(0);
            stuEvaluateSendObject.setSchoolId(stuEvaluate.getSchoolId());
            stuEvaluateSendObject.setCreateTime(stuEvaluateSendObject.getStuEvaluate().getCreateTime());//app端排序需要用评价的createTime排序
            stuEvaluateSendObjectService.saveStuEvaluateSendObject(stuEvaluateSendObject);
        });
        pushToTeacher(teacherIdList,title);
    }


    @GetMapping("/StuEvaluatePushByTime")
    @ApiOperation(value = "根据截止时间定时推送", notes = "没有时返回空")
    public void StuEvaluatePushByTime(){
        StuEvaluateContent stuEvaluateContent =new StuEvaluateContent();
        stuEvaluateContent.setPushState("1");
        stuEvaluateContent.setEvaluateState("2");
        List<StuEvaluateContent> stuEvaluateContentList=stuEvaluateContentService.findStuEvaluateContentListByCondition1(stuEvaluateContent);
        //处理推送
        if(stuEvaluateContentList.size()>0) {

            final Push push = Push.newBuilder(JpushApp.BMP).getSimplePush(stuEvaluateContentList.stream().map(StuEvaluateContent::getStudentId).toArray(String[]::new),"学生评价","老师给你写评语啦，快点击查看吧。",Extras.BMP_STUDENT_EVALUATE);
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));

            //更新推送状态
            stuEvaluateContentList.forEach(stuEvaluateContent1 -> {
                stuEvaluateContent1.setPushState("2");
                stuEvaluateContentService.updateStuEvaluateContent(stuEvaluateContent1);
            });
        }
    }

    public void pushToTeacher(List<String>teacherIdList,String title){
        if(teacherIdList.size()>0){
            final Push push = Push.newBuilder(JpushApp.TAP).getSimplePush(teacherIdList.toArray(new String[teacherIdList.size()]),"学生评价",title+"已出，点击详情查看。",Extras.TAP_EVALUATE_TO_TEACHER);
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
        }

    }

    @PostMapping("/moveStuEvaluateToHistory")
    @ApiOperation(value = "将毕业生学生评价数据移动到历史数据库", notes = "返回对象")
    public void moveStuEvaluateToHistory(
            @ApiParam(value = "对象", required = true)
            @RequestBody HistoryPojo historyPojo){
        stuEvaluateService.moveStuEvaluateToHistory(historyPojo);
    }



}
