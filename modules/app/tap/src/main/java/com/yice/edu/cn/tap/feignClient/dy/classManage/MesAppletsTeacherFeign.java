package com.yice.edu.cn.tap.feignClient.dy.classManage;

import com.yice.edu.cn.common.pojo.mes.classManage.mesAppletsTeacher.MesAppletsTeacher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dy",path = "/mesAppletsTeacher")
public interface MesAppletsTeacherFeign {
    @GetMapping("/findMesAppletsTeacherById/{id}")
    MesAppletsTeacher findMesAppletsTeacherById(@PathVariable("id") String id);
    @PostMapping("/saveMesAppletsTeacher")
    MesAppletsTeacher saveMesAppletsTeacher(MesAppletsTeacher mesAppletsTeacher);
    @PostMapping("/findMesAppletsTeacherListByCondition")
    List<MesAppletsTeacher> findMesAppletsTeacherListByCondition(MesAppletsTeacher mesAppletsTeacher);
    @PostMapping("/findOneMesAppletsTeacherByCondition")
    MesAppletsTeacher findOneMesAppletsTeacherByCondition(MesAppletsTeacher mesAppletsTeacher);
    @PostMapping("/findMesAppletsTeacherCountByCondition")
    long findMesAppletsTeacherCountByCondition(MesAppletsTeacher mesAppletsTeacher);
    @PostMapping("/updateMesAppletsTeacher")
    void updateMesAppletsTeacher(MesAppletsTeacher mesAppletsTeacher);
    @GetMapping("/deleteMesAppletsTeacher/{id}")
    void deleteMesAppletsTeacher(@PathVariable("id") String id);
    @PostMapping("/deleteMesAppletsTeacherByCondition")
    void deleteMesAppletsTeacherByCondition(MesAppletsTeacher mesAppletsTeacher);
}
