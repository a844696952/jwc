package com.yice.edu.cn.osp.feignClient.xw.workerKq;

import com.yice.edu.cn.common.pojo.xw.workerKq.CommenSettings;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "commenSettingsFeign",path = "/commenSettings")
public interface CommenSettingsFeign {
    @GetMapping("/findCommenSettingsById/{id}")
    CommenSettings findCommenSettingsById(@PathVariable("id") String id);
    @PostMapping("/saveCommenSettings")
    CommenSettings saveCommenSettings(CommenSettings commenSettings);
    @PostMapping("/findCommenSettingsListByCondition")
    List<CommenSettings> findCommenSettingsListByCondition(CommenSettings commenSettings);
    @PostMapping("/findOneCommenSettingsByCondition")
    CommenSettings findOneCommenSettingsByCondition(CommenSettings commenSettings);
    @PostMapping("/findCommenSettingsCountByCondition")
    long findCommenSettingsCountByCondition(CommenSettings commenSettings);
    @PostMapping("/updateCommenSettings")
    void updateCommenSettings(CommenSettings commenSettings);
    @GetMapping("/deleteCommenSettings/{id}")
    void deleteCommenSettings(@PathVariable("id") String id);
    @PostMapping("/deleteCommenSettingsByCondition")
    void deleteCommenSettingsByCondition(CommenSettings commenSettings);
}
