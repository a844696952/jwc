package com.yice.edu.cn.bmp.service.perm;

import com.yice.edu.cn.bmp.feignClient.perm.PermFeign;
import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermService {
    @Autowired
    private PermFeign permFeign;
    public List<Perm> findPermsForH5BySchoolId(String schoolId){
        return permFeign.findPermsForH5BySchoolId(schoolId);
    }
}
