package com.yice.edu.cn.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Page<T> {

	private long total;
	
	private List<T> content;
	
	private Integer pageSize;
	
	private Integer pageNo;

	public Page(long total, List<T> content) {
		this.total = total;
		this.content = content;
	}
}
