package com.yice.edu.cn.osp.feignClient.dm.schoolActive;

import com.yice.edu.cn.common.pojo.dm.schoolActive.DmActivityInfo;
import com.yice.edu.cn.common.pojo.dm.schoolActive.ExportActiveByClass;
import com.yice.edu.cn.common.pojo.dm.schoolActive.ExportActiveByItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(value="dm",contextId = "dmActivityInfoFeign",path = "/dmActivityInfo")
public interface DmActivityInfoFeign {
    @GetMapping("/findDmActivityInfoById/{id}")
    DmActivityInfo findDmActivityInfoById(@PathVariable("id") String id);
    @PostMapping("/saveDmActivityInfo")
    void saveDmActivityInfo(DmActivityInfo dmActivityInfo);
    @PostMapping("/findDmActivityInfoListByCondition")
    List<DmActivityInfo> findDmActivityInfoListByCondition(DmActivityInfo dmActivityInfo);
    @PostMapping("/findOneDmActivityInfoByCondition")
    DmActivityInfo findOneDmActivityInfoByCondition(DmActivityInfo dmActivityInfo);
    @PostMapping("/findDmActivityInfoCountByCondition")
    long findDmActivityInfoCountByCondition(DmActivityInfo dmActivityInfo);
    @PostMapping("/updateDmActivityInfo")
    void updateDmActivityInfo(DmActivityInfo dmActivityInfo);
    @GetMapping("/deleteDmActivityInfo/{id}")
    void deleteDmActivityInfo(@PathVariable("id") String id);
    @PostMapping("/deleteDmActivityInfoByCondition")
    void deleteDmActivityInfoByCondition(DmActivityInfo dmActivityInfo);
    @GetMapping("/findDmActivityInfoByActivityId/{activityId}")
    DmActivityInfo findDmActivityInfoByActivityId(@PathVariable("activityId")String activityId);
    @PostMapping("/checkGradeSingUp")
    boolean checkGradeSingUp(DmActivityInfo dmActivityInfo);
    @GetMapping("/checkItemSingUp/{itemId}")
    Map checkItemSingUp(@PathVariable("itemId")  String itemId);
    @PostMapping("/findItemByActiveId")
    ExportActiveByItem findItemByActiveId(@RequestBody DmActivityInfo dmActivityInfo);
    @PostMapping("/findClassItemByActiveId")
    ExportActiveByClass findClassItemByActiveId(@RequestBody DmActivityInfo dmActivityInfo);

}
