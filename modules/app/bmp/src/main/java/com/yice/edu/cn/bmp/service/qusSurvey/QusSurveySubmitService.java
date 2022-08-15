package com.yice.edu.cn.bmp.service.qusSurvey;

import com.yice.edu.cn.bmp.feignClient.qusSurvey.QusSurveySubmitFeign;
import com.yice.edu.cn.common.pojo.jw.qusSurvey.QusSurveySubmit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QusSurveySubmitService {
    @Autowired
    private QusSurveySubmitFeign qusSurveySubmitFeign;

    public QusSurveySubmit findQusSurveySubmitById(String id) {
        return qusSurveySubmitFeign.findQusSurveySubmitById(id);
    }

    public QusSurveySubmit saveQusSurveySubmit(QusSurveySubmit qusSurveySubmit) {
        return qusSurveySubmitFeign.saveQusSurveySubmit(qusSurveySubmit);
    }

    public List<QusSurveySubmit> findQusSurveySubmitListByCondition(QusSurveySubmit qusSurveySubmit) {
        return qusSurveySubmitFeign.findQusSurveySubmitListByCondition(qusSurveySubmit);
    }

    public QusSurveySubmit findOneQusSurveySubmitByCondition(QusSurveySubmit qusSurveySubmit) {
        return qusSurveySubmitFeign.findOneQusSurveySubmitByCondition(qusSurveySubmit);
    }

    public long findQusSurveySubmitCountByCondition(QusSurveySubmit qusSurveySubmit) {
        return qusSurveySubmitFeign.findQusSurveySubmitCountByCondition(qusSurveySubmit);
    }

    public void updateQusSurveySubmit(QusSurveySubmit qusSurveySubmit) {
        qusSurveySubmitFeign.updateQusSurveySubmit(qusSurveySubmit);
    }

    public void deleteQusSurveySubmit(String id) {
        qusSurveySubmitFeign.deleteQusSurveySubmit(id);
    }

    public void deleteQusSurveySubmitByCondition(QusSurveySubmit qusSurveySubmit) {
        qusSurveySubmitFeign.deleteQusSurveySubmitByCondition(qusSurveySubmit);
    }
}
