package com.yice.edu.cn.bmp.service.stuEvaluate;

import com.yice.edu.cn.bmp.feignClient.stuEvaluate.StuEvaluateContentFeign;
import com.yice.edu.cn.common.pojo.jw.stuEvaluate.StuEvaluateContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StuEvaluateContentService {
    @Autowired
    private StuEvaluateContentFeign stuEvaluateContentFeign;

    public List<StuEvaluateContent> findStuEvaluateContentListByCondition(StuEvaluateContent stuEvaluateContent) {
        return stuEvaluateContentFeign.findStuEvaluateContentListByCondition(stuEvaluateContent);
    }

    public long findStuEvaluateContentCountByCondition(StuEvaluateContent stuEvaluateContent) {
        return stuEvaluateContentFeign.findStuEvaluateContentCountByCondition(stuEvaluateContent);
    }
}
