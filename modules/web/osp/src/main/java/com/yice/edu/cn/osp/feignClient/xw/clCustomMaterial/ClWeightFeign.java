package com.yice.edu.cn.osp.feignClient.xw.clCustomMaterial;

import com.yice.edu.cn.common.pojo.xw.clCustomMaterial.ClWeight;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "clWeightFeign",path = "/clWeight")
public interface ClWeightFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findClWeightById/{id}")
    ClWeight findClWeightById(@PathVariable("id") String id);
    @PostMapping("/saveClWeight")
    ClWeight saveClWeight(ClWeight clWeight);
    @PostMapping("/batchSaveClWeight")
    void batchSaveClWeight(List<ClWeight> clWeights);
    @PostMapping("/findClWeightListByCondition")
    List<ClWeight> findClWeightListByCondition(ClWeight clWeight);
    @PostMapping("/findOneClWeightByCondition")
    ClWeight findOneClWeightByCondition(ClWeight clWeight);
    @PostMapping("/findClWeightCountByCondition")
    long findClWeightCountByCondition(ClWeight clWeight);
    @PostMapping("/updateClWeight")
    void updateClWeight(ClWeight clWeight);
    @PostMapping("/updateClWeightForAll")
    void updateClWeightForAll(ClWeight clWeight);
    @GetMapping("/deleteClWeight/{id}")
    void deleteClWeight(@PathVariable("id") String id);
    @PostMapping("/deleteClWeightByCondition")
    void deleteClWeightByCondition(ClWeight clWeight);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
   @PostMapping("/addMaxWeight")
    Long addMaxWeight(ClWeight clWeight);
}
