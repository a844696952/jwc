package com.yice.edu.cn.tap.service.knowledge;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yice.edu.cn.common.pojo.jy.knowledge.JyKnowledge;
import com.yice.edu.cn.tap.feignClient.knowledge.JyKnowledgeFeign;

@Service
public class JyKnowledgeService {
    @Autowired
    private JyKnowledgeFeign jyKnowledgeFeign;

    public JyKnowledge findJyKnowledgeById(String id) {
        return jyKnowledgeFeign.findJyKnowledgeById(id);
    }

    public List<JyKnowledge> findJyKnowledgeListByCondition(JyKnowledge jyKnowledge) {
        return jyKnowledgeFeign.findJyKnowledgeListByCondition(jyKnowledge);
    }

    public JyKnowledge findOneJyKnowledgeByCondition(JyKnowledge jyKnowledge) {
        return jyKnowledgeFeign.findOneJyKnowledgeByCondition(jyKnowledge);
    }

    public long findJyKnowledgeCountByCondition(JyKnowledge jyKnowledge) {
        return jyKnowledgeFeign.findJyKnowledgeCountByCondition(jyKnowledge);
    }
}
