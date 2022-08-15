package com.yice.edu.cn.jy.dao.collectivePlan;

import java.util.List;

import com.yice.edu.cn.common.pojo.jy.collectivePlan.JyPrepareLessonsDiscuss;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IJyPrepareLessonsDiscussDao {
    List<JyPrepareLessonsDiscuss> findJyPrepareLessonsDiscussListByCondition(JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss);

    JyPrepareLessonsDiscuss findOneJyPrepareLessonsDiscussByCondition(JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss);

    long findJyPrepareLessonsDiscussCountByCondition(JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss);

    JyPrepareLessonsDiscuss findJyPrepareLessonsDiscussById(@Param("id") String id);

    void saveJyPrepareLessonsDiscuss(JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss);

    void updateJyPrepareLessonsDiscuss(JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss);

    void deleteJyPrepareLessonsDiscuss(@Param("id") String id);

    void deleteJyPrepareLessonsDiscussByCondition(JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss);

    void batchSaveJyPrepareLessonsDiscuss(List<JyPrepareLessonsDiscuss> jyPrepareLessonsDiscusss);
}
