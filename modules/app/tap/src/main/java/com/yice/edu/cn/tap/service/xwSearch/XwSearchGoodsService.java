package com.yice.edu.cn.tap.service.xwSearch;

import com.yice.edu.cn.common.pojo.xw.searchGoods.XwSearchGoods;
import com.yice.edu.cn.tap.feignClient.xwSearch.XwSearchGoodsFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XwSearchGoodsService {
    @Autowired
    private XwSearchGoodsFeign xwSearchGoodsFeign;

    public XwSearchGoods findXwSearchGoodsById(String id) {
        return xwSearchGoodsFeign.findXwSearchGoodsById(id);
    }



    public List<XwSearchGoods> findXwSearchGoodsListByCondition(XwSearchGoods xwSearchGoods) {
        return xwSearchGoodsFeign.findXwSearchGoodsListByCondition(xwSearchGoods);
    }

    public XwSearchGoods findOneXwSearchGoodsByCondition(XwSearchGoods xwSearchGoods) {
        return xwSearchGoodsFeign.findOneXwSearchGoodsByCondition(xwSearchGoods);
    }

    public long findXwSearchGoodsCountByCondition(XwSearchGoods xwSearchGoods) {
        return xwSearchGoodsFeign.findXwSearchGoodsCountByCondition(xwSearchGoods);
    }


}
