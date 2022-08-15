package com.yice.edu.cn.ewb.feignClient.homework;

import com.yice.edu.cn.common.pojo.jy.homework.Homework;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
@FeignClient(value="jy",path = "/homework")
public interface HomeworkFeign {

    @GetMapping("/findHomeworkById/{id}")
    Homework findHomeworkById(@PathVariable("id") String id);

    @PostMapping("/findHomeworkListByConditionEwb")
    List<Homework> findHomeworkListByConditionEwb(Homework homework);
}
