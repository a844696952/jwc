package com.yice.edu.cn.xw.dao.searchGoods;

import com.yice.edu.cn.common.pojo.xw.searchGoods.XwSearchGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IXwSearchGoodsDao {
    List<XwSearchGoods> findXwSearchGoodsListByCondition(XwSearchGoods xwSearchGoods);

    XwSearchGoods findOneXwSearchGoodsByCondition(XwSearchGoods xwSearchGoods);

    long findXwSearchGoodsCountByCondition(XwSearchGoods xwSearchGoods);

    XwSearchGoods findXwSearchGoodsById(@Param("id") String id);

    void saveXwSearchGoods(XwSearchGoods xwSearchGoods);

    void updateXwSearchGoods(XwSearchGoods xwSearchGoods);

    void deleteXwSearchGoods(@Param("id") String id);

    void deleteXwSearchGoodsByCondition(XwSearchGoods xwSearchGoods);

    void batchSaveXwSearchGoods(List<XwSearchGoods> xwSearchGoodss);

    void updateXwSearchGoodsReturn(XwSearchGoods xwSearchGoods);

}
