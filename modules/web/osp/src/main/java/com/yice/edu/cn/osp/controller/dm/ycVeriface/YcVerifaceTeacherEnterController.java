package com.yice.edu.cn.osp.controller.dm.ycVeriface;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.personEnter.YcVerifacePersonEnter;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcDataToRegisterPage;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcTeacherAddBean;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.xw.kq.KqSchoolInit;
import com.yice.edu.cn.common.pojo.xw.kq.KqStuAddBean;
import com.yice.edu.cn.common.pojo.xw.kq.KqTeacherAddBean;
import com.yice.edu.cn.common.pojo.xw.kq.ZyStuBean;
import com.yice.edu.cn.osp.service.dm.ycVeriface.YcVerifacePersonEnterService;
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
@RequestMapping("/YcVerifaceTeacherEnter")
@Api(value = "/YcVerifaceTeacherEnter", description = "亿策人脸识别教职工人像录入")
public class YcVerifaceTeacherEnterController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private YcVerifacePersonEnterService ycVerifacePersonEnterService;
    @Value("#{'${spring.profiles.active}'=='prod'}")
    private Boolean isProd;


    //上传教师头像
    //批量上传图片校验
    @PostMapping("/ycBatchEnterTeacher")
    @ApiOperation(value = "亿策批量校验人员图像信息,更新教师数据", notes = "", response = KqStuAddBean.class)
    public ResponseJson ycBatchEnterTeacher(
            @ApiParam(value = "", required = true)
            @RequestBody YcTeacherAddBean ycTeacherAddBean) {
        if (ycTeacherAddBean.getTeacherList().size() > 100) {
            return new ResponseJson(false, "每次录入人员不可超过100人");
        }
        //中移批量校验录入（超过50人后端分批次录入）
        List<Teacher> teacherList = ycTeacherAddBean.getTeacherList();
        List<List<Teacher>> batchList = ycVerifacePersonEnterService.getTeacherBatchList(teacherList, 50);
        Integer personType = null;
        if (CollectionUtil.isNotEmpty(teacherList)){
           personType = teacherList.get(0).getPersonType();
        }else {
            return new ResponseJson(false, "请选择录入人员");
        }
        //请求人脸服务器校验并获得校验结果数据
        List<YcVerifacePersonEnter> reslist = ycVerifacePersonEnterService.requestYcTeaEnter(isProd, batchList,personType);
        if (CollectionUtil.isEmpty(reslist)) {
            return new ResponseJson(false, "请确认录入人员和头像信息");
        }

        //获得检验成功数据,剔除失败数据
        List<Teacher> enters = ycVerifacePersonEnterService.getEnterTeaData(reslist, teacherList);
        if (enters.size() == 0) {
            return new ResponseJson(reslist);
        }

        //批量上传校验成功数据至七牛并要跟新教师数据
        List<Teacher> qiniuStus = ycVerifacePersonEnterService.uploadTeaImgUrl(enters);
        if (qiniuStus.size() == 0) {
            List<YcVerifacePersonEnter> res = ycVerifacePersonEnterService.updateSuccessTea(reslist, qiniuStus);
            return new ResponseJson(res);
        }

        //批量更新校验成功教师头像
        teacherService.batchUpdateTeacher(qiniuStus);

        //更新校验成功结果集
        List<YcVerifacePersonEnter> res = ycVerifacePersonEnterService.updateSuccessTea(reslist, qiniuStus);

        //返回校验结果数据
        return new ResponseJson(res);
    }

    //校验教师头像
    @PostMapping("/checkTeacherPic")
    @ApiOperation(value = "中移批量校验考勤人员图像信息,更新教师数据", notes = "", response = KqStuAddBean.class)
    public ResponseJson checkTeacherPic(
            @ApiParam(value = "教师", required = true)
            @RequestBody Teacher teacher) {
        if (teacher.getPager()!=null){
            teacher.getPager().setPaging(false);
        }
        teacher.setSchoolId(mySchoolId());
        teacher.setStatus("40");
        List<Teacher> teacherList = ycVerifacePersonEnterService.getAllCheckTea(teacher,teacher.getPersonType());
        List<List<Teacher>> batchList = ycVerifacePersonEnterService.getTeacherBatchList(teacherList, 50);
        //请求人脸服务器校验并获得校验结果数据
        Integer personType = null;
        if (CollectionUtil.isNotEmpty(teacherList)){
            personType = teacherList.get(0).getPersonType();
        }else {
            return new ResponseJson(false, "请确认录入人员和头像信息");
        }
        List<YcVerifacePersonEnter> reslist = ycVerifacePersonEnterService.requestYcTeaEnter(isProd, batchList,personType);
        if (CollectionUtil.isEmpty(reslist)) {
            return new ResponseJson(reslist);
        }
        //获得检验成功数据,剔除失败数据
        List<Teacher> enters = ycVerifacePersonEnterService.getEnterTeaData(reslist, teacherList);
        if (CollectionUtil.isEmpty(enters)) {
            return new ResponseJson(reslist);
        }
        //更新校验成功结果集
        List<YcVerifacePersonEnter> res = ycVerifacePersonEnterService.updateSuccessTea(reslist, enters);
        //返回校验结果数据
        return new ResponseJson(res);
    }
    //查找教师列表
    @PostMapping("/findTeacherForEnter")
    @ApiOperation(value = "中移批量校验考勤人员图像信息,更新教师数据", notes = "", response = KqStuAddBean.class)
    public ResponseJson findTeacherForEnter(
            @ApiParam(value = "教师", required = true)
            @RequestBody Teacher teacher) {
        teacher.setStatus("40");
        teacher.setSchoolId(mySchoolId());
        //teacher.setPersonType(1);
        if (StrUtil.isNotEmpty(teacher.getCheckStatus())){
            YcDataToRegisterPage ycDataToRegisterPage = ycVerifacePersonEnterService.fingTeacherByYcCheckStatus(teacher,teacher.getPersonType());
            return new ResponseJson(ycDataToRegisterPage.getTeacherList(),ycDataToRegisterPage.getCount());
        }
        List<Teacher> data =null;
        long teacherCountByCondition =0;
        if (teacher.getPersonType()==1){
            data  = teacherService.findTeacherListByCondition(teacher);
            teacherCountByCondition = teacherService.findTeacherCountByCondition(teacher);
        }else if (teacher.getPersonType()==2){
            data = ycVerifacePersonEnterService.findStaffsByCondition(teacher);
            teacherCountByCondition = ycVerifacePersonEnterService.findStaffsCountByCondition(teacher);
        }

        //校验状态
        if (CollectionUtil.isNotEmpty(data)){
            data = ycVerifacePersonEnterService.addTeacherCheckStatus(data,teacher.getPersonType()==1?Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_TEACHER:Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_STAFF);
        }
        return new ResponseJson(data,teacherCountByCondition);
    }
}
