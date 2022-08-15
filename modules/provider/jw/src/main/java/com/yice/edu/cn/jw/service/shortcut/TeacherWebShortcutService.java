package com.yice.edu.cn.jw.service.shortcut;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.shortcut.TeacherWebShortcut;
import com.yice.edu.cn.common.pojo.jw.shortcut.WebShortcut;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class TeacherWebShortcutService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;

    @CreateCache(name = "teacher_web_shortcut_", expire = 10000)
    private Cache<String, WebShortcut> teacherWebShortcutCache;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public TeacherWebShortcut findTeacherWebShortcutById(String id) {
        return mot.findById(id, TeacherWebShortcut.class);
    }

    public void saveTeacherWebShortcut(TeacherWebShortcut teacherWebShortcut) {
        teacherWebShortcut.setId(sequenceId.nextId());
        mot.insert(teacherWebShortcut);
    }

    public List<TeacherWebShortcut> findTeacherWebShortcutListByCondition(TeacherWebShortcut teacherWebShortcut) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(TeacherWebShortcut.class)).find(MongoKit.buildMatchDocument(teacherWebShortcut));
        Pager pager = teacherWebShortcut.getPager();
        MongoKit.appendSort(query, pager);
        MongoKit.appendPage(query, pager);
        MongoKit.appendProjection(query, pager);
        List<TeacherWebShortcut> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(TeacherWebShortcut.class, document)));
        return list;
    }

    public long findTeacherWebShortcutCountByCondition(TeacherWebShortcut teacherWebShortcut) {
        return mot.getCollection(mot.getCollectionName(TeacherWebShortcut.class)).countDocuments(MongoKit.buildMatchDocument(teacherWebShortcut));
    }

    public TeacherWebShortcut findOneTeacherWebShortcutByCondition(TeacherWebShortcut teacherWebShortcut) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(TeacherWebShortcut.class)).find(MongoKit.buildMatchDocument(teacherWebShortcut));
        MongoKit.appendProjection(query, teacherWebShortcut.getPager());
        return mot.getConverter().read(TeacherWebShortcut.class, query.first());
    }

    public void updateTeacherWebShortcut(TeacherWebShortcut teacherWebShortcut) {
        teacherWebShortcutCache.remove(teacherWebShortcut.getTeacherId());
        mot.updateFirst(query(where("id").is(teacherWebShortcut.getId())), MongoKit.update(teacherWebShortcut, true), TeacherWebShortcut.class);
    }

    public void updateTeacherWebShortcutForNotNull(TeacherWebShortcut teacherWebShortcut) {
        mot.updateFirst(query(where("id").is(teacherWebShortcut.getId())), MongoKit.update(teacherWebShortcut, false), TeacherWebShortcut.class);
    }

    public void deleteTeacherWebShortcut(String id) {
        mot.remove(query(where("id").is(id)), TeacherWebShortcut.class);
    }

    public void deleteTeacherWebShortcutByCondition(TeacherWebShortcut teacherWebShortcut) {
        mot.getCollection(mot.getCollectionName(TeacherWebShortcut.class)).deleteMany(MongoKit.buildMatchDocument(teacherWebShortcut));
    }

    public void batchSaveTeacherWebShortcut(List<TeacherWebShortcut> teacherWebShortcuts) {
        teacherWebShortcuts.forEach(teacherWebShortcut -> teacherWebShortcut.setId(sequenceId.nextId()));
        mot.insertAll(teacherWebShortcuts);
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public void updateTeacherWebShortcut4List(String teacherId, List<TeacherWebShortcut> teacherWebShortcutList) {
        TeacherWebShortcut del = new TeacherWebShortcut();
        del.setTeacherId(teacherId);
        this.deleteTeacherWebShortcutByCondition(del);
        teacherWebShortcutList.forEach(teacherWebShortcut -> {
            teacherWebShortcut.setId(sequenceId.nextId());
            teacherWebShortcut.setTeacherId(teacherId);
        });
        mot.insertAll(teacherWebShortcutList);
    }


}
