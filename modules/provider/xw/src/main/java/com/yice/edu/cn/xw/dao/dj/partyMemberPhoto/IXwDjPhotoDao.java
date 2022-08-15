package com.yice.edu.cn.xw.dao.dj.partyMemberPhoto;

import com.yice.edu.cn.common.pojo.xw.dj.partyMerberPhoto.XwDjPhoto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IXwDjPhotoDao {
    List<XwDjPhoto> findXwDjPhotoListByCondition(XwDjPhoto xwDjPhoto);

    long findXwDjPhotoCountByCondition(XwDjPhoto xwDjPhoto);

    XwDjPhoto findOneXwDjPhotoByCondition(XwDjPhoto xwDjPhoto);

    XwDjPhoto findXwDjPhotoById(@Param("id") String id);

    void saveXwDjPhoto(XwDjPhoto xwDjPhoto);

    void updateXwDjPhoto(XwDjPhoto xwDjPhoto);

    void deleteXwDjPhoto(@Param("id") String id);

    void deleteXwDjPhotoByCondition(XwDjPhoto xwDjPhoto);

    void batchSaveXwDjPhoto(List<XwDjPhoto> xwDjPhotos);

    XwDjPhoto findXwDjPhotoByIdLeft(@Param("id") String id);
}
