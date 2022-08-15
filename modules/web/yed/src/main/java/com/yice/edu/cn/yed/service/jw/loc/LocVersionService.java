package com.yice.edu.cn.yed.service.jw.loc;

import com.yice.edu.cn.common.pojo.loc.LocVersion;
import com.yice.edu.cn.yed.feignClient.jw.loc.LocVersionFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocVersionService {
    @Autowired
    private LocVersionFeign locVersionFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public LocVersion findLocVersionById(String id) {
        return locVersionFeign.findLocVersionById(id);
    }

    public LocVersion saveLocVersion(LocVersion locVersion) {
        return locVersionFeign.saveLocVersion(locVersion);
    }

    public void batchSaveLocVersion(List<LocVersion> locVersions){
        locVersionFeign.batchSaveLocVersion(locVersions);
    }

    public List<LocVersion> findLocVersionListByCondition(LocVersion locVersion) {
        return locVersionFeign.findLocVersionListByCondition(locVersion);
    }

    public LocVersion findOneLocVersionByCondition(LocVersion locVersion) {
        return locVersionFeign.findOneLocVersionByCondition(locVersion);
    }

    public long findLocVersionCountByCondition(LocVersion locVersion) {
        return locVersionFeign.findLocVersionCountByCondition(locVersion);
    }

    public void updateLocVersion(LocVersion locVersion) {
        locVersionFeign.updateLocVersion(locVersion);
    }

    public void updateLocVersionForAll(LocVersion locVersion) {
        locVersionFeign.updateLocVersionForAll(locVersion);
    }

    public void deleteLocVersion(String id) {
        locVersionFeign.deleteLocVersion(id);
    }

    public void deleteLocVersionByCondition(LocVersion locVersion) {
        locVersionFeign.deleteLocVersionByCondition(locVersion);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public long findVersionNameRepetition(LocVersion locVersion){
        return locVersionFeign.findVersionNameRepetition(locVersion);
    }
}
