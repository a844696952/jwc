package com.yice.edu.cn.pcd.feignClient.pcdData;

import com.yice.edu.cn.common.pojo.xw.pcdData.PcdTeacherData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "pcdTeacherDataFeign",path = "/pcdTeacherData")
public interface PcdTeacherDataFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findPcdTeacherDataById/{id}")
    PcdTeacherData findPcdTeacherDataById(@PathVariable("id") String id);
    @PostMapping("/savePcdTeacherData")
    PcdTeacherData savePcdTeacherData(PcdTeacherData pcdTeacherData);
    @PostMapping("/batchSavePcdTeacherData")
    void batchSavePcdTeacherData(List<PcdTeacherData> pcdTeacherDatas);
    @PostMapping("/findPcdTeacherDataListByCondition")
    List<PcdTeacherData> findPcdTeacherDataListByCondition(PcdTeacherData pcdTeacherData);
    @PostMapping("/findOnePcdTeacherDataByCondition")
    PcdTeacherData findOnePcdTeacherDataByCondition(PcdTeacherData pcdTeacherData);
    @PostMapping("/findPcdTeacherDataCountByCondition")
    long findPcdTeacherDataCountByCondition(PcdTeacherData pcdTeacherData);
    @PostMapping("/updatePcdTeacherData")
    void updatePcdTeacherData(PcdTeacherData pcdTeacherData);
    @PostMapping("/updatePcdTeacherDataForAll")
    void updatePcdTeacherDataForAll(PcdTeacherData pcdTeacherData);
    @GetMapping("/deletePcdTeacherData/{id}")
    void deletePcdTeacherData(@PathVariable("id") String id);
    @PostMapping("/deletePcdTeacherDataByCondition")
    void deletePcdTeacherDataByCondition(PcdTeacherData pcdTeacherData);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @PostMapping("/savePcdTeacherDataKong")
    void savePcdTeacherDataKong(PcdTeacherData pcdTeacherData);
}
