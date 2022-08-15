package com.yice.edu.cn.osp.feignClient.jy.handout;

import com.yice.edu.cn.common.pojo.jy.handout.Handout;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "handoutFeign",path = "/handout")
public interface HandoutFeign {

    /**/
    @GetMapping("/findHandoutById/{id}")
    Handout findHandoutById(@PathVariable("id") String id);
    @PostMapping("/saveHandout")
    Handout saveHandout(Handout handout);
    @PostMapping("/findHandoutListByCondition")
    List<Handout> findHandoutListByCondition(Handout handout);
    @PostMapping("/findOneHandoutByCondition")
    Handout findOneHandoutByCondition(Handout handout);
    @PostMapping("/findHandoutCountByCondition")
    long findHandoutCountByCondition(Handout handout);
    @PostMapping("/updateHandout")
    void updateHandout(Handout handout);
    @GetMapping("/deleteHandout/{id}")
    void deleteHandout(@PathVariable("id") String id);
    @PostMapping("/deleteHandoutByCondition")
    void deleteHandoutByCondition(Handout handout);
    @PostMapping("/findHandoutsByCondition2")
    List<Handout> findHandoutsByCondition2(Handout handout);
    @PostMapping("/findHandoutCountByCondition2")
    long findHandoutCountByCondition2(Handout handout);
}
