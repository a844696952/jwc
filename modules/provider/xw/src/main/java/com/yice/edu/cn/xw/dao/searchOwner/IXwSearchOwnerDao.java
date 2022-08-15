package com.yice.edu.cn.xw.dao.searchOwner;

import com.yice.edu.cn.common.pojo.xw.searchOwner.XwSearchOwner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IXwSearchOwnerDao {
    List<XwSearchOwner> findXwSearchOwnerListByCondition(XwSearchOwner xwSearchOwner);

    XwSearchOwner findOneXwSearchOwnerByCondition(XwSearchOwner xwSearchOwner);

    long findXwSearchOwnerCountByCondition(XwSearchOwner xwSearchOwner);

    XwSearchOwner findXwSearchOwnerById(@Param("id") String id);

    void saveXwSearchOwner(XwSearchOwner xwSearchOwner);

    void updateXwSearchOwner(XwSearchOwner xwSearchOwner);

    void deleteXwSearchOwner(@Param("id") String id);

    void deleteXwSearchOwnerByCondition(XwSearchOwner xwSearchOwner);

    void batchSaveXwSearchOwner(List<XwSearchOwner> xwSearchOwners);
}
