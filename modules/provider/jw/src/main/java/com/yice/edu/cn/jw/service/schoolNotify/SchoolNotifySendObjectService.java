package com.yice.edu.cn.jw.service.schoolNotify;

import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.jw.schoolNotify.SchoolNotify;
import com.yice.edu.cn.common.pojo.jw.schoolNotify.SchoolNotifySendObject;
import com.yice.edu.cn.jw.service.department.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UntypedExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SchoolNotifySendObjectService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private SchoolNotifySendObjectService schoolNotifySendObjectService;
    @Autowired
    private DepartmentService departmentService;

    public SchoolNotifySendObject findSchoolNotifySendObjectById(String id) {
        return mot.findById(id,SchoolNotifySendObject.class);
    }
    public void saveSchoolNotifySendObject(SchoolNotifySendObject schoolNotifySendObject) {
        mot.insert(schoolNotifySendObject);
    }

    public void saveSchoolNotifySendObjectList(List<SchoolNotifySendObject> schoolNotifySendObjectList) {
        mot.insertAll(schoolNotifySendObjectList);
    }


    public List<SchoolNotifySendObject> findSchoolNotifySendObjectListByCondition(SchoolNotifySendObject schoolNotifySendObject) {
        List<AggregationOperation> operations = new ArrayList<>();

        Criteria criteria = new Criteria();
        List<Criteria> cl = new ArrayList<>();
        SchoolNotify schoolNotify=schoolNotifySendObject.getSchoolNotify();
        if(schoolNotify!=null) {
            if (!StrUtil.hasEmpty(schoolNotify.getSearchStartTime())) {
                cl.add(Criteria.where("schoolNotify.createTime").gte(schoolNotify.getSearchStartTime()));
                schoolNotify.setSearchStartTime(null);
            }
            if (!StrUtil.hasEmpty(schoolNotify.getSearchEndTime())) {
                cl.add(Criteria.where("schoolNotify.createTime").lte(schoolNotify.getSearchEndTime()));
                schoolNotify.setSearchEndTime(null);
            }
            if (!StrUtil.hasEmpty(schoolNotify.getTitle())) {
                cl.add(new Criteria("schoolNotify.title").regex(schoolNotify.getTitle()));
                schoolNotify.setTitle(null);
            }
        }
        schoolNotifySendObject.setSchoolNotify(schoolNotify);
        cl.add(MongoKit.buildCriteria(schoolNotifySendObject, schoolNotifySendObject.getPager()));
        criteria.andOperator(cl.toArray(new Criteria[cl.size()]));
        operations.add(Aggregation.match(criteria));
        MongoKit.sortPageInclude(schoolNotifySendObject.getPager(),operations);
        return mot.aggregate(Aggregation.newAggregation(SchoolNotifySendObject.class,operations),SchoolNotifySendObject.class).getMappedResults();

    }
    public long findSchoolNotifySendObjectCountByCondition(SchoolNotifySendObject schoolNotifySendObject) {
        Criteria criteria = new Criteria();
        List<Criteria> cl = new ArrayList<>();
        SchoolNotify schoolNotify=schoolNotifySendObject.getSchoolNotify();
        if(schoolNotify!=null) {
            if (!StrUtil.hasEmpty(schoolNotify.getSearchStartTime())) {
                cl.add(Criteria.where("schoolNotify.createTime").gte(schoolNotify.getSearchStartTime()));
                schoolNotify.setSearchStartTime(null);
            }
            if (!StrUtil.hasEmpty(schoolNotify.getSearchEndTime())) {
                cl.add(Criteria.where("schoolNotify.createTime").lte(schoolNotify.getSearchEndTime()));
                schoolNotify.setSearchEndTime(null);
            }
            if (!StrUtil.hasEmpty(schoolNotify.getTitle())) {
                cl.add(new Criteria("schoolNotify.title").regex(schoolNotify.getTitle()));
                schoolNotify.setTitle(null);
            }
        }
        cl.add( MongoKit.buildCriteria(schoolNotifySendObject, schoolNotifySendObject.getPager()));
        criteria.andOperator(cl.toArray(new Criteria[cl.size()]));
        return mot.count(query(criteria), SchoolNotifySendObject.class);
    }
    public SchoolNotifySendObject findOneSchoolNotifySendObjectByCondition(SchoolNotifySendObject schoolNotifySendObject) {
        Example<SchoolNotifySendObject> example = Example.of(schoolNotifySendObject, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria().alike(example)));
        Pager pager = schoolNotifySendObject.getPager();
        if(pager!=null&&pager.getIncludes()!=null){
            operations.add(Aggregation.project(pager.getIncludes()));
        }
        return mot.aggregate(Aggregation.newAggregation(SchoolNotifySendObject.class,operations),SchoolNotifySendObject.class).getUniqueMappedResult();
    }

    public void updateSchoolNotifySendObject(SchoolNotifySendObject schoolNotifySendObject) {
        mot.updateFirst(query(where("id").is(schoolNotifySendObject.getId())), MongoKit.update(schoolNotifySendObject),SchoolNotifySendObject.class);
    }
    public void deleteSchoolNotifySendObject(String id) {
        mot.remove(query(where("id").is(id)),SchoolNotifySendObject.class);
    }
    public void deleteSchoolNotifySendObjectByCondition(SchoolNotifySendObject schoolNotifySendObject) {
        Example<SchoolNotifySendObject> example = Example.of(schoolNotifySendObject, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        mot.remove(query, SchoolNotifySendObject.class);
    }
    public void batchSaveSchoolNotifySendObject(List<SchoolNotifySendObject> schoolNotifySendObjects){
        schoolNotifySendObjects.forEach(schoolNotifySendObject -> schoolNotifySendObject.setId(sequenceId.nextId()));
        mot.insertAll(schoolNotifySendObjects);
    }

    public List<Department> getSchoolNotifyReadDetail(SchoolNotifySendObject schoolNotifySendObject) {
        schoolNotifySendObject.setReadState(schoolNotifySendObject.getReadState());
        schoolNotifySendObject.setType("1");
        List<SchoolNotifySendObject> data=schoolNotifySendObjectService.findSchoolNotifySendObjectListByCondition(schoolNotifySendObject);

//        //组织架构
//        schoolNotifySendObject.setReadState(null);
//        schoolNotifySendObject.setType("0");
//        List<SchoolNotifySendObject> data2=schoolNotifySendObjectService.findSchoolNotifySendObjectListByCondition(schoolNotifySendObject);


        return  buildTree(data);
    }

    public long getSchoolNotifyReadDetailCount(SchoolNotifySendObject schoolNotifySendObject) {
        schoolNotifySendObject.setReadState(schoolNotifySendObject.getReadState());
        schoolNotifySendObject.setType("1");

        return  schoolNotifySendObjectService.findSchoolNotifySendObjectCountByCondition(schoolNotifySendObject);

    }

    public  List<Department> buildTree(List<SchoolNotifySendObject> data1){

        List<Department> departmentList=new ArrayList<>();
        data1.forEach(schoolNotifySendObject1 -> {
            Department department=new Department();
            department.setId(schoolNotifySendObject1.getObjectId());
            department.setName(schoolNotifySendObject1.getDepartmentName());
            department.setType(Integer.parseInt(schoolNotifySendObject1.getType()));
            department.setParentId(schoolNotifySendObject1.getDepartmentParentId());
            department.setImgUrl(schoolNotifySendObject1.getImgUrl());
            departmentList.add(department);
        });
        return departmentList;

    }


}
