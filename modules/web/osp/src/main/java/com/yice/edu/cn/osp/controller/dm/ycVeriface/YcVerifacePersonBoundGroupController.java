package com.yice.edu.cn.osp.controller.dm.ycVeriface;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDeviceGroupPerson;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.osp.service.dm.ycVeriface.YcVerifacePersonBoundGroupService;
import com.yice.edu.cn.osp.service.dm.zyDevice.KqDeviceGroupPersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/YcVerifacePersonBoundGroup")
@Api(value = "/YcVerifacePersonBoundGroup", description = "亿策考勤设备分组与人员绑定关联")
public class YcVerifacePersonBoundGroupController {
    @Autowired
    private YcVerifacePersonBoundGroupService ycVerifacePersonBoundGroupService;

    //---yc-----
    @PostMapping("/find/ycFastBindDeviceAndPersonByGroupAndPersonType")
    @ApiOperation(value = "亿策根据分组id和人员类型快速绑定设备", notes = "返回响应对象,不包含总条数", response = KqDeviceGroupPerson.class)
    public ResponseJson ycFastBindDeviceAndPersonByGroupAndPersonType(
            @ApiParam(value = "deviceGroupId分组id,fastBound快速绑定数组,(1教师，2学生，3其他职工)")
            @RequestBody KqDeviceGroupPerson kqDeviceGroupPerson) {
        Map<String, List<String>> failureMap = ycVerifacePersonBoundGroupService.ycFastBindDeviceAndPersonByGroupAndPersonType(kqDeviceGroupPerson);
        if (failureMap.size()==0){
            return new ResponseJson();
        }
        return new ResponseJson(failureMap);
    }

    @PostMapping("/find/ycFindAllBoundTeacherByGroup")
    @ApiOperation(value = "亿策根据分组查找所有已经被绑定的教师（可分页）", notes = "返回响应对象,不包含总条数", response = KqDeviceGroupPerson.class)
    public ResponseJson ycFindAllBoundTeacherByGroup(
            @ApiParam(value = "deviceGroupId分组id,workNumber工号,name姓名,要分页传pager")
            @RequestBody KqDeviceGroupPerson kqDeviceGroupPerson) {
        Pager pager = kqDeviceGroupPerson.getPager()==null?null:kqDeviceGroupPerson.getPager();
        List<Teacher> allBoundTeacherByGroup = ycVerifacePersonBoundGroupService.ycFindAllBoundTeacherByGroup(kqDeviceGroupPerson);
        long count = allBoundTeacherByGroup.size();
        if (pager!=null){
            allBoundTeacherByGroup=  allBoundTeacherByGroup.stream().skip(pager.getBeginRow()).limit(pager.getPageSize()).collect(Collectors.toList());
        }
        return new ResponseJson(allBoundTeacherByGroup,count);
    }

    @PostMapping("/find/ycFindAllBoundStaffByGroup")
    @ApiOperation(value = "亿策根据分组查找所有已经被绑定的职工（可分页）", notes = "返回响应对象,不包含总条数", response = KqDeviceGroupPerson.class)
    public ResponseJson ycFindAllBoundStaffByGroup(
            @ApiParam(value = "deviceGroupId分组id,departmentId职工所属部门id,workNumber工号,name姓名,要分页传pager")
            @RequestBody KqDeviceGroupPerson kqDeviceGroupPerson) {
        Pager pager = kqDeviceGroupPerson.getPager()==null?null:kqDeviceGroupPerson.getPager();
        List<Teacher> allBoundStaffByGroup = ycVerifacePersonBoundGroupService.ycFindAllBoundStaffByGroup(kqDeviceGroupPerson);
        long count = allBoundStaffByGroup.size();
        if (pager!=null){
            allBoundStaffByGroup=  allBoundStaffByGroup.stream().skip(pager.getBeginRow()).limit(pager.getPageSize()).collect(Collectors.toList());
        }
        return new ResponseJson(allBoundStaffByGroup,count);
    }

    @PostMapping("/find/ycFindAllBoundStudentByGroup")
    @ApiOperation(value = "亿策根据分组查找所有已经被绑定的学生（可分页）", notes = "返回响应对象,不包含总条数", response = KqDeviceGroupPerson.class)
    public ResponseJson ycFindAllBoundStudentByGroup(
            @ApiParam(value = "deviceGroupId分组id,classesId班级id,gradeId年级id,name姓名,要分页传pager")
            @RequestBody KqDeviceGroupPerson kqDeviceGroupPerson) {
        Pager pager = kqDeviceGroupPerson.getPager()==null?null:kqDeviceGroupPerson.getPager();
        List<Student> allBoundStudentByGroup = ycVerifacePersonBoundGroupService.ycFindAllBoundStudentByGroup(kqDeviceGroupPerson);
        long count = allBoundStudentByGroup.size();
        if (pager!=null&&allBoundStudentByGroup.size()>0){
            allBoundStudentByGroup=  allBoundStudentByGroup.stream().skip(pager.getBeginRow()).limit(pager.getPageSize()).collect(Collectors.toList());
        }
        return new ResponseJson(allBoundStudentByGroup,count);
    }

    @PostMapping("/find/ycFindAllUnBoundTeacherByGroup")
    @ApiOperation(value = "亿策根据分组查找所有未被绑定的已注册教师（可分页）", notes = "返回响应对象,不包含总条数", response = KqDeviceGroupPerson.class)
    public ResponseJson ycFindAllUnBoundTeacherByGroup(
            @ApiParam(value = "deviceGroupId分组id,workNumber工号,name姓名,要分页传pager")
            @RequestBody KqDeviceGroupPerson kqDeviceGroupPerson) {
        Pager pager = kqDeviceGroupPerson.getPager()==null?null:kqDeviceGroupPerson.getPager();
        List<Teacher> allUnBoundTeacherByGroup = ycVerifacePersonBoundGroupService.ycFindAllUnBoundTeacherByGroup(kqDeviceGroupPerson);
        long count = allUnBoundTeacherByGroup.size();
        if (pager!=null){
            allUnBoundTeacherByGroup=  allUnBoundTeacherByGroup.stream().skip(pager.getBeginRow()).limit(pager.getPageSize()).collect(Collectors.toList());
        }
        return new ResponseJson(allUnBoundTeacherByGroup,count);
    }

    @PostMapping("/find/ycFindAllUnBoundStaffByGroup")
    @ApiOperation(value = "亿策根据分组查找所有未被绑定的已注册职工（可分页）", notes = "返回响应对象,不包含总条数", response = KqDeviceGroupPerson.class)
    public ResponseJson ycFindAllUnBoundStaffByGroup(
            @ApiParam(value = "deviceGroupId分组id,departmentId职工所属部门id,workNumber工号,name姓名,要分页传pager")
            @RequestBody KqDeviceGroupPerson kqDeviceGroupPerson) {
        Pager pager = kqDeviceGroupPerson.getPager()==null?null:kqDeviceGroupPerson.getPager();
        List<Teacher> allUnBoundStaffByGroup = ycVerifacePersonBoundGroupService.ycFindAllUnBoundStaffByGroup(kqDeviceGroupPerson);
        long count = allUnBoundStaffByGroup.size();
        if (pager!=null){
            allUnBoundStaffByGroup=  allUnBoundStaffByGroup.stream().skip(pager.getBeginRow()).limit(pager.getPageSize()).collect(Collectors.toList());
        }
        return new ResponseJson(allUnBoundStaffByGroup,count);
    }

    @PostMapping("/find/ycFindAllUnBoundStudentByGroup")
    @ApiOperation(value = "亿策根据分组查找所有未被绑定的已注册学生（可分页）", notes = "返回响应对象,不包含总条数", response = KqDeviceGroupPerson.class)
    public ResponseJson ycFindAllUnBoundStudentByGroup(
            @ApiParam(value = "deviceGroupId分组id,classesId班级id,gradeId年级id,name姓名,要分页传pager")
            @RequestBody KqDeviceGroupPerson kqDeviceGroupPerson) {
        Pager pager = kqDeviceGroupPerson.getPager()==null?null:kqDeviceGroupPerson.getPager();
        List<Student> allUnBoundStudentByGroup = ycVerifacePersonBoundGroupService.ycFindAllUnBoundStudentByGroup(kqDeviceGroupPerson);

        long count = allUnBoundStudentByGroup.size();
        if (pager!=null){
            allUnBoundStudentByGroup=  allUnBoundStudentByGroup.stream().skip(pager.getBeginRow()).limit(pager.getPageSize()).collect(Collectors.toList());
        }
        return new ResponseJson(allUnBoundStudentByGroup,count);
    }

    @PostMapping("/find/ycBindDeviceAndPersonByGroupAndPersons")
    @ApiOperation(value = "亿策根据分组id和人员id列表绑定设备", notes = "返回绑定失败的人员id", response = KqDeviceGroupPerson.class)
    public ResponseJson ycBindDeviceAndPersonByGroupAndPersons(
            @ApiParam(value = "deviceGroupId分组id,mapForPersonIdListByType预绑定人员id数组和人员类型的map,Map的Key人员类型(1教师，2学生，3其他职工)")
            @RequestBody KqDeviceGroupPerson kqDeviceGroupPerson) {
        Map<String, List<String>> failureMap = ycVerifacePersonBoundGroupService.ycBindDeviceAndPersonByGroupAndPersons(kqDeviceGroupPerson);
        return new ResponseJson(failureMap);
    }

    @PostMapping("/find/ycPersonsUnbindDeviceByGroup")
    @ApiOperation(value = "亿策解绑该分组下一批人员", notes = "返回绑定失败的人员id", response = KqDeviceGroupPerson.class)
    public ResponseJson ycPersonsUnbindDeviceByGroup(
            @ApiParam(value = "deviceGroupId分组id,mapForPersonIdListByType预解绑定人员id数组和人员类型的Map,Map的Key人员类型(1教师，2学生，3其他职工)")
            @RequestBody KqDeviceGroupPerson kqDeviceGroupPerson) {
        Map<String, List<String>> failureMap = ycVerifacePersonBoundGroupService.ycPersonsUnbindDeviceByGroup(kqDeviceGroupPerson);
        if (failureMap.size()==0){
            return new ResponseJson();
        }
        return new ResponseJson(failureMap);
    }
}
