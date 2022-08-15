package com.yice.edu.cn.xw.dao.xwClassifiedManagement;

import java.util.List;

import com.yice.edu.cn.common.pojo.xw.xwClassifiedManagement.XwClassifiedManagement;
import com.yice.edu.cn.common.pojo.xw.xwRegulatoryFramework.XwRegulatoryFramework;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IXwClassifiedManagementDao {
    List<XwClassifiedManagement> findXwClassifiedManagementListByCondition(XwClassifiedManagement xwClassifiedManagement);

    XwClassifiedManagement findOneXwClassifiedManagementByCondition(XwClassifiedManagement xwClassifiedManagement);

    long findXwClassifiedManagementCountByCondition(XwClassifiedManagement xwClassifiedManagement);

    XwClassifiedManagement findXwClassifiedManagementById(@Param("id") String id);

    void saveXwClassifiedManagement(XwClassifiedManagement xwClassifiedManagement);

    void updateXwClassifiedManagement(XwClassifiedManagement xwClassifiedManagement);

    void deleteXwClassifiedManagement(@Param("id") String id);

    void deleteXwClassifiedManagementByCondition(XwClassifiedManagement xwClassifiedManagement);

    void batchSaveXwClassifiedManagement(List<XwClassifiedManagement> xwClassifiedManagements);


    List<XwClassifiedManagement> findXwClassifiedManagementListByCondition2(XwClassifiedManagement xwClassifiedManagement);

    long findXwClassifiedManagementCountByCondition2(XwClassifiedManagement xwClassifiedManagement);

    long findCountByIdForDelete(XwRegulatoryFramework xwRegulatoryFramework);
}
