package com.yice.edu.cn.osp.service.jy.topics;

import cn.hutool.core.bean.BeanUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.ResourceVo;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.SchoolQusBank;
import com.yice.edu.cn.common.pojo.jy.source21.model.question.SearchParam;
import com.yice.edu.cn.common.pojo.jy.topics.TopicParam;
import com.yice.edu.cn.common.pojo.jy.topics.Topics;
import com.yice.edu.cn.osp.feignClient.jw.qusBankResource.SchoolQusBankFeign;
import com.yice.edu.cn.osp.feignClient.jy.topics.TopicsFeign;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class TopicsService {
    @Autowired
    private TopicsFeign topicsFeign;
    @Autowired
    private SchoolQusBankFeign schoolQusBankFeign;

    public Topics findTopicsById(String id) {
        return topicsFeign.findTopicsById(id);
    }

    public Topics saveTopics(Topics topics) {
        return topicsFeign.saveTopics(topics);
    }

    public List<Topics> findTopicsListByCondition(Topics topics) {
        return topicsFeign.findTopicsListByCondition(topics);
    }
    public List<Topics> findTopicsListByCondition4Muti(Topics topics) {
        return topicsFeign.findTopicsListByCondition4Muti(topics);
    }
    public Long findTopicsCountByCondition4Muti(Topics topics) {
        return topicsFeign.findTopicsCountByCondition4Muti(topics);
    }

    public Topics findOneTopicsByCondition(Topics topics) {
        return topicsFeign.findOneTopicsByCondition(topics);
    }

    public long findTopicsCountByCondition(Topics topics) {
        return topicsFeign.findTopicsCountByCondition(topics);
    }

    public void updateTopics(Topics topics) {
        topicsFeign.updateTopics(topics);
    }

    public void deleteTopics(String id) {
        topicsFeign.deleteTopics(id);
    }

    public void deleteTopicsByCondition(Topics topics) {
        topicsFeign.deleteTopicsByCondition(topics);
    }

    /**
     * 公告查询接口
     * @param searchParam
     * @return
     */
    public ResponseJson findTopicList(SearchParam searchParam) {
        searchParam.setStage(Integer.parseInt(LoginInterceptor.currentTeacher().getSchool().getTypeId()))
        .setTeacherId(myId()).setSchoolId(mySchoolId());
        return topicsFeign.findTopicList(searchParam);
    }

    public Object findTopicDetail(ResourceVo resourceVo) {
        TopicParam topicParam = new TopicParam().setId(resourceVo.getTempId())
                .setSchoolId(mySchoolId())
                .setSource(resourceVo.getPlatform())
                .setTeacherId(myId());
        return topicsFeign.findTopicDetail(topicParam);
    }

    public Long findTopicUseCount(String topicId) {
        TopicParam topicParam = new TopicParam()
                .setSchoolId(mySchoolId())
                .setId(topicId)
                .setTeacherId(myId());
        return topicsFeign.findTopicUseCount(topicParam);
    }

    /**
     * 平台题库添加到校本题库
     * @param resourceVo
     * @return
     */
    public ResponseJson copyTopicToSchoolQusBank(ResourceVo resourceVo) {
        Topics topics = new Topics();
        BeanUtil.copyProperties(this.findTopicDetail(resourceVo),topics);
        if(topics==null){
            return new ResponseJson(false,201,"题目不存在");
        }else{
            if("204".equals(topics.getErrorCode())){
                return new ResponseJson(false,204,"获取题目额度不足");
            }else{
                //复制到校本题库
                SchoolQusBank schoolQusBank = new SchoolQusBank();
                BeanUtil.copyProperties(topics,schoolQusBank);
                schoolQusBank.setCreateBy(Constant.TOPIC_CREATEBY.YCJD);
                schoolQusBank.setSchoolId(mySchoolId());
                schoolQusBankFeign.copyTopicToSchoolQusBank(schoolQusBank);
                return new ResponseJson();
            }
        }
    }
}
