package com.yice.edu.cn.tap.feignClient.homework;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.yice.edu.cn.common.pojo.jy.homework.HomeworkCountQueryVo;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkCountViewVo;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkStudent;

@FeignClient(value="jy",path = "/homeworkStudent")
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
    @GetMapping("/delRemarkNoteByHomeworkStudentId/{id}")
    public void delRemarkNoteByHomeworkStudentId(@PathVariable("id") String id);
    @PostMapping("/remakrStuHomework")
    public void remakrStuHomework(HomeworkStudent homeworkStudent);
    @PostMapping("/findSchoolHomeworkNumByDateAndStatus")
    public List<HomeworkCountViewVo> findSchoolHomeworkNumByDateAndStatus(
            HomeworkCountQueryVo vo);
}
