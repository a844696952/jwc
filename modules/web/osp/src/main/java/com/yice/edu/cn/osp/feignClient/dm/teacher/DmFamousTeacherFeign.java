package com.yice.edu.cn.osp.feignClient.dm.teacher;

import com.yice.edu.cn.common.pojo.dm.teacher.DmFamousTeacher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "dmFamousTeacherFeign",path = "/dmFamousTeacher")
public interface DmFamousTeacherFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findDmFamousTeacherById/{id}")
    DmFamousTeacher findDmFamousTeacherById(@PathVariable("id") String id);
    @PostMapping("/saveDmFamousTeacher")
    DmFamousTeacher saveDmFamousTeacher(DmFamousTeacher dmFamousTeacher);
    @PostMapping("/batchSaveDmFamousTeacher")
    void batchSaveDmFamousTeacher(List<DmFamousTeacher> dmFamousTeachers);
    @PostMapping("/findDmFamousTeacherListByCondition")
    List<DmFamousTeacher> findDmFamousTeacherListByCondition(DmFamousTeacher dmFamousTeacher);
    @PostMapping("/findOneDmFamousTeacherByCondition")
    DmFamousTeacher findOneDmFamousTeacherByCondition(DmFamousTeacher dmFamousTeacher);
    @PostMapping("/findDmFamousTeacherCountByCondition")
    long findDmFamousTeacherCountByCondition(DmFamousTeacher dmFamousTeacher);
    @PostMapping("/updateDmFamousTeacher")
    void updateDmFamousTeacher(DmFamousTeacher dmFamousTeacher);
    @PostMapping("/updateDmFamousTeacherForAll")
    void updateDmFamousTeacherForAll(DmFamousTeacher dmFamousTeacher);
    @GetMapping("/deleteDmFamousTeacher/{id}")
    void deleteDmFamousTeacher(@PathVariable("id") String id);
    @PostMapping("/deleteDmFamousTeacherByCondition")
    void deleteDmFamousTeacherByCondition(DmFamousTeacher dmFamousTeacher);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
