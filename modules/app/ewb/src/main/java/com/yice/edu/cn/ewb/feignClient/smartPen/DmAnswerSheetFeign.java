package com.yice.edu.cn.ewb.feignClient.smartPen;

import com.yice.edu.cn.common.pojo.dm.smartPen.DmAnswerSheet;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId="dmAnswerSheetFeign",path = "/dmAnswerSheet")
public interface DmAnswerSheetFeign {
    @GetMapping("/findDmAnswerSheetById/{id}")
    DmAnswerSheet findDmAnswerSheetById(@PathVariable("id") String id);
    @PostMapping("/saveDmAnswerSheet")
    DmAnswerSheet saveDmAnswerSheet(DmAnswerSheet dmAnswerSheet);
    @PostMapping("/findDmAnswerSheetListByCondition")
    List<DmAnswerSheet> findDmAnswerSheetListByCondition(DmAnswerSheet dmAnswerSheet);
    @PostMapping("/findOneDmAnswerSheetByCondition")
    DmAnswerSheet findOneDmAnswerSheetByCondition(DmAnswerSheet dmAnswerSheet);
    @PostMapping("/findDmAnswerSheetCountByCondition")
    long findDmAnswerSheetCountByCondition(DmAnswerSheet dmAnswerSheet);
    @PostMapping("/updateDmAnswerSheet")
    void updateDmAnswerSheet(DmAnswerSheet dmAnswerSheet);
    @GetMapping("/deleteDmAnswerSheet/{id}")
    void deleteDmAnswerSheet(@PathVariable("id") String id);
    @PostMapping("/deleteDmAnswerSheetByCondition")
    void deleteDmAnswerSheetByCondition(DmAnswerSheet dmAnswerSheet);
}
