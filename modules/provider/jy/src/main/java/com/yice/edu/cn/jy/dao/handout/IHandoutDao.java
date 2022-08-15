package com.yice.edu.cn.jy.dao.handout;

import java.util.List;

import com.yice.edu.cn.common.pojo.jy.handout.Handout;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IHandoutDao {
    List<Handout> findHandoutListByCondition(Handout handout);

    Handout findOneHandoutByCondition(Handout handout);

    long findHandoutCountByCondition(Handout handout);

    Handout findHandoutById(@Param("id") String id);

    void saveHandout(Handout handout);

    void updateHandout(Handout handout);

    void deleteHandout(@Param("id") String id);

    void deleteHandoutByCondition(Handout handout);

    void batchSaveHandout(List<Handout> handouts);
    /**/
    List<Handout> findHandoutsByCondition2(Handout handout);
    long findHandoutCountByCondition2(Handout handout);

}
