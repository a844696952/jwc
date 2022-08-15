package com.yice.edu.cn.osp.controller.xw.dj.partyMember;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.easypoiplus.ExcelUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherQuit;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.common.pojo.xw.dj.XwDjMyStudyTeacher;
import com.yice.edu.cn.common.pojo.xw.dj.XwDjStudyResource;
import com.yice.edu.cn.common.pojo.xw.dj.partyCommittee.XwPartyCommittee;
import com.yice.edu.cn.common.pojo.xw.dj.partyDuty.XwPartyDuty;
import com.yice.edu.cn.common.pojo.xw.dj.partyMember.*;
import com.yice.edu.cn.osp.service.dd.DdService;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherQuitService;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherService;
import com.yice.edu.cn.osp.service.xw.dj.XwDjMyStudyTeacherService;
import com.yice.edu.cn.osp.service.xw.dj.XwDjStudyResourceService;
import com.yice.edu.cn.osp.service.xw.dj.partyCommittee.XwPartyCommitteeService;
import com.yice.edu.cn.osp.service.xw.dj.partyDuty.XwPartyDutyService;
import com.yice.edu.cn.osp.service.xw.dj.partyMember.XwPartyMemberService;
import com.yice.edu.cn.osp.service.xw.dj.partyMemberActivity.XwDjActivityUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.yice.edu.cn.common.easypoiplus.ExcelUtil.*;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/xwPartyMember")
@Api(value = "/xwPartyMember",description = "校务管理中的党建管理的党员管理模块")

public class XwPartyMemberController {
    @Autowired
    private XwPartyMemberService xwPartyMemberService;

    @Autowired
    private XwPartyCommitteeService xwPartyCommitteeService;

//    @Autowired
//    private XwPartyBranchService xwPartyBranchService;

    @Autowired
    private XwPartyDutyService xwPartyDutyService;

    @Autowired
    private DdService ddService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private TeacherQuitService teacherQuitService;

    @Autowired
    private XwDjMyStudyTeacherService xwDjMyStudyTeacherService;

    @Autowired
    private XwDjStudyResourceService xwDjStudyResourceService;

    @Autowired
    private XwDjActivityUserService xwDjActivityUserService;



    @PostMapping("/saveXwPartyMember")
    @ApiOperation(value = "保存校务管理中的党建管理的党员管理对象", notes = "返回保存好的校务管理中的党建管理的党员管理对象", response = XwPartyMember.class)
    public ResponseJson saveXwPartyMember(
            @ApiParam(value = "校务管理中的党建管理的党员管理对象", required = true)
            @RequestBody XwPartyMember xwPartyMember) {
        xwPartyMember.setSchoolId(mySchoolId());
        XwPartyMember s = xwPartyMemberService.saveXwPartyMember(xwPartyMember);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findXwPartyMemberById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找校务管理中的党建管理的党员管理", notes = "返回响应对象", response = XwPartyMember.class)
    public ResponseJson findXwPartyMemberById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        XwPartyMember xwPartyMember = xwPartyMemberService.findXwPartyMemberById(id);
        return new ResponseJson(xwPartyMember);
    }

    @PostMapping("/update/updateXwPartyMember")
    @ApiOperation(value = "修改校务管理中的党建管理的党员管理对象", notes = "返回响应对象")
    public ResponseJson updateXwPartyMember(
            @ApiParam(value = "被修改的校务管理中的党建管理的党员管理对象,对象属性不为空则修改", required = true)
            @RequestBody XwPartyMember xwPartyMember) {
        xwPartyMemberService.updateXwPartyMember(xwPartyMember);
        return new ResponseJson();
    }

    @GetMapping("/look/lookXwPartyMemberById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找校务管理中的党建管理的党员管理", notes = "返回响应对象", response = XwPartyMember.class)
    public ResponseJson lookXwPartyMemberById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        XwPartyMemberVaild xwPartyMemberVaild = xwPartyMemberService.lookXwPartyMemberById(id);
        return new ResponseJson(xwPartyMemberVaild);
    }
    @PostMapping("/findXwPartyMembersByCondition")
    @ApiOperation(value = "根据条件查找校务管理中的党建管理的党员管理", notes = "返回响应对象", response = XwPartyMember.class)
    public ResponseJson findXwPartyMembersByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwPartyMember xwPartyMember) {
        xwPartyMember.setSchoolId(mySchoolId());
        List<XwPartyMember> data = xwPartyMemberService.findXwPartyMemberListByCondition(xwPartyMember);
        long count = xwPartyMemberService.findXwPartyMemberCountByCondition(xwPartyMember);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneXwPartyMemberByCondition")
    @ApiOperation(value = "根据条件查找单个校务管理中的党建管理的党员管理,结果必须为单条数据", notes = "没有时返回空", response = XwPartyMember.class)
    public ResponseJson findOneXwPartyMemberByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody XwPartyMember xwPartyMember) {
        XwPartyMember one = xwPartyMemberService.findOneXwPartyMemberByCondition(xwPartyMember);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteXwPartyMember/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteXwPartyMember(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        final XwPartyMember xwPartyMember = xwPartyMemberService.findXwPartyMemberById(id);

        XwDjMyStudyTeacher xwDjMyStudyTeacher = new XwDjMyStudyTeacher();
        xwDjMyStudyTeacher.setTeacherId(xwPartyMember.getTeacherId());
        final long count1 = xwDjMyStudyTeacherService.findXwDjMyStudyTeacherCountByCondition(xwDjMyStudyTeacher);

        XwDjStudyResource xwDjStudyResource = new XwDjStudyResource();
        xwDjStudyResource.setTeacherId(xwPartyMember.getTeacherId());
        final long count2 = xwDjStudyResourceService.findXwDjStudyResourceCountByCondition(xwDjStudyResource);

        final boolean hasActivity = xwDjActivityUserService.findXwDjActivityUserByActivityUserId(xwPartyMember.getTeacherId());

        if(count1 >0 || count2 >0 || hasActivity){
            return new ResponseJson(false,"该人员已参加党建学习或活动，不可以删除");
        }

        xwPartyMemberService.deleteXwPartyMember(id);
        return new ResponseJson();
    }


    @PostMapping("/findXwPartyMemberListByCondition")
    @ApiOperation(value = "根据条件查找校务管理中的党建管理的党员管理列表", notes = "返回响应对象,不包含总条数", response = XwPartyMember.class)
    public ResponseJson findXwPartyMemberListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwPartyMember xwPartyMember) {
        xwPartyMember.setSchoolId(mySchoolId());
        List<XwPartyMember> data = xwPartyMemberService.findXwPartyMemberListByCondition(xwPartyMember);
        return new ResponseJson(data);
    }

    @GetMapping("/findXwPartyCommitteesByCondition")
    @ApiOperation(value = "根据条件查找党支部委员会列表", notes = "返回响应对象", response = XwPartyCommittee.class)
    public ResponseJson findXwPartyCommitteesByCondition() {
        XwPartyCommittee xwPartyCommittee = new XwPartyCommittee();
        xwPartyCommittee.setSchoolId(mySchoolId());
        List<XwPartyCommittee> data = xwPartyCommitteeService.findXwPartyCommitteeListByCondition(xwPartyCommittee);
        return new ResponseJson(data);
    }

    @GetMapping("/getTeacherListInMember")
    @ApiOperation(value = "获取目前所存在的讲师列表，进行过滤", notes = "返回响应对象", response = XwPartyCommittee.class)
    public ResponseJson getTeacherListInMember() {
        XwPartyMember xwPartyMember = new XwPartyMember();
        xwPartyMember.setSchoolId(mySchoolId());
        List<XwPartyMember> xwPartyMemberList = xwPartyMemberService.findXwPartyMemberListByCondition(xwPartyMember);
        List<String> stringList = new ArrayList<>();
        xwPartyMemberList.stream().forEach(e -> {
            stringList.add(e.getTeacherId());
        });
        return new ResponseJson(stringList);
    }

    //    @GetMapping("/findXwPartyBranchsByCondition/{partyCommitteeId}")
//    @ApiOperation(value = "根据党支部委员会编号查找党支部管理，传入党支部委员会编号", notes = "返回响应对象", response=XwPartyBranch.class)
//    public ResponseJson findXwPartyBranchsByCondition(
//            @ApiParam(value = "属性不为空则作为条件查询")
//            @Validated
//            @PathVariable("partyCommitteeId") String partyCommitteeId){
//        XwPartyBranch xwPartyBranch = new XwPartyBranch();
//        xwPartyBranch.setPartyCommitteeId(partyCommitteeId);
//        xwPartyBranch.setSchoolId(mySchoolId());
//        List<XwPartyBranch> data=xwPartyBranchService.findXwPartyBranchListByCondition(xwPartyBranch);
//        return new ResponseJson(data);
//    }
    @GetMapping("/findXwPartyDutysByCondition/{partyBranchsId}")
    @ApiOperation(value = "根据党支部编号获取职务列表,传入党支部编号", notes = "返回响应对象", response = XwPartyDuty.class)
    public ResponseJson findXwPartyDutysByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @PathVariable("partyBranchsId") String partyBranchsId) {
        XwPartyDuty xwPartyDuty = new XwPartyDuty();
        xwPartyDuty.setSchoolId(mySchoolId());
        List<XwPartyDuty> data = xwPartyDutyService.findXwPartyDutyListByCondition(xwPartyDuty);
        return new ResponseJson(data);
    }

    @GetMapping("/findArchivalSituationList")
    @ApiOperation(value = "查找档案情况列表", notes = "返回响应对象")
    public ResponseJson findPoliticalFace() {
        Dd dd = new Dd();
        //获取档案情况列表
        dd.setTypeId(Constant.DD_TYPE.ARCHIVALSITUATION);
        List<Dd> data = ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }

    @GetMapping("/findPartyTypeList")
    @ApiOperation(value = "查找党员类别", notes = "返回响应对象")
    public ResponseJson findPartyTypeList() {
        Dd dd = new Dd();
        //获取档案情况列表
        dd.setTypeId(Constant.DD_TYPE.PARTYTYPE);
        List<Dd> data = ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }

    @GetMapping("/findTeacherStatusList")
    @ApiOperation(value = "查找职务状态", notes = "返回响应对象")
    public ResponseJson findTeacherStatusList() {
        Dd dd = new Dd();
        //查找职务状态
        dd.setTypeId(Constant.DD_TYPE.TEACHER_STATUS);
        List<Dd> data = ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }

    @PostMapping("/findXwPartyMembersByConditions")
    @ApiOperation(value = "根据条件查找校务管理中的党建管理的党员管理，表单搜索，获取列表，离职，或者在职", notes = "返回响应对象", response = XwPartyMember.class)
    public ResponseJson findXwPartyMembersByConditions(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwPartyMember xwPartyMember) {
        xwPartyMember.setSchoolId(mySchoolId());
        List<XwPartyMember> data = xwPartyMemberService.findXwPartyMemberListByConditions(xwPartyMember);
        long count = xwPartyMemberService.findXwPartyMemberCountByConditions(xwPartyMember);
        return new ResponseJson(data, count);
    }

    @GetMapping("/getTeacherList")
    @ApiOperation(value = "获取讲师列表", notes = "返回响应对象", response = XwPartyMember.class)
    public ResponseJson getTeacherList() {
        List<IdAndName> idAndNameList = xwPartyMemberService.getTeacherList(mySchoolId());
        return new ResponseJson(idAndNameList);
    }
    @PostMapping("/bachUpdateByArray")
    @ApiOperation(value = "修改校务管理中的党建管理的党员管理", notes = "校务管理中的党建管理的党员管理对象必传")
    public ResponseJson bachUpdateByArray(
            @ApiParam(value = "校务管理中的党建管理的党员管理对象,对象属性不为空则修改", required = true)
            @RequestBody XwPartyMember xwPartyMember){
        //设置为完整
//        xwPartyMember.setIntact(Constant.PARTY_MEMBER_INTACT.COMPLETE);
        xwPartyMemberService.bachUpdateByArray(xwPartyMember);
        return new ResponseJson();
    }

    @PostMapping("/bachSaveXwPartyMember")
    @ApiOperation(value = "批量新增", notes = "返回响应对象", response = XwPartyMember.class)
    public ResponseJson bachSave(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwPartyMember xwPartyMember) {
        try {
            Teacher teacher = new Teacher();
            teacher.setStatus(Constant.STATUS.TEACHER_ON_LINE);
            teacher.setSchoolId(mySchoolId());
            List<Teacher> teacherList = teacherService.findTeacherListByCondition(teacher);
            List<XwPartyMember> xwPartyMemberList = new ArrayList<>();
            List<String> stringList = Arrays.asList(xwPartyMember.getRowData());
            teacherList.stream().forEach(e -> {
                if (stringList.contains(e.getId())) {
                    XwPartyMember xwPartyMember1 = new XwPartyMember();
                    xwPartyMember1.setTeacherId(e.getId());
                    xwPartyMember1.setSchoolId(mySchoolId());
                    xwPartyMember1.setTeacherName(e.getName());
                    xwPartyMember1.setTel(e.getTel());
                    xwPartyMember1.setSex(e.getSex());
                    xwPartyMember1.setCreateTime(DateUtil.now());
                    xwPartyMember1.setUpdateTime(DateUtil.now());
                    //默认是不完整
                    xwPartyMember1.setIntact(Constant.PARTY_MEMBER_INTACT.NOCOMPLETE);
                    xwPartyMemberList.add(xwPartyMember1);
                }
            });
            xwPartyMemberService.batchSaveXwPartyMember(xwPartyMemberList);
            return new ResponseJson();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseJson(false, "新增失败");
        }

    }

    @PostMapping("/bachSaveXwPartyMembers")
    @ApiOperation(value = "批量新增", notes = "返回响应对象", response = XwPartyMember.class)
    public ResponseJson bachSaveXwPartyMembers(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody List<XwPartyMember> xwPartyMemberList) {
        try {
            xwPartyMemberService.batchSaveXwPartyMember(xwPartyMemberList);
            return new ResponseJson();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseJson(false, "新增失败");
        }

    }

    @PostMapping("/findXwPartyMemberListByArray")
    @ApiOperation(value = "根据条件查找校务管理中的党建管理的党员管理，传入指定的id数组", notes = "返回响应对象", response = XwPartyMember.class)
    public ResponseJson findXwPartyMemberListByArray(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwPartyMember xwPartyMember) {
        xwPartyMember.setSchoolId(mySchoolId());
        List<XwPartyMember> data = xwPartyMemberService.findXwPartyMemberListByArray(xwPartyMember);
        return new ResponseJson(data, data.size());
    }

    @PostMapping("/getTeacherNameListByString")
    @ApiOperation(value = "根据条件查找校务管理中的党建管理的党员管理，传入指定的id数组", notes = "返回响应对象", response = XwPartyMember.class)
    public ResponseJson getTeacherNameListByString(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwPartyMember xwPartyMember) {
        xwPartyMember.setSchoolId(mySchoolId());
        String data = xwPartyMemberService.getTeacherNameListByString(xwPartyMember);
        return new ResponseJson(data);
    }


    @PostMapping("/batchUpdateParty")
    @ApiOperation(value = "批量修改党员信息", notes = "")
    public ResponseJson batchUpdateParty(
            @ApiParam(value = "校务管理中的党建管理的党员管理对象", required = true)
            @RequestBody List<XwPartyMember> list){
        xwPartyMemberService.batchUpdateParty(list);
        return new ResponseJson(true,"修改成功");
    }

    @PostMapping("/batchSaveXwPartyMemberByRowData")
    @ApiOperation(value = "批量修改党员信息", notes = "")
    public ResponseJson batchSaveXwPartyMemberByRowData(
            @ApiParam(value = "校务管理中的党建管理的党员管理对象", required = true)
            @RequestBody List<XwPartyMember> list){
        xwPartyMemberService.batchSaveXwPartyMemberByRowData(list);
        return new ResponseJson(true,"修改成功");
    }

    @ApiOperation(value = "导入excel党员信息", notes = "返回一个response")
    @PostMapping("/uploadExcel")
    public ResponseJson uploadExcel(MultipartFile file){


        final List<Dd> daList = getDds(Constant.DD_TYPE.ARCHIVALSITUATION);
        final List<Dd> dyTypeList = getDds(Constant.DD_TYPE.PARTYTYPE);
        final List<XwPartyCommittee> committeeList = getCommitteeList();
        final List<XwPartyCommittee> branchList = getPartyBranchList();
        final List<XwPartyDuty> dutyList = getDutyList();
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(2);
        params.setNeedVerfiy(true);
        //得到数据库已有的党员信息
        List<String> memberTeacherIds= getOnlineMemberList().stream().map(XwPartyMember::getTeacherId).collect(Collectors.toList());
        params.setVerifyHandler(new PartyMemberHandler(daList,dyTypeList,committeeList,branchList,dutyList,memberTeacherIds));
        try (InputStream is = file.getInputStream()) {

            final ExcelImportResult<XwPartyMemberExcel> excelExcelImportResult
                    = ExcelImportUtil.importExcelMore(is, XwPartyMemberExcel.class,params);

            if(excelExcelImportResult.isVerfiyFail()){
                ResponseJson responseJson = new ResponseJson(excelExcelImportResult.getFailList());
                responseJson.getResult().setSuccess(false);
                return  responseJson;
            }
            final List<XwPartyMemberExcel> list = excelExcelImportResult.getList();

            if(list==null || list.isEmpty()){
                return new ResponseJson(false,"excel中无数据");
            }
            //只取 跟党信息有关的数据项
            final List<Teacher> teachers = getOnlineTeachers();
            final Map<String, Teacher> teacherIdMap = teachers.stream().collect(Collectors.toMap(Teacher::getId,teacher -> teacher));
            final List<XwPartyMemberExcel> filterList = list
                    .stream()
                    .filter(e -> e.teacherId() != null && teacherIdMap.get(e.teacherId()) != null)
                    .collect(Collectors.toList());
            if(filterList.isEmpty()){
                return new ResponseJson(false,"excel模板可能有误");
            }
            filterList.forEach(e2->{
                e2.keepTeacher(teacherIdMap.get(e2.teacherId()));
            });
            xwPartyMemberService.uploadExcel(filterList);
            return new ResponseJson(true,"导入成功");
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseJson(false,e.toString());
        }

    }

    @ApiOperation(value = "导出excel党员信息", notes = "返回一个excel")
    @GetMapping("/exportExcel")
    public ResponseEntity<byte[]> exportExcel(
            @Validated(GroupOne.class) XwPartyMember xwPartyMemberParam, final LockParam lockParam) throws IOException {
        final String filename = "党员信息";
        final String attachment = new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1) + ".xls";

        final ExportParams exportParams = new ExportParams(filename, "sheet1");
        xwPartyMemberParam.setSchoolId(mySchoolId());
//        xwPartyMemberParam.setPager(new Pager().setPaging(false));

        List<XwPartyMemberExcel> excelList = getMemberExcels(xwPartyMemberParam);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, XwPartyMemberExcel.class, excelList);
        if(lockParam.isLock()){
            ExcelUtil.lockSheet(workbook,lockParam.getPasswordOrDefault());
        }
        return responseEntity(attachment, workbook);

    }

    private List<XwPartyMemberExcel> getMemberExcels(@Validated(GroupOne.class) XwPartyMember xwPartyMemberParam) {
        List<XwPartyMember> list = xwPartyMemberService.findXwPartyMemberListByCondition(xwPartyMemberParam);
        List<XwPartyMemberExcel> excelList = list.stream().map(XwPartyMemberExcel::new).collect(Collectors.toList());

        List<Teacher> teachers = null;
        //根据条件得到在线或者离线教师列表
        if(Constant.STATUS.TEACHER_ON_LINE.equals(xwPartyMemberParam.getOnline())){
            teachers = getOnlineTeachers();
        }
        if(Constant.STATUS.TEACHER_QUIT_LINE.equals(xwPartyMemberParam.getOnline())){
            teachers = getOfflineTeachers();
        }
        if(teachers!=null){
            List<String> teacherIds = teachers.stream().map(Teacher::getId).collect(Collectors.toList());

            excelList = excelList.stream().filter(e -> teacherIds.contains(e.teacherId())).collect(Collectors.toList());
        }
        return excelList;
    }

    private List<XwPartyMemberExcel> getOnlineMemberExcels(@Validated(GroupOne.class) XwPartyMember xwPartyMemberParam) {
        xwPartyMemberParam.setOnline(Constant.STATUS.TEACHER_ON_LINE);
        return this.getMemberExcels(xwPartyMemberParam);

    }


    /**
     * 下载党员信息模板
     * TODO 增加excel模板的版本，因为excel中有很多下拉框数据都来源于后台管理系统，如果有管理员修改了这些数据，用户之前下载的模板将不再适用，需要提示用户更新模板
     * @return excel
     * @throws Exception
     */
    @GetMapping("/downloadExcelTemplate")
    @ApiOperation(value = "下载excel党员信息模板", notes = "返回一个excel")
    public ResponseEntity<byte[]> downloadExcelTemplate(final LockParam lockParam) throws Exception {
        final String filename = "党员信息模板";
        final String attachment = new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1) + ".xls";
        XwPartyMember xwPartyMemberParam = new XwPartyMember();
        xwPartyMemberParam.setSchoolId(mySchoolId());
        xwPartyMemberParam.setPager(new Pager().setPaging(false));
        xwPartyMemberParam.setTeacherStatus(Constant.STATUS.TEACHER_ON_LINE);
        // 得到已录入党员信息的党员列表
        List<XwPartyMemberExcel> excels = getOnlineMemberExcels(xwPartyMemberParam);
        final int dataValidationSize = excels.size();

        //2019-06-11修改下载excel模板内容(只取已经录入为党员的老师)
//        Set<String> memberIds = memberList.stream().map(XwPartyMember::getTeacherId).collect(Collectors.toSet());
//        final List<Teacher> teachers = getOnlineTeachers();
//
//        final List<XwPartyMemberExcel> excels = teachers
//                .stream()
//                .filter(p -> !memberIds.contains(p.getId()))
//                .map(XwPartyMemberExcel::new)
//                .collect(Collectors.toList());
//
//        final List<XwPartyMemberExcel> excels2 = memberList
//                .stream()
//                .map(XwPartyMemberExcel::new)
//                .collect(Collectors.toList());
//
//        //将已有的党员信息追加到最后
//        excels.addAll(excels2);
//
//        final Set<XwPartyMemberExcel> excels = memberList
//                .stream()
//                .map(XwPartyMemberExcel::new)
//                .collect(Collectors.toSet());

        final ExportParams exportParams = new ExportParams(filename, "sheet1");
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, XwPartyMemberExcel.class, excels);

        //在创建一个隐藏的sheet用来装下拉列表的数据
        createCustomRowAndName(workbook);
        // ******* 添加数据验证项 start **********
        addValidationData(dataValidationSize, workbook);
        // ********** 添加excel验证项完毕  ***********end
        //锁列
        ExcelUtil.lockSheet(workbook, 0, XwPartyMemberExcel.class,lockParam.getPasswordOrDefault());
        return responseEntity(attachment, workbook);
    }

    private List<XwPartyMember> getOnlineMemberList() {
        XwPartyMember xwPartyMemberParam = new XwPartyMember();
        xwPartyMemberParam.setSchoolId(mySchoolId());
        xwPartyMemberParam.setPager(new Pager().setPaging(false));
        xwPartyMemberParam.setTeacherStatus(Constant.STATUS.TEACHER_ON_LINE);
        List<XwPartyMember> memberList = xwPartyMemberService.findXwPartyMemberListByConditions(xwPartyMemberParam);
        return memberList;
    }

    private void addValidationData(int dataValidationSize, Workbook workbook) {
        // 获取委员会列表
        final Set<String> committeeNameSet = getParentCommitteeNameStrings();
        // 获取职务列表
        final Set<String> dutyNameSet = getDutyNameStrings();
        //获取党员类型列表
        Set<String> dyTypeSet = getDdSet(Constant.DD_TYPE.PARTYTYPE);
        //获取档案情况列表
        Set<String> daSet = getDdSet(Constant.DD_TYPE.ARCHIVALSITUATION);
        final Sheet sheet1 = workbook.getSheet("sheet1");

        XwPartyMemberExcel xwPartyMemberExcel = new XwPartyMemberExcel();
        final int dataStartRowIndex = xwPartyMemberExcel.dataStartRowIndex();

        final int dataEndRowIndex = dataValidationSize + dataStartRowIndex;
        final DataValidation dataValidation1 = getDataValidationByExplicitListValues(committeeNameSet, dataStartRowIndex, dataValidationSize, 5, 0);
        sheet1.addValidationData(dataValidation1);
        IntStream.rangeClosed(dataStartRowIndex, dataEndRowIndex).forEach(i -> {
            final DataValidation dataValidation2 = getDataValidationByListFormula("INDIRECT($F$" + (i + 1) + ")", i, 0, 6, 0);
            sheet1.addValidationData(dataValidation2);

        });
        final DataValidation dataValidation3 = getDataValidationByExplicitListValues(dutyNameSet, dataStartRowIndex, dataValidationSize, 7, 0);
        sheet1.addValidationData(dataValidation3);

        final DataValidation dataValidation4 = getDataValidationByExplicitListValues(dyTypeSet, dataStartRowIndex, dataValidationSize, 4, 0);
        sheet1.addValidationData(dataValidation4);
        final DataValidation dataValidation5 = getDataValidationByExplicitListValues(daSet, dataStartRowIndex, dataValidationSize, 10, 0);
        sheet1.addValidationData(dataValidation5);
    }


    private List<Teacher> getOnlineTeachers() {
        Teacher teacherParam = new Teacher();
        teacherParam.setSchoolId(mySchoolId());
        teacherParam.setStatus(Constant.STATUS.TEACHER_ON_LINE);
        teacherParam.setPager(new Pager().setPaging(false));
        return teacherService.findTeacherListByCondition(teacherParam);
    }


    private List<Teacher> getOfflineTeachers() {
        TeacherQuit teacherParam = new TeacherQuit();
        teacherParam.setSchoolId(mySchoolId());
        teacherParam.setStatus(Constant.STATUS.TEACHER_QUIT_LINE);
        teacherParam.setPager(new Pager().setPaging(false));
        List<TeacherQuit> list = teacherQuitService.findTeacherQuitListByCondition(teacherParam);
        final List<Teacher> result = list.stream().map(tq -> {
            Teacher teacher = new Teacher();
            teacher.setId(tq.getId());
            teacher.setCreateTime(tq.getCreateTime());
            teacher.setUpdateTime(tq.getUpdateTime());
            teacher.setImgUrl(tq.getImgUrl());
            teacher.setWorkNumber(tq.getWorkNumber());
            teacher.setName(tq.getName());
            teacher.setPinyin(tq.getPinyin());
            teacher.setInitials(tq.getInitials());
            teacher.setEnglishName(tq.getEnglishName());
            teacher.setSex(tq.getSex());
            teacher.setTel(tq.getTel());
            teacher.setQq(tq.getQq());
            teacher.setWeixin(tq.getWeixin());
            teacher.setEmail(tq.getEmail());
            teacher.setNationality(tq.getNationality());
            teacher.setNation(tq.getNation());
            teacher.setNationName(tq.getNationName());
            teacher.setNativePlace(tq.getNativePlace());
            teacher.setDocumentType(tq.getDocumentType());
            teacher.setDocumentNum(tq.getDocumentNum());
            teacher.setTeacherNum(tq.getTeacherNum());
            teacher.setProvinceName(tq.getProvinceName());
            teacher.setCityName(tq.getCityName());
            teacher.setCountyName(tq.getCountyName());
            teacher.setAddress(tq.getAddress());
            teacher.setPoliticalFace(tq.getPoliticalFace());
            teacher.setBirthDate(tq.getBirthDate());
            teacher.setTopEdu(tq.getTopEdu());
            teacher.setGraduate(tq.getGraduate());
            teacher.setMajor(tq.getMajor());
            teacher.setBeginTime(tq.getBeginTime());
            teacher.setWorks(tq.getWorks());
            teacher.setStatus(tq.getStatus());
            teacher.setSchoolId(tq.getSchoolId());
            teacher.setSchoolName(tq.getSchoolName());
            teacher.setMaritalStatus(tq.getMaritalStatus());
            return teacher;
        }).collect(Collectors.toList());
        return result;
    }

    private void createCustomRowAndName(Workbook workbook) {
        final String hideSheetName = "隐藏的sheet勿动";
        final Sheet hideSheet = ExcelUtil.createHideSheet(hideSheetName, workbook);
        hideSheet.protectSheet("12345678");
        // 获取每个委员会下所有的党支部的列表Map<String,List>
        final Map<String, Set<String>> branchMap = getBranchStringSetMap();
        createRowAndName(workbook, hideSheetName, 0, branchMap);
    }

    private Set<String> getParentCommitteeNameStrings() {
        final List<XwPartyCommittee> committeeList = getRootCommitteeList();
        return committeeList.stream().map(XwPartyCommittee::getName).collect(Collectors.toSet());
    }

    private Set<String> getDutyNameStrings() {

        final List<XwPartyDuty> dutyList = getDutyList();
        return dutyList.stream().map(XwPartyDuty::getName).collect(Collectors.toSet());
    }


    private List<XwPartyCommittee> getCommitteeList() {
        XwPartyCommittee committeeParam = new XwPartyCommittee();
        committeeParam.setSchoolId(mySchoolId());
        committeeParam.setPager(new Pager().setPaging(false));
        return xwPartyCommitteeService.findCommitteeWithParentName(committeeParam);
    }

    private List<XwPartyCommittee> getRootCommitteeList() {
        XwPartyCommittee committeeParam = new XwPartyCommittee();
        committeeParam.setSchoolId(mySchoolId());
        committeeParam.setParentId("-1");
        committeeParam.setPager(new Pager().setPaging(false));
        return xwPartyCommitteeService.findCommitteeWithParentName(committeeParam);
    }

    private List<XwPartyCommittee> getNotRootCommitteeList() {
        return getCommitteeList().stream().filter(c-> !c.getParentId().equals("-1") && StringUtils.isNotBlank(c.getParentName())).collect(Collectors.toList());
    }


    private Map<String, Set<String>> getBranchStringSetMap() {
        final List<XwPartyCommittee> branchList = getPartyBranchList();
        return branchList.stream().collect(
                Collectors.groupingBy(
                        XwPartyCommittee::getParentName,
                        Collectors.mapping(XwPartyCommittee::getName, Collectors.toSet())
                )
        );
    }

    private List<XwPartyCommittee> getPartyBranchList() {
        return getNotRootCommitteeList();
    }

    private Set<String> getDdSet(String partytype) {
        List<Dd> data = getDds(partytype);
        return data.stream().map(Dd::getName).collect(Collectors.toSet());
    }

    private List<Dd> getDds(String partytype) {
        Dd dd = new Dd();
        dd.setTypeId(partytype);
        return ddService.findDdListByCondition(dd);
    }

    private List<XwPartyDuty> getDutyList() {
        XwPartyDuty dutyParam = new XwPartyDuty();
        dutyParam.setSchoolId(mySchoolId());
        dutyParam.setPager(new Pager().setPaging(false));
        return xwPartyDutyService.findXwPartyDutyListByCondition(dutyParam);
    }


    private ResponseEntity<byte[]> responseEntity(String attachment, Workbook workbook) throws IOException {
        //创建一个HttpHeader
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", attachment);
        try (ByteArrayOutputStream outByteStream = new ByteArrayOutputStream()) {
            workbook.write(outByteStream);
            headers.setContentLength(outByteStream.size());
            return new ResponseEntity<>(outByteStream.toByteArray(), headers, HttpStatus.OK);
        } catch (IOException e) {
            throw e;
        }
    }

    @GetMapping("/findPartyMemberTree")
    @ApiOperation(value = "根据党员类别查询党组织树结构", notes = "根据党员类别查询党组织树结构")
    public ResponseJson getPartyMemberTree() {
        List<XwPartyMember> partyMemberTree = xwPartyMemberService.getPartyMemberTree(mySchoolId());
        return new ResponseJson(partyMemberTree);
    }
}


