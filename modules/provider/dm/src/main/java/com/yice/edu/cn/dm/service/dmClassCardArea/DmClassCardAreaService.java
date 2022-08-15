package com.yice.edu.cn.dm.service.dmClassCardArea;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.dm.dmClassCardArea.DmClassCardArea;
import com.yice.edu.cn.dm.dao.dmClassCardArea.DmClassCardAreaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DmClassCardAreaService {
    @Autowired
    private DmClassCardAreaDao dmClassCardAreaDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public DmClassCardArea findDmClassCardAreaById(String id) {
        return dmClassCardAreaDao.findDmClassCardAreaById(id);
    }
    @Transactional
    public void saveDmClassCardArea(DmClassCardArea dmClassCardArea) {
        dmClassCardArea.setId(sequenceId.nextId());
        dmClassCardAreaDao.saveDmClassCardArea(dmClassCardArea);
    }
    @Transactional(readOnly = true)
    public List<DmClassCardArea> findDmClassCardAreaListByCondition(DmClassCardArea dmClassCardArea) {
        return dmClassCardAreaDao.findDmClassCardAreaListByCondition(dmClassCardArea);
    }
    @Transactional(readOnly = true)
    public DmClassCardArea findOneDmClassCardAreaByCondition(DmClassCardArea dmClassCardArea) {
        return dmClassCardAreaDao.findOneDmClassCardAreaByCondition(dmClassCardArea);
    }
    @Transactional(readOnly = true)
    public long findDmClassCardAreaCountByCondition(DmClassCardArea dmClassCardArea) {
        return dmClassCardAreaDao.findDmClassCardAreaCountByCondition(dmClassCardArea);
    }
    @Transactional
    public void updateDmClassCardArea(DmClassCardArea dmClassCardArea) {
        dmClassCardAreaDao.updateDmClassCardArea(dmClassCardArea);
    }
    @Transactional
    public void deleteDmClassCardArea(String id) {
        dmClassCardAreaDao.deleteDmClassCardArea(id);
    }
    @Transactional
    public void deleteDmClassCardAreaByCondition(DmClassCardArea dmClassCardArea) {
        dmClassCardAreaDao.deleteDmClassCardAreaByCondition(dmClassCardArea);
    }
    @Transactional
    public void batchSaveDmClassCardArea(List<DmClassCardArea> dmClassCardAreas){
        dmClassCardAreas.forEach(dmClassCardArea -> dmClassCardArea.setId(sequenceId.nextId()));
        dmClassCardAreaDao.batchSaveDmClassCardArea(dmClassCardAreas);
    }

}
