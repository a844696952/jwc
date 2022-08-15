package com.yice.edu.cn.dm.dao.classes;

import java.util.List;

import com.yice.edu.cn.common.pojo.dm.classes.DmClassPhoto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IDmClassPhotoDao {
    List<DmClassPhoto> findDmClassPhotoListByCondition(DmClassPhoto dmClassPhoto);

    DmClassPhoto findOneDmClassPhotoByCondition(DmClassPhoto dmClassPhoto);

    long findDmClassPhotoCountByCondition(DmClassPhoto dmClassPhoto);

    DmClassPhoto findDmClassPhotoById(@Param("id") String id);

    void saveDmClassPhoto(DmClassPhoto dmClassPhoto);

    void updateDmClassPhoto(DmClassPhoto dmClassPhoto);

    void deleteDmClassPhoto(@Param("id") String id);

    void deleteDmClassPhotoByCondition(DmClassPhoto dmClassPhoto);

    void batchSaveDmClassPhoto(List<DmClassPhoto> dmClassPhotos);

    void batchDeleteDmClassPhoto(List<String> idlist);
}
