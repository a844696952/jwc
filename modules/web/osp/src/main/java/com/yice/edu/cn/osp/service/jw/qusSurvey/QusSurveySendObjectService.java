package com.yice.edu.cn.osp.service.jw.qusSurvey;

import com.yice.edu.cn.common.pojo.jw.qusSurvey.QusSurveySendObject;
import com.yice.edu.cn.osp.feignClient.jw.qusSurvey.QusSurveySendObjectFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QusSurveySendObjectService {
    @Autowired
    private QusSurveySendObjectFeign qusSurveySendObjectFeign;

    public QusSurveySendObject findQusSurveySendObjectById(String id) {
        return qusSurveySendObjectFeign.findQusSurveySendObjectById(id);
    }

    public QusSurveySendObject saveQusSurveySendObject(QusSurveySendObject qusSurveySendObject) {
        return qusSurveySendObjectFeign.saveQusSurveySendObject(qusSurveySendObject);
    }

    public List<QusSurveySendObject> findQusSurveySendObjectListByCondition(QusSurveySendObject qusSurveySendObject) {
        return qusSurveySendObjectFeign.findQusSurveySendObjectListByCondition(qusSurveySendObject);
    }

    public QusSurveySendObject findOneQusSurveySendObjectByCondition(QusSurveySendObject qusSurveySendObject) {
        return qusSurveySendObjectFeign.findOneQusSurveySendObjectByCondition(qusSurveySendObject);
    }

    public long findQusSurveySendObjectCountByCondition(QusSurveySendObject qusSurveySendObject) {
        return qusSurveySendObjectFeign.findQusSurveySendObjectCountByCondition(qusSurveySendObject);
    }

    public void updateQusSurveySendObject(QusSurveySendObject qusSurveySendObject) {
        qusSurveySendObjectFeign.updateQusSurveySendObject(qusSurveySendObject);
    }

    public void deleteQusSurveySendObject(String id) {
        qusSurveySendObjectFeign.deleteQusSurveySendObject(id);
    }

    public void deleteQusSurveySendObjectByCondition(QusSurveySendObject qusSurveySendObject) {
        qusSurveySendObjectFeign.deleteQusSurveySendObjectByCondition(qusSurveySendObject);
    }
}
