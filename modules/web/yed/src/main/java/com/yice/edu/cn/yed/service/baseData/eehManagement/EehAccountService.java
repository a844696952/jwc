package com.yice.edu.cn.yed.service.baseData.eehManagement;

import com.yice.edu.cn.common.pojo.jw.eehManagement.EehAccount;
import com.yice.edu.cn.yed.feignClient.baseData.eehManagement.EehAccountFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EehAccountService {
    @Autowired
    private EehAccountFeign eehAccountFeign;

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

    public void updatePassword(String id){
        eehAccountFeign.updatePassword(id);
    }

    public void updateEehAccountStatus(EehAccount eehAccount) {
        eehAccountFeign.updateEehAccountStatus(eehAccount);
    }

    public EehAccount findOneEehAccountByAccountType(EehAccount eehAccount) {
        return eehAccountFeign.findOneEehAccountByAccountType(eehAccount);
    }
}
