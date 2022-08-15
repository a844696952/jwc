package com.yice.edu.cn.xw.service.dj;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.xw.dj.DjClassify;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberActivity.XwDjActivity;
import com.yice.edu.cn.xw.dao.dj.IDjClassifyDao;
import com.yice.edu.cn.xw.dao.dj.partyMemberActivity.IXwDjActivityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DjClassifyService {
    @Autowired
    private IDjClassifyDao djClassifyDao;
    @Autowired
    private IXwDjActivityDao xwDjActivityDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public DjClassify findDjClassifyById(String id) {
        return djClassifyDao.findDjClassifyById(id);
    }
    @Transactional
    public void saveDjClassify(DjClassify djClassify) {
        djClassify.setId(sequenceId.nextId());
        djClassifyDao.saveDjClassify(djClassify);
    }
    @Transactional(readOnly = true)
    public List<DjClassify> findDjClassifyListByCondition(DjClassify djClassify) {
        return djClassifyDao.findDjClassifyListByCondition(djClassify);
    }
    @Transactional(readOnly = true)
    public DjClassify findOneDjClassifyByCondition(DjClassify djClassify) {
        return djClassifyDao.findOneDjClassifyByCondition(djClassify);
    }
    @Transactional(readOnly = true)
    public List<DjClassify> findActivityDjClassify() {
        DjClassify djClassify = new DjClassify();
        djClassify.setClassifyType("ACTIVITY");
        //pager不能为null
        Pager pager = new Pager();
        djClassify.setPager(pager);
        return djClassifyDao.findDjClassifyListByCondition(djClassify);
    }
    @Transactional(readOnly = true)
    public long findDjClassifyCountByCondition(DjClassify djClassify) {
        return djClassifyDao.findDjClassifyCountByCondition(djClassify);
    }
    @Transactional
    public void updateDjClassify(DjClassify djClassify) {
        djClassifyDao.updateDjClassify(djClassify);
    }
    @Transactional
    public void deleteDjClassify(String id) {
        djClassifyDao.deleteDjClassify(id);
    }
    @Transactional
    public boolean deleteDjClassifyNotEnpty(String id) {
        long count = xwDjActivityDao.findXwDjActivityCountByColumnId(id);
        if(count>0){
            return false;
        }
        djClassifyDao.deleteDjClassify(id);
        return true;
    }
    @Transactional
    public void deleteDjClassifyByCondition(DjClassify djClassify) {
        djClassifyDao.deleteDjClassifyByCondition(djClassify);
    }
    @Transactional
    public void batchSaveDjClassify(List<DjClassify> djClassifys){
        djClassifys.forEach(djClassify -> djClassify.setId(sequenceId.nextId()));
        djClassifyDao.batchSaveDjClassify(djClassifys);
    }

    /**
     * 根据类型 查询 下拉框（公用）
     * @param djClassify
     * @return
     */
    @Transactional(readOnly = true)
    public List<DjClassify> selectClassifyListByType(DjClassify djClassify) {
        return djClassifyDao.selectClassifyListByType(djClassify);
    }
    /**
     * 查询该类型下拉框是否已经存在
     */
    @Transactional(readOnly = true)
    public List<DjClassify> findClassifyListByType(DjClassify djClassify) {
        return djClassifyDao.findClassifyListByType(djClassify);
    }

}
