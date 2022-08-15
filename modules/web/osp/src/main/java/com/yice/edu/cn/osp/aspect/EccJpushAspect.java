package com.yice.edu.cn.osp.aspect;

import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.annotation.EccJpush;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;


/**
 * 发起推送
 *
 */
@Aspect
@Component
public class EccJpushAspect {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Pointcut("@annotation(com.yice.edu.cn.common.annotation.EccJpush)")
	public void eccJpushPointCut() {
	}
	@Around("eccJpushPointCut()")
	public Object around(ProceedingJoinPoint point) {
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		EccJpush eccJpush = method.getAnnotation(EccJpush.class);
		JpushApp app = JpushApp.getValueById(1);
		// 执行方法
		Object result = null;
		try {
			Push push;
			push = Push.newBuilder(app)
					.setTag(mySchoolId())
					.setMessage(Push.Message.newBuilder().setMsgContent(eccJpush.content()==null?"更新模块":eccJpush.content()).setTitle("更新模块")
							.setExtras(Extras.newBuilder().setType(eccJpush.type()).build())
							.build()
					).build();
			stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
			result = point.proceed();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} catch (Throwable throwable) {
			throwable.printStackTrace();
			return null;
		}
	}

}
