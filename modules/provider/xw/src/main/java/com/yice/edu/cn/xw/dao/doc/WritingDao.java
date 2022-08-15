package com.yice.edu.cn.xw.dao.doc;

import java.util.List;

import com.yice.edu.cn.common.pojo.xw.document.Writing;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WritingDao {
    List<Writing> findWritingListByCondition(Writing writing);

    Writing findOneWritingByCondition(Writing writing);

    long findWritingCountByCondition(Writing writing);

    Writing findWritingById(@Param("id") String id);

    void saveWriting(Writing writing);

    void updateWriting(Writing writing);

    void deleteWriting(@Param("id") String id);

    void deleteWritingByCondition(Writing writing);

    void batchSaveWriting(List<Writing> writings);
}
