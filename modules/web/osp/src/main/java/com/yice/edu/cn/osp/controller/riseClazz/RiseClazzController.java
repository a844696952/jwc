package com.yice.edu.cn.osp.controller.riseClazz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.classes.rise.RiseClazzVo;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.jw.schoolYear.SchoolYear;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherPost;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import com.yice.edu.cn.osp.service.dd.DdService;
import com.yice.edu.cn.osp.service.dm.classCard.DmClassCardService;
import com.yice.edu.cn.osp.service.dm.school.SchoolService;
import com.yice.edu.cn.osp.service.jw.classes.JwClassesService;
import com.yice.edu.cn.osp.service.jw.schoolYear.SchoolYearService;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherPostService;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/riseClazz")
@Api(value = "/riseClazz", description = "升班")
public class RiseClazzController {
	@Autowired
	private SequenceId sequenceId;
	@Autowired
	private JwClassesService jwClassesService;
	@Autowired
	private SchoolService schoolService;
	@Autowired
	private DdService ddService;
    @Autowired
    private DmClassCardService dmClassCardService;
    @Autowired
    private TeacherPostService teacherPostService;
    @Autowired
    private SchoolYearService schoolYearService;
	
	@PostMapping("/schoolRiseClazz")
	@ApiOperation(value = "班级升学", notes = "返回响应对象")
	public ResponseJson riseClazz(
			@RequestBody
			@ApiParam(value = "升班参数", required = true)
			RiseClazzVo riseClazzVo) {
		String schoolId = LoginInterceptor.mySchoolId();
		//判断今年是否要升学
		int status = jwClassesService.prepareRise(schoolId);
		if(status!=Constant.SCHOOL_RISE_RECORD.BEGIN_RISE) {
			return new ResponseJson(false,"升班状态错误!");
		}
		//判断开始升班时间(MM-dd)是否大于下学期的开始时间
		CurSchoolYear currentSchoolYear = schoolYearService.findCurSchoolYear(schoolId);
		SchoolYear schoolYearInfo = schoolYearService.findSchoolYearById(currentSchoolYear.getSchoolYearId());
		String nextTermBeginMMdd = schoolYearInfo.getNextTermBegin().substring(5);
		String nowMMdd = DateUtil.format(new Date(), "MM-dd");
		if(nowMMdd.compareTo(nextTermBeginMMdd)<0) {
			return new ResponseJson(false,"升班的月份日期不能小于下学期的开始日期!");
		}
		
		//开始升学
		riseClazzVo.setCreatorId(LoginInterceptor.myId());
		riseClazzVo.setCreatorName(LoginInterceptor.currentTeacher().getName());
		riseClazzVo.setSchoolId(LoginInterceptor.mySchoolId());
		jwClassesService.riseClazz(riseClazzVo);
		return new ResponseJson();
	}
	
	@PostMapping("/prepareRise")
	@ApiOperation(value = "判断是否升学", notes = "返回状态说明: 1.未设置升学的开始时间和结束时间 2.不在升学时间范围内 3.已经升过学 4.升学状态异常 5.正在升学中 ",response = Integer.class)
	public ResponseJson prepareRise() {
		return new ResponseJson(jwClassesService.prepareRise(LoginInterceptor.mySchoolId()));
	}
	
	@PostMapping("/getCreateLowestClazz")
	@ApiOperation(value = "查询要创建的最低年级班级", notes = "最低年级的班级list",response = JwClasses.class)
	public ResponseJson getCreateLowestClazz() {
		String schoolId = LoginInterceptor.mySchoolId();
		School school = schoolService.findSchoolById(schoolId);
    	String typeId = school.getTypeId();
    	Dd dd = new Dd();
    	dd.setLevelType(typeId);
    	dd.setTypeId(Constant.DD_TYPE.GRADE);
    	dd.setPager(new Pager().setSortField("sort").setSortOrder("ASC"));
    	List<Dd> schoolGradeList = ddService.findDdListByCondition(dd);
    	if(schoolGradeList.isEmpty()) {
    		return new ResponseJson(false,"学校没有选择年段");
    	}
    	Dd mixGradeDd = schoolGradeList.get(0);
    	//取该校最低年级的最大班级号和入学年份
    	JwClasses jwClasses = new JwClasses();
    	jwClasses.setGradeId(mixGradeDd.getId());
    	jwClasses.setSchoolId(schoolId);
    	jwClasses.setPager(new Pager().setSortField("number").setSortOrder("ASC"));
		List<JwClasses> queryClazzList = jwClassesService.findJwClassesListByCondition(jwClasses);
		if(queryClazzList.isEmpty()) {
			return new ResponseJson(new ArrayList<JwClasses>());
		}
		//入学年份应该用最低年级的年份+1
		JwClasses lastClazz = queryClazzList.get(queryClazzList.size()-1);
		String lastClazzEnrollYear = Integer.parseInt(lastClazz.getEnrollYear())+1+"";
		int maxClazzNumber = Integer.parseInt(lastClazz.getNumber());
		List<JwClasses> clazzList = new ArrayList<JwClasses>();
		for(int i=1;i<=maxClazzNumber;i++) {
			JwClasses addClazz = new JwClasses();
			addClazz.setGradeId(mixGradeDd.getId());
			addClazz.setId(sequenceId.nextId());
			addClazz.setSchoolId(schoolId);
			addClazz.setGradeName(mixGradeDd.getName());
			addClazz.setNumber(i+"");
			addClazz.setEnrollYear(lastClazzEnrollYear);
			addClazz.setDel(Constant.DELSIGN.NORMAL);
			clazzList.add(addClazz);
		}
		return new ResponseJson(clazzList);
	}

	@PostMapping("/addClazz")
	@ApiOperation(value = "添加一个最低年级的班级", notes = "返回一个班级",response = JwClasses.class)
	public ResponseJson addClazz(
			@RequestBody
			@ApiParam(value = "{number:班号(要创建班级的班号),gradeId:年级id,gradeName:年级名称,enrollYear:入学年份}", required = true)
			JwClasses jwClassesParams) {
		
    	//取该校最低年级的最大班级号
    	JwClasses addClazz = new JwClasses();
    	addClazz.setId(sequenceId.nextId());
    	addClazz.setGradeId(jwClassesParams.getGradeId());
    	addClazz.setSchoolId(LoginInterceptor.mySchoolId());
    	addClazz.setGradeName(jwClassesParams.getGradeName());
    	addClazz.setEnrollYear(jwClassesParams.getEnrollYear());
    	addClazz.setNumber(jwClassesParams.getNumber());
    	addClazz.setDel(Constant.DELSIGN.NORMAL);
    	
		return new ResponseJson(addClazz);
	}
	
	@PostMapping("/getDmAccount")
	@ApiOperation(value = "获取全部的班牌账号", notes = "班牌账号",response = DmClassCard.class)
	public ResponseJson getDmAccount() {
		DmClassCard dmClassCard = new DmClassCard();
		dmClassCard.setSchoolId(LoginInterceptor.mySchoolId());
		List<DmClassCard> dmClassCardList = dmClassCardService.findDmClassCardListByCondition(dmClassCard);
		return new ResponseJson(dmClassCardList);
	}
	
	@PostMapping("/getSrcClazz")
	@ApiOperation(value = "预获取升学后的全部班级(除最低年级)", notes = "返回除最低年级的班级",response = JwClasses.class)
	public ResponseJson getSrcClazz() {
		String schoolId = LoginInterceptor.mySchoolId();
		School school = schoolService.findSchoolById(schoolId);
    	String typeId = school.getTypeId();
    	Dd dd = new Dd();
    	dd.setLevelType(typeId);
    	dd.setPager(new Pager().setSortField("sort").setSortOrder("ASC"));
    	dd.setTypeId(Constant.DD_TYPE.GRADE);
    	final List<Dd> schoolGradeList = ddService.findDdListByCondition(dd);
    	Dd topGrade = schoolGradeList.get(schoolGradeList.size()-1);
    	
		JwClasses jwClasses = new JwClasses();
		jwClasses.setSchoolId(LoginInterceptor.mySchoolId());
		List<JwClasses> jwClazzList = jwClassesService.findJwClassesListByCondition(jwClasses);
		
		//过滤掉最高年级
		jwClazzList = jwClazzList.stream().filter(t -> !(t.getGradeId().equals(topGrade.getId()))).collect(Collectors.toList());
		
		//升年级
		jwClazzList = jwClazzList.stream().map(clazz->{
			clazz.setGradeId((Integer.parseInt(clazz.getGradeId())+1)+"");
			clazz.setGradeName(schoolGradeList.stream().filter(t -> clazz.getGradeId().equals(t.getId())).findFirst().get().getName());
			return clazz;
		}).collect(Collectors.toList());

		return new ResponseJson(jwClazzList);
	}
	
	@PostMapping("/getGradeMasterList")
	@ApiOperation(value = "预获取升学后年段长和教师关系", notes = "教师职务的list",response = TeacherPost.class)
	public ResponseJson getGradeMasterList() {
		String schoolId = LoginInterceptor.mySchoolId();
		School school = schoolService.findSchoolById(schoolId);
    	String typeId = school.getTypeId();
    	Dd dd = new Dd();
    	dd.setLevelType(typeId);
    	dd.setPager(new Pager().setSortField("sort").setSortOrder("ASC"));
    	dd.setTypeId(Constant.DD_TYPE.GRADE);
    	List<Dd> schoolGradeList = ddService.findDdListByCondition(dd);
    	List<TeacherPost> gradeTeacherList = teacherPostService.findGradeTeacherBySchool(schoolId, schoolGradeList);
		return new ResponseJson(gradeTeacherList);
	}
	
	public static void main(String[] args) {
		String dateStr = "2019-08-10";
		System.out.println(dateStr.substring(5));
	}
	
}
