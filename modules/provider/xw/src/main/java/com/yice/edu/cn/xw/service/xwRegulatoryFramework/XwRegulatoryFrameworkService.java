package com.yice.edu.cn.xw.service.xwRegulatoryFramework;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.xw.xwRegulatoryFramework.XwRegulatoryFramework;
import com.yice.edu.cn.xw.dao.xwRegulatoryFramework.IXwRegulatoryFrameworkDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class XwRegulatoryFrameworkService {
    @Autowired
    private IXwRegulatoryFrameworkDao xwRegulatoryFrameworkDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public XwRegulatoryFramework findXwRegulatoryFrameworkById(String id) {
        return xwRegulatoryFrameworkDao.findXwRegulatoryFrameworkById(id);
    }
    @Transactional
    public void saveXwRegulatoryFramework(XwRegulatoryFramework xwRegulatoryFramework) {
        xwRegulatoryFramework.setId(sequenceId.nextId());
        xwRegulatoryFrameworkDao.saveXwRegulatoryFramework(xwRegulatoryFramework);
    }
    @Transactional(readOnly = true)
    public List<XwRegulatoryFramework> findXwRegulatoryFrameworkListByCondition(XwRegulatoryFramework xwRegulatoryFramework) {
        return xwRegulatoryFrameworkDao.findXwRegulatoryFrameworkListByCondition(xwRegulatoryFramework);
    }
    @Transactional(readOnly = true)
    public XwRegulatoryFramework findOneXwRegulatoryFrameworkByCondition(XwRegulatoryFramework xwRegulatoryFramework) {
        return xwRegulatoryFrameworkDao.findOneXwRegulatoryFrameworkByCondition(xwRegulatoryFramework);
    }
    @Transactional(readOnly = true)
    public long findXwRegulatoryFrameworkCountByCondition(XwRegulatoryFramework xwRegulatoryFramework) {
        return xwRegulatoryFrameworkDao.findXwRegulatoryFrameworkCountByCondition(xwRegulatoryFramework);
    }
    @Transactional
    public void updateXwRegulatoryFramework(XwRegulatoryFramework xwRegulatoryFramework) {
        if(xwRegulatoryFramework.getContent()==null){
            xwRegulatoryFramework.setContent("");
        }
        xwRegulatoryFrameworkDao.updateXwRegulatoryFramework(xwRegulatoryFramework);

    }
    @Transactional
    public void deleteXwRegulatoryFramework(String id) {
        xwRegulatoryFrameworkDao.deleteXwRegulatoryFramework(id);
    }
    @Transactional
    public void deleteXwRegulatoryFrameworkByCondition(XwRegulatoryFramework xwRegulatoryFramework) {
        xwRegulatoryFrameworkDao.deleteXwRegulatoryFrameworkByCondition(xwRegulatoryFramework);
    }
    @Transactional
    public void batchSaveXwRegulatoryFramework(List<XwRegulatoryFramework> xwRegulatoryFrameworks){
        xwRegulatoryFrameworks.forEach(xwRegulatoryFramework -> xwRegulatoryFramework.setId(sequenceId.nextId()));
        xwRegulatoryFrameworkDao.batchSaveXwRegulatoryFramework(xwRegulatoryFrameworks);
    }

}
