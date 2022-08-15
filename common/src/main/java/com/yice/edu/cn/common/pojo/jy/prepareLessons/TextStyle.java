package com.yice.edu.cn.common.pojo.jy.prepareLessons;

import lombok.Data;

@Data
public class TextStyle implements Cloneable {

	private boolean isItalic = false;
	private boolean isBold = false;
	private boolean isUnderline = false;
	private boolean isMathFormula = false;
	private String color;
	private int fontSize = 16;
	private String fontFamily="宋体";
	

	@Override
	public TextStyle clone() throws CloneNotSupportedException {
		TextStyle newTextStyle = (TextStyle) super.clone();
		return newTextStyle;
	}
	
}
