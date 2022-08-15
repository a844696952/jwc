package com.yice.edu.cn.yed.feignClient.baseData.eehManagement;

import com.yice.edu.cn.common.pojo.jw.eehManagement.EehTree;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "eehTreeFeign",path = "/eehTree")
public interface EehTreeFeign {
    @GetMapping("/findEehTreeById/{id}")
    EehTree findEehTreeById(@PathVariable("id") String id);
    @PostMapping("/saveEehTree")
    EehTree saveEehTree(EehTree eehTree);
    @PostMapping("/findEehTreeListByCondition")
    List<EehTree> findEehTreeListByCondition(EehTree eehTree);
    @PostMapping("/findOneEehTreeByCondition")
    EehTree findOneEehTreeByCondition(EehTree eehTree);
    @PostMapping("/findEehTreeCountByCondition")
    long findEehTreeCountByCondition(EehTree eehTree);
    @PostMapping("/updateEehTree")
    void updateEehTree(EehTree eehTree);
    @GetMapping("/deleteEehTree/{id}")
    void deleteEehTree(@PathVariable("id") String id);
    @PostMapping("/deleteEehTreeByCondition")
    void deleteEehTreeByCondition(EehTree eehTree);
    @GetMapping("/findAllTreeMenu/{type}")
    List<EehTree> findAllTreeMenu(@PathVariable("type") String type);

    @GetMapping("/lookEehTreeNewById/{id}")
    EehTree lookEehTreeNewById(@PathVariable("id") String id);

}
