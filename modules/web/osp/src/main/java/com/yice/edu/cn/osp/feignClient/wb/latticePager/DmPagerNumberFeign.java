package com.yice.edu.cn.osp.feignClient.wb.latticePager;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.wb.latticePager.DmPagerBackground;
import com.yice.edu.cn.common.pojo.wb.latticePager.DmPagerNumber;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "dmPagerNumberFeign",path = "/dmPagerNumber")
public interface DmPagerNumberFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findDmPagerNumberById/{id}")
    DmPagerNumber findDmPagerNumberById(@PathVariable("id") String id);
    @PostMapping("/saveDmPagerNumber")
    DmPagerNumber saveDmPagerNumber(DmPagerNumber dmPagerNumber);
    @PostMapping("/batchSaveDmPagerNumber")
    void batchSaveDmPagerNumber(List<DmPagerNumber> dmPagerNumbers);
    @PostMapping("/findDmPagerNumberListByCondition")
    List<DmPagerNumber> findDmPagerNumberListByCondition(DmPagerNumber dmPagerNumber);
    @PostMapping("/findOneDmPagerNumberByCondition")
    DmPagerNumber findOneDmPagerNumberByCondition(DmPagerNumber dmPagerNumber);
    @PostMapping("/findDmPagerNumberCountByCondition")
    long findDmPagerNumberCountByCondition(DmPagerNumber dmPagerNumber);
    @PostMapping("/updateDmPagerNumber")
    void updateDmPagerNumber(DmPagerNumber dmPagerNumber);
    @PostMapping("/updateDmPagerNumberForAll")
    void updateDmPagerNumberForAll(DmPagerNumber dmPagerNumber);
    @GetMapping("/deleteDmPagerNumber/{id}")
    void deleteDmPagerNumber(@PathVariable("id") String id);
    @PostMapping("/deleteDmPagerNumberByCondition")
    void deleteDmPagerNumberByCondition(DmPagerNumber dmPagerNumber);


    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @GetMapping("/findDmPagerBackgroundImg/{id}")
    List<DmPagerBackground> findDmPagerBackgroundImg(@PathVariable("id") String id);

    @PostMapping("/updateRecover")
    ResponseJson updateRecover(DmPagerNumber dmPagerNumber);
}
