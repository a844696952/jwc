package com.yice.edu.cn.bmp.service.scoreAnalysis;

import com.yice.edu.cn.bmp.feignClient.scoreAnalysis.AnalyseStuKnowledgeFeign;
import com.yice.edu.cn.common.pojo.xq.analysisKnowledge.AnalyseStuKnowledge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class AnalyseStuKnowledgeService {

    @Autowired
    private AnalyseStuKnowledgeFeign analyseStuKnowledgeFeign;

    //查询学生薄弱知识点
    public List<AnalyseStuKnowledge> findWeakPoint(AnalyseStuKnowledge analyseStuKnowledge) {

        List<AnalyseStuKnowledge> analyseStuKnowledgeList = analyseStuKnowledgeFeign.findAnalyseStuKnowledgeListByCondition(analyseStuKnowledge);

        /*List<AnalyseStuKnowledge> newAnalyseStuKnowledgeList = new ArrayList<AnalyseStuKnowledge>();

        for (AnalyseStuKnowledge stuKnowledge : analyseStuKnowledgeList) {
            if (stuKnowledge.getGetPersent() < 60) {

                newAnalyseStuKnowledgeList.add(stuKnowledge);
            }

        }

        List<AnalyseStuKnowledge> collect = newAnalyseStuKnowledgeList.stream().sorted(Comparator.comparing(AnalyseStuKnowledge::getGetPersent)).collect(Collectors.toList());*/

        return analyseStuKnowledgeList;
    }


    //查询学生知识点分析
    public List<AnalyseStuKnowledge> findAnalyseStuKnowledge(AnalyseStuKnowledge analyseStuKnowledge) {

        List<AnalyseStuKnowledge> analyseStuKnowledgeList = analyseStuKnowledgeFeign.findAnalyseStuKnowledgeListByCondition(analyseStuKnowledge);


        //List<AnalyseStuKnowledge> collect = analyseStuKnowledgeList.stream().sorted(Comparator.comparing(AnalyseStuKnowledge::getTopicCount).reversed()).collect(Collectors.toList());

        return analyseStuKnowledgeList;
    }


}
