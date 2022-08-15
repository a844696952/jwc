package com.yice.edu.cn.osp.controller.jw.electiveCourse;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.electiveCourse.*;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import com.yice.edu.cn.osp.service.jw.classes.JwClassesService;
import com.yice.edu.cn.osp.service.jw.electiveCourse.ElectiveClassesService;
import com.yice.edu.cn.osp.service.jw.electiveCourse.ElectiveCourseService;
import com.yice.edu.cn.osp.service.jw.electiveCourse.ElectiveSetService;
import com.yice.edu.cn.osp.service.jw.electiveCourse.ElectiveStudentService;
import com.yice.edu.cn.osp.service.jw.schoolYear.SchoolYearService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

import static com.yice.edu.cn.common.easypoiplus.ExcelUtil.getDataValidationByExplicitListValues;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/electiveStudent")
@Api(value = "/electiveStudent",description = "选修学生关联表模块")
public class ElectiveStudentController {
    @Autowired
    private ElectiveStudentService electiveStudentService;
    @Autowired
    private ElectiveClassesService electiveClassesService;
    @Autowired
    private ElectiveCourseService electiveCourseService;
    @Autowired
    private ElectiveSetService electiveSetService;
    @Autowired
    private SchoolYearService schoolYearService;
    @Autowired
    private CurSchoolYearService curSchoolYearService;
    @Autowired
    private JwClassesService jwClassesService;

    @PostMapping("/saveElectiveStudent")
    @ApiOperation(value = "保存选修学生关联表对象", notes = "返回保存好的选修学生关联表对象", response=ElectiveStudent.class)
    public ResponseJson saveElectiveStudent(
            @ApiParam(value = "选修学生关联表对象", required = true)
            @RequestBody ElectiveStudent electiveStudent){
        electiveStudent.setSchoolId(mySchoolId());
        electiveStudent.setIsScore(0);
        curSchoolYearService.setSchoolYearTerm(electiveStudent,mySchoolId());
        Map result=electiveStudentService.saveElectiveStudent(electiveStudent);
        if((boolean) result.get("result")){
            return new ResponseJson();
        }else {
            return new ResponseJson(false,result.get("msg"));
        }
    }

    @GetMapping("/update/findElectiveStudentById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找选修学生关联表", notes = "返回响应对象", response=ElectiveStudent.class)
    public ResponseJson findElectiveStudentById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        ElectiveStudent electiveStudent=electiveStudentService.findElectiveStudentById(id);
        return new ResponseJson(electiveStudent);
    }

    @PostMapping("/update/updateElectiveStudent")
    @ApiOperation(value = "修改选修学生关联表对象", notes = "返回响应对象")
    public ResponseJson updateElectiveStudent(
            @ApiParam(value = "被修改的选修学生关联表对象,对象属性不为空则修改", required = true)
            @RequestBody ElectiveStudent electiveStudent){
        electiveStudentService.updateElectiveStudent(electiveStudent);
        return new ResponseJson();
    }

    @GetMapping("/look/lookElectiveStudentById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找选修学生关联表", notes = "返回响应对象", response=ElectiveStudent.class)
    public ResponseJson lookElectiveStudentById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        ElectiveStudent electiveStudent=electiveStudentService.findElectiveStudentById(id);
        return new ResponseJson(electiveStudent);
    }

    @PostMapping("/findElectiveStudentsByCondition")
    @ApiOperation(value = "根据条件查找选修学生关联表", notes = "返回响应对象", response=ElectiveStudent.class)
    public ResponseJson findElectiveStudentsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ElectiveStudent electiveStudent){
        if( electiveStudent.getPager().getRangeArray().length>0){
            electiveStudent.getPager().getRangeArray()[0]= electiveStudent.getPager().getRangeArray()[0]+" 00:00:00";
            electiveStudent.getPager().getRangeArray()[1]= electiveStudent.getPager().getRangeArray()[1]+" 23:59:59";
        }
        if(electiveStudent.getScore()!=null&&electiveStudent.getScore()>0){
            electiveStudent.setIsScore(1);
        }else if(electiveStudent.getScore()!=null&&electiveStudent.getScore()==0){
            electiveStudent.setIsScore(0);
            electiveStudent.setScore(null);
        }
        electiveStudent.setSchoolId(mySchoolId());
        List<ElectiveStudent> data=electiveStudentService.findElectiveStudentListByCondition(electiveStudent);
        data.stream().filter(e->e.getIsScore().equals(0)).forEach(a->a.setScore(0));
        long count=electiveStudentService.findElectiveStudentCountByCondition(electiveStudent);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneElectiveStudentByCondition")
    @ApiOperation(value = "根据条件查找单个选修学生关联表,结果必须为单条数据", notes = "没有时返回空", response=ElectiveStudent.class)
    public ResponseJson findOneElectiveStudentByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody ElectiveStudent electiveStudent){
        ElectiveStudent one=electiveStudentService.findOneElectiveStudentByCondition(electiveStudent);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteElectiveStudent/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteElectiveStudent(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        electiveStudentService.deleteElectiveStudent(id);
        return new ResponseJson();
    }


    @PostMapping("/findElectiveStudentListByCondition")
    @ApiOperation(value = "根据条件查找选修学生关联表列表", notes = "返回响应对象,不包含总条数", response=ElectiveStudent.class)
    public ResponseJson findElectiveStudentListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ElectiveStudent electiveStudent){
       electiveStudent.setSchoolId(mySchoolId());
        List<ElectiveStudent> data=electiveStudentService.findElectiveStudentListByCondition(electiveStudent);
        return new ResponseJson(data);
    }

    @GetMapping("/findStudentListByElectiveId/{electiveId}")
    @ApiOperation(value = "传入选修id，查询符合条件的学生数组", notes = "返回响应对象")
    public ResponseJson findStudentListByClassIdList(@PathVariable String electiveId){
        List<StudentPojo> gradeList=new ArrayList<>();
        ElectiveClasses electiveClasses=new ElectiveClasses();
        electiveClasses.setElectiveId(electiveId);
        List<ElectiveClasses> classesList= electiveClassesService.findElectiveClassesListByCondition(electiveClasses);
        List<String> classIdList=new ArrayList<>();
        classesList.forEach(c->{
            classIdList.add(c.getClassesId());
        });
        List<StudentPojo> data=null;
        if(classIdList.size()>0){
            data= electiveCourseService.findStudentListByClassIdList(classIdList);
            ElectiveStudent electiveStudent=new ElectiveStudent();
            electiveStudent.setElectiveId(electiveId);
            List<ElectiveStudent> electiveStudentList=electiveStudentService.findElectiveStudentListByCondition(electiveStudent);
           if(electiveStudentList.size()>0){
               data.removeIf(f -> electiveStudentList.stream().anyMatch(v -> v.getStudentId().equals(f.getId())));
           }

            Map<Integer,Map<Integer,List<StudentPojo>>> map=data.stream().collect(Collectors.groupingBy(StudentPojo::getGradeId1,Collectors.groupingBy(StudentPojo::getClassesNum)));
            map.forEach((k,v)->{
                StudentPojo s=new StudentPojo();
                s.setGradeName(v.values().stream().findFirst().get().get(0).getGradeName());
                List<StudentPojo> classList=new ArrayList<>();
                v.forEach((k1,v1)->{
                    StudentPojo classes=new StudentPojo();
                    classes.setGradeName(v1.get(0).getGradeName()+k1+"班");

                    if(v1.size()>=1&&v1.get(0).getId()!=null){
                        List<StudentPojo> stuList=new ArrayList<>();
                        v1.forEach(stu->{
                            StudentPojo student=new StudentPojo();
                            student.setElectiveId(electiveId);
                            student.setClassesName(stu.getClassesNum()+"班");
                            student.setClassesId(stu.getClassesId());
                            student.setGradeName(stu.getGradeName());
                            student.setGradeId(stu.getGradeId1().toString());
                            student.setStudentId(stu.getId());
                            student.setSex(stu.getSex());
                            student.setStudentName(stu.getName());
                            student.setStudentNo(stu.getStudentNo());
                            stuList.add(student);
                        });
                        classes.setStudentPojoList(stuList);
                    }
                    classList.add(classes);
                });
                s.setStudentPojoList(classList);
                gradeList.add(s);
            });
        }

        return new ResponseJson(gradeList);

    }

    @PostMapping("/batchSaveElectiveStudent")
    @ApiOperation(value = "批量保存选修学生关联表", notes = "返回选修学生关联表对象")
    public ResponseJson batchSaveElectiveStudent(
            @ApiParam(value = "选修学生关联表对象", required = true)
            @RequestBody List<ElectiveStudent> electiveStudents){
        CurSchoolYear s=schoolYearService.findCurSchoolYear(mySchoolId());
        electiveStudents.forEach(e->{
            e.setSchoolId(mySchoolId());
            e.setSchoolYearId(s.getSchoolYearId());
            e.setFromTo(s.getFromTo());
            e.setTerm(s.getTerm());
            e.setIsScore(0);
        });
        Map result=electiveStudentService.batchSaveElectiveStudent(electiveStudents);
        if((boolean) result.get("result")){
            return new ResponseJson();
        }else {
            return new ResponseJson(false,result.get("msg"));
        }
    }


    @GetMapping("/exportTemplate/{electiveId}")
    @ApiOperation(value = "导出模板", notes = "返回响应对象,不包含总条数", response=ElectiveStudent.class)
    public void exportTemplate(HttpServletResponse response,@PathVariable String electiveId){

        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=ElectiveStudent.xls");
        try (ServletOutputStream s = response.getOutputStream()) {
            Workbook workbook = electiveStudentService.exportTemplate(electiveId);
            createCustomRowAndName(workbook);
            workbook.write(s);
        } catch (Exception e) {

        }

    }

    //生成下拉框
    private void createCustomRowAndName(Workbook workbook) {
        Set<String> gradeNameSet=new LinkedHashSet<>();
        gradeNameSet.add("是");
        gradeNameSet.add("否");
        final Sheet sheet1 = workbook.getSheet("选修课");
        for (int i = 2; i <sheet1.getLastRowNum()+1 ; i++) {
            final DataValidation dataValidation1 = getDataValidationByExplicitListValues(gradeNameSet, i, 0, 8, 0);
            sheet1.addValidationData(dataValidation1);
        }
    }
    @PostMapping("/uploadElectiveStudent")
    @ApiOperation(value = "导入", notes = "返回响应对象,不包含总条数", response=ElectiveStudent.class)
    public ResponseJson uploadElectiveStudent(@RequestParam("file")MultipartFile file) {
        return new ResponseJson(electiveStudentService.uploadElectiveStudent(file));
    }


    @PostMapping("/findMyElectiveStudentsByCondition")
    @ApiOperation(value = "根据条件查找选课报名情况选修学生关联表", notes = "返回响应对象", response=ElectiveStudent.class)
    public ResponseJson findMyElectiveStudentsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ElectiveStudent electiveStudent){
        electiveStudent.setSchoolId(mySchoolId());
        List<ElectiveStudent> data=electiveStudentService.findMyElectiveStudentListByCondition(electiveStudent);
        long count=electiveStudentService.findMyElectiveStudentCountByCondition(electiveStudent);
        return new ResponseJson(data,count);
    }


    @PostMapping("/findSchoolYearElectiveStudentsByCondition")
    @ApiOperation(value = "根据条件查找本学年选课报名情况选修学生", notes = "返回响应对象", response=ElectiveStudent.class)
    public ResponseJson findSchoolYearElectiveStudentsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ElectiveStudent electiveStudent){
        electiveStudent.setSchoolId(mySchoolId());
        curSchoolYearService.setSchoolYearId(electiveStudent,mySchoolId());
        List<ElectiveStudent> data=electiveStudentService.findSchoolYearElectiveStudentsByCondition(electiveStudent);
        long count=electiveStudentService.findSchoolYearElectiveStudentsCountByCondition(electiveStudent);
        return new ResponseJson(data,count);
    }







    @GetMapping("/findSearchGradeClassesConditionList/{electiveId}")
    @ApiOperation(value = "获取搜索栏年级班级条件结果集", notes = "返回响应对象", response=ElectiveStudent.class)
    public ResponseJson findSearchGradeClassesConditionList(
            @PathVariable String electiveId){
        ElectiveClasses ec=new ElectiveClasses();
        ec.setElectiveId(electiveId);
        ec.setSchoolId(mySchoolId());
        List<ElectiveClasses> electiveClassesList=electiveClassesService.findElectiveClassesListByCondition(ec);
        Map<Integer,List<ElectiveClasses>> map=electiveClassesList.stream().collect(Collectors.groupingBy(ElectiveClasses::getGradeId));
        List<GradeClassesPojo> gradeList=new ArrayList<>();
        List<List> classesList=new ArrayList<>();
        map.forEach((k,v)->{
            gradeList.add(new GradeClassesPojo(){{
                this.setGradeId(v.get(0).getGradeId());
                this.setGradeName(v.get(0).getGradeName());
            }
            });
            List<GradeClassesPojo> cList=new ArrayList<>();
            v.forEach(e->{
                cList.add(new GradeClassesPojo(){{
                    this.setClassesId(e.getClassesId());
                    this.setGradeName(e.getClassesName());
                }
                });

            });

            classesList.add(cList);

        });


        return new ResponseJson(gradeList,classesList);
    }



    @PostMapping("/getCanSelectCourseList")
    @ApiOperation(value = "选课报名情况页面，获取可选课程集合,传入班级id:classesId,studentId,gradeId", notes = "返回响应对象", response=ElectiveCourse.class)
    public ResponseJson getCanSelectCourseList(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody ElectiveStudent electiveStudent){
        //获取可选课程集合
        electiveStudent.setSchoolId(mySchoolId());
        curSchoolYearService.setSchoolYearId(electiveStudent,mySchoolId());
        List<ElectiveCourse> data=electiveStudentService.getCanSelectCourseList(electiveStudent);
        //获取已选课程集合
        List<ElectiveCourse> data1=electiveStudentService.getAlreadySelectCourseList(electiveStudent);


        data.removeIf(e->data1.stream().anyMatch(v->v.getElectiveId().equals(e.getElectiveId())));


        //获取已选学生人数
        ElectiveStudent e=new ElectiveStudent();
        curSchoolYearService.setSchoolYearId(e,mySchoolId());
        e.setStudentId(electiveStudent.getStudentId());
        long alreadyNum= electiveStudentService.findElectiveStudentCountByCondition(e);
        //获取年段允许选择的最大课程数
        ElectiveSet electiveSet=new ElectiveSet();
        electiveSet.setGradeId(electiveStudent.getGradeId());
        electiveSet.setSchoolId(mySchoolId());
        ElectiveSet electiveSet1=electiveSetService.findOneElectiveSetByCondition(electiveSet);


        ElectiveStudent s=new ElectiveStudent();
        s.setCanSelectList(data);
        s.setAlreadySelectList(data1);
        s.setAlreadySelectNum(Integer.parseInt(String.valueOf(alreadyNum)));
        if(electiveSet1!=null){
            s.setMaxNum(electiveSet1.getMaxNum());
        }else {
            return new ResponseJson(false,"选修课未设置");
        }
        return new ResponseJson(s);
    }

    @PostMapping("/findJwClassessByCondition")
    @ApiOperation(value = "根据条件查找班级信息,传入gradeId", notes = "返回响应对象")
    public ResponseJson findJwClassessByCondition(@ApiParam(value = "属性不为空则作为条件查询") @RequestBody JwClasses jwClasses) {
        jwClasses.setSchoolId(mySchoolId());
        jwClasses.getPager().setPaging(false);
        List<JwClasses> data = jwClassesService.findJwClassesListByCondition(jwClasses);
        return new ResponseJson(data);
    }


    @PostMapping("/findSchoolYearStudentScoreListByCondition")
    @ApiOperation(value = "根据条件查找学生选课选分总览", notes = "返回响应对象", response=ElectiveStudent.class)
    public ResponseJson findSchoolYearStudentScoreListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ElectiveStudent electiveStudent){
        electiveStudent.setSchoolId(mySchoolId());
        curSchoolYearService.setSchoolYearId(electiveStudent,mySchoolId());
        List<ElectiveStudent> data=electiveStudentService.findSchoolYearStudentScoreListByCondition(electiveStudent);
        long count=electiveStudentService.findSchoolYearStudentScoreCountByCondition(electiveStudent);
        return new ResponseJson(data,count);
    }

    @PostMapping("/findElectiveStudentsScoreByCondition")
    @ApiOperation(value = "根据条件查找选修学生关联表", notes = "返回响应对象", response=ElectiveStudent.class)
    public ResponseJson findElectiveStudentsScoreByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ElectiveStudent electiveStudent){
        electiveStudent.setSchoolId(mySchoolId());
        List<ElectiveStudent> data=electiveStudentService.findElectiveStudentScoreListByCondition(electiveStudent);
        long count=electiveStudentService.findElectiveStudentScoreCountByCondition(electiveStudent);
        return new ResponseJson(data,count);
    }

}
