package com.yice.edu.cn.osp.feignClient.dm.ycVeriface;

import com.yice.edu.cn.common.pojo.dm.ycVeriface.factory.YcVerifaceFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "ycVerifaceFactoryFeign",path = "/ycVerifaceFactory")
public interface YcVerifaceFactoryFeign {
    @GetMapping("/findYcVerifaceFactoryById/{id}")
    YcVerifaceFactory findYcVerifaceFactoryById(@PathVariable("id") String id);
    @PostMapping("/saveYcVerifaceFactory")
    YcVerifaceFactory saveYcVerifaceFactory(YcVerifaceFactory ycVerifaceFactory);
    @PostMapping("/findYcVerifaceFactoryListByCondition")
    List<YcVerifaceFactory> findYcVerifaceFactoryListByCondition(YcVerifaceFactory ycVerifaceFactory);
    @PostMapping("/findOneYcVerifaceFactoryByCondition")
    YcVerifaceFactory findOneYcVerifaceFactoryByCondition(YcVerifaceFactory ycVerifaceFactory);
    @PostMapping("/findYcVerifaceFactoryCountByCondition")
    long findYcVerifaceFactoryCountByCondition(YcVerifaceFactory ycVerifaceFactory);
    @PostMapping("/updateYcVerifaceFactory")
    void updateYcVerifaceFactory(YcVerifaceFactory ycVerifaceFactory);
    @GetMapping("/deleteYcVerifaceFactory/{id}")
    void deleteYcVerifaceFactory(@PathVariable("id") String id);
    @PostMapping("/deleteYcVerifaceFactoryByCondition")
    void deleteYcVerifaceFactoryByCondition(YcVerifaceFactory ycVerifaceFactory);
}
