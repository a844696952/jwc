package com.yice.edu.cn.ewb.controller.prepareLessonsNew;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.prepareLessonsNew.JyMaterialExtend;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.Material;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.SubjectMaterial;
import com.yice.edu.cn.ewb.service.prepareLessonsNew.JyMaterialExtendService;
import com.yice.edu.cn.ewb.service.subjectSource.SubjectMaterialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.yice.edu.cn.ewb.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.ewb.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/jyMaterialExtend")
@Api(value = "/jyMaterialExtend", description = "教材模块")
public class JyMaterialExtendController {
    @Autowired
    private JyMaterialExtendService jyMaterialExtendService;
    @Autowired
    private SubjectMaterialService subjectMaterialService;

    @PostMapping("/saveJyMaterialExtend")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response = JyMaterialExtend.class)
    public ResponseJson saveJyMaterialExtend(
            @ApiParam(value = "对象", required = true)
            @RequestBody JyMaterialExtend jyMaterialExtend) {
        if (jyMaterialExtend.getType().equals(Constant.TEXTBOOK.SCHOOL_TYPE)) {
            jyMaterialExtend.setSchoolId(mySchoolId());
        } else {
            jyMaterialExtend.setSchoolId(mySchoolId());
            jyMaterialExtend.setTeacherId(myId());
        }
        JyMaterialExtend s = jyMaterialExtendService.saveJyMaterialExtend(jyMaterialExtend);
        return new ResponseJson(s);
    }

    @PostMapping("/batchSaveJyMaterialExtend")
    @ApiOperation(value = "批量保存对象", notes = "返回响应对象", response = JyMaterialExtend.class)
    public ResponseJson batchSaveJyMaterialExtend(
            @ApiParam(value = "对象", required = true)
            @RequestBody List<JyMaterialExtend> jyMaterialExtendList) {
        if (jyMaterialExtendList != null && !jyMaterialExtendList.isEmpty()) {
            if (jyMaterialExtendList.get(0).getType().equals(Constant.TEXTBOOK.SCHOOL_TYPE)) {
                //学校类型只放学校id
                jyMaterialExtendList.stream().forEach(skt -> skt.setSchoolId(mySchoolId()));
            } else {
                //教师类型放学校id和教师id
                jyMaterialExtendList.stream().forEach(skt -> {
                    skt.setSchoolId(mySchoolId());
                    skt.setTeacherId(myId());
                });
            }
            jyMaterialExtendService.batchSaveJyMaterialExtend(jyMaterialExtendList);
            return new ResponseJson();
        }
        return new ResponseJson();
    }

    @GetMapping("/findJyMaterialExtendById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response = JyMaterialExtend.class)
    public ResponseJson findJyMaterialExtendById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        JyMaterialExtend jyMaterialExtend = jyMaterialExtendService.findJyMaterialExtendById(id);
        return new ResponseJson(jyMaterialExtend);
    }

    @PostMapping("/updateJyMaterialExtend")
    @ApiOperation(value = "修改对象非空字段", notes = "返回响应对象")
    public ResponseJson updateJyMaterialExtend(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody JyMaterialExtend jyMaterialExtend) {
        jyMaterialExtendService.updateJyMaterialExtend(jyMaterialExtend);
        return new ResponseJson();
    }

    @PostMapping("/updateJyMaterialExtendForAll")
    @ApiOperation(value = "修改对象所有字段", notes = "返回响应对象")
    public ResponseJson updateJyMaterialExtendForAll(
            @ApiParam(value = "被修改的对象", required = true)
            @RequestBody JyMaterialExtend jyMaterialExtend) {
        jyMaterialExtendService.updateJyMaterialExtendForAll(jyMaterialExtend);
        return new ResponseJson();
    }

    @PostMapping("/findJyMaterialExtendsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response = JyMaterialExtend.class)
    public ResponseJson findJyMaterialExtendsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody JyMaterialExtend jyMaterialExtend) {
        jyMaterialExtend.setSchoolId(mySchoolId());
        List<JyMaterialExtend> data = jyMaterialExtendService.findJyMaterialExtendListByCondition(jyMaterialExtend);
        long count = jyMaterialExtendService.findJyMaterialExtendCountByCondition(jyMaterialExtend);

        //对结果集匹配班级进行过滤
        if (StringUtils.isNotEmpty(jyMaterialExtend.getGradeType())
                && StringUtils.isNotEmpty(jyMaterialExtend.getSubjectName())
                && (data != null && data.size() != 0)) {
            List<JyMaterialExtend> result = data.stream().filter(skt1 -> {
                //获得年级信息，对比班级
                SubjectMaterial sb = subjectMaterialService.findSubjectMaterialById(skt1.getMaterial().getSubjectMaterialId());
                //根据年级信息的父id获得科目信息，对比科目
                SubjectMaterial subject = subjectMaterialService.findSubjectMaterialById(sb.getParentId());
                if (sb.getDdId().equals(jyMaterialExtend.getGradeType())
                        && subject.getName().equals(jyMaterialExtend.getSubjectName())) {
                    return true;
                } else {
                    return false;
                }
            }).collect(Collectors.toList());
            return new ResponseJson(result);
        }
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneJyMaterialExtendByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response = JyMaterialExtend.class)
    public ResponseJson findOneJyMaterialExtendByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody JyMaterialExtend jyMaterialExtend) {
        JyMaterialExtend one = jyMaterialExtendService.findOneJyMaterialExtendByCondition(jyMaterialExtend);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteJyMaterialExtend/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteJyMaterialExtend(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        jyMaterialExtendService.deleteJyMaterialExtend(id);
        return new ResponseJson();
    }


    @PostMapping("/findJyMaterialExtendListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response = JyMaterialExtend.class)
    public ResponseJson findJyMaterialExtendListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody JyMaterialExtend jyMaterialExtend) {
        if (jyMaterialExtend.getType().equals(Constant.TEXTBOOK.SCHOOL_TYPE)) {
            jyMaterialExtend.setSchoolId(mySchoolId());
        } else {
            jyMaterialExtend.setSchoolId(mySchoolId());
            jyMaterialExtend.setTeacherId(myId());
        }
        List<JyMaterialExtend> data = jyMaterialExtendService.findJyMaterialExtendListByCondition(jyMaterialExtend);
        return new ResponseJson(data);
    }

    //点击应用查询教材资源
    @PostMapping("/findMaterialListByCondition")
    @ApiOperation(value = "根据年段id查询教材资源", notes = "返回教材资源，不包含总条数", response = JyMaterialExtend.class)
    public ResponseJson findMaterialListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody JyMaterialExtend jyMaterialExtend) {
        if (jyMaterialExtend.getType().equals(Constant.TEXTBOOK.SCHOOL_TYPE)) {
            jyMaterialExtend.setSchoolId(mySchoolId());
        } else {
            jyMaterialExtend.setSchoolId(mySchoolId());
            jyMaterialExtend.setTeacherId(myId());
        }
        List<Material> data = jyMaterialExtendService.findMaterialListByCondition(jyMaterialExtend);
        return new ResponseJson(data);
    }


}
