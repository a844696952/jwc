package com.yice.edu.cn.xw.dao.doc;

import java.util.List;

import com.yice.edu.cn.common.pojo.xw.document.WritingLeader;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WritingLeaderDao {
    List<WritingLeader> findWritingLeaderListByCondition(WritingLeader writingLeader);

    WritingLeader findOneWritingLeaderByCondition(WritingLeader writingLeader);

    long findWritingLeaderCountByCondition(WritingLeader writingLeader);

    WritingLeader findWritingLeaderById(@Param("id") String id);

    void saveWritingLeader(WritingLeader writingLeader);

    void updateWritingLeader(WritingLeader writingLeader);

    void deleteWritingLeader(@Param("id") String id);

    void deleteWritingLeaderByCondition(WritingLeader writingLeader);

    void batchSaveWritingLeader(List<WritingLeader> writingLeaders);
}
