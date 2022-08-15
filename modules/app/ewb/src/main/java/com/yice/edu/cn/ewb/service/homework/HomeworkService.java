package com.yice.edu.cn.ewb.service.homework;

import com.yice.edu.cn.common.pojo.jy.homework.Homework;
import com.yice.edu.cn.ewb.feignClient.homework.HomeworkFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeworkService {
    @Autowired
    private HomeworkFeign homeworkFeign;

    public Homework findHomeworkById(String id) {
        return homeworkFeign.findHomeworkById(id);
    }
    public List<Homework> findHomeworkListByConditionEwb(Homework homework) {
        return homeworkFeign.findHomeworkListByConditionEwb(homework);
    }
}
