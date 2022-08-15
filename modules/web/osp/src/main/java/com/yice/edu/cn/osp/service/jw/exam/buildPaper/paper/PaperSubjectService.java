package com.yice.edu.cn.osp.service.jw.exam.buildPaper.paper;

import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperSubject;
import com.yice.edu.cn.osp.feignClient.jw.exam.buildPaper.paper.PaperSubjectFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaperSubjectService {
    @Autowired
    private PaperSubjectFeign paperSubjectFeign;


    public void savePaperSubject(PaperSubject paperSubject){
        paperSubjectFeign.savaPaperSubject(paperSubject);
    }

    public PaperSubject findOnePaperSubjectKong(String createUserId){
        return paperSubjectFeign.findOnePaperSubjectKong(createUserId);
    }

}
