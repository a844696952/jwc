package com.yice.edu.cn.tap.feignClient.dm.schoolActive;

import com.yice.edu.cn.common.pojo.dm.schoolActive.DmActivityItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",path = "/dmActivityItem")
public interface DmActivityItemFeign {
    @GetMapping("/findDmActivityItemById/{id}")
    DmActivityItem findDmActivityItemById(@PathVariable("id") String id);
    @PostMapping("/saveDmActivityItem")
    DmActivityItem saveDmActivityItem(DmActivityItem dmActivityItem);
    @PostMapping("/findDmActivityItemListByCondition")
    List<DmActivityItem> findDmActivityItemListByCondition(DmActivityItem dmActivityItem);
    @PostMapping("/findOneDmActivityItemByCondition")
    DmActivityItem findOneDmActivityItemByCondition(DmActivityItem dmActivityItem);
    @PostMapping("/findDmActivityItemCountByCondition")
    long findDmActivityItemCountByCondition(DmActivityItem dmActivityItem);
    @PostMapping("/updateDmActivityItem")
    void updateDmActivityItem(DmActivityItem dmActivityItem);
    @GetMapping("/deleteDmActivityItem/{id}")
    void deleteDmActivityItem(@PathVariable("id") String id);
    @PostMapping("/deleteDmActivityItemByCondition")
    void deleteDmActivityItemByCondition(DmActivityItem dmActivityItem);
    @PostMapping("/findDmActivityItemsByCondition")
    List<DmActivityItem> findDmActivityItemsByCondition(DmActivityItem dmActivityItem);
    @PostMapping("/saveDmActivityItemAndPeople")
    void saveDmActivityItemAndPeople(DmActivityItem dmActivityItem);
    @PostMapping("/updateDmActivityItemAndPeople")
    void updateDmActivityItemAndPeople(DmActivityItem dmActivityItem);
}