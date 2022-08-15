package com.yice.edu.cn.tap.service.exam;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.Paper;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperShare;
import com.yice.edu.cn.tap.feignClient.exam.PaperShareFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperShareService {
    @Autowired
    private PaperShareFeign paperShareFeign;
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    public PaperShare findPaperShareById(String id) {
        return paperShareFeign.findPaperShareById(id);
    }

    public PaperShare savePaperShare(PaperShare paperShare) {
        return paperShareFeign.savePaperShare(paperShare);
    }

    public void batchSavePaperShare(List<PaperShare> paperShares){
        paperShareFeign.batchSavePaperShare(paperShares);
    }

    public List<PaperShare> findPaperShareListByCondition(PaperShare paperShare) {
        return paperShareFeign.findPaperShareListByCondition(paperShare);
    }

    public PaperShare findOnePaperShareByCondition(PaperShare paperShare) {
        return paperShareFeign.findOnePaperShareByCondition(paperShare);
    }

    public long findPaperShareCountByCondition(PaperShare paperShare) {
        return paperShareFeign.findPaperShareCountByCondition(paperShare);
    }

    public void updatePaperShare(PaperShare paperShare) {
        paperShareFeign.updatePaperShare(paperShare);
    }

    public void updatePaperShareForAll(PaperShare paperShare) {
        paperShareFeign.updatePaperShareForAll(paperShare);
    }

    public void deletePaperShare(String id) {
        paperShareFeign.deletePaperShare(id);
    }

    public void deletePaperShareByCondition(PaperShare paperShare) {
        paperShareFeign.deletePaperShareByCondition(paperShare);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    /*public List<PaperShare> findPaperShareListByConditionKong(PaperShare paperShare){
        return paperShareFeign.findPaperShareListByConditionKong(paperShare);
    }

    public long findPaperShareCountByConditionKong(PaperShare paperShare){
        return paperShareFeign.findPaperShareCountByConditionKong(paperShare);
    }*/

    public void savePaperShareListKong(PaperShare paperShare){
        paperShareFeign.savePaperShareListKong(paperShare);
    }
    public ResponseJson updatePaperShareAddPaper(PaperShare paperShare){
        return paperShareFeign.updatePaperShareAddPaper(paperShare);
    }

    public Paper uploadSharePaper(String paperId){
        return paperShareFeign.uploadSharePaper(paperId);
    }

    public void savePaperShareKong(PaperShare paperShare){
        paperShareFeign.savePaperShareKong(paperShare);
    }
}
