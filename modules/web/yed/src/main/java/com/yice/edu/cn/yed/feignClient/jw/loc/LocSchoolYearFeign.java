package com.yice.edu.cn.yed.feignClient.jw.loc;

import com.yice.edu.cn.common.pojo.loc.LocSchoolYear;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "locSchoolYearFeign",path = "/locSchoolYear")
public interface LocSchoolYearFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findLocSchoolYearById/{id}")
    LocSchoolYear findLocSchoolYearById(@PathVariable("id") String id);
    @PostMapping("/saveLocSchoolYear")
    LocSchoolYear saveLocSchoolYear(LocSchoolYear locSchoolYear);
    @PostMapping("/batchSaveLocSchoolYear")
    void batchSaveLocSchoolYear(List<LocSchoolYear> locSchoolYears);
    @PostMapping("/findLocSchoolYearListByCondition")
    List<LocSchoolYear> findLocSchoolYearListByCondition(LocSchoolYear locSchoolYear);
    @PostMapping("/findOneLocSchoolYearByCondition")
    LocSchoolYear findOneLocSchoolYearByCondition(LocSchoolYear locSchoolYear);
    @PostMapping("/findLocSchoolYearCountByCondition")
    long findLocSchoolYearCountByCondition(LocSchoolYear locSchoolYear);
    @PostMapping("/updateLocSchoolYear")
    void updateLocSchoolYear(LocSchoolYear locSchoolYear);
    @PostMapping("/updateLocSchoolYearForAll")
    void updateLocSchoolYearForAll(LocSchoolYear locSchoolYear);
    @GetMapping("/deleteLocSchoolYear/{id}")
    void deleteLocSchoolYear(@PathVariable("id") String id);
    @PostMapping("/deleteLocSchoolYearByCondition")
    void deleteLocSchoolYearByCondition(LocSchoolYear locSchoolYear);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
