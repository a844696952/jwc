package com.yice.edu.cn.bmp.service.parent;

import cn.hutool.core.util.NumberUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.*;
import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.bmp.feignClient.parent.ParentFeign;
import com.yice.edu.cn.bmp.feignClient.weixin.WeixinFeign;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.parent.Parent;
import com.yice.edu.cn.common.pojo.jw.parent.ParentNameRelationship;
import com.yice.edu.cn.common.pojo.jw.parent.ParentStudent;
import com.yice.edu.cn.common.pojo.jw.parent.ParentStudentFile;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.weixin.Jscode2session;
import com.yice.edu.cn.common.pojo.ts.aliMsn.Msn;
import com.yice.edu.cn.common.pojo.ts.aliMsn.Sms;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Service
public class ParentService {
    @Autowired
    private ParentFeign parentFeign;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private WeixinFeign weixinFeign;


    @CreateCache(name = Constant.Redis.BMP_VERIFICATION_CODE, expire = 180)
    private Cache<String, String> codeCache;
    @CreateCache(name=Constant.Redis.BMP_TOKEN_CACHE,expire = Constant.Redis.BMP_PARENT_TIMEOUT, timeUnit = TimeUnit.DAYS)
    private Cache<String,String> tokenCache;
    @CreateCache(name=Constant.Redis.BMP_PARENT_CACHE,expire = Constant.Redis.BMP_PARENT_TIMEOUT, timeUnit = TimeUnit.DAYS)
    private Cache<String,Parent> parentCache;


    public Parent login(Parent parent) {
        return parentFeign.login(parent);
    }

    @Cached(name = Constant.Redis.BMP_TOKEN_CACHE,key = "#key",expire = Constant.Redis.BMP_PARENT_TIMEOUT,timeUnit = TimeUnit.DAYS)
    @CacheRefresh(timeUnit = TimeUnit.DAYS,refresh = 1,stopRefreshAfterLastAccess = Constant.Redis.BMP_PARENT_TIMEOUT,refreshLockTimeout = 60)
    public String findTokenByKey(String key) {
        return tokenCache.get(key);
    }

    public void saveParenToCache(Parent parent) {
        parentCache.PUT(parent.getId(), parent);
    }

    public List<Parent> findParentListByCondition(Parent parent) {
        return parentFeign.findParentListByCondition(parent);
    }

    public List<ParentStudent> findParentStudentListByCondition(ParentStudent parentStudent) {
        return parentFeign.findParentStudentListByCondition(parentStudent);
    }

    public void updateParent(Parent parent) {
        parentFeign.updateParent(parent);
    }

    public void updateParent1(Parent parent) {
        parentFeign.updateParent1(parent);
    }

    public long findParentCountByCondition(
            @ApiParam(value = "家长信息对象")
            @RequestBody Parent parent) {
        return parentFeign.findParentCountByCondition(parent);
    }

    public Parent saveParent(
            @ApiParam(value = "家长信息对象")
            @RequestBody Parent parent) {
        return parentFeign.saveParent(parent);
    }

    public ParentStudent saveParentStudent(
            @ApiParam(value = "家长孩子关联信息对象")
            @RequestBody ParentStudent parentStudent) {
        return parentFeign.saveParentStudent(parentStudent);
    }

    public void updatePassword(Parent parent) {
        parentFeign.updatePassword(parent);
    }

    public String getVerificationCode(String tel) {
        int[] num = NumberUtil.generateRandomNumber(100000, 999999, 1);
        String code = String.valueOf(num[0]);
        Map<String, String> msg = new HashMap<>();
        msg.put("code",code);
        msg.put("product","亿策教育");
        final Sms sms = new Sms(tel, Constant.MCS_SIGN_NAME.YCJD, Constant.MCS_TEMPLATE.SMS_VERIFICATION, msg);
        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.SMS_SEND_MSG, JSONUtil.toJsonStr(sms));
        codeCache.put(tel, code);
        return code;
    }

    //验证码检查
    @ApiOperation(value = "传入手机号，验证码", notes = "返回验证码是否正确")
    public boolean compareIdentifyingCode(
            @ApiParam(value = "手机号")
            @PathVariable String tel, @ApiParam(value = "验证码")
            @PathVariable String code) {
        String code1 = codeCache.get(tel);

        if (code.equals(code1)) {
            return true;
        }
        return false;
    }

    public void updateParentStudent(ParentStudent parentStudent) {
        parentFeign.updateParentStudent(parentStudent);
    }


    public Parent findParentById(String id) {
        return parentFeign.findParentById(id);
    }

    public void deleteParentStudentByCondition(ParentStudent parentStudent) {
        parentFeign.deleteParentStudentByCondition(parentStudent);
    }

    public void deleteParentStudentByParentId(ParentStudent parentStudent) {
        parentFeign.deleteParentStudentByParentId(parentStudent);
    }

    public List<Student> getBoundStudentList(ParentStudent parentStudent) {
        return parentFeign.getBoundStudentList(parentStudent);
    }

    public List<ParentStudentFile> getBoundStudentListInCenter(ParentStudent parentStudent) {
        return parentFeign.getBoundStudentListInCenter(parentStudent);
    }

    public void saveTokenToCache(String key, String token) {
        tokenCache.PUT(key, token);
    }

   // 绑定后亲属关系和家长姓名更新
   public void  updateRelationshipAndParentName (ParentNameRelationship parentNameRelationship){
       parentFeign.updateRelationshipAndParentName(parentNameRelationship);
   }

   public void bindOpenIdParent(Parent parent){
       parentFeign.bindOpenIdParent(parent);
   }

    /**
     * 微信获取openId使用
     * @param jscode2session
     * @return
     */
    public String jsCode2Session(Jscode2session jscode2session){
        return weixinFeign.jsCode2Session(jscode2session);
    }

    public List<ParentStudent> findSchoolByParentId(ParentStudent parentStudent) {
        return parentFeign.findSchoolByParentId(parentStudent);
    }

    public void deleteTokenToCache(String s) {
        tokenCache.remove(s);
    }
}
