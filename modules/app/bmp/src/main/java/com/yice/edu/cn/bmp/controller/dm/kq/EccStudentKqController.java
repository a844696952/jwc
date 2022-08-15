package com.yice.edu.cn.bmp.controller.dm.kq;

import cn.hutool.core.collection.CollUtil;
import com.yice.edu.cn.bmp.service.dm.kq.EccStudentKqService;
import com.yice.edu.cn.bmp.service.school.SchoolYearService;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import com.yice.edu.cn.common.pojo.dm.kq.ProtectedStudent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.mySchoolId;
import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.myStudentId;

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
        DmClassCard dmClassCard=new DmClassCard();
        dmClassCard.setStudentId(myStudentId());
        dmClassCard.setSchoolId(mySchoolId());
        if(org.apache.commons.lang3.StringUtils.isEmpty(protectedStudent.getRecordDate())){
            return new ResponseJson(false,"recordDate为空");
        }
        final List<ProtectedStudent> todayStatistics = eccStudentKqService.getKqStatistics(dmClassCard,protectedStudent.getRecordDate());
        if(CollUtil.isNotEmpty(todayStatistics) && Objects.nonNull(protectedStudent.getBussType())){
            List<ProtectedStudent> collect=null;
            if(protectedStudent.getBussType() == 1){
                //包括请假和未到
                collect= todayStatistics.stream().filter(x -> x.getKqState() == 1 || x.getKqState() == 4).collect(Collectors.toList());
            }else if(protectedStudent.getBussType() ==2){
                collect=todayStatistics.stream().filter(x -> x.getKqState() == 2 || x.getKqState() == 3).collect(Collectors.toList());
            }
            return new ResponseJson(collect);
        }
        return new ResponseJson(new ArrayList<>());
    }

    @ApiOperation(value = "考勤-个人按时间段统计",notes = "返回统计列表")
    @PostMapping("/findKqListByStudentId")
    public ResponseJson findKqListByStudentId(@RequestBody ProtectedStudent protectedStudent){
        if(Objects.nonNull(protectedStudent)){
            protectedStudent.setSchoolId(mySchoolId());
            protectedStudent.setStudentId(myStudentId());
            List<ProtectedStudent> kqListByStudentId = eccStudentKqService.findKqListByStudentId(protectedStudent);
            return new ResponseJson(kqListByStudentId);
        }
        return new ResponseJson(new ArrayList<>());
    }

    @ApiOperation(value = "考勤-按时间段统计",notes = "返回统计列表")
    @PostMapping("/findStudentKqByCondition")
    public ResponseJson findStudentKqByCondition(@RequestBody ProtectedStudent protectedStudent){
        if(Objects.nonNull(protectedStudent)){
         protectedStudent.setSchoolId(mySchoolId());
         protectedStudent.setStudentId(myStudentId());
         return   new ResponseJson(eccStudentKqService.findStudentKqByCondition(protectedStudent));
        }
        return new ResponseJson(new ArrayList<>());
    }

    @GetMapping("/findMaxSchoolYear")
    @ApiOperation(value = "查询当前学期时间段", notes = "返回响应对象")
    public ResponseJson findMaxSchoolYear(){
        String schoolId = mySchoolId();
        if(StringUtils.isNotBlank(schoolId)){
            String[] maxSchoolYear = schoolYearService.findMaxSchoolYear(schoolId);
            return new ResponseJson(maxSchoolYear);
        }
        return new ResponseJson();
    }
}
