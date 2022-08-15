package com.yice.edu.cn.common.aspect;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.yice.edu.cn.common.annotation.SysLog;
import com.yice.edu.cn.common.feign.DmLogFeign;
import com.yice.edu.cn.common.pojo.dm.classCard.DmLog;
import com.yice.edu.cn.common.pojo.dm.classCard.DmTimedTask;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.yice.edu.cn.common.util.ReflexObjectUtil.getValueByKey;


/**
 * 操作日志
 *
 * @author zzx
 */
@Aspect
@Slf4j
public class SysLogAspect {

    @Autowired
	private DmLogFeign dmLogFeign;



	@Around("@annotation(sysLog)")
	@SneakyThrows
	public Object around(ProceedingJoinPoint point, SysLog sysLog) {
		Object obj = point.proceed();//返回参数
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //Object object = Arrays.stream(point.getArgs()).filter(t ->!( t instanceof ServletRequest) && !( t instanceof ServletResponse) ).collect(Collectors.toList());
		//请求参数转成jsonString
		//String requestBody = JSONObject.toJSONString(object);//请求参数
		DmLog dmLog = new DmLog();
		dmLog.setMsg(sysLog.value());//操作信息
		dmLog.setUrl(request.getServletPath().toString());//接口地址
		dmLog.setCreateTime(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));//操作时间
		Object result = getValueByKey(obj, "result");
		//Object data = getValueByKey(obj, "data");
		Boolean success = (Boolean) getValueByKey(result,"success");
		if(success){
			//	success =true 代表接口调用成功 写入日志
			String [] cardIds = (String[]) getValueByKey(getValueByKey(obj, "data"), "cardIds");
			dmLog.setSchoolId((String) getValueByKey(getValueByKey(obj, "data"), "schoolId"));
			dmLog.setTeacherId((String) getValueByKey(getValueByKey(obj, "data"), "teacherId"));
			dmLog.setCardId((String) getValueByKey(getValueByKey(obj, "data"), "id"));
			if(cardIds!=null  && cardIds.length>0 ){
				List<DmLog> li = new ArrayList<>();
				//批量记录日志
				for (String id : cardIds) {
					DmLog dmLog2 = new DmLog();
					BeanUtils.copyProperties(dmLog,dmLog2);
					dmLog2.setCardId(id);
					li.add(dmLog2);
				}
				dmLogFeign.batchSaveDmLog(li);
			}else {
				//记录日志
				dmLogFeign.saveDmLog(dmLog);
			}
		}
		return obj;
	}

}
