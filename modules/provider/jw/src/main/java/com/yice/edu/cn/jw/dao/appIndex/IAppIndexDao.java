package com.yice.edu.cn.jw.dao.appIndex;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.appIndex.AppIndex;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IAppIndexDao {
    List<AppIndex> findAppIndexListByCondition(AppIndex appIndex);

    long findAppIndexCountByCondition(AppIndex appIndex);

    AppIndex findOneAppIndexByCondition(AppIndex appIndex);

    AppIndex findAppIndexById(@Param("id") String id);

    void saveAppIndex(AppIndex appIndex);

    void updateAppIndex(AppIndex appIndex);

    void deleteAppIndex(@Param("id") String id);

    void deleteAppIndexByCondition(AppIndex appIndex);

    void batchSaveAppIndex(List<AppIndex> appIndexs);

    void moveAppIndexes(List<AppIndex> appIndexes);

    List<AppIndex> findAppIndexListForSchool(@Param("schoolId") String schoolId,@Param("type") int type,@Param("parentId") String parentId);
}
