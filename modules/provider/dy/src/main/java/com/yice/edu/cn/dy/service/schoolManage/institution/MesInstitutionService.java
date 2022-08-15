package com.yice.edu.cn.dy.service.schoolManage.institution;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.schoolWeek.SchoolWeek;
import com.yice.edu.cn.common.pojo.jw.schoolYear.SchoolYear;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.mes.schoolManage.schoolEvaluation.MesInstitutionStudent;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesCommonConfig;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesCustomTitle;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesInstitution;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.dy.dao.schoolManage.inspectRecord.IMesInspectRecordDao;
import com.yice.edu.cn.dy.dao.schoolManage.institution.IMesCommonConfigDao;
import com.yice.edu.cn.dy.dao.schoolManage.institution.IMesCustomTitleDao;
import com.yice.edu.cn.dy.dao.schoolManage.institution.IMesInstitutionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MesInstitutionService {
    @Autowired
    private IMesInstitutionDao mesInstitutionDao;
    @Autowired
    private MesSchoolEvaluationService mesSchoolEvaluationService;
    @Autowired
    private IMesCommonConfigDao mesCommonConfigDao;
    @Autowired
    private IMesCustomTitleDao mesCustomTitleDao;
    @Autowired
    private IMesInspectRecordDao mesInspectRecordDao;
    @Autowired
    private SequenceId sequenceId;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public MesInstitution findMesInstitutionById(String id) {
        return mesInstitutionDao.findMesInstitutionById(id);
    }

    @Transactional
    public MesInstitution saveMesInstitution(MesInstitution mesInstitution) {
        mesInstitution.setId(sequenceId.nextId());
        mesInstitutionDao.saveMesInstitution(mesInstitution);
        return mesInstitution;
    }

    @Transactional
    public List<MesInstitution> findMesInstitutionEditing(MesInstitution mesInstitution) {
        List<MesInstitution> mesInstitutionListByCondition = mesInstitutionDao.findMesInstitutionsByCondition(mesInstitution);
        if (CollUtil.isEmpty(mesInstitutionListByCondition)){
            return new ArrayList<MesInstitution>();
        }
        List<MesInstitution> mesInstitutions = ObjectKit.buildTree(mesInstitutionListByCondition, "-1");
        String dateStr = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        for (MesInstitution institution : mesInstitutions) {
            String id = sequenceId.nextId();
            institution.setId(id);
            institution.setCreateTime(dateStr);
            institution.setTimeStatusId("-1");
            List<MesInstitution> children = institution.getChildren();
            if (CollUtil.isNotEmpty(children)){
                for (MesInstitution child : children) {
                    child.setId(sequenceId.nextId());
                    child.setParentId(id);
                    child.setTimeStatusId("-1");
                    child.setCreateTime(dateStr);
                }
            }
        }
        mesInstitutionDao.batchSaveMesInstitution(mesInstitutionListByCondition);
        return mesInstitutions;
    }
    public List<MesInstitution> findMesInstitutionsByCondition(MesInstitution mesInstitution) {
        List<MesInstitution> mesInstitutionListByCondition = mesInstitutionDao.findMesInstitutionsByCondition(mesInstitution);
        List<MesInstitution> mesInstitutions = ObjectKit.buildTree(mesInstitutionListByCondition, "-1");
        return mesInstitutions;
    }

    @Transactional(readOnly = true)
    public List<MesInstitution> findMesInstitutionListByCondition(MesInstitution mesInstitution) {
        List<MesInstitution> mesInstitutionListByCondition = mesInstitutionDao.findMesInstitutionListByCondition(mesInstitution);
        return ObjectKit.buildTree(mesInstitutionListByCondition, "-1");
    }

    @Transactional(readOnly = true)
    public MesInstitution findOneMesInstitutionByCondition(MesInstitution mesInstitution) {
        return mesInstitutionDao.findOneMesInstitutionByCondition(mesInstitution);
    }

    @Transactional(readOnly = true)
    public long findMesInstitutionCountByCondition(MesInstitution mesInstitution) {
        return mesInstitutionDao.findMesInstitutionCountByCondition(mesInstitution);
    }

    @Transactional
    public void updateMesInstitution(MesInstitution mesInstitution) {
        mesInstitutionDao.updateMesInstitution(mesInstitution);
    }

    @Transactional
    public void deleteMesInstitution(String id) {
        mesInstitutionDao.deleteMesInstitution(id);
    }

    @Transactional
    public void deleteMesInstitutionWhereTimeStatusIdIsNull(String id) {
        mesInstitutionDao.deleteMesInstitutionWhereTimeStatusIdIsNull(id);
    }

    @Transactional
    public void deleteMesInstitutionByCondition(MesInstitution mesInstitution) {
        mesInstitutionDao.deleteMesInstitutionByCondition(mesInstitution);
    }

    @Transactional
    public Map<String, List<MesCustomTitle>> batchSaveMesInstitution(List<MesInstitution> mesInstitutions) {
        if (CollUtil.isEmpty(mesInstitutions)) {
            return null;
        }
        mesInstitutionDao.deleteMesInstitutionWhereTimeStatusIdIsNull(mesInstitutions.get(0).getSchoolId());
        List<MesInstitution> saveMesInstitutions=new ArrayList<>();
        String dateStr = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        boolean flag=false;
        outer:
        for (int i = 0; i < mesInstitutions.size(); i++) {
            String name = mesInstitutions.get(i).getName();
            int parentCount=0;
            for (MesInstitution mesInstitution : mesInstitutions) {
                if (mesInstitution.getName().equals(name)){
                    parentCount++;
                }
                if (parentCount>=2){
                    flag=true;
                    break outer;
                }
            }
            String parentId = sequenceId.nextId();
            mesInstitutions.get(i).setId(parentId);
            mesInstitutions.get(i).setSortNumber(i);
            mesInstitutions.get(i).setTimeStatusId("-1");
            mesInstitutions.get(i).setSchoolRange(0);
            mesInstitutions.get(i).setCreateTime(dateStr);
            List<MesInstitution> children = mesInstitutions.get(i).getChildren();
            saveMesInstitutions.add(mesInstitutions.get(i));
            if (CollUtil.isEmpty(children)) {
                continue;
            }
            for (int j = 0; j < children.size(); j++) {
                int childrenCount=0;
                String childName = children.get(j).getName();
                for (MesInstitution child : children) {
                    if (childName.equals(child.getName())){
                        childrenCount++;
                    }
                    if (childrenCount>=2){
                        flag=true;
                        break outer;
                    }
                }
                children.get(j).setId(sequenceId.nextId());
                children.get(j).setParentId(parentId);
                children.get(j).setCreateTime(dateStr);
                children.get(j).setSchoolRange(0);
                children.get(j).setTimeStatusId("-1");
                children.get(j).setSortNumber(j);
                saveMesInstitutions.add(children.get(j));
            }
        }
        if (flag){
            return null;
        }
        mesInstitutionDao.batchSaveMesInstitution(saveMesInstitutions);
        MesInstitution mesInstitution = new MesInstitution();
        mesInstitution.setSchoolId(mesInstitutions.get(0).getSchoolId());
        List<MesInstitution> mesInstitutionListByCondition = mesInstitutionDao.findMesInstitutionListByCondition(mesInstitution);
        List<MesInstitution> mesInstitutions1 = ObjectKit.buildTree(mesInstitutionListByCondition, "-1");
        Map<String, List<MesCustomTitle>> map = new HashMap<>();
        List<MesCustomTitle> mesCustomTitleFirstRank = new ArrayList<>();
        List<MesCustomTitle> mesCustomTitleFirstReach = new ArrayList<>();
        List<MesCustomTitle> mesCustomTitleTotalReach = new ArrayList<>();
        List<MesCustomTitle> mesCustomTitleTotalRank = new ArrayList<>();
        map.put("mesCustomTitleFirstRank", mesCustomTitleFirstRank);
        map.put("mesCustomTitleFirstReach", mesCustomTitleFirstReach);
        map.put("mesCustomTitleTotalReach", mesCustomTitleTotalReach);
        map.put("mesCustomTitleTotalRank", mesCustomTitleTotalRank);
        if (CollUtil.isEmpty(mesInstitutions1)) {
            return map;
        }
        for (int i = 0; i < mesInstitutions1.size(); i++) {
            MesCustomTitle mesCustomTitleRank = new MesCustomTitle();
            mesCustomTitleRank.setRefrenceId(mesInstitutions1.get(i).getId());
            mesCustomTitleRank.setAttrKey(mesInstitutions1.get(i).getName());
            mesCustomTitleRank.setSchoolId(mesInstitutions1.get(i).getSchoolId());
            mesCustomTitleRank.setChildrenType("rank");
            mesCustomTitleRank.setSortNumber(i);
            mesCustomTitleRank.setAttrType("first_institution");
            mesCustomTitleFirstRank.add(mesCustomTitleRank);
            MesCustomTitle mesCustomTitleReach = new MesCustomTitle();
            mesCustomTitleReach.setRefrenceId(mesInstitutions1.get(i).getId());
            mesCustomTitleReach.setAttrKey(mesInstitutions1.get(i).getName());
            mesCustomTitleReach.setSchoolId(mesInstitutions1.get(i).getSchoolId());
            mesCustomTitleReach.setChildrenType("reach");
            mesCustomTitleReach.setAttrType("first_institution");
            mesCustomTitleReach.setSortNumber(i);
            mesCustomTitleFirstReach.add(mesCustomTitleReach);
        }
        for (int i = 0; i < 3; i++) {
            MesCustomTitle mesCustomTitleRank = new MesCustomTitle();
            MesCustomTitle mesCustomTitleReach = new MesCustomTitle();
            if (i == 0) {
                mesCustomTitleRank.setAttrKey("第一名");
                mesCustomTitleReach.setAttrKey("第一名");
            } else if (i == 1) {
                mesCustomTitleRank.setAttrKey("第二名");
                mesCustomTitleReach.setAttrKey("第二名");
            } else {
                mesCustomTitleRank.setAttrKey("第三名");
                mesCustomTitleReach.setAttrKey("第三名");
            }
            mesCustomTitleRank.setSortNumber(i);
            mesCustomTitleReach.setSortNumber(i);
            mesCustomTitleRank.setSchoolId(mesInstitutions.get(0).getSchoolId());
            mesCustomTitleReach.setSchoolId(mesInstitutions.get(0).getSchoolId());
            mesCustomTitleRank.setChildrenType("rank");
            mesCustomTitleReach.setChildrenType("reach");
            mesCustomTitleRank.setAttrType("total");
            mesCustomTitleReach.setAttrType("total");
            mesCustomTitleTotalRank.add(mesCustomTitleRank);
            mesCustomTitleTotalReach.add(mesCustomTitleReach);
        }
        return map;
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @Transactional(readOnly = true)
    public List<MesInstitutionStudent> findAllJwClassesAndStudents(String schoolId) {
        List<MesInstitutionStudent> list = mesInstitutionDao.findAllJwClassesAndStudents(schoolId);
        if (CollUtil.isNotEmpty(list)) {
          return   list.parallelStream().map(x->{
                x.setType("0");
                List<Student> studentsByClassId =mesInstitutionDao.findStudentsByClassId(x.getId());
                x.setStudentList(studentsByClassId);
                return x;
            }).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Transactional(readOnly = true)
    public List<MesInstitutionStudent> findAllJwClassesBySchoolId(String schoolId) {
        return mesInstitutionDao.findAllJwClassesBySchoolId(schoolId);
    }

    @Transactional
    public void saveSchoolWeek(List<SchoolWeek> schoolWeeks) {
        if (CollUtil.isEmpty(schoolWeeks)) {
            return;
        }
        mesInstitutionDao.deleteSchoolWeekBySchoolYearId(schoolWeeks.get(0).getSchoolYearId());
        for (SchoolWeek schoolWeek : schoolWeeks) {
            schoolWeek.setId(sequenceId.nextId());
            mesInstitutionDao.saveSchoolWeek(schoolWeek);
        }
    }

    @Transactional(readOnly = true)
    public List<SchoolWeek> findSchoolYearsBySchoolId(String schoolId) {
        SchoolYear currentSchoolYear = mesCustomTitleDao.findCurrentSchoolYear(schoolId);
        List<SchoolWeek> currentSchoolWeek = mesSchoolEvaluationService.findSchoolWeekBySchoolYearId(currentSchoolYear.getId());
        if (CollUtil.isEmpty(currentSchoolWeek)) {
            SchoolWeek schoolWeek = new SchoolWeek();
            schoolWeek.setSchoolYear(currentSchoolYear.getFromTo());
            schoolWeek.setStartTime(currentSchoolYear.getLastTermBegin());
            schoolWeek.setSchoolYearId(currentSchoolYear.getId());
            Calendar cal = Calendar.getInstance();
            cal.setTime(DateUtil.parse(currentSchoolYear.getNextTermBegin(), "yyyy-MM-dd"));
            cal.add(Calendar.DAY_OF_WEEK, -1);
            schoolWeek.setEndTime(currentSchoolYear.getNextTermBegin());
            schoolWeek.setTermType(0);
            currentSchoolWeek.add(schoolWeek);
            SchoolWeek schoolWeek1 = new SchoolWeek();
            schoolWeek1.setSchoolYear(currentSchoolYear.getFromTo());
            schoolWeek1.setSchoolYearId(currentSchoolYear.getId());
            schoolWeek1.setStartTime(currentSchoolYear.getNextTermBegin());
            cal.setTime(DateUtil.parse(currentSchoolYear.getLastTermBegin(), "yyyy-MM-dd"));
            cal.add(Calendar.YEAR, 1);
            cal.add(Calendar.DAY_OF_YEAR, -1);
            schoolWeek1.setEndTime(DateUtil.format(cal.getTime(), "yyyy-MM-dd"));
            schoolWeek1.setTermType(1);
            currentSchoolWeek.add(schoolWeek1);
        }
        for (SchoolWeek schoolWeek : currentSchoolWeek) {
            schoolWeek.setSchoolYear(currentSchoolYear.getFromTo());
            if (schoolWeek.getTermType() == 0) {
                if (DateUtil.parse(currentSchoolYear.getNextTermBegin(), "yyyy-MM-dd").getTime() < new Date().getTime()) {
                    schoolWeek.setCanUpdate(false);
                } else {
                    schoolWeek.setCanUpdate(true);
                }
            } else {
                Calendar cal = Calendar.getInstance();
                cal.setTime(DateUtil.parse(currentSchoolYear.getLastTermBegin(), "yyyy-MM-dd"));
                cal.add(Calendar.YEAR, 1);
                if (cal.getTime().getTime() < new Date().getTime()) {
                    schoolWeek.setCanUpdate(false);
                } else {
                    schoolWeek.setCanUpdate(true);
                }
            }
        }
        return currentSchoolWeek;
    }

    @Transactional(readOnly = true)
    public List<SchoolWeek> findSchoolYearListBySchoolId(String schoolId) {
        SchoolYear currentSchoolYear = mesCustomTitleDao.findCurrentSchoolYear(schoolId);
        List<SchoolWeek> currentSchoolWeek = mesSchoolEvaluationService.findSchoolWeekBySchoolYearId(currentSchoolYear.getId());
        if (CollUtil.isEmpty(currentSchoolWeek)) {
            SchoolWeek schoolWeek = new SchoolWeek();
            schoolWeek.setSchoolYear(currentSchoolYear.getFromTo());
            schoolWeek.setSchoolYearId(currentSchoolYear.getId());
            schoolWeek.setTermType(0);
            currentSchoolWeek.add(schoolWeek);
            SchoolWeek schoolWeek1 = new SchoolWeek();
            schoolWeek1.setSchoolYear(currentSchoolYear.getFromTo());
            schoolWeek1.setSchoolYearId(currentSchoolYear.getId());
            schoolWeek1.setTermType(1);
            currentSchoolWeek.add(schoolWeek1);
        }else {
            for (SchoolWeek schoolWeek : currentSchoolWeek) {
                schoolWeek.setSchoolYear(currentSchoolYear.getFromTo());
            }
        }
        return currentSchoolWeek;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> findInstitutionScore(MesCommonConfig mesInstitutionStudent) {
        HashMap<String, Object> map = new HashMap<>();
        String schoolId = mesInstitutionStudent.getSchoolId();
        MesCommonConfig commonConfig = new MesCommonConfig();
        commonConfig.setSchoolId(schoolId);
        List<MesCommonConfig> mesCommonConfigListByCondition = mesCommonConfigDao.findMesCommonConfigListByCondition(commonConfig);
        if (CollUtil.isEmpty(mesCommonConfigListByCondition)) {
            return null;
        }
        MesCommonConfig mesCommonConfig = mesCommonConfigListByCondition.get(0);
        Integer countValue;
        Calendar cal = Calendar.getInstance();
        String beginTime;
        String endTime = DateUtil.format(cal.getTime(), "yyyy-MM-dd") + " 23:59:59";
        if (mesInstitutionStudent.getCountType() == 1) {
            //按周
            countValue = Integer.parseInt(mesCommonConfig.getWeekValue());
            int week = cal.get(Calendar.DAY_OF_WEEK);
            //时间设置为周一0点
            if (week == 1) {
                cal.add(Calendar.DAY_OF_WEEK, -6);
                beginTime = DateUtil.format(cal.getTime(), "yyyy-MM-dd") + " 00:00:00";
            } else if (week == 2) {
                beginTime = DateUtil.format(cal.getTime(), "yyyy-MM-dd") + " 00:00:00";
            } else {
                cal.add(Calendar.DAY_OF_WEEK, 2 - cal.get(Calendar.DAY_OF_WEEK));
                beginTime = DateUtil.format(cal.getTime(), "yyyy-MM-dd") + " 00:00:00";
            }
        } else if (mesInstitutionStudent.getCountType() == 2) {
            //按月
            countValue = Integer.parseInt(mesCommonConfig.getMonthValue());
            beginTime = DateUtil.format(cal.getTime(), "yyyy-MM") + "-01 00:00:00";
        } else {
            //按学期
            countValue = Integer.parseInt(mesCommonConfig.getSemesterValue());
            SchoolYear schoolYear = mesCustomTitleDao.findCurrentSchoolYear(schoolId);
            DateTime lastTermBegin = DateUtil.parse(schoolYear.getLastTermBegin(), "yyyy-MM-dd");
            DateTime nextTermBegin = DateUtil.parse(schoolYear.getNextTermBegin(), "yyyy-MM-dd");
            if (cal.getTime().getTime() > lastTermBegin.getTime() && cal.getTime().getTime() < nextTermBegin.getTime()) {
                beginTime = schoolYear.getLastTermBegin() + " 00:00:00";
            } else {
                beginTime = schoolYear.getNextTermBegin() + " 00:00:00";
            }
        }

        if (mesCommonConfig.getIsByReach() == 1) {
            if ("0".equals(mesInstitutionStudent.getReachType())) {
                List<MesCustomTitle> mesCustomTitleListByFirstReach = mesCustomTitleDao.findMesCustomTitleListByTotalReach(schoolId);
                map.put("reach", mesCustomTitleListByFirstReach);
            } else if ("1".equals(mesInstitutionStudent.getReachType())) {
                List<MesCustomTitle> mesCustomTitleListByFirstReach = mesCustomTitleDao.findMesCustomTitleListByFirstReach(schoolId);
                map.put("reach", mesCustomTitleListByFirstReach.stream().filter(m ->
                        mesInstitutionStudent.getInstitutionId().equals(m.getRefrenceId())
                ).collect(Collectors.toList()));
            }
        }
        List<MesInstitutionStudent> list = mesInstitutionDao.findAllJwClassesByGradeId(mesInstitutionStudent);
        if (CollUtil.isNotEmpty(list)) {
            Integer score=0;
            for (MesInstitutionStudent institutionStudent : list) {
                institutionStudent.setBeginTime(beginTime);
                institutionStudent.setEndTime(endTime);
                if ("1".equals(mesInstitutionStudent.getReachType())){
                    institutionStudent.setInstitutionId(mesInstitutionStudent.getInstitutionId());
                    score = mesInspectRecordDao.findMesInstitutionScore(institutionStudent);
                }else if ("0".equals(mesInstitutionStudent.getReachType())){
                    institutionStudent.setSchoolId(mesInstitutionStudent.getSchoolId());
                    score = mesInspectRecordDao.findMesInstitution(institutionStudent);
                }
                institutionStudent.setScore(countValue + (Objects.isNull(score)?0:score));
            }
            map.put("totalScore", countValue);
            map.put("deductionScore", list);
        }
        return map;
    }

}
