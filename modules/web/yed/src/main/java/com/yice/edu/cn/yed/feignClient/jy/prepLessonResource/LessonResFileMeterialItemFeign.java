package com.yice.edu.cn.yed.feignClient.jy.prepLessonResource;

import com.yice.edu.cn.common.pojo.jy.prepLessonResource.LessonResFileMeterialItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "lessonResFileMeterialItemFeign",path = "/lessonResFileMeterialItem")
public interface LessonResFileMeterialItemFeign {
    @GetMapping("/findLessonResFileMeterialItemById/{id}")
    LessonResFileMeterialItem findLessonResFileMeterialItemById(@PathVariable("id") String id);
    @PostMapping("/saveLessonResFileMeterialItem")
    LessonResFileMeterialItem saveLessonResFileMeterialItem(LessonResFileMeterialItem lessonResFileMeterialItem);
    @PostMapping("/findLessonResFileMeterialItemListByCondition")
    List<LessonResFileMeterialItem> findLessonResFileMeterialItemListByCondition(LessonResFileMeterialItem lessonResFileMeterialItem);
    @PostMapping("/findOneLessonResFileMeterialItemByCondition")
    LessonResFileMeterialItem findOneLessonResFileMeterialItemByCondition(LessonResFileMeterialItem lessonResFileMeterialItem);
    @PostMapping("/findLessonResFileMeterialItemCountByCondition")
    long findLessonResFileMeterialItemCountByCondition(LessonResFileMeterialItem lessonResFileMeterialItem);
    @PostMapping("/updateLessonResFileMeterialItem")
    void updateLessonResFileMeterialItem(LessonResFileMeterialItem lessonResFileMeterialItem);
    @GetMapping("/delete/deleteLessonResFileMeterialItem/{id}")
    void deleteLessonResFileMeterialItem(@PathVariable("id") String id);
    @PostMapping("/delete/deleteLessonResFileMeterialItemByCondition")
    void deleteLessonResFileMeterialItemByCondition(LessonResFileMeterialItem lessonResFileMeterialItem);


}
