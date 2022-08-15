package com.yice.edu.cn.dm.service.wb.LatticePager;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.wb.latticePager.DmPagerBackground;
import com.yice.edu.cn.common.pojo.wb.latticePager.DmPagerNumber;
import com.yice.edu.cn.dm.dao.wb.LatticePager.IDmPagerBackgroundDao;
import com.yice.edu.cn.dm.dao.wb.LatticePager.IDmPagerNumberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DmPagerNumberService {
    @Autowired
    private IDmPagerNumberDao dmPagerNumberDao;
    @Autowired
    private IDmPagerBackgroundDao dmPagerBackgroundDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public DmPagerNumber findDmPagerNumberById(String id) {
        return dmPagerNumberDao.findDmPagerNumberById(id);
    }
    @Transactional
    public void saveDmPagerNumber(DmPagerNumber dmPagerNumber) {
        dmPagerNumber.setId(sequenceId.nextId());
        dmPagerNumberDao.saveDmPagerNumber(dmPagerNumber);
    }
    @Transactional(readOnly = true)
    public List<DmPagerNumber> findDmPagerNumberListByCondition(DmPagerNumber dmPagerNumber) {
        return dmPagerNumberDao.findDmPagerNumberListByCondition(dmPagerNumber);
    }
    @Transactional(readOnly = true)
    public DmPagerNumber findOneDmPagerNumberByCondition(DmPagerNumber dmPagerNumber) {
        return dmPagerNumberDao.findOneDmPagerNumberByCondition(dmPagerNumber);
    }
    @Transactional(readOnly = true)
    public long findDmPagerNumberCountByCondition(DmPagerNumber dmPagerNumber) {
        return dmPagerNumberDao.findDmPagerNumberCountByCondition(dmPagerNumber);
    }
    @Transactional
    public void updateDmPagerNumber(DmPagerNumber dmPagerNumber) {
        dmPagerNumberDao.updateDmPagerNumber(dmPagerNumber);
    }
    @Transactional
    public void updateDmPagerNumberForAll(DmPagerNumber dmPagerNumber) {
        dmPagerNumberDao.updateDmPagerNumberForAll(dmPagerNumber);
    }
    @Transactional
    public void deleteDmPagerNumber(String id) {
        dmPagerNumberDao.deleteDmPagerNumber(id);
    }
    @Transactional
    public void deleteDmPagerNumberByCondition(DmPagerNumber dmPagerNumber) {
        dmPagerNumberDao.deleteDmPagerNumberByCondition(dmPagerNumber);
    }
    @Transactional
    public void batchSaveDmPagerNumber(List<DmPagerNumber> dmPagerNumbers){
        dmPagerNumbers.forEach(dmPagerNumber -> dmPagerNumber.setId(sequenceId.nextId()));
        dmPagerNumberDao.batchSaveDmPagerNumber(dmPagerNumbers);
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public List<DmPagerBackground> findDmPagerBackgroundImg(String id) {
        DmPagerNumber dmPagerNumber = findDmPagerNumberById(id);
        if(ObjectUtil.isNull(dmPagerNumber) || ObjectUtil.isNull(dmPagerNumber.getPagerNumber())){
            return null;
        }
        String[] arrays = dmPagerNumber.getPagerNumber().split(",");
        DmPagerBackground dmPagerBackground = new DmPagerBackground();
        dmPagerBackground.setLatticeId(dmPagerNumber.getLatticeId());
        dmPagerBackground.setNumberArrays(arrays);
        return  dmPagerBackgroundDao.findDmPagerBackgroundInNumber(dmPagerBackground);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseJson updateRecover(DmPagerNumber dmPagerNumber) {
        DmPagerNumber pagerNumber = findDmPagerNumberById(dmPagerNumber.getId());
        if(ObjectUtil.isNull(pagerNumber)){
            return new ResponseJson(false,"点阵页码数据错误");
        }
        pagerNumber.setModifyTime(DateUtil.now());
        pagerNumber.setIsRecycle(1);
        dmPagerNumberDao.updateDmPagerNumber(pagerNumber);
        //置空之前的表
        DmPagerBackground dmPagerBackground = new DmPagerBackground();
        dmPagerBackground.setLatticeId(pagerNumber.getLatticeId());
        List<DmPagerBackground> lists = dmPagerBackgroundDao.findPagerNumberIsNull(dmPagerBackground);
        if(CollectionUtil.isNotEmpty(lists)){
            //说明有重置的
            lists.forEach(v ->{
                v.setPagerNumber(null);
                dmPagerBackgroundDao.updateDmPagerBackgroundForAll(v);
            });
        }
        return new ResponseJson("回收成功");
    }
}
