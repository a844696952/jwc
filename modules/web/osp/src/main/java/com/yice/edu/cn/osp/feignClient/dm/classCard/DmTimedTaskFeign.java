package com.yice.edu.cn.osp.feignClient.dm.classCard;

import com.yice.edu.cn.common.pojo.dm.classCard.DmTimedTask;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "dmTimedTaskFeign",path = "/dmTimedTask")
public interface DmTimedTaskFeign {
    @GetMapping("/findDmTimedTaskById/{id}")
    DmTimedTask findDmTimedTaskById(@PathVariable("id") String id);
    @PostMapping("/saveOrUpdateDmTimedTaskAll")
    DmTimedTask saveOrUpdateDmTimedTaskAll(DmTimedTask dmTimedTask);
    @PostMapping("/saveOrUpdateDmTimedTask")
    DmTimedTask saveOrUpdateDmTimedTask(DmTimedTask dmTimedTask);
    @PostMapping("/findDmTimedTaskListByCondition")
    List<DmTimedTask> findDmTimedTaskListByCondition(DmTimedTask dmTimedTask);
    @PostMapping("/findOneDmTimedTaskByCondition")
    DmTimedTask findOneDmTimedTaskByCondition(DmTimedTask dmTimedTask);
    @PostMapping("/findDmTimedTaskCountByCondition")
    long findDmTimedTaskCountByCondition(DmTimedTask dmTimedTask);
    @PostMapping("/updateDmTimedTask")
    void updateDmTimedTask(DmTimedTask dmTimedTask);
    @GetMapping("/deleteDmTimedTask/{id}")
    void deleteDmTimedTask(@PathVariable("id") String id);
    @PostMapping("/deleteDmTimedTaskByCondition")
    void deleteDmTimedTaskByCondition(DmTimedTask dmTimedTask);
    @PostMapping("/informVersion")
    DmTimedTask informVersion(DmTimedTask dmTimedTask);
}
