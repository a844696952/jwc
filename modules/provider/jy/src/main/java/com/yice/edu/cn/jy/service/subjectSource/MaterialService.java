package com.yice.edu.cn.jy.service.subjectSource;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.ResourceVo;
import com.yice.edu.cn.common.pojo.jy.source21.model.common.Book;
import com.yice.edu.cn.jy.source21.service.Source21Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.Material;
import com.yice.edu.cn.jy.dao.subjectSource.IMaterialDao;

@Service
public class MaterialService {
    @Autowired
    private IMaterialDao materialDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private Source21Service source21Service;

    @Transactional(readOnly = true)
    public Material findMaterialById(String id) {
        return materialDao.findMaterialById(id);
    }
    @Transactional
    public void saveMaterial(Material material) {
        material.setId(Optional.ofNullable(material.getId()).orElse(sequenceId.nextId()));
        materialDao.saveMaterial(material);
    }
    @Transactional(readOnly = true)
    public List<Material> findMaterialListByCondition(Material material) {
        return materialDao.findMaterialListByCondition(material);
    }
    @Transactional(readOnly = true)
    public Material findOneMaterialByCondition(Material material) {
        return materialDao.findOneMaterialByCondition(material);
    }
    @Transactional(readOnly = true)
    public long findMaterialCountByCondition(Material material) {
        return materialDao.findMaterialCountByCondition(material);
    }
    @Transactional
    public void updateMaterial(Material material) {
        materialDao.updateMaterial(material);
    }
    @Transactional
    public void deleteMaterial(String id) {
        materialDao.deleteMaterial(id);
    }
    @Transactional
    public void deleteMaterialByCondition(Material material) {
        materialDao.deleteMaterialByCondition(material);
    }
    @Transactional
    public void batchSaveMaterial(List<Material> materials){
        materials.forEach(material -> material.setId(sequenceId.nextId()));
        materialDao.batchSaveMaterial(materials);
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
            List<Material> data=materialDao.findMaterialListByCondition(material);
            return new ResponseJson(data);
        }
    }
}
