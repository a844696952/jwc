package com.yice.edu.cn.common.util.object;

import com.yice.edu.cn.common.pojo.jw.department.Department;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ObjectKit {

    /**
     * 用于在java端构建树结构的数据使用，要求有字段id和parentId,和children字段
     * @param srcList
     * @param parentId
     * @param <T>
     * @return
     */
    public static <T> List<T> buildTree(List<T> srcList,String parentId){
        List<T> list = findTByParentId(srcList, parentId);
        try {
            for (T t : list) {
                Class<?> clazz = t.getClass();
                Method idMethod = clazz.getDeclaredMethod("getId");
                Method childrenMethod = clazz.getDeclaredMethod("setChildren",List.class);
                List<T> children = buildTree(srcList, idMethod.invoke(t).toString());
                if(children.size()!=0){
                    childrenMethod.invoke(t,children);
                }
            }
            return list;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }
    private static <T> List<T> findTByParentId(List<T> srcList,String parentId)  {
        List<T> result = new ArrayList<>();

        try {
            for (T t : srcList) {
                Class<?> clazz = t.getClass();
                Method parentIdMethod = clazz.getMethod("getParentId");
                if(parentIdMethod.invoke(t).equals(parentId)){
                    result.add(t);
                }
            }
            return result;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 必须把pom.xml里的mongodb依赖provided去掉
     * @param args
     * @throws NoSuchMethodException
     */
    public static void main(String[] args) throws NoSuchMethodException {
        List<Department> srcList = new ArrayList<>();
        Department d1 = new Department();
        Department d2 = new Department();
        Department d3 = new Department();
        Department d4 = new Department();
        Department d5 = new Department();
        d1.setId("1");
        d1.setParentId("-1");
        d1.setName("校长办公室");

        d2.setId("2");
        d2.setParentId("-1");
        d2.setName("院长办公室");

        d3.setId("3");
        d3.setParentId("1");
        d3.setName("总务处");

        d4.setId("4");
        d4.setParentId("1");
        d4.setName("校务处");

        d5.setId("5");
        d5.setParentId("2");
        d5.setName("教务处");
        srcList.add(d1);
        srcList.add(d2);
        srcList.add(d3);
        srcList.add(d4);
        srcList.add(d5);
        List<Department> departments = buildTree(srcList, "-1");
        System.out.println(departments);
    }

}
