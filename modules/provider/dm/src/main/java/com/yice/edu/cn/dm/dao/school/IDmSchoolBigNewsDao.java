package com.yice.edu.cn.dm.dao.school;

import java.util.List;

import com.yice.edu.cn.common.pojo.dm.school.DmSchoolBigNews;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IDmSchoolBigNewsDao {
    List<DmSchoolBigNews> findDmSchoolBigNewsListByCondition(DmSchoolBigNews dmSchoolBigNews);

    DmSchoolBigNews findOneDmSchoolBigNewsByCondition(DmSchoolBigNews dmSchoolBigNews);

    long findDmSchoolBigNewsCountByCondition(DmSchoolBigNews dmSchoolBigNews);

    DmSchoolBigNews findDmSchoolBigNewsById(@Param("id") String id);

    void saveDmSchoolBigNews(DmSchoolBigNews dmSchoolBigNews);

    void updateDmSchoolBigNews(DmSchoolBigNews dmSchoolBigNews);

    void deleteDmSchoolBigNews(@Param("id") String id);

    void deleteDmSchoolBigNewsByCondition(DmSchoolBigNews dmSchoolBigNews);

    void batchSaveDmSchoolBigNews(List<DmSchoolBigNews> dmSchoolBigNewss);

    List<DmSchoolBigNews> findDmSchoolBigNewsListByactiveNameLike(DmSchoolBigNews dmSchoolBigNews);

    Long findDmSchoolBigNewsListByactiveNameLikeCount(DmSchoolBigNews dmSchoolBigNews);
}
