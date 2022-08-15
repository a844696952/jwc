package com.yice.edu.cn.common.pojo.xw.dj.partyMember;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yice.edu.cn.common.easypoiplus.LockExcelColumn;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dengfengfeng
 * @apiNote easypoi无法创建内部类，只能创建一个独立的类 java.lang.InstantiationException:
 */

@Data
@NoArgsConstructor
public class HideEntity {


    @Excel(name = "学校编号", isColumnHidden = true, orderNum = "12")
    @LockExcelColumn
    String schoolId;
    @Excel(name = "讲师编号", isColumnHidden = true, orderNum = "13")
    @LockExcelColumn
    String teacherId;

    @Excel(name = "党支部委员会编号", isColumnHidden = true, orderNum = "14")
    @LockExcelColumn
    String partyCommitteeId;

    @Excel(name = "党支部编号", isColumnHidden = true, orderNum = "15")
    @LockExcelColumn
    String partyBranchId;

    @Excel(name = "党员职务编号", isColumnHidden = true, orderNum = "16")
    @LockExcelColumn
    String partyDutiesId;

    @Excel(name = "创建时间", isColumnHidden = true, orderNum = "17", databaseFormat = "yyyy-MM-dd")
    @LockExcelColumn
    String createTime;
    @Excel(name = "修改时间", isColumnHidden = true, orderNum = "18", databaseFormat = "yyyy-MM-dd")
    @LockExcelColumn
    String updateTime;


    @Excel(name = "党员类别id", isColumnHidden = true, orderNum = "19")
    @LockExcelColumn
    String partyTypeId;

    @Excel(name = "档案情况id", isColumnHidden = true, orderNum = "20")
    @LockExcelColumn
    String archivalSituationId;
}
