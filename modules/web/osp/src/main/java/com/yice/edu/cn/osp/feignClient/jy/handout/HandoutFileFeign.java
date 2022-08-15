package com.yice.edu.cn.osp.feignClient.jy.handout;

import com.yice.edu.cn.common.pojo.jy.handout.HandoutFile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "handoutFileFeign",path = "/handoutFile")
public interface HandoutFileFeign {
    @GetMapping("/findHandoutFileById/{id}")
    HandoutFile findHandoutFileById(@PathVariable("id") String id);
    @PostMapping("/saveHandoutFile")
    HandoutFile saveHandoutFile(HandoutFile handoutFile);
    @PostMapping("/findHandoutFileListByCondition")
    List<HandoutFile> findHandoutFileListByCondition(HandoutFile handoutFile);
    @PostMapping("/findOneHandoutFileByCondition")
    HandoutFile findOneHandoutFileByCondition(HandoutFile handoutFile);
    @PostMapping("/findHandoutFileCountByCondition")
    long findHandoutFileCountByCondition(HandoutFile handoutFile);
    @PostMapping("/updateHandoutFile")
    void updateHandoutFile(HandoutFile handoutFile);
    @GetMapping("/deleteHandoutFile/{id}")
    void deleteHandoutFile(@PathVariable("id") String id);
    @PostMapping("/deleteHandoutFileByCondition")
    void deleteHandoutFileByCondition(HandoutFile handoutFile);
}
