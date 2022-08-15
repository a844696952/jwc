package com.yice.edu.cn.osp.service.xw.dj.djLedger;

import com.yice.edu.cn.common.pojo.xw.dj.djLedgerTemplate.DjLedgerTemplat;
import com.yice.edu.cn.osp.feignClient.xw.dj.djLedger.DjLedgerTemplatFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class DjLedgerTemplatService {
    @Autowired
    private DjLedgerTemplatFeign djLedgerTemplatFeign;

    public DjLedgerTemplat findDjLedgerTemplatById(String id) {
        return djLedgerTemplatFeign.findDjLedgerTemplatById(id);
    }

    public DjLedgerTemplat saveDjLedgerTemplat(DjLedgerTemplat djLedgerTemplat) {
        return djLedgerTemplatFeign.saveDjLedgerTemplat(djLedgerTemplat);
    }

    public List<DjLedgerTemplat> findDjLedgerTemplatListByCondition(DjLedgerTemplat djLedgerTemplat) {
        return djLedgerTemplatFeign.findDjLedgerTemplatListByCondition(djLedgerTemplat);
    }

    public DjLedgerTemplat findOneDjLedgerTemplatByCondition(DjLedgerTemplat djLedgerTemplat) {
        return djLedgerTemplatFeign.findOneDjLedgerTemplatByCondition(djLedgerTemplat);
    }

    public long findDjLedgerTemplatCountByCondition(DjLedgerTemplat djLedgerTemplat) {
        return djLedgerTemplatFeign.findDjLedgerTemplatCountByCondition(djLedgerTemplat);
    }

    public void updateDjLedgerTemplat(DjLedgerTemplat djLedgerTemplat) {
        djLedgerTemplatFeign.updateDjLedgerTemplat(djLedgerTemplat);
    }

    public void deleteDjLedgerTemplat(String id) {
        djLedgerTemplatFeign.deleteDjLedgerTemplat(id);
    }

    public void deleteDjLedgerTemplatByCondition(DjLedgerTemplat djLedgerTemplat) {
        djLedgerTemplatFeign.deleteDjLedgerTemplatByCondition(djLedgerTemplat);
    }

    public List<DjLedgerTemplat> findTemplateTree() {
        String schoolId = mySchoolId();
        List<DjLedgerTemplat> templateTree = djLedgerTemplatFeign.findTemplateTree(schoolId);
        return templateTree;
    }
}
