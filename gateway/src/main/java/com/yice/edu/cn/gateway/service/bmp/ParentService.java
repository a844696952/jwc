package com.yice.edu.cn.gateway.service.bmp;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.Cached;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.parent.Parent;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class ParentService {
    @CreateCache(name= Constant.Redis.BMP_TOKEN_CACHE)
    private Cache<String,String> tokenCache;
    @CreateCache(name= Constant.Redis.BMP_PARENT_CACHE)
    private Cache<String,Parent> parentCache;

    @Cached(name = Constant.Redis.BMP_TOKEN_CACHE,key = "#key",expire = Constant.Redis.BMP_PARENT_TIMEOUT,timeUnit = TimeUnit.DAYS)
    @CacheRefresh(timeUnit = TimeUnit.DAYS,refresh = 1,stopRefreshAfterLastAccess = Constant.Redis.BMP_PARENT_TIMEOUT,refreshLockTimeout = 60)
    public String findTokenByKey(String key) {
        return tokenCache.get(key);
    }
    @Cached(name = Constant.Redis.BMP_PARENT_CACHE,key = "#id",expire = Constant.Redis.BMP_PARENT_TIMEOUT,timeUnit = TimeUnit.DAYS)
    @CacheRefresh(timeUnit = TimeUnit.DAYS,refresh = 1,stopRefreshAfterLastAccess = Constant.Redis.BMP_PARENT_TIMEOUT,refreshLockTimeout = 60)
    public Parent findParentById(String id) {
        return parentCache.get(id);
    }
}
