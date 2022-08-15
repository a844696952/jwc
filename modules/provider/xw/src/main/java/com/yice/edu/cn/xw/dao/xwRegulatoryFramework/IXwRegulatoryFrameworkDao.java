package com.yice.edu.cn.xw.dao.xwRegulatoryFramework;

import java.util.List;

import com.yice.edu.cn.common.pojo.xw.xwRegulatoryFramework.XwRegulatoryFramework;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IXwRegulatoryFrameworkDao {
    List<XwRegulatoryFramework> findXwRegulatoryFrameworkListByCondition(XwRegulatoryFramework xwRegulatoryFramework);

    XwRegulatoryFramework findOneXwRegulatoryFrameworkByCondition(XwRegulatoryFramework xwRegulatoryFramework);

    long findXwRegulatoryFrameworkCountByCondition(XwRegulatoryFramework xwRegulatoryFramework);

    XwRegulatoryFramework findXwRegulatoryFrameworkById(@Param("id") String id);

    void saveXwRegulatoryFramework(XwRegulatoryFramework xwRegulatoryFramework);

    void updateXwRegulatoryFramework(XwRegulatoryFramework xwRegulatoryFramework);

    void deleteXwRegulatoryFramework(@Param("id") String id);

    void deleteXwRegulatoryFrameworkByCondition(XwRegulatoryFramework xwRegulatoryFramework);

    void batchSaveXwRegulatoryFramework(List<XwRegulatoryFramework> xwRegulatoryFrameworks);
}
