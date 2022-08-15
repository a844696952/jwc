package com.yice.edu.cn.common.pojo.jy.homework;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ClassVo implements Comparable{
    private String gradeId;
    private String gradeName;
    private String className;
    private String classFullName;
    private String classId;
    private List<CourseVo> list;

    @Override
    public int compareTo(Object o) {
        if(Integer.valueOf(gradeId) >Integer.valueOf(((ClassVo)o).getGradeId())) {
            return 1;
        }else if(Integer.valueOf(gradeId)< Integer.valueOf(((ClassVo)o).getGradeId())) {
            return -1;
        }else {
            if(Integer.valueOf(className)>Integer.valueOf(((ClassVo)o).getClassName())){
                return 1;
            }else if(Integer.valueOf(className)<Integer.valueOf(((ClassVo)o).getClassName())){
                return -1;
            }else{
                return 0;
            }
        }
    }
}
