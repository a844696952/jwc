package com.yice.edu.cn.bmp.feignClient.parentMsg;

import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",path = "/dmClassCard")
public interface MyClassCardFeign {
    @PostMapping("/findOneDmClassCardByCondition")
    public DmClassCard findOneDmClassCardByCondition(DmClassCard dmClassCard);
}
