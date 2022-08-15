package com.yice.edu.cn.tap.feignClient.systemPush;

import com.yice.edu.cn.common.pojo.jw.systemPush.SystemPush;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",path = "/systemPush")
public interface SystemPushFeign {
    @GetMapping("/findSystemPushById/{id}")
    SystemPush findSystemPushById(@PathVariable("id") String id);
    @PostMapping("/saveSystemPush")
    SystemPush saveSystemPush(SystemPush systemPush);
    @PostMapping("/findSystemPushListByCondition")
    List<SystemPush> findSystemPushListByCondition(SystemPush systemPush);
    @PostMapping("/findOneSystemPushByCondition")
    SystemPush findOneSystemPushByCondition(SystemPush systemPush);
    @PostMapping("/findSystemPushCountByCondition")
    long findSystemPushCountByCondition(SystemPush systemPush);
    @PostMapping("/updateSystemPush")
    void updateSystemPush(SystemPush systemPush);
    @GetMapping("/deleteSystemPush/{id}")
    void deleteSystemPush(@PathVariable("id") String id);
    @PostMapping("/deleteSystemPushByCondition")
    void deleteSystemPushByCondition(SystemPush systemPush);
}
