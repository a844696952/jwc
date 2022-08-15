package com.yice.edu.cn.jy.wordGenerator;

import java.util.stream.IntStream;

/**
 * 
* @ClassName: HexColorUtils  
* @Description: rgb转16进制格式颜色  rgb(r,g,b) -> “#xxxxxx” 
* @author xuchang  
* @date 2018年11月15日
 */
public class HexColorUtils {
	
	//16进制对应的编码
	private static String[] code= {"A","B","C","D","E","F"};
	private static Integer[] value= {10,11,12,13,14,15};
	
		
	/**
	 * 
	* @Title: convertRGBToHex  
	* @Description: 将rgb色彩值转成16进制代码  
	* @param @param r
	* @param @param g
	* @param @param b
	* @return String    hex格式颜色  
	* @throws
	 */
	public static String convertRGBToHex(int r, int g, int b) {
		int[] rgb= {r,g,b};
		
        StringBuilder sb=new StringBuilder("");
        for(int  i=0;i<3;i++) {
        	final int prefix = rgb[i] / 16;
        	final int suffix = rgb[i] % 16;
            
            IntStream.rangeClosed(0, 5)
                .filter(n->prefix==value[n])
                .forEach(n->sb.append(code[n]));
            if(sb.length()!=i*2+1) sb.append(prefix);
            
            IntStream.rangeClosed(0, 5)
            	.filter(n->suffix==value[n])
            	.forEach(n->sb.append(code[n]));
            if(sb.length()!=i*2+2) sb.append(suffix);
        }
        
        
        return sb.toString();
		
	}
	
	
}
