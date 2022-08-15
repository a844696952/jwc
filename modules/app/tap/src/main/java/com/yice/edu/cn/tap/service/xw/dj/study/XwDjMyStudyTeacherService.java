package com.yice.edu.cn.tap.service.xw.dj.study;

import com.yice.edu.cn.common.pojo.xw.dj.XwDjMyStudyTeacher;
import com.yice.edu.cn.tap.feignClient.xw.dj.study.XwDjMyStudyTeacherFeign;
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
}
