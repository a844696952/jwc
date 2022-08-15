package com.yice.edu.cn.osp.service.dm.dmMobileRedBanner;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.dmMobileRedBanner.DmMobileRedBanner;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.osp.feignClient.dm.dmMobileRedBanner.DmMobileRedBannerFeign;
import com.yice.edu.cn.osp.feignClient.jw.classes.JwClassesFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

@Service
public class DmMobileRedBannerService {
    @Autowired
    private DmMobileRedBannerFeign dmMobileRedBannerFeign;
    @Autowired
    JwClassesFeign jwClassesFeign;

    public DmMobileRedBanner findDmMobileRedBannerById(String id) {
        DmMobileRedBanner dmMobileRedBanner = dmMobileRedBannerFeign.findDmMobileRedBannerById(id);
        String[] classIdList = dmMobileRedBanner.getClassIds().split(",");
        List<DmMobileRedBanner.GradeAndClazz> gradeAndClazzList = new ArrayList<>();
        for (String classId : classIdList) {
            JwClasses jwClasses = jwClassesFeign.findJwClassesById(classId);
            if(jwClasses == null){
                continue;
            }
            DmMobileRedBanner.GradeAndClazz gradeAndClazz = new DmMobileRedBanner.GradeAndClazz();
            gradeAndClazz.setGradeId(jwClasses.getGradeId());
            gradeAndClazz.setClassId(classId);
            gradeAndClazzList.add(gradeAndClazz);
        }
        dmMobileRedBanner.setGradeAndClazzList(gradeAndClazzList);
        return dmMobileRedBanner;
    }

    //保存之前先更新之前流动红旗状态为已结束
    @Transactional
    public ResponseJson saveDmMobileRedBanner(DmMobileRedBanner dmMobileRedBanner) {
        if(1 == dmMobileRedBanner.getStatus()){
            dmMobileRedBannerFeign.updateStatus();
        }else if(0 == dmMobileRedBanner.getStatus()){
            DmMobileRedBanner toBeIssuedDmMobileRedBanner = dmMobileRedBannerFeign.findToBeIssuedDmMobileRedBannerByAwardTime(dmMobileRedBanner.getAwardTime());
            if(toBeIssuedDmMobileRedBanner != null){
                return new ResponseJson(false, dmMobileRedBanner.getAwardTime()+"已存在待颁发流动红旗！");
            }
        }
        return new ResponseJson(dmMobileRedBannerFeign.saveDmMobileRedBanner(dmMobileRedBanner));
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

    /**
     * 更新流动红旗时，先判断是否是展示中，如果是展示中将正在展示中的更新为已结束
     * @param dmMobileRedBanner
     * @return
     */
    public ResponseJson updateDmMobileRedBanner(DmMobileRedBanner dmMobileRedBanner) {
        //待展示先判断是否有当天待展示的红旗
        return update(dmMobileRedBanner);
    }

    public ResponseJson updateDmMobileRedBannerForNotNull(DmMobileRedBanner dmMobileRedBanner) {
        return update(dmMobileRedBanner);
    }

    public void deleteDmMobileRedBanner(String id) {
        dmMobileRedBannerFeign.deleteDmMobileRedBanner(id);
    }

    public void deleteDmMobileRedBannerByCondition(DmMobileRedBanner dmMobileRedBanner) {
        dmMobileRedBannerFeign.deleteDmMobileRedBannerByCondition(dmMobileRedBanner);
    }

    private ResponseJson update(DmMobileRedBanner dmMobileRedBanner){
        if(0 == dmMobileRedBanner.getStatus()){
            DmMobileRedBanner toBeIssuedDmMobileRedBanner = dmMobileRedBannerFeign.findToBeIssuedDmMobileRedBannerByAwardTime(dmMobileRedBanner.getAwardTime());
            if(toBeIssuedDmMobileRedBanner != null && !toBeIssuedDmMobileRedBanner.getId().equals(dmMobileRedBanner.getId())){
                return new ResponseJson(false, dmMobileRedBanner.getAwardTime()+"已存在待颁发流动红旗！");
            }
        }else if(1 == dmMobileRedBanner.getStatus()){
            //展示中先将展示中的更新为已结束
            dmMobileRedBannerFeign.updateStatus();
        }
        dmMobileRedBannerFeign.updateDmMobileRedBannerForNotNull(dmMobileRedBanner);
        return new ResponseJson();
    }
}
