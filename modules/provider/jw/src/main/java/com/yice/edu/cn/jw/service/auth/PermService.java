package com.yice.edu.cn.jw.service.auth;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.jw.dao.auth.IPermDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermService {
    @Autowired
    private IPermDao permDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public Perm findPermById(String id) {
        return permDao.findPermById(id);
    }
    @Transactional
    public void savePerm(Perm perm) {
        perm.setId(sequenceId.nextId());
        permDao.savePerm(perm);
    }
    @Transactional(readOnly = true)
    public List<Perm> findPermListByCondition(Perm perm) {
        return permDao.findPermListByCondition(perm);
    }
    @Transactional(readOnly = true)
    public long findPermCountByCondition(Perm perm) {
        return permDao.findPermCountByCondition(perm);
    }
    @Transactional
    public void updatePerm(Perm perm) {
        permDao.updatePerm(perm);
    }
    @Transactional
    public void deletePerm(String id) {
        permDao.deletePerm(id);
    }
    @Transactional
    public void deletePermByCondition(Perm perm) {
        permDao.deletePermByCondition(perm);
    }
    @Transactional(readOnly = true)
    public List<Perm> findPermsForH5BySchoolId(String schoolId) {
        List<Perm> r=permDao.findPermsForH5BySchoolId(schoolId);
        return r;
    }
}
