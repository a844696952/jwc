package com.yice.edu.cn.osp.controller.xw.dormManage.houseApplican;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.*;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.osp.service.jw.department.DepartmentService;
import com.yice.edu.cn.osp.service.jw.student.StudentService;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherService;
import com.yice.edu.cn.osp.service.xw.dormManage.dorm.DormBuildAdminService;
import com.yice.edu.cn.osp.service.xw.dormManage.dorm.DormPersonService;
import com.yice.edu.cn.osp.service.xw.dormManage.houseApplican.HouseApplicanFilesService;
import com.yice.edu.cn.osp.service.xw.dormManage.houseApplican.HouseApplicanService;
import com.yice.edu.cn.osp.service.xw.dormManage.houseApplican.HouseApplicanStudentsService;
import com.yice.edu.cn.osp.service.xw.dormManage.houseApplican.HouseApplicanTeachersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
@RestController
@RequestMapping("/houseApplican")
@Api(value = "/houseApplican",description = "模块")
public class HouseApplicanController {
    @Autowired
    private HouseApplicanService houseApplicanService;
    @Autowired
    private DormPersonService dormPersonService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private DormBuildAdminService dormBuildAdminService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private HouseApplicanFilesService houseApplicanFilesService;
    @Autowired
    private HouseApplicanStudentsService houseApplicanStudentsService;
    @Autowired
    private HouseApplicanTeachersService houseApplicanTeachersService;
    @Autowired
    private StudentService studentService;


    @PostMapping("/saveHouseApplican")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response= HouseApplican.class)
    public ResponseJson saveHouseApplican(
            @ApiParam(value = "对象", required = true)
            @RequestBody HouseApplican houseApplican){
        houseApplican.setSchoolId(mySchoolId());
        houseApplican.setHeadTeacherId(myId());

        Teacher teacherById = teacherService.findTeacherById(myId());
        houseApplican.setInitiateId(teacherById.getId());
        houseApplican.setInitiateName(teacherById.getName());
        houseApplican.setInitiateTel(teacherById.getTel());

        houseApplican.setInitiatePort("0");  //为教师端提交
        houseApplican.setStatus("0");
        houseApplican.setProgress("0");
        HouseApplican s=houseApplicanService.saveHouseApplican(houseApplican);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findHouseApplicanById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=HouseApplican.class)
    public ResponseJson findHouseApplicanById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        HouseApplican houseApplican=houseApplicanService.findHouseApplicanById(id);
        return new ResponseJson(houseApplican);
    }

    @PostMapping("/update/updateHouseApplican")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateHouseApplican(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody HouseApplican houseApplican){
        houseApplicanService.updateHouseApplican(houseApplican);
        return new ResponseJson();
    }

    @GetMapping("/look/lookHouseApplicanById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=HouseApplican.class)
    public ResponseJson lookHouseApplicanById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        HouseApplican houseApplican=houseApplicanService.findHouseApplicanById(id);
        HouseApplicanFiles houseApplicanFiles = new HouseApplicanFiles();
        houseApplicanFiles.setHouseApplicanId(id);
        List<HouseApplicanFiles> filesList= houseApplicanFilesService.findHouseApplicanFilesListByCondition(houseApplicanFiles);
       if (filesList.size()>0){
            houseApplican.setHouseApplicanFiles(filesList);
       }

        HouseApplicanStudents houseApplicanStudents = new HouseApplicanStudents();
        houseApplicanStudents.setHouseApplicanId(id);
        List<String> studentIdlist = houseApplicanStudentsService.findHouseApplicanStudentsListByCondition(houseApplicanStudents)
                .stream().map(HouseApplicanStudents :: getStudentId).distinct().collect(Collectors.toList());
        List<Student> studentlist = new ArrayList<>();
        for (String s : studentIdlist){
            Student studentById = studentService.findStudentById(s);
            studentlist.add(studentById);
        }

        HouseApplicanTeachers houseApplicanTeachers = new HouseApplicanTeachers();
        houseApplicanTeachers.setHouseApplicanId(id);
        List<HouseApplicanTeachers> teacherFewList = houseApplicanTeachersService.findHouseApplicanTeachersListByCondition(houseApplicanTeachers);
        List<String> teacherByIdList = teacherFewList.stream().map(HouseApplicanTeachers::getTeacherId).distinct().collect(Collectors.toList());
        List<Teacher> teachersList = new ArrayList<>();
        for (String s : teacherByIdList){
            Teacher teacherById = teacherService.findTeacherById(s);
            teachersList.add(teacherById);
        }

        return new ResponseJson(houseApplican,studentlist,teacherFewList,teachersList);
    }

    @PostMapping("/findHouseApplicansByCondition")
    @ApiOperation(value = "根据条件查找首页", notes = "返回响应对象", response=HouseApplican.class)
    public ResponseJson findHouseApplicansByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody HouseApplican houseApplican){
        houseApplican.setSchoolId(mySchoolId());
        houseApplican.setInitiateId(currentTeacher().getId());
        /*Pager pager = new Pager();
        pager.setSortField("createTime");
        pager.setSortOrder(Pager.DESC);
        houseApplican.setPager(pager);*/
        List<HouseApplican> data=houseApplicanService.findHouseApplicanListByCondition(houseApplican);
        long count=houseApplicanService.findHouseApplicanCountByCondition(houseApplican);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneHouseApplicanByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=HouseApplican.class)
    public ResponseJson findOneHouseApplicanByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody HouseApplican houseApplican){
        HouseApplican one=houseApplicanService.findOneHouseApplicanByCondition(houseApplican);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteHouseApplican/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteHouseApplican(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        houseApplicanService.deleteHouseApplican(id);
        return new ResponseJson();
    }


    @PostMapping("/findHouseApplicanListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=HouseApplican.class)
    public ResponseJson findHouseApplicanListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody HouseApplican houseApplican){
       houseApplican.setSchoolId(mySchoolId());
        List<HouseApplican> data=houseApplicanService.findHouseApplicanListByCondition(houseApplican);
        return new ResponseJson(data);
    }


    @PostMapping("/ignore/uploadFile")
    @ApiOperation(value = "说明：上传文件到七牛", notes = "返回资源名称和资源的url", response = HouseApplican.class)
    public ResponseJson uploadQiniuFile(@ApiParam(value = "上传到七牛的文件", required = true) MultipartFile file) {
        //文件名后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        Map<String, String> map = new HashMap<>();
        //获取保存文件路径
        String url = QiniuUtil.commonUploadFile(file, Constant.Upload.XW_HOUSE_APPLICAN_FILES + suffix);
        map.put("url", url);
        map.put("contractName", file.getOriginalFilename());
        return new ResponseJson(map);
    }



    @PostMapping("/look/lookStudentByClassId")
    @ApiOperation(value = "选择学生,classId", notes = "返回响应对象", response=HouseApplican.class)
    public ResponseJson lookStudentByClassId(
            @ApiParam(value = "去查看页面,需要用到的classId", required = true)
            @Validated
            @RequestBody Student student){
        student.setSchoolId(mySchoolId());
       /* Pager pager = student.getPager();
        pager.setLike("studentCode");
        pager.setLike("name");
        student.setPager(pager)*/;
        //student.getPager().setLike("name");
        // student.getPager().setLike("studentCode");
        List<Student> data=dormPersonService.findStudentListByConditionOnDorm(student);
        long count=dormPersonService.findStudentListCountByConditionOnDorm(student);
        return new ResponseJson(data,count);
    }


    @GetMapping("/update/findDepartmentForTree")
    @ApiOperation(value = "查找组织架构树,包含了成员（教师）")
    public ResponseJson selectDept() {
        return new ResponseJson(departmentService.findDepartmentTreeBySchoolId(mySchoolId()));
    }


    @GetMapping("/findDormBuildTeacherByConditionConnect")
    @ApiOperation(value = "根据条件查找宿舍老师", notes = "返回宿舍老师对象列表", response= DormBuildAdmin.class)
    public ResponseJson findDormBuildTeacherByConditionConnect(){
        DormBuildAdmin dormBuildAdmin = new DormBuildAdmin();
        dormBuildAdmin.setSchoolId(mySchoolId());
        dormBuildAdmin.setStaffType(Constant.DORM_STAFF_TYPE.DORM_TEACHER);
        List<DormBuildAdmin> data=dormBuildAdminService.findDormBuildTeacherByConditionConnect(dormBuildAdmin);
        List<String> teacherIds = data.stream().map(DormBuildAdmin::getStaffId).collect(Collectors.toList());
        /*Teacher teacher = new Teacher();
        teacher.setSchoolId(mySchoolId());*/
        List teacherList = new ArrayList<>();
        for (String s : teacherIds){
            //teacher.setId(s);
            Teacher teacherById = teacherService.findTeacherById(s);
            teacherList.add(teacherById);
        }
        long count=dormBuildAdminService.findDormBuildAdminCountByCondition(dormBuildAdmin);
        return new ResponseJson(teacherList,count);
    }





    @PostMapping("/findApprovalPending")
    @ApiOperation(value = " 待审批", notes = "返回响应对象", response=HouseApplican.class)
    public ResponseJson findApprovalPending(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody HouseApplican houseApplican){
        houseApplican.setSchoolId(mySchoolId());
        houseApplican.setNowTeacherId(currentTeacher().getId());
        List<HouseApplican> data=houseApplicanService.findApprovalPending(houseApplican);
        long count=houseApplicanService.findApprovalPendingCount(houseApplican);
        return new ResponseJson(data,count);
    }

    @PostMapping("/findPassPending")
    @ApiOperation(value = "已审批", notes = "返回响应对象", response=HouseApplican.class)
    public ResponseJson findPassPending(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody HouseApplican houseApplican){
        houseApplican.setSchoolId(mySchoolId());
        houseApplican.setNowTeacherId(currentTeacher().getId());
        List<HouseApplican> data=houseApplicanService.findPassPending(houseApplican);
        long count=houseApplicanService.findPassPendingCount(houseApplican);
        return new ResponseJson(data,count);
    }


    @PostMapping("/saveHouseApplicanFromParent")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response= HouseApplican.class)
    public ResponseJson saveHouseApplicanFromParent(
            @ApiParam(value = "对象", required = true)
            @RequestBody HouseApplican houseApplican){
        houseApplican.setSchoolId(mySchoolId());

        HouseApplicanTeachers hTeachers = new HouseApplicanTeachers();
        hTeachers.setTeacherId(myId());
        hTeachers.setHouseApplicanId(houseApplican.getId());
        List<HouseApplicanTeachers> list = houseApplicanTeachersService.findHouseApplicanTeachersListByCondition(hTeachers);
        if (list.get(0).getStatus().equals("0")){
            HouseApplican s=houseApplicanService.saveHouseApplicanFromParent(houseApplican);
            return new ResponseJson();
        }else
            return new ResponseJson(false,"此审批单已在其他端处理");
    }

    @PostMapping("/update/updateHouseApplicanAndTeacher")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateHouseApplicanAndTeacher(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody HouseApplican houseApplican){
        //当前老师审批时  两个端口都打开处理
        //每次操作时  都要先查询当前教师的审批状态 是否为 未审批
        HouseApplicanTeachers hTeachers = new HouseApplicanTeachers();
        hTeachers.setTeacherId(myId());
        hTeachers.setHouseApplicanId(houseApplican.getId());
        List<HouseApplicanTeachers> list = houseApplicanTeachersService.findHouseApplicanTeachersListByCondition(hTeachers);
        if (list.get(0).getStatus().equals("0")){
            houseApplicanService.updateHouseApplicanAndTeacher(houseApplican);
            return new ResponseJson();
        }else
            return new ResponseJson(false,"此审批单已在其他端处理");
    }

}
