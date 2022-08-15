package com.yice.edu.cn.dm.dao.classes;

import java.util.List;

import com.yice.edu.cn.common.pojo.dm.classes.DmClassVideo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IDmClassVideoDao {
    List<DmClassVideo> findDmClassVideoListByCondition(DmClassVideo dmClassVideo);

    long findDmClassVideoCountByCondition(DmClassVideo dmClassVideo);

    DmClassVideo findOneDmClassVideoByCondition(DmClassVideo dmClassVideo);

    DmClassVideo findDmClassVideoById(@Param("id") String id);

    void saveDmClassVideo(DmClassVideo dmClassVideo);

    void updateDmClassVideo(DmClassVideo dmClassVideo);

    void deleteDmClassVideo(@Param("id") String id);

    void deleteDmClassVideoByCondition(DmClassVideo dmClassVideo);

    void batchSaveDmClassVideo(List<DmClassVideo> dmClassVideos);

    void batchDeleteDmClassVideo(List<String> idlist);
}
