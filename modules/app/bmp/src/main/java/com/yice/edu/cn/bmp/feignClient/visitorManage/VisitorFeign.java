package com.yice.edu.cn.bmp.feignClient.visitorManage;

import com.yice.edu.cn.common.pojo.xw.visitorManage.Visitor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",path = "/visitor")
public interface VisitorFeign {
    @GetMapping("/findVisitorById/{id}")
    Visitor findVisitorById(@PathVariable("id") String id);
    @PostMapping("/saveVisitor")
    Visitor saveVisitor(Visitor visitor);
    @PostMapping("/findVisitorListByCondition")
    List<Visitor> findVisitorListByCondition(Visitor visitor);
    @PostMapping("/findOneVisitorByCondition")
    Visitor findOneVisitorByCondition(Visitor visitor);
    @PostMapping("/findVisitorCountByCondition")
    long findVisitorCountByCondition(Visitor visitor);
    @PostMapping("/updateVisitor")
    void updateVisitor(Visitor visitor);
    @GetMapping("/deleteVisitor/{id}")
    void deleteVisitor(@PathVariable("id") String id);
    @PostMapping("/deleteVisitorByCondition")
    void deleteVisitorByCondition(Visitor visitor);
}
