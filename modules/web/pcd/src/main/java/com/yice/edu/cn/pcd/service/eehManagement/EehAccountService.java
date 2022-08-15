package com.yice.edu.cn.pcd.service.eehManagement;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.eehManagement.EehAccount;
import com.yice.edu.cn.pcd.feignClient.eehManagement.EehAccountFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EehAccountService {
    @Autowired
    private EehAccountFeign eehAccountFeign;
    @CreateCache(name= Constant.Redis.PCD_ACCOUNT_CACHE,expire = Constant.Redis.PCD_ACCOUNT_TIMEOUT)
    private Cache<String,EehAccount> accountCache;
    public EehAccount findEehAccountById(String id) {
        return eehAccountFeign.findEehAccountById(id);
    }

    public EehAccount saveEehAccount(EehAccount eehAccount) {
        return eehAccountFeign.saveEehAccount(eehAccount);
    }

    public List<EehAccount> findEehAccountListByCondition(EehAccount eehAccount) {
        return eehAccountFeign.findEehAccountListByCondition(eehAccount);
    }

    public EehAccount findOneEehAccountByCondition(EehAccount eehAccount) {
        return eehAccountFeign.findOneEehAccountByCondition(eehAccount);
    }

    public long findEehAccountCountByCondition(EehAccount eehAccount) {
        return eehAccountFeign.findEehAccountCountByCondition(eehAccount);
    }

    public void updateEehAccount(EehAccount eehAccount) {
        eehAccountFeign.updateEehAccount(eehAccount);
    }

    public void deleteEehAccount(String id) {
        eehAccountFeign.deleteEehAccount(id);
    }

    public void deleteEehAccountByCondition(EehAccount eehAccount) {
        eehAccountFeign.deleteEehAccountByCondition(eehAccount);
    }

    public EehAccount EehAccountLogin(EehAccount eehAccount) {
        return eehAccountFeign.EehAccountLogin(eehAccount);
    }

    public void saveAccountToCache(EehAccount eehAccount){
        accountCache.put(eehAccount.getId(),eehAccount);
    }

    public EehAccount saveEehAccountNew(EehAccount eehAccount) {
        return eehAccountFeign.saveEehAccountNew(eehAccount);
    }

    public EehAccount findEehAccountByOldPs(EehAccount eehAccount) {
        return eehAccountFeign.findEehAccountByOldPs(eehAccount);
    }
}
