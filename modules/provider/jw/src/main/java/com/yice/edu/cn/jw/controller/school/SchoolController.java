package com.yice.edu.cn.jw.controller.school;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.jw.school.SchoolExt;
import com.yice.edu.cn.jw.service.school.SchoolService;
import com.yice.edu.cn.jw.service.schoolYear.SchoolYearService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/school")
@Api(value = "/school",description = "学校模块")
public class SchoolController {
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private SchoolYearService schoolYearService;

    @GetMapping("/findSchoolById/{id}")
    @ApiOperation(value = "通过id查找学校", notes = "返回学校对象")
    public School findSchoolById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return schoolService.findSchoolById(id);
    }

    @PostMapping("/saveSchool")
    @ApiOperation(value = "保存学校", notes = "返回学校对象")
    public School saveSchool(
            @ApiParam(value = "学校对象", required = true)
            @RequestBody School school){
        schoolService.saveSchool(school);
        return school;
    }

    @PostMapping("/findSchoolListByCondition")
    @ApiOperation(value = "根据条件查找学校列表", notes = "返回学校列表")
    public List<School> findSchoolListByCondition(
            @ApiParam(value = "学校对象")
            @RequestBody School school){
        return schoolService.findSchoolListByCondition(school);
    }
    @PostMapping("/findSchoolCountByCondition")
    @ApiOperation(value = "根据条件查找学校列表个数", notes = "返回学校总个数")
    public long findSchoolCountByCondition(
            @ApiParam(value = "学校对象")
            @RequestBody School school){
        return schoolService.findSchoolCountByCondition(school);
    }

    @PostMapping("/updateSchool")
    @ApiOperation(value = "修改学校", notes = "学校对象必传")
    public void updateSchool(
            @ApiParam(value = "学校对象,对象属性不为空则修改", required = true)
            @RequestBody School school){
        schoolService.updateSchool(school);
    }

    @GetMapping("/deleteSchool/{id}")
    @ApiOperation(value = "通过id删除学校")
    public void deleteSchool(
            @ApiParam(value = "学校对象", required = true)
            @PathVariable String id){
        schoolService.deleteSchool(id);
    }
    @PostMapping("/deleteSchoolByCondition")
    @ApiOperation(value = "根据条件删除学校")
    public void deleteSchoolByCondition(
            @ApiParam(value = "学校对象")
            @RequestBody School school){
        schoolService.deleteSchoolByCondition(school);
    }


    @GetMapping("/validateSchoolAccount/{schoolId}")
    public boolean validateSchoolAccount(@PathVariable("schoolId") String schoolId){
        String[] validStatus=schoolService.getValidStatusFromCache(schoolId);
        if(validStatus==null){
            return false;
        }
        return validStatus[0].equals("42")&&(validStatus[1]==null||validStatus[1].compareTo(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))>0);
    }

    @PostMapping("/saveSchoolAndSchoolYear")
    public void saveSchoolAndSchoolYear(@RequestBody SchoolExt schoolExt){
        schoolService.saveSchoolAndSchoolYear(schoolExt);
    }
    
	@GetMapping("/prepareRise/{schoolId}")
	@ApiOperation(value = "通过学校id判断现在的升学情况", notes = "返回状态说明: 1.未设置升学的开始时间 2.升学还未开始 3.已经升过学 4.升学状态异常 5.正在升学中 6.多年未升学,请在升学日期开始升学",response = Integer.class)
	public Integer prepareRise(@PathVariable String schoolId) {
		return schoolService.prepareRise(schoolId);
	}


	@GetMapping("/findSchoolExpireOrSchoolYear/{schoolId}")
    public ResponseJson findSchoolExpireOrSchoolYear(@PathVariable String schoolId){
        return schoolService.findSchoolExpireOrSchoolYear(schoolId);
    }

    @PostMapping("/findSchoolNoRepetitionSchoolName")
    public long findSchoolNoRepetitionSchoolName(@RequestBody School school){
        return schoolService.findSchoolNoRepetitionSchoolName(school);
    }
    
    @PostMapping("/queryClockInRange")
    public List<Integer> queryClockInRange(){
        return clockInRange();
    }
    
    private List<Integer> clockInRange(){
    	List<Integer> list = new ArrayList<Integer>();
    	list.add(100);
    	list.add(200);
    	list.add(300);
    	list.add(400);
    	list.add(500);
    	list.add(600);
    	list.add(700);
    	list.add(800);
    	list.add(900);
    	list.add(1000);
    	list.add(2000);
    	return list;
    }
    
    @PostMapping("/updateSchoolAndSaveSchoolYear")
    public void updateSchoolAndSaveSchoolYear(@RequestBody SchoolExt schoolExt){
        schoolService.updateSchoolAndSaveSchoolYear(schoolExt);
    }
}
