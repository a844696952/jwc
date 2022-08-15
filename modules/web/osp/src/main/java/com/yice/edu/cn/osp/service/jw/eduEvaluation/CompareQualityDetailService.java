package com.yice.edu.cn.osp.service.jw.eduEvaluation;

import com.yice.edu.cn.common.pojo.jw.eduEvaluation.CompareQualityDetail;
import com.yice.edu.cn.osp.feignClient.jw.eduEvaluation.CompareQualityDetailFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompareQualityDetailService {
    @Autowired
    private CompareQualityDetailFeign compareQualityDetailFeign;

    public CompareQualityDetail findCompareQualityDetailById(String id) {
        return compareQualityDetailFeign.findCompareQualityDetailById(id);
    }

    public CompareQualityDetail saveCompareQualityDetail(CompareQualityDetail compareQualityDetail) {
        return compareQualityDetailFeign.saveCompareQualityDetail(compareQualityDetail);
    }

    public List<CompareQualityDetail> findCompareQualityDetailListByCondition(CompareQualityDetail compareQualityDetail) {
        return compareQualityDetailFeign.findCompareQualityDetailListByCondition(compareQualityDetail);
    }

    public CompareQualityDetail findOneCompareQualityDetailByCondition(CompareQualityDetail compareQualityDetail) {
        return compareQualityDetailFeign.findOneCompareQualityDetailByCondition(compareQualityDetail);
    }

    public long findCompareQualityDetailCountByCondition(CompareQualityDetail compareQualityDetail) {
        return compareQualityDetailFeign.findCompareQualityDetailCountByCondition(compareQualityDetail);
    }

    public void updateCompareQualityDetail(CompareQualityDetail compareQualityDetail) {
        compareQualityDetailFeign.updateCompareQualityDetail(compareQualityDetail);
    }

    public void deleteCompareQualityDetail(String id) {
        compareQualityDetailFeign.deleteCompareQualityDetail(id);
    }

    public void deleteCompareQualityDetailByCondition(CompareQualityDetail compareQualityDetail) {
        compareQualityDetailFeign.deleteCompareQualityDetailByCondition(compareQualityDetail);
    }

    public void deleteCompareQualityDetailByIdList(List<String> idList) {
        compareQualityDetailFeign.deleteCompareQualityDetailByIdList(idList);
    }


    public List<String> getClassTypeList(CompareQualityDetail compareQualityDetail) {
        return  compareQualityDetailFeign.getClassTypeList(compareQualityDetail);
    }


}
