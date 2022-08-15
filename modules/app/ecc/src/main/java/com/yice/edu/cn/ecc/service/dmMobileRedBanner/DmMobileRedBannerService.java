package com.yice.edu.cn.ecc.service.dmMobileRedBanner;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.yice.edu.cn.common.pojo.dm.dmMobileRedBanner.DmMobileRedBanner;
import com.yice.edu.cn.ecc.feignClient.dmMobileRedBanner.DmMobileRedBannerFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class DmMobileRedBannerService {
    @Autowired
    private DmMobileRedBannerFeign dmMobileRedBannerFeign;

    private final static Logger logger = LoggerFactory.getLogger(DmMobileRedBannerService.class);

    public DmMobileRedBanner findDmMobileRedBannerById(String id) {
        return dmMobileRedBannerFeign.findDmMobileRedBannerById(id);
    }

    //保存之前先更新之前流动红旗状态为已结束
    @Transactional
    public DmMobileRedBanner saveDmMobileRedBanner(DmMobileRedBanner dmMobileRedBanner) {
        dmMobileRedBannerFeign.updateStatus();
        return dmMobileRedBannerFeign.saveDmMobileRedBanner(dmMobileRedBanner);
    }

    public List<DmMobileRedBanner> findDmMobileRedBannerListByCondition(DmMobileRedBanner dmMobileRedBanner) {
        return dmMobileRedBannerFeign.findDmMobileRedBannerListByCondition(dmMobileRedBanner);
    }

    public DmMobileRedBanner findOneDmMobileRedBannerByCondition(DmMobileRedBanner dmMobileRedBanner) {
        return dmMobileRedBannerFeign.findOneDmMobileRedBannerByCondition(dmMobileRedBanner);
    }

    public long findDmMobileRedBannerCountByCondition(DmMobileRedBanner dmMobileRedBanner) {
        return dmMobileRedBannerFeign.findDmMobileRedBannerCountByCondition(dmMobileRedBanner);
    }

    public void updateDmMobileRedBanner(DmMobileRedBanner dmMobileRedBanner) {
        dmMobileRedBannerFeign.updateDmMobileRedBanner(dmMobileRedBanner);
    }

    public void updateDmMobileRedBannerForNotNull(DmMobileRedBanner dmMobileRedBanner) {
        dmMobileRedBannerFeign.updateDmMobileRedBannerForNotNull(dmMobileRedBanner);
    }

    public void deleteDmMobileRedBanner(String id) {
        dmMobileRedBannerFeign.deleteDmMobileRedBanner(id);
    }

    public void deleteDmMobileRedBannerByCondition(DmMobileRedBanner dmMobileRedBanner) {
        dmMobileRedBannerFeign.deleteDmMobileRedBannerByCondition(dmMobileRedBanner);
    }

    public DmMobileRedBanner findDmMobileRedBannerByClassId(String classId){
        return dmMobileRedBannerFeign.findDmMobileRedBannerByClassId(classId);
    }

    public void updateAwardRedBanner() {
        try {
            DmMobileRedBanner todayAwardRedBanner = dmMobileRedBannerFeign.findTodayAwardRedBanner();
            if(todayAwardRedBanner == null){
                return;
            }
            //将之前的流动红旗状态改为已结束
            dmMobileRedBannerFeign.updateStatus();
            //将待颁发红旗状态改为已生效
            todayAwardRedBanner.setStatus(1);
            dmMobileRedBannerFeign.updateStatusShowById(todayAwardRedBanner.getId());

        } catch (HystrixRuntimeException hystrixRuntimeException) {
            // 调用为服务可能失败，在此记录一下日志
            logger.error("微服务调用失败", hystrixRuntimeException);
        } catch (Exception ex) {
            logger.error("服务器错误", ex);
        }
    }
}
