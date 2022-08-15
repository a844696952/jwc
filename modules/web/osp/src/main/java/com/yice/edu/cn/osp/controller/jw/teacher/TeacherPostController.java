package com.yice.edu.cn.osp.controller.jw.teacher;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherPost;
import com.yice.edu.cn.osp.service.jw.classes.JwClassesService;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherPostService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/teacherPost")
public class TeacherPostController {
    @Autowired
    private TeacherPostService teacherPostService;
    @Autowired
    private JwClassesService classesService;

    @PostMapping("/saveTeacherPost")
    @ApiOperation(value = "保存教师职务对象", notes = "返回保存好的教师职务对象", response=TeacherPost.class)
    public ResponseJson saveTeacherPost(
            @ApiParam(value = "教师职务对象", required = true)
            @RequestBody TeacherPost teacherPost){
        teacherPost.setSchoolId(mySchoolId());
        return new ResponseJson(teacherPostService.editTeacherPost(teacherPost));
    }

    @GetMapping("/deleteTeacherPost/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteTeacherPost(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        teacherPostService.deleteTeacherPost(id);
        return new ResponseJson();
    }

    /**
     * 查询职务对应的教师
     * 校长、教务主任、总务主任、心理辅导
     * @return
     */
    @GetMapping("/findTeacherByPost/{post}")
    @ApiOperation(value = "查询教师职务列表", response=TeacherPost.class)
    public ResponseJson findTeacherByPost(@PathVariable("post") String post){
        TeacherPost teacherPost = new TeacherPost();
        teacherPost.setSchoolId(mySchoolId());
        teacherPost.setDdId(post);
        return new ResponseJson(teacherPostService.findTeacherListByPost(teacherPost));
    }

    @GetMapping("/findClassesByGradeId/{gradeId}")
    @ApiOperation(value = "通过年级查询班级", notes = "返回响应对象")
    public ResponseJson findClassesByGradeId(@PathVariable String gradeId) {
        JwClasses c  = new JwClasses();
        c.setGradeId(gradeId);
        c.setSchoolId(mySchoolId());
        return new ResponseJson(classesService.findJwClassesListByCondition(c));
    }
    @GetMapping("/findTeachers4Grade")
    @ApiOperation(value = "学校查询各个年段年段长", notes = "返回响应对象",response=TeacherPost.class)
    public ResponseJson findTeachers4Grade() {
        return new ResponseJson(teacherPostService.findTeachers4Grade(mySchoolId()));
    }
    @PostMapping("/findTeachers4Class")
    @ApiOperation(value = "学校查询各个班级班主任", notes = "返回响应对象",response=TeacherPost.class)
    public ResponseJson findTeachers4Class(@RequestBody TeacherPost teacherPost) {
        teacherPost.setSchoolId(mySchoolId());
        return new ResponseJson(teacherPostService.findTeachers4Class(teacherPost));
    }
}
