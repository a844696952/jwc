package com.yice.edu.cn.tap.service.dmClassPhoto;

import com.yice.edu.cn.common.pojo.dm.classes.DmClassPhoto;
import com.yice.edu.cn.tap.feignClient.dm.dmClassPhoto.DmClassPhotoFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmClassPhotoService {
    @Autowired
    private DmClassPhotoFeign dmClassPhotoFeign;

    public DmClassPhoto findDmClassPhotoById(String id) {
        return dmClassPhotoFeign.findDmClassPhotoById(id);
    }

    public DmClassPhoto saveDmClassPhoto(DmClassPhoto dmClassPhoto) {
        return dmClassPhotoFeign.saveDmClassPhoto(dmClassPhoto);
    }

    public List<DmClassPhoto> findDmClassPhotoListByCondition(DmClassPhoto dmClassPhoto) {
        return dmClassPhotoFeign.findDmClassPhotoListByCondition(dmClassPhoto);
    }

    public DmClassPhoto findOneDmClassPhotoByCondition(DmClassPhoto dmClassPhoto) {
        return dmClassPhotoFeign.findOneDmClassPhotoByCondition(dmClassPhoto);
    }

    public long findDmClassPhotoCountByCondition(DmClassPhoto dmClassPhoto) {
        return dmClassPhotoFeign.findDmClassPhotoCountByCondition(dmClassPhoto);
    }

    public void updateDmClassPhoto(DmClassPhoto dmClassPhoto) {
        dmClassPhotoFeign.updateDmClassPhoto(dmClassPhoto);
    }

    public void deleteDmClassPhoto(String id) {
        dmClassPhotoFeign.deleteDmClassPhoto(id);
    }

    public void deleteDmClassPhotoByCondition(DmClassPhoto dmClassPhoto) {
        dmClassPhotoFeign.deleteDmClassPhotoByCondition(dmClassPhoto);
    }
    public void batchDeleteDmClassPhoto(List<String> idlist){
        dmClassPhotoFeign.batchDeleteDmClassPhoto(idlist);
    }
}
