package com.yice.edu.cn.pcd.feignClient.pcdDoc;

import com.yice.edu.cn.common.pojo.xw.pcdDoc.PcdDoc;
import com.yice.edu.cn.common.pojo.xw.pcdDoc.PcdDocRevert;
import com.yice.edu.cn.common.pojo.xw.pcdDoc.PcdSend;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "pcdSendFeign",path = "/pcdSend")
public interface PcdSendFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findPcdSendById/{id}")
    PcdSend findPcdSendById(@PathVariable("id") String id);
    @PostMapping("/savePcdSend")
    PcdSend savePcdSend(PcdSend pcdSend);
    @PostMapping("/batchSavePcdSend")
    void batchSavePcdSend(List<PcdSend> pcdSends);
    @PostMapping("/findPcdSendListByCondition")
    List<PcdSend> findPcdSendListByCondition(PcdSend pcdSend);
    @PostMapping("/findOnePcdSendByCondition")
    PcdSend findOnePcdSendByCondition(PcdSend pcdSend);
    @PostMapping("/findPcdSendCountByCondition")
    long findPcdSendCountByCondition(PcdSend pcdSend);
    @PostMapping("/updatePcdSend")
    void updatePcdSend(PcdSend pcdSend);
    @PostMapping("/updatePcdSendForAll")
    void updatePcdSendForAll(PcdSend pcdSend);
    @GetMapping("/deletePcdSend/{id}")
    void deletePcdSend(@PathVariable("id") String id);
    @PostMapping("/deletePcdSendByCondition")
    void deletePcdSendByCondition(PcdSend pcdSend);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @PostMapping("/findOnePcdSendByPcdSend")
    PcdDoc findOnePcdSendByPcdSend(PcdDocRevert pcdDocRevert);

    @PostMapping("/findPcdSendByListKong")
    List<PcdSend> findPcdSendByListKong(PcdSend pcdSend);
    @PostMapping("/findPcdSendByCountKong")
    long findPcdSendByCountKong(PcdSend pcdSend);
}
