package com.yice.edu.cn.tap.controller.dm.kq;

import cn.hutool.core.collection.CollUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import com.yice.edu.cn.common.pojo.dm.kq.ProtectedStudent;
import com.yice.edu.cn.common.util.jmessage.utils.StringUtils;
import com.yice.edu.cn.tap.service.dm.kq.EccStudentKqService;
import com.yice.edu.cn.tap.service.schoolYear.SchoolYearService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;


/**
 * @author dengfengfeng
 */
@RequestMapping("/studentKq")
@RestController
@Api(value = "/studentKq",description = "云班牌-人脸打卡")
public class EccStudentKqController {

    @Autowired
    private EccStudentKqService eccStudentKqService;

    @Autowired
    private SchoolYearService schoolYearService;


    @ApiOperation(value = "考勤-日统计",response = ProtectedStudent.class)
    @PostMapping("/statistics/daily")
    public ResponseJson daily(@RequestBody ProtectedStudent protectedStudent){
         DmClassCard dmClassCard =new DmClassCard();
         dmClassCard.setSchoolId(mySchoolId());
         if(StringUtils.isNotEmpty(protectedStudent.getClassId())){
             dmClassCard.setClassId(protectedStudent.getClassId());
         }
         if(org.apache.commons.lang3.StringUtils.isEmpty(protectedStudent.getRecordDate())){
            return new ResponseJson(false,"recordDate为空");
        }
         List<ProtectedStudent> todayStatistics = eccStudentKqService.getKqStatistics(dmClassCard,protectedStudent.getRecordDate());
        if(CollUtil.isNotEmpty(todayStatistics) && Objects.nonNull(protectedStudent.getBussType())){
            if( Objects.nonNull(todayStatistics.get(0).getKqState()) && (todayStatistics.get(0).getKqState() == 5 ||  todayStatistics.get(0).getKqState()== 6 )){
                return new ResponseJson(false,"该天无打卡记录");
            }
            List<ProtectedStudent> collect=null;
            if(protectedStudent.getBussType() == 1){
                //包括请假和未到
                collect= todayStatistics.stream().filter(x -> x.getKqState() == 1 || x.getKqState() == 4).collect(Collectors.toList());
            }else if(protectedStudent.getBussType() ==2){
                collect=todayStatistics.stream().filter(x -> x.getKqState() == 2 || x.getKqState() == 3).collect(Collectors.toList());
            }
            String gradeInfo=CollUtil.isNotEmpty(collect)?String.format("%1$s(%2$s)班",collect.get(0).getGradeName(),collect.get(0).getClassesNumber()):"";
            return new ResponseJson(collect,gradeInfo);
        }
        else{
            return new ResponseJson(todayStatistics);
        }
    }

    @ApiOperation(value = "考勤-个人按时间段统计",notes = "返回统计列表")
    @PostMapping("/findKqListByStudentId")
    public ResponseJson findKqListByStudentId(@RequestBody ProtectedStudent protectedStudent){
        if(Objects.nonNull(protectedStudent)){
            List<ProtectedStudent> kqListByStudentId = eccStudentKqService.findKqListByStudentId(protectedStudent);
            return new ResponseJson(kqListByStudentId);
        }
        return new ResponseJson(new ArrayList<>());
    }

    @ApiOperation(value = "考勤-按时间段统计",notes = "返回统计列表")
    @PostMapping("/findStudentKqByCondition")
    public ResponseJson findStudentKqByCondition(@RequestBody ProtectedStudent protectedStudent){
        if(Objects.nonNull(protectedStudent)){
         return   new ResponseJson(eccStudentKqService.findStudentKqByCondition(protectedStudent));
        }
        return new ResponseJson(new ArrayList<>());
    }

    @GetMapping("/findMaxSchoolYear")
    @ApiOperation(value = "查询当前学期时间段", notes = "返回响应对象")
    public ResponseJson findMaxSchoolYear(){
        String schoolId=mySchoolId();
        if(org.apache.commons.lang3.StringUtils.isNotBlank(schoolId)){
            String[] maxSchoolYear = schoolYearService.findMaxSchoolYear(schoolId);
            return new ResponseJson(maxSchoolYear);
        }
        return new ResponseJson();
    }
}
