package com.yice.edu.cn.jw.service.timetable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.student.ClassesStudentNum;
import com.yice.edu.cn.common.pojo.jw.timetable.Timetable;
import com.yice.edu.cn.common.pojo.jw.timetable.TimetableArrangeTime;
import com.yice.edu.cn.common.pojo.jw.timetable.TimetableJob;
import com.yice.edu.cn.common.pojo.jw.timetable.TimetableTeachInfo;
import com.yice.edu.cn.common.pojo.jw.timetable.TimetableTime;
import com.yice.edu.cn.common.pojo.jw.timetable.GA.ClassScheduleBo;
import com.yice.edu.cn.common.pojo.jw.timetable.GA.ClassesBo;
import com.yice.edu.cn.common.pojo.jw.timetable.GA.Course;
import com.yice.edu.cn.common.pojo.jw.timetable.GA.GeneticAlgorithm;
import com.yice.edu.cn.common.pojo.jw.timetable.GA.Individual;
import com.yice.edu.cn.common.pojo.jw.timetable.GA.Matrix;
import com.yice.edu.cn.common.pojo.jw.timetable.GA.Population;
import com.yice.edu.cn.common.pojo.jw.timetable.GA.TeachInfoSplit;
import com.yice.edu.cn.common.pojo.jw.timetable.GA.TeacherBo;
import com.yice.edu.cn.common.pojo.jw.timetable.GA.TeacherTime;
import com.yice.edu.cn.jw.dao.classes.IJwClassesDao;
import com.yice.edu.cn.jw.dao.jwCourse.JwCourseDao;
import com.yice.edu.cn.jw.dao.student.IStudentDao;
import com.yice.edu.cn.jw.dao.timetable.ITimetableArrangeTimeDao;
import com.yice.edu.cn.jw.dao.timetable.ITimetableDao;
import com.yice.edu.cn.jw.dao.timetable.ITimetableJobDao;
import com.yice.edu.cn.jw.dao.timetable.ITimetableTeachInfoDao;
import com.yice.edu.cn.jw.dao.timetable.ITimetableTimeDao;

/**
 * 
* @ClassName: GARunService  
* @Description: 遗传算法排课执行类(暂行单年级排课)
* @author xuchang  
* @date 2018年12月20日
 */
@Service
public class GARunService {
		
		private final Logger log = LoggerFactory.getLogger(getClass());
	
		@Autowired
		private ITimetableArrangeTimeDao arrangeTimeConditionDao;
		
		@Autowired
		private ITimetableTeachInfoDao teachInfoDao;
		
		@Autowired
		private ITimetableTimeDao timeDao;
		
		@Autowired
		private ITimetableJobDao timetableJobDao;
		
		@Autowired
	    private JwCourseDao jwCourseDao;
		
		@Autowired
		private IJwClassesDao jwClassesDao;
		
		@Autowired
		private IStudentDao studentDao;
		
		@Autowired
	    private ITimetableDao timetableDao;
		
		@Autowired
	    private SequenceId sequenceId;
		
		
		/**
		 * 
		 * @Description: 执行排课    
		 * @throws
		 */
		public ResponseJson execArrangeSchedule(String jobId) {
			TimetableJob timetableJob = timetableJobDao.findTimetableJobById(jobId);
			if(timetableJob==null) {
				return new ResponseJson(false, "未创建工作空间！");
			}
			if(timetableJob.getStatus()==99) {
				return new ResponseJson(false, "正在排课中，请稍后！");
			}
			// 初始化可用资源
			ClassScheduleBo classSchedule = initClassSchedule(timetableJob);
			
			ResponseJson validationResult = parameterValidation(classSchedule);
			if(validationResult.getResult().isSuccess()) {
				
				new Thread(()-> {
					
						
						TimetableJob updateJob =new TimetableJob();
						updateJob.setId(timetableJob.getId());
						updateJob.setStatus(99);
						timetableJobDao.updateTimetableJob(updateJob);
						try {
							Matrix m=GARun(classSchedule);
							if(m==null) {
								updateJob.setStatus(3);
								timetableJobDao.updateTimetableJob(updateJob);
							}else {
								generateTimetable(m, classSchedule,timetableJob);
							}
							
						}catch(Exception e) {
							updateJob.setStatus(2);
							timetableJobDao.updateTimetableJob(updateJob);
							System.out.println(e);
							log.error("排课异常中断={}", e.getMessage(), e);
						}
					
				}).start();
				
				
			}
			
			return validationResult;
		}
		
		public Matrix GARun(ClassScheduleBo classSchedule) {
			//初始化遗传参数
			GeneticAlgorithm ga=new GeneticAlgorithm(1000,0.08,0.8,0.3,2);
			//初始化群体
//			long a=Instant.now().toEpochMilli();
			Population population = ga.initPopulation(classSchedule);
//			System.out.println("当前初始化群体耗时:"+(Instant.now().toEpochMilli()-a));
			if(Arrays.stream(population.getIndividuals()).allMatch(in->in==null)) {
				return null;
			}
			//计算群体适应度
	        ga.evalPopulation(population, classSchedule);
			
			// 当前世代数
			int currentGeneration=1;
			
			int fitnessCount=0;
			double lastFitness=0;
			double currentFitness=0;
			//开始计算适应度
			// TODO 结束条件 &&ga.isTerminationConditionMet(currentGeneration, 1000) == false&&
			while(fitnessCount<500&&ga.isTerminationConditionMet(currentGeneration, 1000) == false) {
				population.sortByFit();
				Individual individual = population.getFittest(0);
				currentFitness=individual.getFitness();
				if(currentFitness==lastFitness) {
					fitnessCount++;
				}else{
					fitnessCount=0;
				}
				System.out.println(String.format("当前第%d代", currentGeneration)+" 最大适应度:"+currentFitness);
				lastFitness=currentFitness;
				//交叉遗传
//				long b=Instant.now().toEpochMilli();
				population=ga.crossoverPopulation(population);
//				System.out.println("当前交叉遗传时间:"+(Instant.now().toEpochMilli()-b));
				//  基因突变
				population = ga.mutatePopulation(population,classSchedule);
				
				// 计算群体适应度
	            ga.evalPopulation(population, classSchedule);
				currentGeneration++;
			}
			Individual indi=population.getFittest(0);
			System.out.println();
			System.out.println(String.format("当前第%d代找到最优解", currentGeneration)+" 适应度:"+indi.getFitness());
			return indi==null?null:indi.getChromosome();
		}
		
		
		@Transactional
		public void generateTimetable(Matrix chromosome,ClassScheduleBo classSchedule,TimetableJob timetableJob) {
			
			List<Timetable> timeTables=new ArrayList<>();
			
			for(int i=0;i<chromosome.getRowNum();i++) {
				
				for(int m=0;m<classSchedule.getDaysSlots();m++) {
					for(int n=0;n<classSchedule.getWeeks();n++) {
						Timetable timetable=new  Timetable();
						timetable.setId(sequenceId.nextId());
						timetable.setJobId(timetableJob.getId());
						timetable.setGradeId(timetableJob.getGradeIds());
						timetable.setSchoolId(timetableJob.getSchoolId());
						timetable.setWeekdays(n+1);
						timetable.setTimeSlot(m+1);
						timetable.setSlot(n*classSchedule.getDaysSlots()+m+1);
						int crossValue = chromosome.getCrossValue(classSchedule.getDaysSlots()*n+m, i);
						if(crossValue!=0) {
							TeachInfoSplit teachingPlanSplit = classSchedule.getTeachingPlans().get(crossValue);
							timetable.setTimetable(teachingPlanSplit);
							//暂时未考虑年级
							System.out.print(teachingPlanSplit.getName()+"|"+teachingPlanSplit.getTeacherName()+"   -   ");
							
						}else {
							ClassesBo classes = classSchedule.getClasses(i+1);
							timetable.setClassId(classes.getJwClassId());
							timetable.setClassName(classes.getClassNumb()+"班");
							System.out.print("                  -   ");
						}
						timeTables.add(timetable);
					}
					System.out.println();
				}
				System.out.println("-------------------------------------");
			}
			Timetable timetableCondi=new Timetable();
			timetableCondi.setJobId(timetableJob.getId());
			timetableDao.deleteTimetableByCondition(timetableCondi);
			timetableDao.batchSaveTimetable(timeTables);
			TimetableJob updateJob =new TimetableJob();
			updateJob.setId(timetableJob.getId());
			updateJob.setStatus(4);
			timetableJobDao.updateTimetableJob(updateJob);
		}
		
		/**
		 * 
		 * @Description: TODO(这里用一句话描述这个方法的作用)  
		 * @return    ClassSchedule 课程表    
		 * @throws
		 */
		public ClassScheduleBo initClassSchedule(TimetableJob timetableJob) {
			String jobId=timetableJob.getId();
			ClassScheduleBo classSchedule=new ClassScheduleBo();
			
			TimetableTime time=new TimetableTime();
			time.setJobId(jobId);
			TimetableTime timetableTime = timeDao.findOneTimetableTimeByCondition(time);
			
			//初始化课表时间片
			if(timetableTime!=null) {
				classSchedule.setDaysSlots(timetableTime.getAms()+timetableTime.getPms()+timetableTime.getNights());
				classSchedule.setWeeks(timetableTime.getWeeks());
				classSchedule.setTeacherTimes(initTime(timetableTime));
			}
			
			
			//初始化课程
			classSchedule.setCourses(initCourses(timetableJob));
			
			//初始化教师
			classSchedule.setTeachers(initTeacher(timetableJob));
			
			//初始化不排课时间
			initNoArrageSlots(timetableJob.getId(),classSchedule);
			
			//初始化计划
			initTeachInfo(timetableJob, classSchedule);
			
			return classSchedule;
		}
		
		public ResponseJson parameterValidation(ClassScheduleBo classSchedule) {
			
			
			if(classSchedule.getTeacherTimes()==null||classSchedule.getTeacherTimes().size()<=0) {
				return new ResponseJson(false, "未设置时间");
			}
			
			if(classSchedule.getCourses()==null||classSchedule.getCourses().size()<=0) {
				return new ResponseJson(false, "未设置课程");
			}
			
			if(classSchedule.getTeachers()==null||classSchedule.getTeachers().size()<=0) {
				return new ResponseJson(false, "未设置相应的教师和课程");
			}
			
			if(classSchedule.getTeachingPlans()==null||classSchedule.getTeachingPlans().size()<=0||classSchedule.getTeachingPlanByClass().size()<=0) {
				return new ResponseJson(false, "未设置教学计划");
			}
			
			return new ResponseJson(true, "初始数据正常");
			
		}
		
		public Map<Integer, TeacherTime> initTime(TimetableTime timetableTime){
			
			Integer ams = timetableTime.getAms();
			Integer pms = timetableTime.getPms();
			Integer nights = timetableTime.getNights();
			Integer weeks = timetableTime.getWeeks();
			Integer totalSection=ams+pms+nights;
			
			Map<Integer, TeacherTime> times=new HashMap<>(totalSection*weeks);
			AtomicInteger count=new AtomicInteger(0);
			Stream.iterate(1, n1->{
				int currentWeeks=n1;
				Stream.iterate(1,n2->{
					int timeId=count.incrementAndGet();
					times.put(timeId, new TeacherTime(timeId, currentWeeks, n2));
					return ++n2;
				}).limit(totalSection+1).count();
				return ++n1;
			}).limit(weeks+1).count();
			
			return times;
		}
		
		public Map<String, Course> initCourses(TimetableJob timetableJob){
			
			JwCourse couCondition=new JwCourse();
			couCondition.setGradeId(timetableJob.getGradeIds());
			couCondition.setSchoolId(timetableJob.getSchoolId());
			List<JwCourse> jwCourses = jwCourseDao.findJwCourseListByCondition(couCondition);
			//课程数较少，不考虑并行
			Map<String, Course> courses=new HashMap<>(jwCourses==null?8:jwCourses.size());
			
			if(jwCourses!=null&&jwCourses.size()>0) {
				courses=jwCourses.stream().collect(Collectors.toMap(JwCourse::getId, jc->{
					
					Course c=new Course(jc.getId(), jc.getImportance(), jc.getAlias(), jc.getNameId(), 1);
					return c;
				}));
			}else {
				System.out.println("该年级还未创建班级");
			}
			
			return courses;
		}
		
		
		public Map<String, TeacherBo> initTeacher(TimetableJob timetableJob){
			
			Map<String, TeacherBo> teachers=new HashMap<>();
			List<TeacherBo> gradeTeacher = timetableJobDao.findGradeTeacherByGradeId(timetableJob.getGradeIds(), timetableJob.getSchoolId());
			if(gradeTeacher!=null&&gradeTeacher.size()>0) {
				teachers=gradeTeacher.stream().collect(Collectors.toMap(TeacherBo::getId, t->t,(t1,t2)->t1));
			}
			return teachers;
		}
	
		public void initTeachInfo(TimetableJob timetableJob,ClassScheduleBo classSchedule){
			
			Map<String, TimetableArrangeTime> TeacherNoArrangeTimeSlots=classSchedule.getTeacherNoArrangeTimeSlots();
			
			Map<String, TimetableArrangeTime> CourseNoArrangeTimeSlots=classSchedule.getCourseNoArrangeTimeSlots();
			
			//获取班级
			JwClasses jwClassCondition=new JwClasses();
			jwClassCondition.setGradeId(timetableJob.getGradeIds());
			jwClassCondition.setSchoolId(timetableJob.getSchoolId());
			List<ClassesStudentNum> classStudentNum = studentDao.findClassStudentNum(timetableJob.getSchoolId(), timetableJob.getGradeIds());
			List<JwClasses> jwClassesList = jwClassesDao.findJwClassesListByCondition(jwClassCondition);
			
			//获取教学信息
			TimetableTeachInfo infoCondition=new TimetableTeachInfo();
			infoCondition.setJobId(timetableJob.getId());
			List<TimetableTeachInfo> teachInfos = teachInfoDao.findTimetableTeachInfoListByCondition(infoCondition);
			
			Map<Integer, TeachInfoSplit> teachingPlans=new HashMap<>();
			Map<Integer, List<TeachInfoSplit>> teachingPlanByClass=new HashMap<>();
			
			if(teachInfos!=null&&teachInfos.size()>0&&classStudentNum!=null&&jwClassesList!=null) {
				
				Map<String, List<TimetableTeachInfo>> collect = teachInfos.stream().collect(Collectors.groupingBy(TimetableTeachInfo::getClassId));
				int teacherPlanSplitIdex=0;
				int classIndex=0;
				Map<Integer, ClassesBo> classes=new HashMap<>(jwClassesList.size());
				for (String  jwClassId : collect.keySet()) {
					
					JwClasses jwClasses = jwClassesList.stream().filter(jwc->Objects.equals(jwc.getId(), jwClassId)).findFirst().get();
					ClassesBo cla=new ClassesBo(jwClasses, ++classIndex,classStudentNum.stream().filter(cla1->Objects.equals(cla1.getClassesId(), jwClasses.getId())).map(m->Integer.valueOf(m.getStudentNum())).findFirst().orElse(-1));
					classes.put(cla.getId(), cla);
					
					List<TeachInfoSplit> teachingPlanSplits=new ArrayList<>();
					for(TimetableTeachInfo timetableTeachInfo:collect.get(jwClassId)) {
						if(timetableTeachInfo==null) {
							System.out.println();
						}
						
						int weekCount=timetableTeachInfo.getCount();
						
						for(int indx=0;indx<weekCount;indx++) {
							TeachInfoSplit teachInfoSplit = new TeachInfoSplit(timetableTeachInfo, ++teacherPlanSplitIdex);
							Set<String> noTimeSlotPos=new HashSet<>(); 
							if(TeacherNoArrangeTimeSlots!=null&&TeacherNoArrangeTimeSlots.size()>0){
								TimetableArrangeTime noArrangeTime = TeacherNoArrangeTimeSlots.get(teachInfoSplit.getTeacherId());
								if(noArrangeTime!=null) {
									String timeSlotPos = noArrangeTime.getTimeSlotPos();
									noTimeSlotPos.addAll(Arrays.asList(timeSlotPos.split(",")));
//									teachInfoSplit.setNoArrangeSlot(noArrangeSlot);
								}
							}
							if(CourseNoArrangeTimeSlots!=null&&CourseNoArrangeTimeSlots.size()>0){
								TimetableArrangeTime noArrangeTime = CourseNoArrangeTimeSlots.get(teachInfoSplit.getJwCourseId());
								if(noArrangeTime!=null) {
									String timeSlotPos = noArrangeTime.getTimeSlotPos();
									noTimeSlotPos.addAll(Arrays.asList(timeSlotPos.split(",")));
//									teachInfoSplit.setNoArrangeSlot(noArrangeSlot);
								}
							}
							if(noTimeSlotPos.size()>0) teachInfoSplit.setNoArrangeSlot(noTimeSlotPos);
							teachInfoSplit.setNoArrangeSlotlen(noTimeSlotPos.size());
							teachingPlanSplits.add(teachInfoSplit);
							teachingPlans.put(teacherPlanSplitIdex, teachInfoSplit);
						}
						
					}
					
					teachingPlanByClass.put(classIndex, teachingPlanSplits);
					
				}
				
				//初始化班级
				classSchedule.setClasses(classes);
				
				//初始化班级分类教师计划
				classSchedule.setTeachingPlanByClass(teachingPlanByClass);
				
				//初始化所有班教师计划
				classSchedule.setTeachingPlans(teachingPlans);
			}
			
			
		}
		
		public void initNoArrageSlots(String jobId,ClassScheduleBo classSchedule) {
			Map<Integer, List<TimetableArrangeTime>> noArrageSlots=new HashMap<>();
			TimetableArrangeTime timeCondtion=new TimetableArrangeTime();
			timeCondtion.setJobId(jobId);
			List<TimetableArrangeTime> timetableArrangeTimeList = arrangeTimeConditionDao.findTimetableArrangeTimeListByCondition(timeCondtion);
			if(timetableArrangeTimeList!=null&&timetableArrangeTimeList.size()>0) {
				noArrageSlots=timetableArrangeTimeList.stream().collect(Collectors.groupingBy(TimetableArrangeTime::getType));
				List<TimetableArrangeTime> list = noArrageSlots.get(1);
				List<TimetableArrangeTime> list2 = noArrageSlots.get(2);
				if(list!=null&&list.size()>0) {
					
					classSchedule.setTeacherNoArrangeTimeSlots(list.stream().collect(Collectors.toMap(TimetableArrangeTime::getAssociationId, ta->ta, (ta1,ta2)->{
						
						ta1.setTimeSlotPos(ta1.getTimeSlotPos()+","+ta2.getTimeSlotPos());
						return ta1;
					})));
					
				} 
				if(list2!=null&&list2.size()>0) {
					
					classSchedule.setCourseNoArrangeTimeSlots(list2.stream().collect(Collectors.toMap(TimetableArrangeTime::getAssociationId, ta->ta, (ta1,ta2)->{
						
						ta1.setTimeSlotPos(ta1.getTimeSlotPos()+","+ta2.getTimeSlotPos());
						return ta1;
						
					})));
					
				}
			}
		}
		
}
