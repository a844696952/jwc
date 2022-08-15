package com.yice.edu.cn.osp.service.jy.titleQuota;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jy.titleQuota.ResourcePlatform;
import com.yice.edu.cn.common.pojo.jy.titleQuota.TopicQuotaResources;
import com.yice.edu.cn.common.pojo.jy.topics.TopicParam;
import com.yice.edu.cn.osp.feignClient.jy.titleQuota.TopicQuotaResourcesFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class TopicQuotaResourcesService {
    @Autowired
    private TopicQuotaResourcesFeign topicQuotaResourcesFeign;

    public TopicQuotaResources getBaiscInfo(String schoolId) {
        return topicQuotaResourcesFeign.getBaiscInfo(schoolId);
    }

    public List<ResourcePlatform> findPaltFormByCondition(TopicQuotaResources topicQuotaResources) {
        return  topicQuotaResourcesFeign.findPaltFormByCondition(topicQuotaResources);
    }
    public List<ResourcePlatform> findPlatform4School(String mySchoolId) {
        TopicQuotaResources topicQuotaResources = new TopicQuotaResources();
        topicQuotaResources.setSchoolId(mySchoolId);
        return topicQuotaResourcesFeign.findPaltFormByCondition(topicQuotaResources);
    }

    public List<ResourcePlatform> findAllPlatform4School(String mySchoolId) {
        TopicQuotaResources topicQuotaResources = new TopicQuotaResources();
        topicQuotaResources.setSchoolId(mySchoolId);
        List<ResourcePlatform> list = new ArrayList<>();
        list.addAll(topicQuotaResourcesFeign.findPaltFormByCondition(topicQuotaResources));
        list.add(new ResourcePlatform().setId(Constant.TOPIC_SOURCE.XBTK).setResourceName("校本题库"));
        list.add(new ResourcePlatform().setId(Constant.TOPIC_SOURCE.WDTK).setResourceName("我的题库"));
        return list;
    }

    public long findTeacherAndSchoolRemain() {
        TopicQuotaResources topicQuotaResources = new TopicQuotaResources();
        topicQuotaResources.setSchoolId(mySchoolId());
        topicQuotaResources.setTeacherId(myId());
        return topicQuotaResourcesFeign.findTeacherAndSchoolRemain(topicQuotaResources);
    }
}
