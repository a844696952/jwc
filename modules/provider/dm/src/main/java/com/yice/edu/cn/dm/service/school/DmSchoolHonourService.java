package com.yice.edu.cn.dm.service.school;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.dm.school.DmSchoolHonour;
import com.yice.edu.cn.dm.dao.school.IDmSchoolHonourDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DmSchoolHonourService {
    @Autowired
    private IDmSchoolHonourDao dmSchoolHonourDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public DmSchoolHonour findDmSchoolHonourById(String id) {
        return dmSchoolHonourDao.findDmSchoolHonourById(id);
    }
    @Transactional
    public void saveDmSchoolHonour(DmSchoolHonour dmSchoolHonour) {
        dmSchoolHonour.setId(sequenceId.nextId());
        dmSchoolHonourDao.saveDmSchoolHonour(dmSchoolHonour);
    }
    @Transactional(readOnly = true)
    public List<DmSchoolHonour> findDmSchoolHonourListByCondition(DmSchoolHonour dmSchoolHonour) {
        return dmSchoolHonourDao.findDmSchoolHonourListByCondition(dmSchoolHonour);
    }
    @Transactional(readOnly = true)
    public DmSchoolHonour findOneDmSchoolHonourByCondition(DmSchoolHonour dmSchoolHonour) {
        return dmSchoolHonourDao.findOneDmSchoolHonourByCondition(dmSchoolHonour);
    }
    @Transactional(readOnly = true)
    public long findDmSchoolHonourCountByCondition(DmSchoolHonour dmSchoolHonour) {
        return dmSchoolHonourDao.findDmSchoolHonourCountByCondition(dmSchoolHonour);
    }
    @Transactional
    public void updateDmSchoolHonour(DmSchoolHonour dmSchoolHonour) {
        dmSchoolHonourDao.updateDmSchoolHonour(dmSchoolHonour);
    }
    @Transactional
    public void deleteDmSchoolHonour(String id) {
        dmSchoolHonourDao.deleteDmSchoolHonour(id);
    }
    @Transactional
    public void deleteDmSchoolHonourByCondition(DmSchoolHonour dmSchoolHonour) {
        dmSchoolHonourDao.deleteDmSchoolHonourByCondition(dmSchoolHonour);
    }
    @Transactional
    public void batchSaveDmSchoolHonour(List<DmSchoolHonour> dmSchoolHonours){
        dmSchoolHonourDao.batchSaveDmSchoolHonour(dmSchoolHonours);
    }

    @Transactional(readOnly = true)
    public List<DmSchoolHonour> findDmSchoolHonourByactiveNameLike(DmSchoolHonour dmSchoolHonour){
        return dmSchoolHonourDao.findDmSchoolHonourByactiveNameLike(dmSchoolHonour);
    }

    @Transactional(readOnly = true)
    public Long findDmSchoolHonourByactiveNameLikeCount(DmSchoolHonour dmSchoolHonour){
        return dmSchoolHonourDao.findDmSchoolHonourByactiveNameLikeCount(dmSchoolHonour);
    }

}
