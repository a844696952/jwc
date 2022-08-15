package com.yice.edu.cn.api.controller.open;

import cn.hutool.core.util.URLUtil;
import com.yice.edu.cn.api.service.student.StudentService;
import com.yice.edu.cn.api.service.teacher.TeacherService;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@RestController
@RequestMapping("/open")
public class OpenRestController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;
    @GetMapping("/teacherHeadImg/{id}")
    public void teacherHeadImg(@PathVariable String id, HttpServletResponse response) throws IOException {
        Teacher t = teacherService.findTeacherById(id);
        if(t==null){
            return;
        }
        String url= Constant.RES_PRE+t.getImgUrl();
        InputStream is = URLUtil.getStream(new URL(url));
        ServletOutputStream os = response.getOutputStream();
        byte[] b=new byte[1024];
        int i;
        while((i=is.read(b))!=-1){
            os.write(b,0,i);
        }
        os.flush();
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @GetMapping("/studentHeadImg/{id}")
    public void studentHeadImg(@PathVariable String id, HttpServletResponse response) throws IOException {
        Student t = studentService.findStudentById(id);
        if(t==null){
            return;
        }
        String url= Constant.RES_PRE+t.getImgUrl();
        InputStream is = URLUtil.getStream(new URL(url));
        ServletOutputStream os = response.getOutputStream();
        byte[] b=new byte[1024];
        int i;
        while((i=is.read(b))!=-1){
            os.write(b,0,i);
        }
        os.flush();
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
