package com.yice.edu.cn.jw.service.classes;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.constants.CommonClassConstants;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import com.yice.edu.cn.common.pojo.dm.classCard.DmDeleteData;
import com.yice.edu.cn.common.pojo.dm.honourRoll.history.DmHonourRollHistory;
import com.yice.edu.cn.common.pojo.dm.honourRoll.history.DmHonourRollStudentHistory;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.classes.*;
import com.yice.edu.cn.common.pojo.jw.classes.rise.ElectronicClazzCard;
import com.yice.edu.cn.common.pojo.jw.classes.rise.RiseClazzVo;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.jw.schoolYear.SchoolYear;
import com.yice.edu.cn.common.pojo.jw.stuEvaluate.HistoryPojo;
import com.yice.edu.cn.common.pojo.jw.student.ClassesStudentNum;
import com.yice.edu.cn.common.pojo.jw.student.JwStudentGraduation;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.jw.dao.classes.IJwClaCadresDao;
import com.yice.edu.cn.jw.dao.classes.IJwClaCadresStuDao;
import com.yice.edu.cn.jw.dao.classes.IJwClassesDao;
import com.yice.edu.cn.jw.dao.riseClazz.IRiseClazzDao;
import com.yice.edu.cn.jw.dao.student.IStudentDao;
import com.yice.edu.cn.jw.feign.analysisScore.AnalysisScoreFeign;
import com.yice.edu.cn.jw.feign.dm.DmClassCardFeign;
import com.yice.edu.cn.jw.feign.homework.ClassTestFeign;
import com.yice.edu.cn.jw.feign.homework.HomeworkFeign;
import com.yice.edu.cn.jw.service.classSchedule.ClassScheduleService;
import com.yice.edu.cn.jw.service.classSchedule.ScheduleListService;
import com.yice.edu.cn.jw.service.dd.DdService;
import com.yice.edu.cn.jw.service.parent.ParentService;
import com.yice.edu.cn.jw.service.school.SchoolService;
import com.yice.edu.cn.jw.service.schoolYear.SchoolYearService;
import com.yice.edu.cn.jw.service.stuEvaluate.StuEvaluateService;
import com.yice.edu.cn.jw.service.student.JwStudentGraduationService;
import com.yice.edu.cn.jw.service.student.StudentHistoryService;
import com.yice.edu.cn.jw.service.student.StudentService;
import com.yice.edu.cn.jw.service.teacher.TeacherClassesService;
import com.yice.edu.cn.jw.service.teacher.TeacherPostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class JwClassesService {
	private static final Logger logger = LoggerFactory.getLogger(JwClassesService.class);
	private static ExecutorService executorService;
	@Autowired
	private IJwClassesDao jwClassesDao;
	@Autowired
	private IJwClaCadresDao jwClaCadresDao;
	@Autowired
	private SequenceId sequenceId;
	@Autowired
	private TeacherClassesService teacherClassesService;
	@Autowired
	private DdService bdService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private IStudentDao studentDao;
	@Autowired
	private IJwClaCadresStuDao jwClaCadresStuDao;
	@Autowired
	private JwStudentGraduationService studentGraduationService;
	@Autowired
	private ClassScheduleService classScheduleService;
	@Autowired
	private TeacherPostService teacherPostService;
	@Autowired
	private SchoolService schoolService;
	@Autowired
	private DdService ddService;
	@Autowired
	private DmClassCardFeign dmClassCardFeign;
	@Autowired
	private HomeworkFeign homeworkFeign;
	@Autowired
	private SchoolYearService schoolYearService;
	@Autowired
	private StuEvaluateService stuEvaluateService;
	@Autowired
	private AnalysisScoreFeign analysisScoreFeign;
	@Autowired
	private ClassTestFeign classTestFeign;
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private StudentHistoryService studentHistoryService;
    @Autowired
    private IRiseClazzDao riseClazzDao;
	@Autowired
	private ParentService parentService;
	@Autowired
	private DormPersonService dormPersonService;
	@Autowired
	private ScheduleListService scheduleListService;

    /**
     * ???????????????
     */
    public static ExecutorService getExecutor() {
        if (executorService == null) {
            executorService = Executors.newFixedThreadPool(20);
        }
        return executorService;
    }
    
	@Transactional(readOnly = true)
	public JwClasses findJwClassesById(String id) {
		return jwClassesDao.findJwClassesById(id);
	}

	@Transactional
	public void saveJwClasses(JwClasses jwClasses) {
		jwClasses.setId(sequenceId.nextId());
		jwClassesDao.saveJwClasses(jwClasses);
	}

	@Transactional(readOnly = true)
	public List<JwClasses> findJwClassesListByCondition(JwClasses jwClasses) {
		return jwClassesDao.findJwClassesListByCondition(jwClasses);
	}

	@Transactional(readOnly = true)
	public long findJwClassesCountByCondition(JwClasses jwClasses) {
		return jwClassesDao.findJwClassesCountByCondition(jwClasses);
	}

	@Transactional
	public void updateJwClasses(JwClasses jwClasses) {
		jwClassesDao.updateJwClasses(jwClasses);
	}

	@Transactional
	public void deleteJwClasses(String id) {
		JwClasses jwClasses = jwClassesDao.findJwClassesById(id);
		//???????????????????????????
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("classesId", id);
		studentDao.removeStudentClasses(map);

		// ????????????
		jwClassesDao.deleteJwClasses(id);
		// ???????????????
		jwClassesDao.updateJwClassesNo(jwClasses.getNumber(), jwClasses.getGradeId(), jwClasses.getSchoolId());

		//????????????????????????
		List<String> clazzIdList = new ArrayList<String>();
		clazzIdList.add(jwClasses.getId());
		removeClassRelateData(clazzIdList, jwClasses.getSchoolId());
	}

	@Transactional
	public void batchSaveClasses(CreateClassesVo createClassesVo) {
		JwClasses queryModel = new JwClasses();
		queryModel.setSchoolId(createClassesVo.getSchoolId());
		queryModel.setGradeId(createClassesVo.getGradeId());
		Integer maxNo = jwClassesDao.findMaxClassesNoByCondition(queryModel);
		if (maxNo == null) {
			maxNo = 0;
		}
		List<JwClasses> models = new ArrayList<JwClasses>();
		for (int count = maxNo + 1; count <= (createClassesVo.getClassesNum().intValue() + maxNo); count++) {
			JwClasses classesModel = new JwClasses();
			classesModel.setId(sequenceId.nextId());
			classesModel.setGradeId(createClassesVo.getGradeId());
			classesModel.setSchoolId(createClassesVo.getSchoolId());
			classesModel.setGradeName(createClassesVo.getGradeName());
			classesModel.setNumber(String.valueOf(count));
			classesModel.setRemarks(createClassesVo.getNote());
			classesModel.setEnrollYear(createClassesVo.getEnrollYear());
			classesModel.setDel(Constant.DELSIGN.NORMAL);
			models.add(classesModel);
		}

		// ??????????????????????????????
		Dd dd = new Dd();
		dd.setDel(Constant.DELSIGN.NORMAL);
		dd.setTypeId(CommonClassConstants.STUDENTDD.STU_DD_TYPE);
		List<Dd> ddList = bdService.findDdListByCondition(dd);

		// ????????????????????????
		List<JwClaCadres> JwClaCadresList = new ArrayList<JwClaCadres>();
		for (JwClasses jwClasses : models) {
			for (Dd ddModel : ddList) {
				JwClaCadres calCadres = new JwClaCadres();
				calCadres.setClassesId(jwClasses.getId());
				calCadres.setSchoolId(jwClasses.getSchoolId());
				calCadres.setType(CommonClassConstants.USERDEFINED.IS_SYSTEM);
				calCadres.setId(sequenceId.nextId());
				calCadres.setName(ddModel.getName());
				JwClaCadresList.add(calCadres);
			}
		}
		jwClassesDao.batchSaveJwClasses(models);
		jwClaCadresDao.batchSaveJwClaCadres(JwClaCadresList);

	}

	/**
	 * ?????????????????????????????????????????????
	 *
	 * @param jwClasses
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<JwClasses> findJwClassesListByConditionAndRelate(JwClasses jwClasses) {
		List<JwClasses> JwClassesList = this.findJwClassesListWithStuNum(jwClasses);
		// ???????????????
		for (JwClasses model : JwClassesList) {
			TeacherClasses teacherClasses = new TeacherClasses();
			teacherClasses.setClassesId(model.getId());
			teacherClasses.setSchoolId(model.getSchoolId());
			Teacher teacher = teacherClassesService.findHeadmasterByClasses(teacherClasses);
			if (teacher != null) {
				model.setHeadTeacher(teacher.getName());
			}
		}
		return JwClassesList;
	}

	/**
	 * ????????????????????????
	 *
	 * @param map
	 */
	@Transactional
	public void updateStuClass(Map<String, String> map) {
		String newClassId = map.get("newClassId");
		String[] ids = map.get("studentIds").split(",");
		String newGradeId = map.get("newGradeId");   //?????????????????????????????????????????????id
		String newGradeName = map.get("newGradeName");
		boolean flag = false;
		int maxSeat = 0;
		Student student1 = new Student();
		student1.setClassesId(newClassId);
		JwClasses jwClasses = jwClassesDao.findJwClassesById(newClassId); //?????????????????????,?????????????????????????????????????????????????????????  ??????????????????????????????????????????
		student1.setEnrollYear(jwClasses.getEnrollYear());
		Student student2 = studentService.findOneStudentMaxSeatNumberByCondition(student1); //???????????????????????????

		if(student2!=null&&student2.getSeatNumber()!=null){  //????????????????????????????????????0
			maxSeat = student2.getSeatNumber();
		}else{ maxSeat = 0; }
		if(newGradeId!=null&&!newGradeId.equals("")){
			flag =true;
		}
		for (String id : ids) {
			Student student = new Student();
			student.setClassesId(newClassId);
			student.setId(id);
			if(flag){ //???????????????????????????
				student.setGradeId(newGradeId);
				student.setGradeName(newGradeName);
			}
			maxSeat+=1;         //?????????????????????
			student.setSeatNumber(maxSeat);
			student.setEnrollYear(jwClasses.getEnrollYear());//????????????????????????
			student.setStatus(Constant.STUDENT_STATUS_TYPE.STUDY_HAS);  //??????????????????????????????
			studentService.updateStudent(student);
			//??????????????????????????????
			JwClaCadresStu claCadresStu  = new JwClaCadresStu();
			claCadresStu.setStudentId(id);
			jwClaCadresStuDao.deleteJwClaCadresStuByCondition(claCadresStu);
		}
	}

	@Transactional(readOnly = true)
	public List<JwClasses> findJwClassesListWithStuNum(JwClasses jwClasses) {
		List<JwClasses> JwClassesList = jwClassesDao.findJwClassesListByConditionAndRelate(jwClasses);

		if (!JwClassesList.isEmpty()) {
			// ???????????????????????????
			List<ClassesStudentNum> studentNumList = studentDao.findClassStudentNum(JwClassesList.get(0).getSchoolId(),
					JwClassesList.get(0).getGradeId());
			for (JwClasses model : JwClassesList) {
				for (ClassesStudentNum classesStudentNum : studentNumList) {
					if (model.getId().equals(classesStudentNum.getClassesId())) {
						model.setStudentCount(classesStudentNum.getStudentNum());
						continue;
					}
				}
			}
		}
		return JwClassesList;
	}

	@Transactional(readOnly = true)
	public JwClasses findOneJwClassesByCondition(JwClasses jwClasses){
		return jwClassesDao.findOneJwClassesByCondition(jwClasses);
	}
	@Transactional(readOnly = true)
	public List<JwClasses> getClassTree(String schoolId,String teacherId){
		return  jwClassesDao.getClassTree(schoolId,teacherId);
	}

	@Transactional(readOnly = true)
	public List<JwClasses> getClassesNumber(JwClasses jwClasses){
		return jwClassesDao.getClassesNumber(jwClasses);
	}
    /**
     * ??????????????????
     * @param jwClasses
     * @return
     */
	@Transactional(readOnly = true)
	public Integer findMaxClassesNoByCondition(JwClasses jwClasses) {
		return jwClassesDao.findMaxClassesNoByCondition(jwClasses);
	}
	/**
	 * ??????
	 * @param schoolId
	 */
	@Transactional
	public void riseClazz(RiseClazzVo riseClazzVo) {
		String schoolId = riseClazzVo.getSchoolId();
    	School school = schoolService.findSchoolById(schoolId);
		//??????????????????
    	CurSchoolYear curSchoolYear  = schoolYearService.findCurSchoolYear(schoolId);
    	if(school==null) {
    		logger.error("school is empty???");
    		return;
    	}
    	String typeId = school.getTypeId();
    	Dd dd = new Dd();
    	dd.setLevelType(typeId);
    	dd.setTypeId(Constant.DD_TYPE.GRADE);
    	dd.setPager(new Pager().setSortField("sort").setSortOrder("DESC"));
    	List<Dd> schoolGradeList = ddService.findDdListByCondition(dd);
    	if(schoolGradeList.isEmpty()) {
    		logger.error("schoolGradeList is empty???");
    		return;
    	}
    	
        //????????????????????????
    	saveToHistoryGeneralRise(riseClazzVo.getSchoolId(),curSchoolYear);
    	topClazzDataHandle(schoolGradeList.get(0).getId(),schoolGradeList.get(0).getName(),schoolId,curSchoolYear);

    	//????????????
		riseGeneral(schoolId,schoolGradeList);
        if(riseClazzVo.getLowestClazzList()!=null && !riseClazzVo.getLowestClazzList().isEmpty()) {
        	createLowest(riseClazzVo.getLowestClazzList());
        }
        
        //???????????????????????????
        teacherPostService.GradeLeaderPromotionCarry(schoolId,riseClazzVo.getGradeMasterList());
        
        //????????????????????????????????????
    	DmClassCard dmClassCard = new DmClassCard();
    	dmClassCard.setSchoolId(schoolId);
    	riseClazzDao.clearDmAndClazzRelateBySchoolId(dmClassCard);
        if(riseClazzVo.getElectronicClazzCardList()!=null && !riseClazzVo.getElectronicClazzCardList().isEmpty()) {
            for(ElectronicClazzCard electronicClazzCard:riseClazzVo.getElectronicClazzCardList()) {
            	DmClassCard updateDmClassCard = new DmClassCard();
            	updateDmClassCard.setClassId(electronicClazzCard.getClazzId());
            	updateDmClassCard.setId(electronicClazzCard.getDmClassCardId());
            	riseClazzDao.updateDmClazzRelateByDmId(updateDmClassCard);
            }
        }
        
        //?????????????????????????????????
        updateRiseRecord(riseClazzVo,curSchoolYear.getSchoolYearId());
        
        //??????????????????????????????
		getExecutor().execute(() -> { 
			dmClassCardFeign.deleteDmKqData(schoolId);
		});
        
	}
	
	private void updateRiseRecord(RiseClazzVo riseClazzVo,String schoolYearId) {
		
		Date nowDate = new Date();
        SchoolYear querySchoolYear = schoolYearService.findSchoolYearById(schoolYearId);
        
        //???????????????
        SchoolYear addSchoolYear = new SchoolYear();
        addSchoolYear.setFromYear(querySchoolYear.getFromYear()+1);
        addSchoolYear.setToYear(querySchoolYear.getToYear()+1);
        addSchoolYear.setFromTo(addSchoolYear.getFromYear()+"-"+addSchoolYear.getToYear());
        addSchoolYear.setCreateId(riseClazzVo.getCreatorId());
        addSchoolYear.setCreateName(riseClazzVo.getCreatorName());
        addSchoolYear.setLastTermBegin(Integer.parseInt(querySchoolYear.getLastTermBegin().substring(0, 4))+1+"-"+DateUtil.format(nowDate, "MM-dd"));
        addSchoolYear.setNextTermBegin(Integer.parseInt(querySchoolYear.getNextTermBegin().substring(0, 4))+1+querySchoolYear.getNextTermBegin().substring(4));
        addSchoolYear.setSchoolId(querySchoolYear.getSchoolId());
        schoolYearService.saveSchoolYear(addSchoolYear);
        
        //????????????
        SchoolYear updateSchoolYear = new SchoolYear();
        updateSchoolYear.setId(querySchoolYear.getId());
        updateSchoolYear.setNextTermEnd(addSchoolYear.getLastTermBegin());
        schoolYearService.updateSchoolYear(updateSchoolYear);
        
        //??????????????????????????????
        schoolYearService.clearCurSchoolYear(querySchoolYear.getSchoolId());
        
	}
	
	/**
	 * ????????????
	 */
	private void saveToHistoryGeneralRise(String schoolId,CurSchoolYear curSchoolYear) {
		//??????????????????????????????
		studentHistoryService.archiveStudentHistory(schoolId,curSchoolYear);
	}
	
	private void topClazzDataHandle(String topGradeId,String topGradeName,String schoolId,CurSchoolYear curSchoolYear) {
		JwClasses queryTopClazz = new JwClasses();
		queryTopClazz.setSchoolId(schoolId);
		queryTopClazz.setGradeId(topGradeId);
		List<JwClasses> clazzList = jwClassesDao.findJwClassesListByCondition(queryTopClazz);
		if(clazzList.isEmpty()) {
			return;
		}
		List<String> clazzIdList = clazzList.stream().map(clazz->{
			return clazz.getId();
		}).collect(Collectors.toList());
		
		handleTopRiseData(schoolId,topGradeId, topGradeName, clazzIdList, curSchoolYear,clazzList);
		
	}
	
	/**
	 *?????????????????????
	 * @param gradeId
	 * @param gradeName
	 * @param clazzIdList ???????????????id??????
	 * @param semester
	 */
	private void handleTopRiseData(String schoolId,String gradeId,String gradeName,List<String> clazzIdList,CurSchoolYear curSchoolYear,List<JwClasses> clazzList) {
	    //?????????????????????
		Student student  = new Student();
		student.setSchoolId(schoolId);
		student.setClazzIdList(clazzIdList);
	    dormPersonService.LeaveDormByClassesIds(student);
		//????????????????????????????????????
		parentService.deleteParentStudentByShiftpromotion(clazzIdList);
		//???????????????????????????
		updateGradeStu(schoolId,gradeName,clazzIdList);
		//??????????????????
		clazzList.forEach(clazz->{
				JwClassesHistory classesHistory = new JwClassesHistory();
				BeanUtils.copyProperties(clazz, classesHistory);
				classesHistory.setTerm(curSchoolYear.getTerm());
				classesHistory.setSchoolYearId(curSchoolYear.getSchoolYearId());
				classesHistory.setFromTo(curSchoolYear.getFromTo());
				mot.save(classesHistory);
			});
		//??????????????????
		jwClassesDao.deleteJTopClazz(clazzIdList);
		
		//??????????????????
	    getExecutor().execute(() -> {
		    //???????????????????????????
		    saveDmHonour(clazzIdList);
			//??????????????????
			HistoryPojo historyPojo = new HistoryPojo();
			historyPojo.setClassIdList(clazzIdList);
			stuEvaluateService.moveStuEvaluateToHistory(historyPojo);
			//?????????????????????????????? ?????????????????????
			//compareQualityService.moveCompareQualityDataToHistory(clazzIdList);
			//????????????
			analysisScoreFeign.saveTopClazzScoreAnalyse(clazzIdList);
		});

		//???????????????????????????????????????
		getExecutor().execute(() -> {
		  removeClassRelateData(clazzIdList, schoolId);
		});
		
	}
	
	/**
	 * ??????????????????????????????
	 * @param clazzIdList
	 * @param schoolId
	 */
	private void removeClassRelateData(List<String> clazzIdList,String schoolId) {
		
		//???????????????????????????
		clazzIdList.forEach(ss-> {
			TeacherClasses teacherClasses = new TeacherClasses();
			teacherClasses.setClassesId(ss);
			teacherClasses.setSchoolId(schoolId);
			teacherClassesService.delTeacherClassesByClass(teacherClasses);
		});
		
		//?????????????????????
		jwClaCadresStuDao.deleteClaCadresStuByClazzIdList(clazzIdList);
		jwClaCadresDao.deleteClaCadresByClazzIdList(clazzIdList);
		
	    //????????????????????????
		scheduleListService.pastDueScheduleList(schoolId);
		
		//??????????????????????????????
		dmClassCardFeign.deleteDmData(clazzIdList);
		
        //????????????????????????????????????
		classTestFeign.deleteClassTest(clazzIdList);
		
		//????????????????????????????????????
		homeworkFeign.deleteHomeworkByClasses(clazzIdList);
	}
	
	private void riseGeneral(String schoolId,List<Dd> gradeDdList) {
		JwClasses jwClasses = new JwClasses();
		jwClasses.setSchoolId(schoolId);
		jwClassesDao.riseGenerateClazz(jwClasses);
		//??????grade_id??????grade_name
		gradeDdList.forEach(dd ->{
			JwClasses updateClazz = new JwClasses();
			updateClazz.setGradeId(dd.getId());
			updateClazz.setSchoolId(schoolId);
			updateClazz.setGradeName(dd.getName());
			jwClassesDao.updateByGradeIdAndSchoolId(updateClazz);
		});
	}
	
	/**
	 * ???????????????????????????
	 * @param clazzList
	 */
	private void createLowest(List<JwClasses> clazzList) {
		jwClassesDao.batchSaveJwClasses(clazzList);
		//??????????????????????????????
		Dd dd = new Dd();
		dd.setTypeId(CommonClassConstants.STUDENTDD.STU_DD_TYPE);
		List<Dd> ddList = bdService.findDdListByCondition(dd);
		
		List<JwClaCadres> JwClaCadresList = new ArrayList<JwClaCadres>();
		for (JwClasses jwClasses : clazzList) {
			for (Dd ddModel : ddList) {
				JwClaCadres calCadres = new JwClaCadres();
				calCadres.setClassesId(jwClasses.getId());
				calCadres.setSchoolId(jwClasses.getSchoolId());
				calCadres.setType(CommonClassConstants.USERDEFINED.IS_SYSTEM);
				calCadres.setId(sequenceId.nextId());
				calCadres.setName(ddModel.getName());
				JwClaCadresList.add(calCadres);
			}
		}
		jwClaCadresDao.batchSaveJwClaCadres(JwClaCadresList);
	}
	
	/**
	 * ??????????????????????????????
	 */
	private void updateGradeStu(String schoolId,String gradeName,List<String> clazzIdList) {
		Student student = new Student();
		student.setClazzIdList(clazzIdList);
		student.setSchoolId(schoolId);
		List<Student> studentList = studentDao.findSchoolStudentByClazzIdList(student);
		List<JwStudentGraduation> jwStudentGraduationList = new ArrayList<JwStudentGraduation>();
		Date nowDate = new Date();
		for(Student stuModel:studentList) {
			JwStudentGraduation studentGraduation = new JwStudentGraduation();
			BeanUtils.copyProperties(stuModel, studentGraduation);
			studentGraduation.setId(sequenceId.nextId());
			studentGraduation.setStudentId(stuModel.getId());
			studentGraduation.setGraduationTime(DateUtil.format(nowDate, "yyyy"));
			studentGraduation.setClassesName(gradeName+"("+stuModel.getClassesNumber()+")???");
			studentGraduation.setGraduationGrade(gradeName);
			studentGraduation.setGraduationClass(stuModel.getClassesNumber());
			studentGraduation.setCreateTime(DateUtil.formatDateTime(nowDate));
			studentGraduation.setUpdateTime(null);
			jwStudentGraduationList.add(studentGraduation);
		}
		if(!jwStudentGraduationList.isEmpty()) {
			studentGraduationService.batchSaveJwStudentGraduation(jwStudentGraduationList);
			studentDao.deleteStudentByClazzIdList(clazzIdList);
		}
	}
	
	/**
	 * ???????????????????????????
	 */
	private void saveDmHonour(List<String> clazzIdList) {
		DmDeleteData dmDeleteData  = new DmDeleteData();
		dmDeleteData.setClassIdList(clazzIdList);
		
		List<DmHonourRollHistory> honourRollList =  riseClazzDao.selectHonourRollByClazzIdList(dmDeleteData);
		List<DmHonourRollStudentHistory> honourRollStudentList =  riseClazzDao.selectHonourRollStudentByClazzIdList(dmDeleteData);

		honourRollList.forEach(s->{mot.save(s);});
		honourRollStudentList.forEach(s->{mot.save(s);});
//		mot.insertAll(honourRollList);
//		mot.insertAll(honourRollStudentList);
		
		riseClazzDao.deleteDmHonourRollByClassId(dmDeleteData);
		riseClazzDao.deleteDmHonourRollStudentByClassId(dmDeleteData);
	}

	public List<JwClasses> findJwClassessByConditionForExam(JwClasses jwClasses) {
		return jwClassesDao.findJwClassessByConditionForExam(jwClasses);
	}

	public List<JwClasses> findTeacherClassesByTeacherId(String teacherId,String activityId) {
		return jwClassesDao.findTeacherClassesByTeacherId(teacherId,activityId);
	}

	@Transactional(readOnly = true)
	public List<JwClasses> findClassListByJwClassesKong(JwClasses jwClasses){
		return jwClassesDao.findClassListByJwClassesKong(jwClasses);
	}
}
