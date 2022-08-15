package com.yice.edu.cn.osp.feignClient.jw.eduEvaluation;

import com.yice.edu.cn.common.pojo.jw.eduEvaluation.CompareQualityDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "compareQualityDetailFeign",path = "/compareQualityDetail")
public interface CompareQualityDetailFeign {
    @GetMapping("/findCompareQualityDetailById/{id}")
    CompareQualityDetail findCompareQualityDetailById(@PathVariable("id") String id);
    @PostMapping("/saveCompareQualityDetail")
    CompareQualityDetail saveCompareQualityDetail(CompareQualityDetail compareQualityDetail);
    @PostMapping("/findCompareQualityDetailListByCondition")
    List<CompareQualityDetail> findCompareQualityDetailListByCondition(CompareQualityDetail compareQualityDetail);
    @PostMapping("/findOneCompareQualityDetailByCondition")
    CompareQualityDetail findOneCompareQualityDetailByCondition(CompareQualityDetail compareQualityDetail);
    @PostMapping("/findCompareQualityDetailCountByCondition")
    long findCompareQualityDetailCountByCondition(CompareQualityDetail compareQualityDetail);
    @PostMapping("/updateCompareQualityDetail")
    void updateCompareQualityDetail(CompareQualityDetail compareQualityDetail);
    @GetMapping("/deleteCompareQualityDetail/{id}")
    void deleteCompareQualityDetail(@PathVariable("id") String id);
    @PostMapping("/deleteCompareQualityDetailByCondition")
    void deleteCompareQualityDetailByCondition(CompareQualityDetail compareQualityDetail);
    @PostMapping("/deleteCompareQualityDetailByIdList")
    void deleteCompareQualityDetailByIdList(List<String> idList);
    @PostMapping("/getClassTypeList")
    List<String> getClassTypeList(CompareQualityDetail compareQualityDetail);

}
