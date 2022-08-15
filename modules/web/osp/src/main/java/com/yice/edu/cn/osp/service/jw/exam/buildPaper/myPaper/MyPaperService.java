package com.yice.edu.cn.osp.service.jw.exam.buildPaper.myPaper;

import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.Paper;
import com.yice.edu.cn.osp.feignClient.jw.exam.buildPaper.myPaper.MyPaperFeign;
import com.yice.edu.cn.osp.feignClient.jw.exam.buildPaper.paper.PaperFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyPaperService {
    @Autowired
    private MyPaperFeign myPaperFeign;

    @Autowired
    private PaperFeign paperFeign;

    public Paper findOneTestPaperByCondition(Paper paper){
        return myPaperFeign.findOneTestPaperByCondition(paper);
    }

    public Paper updatePaperQuestion(Paper paper){
       return   myPaperFeign.updatePaperQuestion(paper);
    }

    public long deletePaper(String id){
        return myPaperFeign.deletePaper(id);
    }

    public Paper findOnePaper(Paper paper){
        return myPaperFeign.findOnePaper(paper);
    }

    public Paper paperClone(Paper paper){
        return myPaperFeign.paperClone(paper);
    }

    public Paper coveringPaper(Paper paper){
        return myPaperFeign.coveringPaper(paper);
    }
}
