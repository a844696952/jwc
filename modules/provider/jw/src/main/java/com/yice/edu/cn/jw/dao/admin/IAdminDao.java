package com.yice.edu.cn.jw.dao.admin;

import java.util.List;

import com.yice.edu.cn.common.pojo.yedAdmin.Admin;
import com.yice.edu.cn.common.pojo.yedAdmin.SysPerm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IAdminDao {
    List<Admin> findAdminListByCondition(Admin admin);

    Admin findOneAdminByCondition(Admin admin);

    long findAdminCountByCondition(Admin admin);

    Admin findAdminById(@Param("id") String id);

    void saveAdmin(Admin admin);

    void updateAdmin(Admin admin);

    void deleteAdmin(@Param("id") String id);

    void deleteAdminByCondition(Admin admin);

    void batchSaveAdmin(List<Admin> admins);


    List<String> findCheckedRoloIdsByAdminId(@Param("adminId")String adminId);

    List<SysPerm> findSysFuncPermsByAdminId(@Param("adminId") String adminId);
}
