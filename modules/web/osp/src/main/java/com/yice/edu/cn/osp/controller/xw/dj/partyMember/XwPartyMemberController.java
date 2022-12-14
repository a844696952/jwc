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
@Api(value = "/xwPartyMember",description = "???????????????????????????????????????????????????")

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
    @ApiOperation(value = "?????????????????????????????????????????????????????????", notes = "?????????????????????????????????????????????????????????????????????", response = XwPartyMember.class)
    public ResponseJson saveXwPartyMember(
            @ApiParam(value = "???????????????????????????????????????????????????", required = true)
            @RequestBody XwPartyMember xwPartyMember) {
        xwPartyMember.setSchoolId(mySchoolId());
        XwPartyMember s = xwPartyMemberService.saveXwPartyMember(xwPartyMember);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findXwPartyMemberById/{id}")
    @ApiOperation(value = "???????????????,??????id???????????????????????????????????????????????????", notes = "??????????????????", response = XwPartyMember.class)
    public ResponseJson findXwPartyMemberById(
            @ApiParam(value = "???????????????,???????????????id", required = true)
            @PathVariable String id) {
        XwPartyMember xwPartyMember = xwPartyMemberService.findXwPartyMemberById(id);
        return new ResponseJson(xwPartyMember);
    }

    @PostMapping("/update/updateXwPartyMember")
    @ApiOperation(value = "?????????????????????????????????????????????????????????", notes = "??????????????????")
    public ResponseJson updateXwPartyMember(
            @ApiParam(value = "???????????????????????????????????????????????????????????????,??????????????????????????????", required = true)
            @RequestBody XwPartyMember xwPartyMember) {
        xwPartyMemberService.updateXwPartyMember(xwPartyMember);
        return new ResponseJson();
    }

    @GetMapping("/look/lookXwPartyMemberById/{id}")
    @ApiOperation(value = "???????????????,??????id???????????????????????????????????????????????????", notes = "??????????????????", response = XwPartyMember.class)
    public ResponseJson lookXwPartyMemberById(
            @ApiParam(value = "???????????????,???????????????id", required = true)
            @PathVariable String id) {
        XwPartyMemberVaild xwPartyMemberVaild = xwPartyMemberService.lookXwPartyMemberById(id);
        return new ResponseJson(xwPartyMemberVaild);
    }
    @PostMapping("/findXwPartyMembersByCondition")
    @ApiOperation(value = "???????????????????????????????????????????????????????????????", notes = "??????????????????", response = XwPartyMember.class)
    public ResponseJson findXwPartyMembersByCondition(
            @ApiParam(value = "????????????????????????????????????")
            @Validated
            @RequestBody XwPartyMember xwPartyMember) {
        xwPartyMember.setSchoolId(mySchoolId());
        List<XwPartyMember> data = xwPartyMemberService.findXwPartyMemberListByCondition(xwPartyMember);
        long count = xwPartyMemberService.findXwPartyMemberCountByCondition(xwPartyMember);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneXwPartyMemberByCondition")
    @ApiOperation(value = "?????????????????????????????????????????????????????????????????????,???????????????????????????", notes = "??????????????????", response = XwPartyMember.class)
    public ResponseJson findOneXwPartyMemberByCondition(
            @ApiParam(value = "????????????????????????????????????")
            @RequestBody XwPartyMember xwPartyMember) {
        XwPartyMember one = xwPartyMemberService.findOneXwPartyMemberByCondition(xwPartyMember);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteXwPartyMember/{id}")
    @ApiOperation(value = "??????id??????", notes = "??????????????????")
    public ResponseJson deleteXwPartyMember(
            @ApiParam(value = "??????????????????id", required = true)
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
            return new ResponseJson(false,"?????????????????????????????????????????????????????????");
        }

        xwPartyMemberService.deleteXwPartyMember(id);
        return new ResponseJson();
    }


    @PostMapping("/findXwPartyMemberListByCondition")
    @ApiOperation(value = "?????????????????????????????????????????????????????????????????????", notes = "??????????????????,??????????????????", response = XwPartyMember.class)
    public ResponseJson findXwPartyMemberListByCondition(
            @ApiParam(value = "????????????????????????????????????")
            @Validated
            @RequestBody XwPartyMember xwPartyMember) {
        xwPartyMember.setSchoolId(mySchoolId());
        List<XwPartyMember> data = xwPartyMemberService.findXwPartyMemberListByCondition(xwPartyMember);
        return new ResponseJson(data);
    }

    @GetMapping("/findXwPartyCommitteesByCondition")
    @ApiOperation(value = "??????????????????????????????????????????", notes = "??????????????????", response = XwPartyCommittee.class)
    public ResponseJson findXwPartyCommitteesByCondition() {
        XwPartyCommittee xwPartyCommittee = new XwPartyCommittee();
        xwPartyCommittee.setSchoolId(mySchoolId());
        List<XwPartyCommittee> data = xwPartyCommitteeService.findXwPartyCommitteeListByCondition(xwPartyCommittee);
        return new ResponseJson(data);
    }

    @GetMapping("/getTeacherListInMember")
    @ApiOperation(value = "???????????????????????????????????????????????????", notes = "??????????????????", response = XwPartyCommittee.class)
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
//    @ApiOperation(value = "????????????????????????????????????????????????????????????????????????????????????", notes = "??????????????????", response=XwPartyBranch.class)
//    public ResponseJson findXwPartyBranchsByCondition(
//            @ApiParam(value = "????????????????????????????????????")
//            @Validated
//            @PathVariable("partyCommitteeId") String partyCommitteeId){
//        XwPartyBranch xwPartyBranch = new XwPartyBranch();
//        xwPartyBranch.setPartyCommitteeId(partyCommitteeId);
//        xwPartyBranch.setSchoolId(mySchoolId());
//        List<XwPartyBranch> data=xwPartyBranchService.findXwPartyBranchListByCondition(xwPartyBranch);
//        return new ResponseJson(data);
//    }
    @GetMapping("/findXwPartyDutysByCondition/{partyBranchsId}")
    @ApiOperation(value = "???????????????????????????????????????,?????????????????????", notes = "??????????????????", response = XwPartyDuty.class)
    public ResponseJson findXwPartyDutysByCondition(
            @ApiParam(value = "????????????????????????????????????")
            @Validated
            @PathVariable("partyBranchsId") String partyBranchsId) {
        XwPartyDuty xwPartyDuty = new XwPartyDuty();
        xwPartyDuty.setSchoolId(mySchoolId());
        List<XwPartyDuty> data = xwPartyDutyService.findXwPartyDutyListByCondition(xwPartyDuty);
        return new ResponseJson(data);
    }

    @GetMapping("/findArchivalSituationList")
    @ApiOperation(value = "????????????????????????", notes = "??????????????????")
    public ResponseJson findPoliticalFace() {
        Dd dd = new Dd();
        //????????????????????????
        dd.setTypeId(Constant.DD_TYPE.ARCHIVALSITUATION);
        List<Dd> data = ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }

    @GetMapping("/findPartyTypeList")
    @ApiOperation(value = "??????????????????", notes = "??????????????????")
    public ResponseJson findPartyTypeList() {
        Dd dd = new Dd();
        //????????????????????????
        dd.setTypeId(Constant.DD_TYPE.PARTYTYPE);
        List<Dd> data = ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }

    @GetMapping("/findTeacherStatusList")
    @ApiOperation(value = "??????????????????", notes = "??????????????????")
    public ResponseJson findTeacherStatusList() {
        Dd dd = new Dd();
        //??????????????????
        dd.setTypeId(Constant.DD_TYPE.TEACHER_STATUS);
        List<Dd> data = ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }

    @PostMapping("/findXwPartyMembersByConditions")
    @ApiOperation(value = "?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????", notes = "??????????????????", response = XwPartyMember.class)
    public ResponseJson findXwPartyMembersByConditions(
            @ApiParam(value = "????????????????????????????????????")
            @Validated
            @RequestBody XwPartyMember xwPartyMember) {
        xwPartyMember.setSchoolId(mySchoolId());
        List<XwPartyMember> data = xwPartyMemberService.findXwPartyMemberListByConditions(xwPartyMember);
        long count = xwPartyMemberService.findXwPartyMemberCountByConditions(xwPartyMember);
        return new ResponseJson(data, count);
    }

    @GetMapping("/getTeacherList")
    @ApiOperation(value = "??????????????????", notes = "??????????????????", response = XwPartyMember.class)
    public ResponseJson getTeacherList() {
        List<IdAndName> idAndNameList = xwPartyMemberService.getTeacherList(mySchoolId());
        return new ResponseJson(idAndNameList);
    }
    @PostMapping("/bachUpdateByArray")
    @ApiOperation(value = "???????????????????????????????????????????????????", notes = "?????????????????????????????????????????????????????????")
    public ResponseJson bachUpdateByArray(
            @ApiParam(value = "???????????????????????????????????????????????????,??????????????????????????????", required = true)
            @RequestBody XwPartyMember xwPartyMember){
        //???????????????
//        xwPartyMember.setIntact(Constant.PARTY_MEMBER_INTACT.COMPLETE);
        xwPartyMemberService.bachUpdateByArray(xwPartyMember);
        return new ResponseJson();
    }

    @PostMapping("/bachSaveXwPartyMember")
    @ApiOperation(value = "????????????", notes = "??????????????????", response = XwPartyMember.class)
    public ResponseJson bachSave(
            @ApiParam(value = "????????????????????????????????????")
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
                    //??????????????????
                    xwPartyMember1.setIntact(Constant.PARTY_MEMBER_INTACT.NOCOMPLETE);
                    xwPartyMemberList.add(xwPartyMember1);
                }
            });
            xwPartyMemberService.batchSaveXwPartyMember(xwPartyMemberList);
            return new ResponseJson();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseJson(false, "????????????");
        }

    }

    @PostMapping("/bachSaveXwPartyMembers")
    @ApiOperation(value = "????????????", notes = "??????????????????", response = XwPartyMember.class)
    public ResponseJson bachSaveXwPartyMembers(
            @ApiParam(value = "????????????????????????????????????")
            @Validated
            @RequestBody List<XwPartyMember> xwPartyMemberList) {
        try {
            xwPartyMemberService.batchSaveXwPartyMember(xwPartyMemberList);
            return new ResponseJson();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseJson(false, "????????????");
        }

    }

    @PostMapping("/findXwPartyMemberListByArray")
    @ApiOperation(value = "?????????????????????????????????????????????????????????????????????????????????id??????", notes = "??????????????????", response = XwPartyMember.class)
    public ResponseJson findXwPartyMemberListByArray(
            @ApiParam(value = "????????????????????????????????????")
            @Validated
            @RequestBody XwPartyMember xwPartyMember) {
        xwPartyMember.setSchoolId(mySchoolId());
        List<XwPartyMember> data = xwPartyMemberService.findXwPartyMemberListByArray(xwPartyMember);
        return new ResponseJson(data, data.size());
    }

    @PostMapping("/getTeacherNameListByString")
    @ApiOperation(value = "?????????????????????????????????????????????????????????????????????????????????id??????", notes = "??????????????????", response = XwPartyMember.class)
    public ResponseJson getTeacherNameListByString(
            @ApiParam(value = "????????????????????????????????????")
            @Validated
            @RequestBody XwPartyMember xwPartyMember) {
        xwPartyMember.setSchoolId(mySchoolId());
        String data = xwPartyMemberService.getTeacherNameListByString(xwPartyMember);
        return new ResponseJson(data);
    }


    @PostMapping("/batchUpdateParty")
    @ApiOperation(value = "????????????????????????", notes = "")
    public ResponseJson batchUpdateParty(
            @ApiParam(value = "???????????????????????????????????????????????????", required = true)
            @RequestBody List<XwPartyMember> list){
        xwPartyMemberService.batchUpdateParty(list);
        return new ResponseJson(true,"????????????");
    }

    @PostMapping("/batchSaveXwPartyMemberByRowData")
    @ApiOperation(value = "????????????????????????", notes = "")
    public ResponseJson batchSaveXwPartyMemberByRowData(
            @ApiParam(value = "???????????????????????????????????????????????????", required = true)
            @RequestBody List<XwPartyMember> list){
        xwPartyMemberService.batchSaveXwPartyMemberByRowData(list);
        return new ResponseJson(true,"????????????");
    }

    @ApiOperation(value = "??????excel????????????", notes = "????????????response")
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
        //????????????????????????????????????
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
                return new ResponseJson(false,"excel????????????");
            }
            //?????? ??????????????????????????????
            final List<Teacher> teachers = getOnlineTeachers();
            final Map<String, Teacher> teacherIdMap = teachers.stream().collect(Collectors.toMap(Teacher::getId,teacher -> teacher));
            final List<XwPartyMemberExcel> filterList = list
                    .stream()
                    .filter(e -> e.teacherId() != null && teacherIdMap.get(e.teacherId()) != null)
                    .collect(Collectors.toList());
            if(filterList.isEmpty()){
                return new ResponseJson(false,"excel??????????????????");
            }
            filterList.forEach(e2->{
                e2.keepTeacher(teacherIdMap.get(e2.teacherId()));
            });
            xwPartyMemberService.uploadExcel(filterList);
            return new ResponseJson(true,"????????????");
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseJson(false,e.toString());
        }

    }

    @ApiOperation(value = "??????excel????????????", notes = "????????????excel")
    @GetMapping("/exportExcel")
    public ResponseEntity<byte[]> exportExcel(
            @Validated(GroupOne.class) XwPartyMember xwPartyMemberParam, final LockParam lockParam) throws IOException {
        final String filename = "????????????";
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
        //????????????????????????????????????????????????
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
     * ????????????????????????
     * TODO ??????excel????????????????????????excel?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     * @return excel
     * @throws Exception
     */
    @GetMapping("/downloadExcelTemplate")
    @ApiOperation(value = "??????excel??????????????????", notes = "????????????excel")
    public ResponseEntity<byte[]> downloadExcelTemplate(final LockParam lockParam) throws Exception {
        final String filename = "??????????????????";
        final String attachment = new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1) + ".xls";
        XwPartyMember xwPartyMemberParam = new XwPartyMember();
        xwPartyMemberParam.setSchoolId(mySchoolId());
        xwPartyMemberParam.setPager(new Pager().setPaging(false));
        xwPartyMemberParam.setTeacherStatus(Constant.STATUS.TEACHER_ON_LINE);
        // ??????????????????????????????????????????
        List<XwPartyMemberExcel> excels = getOnlineMemberExcels(xwPartyMemberParam);
        final int dataValidationSize = excels.size();

        //2019-06-11????????????excel????????????(????????????????????????????????????)
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
//        //???????????????????????????????????????
//        excels.addAll(excels2);
//
//        final Set<XwPartyMemberExcel> excels = memberList
//                .stream()
//                .map(XwPartyMemberExcel::new)
//                .collect(Collectors.toSet());

        final ExportParams exportParams = new ExportParams(filename, "sheet1");
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, XwPartyMemberExcel.class, excels);

        //????????????????????????sheet??????????????????????????????
        createCustomRowAndName(workbook);
        // ******* ????????????????????? start **********
        addValidationData(dataValidationSize, workbook);
        // ********** ??????excel???????????????  ***********end
        //??????
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
        // ?????????????????????
        final Set<String> committeeNameSet = getParentCommitteeNameStrings();
        // ??????????????????
        final Set<String> dutyNameSet = getDutyNameStrings();
        //????????????????????????
        Set<String> dyTypeSet = getDdSet(Constant.DD_TYPE.PARTYTYPE);
        //????????????????????????
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
        final String hideSheetName = "?????????sheet??????";
        final Sheet hideSheet = ExcelUtil.createHideSheet(hideSheetName, workbook);
        hideSheet.protectSheet("12345678");
        // ???????????????????????????????????????????????????Map<String,List>
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
        //????????????HttpHeader
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
    @ApiOperation(value = "??????????????????????????????????????????", notes = "??????????????????????????????????????????")
    public ResponseJson getPartyMemberTree() {
        List<XwPartyMember> partyMemberTree = xwPartyMemberService.getPartyMemberTree(mySchoolId());
        return new ResponseJson(partyMemberTree);
    }
}


