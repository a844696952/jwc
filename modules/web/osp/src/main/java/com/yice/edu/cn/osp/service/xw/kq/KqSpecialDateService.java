package com.yice.edu.cn.osp.service.xw.kq;

import com.yice.edu.cn.common.pojo.xw.kq.KqDailyStatistical;
import com.yice.edu.cn.common.pojo.xw.kq.KqOriginalData;
import com.yice.edu.cn.common.pojo.xw.kq.KqSpecialDate;
import com.yice.edu.cn.osp.feignClient.xw.kq.KqSpecialDateFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KqSpecialDateService {
    @Autowired
    private KqSpecialDateFeign kqSpecialDateFeign;

    public KqSpecialDate findKqSpecialDateById(String id) {
        return kqSpecialDateFeign.findKqSpecialDateById(id);
    }

    public KqSpecialDate saveKqSpecialDate(KqSpecialDate kqSpecialDate) {
        return kqSpecialDateFeign.saveKqSpecialDate(kqSpecialDate);
    }

    public List<KqSpecialDate> findKqSpecialDateListByCondition(KqSpecialDate kqSpecialDate) {
        return kqSpecialDateFeign.findKqSpecialDateListByCondition(kqSpecialDate);
    }

    public KqSpecialDate findOneKqSpecialDateByCondition(KqSpecialDate kqSpecialDate) {
        return kqSpecialDateFeign.findOneKqSpecialDateByCondition(kqSpecialDate);
    }

    public long findKqSpecialDateCountByCondition(KqSpecialDate kqSpecialDate) {
        return kqSpecialDateFeign.findKqSpecialDateCountByCondition(kqSpecialDate);
    }

    public void updateKqSpecialDate(KqSpecialDate kqSpecialDate) {
        kqSpecialDateFeign.updateKqSpecialDate(kqSpecialDate);
    }

    public void deleteKqSpecialDate(String id) {
        kqSpecialDateFeign.deleteKqSpecialDate(id);
    }

    public void deleteKqSpecialDateByCondition(KqSpecialDate kqSpecialDate) {
        kqSpecialDateFeign.deleteKqSpecialDateByCondition(kqSpecialDate);
    }


}
