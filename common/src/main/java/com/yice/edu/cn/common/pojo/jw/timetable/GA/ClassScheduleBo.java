package com.yice.edu.cn.common.pojo.jw.timetable.GA;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yice.edu.cn.common.pojo.jw.timetable.TimetableArrangeTime;


/**
 * 
* @ClassName: ClassSchedule  
* @Description: 可用资源(教室、教师、课程、时间片、班级)
* 				读取染色体
* @author xuchang  
* @date 2018年12月24日
 */
public class ClassScheduleBo {
	
	private Map<String, TimetableArrangeTime> TeacherNoArrangeTimeSlots;
	
	private Map<String, TimetableArrangeTime> CourseNoArrangeTimeSlots;
	
//	private Map<Integer, Room> rooms;
	
	private Map<String, TeacherBo> teachers;
	
	private Map<String, Course> courses;
	
	private Map<Integer, TeacherTime> teacherTimes;
	
	private Map<Integer, ClassesBo> classes;
	
	private Map<Integer, TeachInfoSplit> teachingPlans;
	
	private Map<Integer, List<TeachInfoSplit>> teachingPlanByClass;
	
	//预留字段,
	private int[] endSlot= {4,7};
	
	private int weeks=5;
	
	private int daysSlots=7;
	
	public ClassScheduleBo() {
		// TODO Auto-generated constructor stub
	}
	
	public ClassScheduleBo(ClassScheduleBo cloneable) {
//		this.rooms = cloneable.getRooms();
		this.teachers = cloneable.getTeachers();
		this.courses = cloneable.getCourses();
		this.classes = cloneable.getClasses();
		this.teacherTimes = cloneable.getTeacherTimes();
		this.teachingPlans=cloneable.getTeachingPlans();
		this.weeks=cloneable.weeks;
		this.daysSlots=cloneable.daysSlots;
	}

	
//	public void addRoom(Room room) {
//		
//		rooms.put(room.getId(), room);
//	}
	
	public void addTeacher(TeacherBo teacher) {
		
		teachers.put(teacher.getId(), teacher);
	}
	
	public void addTeacherTimes(TeacherTime time) {
		
		teacherTimes.put(time.getId(), time);
	}
	
	public void addClasses(ClassesBo classes) {
		
		this.classes.put(classes.getId(), classes);
	}
	
	public void addCourses(Course course) {
		
		courses.put(course.getId(), course);
	}

	public ClassesBo getClasses(Integer classId) {
		ClassesBo class1 = classes.get(classId);
//		return String.format("%s年级 %d班", class1.getGradeId(),class1.getClassNumb());
		return class1;
	}
	
	public String getTeacher(Integer teacherPlanSId) {
		
		TeachInfoSplit teachingPlanSplit = teachingPlans.get(teacherPlanSId);
		return teachers.get(teachingPlanSplit.getTeacherId()).getName();
	}
	
//	public String getRoom(Integer roomId) {
//		Room room = rooms.get(roomId);
//		return String.format("%s栋 %s室", room.getBuildingNumb(),room.getRoomNumb());
//	}
	
//	public String getCourse(Integer teacherPlanSId) {
//		return teachingPlans.get(teacherPlanSId).getCourse().getName();
//	}
	
	public TeachInfoSplit getTeachInfoSplit(Integer tiId) {
		return teachingPlans.get(tiId);
	}
	
	
	public Map<Integer, TeachInfoSplit> getTeachingPlans() {
		return teachingPlans;
	}

	public void setTeachingPlans(Map<Integer, TeachInfoSplit> teachingPlans) {
		this.teachingPlans = teachingPlans;
	}



	public void setCourses(HashMap<String, Course> courses) {
		this.courses = courses;
	}


	public void setTeacherTimes(HashMap<Integer, TeacherTime> teacherTimes) {
		this.teacherTimes = teacherTimes;
	}
	public Map<Integer, List<TeachInfoSplit>> getTeachingPlanByClass() {
		return teachingPlanByClass;
	}

	public void setTeachingPlanByClass(Map<Integer, List<TeachInfoSplit>> teachingPlanByClass) {
		this.teachingPlanByClass = teachingPlanByClass;
	}

	public TeachInfoSplit getRandomPlan() {
		
		Object[] values = this.teachingPlans.values().toArray();
		TeachInfoSplit plan =(TeachInfoSplit)values[(int)(values.length*Math.random())];
		
		return plan;
		
	}
	
	
	public Map<String, TeacherBo> getTeachers() {
		return teachers;
	}

	public void setTeachers(Map<String, TeacherBo> teachers) {
		this.teachers = teachers;
	}

	public Map<String, Course> getCourses() {
		return courses;
	}

	public void setCourses(Map<String, Course> courses) {
		this.courses = courses;
	}

	public Map<Integer, TeacherTime> getTeacherTimes() {
		return teacherTimes;
	}

	public void setTeacherTimes(Map<Integer, TeacherTime> teacherTimes) {
		this.teacherTimes = teacherTimes;
	}

	public Map<Integer, ClassesBo> getClasses() {
		return classes;
	}

	public void setClasses(Map<Integer, ClassesBo> classes) {
		this.classes = classes;
	}
	

	public  boolean isEnd(int slot) {
		
		for(int i=0;i<endSlot.length;i++) {
			
			if(slot==endSlot[i]) return true;
			
		}
		return false;
	}

	public Map<String, TimetableArrangeTime> getTeacherNoArrangeTimeSlots() {
		return TeacherNoArrangeTimeSlots;
	}

	public void setTeacherNoArrangeTimeSlots(Map<String, TimetableArrangeTime> teacherNoArrangeTimeSlots) {
		TeacherNoArrangeTimeSlots = teacherNoArrangeTimeSlots;
	}

	public Map<String, TimetableArrangeTime> getCourseNoArrangeTimeSlots() {
		return CourseNoArrangeTimeSlots;
	}

	public void setCourseNoArrangeTimeSlots(Map<String, TimetableArrangeTime> courseNoArrangeTimeSlots) {
		CourseNoArrangeTimeSlots = courseNoArrangeTimeSlots;
	}

	public int[] getEndSlot() {
		return endSlot;
	}

	public void setEndSlot(int[] endSlot) {
		this.endSlot = endSlot;
	}

	public int getWeeks() {
		return weeks;
	}

	public void setWeeks(int weeks) {
		this.weeks = weeks;
	}

	public int getDaysSlots() {
		return daysSlots;
	}

	public void setDaysSlots(int daysSlots) {
		this.daysSlots = daysSlots;
	}
	
	
	
}
