package com.yice.edu.cn.dy.service.classManage;

import cn.hutool.core.util.ObjectUtil;
import com.yice.edu.cn.common.util.wx.WxUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class WxUtilService {

    private final StringRedisTemplate stringRedisTemplate;

    private final Lock lock ;

    {
        lock = new ReentrantLock();
    }

    @Autowired
    public WxUtilService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public String getAccessToken(String appId,String appSecert,String key){
        String access_token = stringRedisTemplate.opsForValue().get(key);
        if(ObjectUtil.isNull(access_token)){
            //由于Key是项目常量，不会造成缓存穿透，此时防止key刚好在过期时间的缓存穿击，当失效时候，如果有大量并发，会同步锁
            if(lock.tryLock()){
                try{
                    access_token = WxUtil.getAccessToken(appId,appSecert);
                    //缓存新的access_token
                    if(ObjectUtil.isNull(access_token)){
                        return null;
                    }
                    stringRedisTemplate.opsForValue().set(key,access_token,110, TimeUnit.MINUTES);
                }finally {
                    //手动释放锁，否则容易死锁
                    lock.unlock();
                }
            }
        }
        return access_token;
    }
}
