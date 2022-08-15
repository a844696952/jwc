package com.yice.edu.cn.osp.feignClient.dm.modeManage;

import com.yice.edu.cn.common.pojo.dm.modeManage.ExamTask;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value="dm",contextId = "attendClassModeFeign",path = "/attendClassMode")
public interface AttendClassModeFeign {
    @GetMapping("/openAttendClassMode/{id}")
    List<String> openAttendClassMode(@PathVariable("id") String id);
    @GetMapping("/closeAttendClassMode/{id}")
    List<String> closeAttendClassMode(@PathVariable("id") String id);
    @GetMapping("/findClassModeTask/{id}")
    ExamTask findClassModeTask(@PathVariable("id") String id);
}
