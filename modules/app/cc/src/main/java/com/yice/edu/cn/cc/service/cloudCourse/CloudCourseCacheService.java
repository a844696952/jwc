package com.yice.edu.cn.cc.service.cloudCourse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Service;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourse;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CoursePerVo;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.ListenTeacher;
import com.yice.edu.cn.common.pojo.cc.otherSchoolAccount.OtherSchoolAccount;

@Service
public class CloudCourseCacheService {

	@CreateCache(name = Constant.Redis.CC_COURSE_ROOM, expire = Constant.Redis.CC_COURSE_TIME, timeUnit = TimeUnit.HOURS)
	@CacheRefresh(timeUnit = TimeUnit.HOURS, refresh = 1, stopRefreshAfterLastAccess = Constant.Redis.CC_COURSE_TIME)
	private Cache<String, Integer> courseCache;// 课堂码 旁听人数

	@CreateCache(name = Constant.Redis.CC_PER_STATUS, expire = Constant.Redis.CC_COURSE_TIME, timeUnit = TimeUnit.HOURS)
	@CacheRefresh(timeUnit = TimeUnit.HOURS, refresh = 1, stopRefreshAfterLastAccess = Constant.Redis.CC_COURSE_TIME)
	private Cache<String, List<CoursePerVo>> coursePerStatusCache;// 课堂码 课程对象

	@Autowired
	private RedisLockRegistry redisLockRegistry;

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
	 * 开始/进入课堂后，缓存课堂的参与人员并修改状态
	 * 
	 * @param coursePerVo
	 * @param cloudCourse
	 * @return
	 */
//	public boolean updateCoursePerStatus(CloudCourse cloudCourse, CoursePerVo coursePerVo) {
//		Lock lock = redisLockRegistry.obtain(Constant.CCourse.APP_PRE+"_"+cloudCourse.getBroadcastCode());
//		lock.lock();
//		try {
//			List<CoursePerVo> perList = coursePerStatusCache.computeIfAbsent(coursePerVo.getBroadcastCode(),
//					(t) -> new ArrayList<CoursePerVo>());
//			if (perList.isEmpty()) {
//				packageCoursePerList(cloudCourse, perList);
//			}
//
//			perList.forEach(ss -> {
//				if (ss.getUserId().equals(coursePerVo.getUserId())
//						&& ss.getUserType().intValue() == coursePerVo.getUserType().intValue()) {
//					// 修改人员的在线/离线状态
//					if (coursePerVo.getStatus() != null) {
//						if (coursePerVo.getStatus() == Constant.CCourse.ACCOUNT_ONLINE) {
//							ss.setMac(coursePerVo.getMac());
//							ss.setStatus(Constant.CCourse.ACCOUNT_ONLINE);
//							ss.setUuId(coursePerVo.getUuId());
//						}
//						if (coursePerVo.getStatus() == Constant.CCourse.ACCOUNT_OFFLINE
//								&& (ss.getUuId() != null && ss.getUuId().equals(coursePerVo.getUuId()))) {
//							ss.setStatus(Constant.CCourse.ACCOUNT_OFFLINE);
//							ss.setLecturer(false);
//						}
//					}
//
//					// 修改主讲人
//					if (coursePerVo.getLecturer() != null) {
//						if (coursePerVo.getLecturer()) {
//							long count = perList.stream().filter(vo -> vo.getLecturer().booleanValue()).count();
//							if (count == 0) {
//								ss.setLecturer(true);
//							}
//						} else {
//							ss.setLecturer(false);
//						}
//					}
//					return;
//				}
//			});
//			coursePerStatusCache.put(coursePerVo.getBroadcastCode(), perList);
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		} finally {
//			lock.unlock();
//		}
//	}

	/**
	 * 获取某个课堂某个用户的状态
	 * 
	 * @param coursePerVo
	 * @return
	 */
	public CoursePerVo findCoursePerStatus(CoursePerVo coursePerVo) {
		List<CoursePerVo> coursePerVoList = coursePerStatusCache.get(coursePerVo.getBroadcastCode());
		if (coursePerVoList == null) {
			return null;
		}
		CoursePerVo vo = coursePerVoList.stream()
				.filter(ss -> (ss.getUserId().equals(coursePerVo.getUserId())
						&& ss.getUserType().intValue() == coursePerVo.getUserType().intValue()))
				.findFirst().orElse(null);
		return vo;
	}

	/**
	 * 获取某个课堂的当前主讲人
	 * 
	 * @param broadcastCode 房间码
	 * @return
	 */
	public CoursePerVo findCoursePerLecturer(String broadcastCode) {
		List<CoursePerVo> coursePerVoList = coursePerStatusCache.get(broadcastCode);
		if (coursePerVoList == null) {
			return null;
		}
		CoursePerVo vo = coursePerVoList.stream().filter(ss -> ss.getLecturer().booleanValue()).findFirst()
				.orElse(null);
		return vo;
	}

	/**
	 * 清除课程对象缓存
	 * 
	 * @param broadcastCode 房间码
	 * @return
	 */
	public boolean clearCoursePerStatusCache(String broadcastCode) {
		Lock lock = redisLockRegistry.obtain(Constant.CCourse.APP_PRE+"_"+broadcastCode);
		lock.lock();
		try {
			coursePerStatusCache.remove(broadcastCode);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 拼接课堂的听课人员到同一个list
	 * 
	 * @param cloudCourse
	 * @param list
	 */
//	private void packageCoursePerList(CloudCourse cloudCourse, List<CoursePerVo> list) {
//		// 存放主讲人
//		CoursePerVo createTeacherVo = new CoursePerVo();
//		createTeacherVo.setBroadcastCode(cloudCourse.getBroadcastCode());
//		createTeacherVo.setStatus(Constant.CCourse.ACCOUNT_OFFLINE);
//		createTeacherVo.setCreator(true);
//		createTeacherVo.setLecturer(true);
//		createTeacherVo.setUserName(cloudCourse.getCreateTeacherTel());
//		createTeacherVo.setUserId(cloudCourse.getCreateTeacherId());
//		createTeacherVo.setUserType(Constant.CCourse.ACCOUNT_TYPE_INSIDE);
//		list.add(createTeacherVo);
//
//		// 校内听课老师
//		for (ListenTeacher ss : cloudCourse.getListenTeachers()) {
//			CoursePerVo vo = new CoursePerVo();
//			vo.setBroadcastCode(cloudCourse.getBroadcastCode());
//			vo.setStatus(Constant.CCourse.ACCOUNT_OFFLINE);
//			vo.setUserId(ss.getId());
//			vo.setCreator(false);
//			vo.setLecturer(false);
//			vo.setUserName(ss.getTel());
//			vo.setUserType(Constant.CCourse.ACCOUNT_TYPE_INSIDE);
//			list.add(vo);
//		}
//
//		// 校外听课账号
//		for (OtherSchoolAccount ss : cloudCourse.getOtherSchoolAccounts()) {
//			CoursePerVo vo = new CoursePerVo();
//			vo.setBroadcastCode(cloudCourse.getBroadcastCode());
//			vo.setStatus(Constant.CCourse.ACCOUNT_OFFLINE);
//			vo.setUserId(ss.getId());
//			vo.setUserName(ss.getName());
//			vo.setCreator(false);
//			vo.setLecturer(false);
//			vo.setUserType(Constant.CCourse.ACCOUNT_TYPE_OUTSIDE);
//			list.add(vo);
//		}
//
//	}
}