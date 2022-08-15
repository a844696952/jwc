package com.yice.edu.cn.dm.dao.dmLockScreenPhoto;

import com.yice.edu.cn.common.pojo.dm.dmLockScreenPhoto.DmLockScreenPhoto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IDmLockScreenPhotoDao {
    List<DmLockScreenPhoto> findDmLockScreenPhotoListByCondition(DmLockScreenPhoto dmLockScreenPhoto);

    long findDmLockScreenPhotoCountByCondition(DmLockScreenPhoto dmLockScreenPhoto);

    DmLockScreenPhoto findOneDmLockScreenPhotoByCondition(DmLockScreenPhoto dmLockScreenPhoto);

    DmLockScreenPhoto findDmLockScreenPhotoById(@Param("id") String id);

    void saveDmLockScreenPhoto(DmLockScreenPhoto dmLockScreenPhoto);

    void updateDmLockScreenPhoto(DmLockScreenPhoto dmLockScreenPhoto);

    void deleteDmLockScreenPhoto(@Param("id") String id);

    void deleteDmLockScreenPhotoByCondition(DmLockScreenPhoto dmLockScreenPhoto);

    void batchSaveDmLockScreenPhoto(List<DmLockScreenPhoto> dmLockScreenPhotos);

    void updateStatus();

    void updateStatusById(@Param("id") String id);

    void cancelCurrentScreenPhoto(@Param("id") String id);

    void batchdelete(DmLockScreenPhoto dmLockScreenPhoto);

}
