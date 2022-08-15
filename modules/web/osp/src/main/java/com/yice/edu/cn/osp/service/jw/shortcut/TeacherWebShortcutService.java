package com.yice.edu.cn.osp.service.jw.shortcut;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.Cached;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.shortcut.TeacherWebShortcut;
import com.yice.edu.cn.osp.feignClient.jw.shortcut.TeacherWebShortcutFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class TeacherWebShortcutService {
    @Autowired
    private TeacherWebShortcutFeign teacherWebShortcutFeign;

    public List<TeacherWebShortcut> findTeacherWebShortcutListByCondition(TeacherWebShortcut teacherWebShortcut) {
        return teacherWebShortcutFeign.findTeacherWebShortcutListByCondition(teacherWebShortcut);
    }

    @Cached(name = "teacher_web_shortcut_",key = "#teacherId",expire = 10000)
    public List<TeacherWebShortcut> findTeacherWebShortcut4Index(String teacherId){
        TeacherWebShortcut teacherWebShortcut = new TeacherWebShortcut();
        teacherWebShortcut.setTeacherId(teacherId);
        teacherWebShortcut.setPager(new Pager().setPaging(false).setSortField("sort"));
        return teacherWebShortcutFeign.findTeacherWebShortcutListByCondition(teacherWebShortcut);
    }

    @CacheInvalidate(name = "teacher_web_shortcut_",key = "#teacherId")
    public void updateTeacherWebShortcut4List(String teacherId,List<TeacherWebShortcut> teacherWebShortcutList) {
        teacherWebShortcutList.stream().forEach(teacherWebShortcut -> teacherWebShortcut.setSchoolId(mySchoolId()));
        teacherWebShortcutFeign.updateTeacherWebShortcut4List(teacherId,teacherWebShortcutList);
    }
}
