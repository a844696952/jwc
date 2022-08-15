package com.yice.edu.cn.osp.controller.jy.collectivePlan;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.collectivePlan.CollectivePlan;
import com.yice.edu.cn.common.pojo.jy.collectivePlan.CollectivePlanTeacher;
import com.yice.edu.cn.osp.service.jy.collectivePlan.CollectivePlanService;
import com.yice.edu.cn.osp.service.jy.collectivePlan.CollectivePlanTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/collectivePlan")
@Api(value = "/collectivePlan",description = "集体备课表模块")
public class CollectivePlanController {
    @Autowired
    private CollectivePlanService collectivePlanService;
    @Autowired
    private CollectivePlanTeacherService collectivePlanTeacherService;

    @PostMapping("/saveCollectivePlan")
    @ApiOperation(value = "保存集体备课表对象--------------------------------------------------------", notes = "返回响应对象")
    public ResponseJson saveCollectivePlan(
            @ApiParam(value = "集体备课表对象", required = true)
            @RequestBody CollectivePlan collectivePlan){
        collectivePlan.setSchoolId(mySchoolId());
        collectivePlan.setTeacherId(myId());
        //根据条件查找讨论组是否重名
        List<CollectivePlan> data = collectivePlanService.findCollectivePlanByPlanName(collectivePlan);
        if(data.size()>0){
            return new ResponseJson(false,"此讨论组名称已被使用");
        }else{
            CollectivePlan s=collectivePlanService.saveCollectivePlan(collectivePlan);
            return new ResponseJson(s);
        }
    }

    @GetMapping("/update/findCollectivePlanById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找集体备课表", notes = "返回响应对象")
    public ResponseJson findCollectivePlanById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        CollectivePlan collectivePlan=collectivePlanService.findCollectivePlanById(id);
        return new ResponseJson(collectivePlan);
    }

    @PostMapping("/update/updateCollectivePlan")
    @ApiOperation(value = "修改集体备课表对象-------------------------------------------------", notes = "返回响应对象")
    public ResponseJson updateCollectivePlan(
            @ApiParam(value = "被修改的集体备课表对象,对象属性不为空则修改", required = true)
            @RequestBody CollectivePlan collectivePlan){
        collectivePlanService.updateCollectivePlan(collectivePlan);
        return new ResponseJson();
    }

    @GetMapping("/look/lookCollectivePlanById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找集体备课表", notes = "返回响应对象")
    public ResponseJson lookCollectivePlanById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        CollectivePlan collectivePlan=collectivePlanService.findCollectivePlanById(id);
        return new ResponseJson(collectivePlan);
    }

    @PostMapping("/findCollectivePlansByCondition")
    @ApiOperation(value = "根据条件查找集体备课表", notes = "返回响应对象")
    public ResponseJson findCollectivePlansByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CollectivePlan collectivePlan){
       collectivePlan.setSchoolId(mySchoolId());
        List<CollectivePlan> data=collectivePlanService.findCollectivePlanListByCondition(collectivePlan);
        long count=collectivePlanService.findCollectivePlanCountByCondition(collectivePlan);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneCollectivePlanByCondition")
    @ApiOperation(value = "根据id查询一条讨论组进行编辑---------------------------------------------", notes = "没有时返回空")
    public ResponseJson findOneCollectivePlanByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody CollectivePlan collectivePlan){
        CollectivePlan one=collectivePlanService.findOneCollectivePlanByCondition(collectivePlan);
        CollectivePlanTeacher collectivePlanTeacher = new CollectivePlanTeacher();
        collectivePlanTeacher.setCollectivePlanId(one.getId());
        List<CollectivePlanTeacher> data=collectivePlanTeacherService.findOneCollectivePlanByCollectivePlanId(collectivePlanTeacher);
        if(data.size()>0){
            one.setCollectivePlanTeacher(data);
        }
        return new ResponseJson(one);
    }
    @GetMapping("/deleteCollectivePlan/{id}")
    @ApiOperation(value = "根据id删除集体备课讨论组-----------------------------------------------", notes = "返回响应对象")
    public ResponseJson deleteCollectivePlan(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        collectivePlanService.deleteCollectivePlan(id);
        return new ResponseJson();
    }


    @PostMapping("/findCollectivePlanListByCondition")
    @ApiOperation(value = "根据条件查找集体备课表列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findCollectivePlanListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody CollectivePlan collectivePlan){
       collectivePlan.setSchoolId(mySchoolId());
        List<CollectivePlan> data=collectivePlanService.findCollectivePlanListByCondition(collectivePlan);
        return new ResponseJson(data);
    }

    //查询我创建的备课组
    @PostMapping("/findCollectivePlanList")
    @ApiOperation(value = "查询我创建的备课组---------------------------------------------------------", notes = "返回集体备课表列表")
    public ResponseJson findCollectivePlanList(
            @ApiParam(value = "集体备课表对象")
            @RequestBody CollectivePlan collectivePlan){
        collectivePlan.setTeacherId(myId());
        List<CollectivePlan> data =collectivePlanService.findCollectivePlanList(collectivePlan);
        collectivePlan.setPager(null);
        List<CollectivePlan> dataSize =collectivePlanService.findCollectivePlanList(collectivePlan);
        return new ResponseJson(data,dataSize.size());
    }
    //查询集体备课首用讨论组
    @PostMapping("/findCollectivePlanListByTeacherId")
    @ApiOperation(value = "查询集体备课首用讨论组---------------------------------------------------------", notes = "返回集体备课表列表")
    public ResponseJson findCollectivePlanListByTeacherId(
            @ApiParam(value = "集体备课表对象")
            @RequestBody CollectivePlan collectivePlan){
        collectivePlan.setTeacherId(myId());
        List<CollectivePlan> data =collectivePlanService.findCollectivePlanListByTeacherId(collectivePlan);
        return new ResponseJson(data);
    }

    //查询学年
    @PostMapping("/findSchoolYear")
    @ApiOperation(value = "查询学年---------------------------------------------------------", notes = "返回集体备课表列表")
    public ResponseJson findSchoolYear(
            @ApiParam(value = "集体备课表对象")
            @RequestBody CollectivePlan collectivePlan){
        collectivePlan.setTeacherId(myId());
        List<CollectivePlan> data =collectivePlanService.findSchoolYear(collectivePlan);
        return new ResponseJson(data);
    }

    @GetMapping("/getCurrentTime")
    @ApiOperation(value = "获取服务器的当前时间-----------------------------------------------", notes = "返回响应对象")
    public ResponseJson getCurrentTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return new ResponseJson(formatter.format(new Date()));
    }

    @GetMapping("/findCollectivePlanCountByCondition")
    @ApiOperation(value = "查找单个教师集体备课统计条数", notes = "count")
    public ResponseJson findCollectivePlanCountByCondition(){
        CollectivePlan collectivePlan = new CollectivePlan();
        collectivePlan.setSchoolId(mySchoolId());
        collectivePlan.setTeacherId(myId());
        long count=collectivePlanService.findCollectivePlanCountByCondition(collectivePlan);
        return new ResponseJson(count);
    }

}
