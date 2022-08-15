package com.yice.edu.cn.xw.service.zc.assetsContract;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.xw.zc.assetsContract.AssetsContract;
import com.yice.edu.cn.xw.dao.zc.assetsContract.IAssetsContractDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AssetsContractService {
    @Autowired
    private IAssetsContractDao assetsContractDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public AssetsContract findAssetsContractById(String id) {
        return assetsContractDao.findAssetsContractById(id);
    }

    @Transactional
    public void saveAssetsContract(AssetsContract assetsContract) {
        assetsContract.setId(sequenceId.nextId());
        assetsContractDao.saveAssetsContract(assetsContract);
    }

    @Transactional(readOnly = true)
    public List<AssetsContract> findAssetsContractListByCondition(AssetsContract assetsContract) {
        return assetsContractDao.findAssetsContractListByCondition(assetsContract);
    }

    @Transactional(readOnly = true)
    public AssetsContract findOneAssetsContractByCondition(AssetsContract assetsContract) {
        return assetsContractDao.findOneAssetsContractByCondition(assetsContract);
    }

    @Transactional(readOnly = true)
    public long findAssetsContractCountByCondition(AssetsContract assetsContract) {
        return assetsContractDao.findAssetsContractCountByCondition(assetsContract);
    }

    @Transactional
    public void updateAssetsContract(AssetsContract assetsContract) {
        assetsContractDao.updateAssetsContract(assetsContract);
    }

    @Transactional
    public void deleteAssetsContract(String id) {
        assetsContractDao.deleteAssetsContract(id);
    }

    @Transactional
    public void deleteAssetsContractByCondition(AssetsContract assetsContract) {
        assetsContractDao.deleteAssetsContractByCondition(assetsContract);
    }

    @Transactional
    public void batchSaveAssetsContract(List<AssetsContract> assetsContracts) {
        assetsContracts.forEach(assetsContract -> assetsContract.setId(sequenceId.nextId()));
        assetsContractDao.batchSaveAssetsContract(assetsContracts);
    }

    @Transactional
    public List<AssetsContract> getFileList(AssetsContract assetsContract) {
        return assetsContractDao.getFileList(assetsContract);
    }

    @Transactional
    public void deleteAssetsContractByIds(AssetsContract assetsContract){
        assetsContractDao.deleteAssetsContractByIds(assetsContract);
    }
}
