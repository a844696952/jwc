package com.yice.edu.cn.ewb.feignClient.prepareLessons;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.yice.edu.cn.common.pojo.jy.prepareLessons.TextbookSetting;



@FeignClient(value="jy",contextId = "textbookSettingFeign",path = "/textbookSetting")
public interface TextbookSettingFeign {

    @GetMapping("/findTextbookSettingById/{id}")
    public List<TextbookSetting> findTextbookSettingByTeacherId(@PathVariable("id") String id);

    
    @GetMapping("/findLastSettingbyTeacherId/{teacherId}")
    public TextbookSetting findLastSettingbyTeacherId(@PathVariable("teacherId") String teacherId);
    
}
