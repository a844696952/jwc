package com.yice.edu.cn.osp.service.xw.searchGoods;

import com.yice.edu.cn.common.pojo.xw.searchGoods.XwSearchGoods;
import com.yice.edu.cn.osp.feignClient.xw.searchGoods.XwSearchGoodsFeign;
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

    public XwSearchGoods saveXwSearchGoods(XwSearchGoods xwSearchGoods) {
        return xwSearchGoodsFeign.saveXwSearchGoods(xwSearchGoods);
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

    public void updateXwSearchGoods(XwSearchGoods xwSearchGoods) {
        xwSearchGoodsFeign.updateXwSearchGoods(xwSearchGoods);
    }

    public void deleteXwSearchGoods(String id) {
        xwSearchGoodsFeign.deleteXwSearchGoods(id);
    }

    public void deleteXwSearchGoodsByCondition(XwSearchGoods xwSearchGoods) {
        xwSearchGoodsFeign.deleteXwSearchGoodsByCondition(xwSearchGoods);
    }

    public void updateXwSearchGoodsReturn(XwSearchGoods xwSearchGoods) {
        xwSearchGoodsFeign.updateXwSearchGoodsReturn(xwSearchGoods);
    }
}
