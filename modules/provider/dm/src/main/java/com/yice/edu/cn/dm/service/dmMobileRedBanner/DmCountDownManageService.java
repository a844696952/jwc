package com.yice.edu.cn.dm.service.dmMobileRedBanner;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.dm.dmMobileRedBanner.DmCountDownManage;
import com.yice.edu.cn.dm.dao.dmMobileRedBanner.IDmCountDownManageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DmCountDownManageService {
    @Autowired
    public IDmCountDownManageDao dmCountDownManageDao;
    @Autowired
    private SequenceId sequenceId;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/

    /**
     * 根据id查询单条数据详情
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public DmCountDownManage findDmCountDownManageById(String id) {
        DmCountDownManage dc = dmCountDownManageDao.findDmCountDownManageById(id);
        return dc;
    }

    /**
     * 新增
     *
     * @param dmCountDownManage
     */
    @Transactional
    public void saveDmCountDownManage(DmCountDownManage dmCountDownManage) {
        String countDownId = sequenceId.nextId();
        //存入倒计时数据
        dmCountDownManage.setId(countDownId);
        dmCountDownManage.setStatus(Constant.DM_COUNT_DOWN_MANAGE.MANAGE_DISABLE);
        dmCountDownManageDao.saveDmCountDownManage(dmCountDownManage);

    }

    /**
     * 查询
     *
     * @param dmCountDownManage
     * @return
     */
    @Transactional
    public List<DmCountDownManage> findDmCountDownManageListByCondition(DmCountDownManage dmCountDownManage) {
        List<DmCountDownManage> dmList = dmCountDownManageDao.findDmCountDownManageListByCondition(dmCountDownManage);
        return dmList;
    }

    /**
     * 判断breakTime时间是否过期，如果过期前台展示自动改变状态
     *
     * @param dmList
     * @return
     */
    private List<DmCountDownManage> judgeTime(List<DmCountDownManage> dmList) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long currentTime = System.currentTimeMillis();
        List<DmCountDownManage> newDmList = dmList.stream().map(skt -> {
            if (skt.getStatus() == Constant.DM_COUNT_DOWN_MANAGE.MANAGE_VALID){
                Date dateTime = null;
                try {
                    dateTime = df.parse(skt.getBreakTime());
                    if (dateTime.getTime() < currentTime) {
                        skt.setStatus(Constant.DM_COUNT_DOWN_MANAGE.MANAGE_DISABLE);
                        dmCountDownManageDao.updateDmCountDownManageStatus(skt);
                        return skt;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            return skt;
        }).collect(Collectors.toList());
        return newDmList;
    }

    @Transactional(readOnly = true)
    public DmCountDownManage findOneDmCountDownManageByCondition(DmCountDownManage dmCountDownManage) {
        return dmCountDownManageDao.findOneDmCountDownManageByCondition(dmCountDownManage);
    }

    @Transactional(readOnly = true)
    public long findDmCountDownManageCountByCondition(DmCountDownManage dmCountDownManage) {
        return dmCountDownManageDao.findDmCountDownManageCountByCondition(dmCountDownManage);
    }

    /**
     * 修改，先删再存，可以优化
     *
     * @param dmCountDownManage
     */
    @Transactional
    public void updateDmCountDownManage(DmCountDownManage dmCountDownManage) {
        dmCountDownManageDao.updateDmCountDownManage(dmCountDownManage);
    }

    @Transactional
    public void updateDmCountDownManageForAll(DmCountDownManage dmCountDownManage) {
        dmCountDownManageDao.updateDmCountDownManageForAll(dmCountDownManage);
    }

    /**
     * 根据id删除
     *
     * @param id
     */
    @Transactional
    public void deleteDmCountDownManage(String id) {
        dmCountDownManageDao.deleteDmCountDownManage(id);
    }

    @Transactional
    public void deleteDmCountDownManageByCondition(DmCountDownManage dmCountDownManage) {
        dmCountDownManageDao.deleteDmCountDownManageByCondition(dmCountDownManage);
    }

    @Transactional
    public void batchSaveDmCountDownManage(List<DmCountDownManage> dmCountDownManages) {
        dmCountDownManages.forEach(dmCountDownManage -> dmCountDownManage.setId(sequenceId.nextId()));
        dmCountDownManageDao.batchSaveDmCountDownManage(dmCountDownManages);
    }

    @Transactional
    public void updateDmCountDownManageStatus(DmCountDownManage dmCountDownManage) {
        dmCountDownManageDao.updateDmCountDownManageStatus(dmCountDownManage);

        dmCountDownManage.setStatus(Constant.DM_COUNT_DOWN_MANAGE.MANAGE_DISABLE);
        dmCountDownManageDao.updateDmCountDownManageStatusAll(dmCountDownManage);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
