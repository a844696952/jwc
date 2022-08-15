package com.yice.edu.cn.tap.service.homework;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yice.edu.cn.common.pojo.jy.homework.HomeworkCountQueryVo;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkCountViewVo;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkStudent;
import com.yice.edu.cn.tap.feignClient.homework.HomeworkStudentFeign;

@Service
public class HomeworkStudentService {
    @Autowired
    private HomeworkStudentFeign homeworkStudentFeign;

    public HomeworkStudent findHomeworkStudentById(String id) {
        return homeworkStudentFeign.findHomeworkStudentById(id);
    }

    public HomeworkStudent saveHomeworkStudent(HomeworkStudent homeworkStudent) {
        return homeworkStudentFeign.saveHomeworkStudent(homeworkStudent);
    }

    public List<HomeworkStudent> findHomeworkStudentListByCondition(HomeworkStudent homeworkStudent) {
        return homeworkStudentFeign.findHomeworkStudentListByCondition(homeworkStudent);
    }

    public HomeworkStudent findOneHomeworkStudentByCondition(HomeworkStudent homeworkStudent) {
        return homeworkStudentFeign.findOneHomeworkStudentByCondition(homeworkStudent);
    }

    public long findHomeworkStudentCountByCondition(HomeworkStudent homeworkStudent) {
        return homeworkStudentFeign.findHomeworkStudentCountByCondition(homeworkStudent);
    }

    public void updateHomeworkStudent(HomeworkStudent homeworkStudent) {
        homeworkStudentFeign.updateHomeworkStudent(homeworkStudent);
    }

    public void deleteHomeworkStudent(String id) {
        homeworkStudentFeign.deleteHomeworkStudent(id);
    }

    public void deleteHomeworkStudentByCondition(HomeworkStudent homeworkStudent) {
        homeworkStudentFeign.deleteHomeworkStudentByCondition(homeworkStudent);
    }
    public void delRemarkNoteByHomeworkStudentId(String id) {
        homeworkStudentFeign.delRemarkNoteByHomeworkStudentId(id);
    }
    public void remakrStuHomework(HomeworkStudent homeworkStudent) {
        homeworkStudentFeign.remakrStuHomework(homeworkStudent);
    }
    public List<HomeworkCountViewVo> findSchoolHomeworkNumByDateAndStatus(
            HomeworkCountQueryVo vo){
       return homeworkStudentFeign.findSchoolHomeworkNumByDateAndStatus(vo);
    }
}
