package com.yice.edu.cn.tap.feignClient.homework;

import com.yice.edu.cn.common.pojo.jy.homework.Homework;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",path = "/homework")
public interface HomeworkFeign {
    @GetMapping("/findHomeworkById/{id}")
    Homework findHomeworkById(@PathVariable("id") String id);
    @PostMapping("/saveHomework")
    Homework saveHomework(Homework homework);
    @PostMapping("/findHomeworkListByCondition")
    List<Homework> findHomeworkListByCondition(Homework homework);
    @PostMapping("/findOneHomeworkByCondition")
    Homework findOneHomeworkByCondition(Homework homework);
    @PostMapping("/findHomeworkCountByCondition")
    long findHomeworkCountByCondition(Homework homework);
    @PostMapping("/updateHomework")
    void updateHomework(Homework homework);
    @PostMapping("/updateHomeworkNew")
    void updateHomeworkNew(Homework homework);
    @GetMapping("/deleteHomework/{id}")
    void deleteHomework(@PathVariable("id") String id);
    @PostMapping("/deleteHomeworkByCondition")
    void deleteHomeworkByCondition(Homework homework);
    @PostMapping("/publishHomework")
    Homework publishHomework(Homework homework);
    @GetMapping("/deleteHomeworkNew/{id}")
    void deleteHomeworkNew(@PathVariable("id") String id);
    @PostMapping("/findHomeworks4Index")
    List<Homework> findHomeworks4Index(Homework homework);
}
