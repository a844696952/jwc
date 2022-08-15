package com.yice.edu.cn.yed.service.jy.subjectSource;


import java.util.List;

import com.esotericsoftware.minlog.Log;
import com.yice.edu.cn.yed.feignClient.jy.source21.Source21Feign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yice.edu.cn.common.pojo.jy.subjectSourse.Material;
import com.yice.edu.cn.yed.feignClient.jy.subjectSource.MaterialFeign;

@Service
public class MaterialService {
    @Autowired
    private MaterialFeign materialFeign;
    @Autowired
    private Source21Feign source21Feign;
    public Material findMaterialById(String id) {
        return materialFeign.findMaterialById(id);
    }

    public Material saveMaterial(Material material) {
        return materialFeign.saveMaterial(material);
    }

    public List<Material> findMaterialListByCondition(Material material) {
        return materialFeign.findMaterialListByCondition(material);
    }

    public Material findOneMaterialByCondition(Material material) {
        return materialFeign.findOneMaterialByCondition(material);
    }

    public long findMaterialCountByCondition(Material material) {
        return materialFeign.findMaterialCountByCondition(material);
    }

    public void updateMaterial(Material material) {
        materialFeign.updateMaterial(material);
    }

    public void deleteMaterial(String id) {
        materialFeign.deleteMaterial(id);
    }

    public Integer getCategorys(String id) {
        Integer c = source21Feign.getCategorys(id);
        Log.info(c+"");
        return c;
    }

    public void deleteMaterialByCondition(Material material) {
        materialFeign.deleteMaterialByCondition(material);
    }
}
