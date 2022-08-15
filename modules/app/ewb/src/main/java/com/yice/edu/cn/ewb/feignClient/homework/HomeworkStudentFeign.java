package com.yice.edu.cn.ewb.feignClient.homework;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkStudent;
import com.yice.edu.cn.common.pojo.jy.homework.app.HomeworkVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",path = "/homeworkStudent")
public interface HomeworkStudentFeign {
    @PostMapping("/findHomeworkStudents4Bmp")
    List<HomeworkStudent> findHomeworkStudents4Bmp(HomeworkStudent homeworkStudent);
    @GetMapping("/findHomeworkStudentById/{id}")
    HomeworkStudent findHomeworkStudentById(@PathVariable("id") String id);
    @PostMapping("/studentSubmitHomework")
    ResponseJson studentSubmitHomework(HomeworkVo homeworkVo);
    @PostMapping("/findOneHomeworkStudentByCondition")
    HomeworkStudent findOneHomeworkStudentByCondition(HomeworkStudent homeworkStudent);
    @PostMapping("/findHomeworkStudentListByCondition")
    List<HomeworkStudent> findHomeworkStudentListByCondition(HomeworkStudent homeworkStudent);
    @PostMapping("/findHomeworkStudentCountByCondition")
    long findHomeworkStudentCountByCondition(HomeworkStudent homeworkStudent);
}
