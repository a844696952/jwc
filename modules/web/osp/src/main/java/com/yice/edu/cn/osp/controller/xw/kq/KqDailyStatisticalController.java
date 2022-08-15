package com.yice.edu.cn.osp.controller.xw.kq;

import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.xw.kq.KqDailyStatistical;
import com.yice.edu.cn.common.pojo.xw.kq.KqMonthStatistical;
import com.yice.edu.cn.common.pojo.xw.kq.KqOriginalData;
import com.yice.edu.cn.common.util.StringUtils;
import com.yice.edu.cn.osp.service.jw.student.StudentService;
import com.yice.edu.cn.osp.service.xw.kq.KqDailyStatisticalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/kqDailyStatistical")
@Api(value = "/kqDailyStatistical", description = "考勤日统计表模块")
public class KqDailyStatisticalController {
    @Autowired
    private KqDailyStatisticalService kqDailyStatisticalService;
    @Autowired
    private StudentService studentService;

    @PostMapping("/saveKqDailyStatistical")
    @ApiOperation(value = "保存考勤日统计表对象", notes = "返回保存好的考勤日统计表对象", response = KqDailyStatistical.class)
    public ResponseJson saveKqDailyStatistical(
            @ApiParam(value = "考勤日统计表对象", required = true)
            @RequestBody KqDailyStatistical kqDailyStatistical) {
        kqDailyStatistical.setSchoolId(mySchoolId());
        KqDailyStatistical s = kqDailyStatisticalService.saveKqDailyStatistical(kqDailyStatistical);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findKqDailyStatisticalById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找考勤日统计表", notes = "返回响应对象", response = KqDailyStatistical.class)
    public ResponseJson findKqDailyStatisticalById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        KqDailyStatistical kqDailyStatistical = kqDailyStatisticalService.findKqDailyStatisticalById(id);
        return new ResponseJson(kqDailyStatistical);
    }

    @PostMapping("/update/updateKqDailyStatistical")
    @ApiOperation(value = "修改考勤日统计表对象", notes = "返回响应对象")
    public ResponseJson updateKqDailyStatistical(
            @ApiParam(value = "被修改的考勤日统计表对象,对象属性不为空则修改", required = true)
            @RequestBody KqDailyStatistical kqDailyStatistical) {
        kqDailyStatisticalService.updateKqDailyStatistical(kqDailyStatistical);
        return new ResponseJson();
    }

    @GetMapping("/look/lookKqDailyStatisticalById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找考勤日统计表", notes = "返回响应对象", response = KqDailyStatistical.class)
    public ResponseJson lookKqDailyStatisticalById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        KqDailyStatistical kqDailyStatistical = kqDailyStatisticalService.findKqDailyStatisticalById(id);
        return new ResponseJson(kqDailyStatistical);
    }

    @PostMapping("/find/findKqDailyStatisticalsByCondition")
    @ApiOperation(value = "根据条件查找考勤日统计表", notes = "返回响应对象", response = KqDailyStatistical.class)
    public ResponseJson findKqDailyStatisticalsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqDailyStatistical kqDailyStatistical) {
        kqDailyStatistical.setSchoolId(mySchoolId());
        List<KqDailyStatistical> data = kqDailyStatisticalService.findKqDailyStatisticalParentListByCondition(kqDailyStatistical);
        long count = kqDailyStatisticalService.findKqDailyStatisticalCountByCondition(kqDailyStatistical);
        return new ResponseJson(data, count);
    }

    @PostMapping("/find/findOneKqDailyStatisticalByCondition")
    @ApiOperation(value = "根据条件查找单个考勤日统计表,结果必须为单条数据", notes = "没有时返回空", response = KqDailyStatistical.class)
    public ResponseJson findOneKqDailyStatisticalByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody KqDailyStatistical kqDailyStatistical) {
        KqDailyStatistical one = kqDailyStatisticalService.findOneKqDailyStatisticalByCondition(kqDailyStatistical);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteKqDailyStatistical/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteKqDailyStatistical(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        kqDailyStatisticalService.deleteKqDailyStatistical(id);
        return new ResponseJson();
    }


    @PostMapping("/find/findKqDailyStatisticalListByCondition")
    @ApiOperation(value = "根据条件查找考勤日统计表列表", notes = "返回响应对象,不包含总条数", response = KqDailyStatistical.class)
    public ResponseJson findKqDailyStatisticalListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqDailyStatistical kqDailyStatistical) {
        kqDailyStatistical.setSchoolId(mySchoolId());
        List<KqDailyStatistical> data = kqDailyStatisticalService.findKqDailyStatisticalListByCondition(kqDailyStatistical);
        return new ResponseJson(data);
    }

    @PostMapping("/find/findKqMonthStatisticalListByCondition")
    @ApiOperation(value = "月统计", notes = "返回考勤月统计表列表")
    public ResponseJson findKqMonthStatisticalListByCondition(
            @ApiParam(value = "考勤日统计表对象")
            @RequestBody KqDailyStatistical kqDailyStatistical) {
        if (StrUtil.isEmpty(kqDailyStatistical.getKqDate())){
            return new ResponseJson(false,"请先选择考勤月份");
        }
        kqDailyStatistical.setSchoolId(mySchoolId());
        Student student = new Student();
        student.setSchoolId(mySchoolId());
        student.setClassesId(kqDailyStatistical.getClassId());
        Pager pager = new Pager();
       int page = kqDailyStatistical.getPager().getPage();
       int pageSize= kqDailyStatistical.getPager().getPageSize();
        pager.setPage(page);
        pager.setPageSize(pageSize);
        if (StrUtil.isNotEmpty(kqDailyStatistical.getName())){
            student.setName(StringUtils.specialCharacterConvert(kqDailyStatistical.getName()));
            pager.setLike("name");
        }
        if (kqDailyStatistical.getLateStatus()!=null||kqDailyStatistical.getLeaveEarlyStatus()!=null||kqDailyStatistical.getMissStatus()!=null){
            pager.setPaging(false);
        }
        student.setPager(pager);
        List<Student> students = studentService.findStudentListByCondition(student);
        Long count = studentService.findStudentCountByCondition(student);
        kqDailyStatistical.setStudentList(students);
        List<KqMonthStatistical> kqMonthStatisticals = kqDailyStatisticalService.findKqMonthStatisticalListByCondition(kqDailyStatistical);
        List<KqMonthStatistical> kqMonthOrtherMessge = kqDailyStatisticalService.findKqMonthStatisticalListAll(kqDailyStatistical);
        kqMonthOrtherMessge.get(0).setStudentNum(count.intValue());
        if (kqDailyStatistical.getLateStatus()!=null||kqDailyStatistical.getLeaveEarlyStatus()!=null||kqDailyStatistical.getMissStatus()!=null){
            kqMonthOrtherMessge.get(0).setStudentNum(kqMonthStatisticals.size()) ;
            kqMonthStatisticals=  kqMonthStatisticals.stream().skip(kqDailyStatistical.getPager().getBeginRow()).limit(kqDailyStatistical.getPager().getPageSize()).collect(Collectors.toList());
        }

        return new ResponseJson(kqMonthStatisticals, kqMonthOrtherMessge);
    }

    @PostMapping("/find/inTimeCountByRules")
    @ApiOperation(value = "即时统计", notes = "必传学校id  1特殊 2 普通")
    public ResponseJson inTimeCountByRules(
            @ApiParam(value = "考勤打卡原始记录表对象")
            @RequestBody KqOriginalData kqOriginalData) {
        KqDailyStatistical data = kqDailyStatisticalService.inTimeCountByRules(kqOriginalData);
        return new ResponseJson(data);
    }

    /*@PostMapping("/find/inTimeCountByRulesForClass")
    @ApiOperation(value = "即时统计 班級", notes = "必传学校id  1特殊 2 普通")
    public ResponseJson inTimeCountByRulesForClass(
            @ApiParam(value = "考勤打卡原始记录表对象")
            @RequestBody KqOriginalData kqOriginalData) {
        List<KqDailyStatistical> data = kqDailyStatisticalService.inTimeCountByRulesForClass(kqOriginalData);
        return new ResponseJson(data);
    }*/


}
