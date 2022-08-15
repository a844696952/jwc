package com.yice.edu.cn.osp.feignClient.wb.latticePager;


import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.wb.latticePager.LatticePager;
import com.yice.edu.cn.common.pojo.wb.latticePager.LatticePagerInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "latticePagerFeign",path = "/latticePager")
public interface LatticePagerFeign {

    @PostMapping("/findLatticePagerListByCondition")
    List<LatticePager> findLatticePagerListByCondition(LatticePager latticePager);


    @PostMapping("/findLatticePagerCountByCondition")
    long findLatticePagerCountByCondition(LatticePager latticePager);

    @PostMapping("/saveOrUpdateLatticePager")
    LatticePager saveOrUpdateLatticePager(LatticePager latticePager);

    @GetMapping("/deleteLatticePager/{id}")
    void deleteLatticePager(@PathVariable("id") String id);

    @GetMapping("/findLatticePagerById/{id}")
    LatticePager findLatticePagerById(@PathVariable("id") String  id);
}
