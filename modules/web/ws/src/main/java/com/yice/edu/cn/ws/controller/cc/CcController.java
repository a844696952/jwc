package com.yice.edu.cn.ws.controller.cc;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.*;
import com.yice.edu.cn.ws.principal.UserPrincipal;
import com.yice.edu.cn.ws.service.cc.CloudCourseCacheService;
import com.yice.edu.cn.ws.service.cc.CloudCourseRecordService;
import com.yice.edu.cn.ws.service.cc.CloudSubCourseLessonsFileService;
import com.yice.edu.cn.ws.service.cc.CloudSubCourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;
@Controller
@MessageMapping("/cc")
public class CcController {

    private Logger log = LoggerFactory.getLogger(CcController.class);

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private CloudSubCourseService cloudSubCourseService;

    @Autowired
    private CloudSubCourseLessonsFileService cloudSubCourseLessonsFileService;

    @Autowired
    private CloudCourseCacheService cloudCourseCacheService;

    @Autowired
    private CloudCourseRecordService cloudCourseRecordService;

    @MessageMapping("/getRoomList")
    public void getRoomList(UserPrincipal user) {
        log.info("getRoomList...user:{}", user.toString());
        CloudSubCourse cloudSubCourse = new CloudSubCourse();
        cloudSubCourse.setLoginId(user.getId());
        List<CloudSubCourse> data = cloudSubCourseService.findCloudSubCourseListByLoginIdAndNow(cloudSubCourse);
        List<CloudSubCourseRoom> list = assembleCloudSubCourseRoomList(data);
        String addr = cloudCourseCacheService.getSubScribe(Constant.CCourse.SUBSCRIBE.SINGLE_MSG, user.getId());
        simpMessagingTemplate.convertAndSendToUser(addr, Constant.CCourse.SUBSCRIBE.SINGLE_MSG, CCWebSocketResponse.createSuccessData(3, list));
        log.info("getRoomList send response...user:{}", user.toString());
    }


    @MessageMapping("/getUserList")
    public void getUserList(UserPrincipal user) {
        String roomAddr = cloudCourseCacheService.getSubScribe(Constant.CCourse.SUBSCRIBE.ROOM_MSG, user.getId());
        List<CourseUserVo> userList = cloudCourseCacheService.getUserList(roomAddr);
        String addr = cloudCourseCacheService.getSubScribe(Constant.CCourse.SUBSCRIBE.SINGLE_MSG, user.getId());
        simpMessagingTemplate.convertAndSendToUser(addr, Constant.CCourse.SUBSCRIBE.SINGLE_MSG, CCWebSocketResponse.createSuccessData(4, userList));
        log.info("用户列表信息:{}", userList);
    }

    @MessageMapping("/redirect")
    public void redirect(RedirectParam param) {
        String[] target = param.getTarget().split("/");
        String msg = param.getMsg();
        simpMessagingTemplate.convertAndSendToUser(target[0], "/" + target[1], CCWebSocketResponse.createSuccessData(5, msg));
    }

    @MessageMapping("/boardcastAgoraUID")
    public void boardcastAgoraUID(UserPrincipal user, AgoraParam param) {
        log.info("boardcast");
        String roomAddr = cloudCourseCacheService.getSubScribe(Constant.CCourse.SUBSCRIBE.ROOM_MSG, user.getId());
        CourseUserVo updateUser = cloudCourseCacheService.updateAgoraUIDAndDeviceType(param.getAgoraUID(), param.getDeviceType(), user.getId(), roomAddr);
        if (updateUser != null) {
            simpMessagingTemplate.convertAndSendToUser(roomAddr, Constant.CCourse.SUBSCRIBE.ROOM_MSG, CCWebSocketResponse.createSuccessData(6, updateUser));
            log.info("广播了用户信息:{}", updateUser);
        } else {
            String addr = cloudCourseCacheService.getSubScribe(Constant.CCourse.SUBSCRIBE.SINGLE_MSG, user.getId());
            simpMessagingTemplate.convertAndSendToUser(addr, Constant.CCourse.SUBSCRIBE.SINGLE_MSG, CCWebSocketResponse.createFail("声网id更新失败"));
        }
    }

    @MessageMapping("/kickUser")
    public void kickUser(UserPrincipal user, UserParam param) {
        String roomAddr = cloudCourseCacheService.getSubScribe(Constant.CCourse.SUBSCRIBE.ROOM_MSG, user.getId());
        CourseUserVo updateUser = cloudCourseCacheService.kickUser(param.getUserId(), param.getUserType(), roomAddr);
        if (updateUser != null) {
            simpMessagingTemplate.convertAndSendToUser(roomAddr, Constant.CCourse.SUBSCRIBE.ROOM_MSG, CCWebSocketResponse.createSuccessData(7, updateUser));
        } else {
            String addr = cloudCourseCacheService.getSubScribe(Constant.CCourse.SUBSCRIBE.SINGLE_MSG, user.getId());
            simpMessagingTemplate.convertAndSendToUser(addr, Constant.CCourse.SUBSCRIBE.SINGLE_MSG, CCWebSocketResponse.createFail("操作失败,请重试"));
        }
    }

    @MessageMapping("/transFormHost")
    public void transFormHost(UserPrincipal user, UserParam param) {
        String roomAddr = cloudCourseCacheService.getSubScribe(Constant.CCourse.SUBSCRIBE.ROOM_MSG, user.getId());
        List<CourseUserVo> courseUserVoList = cloudCourseCacheService.transFormHost(user.getId(), param.getUserId(), param.getUserType(), roomAddr);
        if (CollUtil.isNotEmpty(courseUserVoList)) {
            simpMessagingTemplate.convertAndSendToUser(roomAddr, Constant.CCourse.SUBSCRIBE.ROOM_MSG, CCWebSocketResponse.createSuccessData(8, courseUserVoList));
        } else {
            String addr = cloudCourseCacheService.getSubScribe(Constant.CCourse.SUBSCRIBE.SINGLE_MSG, user.getId());
            simpMessagingTemplate.convertAndSendToUser(addr, Constant.CCourse.SUBSCRIBE.SINGLE_MSG, CCWebSocketResponse.createFail("主讲人转化失败,请重试"));
        }
    }

    @MessageMapping("/transFormInterActor")
    public void transFormInterActor(UserPrincipal user, UserParam param) {
        String roomAddr = cloudCourseCacheService.getSubScribe(Constant.CCourse.SUBSCRIBE.ROOM_MSG, user.getId());
        CourseUserVo courseUserVo = cloudCourseCacheService.transFormInterActor(param.getUserId(), param.getUserType(), param.isRemoveRole(), roomAddr);
        if (courseUserVo != null) {
            simpMessagingTemplate.convertAndSendToUser(roomAddr, Constant.CCourse.SUBSCRIBE.ROOM_MSG, CCWebSocketResponse.createSuccessData(9, courseUserVo));
        } else {
            String addr = cloudCourseCacheService.getSubScribe(Constant.CCourse.SUBSCRIBE.SINGLE_MSG, user.getId());
            simpMessagingTemplate.convertAndSendToUser(addr, Constant.CCourse.SUBSCRIBE.SINGLE_MSG, CCWebSocketResponse.createFail("互动人转化失败,请重试"));
        }
    }

    @MessageMapping("/paintPad")
    public void paintPad(UserPrincipal user, PadParam param) {
        String addr = cloudCourseCacheService.getSubScribe(Constant.CCourse.SUBSCRIBE.PAD_MSG, user.getId());
        simpMessagingTemplate.convertAndSendToUser(addr, Constant.CCourse.SUBSCRIBE.PAD_MSG, CCWebSocketResponse.createSuccessData(10, param.getPaintInfo()));
    }


    @MessageMapping("/authPad")
    public void authPad(UserParam param) {
        String addr = cloudCourseCacheService.getSubScribe(Constant.CCourse.SUBSCRIBE.SINGLE_MSG, param.getUserId());
        simpMessagingTemplate.convertAndSendToUser(addr, Constant.CCourse.SUBSCRIBE.SINGLE_MSG, CCWebSocketResponse.createSuccessDataMsg(11, param.getParam(), "授权白板,请进行白板订阅"));
    }

    @MessageMapping("/startRecord")
    public void startRecord(UserPrincipal user) {
        String roomAddr = cloudCourseCacheService.getSubScribe(Constant.CCourse.SUBSCRIBE.ROOM_MSG, user.getId());
        CloudSubCourse curCourse = cloudSubCourseService.getCurCourse(roomAddr);
        if (StrUtil.isBlank(roomAddr) || curCourse == null) {
            log.info("用户不在课堂内无法进行课程录制");
            return;
        }
        log.info("开始录制 {}",curCourse.getId());
        cloudCourseRecordService.beginRecord(curCourse.getId());
        cloudCourseCacheService.addUserVideo(roomAddr,curCourse);
        String addr = cloudCourseCacheService.getSubScribe(Constant.CCourse.SUBSCRIBE.SINGLE_MSG, user.getId());
        simpMessagingTemplate.convertAndSendToUser(addr, Constant.CCourse.SUBSCRIBE.SINGLE_MSG, CCWebSocketResponse.createSuccess(12, "录制开始"));
    }

    @MessageMapping("/endRecord")
    public void endRecord(UserPrincipal user) {
        String roomAddr = cloudCourseCacheService.getSubScribe(Constant.CCourse.SUBSCRIBE.ROOM_MSG, user.getId());
        CloudSubCourse curCourse = cloudSubCourseService.getCurCourse(roomAddr);
        if (StrUtil.isBlank(roomAddr) || curCourse == null) {
            log.info("用户不在课堂内无法结束课程录制");
            return;
        }
        log.info("结束录制 {}",curCourse.getId());
        cloudCourseRecordService.endRecord(curCourse.getId());
        String addr = cloudCourseCacheService.getSubScribe(Constant.CCourse.SUBSCRIBE.SINGLE_MSG, user.getId());
        simpMessagingTemplate.convertAndSendToUser(addr, Constant.CCourse.SUBSCRIBE.SINGLE_MSG, CCWebSocketResponse.createSuccess(13, "录制结束"));
    }

    @MessageMapping("/startCourse")
    public void startCourse(UserPrincipal user,CourseParam courseParam) {
        CloudSubCourse cloudSubCourse = new CloudSubCourse();
        cloudSubCourse.setId(courseParam.getCloudSubCourseId());
        cloudSubCourse = cloudSubCourseService.startCourse(cloudSubCourse);
        String roomAddr = cloudCourseCacheService.getSubScribe(Constant.CCourse.SUBSCRIBE.ROOM_MSG, user.getId());
        simpMessagingTemplate.convertAndSendToUser(roomAddr, Constant.CCourse.SUBSCRIBE.ROOM_MSG, CCWebSocketResponse.createSuccess(14,"课程:"+cloudSubCourse.getName()+"开始"));
    }

    @MessageMapping("/endCourse")
    public void endCourse(UserPrincipal user,CourseParam courseParam) {
        CloudSubCourse cloudSubCourse = new CloudSubCourse();
        cloudSubCourse.setId(courseParam.getCloudSubCourseId());
        cloudSubCourse = cloudSubCourseService.endCourse(cloudSubCourse);
        String roomAddr = cloudCourseCacheService.getSubScribe(Constant.CCourse.SUBSCRIBE.ROOM_MSG, user.getId());
        simpMessagingTemplate.convertAndSendToUser(roomAddr, Constant.CCourse.SUBSCRIBE.ROOM_MSG, CCWebSocketResponse.createSuccess(15,"课程:"+cloudSubCourse.getName()+"结束"));
    }

    @MessageMapping("/padControl")
    public void padControl(UserPrincipal user,ControlParam param) {
        String roomAddr = cloudCourseCacheService.getSubScribe(Constant.CCourse.SUBSCRIBE.ROOM_MSG, user.getId());
        if(param.getCommand()==1) {
            cloudCourseCacheService.addPadUID(roomAddr,param.getPadUID());
        }else{
            cloudCourseCacheService.removePadUID(roomAddr);
        }
        simpMessagingTemplate.convertAndSendToUser(roomAddr, Constant.CCourse.SUBSCRIBE.ROOM_MSG, CCWebSocketResponse.createSuccessData(16,param));
    }

    @MessageMapping("/muteControl")
    public void muteControl(ControlParam param) {
        String[] target = param.getTarget().split("/");
        simpMessagingTemplate.convertAndSendToUser(target[0], "/" + target[1], CCWebSocketResponse.createSuccessData(17, param.getCommand()));
    }

    @MessageMapping("/cameraControl")
    public void cameraControl(ControlParam param) {
        String[] target = param.getTarget().split("/");
        simpMessagingTemplate.convertAndSendToUser(target[0], "/" + target[1], CCWebSocketResponse.createSuccessData(18, param.getCommand()));
    }

    @MessageMapping("/getFileList")
    public void getFileList(UserPrincipal user) {
        String roomAddr = cloudCourseCacheService.getSubScribe(Constant.CCourse.SUBSCRIBE.ROOM_MSG, user.getId());
        List<FileVo> fileList = cloudCourseCacheService.getFileList(roomAddr);
        String addr = cloudCourseCacheService.getSubScribe(Constant.CCourse.SUBSCRIBE.SINGLE_MSG, user.getId());
        simpMessagingTemplate.convertAndSendToUser(addr, Constant.CCourse.SUBSCRIBE.SINGLE_MSG, CCWebSocketResponse.createSuccessData(19, fileList));
        log.info("文件列表信息:{}", fileList);
    }

    @MessageMapping("/addFile")
    public void addFile(UserPrincipal user,FileVo fileVo) {
        String roomAddr = cloudCourseCacheService.getSubScribe(Constant.CCourse.SUBSCRIBE.ROOM_MSG, user.getId());
        FileVo file = cloudCourseCacheService.addFile(fileVo, roomAddr);
        String addr = cloudCourseCacheService.getSubScribe(Constant.CCourse.SUBSCRIBE.SINGLE_MSG, user.getId());
        if(file!=null) {
            simpMessagingTemplate.convertAndSendToUser(addr, Constant.CCourse.SUBSCRIBE.SINGLE_MSG, CCWebSocketResponse.createSuccessData(20, file));
        }
        log.info("addFile 文件信息:{}", file);
    }

    @MessageMapping("/removeFile")
    public void removeFile(UserPrincipal user,FileVo fileVo) {
        String roomAddr = cloudCourseCacheService.getSubScribe(Constant.CCourse.SUBSCRIBE.ROOM_MSG, user.getId());
        FileVo file = cloudCourseCacheService.removeFile(fileVo, roomAddr);
        if(file!=null) {
            simpMessagingTemplate.convertAndSendToUser(roomAddr, Constant.CCourse.SUBSCRIBE.ROOM_MSG, CCWebSocketResponse.createSuccessData(21, file));
        }
        log.info("removeFile 文件信息:{}", file);
    }


    @MessageMapping("/shareFile")
    public void shareFile(UserPrincipal user,FileVo fileVo) {
        String roomAddr = cloudCourseCacheService.getSubScribe(Constant.CCourse.SUBSCRIBE.ROOM_MSG, user.getId());
        String[] channels = fileVo.getTarget().split(",");
        for (String channel : channels) {
            String[] target = channel.split("/");
            simpMessagingTemplate.convertAndSendToUser(target[0], "/" + target[1], CCWebSocketResponse.createSuccessData(22, fileVo));
        }
        log.info("shareFile 文件信息:{}", fileVo);
    }

    @MessageMapping("/getLessonFileList")
    public void getLessonFileList(UserPrincipal user) {
        String roomAddr = cloudCourseCacheService.getSubScribe(Constant.CCourse.SUBSCRIBE.ROOM_MSG, user.getId());
        List<FileVo> lessonFileList = cloudSubCourseLessonsFileService.getLessonFileList(roomAddr);
        String addr = cloudCourseCacheService.getSubScribe(Constant.CCourse.SUBSCRIBE.SINGLE_MSG, user.getId());
        simpMessagingTemplate.convertAndSendToUser(addr, Constant.CCourse.SUBSCRIBE.SINGLE_MSG, CCWebSocketResponse.createSuccessData(23, lessonFileList));
        log.info("getLessonFileList 文件列表信息:{}", lessonFileList);
    }



    private List<CloudSubCourseRoom> assembleCloudSubCourseRoomList(List<CloudSubCourse> data) {
        List<CloudSubCourseRoom> resultList = data.stream().map(item -> {
            CloudSubCourseRoom cloudSubCourseRoom = new CloudSubCourseRoom();
            cloudSubCourseRoom.setId(item.getId());
            cloudSubCourseRoom.setName(item.getName());
            cloudSubCourseRoom.setInTime(DateUtil.parse(item.getStartTime()).toString("yyyy-MM-dd HH:mm"));
            cloudSubCourseRoom.setOutTime(DateUtil.parse(item.getEndTime()).toString("yyyy-MM-dd HH:mm"));
            cloudSubCourseRoom.setCreateTime(item.getCreateTime());
            cloudSubCourseRoom.setSchoolId(item.getSchoolId());
            cloudSubCourseRoom.setBroadcastCode(item.getBroadcastCode());
            if (item.getCloudCourse() != null) {
                cloudSubCourseRoom.setAlertTime(item.getCloudCourse().getAlertTime());
                cloudSubCourseRoom.setAllowListen(item.getCloudCourse().getAllowListen());
                cloudSubCourseRoom.setTeacherCount(item.getCloudCourse().getTeacherCount());
                cloudSubCourseRoom.setParentName(item.getCloudCourse().getName());
                cloudSubCourseRoom.setDuration(item.getCloudCourse().getDuration());
                if (item.getCloudCourse().getCreateTeacher() != null) {
                    cloudSubCourseRoom.setCreateTeacherId(item.getCloudCourse().getCreateTeacher().getId());
                    cloudSubCourseRoom.setCreateTeacherName(item.getCloudCourse().getCreateTeacher().getName());
                    cloudSubCourseRoom.setCreateTeacherTel(item.getCloudCourse().getCreateTeacher().getTel());
                }
            }
            if (item.getTeacher() != null) {
                cloudSubCourseRoom.setTeacherId(item.getTeacher().getId());
                cloudSubCourseRoom.setTeacherName(item.getTeacher().getName());
                cloudSubCourseRoom.setTeacherTel(item.getTeacher().getTel());
            }
            return cloudSubCourseRoom;
        }).collect(Collectors.toList());
        return resultList;
    }



    //    @MessageMapping("/getShareFileList")
//    public void getShareFileList(UserPrincipal user){
//        CloudCourseShareFile cloudCourseShareFile = new CloudCourseShareFile();
//        cloudCourseShareFile.setBroadcastCode(user.getGroupName());
//        Pager pager = new Pager();
//        pager.setIncludes("path","name");
//        pager.setPaging(false);
//        cloudCourseShareFile.setPager(pager);
//        List<CloudCourseShareFile> data = cloudCourseShareFileService.findCloudCourseShareFileListByCondition(cloudCourseShareFile);
//        data.forEach(returnData ->{
//            returnData.setPath(Constant.RES_PRE+returnData.getPath());
//        });
//        String addr = cloudCourseCacheService.getSubScribe(Constant.CCourse.SUBSCRIBE.SINGLE_MSG, user.getUserId());
//        simpMessagingTemplate.convertAndSendToUser(addr, Constant.CCourse.SUBSCRIBE.SINGLE_MSG, CCWebSocketResponse.createSuccessData(12,data));
//    }
//
//    @MessageMapping("/saveShareFile")
//    public void saveShareFile(UserPrincipal user,FileParam param){
//        String id = Token2IdUtil.token2Id(user.getId());
//        String broadcastCode = cloudCourseCacheService.getSubScribe(Constant.CCourse.SUBSCRIBE.ROOM_MSG, user.getUserId());
//        String name = param.getName();
//        CloudSubCourse cloudSubCourse = cloudSubCourseService.findCloudSubCourseById(param.getCloudSubCourseId());
//        String addr = cloudCourseCacheService.getSubScribe(Constant.CCourse.SUBSCRIBE.SINGLE_MSG, user.getUserId());
//        if(cloudSubCourse==null) {
//            simpMessagingTemplate.convertAndSendToUser(addr, Constant.CCourse.SUBSCRIBE.SINGLE_MSG, CCWebSocketResponse.createFail("课程不存在"));
//            return;
//        }
//        CloudCourseShareFile cloudCourseShareFile  = new CloudCourseShareFile();
//        cloudCourseShareFile.setName(name);
//        cloudCourseShareFile.setCloudSubCourseId(param.getCloudSubCourseId());
//        cloudCourseShareFile.setPath(param.getPath());
//        cloudCourseShareFile.setBroadcastCode(broadcastCode);
//        cloudCourseShareFile.setUploadUserId(id);
//        cloudCourseShareFile.setSchoolId(cloudSubCourse.getSchoolId());
//        cloudCourseShareFileService.saveCloudCourseShareFile(cloudCourseShareFile);
//        simpMessagingTemplate.convertAndSendToUser(addr, Constant.CCourse.SUBSCRIBE.SINGLE_MSG, CCWebSocketResponse.createSuccessData(13,cloudCourseShareFile));
//    }

}
