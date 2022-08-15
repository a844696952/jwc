package com.yice.edu.cn.osp.feignClient.xw.clCustomMaterial;

import com.yice.edu.cn.common.pojo.xw.clCustomMaterial.ClCustomMaterial;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "clCustomMaterialFeign",path = "/clCustomMaterial")
public interface ClCustomMaterialFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findClCustomMaterialById/{id}")
    ClCustomMaterial findClCustomMaterialById(@PathVariable("id") String id);
    @PostMapping("/saveClCustomMaterial")
    ClCustomMaterial saveClCustomMaterial(ClCustomMaterial clCustomMaterial);
    @PostMapping("/batchSaveClCustomMaterial")
    void batchSaveClCustomMaterial(List<ClCustomMaterial> clCustomMaterials);
    @PostMapping("/findClCustomMaterialListByCondition")
    List<ClCustomMaterial> findClCustomMaterialListByCondition(ClCustomMaterial clCustomMaterial);
    @PostMapping("/findOneClCustomMaterialByCondition")
    ClCustomMaterial findOneClCustomMaterialByCondition(ClCustomMaterial clCustomMaterial);
    @PostMapping("/findClCustomMaterialCountByCondition")
    long findClCustomMaterialCountByCondition(ClCustomMaterial clCustomMaterial);
    @PostMapping("/updateClCustomMaterial")
    void updateClCustomMaterial(ClCustomMaterial clCustomMaterial);
    @PostMapping("/updateClCustomMaterialForAll")
    void updateClCustomMaterialForAll(ClCustomMaterial clCustomMaterial);
    @GetMapping("/deleteClCustomMaterial/{id}")
    void deleteClCustomMaterial(@PathVariable("id") String id);
    @PostMapping("/deleteClCustomMaterialByCondition")
    void deleteClCustomMaterialByCondition(ClCustomMaterial clCustomMaterial);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @PostMapping("/findClCustomMaterialMaxWeight")
    Long findClCustomMaterialMaxWeight(ClCustomMaterial clCustomMaterial);
    @PostMapping("/findClCustomMaterialListByConditionKong")
    List<ClCustomMaterial> findClCustomMaterialListByConditionKong(ClCustomMaterial clCustomMaterial);

    @PostMapping("/saveClCustomMaterialDataAndClWeight")
    void saveClCustomMaterialDataAndClWeight(ClCustomMaterial clCustomMaterial);
}
