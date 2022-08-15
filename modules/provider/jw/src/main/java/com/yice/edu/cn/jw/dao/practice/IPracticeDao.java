package com.yice.edu.cn.jw.dao.practice;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.practice.Practice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IPracticeDao {
    List<Practice> findPracticeListByCondition(Practice practice);

    List<Practice> findPracticeListByCondition1(Practice practice);

    List<Practice> findPracticeListByTeacherId(Practice practice);

    Practice findOnePracticeByCondition(Practice practice);

    long findPracticeCountByCondition(Practice practice);

    long findPracticeCountByCondition1(Practice practice);

    long findPracticeCountByTeacherId(Practice practice);

    Practice findPracticeById(@Param("id") String id);

    void savePractice(Practice practice);

    void updatePractice(Practice practice);

    void deletePractice(@Param("id") String id);

    void deletePracticeByCondition(Practice practice);

    void batchSavePractice(List<Practice> practices);
}
