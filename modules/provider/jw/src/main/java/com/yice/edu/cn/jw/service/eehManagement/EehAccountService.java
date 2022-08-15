package com.yice.edu.cn.jw.service.eehManagement;

import cn.hutool.crypto.digest.DigestUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.eehManagement.EehAccount;
import com.yice.edu.cn.jw.dao.eehManagement.IEehAccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EehAccountService {
    @Autowired
    private IEehAccountDao eehAccountDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public EehAccount findEehAccountById(String id) {
        return eehAccountDao.findEehAccountById(id);
    }
    @Transactional
    public void saveEehAccount(EehAccount eehAccount) {
        eehAccount.setId(sequenceId.nextId());
        eehAccountDao.saveEehAccount(eehAccount);
    }
    @Transactional(readOnly = true)
    public List<EehAccount> findEehAccountListByCondition(EehAccount eehAccount) {
        return eehAccountDao.findEehAccountListByCondition(eehAccount);
    }
    @Transactional(readOnly = true)
    public EehAccount findOneEehAccountByCondition(EehAccount eehAccount) {
        return eehAccountDao.findOneEehAccountByCondition(eehAccount);
    }
    @Transactional(readOnly = true)
    public long findEehAccountCountByCondition(EehAccount eehAccount) {
        return eehAccountDao.findEehAccountCountByCondition(eehAccount);
    }
    @Transactional
    public void updateEehAccount(EehAccount eehAccount) {
        eehAccountDao.updateEehAccount(eehAccount);
    }
    @Transactional
    public void deleteEehAccount(String id) {
        eehAccountDao.deleteEehAccount(id);
    }
    @Transactional
    public void deleteEehAccountByCondition(EehAccount eehAccount) {
        eehAccountDao.deleteEehAccountByCondition(eehAccount);
    }
    @Transactional
    public void batchSaveEehAccount(List<EehAccount> eehAccounts){
        eehAccounts.forEach(eehAccount -> eehAccount.setId(sequenceId.nextId()));
        eehAccountDao.batchSaveEehAccount(eehAccounts);
    }

    @Transactional
    public void updatePassword(String id) {
        EehAccount eehAccount=new EehAccount();
        eehAccount.setEehId(id);
        eehAccount.setPassword(DigestUtil.sha1Hex(DigestUtil.md5Hex(Constant.DEFAULT_PWD)));
        eehAccountDao.updatePassword(eehAccount);
    }


    @Transactional(readOnly = true)
    public EehAccount EehAccountLogin(EehAccount eehAccount) {
        String sha1=eehAccount.getPassword();
        String sha1Hex = DigestUtil.sha1Hex(sha1);
        eehAccount.setPassword(sha1Hex);
        return eehAccountDao.EehAccountLogin(eehAccount);
    }

    @Transactional
    public void saveEehAccountNew(EehAccount eehAccount) {
        eehAccount.setId(sequenceId.nextId());
        String s = DigestUtil.md5Hex(Constant.DEFAULT_PWD);
        String s1 = DigestUtil.sha1Hex(s);
        eehAccount.setPassword(s1);
        eehAccountDao.saveEehAccount(eehAccount);
    }

    @Transactional(readOnly = true)
    public EehAccount findEehAccountByOldPs(EehAccount eehAccount) {
        return eehAccountDao.findEehAccountByOldPs(eehAccount);
    }

    @Transactional
    public void updateEehAccountStatus(EehAccount eehAccount) {
        eehAccountDao.updateEehAccountStatus(eehAccount);
    }

    @Transactional(readOnly = true)
    public EehAccount findOneEehAccountByAccountType(EehAccount eehAccount) {
        return eehAccountDao.findOneEehAccountByAccountType(eehAccount);
    }
}
