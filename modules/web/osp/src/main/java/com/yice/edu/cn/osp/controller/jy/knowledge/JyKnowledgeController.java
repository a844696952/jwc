package com.yice.edu.cn.osp.controller.jy.knowledge;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.yice.edu.cn.common.pojo.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yice.edu.cn.common.constants.CommonClassConstants;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jy.knowledge.JyKnowledge;
import com.yice.edu.cn.common.pojo.jy.knowledge.KnowkedgeTreeNodeViewVo;
import com.yice.edu.cn.common.pojo.jy.knowledge.TreeNodeQueryQueryVo;
import com.yice.edu.cn.common.pojo.jy.topics.Topics;
import com.yice.edu.cn.osp.service.dd.DdService;
import com.yice.edu.cn.osp.service.jy.knowledge.JyKnowledgeService;
import com.yice.edu.cn.osp.service.jy.topics.TopicsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/jyKnowledge")
@Api(value = "/jyKnowledge", description = "知识点树题目模块")
public class JyKnowledgeController {
	@Autowired
	private JyKnowledgeService jyKnowledgeService;
	@Autowired
	private DdService ddService;
	@Autowired
	private TopicsService topicsService;

	@GetMapping("/system/lookJyKnowledgeById/{id}")
	@ApiOperation(value = "去查看页面,通过id查找知识点树", notes = "返回响应对象")
	public ResponseJson lookJyKnowledgeById(
			@ApiParam(value = "去查看页面,需要用到的id", required = true) @PathVariable String id) {
		JyKnowledge jyKnowledge = jyKnowledgeService.findJyKnowledgeById(id);
		return new ResponseJson(jyKnowledge);
	}

	@PostMapping("/system/findJyKnowledgesByCondition")
	@ApiOperation(value = "根据条件查找知识点树", notes = "返回响应对象")
	public ResponseJson findJyKnowledgesByCondition(
			@ApiParam(value = "属性不为空则作为条件查询") @RequestBody JyKnowledge jyKnowledge) {
		List<JyKnowledge> data = jyKnowledgeService.findJyKnowledgeListByCondition(jyKnowledge);
		long count = jyKnowledgeService.findJyKnowledgeCountByCondition(jyKnowledge);
		return new ResponseJson(data, count);
	}

	@GetMapping("/system/findSchoolTypeDic")
	@ApiOperation(value = "查询平台所拥有的年级(高中、初中、小学...)", notes = "返回响应对象")
	public ResponseJson findSchoolTypeDic() {
		Dd dd = new Dd();
		dd.setTypeId(Constant.DD_TYPE.SCHOOL_TYPE);
		List<Dd> data = ddService.findDdListByCondition(dd);

		return new ResponseJson(data);
	}

	@GetMapping("/ignore/findGradeTreeDic/{id}")
	@ApiOperation(value = "查询年级下的树", notes = "返回响应对象")
	public ResponseJson findGradeTreeDic(@PathVariable String id) {
		JyKnowledge jyKnowledge = new JyKnowledge();
		jyKnowledge.setParentId(id);
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

	@GetMapping("/system/findGradeBySchoolType/{id}")
	@ApiOperation(value = "根据学校类型查找年级", notes = "返回响应对象")
	public ResponseJson findGradeBySchoolType(@ApiParam(value = "属性不为空则作为条件查询") @PathVariable String id) {
		Dd dd = new Dd();
		dd.setLevelType(id);
		List<Dd> data = ddService.findDdListByCondition(dd);
		return new ResponseJson(data);
	}

	@GetMapping("/system/findTopicsById/{id}")
	@ApiOperation(value = "去更新页面,通过id查找题目表 mongodb 专用", notes = "返回响应对象")
	public ResponseJson findTopicsById(@ApiParam(value = "去更新页面,需要用到的id", required = true) @PathVariable String id) {
		Topics topics = topicsService.findTopicsById(id);
		return new ResponseJson(topics);
	}
	
	@PostMapping("/system/findTopicsListByCondition4Muti")
	@ApiOperation(value = "根据条件查找题目列表（题目内容，题目类型等）", notes = "返回响应对象,不包含总条数")
	public ResponseJson findTopicsListByCondition4Muti(@ApiParam(value = "属性不为空则作为条件查询") @RequestBody Topics topics) {
		topics.setPager(Optional.ofNullable(topics.getPager()).orElse(new Pager().setPaging(false)).setLike("content"));
		List<Topics> data = topicsService.findTopicsListByCondition4Muti(topics);
		return new ResponseJson(data);
	}
	
}
