package com.yice.edu.cn.osp.service.wb.latticePager;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.wb.latticePager.DmPagerBackground;
import com.yice.edu.cn.common.pojo.wb.latticePager.DmPagerNumber;
import com.yice.edu.cn.osp.feignClient.wb.latticePager.DmPagerNumberFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmPagerNumberService {
    @Autowired
    private DmPagerNumberFeign dmPagerNumberFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public DmPagerNumber findDmPagerNumberById(String id) {
        return dmPagerNumberFeign.findDmPagerNumberById(id);
    }

    public DmPagerNumber saveDmPagerNumber(DmPagerNumber dmPagerNumber) {
        return dmPagerNumberFeign.saveDmPagerNumber(dmPagerNumber);
    }

    public void batchSaveDmPagerNumber(List<DmPagerNumber> dmPagerNumbers){
        dmPagerNumberFeign.batchSaveDmPagerNumber(dmPagerNumbers);
    }

    public List<DmPagerNumber> findDmPagerNumberListByCondition(DmPagerNumber dmPagerNumber) {
        return dmPagerNumberFeign.findDmPagerNumberListByCondition(dmPagerNumber);
    }

    public DmPagerNumber findOneDmPagerNumberByCondition(DmPagerNumber dmPagerNumber) {
        return dmPagerNumberFeign.findOneDmPagerNumberByCondition(dmPagerNumber);
    }

    public long findDmPagerNumberCountByCondition(DmPagerNumber dmPagerNumber) {
        return dmPagerNumberFeign.findDmPagerNumberCountByCondition(dmPagerNumber);
    }

    public void updateDmPagerNumber(DmPagerNumber dmPagerNumber) {
        dmPagerNumberFeign.updateDmPagerNumber(dmPagerNumber);
    }

    public void updateDmPagerNumberForAll(DmPagerNumber dmPagerNumber) {
        dmPagerNumberFeign.updateDmPagerNumberForAll(dmPagerNumber);
    }

    public void deleteDmPagerNumber(String id) {
        dmPagerNumberFeign.deleteDmPagerNumber(id);
    }

    public void deleteDmPagerNumberByCondition(DmPagerNumber dmPagerNumber) {
        dmPagerNumberFeign.deleteDmPagerNumberByCondition(dmPagerNumber);
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public List<DmPagerBackground> findDmPagerBackgroundImg(String id) {
       return dmPagerNumberFeign.findDmPagerBackgroundImg(id);
    }

    public ResponseJson updateRecover(DmPagerNumber dmPagerNumber) {
        return dmPagerNumberFeign.updateRecover(dmPagerNumber);
    }
}
