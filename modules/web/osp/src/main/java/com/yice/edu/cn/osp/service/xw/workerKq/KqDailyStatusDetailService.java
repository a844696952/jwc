package com.yice.edu.cn.osp.service.xw.workerKq;

import com.yice.edu.cn.common.pojo.xw.workerKq.KqDailyStatusDetail;
import com.yice.edu.cn.osp.feignClient.xw.workerKq.KqDailyStatusDetailFeign;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KqDailyStatusDetailService {
    @Autowired
    private KqDailyStatusDetailFeign kqDailyStatusDetailFeign;

    public KqDailyStatusDetail findKqDailyStatusDetailById(String id) {
        return kqDailyStatusDetailFeign.findKqDailyStatusDetailById(id);
    }

    public KqDailyStatusDetail saveKqDailyStatusDetail(KqDailyStatusDetail kqDailyStatusDetail) {
        return kqDailyStatusDetailFeign.saveKqDailyStatusDetail(kqDailyStatusDetail);
    }

    public List<KqDailyStatusDetail> findKqDailyStatusDetailListByCondition(KqDailyStatusDetail kqDailyStatusDetail) {
        return kqDailyStatusDetailFeign.findKqDailyStatusDetailListByCondition(kqDailyStatusDetail);
    }

    public KqDailyStatusDetail findOneKqDailyStatusDetailByCondition(KqDailyStatusDetail kqDailyStatusDetail) {
        return kqDailyStatusDetailFeign.findOneKqDailyStatusDetailByCondition(kqDailyStatusDetail);
    }

    public long findKqDailyStatusDetailCountByCondition(KqDailyStatusDetail kqDailyStatusDetail) {
        return kqDailyStatusDetailFeign.findKqDailyStatusDetailCountByCondition(kqDailyStatusDetail);
    }

    public void updateKqDailyStatusDetail(KqDailyStatusDetail kqDailyStatusDetail) {
        kqDailyStatusDetailFeign.updateKqDailyStatusDetail(kqDailyStatusDetail);
    }

    public void deleteKqDailyStatusDetail(String id) {
        kqDailyStatusDetailFeign.deleteKqDailyStatusDetail(id);
    }

    public void deleteKqDailyStatusDetailByCondition(KqDailyStatusDetail kqDailyStatusDetail) {
        kqDailyStatusDetailFeign.deleteKqDailyStatusDetailByCondition(kqDailyStatusDetail);
    }
    public void saveOaKqDailyStatusDetail() {
        kqDailyStatusDetailFeign.saveOaKqDailyStatusDetail();
    }
}
