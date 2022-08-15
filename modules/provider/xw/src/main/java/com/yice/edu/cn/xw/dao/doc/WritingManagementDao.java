package com.yice.edu.cn.xw.dao.doc;

import java.util.List;

import com.yice.edu.cn.common.pojo.xw.document.Writing;
import com.yice.edu.cn.common.pojo.xw.document.WritingManagement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WritingManagementDao {
    List<WritingManagement> findWritingManagementListByCondition(WritingManagement writingManagement);

    WritingManagement findOneWritingManagementByCondition(WritingManagement writingManagement);

    long findWritingManagementCountByCondition(WritingManagement writingManagement);

    WritingManagement findWritingManagementById(@Param("id") String id);

    void saveWritingManagement(WritingManagement writingManagement);

    void updateWritingManagement(WritingManagement writingManagement);

    void deleteWritingManagement(@Param("id") String id);

    void deleteWritingManagementByCondition(WritingManagement writingManagement);

    void batchSaveWritingManagement(List<WritingManagement> writingManagements);

    List<Writing> findWritingAndWritingManagement(Writing writing);

    long findWritingAndWritingManagementLong(Writing writing);
}
