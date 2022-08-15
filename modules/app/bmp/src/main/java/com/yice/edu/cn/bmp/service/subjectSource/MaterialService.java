package com.yice.edu.cn.bmp.service.subjectSource;


import com.yice.edu.cn.bmp.feignClient.subjectSource.MaterialFeign;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialService {
    @Autowired
    private MaterialFeign materialFeign;

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

    public void deleteMaterialByCondition(Material material) {
        materialFeign.deleteMaterialByCondition(material);
    }
}
