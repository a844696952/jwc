package com.yice.edu.cn.common.pojo.xw.dj.partyMember;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yice.edu.cn.common.easypoiplus.LockExcelColumn;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;


/**
 * @author dengfengfeng
 * @apiNote easypoi无法创建内部类，只能创建一个独立的类 java.lang.InstantiationException:
 */
@Data
@NoArgsConstructor
public class FixedEntity {

    @Excel(name = "教师姓名", orderNum = "1", width = 20)
    @LockExcelColumn
    String teacherName;
    @Excel(name = "性别", orderNum = "2", replace = {"男_4", "女_5", "_null"})
    @LockExcelColumn
    String sex;
    @Excel(name = "手机号", orderNum = "3", width = 20)
    @LockExcelColumn
    String tel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FixedEntity that = (FixedEntity) o;
        return Objects.equals(teacherName, that.teacherName) &&
                Objects.equals(sex, that.sex) &&
                Objects.equals(tel, that.tel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teacherName, sex, tel);
    }
}



