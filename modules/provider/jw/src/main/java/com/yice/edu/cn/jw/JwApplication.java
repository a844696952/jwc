package com.yice.edu.cn.jw;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.yice.edu.cn.jw.service.auth.SchoolPermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.yice.edu.cn.common", "com.yice.edu.cn.jw"})
@Configuration
@EnableMethodCache(basePackages = "com.yice.edu.cn.jw")
@EnableAsync //开启异步调用
@EnableCreateCacheAnnotation
@EnableTransactionManagement
@EnableFeignClients
public class JwApplication {
    @Value("${syncUpdateSchoolPerm:false}")
    private boolean needSyncUpdateSchoolPerm;
    @Autowired
    private RedisTemplate redisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(JwApplication.class, args);
    }


    @Bean
    public CommandLineRunner init(SchoolPermService schoolPermService) {
        return strings -> {
            if (needSyncUpdateSchoolPerm) {
                System.out.println("开始更新学校权限表");
//                schoolPermService.syncUpdate();
                schoolPermService.syncSchoolPermByPro();
                System.out.println("更新学校权限表结束");
                /*System.out.println("清空所有redis缓存");
                RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
                connection.flushDb();
                System.out.println("清空所有redis缓存结束");*/
            }

        };
    }

}
