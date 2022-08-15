package com.yice.edu.cn.jw.dao.pen;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.pen.Pen;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IPenDao {
    List<Pen> findPenListByCondition(Pen pen);

    long findPenCountByCondition(Pen pen);

    Pen findOnePenByCondition(Pen pen);

    Pen findPenById(@Param("id") String id);

    void savePen(Pen pen);

    void updatePen(Pen pen);

    void deletePen(@Param("id") String id);

    void deletePenByCondition(Pen pen);

    void batchSavePen(List<Pen> pens);
}
