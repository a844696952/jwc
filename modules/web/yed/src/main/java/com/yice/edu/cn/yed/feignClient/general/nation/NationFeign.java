package com.yice.edu.cn.yed.feignClient.general.nation;

import com.yice.edu.cn.common.pojo.general.nation.Nation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "nationFeign",path = "/nation")
public interface NationFeign {
    @GetMapping("/findNationById/{id}")
    Nation findNationById(@PathVariable("id") String id);
    @PostMapping("/saveNation")
    Nation saveNation(Nation nation);
    @PostMapping("/findNationListByCondition")
    List<Nation> findNationListByCondition(Nation nation);
    @PostMapping("/findNationCountByCondition")
    long findNationCountByCondition(Nation nation);
    @PostMapping("/updateNation")
    void updateNation(Nation nation);
    @GetMapping("/deleteNation/{id}")
    void deleteNation(@PathVariable("id") String id);
}
