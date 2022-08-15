package com.yice.edu.cn.tap.service.dm.dmLockScreenPhoto;

import com.yice.edu.cn.common.pojo.dm.dmLockScreenPhoto.DmLockScreenPhoto;
import com.yice.edu.cn.tap.feignClient.dm.dmLockScreenPhoto.DmLockScreenPhotoFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmLockScreenPhotoService {
    @Autowired
    private DmLockScreenPhotoFeign dmLockScreenPhotoFeign;

    public DmLockScreenPhoto findDmLockScreenPhotoById(String id) {
        return dmLockScreenPhotoFeign.findDmLockScreenPhotoById(id);
    }

    public DmLockScreenPhoto saveDmLockScreenPhoto(DmLockScreenPhoto dmLockScreenPhoto) {
        return dmLockScreenPhotoFeign.saveDmLockScreenPhoto(dmLockScreenPhoto);
    }

    public List<DmLockScreenPhoto> findDmLockScreenPhotoListByCondition(DmLockScreenPhoto dmLockScreenPhoto) {
        return dmLockScreenPhotoFeign.findDmLockScreenPhotoListByCondition(dmLockScreenPhoto);
    }

    public DmLockScreenPhoto findOneDmLockScreenPhotoByCondition(DmLockScreenPhoto dmLockScreenPhoto) {
        return dmLockScreenPhotoFeign.findOneDmLockScreenPhotoByCondition(dmLockScreenPhoto);
    }

    public long findDmLockScreenPhotoCountByCondition(DmLockScreenPhoto dmLockScreenPhoto) {
        return dmLockScreenPhotoFeign.findDmLockScreenPhotoCountByCondition(dmLockScreenPhoto);
    }

    public void updateDmLockScreenPhoto(DmLockScreenPhoto dmLockScreenPhoto) {
        dmLockScreenPhotoFeign.updateDmLockScreenPhoto(dmLockScreenPhoto);
    }

    public void deleteDmLockScreenPhoto(String id) {
        dmLockScreenPhotoFeign.deleteDmLockScreenPhoto(id);
    }

    public void deleteDmLockScreenPhotoByCondition(DmLockScreenPhoto dmLockScreenPhoto) {
        dmLockScreenPhotoFeign.deleteDmLockScreenPhotoByCondition(dmLockScreenPhoto);
    }
}
