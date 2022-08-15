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
     * 定义线程池
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
		//解除学生挂靠的班级
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("classesId", id);
		studentDao.removeStudentClasses(map);

		// 删除班级
		jwClassesDao.deleteJwClasses(id);
		// 修改班级号
		jwClassesDao.updateJwClassesNo(jwClasses.getNumber(), jwClasses.getGradeId(), jwClasses.getSchoolId());

		//删除班级相关数据
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

		// 获取班级职务数据字典
		Dd dd = new Dd();
		dd.setDel(Constant.DELSIGN.NORMAL);
		dd.setTypeId(CommonClassConstants.STUDENTDD.STU_DD_TYPE);
		List<Dd> ddList = bdService.findDdListByCondition(dd);

		// 批量插入班级职务
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
	 * 查询班级信息并且返回相关联数据
	 *
	 * @param jwClasses
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<JwClasses> findJwClassesListByConditionAndRelate(JwClasses jwClasses) {
		List<JwClasses> JwClassesList = this.findJwClassesListWithStuNum(jwClasses);
		// 查询班主任
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
	 * 修改学生所属班级
	 *
	 * @param map
	 */
	@Transactional
	public void updateStuClass(Map<String, String> map) {
		String newClassId = map.get("newClassId");
		String[] ids = map.get("studentIds").split(",");
		String newGradeId = map.get("newGradeId");   //学生管理分配班级传的参数，年级id
		String newGradeName = map.get("newGradeName");
		boolean flag = false;
		int maxSeat = 0;
		Student student1 = new Student();
		student1.setClassesId(newClassId);
		JwClasses jwClasses = jwClassesDao.findJwClassesById(newClassId); //查找分配的班级,替换对应的班级应届年份加入应届年份条件  防止数据错乱导致座位号不一致
		student1.setEnrollYear(jwClasses.getEnrollYear());
		Student student2 = studentService.findOneStudentMaxSeatNumberByCondition(student1); //查询班级最大座位号

		if(student2!=null&&student2.getSeatNumber()!=null){  //判断有就赋值没有就初始值0
			maxSeat = student2.getSeatNumber();
		}else{ maxSeat = 0; }
		if(newGradeId!=null&&!newGradeId.equals("")){
			flag =true;
		}
		for (String id : ids) {
			Student student = new Student();
			student.setClassesId(newClassId);
			student.setId(id);
			if(flag){ //判断是否要改变年级
				student.setGradeId(newGradeId);
				student.setGradeName(newGradeName);
			}
			maxSeat+=1;         //座位号依次叠加
			student.setSeatNumber(maxSeat);
			student.setEnrollYear(jwClasses.getEnrollYear());//替换班级应届年份
			student.setStatus(Constant.STUDENT_STATUS_TYPE.STUDY_HAS);  //分配班级默认在读状态
			studentService.updateStudent(student);
			//删除该学生的班级职务
			JwClaCadresStu claCadresStu  = new JwClaCadresStu();
			claCadresStu.setStudentId(id);
			jwClaCadresStuDao.deleteJwClaCadresStuByCondition(claCadresStu);
		}
	}

	@Transactional(readOnly = true)
	public List<JwClasses> findJwClassesListWithStuNum(JwClasses jwClasses) {
		List<JwClasses> JwClassesList = jwClassesDao.findJwClassesListByConditionAndRelate(jwClasses);

		if (!JwClassesList.isEmpty()) {
			// 查询班级的学生数量
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
     * 查询最大班号
     * @param jwClasses
     * @return
     */
	@Transactional(readOnly = true)
	public Integer findMaxClassesNoByCondition(JwClasses jwClasses) {
		return jwClassesDao.findMaxClassesNoByCondition(jwClasses);
	}
	/**
	 * 升学
	 * @param schoolId
	 */
	@Transactional
	public void riseClazz(RiseClazzVo riseClazzVo) {
		String schoolId = riseClazzVo.getSchoolId();
    	School school = schoolService.findSchoolById(schoolId);
		//查询当前学期
    	CurSchoolYear curSchoolYear  = schoolYearService.findCurSchoolYear(schoolId);
    	if(school==null) {
    		logger.error("school is empty！");
    		return;
    	}
    	String typeId = school.getTypeId();
    	Dd dd = new Dd();
    	dd.setLevelType(typeId);
    	dd.setTypeId(Constant.DD_TYPE.GRADE);
    	dd.setPager(new Pager().setSortField("sort").setSortOrder("DESC"));
    	List<Dd> schoolGradeList = ddService.findDdListByCondition(dd);
    	if(schoolGradeList.isEmpty()) {
    		logger.error("schoolGradeList is empty！");
    		return;
    	}
    	
        //升班前的数据处理
    	saveToHistoryGeneralRise(riseClazzVo.getSchoolId(),curSchoolYear);
    	topClazzDataHandle(schoolGradeList.get(0).getId(),schoolGradeList.get(0).getName(),schoolId,curSchoolYear);

    	//开始升班
		riseGeneral(schoolId,schoolGradeList);
        if(riseClazzVo.getLowestClazzList()!=null && !riseClazzVo.getLowestClazzList().isEmpty()) {
        	createLowest(riseClazzVo.getLowestClazzList());
        }
        
        //修改教师年段长职务
        teacherPostService.GradeLeaderPromotionCarry(schoolId,riseClazzVo.getGradeMasterList());
        
        //修改电子班牌账号绑定信息
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
        
        //更新或者生成学校记录表
        updateRiseRecord(riseClazzVo,curSchoolYear.getSchoolYearId());
        
        //升学删除云班牌的数据
		getExecutor().execute(() -> { 
			dmClassCardFeign.deleteDmKqData(schoolId);
		});
        
	}
	
	private void updateRiseRecord(RiseClazzVo riseClazzVo,String schoolYearId) {
		
		Date nowDate = new Date();
        SchoolYear querySchoolYear = schoolYearService.findSchoolYearById(schoolYearId);
        
        //添加新学年
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
        
        //更新学年
        SchoolYear updateSchoolYear = new SchoolYear();
        updateSchoolYear.setId(querySchoolYear.getId());
        updateSchoolYear.setNextTermEnd(addSchoolYear.getLastTermBegin());
        schoolYearService.updateSchoolYear(updateSchoolYear);
        
        //更新该学校的学年缓存
        schoolYearService.clearCurSchoolYear(querySchoolYear.getSchoolId());
        
	}
	
	/**
	 * 升学档案
	 */
	private void saveToHistoryGeneralRise(String schoolId,CurSchoolYear curSchoolYear) {
		//学生管理升学存档数据
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
	 *毕业班数据处理
	 * @param gradeId
	 * @param gradeName
	 * @param clazzIdList 顶级班级的id集合
	 * @param semester
	 */
	private void handleTopRiseData(String schoolId,String gradeId,String gradeName,List<String> clazzIdList,CurSchoolYear curSchoolYear,List<JwClasses> clazzList) {
	    //删除学生的宿舍
		Student student  = new Student();
		student.setSchoolId(schoolId);
		student.setClazzIdList(clazzIdList);
	    dormPersonService.LeaveDormByClassesIds(student);
		//修改家长绑定的毕业生孩子
		parentService.deleteParentStudentByShiftpromotion(clazzIdList);
		//将毕业学生移入档案
		updateGradeStu(schoolId,gradeName,clazzIdList);
		//存档顶级班级
		clazzList.forEach(clazz->{
				JwClassesHistory classesHistory = new JwClassesHistory();
				BeanUtils.copyProperties(clazz, classesHistory);
				classesHistory.setTerm(curSchoolYear.getTerm());
				classesHistory.setSchoolYearId(curSchoolYear.getSchoolYearId());
				classesHistory.setFromTo(curSchoolYear.getFromTo());
				mot.save(classesHistory);
			});
		//删除顶级班级
		jwClassesDao.deleteJTopClazz(clazzIdList);
		
		//存档相关数据
	    getExecutor().execute(() -> {
		    //存档电子班牌光荣榜
		    saveDmHonour(clazzIdList);
			//存档学生评价
			HistoryPojo historyPojo = new HistoryPojo();
			historyPojo.setClassIdList(clazzIdList);
			stuEvaluateService.moveStuEvaluateToHistory(historyPojo);
			//综合素质成绩存档数据 产品要求不存档
			//compareQualityService.moveCompareQualityDataToHistory(clazzIdList);
			//学情存档
			analysisScoreFeign.saveTopClazzScoreAnalyse(clazzIdList);
		});

		//开启线程删除毕业班相关数据
		getExecutor().execute(() -> {
		  removeClassRelateData(clazzIdList, schoolId);
		});
		
	}
	
	/**
	 * 删除班级相关联的数据
	 * @param clazzIdList
	 * @param schoolId
	 */
	private void removeClassRelateData(List<String> clazzIdList,String schoolId) {
		
		//删除老师的关联信息
		clazzIdList.forEach(ss-> {
			TeacherClasses teacherClasses = new TeacherClasses();
			teacherClasses.setClassesId(ss);
			teacherClasses.setSchoolId(schoolId);
			teacherClassesService.delTeacherClassesByClass(teacherClasses);
		});
		
		//删除班级的职务
		jwClaCadresStuDao.deleteClaCadresStuByClazzIdList(clazzIdList);
		jwClaCadresDao.deleteClaCadresByClazzIdList(clazzIdList);
		
	    //删除课程相关数据
		scheduleListService.pastDueScheduleList(schoolId);
		
		//删除云班牌的班级数据
		dmClassCardFeign.deleteDmData(clazzIdList);
		
        //删除智慧课堂检测相关数据
		classTestFeign.deleteClassTest(clazzIdList);
		
		//删除作业和错题本相关数据
		homeworkFeign.deleteHomeworkByClasses(clazzIdList);
	}
	
	private void riseGeneral(String schoolId,List<Dd> gradeDdList) {
		JwClasses jwClasses = new JwClasses();
		jwClasses.setSchoolId(schoolId);
		jwClassesDao.riseGenerateClazz(jwClasses);
		//根据grade_id修改grade_name
		gradeDdList.forEach(dd ->{
			JwClasses updateClazz = new JwClasses();
			updateClazz.setGradeId(dd.getId());
			updateClazz.setSchoolId(schoolId);
			updateClazz.setGradeName(dd.getName());
			jwClassesDao.updateByGradeIdAndSchoolId(updateClazz);
		});
	}
	
	/**
	 * 创建最低年级的班级
	 * @param clazzList
	 */
	private void createLowest(List<JwClasses> clazzList) {
		jwClassesDao.batchSaveJwClasses(clazzList);
		//给低年级班级创建职务
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
	 * 将学生移入毕业生档案
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
			studentGraduation.setClassesName(gradeName+"("+stuModel.getClassesNumber()+")班");
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
	 * 将光荣榜存入历史表
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
