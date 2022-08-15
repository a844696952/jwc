package com.yice.edu.cn.pcd.feignClient.pcdData;

import com.yice.edu.cn.common.pojo.xw.pcdData.PcdTeachingData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "pcdTeachingDataFeign",path = "/pcdTeachingData")
public interface PcdTeachingDataFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findPcdTeachingDataById/{id}")
    PcdTeachingData findPcdTeachingDataById(@PathVariable("id") String id);
    @PostMapping("/savePcdTeachingData")
    PcdTeachingData savePcdTeachingData(PcdTeachingData pcdTeachingData);
    @PostMapping("/batchSavePcdTeachingData")
    void batchSavePcdTeachingData(List<PcdTeachingData> pcdTeachingDatas);
    @PostMapping("/findPcdTeachingDataListByCondition")
    List<PcdTeachingData> findPcdTeachingDataListByCondition(PcdTeachingData pcdTeachingData);
    @PostMapping("/findOnePcdTeachingDataByCondition")
    PcdTeachingData findOnePcdTeachingDataByCondition(PcdTeachingData pcdTeachingData);
    @PostMapping("/findPcdTeachingDataCountByCondition")
    long findPcdTeachingDataCountByCondition(PcdTeachingData pcdTeachingData);
    @PostMapping("/updatePcdTeachingData")
    void updatePcdTeachingData(PcdTeachingData pcdTeachingData);
    @PostMapping("/updatePcdTeachingDataForAll")
    void updatePcdTeachingDataForAll(PcdTeachingData pcdTeachingData);
    @GetMapping("/deletePcdTeachingData/{id}")
    void deletePcdTeachingData(@PathVariable("id") String id);
    @PostMapping("/deletePcdTeachingDataByCondition")
    void deletePcdTeachingDataByCondition(PcdTeachingData pcdTeachingData);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @PostMapping("/savePcdTeachingDataKong")
    void savePcdTeachingDataKong(PcdTeachingData pcdTeachingData);
    @GetMapping("/findPcdTeachingDataByIdKong/{id}")
    PcdTeachingData findPcdTeachingDataByIdKong(@PathVariable("id")String id);
}
