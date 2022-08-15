package com.yice.edu.cn.recording.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.cc.recording.UserRecodingVo;
import com.yice.edu.cn.recording.service.CloudCoursedRecordService;

@RestController
@RequestMapping("/cloudCourseRecord")
public class CloudCoursedRecordController {
	
	@Autowired
	private CloudCoursedRecordService cloudCoursedRecordService;

	/**
	 * 开始录播
	 * @param id 云课堂id
	 * @return
	 */
    @GetMapping("/beginRecord/{id}")
    public ResponseJson beginRecord(
            @PathVariable String id){
        try {
			cloudCoursedRecordService.beginRecord(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return new ResponseJson();
    }
    
    /**
     * 结束录播
     * @param id 云课堂id
     * @return
     */
    @GetMapping("/endRecord/{id}")
    public ResponseJson endRecord(
            @PathVariable String id){
        cloudCoursedRecordService.endRecord(id);
        
        return new ResponseJson();
    }
    
    /**
     * 添加录屏流
     * @param vo
     * @return
     */
    @PostMapping("/addUserVideo")
    public ResponseJson addUserVideo(@RequestBody UserRecodingVo vo) {
    	cloudCoursedRecordService.addUserVideo(vo);
    	
    	return new ResponseJson();
    }
    
    /**
     * 移除录屏流
     * @param vo
     * @return
     */
    @PostMapping("/removeUserVideo")
    public ResponseJson removeUserVideo(@RequestBody UserRecodingVo vo) {
    	cloudCoursedRecordService.removeUserVideo(vo);
    	
    	return new ResponseJson();
    }
}
