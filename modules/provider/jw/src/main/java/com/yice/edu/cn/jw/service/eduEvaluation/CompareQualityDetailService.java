package com.yice.edu.cn.jw.service.eduEvaluation;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.eduEvaluation.CompareQuality;
import com.yice.edu.cn.common.pojo.jw.eduEvaluation.CompareQualityDetail;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.jw.service.student.StudentService;
import io.netty.util.internal.StringUtil;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class CompareQualityDetailService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private StudentService studentService;
    @Autowired
    private  CompareQualityService compareQualityService;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public CompareQualityDetail findCompareQualityDetailById(String id) {
        return mot.findById(id,CompareQualityDetail.class);
    }
    public void saveCompareQualityDetail(CompareQualityDetail compareQualityDetail) {
        compareQualityDetail.setId(sequenceId.nextId());
        mot.insert(compareQualityDetail);
    }
    public List<CompareQualityDetail> findCompareQualityDetailListByCondition(CompareQualityDetail compareQualityDetail) {
        Document document1= new Document();
        if(compareQualityDetail.getGradeNameArray()!=null){
            document1.append("gradeName",new Document("$in",compareQualityDetail.getGradeNameArray()));
        }
        document1.putAll(MongoKit.buildMatchDocument(compareQualityDetail));
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(CompareQualityDetail.class)).find(document1);
        Pager pager = compareQualityDetail.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<CompareQualityDetail> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(CompareQualityDetail.class, document)));
        return list;
    }
    public long findCompareQualityDetailCountByCondition(CompareQualityDetail compareQualityDetail) {
        Document document1= new Document();
        if(compareQualityDetail.getGradeNameArray()!=null){
            document1.append("gradeName",new Document("$in",compareQualityDetail.getGradeNameArray()));
        }
        document1.putAll(MongoKit.buildMatchDocument(compareQualityDetail));
        return mot.getCollection(mot.getCollectionName(CompareQualityDetail.class)).countDocuments(document1);
    }
    public CompareQualityDetail findOneCompareQualityDetailByCondition(CompareQualityDetail compareQualityDetail) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(CompareQualityDetail.class)).find(MongoKit.buildMatchDocument(compareQualityDetail));
       MongoKit.appendProjection(query,compareQualityDetail.getPager());
       return mot.getConverter().read(CompareQualityDetail.class,query.first());
    }

    public void updateCompareQualityDetail(CompareQualityDetail compareQualityDetail) {
        mot.updateFirst(query(where("id").is(compareQualityDetail.getId())), MongoKit.update(compareQualityDetail),CompareQualityDetail.class);
    }
    public void deleteCompareQualityDetail(String id) {
        mot.remove(query(where("id").is(id)),CompareQualityDetail.class);
    }
    public void deleteCompareQualityDetailByCondition(CompareQualityDetail compareQualityDetail) {
        mot.getCollection(mot.getCollectionName(CompareQualityDetail.class)).deleteMany(MongoKit.buildMatchDocument(compareQualityDetail));
    }
    public Map<String,Object> batchSaveCompareQualityDetail(List<CompareQualityDetail> compareQualityDetails){

        Map<String,Object> result=ValidateRules(compareQualityDetails);
        if(result.get("code").equals("200")){
            //??????????????????
            CompareQualityDetail compareQualityDetail=new CompareQualityDetail();
            compareQualityDetail.setCompareQualityId(compareQualityDetails.get(0).getCompareQualityId());
            compareQualityDetail.setGradeName(compareQualityDetails.get(0).getGradeName());

            CompareQuality compareQuality=  compareQualityService.findCompareQualityById(compareQualityDetails.get(0).getCompareQualityId());


            deleteCompareQualityDetailByCondition(compareQualityDetail);
            compareQualityDetails.forEach(c -> {
                c.setId(sequenceId.nextId());
                c.setCompareQualityName(compareQuality.getName());
                c.setSchoolYear(compareQuality.getSchoolYear());
                c.setTerm(compareQuality.getTerm());
                c.setCompareQuality(compareQuality);
                c.setCreateTime(DateUtil.now());
            });
            mot.insertAll(compareQualityDetails);
        }
        return  result;
    }




    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    public Map<String,Object>   ValidateRules(List<CompareQualityDetail> compareQualityDetails){
        //????????????
        List<String> errors = new ArrayList<>();

        compareQualityDetails.forEach(c -> {
            int limitLength=15;
            int i = compareQualityDetails.indexOf(c) + 1;//????????????????????????
            StringBuffer error = new StringBuffer();//????????????
            if (StringUtil.isNullOrEmpty(c.getStuName())) {
                error.append("??????????????????;");
            }else {
                if (c.getStuName().length()>limitLength) {
                    error.append("????????????????????????10??????;");
                }
            }


            if (StringUtil.isNullOrEmpty(c.getGradeName())) {
                error.append("??????????????????;");
            }else {
                if (c.getGradeName().length()>limitLength) {
                    error.append("????????????????????????10??????;");
                }
                if(!c.getGradeName().equals(c.getUploadGradeName())){
                    error.append("?????????????????????????????????????????????;");
                }
            }


            if (StringUtil.isNullOrEmpty(c.getClassNum())) {
                error.append("??????????????????;");
            }else {
                if (c.getClassNum().length()>limitLength) {
                    error.append("????????????????????????10??????;");
                }
            }


            if (StringUtil.isNullOrEmpty(c.getStuNum())) {
                error.append("??????????????????;");
            }else {
                if (c.getStuNum().length()>limitLength) {
                    error.append("????????????????????????10??????;");
                }
                Student s=new Student();
                s.setStudentNo(c.getStuNum());
                s.setName(c.getStuName());
                s.setSchoolId(c.getSchoolId());
                List<Student>studentList=studentService.findStudentListByCondition(s);
                if(studentList.size()==0){
                    error.append("????????????;");
                }else {
                    c.setClassId(studentList.get(0).getClassesId());
                }

            }


            if (StringUtil.isNullOrEmpty(c.getMoralEducationScore())) {
                error.append("??????????????????????????????;");
            }else {
                if (c.getMoralEducationScore().length()>limitLength) {
                    error.append("????????????????????????????????????10??????;");
                }
            }


            if (StringUtil.isNullOrEmpty(c.getAcademicLevelScore())) {
                error.append("??????????????????????????????;");
            }else {
                if (c.getAcademicLevelScore().length()>limitLength) {
                    error.append("????????????????????????????????????10??????;");
                }
            }


            if (StringUtil.isNullOrEmpty(c.getPhysicalHealthScore())) {
                error.append("??????????????????????????????;");
            }else {
                if (c.getPhysicalHealthScore().length()>limitLength) {
                    error.append("????????????????????????????????????10??????;");
                }
            }


            if (StringUtil.isNullOrEmpty(c.getArtisticAccomplishmentScore())) {
                error.append("??????????????????????????????;");
            }else {
                if (c.getArtisticAccomplishmentScore().length()>limitLength) {
                    error.append("????????????????????????????????????10??????;");
                }
            }


            if (StringUtil.isNullOrEmpty(c.getSocialPracticeScore())) {
                error.append("??????????????????????????????;");
            }else {
                if (c.getSocialPracticeScore().length()>limitLength) {
                    error.append("????????????????????????????????????10??????;");
                }
            }


            if (StringUtil.isNullOrEmpty(c.getTotalScore())) {
                error.append("????????????????????????;");
            }else {
                if (c.getTotalScore().length()>limitLength) {
                    error.append("??????????????????????????????10??????;");
                }
            }



            //????????????list
            if (error.length() > 0) {
                error.insert(0, "???" + i + "???,");
                errors.add(error.toString());
            }

        });

        Map<String, Object> result = new HashMap<>();
        if (errors.size() <= 0) {
            result.put("code", "200");
        } else {
            result.put("code", "222");
            result.put("errors", errors);
        }

        return  result;
    }

    public void deleteCompareQualityDetailByIdList(List<String> idList) {
        idList.forEach(id->{
            deleteCompareQualityDetail( id);
        });
    }

    public List<String> getClassTypeList(CompareQualityDetail compareQualityDetail) {
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = Criteria.where("compareQualityId").is(compareQualityDetail.getId());
        if(StrUtil.isNotEmpty(compareQualityDetail.getGradeName())){
            criteria.andOperator(Criteria.where("gradeName").is(compareQualityDetail.getGradeName()));
        }
            operations.add(Aggregation.match(criteria));
            operations.add(Aggregation.group("classNum"));
         List<CompareQualityDetail> list= mot.aggregate(Aggregation.newAggregation(CompareQualityDetail.class,operations),CompareQualityDetail.class).getMappedResults();
         return list.stream().map(c -> c.getId()).sorted(Comparator.comparing(Integer::valueOf)).collect(Collectors.toList());

    }


}
