package com.yice.edu.cn.jw.feign.homework;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkStudent;
import com.yice.edu.cn.common.pojo.jy.homework.app.HomeworkVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @PostMapping("/findTodayHomeworkByStudent")
    List<HomeworkStudent> findTodayHomeworkByStudent(HomeworkStudent homeworkStudent);
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
    @PostMapping("/studentSubmitHomework")
    ResponseJson studentSubmitHomework(HomeworkVo homeworkVo);
    @PostMapping("/findHomeworkStudents4Bmp")
    List<HomeworkStudent> findHomeworkStudents4Bmp(HomeworkStudent homeworkStudent);
}
