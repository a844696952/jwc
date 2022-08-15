package com.yice.edu.cn.bmp.service.subjectSource;

import com.yice.edu.cn.bmp.feignClient.subjectSource.KnowledgePointFeign;
import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KnowledgePointService {
    @Autowired
    private KnowledgePointFeign knowledgePointFeign;

    public KnowledgePoint findKnowledgePointById(String id) {
        return knowledgePointFeign.findKnowledgePointById(id);
    }

    public KnowledgePoint saveKnowledgePoint(KnowledgePoint knowledgePoint) {
        return knowledgePointFeign.saveKnowledgePoint(knowledgePoint);
    }

    public List<KnowledgePoint> findKnowledgePointListByCondition(KnowledgePoint knowledgePoint) {
        return knowledgePointFeign.findKnowledgePointListByCondition(knowledgePoint);
    }

    public KnowledgePoint findOneKnowledgePointByCondition(KnowledgePoint knowledgePoint) {
        return knowledgePointFeign.findOneKnowledgePointByCondition(knowledgePoint);
    }

    public long findKnowledgePointCountByCondition(KnowledgePoint knowledgePoint) {
        return knowledgePointFeign.findKnowledgePointCountByCondition(knowledgePoint);
    }

    public KnowledgePoint updateKnowledgePoint(KnowledgePoint knowledgePoint) {
        return knowledgePointFeign.updateKnowledgePoint(knowledgePoint);
    }

    public void deleteKnowledgePoint(String id) {
        knowledgePointFeign.deleteKnowledgePoint(id);
    }

    public void deleteKnowledgePointByCondition(KnowledgePoint knowledgePoint) {
        knowledgePointFeign.deleteKnowledgePointByCondition(knowledgePoint);
    }
}
