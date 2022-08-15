package com.yice.edu.cn.osp.service.xw.dormManage.houseApplican;

import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.HouseApplicanFiles;
import com.yice.edu.cn.osp.feignClient.xw.dormManage.houseApplican.HouseApplicanFilesFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseApplicanFilesService {
    @Autowired
    private HouseApplicanFilesFeign houseApplicanFilesFeign;

    public HouseApplicanFiles findHouseApplicanFilesById(String id) {
        return houseApplicanFilesFeign.findHouseApplicanFilesById(id);
    }

    public HouseApplicanFiles saveHouseApplicanFiles(HouseApplicanFiles houseApplicanFiles) {
        return houseApplicanFilesFeign.saveHouseApplicanFiles(houseApplicanFiles);
    }

    public List<HouseApplicanFiles> findHouseApplicanFilesListByCondition(HouseApplicanFiles houseApplicanFiles) {
        return houseApplicanFilesFeign.findHouseApplicanFilesListByCondition(houseApplicanFiles);
    }

    public HouseApplicanFiles findOneHouseApplicanFilesByCondition(HouseApplicanFiles houseApplicanFiles) {
        return houseApplicanFilesFeign.findOneHouseApplicanFilesByCondition(houseApplicanFiles);
    }

    public long findHouseApplicanFilesCountByCondition(HouseApplicanFiles houseApplicanFiles) {
        return houseApplicanFilesFeign.findHouseApplicanFilesCountByCondition(houseApplicanFiles);
    }

    public void updateHouseApplicanFiles(HouseApplicanFiles houseApplicanFiles) {
        houseApplicanFilesFeign.updateHouseApplicanFiles(houseApplicanFiles);
    }

    public void deleteHouseApplicanFiles(String id) {
        houseApplicanFilesFeign.deleteHouseApplicanFiles(id);
    }

    public void deleteHouseApplicanFilesByCondition(HouseApplicanFiles houseApplicanFiles) {
        houseApplicanFilesFeign.deleteHouseApplicanFilesByCondition(houseApplicanFiles);
    }
}
