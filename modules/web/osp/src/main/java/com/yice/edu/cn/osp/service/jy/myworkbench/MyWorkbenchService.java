package com.yice.edu.cn.osp.service.jy.myworkbench;

import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassSchedule;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.jy.journal.Journal;
import com.yice.edu.cn.common.pojo.jy.journal.NewestJournal;
import com.yice.edu.cn.osp.feignClient.jw.classSchedule.ClassScheduleFeign;
import com.yice.edu.cn.osp.feignClient.jw.teacher.TeacherClassesFeign;
import com.yice.edu.cn.osp.feignClient.jy.journal.JournalFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class MyWorkbenchService {
    @Autowired
    private ClassScheduleFeign classScheduleFeign;
    @Autowired
    private JournalFeign journalFeign;
    @Autowired
    private TeacherClassesFeign teacherClassesFeign;


    public List<List<ClassSchedule>> findList(@RequestBody ClassSchedule classSchedule) {
        return classScheduleFeign.findList(classSchedule);
    }

    public NewestJournal findNewestJournalsForWorkbench() {
        List<TeacherClasses> tcs=teacherClassesFeign.findTeacherClassesByTeacher(myId());
        Journal journal = new Journal();
        journal.setTcs(tcs);
        journal.setUserId(myId());
        journal.setSchoolId(mySchoolId());
        return journalFeign.findNewestJournalsForWorkbench(journal);
    }
}
