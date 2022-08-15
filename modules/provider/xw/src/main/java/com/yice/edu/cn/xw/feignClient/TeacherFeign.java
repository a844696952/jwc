package com.yice.edu.cn.xw.feignClient;

import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.common.pojo.jw.auth.TeacherRole;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient(value="jw",contextId = "teacherFeign",path = "/teacher")
public interface TeacherFeign {
    @GetMapping("/findTeacherById/{id}")
    Teacher findTeacherById(@PathVariable("id") String id);
    @PostMapping("/saveTeacher")
    Teacher saveTeacher(Teacher teacher);
    @PostMapping("/findTeacherListByCondition")
    List<Teacher> findTeacherListByCondition(Teacher teacher);
    @PostMapping("/findTeacherListByCondition4Like")
    List<Teacher> findTeacherListByCondition4Like(Teacher teacher);
    @PostMapping("/findTeacherCountByCondition")
    long findTeacherCountByCondition(Teacher teacher);
    @PostMapping("/findTeacherCountByCondition4Like")
    long findTeacherCountByCondition4Like(Teacher teacher);
    @PostMapping("/updateTeacher")
    Teacher updateTeacher(Teacher teacher);
    @PostMapping("/updateTeacherNew")
    Teacher updateTeacherNew(Teacher teacher);
    @GetMapping("/deleteTeacher/{id}")
    void deleteTeacher(@PathVariable("id") String id);
    @PostMapping("/deleteTeacherByCondition")
    void deleteTeacherByCondition(Teacher teacher);
    @GetMapping("/findTeacherTreeMenu/{id}")
    List<Perm> findTeacherTreeMenu(@PathVariable("id") String id);
    @GetMapping("/findCheckedRolesByTeacherId/{id}")
    List<String> findCheckedRolesByTeacherId(@PathVariable("id") String id);
    @PostMapping("/delsertTeacherRoles")
    void delsertTeacherRoles(TeacherRole teacherRole);
    @GetMapping("/findTeacherFuncPermsByTeacherId/{id}")
    List<Perm> findTeacherFuncPermsByTeacherId(@PathVariable("id") String id);
    @PostMapping("/batchSaveTeacher")
    Map<String,Object> batchSaveTeacher(List<Teacher> teacherList);
    @PostMapping("/updateTeacherAdmin")
    void updateTeacherAdmin(Teacher teacher);

    @PostMapping("/findCorrespondencesByTeacher")
    List<Teacher> findCorrespondencesByTeacher(Teacher teacher);
    @PostMapping("/findTeacherListInfoByCondition")
    List<Teacher> findTeacherListInfoByCondition(Teacher teacher);
    @PostMapping("/batchUpdateTeacherRegisterStatus")
    int batchUpdateTeacherRegisterStatus(Teacher teacher);
    @PostMapping("/findTeacherListSchoolId")
    List<Teacher> findTeacherListSchoolId(Teacher teacher);
    @GetMapping("/findTeacherAdminTreeMenu/{schoolId}")
    List<Perm> findTeacherAdminTreeMenu(@PathVariable("schoolId") String schoolId);
    @PostMapping("/findTeacherImgListByCondition")
    List<Teacher> findTeacherImgListByCondition(Teacher teacher );
    @PostMapping("/findTeacherImgListByConditionWithoutType")
    List<Teacher> findTeacherImgListByConditionWithoutType(Teacher teacher );
}
