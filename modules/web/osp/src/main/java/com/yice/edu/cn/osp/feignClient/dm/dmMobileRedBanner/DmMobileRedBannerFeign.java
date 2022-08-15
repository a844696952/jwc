package com.yice.edu.cn.osp.feignClient.dm.dmMobileRedBanner;

import com.yice.edu.cn.common.pojo.dm.dmMobileRedBanner.DmMobileRedBanner;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "dmMobileRedBannerFeign",path = "/dmMobileRedBanner")
public interface DmMobileRedBannerFeign {
    @GetMapping("/findDmMobileRedBannerById/{id}")
    DmMobileRedBanner findDmMobileRedBannerById(@PathVariable("id") String id);
    @PostMapping("/saveDmMobileRedBanner")
    DmMobileRedBanner saveDmMobileRedBanner(DmMobileRedBanner dmMobileRedBanner);
    @PostMapping("/findDmMobileRedBannerListByCondition")
    List<DmMobileRedBanner> findDmMobileRedBannerListByCondition(DmMobileRedBanner dmMobileRedBanner);
    @PostMapping("/findOneDmMobileRedBannerByCondition")
    DmMobileRedBanner findOneDmMobileRedBannerByCondition(DmMobileRedBanner dmMobileRedBanner);
    @PostMapping("/findDmMobileRedBannerCountByCondition")
    long findDmMobileRedBannerCountByCondition(DmMobileRedBanner dmMobileRedBanner);
    @PostMapping("/updateDmMobileRedBanner")
    void updateDmMobileRedBanner(DmMobileRedBanner dmMobileRedBanner);
    @PostMapping("/updateDmMobileRedBannerForNotNull")
    void updateDmMobileRedBannerForNotNull(DmMobileRedBanner dmMobileRedBanner);
    @GetMapping("/deleteDmMobileRedBanner/{id}")
    void deleteDmMobileRedBanner(@PathVariable("id") String id);
    @PostMapping("/deleteDmMobileRedBannerByCondition")
    void deleteDmMobileRedBannerByCondition(DmMobileRedBanner dmMobileRedBanner);

    @PostMapping("/updateStatus")
    void updateStatus();

    @GetMapping("/findToBeIssuedDmMobileRedBannerByAwardTime/{awardTime}")
    DmMobileRedBanner findToBeIssuedDmMobileRedBannerByAwardTime(@PathVariable("awardTime") String awardTime);
}
