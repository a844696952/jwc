package com.yice.edu.cn.osp.service.jw.stuEvaluate;

import com.yice.edu.cn.common.pojo.jw.stuEvaluate.StuEvaluateContent;
import com.yice.edu.cn.osp.feignClient.jw.stuEvaluate.StuEvaluateContentFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StuEvaluateContentService {
    @Autowired
    private StuEvaluateContentFeign stuEvaluateContentFeign;

    public StuEvaluateContent findStuEvaluateContentById(String id) {
        return stuEvaluateContentFeign.findStuEvaluateContentById(id);
    }

    public StuEvaluateContent saveStuEvaluateContent(StuEvaluateContent stuEvaluateContent) {
        return stuEvaluateContentFeign.saveStuEvaluateContent(stuEvaluateContent);
    }

    public List<StuEvaluateContent> findStuEvaluateContentListByCondition(StuEvaluateContent stuEvaluateContent) {
        return stuEvaluateContentFeign.findStuEvaluateContentListByCondition(stuEvaluateContent);
    }

    public StuEvaluateContent findOneStuEvaluateContentByCondition(StuEvaluateContent stuEvaluateContent) {
        return stuEvaluateContentFeign.findOneStuEvaluateContentByCondition(stuEvaluateContent);
    }

    public long findStuEvaluateContentCountByCondition(StuEvaluateContent stuEvaluateContent) {
        return stuEvaluateContentFeign.findStuEvaluateContentCountByCondition(stuEvaluateContent);
    }

    public void updateStuEvaluateContent(StuEvaluateContent stuEvaluateContent) {
        stuEvaluateContentFeign.updateStuEvaluateContent(stuEvaluateContent);
    }

    public void deleteStuEvaluateContent(String id) {
        stuEvaluateContentFeign.deleteStuEvaluateContent(id);
    }

    public void deleteStuEvaluateContentByCondition(StuEvaluateContent stuEvaluateContent) {
        stuEvaluateContentFeign.deleteStuEvaluateContentByCondition(stuEvaluateContent);
    }
}
