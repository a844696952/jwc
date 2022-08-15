package com.yice.edu.cn.tap.controller.microLesson;

import com.yice.edu.cn.common.pojo.wk.MicroLesson;
import com.yice.edu.cn.tap.service.microLesson.MicroLessonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/MicroLessonController")
@Api(value = "/MicroLessonController", description = "微课doc下载模块")
public class MicroLessonController {

    @Autowired
    private MicroLessonService microLessonService;

    @PostMapping("/downFile")
    @ApiOperation(value = "文件下载", notes = "返回下载的文件流")
    public void downFile(@RequestBody MicroLesson microLesson, HttpServletResponse response){
        try {
            microLessonService.downFile(microLesson.getContent(),microLesson.getFileName(),response);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


}
