package com.yice.edu.cn.ecc.service.modeManage;

import com.yice.edu.cn.common.pojo.dm.modeManage.ExamMode;
import com.yice.edu.cn.ecc.feignClient.modeManage.ExamModeFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamModeService {

    @Autowired
    private ExamModeFeign examModeFeign;


    public List<ExamMode> findByCExamModeByTime(ExamMode exam) {
        return examModeFeign.findByCExamModeByTime(exam);
    }
}
