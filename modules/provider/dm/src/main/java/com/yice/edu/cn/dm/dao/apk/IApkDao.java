package com.yice.edu.cn.dm.dao.apk;

import java.util.List;

import com.yice.edu.cn.common.pojo.dm.classCard.Apk;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IApkDao {
    List<Apk> findApkListByCondition(Apk apk);

    long findApkCountByCondition(Apk apk);

    Apk findOneApkByCondition(Apk apk);

    Apk findApkById(@Param("id") String id);

    void saveApk(Apk apk);

    void updateApk(Apk apk);

    void deleteApk(@Param("id") String id);

    void deleteApkByCondition(Apk apk);

    void batchSaveApk(List<Apk> apks);
}
