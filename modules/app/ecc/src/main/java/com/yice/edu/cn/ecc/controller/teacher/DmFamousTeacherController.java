package com.yice.edu.cn.ecc.controller.teacher;


import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.teacher.DmFamousTeacher;
import com.yice.edu.cn.ecc.service.teacher.DmFamousTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static com.yice.edu.cn.ecc.interceptor.LoginInterceptor.mySchoolId;


@RestController
@RequestMapping("/dmFamousTeacher")
@Api(value = "/dmFamousTeacher",description = "名师风采表模块")
public class DmFamousTeacherController {
    @Autowired
    private DmFamousTeacherService dmFamousTeacherService;


    @GetMapping("/findDmFamousTeacherById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找名师风采表", notes = "返回响应对象", response=DmFamousTeacher.class)
    public ResponseJson findDmFamousTeacherById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DmFamousTeacher dmFamousTeacher=dmFamousTeacherService.findDmFamousTeacherById(id);
        return new ResponseJson(dmFamousTeacher);
    }


    @PostMapping("/findDmFamousTeachersByCondition")
    @ApiOperation(value = "根据条件查找名师风采表", notes = "返回响应对象", response=DmFamousTeacher.class)
    public ResponseJson findDmFamousTeachersByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmFamousTeacher dmFamousTeacher){
        dmFamousTeacher.setSchoolId(mySchoolId());
        List<DmFamousTeacher> data=dmFamousTeacherService.findDmFamousTeacherListByCondition(dmFamousTeacher);
        long count=dmFamousTeacherService.findDmFamousTeacherCountByCondition(dmFamousTeacher);
        return new ResponseJson(data,count);
    }

}
