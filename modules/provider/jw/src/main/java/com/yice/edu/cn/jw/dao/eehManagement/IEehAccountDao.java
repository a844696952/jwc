package com.yice.edu.cn.jw.dao.eehManagement;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.eehManagement.EehAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IEehAccountDao {
    List<EehAccount> findEehAccountListByCondition(EehAccount eehAccount);

    long findEehAccountCountByCondition(EehAccount eehAccount);

    EehAccount findOneEehAccountByCondition(EehAccount eehAccount);

    EehAccount findEehAccountById(@Param("id") String id);

    void saveEehAccount(EehAccount eehAccount);

    void updateEehAccount(EehAccount eehAccount);

    void deleteEehAccount(@Param("id") String id);

    void deleteEehAccountByCondition(EehAccount eehAccount);

    void batchSaveEehAccount(List<EehAccount> eehAccounts);

    void updatePassword(EehAccount eehAccount);

    EehAccount EehAccountLogin(EehAccount eehAccount);

    EehAccount findEehAccountByOldPs(EehAccount eehAccount);

    void updateEehAccountStatus(EehAccount eehAccount);

    EehAccount findOneEehAccountByAccountType(EehAccount eehAccount);
}
