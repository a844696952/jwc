package com.yice.edu.cn.yed.service.jw.faq;

import com.yice.edu.cn.common.pojo.jw.faq.Faq;
import com.yice.edu.cn.yed.feignClient.jw.faq.FaqFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FaqService {
    @Autowired
    private FaqFeign faqFeign;

    public Faq findFaqById(String id) {
        return faqFeign.findFaqById(id);
    }

    public Faq saveFaq(Faq faq) {
        return faqFeign.saveFaq(faq);
    }

    public List<Faq> findFaqListByCondition(Faq faq) {
        return faqFeign.findFaqListByCondition(faq);
    }

    public Faq findOneFaqByCondition(Faq faq) {
        return faqFeign.findOneFaqByCondition(faq);
    }

    public long findFaqCountByCondition(Faq faq) {
        return faqFeign.findFaqCountByCondition(faq);
    }

    public void updateFaq(Faq faq) {
        faqFeign.updateFaq(faq);
    }

    public void deleteFaq(String id) {
        faqFeign.deleteFaq(id);
    }

    public void deleteFaqByCondition(Faq faq) {
        faqFeign.deleteFaqByCondition(faq);
    }
}
