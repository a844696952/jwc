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

    //for tap查询
    @Transactional(readOnly = true)
    public List<CheckedDetail> findCheckedDetailListForAppTapByCondition(CheckedDetail checkedDetail){
        return checkedDetailDao.findCheckedDetailListForAppTapByCondition(checkedDetail);
    }

    //tap校长查询
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
        //判断变更日期有没有重复
        boolean exist = isExist(checkedDetail,one);
        if(!exist){
            return;
        }
        //判断是否允许修改
        boolean updatePermission = updatePermission(checkedDetail);
        if(!updatePermission){
            return;
        }
        //都符合条件继续变更操作
        List<CheckedDetail> checkedDetailListByCondition = checkedDetailDao.findCheckedDetailListByCondition(checkedDetail);
        List<String> delIds = checkedDetailListByCondition.stream().map(s->s.getId()).collect(Collectors.toList());
        if(!delIds.isEmpty()){
            String[] arry = null;
            String puchTimeLaterNew =//最新的打卡时间
                    DoTime.getPuchLater(checkedDetail.getChangedDutye(),one.getDutyTimeStart(),one.getPunchTime());
            arry = DoTime.getDutyDayAndId(checkedDetail.getChangedDutye());//更新星期几和id
            String dutyDayNew = arry[0];
            String dutyDayIdNew = arry[1];
            //进行更改可更改的记录
            CheckedDetail checkedDetailUpdate = new CheckedDetail();
            checkedDetailUpdate.setDelIds(delIds);//批量处理id集合
            checkedDetailUpdate.setPuchTimeLaterNew(puchTimeLaterNew);
            checkedDetailUpdate.setDutyDayNew(dutyDayNew);
            checkedDetailUpdate.setDutyDayIdNew(dutyDayIdNew);
            checkedDetailUpdate.setRecordDate(checkedDetail.getChangedDutye());

            curSchoolService.setSchoolYearTerm(checkedDetailUpdate,checkedDetail.getSchoolId());//添加学校学年3个字段的值

            checkedDetailDao.updateCheckedDetailBatch4Like(checkedDetailUpdate);
        }else{
            checkedDetail.setCode("204");//值班明细活动开始了不可更改
            checkedDetail.setMsg("值班明细活动已开始,不能对此变更操作");//值班明细活动已开始
            return;
        }
    }

    //根据条件删除
    @Transactional
    public void deleteCheckedDetailByCondition4Like(CheckedDetail checkedDetail) {
        //1.判断是否可以修改 操作
        boolean updatePermission = updatePermission(checkedDetail);
        if(!updatePermission){
            return;
        }
        List<CheckedDetail> checkedDetailLists = checkedDetailDao.findCheckedDetailListByCondition(checkedDetail);
        List<String> delIds = checkedDetailLists.stream().map(s->s.getId()).collect(Collectors.toList());//获取可以操作的ids
        if(!delIds.isEmpty()){
            checkedDetailDao.deleteCheckedDetail4Like(delIds);//值班明细
        }else{
            checkedDetail.setCode("204");
            checkedDetail.setMsg("值班明细都已进行，不允许进行删除操作");
            return;
        }
    }


    @Transactional
    public void updateTochangeCheckedDetail4Like(CheckedDetail checkedDetail) {
        //1.判断是否可以修改 操作
        boolean updatePermission = updatePermission(checkedDetail);
        if(!updatePermission){
            return;
        }
        String dutyType = checkedDetail.getDutyType();//值班类型
        if(dutyType.equals("0")){//常规值班
            if(checkedDetail.getDutyAfterSyn().equals("0")){//否
                regularDutyF(checkedDetail);
            }
            if(checkedDetail.getDutyAfterSyn().equals("1")){//是:是否同步之后的常规值班(多条)
                regularDutyS(checkedDetail);
            }
        }
        if(dutyType.equals("1")){//指定值班
            designatedDuty(checkedDetail);
        }
    }

    //常规值班：否的情况
    public void regularDutyF(CheckedDetail checkedDetail){
        denialADesignation(checkedDetail);
    }
    //维护值班计划表和值班计划-教师表,返回ids,名单，对象
    public  Object[] upArramentAndTeacher(CheckedDetail checkedDetail){
        Object[] strs = new Object[4];
        List<DutyteachersDutyarrment> watchListTeacherAll= checkedDetail.getDutyteachersDutyarrmentCheck();//得到值班人员
        List<DutyteachersDutyarrment> dutyteachersDutyarrments = new ArrayList<>();
        StringBuilder  sb = new StringBuilder();//拼接值班人员
        StringBuilder  ids = new StringBuilder();//拼接值班人员id
        if(watchListTeacherAll.size()>0){//插入关联表中
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
                if (sb.length() > 0) {//该步即不会第一位有逗号，也防止最后一位拼接逗号！
                    sb.append(",");
                }
                if (ids.length() > 0) {//该步即不会第一位有逗号，也防止最后一位拼接逗号！
                    ids.append(",");
                }
                ids.append(watchListTeacherAll.get(i).getTeacherId());
                sb.append(watchListTeacherAll.get(i).getTeacherName());
                //值班关联对象存到容器中
                dutyteachersDutyarrments.add(dutyteachersDutyarrment);
            }

        }

        DutyArrment dutyArrment = new DutyArrment();
        dutyArrment.setId(checkedDetail.getDutyArrmentId());
        dutyArrment.setAttendTeacher(sb.toString());
        dutyArrment.setTeacherName(checkedDetail.getDutyOfficer());
        dutyArrment.setTeacherId(checkedDetail.getDutyOfficerId());
        dutyArrment.setAttendTeacherId(ids.toString());//参与值班教师的id字符串
        strs[0] = ids.toString();
        strs[1] = sb.toString();
        strs[2] = dutyteachersDutyarrments;
        strs[3] = dutyArrment;
        return strs;
    }
    //常规值班：是的情况
    @Transactional
    public void regularDutyS(CheckedDetail checkedDetail){

        Object[] objects = upArramentAndTeacher(checkedDetail);//1.根据页面传来的教师得到相关信息
        checkedDetail.setPunTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm"));//2.值班明细表修改(根据值班id取出未值班明细的记录)
        List<CheckedDetail> checkDetailListData =
                checkedDetailDao.findListConditionByTime(checkedDetail);//根据值班计划id筛选出大于今天的
        String ids = (String)objects[0];
        String names = (String)objects[1];
        final List<DutyteachersDutyarrment> dutyteachersDutyarrments = (List<DutyteachersDutyarrment>) objects[2];
        DutyArrment dutyArrment = (DutyArrment)objects[3];

        //定义新值班参与人员id
        List<CheckedDetail> inserList = new ArrayList<>();
        checkDetailListData.stream().map(s->{
            s.setDutyOfficerId(checkedDetail.getDutyOfficerId());
            s.setDutyOfficer(checkedDetail.getDutyOfficer());
            s.setAttendTeacher(names);
            s.setAttendTeacherId(ids);
            s.setUpdateTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm"));
            s.setDutyAfterSyn("1"); return s;}).collect(Collectors.groupingBy(CheckedDetail::getRecordDate)).
                forEach((k,v)->{
                    //获取原有的值班人员
                    List<String> old = v.stream().map(s->s.getTeacherId()).collect(Collectors.toList());
                    //定义删减的人员
                    List<String> del = old.stream().filter(t->!dutyteachersDutyarrments.stream().anyMatch(o->o.getTeacherId().equals(t))).collect(Collectors.toList());
                    //定义新增的人员
                    List<DutyteachersDutyarrment> add = dutyteachersDutyarrments.stream().filter(t-> !old.stream().anyMatch(o->t.getTeacherId().equals(o))).collect(Collectors.toList());
                    //进行增减操作
                    v = v.stream().filter(s->!del.contains(s.getTeacherId())).collect(Collectors.toList());
                    CheckedDetail checkedModel = v.get(0);//当天的值班记录明细
                    v.addAll(add.stream().map(dd->{
                        CheckedDetail cd = new CheckedDetail();
                        BeanUtils.copyProperties(checkedModel,cd);
                        cd.setId(sequenceId.nextId());
                        cd.setTeacherId(dd.getTeacherId());
                        cd.setTeacherName(dd.getTeacherName());
                        cd.setTel(dd.getTel());
                        cd.setTeacherImgul(dd.getTeacherImurl());

                        curSchoolService.setSchoolYearTerm(cd,checkedDetail.getSchoolId());//添加学校学年3个字段的值

                        return cd;
                    }).collect(Collectors.toList()));
                    inserList.addAll(v);
                });
           //对数据库操作
        checkedDetailDao.deleteCheckedDetail4(checkedDetail);//删除符合条件的明细
        DutyteachersDutyarrment detail = new DutyteachersDutyarrment();
        detail.setDutyArrmentId(checkedDetail.getDutyArrmentId());
        dutyteachersDutyarrmentDao.deleteDutyteachersDutyarrmentByCondition(detail);
        dutyteachersDutyarrmentDao.batchSaveDutyteachersDutyarrment(dutyteachersDutyarrments);
        dutyArrmentDao.updateDutyArrment(dutyArrment);
        checkedDetailDao.batchSaveCheckedDetail(inserList);
    }

    //指定和常规否的情况都为同种处理情况
    @Transactional
    public void denialADesignation(CheckedDetail checkedDetail){
        //2.获取值班计划和教师关联数据
        List<DutyteachersDutyarrment> watchListTeacherAll= checkedDetail.getDutyteachersDutyarrmentCheck();//得到值班人员
        StringBuilder  sb = new StringBuilder();//拼接值班人员
        StringBuilder  ids = new StringBuilder();//拼接值班人员id
        if(watchListTeacherAll.size()>0){
            for(int i=0;i<watchListTeacherAll.size();i++){
                if (sb.length() > 0) {//该步即不会第一位有逗号，也防止最后一位拼接逗号！
                    sb.append(",");
                }
                if (ids.length() > 0) {//该步即不会第一位有逗号，也防止最后一位拼接逗号！
                    ids.append(",");
                }
                ids.append(watchListTeacherAll.get(i).getTeacherId());
                sb.append(watchListTeacherAll.get(i).getTeacherName());
            }
        }
        //3.根据dutyAremnetId和recordDate查出符合条件的值班明细
        CheckedDetail checkedDetail1 = new CheckedDetail();
        checkedDetail1.setDutyArrmentId(checkedDetail.getDutyArrmentId());
        checkedDetail1.setRecordDate(checkedDetail.getRecordDate());
        List<CheckedDetail> checkedDetailList = checkedDetailDao.findCheckedDetailListByCondition(checkedDetail1);
        if(checkedDetailList.isEmpty()){
            return;
        }
        //4.获取所有checkedDetailList的id
        //List<String> delIds = checkedDetailList.stream().map(s->s.getId()).collect(Collectors.toList());
        //5.获取模板
        CheckedDetail checkedDetai2 = checkedDetailList.get(0);
        //6.获取最新的数据
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

            curSchoolService.setSchoolYearTerm(checkedDetail11,checkedDetail11.getSchoolId());//添加学校学年3个字段的值

            checkedDetails.add(checkedDetail11);
        }
        //7.允许删除
        CheckedDetail detailCheckDetail = new CheckedDetail();
        detailCheckDetail.setDutyArrmentId(checkedDetail.getDutyArrmentId());
        detailCheckDetail.setRecordDate(checkedDetail.getRecordDate());
        checkedDetailDao.deleteCheckedDetail4LikeF(detailCheckDetail);
        //8.批量新增
        checkedDetailDao.batchSaveCheckedDetail(checkedDetails);
    }
    //指定值班
    public void  designatedDuty(CheckedDetail checkedDetail){
        denialADesignation(checkedDetail);
    }

    //判断是否允许修改
    public boolean updatePermission(CheckedDetail checkedDetail){
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(checkedDetail.getPunchTimeLater()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if((new Date().getTime())>=(c.getTimeInMillis())){//值班明细已被使用
            checkedDetail.setCode("204");//值班明细活动开始了不可更改
            checkedDetail.setMsg("值班明细活动已开始,不能对此修改操作");//值班明细活动已开始
            return false;
        }
        return  true;
    }

    //变更判重
    @Transactional(readOnly = true)
    public boolean isExist(CheckedDetail checkedDetail,CheckedDetail one){
        if(!checkedDetail.getChangedDutye().isEmpty()){
            CheckedDetail checkedDetail1 = new CheckedDetail();
            checkedDetail1.setRecordDate(checkedDetail.getChangedDutye());//修改日期
            checkedDetail1.setDutyArrmentSpace(one.getDutyArrmentSpace());//值班地点
            checkedDetail1.setDutyTimeStart(one.getDutyTimeStart());//值班开始时间
            checkedDetail1.setDutyTimeEnd(one.getDutyTimeEnd());//值班结束时间
            long count = checkedDetailDao.findCheckedDetailCountByCondition(checkedDetail1);
            if(count>0){
                checkedDetail.setCode("204");//值班明细活动开始了不可更改
                checkedDetail.setMsg(checkedDetail.getChangedDutye()+"日已经有值班明细," +
                        "不能对此变更操作");//值班明细活动已开始
                return false;
            }
        }
        return  true;
    }
}
