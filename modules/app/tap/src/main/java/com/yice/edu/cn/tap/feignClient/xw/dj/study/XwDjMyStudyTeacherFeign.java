package com.yice.edu.cn.tap.feignClient.xw.dj.study;

import com.yice.edu.cn.common.pojo.xw.dj.XwDjMyStudyTeacher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "xwDjMyStudyTeacherFeign",path = "/xwDjMyStudyTeacher")
public interface XwDjMyStudyTeacherFeign {
    @GetMapping("/findXwDjMyStudyTeacherById/{id}")
    XwDjMyStudyTeacher findXwDjMyStudyTeacherById(@PathVariable("id") String id);
    @PostMapping("/saveXwDjMyStudyTeacher")
    XwDjMyStudyTeacher saveXwDjMyStudyTeacher(XwDjMyStudyTeacher xwDjMyStudyTeacher);
    @PostMapping("/findXwDjMyStudyTeacherListByCondition")
    List<XwDjMyStudyTeacher> findXwDjMyStudyTeacherListByCondition(XwDjMyStudyTeacher xwDjMyStudyTeacher);
    @PostMapping("/findOneXwDjMyStudyTeacherByCondition")
    XwDjMyStudyTeacher findOneXwDjMyStudyTeacherByCondition(XwDjMyStudyTeacher xwDjMyStudyTeacher);
    @PostMapping("/findXwDjMyStudyTeacherCountByCondition")
    long findXwDjMyStudyTeacherCountByCondition(XwDjMyStudyTeacher xwDjMyStudyTeacher);
    @PostMapping("/updateXwDjMyStudyTeacher")
    void updateXwDjMyStudyTeacher(XwDjMyStudyTeacher xwDjMyStudyTeacher);
    @GetMapping("/deleteXwDjMyStudyTeacher/{id}")
    void deleteXwDjMyStudyTeacher(@PathVariable("id") String id);
    @PostMapping("/deleteXwDjMyStudyTeacherByCondition")
    void deleteXwDjMyStudyTeacherByCondition(XwDjMyStudyTeacher xwDjMyStudyTeacher);
}
