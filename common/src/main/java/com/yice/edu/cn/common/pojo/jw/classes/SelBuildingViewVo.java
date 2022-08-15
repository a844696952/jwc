package com.yice.edu.cn.common.pojo.jw.classes;

import java.util.List;

import lombok.Data;

@Data
public class SelBuildingViewVo {
	
	private String value;
	
	private String label;
	
	private boolean disabled = false;
	
	private Long count;//场地容量
	
	private String typeName;//场地类型名称
	
	private List<SelBuildingViewVo> children;

}
