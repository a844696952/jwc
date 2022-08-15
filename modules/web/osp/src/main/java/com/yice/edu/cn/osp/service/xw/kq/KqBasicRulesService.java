package com.yice.edu.cn.osp.service.xw.kq;

import com.yice.edu.cn.common.pojo.xw.kq.KqBasicRules;
import com.yice.edu.cn.osp.feignClient.xw.kq.KqBasicRulesFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KqBasicRulesService {
    @Autowired
    private KqBasicRulesFeign kqBasicRulesFeign;

    public KqBasicRules findKqBasicRulesById(String id) {
        return kqBasicRulesFeign.findKqBasicRulesById(id);
    }

    public KqBasicRules saveKqBasicRules(KqBasicRules kqBasicRules) {
        return kqBasicRulesFeign.saveKqBasicRules(kqBasicRules);
    }

    public List<KqBasicRules> findKqBasicRulesListByCondition(KqBasicRules kqBasicRules) {
        return kqBasicRulesFeign.findKqBasicRulesListByCondition(kqBasicRules);
    }

    public KqBasicRules findOneKqBasicRulesByCondition(KqBasicRules kqBasicRules) {
        return kqBasicRulesFeign.findOneKqBasicRulesByCondition(kqBasicRules);
    }

    public long findKqBasicRulesCountByCondition(KqBasicRules kqBasicRules) {
        return kqBasicRulesFeign.findKqBasicRulesCountByCondition(kqBasicRules);
    }

    public void updateKqBasicRules(KqBasicRules kqBasicRules) {
        kqBasicRulesFeign.updateKqBasicRules(kqBasicRules);
    }

    public void deleteKqBasicRules(String id) {
        kqBasicRulesFeign.deleteKqBasicRules(id);
    }

    public void deleteKqBasicRulesByCondition(KqBasicRules kqBasicRules) {
        kqBasicRulesFeign.deleteKqBasicRulesByCondition(kqBasicRules);
    }
}
