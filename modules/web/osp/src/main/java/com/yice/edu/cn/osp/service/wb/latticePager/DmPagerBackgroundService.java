package com.yice.edu.cn.osp.service.wb.latticePager;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.wb.latticePager.DmPagerBackground;
import com.yice.edu.cn.osp.feignClient.wb.latticePager.DmPagerBackgroundFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class DmPagerBackgroundService {
    @Autowired
    private DmPagerBackgroundFeign dmPagerBackgroundFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public DmPagerBackground findDmPagerBackgroundById(String id) {
        return dmPagerBackgroundFeign.findDmPagerBackgroundById(id);
    }

    public DmPagerBackground saveDmPagerBackground(DmPagerBackground dmPagerBackground) {
        return dmPagerBackgroundFeign.saveDmPagerBackground(dmPagerBackground);
    }

    public void batchSaveDmPagerBackground(List<DmPagerBackground> dmPagerBackgrounds){
        dmPagerBackgroundFeign.batchSaveDmPagerBackground(dmPagerBackgrounds);
    }

    public List<DmPagerBackground> findDmPagerBackgroundListByCondition(DmPagerBackground dmPagerBackground) {
        return dmPagerBackgroundFeign.findDmPagerBackgroundListByCondition(dmPagerBackground);
    }

    public DmPagerBackground findOneDmPagerBackgroundByCondition(DmPagerBackground dmPagerBackground) {
        return dmPagerBackgroundFeign.findOneDmPagerBackgroundByCondition(dmPagerBackground);
    }

    public long findDmPagerBackgroundCountByCondition(DmPagerBackground dmPagerBackground) {
        return dmPagerBackgroundFeign.findDmPagerBackgroundCountByCondition(dmPagerBackground);
    }

    public void updateDmPagerBackground(DmPagerBackground dmPagerBackground) {
        dmPagerBackgroundFeign.updateDmPagerBackground(dmPagerBackground);
    }

    public void updateDmPagerBackgroundForAll(DmPagerBackground dmPagerBackground) {
        dmPagerBackgroundFeign.updateDmPagerBackgroundForAll(dmPagerBackground);
    }

    public void deleteDmPagerBackground(String id) {
        dmPagerBackgroundFeign.deleteDmPagerBackground(id);
    }

    public void deleteDmPagerBackgroundByCondition(DmPagerBackground dmPagerBackground) {
        dmPagerBackgroundFeign.deleteDmPagerBackgroundByCondition(dmPagerBackground);
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    public ResponseJson upload(MultipartFile file, String latticeId) {
        return dmPagerBackgroundFeign.upload(file,latticeId);
    }

    public ResponseJson textUpdateDmPagerBackground(DmPagerBackground dmPagerBackground) {
        return dmPagerBackgroundFeign.textUpdateDmPagerBackground(dmPagerBackground);
    }

    public ResponseJson reUpload(MultipartFile file, String id) {
        return dmPagerBackgroundFeign.reUpload(file,id);
    }
}
