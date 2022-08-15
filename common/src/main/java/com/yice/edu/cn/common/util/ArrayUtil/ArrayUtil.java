package com.yice.edu.cn.common.util.ArrayUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayUtil {
    public static void main(String[] args) {
        String[] a = new String[] {"a","b","c"};
        String[] b = new String[] {"b","d"};
        List<String> oldD = Stream.of(a).collect(Collectors.toList());
        List<String> newD = Stream.of(b).collect(Collectors.toList());
        Map<String,Object> map =compareArry(oldD,newD);
        List<String> ls = (List<String>) map.get("delete_arry");
        //ls.forEach(System.out::println);
        List<String> ls1 = (List<String>) map.get("add_arry");
        ls1.forEach(System.out::println);
    }
    /**
     * @param t1旧数据
     * @param t2新数据
     * @return
     */
    public static Map<String, Object> compareArry(List<String> t1, List<String> t2) {
        /**
         * 集合A (新的数据有，旧的数据没有，即为要添加的数据)
         */
        List<String> listA=new ArrayList<String>();
        for (String str1:t2) {//遍历新的数组元素
            if (!t1.contains(str1)) {//旧的数组中不包含新的元素（即为要添加的元素）
                listA.add(str1);
            }
        }

        /**
         * 集合B (旧的数据中有，新的数据没有，即为要删除的数据)
         */
        List<String> listB = new ArrayList<String>();
        for(String str2:t1){//遍历旧的数组元素
            if( !t2.contains(str2)){//新的数组中不包含旧的数组（即为要删除的元素）
                listB.add(str2);
            }
        }

        /**
         * 集合C (旧的数据中有，新的数据有，即为共同的数据)
         */
        List<String> listC = new ArrayList<String>();
        for(String str3:t1){//遍历旧的数组元素
            if( t2.contains(str3)){//新的数组中包含旧的数组（即为共同的元素）
                listC.add(str3);
            }
        }


        Map<String, Object> map=new HashMap<String, Object>();
        map.put("add_arry", listA);
        map.put("delete_arry", listB);
        map.put("common_date", listC);
        return map;
    }
}
