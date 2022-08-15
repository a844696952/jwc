package com.yice.edu.cn.dm.dao.classCard;

import com.yice.edu.cn.common.pojo.dm.classCard.ClassCardLock;
import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import com.yice.edu.cn.common.pojo.dm.classCard.DmTimedTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IDmClassCardDao {
    List<DmClassCard> findDmClassCardListByCondition(DmClassCard dmClassCard);

    DmClassCard findOneDmClassCardByCondition(DmClassCard dmClassCard);

    long findDmClassCardCountByCondition(DmClassCard dmClassCard);

    DmClassCard findDmClassCardById(@Param("id") String id);

    void saveDmClassCard(DmClassCard dmClassCard);

    void updateDmClassCard(DmClassCard dmClassCard);

    void deleteDmClassCard(@Param("id") String id);

    void deleteDmClassCardByCondition(DmClassCard dmClassCard);

    void batchSaveDmClassCard(List<DmClassCard> dmClassCards);
    //查询班级是否已经绑定设备（云班牌)
    List<DmClassCard> findDmClassCardListByclassId(DmClassCard dmClassCard);
    //批量解绑
    void relieveDmClassCardAll(String []rowData);
    //根据条件查找列表进行导出xls
    List<DmClassCard> findDmClassCardToXls(DmClassCard dmClassCard);
    //查询账号密码
    List<DmClassCard> findDmClassCardUser(DmClassCard dmClassCard);
    //修改设备的状态和版本号
    void dmClassCardStatus(DmClassCard dmClassCard);

    void updateEquipmentName(DmClassCard dmClassCard);

    void setVersionAll(DmTimedTask dmTimedTask);

    void batchChangeLockStatusByIds(ClassCardLock classCardLock);

    void lockDmScreen(@Param("id") String id);

    void unLockDmScreen(@Param("id") String id);

    List<DmClassCard> getDmClassCardByTeacherId(@Param("id") String id,@Param("lockStatus") String lockStatus);

    //批量设置班牌权限
    void updateDmClassManage(DmClassCard dmClassCard);
    //班牌树形结构班牌
    List<DmClassCard> getDmClassCardTree(DmClassCard dmClassCard);
    //查询该教师已经拥有权限的班牌
    List<DmClassCard> findDmClassCardIdByTeacherId(DmClassCard dmClassCard);
    //批量设置清除班牌权限
    void cleraDmClassManage(DmClassCard dmClassCard);


    List<DmClassCard> selectUserNameListBySchoolId(@Param("schoolId")String schoolId);
    DmClassCard findDmClassCardByStudentId(@Param("studentId")String studentId);

    List<String> findUserNamesBySchoolId(@Param("schoolId")String schoolId);

    DmClassCard selectSchoolByUserName(@Param("userName")String userName);
    
    void clearDmAndClazzRelateBySchoolId(DmClassCard dmClassCard);
    
    void updateDmClazzRelateByDmId(DmClassCard dmClassCard);
}
