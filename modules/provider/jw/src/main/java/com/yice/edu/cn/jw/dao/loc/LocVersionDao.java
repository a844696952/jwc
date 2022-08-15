package com.yice.edu.cn.jw.dao.loc;

import java.util.List;

import com.yice.edu.cn.common.pojo.loc.LocVersion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LocVersionDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<LocVersion> findLocVersionListByCondition(LocVersion locVersion);

    long findLocVersionCountByCondition(LocVersion locVersion);

    LocVersion findOneLocVersionByCondition(LocVersion locVersion);

    LocVersion findLocVersionById(@Param("id") String id);

    void saveLocVersion(LocVersion locVersion);

    void updateLocVersion(LocVersion locVersion);

    void updateLocVersionForAll(LocVersion locVersion);

    void deleteLocVersion(@Param("id") String id);

    void deleteLocVersionByCondition(LocVersion locVersion);

    void batchSaveLocVersion(List<LocVersion> locVersions);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    long findVersionNameRepetition(LocVersion locVersion);
}
