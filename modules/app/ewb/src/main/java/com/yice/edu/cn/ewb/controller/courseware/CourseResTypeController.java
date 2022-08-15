package com.yice.edu.cn.ewb.controller.courseware;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.courseware.ResType;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/courseResType")
@Api(value = "/courseResType",description = "课件资源类型")
public class CourseResTypeController {

    @GetMapping("/list")
    public ResponseJson list(){

        final List<ResType.Label> labels = ResType.getLabels();
        return new ResponseJson(labels);

    }
}
