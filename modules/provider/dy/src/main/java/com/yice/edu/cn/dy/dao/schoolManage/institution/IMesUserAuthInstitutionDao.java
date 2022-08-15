package com.yice.edu.cn.dy.dao.schoolManage.institution;

import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesUserAuthInstitution;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IMesUserAuthInstitutionDao {
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<MesUserAuthInstitution> findMesUserAuthInstitutionListByCondition(MesUserAuthInstitution mesUserAuthInstitution);

    List<MesUserAuthInstitution> findMesUserAuthInstitutionListTeaByCondition(MesUserAuthInstitution mesUserAuthInstitution);

    List<MesUserAuthInstitution> findMesUserAuthInstitutionListStuByCondition(MesUserAuthInstitution mesUserAuthInstitution);

    long findMesUserAuthInstitutionCountByCondition(MesUserAuthInstitution mesUserAuthInstitution);

    MesUserAuthInstitution findOneMesUserAuthInstitutionByCondition(MesUserAuthInstitution mesUserAuthInstitution);

    MesUserAuthInstitution findMesUserAuthInstitutionById(@Param("id") String id);

    void saveMesUserAuthInstitution(MesUserAuthInstitution mesUserAuthInstitution);

    void updateMesUserAuthInstitution(MesUserAuthInstitution mesUserAuthInstitution);

    void deleteMesUserAuthInstitution(@Param("id") String id);

    void deleteMesUserAuthInstitutionByCondition(MesUserAuthInstitution mesUserAuthInstitution);

    void batchSaveMesUserAuthInstitution(List<MesUserAuthInstitution> mesUserAuthInstitutions);
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    List<Department> findDepartmentTreeBySchoolId(@Param("schoolId") String schoolId);

    /**
     * 是否拥有检查权限
     *
     * @param mesUserAuthInstitution 参数
     * @return 0无权限 大于0有权限
     */
    long haveCheckPermission(MesUserAuthInstitution mesUserAuthInstitution);
}
