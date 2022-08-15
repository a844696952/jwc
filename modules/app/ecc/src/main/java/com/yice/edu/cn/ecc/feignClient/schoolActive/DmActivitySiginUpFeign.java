package com.yice.edu.cn.ecc.feignClient.schoolActive;

import com.yice.edu.cn.common.pojo.dm.schoolActive.DmActivitySiginUp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",path = "/dmActivitySiginUp")
public interface DmActivitySiginUpFeign {
    @GetMapping("/findDmActivitySiginUpById/{id}")
    DmActivitySiginUp findDmActivitySiginUpById(@PathVariable("id") String id);
    @PostMapping("/saveDmActivitySiginUp")
    DmActivitySiginUp saveDmActivitySiginUp(DmActivitySiginUp dmActivitySiginUp);
    @PostMapping("/findDmActivitySiginUpListByCondition")
    List<DmActivitySiginUp> findDmActivitySiginUpListByCondition(DmActivitySiginUp dmActivitySiginUp);
    @PostMapping("/findOneDmActivitySiginUpByCondition")
    DmActivitySiginUp findOneDmActivitySiginUpByCondition(DmActivitySiginUp dmActivitySiginUp);
    @PostMapping("/findDmActivitySiginUpCountByCondition")
    long findDmActivitySiginUpCountByCondition(DmActivitySiginUp dmActivitySiginUp);
    @PostMapping("/updateDmActivitySiginUp")
    void updateDmActivitySiginUp(DmActivitySiginUp dmActivitySiginUp);
    @GetMapping("/deleteDmActivitySiginUp/{id}")
    void deleteDmActivitySiginUp(@PathVariable("id") String id);
    @PostMapping("/deleteDmActivitySiginUpByCondition")
    void deleteDmActivitySiginUpByCondition(DmActivitySiginUp dmActivitySiginUp);
    @PostMapping("/batchSaveDmActivitySiginUp")
    void batchSaveDmActivitySiginUp(List<DmActivitySiginUp> dmActivitySiginUps);
}
