package com.yice.edu.cn.common.pojo.jy.knowledge;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class UploadKnowledgeAndGradeMapVo {

	List<UploadKnowledgeVo> jyKnowledgesVoList;
	
	Map<String,String> gradeMap;
}
