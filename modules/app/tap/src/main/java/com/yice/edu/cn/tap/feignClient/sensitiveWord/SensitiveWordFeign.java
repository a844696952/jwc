package com.yice.edu.cn.tap.feignClient.sensitiveWord;

import com.yice.edu.cn.common.pojo.jy.journal.Journal;
import com.yice.edu.cn.common.pojo.jy.sensitiveWord.SensitiveWord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value="jy",path = "/sensitiveWord")
public interface SensitiveWordFeign {
    @GetMapping("/findSensitiveWordById/{id}")
    SensitiveWord findSensitiveWordById(@PathVariable("id") String id);
    @PostMapping("/saveSensitiveWord")
    SensitiveWord saveSensitiveWord(SensitiveWord sensitiveWord);
    @PostMapping("/findSensitiveWordListByCondition")
    List<SensitiveWord> findSensitiveWordListByCondition(SensitiveWord sensitiveWord);
    @PostMapping("/findOneSensitiveWordByCondition")
    SensitiveWord findOneSensitiveWordByCondition(SensitiveWord sensitiveWord);
    @PostMapping("/findSensitiveWordCountByCondition")
    long findSensitiveWordCountByCondition(SensitiveWord sensitiveWord);
    @PostMapping("/updateSensitiveWord")
    void updateSensitiveWord(SensitiveWord sensitiveWord);
    @GetMapping("/deleteSensitiveWord/{id}")
    void deleteSensitiveWord(@PathVariable("id") String id);
    @PostMapping("/deleteSensitiveWordByCondition")
    void deleteSensitiveWordByCondition(SensitiveWord sensitiveWord);
    @PostMapping("/replaceSensitive")
    String replaceSensitive(@RequestParam("text") String text);
    @PostMapping("/hasSensitiveWord")
    boolean hasSensitiveWord(Journal journal);
}
