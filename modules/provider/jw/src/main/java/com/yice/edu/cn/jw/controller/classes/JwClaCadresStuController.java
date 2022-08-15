package com.yice.edu.cn.jw.controller.classes;

import com.yice.edu.cn.common.pojo.jw.classes.JwClaCadresStu;
import com.yice.edu.cn.jw.service.classes.JwClaCadresStuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/jwClaCadresStu")
@Api(value = "/jwClaCadresStu", description = "班级的职位的学生模块")
public class JwClaCadresStuController {
	@Autowired
	private JwClaCadresStuService jwClaCadresStuService;

	@GetMapping("/findJwClaCadresStuById/{id}")
	@ApiOperation(value = "通过id查找班级的职位的学生", notes = "返回班级的职位的学生对象")
	public JwClaCadresStu findJwClaCadresStuById(
			@ApiParam(value = "需要用到的id", required = true) @PathVariable String id) {
		return jwClaCadresStuService.findJwClaCadresStuById(id);
	}


	@PostMapping("/saveJwClaCadresStu")
	@ApiOperation(value = "保存班级的职位的学生", notes = "返回班级的职位的学生对象")
	public JwClaCadresStu saveJwClaCadresStu(
			@ApiParam(value = "班级的职位的学生对象", required = true) @RequestBody JwClaCadresStu jwClaCadresStu) {
		jwClaCadresStuService.saveJwClaCadresStu(jwClaCadresStu);
		return jwClaCadresStu;
	}

	@PostMapping("/findJwClaCadresStuListByCondition")
	@ApiOperation(value = "根据条件查找班级的职位的学生列表", notes = "返回班级的职位的学生列表")
	public List<JwClaCadresStu> findJwClaCadresStuListByCondition(
			@ApiParam(value = "班级的职位的学生对象") @RequestBody JwClaCadresStu jwClaCadresStu) {
		return jwClaCadresStuService.findJwClaCadresStuListByCondition(jwClaCadresStu);
	}

	@PostMapping("/findJwClaCadresStuCountByCondition")
	@ApiOperation(value = "根据条件查找班级的职位的学生列表个数", notes = "返回班级的职位的学生总个数")
	public long findJwClaCadresStuCountByCondition(
			@ApiParam(value = "班级的职位的学生对象") @RequestBody JwClaCadresStu jwClaCadresStu) {
		return jwClaCadresStuService.findJwClaCadresStuCountByCondition(jwClaCadresStu);
	}

	@PostMapping("/updateJwClaCadresStu")
	@ApiOperation(value = "修改班级的职位的学生", notes = "班级的职位的学生对象必传")
	public void updateJwClaCadresStu(
			@ApiParam(value = "班级的职位的学生对象,对象属性不为空则修改", required = true) @RequestBody JwClaCadresStu jwClaCadresStu) {
		jwClaCadresStuService.updateJwClaCadresStu(jwClaCadresStu);
	}

	@GetMapping("/deleteJwClaCadresStu/{id}")
	@ApiOperation(value = "通过id删除班级的职位的学生")
	public void deleteJwClaCadresStu(@ApiParam(value = "班级的职位的学生对象", required = true) @PathVariable String id) {
		jwClaCadresStuService.deleteJwClaCadresStu(id);
	}

	@PostMapping("/deleteJwClaCadresStuByCondition")
	@ApiOperation(value = "根据条件删除班级的职位的学生")
	public void deleteJwClaCadresStuByCondition(
			@ApiParam(value = "班级的职位的学生对象") @RequestBody JwClaCadresStu jwClaCadresStu) {
		jwClaCadresStuService.deleteJwClaCadresStuByCondition(jwClaCadresStu);
	}

	@PostMapping("/updateStudentCadres")
	@ApiOperation(value = "修改学生职位")
	public void updateStudentCadres(@ApiParam(value = "修改学生职位") @RequestBody Map map) {
		jwClaCadresStuService.updateStudentCadres(map);
	}

    @PostMapping("/queryJwStudentByClassesId")
    @ApiOperation(value = "通过班级id查找学生职务信息")
    public List<JwClaCadresStu> queryJwStudentByClassesId(@RequestBody String id){
    	JwClaCadresStu jwClaCadresStu = new JwClaCadresStu();
    	jwClaCadresStu.setClassesId(id);
    	return jwClaCadresStuService.findJwClaCadresStuInfoListByClassesId(jwClaCadresStu);
    }
    
    @PostMapping("/findStuAndCadresByClassesIdAndName")
    @ApiOperation(value = "根据班级id查询学生和对应的班级职务", notes = "返回学生和职务信息列表")
    public List<JwClaCadresStu> findStuAndCadresByClassesIdAndName(
            @ApiParam(value = "学生信息")
            @RequestBody JwClaCadresStu jwClaCadresStu){
        return jwClaCadresStuService.findStuAndCadresByClassesIdAndName(jwClaCadresStu);
    }

	@GetMapping("/checkStudentIdentity/{studentId}")
	@ApiOperation(value = "通过学生id检查是否为班干部", notes = "返回班级的职位的学生对象")
	public long checkStudentIdentity(
			@ApiParam(value = "需要用到的id", required = true) @PathVariable String studentId) {
		return jwClaCadresStuService.checkStudentIdentity(studentId);
	}
}
