package com.yice.edu.cn.osp.service.xw.dj;

import com.yice.edu.cn.common.dto.xw.StudyTeacherDto;
import com.yice.edu.cn.common.pojo.xw.dj.XwDjMyStudyTeacher;
import com.yice.edu.cn.osp.feignClient.xw.dj.XwDjMyStudyTeacherFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XwDjMyStudyTeacherService {
    @Autowired
    private XwDjMyStudyTeacherFeign xwDjMyStudyTeacherFeign;

    public XwDjMyStudyTeacher findXwDjMyStudyTeacherById(String id) {
        return xwDjMyStudyTeacherFeign.findXwDjMyStudyTeacherById(id);
    }

    public XwDjMyStudyTeacher saveXwDjMyStudyTeacher(XwDjMyStudyTeacher xwDjMyStudyTeacher) {
        return xwDjMyStudyTeacherFeign.saveXwDjMyStudyTeacher(xwDjMyStudyTeacher);
    }

    public List<XwDjMyStudyTeacher> findXwDjMyStudyTeacherListByCondition(XwDjMyStudyTeacher xwDjMyStudyTeacher) {
        return xwDjMyStudyTeacherFeign.findXwDjMyStudyTeacherListByCondition(xwDjMyStudyTeacher);
    }

    public XwDjMyStudyTeacher findOneXwDjMyStudyTeacherByCondition(XwDjMyStudyTeacher xwDjMyStudyTeacher) {
        return xwDjMyStudyTeacherFeign.findOneXwDjMyStudyTeacherByCondition(xwDjMyStudyTeacher);
    }

    public long findXwDjMyStudyTeacherCountByCondition(XwDjMyStudyTeacher xwDjMyStudyTeacher) {
        return xwDjMyStudyTeacherFeign.findXwDjMyStudyTeacherCountByCondition(xwDjMyStudyTeacher);
    }

    public void updateXwDjMyStudyTeacher(XwDjMyStudyTeacher xwDjMyStudyTeacher) {
        xwDjMyStudyTeacherFeign.updateXwDjMyStudyTeacher(xwDjMyStudyTeacher);
    }

    public void deleteXwDjMyStudyTeacher(String id) {
        xwDjMyStudyTeacherFeign.deleteXwDjMyStudyTeacher(id);
    }

    public void deleteXwDjMyStudyTeacherByCondition(XwDjMyStudyTeacher xwDjMyStudyTeacher) {
        xwDjMyStudyTeacherFeign.deleteXwDjMyStudyTeacherByCondition(xwDjMyStudyTeacher);
    }

    public List<StudyTeacherDto> findXwDjMyStudyTeacherListByTeacherId(StudyTeacherDto studyTeacherDto) {
        return xwDjMyStudyTeacherFeign.findXwDjMyStudyTeacherListByTeacherId(studyTeacherDto);
    }

    public long findXwDjMyStudyTeacherCountByTeacherId(StudyTeacherDto studyTeacherDto) {
        return xwDjMyStudyTeacherFeign.findXwDjMyStudyTeacherCountByTeacherId(studyTeacherDto);
    }

    public List<XwDjMyStudyTeacher> findXwDjMyStudyTeacherListByStudyResourceId(XwDjMyStudyTeacher xwDjMyStudyTeacher) {
        return xwDjMyStudyTeacherFeign.findXwDjMyStudyTeacherListByStudyResourceId(xwDjMyStudyTeacher);
    }

    public List<XwDjMyStudyTeacher> findXwDjMyStudyTeacherByTime(XwDjMyStudyTeacher xwDjMyStudyTeacher) {
        return xwDjMyStudyTeacherFeign.findXwDjMyStudyTeacherByTime(xwDjMyStudyTeacher);
    }

    public long findXwDjMyStudyTeacherCountByTime(XwDjMyStudyTeacher xwDjMyStudyTeacher) {
        return xwDjMyStudyTeacherFeign.findXwDjMyStudyTeacherCountByTime(xwDjMyStudyTeacher);
    }
}
