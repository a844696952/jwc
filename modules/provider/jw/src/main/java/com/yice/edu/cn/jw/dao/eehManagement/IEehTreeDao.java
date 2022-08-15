package com.yice.edu.cn.jw.dao.eehManagement;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.eehManagement.EehTree;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IEehTreeDao {
    List<EehTree> findEehTreeListByCondition(EehTree eehTree);

    long findEehTreeCountByCondition(EehTree eehTree);

    EehTree findOneEehTreeByCondition(EehTree eehTree);

    EehTree findEehTreeById(@Param("id") String id);

    void saveEehTree(EehTree eehTree);

    void updateEehTree(EehTree eehTree);

    void deleteEehTree(@Param("id") String id);

    void deleteEehTreeByCondition(EehTree eehTree);

    void batchSaveEehTree(List<EehTree> eehTrees);

    List<EehTree> findEehTreeByPid(@Param("id") String id);

    EehTree lookEehTreeNewById(@Param("id") String id);

    List<EehTree> findAllTreeMenu(EehTree eehTree);

    List<EehTree> findEehSchoolListNoCondition(EehTree eehTree);
}
