package com.yice.edu.cn.common.pojo.xw.dj.partyMember;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import com.yice.edu.cn.common.easypoiplus.ExcelPlus;
import com.yice.edu.cn.common.easypoiplus.LockExcelColumn;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;


/**
 * @author dengfengfeng
 */
@Data
public class XwPartyMemberExcel implements IExcelModel, IExcelDataModel, ExcelPlus {


    @Excel(name="党员管理编号(勿动)",isColumnHidden = true,orderNum = "0")
    @LockExcelColumn
    private String id;

    @ExcelEntity(name = "不可编辑(编辑无效)",show = true)
    private FixedEntity fixedEntity = new FixedEntity();

    @ExcelEntity(name = "可编辑区(全量更新)",show = true)
    private EditableEntity editableEntity = new EditableEntity();

    @ExcelEntity(name = "隐藏字段区域",show = true)
    private HideEntity hideEntity = new HideEntity();

    private String errorMsg;

    private int rowNum;

    @Override
    public void setRowNum(int rowNum) {
        this.rowNum = rowNum+1;
    }

    public boolean exist(){
        return StringUtils.isNotBlank(this.id);
    }

    public void keepTeacher(Teacher teacher){

        this.fixedEntity.sex = teacher.getSex();
        this.fixedEntity.teacherName = teacher.getName();
        this.fixedEntity.tel = teacher.getTel();
        //2019-06-04将党员类别移到可编辑区域
//        this.fixedEntity.partyTypeName = teacher.getPoliticalFace();
        this.hideEntity.teacherId = teacher.getId();
        this.hideEntity.schoolId = teacher.getSchoolId();
    }

    public String teacherId(){
        return this.hideEntity.teacherId;
    }

    public String archivalSituationName(){
        return this.editableEntity.archivalSituationName;
    }

    public void setArchivalSituationName(String archivalSituationName){
        this.editableEntity.archivalSituationName = archivalSituationName;
    }

    public void setArchivalSituationId(String archivalSituationId){
        this.hideEntity.archivalSituationId = archivalSituationId;
    }

    public String getPartyTypeName(){
        return this.editableEntity.partyTypeName ;
    }

    public void setPartyTypeName(String partyTypeName){
        this.editableEntity.partyTypeName = partyTypeName;
    }

    public void setPartyTypeId(String partyTypeId){
        this.hideEntity.partyTypeId = partyTypeId;
    }

    public String getPartyCommitteeName(){
        return this.editableEntity.partyCommitteeName ;
    }

    public void setPartyCommitteeName(String partyCommitteeName){
        this.editableEntity.partyCommitteeName = partyCommitteeName;
    }

    public void setPartyCommitteeId(String partyCommitteeId){
        this.hideEntity.partyCommitteeId = partyCommitteeId;
    }

    public String getPartyBranchName(){
        return this.editableEntity.partyBranchName ;
    }

    public void setPartyBranchName(String partyBranchName){
        this.editableEntity.partyBranchName = partyBranchName;
    }

    public void setPartyBranchId(String partyBranchId){
        this.hideEntity.partyBranchId = partyBranchId;
    }

    public String getPartyDutiesName(){
        return this.editableEntity.partyDutiesName ;
    }

    public void setPartyDutiesName(String dutiesName){
        this.editableEntity.partyDutiesName = dutiesName;
    }

    public void setPartyDutiesId(String dutiesId){
        this.hideEntity.partyDutiesId = dutiesId;
    }

    public XwPartyMemberExcel(){

    }

    public XwPartyMember toXwPartyMemberForInsert(String createTime, String id){
        XwPartyMember xwPartyMember = getXwPartyMemberTeacher(id);
        //2019-06-04将党员类别移到可编辑区域
//        xwPartyMember.setPartyTypeId(this.hideEntity.partyTypeId);
//        xwPartyMember.setPartyTypeName(this.editableEntity.partyTypeName);
        xwPartyMember.setCreateTime(createTime);
        setPartyMember(xwPartyMember);
        return xwPartyMember;
    }

    public XwPartyMember toXwPartyMemberForUpdate(String updateTime, String id){
        XwPartyMember xwPartyMember = getXwPartyMemberTeacher(id);
        //2019-06-04将党员类别移到可编辑区域
//        xwPartyMember.setPartyTypeId(this.hideEntity.partyTypeId);
//        xwPartyMember.setPartyTypeName(this.editableEntity.partyTypeName);
        xwPartyMember.setUpdateTime(updateTime);
        setPartyMember(xwPartyMember);
        return xwPartyMember;
    }

    private XwPartyMember getXwPartyMemberTeacher(String id) {
        XwPartyMember xwPartyMember = new XwPartyMember();
        xwPartyMember.setId(id);
        xwPartyMember.setSchoolId(this.hideEntity.schoolId);
        xwPartyMember.setTeacherId(this.teacherId());
        xwPartyMember.setTeacherName(this.fixedEntity.teacherName);
        xwPartyMember.setSex(this.fixedEntity.sex);
        xwPartyMember.setTel(this.fixedEntity.tel);
        return xwPartyMember;
    }

    public XwPartyMember toXwPartyMember(String updateTime){
        XwPartyMember xwPartyMember = new XwPartyMember();
        setPartyMember(xwPartyMember);
        xwPartyMember.setId(this.id);
        xwPartyMember.setUpdateTime(updateTime);
        return xwPartyMember;
    }

    private Integer computeIntact(XwPartyMember xwPartyMember){
        return StringUtils.isAnyBlank(
                xwPartyMember.getPartyCommitteeName(),
                xwPartyMember.getPartyBranchName(),
                xwPartyMember.getPartyDutiesName(),
                xwPartyMember.getApplyTime(),
                xwPartyMember.getWorkerTime(),
                //2019-06-04将党员类别移到可编辑区域
                xwPartyMember.getPartyTypeName(),
                xwPartyMember.getArchivalSituationName()) ||
                ("其他".equals(xwPartyMember.getArchivalSituationName()) && StringUtils.isBlank(xwPartyMember.getArchivalSituationRemark()))?
                2:1;
    }

    private void computeAndSetIntact(XwPartyMember xwPartyMember){
        xwPartyMember.setIntact(computeIntact(xwPartyMember));
    }

    private void setPartyMember(XwPartyMember xwPartyMember) {
        xwPartyMember.setPartyCommitteeId(this.hideEntity.partyCommitteeId);
        xwPartyMember.setPartyCommitteeName(this.editableEntity.partyCommitteeName);
        xwPartyMember.setPartyBranchId(this.hideEntity.partyBranchId);
        xwPartyMember.setPartyBranchName(this.editableEntity.partyBranchName);
        xwPartyMember.setPartyDutiesId(this.hideEntity.partyDutiesId);
        xwPartyMember.setPartyDutiesName(this.editableEntity.partyDutiesName);
        xwPartyMember.setApplyTime(this.editableEntity.applyTime);
        xwPartyMember.setWorkerTime(this.editableEntity.workerTime);
        xwPartyMember.setArchivalSituationId(this.hideEntity.archivalSituationId);
        xwPartyMember.setArchivalSituationName(this.editableEntity.archivalSituationName);
        xwPartyMember.setArchivalSituationRemark(this.editableEntity.archivalSituationRemark);
        //2019-06-04将党员类别移到可编辑区域
        xwPartyMember.setPartyTypeName(this.editableEntity.partyTypeName);
        xwPartyMember.setPartyTypeId(this.hideEntity.partyTypeId);
        xwPartyMember.setIntact(computeIntact(xwPartyMember));
    }

    public XwPartyMemberExcel(Teacher teacher){
        //2019-06-04将党员类别移到可编辑区域
//        this.editableEntity.partyTypeName = teacher.getPoliticalFace();

        this.fixedEntity.teacherName = teacher.getName();
        this.fixedEntity.sex = teacher.getSex();
        this.fixedEntity.tel = teacher.getTel();

        this.hideEntity.schoolId = teacher.getSchoolId();
        this.hideEntity.teacherId = teacher.getId();
    }

    public XwPartyMemberExcel(XwPartyMember xwPartyMember){
        this.id = xwPartyMember.getId();

        this.fixedEntity.teacherName = xwPartyMember.getTeacherName();
        this.fixedEntity.sex = xwPartyMember.getSex();
        this.fixedEntity.tel = xwPartyMember.getTel();


        this.editableEntity.applyTime = xwPartyMember.getApplyTime();
        this.editableEntity.workerTime = xwPartyMember.getWorkerTime();

        this.editableEntity.archivalSituationName = xwPartyMember.getArchivalSituationName();
        this.editableEntity.archivalSituationRemark = xwPartyMember.getArchivalSituationRemark();

        this.editableEntity.partyBranchName = xwPartyMember.getPartyBranchName();

        this.editableEntity.partyCommitteeName = xwPartyMember.getPartyCommitteeName();

        this.editableEntity.partyDutiesName = xwPartyMember.getPartyDutiesName();

        //2019-06-04将党员类别移到可编辑区域
        this.editableEntity.partyTypeName = xwPartyMember.getPartyTypeName();

        this.hideEntity.schoolId = xwPartyMember.getSchoolId();
        this.hideEntity.teacherId = xwPartyMember.getTeacherId();
        this.hideEntity.createTime = xwPartyMember.getCreateTime();
        this.hideEntity.archivalSituationId = xwPartyMember.getArchivalSituationId();

        this.hideEntity.updateTime = xwPartyMember.getUpdateTime();
        this.hideEntity.partyBranchId = xwPartyMember.getPartyBranchId();

        this.hideEntity.partyCommitteeId = xwPartyMember.getPartyCommitteeId();
        this.hideEntity.partyDutiesId = xwPartyMember.getPartyDutiesId();

        this.hideEntity.partyTypeId = xwPartyMember.getPartyTypeId();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        XwPartyMemberExcel that = (XwPartyMemberExcel) o;
        return Objects.equals(fixedEntity, that.fixedEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fixedEntity);
    }

    @Override
    public int dataStartRowIndex() {
        return 3;
    }
}
