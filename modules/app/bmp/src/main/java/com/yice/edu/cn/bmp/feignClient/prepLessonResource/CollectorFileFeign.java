package com.yice.edu.cn.bmp.feignClient.prepLessonResource;

import com.yice.edu.cn.common.pojo.jy.prepLessonResource.CollectorFile;
import com.yice.edu.cn.common.pojo.jy.prepLessonResource.PrepLessonResourceFile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",path = "/collectorFile")
public interface CollectorFileFeign {
    @GetMapping("/findCollectorFileById/{id}")
    CollectorFile findCollectorFileById(@PathVariable("id") String id);
    @PostMapping("/saveCollectorFile")
    CollectorFile saveCollectorFile(CollectorFile collectorFile);
    @PostMapping("/findCollectorFileListByCondition")
    List<CollectorFile> findCollectorFileListByCondition(CollectorFile collectorFile);
    @PostMapping("/findOneCollectorFileByCondition")
    CollectorFile findOneCollectorFileByCondition(CollectorFile collectorFile);
    @PostMapping("/findCollectorFileCountByCondition")
    long findCollectorFileCountByCondition(CollectorFile collectorFile);
    @PostMapping("/updateCollectorFile")
    void updateCollectorFile(CollectorFile collectorFile);
    @GetMapping("/deleteCollectorFile/{id}")
    void deleteCollectorFile(@PathVariable("id") String id);
    @PostMapping("/deleteCollectorFileByCondition")
    void deleteCollectorFileByCondition(CollectorFile collectorFile);

    @PostMapping("/findCollectorFilesByConditionToApp")
    List<PrepLessonResourceFile> findCollectorFilesByConditionToApp(CollectorFile collectorFile);
    @PostMapping("/findCollectorFileCountByConditionToApp")
    long findCollectorFileCountByConditionToApp(CollectorFile collectorFile);
}
