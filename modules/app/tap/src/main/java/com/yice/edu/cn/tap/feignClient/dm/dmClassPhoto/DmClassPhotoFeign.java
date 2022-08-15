package com.yice.edu.cn.tap.feignClient.dm.dmClassPhoto;

import com.yice.edu.cn.common.pojo.dm.classes.DmClassPhoto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",path = "/dmClassPhoto")
public interface DmClassPhotoFeign {
    @GetMapping("/findDmClassPhotoById/{id}")
    DmClassPhoto findDmClassPhotoById(@PathVariable("id") String id);
    @PostMapping("/saveDmClassPhoto")
    DmClassPhoto saveDmClassPhoto(DmClassPhoto dmClassPhoto);
    @PostMapping("/findDmClassPhotoListByCondition")
    List<DmClassPhoto> findDmClassPhotoListByCondition(DmClassPhoto dmClassPhoto);
    @PostMapping("/findOneDmClassPhotoByCondition")
    DmClassPhoto findOneDmClassPhotoByCondition(DmClassPhoto dmClassPhoto);
    @PostMapping("/findDmClassPhotoCountByCondition")
    long findDmClassPhotoCountByCondition(DmClassPhoto dmClassPhoto);
    @PostMapping("/updateDmClassPhoto")
    void updateDmClassPhoto(DmClassPhoto dmClassPhoto);
    @GetMapping("/deleteDmClassPhoto/{id}")
    void deleteDmClassPhoto(@PathVariable("id") String id);
    @PostMapping("/deleteDmClassPhotoByCondition")
    void deleteDmClassPhotoByCondition(DmClassPhoto dmClassPhoto);
    @PostMapping("/batchDeleteDmClassPhoto")
    void batchDeleteDmClassPhoto(List<String> idlist);
}
