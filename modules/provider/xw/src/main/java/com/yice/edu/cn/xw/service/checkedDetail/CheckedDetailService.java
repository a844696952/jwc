package com.yice.edu.cn.xw.service.checkedDetail;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.xw.checkedDetail.CheckedDetail;
import com.yice.edu.cn.common.pojo.xw.dutyArrment.DutyArrment;
import com.yice.edu.cn.common.pojo.xw.dutyteachersDutyarrment.DutyteachersDutyarrment;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import com.yice.edu.cn.common.util.weekDayUtil.DoTime;
import com.yice.edu.cn.xw.dao.DutyteachersDutyarrment.IDutyteachersDutyarrmentDao;
import com.yice.edu.cn.xw.dao.checkedDetail.ICheckedDetailDao;
import com.yice.edu.cn.xw.dao.dutyArrment.IDutyArrmentDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckedDetailService {

    @Autowired
    private IDutyteachersDutyarrmentDao dutyteachersDutyarrmentDao;
    @Autowired
    private ICheckedDetailDao checkedDetailDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private IDutyArrmentDao dutyArrmentDao;
    @Autowired
    private CurSchoolYearService curSchoolService;

    @Transactional(readOnly = true)
    public CheckedDetail findCheckedDetailById(String id) {
        return checkedDetailDao.findCheckedDetailById(id);
    }
    @Transactional
    public void saveCheckedDetail(CheckedDetail checkedDetail) {
        checkedDetail.setId(sequenceId.nextId());
        checkedDetailDao.saveCheckedDetail(checkedDetail);
    }
    @Transactional(readOnly = true)
    public List<CheckedDetail> findCheckedDetailListByCondition(CheckedDetail checkedDetail) {
        return checkedDetailDao.findCheckedDetailListByCondition(checkedDetail);
    }
    @Transactional(readOnly = true)
    public CheckedDetail findOneCheckedDetailByCondition(CheckedDetail checkedDetail) {
        return checkedDetailDao.findOneCheckedDetailByCondition(checkedDetail);
    }
    @Transactional(readOnly = true)
    public long findCheckedDetailCountByCondition(CheckedDetail checkedDetail) {
        return checkedDetailDao.findCheckedDetailCountByCondition(checkedDetail);
    }
    @Transactional
    public void updateCheckedDetail(CheckedDetail checkedDetail) {
        checkedDetailDao.updateCheckedDetail(checkedDetail);
    }
    @Transactional
    public void deleteCheckedDetail(String id) {
        checkedDetailDao.deleteCheckedDetail(id);
    }
    @Transactional
    public void deleteCheckedDetailByCondition(CheckedDetail checkedDetail) {
        checkedDetailDao.deleteCheckedDetailByCondition(checkedDetail);
    }
    @Transactional
    public void batchSaveCheckedDetail(List<CheckedDetail> checkedDetails){
        checkedDetails.forEach(checkedDetail -> checkedDetail.setId(sequenceId.nextId()));
        checkedDetailDao.batchSaveCheckedDetail(checkedDetails);
    }

    //for tap??????
    @Transactional(readOnly = true)
    public List<CheckedDetail> findCheckedDetailListForAppTapByCondition(CheckedDetail checkedDetail){
        return checkedDetailDao.findCheckedDetailListForAppTapByCondition(checkedDetail);
    }

    //tap????????????
    @Transactional(readOnly = true)
    public  List<CheckedDetail> findCheckedDetailsForAppTapByConditionNewPrincipal(CheckedDetail checkedDetail){
        return checkedDetailDao.findCheckedDetailsForAppTapByConditionNewPrincipal(checkedDetail);
    };
    @Transactional(readOnly = true)
    public List<CheckedDetail> findCheckedDetailsForAppTapNameOrTelByConditionNewPrincipal(CheckedDetail checkedDetail){
        return checkedDetailDao.findCheckedDetailsForAppTapNameOrTelByConditionNewPrincipal(checkedDetail);
    }

    @Transactional
    public void updateCheckedDetailForTapNewTeacher(CheckedDetail checkedDetail){
        checkedDetailDao.updateCheckedDetailForTapNewTeacher(checkedDetail);
    }

    @Transactional(readOnly = true)
    public List<CheckedDetail> findCheckedDetailListByCondition4like(CheckedDetail checkedDetail) {
        return checkedDetailDao.findCheckedDetailListByCondition4like(checkedDetail);
    }

    @Transactional
    public void updateCheckedDetail4Like(CheckedDetail checkedDetail) {
        List<CheckedDetail> list = checkedDetailDao.findCheckedDetailListByCondition(checkedDetail);
        if(list.isEmpty()){
            return;
        }
        CheckedDetail one = list.get(0);
        //?????????????????????????????????
        boolean exist = isExist(checkedDetail,one);
        if(!exist){
            return;
        }
        //????????????????????????
        boolean updatePermission = updatePermission(checkedDetail);
        if(!updatePermission){
            return;
        }
        //?????????????????????????????????
        List<CheckedDetail> checkedDetailListByCondition = checkedDetailDao.findCheckedDetailListByCondition(checkedDetail);
        List<String> delIds = checkedDetailListByCondition.stream().map(s->s.getId()).collect(Collectors.toList());
        if(!delIds.isEmpty()){
            String[] arry = null;
            String puchTimeLaterNew =//?????????????????????
                    DoTime.getPuchLater(checkedDetail.getChangedDutye(),one.getDutyTimeStart(),one.getPunchTime());
            arry = DoTime.getDutyDayAndId(checkedDetail.getChangedDutye());//??????????????????id
            String dutyDayNew = arry[0];
            String dutyDayIdNew = arry[1];
            //??????????????????????????????
            CheckedDetail checkedDetailUpdate = new CheckedDetail();
            checkedDetailUpdate.setDelIds(delIds);//????????????id??????
            checkedDetailUpdate.setPuchTimeLaterNew(puchTimeLaterNew);
            checkedDetailUpdate.setDutyDayNew(dutyDayNew);
            checkedDetailUpdate.setDutyDayIdNew(dutyDayIdNew);
            checkedDetailUpdate.setRecordDate(checkedDetail.getChangedDutye());

            curSchoolService.setSchoolYearTerm(checkedDetailUpdate,checkedDetail.getSchoolId());//??????????????????3???????????????

            checkedDetailDao.updateCheckedDetailBatch4Like(checkedDetailUpdate);
        }else{
            checkedDetail.setCode("204");//???????????????????????????????????????
            checkedDetail.setMsg("???????????????????????????,????????????????????????");//???????????????????????????
            return;
        }
    }

    //??????????????????
    @Transactional
    public void deleteCheckedDetailByCondition4Like(CheckedDetail checkedDetail) {
        //1.???????????????????????? ??????
        boolean updatePermission = updatePermission(checkedDetail);
        if(!updatePermission){
            return;
        }
        List<CheckedDetail> checkedDetailLists = checkedDetailDao.findCheckedDetailListByCondition(checkedDetail);
        List<String> delIds = checkedDetailLists.stream().map(s->s.getId()).collect(Collectors.toList());//?????????????????????ids
        if(!delIds.isEmpty()){
            checkedDetailDao.deleteCheckedDetail4Like(delIds);//????????????
        }else{
            checkedDetail.setCode("204");
            checkedDetail.setMsg("??????????????????????????????????????????????????????");
            return;
        }
    }


    @Transactional
    public void updateTochangeCheckedDetail4Like(CheckedDetail checkedDetail) {
        //1.???????????????????????? ??????
        boolean updatePermission = updatePermission(checkedDetail);
        if(!updatePermission){
            return;
        }
        String dutyType = checkedDetail.getDutyType();//????????????
        if(dutyType.equals("0")){//????????????
            if(checkedDetail.getDutyAfterSyn().equals("0")){//???
                regularDutyF(checkedDetail);
            }
            if(checkedDetail.getDutyAfterSyn().equals("1")){//???:?????????????????????????????????(??????)
                regularDutyS(checkedDetail);
            }
        }
        if(dutyType.equals("1")){//????????????
            designatedDuty(checkedDetail);
        }
    }

    //???????????????????????????
    public void regularDutyF(CheckedDetail checkedDetail){
        denialADesignation(checkedDetail);
    }
    //????????????????????????????????????-?????????,??????ids,???????????????
    public  Object[] upArramentAndTeacher(CheckedDetail checkedDetail){
        Object[] strs = new Object[4];
        List<DutyteachersDutyarrment> watchListTeacherAll= checkedDetail.getDutyteachersDutyarrmentCheck();//??????????????????
        List<DutyteachersDutyarrment> dutyteachersDutyarrments = new ArrayList<>();
        StringBuilder  sb = new StringBuilder();//??????????????????
        StringBuilder  ids = new StringBuilder();//??????????????????id
        if(watchListTeacherAll.size()>0){//??????????????????
            for(int i=0;i<watchListTeacherAll.size();i++){
                DutyteachersDutyarrment dutyteachersDutyarrment =
                        new DutyteachersDutyarrment();
                dutyteachersDutyarrment.setId(sequenceId.nextId());
                dutyteachersDutyarrment.setDutyArrmentId(checkedDetail.getDutyArrmentId());
                dutyteachersDutyarrment.setTeacherId(watchListTeacherAll.get(i).getTeacherId());
                dutyteachersDutyarrment.setTeacherName(watchListTeacherAll.get(i).getTeacherName());
                dutyteachersDutyarrment.setTel(watchListTeacherAll.get(i).getTel());
                dutyteachersDutyarrment.setTeacherImurl(watchListTeacherAll.get(i).getTeacherImurl());
                dutyteachersDutyarrment.setCreateTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm"));
                if (sb.length() > 0) {//????????????????????????????????????????????????????????????????????????
                    sb.append(",");
                }
                if (ids.length() > 0) {//????????????????????????????????????????????????????????????????????????
                    ids.append(",");
                }
                ids.append(watchListTeacherAll.get(i).getTeacherId());
                sb.append(watchListTeacherAll.get(i).getTeacherName());
                //?????????????????????????????????
                dutyteachersDutyarrments.add(dutyteachersDutyarrment);
            }

        }

        DutyArrment dutyArrment = new DutyArrment();
        dutyArrment.setId(checkedDetail.getDutyArrmentId());
        dutyArrment.setAttendTeacher(sb.toString());
        dutyArrment.setTeacherName(checkedDetail.getDutyOfficer());
        dutyArrment.setTeacherId(checkedDetail.getDutyOfficerId());
        dutyArrment.setAttendTeacherId(ids.toString());//?????????????????????id?????????
        strs[0] = ids.toString();
        strs[1] = sb.toString();
        strs[2] = dutyteachersDutyarrments;
        strs[3] = dutyArrment;
        return strs;
    }
    //???????????????????????????
    @Transactional
    public void regularDutyS(CheckedDetail checkedDetail){

        Object[] objects = upArramentAndTeacher(checkedDetail);//1.?????????????????????????????????????????????
        checkedDetail.setPunTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm"));//2.?????????????????????(????????????id??????????????????????????????)
        List<CheckedDetail> checkDetailListData =
                checkedDetailDao.findListConditionByTime(checkedDetail);//??????????????????id????????????????????????
        String ids = (String)objects[0];
        String names = (String)objects[1];
        final List<DutyteachersDutyarrment> dutyteachersDutyarrments = (List<DutyteachersDutyarrment>) objects[2];
        DutyArrment dutyArrment = (DutyArrment)objects[3];

        //???????????????????????????id
        List<CheckedDetail> inserList = new ArrayList<>();
        checkDetailListData.stream().map(s->{
            s.setDutyOfficerId(checkedDetail.getDutyOfficerId());
            s.setDutyOfficer(checkedDetail.getDutyOfficer());
            s.setAttendTeacher(names);
            s.setAttendTeacherId(ids);
            s.setUpdateTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm"));
            s.setDutyAfterSyn("1"); return s;}).collect(Collectors.groupingBy(CheckedDetail::getRecordDate)).
                forEach((k,v)->{
                    //???????????????????????????
                    List<String> old = v.stream().map(s->s.getTeacherId()).collect(Collectors.toList());
                    //?????????????????????
                    List<String> del = old.stream().filter(t->!dutyteachersDutyarrments.stream().anyMatch(o->o.getTeacherId().equals(t))).collect(Collectors.toList());
                    //?????????????????????
                    List<DutyteachersDutyarrment> add = dutyteachersDutyarrments.stream().filter(t-> !old.stream().anyMatch(o->t.getTeacherId().equals(o))).collect(Collectors.toList());
                    //??????????????????
                    v = v.stream().filter(s->!del.contains(s.getTeacherId())).collect(Collectors.toList());
                    CheckedDetail checkedModel = v.get(0);//???????????????????????????
                    v.addAll(add.stream().map(dd->{
                        CheckedDetail cd = new CheckedDetail();
                        BeanUtils.copyProperties(checkedModel,cd);
                        cd.setId(sequenceId.nextId());
                        cd.setTeacherId(dd.getTeacherId());
                        cd.setTeacherName(dd.getTeacherName());
                        cd.setTel(dd.getTel());
                        cd.setTeacherImgul(dd.getTeacherImurl());

                        curSchoolService.setSchoolYearTerm(cd,checkedDetail.getSchoolId());//??????????????????3???????????????

                        return cd;
                    }).collect(Collectors.toList()));
                    inserList.addAll(v);
                });
           //??????????????????
        checkedDetailDao.deleteCheckedDetail4(checkedDetail);//???????????????????????????
        DutyteachersDutyarrment detail = new DutyteachersDutyarrment();
        detail.setDutyArrmentId(checkedDetail.getDutyArrmentId());
        dutyteachersDutyarrmentDao.deleteDutyteachersDutyarrmentByCondition(detail);
        dutyteachersDutyarrmentDao.batchSaveDutyteachersDutyarrment(dutyteachersDutyarrments);
        dutyArrmentDao.updateDutyArrment(dutyArrment);
        checkedDetailDao.batchSaveCheckedDetail(inserList);
    }

    //???????????????????????????????????????????????????
    @Transactional
    public void denialADesignation(CheckedDetail checkedDetail){
        //2.???????????????????????????????????????
        List<DutyteachersDutyarrment> watchListTeacherAll= checkedDetail.getDutyteachersDutyarrmentCheck();//??????????????????
        StringBuilder  sb = new StringBuilder();//??????????????????
        StringBuilder  ids = new StringBuilder();//??????????????????id
        if(watchListTeacherAll.size()>0){
            for(int i=0;i<watchListTeacherAll.size();i++){
                if (sb.length() > 0) {//????????????????????????????????????????????????????????????????????????
                    sb.append(",");
                }
                if (ids.length() > 0) {//????????????????????????????????????????????????????????????????????????
                    ids.append(",");
                }
                ids.append(watchListTeacherAll.get(i).getTeacherId());
                sb.append(watchListTeacherAll.get(i).getTeacherName());
            }
        }
        //3.??????dutyAremnetId???recordDate?????????????????????????????????
        CheckedDetail checkedDetail1 = new CheckedDetail();
        checkedDetail1.setDutyArrmentId(checkedDetail.getDutyArrmentId());
        checkedDetail1.setRecordDate(checkedDetail.getRecordDate());
        List<CheckedDetail> checkedDetailList = checkedDetailDao.findCheckedDetailListByCondition(checkedDetail1);
        if(checkedDetailList.isEmpty()){
            return;
        }
        //4.????????????checkedDetailList???id
        //List<String> delIds = checkedDetailList.stream().map(s->s.getId()).collect(Collectors.toList());
        //5.????????????
        CheckedDetail checkedDetai2 = checkedDetailList.get(0);
        //6.?????????????????????
        List<CheckedDetail> checkedDetails = new ArrayList<>();
        for(int z=0;z<watchListTeacherAll.size();z++){
            DutyteachersDutyarrment dutyteachersDutyarrment = watchListTeacherAll.get(z);
            CheckedDetail checkedDetail11 = new CheckedDetail();
            BeanUtils.copyProperties(checkedDetai2,checkedDetail11);
            checkedDetail11.setId(sequenceId.nextId());
            checkedDetail11.setAttendTeacherId(ids.toString());
            checkedDetail11.setAttendTeacher(sb.toString());
            checkedDetail11.setDutyOfficer(checkedDetail.getDutyOfficer());
            checkedDetail11.setDutyOfficerId(checkedDetail.getDutyOfficerId());
            checkedDetail11.setTeacherId(dutyteachersDutyarrment.getTeacherId());
            checkedDetail11.setTeacherName(dutyteachersDutyarrment.getTeacherName());
            checkedDetail11.setTeacherImgul(dutyteachersDutyarrment.getTeacherImurl());
            checkedDetail11.setTel(dutyteachersDutyarrment.getTel());
            checkedDetail11.setCreateTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm"));
            checkedDetail11.setDutyAfterSyn("0");

            curSchoolService.setSchoolYearTerm(checkedDetail11,checkedDetail11.getSchoolId());//??????????????????3???????????????

            checkedDetails.add(checkedDetail11);
        }
        //7.????????????
        CheckedDetail detailCheckDetail = new CheckedDetail();
        detailCheckDetail.setDutyArrmentId(checkedDetail.getDutyArrmentId());
        detailCheckDetail.setRecordDate(checkedDetail.getRecordDate());
        checkedDetailDao.deleteCheckedDetail4LikeF(detailCheckDetail);
        //8.????????????
        checkedDetailDao.batchSaveCheckedDetail(checkedDetails);
    }
    //????????????
    public void  designatedDuty(CheckedDetail checkedDetail){
        denialADesignation(checkedDetail);
    }

    //????????????????????????
    public boolean updatePermission(CheckedDetail checkedDetail){
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(checkedDetail.getPunchTimeLater()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if((new Date().getTime())>=(c.getTimeInMillis())){//????????????????????????
            checkedDetail.setCode("204");//???????????????????????????????????????
            checkedDetail.setMsg("???????????????????????????,????????????????????????");//???????????????????????????
            return false;
        }
        return  true;
    }

    //????????????
    @Transactional(readOnly = true)
    public boolean isExist(CheckedDetail checkedDetail,CheckedDetail one){
        if(!checkedDetail.getChangedDutye().isEmpty()){
            CheckedDetail checkedDetail1 = new CheckedDetail();
            checkedDetail1.setRecordDate(checkedDetail.getChangedDutye());//????????????
            checkedDetail1.setDutyArrmentSpace(one.getDutyArrmentSpace());//????????????
            checkedDetail1.setDutyTimeStart(one.getDutyTimeStart());//??????????????????
            checkedDetail1.setDutyTimeEnd(one.getDutyTimeEnd());//??????????????????
            long count = checkedDetailDao.findCheckedDetailCountByCondition(checkedDetail1);
            if(count>0){
                checkedDetail.setCode("204");//???????????????????????????????????????
                checkedDetail.setMsg(checkedDetail.getChangedDutye()+"????????????????????????," +
                        "????????????????????????");//???????????????????????????
                return false;
            }
        }
        return  true;
    }
}
