package com.yice.edu.cn.pcd.feignClient.pcdData;

import com.yice.edu.cn.common.pojo.xw.pcdData.PcdEducationData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient(value="xw",contextId = "pcdEducationDataFeign",path = "/pcdEducationData")
public interface PcdEducationDataFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findPcdEducationDataById/{id}")
    PcdEducationData findPcdEducationDataById(@PathVariable("id") String id);
    @PostMapping("/savePcdEducationData")
    PcdEducationData savePcdEducationData(PcdEducationData pcdEducationData);
    @PostMapping("/batchSavePcdEducationData")
    void batchSavePcdEducationData(List<PcdEducationData> pcdEducationDatas);
    @PostMapping("/findPcdEducationDataListByCondition")
    List<PcdEducationData> findPcdEducationDataListByCondition(PcdEducationData pcdEducationData);
    @PostMapping("/findOnePcdEducationDataByCondition")
    PcdEducationData findOnePcdEducationDataByCondition(PcdEducationData pcdEducationData);
    @PostMapping("/findPcdEducationDataCountByCondition")
    long findPcdEducationDataCountByCondition(PcdEducationData pcdEducationData);
    @PostMapping("/updatePcdEducationData")
    void updatePcdEducationData(PcdEducationData pcdEducationData);
    @PostMapping("/updatePcdEducationDataForAll")
    void updatePcdEducationDataForAll(PcdEducationData pcdEducationData);
    @GetMapping("/deletePcdEducationData/{id}")
    void deletePcdEducationData(@PathVariable("id") String id);
    @PostMapping("/deletePcdEducationDataByCondition")
    void deletePcdEducationDataByCondition(PcdEducationData pcdEducationData);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @PostMapping("/savePcdEducationDataKong")
    void savePcdEducationDataKong(PcdEducationData pcdEducationData);

    @GetMapping("/findIndexDataByEehId/{eehId}")
    Map<String,Object> findIndexDataByEehId(@PathVariable("eehId")String eehId);
}
