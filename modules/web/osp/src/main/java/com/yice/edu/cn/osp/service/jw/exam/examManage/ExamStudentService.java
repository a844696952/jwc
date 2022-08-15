package com.yice.edu.cn.osp.service.jw.exam.examManage;

import com.yice.edu.cn.common.pojo.jw.exam.examManage.ExamStudent;
import com.yice.edu.cn.osp.feignClient.jw.exam.examManage.ExamStudentFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamStudentService {
    @Autowired
    private ExamStudentFeign examStudentFeign;

    public ExamStudent findExamStudentById(String id) {
        return examStudentFeign.findExamStudentById(id);
    }

    public ExamStudent saveExamStudent(ExamStudent examStudent) {
        return examStudentFeign.saveExamStudent(examStudent);
    }

    public List<ExamStudent> findExamStudentListByCondition(ExamStudent examStudent) {
        return examStudentFeign.findExamStudentListByCondition(examStudent);
    }

    public ExamStudent findOneExamStudentByCondition(ExamStudent examStudent) {
        return examStudentFeign.findOneExamStudentByCondition(examStudent);
    }

    public long findExamStudentCountByCondition(ExamStudent examStudent) {
        return examStudentFeign.findExamStudentCountByCondition(examStudent);
    }

    public void updateExamStudent(ExamStudent examStudent) {
        examStudentFeign.updateExamStudent(examStudent);
    }

    public void deleteExamStudent(String id) {
        examStudentFeign.deleteExamStudent(id);
    }

    public void deleteExamStudentByCondition(ExamStudent examStudent) {
        examStudentFeign.deleteExamStudentByCondition(examStudent);
    }
}
