package com.yice.edu.cn.osp.controller.jy.cloudClassroom.cloudCourse;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourse;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudSubCourse;
import com.yice.edu.cn.common.pojo.cc.otherSchoolAccount.OtherSchoolAccount;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherService;
import com.yice.edu.cn.osp.service.jy.cloudClassroom.cloudCourse.CloudCourseService;
import com.yice.edu.cn.osp.service.jy.cloudClassroom.cloudCourse.CloudSubCourseService;
import com.yice.edu.cn.osp.service.jy.cloudClassroom.otherSchoolAccount.OtherSchoolAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.*;

@RestController
@RequestMapping("/cloudCourse")
@Api(value = "/cloudCourse",description = "云课程模块")
public class CloudCourseController {
    @Autowired
    private CloudCourseService cloudCourseService;
    @Autowired
    private CloudSubCourseService cloudSubCourseService;
    @Autowired
    private OtherSchoolAccountService otherSchoolAccountService;
    @Autowired
    private TeacherService teacherService;

    @PostMapping("/saveCloudCourse")
    @ApiOperation(value = "保存云课程对象", notes = "返回保存好的云课程对象", response= CloudCourse.class)
    public ResponseJson saveCloudCourse(
            @ApiParam(value = "云课程对象", required = true)
            @RequestBody CloudCourse cloudCourse){
        cloudCourse.setCreateTeacher(currentTeacher());
        cloudCourse.setSchoolId(mySchoolId());
        List<CloudSubCourse> cloudSubCourseList = cloudCourse.getCloudSubCourseList();
        assembleCloudCourse(cloudCourse, cloudSubCourseList);
        CloudCourse s=cloudCourseService.saveCloudCourse(cloudCourse);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findCloudCourseById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找云课程", notes = "返回响应对象", response=CloudCourse.class)
    public ResponseJson findCloudCourseById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        CloudCourse cloudCourse = getCloudCourse(id);
        return new ResponseJson(cloudCourse);
    }

    @PostMapping("/update/updateCloudCourse")
    @ApiOperation(value = "修改云课程对象", notes = "返回响应对象")
    public ResponseJson updateCloudCourse(
            @ApiParam(value = "被修改的云课程对象,对象属性不为空则修改", required = true)
            @RequestBody CloudCourse cloudCourse){
        //求差集
        List<CloudSubCourse> cloudSubCourseList = cloudCourse.getCloudSubCourseList();
        List<String> cloudSubCourseIdList = cloudSubCourseList.stream().filter(cloudSubCourse -> cloudSubCourse.getId()!=null).map(cloudSubCourse->cloudSubCourse.getId()).collect(Collectors.toList());
        List<String> beforeCloudSubCourseIdList = cloudSubCourseService.findCloudSubCourseIdListByCloudCourseId(cloudCourse.getId());
        beforeCloudSubCourseIdList.removeAll(cloudSubCourseIdList);
        //删除
        cloudSubCourseService.deleteCloudSubCourseByIds(beforeCloudSubCourseIdList);
        assembleCloudCourse(cloudCourse, cloudSubCourseList);
        cloudCourseService.updateCloudCourse(cloudCourse);
        return new ResponseJson();
    }

    @GetMapping("/look/lookCloudCourseById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找云课程", notes = "返回响应对象", response=CloudCourse.class)
    public ResponseJson lookCloudCourseById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        CloudCourse cloudCourse = getCloudCourse(id);
        return new ResponseJson(cloudCourse);
    }

    @PostMapping("/findCloudCoursesByCondition")
    @ApiOperation(value = "根据条件查找云课程(pager rangeArray name)", notes = "返回响应对象", response=CloudCourse.class)
    public ResponseJson findCloudCoursesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CloudCourse cloudCourse){
        Teacher queryTeacher = new Teacher();
        queryTeacher.setId(myId());
        cloudCourse.setCreateTeacher(queryTeacher);
        cloudCourse.getPager().setRangeArray(cloudCourse.getRangeArray());
        List<CloudCourse> data=cloudCourseService.findCloudCourseListByCondition(cloudCourse);
        //根据子课程是否在上课中或已结束状态来判断课程是否可删除
        data.forEach(item->{
            CloudSubCourse queryCloudSubCourse = new CloudSubCourse();
            CloudCourse queryCloudCourse = new CloudCourse();
            queryCloudCourse.setId(item.getId());
            queryCloudSubCourse.setCloudCourse(queryCloudCourse);
            long count = cloudSubCourseService.findOnGoingOrFinishCloudSubCourseCountByCondition(queryCloudSubCourse);
            if(count>0){
                item.setDeletabled(false);
            }else{
                item.setDeletabled(true);
            }
        });
        long count=cloudCourseService.findCloudCourseCountByCondition(cloudCourse);
        return new ResponseJson(data,count);
    }

    @PostMapping("/findCloudCoursesByConditionOther")
    @ApiOperation(value = "根据条件查找云课程(pager rangeArray name createName)", notes = "返回响应对象", response=CloudCourse.class)
    public ResponseJson findCloudCoursesByConditionOther(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CloudCourse cloudCourse){
        Teacher queryTeacher = new Teacher();
        queryTeacher.setId(myId());
        CloudSubCourse cloudSubCourse = new CloudSubCourse();
        cloudSubCourse.setTeacher(queryTeacher);
        //查询当前老师所教课程的主课程id
        List<String>cloudCourseIdList = cloudSubCourseService.findCloudCourseIdListByTeacher(cloudSubCourse);
        if(CollUtil.isEmpty(cloudCourseIdList)){
            return new ResponseJson(CollUtil.newArrayList(),0);
        }
        cloudCourse.setCloudCourseIdList(cloudCourseIdList);
        cloudCourse.setCreateTeacher(queryTeacher);
        cloudCourse.getPager().setRangeArray(cloudCourse.getRangeArray());
        List<CloudCourse> data=cloudCourseService.findCloudCoursesByConditionOther(cloudCourse);
        long count=cloudCourseService.findCloudCourseCountByConditionOther(cloudCourse);
        return new ResponseJson(data,count);
    }

    @PostMapping("/findCloudCoursesByConditionMine")
    @ApiOperation(value = "根据条件查找云课程(pager rangeArray name)", notes = "返回响应对象", response=CloudCourse.class)
    public ResponseJson findCloudCoursesByConditionMine(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CloudCourse cloudCourse){
        Teacher queryTeacher = new Teacher();
        queryTeacher.setId(myId());
        CloudSubCourse cloudSubCourse = new CloudSubCourse();
        cloudSubCourse.setTeacher(queryTeacher);
        //查询当前老师所教课程的主课程id
        List<String>cloudCourseIdList = cloudSubCourseService.findCloudCourseIdListByTeacher(cloudSubCourse);
        if(CollUtil.isEmpty(cloudCourseIdList)){
            return new ResponseJson(CollUtil.newArrayList(),0);
        }
        cloudCourse.setCloudCourseIdList(cloudCourseIdList);
        cloudCourse.setCreateTeacher(queryTeacher);
        cloudCourse.getPager().setRangeArray(cloudCourse.getRangeArray());
        List<CloudCourse> data=cloudCourseService.findCloudCoursesByConditionMine(cloudCourse);
        //根据子课程是否在上课中或已结束状态来判断课程是否可删除
        data.forEach(item->{
            CloudSubCourse queryCloudSubCourse = new CloudSubCourse();
            CloudCourse queryCloudCourse = new CloudCourse();
            queryCloudCourse.setId(item.getId());
            queryCloudSubCourse.setCloudCourse(queryCloudCourse);
            long count = cloudSubCourseService.findOnGoingOrFinishCloudSubCourseCountByCondition(queryCloudSubCourse);
            if(count>0){
                item.setDeletabled(false);
            }else{
                item.setDeletabled(true);
            }
        });
        long count=cloudCourseService.findCloudCourseCountByConditionMine(cloudCourse);
        return new ResponseJson(data,count);
    }

    @PostMapping("/findOneCloudCourseByCondition")
    @ApiOperation(value = "根据条件查找单个云课程,结果必须为单条数据", notes = "没有时返回空", response=CloudCourse.class)
    public ResponseJson findOneCloudCourseByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody CloudCourse cloudCourse){
        CloudCourse one=cloudCourseService.findOneCloudCourseByCondition(cloudCourse);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteCloudCourse/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteCloudCourse(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        cloudCourseService.deleteCloudCourse(id);
        CloudSubCourse queryCloudSubCourse = new CloudSubCourse();
        CloudCourse queryCloudCourse = new CloudCourse();
        queryCloudCourse.setId(id);
        queryCloudSubCourse.setCloudCourse(queryCloudCourse);
        cloudSubCourseService.deleteCloudSubCourseByCondition(queryCloudSubCourse);
        return new ResponseJson();
    }

    @PostMapping("/findCloudCourseListByCondition")
    @ApiOperation(value = "根据条件查找云课程列表", notes = "返回响应对象,不包含总条数", response=CloudCourse.class)
    public ResponseJson findCloudCourseListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CloudCourse cloudCourse){
        List<CloudCourse> data=cloudCourseService.findCloudCourseListByCondition(cloudCourse);
        return new ResponseJson(data);
    }
    @PostMapping("/ignore/findOtherSchoolAccountsByCondition")
    @ApiOperation(value = "根据条件查找外校账号", notes = "返回响应对象", response= OtherSchoolAccount.class)
    public ResponseJson findOtherSchoolAccountsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody OtherSchoolAccount otherSchoolAccount){
        otherSchoolAccount.setSchoolId(mySchoolId());
        otherSchoolAccount.getPager().addExcludes("password").setRangeField("expireDate").setRangeArray(new String[]{DateUtil.today(),null});;
        List<OtherSchoolAccount> data=otherSchoolAccountService.findOtherSchoolAccountListByCondition(otherSchoolAccount);
        long count=otherSchoolAccountService.findOtherSchoolAccountCountByCondition(otherSchoolAccount);
        return new ResponseJson(data,count);
    }

    @PostMapping("/ignore/querySchoolTeacher")
    @ApiOperation(value = "分页查询该校的在职教师", notes = "返回响应对象", response=CloudCourse.class)
    private ResponseJson querySchoolTeacher(
            @ApiParam(value = "属性不为空则作为条件查询，分页必传")
            @RequestBody Teacher teacher){
        teacher.setSchoolId(mySchoolId());
        teacher.getPager().addExcludes("password");
        teacher.setPersonType(Constant.PERSON_TYPE.TEACHER);
        teacher.setStatus(Constant.STATUS.TEACHER_ON_LINE);
        List<Teacher> data=teacherService.findTeacherListByCondition(teacher);
        long total =teacherService.findTeacherCountByCondition(teacher);
        return new ResponseJson(data,total);
    }

    private CloudCourse getCloudCourse(String id) {
        CloudCourse cloudCourse = cloudCourseService.findCloudCourseById(id);
        //查询子课程
        CloudSubCourse queryCloudSubCourse = new CloudSubCourse();
        CloudCourse queryCloudCourse = new CloudCourse();
        queryCloudCourse.setId(id);
        queryCloudSubCourse.setCloudCourse(queryCloudCourse);
        List<CloudSubCourse> cloudSubCourseList = cloudSubCourseService.findCloudSubCourseListByCondition(queryCloudSubCourse);
        cloudCourse.setCloudSubCourseList(cloudSubCourseList);
        return cloudCourse;
    }

    //saveCloudCourse update/updateCloudCourse 课程装载
    private void assembleCloudCourse(CloudCourse cloudCourse, List<CloudSubCourse> cloudSubCourseList) {
        //由于子课程时间区间不能重叠,这里取子课程开始时间排序
        cloudSubCourseList.sort(Comparator.comparing(CloudSubCourse::getStartTime));
        long d = 0;
        for (CloudSubCourse cloudSubCourse : cloudSubCourseList) {
            long between = DateUtil.between(DateUtil.parse(cloudSubCourse.getStartTime()), DateUtil.parse(cloudSubCourse.getEndTime()), DateUnit.MINUTE);
            d += between;
        }
        String duration = String.format("%s小时%d分钟", d / 60, d % 60);
        cloudCourse.setDuration(duration);
        //提取第一门子课程的开始时间作为母课程的开始时间,最后一门子课程的结束时间作为结束时间
        cloudCourse.setStartTime(cloudSubCourseList.get(0).getStartTime());
        cloudCourse.setEndTime(cloudSubCourseList.get(cloudSubCourseList.size() - 1).getEndTime());
        cloudCourse.setCourseCount((long) cloudSubCourseList.size());
        cloudCourse.setTeacherCount((long) (cloudCourse.getListenTeachers().size() + cloudCourse.getOtherSchoolAccounts().size()));
        //子课程加上学校id
        cloudSubCourseList.forEach(cloudSubCourse -> cloudSubCourse.setSchoolId(mySchoolId()));
    }


}
