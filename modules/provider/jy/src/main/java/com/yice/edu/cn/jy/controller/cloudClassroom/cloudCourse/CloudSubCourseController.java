package com.yice.edu.cn.jy.controller.cloudClassroom.cloudCourse;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourse;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourseResource;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudSubCourse;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.SrsQrCodeVo;
import com.yice.edu.cn.common.util.qrcode.QRCodeUtil;
import com.yice.edu.cn.jy.service.cloudClassroom.cloudCourse.CloudCourseResourceService;
import com.yice.edu.cn.jy.service.cloudClassroom.cloudCourse.CloudCourseService;
import com.yice.edu.cn.jy.service.cloudClassroom.cloudCourse.CloudSubCourseService;
import com.yice.edu.cn.jy.service.cloudClassroom.cloudCourse.LiveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cloudSubCourse")
@Api(value = "/cloudSubCourse",description = "云课堂子课程表模块")
public class CloudSubCourseController {

    private Logger log = LoggerFactory.getLogger(CloudSubCourseController.class);
    @Autowired
    private CloudSubCourseService cloudSubCourseService;
    @Autowired
    private CloudCourseService cloudCourseService;
    @Autowired
    private CloudCourseResourceService cloudCourseResourceService;
    @Value("${cc.live.h5.url}")
    private String liveH5Url;
    @Autowired
    private LiveService liveService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findCloudSubCourseById/{id}")
    @ApiOperation(value = "通过id查找云课堂子课程表", notes = "返回云课堂子课程表对象")
    public CloudSubCourse findCloudSubCourseById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return cloudSubCourseService.findCloudSubCourseById(id);
    }

    @PostMapping("/saveCloudSubCourse")
    @ApiOperation(value = "保存云课堂子课程表", notes = "返回云课堂子课程表对象")
    public CloudSubCourse saveCloudSubCourse(
            @ApiParam(value = "云课堂子课程表对象", required = true)
            @RequestBody CloudSubCourse cloudSubCourse){
        cloudSubCourseService.saveCloudSubCourse(cloudSubCourse);
        return cloudSubCourse;
    }

    @PostMapping("/batchSaveCloudSubCourse")
    @ApiOperation(value = "批量保存云课堂子课程表")
    public void batchSaveCloudSubCourse(
            @ApiParam(value = "云课堂子课程表对象集合", required = true)
            @RequestBody List<CloudSubCourse> cloudSubCourses){
        cloudSubCourseService.batchSaveCloudSubCourse(cloudSubCourses);
    }

    @PostMapping("/findCloudSubCourseListByCondition")
    @ApiOperation(value = "根据条件查找云课堂子课程表列表", notes = "返回云课堂子课程表列表")
    public List<CloudSubCourse> findCloudSubCourseListByCondition(
            @ApiParam(value = "云课堂子课程表对象")
            @RequestBody CloudSubCourse cloudSubCourse){
        return cloudSubCourseService.findCloudSubCourseListByCondition(cloudSubCourse);
    }
    @PostMapping("/findCloudSubCourseCountByCondition")
    @ApiOperation(value = "根据条件查找云课堂子课程表列表个数", notes = "返回云课堂子课程表总个数")
    public long findCloudSubCourseCountByCondition(
            @ApiParam(value = "云课堂子课程表对象")
            @RequestBody CloudSubCourse cloudSubCourse){
        return cloudSubCourseService.findCloudSubCourseCountByCondition(cloudSubCourse);
    }

    @PostMapping("/updateCloudSubCourse")
    @ApiOperation(value = "修改云课堂子课程表有值的字段", notes = "云课堂子课程表对象必传")
    public void updateCloudSubCourse(
            @ApiParam(value = "云课堂子课程表对象,对象属性不为空则修改", required = true)
            @RequestBody CloudSubCourse cloudSubCourse){
        cloudSubCourseService.updateCloudSubCourse(cloudSubCourse);
    }
    @PostMapping("/updateCloudSubCourseForAll")
    @ApiOperation(value = "修改云课堂子课程表所有字段", notes = "云课堂子课程表对象必传")
    public void updateCloudSubCourseForAll(
            @ApiParam(value = "云课堂子课程表对象", required = true)
            @RequestBody CloudSubCourse cloudSubCourse){
        cloudSubCourseService.updateCloudSubCourseForAll(cloudSubCourse);
    }

    @GetMapping("/deleteCloudSubCourse/{id}")
    @ApiOperation(value = "通过id删除云课堂子课程表")
    public void deleteCloudSubCourse(
            @ApiParam(value = "云课堂子课程表对象", required = true)
            @PathVariable String id){
        cloudSubCourseService.deleteCloudSubCourse(id);
    }
    @PostMapping("/deleteCloudSubCourseByCondition")
    @ApiOperation(value = "根据条件删除云课堂子课程表")
    public void deleteCloudSubCourseByCondition(
            @ApiParam(value = "云课堂子课程表对象")
            @RequestBody CloudSubCourse cloudSubCourse){
        cloudSubCourseService.deleteCloudSubCourseByCondition(cloudSubCourse);
    }
    @PostMapping("/findOneCloudSubCourseByCondition")
    @ApiOperation(value = "根据条件查找单个云课堂子课程表,结果必须为单条数据", notes = "返回单个云课堂子课程表,没有时为空")
    public CloudSubCourse findOneCloudSubCourseByCondition(
            @ApiParam(value = "云课堂子课程表对象")
            @RequestBody CloudSubCourse cloudSubCourse){
        return cloudSubCourseService.findOneCloudSubCourseByCondition(cloudSubCourse);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @GetMapping("/findCloudSubCourseIdListByCloudCourseId/{id}")
    @ApiOperation(value = "根据课程id查询子课程id集合", notes = "返回子课程id集合")
    public List<String> findCloudSubCourseIdListByCloudCourseId(
            @ApiParam(value = "云课堂父课程表id")
            @PathVariable("id")String id){
        return cloudSubCourseService.findCloudSubCourseIdListByCloudCourseId(id);
    }

    @PostMapping("/deleteCloudSubCourseByIds")
    @ApiOperation(value = "根据id集合删除子课程")
    public void deleteCloudSubCourseByIds(
            @ApiParam(value = "云课堂子课程表id集合")
            @RequestBody List<String> cloudSubCourseIdList){
        cloudSubCourseService.deleteCloudSubCourseByIds(cloudSubCourseIdList);
    }

    @PostMapping("/genQrCode")
    @ApiOperation(value = "生成课堂二维码的页面地址", notes = "返回响应对象", response= CloudCourse.class)
    public SrsQrCodeVo genQrCode(
            @ApiParam(value = "{broadcastCode:直播码(必填)}", required = true)
            @RequestBody CloudSubCourse cloudSubCourse) throws Exception{
        SrsQrCodeVo vo = new SrsQrCodeVo();
        if(cloudSubCourse.getBroadcastCode()==null) {
            return vo;
        }
        String cloudCourseUrl = liveH5Url+"/?broadcastCode="+cloudSubCourse.getBroadcastCode();
        byte[] qrCodeByte = QRCodeUtil.encode(cloudCourseUrl, null, null, null);

        vo.setPullUrl(cloudCourseUrl);
        vo.setQrCodeByte(qrCodeByte);
        return vo;
    }

    @PostMapping("/getHlsPath")
    @ApiOperation(value = "获取HLS的直播地址", notes = "返回响应对象", response=CloudCourse.class)
    public SrsQrCodeVo getHlsPath(
            @ApiParam(value = "{broadcastCode:直播码(必填)}", required = true)
            @RequestBody CloudSubCourse cloudSubCourse) throws Exception{
        SrsQrCodeVo vo = new SrsQrCodeVo();
        if(cloudSubCourse.getBroadcastCode()==null) {
            return vo;
        }
        vo.setPullUrl(liveService.getLiveM3U8Url(Constant.CCourse.APP_PRE, cloudSubCourse.getBroadcastCode()));
        return vo;
    }

    @PostMapping("/findCloudCourseValid")
    @ApiOperation(value = "根据直播码获取有效的云课堂信息", notes = "返回响应对象", response=CloudCourse.class)
    public Integer findCloudCourseValid(
            @RequestBody CloudSubCourse cloudSubCourse) throws Exception{
        if(cloudSubCourse.getBroadcastCode()==null) {
            return 0;
        }
        return cloudSubCourseService.findCloudCourseValid(cloudSubCourse).size();
    }

    @PostMapping("/getLivePushUrl")
    @ApiOperation(value = "获取直播的推流地址", notes = "返回响应对象", response=String.class)
    public String getLivePushUrl(
            @ApiParam(value = "{broadcastCode:直播码(必填)}", required = true)
            @RequestBody CloudSubCourse cloudSubCourse) throws Exception{
        if(cloudSubCourse.getBroadcastCode()==null) {
            return null;
        }
        return liveService.getLivePushUrl(Constant.CCourse.APP_PRE, cloudSubCourse.getBroadcastCode());
    }


    @PostMapping("/findCloudSubCourseListByLoginIdAndNow")
    @ApiOperation(value = "获取当前用户能听的现在正在进行的云课堂子课程表列表", notes = "返回云课堂子课程表列表")
    public List<CloudSubCourse> findCloudSubCourseListByLoginIdAndNow(
            @ApiParam(value = "云课堂子课程表对象")
            @RequestBody CloudSubCourse cloudSubCourse){
        return cloudSubCourseService.findCloudSubCourseListByLoginIdAndNow(cloudSubCourse);
    }


    @PostMapping("/findOnGoingOrFinishCloudSubCourseCountByCondition")
    @ApiOperation(value = "根据条件查找云课堂子课程表列表个数", notes = "返回云课堂子课程表总个数")
    public long findOnGoingOrFinishCloudSubCourseCountByCondition(
            @ApiParam(value = "云课堂子课程表对象")
            @RequestBody CloudSubCourse cloudSubCourse){
        return cloudSubCourseService.findOnGoingOrFinishCloudSubCourseCountByCondition(cloudSubCourse);
    }


    @PostMapping("/findCloudCourseIdListByTeacher")
    @ApiOperation(value = "根据授课老师查询父课程集合", notes = "返回子课程id集合")
    public List<String> findCloudCourseIdListByTeacher(
            @ApiParam(value = "云课堂子课程表对象")
            @RequestBody CloudSubCourse cloudSubCourse){
        return cloudSubCourseService.findCloudCourseIdListByTeacher(cloudSubCourse.getTeacher());
    }


    @PostMapping("/endCourse")
    @ApiOperation(value = "结束课程", notes = "结束课程")
    public CloudSubCourse endCourse(
            @ApiParam(value = "云课堂子课程表对象")
            @RequestBody CloudSubCourse cloudSubCourse){
        CloudSubCourse beforeCloudSubCourse = cloudSubCourseService.findCloudSubCourseById(cloudSubCourse.getId());
        CloudCourse cloudCourse = cloudCourseService.findCloudCourseById(beforeCloudSubCourse.getCloudCourse().getId());
        beforeCloudSubCourse.getCloudCourse().getId();
        if(beforeCloudSubCourse!=null) {
            //课程状态改为已结束
            beforeCloudSubCourse.setStatus(3);
            cloudSubCourseService.updateCloudSubCourse(beforeCloudSubCourse);
            log.info("课程结束... beforeCloudSubCourse:{}",beforeCloudSubCourse);
        }
        log.info("没有该课程,子课程id:{}.父课程id:{}",cloudSubCourse.getId(),cloudCourse.getId());
        return beforeCloudSubCourse;
    }

    @PostMapping("/startCourse")
    @ApiOperation(value = "开始课程", notes = "结束课程")
    public CloudSubCourse startCourse(
            @ApiParam(value = "云课堂子课程表对象")
            @RequestBody CloudSubCourse cloudSubCourse){
        CloudSubCourse beforeCloudSubCourse = cloudSubCourseService.findCloudSubCourseById(cloudSubCourse.getId());
        if(beforeCloudSubCourse!=null) {
            //课程状态改为上课中
            beforeCloudSubCourse.setStatus(2);
            cloudSubCourseService.updateCloudSubCourse(beforeCloudSubCourse);
            log.info("课程开始... beforeCloudSubCourse:{}",beforeCloudSubCourse);
        }
        log.info("没有该课程,子课程id:{}",cloudSubCourse.getId());
        return beforeCloudSubCourse;
    }

    @PostMapping("/pushCloudSubCourse10")
    @ApiOperation(value = "开始课程", notes = "结束课程")
    public void pushCloudSubCourse10(){
         cloudSubCourseService.pushCloudSubCourse10();
    }

    @PostMapping("/pushCloudSubCourse21")
    @ApiOperation(value = "开始课程", notes = "结束课程")
    public void pushCloudSubCourse21(){
         cloudSubCourseService.pushCloudSubCourse21();
    }

}
