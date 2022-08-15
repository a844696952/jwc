package com.yice.edu.cn.bmp.service.visitorManage;

import com.yice.edu.cn.bmp.feignClient.visitorManage.KqSchoolInitFeign;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.xw.kq.KqSchoolInit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:xushu
 * @date:2019/4/12
 */
@Service
public class KqSchoolInitService {
    @Autowired
    private KqSchoolInitFeign kqSchoolInitFeign;

    public KqSchoolInit findKqSchoolInitById(String id) {
        return kqSchoolInitFeign.findKqSchoolInitById(id);
    }

    public KqSchoolInit saveKqSchoolInit(KqSchoolInit kqSchoolInit) {
        return kqSchoolInitFeign.saveKqSchoolInit(kqSchoolInit);
    }

    public List<KqSchoolInit> findKqSchoolInitListByCondition(KqSchoolInit kqSchoolInit) {
        return kqSchoolInitFeign.findKqSchoolInitListByCondition(kqSchoolInit);
    }

    public KqSchoolInit findOneKqSchoolInitByCondition(KqSchoolInit kqSchoolInit) {
        return kqSchoolInitFeign.findOneKqSchoolInitByCondition(kqSchoolInit);
    }

    public long findKqSchoolInitCountByCondition(KqSchoolInit kqSchoolInit) {
        return kqSchoolInitFeign.findKqSchoolInitCountByCondition(kqSchoolInit);
    }

    public void updateKqSchoolInit(KqSchoolInit kqSchoolInit) {
        kqSchoolInitFeign.updateKqSchoolInit(kqSchoolInit);
    }

    public void deleteKqSchoolInit(String id) {
        kqSchoolInitFeign.deleteKqSchoolInit(id);
    }

    public void deleteKqSchoolInitByCondition(KqSchoolInit kqSchoolInit) {
        kqSchoolInitFeign.deleteKqSchoolInitByCondition(kqSchoolInit);
    }
    //获得未初始化学校列表
    public List<School> getUnInitSchool(List<KqSchoolInit> kqSchoolInits, List<School> schools) {
        List unInitSchools = new ArrayList<School>() ;
        for (School school:schools) {
            Boolean add =true;
            for (KqSchoolInit kqSchool: kqSchoolInits){
                if (school.getId().equals(kqSchool.getCustId())){
                    add=false;
                }
            }
            if (add==true){
                unInitSchools.add(school);
            }
        }
        return unInitSchools;
    }
}

