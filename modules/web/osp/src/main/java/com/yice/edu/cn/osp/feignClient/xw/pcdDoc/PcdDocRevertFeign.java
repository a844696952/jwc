package com.yice.edu.cn.osp.feignClient.xw.pcdDoc;

import com.yice.edu.cn.common.pojo.xw.pcdDoc.PcdDocRevert;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "pcdDocRevertFeign",path = "/pcdDocRevert")
public interface PcdDocRevertFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findPcdDocRevertById/{id}")
    PcdDocRevert findPcdDocRevertById(@PathVariable("id") String id);
    @PostMapping("/savePcdDocRevert")
    PcdDocRevert savePcdDocRevert(PcdDocRevert pcdDocRevert);
    @PostMapping("/batchSavePcdDocRevert")
    void batchSavePcdDocRevert(List<PcdDocRevert> pcdDocReverts);
    @PostMapping("/findPcdDocRevertListByCondition")
    List<PcdDocRevert> findPcdDocRevertListByCondition(PcdDocRevert pcdDocRevert);
    @PostMapping("/findOnePcdDocRevertByCondition")
    PcdDocRevert findOnePcdDocRevertByCondition(PcdDocRevert pcdDocRevert);
    @PostMapping("/findPcdDocRevertCountByCondition")
    long findPcdDocRevertCountByCondition(PcdDocRevert pcdDocRevert);
    @PostMapping("/updatePcdDocRevert")
    void updatePcdDocRevert(PcdDocRevert pcdDocRevert);
    @PostMapping("/updatePcdDocRevertForAll")
    void updatePcdDocRevertForAll(PcdDocRevert pcdDocRevert);
    @GetMapping("/deletePcdDocRevert/{id}")
    void deletePcdDocRevert(@PathVariable("id") String id);
    @PostMapping("/deletePcdDocRevertByCondition")
    void deletePcdDocRevertByCondition(PcdDocRevert pcdDocRevert);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @PostMapping("/savePcdDocRevertKong")
    void savePcdDocRevertKong(PcdDocRevert pcdDocRevert);

    @GetMapping("/findAndUpdatePcdDocRevertById/{id}")
    PcdDocRevert findAndUpdatePcdDocRevertById(@PathVariable("id") String id);
}
