package com.yice.edu.cn.osp.feignClient.xw.dj;

import com.yice.edu.cn.common.pojo.xw.dj.DjClassify;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "djClassifyFeign",path = "/djClassify")
public interface DjClassifyFeign {
    @GetMapping("/findDjClassifyById/{id}")
    DjClassify findDjClassifyById(@PathVariable("id") String id);
    @PostMapping("/saveDjClassify")
    DjClassify saveDjClassify(DjClassify djClassify);
    @GetMapping("/findActivityDjClassify")
    List<DjClassify> findActivityDjClassify();
    @PostMapping("/findDjClassifyListByCondition")
    List<DjClassify> findDjClassifyListByCondition(DjClassify djClassify);
    @PostMapping("/findOneDjClassifyByCondition")
    DjClassify findOneDjClassifyByCondition(DjClassify djClassify);
    @PostMapping("/findDjClassifyCountByCondition")
    long findDjClassifyCountByCondition(DjClassify djClassify);
    @PostMapping("/updateDjClassify")
    void updateDjClassify(DjClassify djClassify);
    @GetMapping("/deleteDjClassify/{id}")
    void deleteDjClassify(@PathVariable("id") String id);
    @GetMapping("/deleteDjClassifyNotEnpty/{id}")
    boolean deleteDjClassifyNotEnpty(@PathVariable("id") String id);
    @PostMapping("/deleteDjClassifyByCondition")
    void deleteDjClassifyByCondition(DjClassify djClassify);
    @PostMapping("/selectClassifyListByType")
    List<DjClassify> selectClassifyListByType(DjClassify djClassify);
    @PostMapping("/findClassifyListByType")
    List<DjClassify> findClassifyListByType(DjClassify djClassify);


}
