package com.yice.edu.cn.jy.service.subjectSource;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.ExamPointKnowledge;
import com.yice.edu.cn.jy.dao.subjectSource.IExamPointKnowledgeDao;

@Service
public class ExamPointKnowledgeService {
    @Autowired
    private IExamPointKnowledgeDao examPointKnowledgeDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public ExamPointKnowledge findExamPointKnowledgeById(String id) {
        return examPointKnowledgeDao.findExamPointKnowledgeById(id);
    }
    @Transactional
    public void saveExamPointKnowledge(ExamPointKnowledge examPointKnowledge) {
        examPointKnowledge.setId(sequenceId.nextId());
        examPointKnowledgeDao.saveExamPointKnowledge(examPointKnowledge);
    }
    @Transactional(readOnly = true)
    public List<ExamPointKnowledge> findExamPointKnowledgeListByCondition(ExamPointKnowledge examPointKnowledge) {
        return examPointKnowledgeDao.findExamPointKnowledgeListByCondition(examPointKnowledge);
    }
    @Transactional(readOnly = true)
    public ExamPointKnowledge findOneExamPointKnowledgeByCondition(ExamPointKnowledge examPointKnowledge) {
        return examPointKnowledgeDao.findOneExamPointKnowledgeByCondition(examPointKnowledge);
    }
    @Transactional(readOnly = true)
    public long findExamPointKnowledgeCountByCondition(ExamPointKnowledge examPointKnowledge) {
        return examPointKnowledgeDao.findExamPointKnowledgeCountByCondition(examPointKnowledge);
    }
    @Transactional
    public void updateExamPointKnowledge(ExamPointKnowledge examPointKnowledge) {
        examPointKnowledgeDao.updateExamPointKnowledge(examPointKnowledge);
    }
    @Transactional
    public void deleteExamPointKnowledge(String id) {
        examPointKnowledgeDao.deleteExamPointKnowledge(id);
    }
    @Transactional
    public void deleteExamPointKnowledgeByCondition(ExamPointKnowledge examPointKnowledge) {
        examPointKnowledgeDao.deleteExamPointKnowledgeByCondition(examPointKnowledge);
    }
    @Transactional
    public void batchSaveExamPointKnowledge(List<ExamPointKnowledge> examPointKnowledges){
        examPointKnowledges.forEach(examPointKnowledge -> examPointKnowledge.setId(sequenceId.nextId()));
        examPointKnowledgeDao.batchSaveExamPointKnowledge(examPointKnowledges);
    }

}
