package com.yice.edu.cn.bmp.controller.dm.honourRoll;

import com.yice.edu.cn.bmp.service.dm.honourRoll.DmHonourRollService;
import com.yice.edu.cn.bmp.service.dm.honourRoll.DmHonourRollStudentService;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.honourRoll.DmHonourRoll;
import com.yice.edu.cn.common.pojo.dm.honourRoll.DmHonourRollStudent;
import com.yice.edu.cn.common.pojo.dm.honourRoll.EccHonourRoll;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dmHonourRoll")
@Api(value = "/dmHonourRoll",description = "光荣榜，管理模块")
public class DmHonourRollController {
    @Autowired
    private DmHonourRollService dmHonourRollService;
    @Autowired
    private DmHonourRollStudentService dmHonourRollStudentService;

    @PostMapping("/findDmHonourRollsByCondition")
    @ApiOperation(value = "根据条件查找光荣榜，管理,分页，classid必传，再加上pger对象", notes = "返回响应对象", response=DmHonourRoll.class)
    public ResponseJson findDmHonourRollsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmHonourRoll dmHonourRoll){
        if(StringUtils.isEmpty(dmHonourRoll.getClassId())){
            return new ResponseJson(false,"班级编号不能为空");
        }else{
            dmHonourRoll.setSchoolId(mySchoolId());
            List<DmHonourRoll> data=dmHonourRollService.findDmHonourRollListByCondition(dmHonourRoll);
            long count=dmHonourRollService.findDmHonourRollCountByCondition(dmHonourRoll);
            return new ResponseJson(data,count);
        }

    }

    @GetMapping("/findDmHonourRollByCondition/{classId}")
    @ApiOperation(value = "根据条件查找光荣榜，管理,不分页", notes = "返回响应对象", response=DmHonourRoll.class)
    public ResponseJson findDmHonourRollByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @PathVariable String classId){
        if(StringUtils.isEmpty(classId)){
            return new ResponseJson(false,"班级编号不能为空");
        }else{
            DmHonourRoll dmHonourRoll = new DmHonourRoll();
            Pager pager = new Pager();
            pager.setPaging(false);
            pager.setSortOrder(Pager.DESC);
            pager.setSortField("create_time");
            dmHonourRoll.setPager(pager);
            dmHonourRoll.setSchoolId(mySchoolId());
            dmHonourRoll.setClassId(classId);
            List<DmHonourRoll> data=dmHonourRollService.findDmHonourRollListByCondition(dmHonourRoll);
            return new ResponseJson(data);
        }

    }

    @GetMapping("/findDmHonourRollStudentsByCondition/{id}")
    @ApiOperation(value = "根据条件查找光荣榜，管理，传入光荣榜编号即可", notes = "返回响应对象", response=DmHonourRoll.class)
    public ResponseJson findDmHonourRollStudentsByCondition(
            @ApiParam(value = "光荣榜编号")
            @Validated
            @PathVariable("id") String id){
        DmHonourRoll dmHonourRoll = dmHonourRollService.findDmHonourRollById(id);
        DmHonourRollStudent dmHonourRollStudent = new DmHonourRollStudent();
        dmHonourRollStudent.setHonourId(id);
        dmHonourRollStudent.setClassId(dmHonourRoll.getClassId());
        dmHonourRollStudent.setPager(new Pager().setPaging(false));
        List<EccHonourRoll> eccHonourRollList = dmHonourRollStudentService.getHonourRollList(dmHonourRollStudent);
        if(eccHonourRollList.size()>0){
            return new ResponseJson(eccHonourRollList,dmHonourRoll.getHonourTime());
        }else{
            return new ResponseJson(false,"无数据");
        }

    }

    @GetMapping("/findDmHonourRollStudents/{id}")
    @ApiOperation(value = "根据条件查找光荣榜，管理，传入光荣榜编号即可", notes = "返回响应对象", response=DmHonourRoll.class)
    public ResponseJson findDmHonourRollStudents(
            @ApiParam(value = "光荣榜编号")
            @Validated
            @PathVariable("id") String id){
        DmHonourRoll dmHonourRoll = dmHonourRollService.findDmHonourRollById(id);
        DmHonourRollStudent dmHonourRollStudent = new DmHonourRollStudent();
        dmHonourRollStudent.setHonourId(id);
        dmHonourRollStudent.setClassId(dmHonourRoll.getClassId());
        dmHonourRollStudent.setPager(new Pager().setPaging(false));
        List<EccHonourRoll> eccHonourRollList = dmHonourRollStudentService.getHonourRollList(dmHonourRollStudent);
        if(eccHonourRollList.size()>0){
            Map<String, List<EccHonourRoll>> resultList = eccHonourRollList.stream().collect(Collectors.groupingBy(EccHonourRoll::getHonourTime));
            return new ResponseJson(resultList);
        } else{
            return new ResponseJson(false,"无数据");
        }

    }





}
