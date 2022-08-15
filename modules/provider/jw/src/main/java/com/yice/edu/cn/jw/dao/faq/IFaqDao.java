package com.yice.edu.cn.jw.dao.faq;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.faq.Faq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IFaqDao {

    List<Faq> findFaqListByCondition4Like(Faq faq);

    List<Faq> findFaqListByCondition(Faq faq);

    Faq findOneFaqByCondition(Faq faq);

    long findFaqCountByCondition(Faq faq);

    Faq findFaqById(@Param("id") String id);

    void saveFaq(Faq faq);

    void updateFaq(Faq faq);

    void deleteFaq(@Param("id") String id);

    void deleteFaqByCondition(Faq faq);

    void batchSaveFaq(List<Faq> faqs);

    long findFaqCountByCondition4Like(Faq faq);
}
