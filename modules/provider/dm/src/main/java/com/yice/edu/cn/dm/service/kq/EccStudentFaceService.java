package com.yice.edu.cn.dm.service.kq;

import cn.hutool.core.date.DateUtil;
import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.Cached;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.dm.kq.EccStudentFace;
import com.yice.edu.cn.common.pojo.dm.kq.ProtectedStudent;
import com.yice.edu.cn.common.pojo.dm.studentAspect.DmStudentAspect;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassScheduleInit;
import com.yice.edu.cn.common.util.jmessage.utils.StringUtils;
import com.yice.edu.cn.dm.dao.studentAspect.IDmStudentAspectDao;
import com.yice.edu.cn.dm.feignClient.jw.classSchedule.ClassScheduleInitFeign;
import com.yice.edu.cn.dm.service.parentMsg.ParentmsgService;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @author dengfengfeng
 */
@Service
@Slf4j
public class EccStudentFaceService {

    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private IDmStudentAspectDao dmStudentAspectDao;
    @Autowired
    private EccStudentKqRecordService eccStudentKqRecordService;

    @Autowired
    private ParentmsgService parentmsgService;

    @Autowired
    private ClassScheduleInitFeign classScheduleInitFeign;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public EccStudentFace findEccStudentFaceById(String id) {
        return mot.findById(id, EccStudentFace.class);
    }

    public void saveEccStudentFace(EccStudentFace eccStudentFace) {
        //先删除历史记录
        mot.remove(query(where("studentId").is(eccStudentFace.getStudentId())), EccStudentFace.class);

        eccStudentFace.setId(sequenceId.nextId());
        eccStudentFace.setCreateTime(DateUtil.now());
        mot.insert(eccStudentFace);
    }

    public List<EccStudentFace> findEccStudentFaceListByCondition(EccStudentFace eccStudentFace) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(EccStudentFace.class)).find(MongoKit.buildMatchDocument(eccStudentFace));
        Pager pager = eccStudentFace.getPager();
        MongoKit.appendSort(query, pager);
        MongoKit.appendPage(query, pager);
        MongoKit.appendProjection(query, pager);
        List<EccStudentFace> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(EccStudentFace.class, document)));
        return list;
    }

    public long findEccStudentFaceCountByCondition(EccStudentFace eccStudentFace) {
        return mot.getCollection(mot.getCollectionName(EccStudentFace.class)).count(MongoKit.buildMatchDocument(eccStudentFace));
    }

    public EccStudentFace findOneEccStudentFaceByCondition(EccStudentFace eccStudentFace) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(EccStudentFace.class)).find(MongoKit.buildMatchDocument(eccStudentFace));
        MongoKit.appendProjection(query, eccStudentFace.getPager());
        return mot.getConverter().read(EccStudentFace.class, query.first());
    }

    public void updateEccStudentFace(EccStudentFace eccStudentFace) {
        mot.updateFirst(query(where("id").is(eccStudentFace.getId())), MongoKit.update(eccStudentFace), EccStudentFace.class);
    }

    public void deleteEccStudentFace(String id) {
        mot.remove(query(where("id").is(id)), EccStudentFace.class);
        //删除人脸特征模块
        DmStudentAspect dmStudentAspect = new DmStudentAspect();
        dmStudentAspect.setFaceId(id);
        dmStudentAspect.setPager(new Pager());
        dmStudentAspectDao.deleteDmStudentAspectByCondition(dmStudentAspect);
    }

    /**
     * 根据schoolId删除当前人脸和人脸特征
     * @param schoolId
     */
    public void batchDeleteEccStudentFace(String schoolId){
        if(StringUtils.isNotEmpty(schoolId)){
            mot.remove(query(where("schoolId").is(schoolId)),EccStudentFace.class);
            //删除人脸特征
            DmStudentAspect dmStudentAspect = new DmStudentAspect();
            dmStudentAspect.setSchoolId(schoolId);
            dmStudentAspect.setPager(new Pager());
            dmStudentAspectDao.deleteDmStudentAspectByCondition(dmStudentAspect);

        }
    }

    /**
     * 删除考勤数据
     * @param schoolId
     */
    public void deleteDmKqData(String schoolId){
        if(StringUtils.isNotEmpty(schoolId)){
            eccStudentKqRecordService.deleteEccKqRecord(schoolId);
            batchDeleteEccStudentFace(schoolId);
            parentmsgService.deleteParentMsgBySchoolId(schoolId);
            log.info(String.format("升班删除电子班牌考勤历史数据:schoolId=%1$s",schoolId));
        }
    }


    /**
     * 缓存课表信息
     * @param schoolId
     * @return
     */
    @Cached(name = Constant.Redis.DM_COURSE_INFO,key = "#schoolId",expire = 3600*3)
    public List<ClassScheduleInit> getClassScheduleInitsForCache(String schoolId){
        ProtectedStudent protectedStudent=new ProtectedStudent();
        protectedStudent.setSchoolId(schoolId);
        ClassScheduleInit scheduleInit = getClassScheduleInit(protectedStudent);
        return classScheduleInitFeign.findListClassScheduleInitBySchool(scheduleInit);
    }

    @CacheInvalidate(name = Constant.Redis.DM_COURSE_INFO,key = "#schoolId")
    public void cacheInvalidateSchoolCourse(String schoolId){

    }



    public void batchDeleteEccKqRecord(String schoolId){
        eccStudentKqRecordService.deleteEccKqRecord(schoolId);
    }

    private ClassScheduleInit getClassScheduleInit(ProtectedStudent protectedStudent) {
        ClassScheduleInit scheduleInit = new ClassScheduleInit();
        scheduleInit.setSchoolId(protectedStudent.getSchoolId());
        Pager pager = getPager();
        scheduleInit.setPager(pager);
        return scheduleInit;
    }

    private Pager getPager() {
        Pager pager = new Pager();
        pager.setSortOrder("asc");
        pager.setSortField("number");
        pager.setPaging(false);
        return pager;
    }


    public void deleteEccStudentFaceByCondition(EccStudentFace eccStudentFace) {
        mot.getCollection(mot.getCollectionName(EccStudentFace.class)).deleteMany(MongoKit.buildMatchDocument(eccStudentFace));
    }


    public void batchSaveEccStudentFace(List<EccStudentFace> eccStudentFaces) {
        eccStudentFaces.forEach(eccStudentFace -> {
                    eccStudentFace.setId(sequenceId.nextId());
                    eccStudentFace.setCreateTime(DateUtil.now());
                }
        );
        mot.insertAll(eccStudentFaces);
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

}
