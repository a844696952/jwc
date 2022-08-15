package com.yice.edu.cn.ws.feignClient.cc;

import com.yice.edu.cn.common.pojo.cc.recording.UserRecodingVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value="recording",contextId = "cloudCourseRecordFeign",path = "/cloudCourseRecord")
public interface CloudCourseRecordFeign {
    @GetMapping("/beginRecord/{id}")
    void beginRecord(@PathVariable("id") String id);

    @GetMapping("/endRecord/{id}")
    void endRecord(@PathVariable("id") String id);

    @PostMapping("/addUserVideo")
    void addUserVideo(UserRecodingVo vo);

    @PostMapping("/removeUserVideo")
    void removeUserVideo(UserRecodingVo vo);
}
