package com.yice.edu.cn.tap.controller.knowledge;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yice.edu.cn.common.constants.KnowledgeConstants;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.knowledge.JyKnowledge;
import com.yice.edu.cn.common.pojo.jy.knowledge.KnowkedgeTreeNodeViewVo;
import com.yice.edu.cn.tap.service.knowledge.JyKnowledgeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/jyKnowledge")
@Api(value = "/jyKnowledge", description = "知识点树题目模块")
public class JyKnowledgeController {
	@Autowired
	private JyKnowledgeService jyKnowledgeService;

//	@GetMapping("/lookJyKnowledgeById/{id}")
//	@ApiOperation(value = "通过id查找知识点详细信息", notes = "返回响应对象")
//	public ResponseJson lookJyKnowledgeById(
//			@ApiParam(value = "知识点id", required = true) @PathVariable String id) {
//		JyKnowledge jyKnowledge = jyKnowledgeService.findJyKnowledgeById(id);
//		return new ResponseJson(jyKnowledge);
//	}

	@GetMapping("/findSubjectByGradeId/{gradeId}")
	@ApiOperation(value = "根据年级查找对应科目", notes = "返回响应对象")
	public ResponseJson findSubjectByGradeId(
			@ApiParam(value = "年级id",required=true) @PathVariable String gradeId) {
		JyKnowledge jyKnowledge = new JyKnowledge();
		jyKnowledge.setParentId(gradeId);
		jyKnowledge.setLevel(KnowledgeConstants.TYPE.SUBJECT);
		jyKnowledge.setDel(Constant.DELSIGN.NORMAL);
		List<JyKnowledge> data = jyKnowledgeService.findJyKnowledgeListByCondition(jyKnowledge);
		long count = jyKnowledgeService.findJyKnowledgeCountByCondition(jyKnowledge);
		return new ResponseJson(data, count);
	}

	@GetMapping("/findGradeTreeDic/{id}")
	@ApiOperation(value = "根据知识点id或者科目id查询子知识点", notes = "返回响应对象")
	public ResponseJson findGradeTreeDic(@ApiParam(value = "知识点id或者科目id") @PathVariable String id) {
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
			viewVo.setLevel(jyKnowledgeModel.getLevel());
			treeNodeQueryQueryVoList.add(viewVo);
		}

		return new ResponseJson(treeNodeQueryQueryVoList);
	}

}
