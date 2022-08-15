package com.yice.edu.cn.ewb.service.classRegister;

import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.ewb.feignClient.classRegister.JwClassesFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JwClassesService {
    @Autowired
    private JwClassesFeign jwClassesFeign;

    public List<JwClasses> findJwClassesListByCondition(JwClasses jwClasses) {
        return jwClassesFeign.findJwClassesListByCondition(jwClasses);
    }
    public JwClasses findJwClassesById(String id) {
        return jwClassesFeign.findJwClassesById(id);
    }
    public void updateJwClasses(JwClasses jwClasses) {
        jwClassesFeign.updateJwClasses(jwClasses);
    }

}
