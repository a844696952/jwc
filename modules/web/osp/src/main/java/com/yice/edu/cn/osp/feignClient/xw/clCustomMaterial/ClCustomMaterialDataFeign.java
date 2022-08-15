package com.yice.edu.cn.osp.feignClient.xw.clCustomMaterial;

import com.yice.edu.cn.common.pojo.xw.clCustomMaterial.ClCustomMaterialData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "clCustomMaterialDataFeign",path = "/clCustomMaterialData")
public interface ClCustomMaterialDataFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findClCustomMaterialDataById/{id}")
    ClCustomMaterialData findClCustomMaterialDataById(@PathVariable("id") String id);
    @PostMapping("/saveClCustomMaterialData")
    ClCustomMaterialData saveClCustomMaterialData(ClCustomMaterialData clCustomMaterialData);
    @PostMapping("/batchSaveClCustomMaterialData")
    void batchSaveClCustomMaterialData(List<ClCustomMaterialData> clCustomMaterialDatas);
    @PostMapping("/findClCustomMaterialDataListByCondition")
    List<ClCustomMaterialData> findClCustomMaterialDataListByCondition(ClCustomMaterialData clCustomMaterialData);
    @PostMapping("/findOneClCustomMaterialDataByCondition")
    ClCustomMaterialData findOneClCustomMaterialDataByCondition(ClCustomMaterialData clCustomMaterialData);
    @PostMapping("/findClCustomMaterialDataCountByCondition")
    long findClCustomMaterialDataCountByCondition(ClCustomMaterialData clCustomMaterialData);
    @PostMapping("/updateClCustomMaterialData")
    void updateClCustomMaterialData(ClCustomMaterialData clCustomMaterialData);
    @PostMapping("/updateClCustomMaterialDataForAll")
    void updateClCustomMaterialDataForAll(ClCustomMaterialData clCustomMaterialData);
    @GetMapping("/deleteClCustomMaterialData/{id}")
    void deleteClCustomMaterialData(@PathVariable("id") String id);
    @PostMapping("/deleteClCustomMaterialDataByCondition")
    void deleteClCustomMaterialDataByCondition(ClCustomMaterialData clCustomMaterialData);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @PostMapping("/findClCustomMaterialDataMaxWeight")
    Long findClCustomMaterialDataMaxWeight(ClCustomMaterialData clCustomMaterialData);
    @PostMapping("/saveClCustomMaterialDataAndClWeight")
    void saveClCustomMaterialDataAndClWeight(ClCustomMaterialData clCustomMaterialData);

    @PostMapping("/findClCustomMaterialDataCountByConditionKong")
    long findClCustomMaterialDataCountByConditionKong(ClCustomMaterialData clCustomMaterialData);
    @PostMapping("/findClCustomMaterialDataListByConditionKong")
    List<ClCustomMaterialData> findClCustomMaterialDataListByConditionKong(ClCustomMaterialData clCustomMaterialData);
}
