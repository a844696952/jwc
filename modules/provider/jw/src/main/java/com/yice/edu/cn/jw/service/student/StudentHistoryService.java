package com.yice.edu.cn.jw.service.student;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.jw.student.StudentHistory;
import com.yice.edu.cn.jw.dao.student.IStudentDao;
import com.yice.edu.cn.jw.service.classes.JwClassesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StudentHistoryService {

    @Autowired
    private MongoTemplate mot;
    @Autowired
    private IStudentDao studentDao;
    @Autowired
    private SequenceId sequenceId;

    /**
     * 对一个学校的学生进行归档操作
     * @param schoolId
     */
    public void archiveStudentHistory(String schoolId,CurSchoolYear curSchoolYear){
        List<StudentHistory> studentHistoryList = studentDao.findStudentHistoryListBySchoold(schoolId);
        studentHistoryList.forEach(studentHistory -> {
           studentHistory.setSchoolYearId(curSchoolYear.getSchoolYearId());
           studentHistory.setFromTo(curSchoolYear.getFromTo());
           studentHistory.setTerm(curSchoolYear.getTerm());
           studentHistory.setCreateTime(DateUtil.now());
           studentHistory.setId(sequenceId.nextId());
        });
        JwClassesService.getExecutor().execute(() -> {
//         mot.insertAll(studentHistoryList);
         studentHistoryList.forEach(s->{mot.save(s);});
        });
    }

}
