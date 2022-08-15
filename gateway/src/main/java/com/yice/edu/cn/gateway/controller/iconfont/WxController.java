package com.yice.edu.cn.gateway.controller.iconfont;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WxController {

    @GetMapping(value="/7E0EO4OGUs.txt",produces = MediaType.TEXT_PLAIN_VALUE)
    public String teacher(){
        return "093bb04e714039e064a127ded2e86799";
    }
    @GetMapping(value="/pLAm0NvQHb.txt",produces = MediaType.TEXT_PLAIN_VALUE)
    public String student(){
        return "5a8d33871231ae9f7223fc48efea6b57";
    }
    @GetMapping(value="/jJ9UFfCk5I.txt",produces = MediaType.TEXT_PLAIN_VALUE)
    public String dyTeacher(){return "c8b9dbe7402cd18866df2bccd9d2c6f1";}
    @GetMapping(value="/xtNp8GjqZF.txt",produces = MediaType.TEXT_PLAIN_VALUE)
    public String dyStudent(){return "4897fb4b4c6166f28efcf90fe0d26349";}
}
