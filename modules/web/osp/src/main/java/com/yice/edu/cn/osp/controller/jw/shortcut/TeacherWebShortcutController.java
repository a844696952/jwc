package com.yice.edu.cn.osp.controller.jw.shortcut;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.shortcut.TeacherWebShortcut;
import com.yice.edu.cn.osp.service.jw.shortcut.TeacherWebShortcutService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/teacherWebShortcut")
@Api(value = "/teacherWebShortcut",description = "用户首页快捷应用模块")
public class TeacherWebShortcutController {
    @Autowired
    private TeacherWebShortcutService teacherWebShortcutService;

    @PostMapping("/ignore/findTeacherWebShortcutList")
    @ApiOperation(value = "根据条件查找用户首页快捷应用列表", notes = "返回响应对象,不包含总条数", response=TeacherWebShortcut.class)
    public ResponseJson findTeacherWebShortcutList(){
        TeacherWebShortcut teacherWebShortcut = new TeacherWebShortcut();
        teacherWebShortcut.setSchoolId(mySchoolId());
        teacherWebShortcut.setTeacherId(myId());
        List<TeacherWebShortcut> data=teacherWebShortcutService.findTeacherWebShortcutListByCondition(teacherWebShortcut);
        return new ResponseJson(data);
    }
    @PostMapping("/ignore/updateTeacherWebShortcut4List")
    @ApiOperation(value = "修改用户首页快捷应用对象所有非@Transient注释的字段", notes = "返回响应对象")
    public ResponseJson updateTeacherWebShortcut4List(@RequestBody List<TeacherWebShortcut> teacherWebShortcutList){
        teacherWebShortcutService.updateTeacherWebShortcut4List(myId(),teacherWebShortcutList);
        return new ResponseJson();
    }

}
