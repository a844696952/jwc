package com.yice.edu.cn.common.pojo.oa.common;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SchoolProcessListView {
	private String key;
	private List<Object> value = new ArrayList<Object>();

}
