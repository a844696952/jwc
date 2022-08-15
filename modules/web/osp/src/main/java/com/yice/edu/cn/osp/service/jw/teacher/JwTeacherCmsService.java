package com.yice.edu.cn.osp.service.jw.teacher;

import com.yice.edu.cn.common.pojo.jw.teacher.JwTeacherCms;
import com.yice.edu.cn.osp.feignClient.jw.teacher.JwTeacherCmsFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JwTeacherCmsService {
    @Autowired
    private JwTeacherCmsFeign jwTeacherCmsFeign;

    public JwTeacherCms findJwTeacherCmsById(String id) {
        return jwTeacherCmsFeign.findJwTeacherCmsById(id);
    }

    public JwTeacherCms saveJwTeacherCms(JwTeacherCms jwTeacherCms) {
        return jwTeacherCmsFeign.saveJwTeacherCms(jwTeacherCms);
    }

    public List<JwTeacherCms> findJwTeacherCmsListByCondition(JwTeacherCms jwTeacherCms) {
        return jwTeacherCmsFeign.findJwTeacherCmsListByCondition(jwTeacherCms);
    }

    public JwTeacherCms findOneJwTeacherCmsByCondition(JwTeacherCms jwTeacherCms) {
        return jwTeacherCmsFeign.findOneJwTeacherCmsByCondition(jwTeacherCms);
    }

    public long findJwTeacherCmsCountByCondition(JwTeacherCms jwTeacherCms) {
        return jwTeacherCmsFeign.findJwTeacherCmsCountByCondition(jwTeacherCms);
    }

    public void updateJwTeacherCms(JwTeacherCms jwTeacherCms) {
        jwTeacherCmsFeign.updateJwTeacherCms(jwTeacherCms);
    }

    public void deleteJwTeacherCms(String id) {
        jwTeacherCmsFeign.deleteJwTeacherCms(id);
    }

    public void deleteJwTeacherCmsByCondition(JwTeacherCms jwTeacherCms) {
        jwTeacherCmsFeign.deleteJwTeacherCmsByCondition(jwTeacherCms);
    }
}
