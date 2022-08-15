package com.yice.edu.cn.xw.dao.dj;

import java.util.List;

import com.yice.edu.cn.common.dto.xw.StudyTeacherDto;
import com.yice.edu.cn.common.pojo.xw.dj.XwDjMyStudyTeacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IXwDjMyStudyTeacherDao {
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<XwDjMyStudyTeacher> findXwDjMyStudyTeacherListByCondition(XwDjMyStudyTeacher xwDjMyStudyTeacher);

    long findXwDjMyStudyTeacherCountByCondition(XwDjMyStudyTeacher xwDjMyStudyTeacher);

    XwDjMyStudyTeacher findOneXwDjMyStudyTeacherByCondition(XwDjMyStudyTeacher xwDjMyStudyTeacher);

    XwDjMyStudyTeacher findXwDjMyStudyTeacherById(@Param("id") String id);

    void saveXwDjMyStudyTeacher(XwDjMyStudyTeacher xwDjMyStudyTeacher);

    void updateXwDjMyStudyTeacher(XwDjMyStudyTeacher xwDjMyStudyTeacher);

    void deleteXwDjMyStudyTeacher(@Param("id") String id);

    void deleteXwDjMyStudyTeacherByCondition(XwDjMyStudyTeacher xwDjMyStudyTeacher);

    void batchSaveXwDjMyStudyTeacher(List<XwDjMyStudyTeacher> xwDjMyStudyTeachers);

    List<StudyTeacherDto> findXwDjMyStudyTeacherListByTeacherId(StudyTeacherDto studyTeacherDto);

    long findXwDjMyStudyTeacherCountByTeacherId(StudyTeacherDto studyTeacherDto);

    List<XwDjMyStudyTeacher> findXwDjMyStudyTeacherByTime(XwDjMyStudyTeacher xwDjMyStudyTeacher);

    long findXwDjMyStudyTeacherCountByTime(XwDjMyStudyTeacher xwDjMyStudyTeacher);
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
