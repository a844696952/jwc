package com.yice.edu.cn.dm.service.school;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.dm.school.DmSchoolBigNews;
import com.yice.edu.cn.dm.dao.school.IDmSchoolBigNewsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DmSchoolBigNewsService {
    @Autowired
    private IDmSchoolBigNewsDao dmSchoolBigNewsDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public DmSchoolBigNews findDmSchoolBigNewsById(String id) {
        return dmSchoolBigNewsDao.findDmSchoolBigNewsById(id);
    }

    @Transactional
    public void saveDmSchoolBigNews(DmSchoolBigNews dmSchoolBigNews) {
        dmSchoolBigNews.setId(sequenceId.nextId());
        dmSchoolBigNewsDao.saveDmSchoolBigNews(dmSchoolBigNews);
    }

    @Transactional(readOnly = true)
    public List<DmSchoolBigNews> findDmSchoolBigNewsListByCondition(DmSchoolBigNews dmSchoolBigNews) {
        return dmSchoolBigNewsDao.findDmSchoolBigNewsListByCondition(dmSchoolBigNews);
    }

    @Transactional(readOnly = true)
    public DmSchoolBigNews findOneDmSchoolBigNewsByCondition(DmSchoolBigNews dmSchoolBigNews) {
        return dmSchoolBigNewsDao.findOneDmSchoolBigNewsByCondition(dmSchoolBigNews);
    }

    @Transactional(readOnly = true)
    public long findDmSchoolBigNewsCountByCondition(DmSchoolBigNews dmSchoolBigNews) {
        return dmSchoolBigNewsDao.findDmSchoolBigNewsCountByCondition(dmSchoolBigNews);
    }

    @Transactional
    public void updateDmSchoolBigNews(DmSchoolBigNews dmSchoolBigNews) {
        dmSchoolBigNewsDao.updateDmSchoolBigNews(dmSchoolBigNews);
    }

    @Transactional
    public void deleteDmSchoolBigNews(String id) {
        dmSchoolBigNewsDao.deleteDmSchoolBigNews(id);
    }

    @Transactional
    public void deleteDmSchoolBigNewsByCondition(DmSchoolBigNews dmSchoolBigNews) {
        dmSchoolBigNewsDao.deleteDmSchoolBigNewsByCondition(dmSchoolBigNews);
    }

    @Transactional
    public void batchSaveDmSchoolBigNews(List<DmSchoolBigNews> dmSchoolBigNewss) {
        dmSchoolBigNewsDao.batchSaveDmSchoolBigNews(dmSchoolBigNewss);
    }

    @Transactional(readOnly = true)
    public List<DmSchoolBigNews> findDmSchoolBigNewsListByactiveNameLike(DmSchoolBigNews dmSchoolBigNews) {
        return dmSchoolBigNewsDao.findDmSchoolBigNewsListByactiveNameLike(dmSchoolBigNews);
    }

    @Transactional(readOnly = true)
    public Long findDmSchoolBigNewsListByactiveNameLikeCount(DmSchoolBigNews dmSchoolBigNews) {
        return dmSchoolBigNewsDao.findDmSchoolBigNewsListByactiveNameLikeCount(dmSchoolBigNews);
    }

}
