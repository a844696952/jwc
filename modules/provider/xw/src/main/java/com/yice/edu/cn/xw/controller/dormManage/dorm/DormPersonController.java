package com.yice.edu.cn.xw.controller.dormManage.dorm;

import com.yice.edu.cn.common.pojo.jw.building.Building;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildVo;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildingPersonInfo;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormPerson;
import com.yice.edu.cn.xw.service.dormManage.dorm.DormPersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dormPerson")
@Api(value = "/dormPerson",description = "模块")
public class DormPersonController {
    @Autowired
    private DormPersonService dormPersonService;

    @GetMapping("/findDormPersonById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public DormPerson findDormPersonById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dormPersonService.findDormPersonById(id);
    }

    @PostMapping("/saveDormPerson")
    @ApiOperation(value = "保存", notes = "返回对象")
    public DormPerson saveDormPerson(
            @ApiParam(value = "对象", required = true)
            @RequestBody DormPerson dormPerson){
        dormPersonService.saveDormPerson(dormPerson);
        return dormPerson;
    }

    @PostMapping("/findDormPersonListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<DormPerson> findDormPersonListByCondition(
            @ApiParam(value = "对象")
            @RequestBody DormPerson dormPerson){
        return dormPersonService.findDormPersonListByCondition(dormPerson);
    }
    @PostMapping("/findDormPersonCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findDormPersonCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody DormPerson dormPerson){
        return dormPersonService.findDormPersonCountByCondition(dormPerson);
    }

    @PostMapping("/updateDormPerson")
    @ApiOperation(value = "修改", notes = "对象必传")
    public long updateDormPerson(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody DormPerson dormPerson){
       return dormPersonService.updateDormPerson(dormPerson);
    }

    @GetMapping("/deleteDormPerson/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteDormPerson(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        dormPersonService.deleteDormPerson(id);
    }
    @PostMapping("/deleteDormPersonByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteDormPersonByCondition(
            @ApiParam(value = "对象")
            @RequestBody DormPerson dormPerson){
        dormPersonService.deleteDormPersonByCondition(dormPerson);
    }
    @PostMapping("/findOneDormPersonByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public DormPerson findOneDormPersonByCondition(
            @ApiParam(value = "对象")
            @RequestBody DormPerson dormPerson){
        return dormPersonService.findOneDormPersonByCondition(dormPerson);
    }

    /*---------------------------------------------------------------------------------------------------------*/
    @PostMapping("/findStudentListByConditionOnDorm")
    List<Student> findStudentListByConditionOnDorm(
            @ApiParam(value = "集合对象")
            @RequestBody Student student){
        return dormPersonService.findStudentListByConditionOnDorm(student);
    }

    @PostMapping("/findStudentListCountByConditionOnDorm")
    Long findStudentListCountByConditionOnDorm(
            @ApiParam(value = "集合对象")
            @RequestBody Student student){
        return dormPersonService.findStudentListCountByConditionOnDorm(student);
    }

    @PostMapping("/findTeacherListByConditionOnDorm")
    List<Teacher> findTeacherListByConditionOnDorm(
            @ApiParam(value = "集合对象")
            @RequestBody Teacher teacher){
        return dormPersonService.findTeacherListByConditionOnDorm(teacher);
    }

    @PostMapping("/findTeacherListCountByConditionOnDorm")
    Long findTeacherListCountByConditionOnDorm(
            @ApiParam(value = "集合对象")
            @RequestBody Teacher teacher){
        return dormPersonService.findTeacherListCountByConditionOnDorm(teacher);
    }


    @PostMapping("/findNoTeacherListByConditionOnDorm")
    List<Teacher> findNoTeacherListByConditionOnDorm(
            @ApiParam(value = "集合对象")
            @RequestBody Teacher teacher){
        return dormPersonService.findNoTeacherListByConditionOnDorm(teacher);
    }

    @PostMapping("/findNoTeacherListCountByConditionOnDorm")
    Long findNoTeacherListCountByConditionOnDorm(
            @ApiParam(value = "集合对象")
            @RequestBody Teacher teacher){
        return dormPersonService.findNoTeacherListCountByConditionOnDorm(teacher);
    }

    @PostMapping("/findDormPersonListConnectTeacher")
    List<DormPerson> findDormPersonListConnectTeacher(
            @ApiParam(value = "集合对象")
            @RequestBody DormBuildVo dormBuildVo){
        return dormPersonService.findDormPersonListConnectTeacher(dormBuildVo);

    }
    @PostMapping("/findDormPersonListConnectStudent")
    List<DormPerson> findDormPersonListConnectStudent(
            @ApiParam(value = "集合对象")
            @RequestBody DormBuildVo dormBuildVo){
        return dormPersonService.findDormPersonListConnectStudent(dormBuildVo);

    }
    @GetMapping("/getDormBuildingById/{id}")
    public DormBuildingPersonInfo getDormBuildingById(
            @ApiParam(value = "宿舍id", required = true)
            @PathVariable String id){
        return dormPersonService.getDormBuildingById(id);
    }
    @PostMapping("/findDormPersonOneConnectTeacher")
    public DormPerson findDormPersonOneConnectTeacher(
            @ApiParam(value = "集合对象")
            @RequestBody DormBuildVo dormBuildVo){
        return dormPersonService.findDormPersonOneConnectTeacher(dormBuildVo);

    }
    @PostMapping("/findDormPersonOneConnectStudent")
    public DormPerson findDormPersonOneConnectStudent(
            @ApiParam(value = "对象")
            @RequestBody DormBuildVo dormBuildVo){
        return dormPersonService.findDormPersonOneConnectStudent(dormBuildVo);

    }
    @PostMapping("/leaveDorm")
    public void leaveDorm(
            @ApiParam(value = "对象")
            @RequestBody DormPerson dormPerson){
      dormPersonService.leaveDorm(dormPerson);
    }

    @PostMapping("/findDormPersonInfoWithStudent")
    public List<DormBuildingPersonInfo> findDormPersonInfoWithStudent(
            @ApiParam(value = "对象")
            @RequestBody DormBuildingPersonInfo dormBuildingPersonInfo){
        return dormPersonService.findDormPersonInfoWithStudent(dormBuildingPersonInfo);
    }
    @PostMapping("/findDormPersonInfoCountWithStudent")
    public long findDormPersonInfoCountWithStudent(
            @ApiParam(value = "对象")
            @RequestBody DormBuildingPersonInfo dormBuildingPersonInfo){
        return dormPersonService.findDormPersonInfoCountWithStudent(dormBuildingPersonInfo);
    }
    @PostMapping("/findDormPersonInfoWithTeacher")
    public List<DormBuildingPersonInfo> findDormPersonInfoWithTeacher(
            @ApiParam(value = "对象")
            @RequestBody DormBuildingPersonInfo dormBuildingPersonInfo){
        return dormPersonService.findDormPersonInfoWithTeacher(dormBuildingPersonInfo);
    }
    @PostMapping("/findDormPersonInfoCountWithTeacher")
    public long findDormPersonInfoCountWithTeacher(
            @ApiParam(value = "对象")
            @RequestBody DormBuildingPersonInfo dormBuildingPersonInfo){
        return dormPersonService.findDormPersonInfoCountWithTeacher(dormBuildingPersonInfo);
    }
    @PostMapping("/findEmptyDormByDormCategory")
    public List<Map<String, String>> findEmptyDormByDormCategory(
            @ApiParam(value = "对象")
            @RequestBody DormBuildVo dormBuildVo){
        return dormPersonService.findEmptyDormByDormCategory(dormBuildVo);
    }

    @PostMapping("/updateSaveDormPerson")
    public long updateSaveDormPerson(
            @ApiParam(value = "对象")
            @RequestBody DormPerson dormPerson){
        return dormPersonService.updateSaveDormPerson(dormPerson);
    }

    @PostMapping("/leaveDormByClassesId")
    public void leaveDormByClassesId(
            @ApiParam(value = "班级id")
            @PathVariable String classesId){
        dormPersonService.leaveDormByClassesId(classesId);
    }


    @PostMapping("/findDormMoveIntoPersonNumByCondition")
    public long findDormMoveIntoPersonNumByCondition(
            @ApiParam(value = "对象")
            @RequestBody DormBuildVo dormBuildVo){
        return dormPersonService.findDormMoveIntoPersonNumByCondition(dormBuildVo);
    }


    @PostMapping("/adjustDormByCondition")
    public void adjustDormByCondition(
            @ApiParam(value = "对象")
            @RequestBody List<DormPerson> dormPersonList){
       dormPersonService.adjustDormByCondition(dormPersonList);
    }

    @PostMapping("/arrangeDorm")
    Map<String,Object> arrangeDorm(
            @ApiParam(value = "对象")
            @RequestBody List<DormPerson> dormPersonList){
        return dormPersonService.arrangeDorm(dormPersonList);
    }

    @PostMapping("/updateDormCapacityByDormId")
    public void updateDormCapacityByDormId(
            @ApiParam(value = "对象")
            @RequestBody Building building){
        dormPersonService.updateDormCapacityByDormId(building);
    }

    @PostMapping("/deleteDormAndDormPerson")
    public void deleteDormAndDormPerson(
            @ApiParam(value = "宿舍id")
            @RequestBody List<String> dormIdList) {
        dormPersonService.deleteDormAndDormPerson(dormIdList);
    }
}
