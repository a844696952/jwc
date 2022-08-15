package com.yice.edu.cn.common.pojo;

import java.util.List;

import lombok.Data;

/**
 * 
 * <p>
 * 级联结构
 * </p>
 * @author dengfengfeng
 * @since 2018年12月26日
 * @param <T>
 */
@Data
public class Cascade {

	private String id;
	private String label;
	private String value;
	private List<Cascade> children;
}
