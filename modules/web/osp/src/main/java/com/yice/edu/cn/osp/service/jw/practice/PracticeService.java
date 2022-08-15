package com.yice.edu.cn.osp.service.jw.practice;

import com.yice.edu.cn.common.pojo.jw.practice.Practice;
import com.yice.edu.cn.osp.feignClient.jw.practice.PracticeFeign;
import com.yice.edu.cn.osp.feignClient.jw.teacher.TeacherFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PracticeService {
    @Autowired
    private PracticeFeign practiceFeign;

    public Practice findPracticeById(String id) {
        return practiceFeign.findPracticeById(id);
    }

    public Practice savePractice(Practice practice) {
        return practiceFeign.savePractice(practice);
    }

    public List<Practice> findPracticeListByCondition(Practice practice) {
        return practiceFeign.findPracticeListByCondition(practice);
    }

    public List<Practice> findPracticeListByCondition1(Practice practice) {
        return practiceFeign.findPracticeListByCondition1(practice);
    }

    public List<Practice> findPracticeListByTeacherId(Practice practice){
        return  practiceFeign.findPracticeListByTeacherId(practice);
    }

    public Practice findOnePracticeByCondition(Practice practice) {
        return practiceFeign.findOnePracticeByCondition(practice);
    }

    public long findPracticeCountByCondition(Practice practice) {
        return practiceFeign.findPracticeCountByCondition(practice);
    }

    public long findPracticeCountByTeacherId(Practice practice) {
        return practiceFeign.findPracticeCountByTeacherId(practice);
    }

    public long findPracticeCountByCondition1(Practice practice) {
        return practiceFeign.findPracticeCountByCondition1(practice);
    }

    public void updatePractice(Practice practice) {
        practiceFeign.updatePractice(practice);
    }

    public void deletePractice(String id) {
        practiceFeign.deletePractice(id);
    }

    public void deletePracticeByCondition(Practice practice) {
        practiceFeign.deletePracticeByCondition(practice);
    }
}
