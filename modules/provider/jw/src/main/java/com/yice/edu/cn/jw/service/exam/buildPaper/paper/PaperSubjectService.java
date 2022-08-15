package com.yice.edu.cn.jw.service.exam.buildPaper.paper;


import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class PaperSubjectService {
    @Autowired
    private MongoTemplate mot;

    @Autowired
    private SequenceId sequenceId;

    public void savePaperSubject(PaperSubject paperSubject){
        Criteria criteria = Criteria.where("createUserId").is(paperSubject.getCreateUserId());
        Query query = new Query(criteria);
        PaperSubject paperSubject1 = mot.findOne(query,PaperSubject.class);
        if(paperSubject1!=null){
            paperSubject.setId(paperSubject1.getId());
            mot.save(paperSubject,"paperSubject");
        }else{
            paperSubject.setId(sequenceId.nextId());
            mot.insert(paperSubject);
        }
    }

    public PaperSubject findOnePaperSubjectKong(String createUserId){
        Criteria criteria = Criteria.where("createUserId").is(createUserId);
        Query query = new Query(criteria);
        return mot.findOne(query,PaperSubject.class);
    }

}
