package com.yice.edu.cn.bmp.service.exam;

import com.yice.edu.cn.bmp.feignClient.exam.SchoolExamFeign;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolExamService {
    @Autowired
    private SchoolExamFeign schoolExamFeign;

    /**
     * 学情分析特殊筛选条件使用
     * @param schoolExam
     * @return
     */
    public List<SchoolExam> findSchoolExamListByCondition2(SchoolExam schoolExam) {
        return schoolExamFeign.findSchoolExamListByCondition2(schoolExam);
    }

    /**
     * 学情分析特殊筛选条件使用
     * @param schoolExam
     * @return
     */
    public long findSchoolExamCountByCondition2(SchoolExam schoolExam) {
        return schoolExamFeign.findSchoolExamCountByCondition2(schoolExam);
    }
}
