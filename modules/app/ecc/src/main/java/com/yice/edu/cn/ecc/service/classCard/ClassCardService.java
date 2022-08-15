package com.yice.edu.cn.ecc.service.classCard;

import com.alibaba.fastjson.JSONObject;
import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.Cached;
import com.sun.org.apache.xalan.internal.lib.ExsltBase;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.util.jmessage.utils.StringUtils;
import com.yice.edu.cn.common.util.jwt.JwtUtil;
import com.yice.edu.cn.ecc.feignClient.classCard.ClassCardFeign;
import com.yice.edu.cn.ecc.service.school.SchoolService;
import com.yice.edu.cn.ecc.service.school.SchoolYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ClassCardService {
    @Autowired
    private ClassCardFeign classCardFeign;
    @Autowired
    private SchoolService schoolService;

    @Autowired
    private SchoolYearService schoolYearService;

    public List<DmClassCard> findDmClassCardUser(DmClassCard dmClassCard) {
        return classCardFeign.findDmClassCardUser(dmClassCard);
    }

    public void dmClassCardStatus(DmClassCard dmClassCard) {
         classCardFeign.dmClassCardStatus(dmClassCard);
    }
    
    public ResponseJson checkToken(String token){
        if(StringUtils.isNotEmpty(token)){
            try{
                JSONObject subject = JSONObject.parseObject(JwtUtil.parseJWT(token).getSubject()) ;
                if(Objects.nonNull(subject) && subject.size()>0 && StringUtils.isNotEmpty(subject.getString("schoolId"))){
                    CurSchoolYear currentSchoolYear = schoolYearService.findCurSchoolYear(subject.getString("schoolId"));
                    if(Objects.nonNull(currentSchoolYear) && StringUtils.isNotEmpty(currentSchoolYear.getSchoolYearId())){
                        ResponseJson schoolYear = schoolService.findSchoolExpireOrSchoolYear(subject.getString("schoolId"));
                        if(!schoolYear.getResult().isSuccess()){
                            return schoolYear;
                        }
                        if(!Objects.equals(currentSchoolYear.getSchoolYearId(),subject.get("schoolYearId"))){
                            return new ResponseJson(false,2005,"重新登录");
                        }
                    }
                }else{
                    return new ResponseJson(false,2005,"重新登录");
                }
            }catch (Exception ex){
                return new ResponseJson(false,2005,"重新登录");
            }
        }else{
            return new ResponseJson(false,2005);
        }
        return new ResponseJson(true,200);
    }
    


    public long findDmClassCardCountByCondition(DmClassCard dmClassCard) {
        return classCardFeign.findDmClassCardCountByCondition(dmClassCard);
    }
    public DmClassCard findOneDmClassCardByCondition(DmClassCard dmClassCard){
        return classCardFeign.findOneDmClassCardByCondition(dmClassCard);
    }
    public void updateDmClassCard(DmClassCard dmClassCard) {
        classCardFeign.updateDmClassCard(dmClassCard);
    }

    public DmClassCard findDmClassMsgCardById(String id){
        return classCardFeign.findDmClassMsgCardById(id);
    }
    @Cached(name = Constant.Redis.ECC_LOGIN,key = "#username",expire = 3600*3)
    public DmClassCard findClassCardForCache(String username){
        DmClassCard dmClassCard = new DmClassCard();
        dmClassCard.setUserName(username);
        return classCardFeign.findOneDmClassCardByCondition(dmClassCard);
    }
    @CacheInvalidate(name = Constant.Redis.ECC_LOGIN,key = "#username")
    public void cacheInvalidateDmClassCard(String username){}


    public void batchEccStudentFace(String schoolId){
        classCardFeign.batchEccStudentFace(schoolId);
    }

    public void deleteParentMsgBySchoolId(String schoolId){
        classCardFeign.deleteParentMsgBySchoolId(schoolId);
    }

    public void batchDeleteEccKqRecord(String schoolId){
        classCardFeign.batchDeleteEccKqRecord(schoolId);
    }
}
