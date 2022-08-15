package com.yice.edu.cn.tap.feignClient.teacher;

import com.yice.edu.cn.common.pojo.xw.teacherattendance.XwTeacherImageInput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "xw",contextId = "xwTeacherImageInputFeign",path = "/xwTeacherImageInput")
public interface XwTeacherImageInputFeign {
    @GetMapping("/findXwTeacherImageInputById/{id}")
    XwTeacherImageInput findXwTeacherImageInputById(@PathVariable("id") String id);

    @PostMapping("/saveXwTeacherImageInput")
    XwTeacherImageInput saveXwTeacherImageInput(XwTeacherImageInput xwTeacherImageInput);

    @PostMapping("/findXwTeacherImageInputListByCondition")
    List<XwTeacherImageInput> findXwTeacherImageInputListByCondition(XwTeacherImageInput xwTeacherImageInput);

    @PostMapping("/findOneXwTeacherImageInputByCondition")
    XwTeacherImageInput findOneXwTeacherImageInputByCondition(XwTeacherImageInput xwTeacherImageInput);

    @PostMapping("/findXwTeacherImageInputCountByCondition")
    long findXwTeacherImageInputCountByCondition(XwTeacherImageInput xwTeacherImageInput);

    @PostMapping("/updateXwTeacherImageInput")
    void updateXwTeacherImageInput(XwTeacherImageInput xwTeacherImageInput);

    @GetMapping("/deleteXwTeacherImageInput/{id}")
    void deleteXwTeacherImageInput(@PathVariable("id") String id);

    @PostMapping("/deleteXwTeacherImageInputByCondition")
    void deleteXwTeacherImageInputByCondition(XwTeacherImageInput xwTeacherImageInput);

    @GetMapping("/findXwTeacherImageInputListAll/{schoolId}")
    List<XwTeacherImageInput> findXwTeacherImageInputListAll(@PathVariable("schoolId") String schoolId);

    @GetMapping("/findXwTeacherImageInputListAllCount/{schoolId}")
    long findXwTeacherImageInputListAllCount(@PathVariable("schoolId") String schoolId);

    @GetMapping("/findXwTeacherImageInputByTeacherName/{teacherName}/{schoolId}")
    List<XwTeacherImageInput> findXwTeacherImageInputByTeacherName(@PathVariable("teacherName") String teacherName
            , @PathVariable("schoolId") String schoolId);

    @GetMapping("/findXwTeacherImageInputByTeacherNameCount/{teacherName}/{schoolId}")
    long findXwTeacherImageInputByTeacherNameCount(@PathVariable("teacherName") String teacherName
            , @PathVariable("schoolId") String schoolId);

    @GetMapping("/findXwTeacherImageInputByTeacherId/{teacherId}/{schoolId}")
    List<XwTeacherImageInput> findXwTeacherImageInputByTeacherId(
            @PathVariable("teacherId") String teacherId,
            @PathVariable("schoolId") String schoolId);
}
