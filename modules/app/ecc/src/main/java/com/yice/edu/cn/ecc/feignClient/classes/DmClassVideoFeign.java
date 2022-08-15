package com.yice.edu.cn.ecc.feignClient.classes;

import com.yice.edu.cn.common.pojo.dm.classes.DmClassVideo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "dmClassVideoFeign",path = "/dmClassVideo")
public interface DmClassVideoFeign {
    @GetMapping("/findDmClassVideoById/{id}")
    DmClassVideo findDmClassVideoById(@PathVariable("id") String id);
    @PostMapping("/saveDmClassVideo")
    DmClassVideo saveDmClassVideo(DmClassVideo dmClassVideo);
    @PostMapping("/findDmClassVideoListByCondition")
    List<DmClassVideo> findDmClassVideoListByCondition(DmClassVideo dmClassVideo);
    @PostMapping("/findOneDmClassVideoByCondition")
    DmClassVideo findOneDmClassVideoByCondition(DmClassVideo dmClassVideo);
    @PostMapping("/findDmClassVideoCountByCondition")
    long findDmClassVideoCountByCondition(DmClassVideo dmClassVideo);
    @PostMapping("/updateDmClassVideo")
    void updateDmClassVideo(DmClassVideo dmClassVideo);
    @GetMapping("/deleteDmClassVideo/{id}")
    void deleteDmClassVideo(@PathVariable("id") String id);
    @PostMapping("/deleteDmClassVideoByCondition")
    void deleteDmClassVideoByCondition(DmClassVideo dmClassVideo);
    @PostMapping("/batchDeleteDmClassVideo")
    void batchDeleteDmClassVideo(List<String> idlist);

}
