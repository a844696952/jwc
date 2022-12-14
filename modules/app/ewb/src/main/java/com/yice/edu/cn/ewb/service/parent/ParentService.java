package com.yice.edu.cn.ewb.service.parent;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.parent.Parent;
import com.yice.edu.cn.common.pojo.jw.parent.ParentNameRelationship;
import com.yice.edu.cn.common.pojo.jw.parent.ParentStudent;
import com.yice.edu.cn.common.pojo.jw.parent.ParentStudentFile;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.ts.aliMsn.Msn;
import com.yice.edu.cn.ewb.feignClient.parent.ParentFeign;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
public class ParentService {
    @Autowired
    private ParentFeign parentFeign;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @CreateCache(name = Constant.Redis.BMP_VERIFICATION_CODE, expire = 180)
    private Cache<String, String> codeCache;
    @CreateCache(name=Constant.Redis.BMP_TOKEN_CACHE,expire = Constant.Redis.BMP_PARENT_TIMEOUT, timeUnit = TimeUnit.DAYS)
    private Cache<String,String> tokenCache;
    @CreateCache(name=Constant.Redis.BMP_PARENT_CACHE,expire = Constant.Redis.BMP_PARENT_TIMEOUT, timeUnit = TimeUnit.DAYS)
    private Cache<String,Parent> parentCache;


    public Parent login(Parent parent) {
        return parentFeign.login(parent);
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
            @ApiParam(value = "??????????????????")
            @RequestBody Parent parent) {
        return parentFeign.findParentCountByCondition(parent);
    }

    public Parent saveParent(
            @ApiParam(value = "??????????????????")
            @RequestBody Parent parent) {
        return parentFeign.saveParent(parent);
    }

    public ParentStudent saveParentStudent(
            @ApiParam(value = "??????????????????????????????")
            @RequestBody ParentStudent parentStudent) {
        return parentFeign.saveParentStudent(parentStudent);
    }

    public void updatePassword(Parent parent) {
        parentFeign.updatePassword(parent);
    }

    public String getVerificationCode(String tel) {
        int[] num = NumberUtil.generateRandomNumber(100000, 999999, 1);
        String code = String.valueOf(num[0]);
        Msn msn = new Msn(tel, code, "????????????");
        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.AUTH_IDENTIFY, JSONUtil.toJsonStr(msn));
        codeCache.put(tel, code);
        return code;
    }

    //???????????????
    @ApiOperation(value = "???????????????????????????", notes = "???????????????????????????")
    public boolean compareIdentifyingCode(
            @ApiParam(value = "?????????")
            @PathVariable String tel, @ApiParam(value = "?????????")
            @PathVariable String code) {
        String code1 = codeCache.get(tel);

        if (code.equals(code1)) {
            codeCache.remove(tel);
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

    public List<Student> getBoundStudentList(ParentStudent parentStudent) {
        return parentFeign.getBoundStudentList(parentStudent);
    }

    public List<ParentStudentFile> getBoundStudentListInCenter(ParentStudent parentStudent) {
        return parentFeign.getBoundStudentListInCenter(parentStudent);
    }

    public void saveTokenToCache(String key, String token) {
        tokenCache.PUT(key, token);
    }

   // ??????????????????????????????????????????
   public void  updateRelationshipAndParentName (ParentNameRelationship parentNameRelationship){
       parentFeign.updateRelationshipAndParentName(parentNameRelationship);
   }
}
