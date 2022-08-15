package com.yice.edu.cn.dm.dao.classes;

import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import com.yice.edu.cn.common.pojo.dm.classes.DmClassDesc;
import java.util.List;

import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IDmClassDescDao {
    List<DmClassDesc> findDmClassDescListByCondition(DmClassDesc dmClassDesc);

    DmClassDesc findOneDmClassDescByCondition(DmClassDesc dmClassDesc);

    long findDmClassDescCountByCondition(DmClassDesc dmClassDesc);

    DmClassDesc findDmClassDescById(@Param("id") String id);

    void saveDmClassDesc(DmClassDesc dmClassDesc);

    void updateDmClassDesc(DmClassDesc dmClassDesc);

    void deleteDmClassDesc(@Param("id") String id);

    void deleteDmClassDescByCondition(DmClassDesc dmClassDesc);

    void batchSaveDmClassDesc(List<DmClassDesc> dmClassDescs);

    List<JwClasses> findJwClassesListByCardCondition(JwClasses jwClasses);

    List<JwClasses> findDmClassesListByCardCondition(JwClasses jwClasses);

    long findJwClassesCountByCardCondition(JwClasses jwClasses);
    long findDmClassesCountByCardCondition(JwClasses jwClasses);


}
