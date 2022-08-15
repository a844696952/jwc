package com.yice.edu.cn.ts.aspect;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 为定时任务添加分布式锁,拿到锁就执行,否则不执行
 */
@Aspect
@Component
public class DistributeLockAspect {
    private static final Logger logger = LoggerFactory.getLogger(DistributeLockAspect.class);
    @CreateCache(name = "distributeLock")
    private Cache<String,Long> lock;


    @Pointcut("execution(* com.yice.edu.cn.ts.task..*.*()) && @annotation(org.springframework.scheduling.annotation.Scheduled)")
    public void distributeLockToTask() { }

    @Pointcut("execution(* com.yice.edu.cn.ts.aliMsn.AliMsn.*(com.yice.edu.cn.common.pojo.ts.aliMsn.Msn))||execution(* com.yice.edu.cn.ts.aliMsn.AliMsn.*(com.yice.edu.cn.common.pojo.ts.aliMsn.ZcMsn))||execution(* com.yice.edu.cn.ts.aliMsn.AliMsn.*(com.yice.edu.cn.common.pojo.ts.aliMsn.Sms))|| execution(public * com.yice.edu.cn.ts.jpush.core.Jpush.sendPush(com.yice.edu.cn.common.pojo.ts.jpush.Push))")
    public void distributeLockToSendMsg() { }
    @Around("distributeLockToTask()")
    public void taskRound(ProceedingJoinPoint pjp){
        final Signature signature = pjp.getSignature();
        final String key = signature.getDeclaringTypeName() + "." + signature.getName();
        lock.tryLockAndRun(key, 10, TimeUnit.SECONDS, () -> {
            try {
                pjp.proceed();
            } catch (Throwable throwable) {
                logger.error(key+" 加分布式锁的目标异常",throwable);
            }
        });
    }
    @Around("distributeLockToSendMsg()")
    public void msgRound(ProceedingJoinPoint pjp){
        Object arg= pjp.getArgs()[0];
        int key = arg.hashCode();
        lock.tryLockAndRun(key+"", 10, TimeUnit.SECONDS, () -> {
            try {
                pjp.proceed();
            } catch (Throwable throwable) {
                logger.error(key+" 加分布式锁的目标异常",throwable);
            }
        });
    }


}
