package com.yice.edu.cn.cc.controller.cloudCourse;

import com.yice.edu.cn.cc.service.cloudCourse.CloudCourseService;
import com.yice.edu.cn.cc.service.cloudCourse.CloudCourseShareFileService;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourseShareFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.yice.edu.cn.cc.interceptor.LoginInterceptor.myId;

@RestController
@RequestMapping("/cloudCourseShareFile")
@Api(value = "/cloudCourseShareFile",description = "云课堂共享文件模块")
public class CloudCourseShareFileController {
    @Autowired
    private CloudCourseService cloudCourseService;
    @Autowired
    private CloudCourseShareFileService cloudCourseShareFileService;

    @PostMapping("/findCloudCourseShareFileListByCondition")
    @ApiOperation(value = "根据条件查找云课堂共享文件列表", notes = "返回响应对象,不包含总条数", response=CloudCourseShareFile.class)
    public ResponseJson findCloudCourseShareFileListByCondition(
            @ApiParam(value = "{broadcastCode:直播码}")
            @RequestBody CloudCourseShareFile cloudCourseShareFile){
    	Pager pager = new Pager();
    	pager.setIncludes("path","name");
    	pager.setPaging(false);
    	cloudCourseShareFile.setPager(pager);
        List<CloudCourseShareFile> data=cloudCourseShareFileService.findCloudCourseShareFileListByCondition(cloudCourseShareFile);
        data.forEach(returnData ->{
        	returnData.setPath(Constant.RES_PRE+returnData.getPath());
        });
        return new ResponseJson(data);
    }
    
    @PostMapping("/saveCloudCourseShareFileUrl")
    @ApiOperation(value = "保存云课堂共享文件对象", notes = "返回文件地址")
    public ResponseJson saveCloudCourseShareFileUrl(
    		 @RequestBody
    		 @ApiParam(value = "{broadcastCode:直播码,cloudCourseId:房间id,path:文件路径,name:文件名称}")
    		CloudCourseShareFile cloudCourseShareFileParams){
    	String broadcastCode = cloudCourseShareFileParams.getBroadcastCode();
//        if(StringUtils.isEmpty(cloudCourseShareFileParams.getBroadcastCode()) || StringUtils.isEmpty(cloudCourseShareFileParams.getCloudCourseId())) {
//        	return new ResponseJson(false,"直播码和房间id不能为空");
//        }
    	String name = cloudCourseShareFileParams.getName();
//        CloudCourse cloudCourse = cloudCourseService.findCloudCourseById(cloudCourseShareFileParams.getCloudCourseId());
//        if(cloudCourse==null) {
//        	return new ResponseJson(false,"不存在的房间");
//        }
        CloudCourseShareFile cloudCourseShareFile  = new CloudCourseShareFile();
        cloudCourseShareFile.setName(name);
        //cloudCourseShareFile.setCloudCourseId(cloudCourseShareFileParams.getCloudCourseId());
        cloudCourseShareFile.setPath(cloudCourseShareFileParams.getPath());
        cloudCourseShareFile.setBroadcastCode(broadcastCode);
        cloudCourseShareFile.setUploadUserId(myId());
//        cloudCourseShareFile.setSchoolId(cloudCourse.getSchoolId());
        cloudCourseShareFileService.saveCloudCourseShareFile(cloudCourseShareFile);
        
        return new ResponseJson();
    }
}
