package com.yice.edu.cn.osp.feignClient.jw.shortcut;

import com.yice.edu.cn.common.pojo.jw.shortcut.TeacherWebShortcut;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "teacherWebShortcutFeign",path = "/teacherWebShortcut")
public interface TeacherWebShortcutFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findTeacherWebShortcutById/{id}")
    TeacherWebShortcut findTeacherWebShortcutById(@PathVariable("id") String id);
    @PostMapping("/saveTeacherWebShortcut")
    TeacherWebShortcut saveTeacherWebShortcut(TeacherWebShortcut teacherWebShortcut);
    @PostMapping("/findTeacherWebShortcutListByCondition")
    List<TeacherWebShortcut> findTeacherWebShortcutListByCondition(TeacherWebShortcut teacherWebShortcut);
    @PostMapping("/findOneTeacherWebShortcutByCondition")
    TeacherWebShortcut findOneTeacherWebShortcutByCondition(TeacherWebShortcut teacherWebShortcut);
    @PostMapping("/findTeacherWebShortcutCountByCondition")
    long findTeacherWebShortcutCountByCondition(TeacherWebShortcut teacherWebShortcut);
    @PostMapping("/updateTeacherWebShortcut")
    void updateTeacherWebShortcut(TeacherWebShortcut teacherWebShortcut);
    @PostMapping("/updateTeacherWebShortcutForNotNull")
    void updateTeacherWebShortcutForNotNull(TeacherWebShortcut teacherWebShortcut);
    @GetMapping("/deleteTeacherWebShortcut/{id}")
    void deleteTeacherWebShortcut(@PathVariable("id") String id);
    @PostMapping("/deleteTeacherWebShortcutByCondition")
    void deleteTeacherWebShortcutByCondition(TeacherWebShortcut teacherWebShortcut);
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @PostMapping("/updateTeacherWebShortcut4List/{teacherId}")
    void updateTeacherWebShortcut4List(@PathVariable("teacherId") String teacherId,List<TeacherWebShortcut> teacherWebShortcutList);
}
