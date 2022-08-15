package com.yice.edu.cn.jy.service.subjectSource;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yice.edu.cn.common.constants.KnowledgeConstants;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.ExamPointKnowledge;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.ExaminationPoint;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.ExaminationPoint;
import com.yice.edu.cn.jy.dao.subjectSource.IExaminationPointDao;

@Service
public class ExaminationPointService {
    @Autowired
    private IExaminationPointDao examinationPointDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public ExaminationPoint findExaminationPointById(String id) {
        return examinationPointDao.findExaminationPointById(id);
    }
    @Transactional
    public void saveExaminationPoint(ExaminationPoint examinationPoint) {
      	//修改父节点为非叶子节点
    	if(!"-1".equals(examinationPoint.getParentId())) {
    		ExaminationPoint parentModel = new ExaminationPoint();
    		parentModel.setId(examinationPoint.getParentId());
    		parentModel.setLeaf(Integer.parseInt(KnowledgeConstants.LEAF.IS_NOT_LEAF));
    		examinationPointDao.updateExaminationPoint(parentModel);
    	}
        examinationPoint.setId(sequenceId.nextId());
        examinationPoint.setPath((examinationPoint.getPath()==null?"":examinationPoint.getPath())+examinationPoint.getId()+";");
        examinationPointDao.saveExaminationPoint(examinationPoint);
    }
    @Transactional(readOnly = true)
    public List<ExaminationPoint> findExaminationPointListByCondition(ExaminationPoint examinationPoint) {
        return examinationPointDao.findExaminationPointListByCondition(examinationPoint);
    }
    @Transactional(readOnly = true)
    public ExaminationPoint findOneExaminationPointByCondition(ExaminationPoint examinationPoint) {
        return examinationPointDao.findOneExaminationPointByCondition(examinationPoint);
    }
    @Transactional(readOnly = true)
    public long findExaminationPointCountByCondition(ExaminationPoint examinationPoint) {
        return examinationPointDao.findExaminationPointCountByCondition(examinationPoint);
    }
    @Transactional
    public void updateExaminationPoint(ExaminationPoint examinationPoint) {
        examinationPointDao.updateExaminationPoint(examinationPoint);
    }
    @Transactional
    public void deleteExaminationPoint(String id) {
    	ExaminationPoint examinationPoint = examinationPointDao.findExaminationPointById(id);
    	
    	ExaminationPoint queryExaminationPoint = new ExaminationPoint();
    	queryExaminationPoint.setParentId(examinationPoint.getParentId());
    	long count = examinationPointDao.findExaminationPointCountByCondition(queryExaminationPoint);
    	if(count<=1) {
    		 //修改父节点为叶子节点
    		ExaminationPoint updateExaminationPoint = new ExaminationPoint();
    		updateExaminationPoint.setId(examinationPoint.getParentId());
    		updateExaminationPoint.setLeaf(Integer.parseInt(KnowledgeConstants.LEAF.IS_LEAF));
    		examinationPointDao.updateExaminationPoint(updateExaminationPoint);
    	}
        examinationPointDao.deleteExaminationPoint(id);
    }
    @Transactional
    public void deleteExaminationPointByCondition(ExaminationPoint examinationPoint) {
        examinationPointDao.deleteExaminationPointByCondition(examinationPoint);
    }
    @Transactional
    public void batchSaveExaminationPoint(List<ExaminationPoint> examinationPoints){
        examinationPoints.forEach(examinationPoint -> examinationPoint.setId(sequenceId.nextId()));
        examinationPointDao.batchSaveExaminationPoint(examinationPoints);
    }
    
    @Transactional(readOnly = true)
    public List<KnowledgePoint> findKnowledgePointListByExamPoint(ExamPointKnowledge examPointKnowledge) {
        return examinationPointDao.findKnowledgePointListByExamPoint(examPointKnowledge);
    }
    @Transactional(readOnly = true)
    public long findKnowledgePointCountByExamPoint(ExamPointKnowledge examPointKnowledge) {
        return examinationPointDao.findKnowledgePointCountByExamPoint(examPointKnowledge);
    }

}
