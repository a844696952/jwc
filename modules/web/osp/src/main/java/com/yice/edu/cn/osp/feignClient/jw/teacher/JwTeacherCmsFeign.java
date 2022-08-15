package com.yice.edu.cn.osp.feignClient.jw.teacher;

import com.yice.edu.cn.common.pojo.jw.teacher.JwTeacherCms;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "jw", contextId = "jwTeacherCmsFeign", path = "/jwTeacherCms")
public interface JwTeacherCmsFeign {
    @GetMapping("/findJwTeacherCmsById/{id}")
    JwTeacherCms findJwTeacherCmsById(@PathVariable("id") String id);

    @PostMapping("/saveJwTeacherCms")
    JwTeacherCms saveJwTeacherCms(JwTeacherCms jwTeacherCms);

    @PostMapping("/findJwTeacherCmsListByCondition")
    List<JwTeacherCms> findJwTeacherCmsListByCondition(JwTeacherCms jwTeacherCms);

    @PostMapping("/findOneJwTeacherCmsByCondition")
    JwTeacherCms findOneJwTeacherCmsByCondition(JwTeacherCms jwTeacherCms);

    @PostMapping("/findJwTeacherCmsCountByCondition")
    long findJwTeacherCmsCountByCondition(JwTeacherCms jwTeacherCms);

    @PostMapping("/updateJwTeacherCms")
    void updateJwTeacherCms(JwTeacherCms jwTeacherCms);

    @GetMapping("/deleteJwTeacherCms/{id}")
    void deleteJwTeacherCms(@PathVariable("id") String id);

    @PostMapping("/deleteJwTeacherCmsByCondition")
    void deleteJwTeacherCmsByCondition(JwTeacherCms jwTeacherCms);
}
