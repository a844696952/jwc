package com.yice.edu.cn.osp.feignClient.xw.stuInAndOut;

import com.yice.edu.cn.common.pojo.xw.stuInAndOut.ClassTime;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "classTimeFeign",path = "/classTime")
public interface ClassTimeFeign {
    @GetMapping("/findClassTimeById/{id}")
    ClassTime findClassTimeById(@PathVariable("id") String id);
    @PostMapping("/saveClassTime")
    ClassTime saveClassTime(ClassTime classTime);
    @PostMapping("/findClassTimeListByCondition")
    List<ClassTime> findClassTimeListByCondition(ClassTime classTime);
    @PostMapping("/findOneClassTimeByCondition")
    ClassTime findOneClassTimeByCondition(ClassTime classTime);
    @PostMapping("/findClassTimeCountByCondition")
    long findClassTimeCountByCondition(ClassTime classTime);
    @PostMapping("/updateClassTime")
    void updateClassTime(ClassTime classTime);
    @GetMapping("/deleteClassTime/{id}")
    void deleteClassTime(@PathVariable("id") String id);
    @PostMapping("/deleteClassTimeByCondition")
    void deleteClassTimeByCondition(ClassTime classTime);
}
