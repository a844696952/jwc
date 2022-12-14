package com.yice.edu.cn.jy.service.knowledgePoint;

import cn.hutool.core.date.DateUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.Cached;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.ExamPointKnowledge;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItemKnowledge;
import com.yice.edu.cn.jy.dao.knowledgePoint.IKnowledgePointDao;
import com.yice.edu.cn.jy.feignClient.dd.DdFeign;
import com.yice.edu.cn.jy.service.subjectSource.ExamPointKnowledgeService;
import com.yice.edu.cn.jy.service.subjectSource.MaterialItemKnowledgeService;
import com.yice.edu.cn.jy.service.topics.TopicsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class KnowledgePointService {
    @Autowired
    private IKnowledgePointDao knowledgePointDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private TopicsService topicsService;
    @Autowired
    private DdFeign ddFeign;
    @Autowired
    private ExamPointKnowledgeService examPointKnowledgeService;
    @Autowired
    private MaterialItemKnowledgeService materialItemKnowledgeService;
    @CreateCache(name="subject_knowledge_",expire = 3,timeUnit = TimeUnit.DAYS)
    private Cache<String,KnowledgePoint> knowledgePointCached;


    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public KnowledgePoint findKnowledgePointById(String id) {
        return knowledgePointDao.findKnowledgePointById(id);
    }
    @Transactional
    public void saveKnowledgePoint(KnowledgePoint knowledgePoint) {
        //???????????? ??????????????????????????????
        KnowledgePoint k = new KnowledgePoint();
        k.setSubjectId(knowledgePoint.getSubjectId());
        k.setName(knowledgePoint.getName());
        if(knowledgePointDao.findKnowledgePointCountByCondition(k)>0){
            knowledgePoint.setCode(400);//??????
        }else{
            knowledgePoint.setId(sequenceId.nextId());
            knowledgePoint.setTopicCount(0);//??????0?????????
            knowledgePoint.setChildNum(0);//??????0?????????
            knowledgePointDao.saveKnowledgePoint(knowledgePoint);
            //??????????????? ???????????? 21?????????0 ????????????
            if(!"0".equals(knowledgePoint.getParentId()))
                knowledgePointDao.updateKnowledgePointChildNum(knowledgePoint.getParentId(),1);
        }
    }
    @Transactional(readOnly = true)
    @Cached(name="subject_knowledge_",key = "#knowledgePoint.subjectId",expire = 3,timeUnit = TimeUnit.DAYS)
    public List<KnowledgePoint> findKnowledgePointListByCondition(KnowledgePoint knowledgePoint) {
        return knowledgePointDao.findKnowledgePointListByCondition(knowledgePoint);
    }
    @Transactional(readOnly = true)
    public KnowledgePoint findOneKnowledgePointByCondition(KnowledgePoint knowledgePoint) {
        return knowledgePointDao.findOneKnowledgePointByCondition(knowledgePoint);
    }
    @Transactional(readOnly = true)
    public long findKnowledgePointCountByCondition(KnowledgePoint knowledgePoint) {
        return knowledgePointDao.findKnowledgePointCountByCondition(knowledgePoint);
    }
    @Transactional
    @Cached(name="subject_knowledge_",key = "#knowledgePoint.subjectId")
    public void updateKnowledgePoint(KnowledgePoint knowledgePoint) {
        //???????????? ??????????????????????????????
        KnowledgePoint k = new KnowledgePoint();
        k.setSubjectId(knowledgePoint.getSubjectId());
        k.setName(knowledgePoint.getName());
        if(knowledgePointDao.findKnowledgePointCountByCondition(k)>0){
            knowledgePoint.setCode(400);//??????
        }else {
            knowledgePointDao.updateKnowledgePoint(knowledgePoint);
            //????????????????????????????????????
            topicsService.updateTopicsKnowledge(knowledgePoint);
        }
    }
    @Transactional
    @Cached(name="subject_knowledge_",key = "#knowledgePoint.subjectId")
    public void updateKnowledgePointForAll(KnowledgePoint knowledgePoint) {
        knowledgePointDao.updateKnowledgePointForAll(knowledgePoint);
    }
    @Transactional
    public void deleteKnowledgePoint(String id) {
        KnowledgePoint old = knowledgePointDao.findKnowledgePointById(id);
        if(old!=null){
            //?????????????????????????????????
            //1???????????????????????????????????????
            ExamPointKnowledge examPointKnowledge = new ExamPointKnowledge();
            examPointKnowledge.setKnowledgePointId(id);
            examPointKnowledgeService.deleteExamPointKnowledgeByCondition(examPointKnowledge);
            //2?????????????????????????????????
            MaterialItemKnowledge materialItemKnowledge = new MaterialItemKnowledge();
            materialItemKnowledge.setKnowledgePointId(id);
            materialItemKnowledgeService.deleteMaterialItemKnowledgeByCondition(materialItemKnowledge);
            //??????????????? ????????????
            if(!"0".equals(old.getParentId()))
                knowledgePointDao.updateKnowledgePointChildNum(old.getParentId(),-1);
            knowledgePointDao.deleteKnowledgePoint(id);
            knowledgePointCached.remove(old.getSubjectId());
        }
    }
    @Transactional
    public void batchSaveKnowledgePoint(List<KnowledgePoint> knowledgePoints){
        knowledgePoints.forEach(knowledgePoint -> knowledgePoint.setId(sequenceId.nextId()));
        knowledgePointDao.batchSaveKnowledgePoint(knowledgePoints);
        knowledgePointCached.close();
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @Transactional
    public void batchSaveKnowledgePoint4New(List<KnowledgePoint> knowledgePoints){
        //???????????????id?????????
        knowledgePoints.forEach(knowledgePoint -> knowledgePoint.setId(Optional.ofNullable(knowledgePoint.getId()).orElse(sequenceId.nextId())));
        knowledgePointDao.batchDeleteKnowledgePoint(knowledgePoints.stream().map(KnowledgePoint::getId).toArray(String[]::new));
        knowledgePointDao.batchSaveKnowledgePoint(knowledgePoints);
    }

    public Map<String, Object> uploadKnowledgePoint(List<KnowledgePoint> knowledgePoints) {
        Map<String, Object> temp = new HashMap<>();
        List<String> errors = new ArrayList<>();
        //????????????????????????????????????
        final Map<String,String> ddMap = new HashMap<>();
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.SCHOOL_TYPE);
        List<Dd> schoolType = ddFeign.findDdListByCondition(dd);
        dd = null;
        if(schoolType==null||schoolType.size()<=0){
            temp.put("code","201");
            temp.put("error","????????????????????????");
            return temp;
        }
        schoolType.forEach(d->{
            ddMap.put(d.getName(),d.getId());
            //???????????????
            final Dd dt = new Dd();
            dt.setTypeId(Constant.DD_TYPE.SUBJECT);
            dt.setLevelType(d.getId());
            ddFeign.findDdListByCondition(dt).forEach(dc->ddMap.put(d.getName()+'-'+dc.getName(),dc.getId()));
        });
        schoolType = null;
        //?????????????????????
        String nt = DateUtil.formatDate(DateUtil.date());
        StringBuffer s = new StringBuffer();
        //????????????????????? ????????????
        KnowledgePoint old = new KnowledgePoint();
        old.setPager(new Pager().setPaging(false).setIncludes("subject_id","type_id","name"));
        final List<KnowledgePoint> olds = this.findKnowledgePointListByCondition(old);
        old = null;
        knowledgePoints.forEach(k -> {
            s.setLength(0);
            int i = (knowledgePoints.indexOf(k)+1);
            //????????????????????????
            if(StringUtils.isEmpty(k.getTypeName())||ddMap.get(k.getTypeName())==null){
                s.append("?????????????????????[??????/??????/??????];");
            }else{
                k.setTypeId(ddMap.get(k.getTypeName()));
            }
            //????????????????????????
            if(StringUtils.isEmpty(k.getSubjectName())||ddMap.get(k.getTypeName()+'-'+k.getSubjectName())==null){
                s.append("?????????????????????[???????????????????????????????????????];");
            }else{
                k.setSubjectId(ddMap.get(k.getTypeName()+'-'+k.getSubjectName()));
            }
            //????????????????????????????????????
            long li = knowledgePoints.stream().filter(k1->k1.getSubjectName().equals(k.getSubjectName())&&k1.getTypeName().equals(k.getTypeName())&&k1.getName().equals(k.getName())).count();
            if(li>1){
                s.append("????????????????????????");
            }else if (StringUtils.isEmpty(k.getName())){
                s.append("?????????????????????");
            //}else if (this.findKnowledgePointCountByCondition(k)>0){
            }else if (olds.stream().anyMatch(o->o.getTypeId().equals(k.getTypeId())&&o.getSubjectId().equals(k.getSubjectId())&&o.getName().equals(k.getName()))){
                s.append("???????????????????????????????????????");
            }
            if (s.length()>0){
                s.insert(0,"???"+i+"????????????");
                errors.add(s.toString());
            }else {
                //???????????????????????????
                k.setId(sequenceId.nextId());
                k.setTopicCount(0);
                k.setCreateTime(nt);
            }
        });
        olds.clear();
        ddMap.clear();
        //??????????????????
        if(errors.size()>0){
            temp.put("code","202");
            temp.put("error",errors);
        }else {
            temp.put("code","200");
            knowledgePointDao.batchSaveKnowledgePoint(knowledgePoints);
        }
        return temp;
    }
}
