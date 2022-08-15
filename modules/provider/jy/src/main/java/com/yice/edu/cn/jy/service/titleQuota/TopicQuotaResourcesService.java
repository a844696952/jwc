package com.yice.edu.cn.jy.service.titleQuota;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jy.titleQuota.*;
import com.yice.edu.cn.jy.dao.titleQuota.IResourcePlatformDao;
import com.yice.edu.cn.jy.dao.titleQuota.ITopicQuotaResourcesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TopicQuotaResourcesService {
    @Autowired
    private ITopicQuotaResourcesDao topicQuotaResourcesDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private HistoryTitleQuotaService historyTitleQuotaService;
    @Autowired
    private PlatformTopicQuReService platformTopicQuReService;
    @Autowired
    private PlatformTopichiscotyService platformTopichiscotyService;
    @Autowired
    private IResourcePlatformDao resourcePlatformDao;
    @Autowired
    private HistoryTeacherAesService historyTeacherAesService;
    @Autowired
    private TeacherAccessConfigurationService teacherAccessConfigurationService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public TopicQuotaResources findTopicQuotaResourcesById(String id) {
        return topicQuotaResourcesDao.findTopicQuotaResourcesById(id);
    }
    @Transactional
    public void saveTopicQuotaResources(TopicQuotaResources topicQuotaResources) {
        topicQuotaResources.setId(sequenceId.nextId());
        topicQuotaResourcesDao.saveTopicQuotaResources(topicQuotaResources);
    }
    @Transactional(readOnly = true)
    public List<TopicQuotaResources> findTopicQuotaResourcesListByCondition(TopicQuotaResources topicQuotaResources) {
        return topicQuotaResourcesDao.findTopicQuotaResourcesListByCondition(topicQuotaResources);
    }
    @Transactional(readOnly = true)
    public TopicQuotaResources findOneTopicQuotaResourcesByCondition(TopicQuotaResources topicQuotaResources) {
        return topicQuotaResourcesDao.findOneTopicQuotaResourcesByCondition(topicQuotaResources);
    }
    @Transactional(readOnly = true)
    public long findTopicQuotaResourcesCountByCondition(TopicQuotaResources topicQuotaResources) {
        return topicQuotaResourcesDao.findTopicQuotaResourcesCountByCondition(topicQuotaResources);
    }
    @Transactional
    public void updateTopicQuotaResources(TopicQuotaResources topicQuotaResources) {
        topicQuotaResourcesDao.updateTopicQuotaResources(topicQuotaResources);
    }
    @Transactional
    public void updateTopicQuotaResourcesForAll(TopicQuotaResources topicQuotaResources) {
        topicQuotaResourcesDao.updateTopicQuotaResourcesForAll(topicQuotaResources);
    }
    @Transactional
    public void deleteTopicQuotaResources(String id) {
        topicQuotaResourcesDao.deleteTopicQuotaResources(id);
    }
    @Transactional
    public void deleteTopicQuotaResourcesByCondition(TopicQuotaResources topicQuotaResources) {
        topicQuotaResourcesDao.deleteTopicQuotaResourcesByCondition(topicQuotaResources);
    }
    @Transactional
    public void batchSaveTopicQuotaResources(List<TopicQuotaResources> topicQuotaResourcess){
        topicQuotaResourcess.forEach(topicQuotaResources -> topicQuotaResources.setId(sequenceId.nextId()));
        topicQuotaResourcesDao.batchSaveTopicQuotaResources(topicQuotaResourcess);
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @Transactional
    public TopicQuotaResources updateTopicQuotaResources4Person(TopicQuotaResources topicQuotaResources) {
        TopicQuotaResources one = new TopicQuotaResources();
        one.setSchoolId(topicQuotaResources.getSchoolId());
        long num = topicQuotaResourcesDao.findTopicQuotaResourcesCountByCondition(one);

        if(num>0){//有修改
            topicQuotaResources = update(topicQuotaResources);
        }
        if(num==0){//没有就新增
            topicQuotaResources = add(topicQuotaResources);
        }
        return  topicQuotaResources;
    }

    @Transactional
    public TopicQuotaResources update(TopicQuotaResources topicQuotaResources){
        //1.维护资源表
        topicQuotaResourcesDao.updateTopicQuotaResources4Like(topicQuotaResources);
        //2.资源和平台关联表
        PlatformTopicQuRe platformTopicQuRe = new PlatformTopicQuRe();
        platformTopicQuRe.setTopicQuotaResourceId(topicQuotaResources.getId());
        platformTopicQuReService.deletePlatformTopicQuReByCondition(platformTopicQuRe);
        List<PlatformTopicQuRe> platformTopicQuReList = getPlatformTopicQuReList(topicQuotaResources);
        platformTopicQuReService.batchSavePlatformTopicQuRe(platformTopicQuReList);
        //3.历史表
        HistoryTitleQuota historyTitleQuota = createHistoryTitleQuotaBean(topicQuotaResources);
        historyTitleQuotaService.saveHistoryTitleQuota(historyTitleQuota);
        //4.历史平台关联表
        List<PlatformTopichiscoty> list2 = getPlatformTopichiscoty(topicQuotaResources,historyTitleQuota);
        platformTopichiscotyService.batchSavePlatformTopichiscoty(list2);
        return topicQuotaResources;
    }

    @Transactional
    public TopicQuotaResources add(TopicQuotaResources topicQuotaResources){
        //1.资源表
        //topicQuotaResources.setId(sequenceId.nextId());
        topicQuotaResources.setTotalMargin(topicQuotaResources.getIncreaseQuantity());
        topicQuotaResources.setRemainingMargin(0);
        saveTopicQuotaResources(topicQuotaResources);
        //2.资源关联表
        List<PlatformTopicQuRe> list1 = getPlatformTopicQuReList(topicQuotaResources);
        platformTopicQuReService.batchSavePlatformTopicQuRe(list1);
        //3.历史表
        HistoryTitleQuota historyTitleQuota = createHistoryTitleQuotaBean(topicQuotaResources);
        historyTitleQuotaService.saveHistoryTitleQuota(historyTitleQuota);
        //4.历史关联表
        List<PlatformTopichiscoty> list2 = getPlatformTopichiscoty(topicQuotaResources,historyTitleQuota);
        platformTopichiscotyService.batchSavePlatformTopichiscoty(list2);
        return topicQuotaResources;
    }

    private HistoryTitleQuota createHistoryTitleQuotaBean(TopicQuotaResources topicQuotaResources){//创建历史表bean
        HistoryTitleQuota historyTitleQuota = new HistoryTitleQuota();
        //historyTitleQuota.setId(sequenceId.nextId());
        historyTitleQuota.setModifyContentClosingDate(topicQuotaResources.getClosingDate());
        historyTitleQuota.setRemarks(topicQuotaResources.getRemarks());
        historyTitleQuota.setIncreaseQuantity(topicQuotaResources.getIncreaseQuantity());
        historyTitleQuota.setUploadVouchers(topicQuotaResources.getUploadVouchers());
        historyTitleQuota.setOperator(topicQuotaResources.getOpertor());
        historyTitleQuota.setSchoolId(topicQuotaResources.getSchoolId());
        return historyTitleQuota;
    }

    private List<PlatformTopicQuRe> getPlatformTopicQuReList(TopicQuotaResources topicQuotaResources){//获取资源表和平台的关联实体
        List<PlatformTopicQuRe> list1 = new ArrayList<>();
        String[] split = topicQuotaResources.getIds().split(",");
        PlatformTopicQuRe platformTopicQuRe;
        for(int i=0;i<split.length;i++){
            platformTopicQuRe = new PlatformTopicQuRe();
            //platformTopicQuRe.setId(sequenceId.nextId());
            platformTopicQuRe.setTopicQuotaResourceId(topicQuotaResources.getId());
            platformTopicQuRe.setPlatformId(split[i]);
            list1.add(platformTopicQuRe);
        }
        return list1;
    }

    private  List<PlatformTopichiscoty> getPlatformTopichiscoty(TopicQuotaResources topicQuotaResources,
                                                                HistoryTitleQuota historyTitleQuota){//获取历史表和平台的关联表
        List<PlatformTopichiscoty> list2 = new ArrayList<>();
        String[] split = topicQuotaResources.getIds().split(",");
        for(int i=0;i<split.length;i++){
            PlatformTopichiscoty platformTopichiscoty = new PlatformTopichiscoty();
            //platformTopichiscoty.setId(sequenceId.nextId());
            platformTopichiscoty.setHistoryTitleQuotaId(historyTitleQuota.getId());
            platformTopichiscoty.setPlatformId(split[i]);
            list2.add(platformTopichiscoty);
        }
        return list2;
    }

    @Transactional(readOnly = true)
    public TopicQuotaResources getBaiscInfo(String id) {
        TopicQuotaResources topicQuotaResources = new TopicQuotaResources();
        topicQuotaResources.setSchoolId(id);
        return topicQuotaResourcesDao.getBaiscInfo(topicQuotaResources);
    }

    @Transactional(readOnly = true)
    public List<ResourcePlatform> findPaltFormByCondition(TopicQuotaResources topicQuotaResources) {
       return topicQuotaResourcesDao.findPaltFormByCondition(topicQuotaResources);
    }

    public long findTeacherAndSchoolRemain(TopicQuotaResources topicQuotaResources) {
        TopicQuotaResources temp = new TopicQuotaResources();
        TopicQuotaResources one = topicQuotaResourcesDao.findOneTopicQuotaResourcesByCondition(topicQuotaResources);
        if(one==null){
            temp.setPersonRemain(0);
            temp.setSchoolRemain(0);
        }else{
            temp.setSchoolRemain(one.getTotalMargin()-one.getRemainingMargin());
        }
        HistoryTeacherAes historyTeacherAes = new HistoryTeacherAes();
        historyTeacherAes.setTeacherId(topicQuotaResources.getTeacherId());
        historyTeacherAes.setSchoolId(topicQuotaResources.getSchoolId());
        String start = DateUtil.beginOfDay(new Date()).toString();
        String end = DateUtil.endOfDay(new Date()).toString();
        Pager pager = new Pager();
        pager.setRangeArray(new String[]{start,end});
        pager.setRangeField("createTime");
        historyTeacherAes.setPager(pager);
        long numTeacherVisits =
                historyTeacherAesService.findHistoryTeacherAesCountByCondition(historyTeacherAes);
        TeacherAccessConfiguration temp1 = new TeacherAccessConfiguration();
        temp1.setTeacherId(topicQuotaResources.getTeacherId());
        TeacherAccessConfiguration one3 = teacherAccessConfigurationService.findOneTeacherAccessConfigurationByCondition(temp1);
        Integer dailyVistts = 0;
        if(one3!=null){
            dailyVistts = one3.getDailyVisits();
            temp.setPersonRemain(dailyVistts-numTeacherVisits);
        }else{
            temp.setPersonRemain(0);
        }
        long min = temp.getPersonRemain();
        if(temp.getSchoolRemain()<min){
            min = temp.getSchoolRemain();
        }
        return min;
    }
}
