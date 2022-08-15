package com.yice.edu.cn.xw.service.dutyArrment;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.xw.checkedDetail.CheckedDetail;
import com.yice.edu.cn.common.pojo.xw.dutyArrment.DutyArrment;
import com.yice.edu.cn.common.pojo.xw.dutyteachersDutyarrment.DutyteachersDutyarrment;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import com.yice.edu.cn.common.util.weekDayUtil.WeekDayUtil;
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
public class DutyArrmentService {
    @Autowired
    private ICheckedDetailDao checkedDetailDao;
    @Autowired
    private IDutyteachersDutyarrmentDao dutyteachersDutyarrmentDao;

    @Autowired
    private IDutyArrmentDao dutyArrmentDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private CurSchoolYearService curSchoolService;

    @Transactional(readOnly = true)
    public DutyArrment findDutyArrmentById(String id) {
        return dutyArrmentDao.findDutyArrmentById(id);
    }

    @Transactional(readOnly = true)
    public boolean plansIsRepetitive(DutyArrment dutyArrment){//保存值班计划是否重复rdutyStartDate
        DutyArrment  dutyArrmentCheck = new DutyArrment();
        BeanUtils.copyProperties(dutyArrment,dutyArrmentCheck);
        dutyArrmentCheck.setPunchTime(null);
        dutyArrmentCheck.setTeacherId(null);
        dutyArrmentCheck.setTeacherName(null);
        dutyArrmentCheck.setAttendTeacherId(null);
        dutyArrmentCheck.setAttendTeacher(null);

        long n = dutyArrmentDao.findDutyArrmentCountByCondition(dutyArrmentCheck);
        //为了减少查询的数据量，通过新增pager减少查询的字段
        dutyArrmentCheck.setRdutyStartDate(null);
        dutyArrmentCheck.setRdutyEndDate(null);

        Pager pager = new Pager();
        pager.setPaging(false);
        String[] str = new String[]{"rdutyStartDate","rdutyEndDate"};
        pager.setIncludes(str);
        dutyArrmentCheck.setPager(pager);
        List<DutyArrment> listByCondition = dutyArrmentDao.findDutyArrmentListByCondition(dutyArrmentCheck);

        //long n = listByCondition.size();
        if(n>0){
            if(dutyArrment.getDutyType().equals("0")){//常规值班
                listByCondition = listByCondition.stream().filter(s->{//判断值班计划是否重叠
                    Date s1 = DateUtil.parse(s.getRdutyStartDate(), "yyyy-MM-dd");
                    Date s2 = DateUtil.parse(s.getRdutyEndDate(), "yyyy-MM-dd");
                    Date s3 = DateUtil.parse(dutyArrment.getRdutyStartDate(), "yyyy-MM-dd");
                    Date s4 = DateUtil.parse(dutyArrment.getRdutyEndDate(), "yyyy-MM-dd");
                    if(s1.after(s4)|| s2.before(s3)){
                        return true;
                    }
                    return  false;
                }).collect(Collectors.toList());
                if(listByCondition.size()==0){
                    dutyArrment.setCode("204");
                    dutyArrment.setMsg("值班计划有重叠");
                    return false;
                }
            }
            dutyArrment.setCode("204");
            dutyArrment.setMsg("已有值班记录");
            return false;
        }
        return  true;
    }
    @Transactional
    public void saveDutyArrment(DutyArrment dutyArrment) {
        //1.判断是否重复
        boolean isRepetitive = plansIsRepetitive(dutyArrment);
        if(!isRepetitive){
            return;
        }
        if(dutyArrment.getDutyType().equals("0")){//常规值班
            String[] co = WeekDayUtil.getDates(dutyArrment.getRdutyStartDate(),dutyArrment.getRdutyEndDate(),dutyArrment.getDutyDay());
            if(co.length==0){
                dutyArrment.setCode("204");
                dutyArrment.setCode(dutyArrment.getRdutyEndDate()+"至"+dutyArrment.getDutyDay()+"包含"
                        +co.length+"个"+dutyArrment.getDutyDay()+"所以已添加0条值班明细");
                return;
            }
        }
        //2.值班安排和关联表
        dutyArrment.setId(sequenceId.nextId());
        List<DutyteachersDutyarrment> watchListTeacherAll= dutyArrment.getDutyteachersDutyarrmentCheck();//得到值班人员
        List<DutyteachersDutyarrment> dutyteachersDutyarrments = new ArrayList<>();
        StringBuilder  sb = new StringBuilder();//拼接值班人员
        StringBuilder  ids = new StringBuilder();//拼接值班人员id
        if(watchListTeacherAll.size()>0){//插入关联表中
            for(int i=0;i<watchListTeacherAll.size();i++){
                DutyteachersDutyarrment dutyteachersDutyarrment =
                    new DutyteachersDutyarrment();
                dutyteachersDutyarrment.setId(sequenceId.nextId());
                dutyteachersDutyarrment.setDutyArrmentId(dutyArrment.getId());
                dutyteachersDutyarrment.setTeacherId(watchListTeacherAll.get(i).getTeacherId());
                dutyteachersDutyarrment.setTeacherName(watchListTeacherAll.get(i).getTeacherName());
                dutyteachersDutyarrment.setTel(watchListTeacherAll.get(i).getTel());
                dutyteachersDutyarrment.setTeacherImurl(watchListTeacherAll.get(i).getTeacherImurl());
                dutyteachersDutyarrment.setCreateTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm"));
                //值班关联对象存到容器中
                dutyteachersDutyarrments.add(dutyteachersDutyarrment);
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
        dutyArrment.setAttendTeacher(sb.toString());
        dutyArrment.setAttendTeacherId(ids.toString());//参与值班教师的id字符串
        CheckedDetail checkedDetail = new CheckedDetail();
        checkedDetail.setDutyArrmentId(dutyArrment.getId());
        dutyteachersDutyarrmentDao.batchSaveDutyteachersDutyarrment(dutyteachersDutyarrments);
        //需要立即生效所以联动值班模板表，关联表，值班明细表
        dutyArrmentDao.saveDutyArrment(dutyArrment);//模板表

        List<CheckedDetail> checkedDetailList =
                checkedDetailDao.findDutyArrmentListByConditionForLike(checkedDetail);//用关联查询添加明细表信息
        List<CheckedDetail> checkedDetailListNew = new ArrayList<>();

        if(checkedDetailList.size()>0){
            for(int i=0;i<checkedDetailList.size();i++){//具体安排时间
                checkedDetailList.get(i).setCreateTime(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm"));
                checkedDetailList.get(i).setSignIn("0");//默认未签到

                if(dutyArrment.getDutyType().equals("0")){//常规值班
                    String[] ymds = WeekDayUtil.getDates(dutyArrment.getRdutyStartDate(),dutyArrment.getRdutyEndDate(),dutyArrment.getDutyDay());
                    for(int j=0;j<ymds.length;j++){
                        CheckedDetail checkedDetail1 = new CheckedDetail();
                        BeanUtils.copyProperties(checkedDetailList.get(i),checkedDetail1);
                        checkedDetail1.setId(sequenceId.nextId());//签到表的id
                        checkedDetail1.setRecordDate(ymds[j]);//记录日期(给app用)
                        //允许打卡的最早时间
                        Calendar cal = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        try {
                            cal.setTime(sdf.parse(ymds[j]+" "+checkedDetail1.getDutyTimeStart()));
                            int minute = Integer.parseInt(checkedDetail1.getPunchTime());
                            cal.add(Calendar.MINUTE,-minute);
                            checkedDetail1.setPunchTimeLater(sdf.format(cal.getTime()));//保存格式为:"yyyy-MM-dd HH:mm"
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        checkedDetail1.setDutyAfterSyn("0");//默认为不同步

                        curSchoolService.setSchoolYearTerm(checkedDetail1,checkedDetail1.getSchoolId());//添加学校学年3个字段的值

                        checkedDetailListNew.add(checkedDetail1);
                    }

                }
                if(dutyArrment.getDutyType().equals("1")){//指定值班
                    checkedDetailList.get(i).setId(sequenceId.nextId());//签到表的id
                    checkedDetailList.get(i).setRecordDate(dutyArrment.getAppointedDate());
                    //允许打卡的最早时间
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    try {
                        cal.setTime(sdf.parse(checkedDetailList.get(i).getRecordDate()+" "+checkedDetailList.get(i).getDutyTimeStart()));
                        int minute = Integer.parseInt(checkedDetailList.get(i).getPunchTime());
                        cal.add(Calendar.MINUTE,-minute);
                        checkedDetailList.get(i).setPunchTimeLater(sdf.format(cal.getTime()));//保存格式为:"yyyy-MM-dd HH:mm"
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    CheckedDetail checkedDetail1 = checkedDetailList.get(i);
                    checkedDetail1.setDutyAfterSyn("0");//默认为不同步

                    curSchoolService.setSchoolYearTerm(checkedDetail1,checkedDetail1.getSchoolId());//添加学校学年3个字段的值

                    checkedDetailListNew.add(checkedDetailList.get(i));
                }
            }
            if(!checkedDetailListNew.isEmpty()){
                //生成签到明细
                checkedDetailDao.batchSaveCheckedDetail(checkedDetailListNew);
                return;
            }else{
                dutyArrment.setCode("204");
                dutyArrment.setCode("已添加0条");
            }
            return;
        }
    }
    @Transactional(readOnly = true)
    public List<DutyArrment> findDutyArrmentListByCondition(DutyArrment dutyArrment) {
        return dutyArrmentDao.findDutyArrmentListByCondition(dutyArrment);
    }
    @Transactional(readOnly = true)
    public DutyArrment findOneDutyArrmentByCondition(DutyArrment dutyArrment) {
        return dutyArrmentDao.findOneDutyArrmentByCondition(dutyArrment);
    }
    @Transactional(readOnly = true)
    public long findDutyArrmentCountByCondition(DutyArrment dutyArrment) {
        return dutyArrmentDao.findDutyArrmentCountByCondition(dutyArrment);
    }
    @Transactional
    public void updateDutyArrment(DutyArrment dutyArrment) {
        //只修改值班安排的状态启用还是禁用
        DutyArrment dutyArrmentCheck = new DutyArrment();
        dutyArrmentCheck.setId(dutyArrment.getId());
        dutyArrmentDao.updateDutyArrment(dutyArrment);
    }
    @Transactional
    public void deleteDutyArrment(String id) {
        //1.先删除值班安排和教师的关联表
        DutyteachersDutyarrment dutyteachersDutyarrment =
                new DutyteachersDutyarrment();
        dutyteachersDutyarrment.setDutyArrmentId(id);//先删除关联表相关数据
        //2.删除值班安排
        dutyArrmentDao.deleteDutyArrment(id);
    }
    @Transactional
    public void deleteDutyArrmentByCondition(DutyArrment dutyArrment) {
        //1.先删除值班安排和教师的关联表
        DutyteachersDutyarrment dutyteachersDutyarrment =
                new DutyteachersDutyarrment();
        dutyteachersDutyarrment.setDutyArrmentId(dutyArrment.getId());//先删除关联表相关数据
        dutyteachersDutyarrmentDao.deleteDutyteachersDutyarrmentByCondition(dutyteachersDutyarrment);
        //2.删除值班安排
        dutyArrmentDao.deleteDutyArrmentByCondition(dutyArrment);
    }
    @Transactional
    public void batchSaveDutyArrment(List<DutyArrment> dutyArrments){
        dutyArrments.forEach(dutyArrment -> dutyArrment.setId(sequenceId.nextId()));
        dutyArrmentDao.batchSaveDutyArrment(dutyArrments);
    }

    public List<DutyArrment> findDutyArrmentListByConditionForLike(DutyArrment dutyArrment) {
        return dutyArrmentDao.findDutyArrmentListByConditionForLike(dutyArrment);
    }

    public long findDutyArrmentCountByConditionForLike(DutyArrment dutyArrment) {
        return dutyArrmentDao.findDutyArrmentCountByConditionForLike(dutyArrment);
    }
}
