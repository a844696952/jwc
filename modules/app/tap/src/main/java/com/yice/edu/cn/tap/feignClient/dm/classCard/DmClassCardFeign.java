package com.yice.edu.cn.tap.feignClient.dm.classCard;

import com.yice.edu.cn.common.pojo.dm.classCard.ClassCardLock;
import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "dmClassCardFeign",path = "/dmClassCard")
public interface DmClassCardFeign {
    @GetMapping("/lockDmScreen/{id}")
    void lockDmScreen(@PathVariable("id") String id);

    @GetMapping("/unLockDmScreen/{id}")
    void unLockDmScreen(@PathVariable("id") String id);

    @GetMapping("/getDmClassCardByTeacherId/{id}/{lockStatus}")
    List<DmClassCard> getDmClassCardByTeacherId(@PathVariable("id") String id,@PathVariable(value = "lockStatus") String lockStatus);

    @PostMapping("/batchChangeLockStatusByIds")
    void batchChangeLockStatusByIds(ClassCardLock classCardLock);

    @GetMapping("/findDmClassCardByStudentId/{studentId}")
    DmClassCard findDmClassCardByStudentId(@PathVariable("studentId") String studentId);
}