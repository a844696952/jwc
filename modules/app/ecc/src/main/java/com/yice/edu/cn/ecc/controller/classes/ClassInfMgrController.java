package com.yice.edu.cn.ecc.controller.classes;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.classes.DmClassDesc;
import com.yice.edu.cn.common.pojo.dm.classes.DmClassPhoto;
import com.yice.edu.cn.common.pojo.jw.classes.ClassesInfoViewVo;
import com.yice.edu.cn.common.pojo.jw.classes.JwClaCadresStu;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.ecc.service.classes.DmClassDescService;
import com.yice.edu.cn.ecc.service.classes.DmClassPhotoService;
import com.yice.edu.cn.ecc.service.classes.JwClaCadresStuService;
import com.yice.edu.cn.ecc.service.classes.JwClassesService;
import com.yice.edu.cn.ecc.service.school.SchoolYearService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/dmClassInfMgr")
@Api(value = "/dmClassInfMgr",description = "云班牌班级信息管理模块")
public class ClassInfMgrController{
    @Autowired
    private DmClassDescService dmClassDescService;
    @Autowired
    private DmClassPhotoService dmClassPhotoService;
    @Autowired
    private JwClassesService jwClassesService;

    @Autowired
    private JwClaCadresStuService jwClaCadresStuService;

    @PostMapping("/findOneDmClassDescByCondition")
    @ApiOperation(value = "根据条件查询班级简介", notes = "没有时返回空")
    public ResponseJson findOneDmClassDescByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmClassDesc dmClassDesc){
        DmClassDesc one = dmClassDescService.findOneDmClassDescByCondition(dmClassDesc);
        if(one==null){
            one = new DmClassDesc();
            one.setDes("");
        }else{
            if(one.getDes() == null){
                one.setDes("");
            }
        }
        return new ResponseJson(one);
    }

    @PostMapping("/findDmClassPhotosByCondition")
    @ApiOperation(value = "根据条件查找班级图片和班级荣誉图片", notes = "返回响应对象")
    public ResponseJson findDmClassPhotosByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmClassPhoto dmClassPhoto){

        List<DmClassPhoto> data = dmClassPhotoService.findDmClassPhotoListByCondition(dmClassPhoto);
        long count = dmClassPhotoService.findDmClassPhotoCountByCondition(dmClassPhoto);
        return new ResponseJson(data);
    }

    @PostMapping("/findDmClassPhotosByConditions")
    @ApiOperation(value = "根据条件查找班级图片和班级荣誉图片", notes = "返回响应对象")
    public ResponseJson findDmClassPhotosByConditions(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmClassPhoto dmClassPhoto){
        dmClassPhoto.setImgType(null);
        List<DmClassPhoto> data = dmClassPhotoService.findDmClassPhotoListByCondition(dmClassPhoto);
        return new ResponseJson(data);
    }

    @PostMapping("/findAllDmClassPhotos")
    @ApiOperation(value = "查找所有班级图片和班级荣誉图片", notes = "返回响应对象")
    public ResponseJson findAllDmClassPhotos(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmClassPhoto dmClassPhoto){
        if(dmClassPhoto.getPager() == null){
            Pager pager = new Pager();
            pager.setPaging(false);
            dmClassPhoto.setPager(pager);
        }else{
            dmClassPhoto.getPager().setPaging(false);
        }
        List<DmClassPhoto> data = dmClassPhotoService.findDmClassPhotoListByCondition(dmClassPhoto);

        return new ResponseJson(data);
    }

    @PostMapping("/getClassesTeacherAndMaster")
    @ApiOperation(value = "查询班级班主任和班长信息")
    public ResponseJson getClassesTeacherAndMaster(
            @ApiParam(value = "班级id、学校id")@RequestBody JwClasses jwClasses){
        ClassesInfoViewVo classesInfoViewVo = jwClassesService.getClassesTeacherAndMaster(jwClasses);
        if(classesInfoViewVo == null){
            classesInfoViewVo = new ClassesInfoViewVo();
            classesInfoViewVo.setClassMasterName("");
            classesInfoViewVo.setCourse("");
            classesInfoViewVo.setGradeName("");
            classesInfoViewVo.setClassNo("");
            classesInfoViewVo.setHomeroomTeacher("");
            classesInfoViewVo.setHomeroomTeacherImg("");
            classesInfoViewVo.setStuNum(new Long(0));
        }else{
            if(classesInfoViewVo.getClassMasterName() == null){
                classesInfoViewVo.setClassMasterName("");
            }

            if(classesInfoViewVo.getCourse() == null){
                classesInfoViewVo.setCourse("");
            }

            if(classesInfoViewVo.getGradeName() == null){
                classesInfoViewVo.setGradeName("");
            }

            if(classesInfoViewVo.getClassNo() == null){
                classesInfoViewVo.setClassNo("");
            }

            if(classesInfoViewVo.getHomeroomTeacher() == null){
                classesInfoViewVo.setHomeroomTeacher("");
            }

            if(classesInfoViewVo.getHomeroomTeacherImg() == null){
                classesInfoViewVo.setHomeroomTeacherImg("");
            }

            if(classesInfoViewVo.getStuNum() == null){
                classesInfoViewVo.setStuNum(new Long(0));
            }
        }
        return new ResponseJson(classesInfoViewVo);
    }

    @PostMapping("/findStuAndCadresByClassesIdAndName")
    @ApiOperation(value = "根据班级id查询学生和对应的班级职务", notes = "返回学生和职务信息列表")
    public ResponseJson findStuAndCadresByClassesIdAndName(
            @ApiParam(value = "学生信息")
            @Validated
            @RequestBody JwClaCadresStu jwClaCadresStu){
        List<JwClaCadresStu> jwClaCadresStuList = jwClaCadresStuService.queryJwStudentByClassesId(jwClaCadresStu.getClassesId());
        return new ResponseJson(jwClaCadresStuList);
    }



}
