package com.yice.edu.cn.osp.service.jy.cloudClassroom.otherSchoolAccount;

import com.yice.edu.cn.common.pojo.cc.otherSchoolAccount.OtherSchoolAccount;
import com.yice.edu.cn.osp.feignClient.jy.cloudClassroom.otherSchoolAccount.OtherSchoolAccountFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OtherSchoolAccountService {
    @Autowired
    private OtherSchoolAccountFeign otherSchoolAccountFeign;

    public OtherSchoolAccount findOtherSchoolAccountById(String id) {
        return otherSchoolAccountFeign.findOtherSchoolAccountById(id);
    }

    public OtherSchoolAccount saveOtherSchoolAccount(OtherSchoolAccount otherSchoolAccount) {
        return otherSchoolAccountFeign.saveOtherSchoolAccount(otherSchoolAccount);
    }

    public List<OtherSchoolAccount> findOtherSchoolAccountListByCondition(OtherSchoolAccount otherSchoolAccount) {
        return otherSchoolAccountFeign.findOtherSchoolAccountListByCondition(otherSchoolAccount);
    }

    public OtherSchoolAccount findOneOtherSchoolAccountByCondition(OtherSchoolAccount otherSchoolAccount) {
        return otherSchoolAccountFeign.findOneOtherSchoolAccountByCondition(otherSchoolAccount);
    }

    public long findOtherSchoolAccountCountByCondition(OtherSchoolAccount otherSchoolAccount) {
        return otherSchoolAccountFeign.findOtherSchoolAccountCountByCondition(otherSchoolAccount);
    }

    public void updateOtherSchoolAccount(OtherSchoolAccount otherSchoolAccount) {
        otherSchoolAccountFeign.updateOtherSchoolAccount(otherSchoolAccount);
    }

    public void deleteOtherSchoolAccount(String id) {
        otherSchoolAccountFeign.deleteOtherSchoolAccount(id);
    }

    public void deleteOtherSchoolAccountByCondition(OtherSchoolAccount otherSchoolAccount) {
        otherSchoolAccountFeign.deleteOtherSchoolAccountByCondition(otherSchoolAccount);
    }
}
