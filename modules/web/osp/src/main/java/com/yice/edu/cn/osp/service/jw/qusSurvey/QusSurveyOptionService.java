package com.yice.edu.cn.osp.service.jw.qusSurvey;

import com.yice.edu.cn.common.pojo.jw.qusSurvey.QusSurveyOption;
import com.yice.edu.cn.osp.feignClient.jw.qusSurvey.QusSurveyOptionFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QusSurveyOptionService {
    @Autowired
    private QusSurveyOptionFeign qusSurveyOptionFeign;

    public QusSurveyOption findQusSurveyOptionById(String id) {
        return qusSurveyOptionFeign.findQusSurveyOptionById(id);
    }

    public QusSurveyOption saveQusSurveyOption(QusSurveyOption qusSurveyOption) {
        return qusSurveyOptionFeign.saveQusSurveyOption(qusSurveyOption);
    }

    public List<QusSurveyOption> findQusSurveyOptionListByCondition(QusSurveyOption qusSurveyOption) {
        return qusSurveyOptionFeign.findQusSurveyOptionListByCondition(qusSurveyOption);
    }

    public QusSurveyOption findOneQusSurveyOptionByCondition(QusSurveyOption qusSurveyOption) {
        return qusSurveyOptionFeign.findOneQusSurveyOptionByCondition(qusSurveyOption);
    }

    public long findQusSurveyOptionCountByCondition(QusSurveyOption qusSurveyOption) {
        return qusSurveyOptionFeign.findQusSurveyOptionCountByCondition(qusSurveyOption);
    }

    public void updateQusSurveyOption(QusSurveyOption qusSurveyOption) {
        qusSurveyOptionFeign.updateQusSurveyOption(qusSurveyOption);
    }

    public void deleteQusSurveyOption(String id) {
        qusSurveyOptionFeign.deleteQusSurveyOption(id);
    }

    public void deleteQusSurveyOptionByCondition(QusSurveyOption qusSurveyOption) {
        qusSurveyOptionFeign.deleteQusSurveyOptionByCondition(qusSurveyOption);
    }

    public List<QusSurveyOption> getOptionCountList(QusSurveyOption qusSurveyOption) {
      return   qusSurveyOptionFeign.getOptionCountList(qusSurveyOption);
    }

}
