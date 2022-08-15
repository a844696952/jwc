package com.yice.edu.cn.jy.dao.collectivePlan;

import java.util.List;

import com.yice.edu.cn.common.pojo.jy.collectivePlan.JyPrepareLessonsDiscussReply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IJyPrepareLessonsDiscussReplyDao {
    List<JyPrepareLessonsDiscussReply> findJyPrepareLessonsDiscussReplyListByCondition(JyPrepareLessonsDiscussReply jyPrepareLessonsDiscussReply);

    long findJyPrepareLessonsDiscussReplyCountByCondition(JyPrepareLessonsDiscussReply jyPrepareLessonsDiscussReply);

    JyPrepareLessonsDiscussReply findOneJyPrepareLessonsDiscussReplyByCondition(JyPrepareLessonsDiscussReply jyPrepareLessonsDiscussReply);

    JyPrepareLessonsDiscussReply findJyPrepareLessonsDiscussReplyById(@Param("id") String id);

    void saveJyPrepareLessonsDiscussReply(JyPrepareLessonsDiscussReply jyPrepareLessonsDiscussReply);

    void updateJyPrepareLessonsDiscussReply(JyPrepareLessonsDiscussReply jyPrepareLessonsDiscussReply);

    void deleteJyPrepareLessonsDiscussReply(@Param("id") String id);

    void deleteJyPrepareLessonsDiscussReplyByCondition(JyPrepareLessonsDiscussReply jyPrepareLessonsDiscussReply);

    void batchSaveJyPrepareLessonsDiscussReply(List<JyPrepareLessonsDiscussReply> jyPrepareLessonsDiscussReplys);

    void deleteJyPrepareLessonsDiscussReplyByDiscussId(@Param("discussId") String discussId);

    List<JyPrepareLessonsDiscussReply> findJyPrepareLessonsDiscussReplyByDiscussId(@Param("discussId") String discussId);
}
