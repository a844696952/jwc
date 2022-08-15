package com.yice.edu.cn.xw.service.zc.assetsUnit;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.xw.zc.assetsUnit.AssetsUnit;
import com.yice.edu.cn.xw.dao.zc.assetsUnit.IAssetsUnitDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AssetsUnitService {
    @Autowired
    private IAssetsUnitDao assetsUnitDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public AssetsUnit findAssetsUnitById(String id) {
        return assetsUnitDao.findAssetsUnitById(id);
    }
    @Transactional
    public void saveAssetsUnit(AssetsUnit assetsUnit) {
        assetsUnit.setId(sequenceId.nextId());
        assetsUnitDao.saveAssetsUnit(assetsUnit);
    }
    @Transactional(readOnly = true)
    public List<AssetsUnit> findAssetsUnitListByCondition(AssetsUnit assetsUnit) {
        return assetsUnitDao.findAssetsUnitListByCondition(assetsUnit);
    }
    @Transactional(readOnly = true)
    public AssetsUnit findOneAssetsUnitByCondition(AssetsUnit assetsUnit) {
        return assetsUnitDao.findOneAssetsUnitByCondition(assetsUnit);
    }
    @Transactional(readOnly = true)
    public long findAssetsUnitCountByCondition(AssetsUnit assetsUnit) {
        return assetsUnitDao.findAssetsUnitCountByCondition(assetsUnit);
    }
    @Transactional
    public void updateAssetsUnit(AssetsUnit assetsUnit) {
        assetsUnitDao.updateAssetsUnit(assetsUnit);
    }
    @Transactional
    public void deleteAssetsUnit(String id) {
        assetsUnitDao.deleteAssetsUnit(id);
    }
    @Transactional
    public void deleteAssetsUnitByCondition(AssetsUnit assetsUnit) {
        assetsUnitDao.deleteAssetsUnitByCondition(assetsUnit);
    }
    @Transactional
    public void batchSaveAssetsUnit(List<AssetsUnit> assetsUnits){
        assetsUnits.forEach(assetsUnit -> assetsUnit.setId(sequenceId.nextId()));
        assetsUnitDao.batchSaveAssetsUnit(assetsUnits);
    }


    @Transactional(readOnly = true)
    public long findAssetsUnitCountByName(AssetsUnit assetsUnit) {
        return assetsUnitDao.findAssetsUnitCountByName(assetsUnit);
    }

}
