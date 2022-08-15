package com.yice.edu.cn.osp.feignClient.jw.exam.buildPaper.paper;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.Paper;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperShare;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "paperShareFeign",path = "/paperShare")
public interface PaperShareFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findPaperShareById/{id}")
    PaperShare findPaperShareById(@PathVariable("id") String id);
    @PostMapping("/savePaperShare")
    PaperShare savePaperShare(PaperShare paperShare);
    @PostMapping("/batchSavePaperShare")
    void batchSavePaperShare(List<PaperShare> paperShares);
    @PostMapping("/findPaperShareListByCondition")
    List<PaperShare> findPaperShareListByCondition(PaperShare paperShare);
    @PostMapping("/findOnePaperShareByCondition")
    PaperShare findOnePaperShareByCondition(PaperShare paperShare);
    @PostMapping("/findPaperShareCountByCondition")
    long findPaperShareCountByCondition(PaperShare paperShare);
    @PostMapping("/updatePaperShare")
    void updatePaperShare(PaperShare paperShare);
    @PostMapping("/updatePaperShareForAll")
    void updatePaperShareForAll(PaperShare paperShare);
    @GetMapping("/deletePaperShare/{id}")
    void deletePaperShare(@PathVariable("id") String id);
    @PostMapping("/deletePaperShareByCondition")
    void deletePaperShareByCondition(PaperShare paperShare);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    /*@PostMapping("/findPaperShareListByConditionKong")
    List<PaperShare> findPaperShareListByConditionKong(PaperShare paperShare);
    @PostMapping("/findPaperShareCountByConditionKong")
    long findPaperShareCountByConditionKong(PaperShare paperShare);*/

    @PostMapping("/savePaperShareListKong")
    void savePaperShareListKong(PaperShare paperShare);
    @PostMapping("/updatePaperShareAddPaper")
    ResponseJson updatePaperShareAddPaper(PaperShare paperShare);
    @GetMapping("/uploadSharePaper/{paperId}")
    Paper uploadSharePaper(@PathVariable("paperId")String paperId);

    @PostMapping("/savePaperShareKong")
    void savePaperShareKong(@RequestBody PaperShare paperShare);
}
