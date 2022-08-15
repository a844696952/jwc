package com.yice.edu.cn.common.pojo.xw.dj.partyMember;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.xw.dj.partyCommittee.XwPartyCommittee;
import com.yice.edu.cn.common.pojo.xw.dj.partyDuty.XwPartyDuty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * 党员信息处理器
 * @author dengfengfeng
 * @version 1.0.1 将其从osp中抽到common里以供其他服务可以使用
 */
@Slf4j
public class PartyMemberHandler implements IExcelVerifyHandler<XwPartyMemberExcel> {

    private final List<Dd> daList;
    private final List<Dd> dyTypeList;
    private final List<XwPartyCommittee> committeeList;
    private final List<XwPartyCommittee> branchList;
    private final List<XwPartyDuty> dutyList;
    private final List<String> memberTeacherIdList;
    private XwPartyMemberExcel obj;
    private ExcelVerifyHandlerResult result;


    /**
     * @param daList
     * @param dyTypeList
     * @param committeeList
     * @param branchList
     * @param dutyList
     * @param memberTeacherIdList
     */
    public PartyMemberHandler(
            List<Dd> daList,
            List<Dd> dyTypeList,
            List<XwPartyCommittee> committeeList,
            List<XwPartyCommittee> branchList,
            List<XwPartyDuty> dutyList,
            List<String> memberTeacherIdList) {
        this.committeeList = committeeList;
        this.branchList = branchList;
        this.dutyList = dutyList;
        this.daList = daList;
        this.dyTypeList = dyTypeList;
        this.memberTeacherIdList = memberTeacherIdList;

    }

    @Override
    public ExcelVerifyHandlerResult verifyHandler(XwPartyMemberExcel obj) {
        // 为隐藏的id赋值，若是匹配不到，则提示excel数据有误，，重新下载模板
        this.obj = obj;
        result = new ExcelVerifyHandlerResult();
//            verifySaved(obj,result);
//            verifyDyType(obj,result);
//            verifyCommittee(obj,result);
//            verifyBranch(obj,result);
//            verifyDuty(obj,result);
//            verifyDa(obj,result);
        //无参重构

        try{
//            verifySaved();
            verifyDyType();
            verifyCommittee();
            verifyBranch();
            verifyDuty();
            verifyDa();
        } catch (Exception e){
            log.error("导入党员excel错误",e);
            throw new IllegalArgumentException("模板可能有更新，请重新下载");
        }



        if (StringUtils.isBlank(result.getMsg())) {
            result.setMsg(null);
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * 验证教师是否已经录入过
     *
     * @param obj
     * @param result
     */
    private void verifySaved(XwPartyMemberExcel obj, ExcelVerifyHandlerResult result) {
        if (StringUtils.isNotBlank(obj.teacherId())) {
            if (memberTeacherIdList.contains(obj.teacherId())) {
                appendMsg("此教师已录入,不可重复录入！", result);
            }
        } else {
            appendMsg("教师id不可为空！请重新下载模板", result);
        }
    }

    /**
     * 验证教师是否已经录入过
     */
    private void verifySaved() {
        verifySaved(obj, result);
    }


    /**
     * 验证党职务
     *
     * @param obj
     * @param result
     */
    private void verifyDuty(XwPartyMemberExcel obj, ExcelVerifyHandlerResult result) {
        if (StringUtils.isNotBlank(obj.getPartyDutiesName())) {
            Optional<XwPartyDuty> anyDuty = dutyList
                    .stream()
                    .filter(d -> obj.getPartyDutiesName().equals(d.getName()))
                    .findFirst();
            if (anyDuty.isPresent()) {
                XwPartyDuty duty = anyDuty.get();
                obj.setPartyDutiesId(duty.getId());
                obj.setPartyDutiesName(duty.getName());
            } else {
                appendMsg("党职务填写有误", result);
            }
        }
    }

    /**
     * 验证党职务
     */
    private void verifyDuty() {
        verifyDuty(obj, result);
    }

    /**
     * 验证党支部
     *
     * @param obj
     * @param result
     */
    private void verifyBranch(XwPartyMemberExcel obj, ExcelVerifyHandlerResult result) {
        if (StringUtils.isNotBlank(obj.getPartyBranchName())) {
            Optional<XwPartyCommittee> anyBranch = branchList
                    .stream()
                    .filter(b -> b.getParentName().equals(obj.getPartyCommitteeName()) && obj.getPartyBranchName().equals(b.getName()))
                    .findFirst();
            if (anyBranch.isPresent()) {
                XwPartyCommittee branch = anyBranch.get();
                obj.setPartyBranchId(branch.getId());
                obj.setPartyBranchName(branch.getName());
            } else {
                appendMsg("党支部填写有误", result);
            }
        }
    }

    /**
     * 验证党支部
     */
    private void verifyBranch() {
        verifyBranch(obj, result);
    }


    /**
     * 验证党委员会
     *
     * @param obj
     * @param result
     */
    private void verifyCommittee(XwPartyMemberExcel obj, ExcelVerifyHandlerResult result) {
        if (StringUtils.isNotBlank(obj.getPartyCommitteeName())) {
            Optional<XwPartyCommittee> anyCommittee = committeeList.stream().filter(c -> obj.getPartyCommitteeName().equals(c.getName())).findFirst();
            if (anyCommittee.isPresent()) {
                XwPartyCommittee committee = anyCommittee.get();
                obj.setPartyCommitteeId(committee.getId());
                obj.setPartyCommitteeName(committee.getName());
            } else {
                appendMsg("党委员会填写有误", result);
            }
        }
    }

    /**
     * 验证党委员会
     */
    private void verifyCommittee() {
        verifyCommittee(obj, result);
    }

    /**
     * 验证党员类别
     *
     * @param obj
     * @param result
     */
    private void verifyDyType(XwPartyMemberExcel obj, ExcelVerifyHandlerResult result) {
        if (StringUtils.isNotBlank(obj.getPartyTypeName())) {
            Optional<Dd> anyDyType = dyTypeList.stream().filter(dy -> obj.getPartyTypeName().equals(dy.getName())).findFirst();
            if (anyDyType.isPresent()) {
                Dd da = anyDyType.get();
                obj.setPartyTypeId(da.getId());
                obj.setPartyTypeName(da.getName());
            } else {
                appendMsg("党员类别填写有误", result);
            }
        }
    }

    /**
     * 验证党员类别
     */
    private void verifyDyType() {
        verifyDyType(obj, result);
    }

    /**
     * 验证档案情况
     *
     * @param obj
     * @param result
     */
    private void verifyDa(XwPartyMemberExcel obj, ExcelVerifyHandlerResult result) {
        if (StringUtils.isNotBlank(obj.archivalSituationName())) {
            Optional<Dd> anyDa = daList.stream().filter(da -> obj.archivalSituationName().equals(da.getName())).findFirst();
            if (anyDa.isPresent()) {
                Dd da = anyDa.get();
                obj.setArchivalSituationId(da.getId());
                obj.setArchivalSituationName(da.getName());
            } else {
                appendMsg("档案情况填写有误", result);
            }
        }
    }

    /**
     * 验证档案情况
     */
    private void verifyDa() {
        verifyDa(obj, result);
    }

    private void appendMsg(final String msg, ExcelVerifyHandlerResult excelVerifyHandlerResult) {
        if (StringUtils.isBlank(excelVerifyHandlerResult.getMsg())) {
            excelVerifyHandlerResult.setMsg(msg);
        } else {
            excelVerifyHandlerResult.setMsg(excelVerifyHandlerResult.getMsg() + "," + msg);
        }
    }

    private void appendMsg(final String msg) {
        if (StringUtils.isBlank(result.getMsg())) {
            result.setMsg(msg);
        } else {
            result.setMsg(result.getMsg() + "," + msg);
        }
    }
}