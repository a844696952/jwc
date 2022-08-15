package com.yice.edu.cn.mes.service;

import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.mes.feign.DdFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DdService {
    @Autowired
    private DdFeign ddFeign;


    public List<Dd> findJwClassesList(Dd dd) {
        return ddFeign.findJwClassesList(dd);
    }
}
