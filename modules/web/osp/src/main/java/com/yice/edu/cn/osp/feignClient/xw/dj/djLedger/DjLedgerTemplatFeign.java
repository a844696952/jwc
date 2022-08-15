package com.yice.edu.cn.osp.feignClient.xw.dj.djLedger;

import com.yice.edu.cn.common.pojo.xw.dj.djLedgerTemplate.DjLedgerTemplat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "DjLedgerTemplatFeign",path = "/djLedgerTemplat")
public interface DjLedgerTemplatFeign {
    @GetMapping("/findDjLedgerTemplatById/{id}")
    DjLedgerTemplat findDjLedgerTemplatById(@PathVariable("id") String id);
    @PostMapping("/saveDjLedgerTemplat")
    DjLedgerTemplat saveDjLedgerTemplat(DjLedgerTemplat djLedgerTemplat);
    @PostMapping("/findDjLedgerTemplatListByCondition")
    List<DjLedgerTemplat> findDjLedgerTemplatListByCondition(DjLedgerTemplat djLedgerTemplat);
    @PostMapping("/findOneDjLedgerTemplatByCondition")
    DjLedgerTemplat findOneDjLedgerTemplatByCondition(DjLedgerTemplat djLedgerTemplat);
    @PostMapping("/findDjLedgerTemplatCountByCondition")
    long findDjLedgerTemplatCountByCondition(DjLedgerTemplat djLedgerTemplat);
    @PostMapping("/updateDjLedgerTemplat")
    void updateDjLedgerTemplat(DjLedgerTemplat djLedgerTemplat);
    @GetMapping("/deleteDjLedgerTemplat/{id}")
    void deleteDjLedgerTemplat(@PathVariable("id") String id);
    @PostMapping("/deleteDjLedgerTemplatByCondition")
    void deleteDjLedgerTemplatByCondition(DjLedgerTemplat djLedgerTemplat);
    @GetMapping("/findTemplateTree/{schoolId}")
    List<DjLedgerTemplat> findTemplateTree(@PathVariable("schoolId") String schoolId);
}
