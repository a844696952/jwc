package com.yice.edu.cn.common.util;

import org.apache.poi.util.StringUtil;

/**
 * 字符串操作工具类
 * @author dengfengfeng
 * @date 2018-10-31
 */
public class StringUtils {
	
	/**
	 * 标志
	 */
	public static final String SUCCESS = "success";
	public static final String UNSUCCESS = "unsuccess";
	
	private static final char UDERLINE = '_';

	/**
	 * 将多个字符串用下划线拼接
	 * @param ss
	 * @return
	 */
    public static String linkStrWithUnderline(String ...ss) {
        StringBuffer sb = new StringBuffer();
        for(String str :ss){
            sb.append(str);
            sb.append(UDERLINE);
        }
        return sb
                .toString()
                .substring(0,sb.toString().lastIndexOf('_'));
    }

	//转义特殊字符
	public static String specialCharacterConvert(String context){
		if(context!=null){
			String[] contexts = context.split("");
			for (int i = 0;i<contexts.length;i++){
				String s = contexts[i];
				if(s.equals("$")
						|| s.equals("(")
						|| s.equals(")")
						|| s.equals("*")
						|| s.equals("+")
						|| s.equals(".")
						|| s.equals("[")
						|| s.equals("?")
						|| s.equals("\\")
						|| s.equals("^")
						|| s.equals("{")
						|| s.equals("|")){
					contexts[i] = "\\"+contexts[i];
				}
			}
			context = StringUtil.join(contexts,"");
		}

		return context;
	}
}
