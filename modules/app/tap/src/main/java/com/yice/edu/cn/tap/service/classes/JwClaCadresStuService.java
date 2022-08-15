package com.yice.edu.cn.tap.service.classes;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.yice.edu.cn.common.pojo.jw.classes.JwClaCadresStu;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.tap.feignClient.classes.JwClaCadresStuFeign;

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
    
    public List<JwClaCadresStu> findStuAndCadresByClassesIdAndName(JwClaCadresStu jwClaCadresStu){
       return jwClaCadresStuFeign.findStuAndCadresByClassesIdAndName(jwClaCadresStu);
    }

}
