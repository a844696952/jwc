package com.yice.edu.cn.osp.feignClient.dm.dmLockScreenPhoto;

import com.yice.edu.cn.common.pojo.dm.dmLockScreenPhoto.DmLockScreenPhoto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "dmLockScreenPhotoFeign",path = "/dmLockScreenPhoto")
public interface DmLockScreenPhotoFeign {
    @GetMapping("/findDmLockScreenPhotoById/{id}")
    DmLockScreenPhoto findDmLockScreenPhotoById(@PathVariable("id") String id);
    @PostMapping("/saveDmLockScreenPhoto")
    DmLockScreenPhoto saveDmLockScreenPhoto(DmLockScreenPhoto dmLockScreenPhoto);
    @PostMapping("/findDmLockScreenPhotoListByCondition")
    List<DmLockScreenPhoto> findDmLockScreenPhotoListByCondition(DmLockScreenPhoto dmLockScreenPhoto);
    @PostMapping("/findOneDmLockScreenPhotoByCondition")
    DmLockScreenPhoto findOneDmLockScreenPhotoByCondition(DmLockScreenPhoto dmLockScreenPhoto);
    @PostMapping("/findDmLockScreenPhotoCountByCondition")
    long findDmLockScreenPhotoCountByCondition(DmLockScreenPhoto dmLockScreenPhoto);
    @PostMapping("/updateDmLockScreenPhoto")
    void updateDmLockScreenPhoto(DmLockScreenPhoto dmLockScreenPhoto);
    @GetMapping("/deleteDmLockScreenPhoto/{id}")
    void deleteDmLockScreenPhoto(@PathVariable("id") String id);
    @PostMapping("/deleteDmLockScreenPhotoByCondition")
    void deleteDmLockScreenPhotoByCondition(DmLockScreenPhoto dmLockScreenPhoto);

    @GetMapping("/updateCurrentScreenPhoto/{id}")
    void updateCurrentScreenPhoto(@PathVariable("id") String id);

    @GetMapping("/cancelCurrentScreenPhoto/{id}")
    void cancelCurrentScreenPhoto(@PathVariable("id") String id);

    @PostMapping("/batchdelete")
    void batchdelete(DmLockScreenPhoto dmLockScreenPhoto);
}
