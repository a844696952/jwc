package com.yice.edu.cn.osp.feignClient.jw.pen;

import com.yice.edu.cn.common.pojo.jw.pen.Pen;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "penFeign",path = "/pen")
public interface PenFeign {
    @GetMapping("/findPenById/{id}")
    Pen findPenById(@PathVariable("id") String id);
    @PostMapping("/savePen")
    Pen savePen(Pen pen);
    @PostMapping("/findPenListByCondition")
    List<Pen> findPenListByCondition(Pen pen);
    @PostMapping("/findOnePenByCondition")
    Pen findOnePenByCondition(Pen pen);
    @PostMapping("/findPenCountByCondition")
    long findPenCountByCondition(Pen pen);
    @PostMapping("/updatePen")
    void updatePen(Pen pen);
    @GetMapping("/deletePen/{id}")
    void deletePen(@PathVariable("id") String id);
    @PostMapping("/deletePenByCondition")
    void deletePenByCondition(Pen pen);
}
