package com.yice.edu.cn.common.pojo.jy.homework;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Objects;

@Data
@Builder
public class CourseVo {
    private String courseName;
    private String courseId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseVo courseVo = (CourseVo) o;
        return Objects.equals(courseName, courseVo.courseName) &&
                Objects.equals(courseId, courseVo.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseName, courseId);
    }
}
