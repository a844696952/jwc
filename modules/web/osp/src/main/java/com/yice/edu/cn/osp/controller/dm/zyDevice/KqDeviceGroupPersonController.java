package com.yice.edu.cn.osp.controller.dm.zyDevice;

import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDeviceGroupPerson;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.osp.service.dm.zyDevice.KqDeviceGroupPersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/kqDeviceGroupPerson")
@Api(value = "/kqDeviceGroupPerson", description = "考勤设备与人员绑定关联")
public class KqDeviceGroupPersonController {
    @Autowired
    private KqDeviceGroupPersonService kqDeviceGroupPersonService;

    @PostMapping("/saveKqDeviceGroupPerson")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response = KqDeviceGroupPerson.class)
    public ResponseJson saveKqDeviceGroupPerson(
            @ApiParam(value = "对象", required = true)
            @RequestBody KqDeviceGroupPerson kqDeviceGroupPerson) {
        kqDeviceGroupPerson.setSchoolId(mySchoolId());
        KqDeviceGroupPerson s = kqDeviceGroupPersonService.saveKqDeviceGroupPerson(kqDeviceGroupPerson);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findKqDeviceGroupPersonById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response = KqDeviceGroupPerson.class)
    public ResponseJson findKqDeviceGroupPersonById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        KqDeviceGroupPerson kqDeviceGroupPerson = kqDeviceGroupPersonService.findKqDeviceGroupPersonById(id);
        return new ResponseJson(kqDeviceGroupPerson);
    }

    @PostMapping("/update/updateKqDeviceGroupPerson")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateKqDeviceGroupPerson(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody KqDeviceGroupPerson kqDeviceGroupPerson) {
        kqDeviceGroupPerson.setSchoolId(mySchoolId());
        kqDeviceGroupPersonService.updateKqDeviceGroupPerson(kqDeviceGroupPerson);
        return new ResponseJson();
    }

    @GetMapping("/look/lookKqDeviceGroupPersonById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response = KqDeviceGroupPerson.class)
    public ResponseJson lookKqDeviceGroupPersonById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        KqDeviceGroupPerson kqDeviceGroupPerson = kqDeviceGroupPersonService.findKqDeviceGroupPersonById(id);
        return new ResponseJson(kqDeviceGroupPerson);
    }

    @PostMapping("/findKqDeviceGroupPersonsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response = KqDeviceGroupPerson.class)
    public ResponseJson findKqDeviceGroupPersonsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqDeviceGroupPerson kqDeviceGroupPerson) {
        List<KqDeviceGroupPerson> data = kqDeviceGroupPersonService.findKqDeviceGroupPersonListByCondition(kqDeviceGroupPerson);
        long count = kqDeviceGroupPersonService.findKqDeviceGroupPersonCountByCondition(kqDeviceGroupPerson);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneKqDeviceGroupPersonByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response = KqDeviceGroupPerson.class)
    public ResponseJson findOneKqDeviceGroupPersonByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody KqDeviceGroupPerson kqDeviceGroupPerson) {
        KqDeviceGroupPerson one = kqDeviceGroupPersonService.findOneKqDeviceGroupPersonByCondition(kqDeviceGroupPerson);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteKqDeviceGroupPerson/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteKqDeviceGroupPerson(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        kqDeviceGroupPersonService.deleteKqDeviceGroupPerson(id);
        return new ResponseJson();
    }


    @PostMapping("/findKqDeviceGroupPersonListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response = KqDeviceGroupPerson.class)
    public ResponseJson findKqDeviceGroupPersonListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqDeviceGroupPerson kqDeviceGroupPerson) {
        List<KqDeviceGroupPerson> data = kqDeviceGroupPersonService.findKqDeviceGroupPersonListByCondition(kqDeviceGroupPerson);
        return new ResponseJson(data);
    }
//---zy-----
    @PostMapping("/find/fastBindDeviceAndPersonByGroupAndPersonType")
    @ApiOperation(value = "根据分组id和人员类型快速绑定设备", notes = "返回响应对象,不包含总条数", response = KqDeviceGroupPerson.class)
    public ResponseJson fastBindDeviceAndPersonByGroupAndPersonType(
            @ApiParam(value = "deviceGroupId分组id,fastBound快速绑定数组,(1教师，2学生，3其他职工)")
            @RequestBody KqDeviceGroupPerson kqDeviceGroupPerson) {
        Map<String, List<String>> failureMap = kqDeviceGroupPersonService.fastBindDeviceAndPersonByGroupAndPersonType(kqDeviceGroupPerson);
        if (failureMap==null){
            return new ResponseJson(false, "本校未开通此服务，请联系管理员！");
        }
        if (failureMap.size()==0){
            return new ResponseJson();
        }
        return new ResponseJson(failureMap);
    }

    @PostMapping("/find/findAllBoundTeacherByGroup")
    @ApiOperation(value = "根据分组查找所有已经被绑定的教师（可分页）", notes = "返回响应对象,不包含总条数", response = KqDeviceGroupPerson.class)
    public ResponseJson findAllBoundTeacherByGroup(
            @ApiParam(value = "deviceGroupId分组id,workNumber工号,name姓名,要分页传pager")
            @RequestBody KqDeviceGroupPerson kqDeviceGroupPerson) {
        Pager pager = kqDeviceGroupPerson.getPager()==null?null:kqDeviceGroupPerson.getPager();
        List<Teacher> allBoundTeacherByGroup = kqDeviceGroupPersonService.findAllBoundTeacherByGroup(kqDeviceGroupPerson);
        long count = allBoundTeacherByGroup.size();
        if (pager!=null){
            allBoundTeacherByGroup=  allBoundTeacherByGroup.stream().skip(pager.getBeginRow()).limit(pager.getPageSize()).collect(Collectors.toList());
        }
        return new ResponseJson(allBoundTeacherByGroup,count);
    }

    @PostMapping("/find/findAllBoundStaffByGroup")
    @ApiOperation(value = "根据分组查找所有已经被绑定的职工（可分页）", notes = "返回响应对象,不包含总条数", response = KqDeviceGroupPerson.class)
    public ResponseJson findAllBoundStaffByGroup(
            @ApiParam(value = "deviceGroupId分组id,departmentId职工所属部门id,workNumber工号,name姓名,要分页传pager")
            @RequestBody KqDeviceGroupPerson kqDeviceGroupPerson) {
        Pager pager = kqDeviceGroupPerson.getPager()==null?null:kqDeviceGroupPerson.getPager();
        List<Teacher> allBoundStaffByGroup = kqDeviceGroupPersonService.findAllBoundStaffByGroup(kqDeviceGroupPerson);
        long count = allBoundStaffByGroup.size();
        if (pager!=null){
            allBoundStaffByGroup=  allBoundStaffByGroup.stream().skip(pager.getBeginRow()).limit(pager.getPageSize()).collect(Collectors.toList());
        }
        return new ResponseJson(allBoundStaffByGroup,count);
    }

    @PostMapping("/find/findAllBoundStudentByGroup")
    @ApiOperation(value = "根据分组查找所有已经被绑定的学生（可分页）", notes = "返回响应对象,不包含总条数", response = KqDeviceGroupPerson.class)
    public ResponseJson findAllBoundStudentByGroup(
            @ApiParam(value = "deviceGroupId分组id,classesId班级id,gradeId年级id,name姓名,要分页传pager")
            @RequestBody KqDeviceGroupPerson kqDeviceGroupPerson) {
        Pager pager = kqDeviceGroupPerson.getPager()==null?null:kqDeviceGroupPerson.getPager();
        List<Student> allBoundStudentByGroup = kqDeviceGroupPersonService.findAllBoundStudentByGroup(kqDeviceGroupPerson);
        long count = allBoundStudentByGroup.size();
        if (pager!=null&&allBoundStudentByGroup.size()>0){
            allBoundStudentByGroup=  allBoundStudentByGroup.stream().skip(pager.getBeginRow()).limit(pager.getPageSize()).collect(Collectors.toList());
        }
        return new ResponseJson(allBoundStudentByGroup,count);

    }

    @PostMapping("/find/findAllUnBoundTeacherByGroup")
    @ApiOperation(value = "根据分组查找所有未被绑定的已注册教师（可分页）", notes = "返回响应对象,不包含总条数", response = KqDeviceGroupPerson.class)
    public ResponseJson findAllUnBoundTeacherByGroup(
            @ApiParam(value = "deviceGroupId分组id,workNumber工号,name姓名,要分页传pager")
            @RequestBody KqDeviceGroupPerson kqDeviceGroupPerson) {
        Pager pager = kqDeviceGroupPerson.getPager()==null?null:kqDeviceGroupPerson.getPager();
        List<Teacher> allUnBoundTeacherByGroup = kqDeviceGroupPersonService.findAllUnBoundTeacherByGroup(kqDeviceGroupPerson);
        long count = allUnBoundTeacherByGroup.size();
        if (pager!=null){
            allUnBoundTeacherByGroup=  allUnBoundTeacherByGroup.stream().skip(pager.getBeginRow()).limit(pager.getPageSize()).collect(Collectors.toList());
        }
        return new ResponseJson(allUnBoundTeacherByGroup,count);
    }

    @PostMapping("/find/findAllUnBoundStaffByGroup")
    @ApiOperation(value = "根据分组查找所有未被绑定的已注册职工（可分页）", notes = "返回响应对象,不包含总条数", response = KqDeviceGroupPerson.class)
    public ResponseJson findAllUnBoundStaffByGroup(
            @ApiParam(value = "deviceGroupId分组id,departmentId职工所属部门id,workNumber工号,name姓名,要分页传pager")
            @RequestBody KqDeviceGroupPerson kqDeviceGroupPerson) {
        Pager pager = kqDeviceGroupPerson.getPager()==null?null:kqDeviceGroupPerson.getPager();
        List<Teacher> allUnBoundStaffByGroup = kqDeviceGroupPersonService.findAllUnBoundStaffByGroup(kqDeviceGroupPerson);
        long count = allUnBoundStaffByGroup.size();
        if (pager!=null){
            allUnBoundStaffByGroup=  allUnBoundStaffByGroup.stream().skip(pager.getBeginRow()).limit(pager.getPageSize()).collect(Collectors.toList());
        }
        return new ResponseJson(allUnBoundStaffByGroup,count);
    }

    @PostMapping("/find/findAllUnBoundStudentByGroup")
    @ApiOperation(value = "根据分组查找所有未被绑定的已注册学生（可分页）", notes = "返回响应对象,不包含总条数", response = KqDeviceGroupPerson.class)
    public ResponseJson findAllUnBoundStudentByGroup(
            @ApiParam(value = "deviceGroupId分组id,classesId班级id,gradeId年级id,name姓名,要分页传pager")
            @RequestBody KqDeviceGroupPerson kqDeviceGroupPerson) {
        Pager pager = kqDeviceGroupPerson.getPager()==null?null:kqDeviceGroupPerson.getPager();
        List<Student> allUnBoundStudentByGroup = kqDeviceGroupPersonService.findAllUnBoundStudentByGroup(kqDeviceGroupPerson);

        long count = allUnBoundStudentByGroup.size();
        if (pager!=null){
        allUnBoundStudentByGroup=  allUnBoundStudentByGroup.stream().skip(pager.getBeginRow()).limit(pager.getPageSize()).collect(Collectors.toList());
        }
        return new ResponseJson(allUnBoundStudentByGroup,count);
    }
    @PostMapping("/find/BindDeviceAndPersonByGroupAndPersons")
    @ApiOperation(value = "根据分组id和人员id列表绑定设备", notes = "返回绑定失败的人员id", response = KqDeviceGroupPerson.class)
    public ResponseJson BindDeviceAndPersonByGroupAndPersons(
            @ApiParam(value = "deviceGroupId分组id,mapForPersonIdListByType预绑定人员id数组和人员类型的map,Map的Key人员类型(1教师，2学生，3其他职工)")
            @RequestBody KqDeviceGroupPerson kqDeviceGroupPerson) {
        Map<String, List<String>> failureMap = kqDeviceGroupPersonService.BindDeviceAndPersonByGroupAndPersons(kqDeviceGroupPerson);
        if (failureMap==null){
            return new ResponseJson(false, "本校未开通此服务，请联系管理员！");
        }
        return new ResponseJson(failureMap);
    }

    @PostMapping("/find/personsUnbindDeviceByGroup")
    @ApiOperation(value = "解绑该分组下一批人员", notes = "返回绑定失败的人员id", response = KqDeviceGroupPerson.class)
    public ResponseJson PersonsUnbindDeviceByGroup(
            @ApiParam(value = "deviceGroupId分组id,mapForPersonIdListByType预解绑定人员id数组和人员类型的Map,Map的Key人员类型(1教师，2学生，3其他职工)")
            @RequestBody KqDeviceGroupPerson kqDeviceGroupPerson) {
        Map<String, List<String>> failureMap = kqDeviceGroupPersonService.personsUnbindDeviceByGroup(kqDeviceGroupPerson);
        if (failureMap==null){
            return new ResponseJson(false, "本校未开通此服务，请联系管理员！");
        }
        if (failureMap.size()==0){
            return new ResponseJson();
        }
        return new ResponseJson(failureMap);
    }
}
