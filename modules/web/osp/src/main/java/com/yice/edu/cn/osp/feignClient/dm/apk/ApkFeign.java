package com.yice.edu.cn.osp.feignClient.dm.apk;

import com.yice.edu.cn.common.pojo.dm.classCard.Apk;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "apkFeign",path = "/apk")
public interface ApkFeign {
    @GetMapping("/findApkById/{id}")
    Apk findApkById(@PathVariable("id") String id);
    @PostMapping("/saveApk")
    Apk saveApk(Apk apk);
    @PostMapping("/findApkListByCondition")
    List<Apk> findApkListByCondition(Apk apk);
    @PostMapping("/findOneApkByCondition")
    Apk findOneApkByCondition(Apk apk);
    @PostMapping("/findApkCountByCondition")
    long findApkCountByCondition(Apk apk);
    @PostMapping("/updateApk")
    void updateApk(Apk apk);
    @GetMapping("/deleteApk/{id}")
    void deleteApk(@PathVariable("id") String id);
    @PostMapping("/deleteApkByCondition")
    void deleteApkByCondition(Apk apk);
}
