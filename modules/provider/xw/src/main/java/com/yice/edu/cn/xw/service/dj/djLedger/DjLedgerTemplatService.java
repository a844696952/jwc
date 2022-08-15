package com.yice.edu.cn.xw.service.dj.djLedger;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.xw.dj.djLedgerTemplate.DjLedgerTemplat;
import com.yice.edu.cn.xw.dao.dj.djLedger.IDjLedgerTemplatDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DjLedgerTemplatService {
    @Autowired
    private IDjLedgerTemplatDao djLedgerTemplatDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public DjLedgerTemplat findDjLedgerTemplatById(String id) {
        return djLedgerTemplatDao.findDjLedgerTemplatById(id);
    }
    @Transactional
    public void saveDjLedgerTemplat(DjLedgerTemplat djLedgerTemplat) {
        djLedgerTemplat.setId(sequenceId.nextId());
        djLedgerTemplatDao.saveDjLedgerTemplat(djLedgerTemplat);
    }
    @Transactional(readOnly = true)
    public List<DjLedgerTemplat> findDjLedgerTemplatListByCondition(DjLedgerTemplat djLedgerTemplat) {
        return djLedgerTemplatDao.findDjLedgerTemplatListByCondition(djLedgerTemplat);
    }
    @Transactional(readOnly = true)
    public DjLedgerTemplat findOneDjLedgerTemplatByCondition(DjLedgerTemplat djLedgerTemplat) {
        return djLedgerTemplatDao.findOneDjLedgerTemplatByCondition(djLedgerTemplat);
    }
    @Transactional(readOnly = true)
    public long findDjLedgerTemplatCountByCondition(DjLedgerTemplat djLedgerTemplat) {
        return djLedgerTemplatDao.findDjLedgerTemplatCountByCondition(djLedgerTemplat);
    }
    @Transactional
    public void updateDjLedgerTemplat(DjLedgerTemplat djLedgerTemplat) {
        djLedgerTemplatDao.updateDjLedgerTemplat(djLedgerTemplat);
    }
    @Transactional
    public void deleteDjLedgerTemplat(String id) {
        djLedgerTemplatDao.deleteDjLedgerTemplat(id);
    }
    @Transactional
    public void deleteDjLedgerTemplatByCondition(DjLedgerTemplat djLedgerTemplat) {
        djLedgerTemplatDao.deleteDjLedgerTemplatByCondition(djLedgerTemplat);
    }
    @Transactional
    public void batchSaveDjLedgerTemplat(List<DjLedgerTemplat> djLedgerTemplats){
        djLedgerTemplats.forEach(djLedgerTemplat -> djLedgerTemplat.setId(sequenceId.nextId()));
        djLedgerTemplatDao.batchSaveDjLedgerTemplat(djLedgerTemplats);
    }

    public List<DjLedgerTemplat> findTemplateTree(String schoolId) {
        List<DjLedgerTemplat> djLedgerList = new ArrayList<>();
        DjLedgerTemplat djLedger = new DjLedgerTemplat();
        djLedger.setTemplateName("党建台账");
        djLedger.setDdidType("5000");
        List<DjLedgerTemplat> djLedgerTemplats = new ArrayList<>();
        List<DjLedgerTemplat> templateTree = djLedgerTemplatDao.findTemplateTree(null);
        Map<String, List<DjLedgerTemplat>> collect = templateTree.stream().filter(djLedgerTemplat1 -> StrUtil.isNotEmpty(djLedgerTemplat1.getTemplateName())).collect(Collectors.groupingBy(DjLedgerTemplat::getDdid));
        Map<String, List<DjLedgerTemplat>> collect1 = templateTree.stream().collect(Collectors.groupingBy(DjLedgerTemplat::getDdid));
        for (String ddid:collect1.keySet()){
            List<DjLedgerTemplat> djLedgerTemplats2 = collect1.get(ddid);
            List<DjLedgerTemplat> djLedgerTemplats1 = collect.get(ddid);

            DjLedgerTemplat djLedgerTemplat = new DjLedgerTemplat();
            djLedgerTemplat.setDdid(djLedgerTemplats2.get(0).getDdid());
            djLedgerTemplat.setTemplateName(djLedgerTemplats2.get(0).getDdTypeName());
            if (CollectionUtil.isNotEmpty(djLedgerTemplats1)){
                djLedgerTemplat.setChildren(djLedgerTemplats1);
            }
            djLedgerTemplats.add(djLedgerTemplat);
        }
        djLedger.setChildren(djLedgerTemplats);
        djLedgerList.add(djLedger);
        return djLedgerList;
    }
}
