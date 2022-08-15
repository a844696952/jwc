package com.yice.edu.cn.yed.feignClient.jw.loc;

import com.yice.edu.cn.common.pojo.loc.LocVersion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "locVersionFeign",path = "/locVersion")
public interface LocVersionFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findLocVersionById/{id}")
    LocVersion findLocVersionById(@PathVariable("id") String id);
    @PostMapping("/saveLocVersion")
    LocVersion saveLocVersion(LocVersion locVersion);
    @PostMapping("/batchSaveLocVersion")
    void batchSaveLocVersion(List<LocVersion> locVersions);
    @PostMapping("/findLocVersionListByCondition")
    List<LocVersion> findLocVersionListByCondition(LocVersion locVersion);
    @PostMapping("/findOneLocVersionByCondition")
    LocVersion findOneLocVersionByCondition(LocVersion locVersion);
    @PostMapping("/findLocVersionCountByCondition")
    long findLocVersionCountByCondition(LocVersion locVersion);
    @PostMapping("/updateLocVersion")
    void updateLocVersion(LocVersion locVersion);
    @PostMapping("/updateLocVersionForAll")
    void updateLocVersionForAll(LocVersion locVersion);
    @GetMapping("/deleteLocVersion/{id}")
    void deleteLocVersion(@PathVariable("id") String id);
    @PostMapping("/deleteLocVersionByCondition")
    void deleteLocVersionByCondition(LocVersion locVersion);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @PostMapping("/findVersionNameRepetition")
    long findVersionNameRepetition(LocVersion locVersion);
}
