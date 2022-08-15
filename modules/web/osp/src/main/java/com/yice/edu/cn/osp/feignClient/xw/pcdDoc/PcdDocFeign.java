package com.yice.edu.cn.osp.feignClient.xw.pcdDoc;

import com.yice.edu.cn.common.pojo.xw.pcdDoc.PcdDoc;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "pcdDocFeign",path = "/pcdDoc")
public interface PcdDocFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findPcdDocById/{id}")
    PcdDoc findPcdDocById(@PathVariable("id") String id);
    @PostMapping("/savePcdDoc")
    PcdDoc savePcdDoc(PcdDoc pcdDoc);
    @PostMapping("/batchSavePcdDoc")
    void batchSavePcdDoc(List<PcdDoc> pcdDocs);
    @PostMapping("/findPcdDocListByCondition")
    List<PcdDoc> findPcdDocListByCondition(PcdDoc pcdDoc);
    @PostMapping("/findOnePcdDocByCondition")
    PcdDoc findOnePcdDocByCondition(PcdDoc pcdDoc);
    @PostMapping("/findPcdDocCountByCondition")
    long findPcdDocCountByCondition(PcdDoc pcdDoc);
    @PostMapping("/updatePcdDoc")
    void updatePcdDoc(PcdDoc pcdDoc);
    @PostMapping("/updatePcdDocForAll")
    void updatePcdDocForAll(PcdDoc pcdDoc);
    @GetMapping("/deletePcdDoc/{id}")
    void deletePcdDoc(@PathVariable("id") String id);
    @PostMapping("/deletePcdDocByCondition")
    void deletePcdDocByCondition(PcdDoc pcdDoc);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @PostMapping("/findListPcdDocByPcdDocKong")
    List<PcdDoc> findListPcdDocByPcdDocKong(PcdDoc pcdDoc);
    @PostMapping("/findCountPcdDocByPcdDocKong")
    long findCountPcdDocByPcdDocKong(PcdDoc pcdDoc);

    @PostMapping("/saveForwardDoc")
    PcdDoc saveForwardDoc(PcdDoc pcdDoc);
}
