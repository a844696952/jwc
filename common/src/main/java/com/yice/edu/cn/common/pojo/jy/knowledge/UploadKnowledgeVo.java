package com.yice.edu.cn.common.pojo.jy.knowledge;

import java.io.Serializable;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class UploadKnowledgeVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8987325445233795389L;
	/**
	 * 年级名称
	 */
	@Excel(name="年级")
	public String gradeName;
	/**
	 * 科目
	 */
	@Excel(name="科目")
	public String subjectName;
	/**
	 * 知识点1
	 */
	@Excel(name="知识点1")
	public String knowledgeF;
	/**
	 * 知识点2
	 */
	@Excel(name="知识点2")
	public String knowledgeS;
	/**
	 * 知识点3
	 */
	@Excel(name="知识点3")
	public String knowledgeT;

}
