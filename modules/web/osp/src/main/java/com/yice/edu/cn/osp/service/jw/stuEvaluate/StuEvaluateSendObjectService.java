package com.yice.edu.cn.osp.service.jw.stuEvaluate;

import com.yice.edu.cn.common.pojo.jw.stuEvaluate.StuEvaluateSendObject;
import com.yice.edu.cn.osp.feignClient.jw.stuEvaluate.StuEvaluateSendObjectFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StuEvaluateSendObjectService {
    @Autowired
    private StuEvaluateSendObjectFeign stuEvaluateSendObjectFeign;

    public StuEvaluateSendObject findStuEvaluateSendObjectById(String id) {
        return stuEvaluateSendObjectFeign.findStuEvaluateSendObjectById(id);
    }

    public StuEvaluateSendObject saveStuEvaluateSendObject(StuEvaluateSendObject stuEvaluateSendObject) {
        return stuEvaluateSendObjectFeign.saveStuEvaluateSendObject(stuEvaluateSendObject);
    }

    public List<StuEvaluateSendObject> findStuEvaluateSendObjectListByCondition(StuEvaluateSendObject stuEvaluateSendObject) {
        return stuEvaluateSendObjectFeign.findStuEvaluateSendObjectListByCondition(stuEvaluateSendObject);
    }

    public StuEvaluateSendObject findOneStuEvaluateSendObjectByCondition(StuEvaluateSendObject stuEvaluateSendObject) {
        return stuEvaluateSendObjectFeign.findOneStuEvaluateSendObjectByCondition(stuEvaluateSendObject);
    }

    public long findStuEvaluateSendObjectCountByCondition(StuEvaluateSendObject stuEvaluateSendObject) {
        return stuEvaluateSendObjectFeign.findStuEvaluateSendObjectCountByCondition(stuEvaluateSendObject);
    }

    public void updateStuEvaluateSendObject(StuEvaluateSendObject stuEvaluateSendObject) {
        stuEvaluateSendObjectFeign.updateStuEvaluateSendObject(stuEvaluateSendObject);
    }

    public void deleteStuEvaluateSendObject(String id) {
        stuEvaluateSendObjectFeign.deleteStuEvaluateSendObject(id);
    }

    public void deleteStuEvaluateSendObjectByCondition(StuEvaluateSendObject stuEvaluateSendObject) {
        stuEvaluateSendObjectFeign.deleteStuEvaluateSendObjectByCondition(stuEvaluateSendObject);
    }
}
