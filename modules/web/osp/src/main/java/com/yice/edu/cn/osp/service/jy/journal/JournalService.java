package com.yice.edu.cn.osp.service.jy.journal;

import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.pojo.jy.journal.Journal;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.osp.feignClient.jw.teacher.TeacherClassesFeign;
import com.yice.edu.cn.osp.feignClient.jy.journal.JournalFeign;
import com.yice.edu.cn.osp.feignClient.jy.sensitiveWord.SensitiveWordFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class JournalService {
    @Autowired
    private JournalFeign journalFeign;
    @Autowired
    private SensitiveWordFeign sensitiveWordFeign;
    @Autowired
    private TeacherClassesFeign teacherClassesFeign;
    public Journal findJournalById(String id) {
        return journalFeign.findJournalById(id);
    }

    public Journal saveJournal(Journal journal) {
        return journalFeign.saveJournal(journal);
    }

    public List<Journal> findJournalListByCondition(Journal journal) {
        return journalFeign.findJournalListByCondition(journal);
    }

    public Journal findOneJournalByCondition(Journal journal) {
        return journalFeign.findOneJournalByCondition(journal);
    }

    public long findJournalCountByCondition(Journal journal) {
        return journalFeign.findJournalCountByCondition(journal);
    }

    public void updateJournal(Journal journal) {
        journalFeign.updateJournal(journal);
    }

    public void deleteJournal(String id) {
        journalFeign.deleteJournal(id);
    }

    public void deleteJournalByCondition(Journal journal) {
        journalFeign.deleteJournalByCondition(journal);
    }

    public List<Journal> findJournalsForMyIndex(Journal journal) {
        List<TeacherClasses> tcs=teacherClassesFeign.findTeacherClassesByTeacher(myId());
        journal.setTcs(tcs);
        journal.setUserId(myId());
        journal.setFromTeacher(true);
        journal.setSchoolId(mySchoolId());
        return journalFeign.findJournalsForMyIndex(journal);
    }

    public void clickThumb(String sqId, String teacherId) {
        journalFeign.clickThumb(sqId,teacherId);
    }

    public boolean saveJournal2(Journal journal) {
        //保存日志前先判断是否含有敏感词
        if(StrUtil.isNotEmpty(journal.getText())){
            boolean has=sensitiveWordFeign.hasSensitiveWord(journal);
//            String text=sensitiveWordFeign.replaceSensitive(journal.getText());
            if(has){
                return true;
            }
        }
        journalFeign.saveJournal(journal);
        return false;
    }
}
