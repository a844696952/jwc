package com.yice.edu.cn.dy.service.classManage.ruleRecord;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.classes.JwClaCadres;
import com.yice.edu.cn.common.pojo.jw.parent.Parent;
import com.yice.edu.cn.common.pojo.jw.schoolYear.SchoolYear;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherPost;
import com.yice.edu.cn.common.pojo.mes.classManage.ruleRecord.MesAppletsRuleRecord;
import com.yice.edu.cn.common.pojo.mes.classManage.ruleRecord.MesStudentRecordVo;
import com.yice.edu.cn.common.pojo.mes.wxPush.WxPushDetail;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import com.yice.edu.cn.common.util.date.DateUtils;
import com.yice.edu.cn.common.util.pinyin.PinyinUtil;
import com.yice.edu.cn.dy.dao.classManage.ruleRecord.IMesAppletsRuleRecordDao;
import com.yice.edu.cn.dy.dao.schoolManage.inspectRecord.IMesInspectRecordDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class MesAppletsRuleRecordService {
    @Autowired
    private IMesAppletsRuleRecordDao mesAppletsRuleRecordDao;
    @Autowired
    private CurSchoolYearService curSchoolYearService;
    @Autowired
    private IMesInspectRecordDao mesInspectRecordDao;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private SequenceId sequenceId;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public MesAppletsRuleRecord findMesAppletsRuleRecordById(String id) {
        return mesAppletsRuleRecordDao.findMesAppletsRuleRecordById(id);
    }

    @Transactional
    public void saveMesAppletsRuleRecord(MesAppletsRuleRecord mesAppletsRuleRecord) {
        Integer score = mesAppletsRuleRecord.getScore();
        if(score < 0){
            mesAppletsRuleRecord.setTagType(0);
        }else if(score > 0){
            mesAppletsRuleRecord.setTagType(1);
        }
        if (mesAppletsRuleRecord.getCommentatorPost() == null) {
            //查出操作人与职务
            List<MesAppletsRuleRecord> commentatorList = mesAppletsRuleRecordDao.findCommentatorByTidAndCid(mesAppletsRuleRecord);
            if(CollUtil.isNotEmpty(commentatorList)){
                mesAppletsRuleRecord.setCommentatorPost(1);
            }else {
                mesAppletsRuleRecord.setCommentatorPost(2);
            }
        } else {
            //设置操作班干部姓名
            mesAppletsRuleRecord.setCommentator(mesAppletsRuleRecordDao.findStudentById(mesAppletsRuleRecord.getCommentatorId()).getName());
        }
        //设置学年
        curSchoolYearService.setSchoolYearTerm(mesAppletsRuleRecord, mesAppletsRuleRecord.getSchoolId());
        //获取当前时间
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        mesAppletsRuleRecord.getStudentIds().forEach(s -> {
            mesAppletsRuleRecord.setId(sequenceId.nextId());
            mesAppletsRuleRecord.setStudentId(s);
            mesAppletsRuleRecord.setCreateTime(currentTime);
            mesAppletsRuleRecordDao.saveMesAppletsRuleRecord(mesAppletsRuleRecord);
        });
    }

    @Transactional(readOnly = true)
    public List<MesAppletsRuleRecord> findMesAppletsRuleRecordListByCondition(MesAppletsRuleRecord mesAppletsRuleRecord) {
        addCondition(mesAppletsRuleRecord);
        List<MesAppletsRuleRecord> list = mesAppletsRuleRecordDao.findMesAppletsRuleRecordListByCondition(mesAppletsRuleRecord);
        //处理删除修改标识
        if(mesAppletsRuleRecord.getCurrentTeacherId() != null && CollUtil.isNotEmpty(list)){
            //判断当前登陆人是否为班主任或者任课老师
            TeacherPost post = mesAppletsRuleRecordDao.findTeacherPostByTidAndCid(mesAppletsRuleRecord.getCurrentTeacherId(), mesAppletsRuleRecord.getClassesId());
            if(post != null){//班主任
                list.forEach(r -> {
                    if(r.getCommentatorPost() == 1 || r.getCommentatorPost() == 3){
                        r.setIsDelete(true);
                    }else if(r.getCommentatorPost() == 2){
                        r.setIsDelete(false);
                    }
                });
            }else {//任课老师
                list.forEach(r -> {
                    if(r.getCommentatorPost() == 1 || r.getCommentatorPost() == 3){
                        r.setIsDelete(false);
                    }else if(r.getCommentatorPost() == 2 && mesAppletsRuleRecord.getCurrentTeacherId().equals(r.getCommentatorId())){
                        r.setIsDelete(true);
                    }else {
                        r.setIsDelete(false);
                    }
                });
            }
        }
        return list;
    }

    private MesAppletsRuleRecord addCondition(MesAppletsRuleRecord mesAppletsRuleRecord){
        Integer searchType = mesAppletsRuleRecord.getSearchType();
        if(searchType != null){
            String[] time = null;
            switch (searchType){
                case 1://本周
                    time = DateUtils.getThisWeekRange();
                    break;
                case 2://上周
                    time = DateUtils.getLastWeekRange();
                    break;
                case 3://本月
                    time = DateUtils.getThisMonth();
                    break;
                default:
                    return mesAppletsRuleRecord;//跳出时间设置结束整个方法
            }
            mesAppletsRuleRecord.setSearchBeginTime(time[0]);
            mesAppletsRuleRecord.setSearchEndTime(time[1]);
        }else {//自定义时间
            mesAppletsRuleRecord.setSearchBeginTime(mesAppletsRuleRecord.getSearchBeginTime() + " 00:00:00");
            mesAppletsRuleRecord.setSearchEndTime(mesAppletsRuleRecord.getSearchEndTime()+ " 23:59:59");
            //获取当前学年
            SchoolYear schoolYear = new SchoolYear();
            schoolYear.setSchoolId(mesAppletsRuleRecord.getSchoolId());
            SchoolYear schoolYearByNowDate = mesInspectRecordDao.findSchoolYearByNowDate(schoolYear);
            if(schoolYearByNowDate != null){
                if(DateUtil.parse(mesAppletsRuleRecord.getSearchBeginTime()).getTime() < DateUtil.parse(schoolYearByNowDate.getLastTermBegin()).getTime()){
                    mesAppletsRuleRecord.setSearchBeginTime(schoolYearByNowDate.getLastTermBegin());
                }
            }
        }
        return mesAppletsRuleRecord;
    }

    /**
     *  查询当前登录班干部今日记录内容
     * @param mesAppletsRuleRecord
     * @return
     */
    @Transactional(readOnly = true)
    public List<MesAppletsRuleRecord> findTodayMesAppletsRuleRecordList(MesAppletsRuleRecord mesAppletsRuleRecord){
        return mesAppletsRuleRecordDao.findMesAppletsRuleRecordListByCondition(mesAppletsRuleRecord);
    }

    @Transactional(readOnly = true)
    public MesAppletsRuleRecord findOneMesAppletsRuleRecordByCondition(MesAppletsRuleRecord mesAppletsRuleRecord) {
        return mesAppletsRuleRecordDao.findOneMesAppletsRuleRecordByCondition(mesAppletsRuleRecord);
    }

    @Transactional(readOnly = true)
    public long findMesAppletsRuleRecordCountByCondition(MesAppletsRuleRecord mesAppletsRuleRecord) {
        addCondition(mesAppletsRuleRecord);
        return mesAppletsRuleRecordDao.findMesAppletsRuleRecordCountByCondition(mesAppletsRuleRecord);
    }

    @Transactional
    public void updateMesAppletsRuleRecord(MesAppletsRuleRecord mesAppletsRuleRecord) {
        mesAppletsRuleRecordDao.updateMesAppletsRuleRecord(mesAppletsRuleRecord);
    }

    @Transactional
    public void deleteMesAppletsRuleRecord(String id) {
        mesAppletsRuleRecordDao.deleteMesAppletsRuleRecord(id);
    }

    @Transactional
    public void deleteMesAppletsRuleRecordByCondition(MesAppletsRuleRecord mesAppletsRuleRecord) {
        mesAppletsRuleRecordDao.deleteMesAppletsRuleRecordByCondition(mesAppletsRuleRecord);
    }

    @Transactional
    public void batchSaveMesAppletsRuleRecord(List<MesAppletsRuleRecord> mesAppletsRuleRecords) {
        mesAppletsRuleRecords.forEach(mesAppletsRuleRecord -> mesAppletsRuleRecord.setId(sequenceId.nextId()));
        mesAppletsRuleRecordDao.batchSaveMesAppletsRuleRecord(mesAppletsRuleRecords);
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public List<MesAppletsRuleRecord> findHighFrequencyMesAppletsRuleRecord(MesAppletsRuleRecord mesAppletsRuleRecord) {
        setSearchTime(mesAppletsRuleRecord);
        List<MesAppletsRuleRecord> list = mesAppletsRuleRecordDao.findHighFrequencyMesAppletsRuleRecord(mesAppletsRuleRecord);
        return list.stream().filter(m -> m.getRecordCount() >= 3).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MesAppletsRuleRecord findMesAppletsRuleRecordByStudentIdAndSearchTime(MesAppletsRuleRecord mesAppletsRuleRecords) {
        setSearchTime(mesAppletsRuleRecords);
        MesAppletsRuleRecord mesAppletsRuleRecord = mesAppletsRuleRecordDao.findMesAppletsRuleRecordByStudentIdAndSearchTime(mesAppletsRuleRecords);
        if (mesAppletsRuleRecord == null) {
            mesAppletsRuleRecord = new MesAppletsRuleRecord() {{this.setAddScore(0);this.setReduceScore(0);this.setRecordCount(0);}};
        }
        return mesAppletsRuleRecord;
    }

    @Transactional(readOnly = true)
    public List<MesAppletsRuleRecord> findScoreChangeByDay(MesAppletsRuleRecord mesAppletsRuleRecord) {
        setSearchTime(mesAppletsRuleRecord);
        List<MesAppletsRuleRecord> list = mesAppletsRuleRecordDao.findScoreChangeByDay(mesAppletsRuleRecord);
        mesAppletsRuleRecord.setSearchBeginTime(mesAppletsRuleRecord.getSearchBeginTime().split(" ")[0]);
        mesAppletsRuleRecord.setSearchEndTime(mesAppletsRuleRecord.getSearchEndTime().split(" ")[0]);
        List<MesAppletsRuleRecord> ruleRecords = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtil.parse(mesAppletsRuleRecord.getSearchBeginTime()));
        setData(cal,mesAppletsRuleRecord,list,ruleRecords);
        return ruleRecords;
    }

    @Transactional(readOnly = true)
    public void setData(Calendar cal, MesAppletsRuleRecord mesAppletsRuleRecord, List<MesAppletsRuleRecord> list, List<MesAppletsRuleRecord> ruleRecords) {
        if (cal.getTime().getTime() > DateUtil.parse(mesAppletsRuleRecord.getSearchEndTime()).getTime()){
            return;
        }
        int i=0;
        for (MesAppletsRuleRecord appletsRuleRecord : list.stream().filter(m -> m.getCreateTime().contains(DateUtil.format(cal.getTime(), "yyyy-MM-dd"))).collect(Collectors.toList())) {
            if (appletsRuleRecord!=null) {
                i += appletsRuleRecord.getScore();
            }
        }
        int finalI = i;
        MesAppletsRuleRecord mesAppletsRuleRecord1 = new MesAppletsRuleRecord() {{this.setRecordCount(finalI);this.setCreateTime(DateUtil.format(cal.getTime(), "yyyy/MM/dd"));}};
        ruleRecords.add(mesAppletsRuleRecord1);
        cal.add(Calendar.DAY_OF_YEAR,1);
        setData(cal,mesAppletsRuleRecord,list,ruleRecords);
    }

    @Transactional(readOnly = true)
    public List<MesAppletsRuleRecord> findStudentRankChange(MesAppletsRuleRecord mesAppletsRuleRecord){
        setSearchTime(mesAppletsRuleRecord);
        Student student=mesAppletsRuleRecordDao.findStudentById(mesAppletsRuleRecord.getStudentId());
        mesAppletsRuleRecord.setClassesId(student.getClassesId());
        mesAppletsRuleRecord.setSchoolId(student.getSchoolId());
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtil.parse(mesAppletsRuleRecord.getSearchBeginTime()));
        List<MesAppletsRuleRecord> list = new ArrayList<>();
        setRankChangeData(cal,mesAppletsRuleRecord,list);
        return list;
    }

    public void setRankChangeData(Calendar cal, MesAppletsRuleRecord mesAppletsRuleRecord, List<MesAppletsRuleRecord> list) {
        if (cal.getTime().getTime()>DateUtil.parse(mesAppletsRuleRecord.getSearchEndTime()).getTime()){
            return;
        }
        MesAppletsRuleRecord find = new MesAppletsRuleRecord();
        find.setSchoolId(mesAppletsRuleRecord.getSchoolId());
        find.setClassesId(mesAppletsRuleRecord.getClassesId());
        find.setSearchBeginTime(DateUtil.format(cal.getTime(),"yyyy-MM-dd 00:00:00"));
        find.setSearchEndTime(DateUtil.format(cal.getTime(),"yyyy-MM-dd 23:59:59"));
        List<MesAppletsRuleRecord> classRuleRecordList = findClassRuleRecordList(find);
        for (MesAppletsRuleRecord appletsRuleRecord : classRuleRecordList) {
            if (mesAppletsRuleRecord.getStudentId().equals(appletsRuleRecord.getStudentId())){
                appletsRuleRecord.setCreateTime(DateUtil.format(cal.getTime(),"yyyy-MM-dd"));
                list.add(appletsRuleRecord);
            }
        }
        cal.add(Calendar.DAY_OF_YEAR,1);
        setRankChangeData(cal,mesAppletsRuleRecord,list);
    }

    @Transactional(readOnly = true)
    public void setSearchTime(MesAppletsRuleRecord mesAppletsRuleRecord) {
        if (mesAppletsRuleRecord.getSearchType() !=null) {
            if (mesAppletsRuleRecord.getSearchType() == 1) {
                String[] thisWeekRange = DateUtils.getThisWeekRange();
                mesAppletsRuleRecord.setSearchBeginTime(thisWeekRange[0]);
                mesAppletsRuleRecord.setSearchEndTime(thisWeekRange[1]);
            } else if (mesAppletsRuleRecord.getSearchType() == 2) {
                String[] lastWeekRange = DateUtils.getLastWeekRange();
                mesAppletsRuleRecord.setSearchBeginTime(lastWeekRange[0]);
                mesAppletsRuleRecord.setSearchEndTime(lastWeekRange[1]);
            } else if (mesAppletsRuleRecord.getSearchType() == 3) {
                String[] thisMonth = DateUtils.getThisMonth();
                mesAppletsRuleRecord.setSearchBeginTime(thisMonth[0]);
                mesAppletsRuleRecord.setSearchEndTime(thisMonth[1]);
            }else if(StringUtils.isNotEmpty(mesAppletsRuleRecord.getSearchBeginTime()) && StringUtils.isNotEmpty(mesAppletsRuleRecord.getSearchEndTime())){
                //判断是否是今天
                if(DateUtil.parse(mesAppletsRuleRecord.getSearchEndTime(), Constant.DATE_FORMATTER_DAY).compareTo(DateUtil.parse(DateUtil.now(),Constant.DATE_FORMATTER_DAY)) == 0){
                    mesAppletsRuleRecord.setSearchEndTime(DateUtil.now());
                }else{
                    mesAppletsRuleRecord.setSearchEndTime(DateUtils.getOriginalDateTime(mesAppletsRuleRecord.getSearchBeginTime(),2));
                }
                mesAppletsRuleRecord.setSearchBeginTime(DateUtils.getOriginalDateTime(mesAppletsRuleRecord.getSearchBeginTime(),1));
            }
        }
    }

    /**
     * 查询班级榜单饼状图
     * @param mesAppletsRuleRecords
     * @return
     */
    public MesAppletsRuleRecord findClassListByRange(MesAppletsRuleRecord mesAppletsRuleRecords){
        List<MesAppletsRuleRecord> classRuleRecordList = mesAppletsRuleRecordDao.findClassRuleRecordByRange(mesAppletsRuleRecords);
        MesAppletsRuleRecord mesAppletsRuleRecord=new MesAppletsRuleRecord();
        if(CollUtil.isNotEmpty(classRuleRecordList)){
            Map<Integer, List<MesAppletsRuleRecord>> recordMap = classRuleRecordList.stream().sorted(Comparator.comparing(MesAppletsRuleRecord::getTagType))
                    .collect(Collectors.groupingBy(MesAppletsRuleRecord::getTagType, LinkedHashMap::new,Collectors.toList()));
            recordMap.forEach((k,v)->{
                 switch (k){
                     case 0:
                         mesAppletsRuleRecord.setReduceScore(v.stream().mapToLong(MesAppletsRuleRecord::getScore).sum());
                         break;
                     case 1:
                         mesAppletsRuleRecord.setAddScore(v.stream().mapToLong(MesAppletsRuleRecord::getScore).sum());
                         break;
                     default:
                         mesAppletsRuleRecord.setReduceScore(0);
                         mesAppletsRuleRecord.setAddScore(0);
                         break;
                 }
            });
         mesAppletsRuleRecord.setRecordCount(classRuleRecordList.size());
         mesAppletsRuleRecord.setStuRecordCount(classRuleRecordList.stream().collect(Collectors.groupingBy(MesAppletsRuleRecord::getStudentId)).size());
        }
        return mesAppletsRuleRecord;
    }

    @Transactional(readOnly = true)
    public List<Parent> findJwParentByStudentId(String studentId) {
        return mesAppletsRuleRecordDao.findJwParentByStudentId(studentId);
    }

    @Transactional(readOnly = true)
    public List<MesAppletsRuleRecord> findTeachingClassByTid(String teacherId,String schoolId){
        List<MesAppletsRuleRecord> list = mesAppletsRuleRecordDao.findTeachingClassByTid(teacherId,schoolId);
        if(CollUtil.isNotEmpty(list)){
            Collections.sort(list, Comparator.comparingInt((MesAppletsRuleRecord o) -> Integer.parseInt(o.getGradeId())).thenComparingInt(o -> Integer.parseInt(o.getClassNumber())));
            List<MesAppletsRuleRecord> list1 = list.stream().filter(x -> x.getOperatorPostName()!=null?x.getOperatorPostName().equals("班主任"):false).collect(Collectors.toList());
            List<MesAppletsRuleRecord> list2 = list.stream().filter(x -> x.getOperatorPostName() == null || !x.getOperatorPostName().equals("班主任")).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(list1)){
                if(CollUtil.isNotEmpty(list2)){
                    list1.addAll(list2.stream().filter(x -> {
                        boolean flag = false;
                        for (MesAppletsRuleRecord r : list1) {
                            if(!x.getClassesId().equals(r.getClassesId())){
                                flag = true;
                            }
                        }
                        return flag;
                    }).collect(Collectors.toList()));
                }
                return list1;
            }else if(CollUtil.isNotEmpty(list2)){
                return list2;
            }
        }
        return list;
    }

    /**
     * 班级管理学生列表-班牌端
     * @param classId
     * @return
     */
    @Transactional(readOnly = true)
    public List<MesStudentRecordVo> findMesStudentRecordVoListByCid(String classId, String schoolId){
        return mesAppletsRuleRecordDao.findMesStudentRecordVoListByCid(addCondition(classId,schoolId));
    }

    /**
     * 班级管理学生列表-教师端
     * @param classId
     * @param schoolId
     * @return
     */
    @Transactional(readOnly = true)
    public List<Map<String,Object>> findMesStudentRecordVoListByClassId(String classId, String schoolId){
        List<MesStudentRecordVo> mesAppletsRuleRecordList = mesAppletsRuleRecordDao.findMesStudentRecordVoListByCid(addCondition(classId, schoolId));
        List<Map<String,Object>> list = new ArrayList<>();
        if(CollUtil.isNotEmpty(mesAppletsRuleRecordList)){
            Map<String,List<MesStudentRecordVo>> map = new TreeMap<>();
            mesAppletsRuleRecordList.forEach(m -> {
                m.setInitial(PinyinUtil.toPinYin(m.getName()));
                if(map.containsKey(m.getInitial())){
                    map.get(m.getInitial()).add(m);
                }else {
                    List<MesStudentRecordVo> tmpList = new ArrayList<MesStudentRecordVo>();
                    tmpList.add(m);
                    map.put(m.getInitial(),tmpList);
                }
            });
            Map<String,Object> treeMap = null;
            for (String initial : map.keySet()) {
                treeMap = new TreeMap<>();
                treeMap.put("region",initial);
                treeMap.put("items",map.get(initial));
                list.add(treeMap);
            }
        }
        return list;
    }

    private MesAppletsRuleRecord addCondition(String classId, String schoolId){
        //封装当天时间、班级id以及学校id
        MesAppletsRuleRecord mesAppletsRuleRecord = new MesAppletsRuleRecord();
        mesAppletsRuleRecord.setClassesId(classId);
        mesAppletsRuleRecord.setSchoolId(schoolId);
        mesAppletsRuleRecord.setSearchBeginTime(DateUtils.getOriginalDateTime(DateUtil.now(),1));
        mesAppletsRuleRecord.setSearchEndTime(DateUtils.getOriginalDateTime(DateUtil.now(),2));
        return mesAppletsRuleRecord;
    }

    /**
     * 获取班级全部榜单
     * @param mesAppletsRuleRecord
     * @return
     */
    public List<MesAppletsRuleRecord> findClassRankList(MesAppletsRuleRecord mesAppletsRuleRecord){
        List<MesAppletsRuleRecord> classRankList=new ArrayList<>();
        //获取总分榜
        List<MesAppletsRuleRecord> classRuleRecordList = findClassRuleRecordList(mesAppletsRuleRecord);
        if(CollUtil.isNotEmpty(classRuleRecordList)){
            LinkedHashMap<Integer, List<MesAppletsRuleRecord>> totalMap = classRuleRecordList.stream().sorted(Comparator.comparing(MesAppletsRuleRecord::getSortNum))
                    .collect(Collectors.groupingBy(MesAppletsRuleRecord::getSortNum, LinkedHashMap::new, Collectors.toList()));
            setMesRecordRankInfo(classRankList, totalMap, 1, 1);

            LinkedHashMap<Integer, List<MesAppletsRuleRecord>> improveMap = classRuleRecordList.stream().sorted(Comparator.comparing(MesAppletsRuleRecord::getSortNum).reversed())
                    .collect(Collectors.groupingBy(MesAppletsRuleRecord::getSortNum, LinkedHashMap::new, Collectors.toList()));
            //获取倒叙之后的第一个排名编号
            Integer index = improveMap.keySet().toArray(new Integer[0])[0];
            //急需改进学生
            setMesRecordRankInfo(classRankList, improveMap, index, 2);
        }
        //获取进步榜学生
        List<MesAppletsRuleRecord> classRuleAdvanceList = findClassRuleAdvanceList(mesAppletsRuleRecord, 1);
        if(CollUtil.isNotEmpty(classRuleAdvanceList)){
            LinkedHashMap<Integer, List<MesAppletsRuleRecord>> advanceMap = classRuleAdvanceList.stream().collect(Collectors.groupingBy(MesAppletsRuleRecord::getSortNum, LinkedHashMap::new, Collectors.toList()));
            setMesRecordRankInfo(classRankList, advanceMap, 1, 3);
        }else{
            MesAppletsRuleRecord mesAdvanceList = new MesAppletsRuleRecord();
            mesAdvanceList.setStuType(3);
            classRankList.add(mesAdvanceList);
        }
        //获取退步榜
        List<MesAppletsRuleRecord> classRuleBackList = findClassRuleAdvanceList(mesAppletsRuleRecord, 2);
        if(CollUtil.isNotEmpty(classRuleBackList)){
            LinkedHashMap<Integer, List<MesAppletsRuleRecord>> ruleBackMap = classRuleBackList.stream().collect(Collectors.groupingBy(MesAppletsRuleRecord::getSortNum, LinkedHashMap::new, Collectors.toList()));
            setMesRecordRankInfo(classRankList,ruleBackMap,1,4);
        }else{
            MesAppletsRuleRecord mesBackList = new MesAppletsRuleRecord();
            mesBackList.setStuType(4);
            classRankList.add(mesBackList);
        }
        return classRankList;
    }

    private void setMesRecordRankInfo(List<MesAppletsRuleRecord> classRankList, LinkedHashMap<Integer, List<MesAppletsRuleRecord>> advanceMap, int i, int i2) {
        MesAppletsRuleRecord mesAppletsAdvanceList = new MesAppletsRuleRecord();
        BeanUtils.copyProperties(advanceMap.get(i).get(0), mesAppletsAdvanceList);
        mesAppletsAdvanceList.setStuType(i2);
        mesAppletsAdvanceList.setRecordCount(advanceMap.get(i).size());
        classRankList.add(mesAppletsAdvanceList);
    }

    /**
         * 查询班级总榜单
         * @param mesAppletsRuleRecord
         * @return
         */
    public List<MesAppletsRuleRecord> findClassRuleRecordList(MesAppletsRuleRecord mesAppletsRuleRecord){
        List<MesAppletsRuleRecord> classRuleRecordList = mesAppletsRuleRecordDao.findClassRuleRecordList(mesAppletsRuleRecord);
        if(CollUtil.isNotEmpty(classRuleRecordList)){
            //在按分数分组
            LinkedHashMap<Long, List<MesAppletsRuleRecord>> scoreMap = classRuleRecordList.stream().sorted(Comparator.comparing(MesAppletsRuleRecord::getTotalScore).reversed()).
                    collect(Collectors.groupingBy(MesAppletsRuleRecord::getTotalScore, LinkedHashMap::new, Collectors.toList()));
            for(int k=0;k<classRuleRecordList.size();k++){
                if(scoreMap.get(classRuleRecordList.get(k).getTotalScore()).size()>1){
                    classRuleRecordList.get(k).setSortNum(findListIndexByScore(classRuleRecordList,classRuleRecordList.get(k))+1);
                }else{
                    classRuleRecordList.get(k).setSortNum(k+1);
                }
            }
        }
        return classRuleRecordList.stream().sorted(Comparator.comparing(MesAppletsRuleRecord::getSortNum)).collect(Collectors.toList());
    }


    public int findListIndexByScore(List<MesAppletsRuleRecord> list,MesAppletsRuleRecord mesAppletsRuleRecord){
        int result =-1;
        if(CollUtil.isNotEmpty(list)){
            for(int i=0;i<list.size();i++){
                if(list.get(i).getTotalScore() == mesAppletsRuleRecord.getTotalScore()){
                    result=i;
                    break;
                }

            }

        }
        return result;
    }


    /**
     * 班级榜单
     * @param mesAppletsRuleRecord
     * @param type 1--进步榜 2--退步榜单
     * @return
     */
    public List<MesAppletsRuleRecord> findClassRuleAdvanceList(MesAppletsRuleRecord mesAppletsRuleRecord,int type){
        MesAppletsRuleRecord lastMesAppletsRuleRecord=new MesAppletsRuleRecord();
        BeanUtils.copyProperties(mesAppletsRuleRecord,lastMesAppletsRuleRecord);
        setAdvanceSearchTime(mesAppletsRuleRecord, lastMesAppletsRuleRecord);
        //查询当前选择时间段排行榜单
        List<MesAppletsRuleRecord> classRuleRecordCurrList = findClassRuleRecordList(mesAppletsRuleRecord);
        //查询对比周期排行榜单
        List<MesAppletsRuleRecord> classRuleRecordLastList = findClassRuleRecordList(lastMesAppletsRuleRecord);
        List<MesAppletsRuleRecord> advanceClassRuleList=new ArrayList<>();
        for(int i=0;i<classRuleRecordCurrList.size();i++){
            for(int k=0;k<classRuleRecordLastList.size();k++){
                if(Objects.equals(classRuleRecordCurrList.get(i).getStudentId(),classRuleRecordLastList.get(k).getStudentId())){
                    if(type == 1){
                        //进步榜
                        if((classRuleRecordCurrList.get(i).getSortNum()-classRuleRecordLastList.get(k).getSortNum())<0){
                            MesAppletsRuleRecord record=new MesAppletsRuleRecord();
                            BeanUtils.copyProperties(classRuleRecordCurrList.get(i),record);
                            record.setSortNum(-1);
                            record.setRank(Math.abs(classRuleRecordCurrList.get(i).getSortNum()-classRuleRecordLastList.get(k).getSortNum()));
                            advanceClassRuleList.add(record);
                        }
                    }else{
                        //退步榜
                        if((classRuleRecordCurrList.get(i).getSortNum()-classRuleRecordLastList.get(k).getSortNum())>0){
                            MesAppletsRuleRecord record=new MesAppletsRuleRecord();
                            BeanUtils.copyProperties(classRuleRecordCurrList.get(i),record);
                            record.setSortNum(-1);
                            record.setRank(classRuleRecordCurrList.get(i).getSortNum()-classRuleRecordLastList.get(k).getSortNum());
                            advanceClassRuleList.add(record);
                        }
                    }
                }
            }
        }
       if(CollUtil.isNotEmpty(advanceClassRuleList)){
           advanceClassRuleList=advanceClassRuleList.stream().sorted(Comparator.comparing(MesAppletsRuleRecord::getRank).reversed()).collect(Collectors.toList());
           Map<Long, List<MesAppletsRuleRecord>> rankMap = advanceClassRuleList.stream().sorted(Comparator.comparing(MesAppletsRuleRecord::getRank).reversed())
                   .collect(Collectors.groupingBy(MesAppletsRuleRecord::getRank,LinkedHashMap::new,Collectors.toList()));
           for(int i=0;i<advanceClassRuleList.size();i++){
               if(rankMap.get(advanceClassRuleList.get(i).getRank()).size()>1){
                   advanceClassRuleList.get(i).setSortNum(advanceClassRuleList.indexOf(advanceClassRuleList.get(i))+1);
               }else{
                   advanceClassRuleList.get(i).setSortNum(i+1);
               }
           }
       }
        return advanceClassRuleList.stream().sorted(Comparator.comparing(MesAppletsRuleRecord::getSortNum)).collect(Collectors.toList());
    }

    /**
     * 设置排行榜对比时间周期
     * @param mesAppletsRuleRecord
     * @param lastMesAppletsRuleRecord
     */
    private void setAdvanceSearchTime(MesAppletsRuleRecord mesAppletsRuleRecord, MesAppletsRuleRecord lastMesAppletsRuleRecord) {
        switch (mesAppletsRuleRecord.getSearchType()){
            case 1:
                //本周
                String[] thisWeekRange = DateUtils.getThisWeekRange();
                mesAppletsRuleRecord.setSearchBeginTime(thisWeekRange[0]);
                mesAppletsRuleRecord.setSearchEndTime(thisWeekRange[1]);
                String[] lastWeekRange = DateUtils.getLastWeekRange();
                lastMesAppletsRuleRecord.setSearchBeginTime(lastWeekRange[0]);
                lastMesAppletsRuleRecord.setSearchEndTime(lastWeekRange[1]);
                break;
            case 2:
                //上周
                String[] lastWeek = DateUtils.getLastWeekRange();
                mesAppletsRuleRecord.setSearchBeginTime(lastWeek[0]);
                mesAppletsRuleRecord.setSearchEndTime(lastWeek[1]);
                String[] lastLastWeekRange = DateUtils.getLastLastWeekRange();
                lastMesAppletsRuleRecord.setSearchBeginTime(lastLastWeekRange[0]);
                lastMesAppletsRuleRecord.setSearchEndTime(lastLastWeekRange[1]);
                break;
            case 3:
                //本月
                String[] thisMonth = DateUtils.getThisMonth();
                mesAppletsRuleRecord.setSearchBeginTime(thisMonth[0]);
                mesAppletsRuleRecord.setSearchEndTime(thisMonth[1]);
                String[] lastMonth = DateUtils.getLastMonth();
                lastMesAppletsRuleRecord.setSearchBeginTime(lastMonth[0]);
                lastMesAppletsRuleRecord.setSearchEndTime(lastMonth[1]);
                break;
            default:
                //自定义
                break;
        }
    }









/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/


    public List<Student> findStudentByParentId(String parentId) {
        return mesAppletsRuleRecordDao.findStudentByParentId(parentId);
    }

    public List<Teacher> findClassTeacherTelByClassesId(String classesId) {
        return mesAppletsRuleRecordDao.findClassTeacherTelByClassesId(classesId);
    }

    @Transactional(readOnly = true)
    public Map findCurrentTermTime(String studentId) {
        String schoolId = mesInspectRecordDao.findSchoolIdBystudentId(studentId);
        return findCurrentTermTimeBySchoolId(schoolId);
    }

    @Transactional(readOnly = true)
    public Map findCurrentTermTimeBySchoolId(String schoolId) {
        SchoolYear schoolYear = mesAppletsRuleRecordDao.findCurrentTermTime(schoolId);
        LocalDate lastTermBeginDate = LocalDate.parse(schoolYear.getLastTermBegin());
        LocalDate nextTermBeginDate = LocalDate.parse(schoolYear.getNextTermBegin());
        LocalDate now = LocalDate.now();
        HashMap<String, String> map = new HashMap<>(16);
        if(now.isAfter(lastTermBeginDate)||now.isEqual(lastTermBeginDate)&&now.isBefore(nextTermBeginDate)){
            map.put("startDate",schoolYear.getLastTermBegin());
            map.put("endDate",nextTermBeginDate.minusDays(1).toString());
        }else{
            map.put("startDate",schoolYear.getNextTermBegin());
            map.put("endDate",lastTermBeginDate.plusYears(1).minusDays(1).toString());
        }
        return map;
    }

    @Transactional(readOnly = true)
    public Boolean findJwClaCadresBySid(String studentId){
        List<JwClaCadres> list = mesAppletsRuleRecordDao.findJwClaCadresBySid(studentId);
        if(CollUtil.isNotEmpty(list)){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 查出记录表当天所有数据关联的openId
     * @return
     */
    @Transactional(readOnly = true)
    public List<MesStudentRecordVo> findTodayRecordOidAndSid(){
        String searchBeginTime = DateUtils.getOriginalDateTime(DateUtil.now(),1);
        String searchEndTime = DateUtils.getOriginalDateTime(DateUtil.now(),2);
        return mesAppletsRuleRecordDao.findTodayRecordOidAndSid(searchBeginTime,searchEndTime);
    }

    @Transactional(readOnly = true)
    public List<String> findClassIdByWeeks(){
        String[] thisWeekRange = DateUtils.getThisWeekRange();
        String searchBeginTime = thisWeekRange[0];
        String searchEndTime = thisWeekRange[1];
        return mesAppletsRuleRecordDao.findClassIdByWeeks(searchBeginTime,searchEndTime);
    }

    /**
     * 在记录表根据学生id查出他所关联的openId
     * @param studentId
     * @return
     */
    @Transactional(readOnly = true)
    public List<MesStudentRecordVo> findMesStudentRecordVoBySid(String studentId){
        return mesAppletsRuleRecordDao.findMesStudentRecordVoBySid(studentId);
    }

    //查询当前家长的通知列表
    public List<WxPushDetail> findWxPushDetailListByOpenId(String openId){
        return mongoTemplate.find(query(where("touser").is(openId)).with(new Sort(Sort.Direction.DESC, "createTime")), WxPushDetail.class);
    }

    //家长端通知列表标记消息为已读
    public void updateWxPushDetailRead(String id){
        mongoTemplate.updateMulti(query(where("id").is(id)), Update.update("isRead",true),WxPushDetail.class);
    }

    public long findStudentCount(String studentId) {
        return mesAppletsRuleRecordDao.findStudentCount(studentId);
    }
}
