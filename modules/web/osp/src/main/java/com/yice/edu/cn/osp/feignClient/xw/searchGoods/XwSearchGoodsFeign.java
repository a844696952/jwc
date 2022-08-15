package com.yice.edu.cn.osp.feignClient.xw.searchGoods;

import com.yice.edu.cn.common.pojo.xw.searchGoods.XwSearchGoods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "xwSearchGoodsFeign",path = "/xwSearchGoods")
public interface XwSearchGoodsFeign {
    @GetMapping("/findXwSearchGoodsById/{id}")
    XwSearchGoods findXwSearchGoodsById(@PathVariable("id") String id);
    @PostMapping("/saveXwSearchGoods")
    XwSearchGoods saveXwSearchGoods(XwSearchGoods xwSearchGoods);
    @PostMapping("/findXwSearchGoodsListByCondition")
    List<XwSearchGoods> findXwSearchGoodsListByCondition(XwSearchGoods xwSearchGoods);
    @PostMapping("/findOneXwSearchGoodsByCondition")
    XwSearchGoods findOneXwSearchGoodsByCondition(XwSearchGoods xwSearchGoods);
    @PostMapping("/findXwSearchGoodsCountByCondition")
    long findXwSearchGoodsCountByCondition(XwSearchGoods xwSearchGoods);
    @PostMapping("/updateXwSearchGoods")
    void updateXwSearchGoods(XwSearchGoods xwSearchGoods);
    @GetMapping("/deleteXwSearchGoods/{id}")
    void deleteXwSearchGoods(@PathVariable("id") String id);
    @PostMapping("/deleteXwSearchGoodsByCondition")
    void deleteXwSearchGoodsByCondition(XwSearchGoods xwSearchGoods);

    @PostMapping("/updateXwSearchGoodsReturn")
    void updateXwSearchGoodsReturn(XwSearchGoods xwSearchGoods);

}
