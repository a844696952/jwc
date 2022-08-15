package com.yice.edu.cn.common.pojo.xq.AnalyseClassScore;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class WebIndexStudyAnalyseVo {
	
	private List<Map<String, Object>> studentAnalyseList;
	
	private List<AnalyseClassScoreAll> analyseClassScoreAll;

}
