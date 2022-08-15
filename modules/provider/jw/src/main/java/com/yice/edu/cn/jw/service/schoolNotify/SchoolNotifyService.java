package com.yice.edu.cn.jw.service.schoolNotify;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.schoolNotify.SchoolNotify;
import com.yice.edu.cn.common.pojo.jw.schoolNotify.SchoolNotifySendObject;
import com.yice.edu.cn.common.pojo.jw.student.StudentForSchoolNotify;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.jw.service.classes.JwClassesService;
import com.yice.edu.cn.jw.service.department.DepartmentService;
import com.yice.edu.cn.jw.service.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UntypedExampleMatcher;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SchoolNotifyService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private SchoolNotifySendObjectService schoolNotifySendObjectService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private JwClassesService jwClassesService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    public SchoolNotify findSchoolNotifyById(String id) {
        return mot.findById(id,SchoolNotify.class);
    }
    @Async
    public void saveSchoolNotify(SchoolNotify schoolNotify) {
        List<SchoolNotifySendObject> schoolNotifySendObjectList=  schoolNotify.getSchoolNotifySendObjectList();
        //由于一个人员可以有多个部门，保存的时候只保存一条数据，去除重复项
        List<SchoolNotifySendObject> schoolNotifySendObjectList1=removeDuplicate(schoolNotifySendObjectList);
        schoolNotify.setCreateTime(DateUtil.now());
        schoolNotify.setId(sequenceId.nextId());
        schoolNotify.setTotalNum(schoolNotifySendObjectList1.size());
        schoolNotify.setAlreadyNum(0);

        //保存发送给教师
        if(schoolNotify.getSendType().equals("1")){
            List<String>teacherIdList=new ArrayList<>();
            List<SchoolNotifySendObject> SendObjectList =new ArrayList<>();
            schoolNotifySendObjectList1.forEach(schoolNotifySendObject -> {
                    teacherIdList.add(schoolNotifySendObject.getObjectId());
                    SchoolNotifySendObject schoolNotifySendObject1=new SchoolNotifySendObject();
//                    schoolNotifySendObject1.setId(sequenceId.nextId());
                    schoolNotifySendObject1.setObjectId(schoolNotifySendObject.getObjectId());
                    schoolNotifySendObject1.setSchoolId(schoolNotify.getSchoolId());
                    schoolNotifySendObject1.setDepartmentName(schoolNotifySendObject.getDepartmentName());
                    schoolNotifySendObject1.setDepartmentParentId(schoolNotifySendObject.getDepartmentParentId());
                    schoolNotifySendObject1.setReadState("1");
                    schoolNotifySendObject1.setSchoolNotify(schoolNotify);
                    schoolNotifySendObject1.setType(schoolNotifySendObject.getType());
                    schoolNotifySendObject1.setCreateTime(schoolNotify.getCreateTime());
                    schoolNotifySendObject1.setImgUrl(schoolNotifySendObject.getImgUrl());
                    schoolNotifySendObject1.setDel("1");
                    SendObjectList.add(schoolNotifySendObject1);
            });
            schoolNotifySendObjectService.saveSchoolNotifySendObjectList(SendObjectList);
            pushToTeacher(teacherIdList,schoolNotify.getTitle());
            //保存发送给学生 type为2
        }else {

            //按照班级id+应届年份推送集合
            ArrayList<String> tags=new ArrayList<>();
            ArrayList<String> classIdList=new ArrayList<>();
            schoolNotifySendObjectList1.forEach(schoolNotifySendObject -> {
                classIdList.add(schoolNotifySendObject.getObjectId());

            });
            //批量插入缓存数据集合
            List<SchoolNotifySendObject> sendObjectListTemp=new ArrayList<>();
            StudentForSchoolNotify studentForSchoolNotify=new StudentForSchoolNotify();
            studentForSchoolNotify.setSchoolId(schoolNotify.getSchoolId());
            studentForSchoolNotify.setClassIdList(classIdList);
            List<StudentForSchoolNotify> students= studentService.findStudentListForSchoolNotify(studentForSchoolNotify);
            schoolNotify.setTotalNum(students.size());
            Map<Integer,Map<String,List<StudentForSchoolNotify>>> studentMap=students.stream().collect(Collectors.groupingBy(StudentForSchoolNotify::getGradeId,Collectors.groupingBy(StudentForSchoolNotify::getClassesId)));
            studentMap.forEach((k,v)->{
                Map<String,List<StudentForSchoolNotify>> map=v;
                List<StudentForSchoolNotify> list1=( List<StudentForSchoolNotify>)getFirstOrNull(map);
                SchoolNotifySendObject schoolNotifySendObject2=new SchoolNotifySendObject();
                schoolNotifySendObject2.setId(sequenceId.nextId());
                schoolNotifySendObject2.setObjectId(list1.get(0).getGradeId().toString());
                schoolNotifySendObject2.setSchoolId(schoolNotify.getSchoolId());
                schoolNotifySendObject2.setDepartmentName(list1.get(0).getGradeName());
                schoolNotifySendObject2.setDepartmentParentId("-1");
                schoolNotifySendObject2.setReadState("1");
                schoolNotifySendObject2.setSchoolNotify(schoolNotify);
                schoolNotifySendObject2.setType("0");
                schoolNotifySendObject2.setDel("1");
                schoolNotifySendObject2.setCreateTime(schoolNotify.getCreateTime());
                sendObjectListTemp.add(schoolNotifySendObject2);
                map.forEach((k1,v1)->{
                    List<StudentForSchoolNotify> list=v1;
                    tags.add(list.get(0).getClassesId()+"."+list.get(0).getEnrollYear());
                    SchoolNotifySendObject schoolNotifySendObject1=new SchoolNotifySendObject();
                    schoolNotifySendObject1.setId(sequenceId.nextId());
                    schoolNotifySendObject1.setClassNum(list.size());
                    schoolNotifySendObject1.setClassReadNum(0);
                    schoolNotifySendObject1.setObjectId(list.get(0).getClassesId());
                    schoolNotifySendObject1.setSchoolId(schoolNotify.getSchoolId());
                    schoolNotifySendObject1.setDepartmentName(list.get(0).getClassesNumber()+"班");
                    schoolNotifySendObject1.setClassNumber(Integer.valueOf(list.get(0).getClassesNumber()));
                    schoolNotifySendObject1.setDepartmentParentId(list.get(0).getGradeId().toString());
                    schoolNotifySendObject1.setReadState("1");
                    schoolNotifySendObject1.setSchoolNotify(schoolNotify);
                    schoolNotifySendObject1.setType("0");
                    schoolNotifySendObject1.setDel("1");
                    schoolNotifySendObject1.setCreateTime(schoolNotify.getCreateTime());
                    sendObjectListTemp.add(schoolNotifySendObject1);
                });

            });

            students.forEach(studentForSchoolNotify1 -> {
            SchoolNotifySendObject schoolNotifySendObject1=new SchoolNotifySendObject();
            schoolNotifySendObject1.setObjectId(studentForSchoolNotify1.getId());
            schoolNotifySendObject1.setSchoolId(schoolNotify.getSchoolId());
            schoolNotifySendObject1.setDepartmentName(studentForSchoolNotify1.getName());
            schoolNotifySendObject1.setDepartmentParentId(studentForSchoolNotify1.getClassesId());
            schoolNotifySendObject1.setReadState("1");
            schoolNotifySendObject1.setSchoolNotify(schoolNotify);
            schoolNotifySendObject1.setType("1");
            schoolNotifySendObject1.setDel("1");
            schoolNotifySendObject1.setCreateTime(schoolNotify.getCreateTime());
            schoolNotifySendObject1.setImgUrl(studentForSchoolNotify1.getImgUrl());
            sendObjectListTemp.add(schoolNotifySendObject1);
            });
            schoolNotifySendObjectService.saveSchoolNotifySendObjectList(sendObjectListTemp);
            PushToStudentByClassId(tags,schoolNotify.getTitle());
        }
        //放到最后执行，中间有改下schoolNotify的状态
        mot.insert(schoolNotify);
    }

    public List<SchoolNotify> findSchoolNotifyListByCondition(SchoolNotify schoolNotify) {
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = new Criteria();
        List<Criteria> cl = new ArrayList<>();
        if(!StrUtil.hasEmpty(schoolNotify.getSearchStartTime())){
            cl.add(Criteria.where("createTime").gte(schoolNotify.getSearchStartTime()));
            schoolNotify.setSearchStartTime(null);
        }
        if(!StrUtil.hasEmpty(schoolNotify.getSearchEndTime())){
            cl.add(Criteria.where("createTime").lte(schoolNotify.getSearchEndTime()));
            schoolNotify.setSearchEndTime(null);
        }
        if(!StrUtil.hasEmpty(schoolNotify.getTitle())){
            cl.add(new Criteria("title").regex(schoolNotify.getTitle()));
            schoolNotify.setTitle(null);
        }

        cl.add(MongoKit.buildCriteria(schoolNotify, schoolNotify.getPager()));
        criteria.andOperator(cl.toArray(new Criteria[cl.size()]));
        operations.add(Aggregation.match(criteria));
        MongoKit.sortPageInclude(schoolNotify.getPager(),operations);
        return mot.aggregate(Aggregation.newAggregation(SchoolNotify.class,operations),SchoolNotify.class).getMappedResults();

    }
    public long findSchoolNotifyCountByCondition(SchoolNotify schoolNotify) {
        Criteria criteria = new Criteria();
        List<Criteria> cl = new ArrayList<>();
        if(!StrUtil.hasEmpty(schoolNotify.getSearchStartTime())){
            cl.add(Criteria.where("createTime").gte(schoolNotify.getSearchStartTime()));
            schoolNotify.setSearchStartTime(null);
        }
        if(!StrUtil.hasEmpty(schoolNotify.getSearchEndTime())){
            cl.add(Criteria.where("createTime").lte(schoolNotify.getSearchEndTime()));
            schoolNotify.setSearchEndTime(null);
        }
        if(!StrUtil.hasEmpty(schoolNotify.getTitle())){
            cl.add(new Criteria("title").regex(schoolNotify.getTitle()));
            schoolNotify.setTitle(null);
        }
        cl.add( MongoKit.buildCriteria(schoolNotify, schoolNotify.getPager()));
        criteria.andOperator(cl.toArray(new Criteria[cl.size()]));
        return mot.count(query(criteria), SchoolNotify.class);
    }
    public SchoolNotify findOneSchoolNotifyByCondition(SchoolNotify schoolNotify) {
        Example<SchoolNotify> example = Example.of(schoolNotify, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria().alike(example)));
        Pager pager = schoolNotify.getPager();
        if(pager!=null&&pager.getIncludes()!=null){
            operations.add(Aggregation.project(pager.getIncludes()));
        }
        return mot.aggregate(Aggregation.newAggregation(SchoolNotify.class,operations),SchoolNotify.class).getUniqueMappedResult();
    }

    public void updateSchoolNotify(SchoolNotify schoolNotify) {
        mot.updateFirst(query(where("id").is(schoolNotify.getId())), MongoKit.update(schoolNotify),SchoolNotify.class);
    }
    public void deleteSchoolNotify(String id) {
        mot.remove(query(where("id").is(id)),SchoolNotify.class);
    }
    public void deleteSchoolNotifyByCondition(SchoolNotify schoolNotify) {
        Example<SchoolNotify> example = Example.of(schoolNotify, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        mot.remove(query, SchoolNotify.class);
    }
    public void batchSaveSchoolNotify(List<SchoolNotify> schoolNotifys){
        schoolNotifys.forEach(schoolNotify -> schoolNotify.setId(sequenceId.nextId()));
        mot.insertAll(schoolNotifys);
    }


    /**
     * 去除重复
     * @param list
     * @return
     */
    public   static   List  removeDuplicate(List<SchoolNotifySendObject> list)  {
        for  ( int  i  =   0 ; i  <  list.size()  -   1 ; i ++ )  {
            for  ( int  j  =  list.size()  -   1 ; j  >  i; j -- )  {
                if  (list.get(j).getObjectId().equals(list.get(i).getObjectId()))  {
                    list.remove(j);
                }
            }
        }
        return list;
    }

    /**
     * 获取map中第一个数据值
     *
     * @param map 数据源
     * @return
     */
    private static Object getFirstOrNull(Map<String, List<StudentForSchoolNotify>> map) {
        Object obj = null;
        for (Map.Entry<String, List<StudentForSchoolNotify>> entry : map.entrySet()) {
            obj = entry.getValue();
            if (obj != null) {
                break;
            }
        }
        return obj;

    }



    public  void pushToTeacher(List<String>teacherIdList,String title){
        if(teacherIdList.size()>0) {
            final Push push = Push.newBuilder(JpushApp.TAP)
                    .setAlias(teacherIdList.toArray(new String[teacherIdList.size()]))
                    .setNotification(Push.Notification.newBuilder().setAlert("您有一条新的校园通知，请及时查收！").setExtras(Extras.newBuilder().setType(Extras.TAP_SCHOOL_NOTIFY).build()).setSound(Constant.JPUSH.SOUND_1).build())
                    .build();
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
        }
    }

    public void PushToStudentByClassId(ArrayList<String> tags,String title){
        String[] tag1 = tags.toArray(new String[tags.size()]);
        final Push push = Push.newBuilder(JpushApp.BMP)
                .setTag(tag1)
                .setNotification(Push.Notification.newBuilder().setAlert("您有一条新的校园通知，请及时查收！").setExtras(Extras.newBuilder().setType(Extras.BMP_SCHOOL_NOTIFY).build()).setSound(Constant.JPUSH.SOUND_1).build())
                .build();
        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));

    }
}
