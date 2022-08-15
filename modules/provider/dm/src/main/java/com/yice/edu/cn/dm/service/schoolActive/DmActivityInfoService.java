package com.yice.edu.cn.dm.service.schoolActive;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.dm.schoolActive.*;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.common.pojo.ts.jpush.PushDetail;
import com.yice.edu.cn.common.util.date.DateUtils;
import com.yice.edu.cn.dm.dao.schoolActive.IDmActivityInfoDao;
import com.yice.edu.cn.dm.dao.schoolActive.IDmActivityItemDao;
import com.yice.edu.cn.dm.dao.schoolActive.IDmActivitySiginUpDao;
import com.yice.edu.cn.dm.dao.schoolActive.IDmAttachmentFileDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class DmActivityInfoService {
    @Autowired
    private IDmActivityInfoDao dmActivityInfoDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private IDmActivityItemDao dmActivityItemDao;
    @Autowired
    private IDmActivitySiginUpDao dmActivitySiginUpDao;
    @Autowired
    private IDmAttachmentFileDao dmAttachmentFileDao;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private MongoTemplate mongoTemplate;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public DmActivityInfo findDmActivityInfoById(String id) {
        return dmActivityInfoDao.findDmActivityInfoById(id);
    }
    @Transactional
    public void saveDmActivityInfo(DmActivityInfo dmActivityInfo) {
        dmActivityInfo.setActivityId(sequenceId.nextId());
        List<DmActivityItem> dmActivityItems = dmActivityInfo.getDmActivityItems();
        List<DmAttachmentFile> files = dmActivityInfo.getFiles();
        saveFiles(files,dmActivityInfo);
        saveInfo(dmActivityInfo);
        if(CollUtil.isNotEmpty(dmActivityItems)){
            dmActivityItems.forEach(d -> {
                d.setSchoolId(dmActivityInfo.getSchoolId());
                d.setId(sequenceId.nextId());
                d.setActivityId(dmActivityInfo.getActivityId());
                d.setCreateTime(DateUtils.Nowss());
            });
            dmActivityItemDao.batchSaveDmActivityItem(dmActivityItems);
        }
        sendMsgByGradeId(dmActivityInfo,dmActivityInfo.getNotifyObj());
    }

    @Transactional(readOnly = true)
    public List<DmActivityInfo> findDmActivityInfoListByCondition(DmActivityInfo dmActivityInfo) {
        int begin=-1;
        int end=-1;
        if(Objects.nonNull(dmActivityInfo.getPager())){
            Integer page = dmActivityInfo.getPager().getPage();
            int pageSize = dmActivityInfo.getPager().getPageSize();
            begin=(page-1)*pageSize+1;
            end=page*pageSize;
            dmActivityInfo.getPager().setPaging(false);
        }
        List<DmActivityInfo> dmActivityInfoListByCondition = dmActivityInfoDao.findDmActivityInfoListByCondition(dmActivityInfo);
        List<DmActivityInfo> dmActivityInfos = pageDmActivityInfos(dmActivityInfoListByCondition,begin,end);
        return  dmActivityInfos;

    }

    private List<DmActivityInfo> pageDmActivityInfos(List<DmActivityInfo> dmActivityInfoListByCondition,int begin,int end) {
        List<DmActivityInfo> result=new ArrayList<>();
        if(CollUtil.isNotEmpty(dmActivityInfoListByCondition)){
            Map<String, List<DmActivityInfo>> collect = dmActivityInfoListByCondition.stream().sorted(Comparator.comparing(DmActivityInfo::getGradeId)).collect(Collectors.groupingBy(x -> x.getActivityId()));
            Iterator<String> iterator = collect.keySet().iterator();
            while (iterator.hasNext()){
                List<DmActivityInfo> dmActivityInfos = collect.get(iterator.next());
                String gradeName = dmActivityInfos.stream().map(x -> x.getGradeName()).collect(Collectors.joining(" "));
                dmActivityInfos.get(0).setGradeNames(gradeName);
                result.add(dmActivityInfos.get(0));
            }
        }
        result.sort((o1, o2) -> {
            LocalDateTime o1Time = LocalDateTime.parse(o1.getCreateTime(),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime o2Time = LocalDateTime.parse(o2.getCreateTime(),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            return o1Time.isBefore(o2Time)||o1Time.isEqual(o2Time)?1:-1;
        });
        if(end<result.size()){
            result=result.subList(begin-1,end);
        }else if(begin-1 > result.size()) {
            return result;
        } else{
            result=result.subList(begin-1,result.size());
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<DmActivityInfo> findDmActivityInfoByActivityId(DmActivityInfo dmActivityInfo){
      return dmActivityInfoDao.findDmActivityInfoByActivityId(dmActivityInfo);
    }


    /**
     * 构建导出模板 根据班级
     * @param dmActivityInfo
     * @return
     */
    public ExportActiveByClass findClassItemByActiveId(DmActivityInfo dmActivityInfo){
        List<DmActivityInfo> dmActivityInfoByActivityId = findDmActivityInfoByActivityId(dmActivityInfo);
        ExportActiveByClass exportActiveByClass=new ExportActiveByClass();
        if(CollUtil.isNotEmpty(dmActivityInfoByActivityId)){
            setActiveClassInfo(dmActivityInfoByActivityId, exportActiveByClass);
            Map<String, List<DmActivityInfo>> collect = dmActivityInfoByActivityId.stream().sorted(Comparator.comparing(DmActivityInfo::getGradeId)).
                    filter(q->StringUtils.isNoneBlank(q.getClassesId()) && StringUtils.isNoneBlank(q.getStudentName())).
                    collect(Collectors.groupingBy(x -> x.getClassesId(),LinkedHashMap::new,Collectors.toList()));
            List<ActivityItemClass> dmActivityClass=new ArrayList<>();
            collect.forEach((k,v)->{
                ActivityItemClass activityClass=new ActivityItemClass();
                activityClass.setClassesId(k);
                activityClass.setClassName(v.get(0).getClassName());
                Map<String, List<DmActivityInfo>> itemListInfo= v.stream().filter(z->Objects.nonNull(z.getItemId())).collect(Collectors.groupingBy(p -> p.getItemId()));
                List<DmActivityItem> items=new ArrayList<>();
                itemListInfo.forEach((d,h)->{
                    DmActivityItem dmActivityItem = new DmActivityItem();
                    dmActivityItem.setClassesId(v.get(0).getClassesId());
                    dmActivityItem.setClassName(v.get(0).getClassName());
                    dmActivityItem.setProjectName(h.get(0).getProjectName());
                    //去除重复的学生
                    Set<DmActivityInfo> set=new TreeSet<>(Comparator.comparing(DmActivityInfo::getStudentName));
                    set.addAll(h);
                    dmActivityItem.setNamesCount(set.size());
                    String names = StringUtils.join(set.stream().map(y -> y.getStudentName()).collect(Collectors.toList()), "、");
                    dmActivityItem.setNames(names);
                    items.add(dmActivityItem);
                });
                activityClass.setDmActivityItemList(items);
                dmActivityClass.add(activityClass);
            });
            exportActiveByClass.setActivityClassList(dmActivityClass);
        }
        return exportActiveByClass;
    }

    private void setActiveClassInfo(List<DmActivityInfo> dmActivityInfoByActivityId, ExportActiveByClass exportActiveByClass) {
        exportActiveByClass.setActivityName(dmActivityInfoByActivityId.get(0).getActivityName());
        exportActiveByClass.setEndDate(dmActivityInfoByActivityId.get(0).getEndDate());
        exportActiveByClass.setIsCustomize(dmActivityInfoByActivityId.get(0).getIsCustomize());
        exportActiveByClass.setContent(dmActivityInfoByActivityId.get(0).getActivityContent());
    }

    /**
     * 构建导出模板 根据项目
     * @param dmActivityInfo
     * @return
     */
    public ExportActiveByItem findItemByActiveId(DmActivityInfo dmActivityInfo){
        List<DmActivityInfo> dmActivityInfoByActivityId = findDmActivityInfoByActivityId(dmActivityInfo);
        ExportActiveByItem exportActiveByItem=new ExportActiveByItem();
        if(CollUtil.isNotEmpty(dmActivityInfoByActivityId)){
            setActiveInfo(dmActivityInfoByActivityId, exportActiveByItem);
            Map<String, List<DmActivityInfo>> collect = dmActivityInfoByActivityId.stream().filter(q->Objects.nonNull(q.getNumber()) && Objects.nonNull(q.getClassesId()))
                    .sorted(Comparator.comparing(DmActivityInfo::getGradeId))
                    .collect(Collectors.groupingBy(x -> x.getItemId(),LinkedHashMap::new,Collectors.toList()));
            List<ActivityItem> items=new ArrayList<>();
            collect.forEach((k,v)->{
                List<ActivityClass> activityClassList=new ArrayList<>();
                ActivityItem item=new ActivityItem();
                item.setProjectName(v.get(0).getProjectName());
                item.setItemId(k);
                Map<String, List<DmActivityInfo>> classCollection = v.stream().filter(z->Objects.nonNull(z.getClassesId()))
                        .sorted(Comparator.comparing(DmActivityInfo::getGradeId))
                        .collect(Collectors.groupingBy(x -> x.getClassesId(),LinkedHashMap::new,Collectors.toList()));
                classCollection.forEach((z,p)->{
                    ActivityClass activityClass=new ActivityClass();
                    activityClass.setClassName(p.get(0).getClassName());
                    activityClass.setClassesId(z);
                    //去除重复的学生
                    p=p.stream().filter(n->Objects.nonNull(n.getStudentName())).collect(Collectors.toList());
                    Set<DmActivityInfo> set=new TreeSet<>(Comparator.comparing(DmActivityInfo::getStudentName));
                    if(p.size()>1){
                        set.addAll(p);
                        String names = set.stream().map(q -> q.getStudentName()).collect(Collectors.joining("、"));
                        activityClass.setStudentNames(names);
                        activityClass.setStudentNamesCount(set.size());
                    }else{
                        activityClass.setStudentNamesCount(p.size());
                        activityClass.setStudentNames(p.get(0).getStudentName());
                    }
                    activityClassList.add(activityClass);
                });
                item.setActivityClassList(activityClassList);
                items.add(item);
            });
            exportActiveByItem.setActivityItemList(items);
        }
        return exportActiveByItem;
    }

    /***
     * 根据项目查询活动报名情况
     * @param dmActivityInfo
     * @return
     */
    public ExportActiveByItem findItemByActiveIdNew(DmActivityInfo dmActivityInfo){
        ExportActiveByItem exportActiveByItem=new ExportActiveByItem();
        List<DmActivityInfo> activeInfos = dmActivityInfoDao.findActiveListById(dmActivityInfo);
        if(CollUtil.isNotEmpty(activeInfos)){
            //设置活动信息
            setActiveInfo(activeInfos,exportActiveByItem);
            //查询该活动下面的项目数量
            DmActivityItem dmActivityItem=new DmActivityItem();
            dmActivityItem.setActivityId(dmActivityInfo.getActivityId());
            dmActivityItem.setSchoolId(dmActivityInfo.getSchoolId());
            List<DmActivityItem> dmActivityItemList = dmActivityItemDao.findDmActivityItemListByCondition(dmActivityItem);
            List<String> grades = activeInfos.stream().sorted(Comparator.comparing(DmActivityInfo::getGradeId)).map(z -> z.getGradeId()).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(dmActivityItemList)){
                List<ActivityItem> activityItemList=new ArrayList<>();
                dmActivityItemList.forEach(x->{
                    ActivityItem activityItem=new ActivityItem();
                    activityItem.setItemId(x.getId());
                    activityItem.setProjectName(x.getProjectName());
                    List<ActivityClass> classList=new ArrayList<>();
                    //查询当前项目分配的所有班级
                    grades.forEach(p->{
                        JwClasses jwClasses=new JwClasses();
                        jwClasses.setSchoolId(dmActivityInfo.getSchoolId());
                        jwClasses.setGradeId(p);
                        List<JwClasses> classInfoList= dmActivityInfoDao.findClassInfoByGradeId(jwClasses);

                        if(CollUtil.isNotEmpty(classInfoList)){
                            classInfoList.forEach(q->{
                                ActivityClass activityClass=new ActivityClass();
                                activityClass.setClassesId(q.getId());
                                activityClass.setClassName(String.format("%1$s(%2$s)班",q.getGradeName(),q.getNumber()));
                                //查询当前班级的报名情况
                                List<DmActivitySiginUp> singUpList = dmActivitySiginUpDao.findDmActivitySiginUpListByItemId(x.getId(), q.getId());
                                if(CollUtil.isNotEmpty(singUpList)){
                                    activityClass.setStudentNames(singUpList.stream().map(k->k.getName()).collect(Collectors.joining("、")));
                                    activityClass.setStudentNamesCount(singUpList.size());
                                }else{
                                    activityClass.setStudentNames(null);
                                    activityClass.setStudentNamesCount(0);
                                }
                                classList.add(activityClass);
                            });
                        }
                    });
                    activityItem.setActivityClassList(classList);
                    activityItemList.add(activityItem);
                });
                exportActiveByItem.setActivityItemList(activityItemList);
            }
        }
        return exportActiveByItem;
    }




    private void setActiveInfo(List<DmActivityInfo> dmActivityInfoByActivityId, ExportActiveByItem exportActiveByItem) {
        exportActiveByItem.setActivityName(dmActivityInfoByActivityId.get(0).getActivityName());
        exportActiveByItem.setEndDate(dmActivityInfoByActivityId.get(0).getEndDate());
        exportActiveByItem.setIsCustomize(dmActivityInfoByActivityId.get(0).getIsCustomize());
        exportActiveByItem.setContent(dmActivityInfoByActivityId.get(0).getActivityContent());
    }


    @Transactional(readOnly = true)
    public DmActivityInfo findOneDmActivityInfoByCondition(DmActivityInfo dmActivityInfo) {
        return dmActivityInfoDao.findOneDmActivityInfoByCondition(dmActivityInfo);
    }

    @Transactional(readOnly = true)
    public List<JwClasses> findClassInfoByGradeId(JwClasses jwClasses){
        return dmActivityInfoDao.findClassInfoByGradeId(jwClasses);
    }


    /**
     * 推送活动信息
     * @param dmActivityInfo
     * @param  type 1---推送教师 2--推送家长 3--教师和家长
     */
    public void sendMsgByGradeId(DmActivityInfo dmActivityInfo,int type){
        List<String> teachers=new ArrayList<>();
        List<String> students=new ArrayList<>();
        if(type == 1){
            teachers = dmActivityInfoDao.findHeadTeacherListByGradeId(dmActivityInfo);
        }else if(type ==2){
            students = dmActivityInfoDao.findParentByGradeId(dmActivityInfo);
        }else if(type == 3){
            teachers = dmActivityInfoDao.findHeadTeacherListByGradeId(dmActivityInfo);
            students = dmActivityInfoDao.findParentByGradeId(dmActivityInfo);
        }
       if(CollUtil.isNotEmpty(teachers)){
           String[] sendObjects=teachers.toArray(new String[0]);
           //向教师端推送
           final Push push = Push.newBuilder(JpushApp.TAP).getSimplePusByRefrenceId(sendObjects, "校园活动",dmActivityInfo.getActivityName(), Extras.DM_TAP_ACTIVITY, dmActivityInfo.getActivityId());
           stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
       }
       if(CollUtil.isNotEmpty(students)){
           String[] sendObjs=students.toArray(new String[0]);
           //向家长端推送
           final Push push = Push.newBuilder(JpushApp.BMP).getSimplePusByRefrenceId(sendObjs, "校园活动",dmActivityInfo.getActivityName(), Extras.DM_BMP_ACTIVITY, dmActivityInfo.getActivityId());
           stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
       }
    }


    @Transactional(readOnly = true)
    public long findDmActivityInfoCountByCondition(DmActivityInfo dmActivityInfo) {
        return dmActivityInfoDao.findDmActivityInfoCountByCondition(dmActivityInfo);
    }
    @Transactional
    public void updateDmActivityInfo(DmActivityInfo dmActivityInfo) {
        DmActivityInfo oldDmActivityInfo = dmActivityInfoDao.findOldDmActivityInfoDao(dmActivityInfo.getActivityId());
        dmActivityInfoDao.deleteDmActivityInfoByActivityId(dmActivityInfo.getActivityId());
        saveInfo(dmActivityInfo);
        dmAttachmentFileDao.deleteDmAttachmentFileByActivityId(dmActivityInfo.getActivityId());
        List<DmAttachmentFile> files = dmActivityInfo.getFiles();
        saveFiles(files,dmActivityInfo);
        if(oldDmActivityInfo.getIsCustomize()==0){
            dmActivityItemDao.deleteDmActivityItemByActivityId(dmActivityInfo.getActivityId());
            List<DmActivityItem> dmActivityItems = dmActivityInfo.getDmActivityItems();
            dmActivityItems.forEach(item->{
                if(ObjectUtil.isNull(item.getId())){
                    item.setId(sequenceId.nextId());
                    item.setCreateTime(DateUtils.Nowss());
                    item.setActivityId(dmActivityInfo.getActivityId());
                    item.setSchoolId(dmActivityInfo.getSchoolId());
                }
            });
            dmActivityItemDao.batchSaveDmActivityItem(dmActivityItems);
            List<String> itemIds = dmActivityItems.stream().map(DmActivityItem::getId).collect(Collectors.toList());
            dmActivitySiginUpDao.deleteNotExist(itemIds,dmActivityInfo.getActivityId());
        }
        List<String> newGradeIds = dmActivityInfo.getGradeIds();
        List<String> classesIds = dmActivityInfoDao.selectClassesIdsByGradeIds(newGradeIds);
        dmActivitySiginUpDao.deleteDmActivitySiginUpByActivityIdAndGradeId(dmActivityInfo.getActivityId(),classesIds);

        mongoTemplate.remove(query(Criteria.where("referenceId").is(dmActivityInfo.getActivityId())), PushDetail.class);
        sendMsgByGradeId(dmActivityInfo,dmActivityInfo.getNotifyObj());

    }

    private void saveItems(DmActivityInfo dmActivityInfo) {
        List<DmActivityItem> dmActivityItems = dmActivityInfo.getDmActivityItems();
        dmActivityItems.forEach(d->{
            d.setId(sequenceId.nextId());
            d.setActivityId(dmActivityInfo.getActivityId());
            d.setSchoolId(dmActivityInfo.getSchoolId());
            d.setCreateTime(DateUtils.Nowss());
        });
        dmActivityItemDao.batchSaveDmActivityItem(dmActivityItems);
    }

    private void saveFiles(List<DmAttachmentFile> files,DmActivityInfo dmActivityInfo) {
        if(CollUtil.isNotEmpty(files)){
            files.forEach(f->{
                f.setId(sequenceId.nextId());
                f.setCreateTime(DateUtils.Nowss());
                f.setReferenceId(dmActivityInfo.getActivityId());
                f.setSchoolId(dmActivityInfo.getSchoolId());
            });
            dmAttachmentFileDao.batchSaveDmAttachmentFile(files); }
    }

    private void saveInfo(DmActivityInfo dmActivityInfo) {
        List<String> gradeIds = dmActivityInfo.getGradeIds();
        ArrayList<DmActivityInfo> dmActivityInfos = new ArrayList<>(gradeIds.size());
        for (String gradeId : gradeIds) {
            DmActivityInfo da = new DmActivityInfo();
            BeanUtils.copyProperties(dmActivityInfo,da);
            da.setId(sequenceId.nextId());
            da.setGradeId(gradeId);
            da.setCreateTime(DateUtils.Nowss());
            dmActivityInfos.add(da);
        }
        dmActivityInfoDao.batchSaveDmActivityInfo(dmActivityInfos);

    }

    @Transactional
    public void deleteDmActivityInfo(String id) {
        dmActivityInfoDao.deleteDmActivityInfo(id);
    }
    @Transactional
    public void deleteDmActivityInfoByCondition(DmActivityInfo dmActivityInfo) {
        dmActivityInfoDao.deleteDmActivityInfoByCondition(dmActivityInfo);
    }
    @Transactional
    public void batchSaveDmActivityInfo(List<DmActivityInfo> dmActivityInfos){
        dmActivityInfos.forEach(dmActivityInfo -> dmActivityInfo.setId(sequenceId.nextId()));
        dmActivityInfoDao.batchSaveDmActivityInfo(dmActivityInfos);
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @Transactional(readOnly = true)
    public DmActivityInfo findDmActivityInfoByActivityId(String activityId) {
        DmActivityInfo dmActivityInfo = new DmActivityInfo();
        dmActivityInfo.setActivityId(activityId);
        List<DmActivityInfo> dmActivityInfos = dmActivityInfoDao.findDmActivityInfoListByCondition(dmActivityInfo);
        List<String> gradeIds = dmActivityInfos.stream().map(DmActivityInfo::getGradeId).collect(Collectors.toList());
        DmActivityInfo activityInfo = dmActivityInfos.get(0);
        activityInfo.setGradeIds(gradeIds);
        List<DmActivityItem> dmActivityItems = null;
        if(CollUtil.isNotEmpty(dmActivityInfos) && dmActivityInfos.get(0).getIsSignUp()!=0 && CollUtil.isNotEmpty(gradeIds) && CollUtil.isNotEmpty(dmActivityInfos)){
            if(activityInfo.getIsCustomize()==0){
                dmActivityItems = dmActivityItemDao.findDmActivityItemListByActivityId(dmActivityInfo.getActivityId());
            }
        }
        activityInfo.setDmActivityItems(dmActivityItems);
        activityInfo.setGradeId(null);
        List<DmAttachmentFile> files = dmAttachmentFileDao.findDmAttachmentFileListByReferenceId(dmActivityInfo.getActivityId());
        activityInfo.setFiles(files);
        return activityInfo;
    }

    @Transactional(readOnly = true)
    public boolean checkGradeSingUp(DmActivityInfo dmActivityInfo) {
        int num = dmActivitySiginUpDao.checkGradeSingUp(dmActivityInfo);
        return num==0?false:true;
    }

    @Transactional(readOnly = true)
    public Map checkItemSingUp(String itemId) {
        List<DmActivitySiginUp> dmActivitySiginUps = dmActivitySiginUpDao.checkItemSingUp(itemId);
        HashMap<String, Object> map = new HashMap<>(2);
        if(CollUtil.isEmpty(dmActivitySiginUps)){
            map.put("isSingUp",false);
            return map;
        }else{
            map.put("isSingUp",true);
            Map<String, List<DmActivitySiginUp>> listMap = dmActivitySiginUps.stream().collect(Collectors.groupingBy(DmActivitySiginUp::getClassesId));
            Set<String> classesIdSet = listMap.keySet();
            List<String> classesIdList = classesIdSet.stream().collect(Collectors.toList());
            List<JwClasses> classesList = dmActivityInfoDao.findSiginUpGrade(classesIdList);
            List<String> gradeNameList = classesList.stream().map(JwClasses::getGradeName).collect(Collectors.toList());
            List<String> gradeNames = gradeNameList.stream().distinct().collect(Collectors.toList());
            map.put("gradeNames",gradeNames);
            return map;
        }
    }

    public List<DmActivityInfo> findDmActivityInfosByCondition(DmActivityInfo dmActivityInfo) {
        List<DmActivityInfo> dmActivityInfos = dmActivityInfoDao.findDmActivityInfosByCondition(dmActivityInfo);
        dmActivityInfos.forEach(d->{
            judgeStatus(d);
            List<DmAttachmentFile> files =  dmAttachmentFileDao.findDmAttachmentFileListByReferenceId(d.getActivityId());
            d.setFiles(files);
        });
        return dmActivityInfos;
    }

    @Transactional(readOnly = true)
    public List<DmActivityInfo> selectDmActivityInfosByCondition(DmActivityInfo dmActivityInfo) {
        List<String> gradeIds = dmActivityInfoDao.findTeacherGradeIdsByTeacherId(dmActivityInfo.getTeacherId());
        if(CollUtil.isEmpty(gradeIds)){
            return null;
        }

        int begin=-1;
        int end=-1;
        if(Objects.nonNull(dmActivityInfo.getPager())){
            Integer page = dmActivityInfo.getPager().getPage();
            int pageSize = dmActivityInfo.getPager().getPageSize();
            begin=(page-1)*pageSize+1;
            end=page*pageSize;
            dmActivityInfo.getPager().setPaging(false);
        }
        List<DmActivityInfo> dmActivityInfos = dmActivityInfoDao.selectDmActivityInfosByGradeIdsAndSchoolId(gradeIds,dmActivityInfo,dmActivityInfo.getPager());
        List<DmActivityInfo> dmActivityInfoList = pageDmActivityInfos(dmActivityInfos, begin, end);
        dmActivityInfoList.forEach(d->{
            judgeStatus(d);
            List<DmAttachmentFile> files = dmAttachmentFileDao.findDmAttachmentFileListByReferenceId(d.getActivityId());
            d.setFiles(files);

        });
        return dmActivityInfoList;
    }


    @Transactional(readOnly = true)
    public DmActivityInfo findDmActivityInfoNoItemByActivityId(String activityId) {
        DmActivityInfo dmActivityInfo = dmActivityInfoDao.findDmActivityInfoNoItemByActivityId(activityId);
        List<DmAttachmentFile> files = dmAttachmentFileDao.findDmAttachmentFileListByReferenceId(activityId);
        dmActivityInfo.setFiles(files);
        dmActivityInfo = judgeStatus(dmActivityInfo);
        return dmActivityInfo;
    }

    @Transactional(readOnly = true)
    public DmActivityInfo findBmpDmActivityInfoByActivityId(String activityId) {
        DmActivityInfo dmActivityInfo = dmActivityInfoDao.findOneDmActivityInfoByActivityId(activityId);
        dmActivityInfo = judgeStatus(dmActivityInfo);
        List<DmAttachmentFile> files = dmAttachmentFileDao.findDmAttachmentFileListByReferenceId(activityId);
        if(CollUtil.isNotEmpty(files)){
            dmActivityInfo.setFiles(files);
        }
        return dmActivityInfo;
    }

    private DmActivityInfo judgeStatus(DmActivityInfo dmActivityInfo) {
        if(dmActivityInfo.getIsSignUp()==1){
            LocalDateTime endDate = LocalDateTime.parse(dmActivityInfo.getEndDate()+" 23:59:59" ,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            if(LocalDateTime.now().isBefore(endDate)){
                dmActivityInfo.setStatus(0);
            }else{
                dmActivityInfo.setStatus(1);
            }
        }else{
            dmActivityInfo.setStatus(3);
        }
        return dmActivityInfo;
    }
}
