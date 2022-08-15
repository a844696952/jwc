package com.yice.edu.cn.bmp.service.topics;

import com.yice.edu.cn.bmp.feignClient.topics.WrongTopicsFeign;
import com.yice.edu.cn.common.pojo.jy.topics.WrongTopics;
import com.yice.edu.cn.common.pojo.jy.topics.app.WrongTopicsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.myStudentId;

@Service
public class WrongTopicsService {
    @Autowired
    private WrongTopicsFeign wrongTopicsFeign;

    public WrongTopics findWrongTopicsById(String id) {
        return wrongTopicsFeign.findWrongTopicsById(id);
    }

    public WrongTopics saveWrongTopics(WrongTopics wrongTopics) {
        return wrongTopicsFeign.saveWrongTopics(wrongTopics);
    }

    public List<WrongTopics> findWrongTopicsListByCondition(WrongTopics wrongTopics) {
        return wrongTopicsFeign.findWrongTopicsListByCondition(wrongTopics);
    }

    public WrongTopics findOneWrongTopicsByCondition(WrongTopics wrongTopics) {
        return wrongTopicsFeign.findOneWrongTopicsByCondition(wrongTopics);
    }

    public long findWrongTopicsCountByCondition(WrongTopicsVo wrongTopicsVo) {
        WrongTopics wrongTopics = new WrongTopics();
        wrongTopics.setStudentId(myStudentId());
        wrongTopics.setSubjectId(wrongTopicsVo.getSubjectId());
        return wrongTopicsFeign.findWrongTopicsCountByCondition(wrongTopics);
    }

    public void updateWrongTopics(WrongTopics wrongTopics) {
        wrongTopicsFeign.updateWrongTopics(wrongTopics);
    }

    public void deleteWrongTopics(String id) {
        wrongTopicsFeign.deleteWrongTopics(id);
    }

    public void deleteWrongTopicsByCondition(WrongTopics wrongTopics) {
        wrongTopicsFeign.deleteWrongTopicsByCondition(wrongTopics);
    }
}
