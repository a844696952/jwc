package com.yice.edu.cn.pcd.feignClient.eehManagement;

import com.yice.edu.cn.common.pojo.jw.eehManagement.EehTree;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "eehTree",path = "/eehTree")
public interface EehTreeFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findEehTreeById/{id}")
    EehTree findEehTreeById(@PathVariable("id") String id);
    @PostMapping("/saveEehTree")
    EehTree saveEehTree(EehTree eehTree);
    @PostMapping("/batchSaveEehTree")
    void batchSaveEehTree(List<EehTree> eehTrees);
    @PostMapping("/findEehTreeListByCondition")
    List<EehTree> findEehTreeListByCondition(EehTree eehTree);
    @PostMapping("/findOneEehTreeByCondition")
    EehTree findOneEehTreeByCondition(EehTree eehTree);
    @PostMapping("/findEehTreeCountByCondition")
    long findEehTreeCountByCondition(EehTree eehTree);
    @PostMapping("/updateEehTree")
    void updateEehTree(EehTree eehTree);
    @PostMapping("/updateEehTreeForAll")
    void updateEehTreeForAll(EehTree eehTree);
    @GetMapping("/deleteEehTree/{id}")
    void deleteEehTree(@PathVariable("id") String id);
    @PostMapping("/deleteEehTreeByCondition")
    void deleteEehTreeByCondition(EehTree eehTree);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @GetMapping("/findChildEehId/{id}")
    List<String> findChildEehId(@PathVariable("id") String id);

    @PostMapping("/findEehSchoolListNoCondition")
    List<EehTree> findEehSchoolListNoCondition(EehTree eehTree);
}
