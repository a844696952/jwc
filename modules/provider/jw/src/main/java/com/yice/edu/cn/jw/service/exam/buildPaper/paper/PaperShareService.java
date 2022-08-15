package com.yice.edu.cn.jw.service.exam.buildPaper.paper;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.answerSheet.AnswerSheet;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.Paper;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperQuestion;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperShare;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.jw.service.exam.buildPaper.answerSheet.AnswerSheetService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class PaperShareService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private PaperService paperService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private AnswerSheetService answerSheetService;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public PaperShare findPaperShareById(String id) {
        return mot.findById(id,PaperShare.class);
    }
    public void savePaperShare(PaperShare paperShare) {
        paperShare.setCreateTime(DateUtil.now());
        paperShare.setId(sequenceId.nextId());
        mot.insert(paperShare);
    }
    public List<PaperShare> findPaperShareListByCondition(PaperShare paperShare) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(PaperShare.class)).find(MongoKit.buildMatchDocument(paperShare));
        Pager pager = paperShare.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<PaperShare> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(PaperShare.class, document)));
        return list;
    }
    public long findPaperShareCountByCondition(PaperShare paperShare) {
        return mot.getCollection(mot.getCollectionName(PaperShare.class)).countDocuments(MongoKit.buildMatchDocument(paperShare));
    }
    public PaperShare findOnePaperShareByCondition(PaperShare paperShare) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(PaperShare.class)).find(MongoKit.buildMatchDocument(paperShare));
       MongoKit.appendProjection(query,paperShare.getPager());
       return mot.getConverter().read(PaperShare.class,query.first());
    }

    public void updatePaperShare(PaperShare paperShare) {
        paperShare.setUpdateTime(DateUtil.now());
        mot.getCollection(mot.getCollectionName(PaperShare.class)).updateOne(new Document("_id",paperShare.getId()), MongoKit.buildUpdateDocument(paperShare,false));
    }
    public void updatePaperShareForAll(PaperShare paperShare) {
        paperShare.setUpdateTime(DateUtil.now());
        mot.getCollection(mot.getCollectionName(PaperShare.class)).updateOne(new Document("_id",paperShare.getId()), MongoKit.buildUpdateDocument(paperShare,true));
    }
    public void deletePaperShare(String id) {
        mot.getCollection(mot.getCollectionName(PaperShare.class)).deleteOne(new Document("_id",id));
    }
    public void deletePaperShareByCondition(PaperShare paperShare) {
        mot.getCollection(mot.getCollectionName(PaperShare.class)).deleteMany(MongoKit.buildMatchDocument(paperShare));
    }
    public void batchSavePaperShare(List<PaperShare> paperShares){
        paperShares.forEach(paperShare -> paperShare.setId(sequenceId.nextId()));
        mot.insertAll(paperShares);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    public void savePaperShareListKong(PaperShare paperShare){
        paperShare.setType(1);
        paperShare.setCreateTime(DateUtil.now());
        List<PaperShare> paperShares = new ArrayList<>();
        int size = paperShare.getReceiveIdList().size();
        String[] shars =  paperShare.getReceiveIdList().toArray(new String[size]);
        for(int i = 0;i<size;i++){
            PaperShare paperShare1 = new PaperShare();
            BeanUtil.copyProperties(paperShare,paperShare1);
            paperShare1.setReceiveId(paperShare.getReceiveIdList().get(i));
            paperShare1.setReceiveIdList(null);
            paperShares.add(paperShare1);
        }
        batchSavePaperShare(paperShares);
        final Push push = Push.newBuilder(JpushApp.TAP).getSimplePush(shars,"试卷分享",paperShare.getCreateUserName()+"老师分享了一份试卷给您",Extras.PAPER_SHARE);
        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH,JSONUtil.toJsonStr(push));

    }
    //加入我的试卷列表
    public ResponseJson updatePaperShareAddPaper(PaperShare paperShare){
        Paper paper = new Paper();
        paper.setId(paperShare.getPaperId());
        Paper paper1 = paperService.findOneTestPaperByCondition(paper);
        if(paper1==null){
            return new ResponseJson(false,"分享人已删除原卷");
        }
        AnswerSheet answerSheet = new AnswerSheet();
        answerSheet.setPaperId(paper1.getId());
        answerSheet = answerSheetService.findOneAnswerSheetByCondition(answerSheet);
        answerSheet.setPaperId(null);
        paper1.setId(null);
        paper1.setCreateUserId(paperShare.getReceiveId());
        paper1.setNumbers(0);
        paper1.setUpdateTime(DateUtil.now());
        paper1.getSubject().stream().forEach(f->{
            f.setCreateUserId(paperShare.getReceiveId());
        });
        List<PaperQuestion> paperQuestions = paper1.getSubject();
        paper1.setSubject(null);
        paperService.addSharePaper(paper1,paperQuestions,answerSheet);

        mot.save(paperShare);
        return new ResponseJson();
    }

    //下载试卷
    public Paper uploadSharePaper(String peperId){
        Paper paper = new Paper();
        paper.setId(peperId);
        Paper paper1 = paperService.findOneTestPaperByCondition(paper);
        return paper1;
    }


    public void savePaperShareKong(PaperShare paperShare){
        mot.save(paperShare);
    }


}
