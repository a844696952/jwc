package com.yice.edu.cn.tap.service.dm.ycVeriface;
import com.yice.edu.cn.common.pojo.xw.kq.KqOriginalData;
import com.yice.edu.cn.tap.feignClient.stuInAndOut.KqOriginalDataFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author:xushu
 * @date:2019/9/3
 */
@Service
public class YcVerifaceAlarmService {
    @Autowired
    private KqOriginalDataFeign kqOriginalDataFeign;

    public List<KqOriginalData> findKqOriginalDataListByCondition(KqOriginalData kqOriginalData) {
        return kqOriginalDataFeign.findKqOriginalDataListByCondition(kqOriginalData);
    }

    public long findKqOriginalDataCountByCondition(KqOriginalData kqOriginalData) {
        return kqOriginalDataFeign.findKqOriginalDataCountByCondition(kqOriginalData);
    }

    public void updateKqOriginalData(KqOriginalData kqOriginalData) {
        kqOriginalDataFeign.updateKqOriginalData(kqOriginalData);
    }
}
