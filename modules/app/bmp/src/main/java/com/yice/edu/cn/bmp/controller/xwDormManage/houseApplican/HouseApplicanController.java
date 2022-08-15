package com.yice.edu.cn.bmp.controller.xwDormManage.houseApplican;

import com.yice.edu.cn.bmp.feignClient.teacher.TeacherClassesFeign;
import com.yice.edu.cn.bmp.service.parent.ParentService;
import com.yice.edu.cn.bmp.service.student.StudentService;
import com.yice.edu.cn.bmp.service.teacher.TeacherService;
import com.yice.edu.cn.bmp.service.xwDormManage.DormPersonService;
import com.yice.edu.cn.bmp.service.xwDormManage.houseApplican.HouseApplicanFilesService;
import com.yice.edu.cn.bmp.service.xwDormManage.houseApplican.HouseApplicanService;
import com.yice.edu.cn.bmp.service.xwDormManage.houseApplican.HouseApplicanStudentsService;
import com.yice.edu.cn.bmp.service.xwDormManage.houseApplican.HouseApplicanTeachersService;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.parent.Parent;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.*;
import com.yice.edu.cn.common.util.oss.QiniuUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.*;


@RestController
@RequestMapping("/houseApplican")
@Api(value = "/houseApplican",description = "模块")
public class HouseApplicanController {
    @Autowired
    private HouseApplicanService houseApplicanService;
    @Autowired
    private DormPersonService dormPersonService;
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
    @Autowired
    private TeacherClassesFeign teacherClassesFeign;
    @Autowired
    private ParentService parentService;



    @PostMapping("/saveHouseApplican")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response= HouseApplican.class)
    public ResponseJson saveHouseApplican(
            @ApiParam(value = "对象", required = true)
            @RequestBody HouseApplican houseApplican){
        //当前孩子班主任
        Student student = studentService.findStudentById(myStudentId());
        TeacherClasses teacherClasses = new TeacherClasses();
        teacherClasses.setClassesId(student.getClassesId());
        Teacher teacher = teacherClassesFeign.findHeadmasterByClasses(teacherClasses);
        DormPerson dormPerson = new DormPerson();
        dormPerson.setPersonId(myStudentId());
        long count = dormPersonService.findDormPersonCountByCondition(dormPerson);
        if (teacher == null) {
            return new ResponseJson(false, "该学生所在班级未绑定班主任");
        } else if (count > 0 ){
            return new ResponseJson(false, "该孩子已经是住宿生");
        }else{
            houseApplican.setSchoolId(teacher.getSchoolId());
            houseApplican.setHeadTeacherId(teacher.getId());

            Parent parentById = parentService.findParentById(currentParent().getId());
            houseApplican.setInitiateId(myParentId());
            houseApplican.setInitiateName(houseApplican.getInitiateName());
            houseApplican.setInitiateTel(parentById.getTel());

            houseApplican.setInitiatePort("1");  //为家长端提交
            houseApplican.setStatus("0");
            houseApplican.setProgress("0");
            houseApplican.setStuId(houseApplican.getStudentsId());
            HouseApplican s=houseApplicanService.saveHouseApplican(houseApplican);
            return new ResponseJson(s);
        }



    }





    @GetMapping("/look/lookHouseApplicanById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=HouseApplican.class)
    public ResponseJson lookHouseApplicanById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        HouseApplican houseApplican=houseApplicanService.findHouseApplicanById(id);
        Student student = studentService.findStudentById(myStudentId());
        //添加 申请对象
        houseApplican.setStudentsName(student.getName());

        HouseApplicanFiles houseApplicanFiles = new HouseApplicanFiles();
        houseApplicanFiles.setHouseApplicanId(id);
        List<HouseApplicanFiles> filesList= houseApplicanFilesService.findHouseApplicanFilesListByCondition(houseApplicanFiles);
       if (filesList.size()>0){
            houseApplican.setHouseApplicanFiles(filesList);
       }

        HouseApplicanStudents houseApplicanStudents = new HouseApplicanStudents();
        houseApplicanStudents.setHouseApplicanId(id);
        List<HouseApplicanStudents> list = houseApplicanStudentsService.findHouseApplicanStudentsListByCondition(houseApplicanStudents);
        /* List<String> studentIdlist = houseApplicanStudentsService.findHouseApplicanStudentsListByCondition(houseApplicanStudents)
               .stream().map(HouseApplicanStudents :: getStudentId).distinct().collect(Collectors.toList());
        List<Student> studentlist = new ArrayList<>();
        for (String s : studentIdlist){
            Student studentById = studentService.findStudentById(s);
            studentlist.add(studentById);
        }*/

        HouseApplicanTeachers houseApplicanTeachers = new HouseApplicanTeachers();
        houseApplicanTeachers.setHouseApplicanId(id);
        List<HouseApplicanTeachers> teacherFewList = houseApplicanTeachersService.findHouseApplicanTeachersListByCondition(houseApplicanTeachers);
        List<String> teacherByIdList = teacherFewList.stream().map(HouseApplicanTeachers::getTeacherId).collect(Collectors.toList());
        List<Teacher> teachersList = new ArrayList<>();
        for (String s : teacherByIdList){
            Teacher teacherById = teacherService.findTeacherById(s);
            teachersList.add(teacherById);
        }
        for( int i = 0;i<teacherFewList.size();i++){
            teacherFewList.get(i).setTeacherName(teachersList.get(i).getName());
            teacherFewList.get(i).setTeacherUrl(teachersList.get(i).getImgUrl());
        }


        return new ResponseJson(houseApplican,list,teacherFewList);
    }

    @PostMapping("/findHouseApplicansByCondition")
    @ApiOperation(value = "根据条件查找首页", notes = "返回响应对象", response=HouseApplican.class)
    public ResponseJson findHouseApplicansByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody HouseApplican houseApplican){
        houseApplican.setSchoolId(currentParent().getSchoolId());
        houseApplican.setInitiateId(currentParent().getId());
     /*   Pager pager = new Pager();
        pager.setSortField("createTime");
        pager.setSortOrder(Pager.DESC);
        houseApplican.setPager(pager);*/
        List<HouseApplican> data=houseApplicanService.findHouseApplicanListByCondition(houseApplican);
        Student student = studentService.findStudentById(myStudentId());
        for (HouseApplican s: data){
            s.setStudentsId(student.getId());
            s.setInitiateName(student.getName());
        }
        long count=houseApplicanService.findHouseApplicanCountByCondition(houseApplican);
        return new ResponseJson(data,count);
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


}
