package com.yice.edu.cn.ecc.controller.honourRoll;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.honourRoll.DmHonourRollStudent;
import com.yice.edu.cn.ecc.service.honourRoll.DmHonourRollStudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/dmHonourRollStudent")
@Api(value = "/dmHonourRollStudent",description = "光荣榜，学生获得者模块")
public class DmHonourRollStudentController {
    @Autowired
    private DmHonourRollStudentService dmHonourRollStudentService;

}
