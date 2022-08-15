package com.yice.edu.cn.ecc.controller.teacher;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher4Classes;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.ecc.service.classes.JwClassesService;
import com.yice.edu.cn.ecc.service.teacher.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/teacher")
@Api(value = "教师控制器")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private JwClassesService jwClassesService;

    @ApiOperation(value = "教师风采", notes = "返回教师列表")
    @PostMapping(value="/findTeacherListByCondition")
    public ResponseJson findTeacherListByCondition(
            @ApiParam(value="班级信息")
            @RequestBody TeacherClasses teacherClasses
    ) {
        List<Teacher4Classes> list1 = new ArrayList<>();
        List<Teacher4Classes> list2 = new ArrayList<>();
        List<Teacher4Classes> list3 = new ArrayList<>();
        List<Teacher4Classes> list = jwClassesService.findClassTeacherListByClasses(teacherClasses);
        list.stream().forEach(e->{
            if(StringUtils.isEmpty(e.getCourseName())){
                e.setCourseName("暂无任课");
            }
            if(StringUtils.isEmpty(e.getPost())){
                e.setPost("普通教师");
            }
        });
        list.stream().forEach(e->{
            if(e.getPost().indexOf("班主任")>-1){
                list1.add(e);
            }else{
                list2.add(e);
            }
        });
        list3.addAll(list1);
        list3.addAll(list2);
        return new ResponseJson(list3);
    }
}
