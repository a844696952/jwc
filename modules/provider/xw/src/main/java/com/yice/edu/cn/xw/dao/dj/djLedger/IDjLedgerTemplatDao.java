package com.yice.edu.cn.xw.dao.dj.djLedger;

import java.util.List;

import com.yice.edu.cn.common.pojo.xw.dj.djLedgerTemplate.DjLedgerTemplat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IDjLedgerTemplatDao {
    List<DjLedgerTemplat> findDjLedgerTemplatListByCondition(DjLedgerTemplat djLedgerTemplat);

    long findDjLedgerTemplatCountByCondition(DjLedgerTemplat djLedgerTemplat);

    DjLedgerTemplat findOneDjLedgerTemplatByCondition(DjLedgerTemplat djLedgerTemplat);

    DjLedgerTemplat findDjLedgerTemplatById(@Param("id") String id);

    void saveDjLedgerTemplat(DjLedgerTemplat djLedgerTemplat);

    void updateDjLedgerTemplat(DjLedgerTemplat djLedgerTemplat);

    void deleteDjLedgerTemplat(@Param("id") String id);

    void deleteDjLedgerTemplatByCondition(DjLedgerTemplat djLedgerTemplat);

    void batchSaveDjLedgerTemplat(List<DjLedgerTemplat> djLedgerTemplats);

    List<DjLedgerTemplat> findTemplateTree( @Param("schoolId") String schoolId);
}
