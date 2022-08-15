package com.yice.edu.cn.jw.service.standard;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.general.standard.Standard;
import com.yice.edu.cn.jw.dao.standard.StandardDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StandardService {
    @Autowired
    private StandardDao standardDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public Standard findStandardById(String id) {
        return standardDao.findStandardById(id);
    }
    @Transactional
    public void saveStandard(Standard standard) {
        standard.setId(sequenceId.nextId());
        standardDao.saveStandard(standard);
    }
    @Transactional(readOnly = true)
    public List<Standard> findStandardListByCondition(Standard standard) {
        return standardDao.findStandardListByCondition(standard);
    }
    @Transactional(readOnly = true)
    public long findStandardCountByCondition(Standard standard) {
        return standardDao.findStandardCountByCondition(standard);
    }
    @Transactional
    public void updateStandard(Standard standard) {
        standardDao.updateStandard(standard);
    }
    @Transactional
    public void deleteStandard(String id) {
        standardDao.deleteStandard(id);
    }
}
