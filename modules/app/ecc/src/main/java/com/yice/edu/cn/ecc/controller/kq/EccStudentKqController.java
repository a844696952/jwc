package com.yice.edu.cn.ecc.controller.kq;

import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import com.yice.edu.cn.common.pojo.dm.kq.EccStudentFace;
import com.yice.edu.cn.common.pojo.dm.kq.ProtectedStudent;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.ecc.service.kq.EccStudentFaceService;
import com.yice.edu.cn.ecc.service.kq.EccStudentKqService;
import com.yice.edu.cn.ecc.service.school.SchoolYearService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.yice.edu.cn.ecc.interceptor.LoginInterceptor.currentDmClassCard;
import static com.yice.edu.cn.ecc.interceptor.LoginInterceptor.mySchoolId;

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
    private EccStudentFaceService eccStudentFaceService;


    @Autowired
    private SchoolYearService schoolYearService;


    @ApiOperation(value = "考勤时间点")
    @GetMapping("/kqTime")
    public ResponseJson kqTime(){
        String kqSettingTiming = eccStudentKqService.getKqBeginTime(mySchoolId());
        if(StringUtils.isBlank(kqSettingTiming)){
            return new ResponseJson(false,"该学校未设置课程表,请联系学校管理员");
        }
        return new ResponseJson(kqSettingTiming);
    }

    @ApiOperation(value = "人脸打卡")
    @PostMapping("/dk")
    public ResponseJson dk(@RequestBody @Validated(GroupOne.class) EccStudentFace studentFace){
        final DmClassCard dmClassCard = currentDmClassCard();
        studentFace.setClassesId(dmClassCard.getClassId());
        studentFace.setGradeId(dmClassCard.getGradeId());
        EccStudentFace eccStudentFace = eccStudentFaceService.findOneEccStudentFaceByCondition(studentFace);
        if(eccStudentFace == null){
            return new ResponseJson(false,"打卡失败,该学生没有录入人脸");
        }
        String errMsg = eccStudentKqService.dkRecord(eccStudentFace.getStudent());
        if (StrUtil.isNotBlank(errMsg)) {
            return new ResponseJson(false, errMsg);
        }
        return new ResponseJson("打卡成功");
    }



    @ApiOperation(value = "考勤-日统计",response = ProtectedStudent.class)
    @GetMapping("/statistics/daily/{date}")
    public ResponseJson daily(@PathVariable("date")String date){
        final DmClassCard dmClassCard = currentDmClassCard();
        final List<ProtectedStudent> todayStatistics = eccStudentKqService.getKqStatistics(dmClassCard,date);
        return new ResponseJson(todayStatistics);
    }

    @ApiOperation(value = "考勤-个人按时间段统计",notes = "返回统计列表")
    @PostMapping("/findKqListByStudentId")
    public ResponseJson findKqListByStudentId(@RequestBody ProtectedStudent protectedStudent){
        if(Objects.nonNull(protectedStudent)){
            final DmClassCard dmClassCard = currentDmClassCard();
            protectedStudent.setSchoolId(dmClassCard.getSchoolId());
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
        final DmClassCard dmClassCard = currentDmClassCard();
        if(StringUtils.isNotBlank(dmClassCard.getSchoolId())){
            String[] maxSchoolYear = schoolYearService.findMaxSchoolYear(dmClassCard.getSchoolId());
            return new ResponseJson(maxSchoolYear);
        }
        return new ResponseJson();
    }




}
