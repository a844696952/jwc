package com.yice.edu.cn.tap.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/login")
public class ArticleController {

    @GetMapping("/toListArticles")
    public String toListArticles(Model model){
        List<String> list = new ArrayList<>();
        list.add("lily");
        list.add("lucy");
        list.add("meimei");
        list.add("gege");
        model.addAttribute("list", list);
        return "views/article/listArticle.html";
    }
}
