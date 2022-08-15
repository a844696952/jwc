package com.yice.edu.cn.xw.service.zc.assetsWarehouse;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.xw.zc.assetsWarehouse.AssetsWarehouse;
import com.yice.edu.cn.xw.dao.zc.assetsWarehouse.IAssetsWarehouseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AssetsWarehouseService {
    @Autowired
    private IAssetsWarehouseDao assetsWarehouseDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public AssetsWarehouse findAssetsWarehouseById(String id) {
        return assetsWarehouseDao.findAssetsWarehouseById(id);
    }
    @Transactional
    public void saveAssetsWarehouse(AssetsWarehouse assetsWarehouse) {
        assetsWarehouse.setId(sequenceId.nextId());
        assetsWarehouseDao.saveAssetsWarehouse(assetsWarehouse);
    }
    @Transactional(readOnly = true)
    public List<AssetsWarehouse> findAssetsWarehouseListByCondition(AssetsWarehouse assetsWarehouse) {
        return assetsWarehouseDao.findAssetsWarehouseListByCondition(assetsWarehouse);
    }
    @Transactional(readOnly = true)
    public AssetsWarehouse findOneAssetsWarehouseByCondition(AssetsWarehouse assetsWarehouse) {
        return assetsWarehouseDao.findOneAssetsWarehouseByCondition(assetsWarehouse);
    }
    @Transactional(readOnly = true)
    public long findAssetsWarehouseCountByCondition(AssetsWarehouse assetsWarehouse) {
        return assetsWarehouseDao.findAssetsWarehouseCountByCondition(assetsWarehouse);
    }
    @Transactional
    public void updateAssetsWarehouse(AssetsWarehouse assetsWarehouse) {
        assetsWarehouseDao.updateAssetsWarehouse(assetsWarehouse);
    }
    @Transactional
    public void deleteAssetsWarehouse(String id) {
        assetsWarehouseDao.deleteAssetsWarehouse(id);
    }
    @Transactional
    public void deleteAssetsWarehouseByCondition(AssetsWarehouse assetsWarehouse) {
        assetsWarehouseDao.deleteAssetsWarehouseByCondition(assetsWarehouse);
    }
    @Transactional
    public void batchSaveAssetsWarehouse(List<AssetsWarehouse> assetsWarehouses){
        assetsWarehouses.forEach(assetsWarehouse -> assetsWarehouse.setId(sequenceId.nextId()));
        assetsWarehouseDao.batchSaveAssetsWarehouse(assetsWarehouses);
    }

    @Transactional(readOnly = true)
    public long findAssetsWarehouseCountByName(AssetsWarehouse assetsWarehouse) {
        return assetsWarehouseDao.findAssetsWarehouseCountByName(assetsWarehouse);
    }

}
