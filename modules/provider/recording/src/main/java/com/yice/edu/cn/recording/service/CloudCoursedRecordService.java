package com.yice.edu.cn.recording.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yice.edu.cn.common.pojo.cc.recording.UserRecodingVo;
import com.yice.edu.cn.recording.executor.RecodingExecutorService;
import com.yice.edu.cn.recording.handler.CcRecordingHandler;

import io.agora.recording.RecordingSDK;

@Service
public class CloudCoursedRecordService {
	
	private static Map<String,CcRecordingHandler> handleMap = new HashMap<String,CcRecordingHandler>();
	
	@Autowired
	private CloudCoursedResourceService cloudCoursedResourceService;
	
    public void beginRecord(String cloudCourseId) throws Exception{
        RecordingSDK recordingSdk = new RecordingSDK();
        CcRecordingHandler newCcRecordingHandler = new CcRecordingHandler(recordingSdk,cloudCoursedResourceService,cloudCourseId);
        RecodingExecutorService.executorService.execute(()->{
	        handleMap.put(cloudCourseId, newCcRecordingHandler);
	        newCcRecordingHandler.createChannel();
	        });
    }
	
    public void endRecord(String cloudCourseId) {
    	CcRecordingHandler ars = handleMap.get(cloudCourseId);
    	if(ars==null) {
    		return;
    	}
    	boolean sign = ars.leaveChannel(ars.mNativeHandle);
    	handleMap.remove(cloudCourseId);
    }
    
    public int addUserVideo(UserRecodingVo vo) {
    	CcRecordingHandler ars = handleMap.get(vo.getCourseId());
    	if(ars==null) {
    		return 0;
    	}
    	return ars.addUserVideoToCanvas(vo.getUsrInfoList());
    }
    
    public int removeUserVideo(UserRecodingVo vo) {
    	CcRecordingHandler ars = handleMap.get(vo.getCourseId());
    	if(ars==null) {
    		return 0;
    	}
    	return ars.removeUserVideoToCanvas(vo.getUsrInfoList());
    }
}
