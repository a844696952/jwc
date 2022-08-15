package com.yice.edu.cn.ts.task;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.yice.edu.cn.common.pojo.wb.classRegister.ClassRegister;
import com.yice.edu.cn.ts.feignClient.ClassRegisterFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Component
@EnableScheduling
public class ClassRegisterTask {

	@Autowired
	private ClassRegisterFeign classRegisterFeign;

	private final static Logger logger = LoggerFactory.getLogger(ClassRegisterTask.class);

	/**
	 * 每天23点修改当天上课的状态为下课状态
	 *
	 * @throws
	 */
	@Scheduled(cron = "0 0 23 * * ?")
	@Async
	public void updateClassRegisterStatus() {
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String today = localDate.format(formatter);
		ClassRegister classRegister = new ClassRegister();
		classRegister.setCreateTime1(today + " 00:59:59");
		classRegister.setCreateTime2(today + " 23:59:59");
		classRegister.setStatus(0);
		try {
			classRegisterFeign.updateClassRegisterStatus(classRegister);
		} catch (HystrixRuntimeException hystrixRuntimeException) {
			logger.error("微服务调用失败", hystrixRuntimeException);
		} catch (Exception ex) {
			logger.error("服务器错误", ex);
		}


	}
}
