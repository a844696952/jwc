package com.yice.edu.cn.osp.feignClient.dm.classes;

import com.yice.edu.cn.common.pojo.dm.classes.DmClassDesc;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "dmClassDescFeign",path = "/dmClassDesc")
public interface DmClassDescFeign {
    @GetMapping("/findDmClassDescById/{id}")
    DmClassDesc findDmClassDescById(@PathVariable("id") String id);
    @PostMapping("/saveDmClassDesc")
    DmClassDesc saveDmClassDesc(DmClassDesc dmClassDesc);
    @PostMapping("/findDmClassDescListByCondition")
    List<DmClassDesc> findDmClassDescListByCondition(DmClassDesc dmClassDesc);
    @PostMapping("/findOneDmClassDescByCondition")
    DmClassDesc findOneDmClassDescByCondition(DmClassDesc dmClassDesc);
    @PostMapping("/findDmClassDescCountByCondition")
    long findDmClassDescCountByCondition(DmClassDesc dmClassDesc);
    @PostMapping("/updateDmClassDesc")
    void updateDmClassDesc(DmClassDesc dmClassDesc);
    @GetMapping("/deleteDmClassDesc/{id}")
    void deleteDmClassDesc(@PathVariable("id") String id);
    @PostMapping("/deleteDmClassDescByCondition")
    void deleteDmClassDescByCondition(DmClassDesc dmClassDesc);
    @GetMapping("/clearClassDescAndPhoto/{classId}")
    void clearClassDescAndPhoto(@PathVariable("classId") String classId);
    @PostMapping("/batchClearClassDescAndPhoto")
    void batchClearClassDescAndPhoto(String classIds);

    @PostMapping("/findJwClassesListByCardCondition")
    List<JwClasses> findJwClassesListByCardCondition(JwClasses jwClasses);
    @PostMapping("/findDmClassesListByCardCondition")
    List<JwClasses> findDmClassesListByCardCondition(JwClasses jwClasses);
    @PostMapping("/findJwClassesCountByCardCondition")
    long findJwClassesCountByCardCondition(JwClasses jwClasses);
    @PostMapping("/findDmClassesCountByCardCondition")
    long findDmClassesCountByCardCondition(JwClasses jwClasses);

}
