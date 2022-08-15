package com.yice.edu.cn.common.util.jmessage.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ts.jMessage.IMReturnResult;
import com.yice.edu.cn.common.pojo.ts.jMessage.UserRigisterApiResult;
import com.yice.edu.cn.common.pojo.ts.jMessage.pojo.UserRegister;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.util.jmessage.JMessageClient;
import com.yice.edu.cn.common.util.jmessage.common.model.RegisterInfo;
import com.yice.edu.cn.common.util.jmessage.common.model.RegisterInfo.Builder;
import com.yice.edu.cn.common.util.jmessage.common.model.UserPayload;
import com.yice.edu.cn.common.util.jmessage.user.UserInfoResult;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;

public class JMessageUserApi {

	private static final Logger logger = LoggerFactory.getLogger(JMessageUserApi.class);

	private static JMessageClient bmpUserClient = new JMessageClient(JpushApp.BMP.getKey(), JpushApp.BMP.getSecret());
	private static JMessageClient tapUserClient = new JMessageClient(JpushApp.TAP.getKey(), JpushApp.TAP.getSecret());

	private static JMessageClient getJMessageClient(int type) throws RuntimeException {
		switch (type) {
		case Constant.APP_TYPE.BMP_TYPE:
			return bmpUserClient;
		case Constant.APP_TYPE.TAP_TYPE:
			return tapUserClient;
		default:
			throw new RuntimeException("this type is not found");
		}
	}

	/**
	 * 批量注册IM用户
	 * 
	 * @param userRegisterList
	 * @param type             注册类型 1.教师APP 2.家长APP Constant.APP_TYPE
	 * @return
	 */
	public static IMReturnResult<List<UserRigisterApiResult>> registerJMessageUsers(List<UserRegister> userRegisterList,
			int type) {

		Assert.notEmpty(userRegisterList);

		List<RegisterInfo> users = new ArrayList<RegisterInfo>();
		for (UserRegister userRegister : userRegisterList) {
			Builder builder = RegisterInfo.newBuilder().setUsername(userRegister.getUserName())
					.setPassword(userRegister.getPassword()).setNickname(userRegister.getShowName());
			if (userRegister.getSex() != null) {
				builder.setGender(userRegister.getSex());
			}
			if (userRegister.getDefaultHeadImage() != null) {
				builder.addExtras(updateExtra(type + "", userRegister.getDefaultHeadImage()));
			}
			users.add(builder.build());
		}
		RegisterInfo[] regUsers = new RegisterInfo[users.size()];
		Gson gson = new Gson();
		try {
			JMessageClient client = getJMessageClient(type);
			String res = client.registerUsers(users.toArray(regUsers));
			return new IMReturnResult<List<UserRigisterApiResult>>(
					gson.fromJson(res, new TypeToken<List<UserRigisterApiResult>>() {
					}.getType()));
		} catch (APIConnectionException e) {
			logger.error("Connection error. Should retry later. ", e);
			return new IMReturnResult<List<UserRigisterApiResult>>("10001", e.getMessage());
		} catch (APIRequestException e) {
			logger.error("Error response from JPush server. Should review and fix it. ", e);
			logger.error(" HTTP Status: " + e.getStatus() + " Error Message: " + e.getMessage());
			List<UserRigisterApiResult> listData = gson.fromJson(e.getMessage(),
					new TypeToken<List<UserRigisterApiResult>>() {
					}.getType());
			listData = listData.stream().filter(userRigisterApiResult -> userRigisterApiResult.getErrorCode() == 899001)
					.collect(Collectors.toList());
			if (!listData.isEmpty()) {
				return new IMReturnResult<List<UserRigisterApiResult>>(listData);
			}
			return new IMReturnResult<List<UserRigisterApiResult>>("10001", e.getMessage());
		} catch (Exception e) {
			logger.error("Error Message: " + e.getMessage());
			return new IMReturnResult<List<UserRigisterApiResult>>("10001", e.getMessage());
		}
	}

	/**
	 * 注册IM用户
	 * 
	 * @param userRegister
	 * @param type         注册类型 1.教师APP 2.家长APP Constant.APP_TYPE
	 * @return
	 */
	public static IMReturnResult<List<UserRigisterApiResult>> registerJMessageUser(UserRegister userRegister,
			int type) {
		Assert.notNull(userRegister.getUserName(),"UserName is null");
		Assert.notNull(userRegister.getPassword(),"Password is null");
		Assert.notNull(userRegister.getShowName(),"ShowName is null");

		Gson gson = new Gson();
		try {
			List<RegisterInfo> userList = new ArrayList<RegisterInfo>();
			Builder userBuilder = RegisterInfo.newBuilder();
			userBuilder.setUsername(userRegister.getUserName());
			userBuilder.setPassword(userRegister.getPassword());
			userBuilder.setNickname(userRegister.getShowName());
			if (userRegister.getSex() != null) {
				userBuilder.setGender(userRegister.getSex());
			}
			if (StrUtil.isNotEmpty(userRegister.getDefaultHeadImage())) {
				userBuilder.addExtras(updateExtra(type + "", userRegister.getDefaultHeadImage()));
			}

			JMessageClient client = getJMessageClient(type);
			userList.add(userBuilder.build());

			RegisterInfo[] regUsers = new RegisterInfo[userList.size()];
			String res = client.registerUsers(userList.toArray(regUsers));
			List<UserRigisterApiResult> resultList = gson.fromJson(res, new TypeToken<List<UserRigisterApiResult>>() {
			}.getType());

			return new IMReturnResult<List<UserRigisterApiResult>>(resultList);
		} catch (APIConnectionException e) {
			logger.error("Connection error. Should retry later. ", e);
			return new IMReturnResult<List<UserRigisterApiResult>>("10001", e.getMessage());
		} catch (APIRequestException e) {
			logger.error("Error response from JPush server. Should review and fix it. ", e);
			logger.error(" HTTP Status: " + e.getStatus() + " Error Message: " + e.getMessage());
			List<UserRigisterApiResult> listData = gson.fromJson(e.getMessage(),
					new TypeToken<List<UserRigisterApiResult>>() {
					}.getType());
			listData = listData.stream().filter(userRigisterApiResult -> userRigisterApiResult.getErrorCode() == 899001)
					.collect(Collectors.toList());
			if (!listData.isEmpty()) {
				return new IMReturnResult<List<UserRigisterApiResult>>(listData);
			}
			return new IMReturnResult<List<UserRigisterApiResult>>("10001", e.getMessage());
		} catch (Exception e) {
			logger.error("Error Message: " + e.getMessage());
			return new IMReturnResult<List<UserRigisterApiResult>>("10001", e.getMessage());
		}
	}

	/**
	 * 使用本地图片更新用户头像
	 * 
	 * @param userName 登陆用户名
	 * @param path     本地图片路径
	 * @param type     注册类型 1.教师APP 2.家长APP Constant.APP_TYPE
	 * @return
	 */
//	public static IMReturnResult updateUserImg(String userName,String path,int type) {
//		
//    	Assert.notNull(userName);
//    	Assert.notNull(path);
//        
//        try {
//            JMessageClient client = getJMessageClient(type);
//        	UploadResult result = client.uploadFile(path, "image");
//        	if (null == result || !result.isResultOK()) {
//        		logger.error("image upload failed",result.getOriginalContent());
//            	return new IMReturnResult("10001","上传图片失败");
//            }
//        	
//            UserPayload payload = UserPayload.newBuilder()
//                    .setAvatar(result.getMediaId())
//                    .build();
//
//            client.updateUserInfo(userName, payload);
//            return new IMReturnResult();
//        } catch (APIConnectionException e) {
//        	logger.error("Connection error. Should retry later. ", e);
//        	return new IMReturnResult("10001","网络连接错误");
//        } catch (APIRequestException e) {
//        	logger.error("Error response from JPush server. Should review and fix it. ", e);
//        	logger.info("HTTP Status: " + e.getStatus());
//            logger.info("Error Message: " + e.getMessage());
//            return new IMReturnResult("10001",e.getMessage());
//        }catch (Exception e) {
//            logger.info("Error Message: " + e.getMessage());
//            return new IMReturnResult("10001",e.getMessage());
//        }
//        
//	}

	/**
	 * 使用http地址图片更新用户头像
	 * 
	 * @param userName IM用户账号
	 * @param httpPath 图片http路径
	 * @param type     注册类型 1.教师APP 2.家长APP Constant.APP_TYPE
	 * @return
	 */
//	public static IMReturnResult updateUserImgByRemote(String userName,String httpPath,int type) {
//    	
//    	Assert.notNull(userName);
//    	Assert.notNull(httpPath);
//		try {
//            JMessageClient client = getJMessageClient(type);
//        	UploadResult result = client.uploadFileByRemoteImage(httpPath, "image");
//        	if (null == result || !result.isResultOK()) {
//        		logger.error("image upload failed",result.getOriginalContent());
//            	return new IMReturnResult("10001","上传图片失败");
//            }
//            UserPayload payload = UserPayload.newBuilder()
//                    .setAvatar(result.getMediaId())
//                    .build();
//            client.updateUserInfo(userName, payload);
//            return new IMReturnResult();
//        } catch (APIConnectionException e) {
//        	logger.error("Connection error. Should retry later. ", e);
//        	return new IMReturnResult("10001","网络连接错误");
//        } catch (APIRequestException e) {
//        	logger.error("Error response from JPush server. Should review and fix it. ", e);
//        	logger.info("HTTP Status: " + e.getStatus());
//            logger.info("Error Message: " + e.getMessage());
//            return new IMReturnResult("10001",e.getMessage());
//        } catch (Exception e) {
//            logger.info("Error Message: " + e.getMessage());
//            return new IMReturnResult("10001",e.getMessage());
//        }
//	}

	/**
	 * 使用http地址图片更新用户头像
	 * 
	 * @param userName IM用户账号
	 * @param httpPath 图片http路径
	 * @param type     注册类型 1.教师APP 2.家长APP Constant.APP_TYPE
	 * @return
	 */
	public static IMReturnResult updateUser(UserRegister userInfo, int type) {
		Assert.notNull(userInfo.getUserName());
		try {
			JMessageClient client = getJMessageClient(type);
			UserPayload.Builder payload = UserPayload.newBuilder();
			if (StrUtil.isNotEmpty(userInfo.getShowName())) {
				payload.setNickname(userInfo.getShowName());
			}
			if (userInfo.getSex() != null) {
				payload.setGender(userInfo.getSex());
			}
			if (StrUtil.isNotEmpty(userInfo.getDefaultHeadImage())) {
				payload.addExtras(updateExtra(type + "", userInfo.getDefaultHeadImage()));
			}
			client.updateUserInfo(userInfo.getUserName(), payload.build());
			return new IMReturnResult();
		} catch (APIConnectionException e) {
			logger.error("Connection error. Should retry later. ", e);
			return new IMReturnResult("10001", "网络连接错误");
		} catch (APIRequestException e) {
			logger.error("Error response from JPush server. Should review and fix it. ", e);
			logger.error("HTTP Status: " + e.getStatus());
			logger.error("Error Message: " + e.getMessage());
			return new IMReturnResult("10001", e.getMessage());
		} catch (Exception e) {
			logger.error("Error Message: " + e.getMessage());
			return new IMReturnResult("10001", e.getMessage());
		}
	}

	/**
	 * 获取IM用户信息
	 * 
	 * @param userName 用户登陆名
	 * @param type     注册类型 1.教师APP 2.家长APP Constant.APP_TYPE
	 */
	public static IMReturnResult<UserInfoResult> findRegisterUserInfo(String userName, int type) {
		Assert.notNull(userName);
		try {
			JMessageClient client = getJMessageClient(type);
			UserInfoResult userInfoResult = client.getUserInfo(userName);
			return new IMReturnResult<UserInfoResult>(userInfoResult);
		} catch (APIConnectionException e) {
			logger.error("Connection error. Should retry later. ", e);
			return new IMReturnResult<UserInfoResult>("10001", "网络连接错误");
		} catch (APIRequestException e) {
			logger.error("Error response from JPush server. Should review and fix it. ", e);
			logger.error("HTTP Status: " + e.getStatus());
			logger.error("Error Message: " + e.getMessage());
			return new IMReturnResult<UserInfoResult>("10001", e.getMessage());
		} catch (Exception e) {
			logger.error("Error Message: " + e.getMessage());
			return new IMReturnResult<UserInfoResult>("10001", e.getMessage());
		}

	}

	/**
	 * 更新用户的昵称
	 * 
	 * @param userName   IM的用户登录账号
	 * @param updateName 新昵称
	 * @param type       注册类型 1.教师APP 2.家长APP Constant.APP_TYPE
	 * @return
	 */
	public static IMReturnResult updateUserNickName(String userName, String updateName, int type) {

		Assert.notNull(userName);
		Assert.notNull(updateName);
		try {
			JMessageClient client = getJMessageClient(type);
			UserPayload payload = UserPayload.newBuilder().setNickname(updateName).build();
			client.updateUserInfo(userName, payload);
			return new IMReturnResult();
		} catch (APIConnectionException e) {
			logger.error("Connection error. Should retry later. ", e);
			return new IMReturnResult("10001", "网络连接错误");
		} catch (APIRequestException e) {
			logger.error("Error response from JPush server. Should review and fix it. ", e);
			logger.error("HTTP Status: " + e.getStatus());
			logger.error("Error Message: " + e.getMessage());
			return new IMReturnResult("10001", e.getMessage());
		} catch (Exception e) {
			logger.error("Error Message: " + e.getMessage());
			return new IMReturnResult("10001", e.getMessage());
		}
	}

	/**
	 * 更新极光IM的自定义参数(重新覆盖)
	 * 
	 * @param build
	 * @param userType
	 * @param defaultImage
	 */
	private static Map<String, String> updateExtra(String userType, String defaultImage) {
		Assert.notNull(userType);
		Assert.notNull(defaultImage);

		Map<String, String> extraMap = new HashMap<String, String>();
		extraMap.put("defaultImage", defaultImage);
		extraMap.put("userType", userType);

		return extraMap;
	}

	public static void main(String[] args) {
//		updateUserNickName("2133443881015795715","慕容飞改5",1);
		List<UserRegister> userRegisterList = new ArrayList<UserRegister>();
//			 UserRegister user = new UserRegister();
//			 user.setUserName("122222222222");
//			 user.setPassword("12345678");
//			 user.setShowName("哈哈");
//			 userRegisterList.add(user);

		UserRegister user2 = new UserRegister();
		user2.setUserName("235543019342249165010234");
		 user2.setPassword("12345678");
		user2.setShowName("哈哈12");
		user2.setSex(1);
		user2.setDefaultHeadImage("http://www.pptbz.com/pptpic/UploadFiles_6909/201203/2012031220134655123.jpg");
//			 userRegisterList.add(user2);
//		JMessageUserApi.registerJMessageUser(user2, Constant.APP_TYPE.TAP_TYPE);
			 JMessageUserApi.updateUser(user2, Constant.APP_TYPE.TAP_TYPE);
//			 JMessageUserApi.registerJMessageUsers(userRegisterList, Constant.APP_TYPE.TAP_TYPE);

//		IMReturnResult<List<UserRigisterApiResult>> result  = JMessageUserApi.registerJMessageUsers(userRegisterList, Constant.APP_TYPE.TAP_TYPE);

//		System.out.println(result.toString());

//		 IMReturnResult result = JMessageUserApi.updateUserImgByRemote("yc_tearcher_wen123","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541762366548&di=4a31f990ebd47ffe178fd1babc18dc15&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2Fb7fd5266d016092444ec7239de0735fae7cd34c3.jpg", Constant.APP_TYPE.TAP_TYPE);

		IMReturnResult<UserInfoResult> resulthaha = JMessageUserApi.findRegisterUserInfo("235543019342249165010234",
				Constant.APP_TYPE.TAP_TYPE);
		System.out.println(123);
//		 UserRegister user2 = new UserRegister();
//		 user2.setUserName("1753688335901876224");
//		 user2.setPassword("1753688335901876224");
//		 user2.setShowName("哈哈1");
//		JMessageUserApi.registerJMessageUser(user2, Constant.APP_TYPE.TAP_TYPE);
//		user2.setHeadImg("/upload/avatar/2018/1107/4ea2415267314b81.jpg");
//		JMessageUserApi.updateUserImgByRemote("1753688335901876224", "http:"+"//pd4ng388a.bkt.clouddn.com"+user2.getHeadImg(), Constant.APP_TYPE.TAP_TYPE);

	}

}
