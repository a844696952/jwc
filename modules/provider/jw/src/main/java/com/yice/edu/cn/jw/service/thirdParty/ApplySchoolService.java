package com.yice.edu.cn.jw.service.thirdParty;

import cn.hutool.core.date.DateUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.api.thirdParty.ApplySchool;
import com.yice.edu.cn.common.pojo.jw.shortcut.TeacherWebShortcut;
import com.yice.edu.cn.common.pojo.jw.shortcut.WebShortcut;
import com.yice.edu.cn.jw.service.shortcut.TeacherWebShortcutService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplySchoolService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private ApplySchoolTeacherService applySchoolTeacherService;
    @Autowired
    private TeacherWebShortcutService teacherWebShortcutService;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public ApplySchool findApplySchoolById(String id) {
        return mot.findById(id, ApplySchool.class);
    }


    public void saveApplySchool(List<ApplySchool> list) {
        List<ApplySchool> applySchoolList = new ArrayList();

        list.parallelStream().forEach(as -> {
            ApplySchool applySchool = new ApplySchool();
            applySchool.setApplyName(as.getApplyName());
            applySchool.setApplyId(as.getApplyId());
            applySchool.setSchoolId(as.getSchoolId());
            //查找是否已经存在，有则添加，反之根据id修改
            ApplySchool aps = findOneApplySchoolByCondition(applySchool);
            if (aps != null) {
                as.setId(aps.getId());
                as.setCreateTime(aps.getCreateTime());
                updateApplySchool(as);

                TeacherWebShortcut teacherWebShortcut = new TeacherWebShortcut();
                teacherWebShortcut.setTitle(as.getApplyName());
                teacherWebShortcut.setPermId(as.getApplyId());
                teacherWebShortcut.setSchoolId(as.getSchoolId());

                List<TeacherWebShortcut> teacherList = teacherWebShortcutService.findTeacherWebShortcutListByCondition(teacherWebShortcut);
                if (teacherList != null && teacherList.size() != 0) {
                    teacherList.stream().forEach(skt -> {
                        skt.setExpireDate(as.getExpireTime());
                        teacherWebShortcutService.updateTeacherWebShortcut(skt);
                    });
                }
            } else {
                applySchoolList.add(as);
            }
        });
        if (applySchoolList != null && applySchoolList.size() != 0) {
            applySchoolList.stream().forEach(skt -> {
                skt.setCreateTime(DateUtil.now());
                skt.setId(sequenceId.nextId());
            });
            mot.insertAll(applySchoolList);
        }
    }

    public List<ApplySchool> findApplySchoolListByCondition(ApplySchool applySchool) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(ApplySchool.class)).find(MongoKit.buildMatchDocument(applySchool));
        Pager pager = applySchool.getPager();
        MongoKit.appendSort(query, pager);
        MongoKit.appendPage(query, pager);
        MongoKit.appendProjection(query, pager);
        List<ApplySchool> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(ApplySchool.class, document)));
        return list;
    }

    public long findApplySchoolCountByCondition(ApplySchool applySchool) {
        return mot.getCollection(mot.getCollectionName(ApplySchool.class)).countDocuments(MongoKit.buildMatchDocument(applySchool));
    }

    public ApplySchool findOneApplySchoolByCondition(ApplySchool applySchool) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(ApplySchool.class)).find(MongoKit.buildMatchDocument(applySchool));
        MongoKit.appendProjection(query, applySchool.getPager());
        return mot.getConverter().read(ApplySchool.class, query.first());
    }

    public void updateApplySchool(ApplySchool applySchool) {
        mot.getCollection(mot.getCollectionName(ApplySchool.class)).updateOne(new Document("_id", applySchool.getId()), MongoKit.buildUpdateDocument(applySchool, false));
    }

    public void updateApplySchoolForAll(ApplySchool applySchool) {
        mot.getCollection(mot.getCollectionName(ApplySchool.class)).updateOne(new Document("_id", applySchool.getId()), MongoKit.buildUpdateDocument(applySchool, true));
    }

    public void deleteApplySchool(String id) {
        mot.getCollection(mot.getCollectionName(ApplySchool.class)).deleteOne(new Document("_id", id));
    }

    public void deleteApplySchoolByCondition(ApplySchool applySchool) {
        mot.getCollection(mot.getCollectionName(ApplySchool.class)).deleteMany(MongoKit.buildMatchDocument(applySchool));
    }

    public void batchSaveApplySchool(List<ApplySchool> applySchools) {
        applySchools.forEach(applySchool -> applySchool.setId(sequenceId.nextId()));
        mot.insertAll(applySchools);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
