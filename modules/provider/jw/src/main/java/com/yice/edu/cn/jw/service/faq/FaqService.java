package com.yice.edu.cn.jw.service.faq;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.faq.Faq;
import com.yice.edu.cn.jw.dao.faq.IFaqDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class FaqService {
    @Autowired
    private IFaqDao faqDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public Faq findFaqById(String id) {
        return faqDao.findFaqById(id);
    }
    @Transactional
    public void saveFaq(Faq faq) {
        faq.setId(sequenceId.nextId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        faq.setUpdateTime(sdf.format(date));
        if(StringUtils.isEmpty(faq.getSort())){
            faq.setSort("0");
        }
        faqDao.saveFaq(faq);
    }
    @Transactional(readOnly = true)
    public List<Faq> findFaqListByCondition(Faq faq) {
        if(!StringUtils.isEmpty(faq.getName())){
            faq.setName(faq.getName().trim());
        }
        return faqDao.findFaqListByCondition4Like(faq);
        //return faqDao.findFaqListByCondition(faq);
    }
    @Transactional(readOnly = true)
    public Faq findOneFaqByCondition(Faq faq) {
        return faqDao.findOneFaqByCondition(faq);
    }
    @Transactional(readOnly = true)
    public long findFaqCountByCondition(Faq faq) {
        if(!StringUtils.isEmpty(faq.getName())){
            faq.setName(faq.getName().trim());
        }
        //return faqDao.findFaqCountByCondition(faq);
        return faqDao.findFaqCountByCondition4Like(faq);
    }
    @Transactional
    public void updateFaq(Faq faq) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        faq.setUpdateTime(sdf.format(date));
        if(StringUtils.isEmpty(faq.getSort())){
            faq.setSort("0");
        }
        faqDao.updateFaq(faq);
    }
    @Transactional
    public void deleteFaq(String id) {
        faqDao.deleteFaq(id);
    }
    @Transactional
    public void deleteFaqByCondition(Faq faq) {
        faqDao.deleteFaqByCondition(faq);
    }
    @Transactional
    public void batchSaveFaq(List<Faq> faqs){
        faqs.forEach(faq -> faq.setId(sequenceId.nextId()));
        faqDao.batchSaveFaq(faqs);
    }

}
