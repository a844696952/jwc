package com.yice.edu.cn.osp.feignClient.dm.dmScreenSaver;

import com.yice.edu.cn.common.pojo.dm.dmScreenSaver.AreaByDmClassVo;
import com.yice.edu.cn.common.pojo.dm.dmScreenSaver.DmScreenSaver;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "dmScreenSaverFeign",path = "/dmScreenSaver")
public interface DmScreenSaverFeign {
    @GetMapping("/findDmScreenSaverById/{id}")
    DmScreenSaver findDmScreenSaverById(@PathVariable("id") String id);
    @PostMapping("/saveDmScreenSaver")
    DmScreenSaver saveDmScreenSaver(DmScreenSaver dmScreenSaver);
    @PostMapping("/findDmScreenSaverListByCondition")
    List<DmScreenSaver> findDmScreenSaverListByCondition(DmScreenSaver dmScreenSaver);
    @PostMapping("/findOneDmScreenSaverByCondition")
    DmScreenSaver findOneDmScreenSaverByCondition(DmScreenSaver dmScreenSaver);
    @PostMapping("/findDmScreenSaverCountByCondition")
    long findDmScreenSaverCountByCondition(DmScreenSaver dmScreenSaver);
    @PostMapping("/updateDmScreenSaver")
    void updateDmScreenSaver(DmScreenSaver dmScreenSaver);
    @GetMapping("/deleteDmScreenSaver/{id}")
    void deleteDmScreenSaver(@PathVariable("id") String id);
    @PostMapping("/deleteDmScreenSaverByCondition")
    void deleteDmScreenSaverByCondition(DmScreenSaver dmScreenSaver);
    @PostMapping("/batchUpdateDmScreenSaver")
    void batchUpdateDmScreenSaver(DmScreenSaver dmScreenSaver);
    @PostMapping("/batchDeleteDmScreenSaver")
    void batchDeleteDmScreenSaver(DmScreenSaver dmScreenSaver);
    //获取班级名称和班牌的用户名
    @PostMapping("/getAddAreaByDmClass")
    List<AreaByDmClassVo> getAreaByDmClass(DmScreenSaver dmScreenSaver);
    //根据班级编号获取到云班牌用户名
    @PostMapping("/getUserNameByClassId")
    String getUserNameByClassId(DmScreenSaver dmScreenSaver);
    @PostMapping("/batchUpdateDmScreenSaverStatus")
    void batchUpdateDmScreenSaverStatus(DmScreenSaver dmScreenSaver);


}
