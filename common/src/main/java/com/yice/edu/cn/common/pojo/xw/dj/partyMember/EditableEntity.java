package com.yice.edu.cn.common.pojo.xw.dj.partyMember;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dengfengfeng
 * @apiNote easypoi无法创建内部类，只能创建一个独立的类 java.lang.InstantiationException:
 */
@Data
@NoArgsConstructor
public class EditableEntity {

    //2019-06-04将其从不可编辑移到可编辑区域
    @Excel(name = "党员类别名称", orderNum = "4", width = 25)
    String partyTypeName;

    @Excel(name = "党支部委员会名称", orderNum = "5", width = 30)
    String partyCommitteeName;

    @Excel(name = "党支部名称", orderNum = "6", width = 30)
    String partyBranchName;

    @Excel(name = "党员职务名称", orderNum = "7", width = 30)
    String partyDutiesName;
    @Excel(name = "申请入党日期           (格式yyyy-mm-dd)", orderNum = "8", width = 20, format = "yyyy-MM-dd", databaseFormat = "yyyy-MM-dd")
    String applyTime;
    @Excel(name = "转正日期                  (格式yyyy-mm-dd)", orderNum = "9", width = 20, format = "yyyy-MM-dd", databaseFormat = "yyyy-MM-dd")
    String workerTime;
    @Excel(name = "档案情况", orderNum = "10", width = 20)
    String archivalSituationName;
    @Excel(name = "档案情况说明(档案情况为'其他'的时候填写)", orderNum = "11", width = 50)
    String archivalSituationRemark;


}