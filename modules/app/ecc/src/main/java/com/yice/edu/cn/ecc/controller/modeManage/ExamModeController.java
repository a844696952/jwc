package com.yice.edu.cn.ecc.controller.modeManage;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.modeManage.ExamMode;
import com.yice.edu.cn.ecc.service.modeManage.ExamModeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/examMode")
@Api(value = "/examMode",description = "考试模式表模块")
public class ExamModeController {


    @Autowired
    private ExamModeService examModeService;

    @PostMapping("/findByCExamModeByTime")
    @ApiOperation(value = "查询当前时间范围内的考试模式", notes = "考试模式表对象必传")
    public ResponseJson  findByCExamModeByTime(
            @ApiParam(value = "考试模式表对象", required = true)
            @RequestBody ExamMode exam){
        List<ExamMode> mode = examModeService.findByCExamModeByTime(exam);
        return new ResponseJson(mode);
    }


}

