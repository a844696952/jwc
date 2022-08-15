package com.yice.edu.cn.ewb.service.exam;

import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.ewb.feignClient.exam.SchoolExamFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SchoolExamService {
    @Autowired
    private SchoolExamFeign schoolExamFeign;

    public SchoolExam findSchoolExamById(String id) {
        return schoolExamFeign.findSchoolExamById(id);
    }
    /**
     * 学情分析特殊筛选条件使用
     * @param schoolExam
     * @return
     */
    public List<SchoolExam> findSchoolExamListByCondition2(SchoolExam schoolExam) {
        List<SchoolExam> list = schoolExamFeign.findSchoolExamListByCondition2(schoolExam);
        if (list==null){
            return new ArrayList<>();
        }
        return list;
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
