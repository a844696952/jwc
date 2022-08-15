package com.yice.edu.cn.yed.feignClient.dm.dmClassCardArea;

import com.yice.edu.cn.common.pojo.dm.dmClassCardArea.DmClassCardArea;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "dmClassCardAreaFeign",path = "/dmClassCardArea")
public interface DmClassCardAreaFeign {
    @GetMapping("/findDmClassCardAreaById/{id}")
    DmClassCardArea findDmClassCardAreaById(@PathVariable("id") String id);
    @PostMapping("/saveDmClassCardArea")
    DmClassCardArea saveDmClassCardArea(DmClassCardArea dmClassCardArea);
    @PostMapping("/findDmClassCardAreaListByCondition")
    List<DmClassCardArea> findDmClassCardAreaListByCondition(DmClassCardArea dmClassCardArea);
    @PostMapping("/findOneDmClassCardAreaByCondition")
    DmClassCardArea findOneDmClassCardAreaByCondition(DmClassCardArea dmClassCardArea);
    @PostMapping("/findDmClassCardAreaCountByCondition")
    long findDmClassCardAreaCountByCondition(DmClassCardArea dmClassCardArea);
    @PostMapping("/updateDmClassCardArea")
    void updateDmClassCardArea(DmClassCardArea dmClassCardArea);
    @GetMapping("/deleteDmClassCardArea/{id}")
    void deleteDmClassCardArea(@PathVariable("id") String id);
    @PostMapping("/deleteDmClassCardAreaByCondition")
    void deleteDmClassCardAreaByCondition(DmClassCardArea dmClassCardArea);
}
