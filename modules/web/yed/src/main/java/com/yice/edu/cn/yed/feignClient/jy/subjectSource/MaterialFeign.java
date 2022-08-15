package com.yice.edu.cn.yed.feignClient.jy.subjectSource;


import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.yice.edu.cn.common.pojo.jy.subjectSourse.Material;

@FeignClient(value="jy",contextId = "materialFeign",path = "/material")
public interface MaterialFeign {
    @GetMapping("/findMaterialById/{id}")
    Material findMaterialById(@PathVariable("id") String id);
    @PostMapping("/saveMaterial")
    Material saveMaterial(Material material);
    @PostMapping("/findMaterialListByCondition")
    List<Material> findMaterialListByCondition(Material material);
    @PostMapping("/findOneMaterialByCondition")
    Material findOneMaterialByCondition(Material material);
    @PostMapping("/findMaterialCountByCondition")
    long findMaterialCountByCondition(Material material);
    @PostMapping("/updateMaterial")
    void updateMaterial(Material material);
    @GetMapping("/deleteMaterial/{id}")
    void deleteMaterial(@PathVariable("id") String id);
    @PostMapping("/deleteMaterialByCondition")
    void deleteMaterialByCondition(Material material);
}
