package com.yice.edu.cn.yed.service.jw.thirdParty;

import com.yice.edu.cn.common.pojo.api.thirdParty.ThirdParty;
import com.yice.edu.cn.yed.feignClient.jw.thirdParty.ThirdPartyFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThirdPartyService {
    @Autowired
    private ThirdPartyFeign thirdPartyFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public ThirdParty findThirdPartyById(String id) {
        return thirdPartyFeign.findThirdPartyById(id);
    }

    public ThirdParty saveThirdParty(ThirdParty thirdParty) {
        return thirdPartyFeign.saveThirdParty(thirdParty);
    }

    public void batchSaveThirdParty(List<ThirdParty> thirdPartys){
        thirdPartyFeign.batchSaveThirdParty(thirdPartys);
    }

    public List<ThirdParty> findThirdPartyListByCondition(ThirdParty thirdParty) {
        return thirdPartyFeign.findThirdPartyListByCondition(thirdParty);
    }

    public ThirdParty findOneThirdPartyByCondition(ThirdParty thirdParty) {
        return thirdPartyFeign.findOneThirdPartyByCondition(thirdParty);
    }

    public long findThirdPartyCountByCondition(ThirdParty thirdParty) {
        return thirdPartyFeign.findThirdPartyCountByCondition(thirdParty);
    }

    public void updateThirdParty(ThirdParty thirdParty) {
        thirdPartyFeign.updateThirdParty(thirdParty);
    }

    public void updateThirdPartyForAll(ThirdParty thirdParty) {
        thirdPartyFeign.updateThirdPartyForAll(thirdParty);
    }

    public void deleteThirdParty(String id) {
        thirdPartyFeign.deleteThirdParty(id);
    }

    public void deleteThirdPartyByCondition(ThirdParty thirdParty) {
        thirdPartyFeign.deleteThirdPartyByCondition(thirdParty);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
