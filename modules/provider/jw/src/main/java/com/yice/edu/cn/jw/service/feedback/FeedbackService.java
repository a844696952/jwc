package com.yice.edu.cn.jw.service.feedback;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.feedback.Feedback;
import com.yice.edu.cn.jw.dao.feedback.IFeedbackDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class FeedbackService {
    @Autowired
    private IFeedbackDao feedbackDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public Feedback findFeedbackById(String id) {
        return feedbackDao.findFeedbackById(id);
    }
    @Transactional
    public void saveFeedback(Feedback feedback) {
        feedback.setId(sequenceId.nextId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        if(feedback.getStatus()==null||"".equals(feedback.getStatus().trim())){
            feedback.setStatus("1");//未处理
        }
        feedback.setUpdateTime(sdf.format(date));
        feedbackDao.saveFeedback(feedback);
    }
    @Transactional(readOnly = true)
    public List<Feedback> findFeedbackListByCondition(Feedback feedback) {
        if(!StringUtils.isEmpty(feedback.getProblemDescription())){
            feedback.setProblemDescription(feedback.getProblemDescription().trim());
        }
        return feedbackDao.findFeedbackListByCondition4Like(feedback);
        //return feedbackDao.findFeedbackListByCondition(feedback);
    }
    @Transactional(readOnly = true)
    public Feedback findOneFeedbackByCondition(Feedback feedback) {
        return feedbackDao.findOneFeedbackByCondition(feedback);
    }
    @Transactional(readOnly = true)
    public long findFeedbackCountByCondition(Feedback feedback) {
        if(!StringUtils.isEmpty(feedback.getProblemDescription())){
            feedback.setProblemDescription(feedback.getProblemDescription().trim());
        }
        return feedbackDao.findFeedbackCountByCondition4Like(feedback);
        //return feedbackDao.findFeedbackCountByCondition(feedback);
    }
    @Transactional
    public void updateFeedback(Feedback feedback) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        feedback.setUpdateTime(sdf.format(date));
        feedbackDao.updateFeedback(feedback);
    }
    @Transactional
    public void deleteFeedback(String id) {
        feedbackDao.deleteFeedback(id);
    }
    @Transactional
    public void deleteFeedbackByCondition(Feedback feedback) {
        feedbackDao.deleteFeedbackByCondition(feedback);
    }
    @Transactional
    public void batchSaveFeedback(List<Feedback> feedbacks){
        feedbacks.forEach(feedback -> feedback.setId(sequenceId.nextId()));
        feedbackDao.batchSaveFeedback(feedbacks);
    }

}
