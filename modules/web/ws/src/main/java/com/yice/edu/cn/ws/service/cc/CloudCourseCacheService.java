package com.yice.edu.cn.ws.service.cc;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.*;
import com.yice.edu.cn.common.pojo.cc.otherSchoolAccount.OtherSchoolAccount;
import com.yice.edu.cn.common.pojo.cc.recording.UserInfo;
import com.yice.edu.cn.common.pojo.cc.recording.UserRecodingVo;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

@Service
public class CloudCourseCacheService {


    private Logger log = LoggerFactory.getLogger(CloudCourseCacheService.class);

    @CreateCache(name = Constant.Redis.CC_USER_STATUS, expire = Constant.Redis.CC_COURSE_TIME, timeUnit = TimeUnit.HOURS)
    @CacheRefresh(timeUnit = TimeUnit.HOURS, refresh = 1, stopRefreshAfterLastAccess = Constant.Redis.CC_COURSE_TIME)
    // 课堂码 课程人员
    private Cache<String, List<CourseUserVo>> courseUserStatusCache;


    @CreateCache(name = Constant.Redis.CC_COURSE_ROOM, expire = Constant.Redis.CC_COURSE_TIME, timeUnit = TimeUnit.HOURS)
    @CacheRefresh(timeUnit = TimeUnit.HOURS, refresh = 1, stopRefreshAfterLastAccess = Constant.Redis.CC_COURSE_TIME)
    // 课堂码 旁听人数
    private Cache<String, Integer> courseCache;

    @CreateCache(name = Constant.Redis.CC_TEMP_FILE, expire = Constant.Redis.CC_COURSE_TIME, timeUnit = TimeUnit.HOURS)
    @CacheRefresh(timeUnit = TimeUnit.HOURS, refresh = 1, stopRefreshAfterLastAccess = Constant.Redis.CC_COURSE_TIME)
    // 课堂码 临时文件
    private Cache<String, List<FileVo>> tempFileCache;

    @CreateCache(name = Constant.Redis.CC_PAD_UID, expire = Constant.Redis.CC_COURSE_TIME, timeUnit = TimeUnit.HOURS)
    @CacheRefresh(timeUnit = TimeUnit.HOURS, refresh = 1, stopRefreshAfterLastAccess = Constant.Redis.CC_COURSE_TIME)
    // 课堂码 白板UID
    private Cache<String, Long> padUIdCache;

    @CreateCache(name = Constant.Redis.CC_SUBSCRIBE, expire = Constant.Redis.CC_COURSE_TIME, timeUnit = TimeUnit.HOURS)
    @CacheRefresh(timeUnit = TimeUnit.HOURS, refresh = 1, stopRefreshAfterLastAccess = Constant.Redis.CC_COURSE_TIME)
    // 订阅地址和用户的关系
    private Cache<String, String> subscribeCache;


    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CloudSubCourseService cloudSubCourseService;

    @Autowired
    private CloudCourseRecordService cloudCourseRecordService;

    @Autowired
    private RedisLockRegistry redisLockRegistry;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    /**
     * 减少观看直播人数
     *
     * @param broadcastCode 直播码
     * @return
     */
    public int reduceWatchNum(String broadcastCode) {
        // 不要求精确，不加锁
        Integer count = courseCache.get(broadcastCode);
        if (count == null || count.intValue() == 0) {
            return 0;
        }
        count--;
        courseCache.put(broadcastCode, count);
        return count;
    }

    /**
     * 累加观看直播人数
     *
     * @param broadcastCode 直播码
     * @return
     */
    public int addWatchNum(String broadcastCode) {
        // 不要求精确，不加锁(分布式锁)
        Integer count = courseCache.get(broadcastCode);
        if (count == null) {
            count = 0;
        }
        count++;
        courseCache.put(broadcastCode, count);

        return count;
    }

    /**
     * 获取观看直播的旁听人数
     *
     * @param broadcastCode
     * @return
     */
    public Integer getBroadcastCodeNum(String broadcastCode) {
        return courseCache.computeIfAbsent(broadcastCode, (t) -> 0);
    }

    /**
     * 发送观看人数
     *
     * @param broadcastCode
     */
    public void sendWatchPeopleNum(String broadcastCode) {
        int num = getBroadcastCodeNum(broadcastCode);
        simpMessagingTemplate.convertAndSendToUser(broadcastCode, "watchPeopleNum", num);
    }

    /**
     * 添加用户
     *
     * @param id
     * @param groupName
     */
    public CourseUserVo addUser(String id, String groupName) {
        CourseUserVo courseUserVo = getCourseUserVo(id);
        if (StrUtil.isEmpty(groupName) || courseUserVo == null) {
            log.info("addUser房间{}不存在或用户{}为空", groupName, id);
            return null;
        }
        //判断是否是主讲人并设置主讲人
        CloudSubCourse curCourse = cloudSubCourseService.getCurCourse(groupName);
        if (curCourse == null || curCourse.getTeacher() == null) {
            log.info("课程{}不存在或该课程无授课老师", groupName);
            return null;
        }
        if (StrUtil.equals(courseUserVo.getUserId(), curCourse.getTeacher().getId())) {
            courseUserVo.setRole(Constant.CCourse.ROLE.LECTURER);
        } else {
            courseUserVo.setRole(Constant.CCourse.ROLE.LISTENER);
        }

        Lock lock = redisLockRegistry.obtain(Constant.Redis.CC_USER_STATUS + "_" + groupName);
        lock.lock();
        try {
            List<CourseUserVo> userList = courseUserStatusCache.computeIfAbsent(groupName, (t) -> new ArrayList<>());
            //为了确保这个用户如果在之前异常断开时,没有被清除,重新添加时清除一次该用户
            userList.remove(courseUserVo);
            if (userList.add(courseUserVo)) {
                courseUserStatusCache.put(groupName, userList);
            }
            log.info("add now list:{}", userList);
            return courseUserVo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            lock.unlock();
        }
    }


    /**
     * 移除用户
     *
     * @param id
     * @param groupName
     */
    public CourseUserVo removeUser(String id, String groupName) {
        CourseUserVo courseUserVo = getCourseUserVo(id);
        if (StrUtil.isEmpty(groupName) || courseUserVo == null) {
            log.info("removeUser房间{}不存在或用户{}为空", groupName, id);
        }
        CloudSubCourse curCourse = cloudSubCourseService.getCurCourse(groupName);
        if (curCourse == null || curCourse.getTeacher() == null) {
            log.info("课程{}不存在或该课程无授课老师", groupName);
            return null;
        }
        Lock lock = redisLockRegistry.obtain(Constant.Redis.CC_USER_STATUS + "_" + groupName);
        lock.lock();
        try {
            List<CourseUserVo> userList = courseUserStatusCache.computeIfAbsent(groupName,
                    (t) -> new ArrayList<>());
            CourseUserVo userVo = userList.stream()
                    .filter(ss -> (ss.equals(courseUserVo)))
                    .findFirst().orElse(null);
            if (userList.remove(userVo)) {
                if (Constant.CCourse.ROLE.LECTURER == userVo.getRole() || Constant.CCourse.ROLE.INTERACTOR == userVo.getRole()) {
                    removeUserVideo(userVo.getAgoraUID(), curCourse, userVo.getRole());
                }
                courseUserStatusCache.put(groupName, userList);
                simpMessagingTemplate.convertAndSendToUser(groupName, Constant.CCourse.SUBSCRIBE.ROOM_MSG, CCWebSocketResponse.create(2, CCWebSocketResponse.SUCCESS, userVo, userVo.getName() + "已下线"));
            }
            log.info("remove now list:{},{}", userList, userVo);
            return userVo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 用户列表
     *
     * @param groupName
     */
    public List<CourseUserVo> getUserList(String groupName) {
        if (StrUtil.isEmpty(groupName)) {
            log.info("房间{}不存在", groupName);
            return null;
        }
        List<CourseUserVo> userList = courseUserStatusCache.computeIfAbsent(groupName,
                (t) -> new ArrayList<>());
        //过滤声网Uid的用户
        List<CourseUserVo> filterList = userList.stream().filter(item -> (item.getAgoraUID() != null && item.getDeviceType() != null)).collect(Collectors.toList());
        log.info("filterList:{}", filterList);
        return filterList;
    }

    /**
     * 临时文件列表
     *
     * @param groupName
     */
    public List<FileVo> getFileList(String groupName) {
        if (StrUtil.isEmpty(groupName)) {
            log.info("房间{}不存在", groupName);
            return null;
        }
        List<FileVo> fileList = tempFileCache.computeIfAbsent(groupName,
                (t) -> new ArrayList<>());
        return fileList;
    }


    /**
     * 临时文件列表
     *
     * @param fileVo
     * @param groupName
     */
    public FileVo addFile(FileVo fileVo, String groupName) {
        if (StrUtil.isEmpty(groupName)) {
            log.info("房间{}不存在", groupName);
            return null;
        }
        Lock lock = redisLockRegistry.obtain(Constant.Redis.CC_TEMP_FILE + "_" + groupName);
        lock.lock();
        try {
            List<FileVo> fileList = tempFileCache.computeIfAbsent(groupName,
                    (t) -> new ArrayList<>());
            int index = fileList.size() == 0 ? 1 : fileList.get(fileList.size() - 1).getId() + 1;
            fileVo.setId(index);
            fileVo.setPath(Constant.RES_PRE + "/" + fileVo.getPath());
            if (fileList.add(fileVo)) {
                tempFileCache.put(groupName, fileList);
                return fileVo;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 删除临时文件
     *
     * @param fileVo
     * @param groupName
     */
    public FileVo removeFile(FileVo fileVo, String groupName) {
        if (StrUtil.isEmpty(groupName)) {
            log.info("房间{}不存在", groupName);
            return null;
        }
        Lock lock = redisLockRegistry.obtain(Constant.Redis.CC_TEMP_FILE + "_" + groupName);
        lock.lock();
        try {
            List<FileVo> fileList = tempFileCache.computeIfAbsent(groupName,
                    (t) -> new ArrayList<>());
            FileVo removableFile = fileList.stream()
                    .filter(ss -> (ss.equals(fileVo)))
                    .findAny().orElse(null);
            if (fileList.remove(fileVo)) {
                tempFileCache.put(groupName, fileList);
                return removableFile;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 根据用户id获取用户信息对象{@link CourseUserVo}
     *
     * @param userId
     * @return
     */
    private CourseUserVo getCourseUserVo(String userId) {
        String name;
        String imgUrl = "";
        int userType;
        String teacherId;
        Teacher teacher = teacherService.getTeacher(userId);
        if (teacher != null) {
            name = teacher.getName();
            imgUrl = teacher.getImgUrl();
            userType = Constant.CCourse.ACCOUNT_TYPE_INSIDE;
            teacherId = teacher.getId();
        } else {
            OtherSchoolAccount account = teacherService.getOtherSchoolAccount(userId);
            if (account != null) {
                name = account.getName();
                userType = Constant.CCourse.ACCOUNT_TYPE_OUTSIDE;
                teacherId = account.getId();
            } else {
                return null;
            }
        }
        CourseUserVo courseUserVo = new CourseUserVo();
        courseUserVo.setName(name);
        courseUserVo.setImgUrl(imgUrl);
        courseUserVo.setUserType(userType);
        courseUserVo.setUserId(teacherId);
        return courseUserVo;
    }

    public CourseUserVo kickUser(String userId, Integer userType, String groupName) {
        if (StrUtil.isEmpty(groupName) || StrUtil.isEmpty(userId) || userType == null) {
            log.info("房间{}不存在或参数不存在", groupName);
            return null;
        }
        CourseUserVo courseUserVo = new CourseUserVo(userType, userId);
        Lock lock = redisLockRegistry.obtain(Constant.Redis.CC_USER_STATUS + "_" + groupName);
        lock.lock();
        try {
            List<CourseUserVo> userList = courseUserStatusCache.computeIfAbsent(groupName,
                    (t) -> new ArrayList<>());
            CourseUserVo vo = userList.stream()
                    .filter(ss -> (ss.equals(courseUserVo)))
                    .findAny().orElse(null);
            if (userList.remove(courseUserVo)) {
                courseUserStatusCache.put(groupName, userList);
                return vo;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            lock.unlock();
        }
    }


    public List<CourseUserVo> transFormHost(String curUserId, String userId, Integer userType, String groupName) {
        if (StrUtil.isEmpty(groupName) || StrUtil.isEmpty(userId) || userType == null) {
            log.info("房间{}不存在或参数不存在", groupName);
            return null;
        }
        CourseUserVo current = getCourseUserVo(curUserId);
        if (current == null) {
            log.info("用户{}为空", curUserId);
            return null;
        }
        List<CourseUserVo> courseUserVoList = CollUtil.newArrayList();
        CourseUserVo transFormUser = new CourseUserVo(userType, userId);
        CloudSubCourse curCourse = cloudSubCourseService.getCurCourse(groupName);
        Lock lock = redisLockRegistry.obtain(Constant.Redis.CC_USER_STATUS + "_" + groupName);
        lock.lock();
        try {
            List<CourseUserVo> userList = courseUserStatusCache.computeIfAbsent(groupName,
                    (t) -> new ArrayList<>());
            boolean hostSuccess = false;
            boolean listenerSuccess = false;
            for (CourseUserVo courseUserVo : userList) {
                if (courseUserVo.equals(current)) {
                    removeUserVideo(courseUserVo.getAgoraUID(), curCourse, courseUserVo.getRole());
                    courseUserVo.setRole(Constant.CCourse.ROLE.LISTENER);
                    courseUserVoList.add(courseUserVo);
                    listenerSuccess = true;
                }
                if (courseUserVo.equals(transFormUser)) {
                    courseUserVo.setRole(Constant.CCourse.ROLE.LECTURER);
                    courseUserVoList.add(courseUserVo);
                    hostSuccess = true;
                }
                if (courseUserVo.getRole() == Constant.CCourse.ROLE.INTERACTOR) {
                    removeUserVideo(courseUserVo.getAgoraUID(), curCourse, courseUserVo.getRole());
                    courseUserVo.setRole(Constant.CCourse.ROLE.LISTENER);
                }
            }

            if (hostSuccess && listenerSuccess) {
                courseUserStatusCache.put(groupName, userList);
                return courseUserVoList;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            lock.unlock();
        }
    }

    public CourseUserVo transFormInterActor(String userId, Integer userType, Boolean removeRole, String groupName) {
        if (StrUtil.isEmpty(groupName) || StrUtil.isEmpty(userId) || userType == null) {
            log.info("房间{}不存在或参数不存在", groupName);
            return null;
        }
        CourseUserVo transFormUser = new CourseUserVo(userType, userId);
        CloudSubCourse curCourse = cloudSubCourseService.getCurCourse(groupName);
        Lock lock = redisLockRegistry.obtain(Constant.Redis.CC_USER_STATUS + "_" + groupName);
        lock.lock();
        try {
            List<CourseUserVo> userList = courseUserStatusCache.computeIfAbsent(groupName,
                    (t) -> new ArrayList<>());
            if (removeRole) {
                for (CourseUserVo courseUserVo : userList) {
                    if (courseUserVo.equals(transFormUser)) {
                        if (courseUserVo.getRole() != Constant.CCourse.ROLE.INTERACTOR) {
                            log.info("该用户不是互动人");
                            return null;
                        }
                        removeUserVideo(courseUserVo.getAgoraUID(), curCourse, courseUserVo.getRole());
                        courseUserVo.setRole(Constant.CCourse.ROLE.LISTENER);
                        transFormUser = courseUserVo;
                    }
                }
            } else {
                long count = userList.stream().filter(t -> t.getRole() != Constant.CCourse.ROLE.INTERACTOR).count();
                //互动人只能有4个
                if (count > 4) {
                    return null;
                }
                for (CourseUserVo courseUserVo : userList) {
                    if (courseUserVo.equals(transFormUser)) {
                        if (courseUserVo.getRole() != Constant.CCourse.ROLE.LISTENER) {
                            log.info("除了听课人的其他角色不能被修改为互动人");
                            return null;
                        }
                        courseUserVo.setRole(Constant.CCourse.ROLE.INTERACTOR);
                        transFormUser = courseUserVo;
                    }
                }
            }
            courseUserStatusCache.put(groupName, userList);
            return transFormUser;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            lock.unlock();
        }
    }


    public CourseUserVo updateAgoraUIDAndDeviceType(Long agoraUID, Integer deviceType, String userId, String groupName) {
        if (StrUtil.isEmpty(groupName) || deviceType == null || agoraUID == null) {
            log.info("房间{}不存在或参数不存在", groupName);
            return null;
        }
        CourseUserVo current = getCourseUserVo(userId);
        if (current == null) {
            log.info("用户{}为空", userId);
            return null;
        }
        Lock lock = redisLockRegistry.obtain(Constant.Redis.CC_USER_STATUS + "_" + groupName);
        lock.lock();
        try {
            CourseUserVo updateUser = null;
            List<CourseUserVo> userList = courseUserStatusCache.computeIfAbsent(groupName,
                    (t) -> new ArrayList<>());
            for (CourseUserVo courseUserVo : userList) {
                if (courseUserVo.equals(current)) {
                    courseUserVo.setAgoraUID(agoraUID);
                    courseUserVo.setDeviceType(deviceType);
                    updateUser = courseUserVo;
                }
            }
            courseUserStatusCache.put(groupName, userList);
            return updateUser;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            lock.unlock();
        }
    }

    public void addSubScribe(String subscribeName, String userId, String destination) {
        String key = String.format("%s:%s", subscribeName, userId);
        Lock lock = redisLockRegistry.obtain(Constant.Redis.CC_SUBSCRIBE + "_" + key);
        lock.lock();
        try {
            subscribeCache.put(key, destination);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void removeSubScribe(String subscribeName, String userId) {
        String key = String.format("%s:%s", subscribeName, userId);
        Lock lock = redisLockRegistry.obtain(Constant.Redis.CC_SUBSCRIBE + "_" + key);
        lock.lock();
        try {
            subscribeCache.remove(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public String getSubScribe(String subscribeName, String userId) {
        String key = String.format("%s:%s", subscribeName, userId);
        return subscribeCache.get(key);
    }

    public void addPadUID(String roomAddr, Long agoraUID) {
        if (agoraUID == null) {
            log.error("白板agoraUID不能为空");
            return;
        }
        padUIdCache.put(roomAddr, agoraUID);
    }

    public void removePadUID(String roomAddr) {
        Long agoraUID = padUIdCache.get(roomAddr);
        if (agoraUID == null) {
            log.error("该房间没有打开白板");
            return;
        }
        padUIdCache.remove(roomAddr);
        CloudSubCourse curCourse = cloudSubCourseService.getCurCourse(roomAddr);
        removeUserVideo(agoraUID, curCourse, Constant.CCourse.ROLE.PAD);
    }

    public void addUserVideo(String groupName, CloudSubCourse cloudSubCourse) {
        List<CourseUserVo> userList = getUserList(groupName);
        try {
            UserRecodingVo vo = new UserRecodingVo();
            vo.setCourseId(cloudSubCourse.getId());
            List<UserInfo> userInfoList = CollUtil.newArrayList();
            for (CourseUserVo userVo : userList) {
                if (userVo.getAgoraUID() == null) {
                    continue;
                }
                if (userVo.getRole() == Constant.CCourse.ROLE.INTERACTOR || userVo.getRole() == Constant.CCourse.ROLE.LECTURER) {
                    UserInfo userInfo = new UserInfo();
                    userInfo.setUid(userVo.getAgoraUID());
                    userInfo.setType(userVo.getRole());
                    userInfoList.add(userInfo);
                }
            }
            Long padUid = padUIdCache.get(groupName);
            if (padUid != null) {
                UserInfo userInfo = new UserInfo();
                userInfo.setUid(padUid);
                userInfo.setType(Constant.CCourse.ROLE.PAD);
                userInfoList.add(userInfo);
            }
            vo.setUsrInfoList(userInfoList);
            cloudCourseRecordService.addUserVideo(vo);
            log.info("添加视频流:{}", vo);
        } catch (Exception e) {
            log.error("录制服务异常", e.getMessage());
        }
    }

    private void removeUserVideo(Long agoraUID, CloudSubCourse curCourse, Integer role) {
        try {
            UserRecodingVo vo = new UserRecodingVo();
            vo.setCourseId(curCourse.getId());
            List<UserInfo> userInfoList = CollUtil.newArrayList();
            UserInfo userInfo = new UserInfo();
            userInfo.setUid(agoraUID);
            userInfo.setType(role);
            userInfoList.add(userInfo);
            vo.setUsrInfoList(userInfoList);
            cloudCourseRecordService.removeUserVideo(vo);
            log.info("删除视频流:{}", vo);
        } catch (Exception e) {
            log.error("录制服务异常", e.getMessage());
        }
    }
}