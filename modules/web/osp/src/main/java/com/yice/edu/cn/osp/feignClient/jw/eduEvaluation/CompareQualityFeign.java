package com.yice.edu.cn.osp.feignClient.jw.eduEvaluation;

import com.yice.edu.cn.common.pojo.jw.eduEvaluation.CompareQuality;
import com.yice.edu.cn.common.pojo.jw.eduEvaluation.CompareQualityDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient(value="jw",contextId = "compareQualityFeign",path = "/compareQuality")
public interface CompareQualityFeign {
    @GetMapping("/findCompareQualityById/{id}")
    CompareQuality findCompareQualityById(@PathVariable("id") String id);
    @PostMapping("/saveCompareQuality")
    CompareQuality saveCompareQuality(CompareQuality compareQuality);
    @PostMapping("/findCompareQualityListByCondition")
    List<CompareQuality> findCompareQualityListByCondition(CompareQuality compareQuality);
    @PostMapping("/findOneCompareQualityByCondition")
    CompareQuality findOneCompareQualityByCondition(CompareQuality compareQuality);
    @PostMapping("/findCompareQualityCountByCondition")
    long findCompareQualityCountByCondition(CompareQuality compareQuality);
    @PostMapping("/updateCompareQuality")
    void updateCompareQuality(CompareQuality compareQuality);
    @GetMapping("/deleteCompareQuality/{id}")
    void deleteCompareQuality(@PathVariable("id") String id);
    @PostMapping("/deleteCompareQualityByCondition")
    void deleteCompareQualityByCondition(CompareQuality compareQuality);
    @PostMapping("/batchSaveCompareQuality")
    Map<String,Object> batchSaveCompareQuality(List<CompareQualityDetail> list);
    @PostMapping("/deleteCompareQualityByIdList")
    void deleteCompareQualityByIdList(List<String> idList);

    @PostMapping("/moveCompareQualityDataToHistory")
    void moveCompareQualityDataToHistory(List<String> classIdList);
}
