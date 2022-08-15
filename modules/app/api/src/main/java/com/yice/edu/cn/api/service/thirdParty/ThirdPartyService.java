package com.yice.edu.cn.api.service.thirdParty;

import com.yice.edu.cn.api.feign.thirdParty.ThirdPartyFeign;
import com.yice.edu.cn.common.pojo.api.thirdParty.ThirdParty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThirdPartyService {
    @Autowired
    private ThirdPartyFeign thirdPartyFeign;

    public ThirdParty findThirdPartyById(String id) {
        return thirdPartyFeign.findThirdPartyById(id);
    }

    public ThirdParty saveThirdParty(ThirdParty thirdParty) {
        return thirdPartyFeign.saveThirdParty(thirdParty);
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

    public void deleteThirdParty(String id) {
        thirdPartyFeign.deleteThirdParty(id);
    }

    public void deleteThirdPartyByCondition(ThirdParty thirdParty) {
        thirdPartyFeign.deleteThirdPartyByCondition(thirdParty);
    }
}
