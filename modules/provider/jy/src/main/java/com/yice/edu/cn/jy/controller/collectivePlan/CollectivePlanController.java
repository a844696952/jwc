package com.yice.edu.cn.jy.controller.collectivePlan;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jy.collectivePlan.CollectivePlan;
import com.yice.edu.cn.common.pojo.jy.collectivePlan.CollectivePlanTeacher;
import com.yice.edu.cn.common.pojo.jy.collectivePlan.TeacherCollection;
import com.yice.edu.cn.common.util.studyTime.GetStudyTimeUtils;
import com.yice.edu.cn.jy.service.collectivePlan.CollectivePlanService;
import com.yice.edu.cn.jy.service.collectivePlan.CollectivePlanTeacherService;
import com.yice.edu.cn.jy.service.collectivePlan.TeacherCollectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/collectivePlan")
@Api(value = "/collectivePlan",description = "集体备课表模块")
public class CollectivePlanController {
    @Autowired
    private CollectivePlanService collectivePlanService;
    @Autowired
    private CollectivePlanTeacherService collectivePlanTeacherService;
    @Autowired
    private TeacherCollectionService teacherCollectionService;
    @Autowired
    private SequenceId sequenceId;

    @GetMapping("/findCollectivePlanById/{id}")
    @ApiOperation(value = "通过id查找集体备课表", notes = "返回集体备课表对象")
    public CollectivePlan findCollectivePlanById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        return collectivePlanService.findCollectivePlanById(id);
    }

    @PostMapping("/saveCollectivePlan")
    @ApiOperation(value = "保存集体备课表", notes = "返回集体备课表对象")
    public CollectivePlan saveCollectivePlan(
            @ApiParam(value = "集体备课表对象", required = true)
            @RequestBody CollectivePlan collectivePlan) {
        List<CollectivePlanTeacher> li =  new ArrayList<CollectivePlanTeacher>();
        collectivePlanService.saveCollectivePlan(collectivePlan);
        //将加入讨论组的老师id 加入中间表，创建的主备人默认进组
        collectivePlan.getTeacherIdss().add(collectivePlan.getTeacherId());
           collectivePlan.getTeacherIdss().forEach(e -> {
               CollectivePlanTeacher ct = new CollectivePlanTeacher();
               ct.setCollectivePlanId(collectivePlan.getId());
               ct.setTeacherId(e);
               ct.setId(sequenceId.nextId());
               li.add(ct);
           });
        collectivePlanTeacherService.batchSaveCollectivePlanTeacher(li);

        return collectivePlan;
    }

    @PostMapping("/findCollectivePlanListByCondition")
    @ApiOperation(value = "根据条件查找集体备课表列表", notes = "返回集体备课表列表")
    public List<CollectivePlan> findCollectivePlanListByCondition(
            @ApiParam(value = "集体备课表对象")
            @RequestBody CollectivePlan collectivePlan) {
        return collectivePlanService.findCollectivePlanListByCondition(collectivePlan);
    }

    @PostMapping("/findCollectivePlanCountByCondition")
    @ApiOperation(value = "根据条件查找集体备课表列表个数", notes = "返回集体备课表总个数")
    public long findCollectivePlanCountByCondition(
            @ApiParam(value = "集体备课表对象")
            @RequestBody CollectivePlan collectivePlan) {
        return collectivePlanService.findCollectivePlanCountByCondition(collectivePlan);
    }

    @PostMapping("/updateCollectivePlan")
    @ApiOperation(value = "修改集体备课表", notes = "集体备课表对象必传")
    public void updateCollectivePlan(
            @ApiParam(value = "集体备课表对象,对象属性不为空则修改", required = true)
            @RequestBody CollectivePlan collectivePlan) {
              List<CollectivePlanTeacher> li= new ArrayList<>();
        //将加入讨论组的老师id 加入中间表
        if(collectivePlan.getTeacherIdss().size()>0) {
            //将原有组的所有老师删除，重新组队
            CollectivePlanTeacher c = new CollectivePlanTeacher();
            c.setCollectivePlanId(collectivePlan.getId());
            collectivePlanTeacherService.deleteCollectivePlanTeacherByCondition(c);
            collectivePlan.getTeacherIdss().forEach(e -> {
                CollectivePlanTeacher ct = new CollectivePlanTeacher();
                ct.setCollectivePlanId(collectivePlan.getId());
                ct.setTeacherId(e);
                ct.setId(sequenceId.nextId());
                li.add(ct);
            });
            collectivePlanTeacherService.batchSaveCollectivePlanTeacher(li);
        }
        collectivePlanService.updateCollectivePlan(collectivePlan);
    }

    @GetMapping("/deleteCollectivePlan/{id}")
    @ApiOperation(value = "通过id删除集体备课表")
    public void deleteCollectivePlan(
            @ApiParam(value = "集体备课表对象", required = true)
            @PathVariable String id) {
        //删除资源
        collectivePlanService.deleteLessonsFileById(id);
        //删除教案
        collectivePlanService.deleteTeamTeachingPlanById(id);
        //删除讨论组中的老师
        CollectivePlanTeacher c = new CollectivePlanTeacher();
        c.setCollectivePlanId(id);
        collectivePlanTeacherService.deleteCollectivePlanTeacherByCondition(c);
        //删除讨论组中的教案
        TeacherCollection teacherCollection = new TeacherCollection();
        teacherCollection.setCollectionPlanId(id);
        teacherCollectionService.deleteTeacherCollectionByCondition(teacherCollection);
        //删除一个讨论组
        collectivePlanService.deleteCollectivePlan(id);

    }

    @PostMapping("/deleteCollectivePlanByCondition")
    @ApiOperation(value = "根据条件删除集体备课表")
    public void deleteCollectivePlanByCondition(
            @ApiParam(value = "集体备课表对象")
            @RequestBody CollectivePlan collectivePlan) {
        collectivePlanService.deleteCollectivePlanByCondition(collectivePlan);
    }

    @PostMapping("/findOneCollectivePlanByCondition")
    @ApiOperation(value = "根据条件查找单个集体备课表,结果必须为单条数据", notes = "返回单个集体备课表,没有时为空")
    public CollectivePlan findOneCollectivePlanByCondition(
            @ApiParam(value = "集体备课表对象")
            @RequestBody CollectivePlan collectivePlan) {
        return collectivePlanService.findOneCollectivePlanByCondition(collectivePlan);
    }

    @PostMapping("/findCollectivePlanByPlanName")
    @ApiOperation(value = "根据条件查找讨论组是否重名", notes = "返回集体备课表列表")
    public List<CollectivePlan> findCollectivePlanByPlanName(
            @ApiParam(value = "集体备课表对象")
            @RequestBody CollectivePlan collectivePlan) {
        return collectivePlanService.findCollectivePlanByPlanName(collectivePlan);
    }

    //查询我创建的备课组
    @PostMapping("/findCollectivePlanList")
    @ApiOperation(value = "查询我创建的备课组", notes = "返回集体备课表列表")
    public List<CollectivePlan> findCollectivePlanList(
            @ApiParam(value = "集体备课表对象")
            @RequestBody CollectivePlan collectivePlan) {
        //设置学年为当前学年
        //collectivePlan.setSchoolYear(GetStudyTimeUtils.getStudyTime());
        return collectivePlanService.findCollectivePlanList(collectivePlan);
    }

    //查询集体备课讨论组(初始进入)
    @PostMapping("/findCollectivePlanListByTeacherId")
    @ApiOperation(value = "查询集体备课首页讨论组", notes = "返回集体备课表列表")
    public List<CollectivePlan> findCollectivePlanListByTeacherId(
            @ApiParam(value = "集体备课表对象")
            @RequestBody CollectivePlan collectivePlan) {
        //设置学年为当前学年
        if(StringUtils.isBlank(collectivePlan.getSchoolYear())){
            collectivePlan.setSchoolYear(GetStudyTimeUtils.getStudyTime());
        }
        return collectivePlanService.findCollectivePlanListByTeacherId(collectivePlan);
    }

    //查询学年
    @PostMapping("/findSchoolYear")
    @ApiOperation(value = "查询学年", notes = "返回集体备课表列表")
    public List<CollectivePlan> findSchoolYear(
            @ApiParam(value = "集体备课表对象")
            @RequestBody CollectivePlan collectivePlan) {
        return collectivePlanService.findSchoolYear(collectivePlan);
    }

}
