package com.yice.edu.cn.common.pojo.jy.knowledge;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TreeNodeQueryQueryVo {

	/**
	 * 当前点击的树节点id
	 */
	@ApiModelProperty(value = "id",dataType = "String")
	private String id;
    /**
     * 当前点击树节点的级别
     */
	@ApiModelProperty(value = "树级别",dataType = "String")
	private String level;
	/**
	 * 当前点击树节点的类型
	 */
	@ApiModelProperty(value = "类型(1.年级 2.科目 3.知识点)",dataType = "String")
	private String type;
}
