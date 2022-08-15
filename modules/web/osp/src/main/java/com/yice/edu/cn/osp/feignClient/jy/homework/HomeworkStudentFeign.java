package com.yice.edu.cn.osp.feignClient.jy.homework;

import com.yice.edu.cn.common.pojo.jy.homework.HomeworkStudent;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "homeworkStudentFeign",path = "/homeworkStudent")
public interface HomeworkStudentFeign {
    @GetMapping("/findHomeworkStudentById/{id}")
    HomeworkStudent findHomeworkStudentById(@PathVariable("id") String id);
    @PostMapping("/saveHomeworkStudent")
    HomeworkStudent saveHomeworkStudent(HomeworkStudent homeworkStudent);
    @PostMapping("/findHomeworkStudentListByCondition")
    List<HomeworkStudent> findHomeworkStudentListByCondition(HomeworkStudent homeworkStudent);
    @PostMapping("/findOneHomeworkStudentByCondition")
    HomeworkStudent findOneHomeworkStudentByCondition(HomeworkStudent homeworkStudent);
    @PostMapping("/findHomeworkStudentCountByCondition")
    long findHomeworkStudentCountByCondition(HomeworkStudent homeworkStudent);
    @PostMapping("/updateHomeworkStudent")
    void updateHomeworkStudent(HomeworkStudent homeworkStudent);
    @GetMapping("/deleteHomeworkStudent/{id}")
    void deleteHomeworkStudent(@PathVariable("id") String id);
    @PostMapping("/deleteHomeworkStudentByCondition")
    void deleteHomeworkStudentByCondition(HomeworkStudent homeworkStudent);
    @PostMapping("/findHasCompleteHomeworkStuListByCondition")
    public List<HomeworkStudent> findHasCompleteHomeworkStuListByCondition(
            @RequestBody HomeworkStudent homeworkStudent);
    @PostMapping("/findHasCompleteHomeworkStuCountByCondition")
    public long findHasCompleteHomeworkStuCountByCondition(
            @RequestBody HomeworkStudent homeworkStudent);
    @PostMapping("/remakrStuHomework")
    public void remakrStuHomework(HomeworkStudent homeworkStudent);

    @GetMapping("/delRemarkNoteByHomeworkStudentId/{id}")  //删除单个学生作业的老师评价
    public void delRemarkNoteByHomeworkStudentId(@PathVariable("id") String id);
}
