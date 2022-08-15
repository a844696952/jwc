package com.yice.edu.cn.osp.controller.dm.zyDevice;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.xw.kq.*;
import com.yice.edu.cn.osp.service.dm.zyDevice.KqTeacherEnterService;
import com.yice.edu.cn.osp.service.jw.staff.StaffService;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherService;
import com.yice.edu.cn.osp.service.xw.kq.KqSchoolInitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/kqTeacherEnter")
@Api(value = "/kqTeacherEnter", description = "教师考勤管理人像录入")
public class KqTeacherEnterController {
    @Autowired
    private KqTeacherEnterService kqTeacherEnterService;
    @Autowired
    private KqSchoolInitService kqSchoolInitService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StaffService staffService;

    @Value("#{'${spring.profiles.active}'=='prod'}")
    private Boolean isProd;
    //批量校验
    @PostMapping("/save/batchEnterTeacher")
    @ApiOperation(value = "中移批量校验考勤人员图像信息,插入教师录入表数据", notes = "", response = KqStuAddBean.class)
    public ResponseJson batchEnterTeacher(
            @ApiParam(value = "考勤管理基础规则表对象,包含教师列表", required = true)
            @RequestBody KqTeacherAddBean kqStuAddBean) {
        if (kqStuAddBean.getTeacherList().size()>100){
            return new ResponseJson(false,"每次录入人员不可超过100人");
        }
        //获得本校密钥
        KqSchoolInit kqSchoolInit = new KqSchoolInit();
        kqSchoolInit.setCustId(mySchoolId());
        KqSchoolInit kqSchool =  kqSchoolInitService.findOneKqSchoolInitByCondition(kqSchoolInit);
        if (kqSchool==null){
            return new ResponseJson(false,"本校暂未开通本服务，请联系管理员");
        }
        String myKey = kqSchool.getKey();
        //中移批量校验录入（超过50人后端分批次录入）
        List<Teacher> teacherList = kqStuAddBean.getTeacherList();
        List<List<Teacher>> batchList = kqTeacherEnterService.getBatchList(teacherList,50);
        //请求中移校验并获得校验结果数据
        List reslist = kqTeacherEnterService.requestZyStuEnter(isProd,myKey,batchList,kqSchool);
        if (reslist.size()==0){
            return new ResponseJson(false,"服务器繁忙");
        }
        //System.out.println("请求中移校验并获得校验结果数据成功");
        //获得检验成功数据,剔除失败数据
        List<Teacher> enterStus = kqTeacherEnterService.getEnterTeacherData(reslist,teacherList);
        if (enterStus.size()==0){
            return new ResponseJson(reslist);
        }
        //System.out.println("获得检验成功数据,剔除失败数据成功");
        //批量上传校验成功数据至七牛并要跟新学生数据
        List<Teacher> qiniuStus = kqTeacherEnterService.uploadImgUrl(enterStus);
        if (qiniuStus.size()==0){
            List<ZyStuBean> res = kqTeacherEnterService.updateSuccessStu(reslist,qiniuStus);
            return new ResponseJson(res);
        }
        //System.out.println("图片上传至七牛成功");
        //批量更新校验成功教师考勤头像录入表
        kqTeacherEnterService.saveTeacherImage(qiniuStus,mySchoolId());
        //System.out.println("保存到教师考勤头像录入表");
        //更新校验成功结果集
        List<ZyStuBean> res = kqTeacherEnterService.updateSuccessStu(reslist,qiniuStus);
        //System.out.println("更新校验成功结果集");
        //返回校验结果数据
        return new ResponseJson(res);
    }

    @PostMapping("/find/findTeachersByCondition")
    @ApiOperation(value = "根据条件查找教师信息", notes = "返回响应对象")
    public ResponseJson findTeachersByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody @Validated Teacher teacher) {
        teacher.setPersonType(Constant.PERSON_TYPE.TEACHER);
        teacher.setStatus("40");
        teacher.setCheckStatus("2");
        if (teacher.getSearchCheckType()==null){
            teacher.setSearchCheckType("1");
        }
        teacher.setSchoolId(mySchoolId());
        teacher.getPager().addExcludes("password");
        teacher.setStatus(Constant.STATUS.TEACHER_ON_LINE);
        List<Teacher> data = teacherService.findTeacherImgListByCondition(teacher);
        teacher.getPager().setPaging(false);
        long count = 0;
        List<Teacher> all = teacherService.findTeacherImgListByCondition(teacher);
        if (all!=null&&all.size()>0){
         count = all.size();
        }
        return new ResponseJson(data, count);
    }

    @PostMapping("/find/findStaffsByCondition")
    @ApiOperation(value = "根据条件查找职工信息", notes = "返回响应对象", response = Teacher.class)
    public ResponseJson findStaffsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Teacher staff) {
        staff.setSchoolId(mySchoolId());
        Pager pager = staff.getPager() == null ? new Pager().setPaging(false) : staff.getPager();
        pager.setLike("name");
        staff.setStatus("40");
        staff.setCheckStatus("2");
        if (staff.getSearchCheckType()==null){
           staff.setSearchCheckType("1");
        }
        List<Teacher> data = staffService.findStaffListByCondition(staff);
        long count = staffService.findStaffCountByCondition(staff);
        return new ResponseJson(data, count);
    }




}
