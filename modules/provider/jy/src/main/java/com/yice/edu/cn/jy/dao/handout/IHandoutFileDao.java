package com.yice.edu.cn.jy.dao.handout;

import java.util.List;

import com.yice.edu.cn.common.pojo.jy.handout.HandoutFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IHandoutFileDao {
    List<HandoutFile> findHandoutFileListByCondition(HandoutFile handoutFile);

    HandoutFile findOneHandoutFileByCondition(HandoutFile handoutFile);

    long findHandoutFileCountByCondition(HandoutFile handoutFile);

    HandoutFile findHandoutFileById(@Param("id") String id);

    void saveHandoutFile(HandoutFile handoutFile);

    void updateHandoutFile(HandoutFile handoutFile);

    void deleteHandoutFile(@Param("id") String id);

    void deleteHandoutFileByCondition(HandoutFile handoutFile);

    void batchSaveHandoutFile(List<HandoutFile> handoutFiles);
}
