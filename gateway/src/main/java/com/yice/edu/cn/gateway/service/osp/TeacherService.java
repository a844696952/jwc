package com.yice.edu.cn.gateway.service.osp;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.Cached;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.gateway.feign.TeacherFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    @CreateCache(name =Constant.Redis.OSP_TEACHER_CACHE,expire = Constant.Redis.OSP_TEACHER_TIMEOUT)
    private Cache<String,Teacher> teacherCache;
    @Autowired
    private TeacherFeign teacherFeign;
    @Cached(name = Constant.Redis.OSP_TEACHER_PERMS,key = "#id",expire = Constant.Redis.OSP_TEACHER_TIMEOUT)
    public List<Perm> getPermsByTeacherId(String id){
        return teacherFeign.findTeacherFuncPermsByTeacherId(id);
    }



    @Cached(name = Constant.Redis.OSP_TEACHER_CACHE,key = "#id",expire = Constant.Redis.OSP_TEACHER_TIMEOUT)
    public Teacher findTeacherById(String id){
        return teacherCache.get(id);
    }

}
