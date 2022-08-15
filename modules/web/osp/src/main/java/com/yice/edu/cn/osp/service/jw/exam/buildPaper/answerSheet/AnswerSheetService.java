package com.yice.edu.cn.osp.service.jw.exam.buildPaper.answerSheet;

import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.answerSheet.AnswerSheet;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.osp.feignClient.jw.exam.buildPaper.answerSheet.AnswerSheetFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerSheetService {
    @Autowired
    private AnswerSheetFeign answerSheetFeign;

    public AnswerSheet findAnswerSheetById(String id) {
        return answerSheetFeign.findAnswerSheetById(id);
    }

    public AnswerSheet saveAnswerSheet(AnswerSheet answerSheet) {
        return answerSheetFeign.saveAnswerSheet(answerSheet);
    }

    public List<AnswerSheet> findAnswerSheetListByCondition(AnswerSheet answerSheet) {
        return answerSheetFeign.findAnswerSheetListByCondition(answerSheet);
    }

    public AnswerSheet findOneAnswerSheetByCondition(AnswerSheet answerSheet) {
        return answerSheetFeign.findOneAnswerSheetByCondition(answerSheet);
    }

    public long findAnswerSheetCountByCondition(AnswerSheet answerSheet) {
        return answerSheetFeign.findAnswerSheetCountByCondition(answerSheet);
    }

    public void updateAnswerSheet(AnswerSheet answerSheet) {
        answerSheetFeign.updateAnswerSheet(answerSheet);
    }

    public void deleteAnswerSheet(String id) {
        answerSheetFeign.deleteAnswerSheet(id);
    }

    public void deleteAnswerSheetByCondition(AnswerSheet answerSheet) {
        answerSheetFeign.deleteAnswerSheetByCondition(answerSheet);
    }

}
