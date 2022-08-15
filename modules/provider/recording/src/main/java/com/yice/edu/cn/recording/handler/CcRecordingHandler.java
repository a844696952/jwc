package com.yice.edu.cn.recording.handler;

import java.util.List;
import java.util.Vector;

import com.yice.edu.cn.common.CloudCourseCommon;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.cc.recording.UserInfo;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.recording.executor.RecodingExecutorService;
import com.yice.edu.cn.recording.service.CloudCoursedResourceService;
import com.yice.edu.cn.recording.utils.VideoFileUtils;

import io.agora.recording.RecordingEventHandler;
import io.agora.recording.RecordingSDK;
import io.agora.recording.common.Common;
import io.agora.recording.common.Common.AudioFrame;
import io.agora.recording.common.Common.AudioVolumeInfo;
import io.agora.recording.common.Common.VideoFrame;
import io.agora.recording.common.RecordingConfig;
import io.agora.recording.common.RecordingEngineProperties;

public class CcRecordingHandler implements RecordingEventHandler {
	private CloudCoursedResourceService cloudCoursedResourceService;
	private final static int log = 1;

	private int width = 0;
	private int height = 0;
	private String storageDir = "";
	Vector<UserInfo> layoutUsers;
	public long mNativeHandle = 0;
	private RecordingConfig config = null;
	private RecordingSDK RecordingSDKInstance = null;
	private String cloudCourseId;

	public CcRecordingHandler(RecordingSDK recording, CloudCoursedResourceService cloudCoursedResourceService,
			String cCourseId) {
		this.RecordingSDKInstance = recording;
		this.cloudCoursedResourceService = cloudCoursedResourceService;
		this.cloudCourseId = cCourseId;
		RecordingSDKInstance.registerOberserver(this);
		layoutUsers = initVideoLayout();
	}

	public CcRecordingHandler() {
	}

	public void unRegister() {
		RecordingSDKInstance.unRegisterOberserver(this);
	}

	public void nativeObjectRef(long nativeHandle) {
		mNativeHandle = nativeHandle;
	}

	public void onLeaveChannel(int reason) {
		System.out.println("---------------onLeaveChannel---------------------------");
	}

	public void onError(int error, int stat_code) {
		System.out.println("RecordingSDK onError,error:" + error + ",stat code:" + stat_code);
	}

	public void onWarning(int warn) {
		System.out.println("onWarning");
	}

	public void onJoinChannelSuccess(String channelId, long uid) {
		System.out.println("onJoinChannelSuccess, channelId:" + channelId + ", uid:" + uid);
	}

	public void onUserOffline(long uid, int reason) {
		System.out.println("onUserOffline");
	}

	public void onUserJoined(long uid, String recordingDir) {
		System.out.println("onUserJoined=" + uid);
		storageDir = recordingDir;
	}

	public void onActiveSpeaker(long uid) {
		System.out.println("onActiveSpeaker");
	}

	public void onReceivingStreamStatusChanged(boolean receivingAudio, boolean receivingVideo) {
		System.out.println("onReceivingStreamStatusChanged");
	}

	public void onConnectionLost() {
		System.out.println("onConnectionLost");
	}

	public void onConnectionInterrupted() {
		System.out.println("onConnectionInterrupted");
	}

	public void onAudioVolumeIndication(AudioVolumeInfo[] infos) {
		System.out.println("onAudioVolumeIndication");
	}

	public void onFirstRemoteVideoDecoded(long uid, int width, int height, int elapsed) {
		System.out.println("onFirstRemoteVideoDecoded: "+" elapsed:"+elapsed+" width:"+width+" height:"+height);
	}

	public void onFirstRemoteAudioFrame(long uid, int elapsed) {
		System.out.println("onFirstRemoteAudioFrame User:" + Long.toString(uid) + ", elapsed:" + elapsed);
	}

	public void audioFrameReceived(long uid, AudioFrame frame) {
		System.out.println("audioFrameReceived");
	}

	public void videoFrameReceived(long uid, int type, VideoFrame frame, int rotation) {
		System.out.println("videoFrameReceived");
	}

	public void stopCallBack() {
		cloudCoursedResourceService.recordingUpload(storageDir);
	}

	public void recordingPathCallBack(String path) {
		storageDir = path;
	}

	/**
	 * 
	 * @param cCourseId 子课程id
	 */
	public void createChannel() {
		RecordingConfig config = new RecordingConfig();
		config.recordFileRootDir = config.recordFileRootDir + "/" + this.cloudCourseId;
		this.config = config;

		String[] sourceStrArray = config.mixResolution.split(",");
		this.width = Integer.valueOf(sourceStrArray[0]).intValue();
		this.height = Integer.valueOf(sourceStrArray[1]).intValue();

		RecordingSDKInstance.createChannel(Constant.CCourse.AGORA.appId, "", this.cloudCourseId, 0, config,
				CcRecordingHandler.log);
	}

	public boolean leaveChannel(long nativeHandle) {
		return RecordingSDKInstance.leaveChannel(nativeHandle);
	}

	public int startService(long nativeHandle) {
		return RecordingSDKInstance.startService(nativeHandle);
	}

	public int stopService(long nativeHandle) {
		return RecordingSDKInstance.stopService(nativeHandle);
	}

	public RecordingEngineProperties getProperties(long nativeHandle) {
		return RecordingSDKInstance.getProperties(nativeHandle);
	}

	public int beginVideoLayout() {
		Common ei = new Common();
		Common.VideoMixingLayout layout = ei.new VideoMixingLayout();
		if (layoutUsers.size() > 17) {
			System.out.println("peers size is bigger than max 17:" + layoutUsers.size());
			return -1;
		}

		layout.canvasHeight = height;
		layout.canvasWidth = width;
		layout.backgroundColor = "#e60a0a";
		layout.regionCount = (int) (layoutUsers.size());

		Common.VideoMixingLayout.Region[] regionList = new Common.VideoMixingLayout.Region[layoutUsers.size()];
		adjustVideoLayout(regionList, layout);
		layout.regions = regionList;
		return RecordingSDKInstance.setVideoMixingLayout(mNativeHandle, layout);
	}

	private void adjustVideoLayout(Common.VideoMixingLayout.Region[] regionList, Common.VideoMixingLayout layout) {
		int number = 1;
		double maxResolutionWidth = 0.7;
		for (int i = 0; i < layoutUsers.size(); i++) {
			regionList[i] = layout.new Region();
			UserInfo user = layoutUsers.get(i);
			// 白板
			if (Constant.CCourse.ROLE.PAD == user.getType().longValue()) {
				regionList[i].uid = user.getUid();
				regionList[i].x = 0.0;
				regionList[i].y = 0.0;
				regionList[i].width = maxResolutionWidth;
				regionList[i].height = 1.0;
				regionList[i].renderMode = 0;
				continue;
			}
			// 主讲人
			if (Constant.CCourse.ROLE.LECTURER == user.getType().longValue()) {
				regionList[i].uid = user.getUid();
				regionList[i].x = maxResolutionWidth;
				regionList[i].y = 0.0;
				regionList[i].width = 1 - maxResolutionWidth;
				regionList[i].height = 0.5;
				regionList[i].renderMode = 0;
				continue;
			}
			// 互动人
			if (Constant.CCourse.ROLE.INTERACTOR == user.getType().longValue()) {
				if (number == 1) {
					regionList[i].x = maxResolutionWidth;
					regionList[i].y = 0.5;
				}
				if (number == 2) {
					regionList[i].x = maxResolutionWidth + 0.15;
					regionList[i].y = 0.5;
				}
				if (number == 3) {
					regionList[i].x = maxResolutionWidth;
					regionList[i].y = 0.5 + 0.25;
				}
				if (number == 4) {
					regionList[i].x = maxResolutionWidth + 0.15;
					regionList[i].y = 0.5 + 0.25;
				}
				regionList[i].uid = user.getUid();
				regionList[i].width = (1 - maxResolutionWidth) * 0.5;
				regionList[i].height = 0.25;
				regionList[i].renderMode = 0;
				number++;
				continue;
			}
		}
	}

	public int addUserVideoToCanvas(List<UserInfo> userInfoList) {
		for (UserInfo addUser : userInfoList) {
			for (UserInfo layoutUser : layoutUsers) {
				if (addUser.getType().longValue() == layoutUser.getType().longValue()) {
					if (layoutUser.getType().longValue() == Constant.CCourse.ROLE.PAD
							|| layoutUser.getType().longValue() == Constant.CCourse.ROLE.LECTURER) {
						layoutUser.setUid(addUser.getUid());
						break;
					}
					if (layoutUser.getType().longValue() == Constant.CCourse.ROLE.INTERACTOR
							&& layoutUser.getUid().longValue() == -1) {
						layoutUser.setUid(addUser.getUid());
						break;
					}
				}
			}

		}
		return beginVideoLayout();
	}

	public int removeUserVideoToCanvas(List<UserInfo> userInfoList) {
		layoutUsers.forEach(s -> {
			userInfoList.forEach(info -> {
				if (s.getUid().longValue() == info.getUid().longValue()) {
					s.setUid(-1l);
				}
			});
		});
		return beginVideoLayout();
	}

	private Vector<UserInfo> initVideoLayout() {
		Vector<UserInfo> m_peers = new Vector<UserInfo>();
		// 白板
		UserInfo whiteBoard = new UserInfo();
		whiteBoard.setType(Constant.CCourse.ROLE.PAD);
		whiteBoard.setUid(-1l);
		m_peers.add(whiteBoard);

		// 主讲人
		UserInfo master = new UserInfo();
		master.setType(Constant.CCourse.ROLE.LECTURER);
		master.setUid(-1l);
		m_peers.add(master);

		// 听课人
		for (int i = 0; i < 4; i++) {
			UserInfo listen = new UserInfo();
			listen.setType(Constant.CCourse.ROLE.INTERACTOR);
			listen.setUid(-1l);
			m_peers.add(listen);
		}

		return m_peers;
	}

}
