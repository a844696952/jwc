package com.yice.edu.cn.osp.service.jw.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yice.edu.cn.common.pojo.jw.classes.JwClaCadres;
import com.yice.edu.cn.osp.feignClient.jw.classes.JwClaCadresFeign;

@Service
public class JwClaCadresService {
    @Autowired
    private JwClaCadresFeign jwClaCadresFeign;

    public JwClaCadres findJwClaCadresById(String id) {
        return jwClaCadresFeign.findJwClaCadresById(id);
    }

    public JwClaCadres saveJwClaCadres(JwClaCadres jwClaCadres) {
        return jwClaCadresFeign.saveJwClaCadres(jwClaCadres);
    }

    public List<JwClaCadres> findJwClaCadresListByCondition(JwClaCadres jwClaCadres) {
        return jwClaCadresFeign.findJwClaCadresListByCondition(jwClaCadres);
    }

    public long findJwClaCadresCountByCondition(JwClaCadres jwClaCadres) {
        return jwClaCadresFeign.findJwClaCadresCountByCondition(jwClaCadres);
    }

    public void updateJwClaCadres(JwClaCadres jwClaCadres) {
        jwClaCadresFeign.updateJwClaCadres(jwClaCadres);
    }

    public void deleteJwClaCadres(String id) {
        jwClaCadresFeign.deleteJwClaCadres(id);
    }

    public void deleteJwClaCadresByCondition(JwClaCadres jwClaCadres) {
        jwClaCadresFeign.deleteJwClaCadresByCondition(jwClaCadres);
    }
    
    public List<JwClaCadres> findJwClaCadresListWithSName(JwClaCadres jwClaCadres) {
        return jwClaCadresFeign.findJwClaCadresListWithSName(jwClaCadres);
    }
}
