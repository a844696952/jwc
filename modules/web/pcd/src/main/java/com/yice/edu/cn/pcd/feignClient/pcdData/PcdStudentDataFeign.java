package com.yice.edu.cn.pcd.feignClient.pcdData;

import com.yice.edu.cn.common.pojo.xw.pcdData.PcdStudentData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "pcdStudentDataFeign",path = "/pcdStudentData")
public interface PcdStudentDataFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findPcdStudentDataById/{id}")
    PcdStudentData findPcdStudentDataById(@PathVariable("id") String id);
    @PostMapping("/savePcdStudentData")
    PcdStudentData savePcdStudentData(PcdStudentData pcdStudentData);
    @PostMapping("/batchSavePcdStudentData")
    void batchSavePcdStudentData(List<PcdStudentData> pcdStudentDatas);
    @PostMapping("/findPcdStudentDataListByCondition")
    List<PcdStudentData> findPcdStudentDataListByCondition(PcdStudentData pcdStudentData);
    @PostMapping("/findOnePcdStudentDataByCondition")
    PcdStudentData findOnePcdStudentDataByCondition(PcdStudentData pcdStudentData);
    @PostMapping("/findPcdStudentDataCountByCondition")
    long findPcdStudentDataCountByCondition(PcdStudentData pcdStudentData);
    @PostMapping("/updatePcdStudentData")
    void updatePcdStudentData(PcdStudentData pcdStudentData);
    @PostMapping("/updatePcdStudentDataForAll")
    void updatePcdStudentDataForAll(PcdStudentData pcdStudentData);
    @GetMapping("/deletePcdStudentData/{id}")
    void deletePcdStudentData(@PathVariable("id") String id);
    @PostMapping("/deletePcdStudentDataByCondition")
    void deletePcdStudentDataByCondition(PcdStudentData pcdStudentData);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @PostMapping("/savePcdStudentDataKong")
    void savePcdStudentDataKong(PcdStudentData pcdStudentData);
}
