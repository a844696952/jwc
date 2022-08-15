package com.yice.edu.cn.tap.feignClient.xwSearch;

import com.yice.edu.cn.common.pojo.xw.searchGoods.XwSearchGoods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",path = "/xwSearchGoods")
public interface XwSearchGoodsFeign {
    @GetMapping("/findXwSearchGoodsById/{id}")
    XwSearchGoods findXwSearchGoodsById(@PathVariable("id") String id);
    @PostMapping("/findXwSearchGoodsListByCondition")
    List<XwSearchGoods> findXwSearchGoodsListByCondition(XwSearchGoods xwSearchGoods);
    @PostMapping("/findOneXwSearchGoodsByCondition")
    XwSearchGoods findOneXwSearchGoodsByCondition(XwSearchGoods xwSearchGoods);
    @PostMapping("/findXwSearchGoodsCountByCondition")
    long findXwSearchGoodsCountByCondition(XwSearchGoods xwSearchGoods);

}
