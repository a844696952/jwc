package com.yice.edu.cn.osp.service.jy.homework;

import com.yice.edu.cn.common.pojo.jy.homework.HomeworkStudent;
import com.yice.edu.cn.osp.feignClient.jy.homework.HomeworkStudentFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

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
    
    public List<HomeworkStudent> findHasCompleteHomeworkStuListByCondition(HomeworkStudent homeworkStudent){
    	return homeworkStudentFeign.findHasCompleteHomeworkStuListByCondition(homeworkStudent);
    }
    public long findHasCompleteHomeworkStuCountByCondition(HomeworkStudent homeworkStudent) {
    	return homeworkStudentFeign.findHasCompleteHomeworkStuCountByCondition(homeworkStudent);
    }
    public void remakrStuHomework(HomeworkStudent homeworkStudent) {
        homeworkStudentFeign.remakrStuHomework(homeworkStudent);
    }
    public void delRemarkNoteByHomeworkStudentId(String id) {
        homeworkStudentFeign.delRemarkNoteByHomeworkStudentId(id);
    }
}
