package com.yice.edu.cn.jy.service.prepareLessons;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.ItemPackage;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.LessonsFile;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.TeachingPlan;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.TextbookSetting;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItem;
import com.yice.edu.cn.jy.dao.prepareLessons.ILessonsFileDao;
import com.yice.edu.cn.jy.dao.prepareLessons.ITeachingPlanDao;
import com.yice.edu.cn.jy.dao.prepareLessons.ItemPackageDao;
import com.yice.edu.cn.jy.dao.resources.IJyResoucesDao;
import com.yice.edu.cn.jy.feignClient.prepareLessons.TeacherClassesFeign;
import com.yice.edu.cn.jy.wordGenerator.AnalysisMEditorHTML;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.io.ByteArrayOutputStream;
import java.util.*;

/**
 * 
* @ClassName: TeachingPlanService  
* @Description: 教师备课service  
* @author xuchang  
* @date 2018年11月6日
 */
@Service
public class TeachingPlanService {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
    private ITeachingPlanDao teachingPlanDao;
    @Autowired
    private ILessonsFileDao lessonsFileDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private ItemPackageDao itemPackageDao;
    
    @Autowired
    private IJyResoucesDao jyResoucesDao;

	@Autowired
	private TextbookSettingService textbookSettingService;

	@Autowired
	private ChapterMenuService chapterMenuService;
	@Autowired
	private TeacherClassesFeign teacherClassesFeign;
    
    public ResponseJson findTeachingPlanListByCondition(TeachingPlan teachingPlan) {
    	
    	List<TeachingPlan> plans = teachingPlanDao.findTeachingPlanListByCondition(teachingPlan);
    	long count = teachingPlanDao.findTeachingPlanCountByCondition(teachingPlan);
    	return new ResponseJson(plans, count);
    }
    
    @Transactional
    public TeachingPlan findTeachingPlanById(String id) {
    	//更新查看次数
    	teachingPlanDao.updateViewCount(id);
    	//获取教案
    	TeachingPlan teachingPlan = teachingPlanDao.findTeachingPlanById(id);
        return teachingPlan;
    }
    
    public TeachingPlan editTeachingPlanById(String id) {
    	TeachingPlan teachingPlan = findTeachingPlanById(id);
        return teachingPlan;
    }
    
    public TeachingPlan lookTeachingPlanById(String id) {
    	TeachingPlan teachingPlan = findTeachingPlanById(id);
    	//通过id获取题包
    	if(teachingPlan!=null) {
    		if(StringUtils.isNotBlank(teachingPlan.getItemPackageIds())) {
    			
    			List<ItemPackage> itemPackages = itemPackageDao.findItemPackageByIds(new ArrayList<>(Arrays.asList(teachingPlan.getItemPackageIds().split(","))));
        		if(itemPackages!=null) teachingPlan.setItemPackages(itemPackages);
    			
    		}
    		
    		
    	}
    	
    	return teachingPlan;
    }
    
    
    
    @Transactional
    public ResponseJson saveTeachingPlan(TeachingPlan teachingPlan) {
        TeachingPlan teachingPlanCondition=new TeachingPlan();
        teachingPlanCondition.setCourseName(teachingPlan.getCourseName());
        //检验是否有重复课程名称
        long count = teachingPlanDao.findTeachingPlanCountByCondition(teachingPlanCondition);
        if(count>0) {
        	return new ResponseJson(false, "教案名称有重复，请修改！");
        }else {
        	//保存课程
        	String nextId = sequenceId.nextId();
        	teachingPlan.setId(nextId);
        	teachingPlan.setViewCount(0);
        	teachingPlan.setDownloadCount(0);
        	int successRow = teachingPlanDao.saveTeachingPlan(teachingPlan);
        	return successRow==1?new ResponseJson(nextId):new ResponseJson(false, "保存失败");
        }
        
    }
    
    @Transactional
    public ResponseJson updateTeachingPlan(TeachingPlan teachingPlan) {
    	if(ObjectUtil.isNotNull(teachingPlan.getShareStatus()) && teachingPlan.getShareStatus() == 1){
    		 //分享到校门时候，先查出来再插入更新
			TeachingPlan plan = teachingPlanDao.findTeachingPlanById(teachingPlan.getId());
			teachingPlan.setTeachTarget(plan.getTeachTarget());
			teachingPlan.setTearchPoint(plan.getTearchPoint());
			teachingPlan.setTearchMethod(plan.getTearchMethod());
			teachingPlan.setTeachDifficulty(plan.getTeachDifficulty());
			teachingPlan.setTearchProcess(plan.getTearchProcess());
		}
    	if(Objects.equals(" ",teachingPlan.getItemPackageIds())){
			teachingPlan.setItemPackageIds("");
		}
    	List<LessonsFile> lessonsFiles = teachingPlan.getLessonsFiles();
        int successRow = teachingPlanDao.updateTeachingPlan(teachingPlan);
        
    	if(lessonsFiles!=null) {
    		LessonsFile le=new LessonsFile();
    		le.setTeachingPlanId(teachingPlan.getId());
    		lessonsFiles.forEach(leFile->{
    			leFile.setTeachingPlanId(teachingPlan.getId());
    		});
    		//删除关联文件
    		lessonsFileDao.deleteLessonsFileByCondition(le);
    		if(lessonsFiles.size()!=0) {
				lessonsFileDao.batchSaveLessonsFile(lessonsFiles);
			}
    	}
    	
    	return successRow==1?new ResponseJson(true, "更新成功"):new ResponseJson(false, "更新失败");
    	
    }
    
    
    @Transactional
    public ResponseJson deleteTeachingPlan(String id) {
    	int successRow = teachingPlanDao.deleteTeachingPlan(id);
    	LessonsFile le=new LessonsFile();
		le.setTeachingPlanId(id);
    	lessonsFileDao.deleteLessonsFileByCondition(le);
    	return successRow==1?new ResponseJson(true, "删除成功"):new ResponseJson(true, "删除失败");
    }
    
    @Transactional
    public ResponseJson deleteLessonsFile(String id) {
    	
    	int successRow = lessonsFileDao.deleteLessonsFile(id);
    	jyResoucesDao.deleteJyResouces(id);
    	return successRow==1?new ResponseJson(true, "删除成功"):new ResponseJson(true, "删除失败");
    }
    
    
    @Transactional
    public ResponseEntity<byte[]> downloadTeachingPlan(String id) {
		TeachingPlan entity = editTeachingPlanById(id);
		ResponseEntity<byte[]> responseEntity = null;
		if(entity!=null) {
			// 教学目标
			String teachTarget=entity.getTeachTarget();
			// 教学要点
			String tearchPoint=entity.getTearchPoint();
			// 教学方法
			String tearchMethod=entity.getTearchMethod();
			// 教学难点
			String teachDifficulty=entity.getTeachDifficulty();
			// 教学过程
			String tearchProcess=entity.getTearchProcess();
			StringBuilder sb =new StringBuilder(String.format("<title>%1$s</title>", entity.getCourseName()));
			sb.append(StringUtils.isNotBlank(teachTarget)?String.format("%s"+teachTarget, "<h1>教学目标</h1>"):"");
			sb.append(StringUtils.isNotBlank(tearchPoint)?String.format("%s"+tearchPoint, "<h1>教学要点</h1>"):"");
			sb.append(StringUtils.isNotBlank(tearchMethod)?String.format("%s"+tearchMethod, "<h1>教学方法</h1>"):"");
			sb.append(StringUtils.isNotBlank(teachDifficulty)?String.format("%s"+teachDifficulty, "<h1>教学难点</h1>"):"");
			sb.append(StringUtils.isNotBlank(tearchProcess)?String.format("%s"+tearchProcess, "<h1>教学过程</h1>"):"");
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "attachment; filename=\"" + entity.getCourseName() + ".docx\"");
	        ByteArrayOutputStream bout = new ByteArrayOutputStream();
			try {
				AnalysisMEditorHTML.writeContextToStream(sb.toString(), bout);
				byte[] b = bout.toByteArray();
				responseEntity=new ResponseEntity<byte[]>(b, headers, HttpStatus.OK);
				
			}finally{
				IoUtil.close(bout);
			}
			teachingPlanDao.updateDownloadCount(id);
		}
		
		return responseEntity;
	}



	public ResponseJson findMaterialInformation(TeachingPlan teachingPlan) {
		List<TeachingPlan> plans = teachingPlanDao.findTeachingPlanListByCondition(teachingPlan);
		TeachingPlan teachingplan = null;
		String textbookId = null;
		if(CollectionUtil.isNotEmpty(plans)){
			teachingplan = plans.get(0);
			textbookId = teachingplan.getTextbookId();
		}
		//当前老师备课的所有教材
		List<TextbookSetting> setting = textbookSettingService.findTextbookSettingByTeacherId(teachingPlan.getTeacherId());
		//老师备课选了教材但是没有设置教案，默认展示教材的第一个展示出章节
		if(Objects.isNull(teachingplan) && CollectionUtil.isNotEmpty(setting)){
			textbookId = setting.get(0).getTextbookId();
		}
		//默认展示最后一次备课所选的教材设置，并且默认展示该教材所有的章节信息
		if(Objects.nonNull(teachingPlan.getTextbookId())){
			textbookId = teachingPlan.getTextbookId();
		}
		List<MaterialItem> materialItems = chapterMenuService.findAllMaterialItemByMaterialIdCancat(textbookId);
		return new ResponseJson(teachingplan,setting,materialItems);
	}
    

}
