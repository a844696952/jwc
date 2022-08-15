package com.yice.edu.cn.osp.controller.jy.collectivePlan;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.collectivePlan.TeacherCollection;
import com.yice.edu.cn.osp.service.jy.collectivePlan.TeacherCollectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;

@RestController
@RequestMapping("/teacherCollection")
@Api(value = "/teacherCollection",description = "集体备课讨论组  与 教案关联表模块")
public class TeacherCollectionController {
    @Autowired
    private TeacherCollectionService teacherCollectionService;

    @PostMapping("/saveTeacherCollection")
    @ApiOperation(value = "保存集体备课讨论组  与 教案关联表对象-----------------------------------------", notes = "返回响应对象")
    public ResponseJson saveTeacherCollection(
            @ApiParam(value = "集体备课讨论组  与 教案关联表对象", required = true)
            @RequestBody TeacherCollection teacherCollection) {
        teacherCollection.setTeacherId(myId());
        if(StringUtils.isBlank(teacherCollection.getTeacherPlanId())){
            return new ResponseJson(false,"请选择教案");
        }
        if(StringUtils.isBlank(teacherCollection.getCollectionPlanId())){
            return new ResponseJson(false,"讨论组id为空");
        }
        TeacherCollection s = teacherCollectionService.saveTeacherCollection(teacherCollection);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findTeacherCollectionById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找集体备课讨论组  与 教案关联表", notes = "返回响应对象")
    public ResponseJson findTeacherCollectionById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        TeacherCollection teacherCollection = teacherCollectionService.findTeacherCollectionById(id);
        return new ResponseJson(teacherCollection);
    }

    @PostMapping("/update/updateTeacherCollection")
    @ApiOperation(value = "修改集体备课讨论组中教案的状态为已讨论完成--------------------------------", notes = "返回响应对象")
    public ResponseJson updateTeacherCollection(
            @ApiParam(value = "被修改的集体备课讨论组  与 教案关联表对象,对象属性不为空则修改", required = true)
            @RequestBody TeacherCollection teacherCollection) {
          if(StringUtils.isBlank(teacherCollection.getTeacherPlanId())){
              return new ResponseJson(false,"教案id为空");
          }
          if(StringUtils.isBlank(teacherCollection.getCollectionPlanId())){
            return new ResponseJson(false,"讨论组id为空");
          }
        teacherCollection.setStatus(0);
        teacherCollectionService.updateTeacherCollection(teacherCollection);
        return new ResponseJson();
    }

    @GetMapping("/look/lookTeacherCollectionById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找集体备课讨论组  与 教案关联表", notes = "返回响应对象")
    public ResponseJson lookTeacherCollectionById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        TeacherCollection teacherCollection = teacherCollectionService.findTeacherCollectionById(id);
        return new ResponseJson(teacherCollection);
    }

    @PostMapping("/findTeacherCollectionsByCondition")
    @ApiOperation(value = "根据条件查找集体备课讨论组  与 教案关联表", notes = "返回响应对象")
    public ResponseJson findTeacherCollectionsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody TeacherCollection teacherCollection) {
        List<TeacherCollection> data = teacherCollectionService.findTeacherCollectionListByCondition(teacherCollection);
        long count = teacherCollectionService.findTeacherCollectionCountByCondition(teacherCollection);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneTeacherCollectionByCondition")
    @ApiOperation(value = "根据条件查找单个集体备课讨论组  与 教案关联表,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneTeacherCollectionByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody TeacherCollection teacherCollection) {
        TeacherCollection one = teacherCollectionService.findOneTeacherCollectionByCondition(teacherCollection);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteTeacherCollection/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteTeacherCollection(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        teacherCollectionService.deleteTeacherCollection(id);
        return new ResponseJson();
    }


    @PostMapping("/findTeacherCollectionListByCondition")
    @ApiOperation(value = "根据条件查找集体备课讨论组  与 教案关联表列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findTeacherCollectionListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody TeacherCollection teacherCollection) {
        List<TeacherCollection> data = teacherCollectionService.findTeacherCollectionListByCondition(teacherCollection);
        return new ResponseJson(data);
    }

    //对集体备课教案的修改次数  进行增加
    @PostMapping("/update/updateModifyCount")
    @ApiOperation(value = "对集体备课教案的修改次数------------------------------------", notes = "返回响应对象")
    public ResponseJson updateModifyCount(
            @ApiParam(value = "讨论组id 与 教案id 对象 ", required = true)
            @RequestBody TeacherCollection teacherCollection) {
        if(StringUtils.isBlank(teacherCollection.getTeacherPlanId())){
            return new ResponseJson(false,"教案id为空");
        }
        if(StringUtils.isBlank(teacherCollection.getCollectionPlanId())){
            return new ResponseJson(false,"讨论组id为空");
        }
        teacherCollectionService.updateModifyCount(teacherCollection);
        return new ResponseJson();
    }

    //对集体备课教案的 评论次数 进行增加
    @PostMapping("/update/updateCommentCount")
    @ApiOperation(value = "对集体备课教案评论次数增加-------------------------------------", notes = "返回响应对象")
    public ResponseJson updateCommentCount(
            @ApiParam(value = "讨论组id 与 教案id 对象", required = true)
            @RequestBody TeacherCollection teacherCollection) {
        if(StringUtils.isBlank(teacherCollection.getTeacherPlanId())){
            return new ResponseJson(false,"教案id为空");
        }
        if(StringUtils.isBlank(teacherCollection.getCollectionPlanId())){
            return new ResponseJson(false,"讨论组id为空");
        }
        teacherCollectionService.updateCommentCount(teacherCollection);
        return new ResponseJson();
    }


}
