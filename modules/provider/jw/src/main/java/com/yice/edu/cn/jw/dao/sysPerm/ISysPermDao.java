package com.yice.edu.cn.jw.dao.sysPerm;

import java.util.List;

import com.yice.edu.cn.common.pojo.yedAdmin.SysPerm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ISysPermDao {
    List<SysPerm> findSysPermListByCondition(SysPerm sysPerm);

    long findSysPermCountByCondition(SysPerm sysPerm);

    SysPerm findSysPermById(String id);

    void saveSysPerm(SysPerm sysPerm);

    void updateSysPerm(SysPerm sysPerm);

    void deleteSysPerm(String id);

    void batchSaveSysPerm(List<SysPerm> sysPerms);

    List<SysPerm> findRootSysPerm();

    List<SysPerm> findSysPermByPId(String id);

    List<SysPerm> findSysPermsByPId(@Param("pId") String pId);

    List<String> findSysPermChecked(String roleId);

    List<SysPerm> findAminTreeMenuV2(@Param("adminId") String adminId);
}
