package com.yice.edu.cn.xw.service.psychologConsult;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.xw.psycholgConsult.XwPshcholgConsult;
import com.yice.edu.cn.xw.dao.pshcholgConsult.IXwPshcholgConsultDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class XwPshcholgConsultService {
    @Autowired
    private IXwPshcholgConsultDao xwPshcholgConsultDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public XwPshcholgConsult findXwPshcholgConsultById(String id) {
        return xwPshcholgConsultDao.findXwPshcholgConsultById(id);
    }

    @Transactional
    public void saveXwPshcholgConsult(XwPshcholgConsult xwPshcholgConsult) {
        xwPshcholgConsult.setId(sequenceId.nextId());
        xwPshcholgConsultDao.saveXwPshcholgConsult(xwPshcholgConsult);
    }

    @Transactional(readOnly = true)
    public List<XwPshcholgConsult> findXwPshcholgConsultListByCondition(XwPshcholgConsult xwPshcholgConsult) {
        Pager pager = new Pager();
        pager.setLike("name");
        xwPshcholgConsult.setPager(pager);
        return xwPshcholgConsultDao.findXwPshcholgConsultListByCondition(xwPshcholgConsult);
    }

    @Transactional(readOnly = true)
    public XwPshcholgConsult findOneXwPshcholgConsultByCondition(XwPshcholgConsult xwPshcholgConsult) {
        return xwPshcholgConsultDao.findOneXwPshcholgConsultByCondition(xwPshcholgConsult);
    }

    @Transactional(readOnly = true)
    public long findXwPshcholgConsultCountByCondition(XwPshcholgConsult xwPshcholgConsult) {
        return xwPshcholgConsultDao.findXwPshcholgConsultCountByCondition(xwPshcholgConsult);
    }

    @Transactional
    public void updateXwPshcholgConsult(XwPshcholgConsult xwPshcholgConsult) {
        if (xwPshcholgConsult.getTel() == null) {
            xwPshcholgConsult.setTel("");
        }
        if (xwPshcholgConsult.getQq() == null) {
            xwPshcholgConsult.setQq("");
        }
        if (xwPshcholgConsult.getBirthday() == null) {
            xwPshcholgConsult.setBirthday("");
        }
        if (xwPshcholgConsult.getReConsultTime() == null) {
            xwPshcholgConsult.setReConsultTime("");
        }
        xwPshcholgConsultDao.updateXwPshcholgConsult(xwPshcholgConsult);
    }

    @Transactional
    public void deleteXwPshcholgConsult(String id) {
        xwPshcholgConsultDao.deleteXwPshcholgConsult(id);
    }

    @Transactional
    public void deleteXwPshcholgConsultByCondition(XwPshcholgConsult xwPshcholgConsult) {
        xwPshcholgConsultDao.deleteXwPshcholgConsultByCondition(xwPshcholgConsult);
    }

    @Transactional
    public void batchSaveXwPshcholgConsult(List<XwPshcholgConsult> xwPshcholgConsults) {
        xwPshcholgConsults.forEach(xwPshcholgConsult -> xwPshcholgConsult.setId(sequenceId.nextId()));
        xwPshcholgConsultDao.batchSaveXwPshcholgConsult(xwPshcholgConsults);
    }

    @Transactional(readOnly = true)//查询时间
    public List<XwPshcholgConsult> findXwPshcholgConsultByCondition2(XwPshcholgConsult xwPshcholgConsult) {
        String[] searchTime = xwPshcholgConsult.getSearchTime();
        if (searchTime != null && searchTime.length > 0) {
            xwPshcholgConsult.setSearchStartTime(searchTime[0]);
            xwPshcholgConsult.setSearchEndTime(searchTime[1]);
        }
        if(xwPshcholgConsult.getSearchStartTime() != null  && xwPshcholgConsult.getSearchEndTime()!= null ) {
            String startTime = DateUtil.format(DateUtil.beginOfDay(DateUtil.parse(xwPshcholgConsult.getSearchStartTime())), "yyyy-MM-dd HH:mm:ss");
            String endTime = DateUtil.format(DateUtil.endOfDay(DateUtil.parse(xwPshcholgConsult.getSearchEndTime())), "yyyy-MM-dd HH:mm:ss");
            xwPshcholgConsult.setSearchStartTime(startTime);
            xwPshcholgConsult.setSearchEndTime(endTime);
        }
        return xwPshcholgConsultDao.findXwPshcholgConsultByCondition2(xwPshcholgConsult);
    }

    @Transactional(readOnly = true)
    public Long findXwPshcholgConsultCountByCondition2(XwPshcholgConsult xwPshcholgConsult) {
        String[] searchTime = xwPshcholgConsult.getSearchTime();
        if (searchTime != null && searchTime.length > 0) {
            xwPshcholgConsult.setSearchStartTime(searchTime[0]);
            xwPshcholgConsult.setSearchEndTime(searchTime[1]);
        }
        return xwPshcholgConsultDao.findXwPshcholgConsultCountByCondition2(xwPshcholgConsult);
    }
}
