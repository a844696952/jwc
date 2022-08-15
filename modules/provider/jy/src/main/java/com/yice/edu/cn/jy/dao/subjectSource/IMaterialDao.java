package com.yice.edu.cn.jy.dao.subjectSource;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yice.edu.cn.common.pojo.jy.subjectSourse.Material;

@Mapper
public interface IMaterialDao {
    List<Material> findMaterialListByCondition(Material material);

    Material findOneMaterialByCondition(Material material);

    long findMaterialCountByCondition(Material material);

    Material findMaterialById(@Param("id") String id);

    void saveMaterial(Material material);

    void updateMaterial(Material material);

    void deleteMaterial(@Param("id") String id);

    void deleteMaterialByCondition(Material material);

    void batchSaveMaterial(List<Material> materials);
}
