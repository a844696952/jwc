package com.yice.edu.cn.tap.controller.homework;

import com.yice.edu.cn.tap.service.topics.TopicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 直接通过题目id返回题目详情 html页面
 */
@Controller
@RequestMapping("/login/")
public class TopicsPageController {
    @Autowired
    private TopicsService topicsService;
    @RequestMapping("/details/{topicsId}")
    public String details(@PathVariable("topicsId") String topicsId, Model model){
        model.addAttribute("topics",topicsService.findTopicsById(topicsId));
        return "views/topics/details.html";
    }
}
