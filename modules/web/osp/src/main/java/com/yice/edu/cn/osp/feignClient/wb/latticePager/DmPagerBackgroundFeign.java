package com.yice.edu.cn.osp.feignClient.wb.latticePager;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.wb.latticePager.DmPagerBackground;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(value="dm",contextId = "dmPagerBackgroundFeign",path = "/dmPagerBackground")
public interface DmPagerBackgroundFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findDmPagerBackgroundById/{id}")
    DmPagerBackground findDmPagerBackgroundById(@PathVariable("id") String id);
    @PostMapping("/saveDmPagerBackground")
    DmPagerBackground saveDmPagerBackground(DmPagerBackground dmPagerBackground);
    @PostMapping("/batchSaveDmPagerBackground")
    void batchSaveDmPagerBackground(List<DmPagerBackground> dmPagerBackgrounds);
    @PostMapping("/findDmPagerBackgroundListByCondition")
    List<DmPagerBackground> findDmPagerBackgroundListByCondition(DmPagerBackground dmPagerBackground);
    @PostMapping("/findOneDmPagerBackgroundByCondition")
    DmPagerBackground findOneDmPagerBackgroundByCondition(DmPagerBackground dmPagerBackground);
    @PostMapping("/findDmPagerBackgroundCountByCondition")
    long findDmPagerBackgroundCountByCondition(DmPagerBackground dmPagerBackground);
    @PostMapping("/updateDmPagerBackground")
    void updateDmPagerBackground(DmPagerBackground dmPagerBackground);
    @PostMapping("/updateDmPagerBackgroundForAll")
    void updateDmPagerBackgroundForAll(DmPagerBackground dmPagerBackground);
    @GetMapping("/deleteDmPagerBackground/{id}")
    void deleteDmPagerBackground(@PathVariable("id") String id);
    @PostMapping("/deleteDmPagerBackgroundByCondition")
    void deleteDmPagerBackgroundByCondition(DmPagerBackground dmPagerBackground);

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @PostMapping("/upload/{latticeId}")
    ResponseJson upload(MultipartFile file, @PathVariable("latticeId") String latticeId);

    @PostMapping("/textUpdateDmPagerBackground")
    ResponseJson textUpdateDmPagerBackground(DmPagerBackground dmPagerBackground);

    @PostMapping("/reUpload/{id}")
    ResponseJson reUpload(MultipartFile file,@PathVariable("id") String id);
}
