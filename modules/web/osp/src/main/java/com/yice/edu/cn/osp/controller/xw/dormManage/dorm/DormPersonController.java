package com.yice.edu.cn.osp.controller.xw.dormManage.dorm;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.building.Building;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.*;
import com.yice.edu.cn.osp.service.xw.dormManage.dorm.DormBuildAdminService;
import com.yice.edu.cn.osp.service.xw.dormManage.dorm.DormPersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dormPerson")
@Api(value = "/dormPerson",description = "住宿人员模块")
public class DormPersonController {
    @Autowired
    private DormPersonService dormPersonService;
    @Autowired
    private DormBuildAdminService dormBuildAdminService;

    @PostMapping("/saveDormPersonOne")
    @ApiOperation(value = "在宿舍中添加单个人员")
    public ResponseJson saveDormPersonOne(
            @ApiParam(value = "对象", required = true)
            @RequestBody DormPerson dormPerson){
      dormPerson.setSchoolId(mySchoolId());
      dormPersonService.updateSaveDormPerson(dormPerson);
      return new ResponseJson();
    }

    @GetMapping("/update/findDormPersonByCondition")
    @ApiOperation(value = "查找单个需要修改的信息", notes = "返回响应对象", response=DormPerson.class)
    public ResponseJson findDormPersonByCondition(
            @ApiParam(value = "id,DormCategory必填", required = true)
            @RequestBody DormBuildVo dormBuildVo){
        DormPerson dormPerson=dormPersonService.findDormPersonByCondition(dormBuildVo);
        return new ResponseJson(dormPerson);
    }

    @PostMapping("/update/updateDormPerson")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateDormPerson(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody DormPerson dormPerson){
        dormPersonService.updateDormPerson(dormPerson);
        return new ResponseJson();
    }

    @GetMapping("/look/lookDormPersonById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=DormPerson.class)
    public ResponseJson lookDormPersonById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        DormPerson dormPerson=dormPersonService.findDormPersonById(id);
        return new ResponseJson(dormPerson);
    }

    @PostMapping("/findDormPersonsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=DormPerson.class)
    public ResponseJson findDormPersonsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DormPerson dormPerson){
       dormPerson.setSchoolId(mySchoolId());
        List<DormPerson> data=dormPersonService.findDormPersonListByCondition(dormPerson);
        long count=dormPersonService.findDormPersonCountByCondition(dormPerson);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDormPersonByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=DormPerson.class)
    public ResponseJson findOneDormPersonByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DormPerson dormPerson){
        DormPerson one=dormPersonService.findOneDormPersonByCondition(dormPerson);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteDormPerson/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDormPerson(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        dormPersonService.deleteDormPerson(id);
        return new ResponseJson();
    }


    @PostMapping("/findDormPersonListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=DormPerson.class)
    public ResponseJson findDormPersonListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DormPerson dormPerson){
       dormPerson.setSchoolId(mySchoolId());
        List<DormPerson> data=dormPersonService.findDormPersonListByCondition(dormPerson);
        return new ResponseJson(data);
    }

/*------------------------------------------------------------------------------------------------------------*/
    @PostMapping("/findStudentListByCondition")
    @ApiOperation(value = "根据条件查找全部学生信息", notes = "返回学生列表对象",response = Student.class)
    public ResponseJson findStudentListByConditionOnDorm(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Student student){
        student.setSchoolId(mySchoolId());
        List<Student> data=dormPersonService.findStudentListByConditionOnDorm(student);
        long count=dormPersonService.findStudentListCountByConditionOnDorm(student);
        return new ResponseJson(data,count);
    }

    @PostMapping("/find/findTeachersAndNoByCondition")
    @ApiOperation(value = "根据条件查找教师信息", notes = "返回教师列表",response = Teacher.class)
    public ResponseJson findTeachersAndNoByCondition(
            @ApiParam(value = "personType属性必填,这里的personType是教师表的(1.教师,2.非教职工),属性不为空则作为条件查询")
            @RequestBody @Validated Teacher teacher) {
        if(teacher.getPersonType()==1){
            teacher.setSchoolId(mySchoolId());
            teacher.setStatus(Constant.STATUS.TEACHER_ON_LINE);
            List<Teacher> data = dormPersonService.findTeacherListByConditionOnDorm(teacher);
            long count = dormPersonService.findTeacherListCountByConditionOnDorm(teacher);
            return new ResponseJson(data,count);
        }

        if(teacher.getPersonType()==2){
            teacher.setSchoolId(mySchoolId());
            teacher.setStatus(Constant.STATUS.TEACHER_ON_LINE);
            List<Teacher> data = dormPersonService.findNoTeacherListByConditionOnDorm(teacher);
            long count = dormPersonService.findNoTeacherListCountByConditionOnDorm(teacher);
            return new ResponseJson(data,count);
        }

        return new ResponseJson(false,"缺少personType参数");

    }

    @PostMapping("/findDormPersonListByConditionConnect")
    @ApiOperation(value = "根据条件连表查询人员入住信息", notes = "返回人员入住信息列表",response = DormPerson.class)
    public ResponseJson findDormPersonListByConditionConnect(
            @ApiParam(value = "参数:宿舍id dormId,宿舍类别 dormCategory")
            @RequestBody
            @Validated
            DormBuildVo dormBuildVo) {
        if(dormBuildVo.getDormCategory()!=null){
            dormBuildVo.setSchoolId(mySchoolId());
            List<DormPerson> buildDormPersonList = dormPersonService.findDormPersonListByConditionConnect(dormBuildVo);
            DormPerson dormPerson = new DormPerson();
            dormPerson.setDormId(dormBuildVo.getDormId());
            dormBuildVo.setSchoolId(mySchoolId());
            long count = dormPersonService.findDormPersonCountByCondition(dormPerson);
            return new ResponseJson(buildDormPersonList,count);
        }else {
            return new ResponseJson();
        }

    }


    @PostMapping("/batchSaveDormPerson")
    @ApiOperation(value = "在宿舍中批量添加人员")
    public ResponseJson batchSaveDormPerson(
            @ApiParam(value = "对象的集合", required = true)
            @RequestBody List<DormPerson> dormPersonList){
        if(dormPersonList!=null&&dormPersonList.size()>0){
            Map<String, Object> msg = dormPersonService.batchSaveDormPerson(dormPersonList);
            return new ResponseJson(msg);
        }else {
            return new ResponseJson(false,"集合内容不能null");
        }
    }


    @PostMapping("/batchUpdate/batchFindDormPersonListByCondition")
    @ApiOperation(value = "批量查找需要修改的信息", notes = "返回响应对象", response=DormPerson.class)
    public ResponseJson batchFindDormPersonListByCondition(
            @ApiParam(value = "id,DormCategory必填", required = true)
            @RequestBody List<DormBuildVo> dormBuildVoList){
        List<DormPerson> list=dormPersonService.batchFindDormPersonListByCondition(dormBuildVoList);
        return new ResponseJson(list);
    }

    @PostMapping("/batchUpdate/batchUpdateDormPerson")
    @ApiOperation(value = "批量修改对象", notes = "返回响应对象")
    public ResponseJson batchUpdateDormPerson(
            @ApiParam(value = "被修改的对象集合,对象属性不为空则修改", required = true)
            @RequestBody DormPerson dormPerson){
        dormPersonService.batchUpdateDormPerson(dormPerson);
        return new ResponseJson();
    }

    @PostMapping("/setDormLeader")
    @ApiOperation(value = "设置宿舍长")
    public ResponseJson setDormLeader(
            @ApiParam(value = "参数:id,宿舍id dormId,人员id personId", required = true)
            @RequestBody DormPerson dormPerson){
        dormPersonService.setDormLeader(dormPerson);
        return new ResponseJson();
    }

    @GetMapping("/leaveDorm/{id}")
    @ApiOperation(value = "单个人员退宿")
    public ResponseJson leaveDormById(
            @ApiParam(value = "被退宿记录的id", required = true)
            @RequestBody DormPerson dormPerson){
        dormPersonService.leaveDormById(dormPerson);
        return new ResponseJson();
    }

    @PostMapping("/batchLeaveDorm")
    @ApiOperation(value = "批量人员退宿")
    public ResponseJson batchLeaveDorm(
            @ApiParam(value = "退宿人员信息的数组ids", required = true)
            @RequestBody DormPerson dormPerson){
        dormPersonService.batchLeaveDorm(dormPerson);
        return new ResponseJson();
    }

    @PostMapping("/adjust/getDormBuildBunkInfo")
    @ApiOperation(value = "调宿时查找楼栋列表信息")
    public ResponseJson getDormBuildBunkInfo(
            @ApiParam(value = "dormType,dormCategory必填", required = true)
            @RequestBody Dorm dorm){
        dorm.setSchoolId(mySchoolId());
        if (dorm.getDormCategory()!=null&&dorm.getDormType()!=null){
            //设置不仅仅查询空床位的宿舍
            dorm.setIsNullBunk(false);
            List<DormBuildingPersonInfo> list = dormPersonService.getDormBuildBunkInfo(dorm);
            return new ResponseJson(list);
        }else {
            return new ResponseJson();
        }
    }


    @GetMapping("/adjust/getDormBuildBunkInfoByDormId/{dormId}")
    @ApiOperation(value = "调宿时根据dormId查找所属楼栋信息")
    public ResponseJson getDormBuildBunkInfoByDormId(
            @ApiParam(value = "dormId必填", required = true)
            @PathVariable String dormId){
        DormBuildingPersonInfo dormBuildingPersonInfo = dormPersonService.getDormBuildBunkInfoByDormId(dormId);
        return new ResponseJson(dormBuildingPersonInfo);
    }

    @PostMapping("/adjust/DormByDormIdAndDormCategory")
    @ApiOperation(value = "调宿时根据宿舍id dormId和宿舍类别dormCategory查找宿舍床位信息与人员信息")
    public ResponseJson getDormByDormIdAndDormCategory(
            @ApiParam(value = "对象", required = true)
            @RequestBody DormBuildVo dormBuildVo){
        dormBuildVo.setSchoolId(mySchoolId());
        List<DormPerson> list = dormPersonService.getDormByDormIdAndDormCategory(dormBuildVo);
        return new ResponseJson(list);
    }

    @PostMapping("/adjust/adjustDormByCondition")
    @ApiOperation(value = "调宿")
    public ResponseJson adjustDormByCondition(
            @ApiParam(value = "对象", required = true)
            @RequestBody List<DormPerson> dormPersonList){
        dormPersonService.adjustDormByCondition(dormPersonList);
        return new ResponseJson();
    }


    @PostMapping("/info/findDormPersonInfoWithStudent")
    @ApiOperation(value = "查询学生住宿信息")
    public ResponseJson findDormPersonInfoWithStudent(
            @ApiParam(value = "对象,属性不为空时则查询", required = true)
            @Validated
            @RequestBody DormBuildingPersonInfo dormBuildingPersonInfo){
        List<Building> dormBuildingList= dormBuildAdminService.findDormBuildingByLogin();
        if (dormBuildingList!=null&&dormBuildingList.size()>0){
            List<String> dormBuildIdList = new ArrayList<>();
            dormBuildingList.forEach(d->{
                dormBuildIdList.add(d.getId());
            });
            dormBuildingPersonInfo.setDormBuildIdList(dormBuildIdList);
            dormBuildingPersonInfo.setSchoolId(mySchoolId());
            List<DormBuildingPersonInfo> list = dormPersonService.findDormPersonInfoWithStudent(dormBuildingPersonInfo);
            Long count = dormPersonService.findDormPersonInfoCountWithStudent(dormBuildingPersonInfo);
            return new ResponseJson(list,count);
        }else {
            return new ResponseJson();
        }

    }


    @PostMapping("/info/findDormPersonInfoWithTeacher")
    @ApiOperation(value = "查询教师及非教职工信息")
    public ResponseJson findDormPersonInfoWithTeacher(
            @ApiParam(value = "对象,属性不为空时则查询", required = true)
            @Validated
            @RequestBody DormBuildingPersonInfo dormBuildingPersonInfo){
        List<Building> dormBuildingList= dormBuildAdminService.findDormBuildingByLogin();
        if (dormBuildingList!=null&&dormBuildingList.size()>0){
            List<String> dormBuildIdList = new ArrayList<>();
            dormBuildingList.forEach(d->{
                dormBuildIdList.add(d.getId());
            });
            dormBuildingPersonInfo.setDormBuildIdList(dormBuildIdList);
            dormBuildingPersonInfo.setSchoolId(mySchoolId());
            List<DormBuildingPersonInfo> list = dormPersonService.findDormPersonInfoWithTeacher(dormBuildingPersonInfo);
            Long count = dormPersonService.findDormPersonInfoCountWithTeacher(dormBuildingPersonInfo);
            return new ResponseJson(list,count);
        }else {
            return new ResponseJson();
        }
    }

    @GetMapping("/info/findHeadmasterInfo/{classId}")
    @ApiOperation(value = "查询班主任信息")
    public ResponseJson findHeadmasterInfo(
            @ApiParam(value = "班级id", required = true)
            @PathVariable String classId){
        TeacherClasses teacherClasses = new TeacherClasses();
        teacherClasses.setClassesId(classId);
        Teacher headmaster = dormPersonService.findHeadmasterByClasses(teacherClasses);
        if (headmaster!=null&&headmaster.getName()!=null){
            return new ResponseJson(headmaster);
        }else {
            return new ResponseJson(true,"该学生暂未分配班主任");
        }

    }


    @GetMapping("/export/findEmptyDormByDormCategory/{dormCategory}")
    @ApiOperation(value = "导出宿舍人员导入模板")
    public void exportEmptyDormByDormCategory(
            @ApiParam(value = "传入宿舍类别 dormCategory", required = true)
            @PathVariable String dormCategory, HttpServletResponse response){
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=dorm.xls");
        try (ServletOutputStream s = response.getOutputStream()) {
            List<Building> dormBuilding = dormBuildAdminService.findDormBuildingByLogin();
            if (dormBuilding!=null&&dormBuilding.size()>0){
                DormBuildVo dormBuildVo =new DormBuildVo();
                dormBuildVo.setDormCategory(dormCategory);
                dormBuildVo.setSchoolId(mySchoolId());
                List<String> dormBuildIdList = dormBuilding.stream().map(Building::getId).collect(Collectors.toList());
                dormBuildVo.setDormBuildIdList(dormBuildIdList);
                Workbook workbook = dormPersonService.findEmptyDormByDormCategory(dormBuildVo);
                workbook.write(s);
            }
        } catch (Exception e) {

        }

    }


    @PostMapping("/import/importDormPerson")
    @ApiOperation(value = "导入宿舍住宿人员信息")
    public ResponseJson importDormPerson(
            MultipartFile file,
            @ApiParam(value = "传入宿舍类别 dormCategory", required = true)
            @RequestParam String dormCategory) {
        return new ResponseJson(dormPersonService.importDormPerson(file,dormCategory));
    }

    @PostMapping("/export/exportDormPersonInfoStudent")
    @ApiOperation(value = "导出学生住宿人员信息")
    public void exportDormPersonInfoStudent(
            @ApiParam(value = "宿舍床位人员入住id数组 Ids", required = true)
            @RequestBody String[] ids, HttpServletResponse response){
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=dormStudent.xls");
        try (ServletOutputStream s = response.getOutputStream()) {
            Workbook workbook = dormPersonService.exportDormPersonInfoStudent(ids);
            workbook.write(s);
        } catch (Exception e) {

        }

    }

    @PostMapping("/export/exportDormPersonInfoTeacher")
    @ApiOperation(value = "导出教师/非教职工住宿人员信息")
    public void exportDormPersonInfoTeacher(
            @ApiParam(value = "宿舍床位人员入住id数组 ids", required = true)
            @RequestBody  String[] ids, HttpServletResponse response){
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=dormTeacher.xls");
        try (ServletOutputStream s = response.getOutputStream()) {
            Workbook workbook = dormPersonService.exportDormPersonInfoTeacher(ids);
            workbook.write(s);
        } catch (Exception e) {

        }

    }


    @PostMapping("/export/exportErrorDormPersonInfo")
    @ApiOperation(value = "导出错误的信息集合")
    public void exportErrorDormPersonInfo(
            @ApiParam(value = "传入错误信息集合", required = true)
            @RequestBody List<DormImport> dormImport, HttpServletResponse response){
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=errorDormPerson.xls");
        try (ServletOutputStream s = response.getOutputStream()) {
            Workbook workbook = dormPersonService.exportErrorDormPersonInfo(dormImport);
            workbook.write(s);
        } catch (Exception e) {

        }

    }


    @PostMapping("/arrange/arrangeGetDormBuildBunkInfo")
    @ApiOperation(value = "分配宿舍时查找楼栋列表信息")
    public ResponseJson arrangeGetDormBuildBunkInfo(
            @ApiParam(value = "dormType,dormCategory必填", required = true)
            @RequestBody Dorm dorm){
        dorm.setSchoolId(mySchoolId());
        if (dorm.getDormCategory()!=null&&dorm.getDormType()!=null){
            //设置仅查询空床位的宿舍
            dorm.setIsNullBunk(true);
            List<DormBuildingPersonInfo> list = dormPersonService.getDormBuildBunkInfo(dorm);
            return new ResponseJson(list);
        }else {
            return new ResponseJson();
        }
    }

    @PostMapping("/arrange/findStudentDormInfo")
    @ApiOperation(value = "分配宿舍时查询学生是否有入住信息")
    public ResponseJson findStudentDormInfo(
            @ApiParam(value = "学生id的数组", required = true)
            @RequestBody String[] personIds){
        if (personIds.length>0){
            List<DormBuildingPersonInfo> list  = dormPersonService.findStudentDormInfo(personIds);
            return new ResponseJson(list);
        }else {
            return new ResponseJson();
        }
    }

    @PostMapping("/arrange/arrangeDorm")
    @ApiOperation(value = "分配宿舍")
    public ResponseJson arrangeDorm(
            @ApiParam(value = "dDormPerson的集合", required = true)
            @RequestBody List<DormPerson> dormPersonList){
        if (dormPersonList!=null&&dormPersonList.size()>0){
            dormPersonList.forEach(dormPerson -> {
                dormPerson.setSchoolId(mySchoolId());
            });
            Map<String, Object> map = dormPersonService.arrangeDorm(dormPersonList);
            return new ResponseJson(map);
        }else {
            return new ResponseJson(false,"数据不能为空");
        }

    }




}
