package com.yice.edu.cn.yed.controller.jy.knowledge;

import com.yice.edu.cn.common.constants.KnowledgeConstants;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jy.knowledge.JyKnowledge;
import com.yice.edu.cn.common.pojo.jy.knowledge.KnowkedgeTreeNodeViewVo;
import com.yice.edu.cn.common.pojo.jy.knowledge.TreeNodeQueryQueryVo;
import com.yice.edu.cn.common.pojo.jy.topics.Topics;
import com.yice.edu.cn.yed.service.general.dd.DdService;
import com.yice.edu.cn.yed.service.jy.knowledge.JyKnowledgeService;
import com.yice.edu.cn.yed.service.jy.topics.TopicsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.yice.edu.cn.yed.interceptor.LoginInterceptor.currentAdmin;
import static com.yice.edu.cn.yed.interceptor.LoginInterceptor.myId;

@RestController
@RequestMapping("/jyKnowledge")
@Api(value = "/jyKnowledge", description = "知识点树模块")
public class JyKnowledgeController {
	@Autowired
	private JyKnowledgeService jyKnowledgeService;
	@Autowired
	private DdService ddService;
	@Autowired
	private TopicsService topicsService;

	@PostMapping("/update/saveJyKnowledge")
	@ApiOperation(value = "保存知识点树对象", notes = "返回响应对象")
	public ResponseJson saveJyKnowledge(
			@ApiParam(value = "知识点树对象", required = true) @RequestBody JyKnowledge jyKnowledge) {
		jyKnowledge.setDel(Constant.DELSIGN.NORMAL);
		jyKnowledge.setType(jyKnowledge.getLevel());
		jyKnowledge.setLeaf(KnowledgeConstants.LEAF.IS_LEAF);
		JyKnowledge s = jyKnowledgeService.saveJyKnowledge(jyKnowledge);
		return new ResponseJson(s);
	}

	@GetMapping("/update/findJyKnowledgeById/{id}")
	@ApiOperation(value = "去更新页面,通过id查找知识点树", notes = "返回响应对象")
	public ResponseJson findJyKnowledgeById(
			@ApiParam(value = "去更新页面,需要用到的id", required = true) @PathVariable String id) {
		JyKnowledge jyKnowledge = jyKnowledgeService.findJyKnowledgeById(id);
		return new ResponseJson(jyKnowledge);
	}

	@PostMapping("/update/updateJyKnowledge")
	@ApiOperation(value = "修改知识点树对象", notes = "返回响应对象")
	public ResponseJson updateJyKnowledge(
			@ApiParam(value = "被修改的知识点树对象,对象属性不为空则修改", required = true) @RequestBody JyKnowledge jyKnowledge) {
		jyKnowledgeService.updateJyKnowledge(jyKnowledge);
		currentAdmin();
		return new ResponseJson();
	}

	@GetMapping("/look/lookJyKnowledgeById/{id}")
	@ApiOperation(value = "去查看页面,通过id查找知识点树", notes = "返回响应对象")
	public ResponseJson lookJyKnowledgeById(
			@ApiParam(value = "去查看页面,需要用到的id", required = true) @PathVariable String id) {
		JyKnowledge jyKnowledge = jyKnowledgeService.findJyKnowledgeById(id);
		return new ResponseJson(jyKnowledge);
	}

	@PostMapping("/findJyKnowledgesByCondition")
	@ApiOperation(value = "根据条件查找知识点树", notes = "返回响应对象")
	public ResponseJson findJyKnowledgesByCondition(
			@ApiParam(value = "属性不为空则作为条件查询") @RequestBody JyKnowledge jyKnowledge) {
		List<JyKnowledge> data = jyKnowledgeService.findJyKnowledgeListByCondition(jyKnowledge);
		long count = jyKnowledgeService.findJyKnowledgeCountByCondition(jyKnowledge);
		return new ResponseJson(data, count);
	}

	@PostMapping("/findOneJyKnowledgeByCondition")
	@ApiOperation(value = "根据条件查找单个知识点树,结果必须为单条数据", notes = "没有时返回空")
	public ResponseJson findOneJyKnowledgeByCondition(
			@ApiParam(value = "属性不为空则作为条件查询") @RequestBody JyKnowledge jyKnowledge) {
		JyKnowledge one = jyKnowledgeService.findOneJyKnowledgeByCondition(jyKnowledge);
		return new ResponseJson(one);
	}

	@GetMapping("/update/deleteJyKnowledge/{id}")
	@ApiOperation(value = "根据id逻辑删除", notes = "返回响应对象")
	public ResponseJson deleteJyKnowledge(@ApiParam(value = "被删除记录的id", required = true) @PathVariable String id) {
		JyKnowledge knowledge = new JyKnowledge();
		knowledge.setId(id);
		jyKnowledgeService.deleteLogicById(knowledge);
		return new ResponseJson();
	}

	@GetMapping("/deleteJyKnowledgeByCondition")
	@ApiOperation(value = "根据条件删除知识点树", notes = "返回响应对象")
	public ResponseJson deleteJyKnowledgeByCondition(
			@ApiParam(value = "被删除的知识点树对象,对象属性不为空则作为删除条件", required = true) @RequestBody JyKnowledge jyKnowledge) {
		jyKnowledgeService.deleteJyKnowledgeByCondition(jyKnowledge);
		return new ResponseJson();
	}

	@GetMapping("/manager/findSchoolTypeDic")
	@ApiOperation(value = "查询平台所拥有的年级(高中、初中、小学...)", notes = "返回响应对象")
	public ResponseJson findSchoolTypeDic() {
		Dd dd = new Dd();
		dd.setTypeId(Constant.DD_TYPE.SCHOOL_TYPE);
		List<Dd> data = ddService.findDdListByCondition(dd);

		return new ResponseJson(data);
	}

	@PostMapping("/manager/findGradeTreeDic")
	@ApiOperation(value = "查询年级下的树", notes = "返回响应对象")
	public ResponseJson findGradeTreeDic(@ApiParam(value = "属性不为空则作为条件查询") @RequestBody TreeNodeQueryQueryVo vo) {
		if (vo.getId() == null) {
			return new ResponseJson();
		}
		JyKnowledge jyKnowledge = new JyKnowledge();
		jyKnowledge.setParentId(vo.getId());
		jyKnowledge.setDel(Constant.DELSIGN.NORMAL);
		List<JyKnowledge> knowledgeList = jyKnowledgeService.findJyKnowledgeListByCondition(jyKnowledge);
		List<KnowkedgeTreeNodeViewVo> treeNodeQueryQueryVoList = new ArrayList<KnowkedgeTreeNodeViewVo>();
		for (JyKnowledge jyKnowledgeModel : knowledgeList) {
			KnowkedgeTreeNodeViewVo viewVo = new KnowkedgeTreeNodeViewVo();
			viewVo.setName(jyKnowledgeModel.getName());
			viewVo.setId(jyKnowledgeModel.getId());
			viewVo.setLeaf(Integer.parseInt(jyKnowledgeModel.getLeaf()) == 1 ? true : false);
			treeNodeQueryQueryVoList.add(viewVo);
		}

		return new ResponseJson(treeNodeQueryQueryVoList);
	}

	@GetMapping("/manager/findGradeBySchoolType/{id}")
	@ApiOperation(value = "根据学校类型查找年级", notes = "返回响应对象")
	public ResponseJson findGradeBySchoolType(@ApiParam(value = "属性不为空则作为条件查询") @PathVariable String id) {
		Dd dd = new Dd();
		dd.setLevelType(id);
		List<Dd> data = ddService.findDdListByCondition(dd);
		return new ResponseJson(data);
	}

	@PostMapping("/updateTopics/update/saveTopics")
	@ApiOperation(value = "保存题目表 mongodb 专用对象", notes = "返回响应对象")
	public ResponseJson saveTopics(@ApiParam(value = "题目表 mongodb 专用对象", required = true) @RequestBody Topics topics) {
		// 设置学校 和 当前人员
		topics.setCreateUser(myId());
		Topics s = topicsService.saveTopics(topics);
		return new ResponseJson(s);
	}

	@GetMapping("/updateTopics/update/findTopicsById/{id}")
	@ApiOperation(value = "去更新页面,通过id查找题目表 mongodb 专用", notes = "返回响应对象")
	public ResponseJson findTopicsById(@ApiParam(value = "去更新页面,需要用到的id", required = true) @PathVariable String id) {
		Topics topics = topicsService.findTopicsById(id);
		return new ResponseJson(topics);
	}

	@PostMapping("/updateTopics/update/updateTopics")
	@ApiOperation(value = "修改题目表 mongodb 专用对象", notes = "返回响应对象")
	public ResponseJson updateTopics(
			@ApiParam(value = "被修改的题目表 mongodb 专用对象,对象属性不为空则修改", required = true) @RequestBody Topics topics) {
		topicsService.updateTopics(topics);
		return new ResponseJson();
	}

	@GetMapping("/deleteTopics/{id}")
	@ApiOperation(value = "根据id删除", notes = "返回响应对象")
	public ResponseJson deleteTopics(@ApiParam(value = "被删除记录的id", required = true) @PathVariable String id) {
		topicsService.deleteTopics(id);
		return new ResponseJson();
	}

	@PostMapping("/ignore/findTopicsListByCondition4Muti")
	@ApiOperation(value = "根据条件查找题目列表（题目内容，题目类型等）", notes = "返回响应对象,不包含总条数")
	public ResponseJson findTopicsListByCondition4Muti(@ApiParam(value = "属性不为空则作为条件查询") @RequestBody Topics topics) {
		topics.setPager(Optional.ofNullable(topics.getPager()).orElse(new Pager().setPaging(false)).setLike("content"));
		List<Topics> data = topicsService.findTopicsListByCondition4Muti(topics);
		return new ResponseJson(data);
	}

    @PostMapping("/manager/upload/uploadKnowledge")
    public ResponseJson uploadStudent(MultipartFile file) {
        try {
        	Map<String, Object> map = jyKnowledgeService.uploadJyKnowledge(file);
        	if(!(boolean)map.get("success")) {
        		return new ResponseJson(false,(String)map.get("msg"));
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return new ResponseJson();
    }

}
