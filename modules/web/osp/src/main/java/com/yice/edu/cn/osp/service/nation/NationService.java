package com.yice.edu.cn.osp.service.nation;

import com.yice.edu.cn.common.pojo.general.nation.Nation;
import com.yice.edu.cn.osp.feignClient.nation.NationFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NationService {
    @Autowired
    private NationFeign nationFeign;

    public Nation findNationById(String id) {
        return nationFeign.findNationById(id);
    }

    public Nation saveNation(Nation nation) {
        return nationFeign.saveNation(nation);
    }

    public List<Nation> findNationListByCondition(Nation nation) {
        return nationFeign.findNationListByCondition(nation);
    }

    public long findNationCountByCondition(Nation nation) {
        return nationFeign.findNationCountByCondition(nation);
    }

    public void updateNation(Nation nation) {
        nationFeign.updateNation(nation);
    }

    public void deleteNation(String id) {
        nationFeign.deleteNation(id);
    }
}
