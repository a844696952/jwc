package com.yice.edu.cn.ecc.controller.classes;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.classes.DmClassVideo;
import com.yice.edu.cn.ecc.service.classes.DmClassVideoService;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/dmClassVideo")
@Api(value = "/dmClassVideo",description = "班级短视频表模块")
public class DmClassVideoController {
    @Autowired
    private DmClassVideoService dmClassVideoService;

    @PostMapping("/saveDmClassVideo")
    @ApiOperation(value = "保存班级短视频表对象", notes = "返回保存好的班级短视频表对象", response= DmClassVideo.class)
    public ResponseJson saveDmClassVideo(
            @ApiParam(value = "班级短视频表对象", required = true)
            @RequestBody DmClassVideo dmClassVideo){
        if(StringUtil.isNullOrEmpty(dmClassVideo.getSchoolId())){
            return new ResponseJson(false, "schoolId不能为空");
        }

        if(StringUtil.isNullOrEmpty(dmClassVideo.getClassId())){
            return new ResponseJson(false, "classId不能为空");
        }

        if(StringUtil.isNullOrEmpty(dmClassVideo.getVideoName())){
            return new ResponseJson(false, "视频名称不能为空");
        }

        if(StringUtil.isNullOrEmpty(dmClassVideo.getVideoUrl())){
            return new ResponseJson(false, "视频地址不能为空");
        }

        dmClassVideo.setVideoEntry(new Integer(3));

        DmClassVideo s=dmClassVideoService.saveDmClassVideo(dmClassVideo);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findDmClassVideoById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找班级短视频表", notes = "返回响应对象", response=DmClassVideo.class)
    public ResponseJson findDmClassVideoById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DmClassVideo dmClassVideo=dmClassVideoService.findDmClassVideoById(id);
        return new ResponseJson(dmClassVideo);
    }

    @PostMapping("/update/updateDmClassVideo")
    @ApiOperation(value = "修改班级短视频表对象", notes = "返回响应对象")
    public ResponseJson updateDmClassVideo(
            @ApiParam(value = "被修改的班级短视频表对象,对象属性不为空则修改", required = true)
            @RequestBody DmClassVideo dmClassVideo){
        dmClassVideoService.updateDmClassVideo(dmClassVideo);
        return new ResponseJson();
    }

    @PostMapping("/findDmClassVideoListByCondition")
    @ApiOperation(value = "根据条件查找班级短视频表列表", notes = "返回响应对象,不包含总条数", response=DmClassVideo.class)
    public ResponseJson findDmClassVideoListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmClassVideo dmClassVideo){
        if(StringUtil.isNullOrEmpty(dmClassVideo.getSchoolId())){
            return new ResponseJson(false, "schoolId不能为空");
        }

        if(StringUtil.isNullOrEmpty(dmClassVideo.getClassId())){
            return new ResponseJson(false, "classId不能为空");
        }

        dmClassVideo.setVideoEntry(new Integer(3));
        if(dmClassVideo.getPager() == null){
            Pager pager = new Pager();
            pager.setPaging(true);
            pager.setPageSize(1000);
            pager.setPage(1);
            pager.setSortField("videoSort");
            pager.setSortOrder("asc");
            dmClassVideo.setPager(pager);
        }
        List<DmClassVideo> data = dmClassVideoService.findDmClassVideoListByCondition(dmClassVideo);
        return new ResponseJson(data);
    }

}
