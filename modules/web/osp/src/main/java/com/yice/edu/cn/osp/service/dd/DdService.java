package com.yice.edu.cn.osp.service.dd;

import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.osp.feignClient.dd.DdFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DdService {
    @Autowired
    private DdFeign ddFeign;

    public Dd findDdById(String id) {
        return ddFeign.findDdById(id);
    }

    public Dd saveDd(Dd dd) {
        return ddFeign.saveDd(dd);
    }

    public List<Dd> findDdListByCondition(Dd dd) {
        return ddFeign.findDdListByCondition(dd);
    }

    public long findDdCountByCondition(Dd dd) {
        return ddFeign.findDdCountByCondition(dd);
    }

    public void updateDd(Dd dd) {
        ddFeign.updateDd(dd);
    }

    public void deleteDd(String id) {
        ddFeign.deleteDd(id);
    }

    public List<Dd> findJwClassesList(Dd dd) {
        return ddFeign.findJwClassesList(dd);
    }
}
