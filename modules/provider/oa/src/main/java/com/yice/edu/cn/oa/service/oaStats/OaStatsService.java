package com.yice.edu.cn.oa.service.oaStats;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.building.Building;
import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.oa.oaStats.OaStats;
import com.yice.edu.cn.common.pojo.oa.oaStats.StatsDetailFacet;
import com.yice.edu.cn.common.pojo.oa.oaStats.StatsFacet;
import com.yice.edu.cn.common.pojo.oa.process.ProcessForm;
import com.yice.edu.cn.common.pojo.oa.process.SchoolProcess;
import com.yice.edu.cn.common.pojo.oa.processBusinessData.ProcessBusinessData;
import com.yice.edu.cn.oa.feignClient.BuildingFeign;
import com.yice.edu.cn.oa.feignClient.DepartmentFeign;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class OaStatsService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private DepartmentFeign departmentFeign;
    @Autowired
    private BuildingFeign buildingFeign;

    /**
     * db.getCollection("processBusinessData").aggregate([
     *     {
     *         $match: {
     *             schoolProcessId: {
     *                 $in: [
     *                     "2012045530859061248",
     *                     "2012045530859061249",
     *                     "2012045530859061250",
     *                     "2012045530859061251",
     *                     "2012045530859061252",
     *                     "2012045530859061253",
     *                     "2028735052216811520",
     *                     "2070898574362951680",
     *                     "2073352478157717504",
     *                     "2073352478157717505",
     *                     "2186223187904446464"
     *                 ]
     *             },
     *             $or: [
     *                 {
     *                     $and: [
     *                         {
     *                             "formData.beginTime": {
     *                                 $gt: "2017"
     *                             }
     *                         },
     *                         {
     *                             "formData.endTime": {
     *                                 $lt: "2020"
     *                             }
     *                         }
     *                     ]
     *                 },
     *                 {
     *                     $and: [
     *                         {
     *                             "formData.applyTime": {
     *                                 $gt: "2017"
     *                             }
     *                         },
     *                         {
     *                             "formData.applyTime": {
     *                                 $lt: "2020"
     *                             }
     *                         }
     *                     ]
     *                 }
     *             ]
     *         }
     *     },
     *     {
     *         $facet: {
     *             "total": [
     *                 {
     *                     $group: {
     *                         _id: "$schoolProcessId",
     *                         schoolProcessId: {
     *                             $first: "$schoolProcessId"
     *                         },
     *                         type: {
     *                             $first: "$type"
     *                         },
     *                         status: {
     *                             $first: "$status"
     *                         },
     *                         count: {
     *                             $sum: 1
     *                         },
     *
     *                     }
     *                 }
     *             ],
     *             "wait": [
     *                 {
     *                     $match: {
     *                         status: 0
     *                     }
     *                 },
     *                 {
     *                     $group: {
     *                         _id: "$schoolProcessId",
     *                         schoolProcessId: {
     *                             $first: "$schoolProcessId"
     *                         },
     *                         type: {
     *                             $first: "$type"
     *                         },
     *                         status: {
     *                             $first: "$status"
     *                         },
     *                         count: {
     *                             $sum: 1
     *                         },
     *
     *                     }
     *                 }
     *             ],
     *             "success": [
     *                 {
     *                     $match: {
     *                         status: 1
     *                     }
     *                 },
     *                 {
     *                     $group: {
     *                         _id: "$schoolProcessId",
     *                         schoolProcessId: {
     *                             $first: "$schoolProcessId"
     *                         },
     *                         type: {
     *                             $first: "$type"
     *                         },
     *                         status: {
     *                             $first: "$status"
     *                         },
     *                         count: {
     *                             $sum: 1
     *                         },
     *
     *                     }
     *                 }
     *             ],
     *             "fail": [
     *                 {
     *                     $match: {
     *                         status: 2
     *                     }
     *                 },
     *                 {
     *                     $group: {
     *                         _id: "$schoolProcessId",
     *                         schoolProcessId: {
     *                             $first: "$schoolProcessId"
     *                         },
     *                         type: {
     *                             $first: "$type"
     *                         },
     *                         status: {
     *                             $first: "$status"
     *                         },
     *                         count: {
     *                             $sum: 1
     *                         },
     *
     *                     }
     *                 }
     *             ]
     *         }
     *     }
     * ])
     * @param schoolId
     * @return
     */
    public ResponseJson findTotalStats(OaStats oaStatsObj, String schoolId){

        String[] rangeTime = oaStatsObj.getRangeTime();
        LocalDate endTime = LocalDate.parse(rangeTime[1], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate beginTime = LocalDate.parse(rangeTime[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if(endTime.minus(1, ChronoUnit.YEARS).isAfter(beginTime)){
            return new ResponseJson(false, "时间范围只能在一年之内,请重新选择");
        }
        //1.获取学校有效的学校流程id
        List<String> schoolProcessIds = new ArrayList<>();
        List<OaStats> oaStatses = new ArrayList<>();
        mot.getCollection(mot.getCollectionName(SchoolProcess.class)).find(
                new Document("schoolId", schoolId).append("status", true)
                        //.append("processLibId", new Document("$exists", true)) /*** 统计所有流程信息,包括自建流程*/
        ).projection(new Document("type", 1).append("processForms",1).append("processLibId",1)).forEach((Block<Document>) document -> {
            schoolProcessIds.add(document.getString("_id"));
            oaStatses.add(mot.getConverter().read(OaStats.class, document));
        });
        //2.使用$facet一次性获取4个统计维度数据
        Document matchDocument = new Document("$match", new Document("schoolProcessId", new Document("$in", schoolProcessIds))
                .append("$and",Arrays.asList(new Document("createTime",new Document("$lt",rangeTime[1]+" 23:59:59")),new Document("createTime",new Document("$gt",rangeTime[0]))))
        );
        Document facetDocument = Document.parse("{" +
                "        $facet: {" +
                "            \"total\": [" +
                "                {" +
                "                    $group: {" +
                "                        _id: \"$schoolProcessId\"," +
                "                        schoolProcessId: {" +
                "                            $first: \"$schoolProcessId\"" +
                "                        }," +
                "                        type: {" +
                "                            $first: \"$type\"" +
                "                        }," +
                "                        status: {" +
                "                            $first: \"$status\"" +
                "                        }," +
                "                        count: {" +
                "                            $sum: 1" +
                "                        }," +
                "                        " +
                "                    }" +
                "                }" +
                "            ]," +
                "            \"wait\": [" +
                "                {" +
                "                    $match: {" +
                "                        status: 0" +
                "                    }" +
                "                }," +
                "                {" +
                "                    $group: {" +
                "                        _id: \"$schoolProcessId\"," +
                "                        schoolProcessId: {" +
                "                            $first: \"$schoolProcessId\"" +
                "                        }," +
                "                        type: {" +
                "                            $first: \"$type\"" +
                "                        }," +
                "                        status: {" +
                "                            $first: \"$status\"" +
                "                        }," +
                "                        count: {" +
                "                            $sum: 1" +
                "                        }," +
                "                        " +
                "                    }" +
                "                }" +
                "            ]," +
                "            \"success\": [" +
                "                {" +
                "                    $match: {" +
                "                        status: 1" +
                "                    }" +
                "                }," +
                "                {" +
                "                    $group: {" +
                "                        _id: \"$schoolProcessId\"," +
                "                        schoolProcessId: {" +
                "                            $first: \"$schoolProcessId\"" +
                "                        }," +
                "                        type: {" +
                "                            $first: \"$type\"" +
                "                        }," +
                "                        status: {" +
                "                            $first: \"$status\"" +
                "                        }," +
                "                        count: {" +
                "                            $sum: 1" +
                "                        }," +
                "                        " +
                "                    }" +
                "                }" +
                "            ]," +
                "            \"fail\": [" +
                "                {" +
                "                    $match: {" +
                "                        status: 2" +
                "                    }" +
                "                }," +
                "                {" +
                "                    $group: {" +
                "                        _id: \"$schoolProcessId\"," +
                "                        schoolProcessId: {" +
                "                            $first: \"$schoolProcessId\"" +
                "                        }," +
                "                        type: {" +
                "                            $first: \"$type\"" +
                "                        }," +
                "                        status: {" +
                "                            $first: \"$status\"" +
                "                        }," +
                "                        count: {" +
                "                            $sum: 1" +
                "                        }," +
                "                        " +
                "                    }" +
                "                }" +
                "            ]" +
                "        }" +
                "    }");
        Document resultDocument = mot.getCollection(mot.getCollectionName(ProcessBusinessData.class))
                .aggregate(Arrays.asList(matchDocument, facetDocument)).first();
        StatsFacet statsFacet = mot.getConverter().read(StatsFacet.class, resultDocument);
        //3.处理成前端好理解的数据结构
        for (OaStats oaStats : oaStatses) {
            for (StatsFacet.CountObj totalObj : statsFacet.getTotal()) {
                if(totalObj.getSchoolProcessId().equals(oaStats.getId())){
                    oaStats.setTotal(totalObj.getCount());
                    break;
                }
            }
            for (StatsFacet.CountObj waitObj : statsFacet.getWait()) {
                if(waitObj.getSchoolProcessId().equals(oaStats.getId())){
                    oaStats.setWait(waitObj.getCount());
                    break;
                }
            }
            for (StatsFacet.CountObj successObj : statsFacet.getSuccess()) {
                if(successObj.getSchoolProcessId().equals(oaStats.getId())){
                    oaStats.setSuccess(successObj.getCount());
                    break;
                }
            }
            for (StatsFacet.CountObj failObj : statsFacet.getFail()) {
                if(failObj.getSchoolProcessId().equals(oaStats.getId())){
                    oaStats.setFail(failObj.getCount());
                    break;
                }
            }
        }
        List<OaStats> list;
        if(Objects.nonNull(oaStatsObj.getPager())){
             list   = oaStatses.stream().skip(oaStatsObj.getPager().getBeginRow()).limit(oaStatsObj.getPager().getPageSize()).collect(Collectors.toList());
        }else{
            list = oaStatses;
        }
        return new ResponseJson(list,Long.valueOf(oaStatses.size()));

    }

    /**
     * db.getCollection("processBusinessData").aggregate([
     *     {
     *         $match: {
     *             schoolProcessId: "2147601055272419330"
     *         }
     *     },
     *     {
     *         $facet: {
     *             total: [
     *                 {
     *                     $group: {
     *
     *                         _id: null,
     *                         count: {
     *                             $sum: 1
     *                         }
     *                     }
     *                 }
     *             ],
     *             maxPerson: [
     *                 {
     *                     $group: {
     *                         _id: "$initiatorId",
     *                         count: {
     *                             $sum: 1
     *                         },
     *                         initiator: {
     *                             $first: "$initiator"
     *                         }
     *                     }
     *                 },
     *                 {
     *                     $sort: {
     *                         count: - 1
     *                     }
     *                 },
     *                 {
     *                     $limit: 1
     *                 }
     *             ],
     *             maxDepartment: [
     *                 {
     *                     $group: {
     *                         _id: "$formData.department",
     *                         count: {
     *                             $sum: 1
     *                         },
     *                         departmentName: {
     *                             $first: "$formData.departmentName"
     *                         }
     *                     }
     *                 },
     *                 {
     *                     $sort: {
     *                         count: - 1
     *                     }
     *                 },
     *                 {
     *                     $limit: 1
     *                 }
     *             ],
     *             maxLeaveType: [
     *                 {
     *                     $group: {
     *                         _id: "$formData.leaveType",
     *                         count: {
     *                             $sum: 1
     *                         },
     *                         leaveTypeName: {
     *                             $first: "$formData.leaveTypeName"
     *                         }
     *                     }
     *                 },
     *                 {
     *                     $sort: {
     *                         count: - 1
     *                     }
     *                 },
     *                 {
     *                     $limit: 1
     *                 }
     *             ]
     *         }
     *     }
     * ])
     * @param processBusinessData
     * @param needCount
     * @return
     */
    public ResponseJson findStatsDetail(ProcessBusinessData processBusinessData, boolean needCount) {
        // 验证rangeTime非空并且时间范围正在一年之内
        if(processBusinessData.getRangeTime().length!=2){
            return new ResponseJson(false, "时间范围必须是两个字符串元素");
        }
        LocalDate endTime = LocalDate.parse(processBusinessData.getRangeTime()[1], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate beginTime = LocalDate.parse(processBusinessData.getRangeTime()[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if(endTime.minus(1, ChronoUnit.YEARS).isAfter(beginTime)){
            return new ResponseJson(false, "时间范围只能在一年之内,请重新选择");
        }
        //1.查询列表
        Document matchDocument = new Document("schoolProcessId", processBusinessData.getSchoolProcessId());
        if(processBusinessData.getStatus()!=null){
            matchDocument.append("status",processBusinessData.getStatus());
        }
        //学年查询
        if(StrUtil.isNotEmpty(processBusinessData.getSchoolYearId())){
            matchDocument.append("schoolYearId",processBusinessData.getSchoolYearId());
        }else{
            //学年与时间范围互斥
            matchDocument.append("$and",Arrays.asList(
                        new Document("createTime",new Document("$lt",processBusinessData.getRangeTime()[1]+" 23:59:59")),
                         new Document("createTime",new Document("$gt",processBusinessData.getRangeTime()[0]))
                  ));
        }
        if(StrUtil.isNotEmpty(processBusinessData.getInitiator())){
            matchDocument.append("initiator",new Document("$regex",".*"+processBusinessData.getInitiator()+".*"));
        }
        //申请人id
        if(StrUtil.isNotEmpty(processBusinessData.getInitiatorId())){
            matchDocument.append("initiatorId",processBusinessData.getInitiatorId());
        }
        //动态表单查询
        if(MapUtil.isNotEmpty(processBusinessData.getFormData())){
            processBusinessData.getFormData().forEach((k,v)-> {
                if(Objects.nonNull(v)){
                   if("purchaseName".equals(k)){
                       matchDocument.append("formData."+k,new Document("$regex",".*"+v+".*"));
                   }else{
                       matchDocument.append("formData."+k,v);
                   }
                }
            });
        }
        MongoCollection<Document> collection = mot.getCollection(mot.getCollectionName(ProcessBusinessData.class));
        List<ProcessBusinessData> rows = new ArrayList<>();
        long count=0;
        collection.find(matchDocument)
                .skip(processBusinessData.getPager().getBeginRow().intValue())
                .limit(processBusinessData.getPager().getPageSize())
                .sort(new Document("createTime",-1))
                .projection(new Document("initiator",1).append("departmentName",1).append("type",1).append("formData",1).append("approver",1)
                        .append("copyFor",1).append("status",1).append("imgUrl",1).append("createTime",1).append("processForms",1)
                        .append("processLibId",1).append("schoolId",1).append("cancelReason",1).append("initiatorId",1)
                        .append("clearLeave",1).append("clearLeaveTime",1))
                .forEach((Block<Document>) document -> rows.add(mot.getConverter().read(ProcessBusinessData.class, document)));
        if(needCount){
            count =collection.countDocuments(matchDocument);
        }
        //2.使用$facet统计 四个东西
        Document statsFacet = new Document("total", Collections.singletonList(Document.parse("{" +
                "                    $group: {" +
                "                        " +
                "                        \"_id\": 1," +
                "                        \"count\": {" +
                "                            $sum: 1" +
                "                        }" +
                "                    }" +
                "                }")))
                .append("maxPerson", Arrays.asList(
                        Document.parse(" {" +
                                "                    $group: {" +
                                "                        _id: \"$initiatorId\"," +
                                "                        count: {" +
                                "                            $sum: 1" +
                                "                        }," +
                                "                        initiator: {" +
                                "                            $first: \"$initiator\"" +
                                "                        }" +
                                "                    }" +
                                "                }"),
                        Document.parse("{" +
                                "                    $sort: {" +
                                "                        count: -1" +
                                "                    }" +
                                "                }"),
                        Document.parse("{" +
                                "                    $limit: 1" +
                                "                }")))
                .append("maxDepartment", Arrays.asList(
                        Document.parse("{" +
                                "                    $group: {" +
                                "                        _id: \"$formData.department\"," +
                                "                        count: {" +
                                "                            $sum: 1" +
                                "                        }," +
                                "                        departmentName: {" +
                                "                            $first: \"$formData.departmentName\"" +
                                "                        }" +
                                "                    }" +
                                "                }"),
                        Document.parse("{" +
                                "                    $sort: {" +
                                "                        count: -1" +
                                "                    }" +
                                "                }"),
                        Document.parse("{" +
                                "                    $limit: 1" +
                                "                }")))
                .append("maxLeaveType",Arrays.asList(
                        Document.parse(" {" +
                                "                    $group: {" +
                                "                        _id: \"$formData.leaveType\"," +
                                "                        count: {" +
                                "                            $sum: 1" +
                                "                        }," +
                                "                        leaveTypeName: {" +
                                "                            $first: \"$formData.leaveType\"" +
                                "                        }" +
                                "                    }" +
                                "                }"),
                        Document.parse("{" +
                                "                    $sort: {" +
                                "                        count: -1" +
                                "                    }" +
                                "                }"),
                        Document.parse("{" +
                                "                    $limit: 1" +
                                "                }")
                ));
        Document facetDocument = new Document("$facet", statsFacet);
        StatsDetailFacet statsDetailFacet = mot.getConverter().read(StatsDetailFacet.class, collection.aggregate(Arrays.asList(new Document("$match",matchDocument), facetDocument)).first());
        //部门名称获取 从feign获取,如果数据量大考虑从缓存中获取
        rows.parallelStream().forEach(ps->{
            Map<String,Object> formData= ps.getFormData();
            if(formData.containsKey("department") && !formData.containsKey("departmentName")){
                Department dep =  departmentFeign.findDepartmentById(formData.get("department").toString());
                formData.put("departmentName",dep.getName());
                ps.setFormData(formData);
            }
            if(formData.containsKey("room")){
                handleName(ps,formData);
            }
        });
        // 处理标题栏的部门名称
        statsDetailFacet.getMaxDepartment().forEach(v -> {
           if(StrUtil.isEmpty(v.getDepartmentName()) && StrUtil.isNotEmpty(v.getId())){
               Department dep =  departmentFeign.findDepartmentById(v.getId());
               v.setDepartmentName(dep.getName());
           }
        });
        // 处理标题栏请假类型
        if(rows.size() > 0){
            List<ProcessForm> ps = rows.get(0).getProcessForms();
            List<StatsDetailFacet.Pipe> ls =statsDetailFacet.getMaxLeaveType();
            for(int i=0;i<ls.size();i++){
                StatsDetailFacet.Pipe v= ls.get(i);
                String name=   ps.stream().filter(f -> StrUtil.equals(f.getName(),"leaveType")).map(m ->
                        m.getLocalDatasource().stream().filter(mf -> mf.getId().equals(v.getLeaveTypeName())).map(n -> n.getName()).findAny().orElse(""))
                        .findAny().orElse("");
                ls.get(i).setLeaveTypeName(name);
            }
        }
        // 统计节数
        List<String> sectionsList = Lists.newArrayList();
        mot.getCollection(mot.getCollectionName(ProcessBusinessData.class)).find(matchDocument).projection(new Document("formData.sections",1)).forEach((Block<Document>)doc ->{
            Document  formData = (Document) doc.get("formData");
            if(formData.containsKey("sections")){
                sectionsList.add(formData.get("sections").toString());
            }
        });
        statsDetailFacet.setTotalSections(sectionsList.stream().mapToInt(Integer::valueOf).sum());
        if(needCount){
            return new ResponseJson(statsDetailFacet,rows, count);
        }
        return new ResponseJson(statsDetailFacet,rows);
    }



    public List<ProcessBusinessData> findProcessesByRangeTime(String id, Pager pager) {
        Document match = new Document("initiatorId", id)
                .append("status",new Document("$ne",2))
                .append("processLibId", new Document("$in", Arrays.asList("20181027163655001", "20181027163655002", "20181027163655010", "20181027163655011")));

        Object[] rangeArray = pager.getRangeArray();
        if(rangeArray !=null&& rangeArray.length==2){
            match.append("formData.beginTime",new Document("$lt",rangeArray[1])).append("formData.endTime",new Document("$gt",rangeArray[0]));
        }
        pager.addExcludes("processForms");
        FindIterable<Document> findIterable = mot.getCollection(mot.getCollectionName(ProcessBusinessData.class)).find(match);
        MongoKit.appendPage(findIterable,pager);
        MongoKit.appendProjection(findIterable,pager);
        List<ProcessBusinessData> r = new ArrayList<>();
        findIterable.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                r.add(mot.getConverter().read(ProcessBusinessData.class,document));
            }
        });
        return r;
    }

    public List<ProcessBusinessData> findProcessesBySchoolIdAndRangeTime(String id, Pager pager) {
        Document match = new Document("schoolId", id)
                .append("status",new Document("$ne",2))
                .append("processLibId", new Document("$in", Arrays.asList("20181027163655001", "20181027163655002", "20181027163655010", "20181027163655011")));

        Object[] rangeArray = pager.getRangeArray();
        if(rangeArray !=null&& rangeArray.length==2){
            match.append("formData.beginTime",new Document("$lt",rangeArray[1])).append("formData.endTime",new Document("$gt",rangeArray[0]));
        }
        pager.addExcludes("processForms");
        FindIterable<Document> findIterable = mot.getCollection(mot.getCollectionName(ProcessBusinessData.class)).find(match);
        MongoKit.appendPage(findIterable,pager);
        MongoKit.appendProjection(findIterable,pager);
        List<ProcessBusinessData> r = new ArrayList<>();
        findIterable.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                r.add(mot.getConverter().read(ProcessBusinessData.class,document));
            }
        });
        return r;

    }

    /**
     * 获取学校流程需要统计的金额
     * @return
     */
    public ResponseJson findTotalMoney(ProcessBusinessData processBusinessData){

        String totalKey=getTotalKey(processBusinessData.getProcessLibId());
        if(StrUtil.isEmpty(totalKey)){
            return new ResponseJson(false,"未找到需要统计的字段信息");
        }
        final String[] rangeTime = processBusinessData.getRangeTime();
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(where("schoolId").is(processBusinessData.getSchoolId())
                        .and("status").is(Constant.OA.SUCCESS_COMPLETE)
                       // .and("processLibId").is(processBusinessData.getProcessLibId())
                        .and("schoolProcessId").is(processBusinessData.getSchoolProcessId())
                        .andOperator(new Criteria("createTime").gte(rangeTime[0]),new Criteria("createTime").lte(rangeTime[1]))
                )
                , Aggregation.group("processLibId").sum(totalKey)
                        .as("totalMoney").count().as("totalCount")
        );
        Document results = mot.aggregate(agg, mot.getCollectionName(ProcessBusinessData.class), Document.class).getUniqueMappedResult();
       if(Objects.isNull(results)){
           return new ResponseJson(0);
       }
        return new ResponseJson(results.get("totalMoney",0),results.get("totalCount",0));

    }
    private String getTotalKey(String key){
        switch(key){
            case "20181027163655004":
                return "formData.compensateMoney";
            case "1121702486948855809":
                return "formData.purchaseMoney";
            case "1121702486948855812":
                return "formData.repairFee";
                default:
                    return null;
        }
    }
    private void handleName(ProcessBusinessData ps, Map<String, Object> formData) {
        List<Building> list = new ArrayList<>();
        if("20181027163655009".equals(ps.getProcessLibId())){ //专用教室申请
            list = getSchoolNumberRooms(ps.getSchoolId());
        }else if("20181027163655006".equals(ps.getProcessLibId())){ //会议室申请
            list = getSchoolMeetingRooms(ps.getSchoolId());
        }
        String name = list.stream().filter(f -> StrUtil.equals(f.getId(), MapUtil.getStr(formData,"room"))).map(m -> m.getName()).findAny().orElse("");
        if(StrUtil.isNotEmpty(name)){
            formData.put("room",name);
        }
    }
    private  List<Building> getSchoolNumberRooms(String schoolId){
        return buildingFeign.findSchoolNumberRooms(schoolId);
    }
    private List<Building> getSchoolMeetingRooms(String schoolId){
        Building building = new Building();
        building.setSchoolId(schoolId);
        building.setTypeId("109");
        Pager pager = new Pager();
        pager.setPaging(false).setIncludes("id","name");
        building.setPager(pager);
        List<Building> buildings = buildingFeign.findBuildingListByCondition(building);
        return buildings;
    }
}
