package com.yice.edu.cn.dy.service.schoolManage.institution;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.schoolWeek.SchoolWeek;
import com.yice.edu.cn.common.pojo.jw.schoolYear.SchoolYear;
import com.yice.edu.cn.common.pojo.mes.institution.MesClassHonor;
import com.yice.edu.cn.common.pojo.mes.schoolManage.inspectRecord.MesInspectRecord;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.*;
import com.yice.edu.cn.common.pojo.mes.schoolManage.schoolEvaluation.MesSchoolEvaluationVo;
import com.yice.edu.cn.common.util.jmessage.utils.StringUtils;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.dy.dao.schoolManage.inspectRecord.IMesInspectRecordDao;
import com.yice.edu.cn.dy.dao.schoolManage.institution.*;
import com.yice.edu.cn.dy.feign.JwClassesFeign;
import com.yice.edu.cn.dy.feign.SchoolFeign;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class MesSchoolEvaluationService {

    @Autowired
    private MesSchoolEvaluationDao mesSchoolEvaluationDao;
    @Autowired
    private IMesTimeStatusDao mesTimeStatusDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private JwClassesFeign jwClassesFeign;
    @Autowired
    private SchoolFeign schoolFeign;
    @Autowired
    private IMesInstitutionDao mesInstitutionDao;
    @Autowired
    private IMesCommonConfigDao mesCommonConfigDao;
    @Autowired
    private IMesInspectRecordDao mesInspectRecordDao;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private IMesCustomTitleDao mesCustomTitleDao;

    @Transactional(readOnly = true)
    public Map findMesSchoolEvaluationListByCondition(MesSchoolEvaluationVo mesSchoolEvaluationVo) {
        SchoolYear schoolYear = mesSchoolEvaluationDao.findSchoolYearById(mesSchoolEvaluationVo.getSchoolYearId());
        JwClasses jwClasses = new JwClasses();
        jwClasses.setGradeId(mesSchoolEvaluationVo.getGradeId());
        jwClasses.setSchoolId(mesSchoolEvaluationVo.getSchoolId());
        List<JwClasses> jwClassesList = jwClassesFeign.findClassesByGradeId(jwClasses);
        if (CollUtil.isNotEmpty(jwClassesList)) {
            Collections.sort(jwClassesList, new Comparator<JwClasses>() {
                @Override
                public int compare(JwClasses o1, JwClasses o2) {
                    return Integer.parseInt(o1.getNumber()) - Integer.parseInt(o2.getNumber());
                }
            });
        }
        //查询学校制度时间
        MesTimeStatus latestMesTimeStatus = mesTimeStatusDao.selectLatestBySchoolId(mesSchoolEvaluationVo.getSchoolId());
        if (latestMesTimeStatus == null) {
            HashMap<String, Object> map = new HashMap<>(1);
            map.put("msg", "该学校未发布制度");
            return map;
        }
        if (mesSchoolEvaluationVo.getTerm() == -1) {
            HashMap<String, Object> map = new HashMap<>(1);
            map.put("msg", "当前学期还未结束");
            return map;
        }
        //按周查询
        SchoolWeek schoolWeek = findSchoolWeekBySchoolYearId(schoolYear.getId(), mesSchoolEvaluationVo.getTerm());
        if ((mesSchoolEvaluationVo.getEvaluationType() == 2 || mesSchoolEvaluationVo.getEvaluationType() == 4) && ObjectUtil.isNull(schoolWeek)) {
            HashMap<String, Object> map = new HashMap<>(1);
            map.put("msg", "学周未设置");
            return map;
        }
        if (ObjectUtil.equal(mesSchoolEvaluationVo.getEvaluationType(), 2)) {
            LocalDate schoolWeekStartTime = LocalDate.parse(schoolWeek.getStartTime());
            LocalDate startWeekPreviousSunday = schoolWeekStartTime.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
            LocalDate beginDate;
            LocalDate endDate;
            if (mesSchoolEvaluationVo.getNumber() == 1) {
                endDate = startWeekPreviousSunday.plusWeeks(mesSchoolEvaluationVo.getNumber());
                beginDate = schoolWeekStartTime;
            } else if (ObjectUtil.equal(mesSchoolEvaluationVo.getNumber(), schoolWeek.getWeekCount())) {
                endDate = LocalDate.parse(schoolWeek.getEndTime());
                beginDate = endDate.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).plusDays(1);
            } else {
                endDate = startWeekPreviousSunday.plusWeeks(mesSchoolEvaluationVo.getNumber());
                beginDate = endDate.minusDays(6);
            }
            Map map = select(beginDate, endDate, mesSchoolEvaluationVo.getSchoolId(), jwClassesList, latestMesTimeStatus, 2, mesSchoolEvaluationVo.getPager());
            return map;

        } else if (ObjectUtil.equal(mesSchoolEvaluationVo.getEvaluationType(), 3)) {
            //按月查询
            LocalDate beginDate;
            LocalDate endDate;
            Integer lastTermMonths = sumMonths(schoolYear.getLastTermBegin(), schoolYear.getNextTermBegin());
            Integer nextTermMonths = sumMonths(schoolYear.getNextTermBegin(), LocalDate.parse(schoolYear.getLastTermBegin()).plusYears(1).toString());
            if (mesSchoolEvaluationVo.getTerm() == 0) {
                if (mesSchoolEvaluationVo.getNumber() == 1) {
                    beginDate = LocalDate.parse(schoolYear.getLastTermBegin());
                    endDate = LocalDate.parse(schoolYear.getLastTermBegin()).plusMonths(mesSchoolEvaluationVo.getNumber() - 1).with(TemporalAdjusters.lastDayOfMonth());
                } else if (mesSchoolEvaluationVo.getNumber().equals(lastTermMonths)) {
                    beginDate = LocalDate.parse(schoolYear.getLastTermBegin()).plusMonths(mesSchoolEvaluationVo.getNumber() - 1).with(TemporalAdjusters.firstDayOfMonth());
                    endDate = LocalDate.parse(schoolYear.getNextTermBegin()).minusDays(1);
                } else {
                    beginDate = LocalDate.parse(schoolYear.getLastTermBegin()).plusMonths(mesSchoolEvaluationVo.getNumber() - 1).with(TemporalAdjusters.firstDayOfMonth());
                    endDate = LocalDate.parse(schoolYear.getLastTermBegin()).plusMonths(mesSchoolEvaluationVo.getNumber() - 1).with(TemporalAdjusters.lastDayOfMonth());
                }
            } else {
                if (mesSchoolEvaluationVo.getNumber() == 1) {
                    beginDate = LocalDate.parse(schoolYear.getNextTermBegin());
                    endDate = LocalDate.parse(schoolYear.getNextTermBegin()).plusMonths(mesSchoolEvaluationVo.getNumber() - 1).with(TemporalAdjusters.lastDayOfMonth());
                } else if (mesSchoolEvaluationVo.getNumber().equals(nextTermMonths)) {
                    beginDate = LocalDate.parse(schoolYear.getNextTermBegin()).plusMonths(mesSchoolEvaluationVo.getNumber() - 1).with(TemporalAdjusters.firstDayOfMonth());
                    endDate = LocalDate.parse(schoolYear.getLastTermBegin()).minusDays(1);
                } else {
                    beginDate = LocalDate.parse(schoolYear.getNextTermBegin()).plusMonths(mesSchoolEvaluationVo.getNumber() - 1).with(TemporalAdjusters.firstDayOfMonth());
                    endDate = LocalDate.parse(schoolYear.getNextTermBegin()).plusMonths(mesSchoolEvaluationVo.getNumber() - 1).with(TemporalAdjusters.lastDayOfMonth());
                }
            }
            Map map = select(beginDate, endDate, mesSchoolEvaluationVo.getSchoolId(), jwClassesList, latestMesTimeStatus, 3, mesSchoolEvaluationVo.getPager());
            return map;
        } else {
            //按学期查询
            LocalDate beginDate = LocalDate.parse(schoolWeek.getStartTime());
            LocalDate endDate = LocalDate.parse(schoolWeek.getEndTime());
            Map map = select(beginDate, endDate, mesSchoolEvaluationVo.getSchoolId(), jwClassesList, latestMesTimeStatus, 4, mesSchoolEvaluationVo.getPager());
            return map;
        }
    }

    private Integer sumMonths(String begin, String end) {
        LocalDate a = LocalDate.parse(begin);
        LocalDate b = LocalDate.parse(end).minusDays(1);
        Integer months;
        if (a.getYear() == b.getYear()) {
            months = b.getMonthValue() - a.getMonthValue();
        } else {
            months = 11 - a.getMonthValue() + b.getMonthValue();
        }
        return months;
    }

    private Map select(LocalDate beginDate, LocalDate endDate, String schoolId, List<JwClasses> jwClassesList, MesTimeStatus latestMesTimeStatus, Integer type, Pager pager) {
        String beginTime = beginDate + " 00:00:00";
        String endTime = endDate + " 23:59:59";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime endDateTime = LocalDateTime.parse(endTime, formatter);
        if (endDateTime.isAfter(LocalDateTime.parse(latestMesTimeStatus.getCreateTime(), formatter)) || endDateTime.isEqual(LocalDateTime.parse(latestMesTimeStatus.getCreateTime(), formatter))) {
            if (LocalDateTime.parse(beginTime, formatter).isBefore(LocalDateTime.parse(latestMesTimeStatus.getCreateTime(), formatter))) {
                beginTime = latestMesTimeStatus.getCreateTime();
            }
            Map map = getMesSchoolEvaluationList(jwClassesList, schoolId, beginTime, endTime, latestMesTimeStatus.getId(), type, pager);
            return map;
        } else {
            Map map = selectSchoolEvaluationListByOldTimeStatus(beginTime, endTime, schoolId, jwClassesList, type, pager);
            return map;
        }
    }

    private Integer getTotalScore(Integer type, MesCommonConfig mesCommonConfig) {
        Integer totalScore;
        if (type == 2) {
            totalScore = Integer.parseInt(mesCommonConfig.getWeekValue());
        } else if (type == 3) {
            totalScore = Integer.parseInt(mesCommonConfig.getMonthValue());
        } else {
            totalScore = Integer.parseInt(mesCommonConfig.getSemesterValue());
        }
        return totalScore;
    }


    private Map selectSchoolEvaluationListByOldTimeStatus(String beginTime, String endTime, String schoolId, List<JwClasses> jwClassesList, Integer type, Pager pager) {
        List<MesTimeStatus> oldMesTimeStatusList = mesTimeStatusDao.selectMesTimeStatusByCondition(beginTime, endTime, schoolId);
        if (CollectionUtils.isNotEmpty(oldMesTimeStatusList)) {
            MesTimeStatus mesTimeStatus = oldMesTimeStatusList.get(0);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            if (LocalDateTime.parse(beginTime, formatter).isBefore(LocalDateTime.parse(mesTimeStatus.getCreateTime(), formatter))) {
                beginTime = mesTimeStatus.getCreateTime();
            }
            Map map = getMesSchoolEvaluationList(jwClassesList, schoolId, beginTime, endTime, oldMesTimeStatusList.get(0).getId(), type, pager);
            return map;
        }
        return noInstitution(beginTime, endTime, schoolId);
    }

    private Map noInstitution(String beginTime, String endTime, String schoolId) {
        String schoolName = mesInstitutionDao.selectSchoolNameBySchoolId(schoolId);
        Map map = new HashMap<>(3);
        map.put("schoolName", schoolName);
        map.put("beginTime", beginTime);
        map.put("endTime", endTime);
        return map;
    }

    private Map getMesSchoolEvaluationList(List<JwClasses> jwClassesList, String schoolId, String beginTime, String endTime, String timeStatusId, Integer type, Pager pager) {
        MesCommonConfig mesCommonConfig = mesCommonConfigDao.findMesCommonConfigByTimeStatusId(timeStatusId);
        if (ObjectUtil.isNull(mesCommonConfig)) {
            return noInstitution(beginTime, endTime, schoolId);
        }
        Integer totalScore = getTotalScore(type, mesCommonConfig);
        List<MesSchoolEvaluation> mesSchoolEvaluationList = new ArrayList<>(jwClassesList.size());
        int num = 0;
        String[] t = beginTime.split(" ");
        beginTime = t[0] + " 00:00:00";
        for (JwClasses c : jwClassesList) {
            List<Criteria> criteriaList = new ArrayList<>();
            criteriaList.add(where("classId").is(c.getId()));
            criteriaList.add(where("createTime").gte(beginTime).lte(endTime));
            criteriaList.add(where("schoolId").is(schoolId));
            Query query = query(new Criteria().andOperator(criteriaList.toArray(new Criteria[criteriaList.size()])));
            List<MesSchoolEvaluation> mesSchoolEvaluations = mongoTemplate.find(query, MesSchoolEvaluation.class);
            if (CollectionUtils.isNotEmpty(mesSchoolEvaluations)) {
                MesSchoolEvaluation mesSchoolEvaluation = new MesSchoolEvaluation();
                mesSchoolEvaluation.setClassName(c.getGradeName() + c.getNumber() + "班");
                mesSchoolEvaluation.setFormData(mesSchoolEvaluations.get(0).getFormData());
                List<Integer> formData = mesSchoolEvaluation.getFormData();
                int count = 0;
                for (MesSchoolEvaluation s : mesSchoolEvaluations) {
                    if (count == 0) {
                        count++;
                        continue;
                    }
                    for (int i = 0; i < s.getFormData().size(); i++) {
                        Integer integer = formData.get(i);
                        integer += s.getFormData().get(i);
                        formData.set(i, integer);
                    }
                }
                String gradeId = mesSchoolEvaluationDao.findGradeIdByClassesId(c.getId());
                mesSchoolEvaluation.setGradeId(gradeId);
                mesSchoolEvaluation.setSortNum(num);
                mesSchoolEvaluation.setClassId(c.getId());
                mesSchoolEvaluationList.add(mesSchoolEvaluation);
                num++;
            }
        }

        for (MesSchoolEvaluation mesSchoolEvaluation : mesSchoolEvaluationList) {
            mesSchoolEvaluation.getFormData().remove(mesSchoolEvaluation.getFormData().size() - 1);
            Integer thisScore = totalScore;
            for (Integer formDatum : mesSchoolEvaluation.getFormData()) {
                thisScore = thisScore + formDatum;
            }
            mesSchoolEvaluation.setTotalScore(thisScore);
            LinkedList<String> titleList = new LinkedList<>();
            mesSchoolEvaluation.setTitles(titleList);
        }
        if (ObjectUtil.equal(mesCommonConfig.getIsByTotal(), 1)) {
            List<MesCustomTitle> mesCustomTitles = mesCustomTitleDao.selectByTotalAndTimeStatusId(timeStatusId);
            if (ObjectUtil.equal(mesCommonConfig.getIsByRank(), 1)) {
                Collections.sort(mesSchoolEvaluationList, new Comparator<MesSchoolEvaluation>() {
                    @Override
                    public int compare(MesSchoolEvaluation o1, MesSchoolEvaluation o2) {
                        return o1.getTotalScore() - o2.getTotalScore();
                    }
                });
                Map<Integer, List<MesSchoolEvaluation>> listMap = mesSchoolEvaluationList.stream().collect(Collectors.groupingBy(MesSchoolEvaluation::getTotalScore));
                Set<Integer> set = listMap.keySet();
                TreeSet<Integer> totalScoreSet = sortScore(set);
                for (int i = 0; i < mesCustomTitles.size(); i++) {
                    int count = 0;
                    setTitles(i, count, listMap, totalScoreSet, mesCustomTitles);
                }
            } else {
                for (int i = 0; i < mesCustomTitles.size(); i++) {
                    if (CollectionUtils.isNotEmpty(mesSchoolEvaluationList)) {
                        Integer percentageBegin = mesCustomTitles.get(i).getPercentageBegin();
                        Integer percentageEnd = mesCustomTitles.get(i).getPercentageEnd();
                        for (MesSchoolEvaluation mesSchoolEvaluation : mesSchoolEvaluationList) {
                            if (i < mesSchoolEvaluation.getFormData().size()) {
                                if (((100 - mesSchoolEvaluation.getTotalScore() * 100 / totalScore >= percentageBegin) && (100 - mesSchoolEvaluation.getTotalScore() * 100 / totalScore <= percentageEnd))) {
                                    mesSchoolEvaluation.getTitles().add(mesCustomTitles.get(i).getAttrValue());
                                }
                            }
                        }
                    }
                }
                if (CollectionUtil.isNotEmpty(mesCustomTitles)) {
                    for (MesSchoolEvaluation mesSchoolEvaluation : mesSchoolEvaluationList) {
                        if (mesSchoolEvaluation.getTotalScore() > totalScore) {
                            mesSchoolEvaluation.getTitles().add(mesCustomTitles.get(0).getAttrValue());
                        }
                    }
                }
            }
        }
        if (ObjectUtil.equal(mesCommonConfig.getIsFirstInstitution(), 1)) {
            List<MesCustomTitle> mesCustomTitles = mesCustomTitleDao.selectByFirstInstitutionAndTimeStatusId(timeStatusId);
            if (ObjectUtil.equal(mesCommonConfig.getIsByRank(), 1)) {
                for (int i = 0; i < mesCustomTitles.size(); i++) {
                    int finalI = i;
                    if (CollectionUtils.isNotEmpty(mesSchoolEvaluationList)) {
                        if (mesSchoolEvaluationList.get(0).getFormData().size() > finalI) {
                            Collections.sort(mesSchoolEvaluationList, (o1, o2) -> {
                                return o1.getFormData().get(finalI) - o2.getFormData().get(finalI);
                            });
                            Map<Integer, List<MesSchoolEvaluation>> listMap = mesSchoolEvaluationList.stream().collect(Collectors.groupingBy(m -> {
                                return m.getFormData().get(finalI);
                            }));
                            Set<Integer> set = listMap.keySet();
                            TreeSet<Integer> scoreSet = sortScore(set);
                            for (MesSchoolEvaluation m : listMap.get(scoreSet.first())) {
                                m.getTitles().add(mesCustomTitles.get(i).getAttrValue());
                            }
                        }
                    }
                }
            } else {
                for (int i = 0; i < mesCustomTitles.size(); i++) {
                    Integer percentageBegin = mesCustomTitles.get(i).getPercentageBegin();
                    Integer percentageEnd = mesCustomTitles.get(i).getPercentageEnd();
                    for (MesSchoolEvaluation mesSchoolEvaluation : mesSchoolEvaluationList) {
                        if (i < mesSchoolEvaluation.getFormData().size()) {
                            if (mesSchoolEvaluation.getFormData().get(i) > 0 || ((totalScore - (mesSchoolEvaluation.getFormData().get(i) + totalScore)) * 100 / totalScore >= percentageBegin && (totalScore - (mesSchoolEvaluation.getFormData().get(i) + totalScore)) * 100 / totalScore <= percentageEnd)) {
                                mesSchoolEvaluation.getTitles().add(mesCustomTitles.get(i).getAttrValue());
                            }
                        }
                    }
                }

            }


        }
        LinkedList<String> clearNull = new LinkedList<>();
        clearNull.add(null);
        mesSchoolEvaluationList.forEach(m->{
            m.getTitles().removeAll(clearNull);
        });
        if (CollectionUtils.isNotEmpty(mesSchoolEvaluationList)) {
            Collections.sort(mesSchoolEvaluationList, Comparator.comparing(MesSchoolEvaluation::getSortNum));
            if ("-1".equals(pager.getSortField())) {
                if ("asc".equals(pager.getSortOrder())) {
                    Collections.sort(mesSchoolEvaluationList, new Comparator<MesSchoolEvaluation>() {
                        @Override
                        public int compare(MesSchoolEvaluation o1, MesSchoolEvaluation o2) {
                            return o1.getTotalScore() - o2.getTotalScore();
                        }
                    });
                } else {
                    Collections.sort(mesSchoolEvaluationList, new Comparator<MesSchoolEvaluation>() {
                        @Override
                        public int compare(MesSchoolEvaluation o1, MesSchoolEvaluation o2) {
                            return o2.getTotalScore() - o1.getTotalScore();
                        }
                    });
                }
            }
            if (ObjectUtil.isNotNull(pager.getSortField()) && !"-1".equals(pager.getSortField())) {
                if ("asc".equals(pager.getSortOrder())) {
                    CollectionUtil.sort(mesSchoolEvaluationList, new Comparator<MesSchoolEvaluation>() {
                        @Override
                        public int compare(MesSchoolEvaluation o1, MesSchoolEvaluation o2) {
                            return o1.getFormData().get(Integer.parseInt(pager.getSortField())) - o2.getFormData().get(Integer.parseInt(pager.getSortField()));
                        }
                    });
                } else {
                    CollectionUtil.sort(mesSchoolEvaluationList, new Comparator<MesSchoolEvaluation>() {
                        @Override
                        public int compare(MesSchoolEvaluation o1, MesSchoolEvaluation o2) {
                            return o2.getFormData().get(Integer.parseInt(pager.getSortField())) - o1.getFormData().get(Integer.parseInt(pager.getSortField()));
                        }
                    });
                }
            }
        }
        DateTime beginDate = DateUtil.parse(beginTime);
        DateTime endDate = DateUtil.parse(endTime);
        List<MesInstitution> tableHead = findTableHead(beginDate, endDate, schoolId);
        HashMap<String, Object> map = new HashMap<>(5);
        String schoolName = mesInstitutionDao.selectSchoolNameBySchoolId(schoolId);
        map.put("schoolName", schoolName);
        map.put("list", mesSchoolEvaluationList);
        map.put("tableHead", tableHead);
        map.put("beginTime", beginTime);
        map.put("endTime", endTime);
        return map;
    }

    private TreeSet<Integer> sortScore(Set<Integer> set) {
        TreeSet<Integer> scoreSet = new TreeSet<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        for (Integer score : set) {
            scoreSet.add(score);
        }
        return scoreSet;
    }

    private void setTitles(int i, int count, Map<Integer, List<MesSchoolEvaluation>> listMap, Set<Integer> totalScoreSet, List<MesCustomTitle> mesCustomTitles) {
        for (Integer s : totalScoreSet) {
            if (count == i) {
                for (MesSchoolEvaluation mesSchoolEvaluation : listMap.get(s)) {
                    mesSchoolEvaluation.getTitles().add(mesCustomTitles.get(i).getAttrValue());
                }
            }
            count++;
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void saveMesSchoolEvaluation() {
        new Thread(() -> {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, -2);
            //将开始时间设置为两天前0点
            Date beginTime = DateUtil.parse(DateUtil.format(cal.getTime(), "yyyy-MM-dd"), "yyyy-MM-dd");
            cal.setTime(beginTime);
            //将结束时间设置为两天前24点
            cal.add(Calendar.DAY_OF_MONTH, 1);
            Date endTime = cal.getTime();
            List<Criteria> criteriaList = new ArrayList<>();
            criteriaList.add(where("createTime").gte(DateUtil.format(beginTime, "yyyy-MM-dd HH:mm:ss")).lt(DateUtil.format(endTime, "yyyy-MM-dd HH:mm:ss")));
            Query query = query(new Criteria().andOperator(criteriaList.toArray(new Criteria[criteriaList.size()])));
            List<MesSchoolEvaluation> list = mongoTemplate.find(query, MesSchoolEvaluation.class);
            if (CollUtil.isNotEmpty(list)) {
                return;
            }
            cal.add(Calendar.DAY_OF_MONTH, -1);
            MesTimeStatus mesTimeStatus = new MesTimeStatus();
            mesTimeStatus.setStatus(1);
            mesTimeStatus.setBeginTime(DateUtil.format(endTime, "yyyy-MM-dd HH:mm:ss"));
            List<MesTimeStatus> mesTimeStatuses = mesTimeStatusDao.findAllUsingMesTimeStatus(mesTimeStatus);
            if (CollUtil.isEmpty(mesTimeStatuses)) {
                return;
            }
            for (MesTimeStatus timeStatus : mesTimeStatuses) {
                List<MesInspectRecord> mesInspectRecords;
                String schoolId = timeStatus.getSchoolId();
                MesCommonConfig mesC = new MesCommonConfig();
                mesC.setSchoolId(schoolId);
                ResponseJson responseJson = schoolFeign.findSchoolExpireOrSchoolYear(schoolId);
                if (!responseJson.getResult().isSuccess()) {
                    continue;
                }
                SchoolYear currentSchoolYear = mesCustomTitleDao.findCurrentSchoolYear(schoolId);
                String nextTermBegin = currentSchoolYear.getNextTermBegin();
                int term = 0;
                if (DateUtil.parse(nextTermBegin, "yyyy-MM-dd").getTime() <= cal.getTime().getTime()) {
                    term = 1;
                }
                mesC.setTimeStatusId(timeStatus.getId());
                MesCommonConfig mesCommonConfig = mesCommonConfigDao.findMesCommonConfigListByCondition(mesC).get(0);
                MesInspectRecord mesInspectRecord = new MesInspectRecord();
                mesInspectRecord.setSchoolId(schoolId);
                mesInspectRecord.setEndTime(DateUtil.format(endTime, "yyyy-MM-dd HH:mm:ss"));
                if (DateUtil.parse(timeStatus.getCreateTime(), "yyyy-MM-dd HH:mm:ss").getTime() > beginTime.getTime() && DateUtil.parse(timeStatus.getCreateTime(), "yyyy-MM-dd HH:mm:ss").getTime() < endTime.getTime()) {
                    //这天有最新制度
                    beginTime = DateUtil.parse(timeStatus.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
                    mesInspectRecord.setBeginTime(DateUtil.format(beginTime, "yyyy-MM-dd HH:mm:ss"));
                    mesInspectRecord.setStatus(1);
                } else if (DateUtil.parse(timeStatus.getCreateTime(), "yyyy-MM-dd HH:mm:ss").getTime() > endTime.getTime()) {
                    //这天制度不是最新
                    mesTimeStatus.setSchoolId(schoolId);
                    mesTimeStatus.setEndTime(DateUtil.format(endTime, "yyyy-MM-dd HH:mm:ss"));
                    MesTimeStatus mesTimeStatusListWhereStatusIsZero = mesTimeStatusDao.findMesTimeStatusListWhereCreateTimeMax(mesTimeStatus);
                    if (Objects.nonNull(mesTimeStatusListWhereStatusIsZero) && beginTime.getTime() < DateUtil.parse(mesTimeStatusListWhereStatusIsZero.getCreateTime(), "yyyy-MM-dd HH:mm:ss").getTime()) {
                        beginTime = DateUtil.parse(mesTimeStatusListWhereStatusIsZero.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
                    }
                    mesInspectRecord.setBeginTime(DateUtil.format(beginTime, "yyyy-MM-dd HH:mm:ss"));
                    mesInspectRecord.setStatus(0);
                    //将当前生效的mesCommonConfig换成对应的mesCommonConfig
                    mesCommonConfig = mesCommonConfigDao.findMesCommonConfigByTimeStatusId(mesTimeStatusListWhereStatusIsZero.getId());
                } else {
                    mesInspectRecord.setBeginTime(DateUtil.format(beginTime, "yyyy-MM-dd HH:mm:ss"));
                    mesInspectRecord.setStatus(1);
                }
                List<JwClasses> jwClasses = mesSchoolEvaluationDao.findJwClassesBySchoolId(schoolId);
                List<MesSchoolEvaluation> mesSchoolEvaluations = new ArrayList<>();
                List<MesInstitution> institutions = mesInstitutionDao.findMesInstitutionsByTimeStatusId(mesCommonConfig.getTimeStatusId());
                List<MesInstitution> mesInstitutions = ObjectKit.buildTree(institutions, "-1");
                for (JwClasses jwClass : jwClasses) {
                    List<Integer> scores = new ArrayList<>();
                    MesSchoolEvaluation mesSchoolEvaluation = new MesSchoolEvaluation();
                    mesInspectRecord.setClassId(jwClass.getId());
                    mesInspectRecords = mesInspectRecordDao.findMesInspectRecordByTime(mesInspectRecord);
                    for (MesInstitution institution : mesInstitutions) {
                        int scoreSum = 0;
                        for (MesInstitution child : institution.getChildren()) {
                            MesInspectRecord mesInspectRecord1 = new MesInspectRecord();
                            mesInspectRecord1.setClassId(jwClass.getId());
                            mesInspectRecord1.setSchoolId(schoolId);
                            mesInspectRecord1.setInstitutionId(child.getId());
                            mesInspectRecord1.setBeginTime(DateUtil.format(beginTime, "yyyy-MM-dd HH:mm:ss"));
                            mesInspectRecord1.setEndTime(DateUtil.format(endTime, "yyyy-MM-dd HH:mm:ss"));
                            Integer score = mesInspectRecordDao.findMesInspectRecordCountByTime(mesInspectRecord1);
                            if (Objects.isNull(score)) {
                                score = 0;
                            }
                            scoreSum += score;
                        }
                        scores.add(scoreSum);
                    }
                    int score = 100;
                    for (MesInspectRecord inspectRecord : mesInspectRecords) {
                        score += inspectRecord.getInstitutionScore();
                    }
                    scores.add(score);
                    mesSchoolEvaluation.setId(sequenceId.nextId());
                    mesSchoolEvaluation.setClassId(jwClass.getId());
                    mesSchoolEvaluation.setGradeId(jwClass.getGradeId());
                    mesSchoolEvaluation.setClassName(jwClass.getGradeName() + jwClass.getNumber() + "班");
                    mesSchoolEvaluation.setCreateTime(DateUtil.formatDateTime(cal.getTime()));
                    mesSchoolEvaluation.setSchoolId(schoolId);
                    mesSchoolEvaluation.setTimeStatusId(mesCommonConfig.getTimeStatusId());
                    mesSchoolEvaluation.setFormData(scores);
                    mesSchoolEvaluation.setFromTo(currentSchoolYear.getFromTo());
                    mesSchoolEvaluation.setSchoolYearId(currentSchoolYear.getId());
                    mesSchoolEvaluation.setTerm(term);
                    mesSchoolEvaluations.add(mesSchoolEvaluation);
                }
                mongoTemplate.insertAll(mesSchoolEvaluations);
            }
        }).start();
    }

    /**
     * 如果定时任务异常，可以手动保存学校日评比数据，参数：'2019-09-01'
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveMesSchoolEvaluationByAdmin(String timeStr) {
        new Thread(() -> {
            Date beginTime = DateUtil.parse(timeStr, "yyyy-MM-dd");
            Date endTime = DateUtil.parse(timeStr + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            cal.setTime(beginTime);
            MesTimeStatus mesTimeStatus = new MesTimeStatus();
            mesTimeStatus.setStatus(1);
            mesTimeStatus.setBeginTime(DateUtil.format(endTime, "yyyy-MM-dd HH:mm:ss"));
            List<Criteria> criteriaList = new ArrayList<>();
            criteriaList.add(where("createTime").gte(DateUtil.format(beginTime, "yyyy-MM-dd HH:mm:ss")).lte(DateUtil.format(endTime, "yyyy-MM-dd HH:mm:ss")));
            Query query = query(new Criteria().andOperator(criteriaList.toArray(new Criteria[criteriaList.size()])));
            List<MesSchoolEvaluation> list = mongoTemplate.find(query, MesSchoolEvaluation.class);
            if (CollUtil.isNotEmpty(list)) {
                return;
            }
            List<MesTimeStatus> mesTimeStatuses = mesTimeStatusDao.findAllUsingMesTimeStatus(mesTimeStatus);
            if (CollUtil.isEmpty(mesTimeStatuses)) {
                return;
            }
            for (MesTimeStatus timeStatus : mesTimeStatuses) {
                List<MesInspectRecord> mesInspectRecords;
                String schoolId = timeStatus.getSchoolId();
                MesCommonConfig mesC = new MesCommonConfig();
                mesC.setSchoolId(schoolId);
                ResponseJson responseJson = schoolFeign.findSchoolExpireOrSchoolYear(schoolId);
                if (!responseJson.getResult().isSuccess()) {
                    continue;
                }
                SchoolYear currentSchoolYear = mesCustomTitleDao.findCurrentSchoolYear(schoolId);
                String nextTermBegin = currentSchoolYear.getNextTermBegin();
                int term = 0;
                if (DateUtil.parse(nextTermBegin, "yyyy-MM-dd").getTime() <= cal.getTime().getTime()) {
                    term = 1;
                }
                mesC.setTimeStatusId(timeStatus.getId());
                MesCommonConfig mesCommonConfig = mesCommonConfigDao.findMesCommonConfigListByCondition(mesC).get(0);
                MesInspectRecord mesInspectRecord = new MesInspectRecord();
                mesInspectRecord.setSchoolId(schoolId);
                mesInspectRecord.setEndTime(DateUtil.format(endTime, "yyyy-MM-dd HH:mm:ss"));
                if (DateUtil.parse(timeStatus.getCreateTime(), "yyyy-MM-dd HH:mm:ss").getTime() > beginTime.getTime() && DateUtil.parse(timeStatus.getCreateTime(), "yyyy-MM-dd HH:mm:ss").getTime() < endTime.getTime()) {
                    //这天有最新制度
                    beginTime = DateUtil.parse(timeStatus.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
                    mesInspectRecord.setBeginTime(DateUtil.format(beginTime, "yyyy-MM-dd HH:mm:ss"));
                    mesInspectRecord.setStatus(1);
                } else if (DateUtil.parse(timeStatus.getCreateTime(), "yyyy-MM-dd HH:mm:ss").getTime() > endTime.getTime()) {
                    //这天制度不是最新
                    mesTimeStatus.setSchoolId(schoolId);
                    mesTimeStatus.setEndTime(DateUtil.format(endTime, "yyyy-MM-dd HH:mm:ss"));
                    MesTimeStatus mesTimeStatusListWhereStatusIsZero = mesTimeStatusDao.findMesTimeStatusListWhereCreateTimeMax(mesTimeStatus);
                    if (Objects.nonNull(mesTimeStatusListWhereStatusIsZero) && beginTime.getTime() < DateUtil.parse(mesTimeStatusListWhereStatusIsZero.getCreateTime(), "yyyy-MM-dd HH:mm:ss").getTime()) {
                        beginTime = DateUtil.parse(mesTimeStatusListWhereStatusIsZero.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
                    }
                    mesInspectRecord.setBeginTime(DateUtil.format(beginTime, "yyyy-MM-dd HH:mm:ss"));
                    mesInspectRecord.setStatus(0);
                    //将当前生效的mesCommonConfig换成对应的mesCommonConfig
                    mesCommonConfig = mesCommonConfigDao.findMesCommonConfigByTimeStatusId(mesTimeStatusListWhereStatusIsZero.getId());
                } else {
                    mesInspectRecord.setBeginTime(DateUtil.format(beginTime, "yyyy-MM-dd HH:mm:ss"));
                    mesInspectRecord.setStatus(1);
                }
                List<JwClasses> jwClasses = mesSchoolEvaluationDao.findJwClassesBySchoolId(schoolId);
                List<MesSchoolEvaluation> mesSchoolEvaluations = new ArrayList<>();
                List<MesInstitution> institutions = mesInstitutionDao.findMesInstitutionsByTimeStatusId(mesCommonConfig.getTimeStatusId());
                List<MesInstitution> mesInstitutions = ObjectKit.buildTree(institutions, "-1");
                for (JwClasses jwClass : jwClasses) {
                    List<Integer> scores = new ArrayList<>();
                    MesSchoolEvaluation mesSchoolEvaluation = new MesSchoolEvaluation();
                    mesInspectRecord.setClassId(jwClass.getId());
                    mesInspectRecords = mesInspectRecordDao.findMesInspectRecordByTime(mesInspectRecord);
                    for (MesInstitution institution : mesInstitutions) {
                        int scoreSum = 0;
                        for (MesInstitution child : institution.getChildren()) {
                            MesInspectRecord mesInspectRecord1 = new MesInspectRecord();
                            mesInspectRecord1.setClassId(jwClass.getId());
                            mesInspectRecord1.setSchoolId(schoolId);
                            mesInspectRecord1.setInstitutionId(child.getId());
                            mesInspectRecord1.setBeginTime(DateUtil.format(beginTime, "yyyy-MM-dd HH:mm:ss"));
                            mesInspectRecord1.setEndTime(DateUtil.format(endTime, "yyyy-MM-dd HH:mm:ss"));
                            Integer score = mesInspectRecordDao.findMesInspectRecordCountByTime(mesInspectRecord1);
                            if (Objects.isNull(score)) {
                                score = 0;
                            }
                            scoreSum += score;
                        }
                        scores.add(scoreSum);
                    }
                    int score = 100;
                    for (MesInspectRecord inspectRecord : mesInspectRecords) {
                        score += inspectRecord.getInstitutionScore();
                    }
                    scores.add(score);
                    mesSchoolEvaluation.setId(sequenceId.nextId());
                    mesSchoolEvaluation.setClassId(jwClass.getId());
                    mesSchoolEvaluation.setGradeId(jwClass.getGradeId());
                    mesSchoolEvaluation.setClassName(jwClass.getGradeName() + jwClass.getNumber() + "班");
                    mesSchoolEvaluation.setCreateTime(DateUtil.formatDateTime(cal.getTime()));
                    mesSchoolEvaluation.setSchoolId(schoolId);
                    mesSchoolEvaluation.setTimeStatusId(mesCommonConfig.getTimeStatusId());
                    mesSchoolEvaluation.setFormData(scores);
                    mesSchoolEvaluation.setFromTo(currentSchoolYear.getFromTo());
                    mesSchoolEvaluation.setSchoolYearId(currentSchoolYear.getId());
                    mesSchoolEvaluation.setTerm(term);
                    mesSchoolEvaluations.add(mesSchoolEvaluation);
                }
                mongoTemplate.insertAll(mesSchoolEvaluations);
            }
        }).start();
    }

    public List<MesSchoolEvaluation> findTodayMesSchoolEvaluation(String schoolId, Date beginTime, Date endTime, String gradeId) {
        MesTimeStatus mesTimeStatus = new MesTimeStatus();
        mesTimeStatus.setStatus(1);
        mesTimeStatus.setSchoolId(schoolId);
        List<MesTimeStatus> times = mesTimeStatusDao.findMesTimeStatusListByCondition(mesTimeStatus);
        if (CollUtil.isEmpty(times)) {
            return new ArrayList<MesSchoolEvaluation>();
        }
        MesTimeStatus timeStatus = times.get(0);
        if (Objects.isNull(timeStatus)) {
            return new ArrayList<MesSchoolEvaluation>();
        }
        List<MesInspectRecord> mesInspectRecords;
        MesCommonConfig mesC = new MesCommonConfig();
        mesC.setSchoolId(schoolId);
        MesCommonConfig mesCommonConfig = mesCommonConfigDao.findMesCommonConfigListByCondition(mesC).get(0);
        MesInspectRecord mesInspectRecord = new MesInspectRecord();
        mesInspectRecord.setSchoolId(schoolId);
        mesInspectRecord.setEndTime(DateUtil.format(endTime, "yyyy-MM-dd HH:mm:ss"));
        if (DateUtil.parse(timeStatus.getCreateTime(), "yyyy-MM-dd HH:mm:ss").getTime() > beginTime.getTime() && DateUtil.parse(timeStatus.getCreateTime(), "yyyy-MM-dd HH:mm:ss").getTime() < endTime.getTime()) {
            //这天有最新制度
            beginTime = DateUtil.parse(timeStatus.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
            mesInspectRecord.setBeginTime(DateUtil.format(beginTime, "yyyy-MM-dd HH:mm:ss"));
            mesInspectRecord.setStatus(1);
        } else if (DateUtil.parse(timeStatus.getCreateTime(), "yyyy-MM-dd HH:mm:ss").getTime() > endTime.getTime()) {
            //这天制度不是最新
            mesTimeStatus.setSchoolId(schoolId);
            mesTimeStatus.setEndTime(DateUtil.format(endTime, "yyyy-MM-dd HH:mm:ss"));
            MesTimeStatus mesTimeStatusListWhereStatusIsZero = mesTimeStatusDao.findMesTimeStatusListWhereCreateTimeMax(mesTimeStatus);
            if (Objects.isNull(mesTimeStatusListWhereStatusIsZero)) {
                return new ArrayList<MesSchoolEvaluation>();
            }
            if (beginTime.getTime() < DateUtil.parse(mesTimeStatusListWhereStatusIsZero.getCreateTime(), "yyyy-MM-dd HH:mm:ss").getTime()) {
                beginTime = DateUtil.parse(mesTimeStatusListWhereStatusIsZero.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
            }
            mesInspectRecord.setBeginTime(DateUtil.format(beginTime, "yyyy-MM-dd HH:mm:ss"));
            mesInspectRecord.setStatus(0);
            //将当前生效的mesCommonConfig换成对应的mesCommonConfig
            mesCommonConfig = mesCommonConfigDao.findMesCommonConfigByTimeStatusId(mesTimeStatusListWhereStatusIsZero.getId());
        } else {
            mesInspectRecord.setBeginTime(DateUtil.format(beginTime, "yyyy-MM-dd HH:mm:ss"));
            mesInspectRecord.setStatus(1);
        }
        List<JwClasses> jwClasses = mesSchoolEvaluationDao.findJwClassesBySchoolIdAndGradeId(schoolId, gradeId);
        List<MesSchoolEvaluation> mesSchoolEvaluations = new ArrayList<>();
        List<MesInstitution> institutions = mesInstitutionDao.findMesInstitutionsByTimeStatusId(mesCommonConfig.getTimeStatusId());
        List<MesInstitution> mesInstitutions = ObjectKit.buildTree(institutions, "-1");
        for (JwClasses jwClass : jwClasses) {
            List<Integer> scores = new ArrayList<>();
            MesSchoolEvaluation mesSchoolEvaluation = new MesSchoolEvaluation();
            mesInspectRecord.setClassId(jwClass.getId());
            mesInspectRecords = mesInspectRecordDao.findMesInspectRecordByTime(mesInspectRecord);
            for (MesInstitution institution : mesInstitutions) {
                Integer scoreSum = 0;
                if (CollUtil.isNotEmpty(institution.getChildren())) {
                    for (MesInstitution child : institution.getChildren()) {
                        MesInspectRecord mesInspectRecord1 = new MesInspectRecord();
                        mesInspectRecord1.setClassId(jwClass.getId());
                        mesInspectRecord1.setSchoolId(schoolId);
                        mesInspectRecord1.setInstitutionId(child.getId());
                        mesInspectRecord1.setBeginTime(DateUtil.format(beginTime, "yyyy-MM-dd HH:mm:ss"));
                        mesInspectRecord1.setEndTime(DateUtil.format(endTime, "yyyy-MM-dd HH:mm:ss"));
                        Integer score = mesInspectRecordDao.findMesInspectRecordCountByTime(mesInspectRecord1);
                        if (Objects.isNull(score)) {
                            score = 0;
                        }
                        scoreSum += score;
                    }
                }
                scores.add(scoreSum);
            }
            int score = 100;
            for (MesInspectRecord inspectRecord : mesInspectRecords) {
                score += inspectRecord.getInstitutionScore();
            }
            scores.add(score);
            mesSchoolEvaluation.setId(sequenceId.nextId());
            mesSchoolEvaluation.setClassId(jwClass.getId());
            mesSchoolEvaluation.setGradeId(jwClass.getGradeId());
            mesSchoolEvaluation.setClassName(jwClass.getGradeName() + jwClass.getNumber() + "班");
            mesSchoolEvaluation.setSchoolId(schoolId);
            mesSchoolEvaluation.setTimeStatusId(mesCommonConfig.getTimeStatusId());
            mesSchoolEvaluation.setFormData(scores);
            mesSchoolEvaluations.add(mesSchoolEvaluation);
        }
        return mesSchoolEvaluations;
    }


    /**
     * 查询学校评比页面是否展示周评比、月评比、学期评比table页
     * @param schoolId
     * @return
     */
    @Transactional
    public List<MesInstitution> selectTableWhetherToShow(String schoolId) {
        List<MesInstitution> integers = new ArrayList<>();
        MesInstitution mesInstitution = new MesInstitution();
        mesInstitution.setName("日评比");
        mesInstitution.setId("0");
        integers.add(mesInstitution);
        MesTimeStatus mesTimeStatus = new MesTimeStatus();
        mesTimeStatus.setSchoolId(schoolId);
        mesTimeStatus.setStatus(1);
        List<MesTimeStatus> mesTimeStatusListByCondition = mesTimeStatusDao.findMesTimeStatusListByCondition(mesTimeStatus);
        if (CollUtil.isEmpty(mesTimeStatusListByCondition)) {
            return integers;
        }
        MesTimeStatus timeStatusIs1 = mesTimeStatusListByCondition.get(0);
        MesCommonConfig mesCommonConfig = mesCommonConfigDao.findMesCommonConfigByTimeStatusId(timeStatusIs1.getId());
        SchoolYear schoolYear = mesSchoolEvaluationDao.findSchoolYearBySchoolId(timeStatusIs1.getSchoolId());
        SchoolWeek schoolweek = findSchoolWeekBySchoolYearId(schoolYear.getId(), 0);
        SchoolWeek schoolweek1 = findSchoolWeekBySchoolYearId(schoolYear.getId(), 1);
        if (mesCommonConfig.getIsByWeek() == 1) {
            MesInstitution institution = new MesInstitution();
            institution.setName("周评比");
            institution.setId("1");
            if (schoolweek == null && schoolweek1 == null) {
                institution.setIsShow(0);
            } else {
                institution.setIsShow(1);
            }
            integers.add(institution);
        }
        if (mesCommonConfig.getIsByMonth() == 1) {
            MesInstitution institution = new MesInstitution();
            institution.setName("月评比");
            institution.setId("2");
            integers.add(institution);
        }
        if (mesCommonConfig.getIsBySemester() == 1) {
            MesInstitution institution = new MesInstitution();
            institution.setName("学期评比");
            institution.setId("3");
            if (schoolweek == null && schoolweek1 == null) {
                institution.setIsShow(0);
            } else {
                institution.setIsShow(1);
            }
            integers.add(institution);
        }
        return integers;
    }

    /**
     * 查询学校日评比表格
     *
     * @param mesSchoolEvaluationVo
     * @return
     */
    @Transactional
    public Map<String, Object> findEvaluationListByDay(MesSchoolEvaluationVo mesSchoolEvaluationVo) {
        Map<String, Object> map = new HashMap<>();
        List<MesSchoolEvaluation> list = new ArrayList<>();
        String searchTime = mesSchoolEvaluationVo.getSearchTime();
        Date searchDate = DateUtil.parse(searchTime, "yyyy/MM/dd");
        searchTime = DateUtil.format(searchDate, "yyyy-MM-dd");
        Date date = new Date();
        Date today = DateUtil.parse(DateUtil.format(date, "yyyy-MM-dd"), "yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date yesterday = cal.getTime();
        String schoolId = mesSchoolEvaluationVo.getSchoolId();
        String schoolName = mesSchoolEvaluationDao.findSchoolNameBySchoolId(schoolId);
        if (searchDate.getTime() == today.getTime()) {
            list = findTodayMesSchoolEvaluation(schoolId, today, date, mesSchoolEvaluationVo.getGradeId());
            List<MesInstitution> tableHead = findTableHead(today, date, schoolId);
            map.put("tableHead", tableHead);
            map.put("beginTime", DateUtil.format(today, "yyyy-MM-dd HH:mm:ss"));
            map.put("endTime", DateUtil.format(date, "yyyy-MM-dd HH:mm:ss"));
        } else if (searchDate.getTime() == yesterday.getTime()) {
            list = findTodayMesSchoolEvaluation(schoolId, yesterday, today, mesSchoolEvaluationVo.getGradeId());
            List<MesInstitution> tableHead = findTableHead(yesterday, today, schoolId);
            map.put("tableHead", tableHead);
            map.put("beginTime", DateUtil.format(yesterday, "yyyy-MM-dd HH:mm:ss"));
            map.put("endTime", DateUtil.format(yesterday, "yyyy-MM-dd") + "24:00:00");
        } else {
            String beginTime = searchTime + " 00:00:00";
            String endTime = searchTime + " 23:59:59";
            JwClasses jwClasses = new JwClasses();
            jwClasses.setGradeId(mesSchoolEvaluationVo.getGradeId());
            jwClasses.setSchoolId(schoolId);
            List<JwClasses> jwClassesList = jwClassesFeign.findClassesByGradeId(jwClasses);
            Date beginDate = DateUtil.parse(beginTime);
            Date endDate = DateUtil.parse(endTime);
            List<MesInstitution> tableHead = findTableHead(beginDate, endDate, schoolId);
            for (JwClasses c : jwClassesList) {
                List<Criteria> criteriaList = new ArrayList<>();
                criteriaList.add(where("classId").is(c.getId()));
                criteriaList.add(where("schoolId").is(schoolId));
                criteriaList.add(where("createTime").gte(beginTime).lte(endTime));
                Query query = query(new Criteria().andOperator(criteriaList.toArray(new Criteria[criteriaList.size()])));
                List<MesSchoolEvaluation> mesSchoolEvaluations = mongoTemplate.find(query, MesSchoolEvaluation.class);
                list.addAll(mesSchoolEvaluations);
            }
            map.put("tableHead", tableHead);
            map.put("beginTime", DateUtil.format(beginDate, "yyyy-MM-dd HH:mm:ss"));
            map.put("endTime", DateUtil.format(endDate, "yyyy-MM-dd HH:mm:ss"));
        }
        if (StringUtils.isNotEmpty(mesSchoolEvaluationVo.getPager().getSortField())) {
            int i;
            if ("-1".equals(mesSchoolEvaluationVo.getPager().getSortField())) {
                if (CollUtil.isNotEmpty(list)) {
                    i = list.get(0).getFormData().size() - 1;
                } else {
                    i = 0;
                }
            } else {
                i = Integer.parseInt(mesSchoolEvaluationVo.getPager().getSortField());
            }
            if ("asc".equals(mesSchoolEvaluationVo.getPager().getSortOrder())) {
                Collections.sort(list, (o1, o2) -> {
                    return o1.getFormData().get(i) - o2.getFormData().get(i);
                });
            } else if ("desc".equals(mesSchoolEvaluationVo.getPager().getSortOrder())) {
                Collections.sort(list, (o1, o2) -> {
                    return o2.getFormData().get(i) - o1.getFormData().get(i);
                });
            }
        }
        map.put("list", list);
        map.put("schoolName", schoolName);
        return map;
    }

    @Transactional
    public Map findSchoolWeekOrMonthByCondition(MesSchoolEvaluationVo mesSchoolEvaluationVo) {
        HashMap<String, Object> map = new HashMap<>();
        SchoolYear schoolYear = mesSchoolEvaluationDao.findSchoolYearBySchoolId(mesSchoolEvaluationVo.getSchoolId());
        if (ObjectUtil.isNull(schoolYear)) {
            map.put("msg", "未设置学年");
            return map;
        }
        map.put("schoolYear", schoolYear.getFromTo());
        map.put("schoolYearId", schoolYear.getId());
        List<SchoolWeek> schoolWeeks = findSchoolWeekBySchoolYearId(schoolYear.getId());
        LocalDate now = LocalDate.now();
        if (now.isAfter(LocalDate.parse(schoolYear.getNextTermBegin()))) {
            map.put("term", 1);
        } else {
            map.put("term", 0);
        }
        if (Objects.equals(mesSchoolEvaluationVo.getEvaluationType(), 2)) {
            if (CollectionUtils.isEmpty(schoolWeeks)) {
                return map;
            }
            map.put("schoolWeeks", schoolWeeks);
            if (Objects.equals(map.get("term"), 0)) {
                List<SchoolWeek> lastTermSchoolWeeks = schoolWeeks.stream().filter(schoolWeek -> schoolWeek.getTermType().equals(0)).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(lastTermSchoolWeeks)) {
                    SchoolWeek lastTermSchoolWeek = lastTermSchoolWeeks.get(0);
                    setNowWeek(now, lastTermSchoolWeek, map);
                    return map;
                }
            } else {
                List<SchoolWeek> nextTermSchoolWeeks = schoolWeeks.stream().filter(schoolWeek -> schoolWeek.getTermType().equals(1)).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(nextTermSchoolWeeks)) {
                    SchoolWeek nextTermSchoolWeek = nextTermSchoolWeeks.get(0);
                    setNowWeek(now, nextTermSchoolWeek, map);
                    return map;
                }
            }
            return map;

        } else if (Objects.equals(mesSchoolEvaluationVo.getEvaluationType(), 3)) {
            map.put("nowMonth", now.getMonthValue());
            map.put("lastTermBeginMonth", LocalDate.parse(schoolYear.getLastTermBegin()).getMonthValue());
            if (LocalDate.parse(schoolYear.getNextTermBegin()).getDayOfMonth() == 1) {
                map.put("lastTermEndMonth", LocalDate.parse(schoolYear.getNextTermBegin()).minusMonths(1).getMonthValue());
            } else {
                map.put("lastTermEndMonth", LocalDate.parse(schoolYear.getNextTermBegin()).getMonthValue());
            }
            if (Objects.equals(map.get("term"), 0)) {
                return map;
            } else {
                map.put("nextTermBeginMonth", LocalDate.parse(schoolYear.getNextTermBegin()).getMonthValue());
                if (LocalDate.parse(schoolYear.getLastTermBegin()).getDayOfMonth() == 1) {
                    map.put("nextTermEndMonth", LocalDate.parse(schoolYear.getLastTermBegin()).minusMonths(1).getMonthValue());
                } else {
                    map.put("nextTermEndMonth", LocalDate.parse(schoolYear.getLastTermBegin()).getMonthValue());
                }
                return map;
            }
        } else {
            if (CollectionUtils.isEmpty(schoolWeeks)) {
                return map;
            }
            if (Objects.equals(map.get("term"), 0)) {
                List<SchoolWeek> lastTermSchoolWeeks = schoolWeeks.stream().filter(schoolWeek -> schoolWeek.getTermType().equals(0)).collect(Collectors.toList());
                return selectTerm(now, lastTermSchoolWeeks, map, null);
            } else {
                List<SchoolWeek> lastTermSchoolWeeks = schoolWeeks.stream().filter(schoolWeek -> schoolWeek.getTermType().equals(0)).collect(Collectors.toList());
                List<SchoolWeek> nextTermSchoolWeeks = schoolWeeks.stream().filter(schoolWeek -> schoolWeek.getTermType().equals(1)).collect(Collectors.toList());
                return selectTerm(now, nextTermSchoolWeeks, map, lastTermSchoolWeeks);
            }
        }
    }

    private Map selectTerm(LocalDate now, List<SchoolWeek> schoolWeeks, Map map, List<SchoolWeek> lastTermSchoolWeeks) {
        if (map.get("term").equals(0)) {
            if (CollectionUtils.isNotEmpty(schoolWeeks)) {
                return termMap(schoolWeeks, map, now);
            } else {
                map.put("msg", "当前学期未设置学周");
                return map;
            }
        } else {
            if (CollectionUtil.isNotEmpty(lastTermSchoolWeeks)) {
                //上学期设置过学周
                if (CollectionUtils.isNotEmpty(schoolWeeks)) {
                    SchoolWeek schoolWeek = schoolWeeks.get(0);
                    if (now.isAfter(LocalDate.parse(schoolWeek.getEndTime()).plusDays(1))) {
                        return map;
                    } else {
                        map.put("term", 0);
                        return map;
                    }
                } else {
                    if (now.isAfter(LocalDate.parse(lastTermSchoolWeeks.get(0).getEndTime()).plusDays(1))) {
                        map.put("term", 0);
                        return map;
                    } else {
                        map.put("term", -1);
                        return map;
                    }
                }
            } else {
                //上学期未设置过学周
                if (CollectionUtils.isNotEmpty(schoolWeeks)) {
                    return termMap(schoolWeeks, map, now);
                } else {
                    map.put("msg", "未设置学周");
                    return map;
                }
            }
        }
    }

    private Map termMap(List<SchoolWeek> schoolWeeks, Map map, LocalDate now) {
        SchoolWeek schoolWeek = schoolWeeks.get(0);
        if (now.isAfter(LocalDate.parse(schoolWeek.getEndTime()).plusDays(1))) {
            return map;
        } else {
            map.put("term", -1);
            return map;
        }
    }

    public void setNowWeek(LocalDate now, SchoolWeek schoolWeek, Map map) {
        if (now.isAfter(LocalDate.parse(schoolWeek.getEndTime()))) {
            map.put("nowWeek", schoolWeek.getWeekCount());
        } else {
            LocalDate startWeekDateSunday = LocalDate.parse(schoolWeek.getStartTime()).with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
            LocalDate nowWeekMonday = now.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
            long days = nowWeekMonday.toEpochDay() - startWeekDateSunday.toEpochDay();
            long nowWeek = days / 7 + 1;
            map.put("nowWeek", nowWeek);
        }

    }

    public List<MesInstitution> findTableHead(Date beginTime, Date endTime, String schoolId) {
        MesTimeStatus mesTimeStatus = new MesTimeStatus();
        mesTimeStatus.setStatus(1);
        mesTimeStatus.setSchoolId(schoolId);
        List<MesTimeStatus> mesTimeStatuses = mesTimeStatusDao.findMesTimeStatusListByCondition(mesTimeStatus);
        if (CollUtil.isEmpty(mesTimeStatuses)) {
            return null;
        }
        MesTimeStatus timeStatus = mesTimeStatuses.get(0);
        if (DateUtil.parse(timeStatus.getCreateTime(), "yyyy-MM-dd HH:mm:ss").getTime() >= beginTime.getTime() && DateUtil.parse(timeStatus.getCreateTime(), "yyyy-MM-dd HH:mm:ss").getTime() <= endTime.getTime()) {
            //这天有最新制度
            beginTime = DateUtil.parse(timeStatus.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
        } else if (DateUtil.parse(timeStatus.getCreateTime(), "yyyy-MM-dd HH:mm:ss").getTime() > endTime.getTime()) {
            //这天制度不是最新
            mesTimeStatus.setEndTime(DateUtil.format(endTime, "yyyy-MM-dd HH:mm:ss"));
            MesTimeStatus mesTimeStatusListWhereStatusIsZero = mesTimeStatusDao.findMesTimeStatusListWhereCreateTimeMax(mesTimeStatus);
            if (Objects.nonNull(mesTimeStatusListWhereStatusIsZero)) {
                beginTime = DateUtil.parse(mesTimeStatusListWhereStatusIsZero.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
            } else {
                return null;
            }
        }
        mesTimeStatus.setEndTime(DateUtil.format(endTime, "yyyy-MM-dd HH:mm:ss"));
        MesTimeStatus mesTimeStatusListWhereCreateTime = mesTimeStatusDao.findMesTimeStatusListWhereCreateTimeMax(mesTimeStatus);
        List<MesInstitution> list = mesInstitutionDao.findMesInstitutionListByTimeStatusId(mesTimeStatusListWhereCreateTime.getId());
        MesInstitution mesInstitution = new MesInstitution();
        mesInstitution.setName("总分");
        mesInstitution.setId("-1");
        list.add(mesInstitution);
        return list;
    }

    public List<SchoolWeek> findSchoolWeekBySchoolYearId(String schoolYearId) {
        SchoolYear schoolYear = mesInstitutionDao.findSchoolYearById(schoolYearId);
        List<SchoolWeek> schoolWeeks = mesInstitutionDao.findSchoolWeekBySchoolYearId(schoolYearId);
        if (CollUtil.isNotEmpty(schoolWeeks)) {
            for (SchoolWeek schoolWeek : schoolWeeks) {
                setSchoolWeekEndTime(schoolWeek, schoolYear, schoolWeek.getTermType());
                setWeekCount(schoolWeek);
            }
        }
        return schoolWeeks;
    }

    public SchoolWeek findSchoolWeekBySchoolYearId(String schoolYearId, Integer term) {
        if (term == null) return null;
        SchoolYear schoolYear = mesInstitutionDao.findSchoolYearById(schoolYearId);
        SchoolWeek schoolWeek = mesSchoolEvaluationDao.findSchoolWeekBySchoolYearIdAndTerm(schoolYearId, term);
        setSchoolWeekEndTime(schoolWeek, schoolYear, term);
        setWeekCount(schoolWeek);
        return schoolWeek;
    }

    public void setSchoolWeekEndTime(SchoolWeek schoolWeek, SchoolYear schoolYear, Integer term) {
        if (Optional.ofNullable(schoolWeek).isPresent()) {
            if (term == 0) {
                schoolWeek.setEndTime(schoolYear.getNextTermBegin());
            } else {
                if (StringUtils.isNotEmpty(schoolYear.getNextTermEnd())) {
                    schoolWeek.setEndTime(schoolYear.getNextTermEnd());
                } else {
                    String lastTermBegin = schoolYear.getLastTermBegin();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(DateUtil.parse(lastTermBegin, "yyyy-MM-dd"));
                    cal.add(Calendar.YEAR, 1);
                    cal.add(Calendar.DAY_OF_YEAR, -1);
                    schoolWeek.setEndTime(DateUtil.format(cal.getTime(), "yyyy-MM-dd"));
                }
            }
        }
    }

    public void setWeekCount(SchoolWeek schoolWeek) {
        if (Optional.ofNullable(schoolWeek).isPresent()) {
            String startTime = schoolWeek.getStartTime() + " 00:00:00";
            String endTime = schoolWeek.getEndTime() + " 23:59:59";
            Calendar cal = Calendar.getInstance();
            cal.setTime(DateUtil.parse(startTime, "yyyy-MM-dd HH:mm:ss"));
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            //时间设置为周一0点
            if (dayOfWeek == 1) {
                cal.add(Calendar.DAY_OF_WEEK, -6);
            } else if (dayOfWeek != 2) {
                cal.add(Calendar.DAY_OF_WEEK, 2 - cal.get(Calendar.DAY_OF_WEEK));
            }
            Date d = cal.getTime();
            cal.setTime(DateUtil.parse(endTime, "yyyy-MM-dd HH:mm:ss"));
            dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            //时间设置为周日23点59分
            if (dayOfWeek != 1) {
                cal.add(Calendar.DAY_OF_WEEK, 8 - cal.get(Calendar.DAY_OF_WEEK));
            }
            Date j = cal.getTime();
            long time = j.getTime() - d.getTime();
            int week = (int) ((time + 1000) / 604800000);
            schoolWeek.setWeekCount(week);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveMesSchoolEvaluationHonorByAdmin(String time) {
        new Thread(() -> {

            Date now=DateUtil.parse(time);
            List<MesClassHonor> mesClassHonorList = new ArrayList<>();
            Calendar cal = Calendar.getInstance();
            cal.setTime(now);
            cal.add(Calendar.DAY_OF_MONTH, -2);
            //将开始时间设置为两天前0点
            Date beginTime = DateUtil.parse(DateUtil.format(cal.getTime(), "yyyy-MM-dd"), "yyyy-MM-dd");
            cal.setTime(beginTime);
            //将结束时间设置为两天前24点
            cal.add(Calendar.DAY_OF_MONTH, 1);
            Date endTime = cal.getTime();
            cal.add(Calendar.DAY_OF_MONTH, -1);
            MesTimeStatus mesTimeStatus = new MesTimeStatus();
            mesTimeStatus.setStatus(1);
            mesTimeStatus.setBeginTime(DateUtil.format(endTime, "yyyy-MM-dd HH:mm:ss"));
            List<MesTimeStatus> mesTimeStatuses = mesTimeStatusDao.findAllUsingMesTimeStatus(mesTimeStatus);
            if (CollUtil.isEmpty(mesTimeStatuses)) {
                return;
            }
            for (MesTimeStatus timeStatus : mesTimeStatuses) {
                String searchBeginTime;
                String searchEndTime;
                String schoolId = timeStatus.getSchoolId();
                List<String> gradeIds = mesSchoolEvaluationDao.findGradeIdBySchoolId(schoolId);
                if (CollUtil.isEmpty(gradeIds)) {
                    return;
                }
                Calendar instance = Calendar.getInstance();
                instance.setTime(now);
                if (instance.get(Calendar.DAY_OF_WEEK) == 3) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(cal.getTime());
                    searchEndTime = DateUtil.format(calendar.getTime(), "yyyy-MM-dd") + " 23:59:59";
                    calendar.add(Calendar.DAY_OF_WEEK, -6);
                    searchBeginTime = DateUtil.format(calendar.getTime(), "yyyy-MM-dd") + " 00:00:00";
                    calendar.add(Calendar.DAY_OF_WEEK, 6);
                    MesTimeStatus mesTimeStatusIsUsing = findMesTimeStatusIsUsing(searchEndTime, schoolId);
                    if (DateUtil.parse(searchBeginTime, "yyyy-MM-dd HH:mm:ss").getTime()<DateUtil.parse(mesTimeStatusIsUsing.getCreateTime(),"yyyy-MM-dd HH:mm:ss").getTime()){
                        searchBeginTime=mesTimeStatusIsUsing.getCreateTime();
                    }
                    String timeStatusId = mesTimeStatusIsUsing.getId();
                    MesCommonConfig mesCommonConfigByTimeStatusId = mesCommonConfigDao.findMesCommonConfigByTimeStatusId(timeStatusId);
                    if (mesCommonConfigByTimeStatusId.getIsByWeek() == 1) {
                        for (String gradeId : gradeIds) {
                            List<JwClasses> jwClassesList = mesSchoolEvaluationDao.findJwClassesBySchoolIdAndGradeId(schoolId, gradeId);
                            List<MesClassHonor> mesClassHonors = setHonor(DateUtil.parse(searchBeginTime), DateUtil.parse(searchEndTime), timeStatusId, schoolId, jwClassesList, 2);
                            mesClassHonorList.addAll(mesClassHonors);
                        }
                    }
                }
                if (instance.get(Calendar.DAY_OF_MONTH) == 2) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(cal.getTime());
                    searchEndTime = DateUtil.format(calendar.getTime(), "yyyy-MM-dd") + " 23:59:59";
                    calendar.set(Calendar.DAY_OF_MONTH, 1);
                    searchBeginTime = DateUtil.format(calendar.getTime(), "yyyy-MM-dd") + " 00:00:00";
                    calendar.set(Calendar.DAY_OF_MONTH, 2);
                    calendar.add(Calendar.MONTH, 1);
                    MesTimeStatus mesTimeStatusIsUsing = findMesTimeStatusIsUsing(searchEndTime, schoolId);
                    if (DateUtil.parse(searchBeginTime, "yyyy-MM-dd HH:mm:ss").getTime()<DateUtil.parse(mesTimeStatusIsUsing.getCreateTime(),"yyyy-MM-dd HH:mm:ss").getTime()){
                        searchBeginTime=mesTimeStatusIsUsing.getCreateTime();
                    }
                    String timeStatusId = mesTimeStatusIsUsing.getId();
                    MesCommonConfig mesCommonConfigByTimeStatusId = mesCommonConfigDao.findMesCommonConfigByTimeStatusId(timeStatusId);
                    if (mesCommonConfigByTimeStatusId.getIsByMonth() == 1) {
                        for (String gradeId : gradeIds) {
                            List<JwClasses> jwClassesList = mesSchoolEvaluationDao.findJwClassesBySchoolIdAndGradeId(schoolId, gradeId);
                            List<MesClassHonor> mesClassHonors = setHonor(DateUtil.parse(searchBeginTime), DateUtil.parse(searchEndTime), timeStatusId, schoolId, jwClassesList, 3);
                            mesClassHonorList.addAll(mesClassHonors);
                        }
                    }
                }
                SchoolYear schoolYearBySchoolId = mesSchoolEvaluationDao.findSchoolYearBySchoolId(schoolId);
    /*            if (DateUtil.parse(schoolYearBySchoolId.getLastTermBegin(), "yyyy-MM-dd").getTime() == cal.getTime().getTime()) {
                    //当前版本只能查当前学年，所以这一块不做
                }*/
                if (DateUtil.parse(schoolYearBySchoolId.getNextTermBegin(), "yyyy-MM-dd").getTime() == cal.getTime().getTime()) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(cal.getTime());
                    calendar.add(Calendar.DAY_OF_MONTH, -1);
                    searchEndTime = DateUtil.format(calendar.getTime(), "yyyy-MM-dd") + " 23:59:59";
                    calendar.setTime(DateUtil.parse(schoolYearBySchoolId.getLastTermBegin(), "yyyy-MM-dd"));
                    searchBeginTime = DateUtil.format(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");
                    MesTimeStatus mesTimeStatusIsUsing = findMesTimeStatusIsUsing(searchEndTime, schoolId);
                    if (DateUtil.parse(searchBeginTime, "yyyy-MM-dd HH:mm:ss").getTime()<DateUtil.parse(mesTimeStatusIsUsing.getCreateTime(),"yyyy-MM-dd HH:mm:ss").getTime()){
                        searchBeginTime=mesTimeStatusIsUsing.getCreateTime();
                    }
                    String timeStatusId = mesTimeStatusIsUsing.getId();
                    MesCommonConfig mesCommonConfigByTimeStatusId = mesCommonConfigDao.findMesCommonConfigByTimeStatusId(timeStatusId);
                    if (mesCommonConfigByTimeStatusId.getIsBySemester() == 1) {
                        for (String gradeId : gradeIds) {
                            List<JwClasses> jwClassesList = mesSchoolEvaluationDao.findJwClassesBySchoolIdAndGradeId(schoolId, gradeId);
                            List<MesClassHonor> mesClassHonors = setHonor(DateUtil.parse(searchBeginTime), DateUtil.parse(searchEndTime), timeStatusId, schoolId, jwClassesList, 4);
                            mesClassHonorList.addAll(mesClassHonors);
                        }
                    }
                }
            }
            for (MesClassHonor mesClassHonor : mesClassHonorList) {
                mesClassHonor.setCreateTime(time);
            }
            mongoTemplate.insertAll(mesClassHonorList);
        }).start();
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveMesSchoolEvaluationHonor() {
        new Thread(() -> {
            List<MesClassHonor> mesClassHonorList = new ArrayList<>();
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, -2);
            //将开始时间设置为两天前0点
            Date beginTime = DateUtil.parse(DateUtil.format(cal.getTime(), "yyyy-MM-dd"), "yyyy-MM-dd");
            cal.setTime(beginTime);
            //将结束时间设置为两天前24点
            cal.add(Calendar.DAY_OF_MONTH, 1);
            Date endTime = cal.getTime();
            cal.add(Calendar.DAY_OF_MONTH, -1);
            MesTimeStatus mesTimeStatus = new MesTimeStatus();
            mesTimeStatus.setStatus(1);
            mesTimeStatus.setBeginTime(DateUtil.format(endTime, "yyyy-MM-dd HH:mm:ss"));
            List<MesTimeStatus> mesTimeStatuses = mesTimeStatusDao.findAllUsingMesTimeStatus(mesTimeStatus);
            if (CollUtil.isEmpty(mesTimeStatuses)) {
                return;
            }
            for (MesTimeStatus timeStatus : mesTimeStatuses) {
                String searchBeginTime;
                String searchEndTime;
                String schoolId = timeStatus.getSchoolId();
                List<String> gradeIds = mesSchoolEvaluationDao.findGradeIdBySchoolId(schoolId);
                if (CollUtil.isEmpty(gradeIds)) {
                    return;
                }
                Calendar instance = Calendar.getInstance();
                if (instance.get(Calendar.DAY_OF_WEEK) == 3) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(cal.getTime());
                    searchEndTime = DateUtil.format(calendar.getTime(), "yyyy-MM-dd") + " 23:59:59";
                    calendar.add(Calendar.DAY_OF_WEEK, -6);
                    searchBeginTime = DateUtil.format(calendar.getTime(), "yyyy-MM-dd") + " 00:00:00";
                    calendar.add(Calendar.DAY_OF_WEEK, 6);
                    MesTimeStatus mesTimeStatusIsUsing = findMesTimeStatusIsUsing(searchEndTime, schoolId);
                    if (DateUtil.parse(searchBeginTime, "yyyy-MM-dd HH:mm:ss").getTime()<DateUtil.parse(mesTimeStatusIsUsing.getCreateTime(),"yyyy-MM-dd HH:mm:ss").getTime()){
                        searchBeginTime=mesTimeStatusIsUsing.getCreateTime();
                    }
                    String timeStatusId =mesTimeStatusIsUsing.getId();
                    MesCommonConfig mesCommonConfigByTimeStatusId = mesCommonConfigDao.findMesCommonConfigByTimeStatusId(timeStatusId);
                    if (mesCommonConfigByTimeStatusId.getIsByWeek() == 1) {
                        for (String gradeId : gradeIds) {
                            List<JwClasses> jwClassesList = mesSchoolEvaluationDao.findJwClassesBySchoolIdAndGradeId(schoolId, gradeId);
                            List<MesClassHonor> mesClassHonors = setHonor(DateUtil.parse(searchBeginTime), DateUtil.parse(searchEndTime), timeStatusId, schoolId, jwClassesList, 2);
                            mesClassHonorList.addAll(mesClassHonors);
                        }
                    }
                }
                if (instance.get(Calendar.DAY_OF_MONTH) == 2) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(cal.getTime());
                    searchEndTime = DateUtil.format(calendar.getTime(), "yyyy-MM-dd") + " 23:59:59";
                    calendar.set(Calendar.DAY_OF_MONTH, 1);
                    searchBeginTime = DateUtil.format(calendar.getTime(), "yyyy-MM-dd") + " 00:00:00";
                    calendar.set(Calendar.DAY_OF_MONTH, 2);
                    calendar.add(Calendar.MONTH, 1);
                    MesTimeStatus mesTimeStatusIsUsing = findMesTimeStatusIsUsing(searchEndTime, schoolId);
                    if (DateUtil.parse(searchBeginTime, "yyyy-MM-dd HH:mm:ss").getTime()<DateUtil.parse(mesTimeStatusIsUsing.getCreateTime(),"yyyy-MM-dd HH:mm:ss").getTime()){
                        searchBeginTime=mesTimeStatusIsUsing.getCreateTime();
                    }
                    String timeStatusId = mesTimeStatusIsUsing.getId();
                    MesCommonConfig mesCommonConfigByTimeStatusId = mesCommonConfigDao.findMesCommonConfigByTimeStatusId(timeStatusId);
                    if (mesCommonConfigByTimeStatusId.getIsByMonth() == 1) {
                        for (String gradeId : gradeIds) {
                            List<JwClasses> jwClassesList = mesSchoolEvaluationDao.findJwClassesBySchoolIdAndGradeId(schoolId, gradeId);
                            List<MesClassHonor> mesClassHonors = setHonor(DateUtil.parse(searchBeginTime), DateUtil.parse(searchEndTime), timeStatusId, schoolId, jwClassesList, 3);
                            mesClassHonorList.addAll(mesClassHonors);
                        }
                    }
                }
                SchoolYear schoolYearBySchoolId = mesSchoolEvaluationDao.findSchoolYearBySchoolId(schoolId);
    /*            if (DateUtil.parse(schoolYearBySchoolId.getLastTermBegin(), "yyyy-MM-dd").getTime() == cal.getTime().getTime()) {
                    //当前版本只能查当前学年，所以这一块不做
                }*/
                if (DateUtil.parse(schoolYearBySchoolId.getNextTermBegin(), "yyyy-MM-dd").getTime() == cal.getTime().getTime()) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(cal.getTime());
                    calendar.add(Calendar.DAY_OF_MONTH, -1);
                    searchEndTime = DateUtil.format(calendar.getTime(), "yyyy-MM-dd") + " 23:59:59";
                    calendar.setTime(DateUtil.parse(schoolYearBySchoolId.getLastTermBegin(), "yyyy-MM-dd"));
                    searchBeginTime = DateUtil.format(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");
                    MesTimeStatus mesTimeStatusIsUsing = findMesTimeStatusIsUsing(searchEndTime, schoolId);
                    if (DateUtil.parse(searchBeginTime, "yyyy-MM-dd HH:mm:ss").getTime()<DateUtil.parse(mesTimeStatusIsUsing.getCreateTime(),"yyyy-MM-dd HH:mm:ss").getTime()){
                        searchBeginTime=mesTimeStatusIsUsing.getCreateTime();
                    }
                    String timeStatusId = mesTimeStatusIsUsing.getId();
                    MesCommonConfig mesCommonConfigByTimeStatusId = mesCommonConfigDao.findMesCommonConfigByTimeStatusId(timeStatusId);
                    if (mesCommonConfigByTimeStatusId.getIsBySemester() == 1) {
                        for (String gradeId : gradeIds) {
                            List<JwClasses> jwClassesList = mesSchoolEvaluationDao.findJwClassesBySchoolIdAndGradeId(schoolId, gradeId);
                            List<MesClassHonor> mesClassHonors = setHonor(DateUtil.parse(searchBeginTime), DateUtil.parse(searchEndTime), timeStatusId, schoolId, jwClassesList, 4);
                            mesClassHonorList.addAll(mesClassHonors);
                        }
                    }
                }
            }
            mongoTemplate.insertAll(mesClassHonorList);}
        ).start();
    }

    public MesTimeStatus findMesTimeStatusIsUsing(String searchEndTime, String schoolId) {
        MesTimeStatus mesTimeStatus1 = new MesTimeStatus();
        mesTimeStatus1.setSchoolId(schoolId);
        mesTimeStatus1.setEndTime(searchEndTime);
        return mesTimeStatusDao.findMesTimeStatusListWhereCreateTimeMax(mesTimeStatus1);
    }

    public List<MesClassHonor> setHonor(Date beginTime, Date endTime, String timeStatusId, String schoolId, List<JwClasses> jwClassesList, Integer type) {
        List<MesClassHonor> mesClassHonors = new ArrayList<>();
        String today = DateUtil.format(new Date(), "yyyy-MM-dd");
        MesCommonConfig mesCommonConfig = mesCommonConfigDao.findMesCommonConfigByTimeStatusId(timeStatusId);
        if (ObjectUtil.isNull(mesCommonConfig)) {
            return null;
        }
        Integer totalScore = getTotalScore(type, mesCommonConfig);
        List<MesSchoolEvaluation> mesSchoolEvaluationList = new ArrayList<>(jwClassesList.size());
        int num = 0;
        for (JwClasses c : jwClassesList) {
            List<Criteria> criteriaList = new ArrayList<>();
            criteriaList.add(where("classId").is(c.getId()));
            criteriaList.add(where("createTime").gte(DateUtil.format(beginTime,"yyyy-MM-dd HH:mm:ss")).lte(DateUtil.format(endTime,"yyyy-MM-dd HH:mm:ss")));
            criteriaList.add(where("schoolId").is(schoolId));
            Query query = query(new Criteria().andOperator(criteriaList.toArray(new Criteria[criteriaList.size()])));
            List<MesSchoolEvaluation> mesSchoolEvaluations = mongoTemplate.find(query, MesSchoolEvaluation.class);
            if (CollectionUtils.isNotEmpty(mesSchoolEvaluations)) {
                MesSchoolEvaluation mesSchoolEvaluation = new MesSchoolEvaluation();
                mesSchoolEvaluation.setClassName(c.getGradeName() + c.getNumber() + "班");
                mesSchoolEvaluation.setFormData(mesSchoolEvaluations.get(0).getFormData());
                List<Integer> formData = mesSchoolEvaluation.getFormData();
                int count = 0;
                for (MesSchoolEvaluation s : mesSchoolEvaluations) {
                    if (count == 0) {
                        count++;
                        continue;
                    }
                    for (int i = 0; i < s.getFormData().size(); i++) {
                        Integer integer = formData.get(i);
                        integer += s.getFormData().get(i);
                        formData.set(i, integer);
                    }
                }
                mesSchoolEvaluation.setSortNum(num);
                mesSchoolEvaluation.setClassId(c.getId());
                mesSchoolEvaluationList.add(mesSchoolEvaluation);
                num++;
            }
        }
        for (MesSchoolEvaluation mesSchoolEvaluation : mesSchoolEvaluationList) {
            mesSchoolEvaluation.getFormData().remove(mesSchoolEvaluation.getFormData().size() - 1);
            Integer thisScore = totalScore;
            for (Integer formDatum : mesSchoolEvaluation.getFormData()) {
                thisScore = thisScore + formDatum;
            }
            mesSchoolEvaluation.setTotalScore(thisScore);
            LinkedList<String> titleList = new LinkedList<>();
            mesSchoolEvaluation.setTitles(titleList);
        }
        if (ObjectUtil.equal(mesCommonConfig.getIsByTotal(), 1)) {
            List<MesCustomTitle> mesCustomTitles = mesCustomTitleDao.selectByTotalAndTimeStatusId(timeStatusId);
            if (ObjectUtil.equal(mesCommonConfig.getIsByRank(), 1)) {
                Collections.sort(mesSchoolEvaluationList, new Comparator<MesSchoolEvaluation>() {
                    @Override
                    public int compare(MesSchoolEvaluation o1, MesSchoolEvaluation o2) {
                        return o1.getTotalScore() - o2.getTotalScore();
                    }
                });
                Map<Integer, List<MesSchoolEvaluation>> listMap = mesSchoolEvaluationList.stream().collect(Collectors.groupingBy(MesSchoolEvaluation::getTotalScore));
                Set<Integer> set = listMap.keySet();
                TreeSet<Integer> totalScoreSet = sortScore(set);
                for (int i = 0; i < mesCustomTitles.size(); i++) {
                    int count = 0;
                    for (Integer s : totalScoreSet) {
                        if (count == i) {
                            for (MesSchoolEvaluation mesSchoolEvaluation : listMap.get(s)) {
                                mesSchoolEvaluation.getTitles().add(mesCustomTitles.get(i).getAttrValue());
                                MesClassHonor mesClassHonor = new MesClassHonor();
                                mesClassHonor.setAttrValue(mesCustomTitles.get(i).getAttrValue());
                                mesClassHonor.setClassId(mesSchoolEvaluation.getClassId());
                                mesClassHonor.setSchoolId(mesSchoolEvaluation.getSchoolId());
                                mesClassHonor.setCreateTime(today);
                                mesClassHonor.setSortNumber(i);
                                mesClassHonor.setTimeStatusId(mesSchoolEvaluation.getTimeStatusId());
                                mesClassHonor.setIsByRankOrReach(0);
                                mesClassHonor.setIsByTotalOrInstitution(0);
                                mesClassHonor.setIsByweekOrMonthOrSemester(type - 2);
                                mesClassHonors.add(mesClassHonor);
                            }
                        }
                        count++;
                    }
                }
            } else {
                for (int i = 0; i < mesCustomTitles.size(); i++) {
                    if (CollectionUtils.isNotEmpty(mesSchoolEvaluationList)) {
                        Integer percentageBegin = mesCustomTitles.get(i).getPercentageBegin();
                        Integer percentageEnd = mesCustomTitles.get(i).getPercentageEnd();
                        for (MesSchoolEvaluation mesSchoolEvaluation : mesSchoolEvaluationList) {
                            if (i < mesSchoolEvaluation.getFormData().size()) {
                                if (((100 - mesSchoolEvaluation.getTotalScore() * 100 / totalScore >= percentageBegin) && (100 - mesSchoolEvaluation.getTotalScore() * 100 / totalScore <= percentageEnd))) {
                                    mesSchoolEvaluation.getTitles().add(mesCustomTitles.get(i).getAttrValue());
                                    MesClassHonor mesClassHonor = new MesClassHonor();
                                    mesClassHonor.setAttrValue(mesCustomTitles.get(i).getAttrValue());
                                    mesClassHonor.setClassId(mesSchoolEvaluation.getClassId());
                                    mesClassHonor.setSchoolId(mesSchoolEvaluation.getSchoolId());
                                    mesClassHonor.setCreateTime(today);
                                    mesClassHonor.setSortNumber(null);
                                    mesClassHonor.setTimeStatusId(mesSchoolEvaluation.getTimeStatusId());
                                    mesClassHonor.setIsByRankOrReach(1);
                                    mesClassHonor.setIsByTotalOrInstitution(0);
                                    mesClassHonor.setIsByweekOrMonthOrSemester(type - 2);
                                    mesClassHonors.add(mesClassHonor);
                                }
                            }
                        }
                    }
                }
                if (CollectionUtil.isNotEmpty(mesCustomTitles)) {
                    for (MesSchoolEvaluation mesSchoolEvaluation : mesSchoolEvaluationList) {
                        if (mesSchoolEvaluation.getTotalScore() > totalScore) {
                            mesSchoolEvaluation.getTitles().add(mesCustomTitles.get(0).getAttrValue());
                            MesClassHonor mesClassHonor = new MesClassHonor();
                            mesClassHonor.setAttrValue(mesCustomTitles.get(0).getAttrValue());
                            mesClassHonor.setClassId(mesSchoolEvaluation.getClassId());
                            mesClassHonor.setSchoolId(mesSchoolEvaluation.getSchoolId());
                            mesClassHonor.setCreateTime(today);
                            mesClassHonor.setSortNumber(null);
                            mesClassHonor.setTimeStatusId(mesSchoolEvaluation.getTimeStatusId());
                            mesClassHonor.setIsByRankOrReach(1);
                            mesClassHonor.setIsByTotalOrInstitution(0);
                            mesClassHonor.setIsByweekOrMonthOrSemester(type - 2);
                            mesClassHonors.add(mesClassHonor);
                        }
                    }
                }
            }
        }
        if (ObjectUtil.equal(mesCommonConfig.getIsFirstInstitution(), 1)) {
            List<MesCustomTitle> mesCustomTitles = mesCustomTitleDao.selectByFirstInstitutionAndTimeStatusId(timeStatusId);
            if (ObjectUtil.equal(mesCommonConfig.getIsByRank(), 1)) {
                for (int i = 0; i < mesCustomTitles.size(); i++) {
                    int finalI = i;
                    if (CollectionUtils.isNotEmpty(mesSchoolEvaluationList)) {
                        if (mesSchoolEvaluationList.get(0).getFormData().size() > finalI) {
                            Collections.sort(mesSchoolEvaluationList, (o1, o2) -> {
                                return o1.getFormData().get(finalI) - o2.getFormData().get(finalI);
                            });
                            Map<Integer, List<MesSchoolEvaluation>> listMap = mesSchoolEvaluationList.stream().collect(Collectors.groupingBy(m -> {
                                return m.getFormData().get(finalI);
                            }));
                            Set<Integer> set = listMap.keySet();
                            TreeSet<Integer> scoreSet = sortScore(set);
                            for (MesSchoolEvaluation m : listMap.get(scoreSet.first())) {
                                m.getTitles().add(mesCustomTitles.get(i).getAttrValue());
                                MesClassHonor mesClassHonor = new MesClassHonor();
                                mesClassHonor.setAttrValue(mesCustomTitles.get(i).getAttrValue());
                                mesClassHonor.setClassId(m.getClassId());
                                mesClassHonor.setSchoolId(m.getSchoolId());
                                mesClassHonor.setCreateTime(today);
                                mesClassHonor.setSortNumber(null);
                                mesClassHonor.setTimeStatusId(m.getTimeStatusId());
                                mesClassHonor.setIsByRankOrReach(0);
                                mesClassHonor.setIsByTotalOrInstitution(1);
                                mesClassHonor.setIsByweekOrMonthOrSemester(type - 2);
                                mesClassHonors.add(mesClassHonor);
                            }
                        }
                    }
                }
            } else {
                for (int i = 0; i < mesCustomTitles.size(); i++) {
                    Integer percentageBegin = mesCustomTitles.get(i).getPercentageBegin();
                    Integer percentageEnd = mesCustomTitles.get(i).getPercentageEnd();
                    for (MesSchoolEvaluation mesSchoolEvaluation : mesSchoolEvaluationList) {
                        if (i < mesSchoolEvaluation.getFormData().size()) {
                            if (mesSchoolEvaluation.getFormData().get(i) > 0 || ((totalScore - (mesSchoolEvaluation.getFormData().get(i) + totalScore)) * 100 / totalScore >= percentageBegin && (totalScore - (mesSchoolEvaluation.getFormData().get(i) + totalScore)) * 100 / totalScore <= percentageEnd)) {
                                mesSchoolEvaluation.getTitles().add(mesCustomTitles.get(i).getAttrValue());
                                MesClassHonor mesClassHonor = new MesClassHonor();
                                mesClassHonor.setAttrValue(mesCustomTitles.get(i).getAttrValue());
                                mesClassHonor.setClassId(mesSchoolEvaluation.getClassId());
                                mesClassHonor.setSchoolId(mesSchoolEvaluation.getSchoolId());
                                mesClassHonor.setCreateTime(today);
                                mesClassHonor.setSortNumber(null);
                                mesClassHonor.setTimeStatusId(mesSchoolEvaluation.getTimeStatusId());
                                mesClassHonor.setIsByRankOrReach(1);
                                mesClassHonor.setIsByTotalOrInstitution(1);
                                mesClassHonor.setIsByweekOrMonthOrSemester(type - 2);
                                mesClassHonors.add(mesClassHonor);
                            }
                        }
                    }
                }
            }
        }
        return mesClassHonors;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<List<MesClassHonor>> findMesSchoolEvaluationHonor(MesClassHonor mesClassHonor) {
        List<Criteria> criteriaList = new ArrayList<>();
        SchoolYear schoolYear = mesSchoolEvaluationDao.findSchoolYearBySchoolId(mesClassHonor.getSchoolId());
        Date lastTermBegin = DateUtil.parse(schoolYear.getLastTermBegin());
        Date nextTermBegin = DateUtil.parse(schoolYear.getNextTermBegin());
        String beginTime;
        String endTime;
        Date now = new Date();
        if (now.getTime()>lastTermBegin.getTime()&&now.getTime()<nextTermBegin.getTime()){
            endTime=schoolYear.getNextTermBegin();
            beginTime=schoolYear.getLastTermBegin();
        }else {
            beginTime=schoolYear.getNextTermBegin();
            Calendar cal = Calendar.getInstance();
            cal.setTime(lastTermBegin);
            cal.add(Calendar.YEAR,1);
            cal.add(Calendar.DAY_OF_YEAR,-1);
            endTime=DateUtil.format(cal.getTime(),"yyyy-MM-dd");
        }
        criteriaList.add(where("classId").is(mesClassHonor.getClassId()));
        criteriaList.add(where("createTime").gte(beginTime).lte(endTime));
        Query query = query(new Criteria().andOperator(criteriaList.toArray(new Criteria[criteriaList.size()])));
        Pageable pageable = PageRequest.of(0,10000, Sort.Direction.DESC,"createTime");
        query.with(pageable);
        List<MesClassHonor> mesClassHonors = mongoTemplate.find(query, MesClassHonor.class);
        if (CollUtil.isEmpty(mesClassHonors)){
            return null;
        }
        Set<String> set = new LinkedHashSet<>();
        for (MesClassHonor classHonor : mesClassHonors) {
            set.add(classHonor.getCreateTime());
        }
        List<List<MesClassHonor>> list=new ArrayList<>();
        for (String s : set) {
            List<MesClassHonor> honors = new ArrayList<>();
            for (MesClassHonor classHonor : mesClassHonors) {
                if (s.equals(classHonor.getCreateTime())){
                    honors.add(classHonor);
                }
            }
            list.add(honors);
        }
        return list;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<MesClassHonor> findMesSchoolEvaluationHonorNewest(MesClassHonor mesClassHonor) {
        List<Criteria> criteriaList = new ArrayList<>();
        SchoolYear schoolYear = mesSchoolEvaluationDao.findSchoolYearBySchoolId(mesClassHonor.getSchoolId());
        Date lastTermBegin = DateUtil.parse(schoolYear.getLastTermBegin());
        Date nextTermBegin = DateUtil.parse(schoolYear.getNextTermBegin());
        String beginTime;
        String endTime;
        Date now = new Date();
        if (now.getTime()>lastTermBegin.getTime()&&now.getTime()<nextTermBegin.getTime()){
            endTime=schoolYear.getNextTermBegin();
            beginTime=schoolYear.getLastTermBegin();
        }else {
            beginTime=schoolYear.getNextTermBegin();
            Calendar cal = Calendar.getInstance();
            cal.setTime(lastTermBegin);
            cal.add(Calendar.YEAR,1);
            cal.add(Calendar.DAY_OF_YEAR,-1);
            endTime=DateUtil.format(cal.getTime(),"yyyy-MM-dd");
        }
        criteriaList.add(where("classId").is(mesClassHonor.getClassId()));
        criteriaList.add(where("createTime").gte(beginTime).lte(endTime));
        Query query = query(new Criteria().andOperator(criteriaList.toArray(new Criteria[criteriaList.size()])));
        Pageable pageable = PageRequest.of(0,1, Sort.Direction.DESC,"createTime");
        query.with(pageable);
        List<MesClassHonor> mesClassHonors = mongoTemplate.find(query, MesClassHonor.class);
        if (CollUtil.isEmpty(mesClassHonors)){
            return null;
        }
        List<Criteria> criteriaList1 = new ArrayList<>();
        criteriaList1.add(where("classId").is(mesClassHonor.getClassId()));
        criteriaList1.add(where("createTime").is(mesClassHonors.get(0).getCreateTime()));
        Query query1 = query(new Criteria().andOperator(criteriaList1.toArray(new Criteria[criteriaList1.size()])));
        List<MesClassHonor> list = mongoTemplate.find(query1, MesClassHonor.class);
        return list;
    }
}
