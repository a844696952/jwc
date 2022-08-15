package com.yice.edu.cn.osp.feignClient.jw.teacher;

import com.yice.edu.cn.common.pojo.general.node.Node;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "jw",contextId = "teacherExtFeign",path = "/teacherExt")
public interface TeacherExtFeign {
    @PostMapping("/searchTeachersByName")
    List<Node> searchTeachersByName(Teacher teacher);
}
