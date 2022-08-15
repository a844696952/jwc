package com.yice.edu.cn.jw.service.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.classes.JwClaCadres;
import com.yice.edu.cn.common.pojo.jw.classes.JwClaCadresStu;
import com.yice.edu.cn.jw.dao.classes.IJwClaCadresDao;
import com.yice.edu.cn.jw.dao.classes.IJwClaCadresStuDao;

@Service
public class JwClaCadresService {
    @Autowired
    private IJwClaCadresDao jwClaCadresDao;
    @Autowired
    private IJwClaCadresStuDao jwClaCadresStuDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public JwClaCadres findJwClaCadresById(String id) {
        return jwClaCadresDao.findJwClaCadresById(id);
    }
    @Transactional
    public void saveJwClaCadres(JwClaCadres jwClaCadres) {
        jwClaCadres.setId(sequenceId.nextId());
        jwClaCadresDao.saveJwClaCadres(jwClaCadres);
    }
    @Transactional(readOnly = true)
    public List<JwClaCadres> findJwClaCadresListByCondition(JwClaCadres jwClaCadres) {
        return jwClaCadresDao.findJwClaCadresListByCondition(jwClaCadres);
    }
    @Transactional(readOnly = true)
    public long findJwClaCadresCountByCondition(JwClaCadres jwClaCadres) {
        return jwClaCadresDao.findJwClaCadresCountByCondition(jwClaCadres);
    }
    @Transactional
    public void updateJwClaCadres(JwClaCadres jwClaCadres) {
        jwClaCadresDao.updateJwClaCadres(jwClaCadres);
    }
    @Transactional
    public void deleteJwClaCadres(String id) {
        jwClaCadresDao.deleteJwClaCadres(id);
        JwClaCadresStu  jwClaCadresStu = new JwClaCadresStu();
        if(id==null || "".equals(id)) {
        	return;
        }
        jwClaCadresStu.setClaCadresId(id);
        jwClaCadresStuDao.deleteJwClaCadresStuByCondition(jwClaCadresStu);
    }
    @Transactional
    public void deleteJwClaCadresByCondition(JwClaCadres jwClaCadres) {
        jwClaCadresDao.deleteJwClaCadresByCondition(jwClaCadres);
    }
    @Transactional(readOnly = true)
    public List<JwClaCadres> findJwClaCadresListWithSName(JwClaCadres jwClaCadres) {
        return jwClaCadresDao.findJwClaCadresListWithSName(jwClaCadres);
    }
}
