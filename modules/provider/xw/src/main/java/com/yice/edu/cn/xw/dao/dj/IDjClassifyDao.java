package com.yice.edu.cn.xw.dao.dj;

import java.util.List;

import com.yice.edu.cn.common.pojo.xw.dj.DjClassify;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IDjClassifyDao {
    List<DjClassify> findDjClassifyListByCondition(DjClassify djClassify);

    long findDjClassifyCountByCondition(DjClassify djClassify);

    DjClassify findOneDjClassifyByCondition(DjClassify djClassify);

    DjClassify findDjClassifyById(@Param("id") String id);

    void saveDjClassify(DjClassify djClassify);

    void updateDjClassify(DjClassify djClassify);

    void deleteDjClassify(@Param("id") String id);

    void deleteDjClassifyByCondition(DjClassify djClassify);

    void batchSaveDjClassify(List<DjClassify> djClassifys);
    /**
     * 根据类型 查询 下拉框（公用）
     * @param djClassify
     * @return
     */
    List<DjClassify> selectClassifyListByType(DjClassify djClassify);
    /**
     * 查询该类型下拉框是否已经存在
     */
    List<DjClassify> findClassifyListByType(DjClassify djClassify);
}
