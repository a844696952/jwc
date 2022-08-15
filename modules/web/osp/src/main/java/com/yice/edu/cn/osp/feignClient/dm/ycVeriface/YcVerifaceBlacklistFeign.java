package com.yice.edu.cn.osp.feignClient.dm.ycVeriface;

import com.yice.edu.cn.common.pojo.dm.ycVeriface.blacklist.YcVerifaceBlacklist;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "ycVerifaceBlacklistFeign",path = "/ycVerifaceBlacklist")
public interface YcVerifaceBlacklistFeign {
    @GetMapping("/findYcVerifaceBlacklistById/{id}")
    YcVerifaceBlacklist findYcVerifaceBlacklistById(@PathVariable("id") String id);
    @PostMapping("/saveYcVerifaceBlacklist")
    YcVerifaceBlacklist saveYcVerifaceBlacklist(YcVerifaceBlacklist ycVerifaceBlacklist);
    @PostMapping("/findYcVerifaceBlacklistListByCondition")
    List<YcVerifaceBlacklist> findYcVerifaceBlacklistListByCondition(YcVerifaceBlacklist ycVerifaceBlacklist);
    @PostMapping("/findOneYcVerifaceBlacklistByCondition")
    YcVerifaceBlacklist findOneYcVerifaceBlacklistByCondition(YcVerifaceBlacklist ycVerifaceBlacklist);
    @PostMapping("/findYcVerifaceBlacklistCountByCondition")
    long findYcVerifaceBlacklistCountByCondition(YcVerifaceBlacklist ycVerifaceBlacklist);
    @PostMapping("/updateYcVerifaceBlacklist")
    void updateYcVerifaceBlacklist(YcVerifaceBlacklist ycVerifaceBlacklist);
    @GetMapping("/deleteYcVerifaceBlacklist/{id}")
    void deleteYcVerifaceBlacklist(@PathVariable("id") String id);
    @PostMapping("/deleteYcVerifaceBlacklistByCondition")
    void deleteYcVerifaceBlacklistByCondition(YcVerifaceBlacklist ycVerifaceBlacklist);
}
