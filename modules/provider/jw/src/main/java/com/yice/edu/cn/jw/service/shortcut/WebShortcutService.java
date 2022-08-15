package com.yice.edu.cn.jw.service.shortcut;

import cn.hutool.core.date.DateUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.Cached;
import com.alicp.jetcache.anno.CreateCache;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
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
public class WebShortcutService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @CreateCache(name = "shortcut_list_", expire = 10000)
    private Cache<String, WebShortcut> webShortcutCache;
    @CreateCache(name = "teacher_web_shortcut_", expire = 10000)
    private Cache<String, WebShortcut> teacherWebShortcutCache;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public WebShortcut findWebShortcutById(String id) {
        return mot.findById(id, WebShortcut.class);
    }
    public void saveWebShortcut(WebShortcut webShortcut) {
        webShortcut.setCreateTime(DateUtil.now());
        webShortcut.setId(sequenceId.nextId());
        mot.insert(webShortcut);
        webShortcutCache.remove("osp_index");
    }
    public List<WebShortcut> findWebShortcutListByCondition(WebShortcut webShortcut) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(WebShortcut.class)).find(MongoKit.buildMatchDocument(webShortcut));
        Pager pager = webShortcut.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<WebShortcut> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(WebShortcut.class, document)));
        return list;
    }
    public long findWebShortcutCountByCondition(WebShortcut webShortcut) {
        return mot.getCollection(mot.getCollectionName(WebShortcut.class)).countDocuments(MongoKit.buildMatchDocument(webShortcut));
    }
    public WebShortcut findOneWebShortcutByCondition(WebShortcut webShortcut) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(WebShortcut.class)).find(MongoKit.buildMatchDocument(webShortcut));
       MongoKit.appendProjection(query,webShortcut.getPager());
       return mot.getConverter().read(WebShortcut.class,query.first());
    }

    public void updateWebShortcut(WebShortcut webShortcut) {
        mot.updateFirst(query(where("id").is(webShortcut.getId())), MongoKit.update(webShortcut,true),WebShortcut.class);
    }
    public void updateWebShortcutForNotNull(WebShortcut webShortcut) {
        webShortcutCache.remove("osp_index");
        teacherWebShortcutCache.close();
        mot.updateFirst(query(where("id").is(webShortcut.getId())), MongoKit.update(webShortcut,false),WebShortcut.class);
    }
    public void deleteWebShortcut(String id) {
        webShortcutCache.remove("osp_index");
        teacherWebShortcutCache.close();
        mot.remove(query(where("id").is(id)),WebShortcut.class);
    }
    public void deleteWebShortcutByCondition(WebShortcut webShortcut) {
        mot.getCollection(mot.getCollectionName(WebShortcut.class)).deleteMany(MongoKit.buildMatchDocument(webShortcut));
    }
    public void batchSaveWebShortcut(List<WebShortcut> webShortcuts){
        webShortcuts.forEach(webShortcut -> webShortcut.setId(sequenceId.nextId()));
        mot.insertAll(webShortcuts);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
