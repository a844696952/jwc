package com.yice.edu.cn.osp.service.jw.classes;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yice.edu.cn.common.pojo.jw.classes.JwClaCadresStu;
import com.yice.edu.cn.osp.feignClient.jw.classes.JwClaCadresStuFeign;

@Service
public class JwClaCadresStuService {
    @Autowired
    private JwClaCadresStuFeign jwClaCadresStuFeign;

    public JwClaCadresStu findJwClaCadresStuById(String id) {
        return jwClaCadresStuFeign.findJwClaCadresStuById(id);
    }

    public JwClaCadresStu saveJwClaCadresStu(JwClaCadresStu jwClaCadresStu) {
        return jwClaCadresStuFeign.saveJwClaCadresStu(jwClaCadresStu);
    }

    public List<JwClaCadresStu> findJwClaCadresStuListByCondition(JwClaCadresStu jwClaCadresStu) {
        return jwClaCadresStuFeign.findJwClaCadresStuListByCondition(jwClaCadresStu);
    }

    public long findJwClaCadresStuCountByCondition(JwClaCadresStu jwClaCadresStu) {
        return jwClaCadresStuFeign.findJwClaCadresStuCountByCondition(jwClaCadresStu);
    }

    public void updateJwClaCadresStu(JwClaCadresStu jwClaCadresStu) {
        jwClaCadresStuFeign.updateJwClaCadresStu(jwClaCadresStu);
    }

    public void deleteJwClaCadresStu(String id) {
        jwClaCadresStuFeign.deleteJwClaCadresStu(id);
    }

    public void deleteJwClaCadresStuByCondition(JwClaCadresStu jwClaCadresStu) {
        jwClaCadresStuFeign.deleteJwClaCadresStuByCondition(jwClaCadresStu);
    }
    
    public void updateStudentCadres(Map map) {
    	jwClaCadresStuFeign.updateStudentCadres(map);
    }
    
    public List<JwClaCadresStu> queryJwStudentByClassesId(String id){
    	return jwClaCadresStuFeign.queryJwStudentByClassesId(id);
    }
}
