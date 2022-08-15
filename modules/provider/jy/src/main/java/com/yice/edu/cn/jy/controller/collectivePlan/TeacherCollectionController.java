package com.yice.edu.cn.jy.controller.collectivePlan;

import com.yice.edu.cn.common.pojo.jy.collectivePlan.TeacherCollection;
import com.yice.edu.cn.common.pojo.jy.collectivePlan.TeamTeachingPlan;
import com.yice.edu.cn.jy.service.collectivePlan.TeacherCollectionService;
import com.yice.edu.cn.jy.service.collectivePlan.TeamTeachingPlanService;
import com.yice.edu.cn.jy.service.prepareLessons.TeachingPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacherCollection")
@Api(value = "/teacherCollection",description = "集体备课讨论组  与 教案关联表模块")
public class TeacherCollectionController {
    @Autowired
    private TeacherCollectionService teacherCollectionService;
    @Autowired
    private TeamTeachingPlanService teamTeachingPlanService;

    @Autowired
    private TeachingPlanService teachingPlanService;

    @GetMapping("/findTeacherCollectionById/{id}")
    @ApiOperation(value = "通过id查找集体备课讨论组  与 教案关联表", notes = "返回集体备课讨论组  与 教案关联表对象")
    public TeacherCollection findTeacherCollectionById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return teacherCollectionService.findTeacherCollectionById(id);
    }

    @PostMapping("/saveTeacherCollection")
    @ApiOperation(value = "保存集体备课讨论组  与 教案关联表", notes = "返回集体备课讨论组  与 教案关联表对象")
    public TeacherCollection saveTeacherCollection(
            @ApiParam(value = "集体备课讨论组  与 教案关联表对象", required = true)
            @RequestBody TeacherCollection teacherCollection){
          //在个人教案中查询一条完整的教案进行复制到 集体备课教案表中
        TeamTeachingPlan teachingPlan = teamTeachingPlanService.findTeachingPlanById(teacherCollection.getTeacherPlanId());
        teachingPlan.setTeacherId(teacherCollection.getTeacherId());
        teachingPlan.setTeacherPlanId(teachingPlan.getId());
         //新增到集体教案表
        String  id = teamTeachingPlanService.saveTeamTeachingPlan(teachingPlan);
        teacherCollection.setTeacherPlanId(id);
        teacherCollection.setStatus(1);
        teacherCollectionService.saveTeacherCollection(teacherCollection);
        return teacherCollection;
    }

    @PostMapping("/findTeacherCollectionListByCondition")
    @ApiOperation(value = "根据条件查找集体备课讨论组  与 教案关联表列表", notes = "返回集体备课讨论组  与 教案关联表列表")
    public List<TeacherCollection> findTeacherCollectionListByCondition(
            @ApiParam(value = "集体备课讨论组  与 教案关联表对象")
            @RequestBody TeacherCollection teacherCollection){
        return teacherCollectionService.findTeacherCollectionListByCondition(teacherCollection);
    }
    @PostMapping("/findTeacherCollectionCountByCondition")
    @ApiOperation(value = "根据条件查找集体备课讨论组  与 教案关联表列表个数", notes = "返回集体备课讨论组  与 教案关联表总个数")
    public long findTeacherCollectionCountByCondition(
            @ApiParam(value = "集体备课讨论组  与 教案关联表对象")
            @RequestBody TeacherCollection teacherCollection){
        return teacherCollectionService.findTeacherCollectionCountByCondition(teacherCollection);
    }

    @PostMapping("/updateTeacherCollection")
    @ApiOperation(value = "修改集体备课讨论组  与 教案关联表", notes = "集体备课讨论组  与 教案关联表对象必传")
    public void updateTeacherCollection(
            @ApiParam(value = "集体备课讨论组  与 教案关联表对象,对象属性不为空则修改", required = true)
            @RequestBody TeacherCollection teacherCollection){
        teacherCollectionService.updateTeacherCollection(teacherCollection);
    }

    @GetMapping("/deleteTeacherCollection/{id}")
    @ApiOperation(value = "通过id删除集体备课讨论组  与 教案关联表")
    public void deleteTeacherCollection(
            @ApiParam(value = "集体备课讨论组  与 教案关联表对象", required = true)
            @PathVariable String id){
        teacherCollectionService.deleteTeacherCollection(id);
    }
    @PostMapping("/deleteTeacherCollectionByCondition")
    @ApiOperation(value = "根据条件删除集体备课讨论组  与 教案关联表")
    public void deleteTeacherCollectionByCondition(
            @ApiParam(value = "集体备课讨论组  与 教案关联表对象")
            @RequestBody TeacherCollection teacherCollection){
        teacherCollectionService.deleteTeacherCollectionByCondition(teacherCollection);
    }
    @PostMapping("/findOneTeacherCollectionByCondition")
    @ApiOperation(value = "根据条件查找单个集体备课讨论组  与 教案关联表,结果必须为单条数据", notes = "返回单个集体备课讨论组  与 教案关联表,没有时为空")
    public TeacherCollection findOneTeacherCollectionByCondition(
            @ApiParam(value = "集体备课讨论组  与 教案关联表对象")
            @RequestBody TeacherCollection teacherCollection){
        return teacherCollectionService.findOneTeacherCollectionByCondition(teacherCollection);
    }


    //对集体备课教案的修改次数  进行增加
    @PostMapping("/updateModifyCount")
    @ApiOperation(value = "对集体备课教案的修改次数  进行增加", notes = "集体备课讨论组  与 教案关联表对象必传")
    public void updateModifyCount(
            @ApiParam(value = "集体备课讨论组  与 教案关联表对象,对象属性不为空则修改", required = true)
            @RequestBody TeacherCollection teacherCollection){
        teacherCollectionService.updateModifyCount(teacherCollection);
    }

    //对集体备课教案的 评论次数 进行增加
    @PostMapping("/updateCommentCount")
    @ApiOperation(value = "修改集体备课讨论组  与 教案关联表", notes = "集体备课讨论组  与 教案关联表对象必传")
    public void updateCommentCount(
            @ApiParam(value = "集体备课讨论组  与 教案关联表对象,对象属性不为空则修改", required = true)
            @RequestBody TeacherCollection teacherCollection){
        teacherCollectionService.updateCommentCount(teacherCollection);
    }
}
