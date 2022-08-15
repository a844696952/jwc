package com.yice.edu.cn.ewb.service.classRegister;

import com.yice.edu.cn.common.pojo.wb.classRegister.HelpCenter;
import com.yice.edu.cn.ewb.feignClient.classRegister.HelpCenterFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HelpCenterService {
    @Autowired
    private HelpCenterFeign helpCenterFeign;

    public HelpCenter findHelpCenterById(String id) {
        return helpCenterFeign.findHelpCenterById(id);
    }

    public HelpCenter saveHelpCenter(HelpCenter helpCenter) {
        return helpCenterFeign.saveHelpCenter(helpCenter);
    }

    public List<HelpCenter> findHelpCenterListByCondition(HelpCenter helpCenter) {
        return helpCenterFeign.findHelpCenterListByCondition(helpCenter);
    }

    public HelpCenter findOneHelpCenterByCondition(HelpCenter helpCenter) {
        return helpCenterFeign.findOneHelpCenterByCondition(helpCenter);
    }

    public long findHelpCenterCountByCondition(HelpCenter helpCenter) {
        return helpCenterFeign.findHelpCenterCountByCondition(helpCenter);
    }

    public void updateHelpCenter(HelpCenter helpCenter) {
        helpCenterFeign.updateHelpCenter(helpCenter);
    }

    public void deleteHelpCenter(String id) {
        helpCenterFeign.deleteHelpCenter(id);
    }

    public void deleteHelpCenterByCondition(HelpCenter helpCenter) {
        helpCenterFeign.deleteHelpCenterByCondition(helpCenter);
    }
}
