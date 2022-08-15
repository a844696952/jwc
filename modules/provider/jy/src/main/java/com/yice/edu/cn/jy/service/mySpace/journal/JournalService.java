package com.yice.edu.cn.jy.service.mySpace.journal;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCursor;
import cn.hutool.core.util.StrUtil;
import com.mongodb.client.model.UpdateOptions;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jy.album.Album;
import com.yice.edu.cn.common.pojo.jy.journal.Journal;
import com.yice.edu.cn.common.pojo.jy.journal.JournalThumb;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.jy.journal.NewestJournal;
import com.yice.edu.cn.jy.service.mySpace.album.AlbumService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UntypedExampleMatcher;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import cn.hutool.core.date.DateUtil;

import java.util.*;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class JournalService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private MongoConverter mongoConverter;
    public Journal findJournalById(String id) {
        return mot.findOne(query(where("id").is(id)), Journal.class);
    }
    public void saveJournal(Journal journal) {
        journal.setCreateTime(DateUtil.now());
        journal.setSqId(sequenceId.nextId());
        if(journal.getImages()!=null&&journal.getImages().size()>0){
            List<Album> images = journal.getImages();
            Date date = new Date();
            for (Album image : images) {
                image.setUserId(journal.getUserId());
                image.setParentId("-1");
                image.setType("1");
                if(StrUtil.isEmpty(image.getTitle())){
                    image.setTitle(DateUtil.format(date,"yyyyMMdd"));
                }
                mot.save(image);
            }
        }
        mot.insert(journal);

    }
    public List<Journal> findJournalListByCondition(Journal journal) {
        Example<Journal> example = Example.of(journal, UntypedExampleMatcher.matchingAll().withStringMatcher(UntypedExampleMatcher.StringMatcher.DEFAULT));
        Query query = query(new Criteria().alike(example));
        if(journal.getPager()!=null&&journal.getPager().getPaging()){
            query.with(journal.getPager());
        }
        return mot.find(query, Journal.class);
    }
    public Journal findOneJournalByCondition(Journal journal) {
        Example<Journal> example = Example.of(journal, UntypedExampleMatcher.matchingAll().withStringMatcher(UntypedExampleMatcher.StringMatcher.DEFAULT));
        Query query = query(new Criteria().alike(example));
        return mot.findOne(query, Journal.class);
    }
    public long findJournalCountByCondition(Journal journal) {
        Example<Journal> example = Example.of(journal, UntypedExampleMatcher.matchingAll().withStringMatcher(UntypedExampleMatcher.StringMatcher.DEFAULT));
        Query query = query(new Criteria().alike(example));
        return mot.count(query, Journal.class);
    }
    public void updateJournal(Journal journal) {
        mot.updateFirst(query(where("id").is(journal.getId())), MongoKit.update(journal),Journal.class);
    }
    public void deleteJournal(String id) {
        Journal journal = mot.findAndRemove(query(where("id").is(id)), Journal.class);
        //同时删除相册里的图片
        if(journal==null)return;
        List<Album> images = journal.getImages();
        if(images !=null&& images.size()>0){
            for (Album image : images) {
                mot.remove(query(where("id").is(image.getId())), Album.class);
            }
        }
        //同时删除点赞好了
        mot.remove(query(where("journalSqId").is(journal.getSqId())), JournalThumb.class);
    }
    public void deleteJournalByCondition(Journal journal) {
        Example<Journal> example = Example.of(journal, UntypedExampleMatcher.matchingAll().withStringMatcher(UntypedExampleMatcher.StringMatcher.DEFAULT));
        Query query = query(new Criteria().alike(example));
        mot.remove(query, Journal.class);
    }


    /**
     * mongodb shell 脚本:
     * db.journal.aggregate([
     * {
     *     $match: {
     *         $or: [
     *         { teacherId: { $exists: true } },
     *         {classIdEnrollYear:{$in:["100,2018","101,2018"]}}
     *         ]
     *
     *        }
     *   },
     *   { $sort: { createTime: -1} },
     *   { $skip: 0 },
     *   { $limit: 4 },
     *
     *    {
     *      $lookup:
     *        {
     *          from: "journalThumb",
     *          localField: "cId",
     *          foreignField: "journalId",
     *          as: "thumbs"
     *        }
     *   },
     *    { $replaceRoot: { newRoot: { $mergeObjects: [ { thumbCount: { $size: "$thumbs" } }, "$$ROOT" ] } } },
     *    { $replaceRoot: { newRoot: { $mergeObjects: [ { thumbed: { $in: ['123','$thumbs.teacherId'] } }, "$$ROOT" ] } } },
     *    { $project: { thumbs: 0 } }
     * ])
     * @param journal
     * @return
     */
    public List<Journal> findJournalsForMyIndex(Journal journal) {
        Pager pager = journal.getPager();
        List<TeacherClasses> tcs=journal.getTcs();
        StringBuilder classIdEnrollYears = new StringBuilder("[");
        if(tcs!=null){
            for (int i = 0; i < tcs.size(); i++) {
                final TeacherClasses tc = tcs.get(i);
                classIdEnrollYears.append("'").append(tc.getClassesId()).append(",").append(tc.getEnrollYear()).append("'");
                if(i!=tcs.size()-1){
                    classIdEnrollYears.append(",");
                }
            }
        }

        classIdEnrollYears.append("]");
        Boolean mySelf = journal.getMySelf();//是否只查询自己的日志
        boolean isTeacher=journal.getFromTeacher();//是否老师
        Document firstMatch;

        if(mySelf!=null&&mySelf){
            firstMatch=Document.parse("{$match: {userId: '"+journal.getUserId()+"'  } }");
        }else{
            if(isTeacher){//不是mySelf表明需要查询同校的所有老师和所教班级的日志
                firstMatch=Document.parse("{$match: {  $or: [ { $and:[{schoolId: { $eq: '"+journal.getSchoolId()+"' }},{fromTeacher:{$eq:true}}] },{classIdEnrollYear:{$in:"+classIdEnrollYears+"}}]  }}");
            }else{
                firstMatch=Document.parse("{$match: {  $or: [ { $and:[{schoolId: { $eq: '"+journal.getSchoolId()+"' }},{fromTeacher:{$eq:true}}] },{classIdEnrollYear:{$in:"+classIdEnrollYears+"}}]  }}");
            }
        }
        Document sort=Document.parse("{ $sort: { createTime: -1} }");
        Document skip=new Document("$skip",pager.getBeginRow());
        Document limit=new Document("$limit",pager.getPageSize());
        Document lookUp=Document.parse("{$lookup: {from: 'journalThumb', localField: 'sqId', foreignField: 'journalSqId', as: 'thumbs'}}");
        Document size=Document.parse("{ $replaceRoot: { newRoot: { $mergeObjects: [ { thumbCount: { $size: '$thumbs' } }, '$$ROOT' ] } } }");
        Document in=Document.parse("{ $replaceRoot: { newRoot: { $mergeObjects: [ { thumbed: { $in: ['"+journal.getUserId()+"','$thumbs.userId'] } }, '$$ROOT' ] } } }");
        Document project=Document.parse("{ $project: { thumbs: 0 } }");
        AggregateIterable<Document> aggregate = mot.getCollection("journal").aggregate(Arrays.asList(firstMatch,sort,skip,limit,lookUp,size,in,project));
        List<Journal> r = new ArrayList<>();
        MongoCursor<Document> iterator = aggregate.iterator();
        while(iterator.hasNext()){
            Document doc = iterator.next();
            Journal j = mongoConverter.read(Journal.class, doc);
            r.add(j);
        }
        return r;
    }

    /**
     * 查看别人的日志空间
     * @param journal
     * @return
     */
    public List<Journal> findOtherIndexJournals(Journal journal){
        Pager pager = journal.getPager();
        Document firstMatch=Document.parse("{$match: {userId: '"+journal.getUserId()+"'  } }");

        Document sort=Document.parse("{ $sort: { createTime: -1} }");
        Document skip=new Document("$skip",pager.getBeginRow());
        Document limit=new Document("$limit",pager.getPageSize());
        Document lookUp=Document.parse("{$lookup: {from: 'journalThumb', localField: 'sqId', foreignField: 'journalSqId', as: 'thumbs'}}");
        Document size=Document.parse("{ $replaceRoot: { newRoot: { $mergeObjects: [ { thumbCount: { $size: '$thumbs' } }, '$$ROOT' ] } } }");
        Document in=Document.parse("{ $replaceRoot: { newRoot: { $mergeObjects: [ { thumbed: { $in: ['"+journal.getLoginId()+"','$thumbs.userId'] } }, '$$ROOT' ] } } }");
        Document project=Document.parse("{ $project: { thumbs: 0 } }");
        AggregateIterable<Document> aggregate = mot.getCollection("journal").aggregate(Arrays.asList(firstMatch,sort,skip,limit,lookUp,size,in,project));
        List<Journal> r = new ArrayList<>();
        MongoCursor<Document> iterator = aggregate.iterator();
        while(iterator.hasNext()){
            Document doc = iterator.next();
            Journal j = mongoConverter.read(Journal.class, doc);
            r.add(j);
        }
        return r;
    }
    public void clickThumb(String sqId, String userId) {
        JournalThumb journalThumb = new JournalThumb();
        journalThumb.setJournalSqId(sqId);
        journalThumb.setUserId(userId);
        mot.insert(journalThumb);
    }

    public NewestJournal findNewestJournalsForWorkbench(Journal journal) {
        Pager pager = new Pager();
        pager.setPageSize(4).setSortField("createTime").setSortOrder(Pager.DESC);
        Query query = query(where("schoolId").is(journal.getSchoolId()).and("fromTeacher").is(true));
        query.with(pager);
        List<Journal> teacherJournals = mot.find(query, Journal.class);
        List<String> classIdEnrollYearList = new ArrayList<>();
        List<TeacherClasses> tcs = journal.getTcs();
        if(tcs!=null){
            for (TeacherClasses tc : tcs) {
                classIdEnrollYearList.add(tc.getClassesId()+","+tc.getEnrollYear());
            }
        }
        Query query2 = query(where("classIdEnrollYear").in(classIdEnrollYearList));
        query2.with(pager);
        List<Journal> studentJournals = mot.find(query2, Journal.class);
        return new NewestJournal(teacherJournals,studentJournals);
    }

    public long findOtherIndexJournalCount(Journal journal) {
        return mot.count(query(where("userId").is(journal.getUserId())), Journal.class);
    }
}
