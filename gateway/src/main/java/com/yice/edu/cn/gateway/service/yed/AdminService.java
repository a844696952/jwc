package com.yice.edu.cn.gateway.service.yed;

import com.alicp.jetcache.anno.Cached;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.yedAdmin.SysPerm;
import com.yice.edu.cn.gateway.feign.AdminFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminFeign adminFeign;


    @Cached(name = Constant.Redis.YED_ADMIN_PERMS,key = "#id",expire = Constant.Redis.YED_ADMIN_TIMEOUT)
    public List<SysPerm> getSysPermsByAdminId(String id){
        return adminFeign.findSysFuncPermsByAdminId(id);
    }


}
