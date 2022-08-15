package com.yice.edu.cn.xw.service.dormManage.houseApplican;

import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.*;
import com.yice.edu.cn.xw.dao.dormManage.dorm.IDormPersonDao;
import com.yice.edu.cn.xw.dao.dormManage.houseApplican.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class HouseApplicanService {
    @Autowired
    private IHouseApplicanDao houseApplicanDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private IHouseApplicanFilesDao houseApplicanFilesDao;
    @Autowired
    private IHouseApplicanStudentsDao houseApplicanStudentsDao;
    @Autowired
    private IHouseApplicanTeachersDao houseApplicanTeachersDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private IDormPersonDao dormPersonDao;

    @Transactional(readOnly = true)
    public HouseApplican findHouseApplicanById(String id) {
        return houseApplicanDao.findHouseApplicanById(id);
    }
    @Transactional
    public void saveHouseApplican(HouseApplican houseApplican) {
        houseApplican.setId(sequenceId.nextId());
        houseApplicanDao.saveHouseApplican(houseApplican);

        if (houseApplican.getInitiatePort().equals("0")){
            List<HouseApplicanStudents> houseApplicanStudents = houseApplican.getHouseApplicanStudents();
            //保存学生对象
            if (houseApplicanStudents.size() > 0 ){
                houseApplicanStudents.forEach(houseApplicanStudent ->{
                    houseApplicanStudent.setId(sequenceId.nextId());
                    houseApplicanStudent.setSchoolId(houseApplican.getSchoolId());
                    houseApplicanStudent.setHouseApplicanId(houseApplican.getId());
                    houseApplicanStudent.setType("0");
                });
            houseApplicanStudentsDao.batchSaveHouseApplicanStudents(houseApplicanStudents);
            }
        }
        //家长端
        if (houseApplican.getInitiatePort().equals("1")){
            HouseApplicanStudents houseApplicanStudents = new HouseApplicanStudents();
            houseApplicanStudents.setId(sequenceId.nextId());
            houseApplicanStudents.setSchoolId(houseApplican.getSchoolId());
            houseApplicanStudents.setHouseApplicanId(houseApplican.getId());
            houseApplicanStudents.setType("0");
            houseApplicanStudents.setStudentId(houseApplican.getStudentsId());
            houseApplicanStudentsDao.saveHouseApplicanStudents(houseApplicanStudents);
        }

         //保存附件
        if (houseApplican.getHouseApplicanFiles()!= null){
            List<HouseApplicanFiles> houseApplicanFiles = houseApplican.getHouseApplicanFiles();
            if (houseApplicanFiles.size()>0){
                houseApplicanFiles.forEach(houseApplicanFile ->{
                    houseApplicanFile.setId(sequenceId.nextId());
                    houseApplicanFile.setSchoolId(houseApplican.getSchoolId());
                    houseApplicanFile.setHouseApplicanId(houseApplican.getId());
                });
            houseApplicanFilesDao.batchSaveHouseApplicanFiles(houseApplicanFiles);
            }
        }

        //教师端提交
        if (houseApplican.getInitiatePort().equals("0")){
            HouseApplicanTeachers houseApplicanTeachers = new HouseApplicanTeachers();
            for (int i = 0 ; i < houseApplican.getHouseApplicanTeachers().size();i++){
                houseApplicanTeachers.setId(sequenceId.nextId());
                houseApplicanTeachers.setSchoolId(houseApplican.getSchoolId());
                houseApplicanTeachers.setHouseApplicanId(houseApplican.getId());
                houseApplicanTeachers.setType("0");   //是否为宿管老师   0否   1是
                houseApplicanTeachers.setStatus("0");  //当前教师的审核状态 0待审核  1通过  2驳回
                houseApplicanTeachers.setTeacherId(houseApplican.getHouseApplicanTeachers().get(i));
                houseApplicanTeachers.setSort(String.valueOf(i));
                if (i == houseApplican.getHouseApplicanTeachers().size()-1){
                    houseApplicanTeachers.setNextSort("99");
                    houseApplicanTeachers.setType("1");
                }else{
                    houseApplicanTeachers.setNextSort(String.valueOf(i+1));
                    houseApplicanTeachers.setType("0");
                }
                houseApplicanTeachersDao.saveHouseApplicanTeachers(houseApplicanTeachers);
            }

            this.dormPushMsg(houseApplican,1);
        }
        //家长端提交
        if (houseApplican.getInitiatePort().equals("1")){
            HouseApplicanTeachers houseApplicanTeachers = new HouseApplicanTeachers();
            houseApplicanTeachers.setHouseApplicanId(houseApplican.getId());
            houseApplicanTeachers.setSchoolId(houseApplican.getSchoolId());
            houseApplicanTeachers.setId(sequenceId.nextId());
            houseApplicanTeachers.setStatus("0");
            houseApplicanTeachers.setType("0");
            houseApplicanTeachers.setTeacherId(houseApplican.getHeadTeacherId());
            //提交到班主任
            houseApplicanTeachers.setSort("0");
            houseApplicanTeachers.setNextSort("1");
            houseApplicanTeachersDao.saveHouseApplicanTeachers(houseApplicanTeachers);

            this.dormPushMsg(houseApplican,2);
        }


    }

    @Transactional(readOnly = true)
    public List<HouseApplican> findHouseApplicanListByCondition(HouseApplican houseApplican) {
        //添加在某某时间段查询
        String[] searchTimeZone = houseApplican.getSearchTimeZone();
        if (searchTimeZone!=null&&searchTimeZone.length>0){
            houseApplican.setSearchStearTime(searchTimeZone[0]);
            houseApplican.setSearchEndTime(searchTimeZone[1]);
        }
        return houseApplicanDao.findHouseApplicanListByCondition(houseApplican);
    }
    @Transactional(readOnly = true)
    public HouseApplican findOneHouseApplicanByCondition(HouseApplican houseApplican) {
        return houseApplicanDao.findOneHouseApplicanByCondition(houseApplican);
    }
    @Transactional(readOnly = true)
    public long findHouseApplicanCountByCondition(HouseApplican houseApplican) {
        String[] searchTimeZone = houseApplican.getSearchTimeZone();
        if (searchTimeZone!=null&&searchTimeZone.length>0){
            houseApplican.setSearchStearTime(searchTimeZone[0]);
            houseApplican.setSearchEndTime(searchTimeZone[1]);
        }
        return houseApplicanDao.findHouseApplicanCountByCondition(houseApplican);
    }
    @Transactional
    public void updateHouseApplican(HouseApplican houseApplican) {
        houseApplicanDao.updateHouseApplican(houseApplican);
    }
    @Transactional
    public void deleteHouseApplican(String id) {
        houseApplicanDao.deleteHouseApplican(id);
    }
    @Transactional
    public void deleteHouseApplicanByCondition(HouseApplican houseApplican) {
        houseApplicanDao.deleteHouseApplicanByCondition(houseApplican);
    }
    @Transactional
    public void batchSaveHouseApplican(List<HouseApplican> houseApplicans){
        houseApplicans.forEach(houseApplican -> houseApplican.setId(sequenceId.nextId()));
        houseApplicanDao.batchSaveHouseApplican(houseApplicans);
    }


    @Transactional(readOnly = true)
    public List<HouseApplican> findApprovalPending(HouseApplican houseApplican) {
        //添加在某某时间段查询
        String[] searchTimeZone = houseApplican.getSearchTimeZone();
        if (searchTimeZone!=null&&searchTimeZone.length>0){
            houseApplican.setSearchStearTime(searchTimeZone[0]);
            houseApplican.setSearchEndTime(searchTimeZone[1]);
        }
        return houseApplicanDao.findApprovalPending(houseApplican);
    }
    @Transactional(readOnly = true)
    public long findApprovalPendingCount(HouseApplican houseApplican) {
        return houseApplicanDao.findApprovalPendingCount(houseApplican);
    }

    @Transactional(readOnly = true)
    public List<HouseApplican> findPassPending(HouseApplican houseApplican) {
        //添加在某某时间段查询
        String[] searchTimeZone = houseApplican.getSearchTimeZone();
        if (searchTimeZone!=null&&searchTimeZone.length>0){
            houseApplican.setSearchStearTime(searchTimeZone[0]);
            houseApplican.setSearchEndTime(searchTimeZone[1]);
        }
        return houseApplicanDao.findPassPending(houseApplican);
    }
    @Transactional(readOnly = true)
    public long findPassPendingCount(HouseApplican houseApplican) {
        String[] searchTimeZone = houseApplican.getSearchTimeZone();
        if (searchTimeZone!=null&&searchTimeZone.length>0){
            houseApplican.setSearchStearTime(searchTimeZone[0]);
            houseApplican.setSearchEndTime(searchTimeZone[1]);
        }
        return houseApplicanDao.findPassPendingCount(houseApplican);
    }


    /**
     * 通过 TeacherStatus  判断前端点的是 同意 还是 驳回
     * @param houseApplican
     */
    @Transactional
    public void saveHouseApplicanFromParent(HouseApplican houseApplican) {
        //家长端提交后 班主任拒绝
        if (houseApplican.getTeacherStatus().equals("2")){
            HouseApplicanTeachers houseApplicanTeachers = new HouseApplicanTeachers();
            houseApplicanTeachers.setHouseApplicanId(houseApplican.getId());
            houseApplicanTeachers.setSchoolId(houseApplican.getSchoolId());
            houseApplicanTeachers.setRemark(houseApplican.getTeacherRemark());
            houseApplicanTeachers.setStatus("2");
            houseApplicanTeachersDao.updateHouseApplicanTeachers1(houseApplicanTeachers);
            houseApplican.setStatus("2");
            houseApplican.setProgress(houseApplican.getTeacherNextSort());
            houseApplicanDao.updateHouseApplican(houseApplican);
            this.dormPushMsg(houseApplican,3);
        }
        //家长端提交后 班主任同意
        if (houseApplican.getTeacherStatus().equals("1")){
            //申请表状态更改
            houseApplican.setProgress("1");
            houseApplicanDao.updateHouseApplican(houseApplican);
            //同意后 当前教师表 修改状态
            HouseApplicanTeachers houseApplicanTeachers1 = new HouseApplicanTeachers();
            if (houseApplican.getTeacherRemark() != null){
                houseApplicanTeachers1.setRemark(houseApplican.getTeacherRemark());
            }
            houseApplicanTeachers1.setStatus("1");
            houseApplicanTeachers1.setId(houseApplican.getHouseTeacherId());
            houseApplicanTeachersDao.updateHouseApplicanTeachers(houseApplicanTeachers1);
            //添加后续审批教师
            HouseApplicanTeachers houseApplicanTeachers = new HouseApplicanTeachers();
            houseApplicanTeachers.setHouseApplicanId(houseApplican.getId());
            for (int i = 1 ; i < houseApplican.getHouseApplicanTeachers().size()+1;i++){
                houseApplicanTeachers.setId(sequenceId.nextId());
                houseApplicanTeachers.setSort(String.valueOf(i));
                houseApplicanTeachers.setStatus("0");
                houseApplicanTeachers.setSchoolId(houseApplican.getSchoolId());
                houseApplicanTeachers.setTeacherId(houseApplican.getHouseApplicanTeachers().get(i-1));
                if (i == houseApplican.getHouseApplicanTeachers().size()){
                    houseApplicanTeachers.setNextSort("99");
                    houseApplicanTeachers.setType("1");
                }else{
                    houseApplicanTeachers.setNextSort(String.valueOf(i+1));
                    houseApplicanTeachers.setType("0");
                }
                houseApplicanTeachersDao.saveHouseApplicanTeachers(houseApplicanTeachers);
            }

            this.dormPushMsg(houseApplican,4);
        }
    }

    /**
     * TeacherStatus  判断前端点的是 同意 还是 驳回
     * 中间审批人操作
     * @param houseApplican
     */
    public void updateHouseApplicanAndTeacher(HouseApplican houseApplican) {
        HouseApplicanTeachers houseApplicanTeachers = new HouseApplicanTeachers();
        //驳回
        if (houseApplican.getTeacherStatus().equals("2")){
            houseApplicanTeachers.setId(houseApplican.getHouseTeacherId());
            houseApplicanTeachers.setStatus("2");
            houseApplicanTeachers.setRemark(houseApplican.getTeacherRemark());
            houseApplicanTeachersDao.updateHouseApplicanTeachers(houseApplicanTeachers);

            houseApplican.setStatus("2");
            houseApplican.setProgress(houseApplican.getTeacherNextSort());
            houseApplicanDao.updateHouseApplican(houseApplican);
            if (houseApplican.getInitiatePort().equals("0")){  //发起端口  0教师 1家长
                this.dormPushMsg(houseApplican,5);
            }
            if (houseApplican.getInitiatePort().equals("1")){
                this.dormPushMsg(houseApplican,6);
            }


        }
        //同意
        if (houseApplican.getTeacherStatus().equals("1")){
            //修改教师表 getHouseTeacherId为表id
            if (houseApplican.getTeacherRemark()!= null){
                houseApplicanTeachers.setRemark(houseApplican.getTeacherRemark());
            }
            houseApplicanTeachers.setId(houseApplican.getHouseTeacherId());
            houseApplicanTeachers.setStatus("1");
            houseApplicanTeachersDao.updateHouseApplicanTeachers(houseApplicanTeachers);
            //审批表
            //当前教师为  宿舍老师
            if (houseApplican.getTeacherNextSort().equals("99")){
                boolean ww = true;

                //结束审批
                houseApplican.setProgress(houseApplican.getTeacherNextSort());
                houseApplican.setStatus("1");
                houseApplicanDao.updateHouseApplican(houseApplican);
                //学生表状态改为 : 宿管同意 还未分配住宿
                HouseApplicanStudents houseApplicanStudents = new HouseApplicanStudents();
                houseApplicanStudents.setHouseApplicanId(houseApplican.getId());
                houseApplicanStudents.setType("1");
                houseApplicanStudentsDao.updateHouseApplicanStudents1(houseApplicanStudents);



                //判断是否全部已经办理过住宿
                //collect 当前提交申请表中 学生id  主
                List<String> collect = houseApplicanStudentsDao.findHouseApplicanStudentsListByCondition(houseApplicanStudents)
                        .stream().map(HouseApplicanStudents::getStudentId).collect(Collectors.toList());
                DormBuildingPersonInfo d = new DormBuildingPersonInfo();
                //住宿表中 与申请相同的学生
                List<DormBuildingPersonInfo> list1 = new ArrayList<>();
                for (String s : collect) {
                    d.setPersonId(s);
                    List<DormBuildingPersonInfo> student = dormPersonDao.findDormPersonInfoWithStudent(d);
                    if (student!=null && student.size() > 0){
                        list1.add(student.get(0));
                    }
                }

                if (collect.size() == list1.size()){
                    //全部相同
                    //发送推送
                    if (ww){
                        if (houseApplican.getInitiatePort().equals("0")){  //发起端口  0教师 1家长
                            this.dormPushMsg(houseApplican,11);
                        }
                        if (houseApplican.getInitiatePort().equals("1")){
                            this.dormPushMsg(houseApplican,12);
                        }
                        ww = false;
                    }
                    //把以前houseApplicanStudeng分配的数据填充
                    HouseApplicanStudents houseApplicanStudents2 = new HouseApplicanStudents();
                    houseApplicanStudents2.setHouseApplicanId(houseApplican.getId());
                    //修改状态
                    houseApplicanStudents2.setType("2");
                    for (int i = 0; i < collect.size(); i++){
                        for (int j = 0 ; j < list1.size(); j++){
                            if (collect.get(i).equals(list1.get(j).getPersonId())){
                                houseApplicanStudents2.setDormitoryId(list1.get(j).getDormBuildId());
                                houseApplicanStudents2.setDormitoryName(list1.get(j).getDormBuildName());
                                houseApplicanStudents2.setFloorId(list1.get(j).getFloorId());
                                houseApplicanStudents2.setFloorName(list1.get(j).getFloor());
                                houseApplicanStudents2.setBedRoomId(list1.get(j).getDormId());
                                houseApplicanStudents2.setBedRoomName(list1.get(j).getDormName());
                                houseApplicanStudents2.setBedCode(list1.get(j).getBunkName());
                                houseApplicanStudents2.setStudentId(list1.get(j).getPersonId());
                                houseApplicanStudentsDao.saveHouseApplicanStudents1(houseApplicanStudents2);

                            }
                        }
                    }
                }
                if (ww){
                    if (houseApplican.getInitiatePort().equals("0")){  //发起端口  0教师 1家长
                        this.dormPushMsg(houseApplican,7);
                    }
                    if (houseApplican.getInitiatePort().equals("1")){
                        this.dormPushMsg(houseApplican,8);
                    }
                }

            }else{
                houseApplican.setProgress(houseApplican.getTeacherNextSort());
                houseApplicanDao.updateHouseApplican(houseApplican);
                this.dormPushMsg(houseApplican,9);
            }

        }

    }
    @Transactional(readOnly = true)
    public List<HouseApplican> findMyApproval(HouseApplican houseApplican) {
        return houseApplicanDao.findMyApproval(houseApplican);
    }
    @Transactional(readOnly = true)
    public long findMyApprovalCount(HouseApplican houseApplican) {
        return houseApplicanDao.findMyApprovalCount(houseApplican);
    }



    private void dormPushMsg(HouseApplican houseApplican,Integer i){
        if (i==1){  //教师提交申请  给下一老师发推送 TAP
            Map<String , Object> map = new HashMap();
            map.put("title",houseApplican.getInitiateName()+"提交的申请需要您的审批");
            map.put("content","您有一条新的宿舍审批!");
            map.put("applicanType",houseApplican.getApplicanType());
            map.put("status",0); //0待审批  1审批成功  2驳回
            map.put("port",0);   //0跳我的审批    1我的申请
            String[] teacherId = new String[]{houseApplican.getHouseApplicanTeachers().get(0)};
            final Push push = Push.newBuilder(JpushApp.TAP).getComplexPush(teacherId, "宿舍申请管理", "您有一条新的宿舍审批!", Extras.DORM_APPLICANT_TAP,map);
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
            System.out.println("1");
        }else if (i==2){    //家长提交 给班主任发推送  TAP
            Map<String , Object> map = new HashMap();
            map.put("title",houseApplican.getInitiateName()+"提交的申请需要您的审批");
            map.put("content","您有一条新的宿舍审批!");
            map.put("applicanType",houseApplican.getApplicanType());
            map.put("status",0); //0待审批  1审批成功  2驳回
            map.put("port",0);   //0跳我的审批    1我的申请
            String[] teacherId = new String[]{houseApplican.getHeadTeacherId()};
            final Push push = Push.newBuilder(JpushApp.TAP).getComplexPush(teacherId, "宿舍申请管理", "您有一条新的宿舍审批!", Extras.DORM_APPLICANT_TAP,map);
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
            System.out.println("2");
        }else if (i==3){    //家长端提交 班主任拒绝  BMP
            Map<String , Object> map = new HashMap();
            map.put("title","您提交的申请已完成审批");
            map.put("content","您提交的申请已完成审批!");
            map.put("applicanType",houseApplican.getApplicanType());
            map.put("status",2); //0待审批  1审批成功  2驳回
            map.put("port",1);   //0跳我的审批    1我的申请
            String[] teacherId = new String[]{houseApplican.getStudentsId()};
            final Push push = Push.newBuilder(JpushApp.BMP).getComplexPush(teacherId, "宿舍申请管理", "您提交的申请已驳回，请知晓!", Extras.DORM_APPLICANT_BMP,map);
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
            System.out.println("3");
        }else if (i==4){    //家长端提交 班主任同意  转发下一老师  TAP
            Map<String , Object> map = new HashMap();
            map.put("title",houseApplican.getInitiateName()+"提交的申请需要您的审批");
            map.put("content","您有一条新的宿舍审批!");
            map.put("applicanType",houseApplican.getApplicanType());
            map.put("status",0); //0待审批  1审批成功  2驳回
            map.put("port",0);   //0跳我的审批    1我的申请
            String[] teacherId = new String[]{houseApplican.getHouseApplicanTeachers().get(0)};
            final Push push = Push.newBuilder(JpushApp.TAP).getComplexPush(teacherId, "宿舍申请管理", "您有一条新的宿舍审批!", Extras.DORM_APPLICANT_TAP,map);
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
            System.out.println("4");
        }else if (i==5){    //教师 提交 中间审批人/宿管老师 操作驳回  转发班主任 TAP
            Map<String , Object> map = new HashMap();
            map.put("title","您提交的申请已完成审批");
            map.put("content","您提交的申请已完成审批!");
            map.put("applicanType",houseApplican.getApplicanType());
            map.put("status",2); //0待审批  1审批成功  2驳回
            map.put("port",1);   //0跳我的审批    1我的申请
            String[] teacherId = new String[]{houseApplican.getInitiateId()};
            final Push push = Push.newBuilder(JpushApp.TAP).getComplexPush(teacherId, "宿舍申请管理", "您提交的申请已完成审批!", Extras.DORM_APPLICANT_TAP,map);
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
            System.out.println("5");
        }else if (i==6){    //家长 提交 中间审批人/宿管老师 操作驳回  转发家长 BMP
            Map<String , Object> map = new HashMap();
            map.put("title","您提交的申请已完成审批");
            map.put("content","您提交的申请已完成审批!");
            map.put("applicanType",houseApplican.getApplicanType());
            map.put("status",2); //0待审批  1审批成功  2驳回
            map.put("port",1);   //0跳我的审批    1我的申请
            String[] teacherId = new String[]{houseApplican.getStudentsId()};
            final Push push = Push.newBuilder(JpushApp.BMP).getComplexPush(teacherId, "宿舍申请管理", "您提交的申请已驳回，请知晓!", Extras.DORM_APPLICANT_BMP,map);
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
            System.out.println("6");
        }else if (i==7){    //班主任提交 宿管老师同意  转发班主任   TAP
            Map<String , Object> map = new HashMap();
            map.put("title","您提交的申请已完成审批");
            map.put("content","您提交的申请已完成审批!");
            map.put("applicanType",houseApplican.getApplicanType());
            map.put("status",1); //0待审批  1审批成功  2驳回
            map.put("port",1);   //0跳我的审批    1我的申请
            String[] teacherId = new String[]{houseApplican.getInitiateId()};
            final Push push = Push.newBuilder(JpushApp.TAP).getComplexPush(teacherId, "宿舍申请管理", "您提交的申请已完成审批!", Extras.DORM_APPLICANT_TAP,map);
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
            System.out.println("7");
        }else if (i==8){    //家长提交 宿管老师同意  转发家长   BMP
            Map<String , Object> map = new HashMap();
            map.put("title","您提交的申请已完成审批");
            map.put("content","您提交的申请已完成审批!");
            map.put("applicanType",houseApplican.getApplicanType());
            map.put("status",1); //0待审批  1审批成功  2驳回
            map.put("port",1);   //0跳我的审批    1我的申请
            String[] teacherId = new String[]{houseApplican.getStudentsId()};
            final Push push = Push.newBuilder(JpushApp.BMP).getComplexPush(teacherId, "宿舍申请管理", "您提交的申请已通过，请知晓！!", Extras.DORM_APPLICANT_BMP,map);
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
            System.out.println("8");
        }else if (i==9){    //中间审批人操作同意  转发下一教师 TAP
            Map<String , Object> map = new HashMap();
            map.put("title",houseApplican.getInitiateName()+"提交的申请需要您的审批");
            map.put("content","您有一条新的宿舍审批!");
            map.put("applicanType",houseApplican.getApplicanType());
            map.put("status",0); //0待审批  1审批成功  2驳回
            map.put("port",1);   //0跳我的审批    1我的申请
            String[] teacherId = new String[]{houseApplican.getNextTeacherId()};
            final Push push = Push.newBuilder(JpushApp.TAP).getComplexPush(teacherId, "宿舍申请管理", "您有一条新的宿舍审批!", Extras.DORM_APPLICANT_TAP,map);
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
            System.out.println("9");
        }else if (i==11){    //教师申请的学生已经安排住宿  转发下一教师 TAP
            Map<String , Object> map = new HashMap();
            map.put("title","该申请单学生已安排住宿");
            map.put("content","该申请单学生已安排住宿!");
            map.put("applicanType",houseApplican.getApplicanType());
            map.put("status",1); //0待审批  1审批成功  2驳回
            map.put("port",1);   //0跳我的审批    1我的申请
            String[] teacherId = new String[]{houseApplican.getNextTeacherId()};
            final Push push = Push.newBuilder(JpushApp.TAP).getComplexPush(teacherId, "宿舍申请管理", "该申请单学生已安排住宿!", Extras.DORM_APPLICANT_TAP,map);
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
            System.out.println("11");
        }else if (i==12){    //家长申请的学生已经安排住宿  转发下一家长 BMP
            Map<String , Object> map = new HashMap();
            map.put("title","该申请单学生已安排住宿");
            map.put("content","该申请单学生已安排住宿!");
            map.put("applicanType",houseApplican.getApplicanType());
            map.put("status",1); //0待审批  1审批成功  2驳回
            map.put("port",1);   //0跳我的审批    1我的申请
            String[] teacherId = new String[]{houseApplican.getStudentsId()};
            final Push push = Push.newBuilder(JpushApp.BMP).getComplexPush(teacherId, "宿舍申请管理", "该申请单学生已安排住宿!", Extras.DORM_APPLICANT_BMP,map);
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
            System.out.println("12");
        }

    }
}
