package com.yice.edu.cn.tap.service.subjectSource;


import java.util.List;
import java.util.stream.Collectors;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.ResourceVo;
import com.yice.edu.cn.common.pojo.jy.source21.model.common.Book;
import com.yice.edu.cn.tap.service.source21.Source21Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yice.edu.cn.common.pojo.jy.subjectSourse.Material;
import com.yice.edu.cn.tap.feignClient.subjectSource.MaterialFeign;

@Service
public class MaterialService {
    @Autowired
    private MaterialFeign materialFeign;
    @Autowired
    private Source21Service source21Service;

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

    /**
     * 查询教材的第二级别目录
     * 1、我们平台资源第二级别是获取教材
     * 2、21世纪平台是获取教材版本对应的年级册别
     * @param resourceVo
     * @return
     */
    public ResponseJson findMaterialsByCondition(ResourceVo resourceVo) {
        if(Constant.TOPIC_SOURCE.TWENTYONESHIJI.equals(resourceVo.getPlatform())){
            List<Book> books = source21Service.getBooksByVersion(resourceVo.getTempId());
            return new ResponseJson(books.stream().map(book -> {
                Material material = new Material();
                material.setId(String.valueOf(book.getBookId()));
                material.setName(book.getBookName());
                return material;
            }).collect(Collectors.toList()));
        }else{
            Material material = new Material();
            material.setSubjectMaterialId(resourceVo.getTempId());
            material.setPager(new Pager().setPaging(false).setIncludes("id","name"));
            List<Material> data=materialFeign.findMaterialListByCondition(material);
            return new ResponseJson(data);
        }
    }
}
