package com.yice.edu.cn.osp.service.jw.practice;

import com.yice.edu.cn.common.pojo.jw.practice.PracticeTeacher;
import com.yice.edu.cn.osp.feignClient.jw.practice.PracticeTeacherFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PracticeTeacherService {
    @Autowired
    private PracticeTeacherFeign practiceTeacherFeign;

    public PracticeTeacher findPracticeTeacherById(String id) {
        return practiceTeacherFeign.findPracticeTeacherById(id);
    }

    public PracticeTeacher savePracticeTeacher(PracticeTeacher practiceTeacher) {
        return practiceTeacherFeign.savePracticeTeacher(practiceTeacher);
    }

    public List<PracticeTeacher> findPracticeTeacherListByCondition(PracticeTeacher practiceTeacher) {
        return practiceTeacherFeign.findPracticeTeacherListByCondition(practiceTeacher);
    }

    public PracticeTeacher findOnePracticeTeacherByCondition(PracticeTeacher practiceTeacher) {
        return practiceTeacherFeign.findOnePracticeTeacherByCondition(practiceTeacher);
    }

    public long findPracticeTeacherCountByCondition(PracticeTeacher practiceTeacher) {
        return practiceTeacherFeign.findPracticeTeacherCountByCondition(practiceTeacher);
    }

    public void updatePracticeTeacher(PracticeTeacher practiceTeacher) {
        practiceTeacherFeign.updatePracticeTeacher(practiceTeacher);
    }

    public void deletePracticeTeacher(String id) {
        practiceTeacherFeign.deletePracticeTeacher(id);
    }

    public void deletePracticeTeacherByCondition(PracticeTeacher practiceTeacher) {
        practiceTeacherFeign.deletePracticeTeacherByCondition(practiceTeacher);
    }

    public List<PracticeTeacher> findPracticeTeacherListById(String id){
        return  practiceTeacherFeign.findPracticeTeacherListById(id);
    }
    public List<PracticeTeacher> findPracticeTeacherNameById(String id){
        return  practiceTeacherFeign.findPracticeTeacherNameById(id);
    }
}
