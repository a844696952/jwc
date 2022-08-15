package com.yice.edu.cn.xw.service.searchGoods;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.xw.searchGoods.XwSearchGoods;
import com.yice.edu.cn.xw.dao.searchGoods.IXwSearchGoodsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class XwSearchGoodsService {
    @Autowired
    private IXwSearchGoodsDao xwSearchGoodsDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public XwSearchGoods findXwSearchGoodsById(String id) {
        return xwSearchGoodsDao.findXwSearchGoodsById(id);
    }
    @Transactional
    public void saveXwSearchGoods(XwSearchGoods xwSearchGoods) {
        xwSearchGoods.setId(sequenceId.nextId());
        xwSearchGoodsDao.saveXwSearchGoods(xwSearchGoods);
    }
    @Transactional(readOnly = true)
    public List<XwSearchGoods> findXwSearchGoodsListByCondition(XwSearchGoods xwSearchGoods) {
        return xwSearchGoodsDao.findXwSearchGoodsListByCondition(xwSearchGoods);
    }
    @Transactional(readOnly = true)
    public XwSearchGoods findOneXwSearchGoodsByCondition(XwSearchGoods xwSearchGoods) {
        return xwSearchGoodsDao.findOneXwSearchGoodsByCondition(xwSearchGoods);
    }
    @Transactional(readOnly = true)
    public long findXwSearchGoodsCountByCondition(XwSearchGoods xwSearchGoods) {
        return xwSearchGoodsDao.findXwSearchGoodsCountByCondition(xwSearchGoods);
    }
    @Transactional
    public void updateXwSearchGoods(XwSearchGoods xwSearchGoods) {
        xwSearchGoodsDao.updateXwSearchGoods(xwSearchGoods);
    }
    @Transactional
    public void deleteXwSearchGoods(String id) {
        xwSearchGoodsDao.deleteXwSearchGoods(id);
    }
    @Transactional
    public void deleteXwSearchGoodsByCondition(XwSearchGoods xwSearchGoods) {
        xwSearchGoodsDao.deleteXwSearchGoodsByCondition(xwSearchGoods);
    }
    @Transactional
    public void batchSaveXwSearchGoods(List<XwSearchGoods> xwSearchGoodss){
        xwSearchGoodss.forEach(xwSearchGoods -> xwSearchGoods.setId(sequenceId.nextId()));
        xwSearchGoodsDao.batchSaveXwSearchGoods(xwSearchGoodss);
    }

    @Transactional
    public void updateXwSearchGoodsReturn(XwSearchGoods xwSearchGoods) {
        xwSearchGoodsDao.updateXwSearchGoodsReturn(xwSearchGoods);
    }

}
